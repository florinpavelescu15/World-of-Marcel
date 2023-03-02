public abstract class Character extends Entity {
    public String name;
    public int curr_x;
    public int curr_y;
    public Inventory inventory;
    public int curr_experience;
    public int curr_level;
    public int strength;
    public int charisma;
    public int dexterity;

    public abstract String toString();

    public void buyPotion(Potion potion) {
        if (potion.getWeight() + inventory.getTotalWeight() <= inventory.max_weight && inventory.nr_coins >= potion.getPrice()) {
            inventory.addPotion(potion);
            inventory.nr_coins = inventory.nr_coins - potion.getPrice();
        }
    }

    public void printCharacter() {
        System.out.println(name + " [life: " + curr_life + ", mana: " + curr_mana + ", money: " + inventory.nr_coins + "]");
    }
}
