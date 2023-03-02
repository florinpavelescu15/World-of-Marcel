import java.util.Random;

public class ManaPotion implements Potion {
    int price;
    int weight;
    int regeneration_value;

    public ManaPotion() {
        Random rand = new Random();
        price = 15;
        weight = 20;
        regeneration_value = 25;
    }

    public String toString() {
        return "ManaPotion [price: " + price + ", weight: " + weight + ", regeneration_value: " + regeneration_value + "]";
    }

    public void usePotion(Character character) {
        character.regenerateMana(regeneration_value);
    }

    public int getPrice() {
        return price;
    }

    public int getRegenerationValue() {
        return regeneration_value;
    }

    public int getWeight() {
        return weight;
    }
}
