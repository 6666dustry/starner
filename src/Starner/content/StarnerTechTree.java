package Starner.content;

import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.game.Objectives.Objective;
import mindustry.type.ItemStack;

public class StarnerTechTree {

    public static void load() {
        // item.
        newNode(StarnerItems.MoonStone, Items.coal, null, Seq.with(new Objectives.Produce(StarnerItems.MoonStone)));
        // crafting.
        newNode(StarnerBlocks.StoneFuser, Blocks.graphitePress, null, null);
        // turret.
        newNode(StarnerBlocks.StarShooter, Blocks.duo, null, null);
        newNode(StarnerBlocks.StarCannon, StarnerBlocks.StarShooter, null, null);
        newNode(StarnerBlocks.StarConduit, Blocks.mender, null, null);
        newNode(StarnerBlocks.StarDuster, Blocks.scorch, null, null);
        newNode(StarnerBlocks.StarRocket, Blocks.arc, null, null);
        newNode(StarnerBlocks.CometFlyer, Blocks.scatter, null, null);
        newNode(StarnerBlocks.CometThrower, Blocks.salvo, null, null);
        newNode(StarnerBlocks.CometMixer, Blocks.cryofluidMixer, null, null);
        newNode(StarnerBlocks.StarFactory, Blocks.groundFactory, null, null);
        newNode(StarnerUnitTypes.DebriStar, StarnerBlocks.StarFactory, null, null);
    }

    private static void newNode(UnlockableContent content, UnlockableContent parent, @Nullable ItemStack[] req,
            @Nullable Seq<Objective> Objectives) {
        TechNode parentNode = parent.techNode;
        TechNode node = new TechNode(parentNode, content, req != null ? req : content.researchRequirements());
        if (Objectives != null) {
            node.objectives.add(Objectives);
        }
    }
}
