import javax.swing.JFrame;

public class QMaze {
	public static void main(String[] args){
		new QMaze();
		
	}
	
	public QMaze(){
		JFrame f = new JFrame();
		f.setTitle("QMaze");
		f.add(new QBoard());
		f.setSize(176,199);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
