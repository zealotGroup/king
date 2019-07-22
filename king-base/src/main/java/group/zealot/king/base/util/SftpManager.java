package group.zealot.king.base.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

public class SftpManager {
    private static Logger logger = LoggerFactory.getLogger(SftpManager.class);

    private static ConcurrentHashMap<String, ChannelSftp> sftps = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> sftpsTimes = new ConcurrentHashMap<>();//次数
    private static ConcurrentHashMap<String, LocalDateTime> sftpsTime = new ConcurrentHashMap<>();//时间

    private static final long sleep_time = 1500;//获取连接异常等待时间
    private static final int tryTimes = 3;//重连失败次数
    public static final String _COPY = "_COPY";

    /**
     * 生成一个sftp连接
     *
     * @param name sftp的名称sftpNames
     */
    public static synchronized ChannelSftp getSftp(String name) throws JSchException {
        String keyName = name;
        String sftpName = getSftpName(keyName);
        ChannelSftp sftp = sftps.get(keyName);

        if (!sftpIsAlive(sftp, keyName)) {
            boolean first = true;
            int time = 0;
            do {
                if (!first) {
                    try {
                        logger.info("停1.5秒，再建立连接");
                        Thread.sleep(SftpManager.sleep_time);
                    } catch (InterruptedException e) {
                        logger.info("Thread.sleep(1500)异常", e);
                    }
                }
                ChannelSftp old = sftp;
                SftpUtil.disConnectedSftp(old);
                logger.info("重新建立新连接" + sftpName);
                try {
                    sftp = SftpUtil.connect(sftpName);
                    sftpsTime.put(keyName, LocalDateTime.now());
                    sftpsTimes.put(keyName, 0);
                } catch (JSchException e) {
                    time++;
                    logger.error("重新建立新连接【异常】" + sftpName, e);
                    if (time >= SftpManager.tryTimes) {
                        throw e;
                    }
                }
                first = false;
            } while (!sftpIsAlive(sftp, keyName));
            sftps.put(keyName, sftp);
        } else {
            Integer times = sftpsTimes.get(keyName);
            if (times == null) {
                times = 0;
            }
            sftpsTimes.put(keyName, times + 1);
        }
        return sftp;
    }

    /**
     * 判断此sftp 是否可用
     *
     * @param sftp
     * @return
     * @throws JSchException
     */
    private static boolean sftpIsAlive(ChannelSftp sftp, String keyName) throws JSchException {
        if (sftp == null || !sftp.isConnected() || !sftp.getSession().isConnected()) {
            logger.debug("sftp is unconnected");
            return false;
        }
        Integer times = sftpsTimes.get(keyName);
        if (times != null && times > 100) {
            logger.info("此连接 获取次数超过100，重新连接 times " + times);
            return false;
        }
        LocalDateTime now = sftpsTime.get(keyName);
        if (now != null && now.plusMinutes(1L).isBefore(LocalDateTime.now())) {
            logger.info("此连接 获取时间超过1min，重新连接 time " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return false;
        }
        try {
            logger.debug("sftp is connected ,size / " + SftpUtil.listFiles("/", sftp).size());
            return true;
        } catch (SftpException e) {
            logger.error("sftp cd / 异常", e);
            return false;
        }
    }

    /**
     * 通过存储在 map中的 keyName 获取sftpName（用于连接sftp）
     *
     * @param keyName
     * @return 删除 _COPY 字符后的new keyName
     */
    private static String getSftpName(String keyName) {
        if (keyName.contains(_COPY)) {
            return keyName.replaceAll(_COPY, "");
        }
        return keyName;
    }
}
