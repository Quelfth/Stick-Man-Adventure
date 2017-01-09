package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TreeSet;

public class Entity {
	int x = 0;
	int y = 0;
	int x2 = 0;
	int y2 = 0;
	int w = 0;
	int h = 0;
	StickMan s;
	int hp = 0;
	int maxHp = 20;
	int velX = 0;
	int velY = 0;
	int extraVel = 0;
	TreeSet<Integer> wallsLeft = new TreeSet<Integer>();
	TreeSet<Integer> wallsRight = new TreeSet<Integer>();
	TreeSet<Integer> ceilings = new TreeSet<Integer>();
	TreeSet<Integer> floors = new TreeSet<Integer>();
	
	public Entity(int x, int y, int x2, int y2) {
		this.x = x;
		this.y = y;
		w = x2-x;
		h = y2-y;
	}
	
	public void update(){
		x2 = x + w;
		y2 = y + h;
		wallsLeft.add(x);
		wallsRight.add(StickManAdventure.getLevel().width - x2);
		ceilings.add(y2 - StickManAdventure.frameHeight + StickManAdventure.getLevel().height);
		floors.add(StickManAdventure.frameHeight - y);
		velY--;
		if (ceilings.first() > velY && floors.first() > -velY) {
			y -= velY;
		}else{
			if(velY>0)
				y += ceilings.first();
			if(velY>0)
				y -= floors.first();
			velY = 0;
		}
		if (y2 > StickManAdventure.frameHeight)
			velY += 9;
		wallsLeft.clear();
		wallsRight.clear();
		ceilings.clear();
		floors.clear();
	}
	
	public void paint(Graphics g){
		paintBox(g);
	}
	
	public void paintBox(Graphics g){
		g.setColor(new Color(0, 0, 0));
		g.fillRect(x+StickManAdventure.xo, y+StickManAdventure.yo, w, h);
	}
}
