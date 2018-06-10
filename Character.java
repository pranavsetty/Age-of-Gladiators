
/**  This class is part of the "Age of Gladiators" application. 
 * "Age of Gladiators" is a very simple, text based adventure game.
 * 
 * 
 * The Character class hold the following information :-
 * 
 * There are total of 4 characters in the game Gold Smith , Weapon Smith , Money Smith and Magician
 * When the player in a certain room and types the 'speak' command ,the character present in the room , gives out the instrutions on how  
 * to proceeed further
 */
public class Character
{
    Character goldSmith;
    Character weaponSmith;
    Character armourSmith;
    Character magician;

    public void Character(){
        goldSmith = new Character();
        weaponSmith = new Character();
        armourSmith = new Character();
        magician = new Character();

    }

    /**
     * When the player speaks to the gold smith , the below output is shown
     */
    public void speakToGoldSmith(){
        System.out.println();
        System.out.println(" GOLD-SMITH : Collect gold coins for battles won and exchange them to upgrade your armour ,weapons and magic potions");
        System.out.println(" GOLD-SMITH : For each battle you win, you can redeem 5 gold coins");
        System.out.println(" GOLD-SMITH : Check your balance and redeem your coins");
        System.out.println(" GOLD-SMITH : Type 'battles-won' to check how much you can redeem ");
        System.out.println(" GOLD-SMITH : Type 'balance' to check the gold coins you have ");
        System.out.println(" GOLD-SMITH : Type 'redeem' to redeem gold coins ");

    }

    /**
     * When the player speaks to the weapon smith , the below output is shown
     */

    public void speakToWeaponSmith(){
        System.out.println();
        System.out.println("WEAPON-SMITH: Redeem weapons for the gold coins you have");
        System.out.println("WEAPON-SMITH: Each weapon will cost you a total of 10 gold coins");
        System.out.println("WEAPON-SMITH: Type 'balance' to check how many weapons you can redeem");
        System.out.println("WEAPON-SMITH: Type 'show-stats' to see statistics of items available");
        System.out.println("WEAPON-SMITH: Type 'pick-weapon' to add a weapon to your inventory");
        System.out.println("WEAPON-SMITH: Type 'help' if you have any doubts");
        System.out.println();

    }
    
    /**
     * When the player speaks to the armour smith , the below output is shown
     */
    public void speakToArmourSmith(){
        System.out.println();
        System.out.println("ARMOUR-SMITH: Redeem an armour for the gold coins you have");
        System.out.println("ARMOUR-SMITH: Each armour will cost you a total of 10 gold coins");
        System.out.println("ARMOUR-SMITH: Type 'balance' to check how many armours you can redeem");
        System.out.println("ARMOUR-SMITH: Type 'show-stats' to see statistics of items available");
        System.out.println("ARMOUR-SMITH: Type 'pick-armour' to add a armour to your inventory");
        System.out.println("ARMOUR-SMITH: Type 'help' if you have any doubts");
        System.out.println();

    }

    /**
     * When the player speaks to the magician , the below output is shown
     */

    public void speakToMagician(){
     System.out.println();
        System.out.println("MAGICIAN: Redeem weapons for the gold coins you have");
        System.out.println("MAGICIAN: Each weapon will cost you a total of 15 gold coins");
        System.out.println("MAGICIAN: Type 'balance' to check how many spells you can redeem");
        System.out.println("MAGICIAN: Type 'show-stats' to see statistics of items available");
        System.out.println("MAGICIAN: Type 'pick-spell' to add a spell to your inventory");
        System.out.println("MAGICIAN: Type 'help' if you have any doubts");
        System.out.println();

    }
}
