import java.util.ArrayList;

public class Inventory {
    public ArrayList<Potion> potion_list;
    public int max_weight;
    public int nr_coins;

    public Inventory() {
        this.potion_list = new ArrayList<Potion>();
        this.max_weight = 10000;
        this.nr_coins = 10;
    }

    public void addPotion(Potion potion) {
        potion_list.add(potion);
    }

    public void removePotion(Potion potion) {
        potion_list.remove(potion);
    }

    public int getTotalWeight() {
        int weight = 0;
        for (Potion pt : potion_list) {
            weight = weight + pt.getWeight();
        }
        return weight;
    }
}
