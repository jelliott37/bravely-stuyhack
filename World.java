import java.util.Random;
public class World{
    //handles the 2d array that is the gamespace
    private char[][] map;
    private int sideLength;
    private Player pc;
    private int level;
    private Random r = new Random();
    public World(int n, Player pco, int lvl){ //precondition: n is odd
	map = new char[n][n];
	sideLength = n;
	pc=pco;
	level = lvl;
    }
    public void generate(){ //creates map
	generateBorders();
	int ps = r.nextInt(20)-level;
	if (sideLength < 11){
	    preset3();
	} else if(ps < 0){
	    generateBoss();
	} else if(ps % 3 == 0){
	    preset1();
	} else if(ps % 3 == 1){
	    preset2();
	} else if(ps % 3 == 2){
	    preset3();
	}
	
    }
    public void generateBorders(){ //creates borders and fills with blankspace
	for(int c = 0; c < sideLength; c++){
	    for(int r = 0; r < sideLength; r++){
		if(c == 0 || c == sideLength - 1){
		    if(r != sideLength/2 - 1 && 
		       r != sideLength/2 && 
		       r != sideLength/2 + 1){
			map[r][c] = 'X';
		    }
		    else{
			map[r][c] = ' ';
		    }
		}
		else if(r == 0 || r == sideLength - 1){
		    if(c != sideLength/2 - 1 && 
		       c != sideLength/2 && 
		       c != sideLength/2 + 1){
			map[r][c] = 'X';
		    }
		    else{
			map[r][c] = ' ';
		    }
		}
		else{
		    map[r][c] = ' ';
		}
	    }
	}    
    }
    public void preset1(){
	int modifier = (sideLength - 2)/3;
	for(int c = sideLength/2 - modifier + 1; c < sideLength/2 + modifier; c++){
	    for(int r = sideLength/2 - modifier + 1; r < sideLength/2 + modifier; r++){
		if( r == sideLength/2 || c == sideLength/2){
		    map[r][c]='X';
		}
	    }
	}

    }
    public void preset2(){
	for(int c = 0; c < sideLength; c++){
	    for(int r = 0; r < sideLength; r++){
		if(Math.abs(r - sideLength/2) <= 1 || Math.abs(c - sideLength/2) <= 1){
		} else if((r + c) % 2 == 0 && r % 2 == 0){
		    map[r][c] = 'X';
		}
		
	    }
	}
    }
    public void preset3(){
	for(int c = 0; c < sideLength; c++){
	    for(int r = 0; r < sideLength; r++){
		if((Math.abs(r - sideLength/2) == 2 || 
		    Math.abs(c - sideLength/2) == 2) && 
		   !(r == 1 || c ==1 || r == sideLength/2 || c == sideLength/2 || 
		     r == sideLength - 2 || c == sideLength - 2)){
		    map[r][c] = 'X';
		}
	    }
	}
    }
    public void generateBoss(){
	int centerlow = sideLength/4;
	int centerhigh = sideLength*3/4;
	for(int c = 0; c < sideLength; c++){
	    for(int r = 0; r < sideLength; r++){
		if((Math.abs(r - centerlow) <= 1|| Math.abs(r - centerhigh) <= 1)
		   &&(Math.abs(c - centerlow)<=1||Math.abs(c - centerhigh)<= 1)){
		    map[r][c] = 'X';
		}
	    }
	}
    }
    public void generateChests(){
	
    }
    public void generateMobs(){
	
    }
    public String toString()
    {
	String s = "\033\143";
	for (int y=0; y<sideLength; y++)
	    {
		for (int x=0;x<sideLength;x++)
		    s = s + map[y][x];
		s=s+"\n";
	    }
	return s;
    }
    public void wait(int n){
	try{
	    Thread.sleep(n);
	} catch (Exception e){

	}

    }
}
