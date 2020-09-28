package in_Earths_wake;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/////////////////////////////////////////////////////////////////////GAME CLASSES\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
class item{
	String name;
	String type;
	double attackDamage;
	public double defensePoints;
	Integer id;
	
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
class Entity{
	public String name;
	public String Description;
	public Integer level;
	public double armor;
	public double damage;
	public double health;
	public boolean mutant;
	public item weapon;
	public item sheild;
	public Entity(String n, String d, Integer l,double a , double da,double h,boolean m,item w,item s) {
		this.name = n;
		this.Description = d;
		this.level = l;
		this.armor = a;
		this.damage = da;
		this.health = h;
		this.mutant = m;
		this.weapon = w;
		this.sheild = s;
	}
	public int takenDamage(item weapon,double attackerDamage,boolean defened,boolean crit) {
		double criticalHit = (crit)? 1.6:1.0;
		double playerDefended = (defened)?-(this.sheild.defensePoints):0.0;
		double totalDamageDealt = (weapon.attackDamage*criticalHit)+playerDefended+attackerDamage;
		if (this.armor>totalDamageDealt) {
			System.out.println(this.name+" has defended succesfully");
		}else {
			System.out.println("Total damage sustained:"+totalDamageDealt);
			this.health += (this.armor-totalDamageDealt);
			if(this.health<0) {
				System.out.println(this.name+" has been defeated");
				return -1;
			}else {
				System.out.println("Remaining health:"+this.health);
			}
		}
		
		//double damagePoints = weapon
		return 0;
	}
}
class enemy extends Entity{

	public String Type;
	public enemy(String n, String d, Integer l, double a, double da, double h,boolean m,String type,item w,item s) {
		super(n, d, l, a, da, h, m, w, s);
		// TODO Auto-generated constructor stub
		this.Type = type;
	}

	
	

}

class gameCharacter extends Entity{
	public Integer maxInventory;
	public Vector<item> Inventory;
	public item[] hotkey ;
	public Integer XP;
	public double Intelligence;
	public double Charisma;
	public double Luck;
	public gameCharacter(String n, String d, Integer l, double a, double da, double h,boolean m,Integer ma,double i,double c,double lu,item w,item s) {
		super(n, d, l, a, da, h,m,w,s);
		this.maxInventory = ma;
		this.Intelligence = i;
		this.Charisma =c;
		this.Luck = lu;
		this.hotkey = new item[4];
		// TODO Auto-generated constructor stub
	}
	public void gotHit(double damageTaken,boolean crit) {
		
	}
	public void addInventory(item i) {
		if(this.maxInventory==0) {
			System.out.print("*your inventory is full please try removing an item*");
		}else {
			this.Inventory.add(i);
			this.maxInventory--;
		}
	}
	public void removeFromInventory() {
		if(this.Inventory.size()==0) {
			System.out.println("*Your inventory is empty*");
		}else {
			for(int i=0;i<Inventory.size();i++) {
				System.out.println(i+"- "+this.Inventory.elementAt(i));
			}
			System.out.println("Please select which item you would like to remove:");
			Scanner Input = new Scanner(System.in);
			Integer i = Input.nextInt();
			while (i<0||i>this.Inventory.capacity()) {
				System.out.println("Please select which item you would like to remove:");
				i = Input.nextInt();
			}
			System.out.println("*item:"+this.Inventory.elementAt(i)+" has been removed from your inventory*");
			this.Inventory.removeElementAt(i);
			
		}
	}
	@Override public String toString() {
		return name+": "+this.name+"\n"+this.Description+"\nDamage boost: "+String.valueOf(this.damage)
		+"\nHealth: "+String.valueOf(this.health)+"\nIntelligence: "+String.valueOf(this.Intelligence)+
		"\nCharisma: "+String.valueOf(this.Charisma)+"\nLuck: "+String.valueOf(this.Luck);
	} 
}
public class driver {
	private static class combat {
        public String combatInput;
        public boolean blocked;
        public boolean attacked;
    }
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
	static Vector<item> inventory = new Vector();
	static Vector<item> savedInventory = new Vector();
	static Integer savedRoom;
	static Integer savedCoin;
	static Integer savedSubRoom;
	static gameCharacter savedChosenCharacter;
	static gameCharacter chosenCharacter;
	static gameCharacter theKnight;
	static gameCharacter theGirl = new gameCharacter("Mivian",gDesc,0, 60.0, 0.4,100.0,false,30,0.9,0.8,0.5,null,null); //new gameCharacter("Mivian",gDesc,60,2,0.8,0.6,100.0,100.0,0.9,0.4,false);
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
		System.out.println("				to Earth's Wake, the action adventure game");
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
	
//	public static Integer handleKnightIntro(){/////////////////////////Knight Intro Scene(Amr)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//		boolean croom = true, innercitystreets = false, meetinghall = false, nightclub = false,readytoleave=false;
//		int direblade,photongun,chaindagger,blackarmour,soularrow, intro = 0, decision = 0;
//		print("You open your eyes having enjoyed a fleeting moment of contemplation\nThe local clergy have long advised you ");
//		print("that ample meditation and serene environments may alleviate your cognitive wounds, yet such luxuries grow harder and harder to find\n");
//		print("lying in your private chambers, you prepare for your next assignment\n");
//		getInput();
//		while(croom)
//		{	getInput();
//			if (input.contains("look"))
//			{
//				print("your private quarters retain the same grim gothic feel that most deinan architecture projects, yet you suppose it is more welcoming");
//				print("than the hovels most lesser men are consigned to, in truth you had long grown to prefer it over more ornate or vibrant environments as ");
//                print("the austere setting helps maintain your discipline and focus\n the walls of your quarters are lined with countless accolades");
//                print("bearing testament to your many feats throughout the harrowing wars of restitution\ntowards the corner of your room several books");
//                print("lay loosely piled on top of your study, at your feet your sacred knightly armaments lay neatly arrayed ready to bring ");
//                print("the divine wrath of the deinans upon your enemies");
//            }
//			if (input.contains("look at accolades"))
//			{
//				print("you look upon the many trophies, grandiose sigils, and gilded figures that adorn your wall, you get the sense that they ");
//				print("should fill you with pride, yet you struggle to feel much beyond a modicum of disdain for the pretence of honour and glory they present as you remain fully aware of the circumstances");
//				print("that earned you them\nFor every true test of arms you faced there were a dozen brutal massacres, for every worthy adversary you slew there were a hundred hapless laymen barely able to");
//				print("weild a gun or sword screaming in fear as you scythed though them\such sordid work hardly invokes the images of heroism and valour etched before you and you often though that celebrating them");
//				print("was unbecoming of a veteran knight\n");
//			}
//			if (input.contains("look at books"))
//			{
//				print("you examine the pile of books that has accumulated on your study, ");
//				print("Comprising it are a wide range of treateses on history, philosophy, psychology, and eugenics, among other fields\n you had found interest in these topics throughout your youth");
//				print("and were often encouraged to pursue them by your mentors, who viewed such insight as invaluable for understanding and thus manipulating one's adversaries");
//				print("These days you rarely find much you care about or do not already know, with most readings only leaving you feeling more jaded and aloof");
//				print("At the top of the pile remain the only three books that you beleive to still warrant your attention. You briefly glance at the titles you had long grown accustomed to");
//				print("The book of Loec which details the laws and virtues of the warrior saint, Homo Novus and the traveller from the stars, which describes how human alien interaction");
//				print("has come to shape both throughout the last century, and finally the waning of the Dawn, which details the final years of the wars of restitution, where you yourself had earned your name");
//			}
//			if(input.contains("look at equipment"))
//			{
//				print("You inspect the arms and armour placed before you, ensuring they remain in pristine condition. As a knight your arms are far from the mass produced common tools of lesser warriors,");
//				print("which are to be used and discarded at a whim. Each constituent of your panoply of war represents a remarkable and costly feat of metallurgy and technology, so much so that it would");
//				print("would only be afforded to the finest soldiers the Deinans have to offer. The direblade, photon gun, chain dagger, soul arrow, and black armour");
//				print("each play a vital role in the knight's arsenal, each contributing to their status as the most terrifying and efficient killers in Terra Nova");
//			}
//			switch(input)
//			{
//			case "grab direblade": 
//                print("The direblade is more than a mere weapon it is an extension of your very arm and a representation of who you are as a man and warrior. It is easily fit for its role as a knight's primary weapon"); direblade=1;
//                print("as its fine sleek design, exceptionally strong composition capable of withstanding cannon fire, and fine permanently sharp blade render it a devastating weapon, often regarded as the swiftest and deadliest in all Terra.");break; 
//            case "grab photon gun": 
//                System.out.println("...."); photongun=1;
//                break; 
//            case "grab chain dagger": 
//            print("Although it lacks the elegant design and imposing image of the swift direblade, the chain dagger remains more advantageous in certain scenarios. initially designed for elite troops"); chaindagger=1;
//            print("to replace the now archaic pistol, the chain dagger consists of several sharpened magnetic plates bound by electromagnetic forces witch can be reversed sending the plates rapidly bursting towards ones targets");
//            print("before being remotely relinked by once again reversing its polarity ripping through the flesh of those previously struck by them and any unfortunate enough to remain in their path");
//            print("extremely effective against lightly armed oppents however well equipped enemies may easily withstand it");  // might place in item description men instead of initial switch statement
//            break;
//            case "grab black armour": 
//                System.out.println("......"); blackarmour=1;
//                break;  
//            case "grab soul arrow": 
//            print("the soul arrow represents a marvel of modern biotechnology able to preserve the wearer's genetic code and use it to restore even limbs and complex organs through reconstructing its wielder at a molecular level."); soularrow=1;
//            print("In addition to its exceptionally healing qualities, the soul arrow also acts as a reservoir for information preserving the wearer's knowledge and memories even beyond their death, enabling those capable enough with its use to access them");break;  
//			print("It is not without limitations however as its use incurs extreme physical and mental trauma to those unfamiliar with it, in addition to requiring extensive contact and lifelong training from its wielder in order to acheive optimal use");
//			print("It also notably prevents gene modding as the introduction of rapid mutation complexifies the user's genetic code making it difficult to recreate");
//			//use switch statement for equipment 
//			}
//		if ((direblade == 1) && (photongun == 1) && (chaindagger==1) && (blackarmour == 1) && (soularrow == 1))
//		{
//			print("You have gathered your arms and are ready to pursue your mission\n");
//			print("Your alien Deinan lord had ordered you to find and retreive his wayward son, as his spies suspect an attempt on his life will soon be made. You are unsurprised");
//			print("as Andros's perfidious and self indulgent nature often leaves him in the debt of unscrupulous individuals and even more frequently incurrs their enmity");
//			print("His behaviour has also earned him few friends amongst his fellow alien kin, particularly amongst the inquisition, as the often zealous nature of the deinans often leads them to look down upon it");
//			readytoleave=true;
//		}
//		if (input.contains("leave")&&(readytoleave==true))
//		{
//		    print("Leaving behind the refuge of your private quarters and the mountainous lands of house Andrek, you ride towards the urban neon lit reaches of the inner city where you suspect");
//		    print("you'll find the irreverent lordling you seek\n");
//		    croom = false; nightclub = true;
//		}
//		else if (readytoleave==false)
//		{ 
//			print("You have not yet gathered your equipment, you cannot leave yet\n");
//		}
//		}
//		while (nightclub)
//		{
//		if(intro == 0)
//		{
//		print("after a brief search you arrive at the heart of the inner city. A string of disparate sights lay before ranging from poor slums and croocked alleys to large glass towers and grandiose churches. Your breif");
//		print("interactions with the many criminals, pompous young nobles, and the occasional puritan zealot that inhabit this zone quickly remind why you hold this cesspool of depravation and vice with such contempt");
//		print("yet their fear of you ensures most remain helpful and with their aid you soon track down you target\nUpon entering one of the night clubs he regularly frequents, you are unsurprised to find him in a clearly");
//		print("inebriated state,surrounded by dubious looking women\nHe greets you in a jubilant and unconcerned manner, further trying your patiene\nAndros: Caul! i never thought id see you here, come old friend");
//		print("sit down and have a drink on me, i was just regilailing my lovely companions with some of your exploits\nCaul: We don't have time for this, get up and follow me right now, you can consort with your harlots later");
//		print("His attempts at protest are swiftly cut short by the sounds of screams, soon followed by the sound of a bullet leaving its chamber roughly to the south and east of you. Under normal circumstances your training as a knight");
//		print("Would allow you to easily deflect it, however with your awkward stance and turned back it will be difficult to ascertain its trajectory. You may still attempt to dodge it though there is no guarantee");
//		print("where the bullet may land if you do so");// use timer to make decision 30 seconds to make it can be done by implementing below functions into timer function?; 
//		while (timer!=0)
//			{
//			getInput();
//		    if (input.contains("dodge")){print("You skillfully dodge the bullet and watch as it flies past you, grazing Andros's arm and striking a woman next to him square in the chest");
//		    	print("the latter instantly falling dead while the former is left shaken and wounded\nAngered you turn to face your enemies, ready to exact vengeance"); decision = 1; break;}
//            else if (input.contains("deflect")){
//		    	print("You manage to deflect the bullet to the awe of all those present, instilling fear and hesitation in your attackers");decision = 1;break;}// lower enemy morale
//		    else {
//		    	print("Your excessive delibarition takes its toll as the bullet crashes against your ribs, luckily your armour absorbs most of the damage but nontheless you are not unscathed");
//		    	print("You suspect that absorbing any more of such shots may leave you seriosuly injured"); decision = 1; break;// lower cauls health by 20
//		    	}
//		    }
//		if ((timer == 0)&&(decision == 0))
//		{
//			print("Your excessive delibarition takes its toll as the bullet crashes against your ribs, luckily your armour absorbs most of the damage but nontheless you are not unscathed");
//			print("You suspect that absorbing any more of such shots may leave you seriosuly injured");// lower cauls health by 20 
//		}
//		print("You spot a dozen or so well armed assailants bearing the sign of the red hand, a sizable Alien lead criminal organization with ties to the Deinan nobility and a reputation for extreme violence");
//		print("you should be capable of easily dispatching them but they have you encircled and with Andros requiring protection, your movement is somewhat restricted.");
//		print("If you are not careful this fight may grow much more dificult than it should be"); int enemies = 12;
//		while(enemies>0)
//		{
//			getInput();
//			
//		{
//			if (input.contains("use direblade")){print("You slash at your enemies with your direblade hewing several of them in half before they have a chance to strike"); enemies = enemies - 5;
//			print("They eventually regroup though and wisely attempt to overwhelm you with their numbers");}// use sensor to attack standard is 5 kills if player swings fast enough may kill more};
//			else if (input.contains("use photon gun")){print(""); enemies = enemies - 4;} //photon gun chrge reduced , caul takes moderate damage}; 
//			else if (input.contains("use chain dagger")){print("You unleash your chain knife and watch as it splinters into metal shards hurling towards your enemies");enemies = enemies - 7;
//			print("Several lightly armoured enemies immediatly fall to the ground however those more heavily eqiupped are unaffected as the shards merely bounce off their vests");}// caul takes light damage
//			else if (input.contains("use soul arrow")) {print("You launch your soul arrow towards your enemies and watch as it tears through 3 of them instantly killing them");enemies = enemies - 3;
//			print("Hower the task of directing it absorbs all your focus allowing your enemies to outmanouver you and esurround you further");} // caul takes moderate damage
//            //use timed parry function to simulate enemy attacks
//		}
//		}
//		intro = 1;
//		}// use switch statement for attacks
//		if(input.contains("leave")){
//		print("You carve a bloody path through the assailants blocking the door before cutting through dozens more of them as you escape through the winding alleys of the inner city.");
//		print("Eventually you lose them in the maze of streets as those bold or persistent enough to follow you grow gradully fewer");
//		nightclub=false;innercitystreets=true;}
//		}
//		print("After a long walk you arrive at the center of an empty dimly lit street close to the borders of the inner city surrounded by old or incomplete buildings");
//		print("You tread carefully past it doubting this area is truly as abandoned as it seems ");
//		while(innercitystreets)
//		{
//			getInput();
//			if (input.contains("look"))
//			{
//			print("Andros stands close behind you, still somewhat dazed and freightened. He glares at you indignantly, a mixture of anger, releif, and shame evident upon his feline features");
//			print("Towards the corner of a dark alley you notice a faint glimmer that catches your eye, stemming from what appears to be a dark ruby placed ");
//			print("conscpicuosly beneath a patch of rubble although it seems an obvious trap to you, you cannot help but feel drawn to it as something within it calls to you beckoning you forward");
//			print("Near the end of the street You see a tall emaciated and shrouded man donning a dark tattered overcoat and a garish porcelain mask ");
//			print("He seems to be of kesri stock though he's clearly attempting to hide it. He motions to you to come closer");
//			if (input.contains("talk to drifter"))
//			{
//			 print("As you approach the shrouded man he begins to whisper to you in a deep serpentine voice\nDrifter: Greetings sir Caul, I have some information that may be of interest to you");
//			 print("Caul: What would a lowly drifter have to offer a knight");
//			 print("Drifter: You would be surprised my lord, many here have found wealth in plying illegal goods such as guns or gene modders, i trade in a much rare and more important commodity information");
//			 print("the kind that men of wit or ambition may use to transform fates and topple giants");print("Caul: Very well you have my attention, tell me what is it you know");
//			 print("Drifter: There is a young woman in your court that you would be wise to watch closely, one i am told you have come to hold in rather high regard");
//			 print("Drifter: I would disregard any ..... you think you share with her lest you find yourself dissapointed or worse, after all rebel sympathies often lead to drastic acts");
//			 print("Caul: you understand that such accusations are dangerous drifter and that you cannot be allowed to go unpunished if they are false");
//			 print("Drifter: Haha but of course, I am no fool sir, i understand it would be unwise to incite the ire of a knight, not to mention that misinforming my clients would be rather bad for bussiness");
//			 print("Drifter: You may investigate my claims yourself if you do not beleive them. Until then you may ask me of other matters that concern you for a price");
//			}// pay small amount of credits for info
//				if (input.contains("ask about ruby"))
//				{
//					print("Drifter: You refer to the ruby towards the alley yes. I have heard tales of such gemstones leading unaware travellers to their doom, driving them to reckless acts of greed or madness");
//					print("Drifter: some say they are the work of Kesri sorcery. All i can say for certain is that there are those who hold such stones to be sacred and would not let them escape their grasp easily");
//					print("Drifter: nevertheless they tend to be worth a considerable amount, though personally i would not hold onto them for long");
//				}
//				if (input.contains("ask about kesri")) 
//				{
//					print("So you wish to know about the kesri, you would be hard pressed to find any here with puritan inquisitors patrolling the streets seemingly at random, after all ");
//					print("it is no secret that the only thing they hate more than men are darklings. The darklings are a peculiar lot, thier systems of governance and worship are convoluted and foreign");
//					print("Their philosophies perplexing and rife with contradiction. They venerate a deity they believe fated to die, and view acts of cannibalism and blood sacrifice as bringing them");
//					print("closer to the divine. Despite their incompatible nature with men and deinans their talent for espionage and sorcery may prove useful to any willing to ally with them");
//				    print("If you wish to further engage with them yourself or even hire them you may seek them out in the scarred lands. I have heard a group of eighty or so kesri mercenaries");
//				    print("are currently scouring the ashen wastes pursuing some form of contract, though i do not know its exact nature.");
//				}
//			}
//			if (input.contains("attack drifter"))
//			{
//				//drifter reveals powerful weapons and engages Caul in a difficult fight, two kesri ambushers join if not already killed;
//				//players gain porcelain mask and rune blade if they win.
//			}
//			if(input.contains("grab ruby"))
//			{
//				print("The ruby shines with a peculiar glow that grows stronger the closer you get, seeming to consume all light around it, by the time you reach it the glow has become overpowering");
//				print("leaving you in a near blind state. As you kneel to grab it two pale black eyed figures leap at you from the shadows, weapons drawn, no doubt the ones responsible for placing the gemstone"); 
//				// 2 kesri mercenaries attack 
//			}
//			if(input.contains("leave")){innercitystreets=false;meetinghall=true;print("You leave the chaotic streets of the inner city behind you as you begin your journey");
//			print("back to your lord's lands, he will no doubt be grateful for his son's safe return though you suspect a part of him wishes he had perished");}
//		}
//		while(meetinghall)
//		{
//			getInput();
//		}
//				return 0;
//	}

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
	public static boolean generateEnemyCrit() {
		rand.setSeed(System.currentTimeMillis());
		boolean hit = (rand.nextInt(16-0)==6)? true:false;//one in 16 chance of getting critical hit
		return hit;
		
	}
	
	public static void battleEnemy(gameCharacter user,enemy villian) {///function for when the user is in a boss battle (1v1)
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
		final combat Combat = new combat();
		
		while(user.health>0&&villian.health>0) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Combat.combatInput = "-:P";
			Combat.blocked =false;
			print(villian.name+" is using their weapon the "+villian.weapon.name+".");
			
			Thread incoming = new Thread() {
				public Scanner in = new Scanner(System.in);
				@Override 
				public void run() {
					
					System.out.println(">");
					
					String i = "";
					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.print(">");
					i = in.nextLine();
					print("beep beep;"+i+"/");
					if(i.contains("2")) {
						Combat.blocked = true;
					}
					if(interrupted()) {
						in.close();
					}
				}

//					
			};
			incoming.start();
			try {
				Thread.sleep(9000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			incoming.interrupt();
			if(Combat.blocked==true) {
				print("*ATTACK BLOCKED*");
			}else {
			print("*Too Late "+villian.name+" was successful in their attack*");
				//incoming.interrupt();
			}
			user.takenDamage(villian.weapon, villian.damage, Combat.blocked, generateEnemyCrit());
			print(user.name+ "has "+user.health+"left");
			print("You manage to create some distance between you and "+ villian.name);
			
			Thread outgoing = new Thread() {
				@Override 
				public void run() {
					Combat.attacked = false;
					System.out.println(">");
					Scanner ino = new Scanner(System.in);
					String i = "";
					//System.out.print(">");
					i = ino.nextLine();
					print("beep beep;"+i+"/");
					if(i.contains("1")) {
						Combat.attacked = true;
					}
					if(interrupted()) {
						ino.close();
					}
				}
					
			};
			outgoing.start();
			try {
				Thread.sleep(9000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outgoing.interrupt();
			if(Combat.attacked==true) {
				print("*ATTACK DEALT*");
			}else {
			print("*ATTACK MISSED*");
				//incoming.interrupt();
			}
			villian.takenDamage(user.weapon, user.damage, false, generateEnemyCrit());
			print(villian.name+ "has "+villian.health+"left");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
	}
	
	public static void main(String[] args) {
		item att = new item("sword","Sword",40.0,15.0,5952);
		item sheild = new item("sh","Sheild",0.5,50,5952);
		enemy temp= new enemy("IGOR", "wee", 44, 40.0, 40.0, 100.0,true,"boss",att,sheild);
		theGirl.sheild =sheild;
		theGirl.weapon = att;
		battleEnemy(theGirl,temp);
		
	if(welcomeScreen()) {
	Integer roomNo = 2;
	boolean quit = false;
	while(quit == false) {
	switch(roomNo) 
	{
	case 0: //roomNo = handleKnightIntro();
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
