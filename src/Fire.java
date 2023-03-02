public class Fire extends Spell {
    public Fire() {
        damage = 20;
        mana_cost = 10;
    }

    public void useSpell(Entity entity) {
        if (entity.fire == false) {
            entity.receiveDamage(damage);
        }
    }

    public String toString() {
        return "Fire [damage: " + damage + ", cost: " + mana_cost + "]";
    }
}
