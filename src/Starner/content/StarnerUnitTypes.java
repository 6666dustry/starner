package Starner.content;

import Starner.entites.bullet.MultiSplashBulletType;
import Starner.entites.effect.StarTrail;
import Starner.entites.unit.TrailUnit;
import Starner.type.unit.TrailUnitType;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.draw.DrawCrucibleFlame;
import mindustry.world.draw.DrawFlame;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Trail;
import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.*;
import arc.util.Time;

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

    public static UnitType DebriStar, CometSlicer, CometSpawner, StarSniper;

    public static void load() {
        setupID();

        DebriStar = new TrailUnitType("debri-star") {
            {
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
                description = "we are star!";
                details = "star slice!";
                flying = true;
                speed = 2.5f;
                health = 425;
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
        CometSpawner = new TrailUnitType("comet-spawner") {
            {
                trailEffect = new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                };
                parts.add(new DrawPart() {
                    public void load(String name) {

                    };

                    public void draw(PartParams params) {
                        float f = Interp.sine.apply(Time.time / 60f);

                        Draw.color(Color.sky);
                        Fill.circle(params.x, params.y, 1.5f + 1f + f);
                        Draw.color(Color.white);
                        Fill.circle(params.x, params.y, 1.5f + f);
                        // TODO draw light. why this does't work?
                        Drawf.light(params.x, params.y, 80f, Color.sky, 1);
                    };
                });
                hitSize = 20f;
                trailChance = 0.3f;
                description = "spawn comets";
                details = "";
                flying = true;
                speed = 1.5f;
                health = 890;
                armor = 1.5f;
                range = 240f;
                deathExplosionEffect = new StarTrail() {
                    {
                        length = length * 4.5f;
                        particles = 15;
                    }
                };
                abilities.add(new ForceFieldAbility(40f, 0.1f, 250, 300f));
                weapons.add(new Weapon() {
                    {
                        mirror = false;
                        x = 0;
                        y = -3;
                        shoot = new ShootAlternate() {

                            {
                                shots = 5;
                                shotDelay = 6f;
                                barrels = 1;
                            }
                        };

                        shootSound = Sounds.missile;
                        shootCone = 360f;
                        rotate = false;
                        inaccuracy = 360f;
                        layerOffset = 1;
                        reload = 60f;
                        bullet = new MissileBulletType(2f, 15) {
                            {
                                status = StatusEffects.freezing;
                                statusDuration = 120f;
                                layer = Layer.flyingUnit + 0.1f;
                                lifetime = 190f;
                                trailLength = 20;
                                trailWidth = 2f;
                                rangeOverride = 300f;

                                trailColor = Color.sky;
                                width = height = 7.5f;
                                frontColor = Color.white;
                                backColor = Color.sky;
                                homingDelay = 20f;
                                homingRange = 240f;
                                homingPower = 0.04f;
                            }
                        };
                    }
                });
            }
        };
        StarSniper = new TrailUnitType("star-sniper") {
            {
                trailEffect = new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                };
                trailChance = 0.3f;
                description = "spawn comets";
                details = "";
                hitSize = 48f;
                flying = true;
                speed = 1f;
                armor = 4.5f;
                health = 6290;
                range = 240f;
                deathExplosionEffect = new StarTrail() {
                    {
                        length = length * 4.5f;
                        particles = 15;
                    }
                };
                weapons.add(new Weapon("starner-point-cannon") {
                    {
                        mirror = false;
                        x = 0;
                        y = 2;
                        recoil = 3f;
                        layerOffset = 1;

                        shootSound = Sounds.missile;
                        shootCone = 0f;
                        rotate = true;
                        rotateSpeed = 1.5f;
                        rotationLimit = 360f;
                        reload = 240f;
                        bullet = new PointBulletType() {
                            {
                                speed = 3.6f;
                                damage = 135;
                                trailSpacing = 3f;
                                trailEffect = new StarTrail() {
                                    {
                                        length = length * 3;
                                        particles = 1;
                                        sizeFrom = 8f;
                                    }
                                };
                                despawnEffect = new StarTrail() {
                                    {
                                        particles = 8;
                                        sizeFrom = 10f;
                                        lifetime = 20f;
                                        length = 60f;
                                    }
                                };
                                fragBullets = 1;
                                fragBullet = new MultiSplashBulletType() {
                                    {
                                        speed = 0;
                                        collides = false;
                                        width = height = 0;
                                        fragBullets = 8;
                                        fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                        fragRandomSpread = 0f;
                                        fragSpread = 360f / fragBullets;
                                        lifetime = 180;
                                        splashInterval = 60;
                                        fragBullet = new BasicBulletType(3f, 20, "starner-star-bullet") {
                                            {
                                                rangeOverride = 16f;

                                                frontColor = backColor = Color.valueOf("ffffffff");
                                                weaveRandom = false;
                                                lifetime = 35f;
                                                width = height = 10f;
                                                weaveMag = 4f;
                                                weaveScale = 90f;
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                }, new Weapon("starner-comet-cannon") {
                    {
                        x = -25;
                        y = 0;
                        shoot = new ShootAlternate() {

                            {
                                shots = 3;
                                shotDelay = 6f;
                                barrels = 1;
                            }
                        };

                        shootSound = Sounds.missile;
                        shootCone = 90f;
                        rotate = true;
                        rotationLimit = 25f;
                        rotateSpeed = 6f;
                        inaccuracy = 45f;
                        layerOffset = 1;
                        reload = 20f;
                        bullet = new MissileBulletType(2f, 20) {
                            {
                                status = StatusEffects.freezing;
                                statusDuration = 150f;
                                lifetime = 190f;
                                trailLength = 30;
                                trailWidth = 2.5f;
                                rangeOverride = 300f;

                                trailColor = Color.sky;
                                width = height = 9.5f;
                                frontColor = Color.white;
                                backColor = Color.sky;
                                homingDelay = 20f;
                                homingRange = 280f;
                                homingPower = 0.04f;
                            }
                        };
                    }
                }, new Weapon() {
                    {
                        mirror = false;
                        x = 0;
                        y = 20;
                        shoot = new ShootAlternate() {

                            {
                                shots = 6;
                                shotDelay = 8f;
                                barrels = 1;
                            }
                        };

                        shootSound = Sounds.missile;
                        shootCone = 100f;
                        inaccuracy = 90f;
                        layerOffset = -1;
                        reload = 100f;
                        bullet = new MissileBulletType(2.5f, 30) {
                            {
                                status = StatusEffects.burning;
                                statusDuration = 120f;
                                lifetime = 200f;
                                trailLength = 40;
                                trailWidth = 3f;
                                rangeOverride = 300f;

                                trailColor = Color.orange;
                                width = height = 12.5f;
                                frontColor = Color.white;
                                backColor = Color.orange;
                                weaveMag = 2.5f;
                                weaveScale = 20f;
                                homingDelay = 55f;
                                homingRange = 350f;
                                homingPower = 0.075f;
                            }
                        };
                    }
                }, new Weapon() {
                    {
                        baseRotation = 180;
                        mirror = false;
                        x = 0;
                        y = -20;
                        shoot = new ShootAlternate() {

                            {
                                shots = 6;
                                shotDelay = 8f;
                                barrels = 1;
                            }
                        };
                        shootSound = Sounds.missile;
                        shootCone = 360f;
                        inaccuracy = 90f;
                        layerOffset = -1;
                        reload = 100f;
                        bullet = new MissileBulletType(2.5f, 30) {
                            {
                                status = StatusEffects.burning;
                                statusDuration = 120f;
                                lifetime = 250f;
                                trailLength = 40;
                                trailWidth = 3f;
                                rangeOverride = 300f;

                                trailColor = Color.orange;
                                width = height = 12.5f;
                                frontColor = Color.white;
                                backColor = Color.orange;
                                weaveMag = 2.5f;
                                weaveScale = 20f;
                                homingDelay = 55f;
                                homingRange = 350f;
                                homingPower = 0.075f;
                            }
                        };
                    }
                });
            }
        };

        override();
    }

    private static void override() {
        Reconstructor additive = (Reconstructor) Blocks.additiveReconstructor;
        additive.upgrades.add(new UnitType[] { DebriStar, CometSlicer });
        Reconstructor multi = (Reconstructor) Blocks.multiplicativeReconstructor;
        multi.upgrades.add(new UnitType[] { CometSlicer, CometSpawner });
        Reconstructor expo = (Reconstructor) Blocks.exponentialReconstructor;
        expo.upgrades.add(new UnitType[] { CometSpawner, StarSniper });
    }
}
