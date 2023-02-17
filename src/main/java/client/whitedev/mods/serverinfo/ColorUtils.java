package client.whitedev.mods.serverinfo;

import java.awt.*;

public class ColorUtils {

    public static Color transpartensColor(Color colorType, int alpha) {
        return new Color(colorType.getRed(), colorType.getGreen(), colorType.getBlue(), alpha);
    }

}
