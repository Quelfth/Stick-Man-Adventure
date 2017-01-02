package stickManAdventure;

import java.util.TreeSet;

public class Entity {
	int x = 0;
	int y = 0;
	int x2 = 0;
	int y2 = 0;
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
	
	public void update(){
		wallsLeft.add(x);
		wallsRight.add(StickManAdventure.getLevel().width - x2);
		ceilings.add(y2 - StickManAdventure.frameHeight + StickManAdventure.getLevel().height);
		floors.add(StickManAdventure.frameHeight - y);
		
	}
}
