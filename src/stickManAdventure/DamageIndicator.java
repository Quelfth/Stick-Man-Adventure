package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DamageIndicator {
    int x;
    int y;
    int damage;
    int time = 21;
    Color color = null;
    DamageIndicator next;
    int depth = 0;

    public DamageIndicator(int x, int y, int damage, Color color) {
        this.color = color;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }

    private void thisIs(int x, int y, int damage, Color color) {
        this.color = color;
        this.damage = damage;
        this.x = x;
        this.y = y;
        time = 0;
    }

    public DamageIndicator() {
        this.color = Color.BLACK;
        this.damage = 0;
        this.x = 0;
        this.y = 0;
    }

    public void add(int x, int y, int damage, Color color) {
        if (time > 20)
            thisIs(x, y, damage, color);
        else {
            if(next == null)
                next = new DamageIndicator();
            next.add(x, y, damage, color);
            depth++;
        }

    }

    public void paint(Graphics g) {
        int xo = Adventure.xo;
        int yo = Adventure.yo;
        g.setColor(color);
        if (time < 20) {
            if (damage > 0)
                g.drawString("-" + damage, x + xo, y + yo);
            if (damage < 0)
                g.drawString("+" + -damage, x + xo, y + yo);

            if (damage == 0)
                g.drawString("0", x, y);

        }
        if(depth > 0)
            next.paint(g);
    }

    public void update() {
        if (time < 20 && damage != 0) {
            y--;
            Random r = new Random();
            boolean xs = r.nextBoolean();
            if (xs)
                x += 2;
            else
                x -= 2;
            time++;
        }
        if(depth > 0)
            next.update();
    }
}
