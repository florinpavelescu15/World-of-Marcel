public class Earth extends Spell {
    public Earth() {
        damage = 15;
        mana_cost = 5;
    }

    public void useSpell(Entity entity) {
        if (entity.earth == false) {
            entity.receiveDamage(damage);
        }
    }

    public String toString() {
        return "Earth [damage: " + damage + ", cost: " + mana_cost + "]";
    }
}
