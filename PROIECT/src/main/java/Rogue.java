import java.util.ArrayList;
import java.util.Random;

public class Rogue extends Character {

    public Rogue(String name, int experience, int level) {
        Random rand = new Random();
        this.abilities = new ArrayList<Spell>();
        for (int i = 1; i <= 4; i++) {
            int type = rand.nextInt(3);
            if (type == 0) {
                abilities.add(new Earth());
            }
            if (type == 1) {
                abilities.add(new Fire());
            }
            if (type == 2) {
                abilities.add(new Ice());
            }
        }
        this.name = name;
        this.curr_experience = experience;
        this.curr_level = level;
        this.curr_mana = 100;
        this.curr_life = 100;
        this.max_mana = 100;
        this.max_life = 100;
        this.inventory = new Inventory();
        this.dexterity = 5 + rand.nextInt(level);
        this.charisma = 5 + rand.nextInt(level);
        this.strength = 5 + rand.nextInt(level);
        this.fire = false;
        this.ice = false;
        this.earth = true;
    }

    public String toString() {
        return " * Rogue [name: " + name + ", experience: " + curr_experience + ", level: " + curr_level + "]";
    }

    public void receiveDamage(int val) {
        if (charisma + strength > val) {
            curr_life = curr_life - val / 2;
        } else {
            curr_life = curr_life - val;
        }
    }

    public int getDamage() {
        int damage = charisma + dexterity + strength;
        if (dexterity > damage / 2) {
            return 2 * damage;
        } else {
            return damage;
        }
    }
}
