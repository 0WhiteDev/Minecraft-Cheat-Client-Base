package client.whitedev.helper;

import java.time.Instant;
import java.util.ArrayList;

public final class TimeHelper {
    private static final TimeHelper tpsTimer = new TimeHelper();
    private static final ArrayList times = new ArrayList();
    public static long currentCalcLag;
    public static double lastTps = 20.0D;
    private static long lastMS = 0L;
    public long time = System.nanoTime() / 1000000L;
    public long tick;
    public int time1;
    public boolean enabling;
    private long prevMS = 0L;

    public static double fromMillis(long millis) {
        return (double) millis / 1000.0D;
    }

    public static long getCurrentMillis() {
        return Instant.now().toEpochMilli();
    }

    public static double getCurrentTime() {
        return fromMillis(getCurrentMillis());
    }

    public static double calcDuration(double startTime) {
        return getCurrentTime() - startTime;
    }

    public static double calcDuration(double startTime, double endTime) {
        return endTime - startTime;
    }

    public static double calcDuration(double startTime, long endTimeMillis) {
        return calcDuration(startTime, fromMillis(endTimeMillis));
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public static boolean hasReached(long millisecounds) {
        return getCurrentMS() - lastMS >= millisecounds;
    }

    public static void resets() {
        lastMS = getCurrentMS();
    }

    public static long bytesToMb(long l) {
        return l / 1024L / 1024L;
    }

    public static long getLag() {
        return System.currentTimeMillis() - currentCalcLag;
    }

    public static long getFormattedLag() {
        long currentLag = getLag();
        return currentLag >= 2500L && currentLag < 5000000L ? currentLag : 0L;
    }

    public void update() {
        this.time = this.enabling ? ++this.time : --this.time;
        if (this.time < 0L) {
            this.time = 0L;
        }

        if (this.time > (long) this.getMaxTime()) {
            this.time = this.getMaxTime();
        }

    }

    public void reset1() {
        this.time = 0L;
    }

    public int getMaxTime() {
        return 10;
    }

    public int getTime1() {
        return this.time1;
    }

    public void on() {
        this.enabling = true;
    }

    public boolean delay(float milliSec) {
        return (float) (this.getTime() - this.prevMS) >= milliSec;
    }

    public void resetss() {
        this.prevMS = this.getTime();
    }

    public long getTimes() {
        return System.nanoTime() / 1000000L;
    }

    public long getDifference() {
        return this.getTime() - this.prevMS;
    }

    public void setDifference(long difference) {
        this.prevMS = this.getTime() - difference;
    }

    public boolean isDelayComplete(float f) {
        return (float) (System.currentTimeMillis() - lastMS) >= f;
    }

    public void setLastMS(long lastMS) {
        TimeHelper.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int perSecound) {
        return 1000 / perSecound;
    }

    public void updateTiming() {
        ++this.time1;
    }

    public void deleteTiming() {
        --this.time1;
    }

    public void updateTick() {
        ++this.tick;
    }

    public void resetTick() {
        this.tick = 0L;
    }

    public boolean hasTimePassedTick(long ticks) {
        return this.tick >= ticks;
    }

    public long getTick() {
        return this.tick;
    }

    public int getTiming() {
        return this.time1;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }

            return true;
        } else {
            return false;
        }
    }

    public long getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }

    public long getTimeing() {
        return System.nanoTime() / 1000L;
    }

    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
}
