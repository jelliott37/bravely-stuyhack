import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
public class Player extends Entity{
    private Tool weapon;
    private boolean waiting = false;
    private LinkedList<Character> storedActions = new LinkedList<Character>();
    private Spell[] spells = new Spell[3];
    private Random rand = new Random();
    private Scanner in = new Scanner(System.in);
    private int[] inventory = new int[4];
    public Player(char c, int h, int m, String n, int a, int d, int x, int y, Tool w){
	super(c, h, m, n, a, d, x, y);
	weapon = w;
	spells[0] = new Spell("Flame Burst", 25, 'f', 10);
	spells[1] = new Spell("Basic Beam", 10, 'b', 25);
	spells[2] = new Spell("Energy Burst", 5, 'e', 50);
    }
    public Tool getWeapon(){
	return weapon;
    }
    public void setWeapon(Tool w){
	weapon = w;
    }
    public Spell[] getSpells(){
	return spells;
    }
    public void setSpells(int i, Spell s){
	spells[i]=s;
    }
    public void addInv(int i){
	inventory[i]++;
    }
    public String toString(){
	return this.getName() + ":\nHealth: " + this.getHealth() + "/" + this.getMaxHealth() + "\nMana: " + this.getMana() + "/" + this.getMaxMana() + "\nAttack: " + this.getAttack() + "\nDefense: " + this.getDefense() + "\nWeapon: " + weapon;
    }
    public void storeMoves(){
	System.out.println("What action do you want to store?");
	char c = '.';
	try{
	    c = in.nextLine().charAt(0);
	}
	catch (StringIndexOutOfBoundsException e){
	    System.out.println("Invalid entry. Action Storage Terminated.");
	}
	storedActions.add(c);
	if(c == 's' || c == 'S'){
	    System.out.println("What spell do you want to store?\n");
	    listSpells();
	    try{
		c = in.nextLine().charAt(0);
	    }
	    catch (StringIndexOutOfBoundsException e){
		c = spells[0].getSymbol();
	    }   
	    storedActions.add(c);
	} else if (c == ' '){
	    System.out.println("Which direction do you want to attack in?");
	    try{
		c = in.nextLine().charAt(0);
	    }
	    catch (StringIndexOutOfBoundsException e){
		c = 'w';
	    }   
	    storedActions.add(c);
	} else if (c == 'i' || c == 'I' || c == 'o' || c == 'O' || c == 'P' || c == 'p'){
	    System.out.println("You can't preemptively use special actions, sorry.");
	    
	}
    }
    public void releaseStoredMoves(World w){
	while(storedActions.size() > 0){
	    char c = storedActions.remove();
	    if(c == 's' || c == 'S'){
		cast(w,storedActions.remove());
	    } else if (c == ' '){
		w.attackDirection(storedActions.remove());
	    } else {
		w.commandHandle(c);
	    }
	}
	   
    }
    public void inventoryHandler(){
	System.out.println("You have " + inventory[0] + " red potions, " + inventory[1] + " green potions, " + inventory[2] + " blue potions, " + inventory[3] + " purple potions. Which would you like to use? (r,g,b,p)");
	char c = ' ';
	while(c == ' '){
	    try{
		 c = in.nextLine().charAt(0);
	    } catch(StringIndexOutOfBoundsException e){
		System.out.println("could you at lease try to follow instructions");
	    }
	}
	String ret = "";
	switch (c){
	    case 'r':
		if(inventory[0]>0){
		    this.setHealth(this.getMaxHealth());
		    ret+= "You feel rejuvinated";
		    inventory[0]--;
		}
		break;
		
	    case 'g':
		if(inventory[1]>0){
		    this.setMana(this.getMaxMana());
		    ret += "You feel the world come back into focus";
		    inventory[1]--;
		}
		break;
	    case 'b':
		if(inventory[2]>0){
		    this.setMaxHealth(this.getMaxHealth()+50);
		    this.setHealth(this.getMaxHealth());
		    ret += "You feel better than you've ever felt";
		    inventory[2]--;
		}
		break;
	    case 'p':
		if(inventory[3]>0){
		    this.setMaxMana(this.getMaxMana() + 50);
		    this.setMana(this.getMaxMana());
		    ret += "The world starts glowing with untapped power";
		    inventory[3]--;
		}
		break;
	    }
	System.out.println(ret);
	in.nextLine();
    }
    public void listSpells(){
	String ret = "";
	for (Spell s: spells){
	    ret += s.getName() + " (" + s.getSymbol() + "), ";
	}
	System.out.println("Heal (h), " + ret.substring(0, ret.length() - 2));
    }
    public void cast(World w, char c){
	if(c == 'h' || c == 'H'){
	    int ph = this.getMaxHealth() - this.getHealth();
	    this.setHealth( this.getHealth() + ph / 2);
	    this.setMana(this.getMana() - ph / 4);
	    System.out.println("You healed yourself for " + (ph / 2) + " damage." );
	} else if (c == spells[0].getSymbol()){
	    w.clocal(spells[0]);
	    this.setMana(this.getMana() - spells[0].getCost());
	} else if (c == spells[1].getSymbol()){
	    w.clong(spells[1]);
	    this.setMana(this.getMana() - spells[2].getCost());
	} else if (c == spells[2].getSymbol()){
	    w.ccomplete(spells[2]);
	    this.setMana(this.getMana() - spells[2].getCost());
	} else {
	    int ph = rand.nextInt(this.getMaxMana()/10) + this.getMaxMana()/8;
	    this.setMana(this.getMana() - ph);
	    System.out.println("Your spell fizzled and died. You lost " + ph + " mana");
	}
    } 
    public void attack(World w, Entity e){
	int attack = this.getAttack();
	int ph = attack;
	attack += weapon.getDam();
	if(weapon.getHoly()){
	    attack *= 2;
	}
	this.setAttack(attack);
	w.damcalc(this, e);
	this.setAttack(ph);
    }
    
}
