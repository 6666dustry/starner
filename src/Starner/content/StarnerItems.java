package Starner.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class StarnerItems {
  public static Item MoonStone, CometPiece, SunCrystal, DarkMatter;

  public static void load() {
    MoonStone = new Item("moon-stone", Color.valueOf("AAAAAA")) {
      {
        cost = 0.5f;
        radioactivity = 0.2f;
        charge = 0.2f;
      }
    };
    CometPiece = new Item("comet-piece", Color.valueOf("AAAAAA")) {
      {
        cost = 0.75f;
        flammability = -0.3f;
      }
    };
    SunCrystal = new Item("sun-crystal", Color.orange) {
      {
        cost = 0.75f;
        flammability = 1f;
      }
    };
    DarkMatter = new Item("dark-matter", Color.black) {
      {
        cost = 1f;
        flammability = 1f;
        explosiveness = 1f;
        radioactivity = 1f;
        charge = 1f;
      }
    };
  }
}
