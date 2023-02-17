package client.whitedev.mods.serverinfo;

import java.net.InetAddress;
import net.minecraft.client.multiplayer.ServerAddress;

public class DomainUtils {
    public static String getIp(String domain) {
        try {
            String ip;
            InetAddress address = InetAddress.getByName(ServerAddress.fromString(domain).getIP());
            ip = address.getHostAddress();
            return ip;
        } catch (Exception e) {
            return "";
        }
    }

    public static int getPort(String domain) {
        try {
            return ServerAddress.fromString(domain).getPort();
        } catch (Exception e) {
            return -1;
        }
    }
}