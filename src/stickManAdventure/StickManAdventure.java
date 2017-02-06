package stickManAdventure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StickManAdventure {
	static int xo = 0;
	static int yo = 0;
	static int stage = 0;
	static Level[] levels = new Level[256];
	static boolean lastWCheck = false;
	static int levelWidth = 0;
	static int levelHeight = 0;
	static int frameWidth = 0;
	static int frameHeight = 0;
	static StickMan s;
	static String stop;

	public static Level getLevel() {
		// if there isn't a level in the current stage value
		if (levels[stage + 127] == null) {
			// return the last level, which is the default
			return levels[255];
		}
		// else, return the level in the current stage value
		return levels[stage + 127];
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Stick Man Adventure");
		String FrameHeight = "";// JOptionPane.showInputDialog("Height of
								// Window:");
		String FrameWidth = "";// JOptionPane.showInputDialog("Width of
								// Window:");
		if (FrameWidth.equals("")) {
			FrameWidth = "1500";
		}
		if (FrameHeight.equals("")) {
			FrameHeight = "1050";
		}
		frameWidth = Integer.parseInt(FrameWidth);
		frameHeight = Integer.parseInt(FrameHeight);
		frame.setSize(frameWidth, frameHeight);
		s = new StickMan(96, 0, frame);
		Zone zoneA = new Zone(200, 800, 400, 200, 1, 0);
		Zone zoneB = new Zone(1000, 700, 1200, 500, 2, 0);
		Zone zoneC = new Zone(0, 300, 200, 200, 2, 0);
		Zone zoneD = new Zone(1200, 501, 1250, 0, 1, 0);
		Zone zoneE = new Zone(1000, 500, 1100, 100, 2, 1);
		Zone zoneF = new Zone(399, 400, 401, 200, -1, 0);
		Zone zoneG = new Zone(200, 200, 300, 0, 1, 0);
		Zone zoneH = new Zone(0, 1050, 500, 1000, 1, 0);
		Zone zoneI = new Zone(500, 1050, 1000, 1010, -1, 1);
		Zone zoneJ = new Zone(1000, 1050, 1500, 1000, 1, 0);
		Zone zoneK = new Zone(550, 1050, 950, 600, -1, 1);
		Zone zoneL = new Zone(550, 1050, 950, 250, -1, 1);
		Zone door1 = new Zone(1000, 500, 1200, 100, 3, 0);
		Zone door2 = new Zone(1300, 1000, 1500, 600, 3, 0);
		Zone doorS0 = new Zone(1300, 1050, 1500, 650, 3, 2);
		doorS0.setAdData(-8);
		Zone door10 = new Zone(200, 1050, 400, 650, 3, -1);
		Level start = new Level(s, 1500, 1300);
		start.add(door1);
		start.add(zoneA);
		start.add(zoneB);
		start.add(zoneC);
		start.add(zoneD);
		start.add(zoneE);
		start.add(zoneF);
		start.add(zoneG);
		Level lava0 = new Level(s, 1500, 1050);
		lava0.add(door2);
		lava0.add(zoneH);
		lava0.add(zoneI);
		lava0.add(zoneJ);
		Level lava1 = new Level(s, 1500, 1050);
		lava1.add(door2);
		lava1.add(zoneH);
		lava1.add(zoneI);
		lava1.add(zoneJ);
		lava1.add(zoneK);
		Level lava2 = new Level(s, 1500, 1050);
		lava2.add(door2);
		lava2.add(zoneH);
		lava2.add(zoneI);
		lava2.add(zoneJ);
		lava2.add(zoneL);
		Level cliff0 = new Level(s, 1500, 1050);
		cliff0.add(new Zone(0, 1050, 300, 300, 1, 0));
		cliff0.add(new Zone(600, 650, 640, 0, 1, 0));
		cliff0.add(new Zone(900, 1050, 980, 400, 1, 0));
		cliff0.add(new Zone(898, 1050, 900, 398, 1, 0));
		cliff0.add(new Zone(980, 500, 1500, 460, 1, 0));
		cliff0.add(new Zone(1300, 450, 1500, 50, 3, 0));
		cliff0.add(doorS0);
		Level enemy0 = new Level(s, 1500, 1050);
		enemy0.add(new Entity(200, 200, 300, 300));
		Level tyq = new Level(s, 1500, 1050);
		tyq.add(new Zone(1300, 1050, 1500, 650, 3, 2));
		Level last = new Level(s, 65536, 65536);
		last.add(door10);
		levels[-8 + 127] = tyq;
		levels[0 + 127] = start;
		levels[1 + 127] = lava0;
		levels[2 + 127] = lava1;
		levels[3 + 127] = lava2;
		levels[4 + 127] = cliff0;
		levels[5 + 127] = enemy0;
		JPanel panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 36));
				g.setColor(Color.white);
				g.fillRect(0, 0, frameWidth, frameHeight);
				getLevel().paint(g);
				g.setColor(Color.RED);
				if (s.hp >= 0)
					g.drawString(s.hp + "", 1400, 200);
				else
					g.drawString("0", 1400, 200);
				if (s.fn3)
					s.debugLines(g);
			}
		};
		frame.setVisible(true);
		frame.add(panel);
		panel.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boolean lastSpaceCheck = false;

		s.setStats(20, 30, 0);
		Zone.setP1(s);
		while (true) {
			xo = (frameWidth / 2)-s.x;
			yo = (frameHeight / 2)-s.y;
			frame.pack();
			s.frame = frame;
			if (s.vkgrave) {
				if (s.codePressed != KeyEvent.VK_BACK_QUOTE == false) {
					break;
				}
				String Speed = JOptionPane.showInputDialog("Speed:");
				int speed = 20;
				int jump = 30;
				int wallJump = 30;
				int doubleJumps = 0;
				try {
					speed = Integer.parseInt(Speed);
				} catch (NumberFormatException e) {
				}
				String Jump = JOptionPane.showInputDialog("Jump Force:");
				try {
					jump = Integer.parseInt(Jump);
				} catch (NumberFormatException e) {
				}
				String WallJump = JOptionPane.showInputDialog("Wall Jump Force:");
				try {
					wallJump = Integer.parseInt(WallJump);
				} catch (NumberFormatException e) {
				}
				String DoubleJumps = JOptionPane.showInputDialog("Double Jumps:");
				try {
					doubleJumps = Integer.parseInt(DoubleJumps);
				} catch (NumberFormatException e) {
				}
				s.setStats(speed, jump, wallJump, doubleJumps);
				String Stage = JOptionPane.showInputDialog("Stage:");
				try {
					stage = Integer.parseInt(Stage);
				} catch (NumberFormatException e) {
				}
				s.vkgrave = false;
			}

			if (s.vkd) {
				s.walk(true, true);
			} else {
				s.walk(false, true);
			}
			if (s.vka) {
				s.walk(true, false);
			} else {
				s.walk(false, false);
			}

			if (s.vkspace) {
				if (lastSpaceCheck == false) {
					s.jump();
				}
				lastSpaceCheck = true;
			} else {
				lastSpaceCheck = false;
			}
			s.setFloat(s.vkshift && !s.vkspace);
			getLevel().update();
			lastWCheck = s.vkw;
			s.update();
			panel.repaint();
			try{
			System.out.println(frameHeight - levels[5+127].e[0].y2 + " " + levels[5+127].e[0].velY);
			}catch(Exception e){
			}
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(stop);
		}
	}
	
	static void stop(String stop){
		StickManAdventure.stop = stop;
	}
}
