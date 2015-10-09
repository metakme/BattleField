package BattleField;

import static BattleField.BattleField.collidables;
import java.util.ArrayList;

public interface Collidable {
    
    
    
    public default boolean checkCollision (Hitbox other){
        return false;        
    }
    
    public Hitbox getHitbox();
    
    public default boolean checkGlobalCollision(){
        for(Object obj : collidables){
            Collidable object = (Collidable)obj;
            if(object.getHitbox()!=this.getHitbox()&&this.checkCollision(object.getHitbox()))
                return true;
        }
        return false;
    }
    public default ArrayList<Object> checkGlobalCollisionWithObstacle(){
        ArrayList<Object> list = new ArrayList<>();
        boolean bool = false;
        for(Object obj : collidables){
            Collidable object = (Collidable)obj;
            if(object.getHitbox()!=this.getHitbox()&&this.checkCollision(object.getHitbox())){
                bool = true;
                list.add(bool);
                list.add(object);
                return list;
            }
        }
        list.add(bool);
        return list;
    }
}
