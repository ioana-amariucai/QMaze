import java.awt.Image;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
 

public class QLearning2{
	
	final DecimalFormat df = new DecimalFormat("#.##");
	 
    // path finding
    final double alpha = 0.1;
    final double gamma = 0.9;
    public int[] goTo = new int[9];
    
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
	
	int[][] R = new int[9][9];
	double[][] Q = new double[9][9];
	
	public void calculate(){
	
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
	
	public void actionsFromState(int fromState, int[] actionsFromState){
		for(int toState = 0 ; toState < 9; toState++){
				actionsFromState[toState] = actions[fromState][toState];
		}
	}
	
	public int actionsCounter(int[] actionsFromState){
		int counter = 0;
		int i = 0;
		
		while(actionsFromState[i] != -1){
			counter++;
			i++;
		}
		
		return counter;
	}
	
	QLearning2(){
		init();
	}
	
	public void init() {   
	    R[7][8] = 100;
	    calculate();
		run();
		printResult();
		showPolicy();
	 }
	
	void run(){
		 Random rand = new Random();
		 
		 for (int i = 0; i < 1000; i++) {
			 
			 int state = rand.nextInt(9);
			 
			 while( state != 8){
				 int[] actionsFromState = new int[9];
				 actionsFromState(state, actionsFromState);
				 
				 int actionsCounter = actionsCounter(actionsFromState);
				 
				 int index = rand.nextInt(actionsCounter);
				 int action = actionsFromState[index];
				 int nextState = action;
				 
				 double q = getQ(state, action);
				 double maxQ = maxQ(nextState);
				 int r = getR(state, action);
				 
				 double value = q + alpha * (r + gamma * maxQ - q);
	             setQ(state, action, value);
	 
	             state = nextState;
			 }
		 }
	 }

	double maxQ(int s){
		 int[] actionsFromState = new int[9];
		 actionsFromState(s, actionsFromState);
		 int actionsCounter = actionsCounter(actionsFromState);	 
		 double maxValue = Double.MIN_VALUE;
		 
		 for(int i = 0; i < actionsCounter; i++){
			 int nextState = actionsFromState[i];
			 double value = Q[s][nextState];
			 
			 if(value > maxValue){
				 maxValue = value;
			 }
		 }
		 
		 return maxValue;
	}
	
	int policy(int state){
		int[] actionsFromState = new int[9];
		actionsFromState(state, actionsFromState);
		int actionsCounter = actionsCounter(actionsFromState);
		double maxValue = Double.MIN_VALUE;
		int policyGoToState = state;
		
		for(int i = 0; i < actionsCounter; i++){
			int nextState = actionsFromState[i];
			double value = Q[state][nextState];
			
			if(value > maxValue){
				maxValue = value;
				policyGoToState = nextState;
			}
		}
		
		return policyGoToState;
	}
	
	double getQ(int s, int a){
		return Q[s][a];
	}
	
	void setQ(int s, int a, double value){
		Q[s][a] = value;
	}

	int getR(int s, int a) {
        return R[s][a];
    }
	
	void printResult(){
		System.out.println("Print result");
        for (int i = 0; i < 9; i++) {
            System.out.print("out from state" + i + ":  ");
            for (int j = 0; j < 9; j++) {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
	}

	void showPolicy() {
        for (int i = 0; i < 9; i++) {
            int from = i;
            int to =  policy(from);
            goTo[from] = to;
            //System.out.println("from " + from+ " go to " + to);
        }           
    }
	
//	public static void main(String[] args){
//		
//		QLearning2 obj = new QLearning2();
//		obj.calculate();
//		
//		obj.run();
//		obj.printResult();
//		obj.showPolicy();
//		//obj.afiseaza();
//		}

	
}