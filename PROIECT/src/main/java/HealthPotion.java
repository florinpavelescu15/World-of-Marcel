public class HealthPotion implements Potion {
    public int price;
    public int weight;
    public int regeneration_value;

    public HealthPotion() {
        price = 10;
        weight = 10;
        regeneration_value = 30;
    }

    public String toString() {
        return "HealthPotion [price: " + price + ", weight: " + weight + ", regeneration_value: " + regeneration_value + "]";
    }

    public void usePotion(Character character) {
        character.regenerateLife(regeneration_value);
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
