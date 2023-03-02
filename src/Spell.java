public abstract class Spell {
    public int damage;
    public int mana_cost;

    // metoda visit (Visitor Pattern)
    public abstract void useSpell(Entity entity);

    public abstract String toString();
}
