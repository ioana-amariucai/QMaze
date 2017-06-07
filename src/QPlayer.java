import java.awt.Image;

import javax.swing.ImageIcon;

public class QPlayer {
	
	private int tileX, tileY;
	
	private Image player;
	
	public QPlayer(){ 

		ImageIcon img = new ImageIcon("D://Licenta//QMaze//utils//player.png");
		player = img.getImage();	
		
		tileX = 0;
		tileY = 1;
	}
	
	public Image getPlayer(){
		return player;
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public void move(int dx, int dy){
		tileX += dx;
		tileY += dy;
	}
}
