package group.zealot.king.base.util;

import com.jcraft.jsch.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class SftpUtil {
    private static final ConcurrentHashMap<String, FtpEntity> cache = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    public static void loadCache(FtpEntity ftpEntity) {
        cache.put(ftpEntity.getName(), ftpEntity);
    }

    protected static ChannelSftp connect(String key) throws JSchException {
        FtpEntity ftpEntity = cache.get(key);
        String host = ftpEntity.getHostname();
        int port = ftpEntity.getPort();
        String username = ftpEntity.getUsername();
        String password = ftpEntity.getPassword();
        return connect(host, port, username, password);
    }

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    protected static ChannelSftp connect(String host, int port, String username, String password) throws JSchException {
        ChannelSftp sftp;
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.setTimeout(60000);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
        return sftp;
    }

    /**
     * 上传文件
     *
     * @param inputStream    要上传的文件IO流
     * @param directory      上传的目录
     * @param uploadFileName 上传后的文件名称
     * @param sftp           sftp
     */
    public static synchronized void upload(InputStream inputStream, String directory, String uploadFileName, ChannelSftp sftp)
            throws SftpException {
        logger.debug("开始 upload");
        sftp.cd("/");
        sftp.cd(directory);
        sftp.put(inputStream, uploadFileName);
    }

    /**
     * 上传本地文件
     *
     * @param directory      上传的目录
     * @param uploadFile     要上传的文件
     * @param uploadFileName 上传后的文件名称
     * @param sftp           sftp
     */
    public static synchronized void upload(String directory, String uploadFile, String uploadFileName, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        upload(new FileInputStream(new File(uploadFile)), directory, uploadFileName, sftp);
    }

    /**
     * 上传本地文件
     *
     * @param flie           要上传的文件
     * @param directory      上传的目录
     * @param uploadFileName 上传后的文件名称
     * @param sftp           sftp
     */
    public static synchronized void upload(File flie, String directory, String uploadFileName, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        upload(new FileInputStream(flie), directory, uploadFileName, sftp);
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名称
     * @param saveFile     存在本地的文件路径名称
     * @param sftp         sftp
     */
    public static synchronized void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        File file = new File(saveFile);
        download(directory, downloadFile, file, sftp);
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名称
     * @param file         存在本地的文件
     * @param sftp         sftp
     */
    public static synchronized void download(String directory, String downloadFile, File file, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        logger.debug("开始 download");
        sftp.cd("/");
        sftp.cd(directory);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @return 下载文件流
     */
    public static synchronized InputStream download(String directory, String downloadFile, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 download is " + directory);
        sftp.cd("/");
        InputStream is;
        sftp.cd(directory);
        is = sftp.get(downloadFile);
        return is;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp       sftp
     */
    public static synchronized void delete(String directory, String deleteFile, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 delete " + directory + " " + deleteFile);
        sftp.cd("/");
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 重命名文件
     *
     * @param path    原文件路径
     * @param oldName 原名称
     * @param newName 新名称
     * @param sftp    sftp
     * @throws SftpException
     */
    public static synchronized void rename(String path, String oldName, String newName, ChannelSftp sftp) throws SftpException {
        rename(path + "/" + oldName, path + "/" + newName, sftp);
    }

    /**
     * 重命名文件
     *
     * @param oldpath 文件绝对路径+旧名称
     * @param newpath 文件绝对路径+新名称
     * @param sftp    sftp
     * @throws SftpException
     */
    public static synchronized void rename(String oldpath, String newpath, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 rename " + oldpath + " " + newpath);
        sftp.cd("/");
        sftp.rename(oldpath, newpath);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp      sftp
     * @return
     * @throws SftpException
     */
    public static synchronized Vector<ChannelSftp.LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 listFiles " + directory);
        sftp.cd("/");
        return sftp.ls(directory);
    }

    /**
     * 创建目录
     *
     * @param path 目录地址
     * @param sftp sftp
     * @throws SftpException
     */
    public static synchronized void mkDir(String path, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 mkDir " + path);
        sftp.cd("/");
        sftp.mkdir(path);
    }

    /**
     * 创建目录（会逐级判断，不存在则创建）
     *
     * @param path 目录地址
     * @param sftp sftp
     * @throws SftpException
     */
    public static synchronized void mkDir2(String path, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 mkDir2 " + path);
        sftp.cd("/");
        String[] paths = path.split("/");
        for (int i = 0; i < paths.length; i++) {
            if (!StringUtil.isEmpty(paths[i])) {
                String str = "";
                for (int j = 0; j <= i; j++) {
                    str += "/" + paths[j];
                }
                str = str.replaceAll("//", "/");
                if (!isHaveDir2(str, sftp)) {
                    sftp.mkdir(str);
                }
            }
        }
    }

    /**
     * 判断指定目录是否存在
     *
     * @param directory 目录
     * @param sftp      sftp
     * @return
     * @throws SftpException
     */
    public static synchronized boolean isHaveDir(String directory, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 isHaveDir " + directory);
        sftp.cd("/");
        try {
            sftp.cd(directory);
            return true;
        } catch (SftpException e) {
            if (e.getMessage().contains("No such file")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    /**
     * 判断指定目录是否存在
     *
     * @param directory 目录
     * @param sftp      sftp
     * @return
     * @throws SftpException
     */
    public static synchronized boolean isHaveDir2(String directory, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 isHaveDir2 " + directory);
        try {
            SftpATTRS attrs = stat(directory, sftp);
            if (attrs != null) {
                return true;
            } else {
                return false;
            }
        } catch (SftpException e) {
            if (e.getMessage().contains("No such file")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    /**
     * 判断指定目录下是否有指定文件
     *
     * @param directory 文件目录
     * @param fileName  文件名称
     * @param sftp      sftp
     * @throws SftpException
     */
    public static synchronized boolean isHaveFile(String directory, String fileName, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 isHaveFile " + directory + " " + fileName);
        sftp.cd("/");
        Vector<ChannelSftp.LsEntry> vector = listFiles(directory, sftp);
        if (vector != null) {
            for (ChannelSftp.LsEntry lsEntry : vector) {
                if (lsEntry == null && lsEntry.getFilename() == null) {
                    continue;
                }
                if (fileName.length() == lsEntry.getFilename().length() && fileName.equals(lsEntry.getFilename())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断指定目录下是否有指定文件【方法2】
     *
     * @param directory 文件目录
     * @param fileName  文件名称
     * @param sftp      sftp
     * @return
     * @throws SftpException
     */
    public static synchronized boolean isHaveFile2(String directory, String fileName, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 isHaveFile2 " + directory + " " + fileName);
        sftp.cd("/");
        try {
            InputStream is = download(directory, fileName, sftp);
            try {
                is.close();
            } catch (IOException e) {
                logger.error("关闭InputStream异常", e);
            }
            return true;
        } catch (SftpException e) {
            if (e.getMessage().contains("No such file")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    public static synchronized boolean isHaveFile3(String directory, String fileName, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 isHaveFile3 " + directory + " " + fileName);
        try {
            SftpATTRS attrs = stat(directory + File.separatorChar + fileName, sftp);
            if (attrs != null) {
                return true;
            } else {
                return false;
            }
        } catch (SftpException e) {
            if (e.getMessage().contains("No such file")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    public static synchronized SftpATTRS stat(String directory, ChannelSftp sftp) throws SftpException {
        logger.debug("开始 stat " + directory);
        return sftp.stat(directory);
    }

    protected static synchronized void disConnectedSftp(ChannelSftp old) {
        if (old != null) {
            logger.info("尝试关闭sftp连接");
            try {
                if (old.getSession() != null) {
                    old.getSession().disconnect();
                }
                old.disconnect();
            } catch (JSchException e) {
                logger.error("关闭old连接异常", e);
            }
        }
    }

    public static void main(String[] args) throws JSchException, SftpException {

//        System.out.println(isHaveDir("/egsn/201810", connect("dcp")));
//        isHaveFile3("/data/cdr_copy/egsn/201905/", "GLPGW1_20190529133755_9195_50000011", connect("dcp"));
        System.exit(0);
    }

    @Getter
    @Setter
    static class FtpEntity {
        private String name;
        private String hostname;
        private String username;
        private String password;
        private int port;
    }
}
