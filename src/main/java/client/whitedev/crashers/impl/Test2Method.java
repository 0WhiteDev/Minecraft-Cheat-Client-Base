package client.whitedev.crashers.impl;

import client.whitedev.crashers.Method;

public class Test2Method implements Method {
    private static final String METHOD_NAME = "test2";

    @Override
    public boolean check(String string) {
        return string.equalsIgnoreCase(METHOD_NAME);
    }

    @Override
    public String getName() {
        return METHOD_NAME;
    }

    @Override
    public void onMethod(String[] args) {
        int packets = Integer.parseInt(args[2]);
        sendMessage("Sending &8[&f" + METHOD_NAME + "&8]&7 - &8[&f" + packets + "&8]", true);
        (new Thread(() -> {
            for (int c = 0; c < packets; c++) {

            }
        })).start();
        sendMessage("Packets has been sent!", true);
    }
}
