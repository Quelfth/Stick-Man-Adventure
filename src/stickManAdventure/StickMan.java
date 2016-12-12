package stickManAdventure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class StickMan extends Entity implements KeyListener{
	int spawnX = 0;
	int spawnY = 0;
	int x2 = 72;
	int y2 = -192;
	int xC = 36;
	int yC = -96;
	int velX = 0;
	int velY = 0;
	boolean dir = true;
	BufferedImage idleLeft;
	BufferedImage idleRight;
	BufferedImage[] goLeft = new BufferedImage[10];
	BufferedImage[] goRight = new BufferedImage[10];
	BufferedImage jumpingLeft;
	BufferedImage jumpingRight;
	BufferedImage climbingLeftLookingLeft;
	BufferedImage climbingRightLookingLeft;
	BufferedImage climbingLeftLookingRight;
	BufferedImage climbingRightLookingRight;
	BufferedImage fallingLeft;
	BufferedImage fallingRight;
	BufferedImage deadLeft;
	BufferedImage deadRight;
	boolean facing = true;
	JFrame frame = null;
	int frameHeight = 1500;
	int frameWidth = 1050;
	int ground = 0;
	int roof = 0;
	int leftSide = 0;
	int rightSide = 0;
	int codePressed = 0;
	int codeReleased = 0;
	int walkVel = 0;
	int extraVel = 0;
	boolean vkgrave = false;
	boolean vkw = false;
	boolean vks = false;
	boolean vka = false;
	boolean vkd = false;
	boolean vkshift = false;
	boolean vkspace = false;
	boolean fn3 = false;
	int jumps = 0;
	int speed = 0;
	int jumpForce = 0;
	int wallJumpForce = 0;
	int frameOfAnimation = 0;
	String skinPath = "/Users/league/Desktop/Stick-Man-Adventure/src/stickManAdventure/skin/";
	int doubleJumps = 0;
	boolean onFloor = false;
	boolean onLeft = false;
	boolean onRight = false;
	boolean onCeiling = false;
	boolean Float = false;
	boolean onWall = false;
	boolean falling = false;
	boolean rising = false;
	boolean retreating = false;
	boolean running = false;
	TreeSet<Integer> zonesLeft = new TreeSet<Integer>();
	TreeSet<Integer> zonesRight = new TreeSet<Integer>();
	TreeSet<Integer> zonesUp = new TreeSet<Integer>();
	TreeSet<Integer> zonesDown = new TreeSet<Integer>();
	int invisibilityFrames = 0;

	public StickMan(int _x, int _y, JFrame _frame) {
		spawnX = _x;
		spawnY = _y;
		x = _x;
		y = _y;
		maxHp = 60;
		hp = maxHp;
		frame = _frame;

		try {
			idleLeft = loadImgRes("idleLeft.png");
			idleRight = loadImgRes("idleRight.png");
			for (int i = 0; i < 9; i++)
				goLeft[i] = loadImgRes("goLeft0" + i + ".png");
			for (int i = 0; i < 9; i++)
				goRight[i] = loadImgRes("goRight0" + i + ".png");
			jumpingLeft = loadImgRes("jumpingLeft.png");
			jumpingRight = loadImgRes("jumpingRight.png");
			climbingLeftLookingLeft = loadImgRes("climbingLeftLookingLeft.png");
			climbingRightLookingLeft = loadImgRes("climbingRightLookingLeft.png");
			climbingLeftLookingRight = loadImgRes("climbingLeftLookingRight.png");
			climbingRightLookingRight = loadImgRes("climbingRightLookingRight.png");
			fallingLeft = loadImgRes("fallingLeft.png");
			fallingRight = loadImgRes("fallingRight.png");
			deadLeft = loadImgRes("deadLeft.png");
			deadRight = loadImgRes("deadRight.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		frameHeight = frame.getHeight();
		frameWidth = frame.getWidth();
		ground = frameHeight;
		rightSide = frameWidth;
		frame.addKeyListener(this);
	}

	private BufferedImage loadImgRes(String imgName) throws IOException {
		return ImageIO.read(StickMan.class.getResourceAsStream("/" + imgName));
	}

	// Respawn
	public void respawn(int stage) {
		switch (stage) {
		case 0:
			spawnX = 96;
			spawnY = 0;
			break;
		case 1:
			spawnX = 96;
			spawnY = 999;
			break;
		case 2:
			spawnX = 96;
			spawnY = 999;
			break;
		case 3:
			spawnX = 96;
			spawnY = 999;
			break;
		case 4:
			spawnX = 10;
			spawnY = 300;
			break;
		default:
			spawnX = x;
			spawnY = y;
			break;
		}
		x = spawnX;
		y = spawnY;
		hp = maxHp;
		setVelocity(0, 0);
	}

	public void respawn() {
		respawn(StickManAdventure.stage);
	}

	public void draw(Graphics g, BufferedImage skin) {
		if (dir) {
			g.drawImage(skin, x - 88 + StickManAdventure.xo, y - 256 + StickManAdventure.yo, null);
		} else {
			g.drawImage(skin, x - 96 + StickManAdventure.xo, y - 256 + StickManAdventure.yo, null);
		}

	}

	public BufferedImage animateGoRight() {
		return skinGoRight(frameClock());
	}

	public BufferedImage animateGoLeft() {
		return skinGoLeft(frameClock());
	}

	public int visActID() {
		if (hp <= 0) {
			return -1;
		}
		if ((vkd || vka) && onFloor) {
			return 1;
		} else {
			if (onFloor) {
				return 0;
			} else {
				if (onLeft) {
					return 3;
				} else {
					if (onRight) {
						return 4;
					}
					if (rising) {
						return 2;
					} else {
						if (falling) {
							return 5;
						}
						return 0;
					}
				}
			}

		}
	}

	// Paint
	public void paint(Graphics g) {
		if (dir) {
			switch (visActID()) {
			case -1:
				draw(g, deadRight);
				break;
			case 0:
				draw(g, idleRight);
				break;
			case 1:
				draw(g, animateGoRight());
				break;
			case 2:
				draw(g, jumpingRight);
				break;
			case 3:
				draw(g, climbingLeftLookingRight);
				break;
			case 4:
				draw(g, climbingRightLookingRight);
				break;
			case 5:
				draw(g, fallingRight);
				break;
			}
		} else {
			switch (visActID()) {
			case -1:
				draw(g, deadLeft);
				break;
			case 0:
				draw(g, idleLeft);
				break;
			case 1:
				draw(g, animateGoLeft());
				break;
			case 2:
				draw(g, jumpingLeft);
				break;
			case 3:
				draw(g, climbingLeftLookingLeft);
				break;
			case 4:
				draw(g, climbingRightLookingLeft);
				break;
			case 5:
				draw(g, fallingLeft);
				break;
			}
		}

	}

	public void debugLines(Graphics g) {
		int xo = StickManAdventure.xo;
		int yo = StickManAdventure.yo;
		g.setColor(new Color(255, 0, 0));
		g.drawLine(0, roof + yo, frameWidth, roof + yo);
		g.setColor(new Color(0, 0, 255));
		g.drawLine(0, ground - 1 + yo, frameWidth, ground - 1 + yo);
		g.setColor(new Color(0, 255, 0));
		g.drawLine(leftSide + xo, 0, leftSide + xo, frameHeight);
		g.setColor(new Color(255, 255, 0));
		g.drawLine(rightSide - 1 + xo, 0, rightSide - 1 + xo, frameHeight);
		g.setColor(new Color(0, 127, 255));
		g.drawRect(x + xo, y2 + yo, 72, 192);
	}

	// Collision
	public void floor() {
		int effVel = velY;
		int floor = ground - y;
		if (floor < -effVel) {
			y += floor;
		} else {
			y -= effVel;
		}
		if (floor == 0) {
			velY = 0;
		}
	}

	public void ceiling() {
		int ceiling = y2 - roof;
		if (ceiling < velY) {
			y -= ceiling;
		} else {
			y -= velY;
		}
		if (ceiling == 0) {
			velY = 0;
		}
	}

	public void leftWall() {
		int leftWall = x - leftSide;
		if (leftWall < -velX) {
			x -= leftWall;
		} else {
			x += velX;
		}
		if (leftWall == 0) {
			velX = 0;
		}
	}

	public void rightWall() {
		int rightWall = rightSide - x2;
		if (rightWall < velX) {
			x += rightWall;
		} else {
			x += velX;
		}
		if (rightWall == 0) {
			velX = 0;
		}
	}

	// Key Detection
	public void vkIndex(int i, boolean o) {
		switch (i) {
		case KeyEvent.VK_BACK_QUOTE:
			vkgrave = o;
			break;
		case KeyEvent.VK_W:
			vkw = o;
			break;
		case KeyEvent.VK_A:
			vka = o;
			break;
		case KeyEvent.VK_S:
			vks = o;
			break;
		case KeyEvent.VK_D:
			vkd = o;
			break;
		case KeyEvent.VK_SHIFT:
			vkshift = o;
			break;
		case KeyEvent.VK_SPACE:
			vkspace = o;
			break;

		}
	}

	public void setFloat(boolean _Float) {
		Float = _Float;
	}

	// Update
	public void update() {
		if (hp > maxHp) {
			hp = maxHp;
		}
		if (hp <= -10000) {
			respawn();
		}
		if (hp <= 0 && onFloor) {
			respawn();
		}
		if (codePressed == KeyEvent.VK_F3) {
			if (fn3)
				fn3 = false;
			else
				fn3 = true;
		}
		zonesLeft.add(x);
		zonesRight.add(StickManAdventure.getLevel().width - x2);
		zonesUp.add(y2 - frameHeight + StickManAdventure.getLevel().height);
		zonesDown.add(frameHeight - y);
		ground = y + zonesDown.first();
		roof = y2 - zonesUp.first();
		leftSide = x - zonesLeft.first();
		rightSide = x2 + zonesRight.first();
		onFloor = (y == ground);
		onLeft = (x == leftSide);
		onCeiling = (y2 == roof);
		onRight = (x2 == rightSide);
		onWall = (onLeft || onRight);
		falling = velY < 0;
		rising = velY > 0;
		running = velX > 0;
		retreating = velX < 0;
		frameOfAnimation++;
		if (y == ground && velY == 0) {
			jumps = 0;
		}
		x2 = x + 72;
		y2 = y - 192;
		xC = x + 36;
		yC = y - 96;
		if (y < ground) {
			velY--;
		}
		if (extraVel < 0) {
			extraVel++;
		}
		if (extraVel > 0) {
			extraVel--;
		}
		velX = walkVel + extraVel;
		if (Float && onWall && !onFloor && hp > 0) {
			setVelocity(0, 0);
			y++;
		}
		vkIndex(codePressed, true);
		vkIndex(codeReleased, false);
		codePressed = 0;
		codeReleased = 0;
		if (velY < 0) {
			floor();
		} else {
			ceiling();
		}
		if (velX < 0) {
			leftWall();
		} else {
			rightWall();
		}
		zonesLeft.clear();
		zonesRight.clear();
		zonesUp.clear();
		zonesDown.clear();
	}

	public void setStats(int _speed, int _jumpForce, int _wallJumpForce, int _doubleJumps) {
		speed = _speed;
		jumpForce = _jumpForce;
		wallJumpForce = _wallJumpForce;
		doubleJumps = _doubleJumps;
	}

	public void setStats(int _speed, int _jumpForce, int _doubleJumps) {
		speed = _speed;
		jumpForce = _jumpForce;
		wallJumpForce = _jumpForce;
		doubleJumps = _doubleJumps;
	}

	// Jumping
	public void jump() {
		if (hp > 0) {
			if (velY < 0 && (onFloor || onWall)) {
				velY = 0;
			}
			if (!onWall || onFloor) {
				if (jumps < 1 + doubleJumps) {
					velY += jumpForce;
					jumps++;
				}
			} else {
				if (onLeft && dir) {
					velY += (int) (jumpForce / 1.5);
					extraVel += wallJumpForce;
					jumps = 1;
				}
				if (onRight && !dir) {
					velY += (int) (jumpForce / 1.5);
					extraVel -= wallJumpForce;
					jumps = 1;
				}
			}
		}
	}

	// Walking
	public void walk(boolean inc, boolean _dir) {
		if (hp > 0) {
			if (inc) {
				dir = _dir;
				if (walkVel < speed && _dir == true) {
					walkVel++;
				}
				if (walkVel > -speed && _dir == false) {
					walkVel--;
				}
			} else {
				if (walkVel > 0 && _dir == true) {
					if (walkVel > 3) {
						walkVel -= 4;
					} else {
						walkVel = 0;
					}
				}
				if (walkVel < 0 && _dir == false) {
					if (walkVel < -3) {
						walkVel += 4;
					} else {
						walkVel = 0;
					}
				}
			}
			if (walkVel > speed) {
				walkVel = speed;
			}
			if (walkVel < -speed) {
				walkVel = -speed;
			}
		}
	}

	public void setVelocity(int _velX, int _velY) {
		velX = _velX;
		velY = _velY;
	}

	public BufferedImage skinGoLeft(int frame) {
		return goLeft[frame];
	}

	public BufferedImage skinGoRight(int frame) {
		return goRight[frame];
	}

	public int frameClock() {
		if (frameOfAnimation > 19) {
			frameOfAnimation = 0;
		}
		return frameOfAnimation / 2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		codePressed = code;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		codeReleased = code;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
