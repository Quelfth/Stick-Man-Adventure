package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.TreeSet;

public class Entity {
	Hitbox k = new Hitbox();
	int hp = 0;
	int maxHp = 20;
	int velX = 0;
	int velY = 0;
	int extraVel = 0;
	TreeSet<Integer> wallsLeft = new TreeSet<>();
	TreeSet<Integer> wallsRight = new TreeSet<>();
	TreeSet<Integer> ceilings = new TreeSet<>();
	TreeSet<Integer> floors = new TreeSet<>();
	ArrayList<DamageIndicator> indicators = new ArrayList<>();
	DamageIndicator indicator;
	int leftWall = 0;
	int rightWall = 0;
	int floor = 0;
	int ceiling = 0;
	int ticks = 0;
	int lastHealth = 0;
	int lastMod = 0;
	int jumps = 0;
	int speed = 0;
	int jumpForce = 0;
	int wallJumpForce = 0;
	int doubleJumps = 0;
	boolean dir = false;

	void takeCollision(TreeSet<Integer>[] c){
	    for(int i = 0; i < 4; i++)
	        for(int j : c[i])
	            switch(i){
                    case 0:
                        floors.add(j);
                    case 1:
                        wallsLeft.add(j);
                    case 2:
                        ceilings.add(j);
                    case 3:
                        wallsRight.add(j);
                }
    }

	void indicate(int x, int y, int dam) {
		indicator.add(x, y, dam, dam > 0 ? new Color(255, 0, 0) : new Color(0, 255, 0));
	}

	public Entity(int x, int y, int x2, int y2) {
		k.setX(x);
		k.setY(y);
		k.setWidth(x2-x);
		k.setHeight(y2-y);
		hp = 20;
		jumpForce = 10;
		indicator = new DamageIndicator(0, 0, 0, new Color(0, 0, 0));
		indicator.time = 21;
	}

	public void update() {
		if (hp != lastHealth)
			lastMod = ticks % 20;
		if (ticks % 20 == 0)

			ticks++;
		if (hp > maxHp) {
			hp = maxHp;
		}
	}

	public void eUpdate() {
		update();
		wallsLeft.add(k.x);
		wallsRight.add(Adventure.getLevel().width - k.x2());
		ceilings.add(k.y + 1050 - Adventure.getLevel().height);
		floors.add(1050 - k.y2());
		leftWall = wallsLeft.first();
		rightWall = wallsRight.first();
		floor = floors.first();
		ceiling = ceilings.first();
		velY--;
		if(k.y < 1050 - Adventure.getLevel().height)
		    k.y = 1050 - Adventure.getLevel().height;
		if (ceilings.first() >= velY && floors.first() >= -velY) {
			k.y -= velY;
		} else {
			if (velY > 0)
				k.y += ceilings.first();
			if (velY > 0)
				k.y -= floors.first();
			velY = 0;
		}
		if (k.y2() > 1050) {
			k.y = 1050 - k.h;
			velY = 0;
		}
		if (-velX < wallsLeft.first() && velX < wallsRight.first())
			k.x += velX;
		else {
			if (velX > 0)
				k.x += wallsRight.first();
			if (velX < 0)
				k.x -= wallsLeft.first();
		}
		seek(Adventure.s);
		bite(Adventure.s);

		indicator.update();
		wallsLeft.clear();
		wallsRight.clear();
		ceilings.clear();
		floors.clear();
	}

	public void seek(Entity subject) {
		if (!touchH(subject)) {
			if (subject.k.x < k.x)
				move(false);
			else if (subject.k.x > k.x)
				move(true);
		} else {
			if (velX > 0)
				velX--;
			else if (velX < 0)
				velX++;
		}
		if (touchH(subject) && !touchV(subject))
			jump();
	}

	public boolean onFloor() {
		return k.y == ground();
	}

	public int ground() {
		return k.y + floor;
	}

	public int roof() {
		return k.y2() - ceiling;
	}

	public int leftSide() {
		return k.x - leftWall;
	}

	public int rightSide() {
		return k.x2() + rightWall;
	}

	public boolean onLeft() {
		return k.x == leftSide();
	}

	public boolean onCeiling() {
		return k.y2() == roof();
	}

	public boolean onRight() {
		return k.x2() == rightSide();
	}

	public boolean onWall() {
		return onLeft() || onRight();
	}

	public void jump() {
		if (hp > 0) {
			if (velY < 0 && (onFloor() || onWall())) {
				velY = 0;
			}
			if (onFloor()) {
				velY += jumpForce;
			}
		}
	}

	public void move(boolean dir) {
		if (dir) {
			if (velX >= 0)
				velX++;
			else
				velX += 2;
		} else {
			if (velX <= 0)
				velX--;
			else
				velX -= 2;
		}
	}

	public boolean touchH(Entity subject) {
		for (int i = subject.k.x; i < subject.k.x2(); i++) {
			if (i > k.x && i < k.x2()) {
				return true;
			}
		}
		return false;
	}

	public boolean touchV(Entity subject) {
		for (int i = subject.k.y; i > subject.k.y2(); i--) {
			if (i > k.y && i < k.y2()) {
				return true;
			}
		}
		return false;
	}

	public boolean touch(Entity subject) {
		return touchH(subject) && touchV(subject);
	}

	public void bite(Entity subject) {
		if (touch(subject)) {
			subject.hp--;
			indicator.add(subject.k.xC(), subject.k.y2(), 1, new Color(255, 0, 0));
		}

	}

	public void paint(Graphics g) {
		paintBox(g);
		indicator.paint(g);
	}

	public void paintBox(Graphics g) {
		g.setColor(new Color(255, 127, 0));
		g.fillRect(k.x + Adventure.xo, k.y + Adventure.yo, k.w, k.h);
	}
}
