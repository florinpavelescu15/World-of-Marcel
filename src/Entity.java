import java.util.ArrayList;

public abstract class Entity {
    public ArrayList<Spell> abilities;
    public int curr_life;
    public int max_life;
    public int curr_mana;
    public int max_mana;
    public boolean fire;
    public boolean ice;
    public boolean earth;

    public void regenerateLife(int val) {
        if (val > max_life - curr_life) {
            curr_life = max_life;
        } else {
            curr_life = curr_life + val;
        }
    }

    public void regenerateMana(int val) {
        if (val > max_mana - curr_mana) {
            curr_mana = max_mana;
        } else {
            curr_mana = curr_mana + val;
        }
    }

    public void useAbility(Spell ability, Entity enemy) {
        if (curr_mana >= ability.mana_cost) {
            ability.useSpell(enemy);
            curr_mana = curr_mana - ability.mana_cost;
        } else {
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("Message: You don't have enough mana to use the ability!");
        }
    }

    // metoda accept (Visitor Pattern)
    public abstract void receiveDamage(int val);

    public abstract int getDamage();
}
