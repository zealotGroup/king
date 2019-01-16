package group.zealot.king.base.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SftpManager {
    private static Logger logger = LoggerFactory.getLogger(SftpManager.class);

    private static ConcurrentHashMap<String, ChannelSftp> sftps = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, AtomicLong> sftpTimes = new ConcurrentHashMap<>();

    private static long timeout = 15000;//sftp未使用时间保护时间
    private static long timeout60 = 60000;//sftp强制超时时间
    private static long sleep_time = 1500;//获取连接异常等待时间

    public static final String _COPY = "_COPY";

    /**
     * 生成一个sftp连接
     *
     * @param sftpName sftp的名称sftpNames
     */
    public static ChannelSftp getSftp(String sftpName) throws JSchException {
        String keyName = sftpName;
        if (sftpName.contains(_COPY)) {
            sftpName = sftpName.replaceAll(_COPY, "");
        }
        ChannelSftp sftp = sftps.get(keyName);
        AtomicLong last = sftpTimes.get(keyName + "last");
        if (last == null) {
            last = new AtomicLong(0L);
            sftpTimes.put(keyName + "last", last);
        }
        AtomicLong createTime = sftpTimes.get(keyName + "createTime");
        if (createTime == null) {
            createTime = new AtomicLong(0L);
            sftpTimes.put(keyName + "createTime", createTime);
        }

        long nowTime = new Date().getTime();
        boolean timeout = (nowTime - last.get()) > SftpManager.timeout;
        boolean timeout60 = (nowTime - createTime.get()) > SftpManager.timeout60;
        last.set(nowTime);
        if (sftp == null || !sftp.isConnected() || timeout || timeout60) {
            synchronized (SftpManager.class) {
                boolean first = true;
                while (sftp == null || !sftp.isConnected() || timeout || timeout60) {
                    if (!first) {
                        try {
                            logger.info("停1.5秒，再建立连接");
                            Thread.sleep(SftpManager.sleep_time);
                        } catch (InterruptedException e) {
                            logger.info("Thread.sleep(1500)异常", e);
                        }
                    }
                    ChannelSftp old = sftp;
                    if (old != null) {
                        old.getSession().disconnect();
                        old.disconnect();
                    }
                    logger.error("重新建立新连接" + keyName);
                    sftp = SftpUtil.connect(sftpName);
                    first = false;
                    timeout = false;
                    timeout60 = false;
                }
                createTime.set(new Date().getTime());
                sftps.put(keyName, sftp);
            }
        }
        return sftp;
    }
}
