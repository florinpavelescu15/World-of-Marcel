public class CharacterFactory {
    // folosesc sablonul de proiectare Factory Pattern
    public Character getCharacter(String characterType, String name, int experience, int level) {
        if (characterType == null) {
            return null;
        }
        if (characterType.equalsIgnoreCase("Warrior")) {
            return new Warrior(name, experience, level);
        } else if (characterType.equalsIgnoreCase("Rogue")) {
            return new Rogue(name, experience, level);
        } else if (characterType.equalsIgnoreCase("Mage")) {
            return new Mage(name, experience, level);
        }
        return null;
    }
}