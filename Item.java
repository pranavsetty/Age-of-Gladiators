
/**
 *  This class is part of the "Age of Gladiators" application. 
 * "Age of Gladiators" is a very simple, text based adventure game.
 * 
 * The Item class hold holds information of items present in different rooms (i.e weapons , armour , spells , player inventory ).
 * 
 */
import java.util.*;
import java.util.Random;

public class Item 
{
    private ArrayList<String>weapons;   // stores types of weapons 
    private ArrayList<String>armour;    // stores types of armour
    private ArrayList<String>myInventory; // stores items that are added to the player inventory
    private ArrayList<String>magicSpells; // stores types of magic spells
    private Random randomGenerator;
    private int r;

    public Item(){
        weapons = new ArrayList<>();
        weapons.add("Bow & Arrow - weapon1");
        weapons.add("Sword - weapon2");
        weapons.add("Dagger- weapon3");
        weapons.add("Spear- weapon4");
        weapons.add("Trident- weapon5");

        armour = new ArrayList<>();
        armour.add("Sword Belt");
        armour.add("Arm Guard");
        armour.add("Leg Guard");
        armour.add("Breast Plate");
        armour.add("Steel Helmet");

        myInventory = new ArrayList<>();

        magicSpells = new ArrayList<>();
        magicSpells.add("Vitality Spell");
        magicSpells.add("Strength Spell");
        magicSpells.add("Agility Spell");
        magicSpells.add("Defence Spell");
        magicSpells.add("Stamina Spell");
        magicSpells.add("Attack Spell");

    }

    /**
     * Displays statistics of various weapons, armours and spells
     */
    public void increaseInEnergyLevels(){
        System.out.println();
        System.out.println("CAN REDEEM ONLY FROM WEAPON SMITH");
        System.out.println("1) Bow & Arrow - Need 10 gold coins | INCREASES ENERGY BY 2 \n2) Sword - Need 10 gold coins |INCREASES ENERGY BY 2 \n3) Dagger - Need 10 gold coins | INCREASES ENERGY BY 2 \n4) Spear - Need 10 gold coins | INCREASES ENERGY BY 2 \n5) Trident -  Need 10 gold coins |INCREASES ENERGY BY 2 ");
        System.out.println("---------------------------------");
        System.out.println("CAN REDEEM ONLY FROM ARMOUR SMITH");
        System.out.println("6) Sword Belt - Need 10 gold coins | INCREASES ENERGY BY 2 \n7) Arm Guard - Need 10 gold coins | INCREASES ENERGY BY 2 \n8) Leg Guard - Need 10 gold coins |INCREASES ENERGY BY 2 \n9) Breast Plate - Need 10 gold coins | INCREASES ENERGY BY 2 \n10) Steel Helmet - Need 10 gold coins | INCREASES ENERGY BY 2 ");
        System.out.println("---------------------------------");
        System.out.println("CAN REDEEM ONLY FROM MAGIC SHOP");
        System.out.println("11) Vitality Spell - Need 15 gold coins | INCREASES ENERGY BY 3 \n12) Strength Spell - Need 15 gold coins | INCREASES ENERGY BY 3 \n13) Defence Spell- Need 15 gold coins |INCREASES ENERGY BY 3 \n14) Stamina Spell - Need 15 gold coins | INCREASES ENERGY BY 3 \n15) Attack Spell - Need 15 gold coins | INCREASES ENERGY BY 3 ");
        System.out.println();
    }

    /**
     * Returns the list of weapons
     */
    public ArrayList<String> getList1()
    {
        return weapons;        
    }

    /**
     * Returns the list of armours
     */
    public ArrayList<String> getList2(){
        return armour ;   
    }

    /**
     * Returns the list of magic spells
     */
    public ArrayList<String> getList3(){
        return magicSpells ;   
    }

    /**
     * Prints out all the items in the weapons inventory
     */
    public void weaponInventory(){
        for (String weaponsInventory: weapons){
            System.out.println(weaponsInventory);
        }
    }

    /**
     * Prints out all the items in the armour inventory
     */
    public void armourInventory(){

        for ( String armourInventory: armour){
            System.out.println(armourInventory);   
        }

    }

    /**
     * Prints out all the items in the spellInventory()
     */
    public void spellInventory(){
        for(String spellsInventory : magicSpells){
            System.out.println(spellsInventory);   
        }

    }

    /**
     * 
     * Add the first item from the weapon inventory to the player inventory.
     * Once added that item is removed from the weapon inventory
     */
    public void addWeapon(){
        myInventory.add(weapons.get(0));
        weapons.remove(0);

    }

    /**
     * 
     * Add the first item from the armour inventory to the player inventory.
     * Once added that item is removed from the armour inventory
     */

    public void addArmour(){
        myInventory.add(armour.get(0));
        armour.remove(0);

    }

    /**
     * 
     * Add a randrom item from the spell inventory to the player inventory.
     * Once added that item is removed from the spell inventory
     */

    public void addSpell(){

        itemGenerator();

        if(magicSpells.contains(magicSpells.get(r))){

            String spell = magicSpells.get(r);
            myInventory.add(spell);
            magicSpells.remove(spell);
        }

        else{
            System.out.println("Try the 'pick-spell' command again");   

        }

    }

    /**
     * Generates a random number , which is used to choose an item from the spell inventory
     */

    public void itemGenerator(){

        int min = 0;
        int max = 4;
        Random rand = new Random();

        int r = rand.nextInt(max-min)+min;
        System.out.println("Item generated : " + r);

    }

    /**
     * Prints out all the items in the player inventory
     */

    public void myInventory(){
        for (String playerInventory: myInventory){
            System.out.println(playerInventory); 
        }

    }

}
