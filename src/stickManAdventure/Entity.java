package stickManAdventure;

import java.awt.Graphics;
import java.util.TreeSet;

public class Entity {
	int x = 0;
	int y = 0;
	int x2 = 0;
	int y2 = 0;
	int xC = 0;
	int yC = 0;
	int hp = 0;
	int maxHp = 20;
	int velX = 0;
	int velY = 0;
	int extraVel = 0;
	int invisibilityFrames = 0;
	TreeSet<Integer> zonesLeft = new TreeSet<Integer>();
	TreeSet<Integer> zonesRight = new TreeSet<Integer>();
	TreeSet<Integer> zonesUp = new TreeSet<Integer>();
	TreeSet<Integer> zonesDown = new TreeSet<Integer>();
	String name = "";
	void paint(Graphics g){
		
	}
	void update(){
		
	}
}
