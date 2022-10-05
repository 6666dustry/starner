package Starner.content;

import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.content.Items;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.game.Objectives.Objective;
import mindustry.type.ItemStack;
import static Starner.content.StarnerBlocks.*;
import static Starner.content.StarnerItems.*;
import static Starner.content.StarnerUnitTypes.*;
import static mindustry.content.Blocks.*;

public class StarnerTechTree {

    public static void load() {
        // item.
        newNode(MoonStone, Items.coal, null, Seq.with(new Objectives.Produce(MoonStone)));
        newNode(CometPiece, MoonStone, null, Seq.with(new Objectives.Produce(CometPiece)));
        newNode(SunCrystal, CometPiece, null, Seq.with(new Objectives.Produce(SunCrystal)));
        newNodes(
                // crafting.
                StoneFuser, graphitePress,
                StoneCrimper, multiPress,
                CometMixer, cryofluidMixer,
                SunConvergencer, blastMixer,
                // turret.
                StarShooter, duo,
                StarCannon, StarShooter,
                StarConduit, mender,
                StarDuster, scorch,
                StarRocket, arc,
                StarBow, hail,
                StarPulser, salvo,
                CometFlyer, scatter,
                StarBoomer, fuse,
                CometThrower, ripple,
                StarLancer, lancer,
                Wind, wave,
                Fielder, ripple,
                // unit.
                StarFactory, groundFactory,
                DebriStar, StarFactory,
                StarSlicer, DebriStar,
                StarStriker, StarSlicer,
                BigStar, StarStriker,
                MegaStar, BigStar,
                // CometSpawner, CometSniper,
                SolarPointer, repairTurret);
    }

    private static void newNodes(UnlockableContent... nodes) {
        for (int i = 0; i < nodes.length; i += 2) {
            newNode(nodes[i], nodes[i + 1], null, null);
        }
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
