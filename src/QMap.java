import java.awt.*;
import java.io.File;
import java.util.*;

import javax.swing.ImageIcon;

public class QMap {
	
	public int [][] maze = {    { 0, 0, 0, 0, 0 },
								{ 1, 0, 1, 0, 0 },
								{ 1, 1, 1, 0, 0 },
								{ 0, 1, 1, 1, 1 },
								{ 0, 0, 0, 0, 0 }
					};

	public int n = maze.length;
	
	public int [] availableStates = new int[9]; // nu are dimensiune pentru ca are 9 elemente = 9 stari cu 1
	public int stateNr = 0;
	
	public int [][] actions = new int [9][9];
	
	public int finalStateI = 3;
	public int finalStateJ = 4;
	
	private Scanner m;
	private String Map[] = new String[5];
	private Image grass, wall, finish;
	
	public QMap(){
		
		ImageIcon img = new ImageIcon("D://Licenta//QMaze//utils//grass.png");
		grass = img.getImage();
		
		img = new ImageIcon("D://Licenta//QMaze//utils//wall.png");
		wall = img.getImage();
		
		img = new ImageIcon("D://Licenta//QMaze//utils//finish.png");
		finish = img.getImage();
		
		openFile();
		readFile();
		closeFile();
		initialize();
	}
	
	public Image getGrass(){
		return grass;
	}
	
	public Image getFinish(){
		return finish;
	}
	
	public Image getWall(){
		return wall;
	}
	
	
	public String getMap(int x, int y){
		String index = Map[y].substring(x, x + 1);
		return index;
	}
	
	public void openFile(){
		try{
			m = new Scanner(new File("D://Licenta//QMaze//utils//Map.txt"));
		}catch(Exception e){
			System.out.println("ERROR loading map");
		}
	}
	

	public void closeFile(){
		m.close();
	}
	
	public void readFile(){
		int i = 0; 
		int j = 0;
		while(m.hasNext()){
			for(int k = 0; k < 5; k++){
				String character = m.next(); //
				int result = Integer.parseInt(character);
				maze[i][j] = result;
			}
		}
	}
	
	public void initialize(){
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				actions[i][j] = -1;
			}	
		}
		
		for(int i = 1; i < n; i++){
			for(int j = 0; j < n; j++){
				
				int column = 0;
					
				if(maze[i][j] == 1) { //daca starea e disponibila, vreau sa retin toate conexiunile ei				
					
					availableStates[stateNr] = i*10+j;
					
					//prima linie
					if(i == 1){ 
							
						// coltul stanga sus
						if(j == 0){ 
								
							if (maze[i][j+1] == 1){
								actions[stateNr][column] = (i*10) + (j+1);
								column++;
							}
								
							if(maze[i+1][j] == 1){
								actions[stateNr][column] = (i+1)*10 + j;
								column++;
							}
						}else{
								//coltul dreapta sus
								if(j == n-1){ 
									if (maze[i][j-1] == 1){
										actions[stateNr][column] = (i*10) + (j-1);
										column++;
									}
										
									if(maze[i+1][j] == 1){
										actions[stateNr][column] = (i+1)*10 + j;
										column++;
									}
									
								}else{ //doar pe prima linie, fara sa fie pe colturi
									
									if (maze[i][j-1] == 1){ //starea din stanga
										actions[stateNr][column] = (i*10) + (j-1);
										column++;
									}
										
									if(maze[i][j+1] == 1){ //starea din dreapta
										actions[stateNr][column] = (i)*10 + j+1;
										column++;
									}
										
									if (maze[i+1][j] == 1){ //starea de jos
										actions[stateNr][column] = (i+1)*10 + j;
										column++;
									}
								}
							}
						}
						
						
						//ultima linie
						if( i == n-1 ){ 
							
							//coltul stanga jos
							if( j == 0){
								if (maze[i][j+1] == 1){
									actions[stateNr][column] = (i*10) + (j+1);
									column++;
								}
								
								if(maze[i-1][j] == 1){
									actions[stateNr][column] = (i-1)*10 + j;
									column++;
								}
							}else{
								// coltul dreapta jos
								if( j == n-1 ){  
									if (maze[i][j-1] == 1){
										actions[stateNr][column] = (i*10) + (j-1);
										column++;
									}
									
									if(maze[i-1][j] == 1){
										actions[stateNr][column] = (i-1)*10 + j;
										column++;
									}
								}else{
									// pe utlima linie dar nu pe colt
									if (maze[i][j-1] == 1){ //starea din stanga
										actions[stateNr][column] = (i*10) + (j-1);
										column++;
									}
									
									if(maze[i][j+1] == 1){ //starea din dreapta
										actions[stateNr][column] = (i)*10 + j+1;
										column++;
									}
									
									if (maze[i-1][j] == 1){ //starea de sus
										actions[stateNr][column] = (i-1)*10 + j;
										column++;
									}
								}
							}
							
							
						}					
						
						if( j == 0 && i != 1 ){ // prima coloana
							if(maze[i][j+1] == 1){
								actions[stateNr][column] = i*10 + (j+1);
								column++;
							}
							
							if(maze[i-1][j] == 1){
								actions[stateNr][column] = (i-1)*10 + j;
								column++;
							}
							
							if(maze[i+1][j] == 1){
								actions[stateNr][column] = (i+1)*10 + j;
								column++;
							}
						}
						
						
						if(j == n-1 && i != 1 && i != n-1){// ultima coloana
							
							if(maze[i][j-1] == 1){ //starea din stanga
								actions[stateNr][column] = i*10 + (j-1);
								column++;
							}
							
							if(maze[i-1][j] == 1){
								actions[stateNr][column] = (i-1)*10 + j;
								column++;
							}
							
							if(maze[i+1][j] == 1){
								actions[stateNr][column] = (i+1)*10 + j;
								column++;
							}
						}
						
						if( i != 1 && j != 0 && i != n-1 && j != n-1){ // sunt in interiorul matricii
							
							if(maze[i-1][j] == 1){ //sus
								actions[stateNr][column] = (i-1)*10 + j;
								column++;
							}
							
							if(maze[i+1][j] == 1){ //jos 
								actions[stateNr][column] = (i+1)*10 + j;
								column++;
							}
							
							if(maze[i][j-1] == 1){ //stanga
								actions[stateNr][column] = i*10 + (j-1);
								column++;
							}
							
							if(maze[i][j+1] == 1){ //dreapta
								actions[stateNr][column] = i*10 + (j+1);
								column++;
							}
						}
						
		
					if( i == finalStateI && j == finalStateJ){
						actions[stateNr][column-1]= finalStateI * 10 + finalStateJ;
					}
					stateNr++;
				}
				
			}
			
		}
		
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(actions[i][j] != -1){
					int stateNumber = 0;
					while(availableStates[stateNumber] != actions[i][j]){
						stateNumber++;
					}
					actions[i][j] = stateNumber;
				}
			}
		}
		
	}
	
	public void afiseaza(){
		System.out.println("actions:");
		for(int i = 0; i < actions.length; i++){
			for(int j = 0; j < actions.length; j ++){
				if(actions[i][j] != -1)
				System.out.print(actions[i][j]+" ");
			}
		System.out.println();
		}
		
		System.out.println();
		
		System.out.println("availableStates:");
		for(int j = 0; j < availableStates.length; j ++){
			System.out.print(availableStates[j]+" ");
		}
		System.out.println();
	}
	

}
