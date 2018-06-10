/**
 * This class is the main class of the "Age of Gladiators" application
 * "Age of Gladiators" is a very simple, text based adventure game .Users can walk around  
 * rooms , pick weapons , armours , spells and increase their chances of winning 
 * battles in the colosseum.
 * 
 * To play the game, create an instance of this class and call the "play" method.
 * 
 *This main class creates and initialises all the others : it creates all the rooms,
 *creates the parser and starts the game. It also evaluates and executes the commands theat the 
 *parser returns.
 *
 */

import java.util.Random;
import java.util.*;

public class Game 
{
    private Parser parser;
    private Room currentRoom, nextRoom;
    private int balance,r,opponentEnergy,weaponWeight,spellWeight,armourWeight,battlesWon,battlesToRedeem,roomCounter,minEnergy,maxEnergy;
    private float progress;
    private boolean speak = false;
    String direction;
    private Room colosseum , armoury , weaponSmith , moneyRoom , magicShop , transporter;   
    private String commandWord;
    private Character playerTalk = new Character();
    private int playerEnergy = 10;
    private Item link = new Item();
    private Item warItems = new Item();
    private ArrayList<Room>roomRecord = new ArrayList<>();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     */

    private void createRooms()
    {

        // create the rooms
        // initialise room exits

        colosseum = new Room("in the Colesseum");
        armoury = new Room("in the Armoury Shop");
        weaponSmith = new Room("with the Weaponsmith");
        moneyRoom = new Room("in the Money Room");
        magicShop = new Room("in the Magic Shop");
        transporter = new Room("in the Transporter Room");

        // Map - The colosseum is in the center. 
        // Go to the 'map' method to see a pictorial view of all tthe rooms and their

        // Colosseum arena exits
        colosseum.setExit("|West|", magicShop);
        colosseum.setExit("|South|", moneyRoom);
        colosseum.setExit("|East|", weaponSmith);
        colosseum.setExit("|North|",armoury);

        // Armoury room exits 
        armoury.setExit("|South-to-C|", colosseum);  // The letter 'C' in the exit "|South-to-C|"stands for colosseum
        armoury.setExit("|Arena-South|",moneyRoom);  // The above exit means that the moneyRoom is in the north direction of the colosseum. The same goes for similar exits which have the word 'Arena'
        armoury.setExit("|Arena-West|",magicShop);
        armoury.setExit("|Arena-East|",weaponSmith);

        // Weaponsmith room exits
        weaponSmith.setExit("|West-to-C|", colosseum); // The letter 'C' in the exit "|West-to-C|"stands for colosseum
        armoury.setExit("|Arena-South|",moneyRoom);
        weaponSmith.setExit("|Arena-North|", armoury);
        weaponSmith.setExit("|Arena-South|",moneyRoom);
        weaponSmith.setExit("|Arena-West|",magicShop);
        weaponSmith.setExit("|East-to-T|",transporter); // The letter 'T' in the exit "|East-to-T|"stands for Transporter

        // Transporter exits
        transporter.setExit("|West-to-W|",weaponSmith); // The letter 'C' in the exit "|West-to-W|"stands for weapon Smith

        // Money room exits
        moneyRoom.setExit("|North-to-C|", colosseum);
        moneyRoom.setExit("|Arena-North|", armoury);
        moneyRoom.setExit("|Arena-West|",magicShop);
        moneyRoom.setExit("|Arena-East|",weaponSmith);

        // Magic shop exits
        magicShop.setExit("|East-to-C|",colosseum);
        magicShop.setExit("|Arena-North|", armoury);
        magicShop.setExit("|Arena-South|",moneyRoom);
        magicShop.setExit("|Arena-East|",weaponSmith);

    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the age of Gladiators");
        System.out.println("You need to win battles in order to emerge victorious");
        System.out.println("You will have an initial energy of 10 . Your opponent will have a energy range between 1 to 25 (Randomly decided)");
        System.out.println("Increse your energy by collecting weapons , aromour and spells");
        System.out.println("Type 'help' if you need help.");
        System.out.println("NOTE - COMMANDS ARE CASE-SENSITIVE . TYPE CAREFULLY!. Type the word 'go' before you decide to exit . eg 'go |North|'." );
        System.out.println("Type 'progress' to check your progress in the game ");
        currentRoom = colosseum;  // start game outside
        System.out.println(currentRoom.getLongDescription());

        System.out.println();
        map(); 
        
        colosseum();
        
       
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }

        else if (commandWord.equals("quit")) {

            wantToQuit = quit(command);
        }

        else if(commandWord.equals("map")){
            map();

        }

        else if(commandWord.equals("back")){
            back();

        }
        else if (commandWord.equals("balance")){
            System.out.println("Your current balance is :"+ balance);

        }
        else if (currentRoom == transporter && commandWord.equals("generate")){        
            transporter();            
        }

        else if (currentRoom == moneyRoom && commandWord.equals("speak") ){
            playerTalk.speakToGoldSmith();

        }

        else if (currentRoom == armoury && commandWord.equals("speak")){
            playerTalk.speakToArmourSmith();

        }

        else if (currentRoom == weaponSmith && commandWord.equals("speak")){
            playerTalk.speakToWeaponSmith();   
        }

        else if (currentRoom == weaponSmith && commandWord.equals("available-weapons")) {
            warItems.weaponInventory();
        }

        else if (currentRoom == magicShop && commandWord.equals("speak")){
            playerTalk.speakToMagician();

        }

        else if(commandWord.equals("progress")){
            progressStats();   

        }

        else if (currentRoom == moneyRoom && commandWord.equals("redeem")){
            if(battlesToRedeem > 0){

                battlesToRedeem -= 1;
                balance += 5;
                System.out.println("Your current balance is :" + balance);
                System.out.println("You can redeem a total of " + battlesToRedeem + " battles.");
            }

            else {

                System.out.println("Go to the Colosseum and battle again . You can't redeem anymore :(");   
            }

        }

        else if(currentRoom == colosseum && (commandWord.equals("battle") || commandWord.equals("battle-again"))){
            energyLevels(); 

            if(spellWeight == 2 && armourWeight == 5 && weaponWeight ==5 && battlesWon == 34){

                System.out.println("You have won the tournament. Congratulations ! You should feel proud of your self.");
                System.out.println("Type 'quit' to close the game");
            }

        }

        else if((currentRoom == colosseum || currentRoom == moneyRoom ) && commandWord.equals("battles-won")){
            System.out.println("You have won a total of " + battlesToRedeem + " battles."); 


        }

        else if ((currentRoom == armoury || currentRoom == weaponSmith ||currentRoom == magicShop)  && commandWord.equals("show-stats")){
            energyStats();
        }

        else if (currentRoom == weaponSmith && commandWord.equals("pick-weapon")){

            pickWeapon(command);

        }

        else if (currentRoom == armoury && commandWord.equals("pick-armour")){
            pickArmour(command);

        }
        else if (currentRoom == magicShop && commandWord.equals("pick-spell")){
            pickSpell(command);

        }

        // else command not recognised.
        return wantToQuit;

    }

    
    /** 
     * Shows a pictorial representian of the directions to room .
     */ 
    public void map(){
        System.out.println("                       ARMOURY"                                                                                 );
        System.out.println("                          ↑                                                                       North           ");
        System.out.println("                 ------------------                                                                 ↑            ");
        System.out.println("   MAGIC SHOP ← |COLOSSEUM (Arena)| → WEAPON SMITH → TRANSPORTER ROOM                    West ← COMPASS → East          ");
        System.out.println("                 ------------------                                                                 ↓            ");
        System.out.println("                          ↓                                                                       South               ");
        System.out.println("                     MONEY ROOM                                                                                 ");    

    }
    
    
    /**
     * Shows statistics of items presesnt in other rooms . (Such as weapons , armour and spell)
     */
    public void energyStats(){
        link.increaseInEnergyLevels();   

    }
    
    /** 
     * Shows your current progress in the game.
     */
    
    public void progressStats(){
        System.out.println("You need to win a total of 10 battles and have a spell weight of 2 and weapon/armour weight of 5 each");
        System.out.println();
        System.out.println("Your current progress in the game " + progress +" percent");
        System.out.println();
        System.out.println("Your current weapon weight is :" + weaponWeight);
        System.out.println("Your current armour weight is :" + armourWeight);
        System.out.println("Your current spell weight is  :" + spellWeight);
        System.out.println("You have won a total of " + battlesWon +" battles");

    }
    

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {

        System.out.println();
        System.out.println("Type 'map' to see the directions you can go");
        System.out.println("The exits for each room are provided when you first enter the room");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println("Note :- Some of the commands will work only in specific rooms.");
    }

    /**
     * Takes you back to the previousRoom
     */
        
    public void back(){
        if(roomCounter <= 0){

            System.out.println("You can't go further back.");

        }
        else{
            currentRoom = roomRecord.get(--roomCounter);
            System.out.println(currentRoom.getLongDescription());
        }

    }

    /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        direction = command.getSecondWord();

        // Try to leave current room.
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no room!");
        }
        else {
            roomCounter ++;
            roomRecord.add(currentRoom);
            currentRoom = nextRoom;

            System.out.println(currentRoom.getLongDescription());
            if(currentRoom == transporter){
                System.out.println("Welcome to the Transporter room. You will get transported to a random location based on the random number generated.");
                System.out.println("Type 'generate' to get started !");
            }

            else if(currentRoom == moneyRoom){
                System.out.println("Welcome to the Money Room. If you want to exit the room , use the above available commands.");
                System.out.println("Type 'speak' to talk to the goldsmith");

            }

            else if (currentRoom == armoury){
                System.out.println("Welcome to the Armoury Shop.If you want to exit the room , use the above available commands.");
                System.out.println("Type 'speak' to talk to the armourysmith");
            }

            else if(currentRoom == magicShop){
                System.out.println("Welcome to the Magic Shop.If you want to exit the room , use the above available commands.");
                System.out.println("Type 'speak' to talk to the magician");

            }

            else if (currentRoom == weaponSmith){
                System.out.println("Welcome to the Weapon Shop.If you want to exit the room , use the above available commands.");
                System.out.println("Type 'speak' to talk to the weaponsmith");

            }
            else if(currentRoom == colosseum){

                colosseum();   

            }
        }
    }

    /**
     * Its picks a weapon , if you have the necessary balance .
     * With every weapon you pick , the method deducts the balance by 10 gold coins , increases the player energy by 2 ,opponent energy by 1,
     * progress by 2.94 % and weapon weight by 1
     * 
     * If  the weapon weight is equal to 5 , you cannot pick any further. 
    **/
    
    public void pickWeapon(Command command){
        if(balance >= 10){
            balance -=10;
            playerEnergy += 2;
            opponentEnergy += 1;
            progress += 2.94 ;

            if(weaponWeight == 5){
                System.out.println("You have reached your maximum capacity. You cannot carry anymore weapons.");   
            }

            else {
                System.out.println("The current items in your inventory are:");
                warItems.addWeapon();
                warItems.myInventory();
                weaponWeight += 1;
                System.out.println();
                System.out.println("Your current weapon weight is " + weaponWeight); 

            }

            System.out.println();

            if(warItems.getList1().isEmpty()){
                System.out.println("The weapon smith doesn't have any weapons left. Please exit the room");
            }
            else{
                System.out.println("The weapons available with the weaponSmith are :");
                warItems.weaponInventory();
            }

            System.out.println();
            System.out.println("Your current balance is " + balance + " gold coins");
        }

        else{
            System.out.println("You either don't have the balance to redeem a weapon or the weaponsmith does'nt have any left ! Go to the Colosseum again and win battles or try another room");   
        }

    }

    
    /**
     * Its picks a armour , if you have the necessary balance .
     * With every armour you pick , the method deducts the balance by 10 gold coins , increases the player energy by 2 ,opponent energy by 1,
     * progress by 2.94 % and armour weight by 1
     * 
     * If  the armour weight is equal to 5 , you cannot pick any further. 
    **/
    
    public void pickArmour(Command command){
        if(balance >= 10){
            balance -=10;
            playerEnergy += 2;
            opponentEnergy += 1;
            progress += 2.94 ;

            if(armourWeight == 5){
                System.out.println("You have reached your maximum capacity. You cannot carry anymore armour.");   
            }

            else {
                System.out.println("The current items in your inventory are:");
                warItems.addArmour();
                warItems.myInventory();
                armourWeight += 1;
                System.out.println();
                System.out.println("Your current armour weight is " + armourWeight);
            }

            System.out.println();
            if(warItems.getList2().isEmpty()){
                System.out.println("The armour smith doesn't have any armour left. Please exit the room");
            }
            else{
                System.out.println("The armour available with the armourSmith are :");
                warItems.armourInventory();
            }

            System.out.println();
            System.out.println("Your current balance is " + balance + " gold coins");
        }

        else{
            System.out.println("You either don't have the balance to redeem a armour or the weaponsmith does'nt have any left ! Go to the Colosseum again and win battles or try another room");   
        }

    }

    /**
     * Its picks a spell at random , if you have the necessary balance .
     * With every spell you pick , the method deducts the balance by 15 gold coins , increases the player energy by 3 ,opponent energy by 1,
     * progress by 2.94 % and spell weight by 1
     * 
     * If the spell weight is equal to 2 , you cannot pick any further. 
    **/
    
    
    public void pickSpell(Command command){
        if(balance >= 15){
            if(spellWeight == 2){
                System.out.println("You have reached your maximum capacity. You cannot carry anymore spells.");   
            }

            else {

                balance -=15;
                playerEnergy += 3;
                opponentEnergy += 1;
                progress += 2.94 ;

                System.out.println("The current items in your inventory are:");
                warItems.addSpell();
                warItems.myInventory();
                spellWeight += 1;
                System.out.println();
                System.out.println("Your current spell weight is " + spellWeight);
            }

            System.out.println();
            if(warItems.getList3().isEmpty()){
                System.out.println("The magician doesn't have any spells left. Please exit the room");
            }
            else{
                System.out.println("The spells available with the magician are :");
                warItems.spellInventory();
            }

            System.out.println();
            System.out.println("Your current balance is " + balance + " gold coins");
        }

        else{
            System.out.println("You either don't have the balance to redeem a spell or the weaponsmith doesnt have any left or you have reached your maximum spell weight. ! \n Go to the Colosseum again and win battles or try another room");   
        }

    }

    /**
     * Sends you to a random room when the current room is the transporter
     * 
     */
    public void transporter(){
        int min = 1;
        int max = 5;

        Random rand = new Random();

        r = rand.nextInt(max-min) + min;
        System.out.println("Room number generated : " + r);

        if(r == 1){
            currentRoom = armoury;
            playerTalk.speakToArmourSmith();
            
        }
        else if (r == 2){
            currentRoom = moneyRoom;
            playerTalk.speakToGoldSmith();
        }
        else if(r == 3){
            currentRoom = magicShop;
            playerTalk.speakToMagician();
        }
        else if (r == 4){
            currentRoom = weaponSmith;
            playerTalk.speakToWeaponSmith();
        }
        else {
            currentRoom = colosseum;
            
        }
        System.out.println(currentRoom.getLongDescription());

    }

    /**
     * 
     * Instructions to be shown when the current room is the colosseum
     */
    public void colosseum(){
        System.out.println();
        System.out.println("Welcome to the main battle arena - THE COLOSSEUM. If you want to exit the room , use the above available commands." );
        System.out.println("Type 'map' to show your current location");
        System.out.println("You will be fighting with top gladiators from all over the Roman Empire. The outcome of the battle is decided by energy of the player");
        System.out.println("If your energy levels are higher than your opponent, you win , else you loose");
        System.out.println("For every battle you win, you can redeem 4 gold coins ");
        System.out.println("Type 'battle' to start the battle");
    }

    
    /**
     * 
     * Logic for the battle in the colosseum
     * The opponent's energy is generated by random based on the range .
     * If the player's energy is less than the opponent's energy , you then loose , else you win
     * 
     */
    public void energyLevels(){
        minEnergy = 1;
        maxEnergy = 25;

        Random rand = new Random();

        opponentEnergy = rand.nextInt(maxEnergy-minEnergy)+minEnergy;
        System.out.println("Opponent Energy: " + opponentEnergy);
        System.out.println("Your energy:" + playerEnergy);
        

        if(playerEnergy > opponentEnergy){
            battlesToRedeem += 1;
            battlesWon +=1;
            progress += 2.94 ;
            
            System.out.println("You won !");
            System.out.println("Exit the arena if you want to redeem items. Type 'map' to see where you want to go");

        }
        else {
            System.out.println("You lost !");  

        }

        System.out.println("Type 'battle again' to try once more" );
        System.out.println("Type 'battles-won' to check your wins");
        System.out.println("Type 'progress' to check your progress in the game ");

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
