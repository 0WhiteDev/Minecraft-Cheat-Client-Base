package client.whitedev.helper;

import java.util.ArrayList;
import java.util.Iterator;

public class RandomHelper {
    private static final RandomHelper instance = new RandomHelper();

    public static RandomHelper getRandomHelper() {
        return instance;
    }

    public String getRandomChar(boolean UpperCase) {
        String randomChar = "null";
        int randomInt = this.getRandomInt(0, 25);
        if (randomInt == 0) {
            randomChar = "a";
        }

        if (randomInt == 1) {
            randomChar = "b";
        }

        if (randomInt == 2) {
            randomChar = "c";
        }

        if (randomInt == 3) {
            randomChar = "d";
        }

        if (randomInt == 4) {
            randomChar = "e";
        }

        if (randomInt == 5) {
            randomChar = "f";
        }

        if (randomInt == 6) {
            randomChar = "g";
        }

        if (randomInt == 7) {
            randomChar = "h";
        }

        if (randomInt == 8) {
            randomChar = "i";
        }

        if (randomInt == 9) {
            randomChar = "j";
        }

        if (randomInt == 10) {
            randomChar = "k";
        }

        if (randomInt == 11) {
            randomChar = "l";
        }

        if (randomInt == 12) {
            randomChar = "m";
        }

        if (randomInt == 13) {
            randomChar = "n";
        }

        if (randomInt == 14) {
            randomChar = "o";
        }

        if (randomInt == 15) {
            randomChar = "p";
        }

        if (randomInt == 16) {
            randomChar = "q";
        }

        if (randomInt == 17) {
            randomChar = "r";
        }

        if (randomInt == 18) {
            randomChar = "s";
        }

        if (randomInt == 19) {
            randomChar = "t";
        }

        if (randomInt == 20) {
            randomChar = "u";
        }

        if (randomInt == 21) {
            randomChar = "v";
        }

        if (randomInt == 22) {
            randomChar = "w";
        }

        if (randomInt == 23) {
            randomChar = "x";
        }

        if (randomInt == 24) {
            randomChar = "y";
        }

        if (randomInt == 25) {
            randomChar = "z";
        }

        return UpperCase ? randomChar.toUpperCase() : randomChar;
    }

    public boolean getRandomBoolean() {
        boolean randomBoolean = false;
        int randomInt = this.getRandomInt(0, 1);
        if (randomInt == 0) {
            randomBoolean = true;
        }

        if (randomInt == 1) {
            randomBoolean = false;
        }

        return randomBoolean;
    }

    public String getRandomStringWithRandomUpperCase(int length) {
        String randomString = "";
        int randomInt = this.getRandomInt(0, 25);

        for (int i = 0; i <= length - 1; ++i) {
            randomString = randomString + this.getRandomChar(this.getRandomBoolean());
        }

        return randomString;
    }


    public String getRandomString(int length, boolean UpperCase) {
        String randomString = "";
        int randomInt = this.getRandomInt(0, 25);

        for (int i = 0; i <= length - 1; ++i) {
            randomString = randomString + this.getRandomChar(UpperCase);
        }

        return randomString;
    }

    public String getRandomStringFromArrayList(ArrayList<String> array, String abweichung) {
        int nextInt = 0;
        int randomInt = this.getRandomInt(0, array.size());

        for (Iterator var5 = array.iterator(); var5.hasNext(); ++nextInt) {
            String string = (String) var5.next();
            if (nextInt == randomInt) {
                return string;
            }
        }

        return abweichung;
    }

    public float getRandomFloat(int min, int max) {
        return (float) ((double) min + Math.random() * (double) (max - min + 1));
    }

    public double getRandomDouble(int min, int max) {
        return (double) min + Math.random() * (double) (max - min + 1);
    }

    public int getRandomInt(int min, int max) {
        return (int) ((double) min + Math.random() * (double) (max - min + 1));
    }

}
