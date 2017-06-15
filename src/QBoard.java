import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class QBoard extends JPanel {

	private Timer timer;

	private QMap m;

	private QPlayer p;

	private boolean win = false;

	private String Message = " ";

	private Font font = new Font("Serif", Font.BOLD, 48);

	public QBoard() {

		m = new QMap();
		p = new QPlayer();
		p.move(0,1);
		//p.move(9, 9);

		setFocusable(true);
		movePlayerAuto();
		repaint();

	}

	public void paint(Graphics g) {

		super.paint(g);

		if (!win) {
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 5; x++) {

					if (m.getMap(x, y).equals("2")) {
						g.drawImage(m.getFinish(), x * 32, y * 32, null);
					}

					if (m.getMap(x, y).equals("1")) {
						g.drawImage(m.getGrass(), x * 32, y * 32, null);
					}

					if (m.getMap(x, y).equals("0")) {
						g.drawImage(m.getWall(), x * 32, y * 32, null);
					}
				}
			}
			g.drawImage(p.getPlayer(), p.getTileX() * 32, p.getTileY() * 32, null);
		}

		if (win) {
			g.setColor(Color.ORANGE);
			g.setFont(font);
			g.drawString(Message, 130, 230);
		}

	}

	public void movePlayerAuto() {
		
		QLearning2 qlearning = new QLearning2();
		//qlearning.calculate();
		//obj.afiseaza();
		
		int i = 1, j = 0;
		int k = 1;
		while (win != true && k==1) {

			if (qlearning.maze[i][j] == 1) {
				int position = i * 10 + j;
				
				int from = 0;
				
				for(int o = 0; o < 9; o++){
					System.out.print(qlearning.goTo[o]+" ");
				}
				while (position != qlearning.availableStates[from]) {
					from++;
				}

				int toState = qlearning.goTo[from];
				int toPosition = qlearning.availableStates[toState];

				int jToPosition = toPosition % 10;
				int iToPosition = (toPosition / 10) % 10;

				if (i > iToPosition && j == jToPosition) {
					p.move(0, -1);
					repaint();
					i = iToPosition;
					j = jToPosition;
				}

				if (i < iToPosition && j == jToPosition) {
					p.move(0, 1);
					repaint();
					i = iToPosition;
					j = jToPosition;
				}

				if (i == iToPosition && j > jToPosition) {
					p.move(-1, 0);
					repaint();
					i = iToPosition;
					j = jToPosition;
				}

				if (i == iToPosition && j < jToPosition) {
					p.move(1, 0);
					repaint();
					i = iToPosition;
					j = jToPosition;
				}
			}

			if (qlearning.maze[i][j] == 2) {
				win = true;
			}
			k=2;
		}
	}
}
