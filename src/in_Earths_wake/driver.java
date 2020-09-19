package in_Earths_wake;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/////////////////////////////////////////////////////////////////////GAME CLASSES\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
class item{
	private String name;
	private String type;
	private double attackDamage;
	private double defensePoints;
	private Integer id;
	
	public item(String Name,String Type,double a,double d,Integer i) {
		this.name = Name;
		this.type = Type;
		this.attackDamage = a;
		this.defensePoints = d;
		this.id = i;
	} 
	
	@Override public String toString() {
		return this.name;
	} 
}
class enemy{
	public String name;
	public Integer level;
	public Integer type;
	public double armor;
	public double damage;
	public double health;
	
	public enemy(String n,Integer l,Integer t,double a,double d,double h) {
		this.name = n;
		this.level = l;
		this.type = t ;
		this.armor =a;
		this.damage = d;
		this.health = h;
	}
}

class gameCharacter{
	public String name;
	public String Description;
	public Integer maxInventory;
	public Integer currentInventory;
	public Integer XP;
	public int Profession;
	public double Intelligence;
	public double Strength;
	public double health;
	public double maxArmor;
	public double Charisma;
	public double Luck;
	public boolean Mutuant;
	
	
	public gameCharacter(String n, String d,Integer mi,Integer p,double i,double s,double h,double ma,double c,double l,boolean m) {
		this.name = n;
		this.Description = d;
		this.maxInventory = mi;
		this.currentInventory = 0;
		this.XP = 0;
		this.Profession = p;
		this.Intelligence = i;
		this.Strength = s;
		this.health = h;
		this.maxArmor = ma;
		this.Charisma = c;
		this.Mutuant = m;
		this.Luck = l;
	}
	@Override public String toString() {
		return name+": "+this.name+"\n"+this.Description+"\nStrength: "+String.valueOf(this.Strength)
		+"\nHealth: "+String.valueOf(this.health)+"\nIntelligence: "+String.valueOf(this.Intelligence)+
		"\nCharisma: "+String.valueOf(this.Charisma)+"\nLuck: "+String.valueOf(this.Luck);
	} 
}
public class driver {
	/* Rooms Dictionary (SO FAR, TENNATIVE)
	* 0  	-----> 		Knight Intro scene
	* 1  	-----> 		Towers Scene
	* 2  	-----> 		Citadel Scene
	* 3  	-----> 		Rebel Base Scene
	* 4  	-----> 		Mercenary Intro Scene
	* 5  	-----> 		Alchemist Intro Scene
	* */
	static String  gDesc = "Compassionate \r\n" + 
			"Gregarious \r\n" + 
			"Optimistic and somewhat naive \r\n" + 
			"Remarkably intelligent and beautiful \r\n" + 
			"Timid emotional exterior yet exceptionally brave when she needs to be \r\n" + 
			"";
	static Integer compassDegree;
	static Scanner userInput = new Scanner(System.in);
	static String[] rooms = {"",};
	static enemy[] enemies = {};
	static String input = "";
	static Random rand = new Random();
	static Integer coins = 0;
	static Integer subRoom = 0;
	static Vector inventory = new Vector();
	static Vector savedInventory = new Vector();
	static Integer savedRoom;
	static Integer savedCoin;
	static Integer savedSubRoom;
	static gameCharacter savedChosenCharacter;
	static gameCharacter chosenCharacter;
	static gameCharacter theKnight;
	static gameCharacter theGirl = new gameCharacter("Mivian",gDesc,60,2,0.8,0.6,100.0,100.0,0.9,0.4,false);
	static gameCharacter theMercenary;
	static gameCharacter theAlchemist;
	static gameCharacter[] characters= {theKnight,theGirl,theMercenary,theAlchemist};
	static Instant start = Instant.now();

	static void invalidInput() {//when the user input is not an an available option
		print("That is not a verb I recognize");
		getInput();
	}
	static void printdialogue(String d,int t) {
		System.out.println(d);
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void getInput() {//get user input and check for general game commands
		System.out.print("> ");
		input = userInput.nextLine().toLowerCase();
		if (input.contains("help")) {
			print("Commands to use\n" + "Hint		--->	Show possible hints\n"
					+ "Look		--->	Describes what your character sees\n"
					+ "Inventory	--->	Lists your character's inventory\n"
					+ "Save		--->	Saves your current game state\n"
					+ "Restore		--->	Restores your previous save\n"
					+ "Diagonse	--->	List any injury your charachter has\n"
					+ "Time		---> 	Tells you how long you have been playing.\n"
					+ "Score		--->	Prints your current score\n");
		}
		if (input.contains("time")) {

		} else if (input.contains("hint")) {

		} else if (input.contains("look")) {

		} else if (input.contains("inventory")) {
			print("Your inventory");
			for (int i = 0; i < inventory.size(); i++) {
				System.out.println(inventory.elementAt(i));
			}

		} else if (input.contains("save")) {

		} else if (input.contains("restore")) {

		} else if (input.contains("diagnose")) {

		} else if (input.contains("score")) {

		}
	}

	static void print(String i) {///general purpose print function instead of sys.out.println
		System.out.println(i);
	}

	static Boolean welcomeScreen() {
		System.out.println(
				"============================================================================================================");
		System.out.println("					     WELCOME!!");
		System.out.println("				to (whatever the game name is), the action adventure game");
		System.out.println("		press C to to select your character and begin your adventure");
		System.out.println("					 press q to Quit");
		System.out.println(
				"============================================================================================================");
		getInput();
		if (input.equals("q")) { 
			return false;
		} else if (input.equals("c")) {
			print("============================================================================================================");
			for (int i = 0; i < characters.length; i++) {
				System.out.println(characters[i]);
			}
			print("============================================================================================================");
			print("Pick a character from 1-4: ");
			Integer ch = userInput.nextInt();
			if (ch > 0 && ch < 5) {
				chosenCharacter = characters[ch - 1];
			} else {
				print("error");
			}

		} else {
			print("error");
		}
		userInput.nextLine();
		print("Greetings, " + chosenCharacter.name + "\nare you ready to start a new game?(y/n):");
		System.out.print(">");
		String play = userInput.nextLine().toLowerCase();
		while (play.contains("y") == false && play.contains("n") == false) {
			print("Error\\nare you ready to start a new game?(y/n):");
			play = userInput.nextLine().toLowerCase();
		}
		if (play.contains("y")) {
			return true;
		}
		return false;

	}
	
	public static Integer handleKnightIntro(){/////////////////////////Knight Intro Scene(Amr)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		return 0;
	}
	public static Integer handleTowersScene(){/////////////////////////Tower Scene(Amr)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		return 0;
	}
	public static Integer handleCitadel() throws InterruptedException{/////////////////////////Girl Intro Scene(Karim)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		switch(subRoom) {
		case 0:
			boolean inRoom =true;
			boolean keyCollected = false;
			boolean weaponCollected = false;
			boolean atDresser=false;
			boolean atCloset=false;
			boolean letterRead=false;
			boolean outfitWorn=false;
			String letterTxt ="hello ";
			printdialogue("*Alarm rings*",1000);
			printdialogue("Riva: Madam Ambassador the congress session will start soon.",800);
			printdialogue("Mivian: Thank you Riva, please make sure the guards are busy.",800);
			print("Riva goes to the entrence of the ambassador's quarters\nThe Ambassador's bedroom/nYou are on the bed, to the right you see a dresser"
					+ "\nTo the left you see the closet\n(go right or go left).");
			getInput();
			while(inRoom) {
			if (input.contains("go right")) {
				atDresser =true;
				while(atDresser) {
				print("You are sitting infront of the dresser, infront of you is a letter, on the right you see a locked drawer"
						+ "\n(read letter/open drawer/go left/get ready)");
				getInput();
				if (input.contains("open drawer")) {
					if(weaponCollected) {print("empty.\n(get ready/read letter/go left)");getInput();}else {
					String keyMsg =(keyCollected)? "i can use the key to unlock this\n(unlock drawer)":"the drawer is locked, need to find the key(go left/read letter)";
					print(keyMsg);
					getInput();
					if(input.contains("unlock")) {
						if(keyCollected) {
							String keyAction = letterRead ?")":"/read letter)";
							print("you see your weapon of choice the staff of Yotraci\n(take staff/go left"+keyAction);
						}else {
							String keyAction = letterRead ?")":"/read letter)";
							print("need to find a key.\n(go left"+keyAction);
						}
						getInput();
						if (input.contains("take staff")&&keyCollected == true) {
							item staff = new item("The Staff of Yotraci","Weapon",35.0,75.0,8);
							inventory.add(staff);
							weaponCollected=true;
						}
						else if (input.contains("take staff")&&keyCollected == false) {
							invalidInput();
						}
					}}
					}
				if(input.contains("read letter")) { 
					print(letterTxt);
				}
				else if (input.contains("go left")) {
					atCloset = true;
					atDresser = false;
					break;
				}else if (input.contains("get ready")) {
					//if()
				}
				else {invalidInput();}
				}
				
				}
				if (input.contains("go left")) {
					atCloset = true;
					while(atCloset) {
						String closetMsg = outfitWorn? "a key is on the floor(pick up key/go right)":"you see the outfit for today(wear outfit/go right)";
						print("you are at the closet, "+closetMsg);
						getInput();
						if (input.contains("wear outfit")) {
							outfitWorn=true;
							print("you put on your dress\nyou hear a noise\n you look down it appears a key was in the pockets and was dropped on the floor");
							print("(take key/go right)");
							getInput();
						}
						if(input.contains("go right")) {
							atDresser = true;
							atCloset = false;
							break;
						}else if(input.contains("pick up key")&&outfitWorn==true) {
							print("a key was added to your inventory");
							keyCollected = true;
							item key = new item("drawer key","key",0.1,0.1,9);
							inventory.add(key);
						}else {
							invalidInput();
						}
					}
			}
			}
			
			
			
			break;
			default:
				
		}
		return 0;
	}
	public static Integer handleRebelBase(){/////////////////////////Rebel Base Scene(Karim)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		return 0;
	}
	public static Integer handleMercenaryIntro() {//////////////////Mercenary Intro Scene(Yazan)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		return 0;
	}
	public static Integer handleAlchemistIntro(){/////////////////////////Alchemist Intro Scene(Yazan)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		return 0;
	}
	
	public static void battleEnemy() {///function for when the user is in a boss battle (1v1)
		/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		 * Fighting mechanics:-
		 * 	- print enemy is swinging his weapon 
		 * 	- The player has anywhere between [1 to 5 seconds] to turn phone into landscape mode (can incorporate gyro or magnetometer)
		 * 	- Turning the phone successfully means the player parried/blocked the attack and taking 5% of the damage
		 *  - Without sensors the user will have to type "parry" or "block" within the time window
		 *  - certain characters will have extended attack time
		 *  - Once the enemy attack is done attacking, the user will be provided with the enemy's N/S/W/E direction,health and player health
		 *  - the user has between [0 to 3] seconds to swing the phone/aim phone or type attach/swing/shoot
		 *  - swing will incorporate the acceloroometer/gyro, aim (for guns) will incorporate the compass sensor
		 *  - the value of the attack will be proportional to how close the time of the attack is to 1 second
		 *  - certain characters will have extended attack time.
		 *  - repeat until enemy or user is killed.
		 *  ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		 * */
	}
	
	public static void main(String[] args) {
	if(welcomeScreen()) {
	Integer roomNo = 2;
	boolean quit = false;
	while(quit == false) {
	switch(roomNo) 
	{
	case 0: roomNo = handleKnightIntro();
	break;
	case 1: roomNo = handleTowersScene();
	break;
	case 2: try {
			roomNo = handleCitadel();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	break;
	case 3: roomNo = handleRebelBase();
	break;
	case 4: roomNo = handleMercenaryIntro();
	break;
	case 5: roomNo = handleAlchemistIntro();
	break;	
	default:
	}
	}
	
	}else {
		print("Thank you for playing our game <3");
	}
	}


}
