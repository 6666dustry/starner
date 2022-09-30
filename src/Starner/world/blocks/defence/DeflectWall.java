package Starner.world.blocks.defence;

import arc.util.Nullable;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.Wall;

public class DeflectWall extends Wall {
    @Nullable
    public StatusEffect DeflectEffect;

    public DeflectWall(String name) {
        super(name);
        chanceDeflect = 10f;
    }

    public class DeflectWallBuild extends WallBuild {
        @Override
        public boolean collision(Bullet bullet) {

            boolean collided = super.collision(bullet);
            if (!collided) {
                BulletType b = bullet.type.copy();
                b.status = DeflectEffect;
                bullet.type = b;
            }
            return collided;
        }
    }
}
