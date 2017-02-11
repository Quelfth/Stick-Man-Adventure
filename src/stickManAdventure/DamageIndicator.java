package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DamageIndicator {
	int x;
	int y;
	int damage;
	int time = 0;
	Color color = null;

	public DamageIndicator(int x, int y, int damage, Color color) {
		this.color = color;
		this.damage = damage;
		this.x = x;
		this.y = y;
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

	}

	public void update() {
		if (time < 20 && damage >= 0) {
			y--;
			Random r = new Random();
			boolean xs = r.nextBoolean();
			if (xs)
				x += 2;
			else
				x -= 2;
			time++;
		}
	}
}
