package Starner.content;

import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BlockGroup;
import mindustry.type.*;
import mindustry.world.draw.*;
import Starner.world.blocks.units.StatusRepairTurret;
import Starner.entities.bullet.*;
import Starner.entities.effect.*;
import Starner.world.blocks.defence.*;
import mindustry.entities.UnitSorts;

import static Starner.content.StarnerItems.*;
import static mindustry.type.ItemStack.*;

import Starner.world.draw.DrawFusion;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

public class StarnerBlocks {

  public static Block
  // walls.
  CometWall, CometWallLarge, SunCrystalWall, SunCrystalWallLarge,
      // production.
      StoneFuser, StoneCrimper, CometMixer, SunConvergencer,
      StarDisassembler,

      // turrets.
      StarShooter, StarCannon, StarConduit, StarDuster, StarRocket, StarPulser, StarFlame, CometFlyer,
      CometThrower, StarBubble,
      StarLancer, StarBow,
      StarBoomer,
      Wind, Fielder,
      // unit factory.
      StarFactory, SolarPointer,
      // TODO
      StarDriver;

  public static void load() {
    CometWall = new DeflectWall("comet-wall") {
      {
        DeflectStatus = StatusEffects.freezing;
        trailLength = 30;
        trailColor = Color.sky;
        group = BlockGroup.walls;
        requirements(
            Category.defense,
            with(CometPiece, 24));
        health = 500;
        size = 1;
      }
    };

    CometWallLarge = new DeflectWall("comet-wall-large") {
      {
        DeflectStatus = StatusEffects.freezing;
        trailLength = 30;
        trailColor = Color.sky;
        group = BlockGroup.walls;
        requirements(
            Category.defense,
            with(
                CometPiece, 24));
        health = 2000;
        size = 2;
      }
    };

    SunCrystalWall = new DeflectWall("sun-crystal-wall") {
      {
        DeflectStatus = StatusEffects.burning;
        trailLength = 30;
        lightningColor = Color.orange;
        lightningLength = 15;
        lightningDamage = 15f;
        lightningChance = 0.25f;
        trailColor = Color.orange;
        group = BlockGroup.walls;
        requirements(
            Category.defense,
            with(
                SunCrystal, 24));
        health = 750;
        size = 1;
      }
    };

    SunCrystalWallLarge = new DeflectWall("sun-crystal-wall-large") {
      {
        DeflectStatus = StatusEffects.burning;
        trailLength = 30;
        lightningColor = Color.orange;
        lightningLength = 15;
        lightningDamage = 15f;
        lightningChance = 0.25f;
        trailColor = Color.orange;
        group = BlockGroup.walls;
        requirements(
            Category.defense,
            with(
                SunCrystal, 24));
        health = 3500;
        size = 2;
      }
    };

    StarShooter = new ItemTurret("star-shooter") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                Items.copper, 30,
                MoonStone, 20));
        health = 300;
        size = 1;
        inaccuracy = 10f;
        maxAmmo = 25;
        range = 155;
        reload = 15;
        coolant = consumeCoolant(size / 10f);
        ammo(
            Items.copper, new BasicBulletType(2f, 15, "starner-star-bullet") {
              {
                spin = 6f;
                lifetime = 1000f;
                width = height = 12f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ffffffff");
                weaveMag = 1.5f;
                weaveScale = 900f;
                shrinkX = 0.5f;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            },
            StarnerItems.MoonStone, new BasicBulletType(2f, 19, "starner-star-bullet") {
              {
                pierce = true;
                pierceCap = 3;
                spin = 6f;
                lifetime = 1000f;
                width = height = 12f;
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
                fragBullet = new BasicBulletType(2f, 13, "starner-star-bullet") {
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

            Items.pyratite, new BasicBulletType(2f, 14, "starner-star-bullet") {
              {
                pierce = true;
                pierceCap = 3;
                status = StatusEffects.burning;
                spin = 6;
                lifetime = 1000f;
                width = height = 11f;
                ammoMultiplier = 6;
                frontColor = backColor = Color.valueOf("ffccccff");
                makeFire = true;
                weaveMag = 1.5f;
                weaveScale = 900f;
                incendChance = 0.5f;
                shrinkX = 0.5f;
                trailChance = 1.0f;
                despawnEffect = new Effect();
              }
            },
            Items.metaglass, new BasicBulletType(2f, 8, "starner-star-bullet") {
              {
                pierce = true;
                pierceCap = 3;
                inaccuracy = 200f;
                spin = 6;
                lifetime = 900f;
                width = height = 8f;
                ammoMultiplier = 13;
                reloadMultiplier = 1.65f;
                frontColor = backColor = Color.valueOf("ffffffff");
                trailEffect = new StarTrail();
                makeFire = true;
                weaveMag = 1.5f;
                weaveScale = 900f;
                incendChance = 0.5f;
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
        requirements(
            Category.turret,
            with(
                MoonStone, 40,
                Items.lead, 20,
                Items.copper, 30));
        shoot = new ShootAlternate() {
          {
            shots = 3;
          }
        };
        health = 750;
        size = 2;
        targetAir = false;
        maxAmmo = 20;
        inaccuracy = 5f;
        range = 135;
        ammoPerShot = 3;
        reload = 50;
        coolant = consumeCoolant(size / 10f);
        ammo(
            Items.lead, new BasicBulletType(2f, 16, "starner-star-bullet") {
              {
                collidesAir = false;
                shrinkX = 0.5f;
                spin = 6f;
                width = height = 10f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ccccccff");
                hitEffect = StarnerFx.splashStar;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            },
            StarnerItems.MoonStone, new BasicBulletType(2f, 23, "starner-star-bullet") {
              {
                collidesAir = false;
                shrinkX = 0.5f;
                spin = 6f;
                width = height = 10f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ffffffff");
                hitEffect = StarnerFx.splashStar;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            },
            Items.silicon, new BasicBulletType(2f, 28, "starner-star-bullet") {
              {
                collidesAir = false;
                inaccuracy = 50f;
                shrinkX = 0.5f;
                spin = 6f;

                width = height = 10f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ffffffff");
                homingDelay = 0f;
                homingPower = 0.1f;
                hitEffect = StarnerFx.splashStar;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            });
        limitRange();
      }
    };

    StarConduit = new ItemTurret("star-conduit") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                Items.copper, 40,
                MoonStone, 30,
                Items.silicon, 40,
                Items.metaglass, 30));
        health = 800;
        size = 2;
        maxAmmo = 27;
        inaccuracy = 0f;
        range = 100f;
        ammoPerShot = 3;
        reload = 200;
        heatColor = Color.valueOf("00ff00aa");
        recoil = 0;
        targetHealing = true;
        shootY = 0;
        coolant = consumeCoolant(size / 10f);
        ammo(
            Items.sporePod, new BombBulletType() {
              {
                knockback = 3f;
                status = StatusEffects.sporeSlowed;
                lifetime = 0f;
                ammoMultiplier = 2;
                frontColor = backColor = Color.valueOf("ffffffff");
                splashDamageRadius = 100f;
                splashDamage = 20;
                healPercent = 2.5f;
                despawnEffect = hitEffect = StarnerFx.sporeAura;
              }
            },
            StarnerItems.MoonStone, new BombBulletType() {
              {
                knockback = 3f;
                lifetime = 0f;
                ammoMultiplier = 1;
                frontColor = backColor = Color.valueOf("ffffffff");
                splashDamageRadius = 100f;
                splashDamage = 15;
                healPercent = 2.5f;

                healAmount = 10;
                collidesTeam = true;
                despawnEffect = hitEffect = StarnerFx.healAura;
              }
            },
            Items.pyratite, new BombBulletType() {
              {
                knockback = 3f;
                status = StatusEffects.burning;
                lifetime = 0f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ffffffff");
                splashDamageRadius = 100f;
                splashDamage = 25;

                despawnEffect = hitEffect = StarnerFx.fireAura;
              }
            },
            StarnerItems.CometPiece, new BombBulletType() {
              {
                knockback = 3f;
                status = StatusEffects.freezing;
                lifetime = 0f;
                ammoMultiplier = 3;
                frontColor = backColor = Color.valueOf("ffffffff");
                rangeChange = 20f;
                splashDamageRadius = range + rangeChange;
                splashDamage = 23;

                despawnEffect = hitEffect = StarnerFx.freezeAura;
              }
            });
      }
    };

    StarDuster = new ItemTurret("star-duster") {
      {
        group = BlockGroup.turrets;
        requirements(Category.turret, with(MoonStone, 50, Items.copper, 75, Items.lead, 75));
        shootY = -1.5f;
        health = 850;
        size = 2;
        inaccuracy = 25f;
        maxAmmo = 20;
        range = 110;
        reload = 1.5f;
        coolant = consumeCoolant(size / 10f);
        coolantMultiplier = 1.4f;
        ammo(

            Items.scrap, new BasicBulletType(3f, 14, "starner-star-bullet") {
              {
                spin = 6f;
                lifetime = 45f;
                width = height = 5f;
                ammoMultiplier = 6;
                frontColor = backColor = Color.valueOf("ffffffff");
                despawnEffect = new Effect();
                trailChance = 1f / 6f;
                trailEffect = new StarTrail();
              }
            },
            CometPiece, new BasicBulletType(3f, 20, "starner-star-bullet") {
              {
                status = StatusEffects.freezing;
                spin = 6f;
                lifetime = 46f;
                width = height = 5f;
                ammoMultiplier = 10;
                frontColor = Color.valueOf("ffffffff");
                backColor = Color.sky;
                despawnEffect = new Effect();
                trailChance = 1f / 5f;
                trailEffect = new StarTrail() {
                  {
                    colorTo = Color.sky;
                  }
                };
              }
            }, Items.titanium, new BasicBulletType(3f, 22, "starner-star-bullet") {
              {
                status = StatusEffects.freezing;
                statusDuration = 60f;
                spin = 6f;
                lifetime = 46f;
                width = height = 6.5f;
                reloadMultiplier = 0.7f;
                knockback = 1.2f;
                ammoMultiplier = 6;
                frontColor = backColor = Color.cyan;
                despawnEffect = new Effect();
                trailChance = 1f / 5f;
                trailEffect = new StarTrail() {
                  {
                    colorTo = Color.cyan;
                  }
                };
              }
            });
      }
    };
    StarBow = new ItemTurret("star-bow") {
      {
        shootCone = 1f;
        unitSort = UnitSorts.strongest;
        group = BlockGroup.turrets;
        requirements(Category.turret, with(MoonStone, 50, Items.copper, 65, Items.lead, 45));
        health = 750;
        size = 2;
        inaccuracy = 0f;
        maxAmmo = 10;
        reload = 150f;
        coolant = consumeCoolant(size / 10f);
        heatColor = Color.yellow;
        accurateDelay = false;
        ammoPerShot = 2;
        targetHealing = true;
        float brange = range = 290f;
        ammo(
            Items.copper, new PointBulletType() {
              {
                speed = brange;
                buildingDamageMultiplier = 0.25f;
                damage = 75;
                trailSpacing = 1f;
                trailEffect = new StarTrail();
                despawnEffect = new StarTrail() {
                  {
                    particles = 8;
                    sizeFrom = 10f;
                    lifetime = 20f;
                    length = 60f;
                  }
                };
              }
            },
            MoonStone, new PointBulletType() {
              {
                ammoMultiplier = 4f;
                speed = brange;
                buildingDamageMultiplier = 0.25f;
                damage = 100;
                splashDamageRadius = 40f;
                splashDamage = 18f;
                trailSpacing = 1f;
                trailEffect = new StarTrail();
                despawnEffect = new StarTrail() {
                  {
                    particles = 8;
                    sizeFrom = 10f;
                    lifetime = 20f;
                    length = 60f;
                  }
                };
              }
            },
            Items.graphite, new PointBulletType() {
              {
                rangeChange = 60f;
                reloadMultiplier = 0.6f;
                speed = brange;
                buildingDamageMultiplier = 0.1f;
                damage = 250;
                trailSpacing = 1f;
                trailEffect = new StarTrail();
                ammoMultiplier = 1;
                despawnEffect = new StarTrail() {
                  {
                    particles = 8;
                    sizeFrom = 10f;
                    lifetime = 20f;
                    length = 60f;
                  }
                };
              }
            },
            CometPiece, new PointBulletType() {
              {
                ammoMultiplier = 3;
                reloadMultiplier = 1.5f;
                speed = brange;
                buildingDamageMultiplier = 0.25f;
                damage = 100;
                status = StatusEffects.freezing;
                trailSpacing = 1f;
                trailEffect = new StarTrail() {
                  {
                    colorTo = Color.sky;
                  }
                };
                despawnEffect = new StarTrail() {
                  {
                    particles = 8;
                    sizeFrom = 10f;
                    colorTo = Color.sky;
                    lifetime = 20f;
                    length = 60f;
                  }
                };
              }
            },
            Items.plastanium, new HealPointBulletType() {
              {
                ammoMultiplier = 4;
                rangeChange = 50f;
                speed = brange;
                buildingDamageMultiplier = 0.25f;
                damage = 50;
                healPercent = 10f;
                collidesTeam = true;
                trailSpacing = 1f;
                trailEffect = new StarTrail() {
                  {
                    colorTo = Pal.heal;
                  }
                };
                despawnEffect = new StarTrail() {
                  {
                    particles = 8;
                    sizeFrom = 10f;
                    colorTo = Pal.heal;
                    lifetime = 20f;
                    length = 60f;
                  }
                };
              }
            });
      }
    };
    StarFlame = new ContinuousNonDelayTurret("star-flame") {
      {
        shootCone = 188f;
        unitSort = UnitSorts.strongest;
        group = BlockGroup.turrets;
        requirements(Category.turret, with(MoonStone, 60, Items.silicon, 65, Items.titanium, 55));
        consumePower(7.5f);
        health = 1250;
        size = 2;
        inaccuracy = 0f;
        maxAmmo = 10;
        reload = 45f;
        rotateSpeed = 3.5f;
        range = 65f;
        shootY = 4;
        shootSound = Sounds.laserbig;
        loopSound = Sounds.beam;
        heatColor = Color.yellow;
        shootType = new ContinuousLaserTrailBulletType(5.5f) {
          {
            status = StatusEffects.melting;

            colors[0] = Color.yellow;
            width = 3.1f;
            shake = 0.25f;
            length = 60f;
            trailChance = 0.1f;
            trailInterval = -1f;
            trailEffect = new StarTrail() {
              {
                length = 40f;
                colorFrom = Color.yellow;
              }
            };
          }
        };
      }
    };

    StarRocket = new PowerTurret("star-rocket") {
      {
        group = BlockGroup.turrets;
        requirements(Category.turret, with(MoonStone, 100, Items.silicon, 70, Items.metaglass, 80));
        health = 900;
        range = 185f;
        size = 2;
        reload = 100f;
        consumePower(200f / 60f);
        shootSound = Sounds.missile;
        coolant = consumeCoolant(size / 10f);
        BulletType spawn = new BasicBulletType(3f, 22, "starner-star-bullet") {
          {
            lifetime = 78f;
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
            lifetime = 70f;
            fragBullet = spawn;
            fragBullets = 8;
            fragSpread = 360 / fragBullets;
            fragRandomSpread = 0f;
            trailChance = 1f;
            trailEffect = new StarTrail();
            fragVelocityMax = fragVelocityMin = 1f;

            width = height = 15f;
          }
        };
      }
    };
    StarPulser = new ItemTurret("star-pulser") {
      {
        group = BlockGroup.turrets;
        requirements(Category.turret, with(Items.graphite, 50, MoonStone, 40, Items.titanium, 40));
        shoot = new ShootAlternate() {
          {
            shots = 3;
            barrels = 1;
            spread = 3.5f;
            shotDelay = 5f;
          }
        };
        health = 900;
        size = 2;
        inaccuracy = 10f;
        maxAmmo = 10;
        range = 160;
        reload = 35;
        shootSound = Sounds.missile;
        coolant = consumeCoolant(size / 10f);
        ammo(
            Items.graphite, new BasicBulletType(3f, 15) {
              {
                knockback = 0.75f;
                pierce = true;
                pierceCap = 2;
                lifetime = 75f;
                trailChance = 0f;
                trailInterval = 5f;
                trailEffect = new StarnerFx.Pulse();
              }
            }, Items.silicon, new BasicBulletType(3f, 20) {
              {
                knockback = 0.75f;
                pierce = true;
                pierceCap = 3;
                lifetime = 75f;
                ammoMultiplier = 2;
                homingRange = 100f;
                homingDelay = 20f;
                homingPower = 0.05f;
                trailChance = 0f;
                trailInterval = 5f;
                trailEffect = new StarnerFx.Pulse();
              }
            }, Items.pyratite, new BasicBulletType(3f, 25) {
              {
                knockback = .75f;
                pierce = true;
                pierceCap = 2;
                lifetime = 75f;
                ammoMultiplier = 4;
                status = StatusEffects.burning;
                trailChance = 0f;
                trailInterval = 5f;
                trailEffect = new StarnerFx.Pulse(Color.orange);
              }
            }, Items.blastCompound, new BasicBulletType(3f, 35) {
              {
                knockback = 1.35f;
                pierce = true;
                pierceCap = 1;
                lifetime = 75f;
                ammoMultiplier = 3;
                splashDamageRadius = 16f;
                splashDamage = 10;
                trailChance = 0f;
                trailInterval = 5f;
                trailEffect = new StarnerFx.Pulse(Color.red);
              }
            }, CometPiece, new BasicBulletType(3f, 20) {
              {
                knockback = 0.6f;
                pierce = true;
                ammoMultiplier = 4;
                reloadMultiplier = 1.4f;
                status = StatusEffects.freezing;
                trailChance = 0f;
                trailInterval = 5f;
                trailEffect = new StarnerFx.Pulse(Color.sky);
              }
            });
      }
    };
    CometFlyer = new ItemTurret("comet-flyer") {
      {
        group = BlockGroup.turrets;
        requirements(Category.turret, with(CometPiece, 50, MoonStone, 40));
        shoot = new ShootAlternate() {
          {
            shots = 6;
            barrels = 1;
            spread = 3.5f;
            shotDelay = 4f;
          }
        };
        health = 1000;
        size = 2;
        inaccuracy = 45f;
        maxAmmo = 30;
        range = 180;
        reload = 45;
        shootSound = Sounds.missile;
        coolant = consumeCoolant(size / 10f);
        ammo(
            StarnerItems.MoonStone, new BasicBulletType(2f, 20, "starner-star-bullet") {
              {
                spin = 6f;
                lifetime = 60f;
                width = height = 10f;
                ammoMultiplier = 8;
                frontColor = backColor = Color.valueOf("ffffffff");
                shrinkX = 0.5f;
                homingPower = 0.05f;
                homingDelay = 30f;
                homingRange = 250f;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            },
            Items.silicon, new BasicBulletType(2f, 25, "starner-star-bullet") {
              {
                spin = 6f;
                lifetime = 60f;
                width = height = 10f;
                ammoMultiplier = 10;
                frontColor = backColor = Color.valueOf("ffffffff");
                shrinkX = 0.5f;
                homingPower = 0.1f;
                homingRange = 250f;
                homingDelay = 30f;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail();
                despawnEffect = new Effect();
              }
            },
            StarnerItems.CometPiece, new BasicBulletType(2f, 30, "starner-star-bullet") {
              {
                status = StatusEffects.freezing;
                ammoMultiplier = 9;
                frontColor = backColor = Color.valueOf("9999ffff");
                shrinkX = 0.5f;
                homingPower = 0.05f;
                homingRange = 250f;
                homingDelay = 30f;
                trailChance = 1f / 5f;
                trailRotation = true;
                trailEffect = new StarTrail() {
                  {
                    colorTo = Color.valueOf("7777ffff");
                  }
                };
                despawnEffect = new Effect();
              }
            });
        limitRange();
      }
    };
    StarBoomer = new ItemTurret("star-boomer") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                Items.copper, 140,
                MoonStone, 100,
                Items.thorium, 70));
        health = 1700;
        size = 3;
        inaccuracy = 20f;
        heatColor = Color.yellow;
        maxAmmo = 10;
        shoot = new ShootSpread(10, 5);
        range = 135;
        reload = 120;
        shootSound = Sounds.shotgun;
        coolant = consumeCoolant(size / 10f);
        velocityRnd = 0.5f;
        ammo(MoonStone, new SlowlyBulletType() {
          {
            ammoMultiplier = 2;
            lifetime = 120f;
            damage = 15;
            sprite = "starner-star-bullet";
            backColor = frontColor = Color.white;
            speed = 5.5f;
            spin = 6f;
            shrinkX = shrinkY = 0.75f;
            width = height = 12.5f;
          }
        }, Items.graphite, new SlowlyBulletType() {
          {
            ammoMultiplier = 1;
            lifetime = 120f;
            damage = 15;
            sprite = "starner-star-bullet";
            backColor = frontColor = Color.white;
            speed = 5.5f;
            spin = 6f;
            shrinkX = shrinkY = 0.75f;
            width = height = 12.5f;
          }
        }, CometPiece, new SlowlyBulletType() {
          {
            ammoMultiplier = 3;
            status = StatusEffects.freezing;
            lifetime = 120f;
            damage = 25;
            sprite = "starner-star-bullet";
            frontColor = Color.white;
            backColor = Color.sky;
            speed = 5.5f;
            spin = 6f;
            shrinkX = shrinkY = 0.75f;
            width = height = 12.5f;
          }
        }, Items.thorium, new SlowlyBulletType() {
          {
            ammoMultiplier = 3;
            lifetime = 120f;
            damage = 30;
            sprite = "starner-star-bullet";
            frontColor = Color.white;
            backColor = Color.pink.cpy().saturation(0.3f);
            speed = 5.5f;
            spin = 6f;
            shrinkX = shrinkY = 0.75f;
            width = height = 12.5f;
          }
        }, SunCrystal, new SlowlyBulletType() {
          {
            ammoMultiplier = 3;
            status = StatusEffects.burning;
            lifetime = 120f;
            damage = 40;
            sprite = "starner-star-bullet";
            frontColor = Color.white;
            backColor = Color.orange.cpy().saturation(0.3f);
            speed = 5.5f;
            spin = 6f;
            shrinkX = shrinkY = 0.75f;
            width = height = 12.5f;
          }
        });
      }
    };
    StarBubble = new ItemTurret("star-bubble") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                Items.copper, 240,
                MoonStone, 150,
                Items.thorium, 120,
                Items.titanium, 95));
        health = 1650;
        size = 3;
        inaccuracy = 55f;
        heatColor = Color.yellow;
        maxAmmo = 10;
        shoot = new ShootSpread(6, 12);
        range = 220;
        reload = 105;
        shootSound = Sounds.shotgun;
        coolant = consumeCoolant(size / 10f);
        velocityRnd = 0.9f;
        ammo(Items.pyratite, new FieldBulletType() {
          {
            slowPercent = 0.0225f;
            speed = 6f;
            fieldRadius = 15f;
            fieldDamage = 0.5f;
            fieldStatus = StatusEffects.burning;
            fieldColor = Color.orange;
          }
        }, CometPiece, new FieldBulletType() {
          {
            slowPercent = 0.0225f;
            speed = 6f;
            fieldRadius = 15f;
            fieldDamage = 0.25f;
            fieldStatus = StatusEffects.freezing;
            fieldColor = Color.sky;
          }
        }, Items.thorium, new FieldBulletType() {
          {
            slowPercent = 0.0225f;
            speed = 6f;
            fieldRadius = 15f;
            fieldDamage = 0.4f;
            fieldStatus = StatusEffects.disarmed;
            fieldColor = Color.pink;
          }
        });
      }
    };
    CometThrower = new ItemTurret("comet-thrower") {
      {
        group = BlockGroup.turrets;
        requirements(Category.turret, with(CometPiece, 85, MoonStone, 115, Items.copper, 140));
        health = 1150;
        size = 3;
        inaccuracy = 20f;
        maxAmmo = 25;
        range = 160;
        reload = 30;
        shootY = -1;
        recoil = 2.5f;
        shootSound = Sounds.artillery;
        coolant = consumeCoolant(size / 10f);
        ammo(Items.metaglass, new ArtilleryBulletType(3.5f, 30) {
          {
            ammoMultiplier = 3;
            shake = 1f;
            velocityRnd = 0.2f;
            lifetime = 60f;
            splashDamage = 8;
            splashDamageRadius = 10f;
            shrinkX = shrinkY = 0;
            width = height = 15f;
            frontColor = backColor = Color.valueOf("bbbbffff");
            trailChance = 1f;
            trailRotation = true;
            trailEffect = new StarTrail() {
              {
                sizeFrom = 6.5f;
                colorTo = Color.valueOf("ffffffff");
              }
            };
            despawnEffect = StarnerFx.freezeAura;
            fragBullets = 6;
            fragRandomSpread = 5f;
            fragSpread = 360f / fragBullets;
            fragLifeMin = fragLifeMax = 1f;
            fragVelocityMin = fragVelocityMax = 1f;
            fragBullet = new LaserBulletType(12) {
              {
                collidesAir = true;
                lifetime = 15f;
                length = 45f;

                frontColor = Color.valueOf("ffffffff");
                backColor = Color.valueOf("ffffffff");
                despawnEffect = new Effect();
              }
            };
          }
        },
            CometPiece, new ArtilleryBulletType(3.5f, 40) {
              {
                ammoMultiplier = 4;
                shake = 1f;
                velocityRnd = 0.2f;
                lifetime = 60f;
                splashDamage = 10;
                splashDamageRadius = 12f;
                shrinkX = shrinkY = 0;
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
                fragRandomSpread = 0f;
                fragSpread = 360f / fragBullets;
                fragLifeMin = fragLifeMax = 1f;
                fragVelocityMin = fragVelocityMax = 1f;
                fragBullet = new LaserBulletType(12) {
                  {
                    collidesAir = true;
                    status = StatusEffects.freezing;
                    lifetime = 15f;
                    length = 45f;

                    frontColor = Color.valueOf("ffffffff");
                    backColor = Color.valueOf("9999ffff");
                    despawnEffect = new Effect();
                  }
                };
              }
            },
            Items.blastCompound, new ArtilleryBulletType(3.5f, 50) {
              {
                status = StatusEffects.blasted;
                ammoMultiplier = 5;
                shake = 1f;
                velocityRnd = 0.2f;
                lifetime = 60f;
                splashDamage = 20;
                splashDamageRadius = 36f;
                shrinkX = shrinkY = 0;
                width = height = 15f;
                frontColor = backColor = Color.valueOf("ff8888ff");
                trailChance = 1f;
                trailRotation = true;
                trailEffect = new StarTrail() {
                  {
                    sizeFrom = 6.5f;
                    colorTo = Color.valueOf("ff555555");
                  }
                };
                fragBullets = 4;
                fragRandomSpread = 0f;
                fragSpread = 360f / fragBullets;
                fragLifeMin = fragLifeMax = 1f;
                fragVelocityMin = fragVelocityMax = 1f;
                fragBullet = new LaserBulletType(22) {
                  {
                    collidesAir = true;
                    lifetime = 15f;
                    length = 45f;

                    colors[0] = Color.red;
                    despawnEffect = new Effect();
                  }
                };
                despawnEffect = Fx.massiveExplosion;
              }
            },
            Items.pyratite, new ArtilleryBulletType(3.5f, 35) {
              {
                status = StatusEffects.burning;
                ammoMultiplier = 4;
                incendAmount = 10;
                incendChance = 1f;
                incendSpread = 30f;
                shake = 1f;
                velocityRnd = 0.2f;
                lifetime = 60f;
                splashDamage = 10;
                splashDamageRadius = 30f;
                shrinkX = shrinkY = 0;
                width = height = 15f;
                frontColor = backColor = Color.valueOf("ffaaaaff");
                trailChance = 1f;
                trailRotation = true;
                trailEffect = new StarTrail() {
                  {
                    sizeFrom = 6.5f;
                    colorTo = Color.valueOf("ff999955");
                  }
                };
                fragBullets = 5;
                fragRandomSpread = 0f;
                fragSpread = 360f / fragBullets;
                fragLifeMin = fragLifeMax = 1f;
                fragVelocityMin = fragVelocityMax = 1f;
                fragBullet = new LaserBulletType(18) {
                  {
                    collidesAir = true;
                    lifetime = 15f;
                    length = 45f;

                    colors[0] = Color.orange;
                    despawnEffect = new Effect();
                  }
                };
                despawnEffect = Fx.massiveExplosion;
              }
            });
      }
    };
    StarLancer = new ItemTurret("star-lancer") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                Items.copper, 100,
                CometPiece, 50, MoonStone, 60,
                Items.titanium, 40));
        health = 1600;
        size = 3;
        inaccuracy = 0f;
        rotateSpeed = 1f;
        targetHealing = true;
        maxAmmo = 15;
        range = 110f;
        reload = 60;
        heatColor = Color.red;
        moveWhileCharging = false;
        accurateDelay = false;
        recoil = 2.5f;
        shootSound = Sounds.laser;

        consumePower(180f / 60);
        coolant = consumeCoolant(size / 10f);
        ammo(CometPiece, new FragLaserBulletType(50) {
          {
            status = StatusEffects.freezing;
            range = 110f;
            length = 110f;
            width = 40f;
            fragBullets = 10;
            fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
            fragRandomSpread = 0f;
            fragSpread = 360f / fragBullets;
            sideLength = 10f;
            fragBullet = new BasicBulletType(4f, 15, "starner-star-bullet") {
              {
                status = StatusEffects.freezing;
                spin = 6f;
                frontColor = backColor = Color.valueOf("ddddffff");
                weaveRandom = false;
                lifetime = 30f;
                weaveMag = 5.1f;
                weaveScale = 90f;
                shrinkX = shrinkY = 0.8f;
                width = height = 15f;
              }
            };
          }
        }, SunCrystal, new FragLaserBulletType(65) {
          {
            status = StatusEffects.melting;
            colors[0] = Color.orange;
            range = 110f;
            length = 110f;
            width = 40f;
            fragBullets = 12;
            fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
            fragRandomSpread = 0f;
            fragSpread = 360f / fragBullets;

            fragBullet = new BasicBulletType(2f, 20, "starner-star-bullet") {
              {
                status = StatusEffects.melting;
                spin = 6f;
                shrinkX = shrinkY = 0.5f;
                frontColor = Color.orange;
                weaveRandom = true;
                lifetime = 60f;
                weaveMag = 3.1f;
                weaveScale = 120f;
                shrinkX = shrinkY = 0.8f;
                width = height = 15f;
              }
            };
          }
        },
            Items.blastCompound, new FragLaserBulletType(70) {
              {
                status = StatusEffects.blasted;
                colors[0] = Color.red;
                range = 110f;
                length = 110f;
                width = 40f;
                fragBullets = 16;
                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                fragRandomSpread = 0f;
                fragSpread = 360f / fragBullets;

                fragBullet = new BasicBulletType(4f, 17, "starner-star-bullet") {
                  {
                    status = StatusEffects.blasted;
                    spin = 6f;
                    frontColor = Color.red;
                    backColor = Color.orange;
                    lifetime = 25f;
                    shrinkX = shrinkY = 0.8f;
                    width = height = 15f;
                  }
                };
              }
            },
            Items.plastanium, new FragLaserBulletType(30) {
              {
                collidesTeam = true;
                status = StatusEffects.blasted;
                colors[0] = Color.green;
                range = 110f;
                length = 110f;
                width = 40f;
                fragBullets = 6;
                fragLifeMax = fragLifeMin = fragVelocityMax = fragVelocityMin = 1f;
                fragRandomSpread = 0f;
                fragSpread = 360f / fragBullets;
                healAmount = 30;
                fragBullet = new BasicBulletType(3f, 18, "starner-star-bullet") {
                  {

                    collidesTeam = true;
                    spin = 6f;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                    lifetime = 40f;
                    width = height = 12f;
                    homingDelay = 20f;
                    homingRange = 140f;
                    homingPower = 0.05f;
                    shrinkX = shrinkY = 0.8f;
                    healPercent = 0.5f;
                    healAmount = 10;
                  }
                };
              }
            });
      }
    };
    Wind = new LiquidTurret("wind") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                CometPiece, 65,
                Items.copper, 130,
                MoonStone, 80,
                Items.metaglass, 75));
        shoot = new ShootAlternate() {
          {
            shots = 100;
            barrels = 1;
            spread = 3.5f;
          }
        };
        shootSound = Sounds.bigshot;
        health = 1860;
        size = 3;
        inaccuracy = 60f;
        liquidCapacity = 200f;
        recoil = 3f;
        reload = 90;
        targetGround = false;
        shootEffect = null;

        float tRange = 155f;
        range = tRange;

        ammo(Liquids.water, new LiquidBulletType(Liquids.water) {
          {
            shootEffect = new SplashLiquids(liquid) {
              {
                length = tRange;
                cone = 60f;
                lifetime = tRange / speed;
                particles = 1;
              }
            };
            collidesGround = false;
            statusDuration = 60f * 6f;
            damage = 0.1f;
            ammoMultiplier = 0.01f;
            knockback = 10f;
            speed = 8.5f;
            lifetime = tRange / speed;
          }
        }, Liquids.slag, new LiquidBulletType(Liquids.slag) {
          {
            shootEffect = new SplashLiquids(liquid) {
              {
                length = tRange;
                cone = 60f;
                lifetime = tRange / speed;
                particles = 1;
              }
            };
            collidesGround = false;
            statusDuration = 60f * 6f;
            damage = 1;
            ammoMultiplier = 0.01f;
            knockback = 10f;
            speed = 8.5f;
            lifetime = tRange / speed;
          }
        }, Liquids.oil, new LiquidBulletType(Liquids.oil) {
          {
            shootEffect = new SplashLiquids(liquid) {
              {
                length = tRange;
                cone = 60f;
                lifetime = tRange / speed;
                particles = 1;
              }
            };
            collidesGround = false;
            statusDuration = 60f * 6f;
            damage = 0.1f;
            ammoMultiplier = 0.01f;
            knockback = 10f;
            speed = 8.5f;
            lifetime = tRange / speed;
          }
        }, Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid) {
          {
            shootEffect = new SplashLiquids(liquid) {
              {
                length = tRange;
                cone = 60f;
                lifetime = tRange / speed;
                particles = 1;
              }
            };
            collidesGround = false;
            statusDuration = 60f * 6f;
            damage = 0.1f;
            ammoMultiplier = 0.01f;
            knockback = 10f;
            speed = 8.5f;
            lifetime = tRange / speed;
          }
        });

      }
    };
    Fielder = new ItemTurret("fielder") {
      {
        group = BlockGroup.turrets;
        requirements(
            Category.turret,
            with(
                MoonStone, 120,
                CometPiece, 95,
                Items.titanium, 100,
                Items.lead, 200,
                Items.copper, 230));
        health = 2254;
        size = 4;
        maxAmmo = 20;
        inaccuracy = 5f;
        shootSound = Sounds.artillery;
        range = 215;
        reload = 170;
        targetHealing = true;

        coolant = consumeCoolant(size / 10f);
        ammo(
            MoonStone,
            new ArtilleryBulletType() {
              {
                speed = 1.5f;
                backColor = frontColor = Color.lightGray;
                height = 20f;
                width = 17.5f;
                splashDamage = 0;
                splashDamageRadius = -1f;
                fragBullets = 1;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new FieldBulletType() {
                  {
                    fieldColor = Color.lightGray.cpy();
                    fieldDamage = 1f;
                    lifetime = 240f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;

                      }
                    };
                  }
                };
              }
            },
            CometPiece,
            new ArtilleryBulletType() {
              {
                speed = 1.5f;
                backColor = frontColor = Color.sky;
                height = 20f;
                width = 17.5f;
                splashDamage = 0;
                splashDamageRadius = -1f;
                fragBullets = 1;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new FieldBulletType() {
                  {
                    fieldColor = Color.sky.cpy();
                    fieldStatus = StatusEffects.freezing;
                    fieldDamage = 0.1f;
                    fieldRadius = 90f;
                    fieldStatusDuration = 140f;
                    lifetime = 300f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;

                      }
                    };
                  }
                };
              }
            },
            SunCrystal,
            new ArtilleryBulletType() {
              {
                speed = 1.5f;
                backColor = frontColor = Color.orange;
                height = 20f;
                width = 17.5f;
                splashDamage = 0;
                splashDamageRadius = -1f;
                fragBullets = 1;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new FieldBulletType() {
                  {
                    fieldDamage = 1;
                    fieldColor = Color.orange;
                    fieldStatus = StatusEffects.melting;
                    fieldStatusDuration = 60f;
                    lifetime = 300f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;

                      }
                    };
                  }
                };
              }
            },
            Items.plastanium,
            new ArtilleryBulletType() {
              {
                speed = 1.5f;
                backColor = frontColor = Pal.heal;
                height = 20f;
                width = 17.5f;
                splashDamage = 0;
                collidesTeam = true;
                healAmount = 10f;
                splashDamageRadius = -1f;
                healPercent = 100f;
                fragBullets = 1;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new FieldBulletType() {
                  {
                    fieldDamage = 0.1f;
                    fieldRadius = 150f;
                    fieldColor = Pal.heal;
                    fieldStatus = StatusEffects.electrified;
                    healAmount = 0.6f;
                    lifetime = 325f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;
                      }
                    };
                  }
                };
              }
            }, Items.metaglass, new ArtilleryBulletType() {
              {
                speed = 1.5f;
                velocityRnd = 0.3f;
                reloadMultiplier = 2.5f;
                backColor = frontColor = Color.white;
                height = 20f;
                inaccuracy = 15f;
                width = 17.5f;
                splashDamage = 0;
                splashDamageRadius = -1f;
                fragBullets = 8;
                fragLifeMin = 0.8f;
                fragLifeMax = 1.2f;
                fragBullet = new FieldBulletType() {
                  {
                    slowPercent = 0.015f;
                    speed = 3.5f;
                    fieldColor = Color.white.cpy();
                    fieldStatus = StatusEffects.slow;
                    fieldStatusDuration = 90f;
                    fieldRadius = 25f;
                    lifetime = 170f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;

                      }
                    };
                  }
                };
              }
            }, Items.phaseFabric,
            new ArtilleryBulletType() {
              {
                ammoMultiplier = 4;
                speed = 1.5f;
                height = 20f;
                width = 17.5f;
                splashDamage = 0;
                splashDamageRadius = -1f;
                fragBullets = 1;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new FieldBulletType() {
                  {
                    knockback = 0.25f;
                    fieldDamage = 0;
                    fieldHealth = 2500;
                    fieldRadius = 100;
                    lifetime = 300f;
                    anchorEffect = new StarTrail() {
                      {
                        colorFrom = fieldColor;
                        length = fieldRadius;

                      }
                    };
                  }
                };
              }
            });
      }
    };

    // crafting.
    StoneFuser = new GenericCrafter("stone-fuser") {
      {
        craftEffect = StarnerFx.splashStar;
        requirements(Category.crafting, with(Items.copper, 40, Items.lead, 25));
        size = 2;
        health = 350;
        consumeItems(with(Items.scrap, 4, Items.coal, 1));
        outputItem = new ItemStack(MoonStone, 1);
        drawer = new DrawMulti(new DrawDefault(), new DrawFusion());
      }
    };
    StoneCrimper = new GenericCrafter("stone-crimper") {
      {
        craftEffect = StarnerFx.splashStar;
        craftTime = 125f;
        requirements(Category.crafting, with(Items.copper, 120, Items.titanium, 75, MoonStone, 90));
        size = 3;
        itemCapacity = 20;
        health = 477;
        consumeItems(with(Items.pyratite, 1, Items.titanium, 5));
        outputItem = new ItemStack(MoonStone, 10);
        consumePower(3f);
        drawer = new DrawMulti(new DrawDefault(), new DrawFlame());
      }
    };
    CometMixer = new GenericCrafter("comet-mixer") {
      {
        requirements(Category.crafting,
            with(
                StarnerItems.MoonStone, 80, Items.silicon, 60, Items.metaglass, 60,
                Items.titanium, 40));
        craftTime = 90f;
        hasLiquids = hasPower = consumesPower = true;
        liquidCapacity = 50f;
        consumePower(120f / 60f);
        consumeLiquid(Liquids.cryofluid, 12f / 60f);
        updateEffect = new StarTrail();
        updateEffectChance = 0.25f;
        craftEffect = StarnerFx.freezeAura;
        size = 3;
        health = 300;
        consumeItems(with(Items.titanium, 1));
        outputItem = new ItemStack(CometPiece, 2);
        drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion(), new DrawRegion("-rotate") {
          {
            spinSprite = true;
            rotateSpeed = 5f;
          }
        });
      }
    };
    SunConvergencer = new GenericCrafter("sun-convergencer") {
      {
        requirements(Category.crafting,
            with(
                CometPiece, 70,
                StarnerItems.MoonStone, 80, Items.silicon, 60, Items.metaglass, 60,
                Items.titanium, 40));
        craftTime = 270f;
        hasLiquids = hasPower = consumesPower = true;
        liquidCapacity = 50f;
        consumePower(240f / 60f);
        consumeLiquid(Liquids.oil, 12f / 60f);
        craftEffect = StarnerFx.fireAura;
        updateEffect = new MultiEffect(Fx.fallSmoke, new StarTrail(), Fx.lava);
        updateEffectChance = 0.5f;
        size = 3;
        health = 500;
        consumeItems(with(Items.pyratite, 1, Items.blastCompound, 1));
        outputItem = new ItemStack(SunCrystal, 1);
        drawer = new DrawMulti(new DrawDefault(), new DrawFlame() {
          {

            flameRadius = 5.5f;
            flameRadiusIn = 3.5f;
            flameColor = Color.orange;
          }
        }, new DrawRegion("-frame"));
      }
    };

    StarFactory = new UnitFactory("star-factory") {
      {
        hasPower = true;
        consumePower(120f / 60f);
        size = 3;
        requirements(Category.units,
            with(StarnerItems.MoonStone, 40, Items.silicon, 35, Items.metaglass, 25));
        plans = Seq.with(
            new UnitPlan(StarnerUnitTypes.DebriStar, 300f, with(MoonStone, 20)));
      }
    };
    SolarPointer = new StatusRepairTurret("solar-pointer") {
      {
        healCircleColor = Color.orange.cpy().mul(1.1f, 1.1f, 1.1f, 0.25f);
        healCircleLineColor = Color.orange.cpy().mul(1f, 1.1f, 1.1f, 0.75f);
        laserColor = Color.orange.cpy().mul(1f, 1.1f, 1.1f, 0.65f);
        beamWidth = 1.5f;
        healRadius = 35f;
        pulseRadius = 15f;
        healSpeed = 0.33f;
        statusEffect = StatusEffects.overclock;
        coolantUse = 0.25f;
        requirements(Category.units,
            with(StarnerItems.MoonStone, 50, Items.silicon, 35, SunCrystal, 40));
        laserEffect = StarnerFx.splashStar;
        laserEffectChance = 0.1f;
        laserEffectInterval = 5f;
        laserEffectSpacing = 10f;
        size = 3;
        health = 720;
        powerUse = 10f;
        repairSpeed = 5f;
        repairRadius = 240f;
        acceptCoolant = true;
      }
    };
  }
}