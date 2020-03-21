package group.zealot.king.core.zt;

import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;

/**
 * @author zealot
 * @date 2020/3/21 16:16
 */
public class Passwd {

    public static String getNewPassword(byte[] password, byte[] salt) {
        //加密密码
        byte[] hashPassword = DigestUtils.sha1(password, salt, 1024);
        return EncodeUtil.encodeHex(hashPassword);
    }
}
