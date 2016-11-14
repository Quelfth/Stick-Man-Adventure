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
	static int xOffset = 0;
	static int yOffset = 0;
	static int stage = 0;
	static boolean lastWCheck = false;

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
		int frameWidth = Integer.parseInt(FrameWidth);
		int frameHeight = Integer.parseInt(FrameHeight);
		frame.setSize(frameWidth, frameHeight);
		StickMan p1 = new StickMan(96, 0, frame);
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
		Zone door10 = new Zone(200, 1050, 400, 650, 3, -1);
		Level start = new Level(p1);
		start.add(door1);
		start.add(zoneA);
		start.add(zoneB);
		start.add(zoneC);
		start.add(zoneD);
		start.add(zoneE);
		start.add(zoneF);
		start.add(zoneG);
		Level lava0 = new Level(p1);
		lava0.add(door2);
		lava0.add(zoneH);
		lava0.add(zoneI);
		lava0.add(zoneJ);
		Level lava1 = new Level(p1);
		lava1.add(door2);
		lava1.add(zoneH);
		lava1.add(zoneI);
		lava1.add(zoneJ);
		lava1.add(zoneK);
		Level lava2 = new Level(p1);
		lava2.add(door2);
		lava2.add(zoneH);
		lava2.add(zoneI);
		lava2.add(zoneJ);
		lava2.add(zoneL);
		Level cliff0 = new Level(p1);
		cliff0.add(new Zone(0, 1050, 300, 300, 1, 0));
		Level last = new Level(p1);
		last.add(door10);
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
				switch (stage) {
				case 0:
					start.paint(g);
					break;
				case 1:
					lava0.paint(g);
					break;
				case 2:
					lava1.paint(g);
					break;
				case 3:
					lava2.paint(g);
					break;
				case 4:
					cliff0.paint(g);
					break;
				default:
					last.paint(g);
					break;
				}
				g.setColor(Color.RED);
				if (p1.hp >= 0)
					g.drawString(p1.hp + "", 1400, 200);
				else
					g.drawString("0", 1400, 200);
				if (p1.fn3)
					p1.debugLines(g);
			}
		};
		frame.setVisible(true);
		frame.add(panel);
		panel.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boolean lastSpaceCheck = false;

		p1.setStats(20, 30, 0);
		Zone.setP1(p1);
		while (true) {

			frame.pack();
			p1.frame = frame;
			if (p1.vkgrave) {
				if (p1.codePressed != KeyEvent.VK_BACK_QUOTE == false) {
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
				p1.setStats(speed, jump, wallJump, doubleJumps);
				String Stage = JOptionPane.showInputDialog("Stage:");
				try {
					stage = Integer.parseInt(Stage);
				} catch (NumberFormatException e) {
				}
				p1.vkgrave = false;
			}
			
			if (p1.vkd) {
				p1.walk(true, true);
			} else {
				p1.walk(false, true);
			}
			if (p1.vka) {
				p1.walk(true, false);
			} else {
				p1.walk(false, false);
			}

			if (p1.vkspace) {
				if (lastSpaceCheck == false) {
					p1.jump();
				}
				lastSpaceCheck = true;
			} else {
				lastSpaceCheck = false;
			}
			p1.setFloat(p1.vkshift && !p1.vkspace);
			switch (stage) {
			case 0:
				start.update();
				break;
			case 1:
				lava0.update();
				break;
			case 2:
				lava1.update();
				break;
			case 3:
				lava2.update();
				break;
			case 4:
				cliff0.update();
				break;
			default:
				last.update();
				break;
			}
			if (p1.vkw)
				lastWCheck = true;
			else
				lastWCheck = false;
			p1.update();
			panel.repaint();
			System.out.println(stage);
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
