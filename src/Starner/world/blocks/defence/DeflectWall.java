package Starner.world.blocks.defence;

import arc.util.Nullable;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.Wall;
import arc.graphics.Color;

public class DeflectWall extends Wall {
    @Nullable
    public StatusEffect DeflectStatus;
    public int trailLength = -1;
    public Color trailColor = Color.white;

    public DeflectWall(String name) {
        super(name);
        chanceDeflect = 10f;
    }

    public class DeflectWallBuild extends WallBuild {
        @Override
        public boolean collision(Bullet bullet) {

            boolean collided = super.collision(bullet);
            if (!collided) {
                BulletType bType = bullet.type.copy();
                bType.status = DeflectStatus;

                if (trailLength > 0) {
                    bType.trailWidth = bType.hitSize * 0.5f;
                    bType.trailLength = trailLength;
                    bType.trailColor = trailColor;
                }
                bullet.type = bType;
            }
            return collided;
        }
    }
}
