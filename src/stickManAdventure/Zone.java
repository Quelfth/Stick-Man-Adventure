package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;

public class Zone {
	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;
	int type = 0;
	int metaType = 0;
	int adData = 0;
	static StickMan s = null;
	int height = 0;
	int width = 0;
	int gx1 = 0;
	int gx2 = 0;
	int gy1 = 0;
	int gy2 = 0;
	private int func0;
	Entity ent = null;
	DamageIndicator indicator = new DamageIndicator(0, 0, 0, null);

	public void setAdData(int adData) {
		this.adData = adData;
	}

	public Zone(int _x1, int _y1, int _x2, int _y2, int _type, int _metaType) {
		x1 = _x1;
		y1 = _y1;
		x2 = _x2;
		y2 = _y2;
		type = _type;
		metaType = _metaType;
		width = x2 - x1;
		height = y1 - y2;
	}

	private void mapHorizontalCollision() {
		//for all y values the entity occupies
		for (int i = ent.y2; i < ent.y; i++) {
			//if the zone occupies that x
			if (i < y1 && i > y2) {
				if (ent.x - x2 >= 0) {
					ent.zonesLeft.add(ent.x - x2);
				}
				if (x1 - ent.x2 >= 0) {
					ent.zonesRight.add(x1 - ent.x2);
				}
			}
		}
	}

	private void mapVerticalCollision() {
		//for all x values the entity occupies
		for (int i = ent.x; i < ent.x2; i++) {
			//if the zone occupies that x value as well
			if (i > x1 && i < x2) {
				//if the top of the entity is below the bottom of the zone
				if (ent.y2 - y1 >= 0) {
					//add the distance between them to zonesUp
					ent.zonesUp.add(ent.y2 - y1);
				}
				//if the bottom of the entity is above the top of the zone
				if (y2 - ent.y >= 0) {
					//add the distance between them to zonesDown
					ent.zonesDown.add(y2 - ent.y);
				}
			}
		}
	}

	private void mapTopCollision(Entity e) {
		for (int i = e.x; i < e.x2; i++) {
			if (i > x1 && i < x2) {
				if (y2 - e.y >= 0) {
					e.zonesDown.add(y2 - e.y);
				}
			}
		}
	}

	private void mapBottomCollision(Entity e) {
		for (int i = e.x; i < e.x2; i++) {
			if (i > x1 && i < x2) {
				if (e.y2 - y1 >= 0) {
					e.zonesUp.add(e.y2 - y1);
				}
			}
		}
	}

	private void mapLeftCollision(Entity e) {
		for (int i = e.y2; i < e.y; i++) {
			if (i < y1 && i > y2) {
				if (x1 - e.x2 >= 0) {
					e.zonesRight.add(x1 - e.x2);
				}
			}
		}
	}

	private void mapRightCollision(Entity e) {
		for (int i = e.y2; i < e.y; i++) {
			if (i < y1 && i > y2) {
				if (e.x - x2 >= 0) {
					e.zonesLeft.add(e.x - x2);
				}
			}
		}
	}

	private void mapCollision() {
		mapVerticalCollision();
		mapHorizontalCollision();
	}

	private void mapUpwardMotionCancel() {
		if (ent.y2 < y1 && detHIntersect() && ent.velY > 0 && !(ent.y2 < y2)) {
			ent.velY = 0;
		}
	}

	private void mapDownwardMotionCancel() {
		if (ent.y > y2 && detHIntersect() && ent.velY < 1 && !(ent.y > y1)) {
			ent.velY = 1;
		}
	}

	private void mapVerticalMotionCancel() {
		mapUpwardMotionCancel();
		mapDownwardMotionCancel();
	}

	public boolean detVIntersect() {
		for (int i = ent.y2; i < ent.y; i++) {
			if (i < y1 && i > y2) {
				return true;
			}
		}
		return false;
	}

	public boolean detHIntersect() {
		for (int i = ent.x; i < ent.x2; i++) {
			if (i > x1 && i < x2) {
				return true;
			}
		}
		return false;
	}

	public boolean detIntersect() {
		return detHIntersect() && detVIntersect();
	}

	private void mapDamage(int damage, int velocityV, int velocityH) {
		if (detIntersect()) {
			ent.hp -= damage;
			ent.invisibilityFrames = 20;
			ent.extraVel += velocityH;
			ent.velY += velocityV;
			indicator = new DamageIndicator(s.xC, s.yC, damage, Color.RED);
		}
	}

	private void mapDamage(int damage) {
		mapDamage(damage, 0, 0);
	}

	public static void setP1(StickMan _s) {
		s = _s;
	}

	@SuppressWarnings("unused")
	private void mapFunction(int id) {
		mapFunction(id, 0);
	}

	private void mapFunction(int id, int metaId) {
		mapFunction(id, metaId, 0);
	}

	private void mapFunction(int id, int metaId, int adData) {
		if (detIntersect()) {
			switch (id) {
			case 0:
				switch (metaId) {
				case -1:
					if (s.hp > 0 && (detFullIntersect() && s.vkw && !StickManAdventure.lastWCheck)) {
						StickManAdventure.stage--;
						s.respawn();
					}
					break;
				case 0:
					// if you can go through the door && pressing w
					if (s.hp > 0 && ((StickManAdventure.stage > 0 && (detFullIntersect() && s.vkw && StickManAdventure.lastWCheck))
							|| (StickManAdventure.stage <= 0 && (s.onFloor || (detFullIntersect() && s.onWall))))) {
						// go through the door
						StickManAdventure.stage++;
						s.respawn();
					}
					break;
				case 2:
					if (s.hp > 0 && detFullIntersect())
						func0 = 1;
					if (func0 == 1 && s.vkw && StickManAdventure.lastWCheck) {
						StickManAdventure.stage = adData;
					}
				}
				break;
			}
		}
	}

	private boolean detFullIntersect() {
		return s.y <= y1 && s.y2 >= y2 && s.x >= x1 && s.x2 <= x2;
	}

	public void update(Entity ent) {
		this.ent = ent;
		indicator.update();
		switch (type) {
		case -2:
			switch (metaType) {
			case 0:
				mapDamage(5, 20, 0);
				break;
			case 1:
				mapDamage(5, 0, 20);
				break;
			case 2:
				mapDamage(5, -20, 0);
				break;
			case 3:
				mapDamage(5, 0, -20);
				break;
			}
		case -1:
			switch (metaType) {
			case 0:
				mapDamage(9999);
				break;
			case 1:
				mapDamage(9999, 20, 0);
				break;
			}

			break;
		case 1:
			mapCollision();
			mapVerticalMotionCancel();
			break;
		case 2:
			switch (metaType) {
			case 0:
				if (!s.vks) {
					mapTopCollision(s);
				}
				break;
			case 1:
				if (!s.vkd) {
					mapLeftCollision(s);
				}
				break;
			case 2:
				if (!s.vkw) {
					mapBottomCollision(s);
				}
				break;
			case 3:
				if (!s.vka) {
					mapRightCollision(s);
				}
			}
			break;
		case 3:
			if (this.ent.name.equals("Stick Man"))
				mapFunction(0, metaType, adData);
			break;
		}
	}

	private void fillZone(Graphics g) {
		g.fillRect(x1 + StickManAdventure.xo, y2 + StickManAdventure.yo, x2 - x1, y1 - y2);
	}

	public void paint(Graphics g) {
		indicator.paint(g);
		int[] TriX = null;
		int[] TriY = null;
		TriX = new int[3];
		TriY = new int[3];
		switch (type) {
		case -1:
			g.setColor(new Color(255, 0, 0));
			fillZone(g);
			break;
		case 1:
			g.setColor(new Color(191, 191, 191));
			fillZone(g);
			break;
		case 2:
			switch (metaType) {
			case 0:
				g.setColor(new Color(204, 153, 102));
				g.drawLine(x1 + StickManAdventure.xo, y2 + StickManAdventure.yo, x2 + StickManAdventure.xo, y2 + StickManAdventure.yo);
				TriX[0] = x1 + StickManAdventure.xo;
				TriX[1] = x1 + StickManAdventure.xo;
				TriX[2] = x1 + StickManAdventure.xo + width / 8;
				TriY[0] = y2 + StickManAdventure.yo;
				TriY[1] = y2 + StickManAdventure.yo + height / 4;
				TriY[2] = y2 + StickManAdventure.yo;
				g.drawPolygon(TriX, TriY, 3);
				TriX[0] = x2 + StickManAdventure.xo;
				TriX[1] = x2 + StickManAdventure.xo;
				TriX[2] = x2 + StickManAdventure.xo - width / 8;
				g.drawPolygon(TriX, TriY, 3);
				break;
			case 1:
				g.setColor(new Color(204, 153, 102));
				g.drawLine(x1 + StickManAdventure.xo, y1 + StickManAdventure.yo, x1 + StickManAdventure.xo, y2 + StickManAdventure.yo);
				TriX[0] = x1 + StickManAdventure.xo;
				TriX[1] = x1 + StickManAdventure.xo + width / 4;
				TriX[2] = x1 + StickManAdventure.xo;
				TriY[0] = y1 + StickManAdventure.yo;
				TriY[1] = y1 + StickManAdventure.yo;
				TriY[2] = y1 + StickManAdventure.yo - width / 8;
				g.drawPolygon(TriX, TriY, 3);
				TriY[0] = y2 + StickManAdventure.yo;
				TriY[1] = y2 + StickManAdventure.yo;
				TriY[2] = y2 + StickManAdventure.yo + width / 8;
				g.drawPolygon(TriX, TriY, 3);
				break;
			case 2:
				g.setColor(new Color(204, 153, 102));
				g.drawLine(x1 + StickManAdventure.xo, y1 + StickManAdventure.yo, x2 + StickManAdventure.xo, y1 + StickManAdventure.yo);
				TriX[0] = x1 + StickManAdventure.xo;
				TriX[1] = x1 + StickManAdventure.xo;
				TriX[2] = x1 + StickManAdventure.xo + width / 8;
				TriY[0] = y2 + StickManAdventure.yo;
				TriY[1] = y2 + StickManAdventure.yo + height / 4;
				TriY[2] = y2 + StickManAdventure.yo;
				g.drawPolygon(TriX, TriY, 3);
				TriX[0] = x2 + StickManAdventure.xo;
				TriX[1] = x2 + StickManAdventure.xo;
				TriX[2] = x2 + StickManAdventure.xo - width / 8;
				g.drawPolygon(TriX, TriY, 3);
				break;
			}
			break;
		case 3:
			switch (metaType) {
			case -1:
				drawDoor(g, new Color(63, 63, 63), new Color(95, 95, 95), new Color(127, 127, 127));
				break;
			case 0:
				drawDoor(g, new Color(172, 115, 57), new Color(96, 64, 32), new Color(63, 63, 63));
				break;
			case 2:
				if (func0 == 1)
					drawDoor(g, new Color(255, 192, 0), new Color(192, 144, 0), new Color(255, 255, 0));
				break;
			}
			break;
		}
	}

	private void drawDoor(Graphics g, Color main, Color indents, Color knob) {
		g.setColor(main);
		fillZone(g);
		g.setColor(indents);
		g.fillRect(x1 + StickManAdventure.xo + (width / 7), y2 + StickManAdventure.yo + (height / 11), width * 2 / 7, height * 4 / 11);
		g.fillRect(x1 + StickManAdventure.xo + (width * 4 / 7), y2 + StickManAdventure.yo + (height / 11), width * 2 / 7, height * 4 / 11);
		g.fillRect(x1 + StickManAdventure.xo + (width / 7), y2 + StickManAdventure.yo + (height * 6 / 11), width * 2 / 7, height * 4 / 11);
		g.fillRect(x1 + StickManAdventure.xo + (width * 4 / 7), y2 + StickManAdventure.yo + (height * 6 / 11), width * 2 / 7, height * 4 / 11);
		g.setColor(knob);
		g.fillOval(x1 + StickManAdventure.xo + (width * 49 / 56), y2 + StickManAdventure.yo + (height * 6 / 11) + ((height / 2) / 56), width * 6 / 56, width * 6 / 56);
	}
}
