package Starner.content;

import Starner.entities.bullet.FragLaserBulletType;
import Starner.entities.bullet.MultiSplashBulletType;
import Starner.entities.effect.StarTrail;
import Starner.entities.pattern.ShootOnce;
import Starner.entities.unit.TrailUnit;
import Starner.type.unit.TrailUnitType;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Interp;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.DrawPart;
import mindustry.entities.pattern.*;
import mindustry.type.*;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.gen.*;
import mindustry.graphics.*;
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

    public static UnitType
    // star.
    DebriStar, StarSlicer, StarStriker, BigStar, MegaStar,
            // comet.
            CometSpawner, CometSniper;

    public static void load() {
        setupID();

        DebriStar = new TrailUnitType("debri-star") {
            {
                trailEffects.add(new TrailEffect(new StarTrail()) {
                    {
                        y = -5;
                    }
                });
                description = "very cheep unit.";
                details = "flying crawler.";
                flying = true;
                lowAltitude = true;
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
        StarSlicer = new TrailUnitType("star-slicer") {
            {
                description = "we are star!";
                details = "";
                flying = true;
                lowAltitude = true;
                speed = 2.5f;
                health = 425;
                armor = 1;
                range = 75f;
                deathExplosionEffect = StarnerFx.splashStar;
                fallSpeed = Float.POSITIVE_INFINITY;
                mechStepParticles = true;
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        length = length * 2;
                    }
                }) {
                    {
                        chance = 1f;
                        interval = 0.1f;
                        y = -10;
                    }
                });

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
        StarStriker = new TrailUnitType("star-striker") {
            {
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                }) {
                    {
                        chance = 0.3f;
                        y = -16;
                    }
                });
                rotateSpeed = 2.5f;
                description = "short range unit. and has a disposable force field.";
                details = "bang!";
                hitSize = 16f;
                flying = true;
                lowAltitude = true;
                speed = 2.5f;
                armor = 4.5f;
                health = 1290;
                deathExplosionEffect = new StarTrail() {
                    {
                        length = length * 4.5f;
                        particles = 10;
                    }
                };
                abilities.add(new ForceFieldAbility(25f, 0.75f, 1500, 600 * 5));
                weapons.add(new Weapon() {
                    {
                        mirror = false;
                        x = 0;
                        y = 2.5f;
                        shootCone = 45f;
                        reload = 40f;
                        shootSound = Sounds.shotgun;
                        shoot = new ShootSpread(10, 5);
                        bullet = new BasicBulletType(5f, 30, "starner-star-bullet") {
                            {
                                trailEffect = new StarTrail();
                                backColor = frontColor = Color.white;
                                lifetime = 15f;
                            }
                        };

                    }
                });
            }
        };
        BigStar = new TrailUnitType("big-star") {
            {
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                }) {
                    {
                        chance = 0.3f;
                        y = -16;
                    }
                });
                rotateSpeed = 1.75f;
                description = "big star.";
                details = "star is my god.";
                hitSize = 40f;
                flying = true;
                lowAltitude = true;
                speed = 1f;
                armor = 6.5f;
                health = 9290;
                buildSpeed = 1f;

                deathExplosionEffect = new StarTrail() {
                    {
                        length = length * 4.5f;
                        particles = 10;
                    }
                };
                weapons.add(new PointDefenseWeapon() {
                    {
                        x = 5;
                        y = 5;
                        shootY = 0;
                        reload = 4f;
                        targetInterval = 5f;
                        targetSwitchInterval = 5f;
                        bullet = new BulletType(1, 30) {
                            {
                                maxRange = 160f;
                                trailEffect = new StarTrail();
                                hitEffect = StarnerFx.splashStar;
                            }
                        };
                    }
                }, new PointDefenseWeapon() {
                    {
                        x = 9;
                        y = -19;
                        shootY = 0;
                        reload = 10f;
                        targetInterval = 5f;
                        targetSwitchInterval = 5f;
                        bullet = new BulletType(1, 60) {
                            {
                                maxRange = 160f;
                                trailEffect = new StarTrail();
                                hitEffect = StarnerFx.splashStar;
                            }
                        };
                    }
                }, new RepairBeamWeapon("starner-sun-heal") {
                    {
                        mirror = false;
                        x = 0;
                        y = 0;
                        repairSpeed = 10f;
                        beamWidth = 1.8f;
                        laserColor = Color.orange.cpy().a(0.5f);
                        bullet = new BulletType() {
                            {
                                maxRange = 130f;
                                trailEffect = new StarTrail();
                                hitEffect = StarnerFx.splashStar;
                            }
                        };
                    }
                }, new Weapon("starner-starthrower") {
                    {
                        layerOffset = -1;
                        shootSound = Sounds.flame;
                        x = 13f;
                        y = 15f;
                        rotate = true;
                        rotationLimit = 30f;
                        rotateSpeed = 1f;
                        reload = 6f;
                        recoil = 1f;
                        ejectEffect = Fx.none;
                        bullet = new BulletType(6.5f, 37f) {
                            {
                                hitSize = 7f;
                                lifetime = 13f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 2;
                                statusDuration = 60f * 6;
                                shootEffect = StarnerFx.shootStarFlame;
                                hitEffect = new StarTrail();
                                despawnEffect = Fx.none;
                                status = StatusEffects.burning;
                                keepVelocity = false;
                                hittable = false;
                            }
                        };
                    }
                });
                weapons.add(new Weapon() {
                    {
                        x = 17f;
                        y = -19f;
                        shootY = 0;
                        rotate = false;
                        shootCone = 360f;
                        reload = 30f;
                        recoil = 0f;
                        ejectEffect = Fx.none;
                        shoot = new ShootSpread(8, 360f / (8f * 2f));
                        bullet = new BulletType(6.5f, 37f) {
                            {
                                hitSize = 7f;
                                lifetime = 13f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 2;
                                statusDuration = 60f * 6;
                                shootEffect = StarnerFx.shootStarFlame;
                                hitEffect = new StarTrail();
                                despawnEffect = Fx.none;
                                status = StatusEffects.burning;
                                keepVelocity = false;
                                hittable = false;
                            }
                        };
                    }
                });
            }
        };
        MegaStar = new TrailUnitType("mega-star") {
            {
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        length = length * 3;
                    }
                }) {
                    {
                        y = -35;
                    }
                });
                description = "MEGASTAR";
                details = "very strong.";
                flying = true;
                rotateSpeed = 1f;
                speed = 0.75f;
                health = 18222;
                lowAltitude = true;
                armor = 30f;
                hitSize = 56f;
                deathExplosionEffect = new StarTrail() {
                    {
                        length = length * 30;
                        lifetime = 120;
                        particles = 25;
                    }
                };
                weapons.add(new Weapon("starner-star-lancer-weapon") {
                    {
                        mirror = false;
                        x = 0;
                        y = 2;
                        recoil = 3f;
                        layerOffset = 1;
                        shoot = new ShootOnce() {
                            {
                                shots = 9;
                                shotOnce = 3;
                                shotDelay = 20f;
                            }
                        };

                        shootSound = Sounds.laser;
                        shootCone = 20f;
                        rotate = true;
                        rotateSpeed = 0.5f;
                        rotationLimit = 360f;
                        reload = 120f;
                        bullet = new FragLaserBulletType() {
                            {
                                damage = 135;
                                length = 180f;
                                colors[0] = Color.yellow;
                            }
                        };
                    }
                }, new Weapon("starner-star-laser") {
                    {
                        y = 18.5f;
                        x = 12;
                        layerOffset = 1;
                        reload = 45f;
                        rotate = false;
                        shootSound = Sounds.laser;
                        bullet = new FragLaserBulletType(15) {
                            {
                                colors[0] = Color.yellow;
                                length = 220f;
                                width = width * 2;
                                instantDisappear = true;
                                fragBullets = 16;
                                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                                fragRandomSpread = 0f;
                                fragSpread = 360f / fragBullets;
                                pierceCap = 2;
                                fragBullet = new BasicBulletType(3.2f, 6, "starner-star-bullet") {
                                    {
                                        width = height = 10f;
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
                }, new Weapon("starner-comet-cannon") {
                    {
                        x = 25;
                        y = -17;
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
                }, new Weapon("starner-point-cannon") {
                    {
                        x = -12;
                        y = -12;
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
                                        length = length * 5;
                                        particles = 2;
                                        sizeFrom = 8f;
                                    }
                                };
                                despawnEffect = new StarTrail() {
                                    {
                                        particles = 20;
                                        sizeFrom = 10f;
                                        lifetime = 25f;
                                        length = 200f;
                                    }
                                };
                                fragBullets = 1;
                                fragBullet = new MultiSplashBulletType() {
                                    {
                                        spin = 2f;
                                        speed = 0;
                                        collides = false;
                                        width = height = 0;
                                        fragBullets = 12;
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
                                                width = height = 15f;
                                                weaveMag = 4f;
                                                weaveScale = 90f;
                                            }
                                        };
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
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                }) {
                    {
                        chance = 0.3f;
                    }
                });
                parts.add(new DrawPart() {
                    public void load(String name) {

                    };

                    public void draw(PartParams params) {
                        float f = Interp.sine.apply(Time.time / 60f);

                        Draw.color(Color.sky);
                        Fill.circle(params.x, params.y, 1.5f + 1f + f);
                        Draw.color(Color.white);
                        Fill.circle(params.x, params.y, 1.5f + f);

                        Draw.z(Layer.light);
                        Drawf.light(params.x, params.y, 80f, Color.sky, 0.9f);
                    };

                });
                hitSize = 20f;
                description = "spawn comets";
                details = "";
                flying = true;
                lowAltitude = true;
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
        CometSniper = new TrailUnitType("comet-sniper") {
            {
                trailEffects.add(new TrailEffect(new StarTrail() {
                    {
                        particles = 2;
                        length = length * 3;
                        sizeFrom = sizeFrom * 1.5f;
                    }
                }) {
                    {
                        chance = 0.3f;
                        y = -26;
                    }
                });
                rotateSpeed = 2.5f;
                description = "long range unit.";
                details = "snipe!";
                hitSize = 48f;
                flying = true;
                lowAltitude = true;
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
                                        spin = 2f;
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
                                weaveScale = 10f;
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
                                weaveScale = 10f;
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
        additive.upgrades.add(new UnitType[] { DebriStar, StarSlicer });
        Reconstructor multi = (Reconstructor) Blocks.multiplicativeReconstructor;
        multi.upgrades.add(new UnitType[] { StarSlicer, StarStriker });
        Reconstructor expo = (Reconstructor) Blocks.exponentialReconstructor;
        expo.upgrades.add(new UnitType[] { StarStriker, BigStar });
        Reconstructor tetra = (Reconstructor) Blocks.tetrativeReconstructor;
        tetra.upgrades.add(new UnitType[] { BigStar, MegaStar });
    }
}
