package BattleField;

import static BattleField.BattleField.collidables;
import static BattleField.BattleField.destroyed;
import static BattleField.BattleField.globalDraw;
import static BattleField.BattleField.objects_to_draw;
import static BattleField.BattleField.projectiles_in_air;
import static BattleField.BattleField.screen;
import static com.googlecode.lanterna.terminal.Terminal.Color.*;
import java.util.ArrayList;

public class Projectile extends VisualObject implements Collidable {

    private int distance_counter;
    private int distance;
    private double direction;
    private double radian_direction;
    private int org_x;
    private int org_y;
    private int width = 0;
    private int height = 0;
    Hitbox hitbox;

    public Projectile(int x_pos, int y_pos, int maxdistance, double angle) {
        super(x_pos, y_pos);
        this.org_x = x_pos;
        this.org_y = y_pos;
        colorBg = BLACK;
        colorFg = RED;
        look = new String[]{"0"};
        this.distance = maxdistance;
        this.direction = angle;
        this.radian_direction = direction * (Math.PI / 180);
        generateHitbox();

    }

    public void setShootingAngle(double angle) {
        this.direction = angle;
        this.radian_direction = direction * (Math.PI / 180);
    }
   
    
    public void fly() {
        if (distance_counter <= distance) {
            this.pos[0] = (int) (org_x + (distance_counter * Math.cos(radian_direction) * 3));
            this.pos[1] = (int) (org_y + distance_counter * Math.sin(radian_direction));
            distance_counter++;
        } else {
            projectiles_in_air = false;
        }
    }

    public void project() {
        objects_to_draw.add(this);
        projectiles_in_air = true;
        globalDraw();
        screen.clear();
        while (projectiles_in_air) {
            this.fly();
            globalDraw();
        }
        screen.refresh();
        objects_to_draw.remove(this);

    }

    public void hasBeenFired() throws InterruptedException {
        objects_to_draw.add(this);
        projectiles_in_air = true;
        while (projectiles_in_air) {
            screen.clear();
            globalDraw();
            screen.refresh();
            this.fly();
            ArrayList<Object> temp = this.checkGlobalCollisionWithObstacle();
            if ((boolean) temp.get(0)) {
                objects_to_draw.remove(this);
                collidables.remove(temp.get(1));
                projectiles_in_air = false;
                screen.refresh();
                explosion(this.pos);
                objects_to_draw.remove(temp.get(1));
                destroyed.add((Player)temp.get(1));
                return;
            }
            Thread.sleep(50);
            screen.readInput();

        }
        objects_to_draw.remove(this);
    }

    public void explosion(int[] pos) throws InterruptedException {
        Explosion explosion = new Explosion(pos[0], pos[1]);
        explosion.explode();

    }

    public void generateHitbox() {
        this.hitbox = new Hitbox();
        this.hitbox.pos = this.pos;
        this.hitbox.width = this.width;
        this.hitbox.height = this.width;
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public boolean checkCollision(Hitbox other) {
        return this.hitbox.checkCollision(other);
    }
}
