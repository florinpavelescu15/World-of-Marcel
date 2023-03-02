import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements CellElement {

    public Enemy() {
        Random rand = new Random();
        abilities = new ArrayList<Spell>();
        curr_mana = 100;
        curr_life = 100;
        max_life = 100;
        max_mana = 100;
        ice = rand.nextBoolean();
        fire = rand.nextBoolean();
        earth = rand.nextBoolean();
        for (int i = 1; i <= 3; i++) {
            int type = rand.nextInt(3);
            if (type == 0) {
                abilities.add(new Fire());
            }
            if (type == 1) {
                abilities.add(new Ice());
            }
            if (type == 2) {
                abilities.add(new Earth());
            }
        }
    }

    public String toCharacter() {
        return "E";
    }

    public void receiveDamage(int val) {
        Random rand = new Random();
        // 50% sanse sa injumatatesc damage-ul
        int chance = rand.nextInt(2);
        if (chance == 1) {
            curr_life = curr_life - val / 2;
        } else {
            curr_life = curr_life - val;
        }
    }

    public int getDamage() {
        Random rand = new Random();
        // 50% sanse sa dublez damage-ul
        int chance = rand.nextInt(2);
        int damage = 7;
        if (chance == 1) {
            return 2 * damage;
        } else {
            return damage;
        }
    }
}
