package Starner.content;

import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BlockGroup;
import mindustry.type.*;
import mindustry.world.draw.*;

import static Starner.content.StarnerItems.*;
import static mindustry.type.ItemStack.*;

import Starner.content.entites.effect.StarTrail;
import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Log;
import arc.*;
import arc.audio.Sound;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.EmpBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;

public class StarnerBlocks {

    public static Block
    // production.
    StoneFuser, CometMixer,

            // turrets.
            StarShooter, StarCannon, StarConduit, StarDuster, StarRocket, CometThrower,
            // unit factory.
            StarFactory;

    public static void load() {
        StarShooter = new ItemTurret("star-shooter") {
            {
                group = BlockGroup.turrets;
                requirements(Category.turret, with(Items.copper, 30, MoonStone, 15));
                description = "shot star.";
                details = "How beautiful!";
                health = 500;
                size = 1;
                inaccuracy = 10f;
                maxAmmo = 25;
                range = 155;
                reload = 15;
                ammo(
                        StarnerItems.MoonStone, new BasicBulletType(2f, 10, "starner-star-bullet") {
                            {
                                pierce = true;
                                pierceCap = 3;
                                spin = 6f;
                                lifetime = 1000f;
                                width = height = 10f;
                                ammoMultiplier = 4;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                weaveMag = 1.5f;
                                weaveScale = 900f;
                                sprite = "starner-star-bullet";
                                shrinkX = 0.5f;
                                homingPower = 0.01f;
                                homingRange = 250f;
                                trailChance = 1f / 5f;
                                trailRotation = true;
                                trailEffect = new StarTrail();
                                despawnEffect = new Effect();
                            }
                        },
                        StarnerItems.CometPiece, new BasicBulletType(2f, 0, "starner-star-bullet") {
                            {
                                status = StatusEffects.freezing;
                                instantDisappear = true;
                                ammoMultiplier = 2;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                weaveMag = 1.5f;
                                weaveScale = 1800f;
                                shrinkX = 0.5f;
                                homingPower = 0.01f;
                                homingRange = 250f;
                                trailChance = 1f / 5f;
                                trailRotation = true;
                                trailEffect = new StarTrail() {
                                    {
                                        colorTo = Color.valueOf("7777ff");
                                    }
                                };
                                despawnEffect = new Effect();
                                fragBullets = 3;
                                fragSpread = 360 / fragBullets;
                                fragBullet = new BasicBulletType(2f, 0, "starner-star-bullet") {
                                    {
                                        status = StatusEffects.freezing;
                                        pierce = true;
                                        pierceCap = 1;
                                        spin = 6f;
                                        lifetime = 1000f;
                                        width = height = 7.5f;
                                        frontColor = Color.valueOf("ffffffff");
                                        backColor = Color.valueOf("ffffffff");
                                        weaveMag = 1.5f;
                                        weaveScale = 900f;
                                        shrinkX = 0.5f;
                                        homingPower = 0.01f;
                                        homingRange = 250f;
                                        trailChance = 1f / 5f;
                                        trailRotation = true;
                                        trailEffect = new StarTrail() {
                                            {
                                                colorTo = Color.valueOf("7777ff");
                                            }
                                        };
                                        ;
                                        despawnEffect = new Effect();
                                    }
                                };
                            }
                        },

                        Items.pyratite, new BasicBulletType(2f, 3, "starner-star-bullet") {
                            {
                                pierce = true;
                                pierceCap = 3;
                                status = StatusEffects.burning;
                                spin = 6;
                                lifetime = 1000f;
                                width = height = 10f;
                                ammoMultiplier = 6;
                                frontColor = backColor = Color.valueOf("ffccccff");
                                makeFire = true;
                                weaveMag = 1.5f;
                                weaveScale = 900f;
                                incendChance = 0.5f;
                                sprite = "starner-star-bullet";
                                shrinkX = 0.5f;
                                trailChance = 1.0f;
                                despawnEffect = new Effect();
                            }
                        },
                        Items.metaglass, new BasicBulletType(2f, 6, "starner-star-bullet") {
                            {
                                pierce = true;
                                pierceCap = 3;
                                inaccuracy = 200f;
                                spin = 6;
                                lifetime = 900f;
                                width = height = 7.5f;
                                ammoMultiplier = 12;
                                reloadMultiplier = 1.5f;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                trailEffect = new StarTrail();
                                makeFire = true;
                                weaveMag = 1.5f;
                                weaveScale = 900f;
                                incendChance = 0.5f;
                                sprite = "starner-star-bullet";
                                shrinkX = 0.5f;
                                trailChance = 0.8f;
                                despawnEffect = new Effect();
                            }
                        });
            }
        };

        StarCannon = new ItemTurret("star-cannon") {
            {
                group = BlockGroup.turrets;
                requirements(Category.turret, with(MoonStone, 30, Items.lead, 15));
                shoot = new ShootAlternate() {
                    {
                        shots = 3;
                    }
                };
                description = "shot some stars at once.";
                details = "How many...?";
                health = 750;
                size = 2;
                maxAmmo = 20;
                inaccuracy = 5f;
                range = 135;
                ammoPerShot = 3;
                reload = 50;

                ammo(
                        StarnerItems.MoonStone, new BasicBulletType(2f, 10, "starner-star-bullet") {
                            {
                                shrinkX = 0.5f;
                                spin = 6f;
                                lifetime = 135f / speed;
                                width = height = 10f;
                                ammoMultiplier = 3;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                sprite = "starner-star-bullet";
                                hitEffect = StarnerFx.splashStar;
                                trailChance = 1f / 5f;
                                trailRotation = true;
                                trailEffect = new StarTrail();
                                despawnEffect = new Effect();
                            }
                        },
                        Items.silicon, new BasicBulletType(2f, 15, "starner-star-bullet") {
                            {
                                inaccuracy = 50f;
                                shrinkX = 0.5f;
                                spin = 6f;

                                lifetime = 135f / speed;
                                width = height = 10f;
                                ammoMultiplier = 3;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                sprite = "starner-star-bullet";
                                homingDelay = 0f;
                                homingPower = 0.1f;
                                hitEffect = StarnerFx.splashStar;
                                trailChance = 1f / 5f;
                                trailRotation = true;
                                trailEffect = new StarTrail();
                                despawnEffect = new Effect();
                            }
                        });
            }
        };

        StarConduit = new ItemTurret("star-conduit") {
            {
                group = BlockGroup.turrets;
                requirements(Category.turret, with(MoonStone, 30, Items.silicon, 20));
                description = "make aura.";
                details = "";
                health = 750;
                size = 2;
                maxAmmo = 10;
                inaccuracy = 5f;
                range = 135f;
                ammoPerShot = 3;
                reload = 200;
                heatColor = Color.valueOf("00ff00aa");
                recoil = 0;
                targetHealing = true;
                shootY = 0;
                ammo(
                        StarnerItems.MoonStone, new BombBulletType() {
                            {
                                lifetime = 0f;
                                ammoMultiplier = 1;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                splashDamageRadius = 100f;
                                splashDamage = 0;
                                healPercent = 2.5f;

                                healAmount = 10;
                                collidesTeam = true;
                                despawnEffect = hitEffect = StarnerFx.healAura;
                            }
                        },
                        Items.pyratite, new BombBulletType() {
                            {
                                status = StatusEffects.burning;
                                lifetime = 0f;
                                ammoMultiplier = 1;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                splashDamageRadius = 100f;
                                splashDamage = 5;

                                despawnEffect = hitEffect = StarnerFx.fireAura;
                            }
                        },
                        StarnerItems.CometPiece, new BombBulletType() {
                            {
                                status = StatusEffects.freezing;
                                lifetime = 0f;
                                ammoMultiplier = 3;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                rangeChange = 15f;
                                splashDamageRadius = range + rangeChange;
                                splashDamage = 10;

                                despawnEffect = hitEffect = StarnerFx.freezeAura;
                            }
                        });
            }
        };

        StarDuster = new ItemTurret("star-duster") {
            {
                group = BlockGroup.turrets;
                requirements(Category.turret, with(MoonStone, 40));
                description = "shot many many star dust.";
                details = "looks like snow?";
                shootY = -1.5f;
                health = 850;
                size = 2;
                inaccuracy = 25f;
                maxAmmo = 20;
                range = 100;
                reload = 1;
                ammo(

                        Items.scrap, new BasicBulletType(3f, 4, "starner-star-bullet") {
                            {
                                spin = 6f;
                                lifetime = 45f;
                                width = height = 5f;
                                ammoMultiplier = 4;
                                frontColor = backColor = Color.valueOf("ffffffff");
                                despawnEffect = new Effect();
                                trailChance = 1f / 6f;
                                trailEffect = new StarTrail();
                            }
                        },
                        StarnerItems.CometPiece, new BasicBulletType(3f, 6, "starner-star-bullet") {
                            {
                                status = StatusEffects.freezing;
                                spin = 6f;
                                lifetime = 46f;
                                width = height = 5f;
                                ammoMultiplier = 7;
                                frontColor = Color.valueOf("ffffffff");
                                backColor = Color.valueOf("bbbbffff");
                                despawnEffect = new Effect();
                                trailChance = 1f / 5f;
                                trailEffect = new StarTrail() {
                                    {
                                        colorTo = Color.valueOf("bbbbffff");
                                    }
                                };
                            }
                        });
            }
        };

        StarRocket = new PowerTurret("star-rocket") {
            {
                group = BlockGroup.turrets;
                requirements(Category.turret, with(MoonStone, 40, Items.silicon, 30, Items.metaglass, 30));
                description = "shot little rocket.";
                details = "rocket!";
                health = 1200;
                range = 225f;
                ;
                size = 2;
                reload = 100f;
                consumePower(300f / 60f);
                shootSound = Sounds.missile;
                BulletType spawn = new BasicBulletType(3f, 8, "starner-star-bullet") {
                    {
                        lifetime = 75f;
                        frontColor = backColor = Color.valueOf("ffffffff");
                        despawnEffect = new Effect();
                        trailChance = 1f / 6f;
                        trailEffect = new StarTrail();
                        pierce = true;
                        spin = 6f;
                        shrinkY = shrinkX = 0.5f;
                        weaveRandom = false;
                        weaveScale = lifetime * 2;
                        weaveMag = 120 / lifetime * speed;
                        width = height = 7.5f;
                    }
                };
                shootType = new MissileBulletType(3.5f, 20) {
                    {
                        frontColor = backColor = Color.valueOf("ffffffff");
                        lifetime = 75f;
                        fragBullet = spawn;
                        fragBullets = 6;
                        fragSpread = 60f;
                        fragRandomSpread = 0f;
                        trailChance = 1f;
                        trailEffect = new StarTrail();
                        fragVelocityMax = fragVelocityMin = 1f;

                        width = height = 15f;
                    }
                };
            }
        };

        CometThrower = new ItemTurret("comet-thrower") {
            {
                targetAir = false;
                group = BlockGroup.turrets;
                requirements(Category.turret, with(CometPiece, 15, MoonStone, 35, Items.copper, 35));
                description = "throw comet";
                details = "boom!";
                health = 1150;
                size = 3;
                inaccuracy = 10f;
                maxAmmo = 25;
                range = 160;
                reload = 90;
                shootY = -1;
                recoil = 2.5f;
                ammo(
                        StarnerItems.CometPiece, new ArtilleryBulletType(2f, 15) {
                            {
                                collidesAir = false;
                                splashDamage = 8;
                                splashDamageRadius = 75f;
                                width = height = 15f;
                                status = StatusEffects.freezing;
                                frontColor = backColor = Color.valueOf("bbbbffff");
                                trailChance = 1f;
                                trailRotation = true;
                                trailEffect = new StarTrail() {
                                    {
                                        sizeFrom = 6.5f;
                                        colorTo = Color.valueOf("7777ff");
                                    }
                                };
                                despawnEffect = StarnerFx.freezeAura;
                                fragBullets = 10;
                                fragRandomSpread = 110f;
                                fragLifeMin = fragLifeMax = 1f;
                                fragVelocityMin = fragVelocityMax = 1f;
                                fragBullet = new BasicBulletType(4f, 10) {
                                    {
                                        collidesAir = false;
                                        status = StatusEffects.freezing;
                                        lifetime = 15f;
                                        width = height = 7.5f;
                                        frontColor = Color.valueOf("ffffffff");
                                        backColor = Color.valueOf("9999ffff");
                                        despawnEffect = new Effect();
                                    }
                                };
                            }
                        },
                        Items.titanium, new ArtilleryBulletType(2f, 15) {
                            {
                                ammoMultiplier = 0.5f;
                                collidesAir = false;
                                splashDamage = 15;
                                splashDamageRadius = 90f;
                                width = height = 17.5f;
                                frontColor = backColor = Color.valueOf("bbbbffff");
                                trailChance = 1f;
                                trailRotation = true;
                                trailEffect = new StarTrail() {
                                    {
                                        sizeFrom = 6.5f;
                                        colorTo = Color.valueOf("7777ff");
                                    }
                                };
                                despawnEffect = StarnerFx.freezeAura;

                            }
                        });
            }
        };

        // crafting.
        StoneFuser = new GenericCrafter("stone-fuser") {
            {
                craftEffect = StarnerFx.splashStar;
                requirements(Category.crafting, with(Items.copper, 30, Items.lead, 15));
                description = "fusing moon stone.";
                size = 2;
                health = 350;
                consumeItems(with(Items.scrap, 4, Items.coal, 1));
                outputItem = new ItemStack(MoonStone, 1);
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame() {
                    {
                        flameRadiusScl = 4;
                    }
                });
            }
        };
        CometMixer = new GenericCrafter("comet-mixer") {
            {
                craftTime = 90f;
                hasLiquids = hasPower = consumesPower = true;
                liquidCapacity = 50f;
                consumePower(120f / 60f);
                consumeLiquid(Liquids.cryofluid, 2f / 60f);
                updateEffect = StarnerFx.splashStar;
                updateEffectChance = 0.25f;
                craftEffect = StarnerFx.freezeAura;
                requirements(Category.crafting,
                        with(StarnerItems.MoonStone, 60, Items.silicon, 30, Items.metaglass, 20));
                description = "mixing comet pieces";
                size = 3;
                health = 500;
                consumeItems(with(Items.scrap, 3));
                outputItem = new ItemStack(CometPiece, 2);
                drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion(), new DrawRegion("-rotate") {
                    {
                        spinSprite = true;
                        rotateSpeed = 5f;
                    }
                });
            }
        };
        StarFactory = new UnitFactory("star-factory") {
            {
                size = 3;
                requirements(Category.units,
                        with(StarnerItems.MoonStone, 40, Items.silicon, 30, Items.metaglass, 20));
                plans = Seq.with(
                        new UnitPlan(StarnerUnitTypes.DebriStar, 300f, with(MoonStone, 20)));
            }
        };
    }
}