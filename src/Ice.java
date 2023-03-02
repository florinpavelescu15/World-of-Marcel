public class Ice extends Spell {
    public Ice() {
        damage = 10;
        mana_cost = 7;
    }

    public void useSpell(Entity entity) {
        if (entity.ice == false) {
            entity.receiveDamage(damage);
        }
    }

    public String toString() {
        return "Ice [damage: " + damage + ", cost: " + mana_cost + "]";
    }
}
