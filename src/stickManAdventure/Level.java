package stickManAdventure;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class Level {
	private static final int MAX_DOORS = 16;
	private static final int MAX_ZONES = 256;
	private static final int MAX_ENTS = 256;
	ArrayList<Zone> z = new ArrayList<>();
	ArrayList<Zone> d = new ArrayList<>();
	ArrayList<Entity> e = new ArrayList<>();
	StickMan s = null;
	int nextZone = 0;
	int nextDoor = 0;
	int nextEnt = 0;
	int height = 0;
	int width = 0;
	int time = 0;
	boolean timing = false;
	int winTime = -1;
	int loseTime = -1;
	
	public Level(StickMan s, int w, int h) {
		this.s = s;
		width = w;
		height = h;
	}
	
	void startTimer(){
		timing = true;
	}
	
	void setWinTime(int time){
		winTime = time;
	}
	
	void setLoseTime(int time){
		loseTime = time;
	}

    TreeSet<Integer>[] mergeCollision(Entity e){
        TreeSet<Integer>[] rets = new TreeSet[4];
	    for(Zone i : z){
	        for(int j = 0; j < 4; j++)
	            for(int k : i.getCollision(e)[j])
	                rets[j].add(k);
        }
        return rets;
    }

	public void add(Zone z) {
		if (z.type == 3) {
			this.d.add(z);
		} else {
			this.z.add(z);
		}
	}
	public void add(Entity e) {
		this.e.add(e);
	}
	public void paint(Graphics g) {
		int xo = Adventure.xo;
		int yo = Adventure.yo;
		int fw = Adventure.frameWidth;
		int fh = Adventure.frameHeight;
		g.setColor(new Color(191, 191, 191));
		g.fillRect(0, 0, xo, fh);									//left wall
		g.fillRect(xo, 0, width, yo - height + 1050);			//ceiling
		g.fillRect(xo + width, 0, fw - xo - width, fh);	//right wall
		g.fillRect(xo, yo + 1050, width, fh);							//floor
		for(Zone i : d)
		    i.paint(g);
		for(Entity i : e)
		    i.paint(g);
		s.paint(g);
		for (Zone i : z)
			i.paint(g);
		
			
		if(winTime >= 0 && winTime <= time)
			g.drawString("YOU WIN!!!", Adventure.frameWidth / 2 - 100, Adventure.frameHeight / 2 - 100);
	}

	public void update() {
		if(timing)
			time++;
		if(loseTime >= 0 && loseTime <= time)
			s.respawn();
		for (Zone i : z)
		    i.update();
		for (Entity i : e)
		    i.eUpdate();
		for (Zone i : d)
			i.update();
		s.update();
	}

}
