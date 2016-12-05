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

	public DamageIndicator(int _x, int _y, int _damage, Color _color) {
		color = _color;
		damage = _damage;
		x = _x;
		y = _y;
	}

	public void paint(Graphics g) {
		int xo = StickManAdventure.xo;
		int yo = StickManAdventure.yo;
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
