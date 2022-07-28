import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    ArrayList<Account> accounts;
    Map<CellEnum, List<String>> stories;
    // folosesc sablonul de proiectare Singleton Pattern
    private static Game instance;

    // constructor privat, pentru a nu putea instantia direct
    private Game() {
    }

    public static Game getInstance() {
        // daca nu exista o instanta Game, o instantiez
        // altfel, returnez instanta existenta
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void extractAccounts(JSONObject account) {
        // parcurg fisierul JSON ce contine datele conturilor
        JSONArray accountArray = (JSONArray) account.get("accounts");
        accounts = new ArrayList<Account>();
        for (int i = 0; i < accountArray.size(); i++) {
            Account new_account = new Account();
            new_account.characters = new ArrayList<Character>();
            JSONObject acc = (JSONObject) accountArray.get(i);

            JSONObject crd = (JSONObject) acc.get("credentials");
            Credentials credentials = new Credentials();
            credentials.setEmail(crd.get("email").toString());
            credentials.setPassword(crd.get("password").toString());

            JSONArray fav_gamesArray = (JSONArray) acc.get("favorite_games");
            TreeSet<String> fav_gam = new TreeSet<String>();
            for (int j = 0; j < fav_gamesArray.size(); j++) {
                String game = fav_gamesArray.get(j).toString();
                fav_gam.add(game);
            }
            new_account.info = new Account.Information.InformationBuilder()
                    .credentials(credentials)
                    .favourite_games(fav_gam)
                    .name(acc.get("name").toString())
                    .country(acc.get("country").toString())
                    .build();

            JSONArray chrArray = (JSONArray) acc.get("characters");
            for (int j = 0; j < chrArray.size(); j++) {
                JSONObject chr = (JSONObject) chrArray.get(j);
                CharacterFactory factory = new CharacterFactory();
                Character new_character = factory.getCharacter(chr.get("profession").toString(), chr.get("name").toString(), Integer.parseInt(chr.get("experience").toString()), Integer.parseInt(chr.get("level").toString()));
                new_account.characters.add(new_character);
            }
            new_account.number_of_games = Integer.parseInt(acc.get("maps_completed").toString());

            // completez lista de conturi cu datele obtinute
            this.accounts.add(new_account);
        }
    }

    public void extractStories(JSONObject story) {
        // parcurg fisierul JSON ce contine povestile
        JSONArray storyArray = (JSONArray) story.get("stories");
        stories = new Hashtable<CellEnum, List<String>>();
        ArrayList<String> enemy_stories = new ArrayList<String>();
        ArrayList<String> empty_stories = new ArrayList<String>();
        ArrayList<String> finish_stories = new ArrayList<String>();
        ArrayList<String> shop_stories = new ArrayList<String>();
        for (int i = 0; i < storyArray.size(); i++) {
            JSONObject st = (JSONObject) storyArray.get(i);
            if (st.get("type").toString().equals("ENEMY")) {
                enemy_stories.add(st.get("value").toString());
            }

            if (st.get("type").toString().equals("EMPTY")) {
                empty_stories.add(st.get("value").toString());
            }

            if (st.get("type").toString().equals("FINISH")) {
                finish_stories.add(st.get("value").toString());
            }

            if (st.get("type").toString().equals("SHOP")) {
                shop_stories.add(st.get("value").toString());
            }
        }

        // completez lista de povesti
        stories.put(CellEnum.ENEMY, enemy_stories);
        stories.put(CellEnum.EMPTY, empty_stories);
        stories.put(CellEnum.FINISH, finish_stories);
        stories.put(CellEnum.SHOP, shop_stories);
    }

    public Account logIn(String my_email, String my_password) {
        for (Account acc : accounts) {
            if (acc.info.getCredentials().getEmail().equals(my_email) && acc.info.getCredentials().getPassword().equals(my_password)) {
                return acc;
            }
        }
        return null;
    }

    public Character searchCharacter(String my_name, Account my_account) {
        for (Character chr : my_account.characters) {
            if (chr.name.equals(my_name)) {
                return chr;
            }
        }
        return null;
    }

    public void run() {
        // construiesc jocul cu datele citite din JSON
        try (FileReader fileReader = new FileReader("src/main/java/accounts.json")) {
            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(fileReader);
            extractAccounts((JSONObject) object);
        } catch (ParseException | IOException exception) {
            exception.printStackTrace();
        }

        try (FileReader fileReader1 = new FileReader("src/main/java/stories.json")) {
            JSONParser jsonParser1 = new JSONParser();
            Object object1 = jsonParser1.parse(fileReader1);
            extractStories((JSONObject) object1);
        } catch (ParseException | IOException exception) {
            exception.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("-------------------------------------World of Marcel-------------------------------------");
        System.out.println("CLI or GUI?");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.print("Command: ");
        String choice = scanner.next();
        System.out.println("-----------------------------------------------------------------------------------------");
        try {
            if (choice.toUpperCase(Locale.ROOT).equals("CLI")) {
                System.out.print("Email: ");
                String my_email = scanner.next();
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.print("Password: ");
                String my_password = scanner.next();
                System.out.println("-----------------------------------------------------------------------------------------");
                Account curr_account = logIn(my_email, my_password);
                if (curr_account != null) {
                    System.out.println("Message: Login successful!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("To start the game, choose one of the following characters:");
                    for (Character chr : curr_account.characters) {
                        System.out.println(chr.toString());
                    }
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.print("Enter the name of the selected character: ");
                    String my_name = scanner.next();
                    System.out.println("-----------------------------------------------------------------------------------------");
                    Character curr_character = searchCharacter(my_name, curr_account);
                    if (curr_character != null) {
                        System.out.println("Message: Now, you are " + curr_character.name + ". Succes!");
                        curr_character.curr_y = 0;
                        curr_character.curr_x = 0;
                        Grid grid = Grid.factory(5, 5, curr_character);
                        getInstructions(grid);
                    } else {
                        System.out.println("Message: The chosen character does not exist! Keep trying!");
                        System.out.println("-----------------------------------------------------------------------------------------");
                    }
                } else {
                    System.out.println("Message: Incorrect email or password! Keep trying!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                }
            } else if (choice.toUpperCase(Locale.ROOT).equals("GUI")) {
                LogIn_GUI j = new LogIn_GUI();
                j.setVisible(true);
            } else throw new InvalidCommandException("Invalid command!");
        } catch (InvalidCommandException e) {
            System.out.println("Message: Invalid command!");
            System.out.println("-----------------------------------------------------------------------------------------");
        }
    }

    // convertesc un string la int
    // daca stringul nu e numar intreg, returnez -10
    public int toInt(String strNum) {
        int res;
        if (strNum == null) {
            res = -10;
        } else {
            try {
                res = Integer.parseInt(strNum);
            } catch (NumberFormatException e) {
                res = -10;
            }
        }
        return res;
    }

    // jocul propriu-zis
    // metoda recursiva
    public void getInstructions(Grid grid) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("-------------------------------------World of Marcel-------------------------------------");
        // daca am ajuns la final
        if (grid.curr_cell.type == CellEnum.FINISH) {
            grid.printGrid();
            System.out.println("-----------------------------------------------------------------------------------------");
            grid.curr_character.printCharacter();
            System.out.println("-----------------------------------------------------------------------------------------");
            printStory(grid.curr_cell);
            System.out.println("Message: Congratulations! You've reached FINISH!");
            System.out.println("-----------------------------------------------------------------------------------------");
        } // daca viata curenta a ajuns sub 0
        else if (grid.curr_character.curr_life <= 0) {
            System.out.println("Message: I'm sorry, you were defeated! Keep trying!");
            System.out.println("-----------------------------------------------------------------------------------------");
        } else {
            grid.printGrid();
            System.out.println("-----------------------------------------------------------------------------------------");
            grid.curr_character.printCharacter();
            System.out.println("-----------------------------------------------------------------------------------------");
            printStory(grid.curr_cell);
            // daca ajung intr-o celula goala
            if (grid.curr_cell.type == CellEnum.EMPTY) {
                if (grid.curr_cell.visited == false) {
                    int coins = rand.nextInt(100);
                    System.out.println("Message: You are in an empty land! You received " + coins + " gold coins as a gift!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    grid.curr_character.inventory.nr_coins += coins;
                } else {
                    System.out.println("Message: You are in an empty land! You've been here before!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                }
            }

            // daca ajung intr-un magazin
            if (grid.curr_cell.type == CellEnum.SHOP) {
                while (true) {
                    System.out.println("Message: You're in a store! Here's what you can buy:");
                    Shop this_shop = (Shop) grid.curr_cell.element;
                    for (int i = 0; i < this_shop.potions.size(); i++) {
                        System.out.println(i + 1 + ". " + this_shop.potions.get(i).toString());
                    }
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("Message: Do you want to buy something?");
                    System.out.println("If YES, enter the potion serial number, if NO, enter 0.");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.print("Command: ");
                    int option = toInt(scanner.next());
                    System.out.println("-----------------------------------------------------------------------------------------");
                    try {
                        if (option == 0) {
                            break;
                        } else if (option > 0 && option <= this_shop.potions.size()) {
                            grid.curr_character.buyPotion(this_shop.selectPotion(option - 1));
                        } else throw new InvalidCommandException("Invalid command!");
                    } catch (InvalidCommandException e) {
                        System.out.println("Message: Invalid command!");
                        System.out.println("-----------------------------------------------------------------------------------------");
                    }
                }
            }

            // daca intalnesc un inamic
            if (grid.curr_cell.type == CellEnum.ENEMY) {
                System.out.println("Message: An enemy has appeared! The battle begins!");
                System.out.println("-----------------------------------------------------------------------------------------");
                Enemy enemy = (Enemy) grid.curr_cell.element;
                int round = 0;
                // lupta
                while (grid.curr_character.curr_life > 0 && enemy.curr_life > 0) {
                    if (round % 2 == 0) { // randul meu
                        grid.curr_character.printCharacter();
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Enemy [life: " + enemy.curr_life + ", fire: " + enemy.fire + ", ice: " + enemy.ice + ", earth: " + enemy.earth + "]");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Options:");
                        System.out.println(" * Press 1 to go attack enemy.");
                        System.out.println(" * Press 2 to go use an ability.");
                        System.out.println(" * Press 3 to go use a potion.");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.print("Command: ");
                        int my_option = toInt(scanner.next());
                        System.out.println("-----------------------------------------------------------------------------------------");
                        try {
                            if (my_option == 1) {
                                enemy.receiveDamage(grid.curr_character.getDamage());
                            } else if (my_option == 2) {
                                System.out.println("Choose one of the following abilities:");
                                for (int i = 0; i < grid.curr_character.abilities.size(); i++) {
                                    System.out.println(i + 1 + ". " + grid.curr_character.abilities.get(i).toString());
                                }
                                System.out.println("-----------------------------------------------------------------------------------------");
                                System.out.print("Command: ");
                                int my_opt = toInt(scanner.next());
                                System.out.println("-----------------------------------------------------------------------------------------");
                                try {
                                    if (my_opt > 0 && my_opt <= grid.curr_character.abilities.size()) {
                                        grid.curr_character.useAbility(grid.curr_character.abilities.remove(my_opt - 1), enemy);
                                    } else throw new InvalidCommandException("Invalid command");
                                } catch (InvalidCommandException e) {
                                    System.out.println("Message: Invalid command!");
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                }

                            } else if (my_option == 3) {
                                System.out.println("Choose one of the following potions:");
                                for (int i = 0; i < grid.curr_character.inventory.potion_list.size(); i++) {
                                    System.out.println(i + 1 + ". " + grid.curr_character.inventory.potion_list.get(i).toString());
                                }
                                System.out.println("-----------------------------------------------------------------------------------------");
                                System.out.print("Command: ");
                                int my_opt = toInt(scanner.next());
                                System.out.println("-----------------------------------------------------------------------------------------");
                                try {
                                    if (my_opt > 0 && my_opt <= grid.curr_character.inventory.potion_list.size()) {
                                        grid.curr_character.inventory.potion_list.get(my_opt - 1).usePotion(grid.curr_character);
                                        grid.curr_character.inventory.removePotion(grid.curr_character.inventory.potion_list.get(my_opt - 1));
                                    } else throw new InvalidCommandException("Invalid command!");
                                } catch (InvalidCommandException e) {
                                    System.out.println("Message: Invalid command!");
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                }
                            } else throw new InvalidCommandException("Invalid command!");
                        } catch (InvalidCommandException e) {
                            System.out.println("Message: Invalid command!");
                            System.out.println("-----------------------------------------------------------------------------------------");
                        }
                    } else { // randul inamicului
                        if (enemy.curr_mana > 30 && enemy.abilities.size() > 0) {
                            enemy.useAbility(enemy.abilities.remove(rand.nextInt(enemy.abilities.size())), grid.curr_character);
                        } else {
                            grid.curr_character.receiveDamage(enemy.getDamage());
                        }
                    }
                    round++;
                }
                grid.curr_character.printCharacter();
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("Enemy [life: " + enemy.curr_life + ", fire: " + enemy.fire + ", ice: " + enemy.ice + ", earth: " + enemy.earth + "]");
                System.out.println("-----------------------------------------------------------------------------------------");
                if (grid.curr_character.curr_life > 0) {
                    System.out.println("Message: The battle is over! Congratulations! You defeated the enemy!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                } else {
                    System.out.println("Message: The battle is over! I'm sorry, you were defeated! Keep trying!");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    return;
                }
            }
            System.out.println("Options:");
            System.out.println(" * Press N to go north.");
            System.out.println(" * Press S to go south.");
            System.out.println(" * Press E to go east.");
            System.out.println(" * Press V to go west.");
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.print("Command: ");
            String next_instruction = scanner.next();
            try {
                if (next_instruction.equals("N")) {
                    grid.goNorth();
                } else if (next_instruction.equals("S")) {
                    grid.goSouth();
                } else if (next_instruction.equals("E")) {
                    grid.goEast();
                } else if (next_instruction.equals("V")) {
                    grid.goWest();
                } else throw new InvalidCommandException("Invalid command!");
            } catch (InvalidCommandException e) {
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("Message: Invalid command!");
            }
            getInstructions(grid);
        }
    }

    public void printStory(Cell cell) {
        if (cell.visited == false) {
            Random rand = new Random();
            System.out.println("Story: " + stories.get(cell.type).get(rand.nextInt(stories.get(cell.type).size())));
            System.out.println("-----------------------------------------------------------------------------------------");
        }
    }
}
