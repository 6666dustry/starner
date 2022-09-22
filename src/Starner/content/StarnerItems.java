package Starner.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class StarnerItems {
    public static Item MoonStone, CometPiece, SunCrystal;

    public static void load() {
        MoonStone = new Item("moon-stone", Color.valueOf("AAAAAA")) {
            {
                description = "It is came from moon.";
                cost = 0.5f;
                radioactivity = 0.2f;
                charge = 0.2f;
            }
        };
        CometPiece = new Item("comet-piece", Color.valueOf("AAAAAA")) {
            {
                description = "comet piece. very cool!";
                cost = 0.75f;
                flammability = -0.3f;
            }
        };
        SunCrystal = new Item("sun-crystal", Color.orange) {
            {
                description = "sun crystal. very hot!";
                cost = 0.75f;
                flammability = 1f;
            }
        };
    }
}
