package group.zealot.king.base;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Funcation {
    public static String getHostName() {
        try {
            return getInetAddress().getHostName();
        } catch (UnknownHostException e) {
            return "UnknownHostException";
        }
    }

    public static String getHostAddress() {
        try {
            return getInetAddress().getHostAddress();
        } catch (UnknownHostException e) {
            return "UnknownHostException";
        }
    }

    public static InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }
}
