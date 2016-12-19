package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Level {
	private static final int MAX_DOORS = 16;
	private static final int MAX_ZONES = 256;
	private static final int MAX_ENTS = 256;
	Zone[] z = new Zone[MAX_ZONES];
	Zone[] d = new Zone[MAX_DOORS];
	Entity[] e = new Entity[MAX_ENTS + 1];
	int nextZone = 0;
	int nextDoor = 0;
	int nextEnt = 0;
	int height = 0;
	int width = 0;

	public Level(StickMan s, int w, int h) {
		e[MAX_ENTS] = s;
		width = w;
		height = h;
	}

	public void add(Zone z) {
		if (z.type == 3) {
			this.d[nextDoor] = z;
			nextDoor++;
		} else {
			this.z[nextZone] = z;
			nextZone++;
		}
	}

	public void add(Entity e) {

	}

	public void paint(Graphics g) {
		int xo = StickManAdventure.xo;
		int yo = StickManAdventure.yo;
		int fw = StickManAdventure.frameWidth;
		int fh = StickManAdventure.frameHeight;
		g.setColor(new Color(191, 191, 191));
		g.fillRect(0, 0, xo, fh);
		g.fillRect(xo, 0, width, yo - height + fh);
		g.fillRect(xo + width, 0, fw - xo - width, fh);
		g.fillRect(xo, yo + fh, width, -yo);
		for (int i = 0; i < MAX_DOORS; i++) {
			if (d[i] == null)
				break;
			d[i].paint(g);
		}
		e[MAX_ENTS].paint(g);
		for (int i = 0; i < MAX_ZONES; i++) {
			if (z[i] == null)
				return;
			z[i].paint(g);
		}
	}

	public void update() {
		for (int i = 0; i < MAX_ZONES; i++) {
			if (z[i] == null)
				break;
			for (int j = 0; j <= MAX_ENTS; j++)
				if(e[j] != null)
					z[i].update(e[j]);
				else
					break;
		}
		for (int i = 0; i < MAX_DOORS; i++) {
			if (d[i] == null)
				return;
			d[i].update(e[MAX_ENTS]);
		}
		e[MAX_ENTS].update();
	}

	public void updateInteractions() {

	}

}
