package stickManAdventure;

/**
 * Created by Neutron on 2/13/2017.
 */
public class Hitbox {
    int x;
    int y;
    int w;
    int h;

    int x2(){
        return x + w;
    }

    int y2(){
        return y + h;
    }

    int xC(){
        return x + (w / 2);
    }

    int yC(){
        return y + (h / 2);
    }

    boolean sect(Hitbox b){
        return dis(b) == 0;
    }

    int dis(Hitbox b){
        return (int) Math.sqrt(Math.pow(disX(b), 2)+Math.pow(disY(b), 2));
    }

    int disX(Hitbox b){
        if(x > b.x2())
            return x - b.x2();
        if(x2() < b.x)
            return x2() - b.x;
        return 0;
    }

    int disY(Hitbox b){
        if(y > b.y2())
            return y - b.y2();
        if(y2() < b.y)
            return y2() - b.y;
        return 0;
    }
}
