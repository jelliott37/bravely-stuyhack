import java.util.Random;
public class World{
    //handles the 2d array that is the gamespace
    private char[][] map;
    private int sideLength;
    private Player pc;
    private Monster[] mobs;
    private int level;
    private Random rand = new Random();
    public World(int n, Player pci, int lvl){ //precondition: n is odd
	map = new char[n][n];
	sideLength = n;
	pc=pci;
	level = lvl;
    }
    public int getSL(){
	return sideLength;
    }
    public void generate(){ //creates map
	generateBorders();
	int ps = rand.nextInt(20)-level;
	if (sideLength < 11){
	    preset3();
	    generateChests(sideLength); 
	} else if(ps < 0){
	    generateBoss();
	} else if(ps % 4 == 0){
	    preset1();
	} else if(ps % 4 == 1){
	    preset2();
	} else if(ps % 4 == 2){
	    preset3();
	}
	if(ps >= 0){
	    generateChests(sideLength/4);
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
    public void generateChests(int n){
	int counter = n;
	for (int r = 0; r < sideLength; r++){
	    for (int c = 0; c < sideLength; c++){
		if(map[r][c] != 'X'){
		    if(counter >= rand.nextInt(sideLength)){
			map[r][c] = 'C';
		    } 
		}
	    }
	}
    }
    public void generateMobs(){
	int spawncap = 3;
	for (int r = 0; r < sideLength; r++){
	    for (int c = 0; c < sideLength; c++){	
		if((map[r][c] != 'X') && (map[r][c] != 'C')){
		    while(spawncap > 0){
			//	map[rand.nextInt(sideLength)][rand.nextInt(sideLength)] = 'M';
			spawncap--;
		    }
		}
	    }
	}
    }
    public void move(Entity e, int x, int y){
	if(map[x][y] == ' '){
	    map[e.getXCor()][e.getYCor()] = ' ';
	    e.setXCor(x);
	    e.setYCor(y);
	    map[x][y]=e.getSymbol();
	}
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
	return s+pc.toString();
    }
    public void wait(int n){
	try{
	    Thread.sleep(n);
	} catch (Exception e){

	}

    }
    public String commands(){
	return "Commands: \nMovement: N(w), NW(q), W(a), SW(z), S(x), SE(c), E(d), NE(e) \nInventory(u), Attack(space), Spells(s)\n Wait(i), Store Commands(o), Release Stored Time(p)";
    }
    public void commandHandle(char c){
	if(c == 'w' || c == 'W'){
	    
	}
    }
}
