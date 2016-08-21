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
		Zone zoneK = new Zone(650, 1050, 850, 1300, -1, 1);
		Zone door1 = new Zone(1000, 500, 1200, 100, 3, 0);
		Zone door2 = new Zone(1300, 1000, 1500, 600, 3, 0);
		Zone door10 = new Zone(200, 1050, 400, 650, 3, -1);
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
					door1.paint(g);
					p1.paint(g);
					zoneA.paint(g);
					zoneB.paint(g);
					zoneC.paint(g);
					zoneD.paint(g);
					zoneE.paint(g);
					zoneF.paint(g);
					zoneG.paint(g);
					break;
				case 1:
					door2.paint(g);
					p1.paint(g);
					zoneH.paint(g);
					zoneI.paint(g);
					zoneJ.paint(g);
					break;
				case 2:
					door2.paint(g);
					p1.paint(g);
					zoneH.paint(g);
					zoneI.paint(g);
					zoneJ.paint(g);
					break;
				default:
					door10.paint(g);
					p1.paint(g);
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
				zoneA.update();
				zoneB.update();
				zoneC.update();
				zoneD.update();
				zoneE.update();
				zoneF.update();
				zoneG.update();
				door1.update();
				break;
			case 1:
				zoneH.update();
				zoneI.update();
				zoneJ.update();
				door2.update();
				break;
			case 2:
				zoneH.update();
				zoneI.update();
				zoneJ.update();
				door2.update();
				break;
			default:
				door10.update();
				break;
			}
			if (p1.vkw)
				lastWCheck = true;
			else
				lastWCheck = false;
			p1.update();
			panel.repaint();
			System.out.println(lastWCheck);
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
