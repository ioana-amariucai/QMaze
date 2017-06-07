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
		p.move(2, 1);

		setFocusable(true);
		// new MovePlayer();

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

	// public class MovePlayer{
	// public void MovePlayer(){
	//
	// QLearning2 obj = new QLearning2();
	//
	// int i = 0, j =0;
	// p.move(i, j);
	//
	// while( i < 9 && j < 9 && win != true){
	//
	// if( obj.maze[i][j] == 1){
	// int position = i*10 +j;
	// int from = 0;
	//
	// while(position != obj.availableStates[from]){
	// from++;
	// }
	//
	// int toState = obj.goTo[from];
	// int toPosition = obj.availableStates[toState];
	//
	// int jToPosition = toPosition%10;
	// int iToPosition = (toPosition/10)%10;
	//
	// if( i > iToPosition && j == jToPosition){
	// p.move(0, -1);
	// i = iToPosition;
	// j = jToPosition;
	// }
	//
	// if( i < iToPosition && j == jToPosition){
	// p.move(0, 1);
	// i = iToPosition;
	// j = jToPosition;
	// }
	//
	// if( i == iToPosition && j > jToPosition){
	// p.move( -1, 0);
	// i = iToPosition;
	// j = jToPosition;
	// }
	//
	// if( i == iToPosition && j < jToPosition){
	// p.move(1, 0);
	// i = iToPosition;
	// j = jToPosition;
	// }
	// }
	//
	// if( obj.maze[i][j] == 2){
	// win = true;
	// }
	// }
	// }
	// }

}
