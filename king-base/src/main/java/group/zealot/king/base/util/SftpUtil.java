package group.zealot.king.base.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SftpUtil {
    private static ConcurrentHashMap<String, SftpObject> cache = new ConcurrentHashMap<>();

    public static void load(String key, String ip, int port, String username, String password) {
        cache.put(key, new SftpObject(ip, port, username, password.toCharArray()));
    }

    public static ChannelSftp connect(String cacheName) throws JSchException {
        SftpObject sftpObject = cache.get(cacheName);
        return connect(sftpObject.ip, sftpObject.port, sftpObject.username, new String(sftpObject.password));
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
    public static ChannelSftp connect(String host, int port, String username, String password) throws JSchException {
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
        ChannelSftp sftp = (ChannelSftp) channel;
        return sftp;
    }

    /**
     * 上传文件
     *
     * @param directory      上传的目录
     * @param uploadFile     要上传的文件
     * @param uploadFileName 上传后的文件名称
     * @param sftp           sftp
     */
    public static void upload(String directory, String uploadFile, String uploadFileName, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        sftp.cd(directory);
        File file = new File(uploadFile);
        sftp.put(new FileInputStream(file), uploadFileName);
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp         sftp
     */
    public static void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp)
            throws SftpException, FileNotFoundException {
        sftp.cd(directory);
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @return 下载文件流
     */
    public static InputStream download(String directory, String downloadFile, ChannelSftp sftp) throws SftpException {
        InputStream is = null;
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
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) throws SftpException {
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
    public static void rename(String path, String oldName, String newName, ChannelSftp sftp) throws SftpException {
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
    public static void rename(String oldpath, String newpath, ChannelSftp sftp) throws SftpException {
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
    public static Vector<ChannelSftp.LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 创建目录
     *
     * @param path 目录地址
     * @param sftp sftp
     * @throws SftpException
     */
    public static void mkDir(String path, ChannelSftp sftp) throws SftpException {
        sftp.mkdir(path);
    }

    /**
     * 创建目录（会逐级判断，不存在则创建）
     *
     * @param path 目录地址
     * @param sftp sftp
     * @throws SftpException
     */
    public static void mkDir2(String path, ChannelSftp sftp) throws SftpException {
        String[] paths = path.split("/");
        for (int i = 0; i < paths.length; i++) {
            if (!StringUtil.isEmpty(paths[i])) {
                String str = "";
                for (int j = 0; j <= i; j++) {
                    str += "/" + paths[j];
                }
                str = str.replaceAll("//", "/");
                if (!isHaveDir(str, sftp)) {
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
    public static boolean isHaveDir(String directory, ChannelSftp sftp) throws SftpException {
        if (directory == null) {
            return false;
        }
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
     * 判断指定目录下是否有指定文件
     *
     * @param directory 文件目录
     * @param fileName  文件名称
     * @param sftp      sftp
     * @throws SftpException
     */
    public static boolean isHaveFile(String directory, String fileName, ChannelSftp sftp) throws SftpException {
        if (fileName == null) {
            return false;
        }
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
        }
        return false;
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
    public static boolean isHaveFile2(String directory, String fileName, ChannelSftp sftp) throws SftpException {
        if (fileName == null) {
            return false;
        }
        try {
            InputStream is = download(directory, fileName, sftp);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
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

    static class SftpObject {
        private String ip;
        private int port;
        private String username;
        private char[] password;

        SftpObject(String ip, int port, String username, char[] password) {
            this.ip = ip;
            this.port = port;
            this.username = username;
            this.password = password;
        }

        @Override
        public int hashCode() {
            return getCode().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return getCode().equals(o);
        }

        private String getCode() {
            return ip + port + username + password;
        }
    }
}
