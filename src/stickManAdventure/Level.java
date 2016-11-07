package stickManAdventure;

import java.awt.Graphics;

public class Level {
	private static final int MAX_DOORS = 16;
	private static final int MAX_ZONES = 256;
	Zone[] z = new Zone[MAX_ZONES];
	Zone[] d = new Zone[MAX_DOORS];
	StickMan s = null;
	int nextZone = 0;
	int nextDoor = 0;

	public Level(StickMan s) {
		this.s = s;
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

	public void paint(Graphics g) {
		for (int i = 0; i < MAX_DOORS; i++) {
			if (d[i] == null)
				break;
			d[i].paint(g);
		}
		s.paint(g);
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
			z[i].update();
		}
		for (int i = 0; i < MAX_DOORS; i++) {
			if (d[i] == null)
				return;
			d[i].update();
		}
		s.update();
	}

}
