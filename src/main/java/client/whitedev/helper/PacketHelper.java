package client.whitedev.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public enum PacketHelper {
    instance;

    private static final DecimalFormat df = new DecimalFormat();
    private static final TimeHelper th = new TimeHelper();
    private static final float listTime = 300.0F;
    private static final ArrayList<Float> tpsList = new ArrayList<>();
    public static float fiveMinuteTPS = 0.0F;
    public static double lastTps;
    public static double tps;
    private static int tempTicks = 0;
    private static long lastReceiveTime;
    private static long startTime;
    private static boolean doneOneTime;
    private static long lastMS;
    private static int packetsPerSecond;
    private static int packetsPerSecondTemp = 0;

    public static void onPacketReceive(Packet event) {
        lastTps = tps;
        if (event instanceof S01PacketJoinGame) {
            tps = 20.0D;
            fiveMinuteTPS = 20.0F;
        }

        if (event instanceof S03PacketTimeUpdate || event instanceof S00PacketKeepAlive) {
            long currentReceiveTime = System.currentTimeMillis();
            if (lastReceiveTime != -1L) {
                long timeBetween = currentReceiveTime - lastReceiveTime;
                double neededTps = (double) timeBetween / 50.0D;
                double multi = neededTps / 20.0D;
                tps = 20.0D / multi;
                if (tps < 0.0D) {
                    tps = 0.0D;
                }

                if (tps > 20.0D) {
                    tps = 20.0D;
                }
            }

            lastReceiveTime = currentReceiveTime;
        }

        if (event instanceof S03PacketTimeUpdate || event instanceof S00PacketKeepAlive) {
            ++packetsPerSecondTemp;
        }

    }

    public static void onUpdate() {
        if (TimeHelper.hasReached(2000L) && getServerLagTime() > 5000L) {
            th.reset();
            tps /= 2.0D;
        }

        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
            tpsList.clear();
        }

        float tteemmpp = 0.0F;
        if (tempTicks >= 20) {
            tpsList.add((float) tps);
            tempTicks = 0;
        }

        if ((float) tpsList.size() >= listTime) {
            tpsList.clear();
            tpsList.add((float) tps);
        }

        Float aFloat;
        for (Iterator<Float> var1 = tpsList.iterator(); var1.hasNext(); tteemmpp += aFloat) {
            aFloat = var1.next();
        }

        fiveMinuteTPS = tteemmpp / (float) tpsList.size();
        ++tempTicks;
        if (System.currentTimeMillis() - lastMS >= 1000L) {
            lastMS = System.currentTimeMillis();
            packetsPerSecond = packetsPerSecondTemp;
            packetsPerSecondTemp = 0;
        }

        if (packetsPerSecond < 1) {
            if (!doneOneTime) {
                startTime = System.currentTimeMillis();
                doneOneTime = true;
            }
        } else {
            if (doneOneTime) {
                doneOneTime = false;
            }

            startTime = 0L;
        }

    }

    public static long getServerLagTime() {
        return startTime <= 0L ? 0L : System.currentTimeMillis() - startTime;
    }

}
