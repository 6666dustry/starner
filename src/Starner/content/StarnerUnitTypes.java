package Starner.content;

import Starner.entites.effect.StarTrail;
import Starner.entites.unit.TrailUnit;
import Starner.type.unit.TrailUnitType;
import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.gen.*;
import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.*;

@SuppressWarnings("unchecked")
public class StarnerUnitTypes {
    // I steal these from prog mats java which
    // Steal from Endless Rusting which stole from Progressed Materials in the past
    // which stole from BetaMindy
    private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[] {
            prov(TrailUnit.class, TrailUnit::new)
    };

    private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * 
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov) {
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * 
     * @author GlennFolker
     */

    private static void setupID() {
        for (int i = 0,
                j = 0,
                len = EntityMapping.idMap.length;

                i < len;

                i++) {
            if (EntityMapping.idMap[i] == null) {
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if (++j >= types.length)
                    break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * 
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type) {
        return idMap.get(type, -1);
    }

    public static UnitType DebriStar, CometSlicer;

    public static void load() {
        setupID();

        DebriStar = new TrailUnitType("debri-star") {
            {
                // constructor = TrailUnit::new;
                trailEffect = new StarTrail();
                description = "very cheep unit.";
                details = "flying crawler.";
                flying = true;
                speed = 3.5f;
                health = 105;
                fallSpeed = Float.POSITIVE_INFINITY;
                range = 16f;
                maxRange = 10f;
                fogRadius = 3f;
                mechStepParticles = true;
                deathExplosionEffect = StarnerFx.splashStar;
                weapons.add(new Weapon() {
                    {
                        mirror = false;
                        reload = 60f;
                        rotate = true;
                        shootOnDeath = true;
                        rotateSpeed = Float.POSITIVE_INFINITY;
                        bullet = new BasicBulletType() {
                            {

                                instantDisappear = true;
                                speed = 0f;
                                killShooter = true;
                                splashDamage = 10;
                                splashDamageRadius = 24f;
                                fragBullets = 14;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                lifetime = 0f;
                                rangeOverride = 10f;
                                fragBullet = new BasicBulletType(3f, 8, "starner-star-bullet") {
                                    {
                                        rangeOverride = 16f;

                                        frontColor = backColor = Color.valueOf("ffffffff");
                                        weaveRandom = false;
                                        lifetime = 35f;
                                        weaveMag = 4f;
                                        weaveScale = 90f;
                                    }
                                };
                            }
                        };
                    }
                });
            }
        };
        CometSlicer = new TrailUnitType("comet-slicer") {
            {
                // constructor = TrailUnit::new;
                description = "we are star!";
                details = "star slice!";
                flying = true;
                speed = 2.5f;
                health = 225;
                armor = 1;
                range = 75f;
                deathExplosionEffect = StarnerFx.splashStar;
                fallSpeed = Float.POSITIVE_INFINITY;
                mechStepParticles = true;
                trailEffect = new StarTrail() {
                    {
                        length = length * 2;
                    }
                };
                trailChance = 1f;
                trailInterval = 0.1f;
                weapons.add(new Weapon("starner-star-laser") {
                    {
                        y = 6f;
                        layerOffset = -1;
                        reload = 45f;
                        rotate = false;
                        shootSound = Sounds.laser;
                        bullet = new LaserBulletType(15) {
                            {
                                range = 75f;
                                length = 55f;
                                instantDisappear = true;
                                fragBullets = 16;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                pierceCap = 2;
                                fragBullet = new BasicBulletType(3.2f, 6, "starner-star-bullet") {
                                    {
                                        frontColor = backColor = Color.valueOf("ffffffff");
                                        weaveRandom = false;
                                        lifetime = 40f;
                                        weaveMag = 4.1f;
                                        weaveScale = 90f;
                                    }
                                };
                            }
                        };
                    }
                });

            }
        };
        override();
    }

    private static void override() {
        Reconstructor ride = (Reconstructor) Blocks.additiveReconstructor;
        ride.upgrades.add(new UnitType[] { DebriStar, CometSlicer });
    }
}
