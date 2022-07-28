import java.util.ArrayList;
import java.util.Random;

public class Shop implements CellElement {
    public ArrayList<Potion> potions;

    public Shop() {
        Random rand = new Random();
        potions = new ArrayList<Potion>();
        for (int i = 1; i <= 3; i++) {
            int type = rand.nextInt(2);
            if (type == 1)
                potions.add(new HealthPotion());
            else
                potions.add(new ManaPotion());
        }
    }

    public String toCharacter() {
        return "S";
    }

    public Potion selectPotion(int index) {
        return potions.remove(index);
    }
}
