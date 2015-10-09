package BattleField;

public class Hitbox implements Collidable{
    int[] pos;
    int width;
    int height;
    
    public Hitbox(){
        
    }
    public Hitbox(int[] pos, int width, int height){
        this.pos = pos;
        this.width = width;
        this.height = height;        
    }
    @Override
    public boolean checkCollision(Hitbox other){        
        return !(pos[0]>other.pos[0]+other.width||
                pos[0]+width<other.pos[0]||
                pos[1]>other.pos[1]+other.height||
                pos[1]+height<other.pos[1]);
    }

    @Override
    public Hitbox getHitbox() {
        return this;
    }
}
