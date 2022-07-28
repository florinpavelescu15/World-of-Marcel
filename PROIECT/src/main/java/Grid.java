import java.util.ArrayList;

public class Grid extends ArrayList<Cell> {
    public int length;
    public int width;
    public Character curr_character;
    public Cell curr_cell;

    private Grid(int length, int width, Character curr_character) {
        this.length = length;
        this.width = width;
        this.curr_character = curr_character;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Cell cell = new Cell(j, i);
                if (length * i + j == 3) {
                    cell.type = CellEnum.SHOP;
                    cell.element = new Shop();
                }
                if (length * i + j == 10) {
                    cell.type = CellEnum.SHOP;
                    cell.element = new Shop();
                }
                if (length * i + j == 5) {
                    cell.type = CellEnum.ENEMY;
                    cell.element = new Enemy();
                }
                if (length * i + j == 13) {
                    cell.type = CellEnum.ENEMY;
                    cell.element = new Enemy();
                }
                if (length * i + j == 8) {
                    cell.type = CellEnum.ENEMY;
                    cell.element = new Enemy();
                }
                if (length * i + j == 15) {
                    cell.type = CellEnum.ENEMY;
                    cell.element = new Enemy();
                }
                if (length * i + j == 24) {
                    cell.type = CellEnum.FINISH;
                    cell.element = null;
                }
                add(cell);
            }
        }
        this.curr_cell = get(length * curr_character.curr_y + curr_character.curr_x);
    }

    // Grid are constructor privat, nu poate fi instantiat direct
    // instantierea se va face prin intermediul metodei factory care apeleaza constructorul
    public static Grid factory(int length, int width, Character curr_character) {
        return new Grid(length, width, curr_character);
    }

    public void printGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (curr_character.curr_x == j && curr_character.curr_y == i) {
                    System.out.print("P ");
                } else {
                    System.out.print(get(length * i + j).toString() + " ");
                }
            }
            System.out.println();
        }
    }

    public void goNorth() {
        get(length * curr_character.curr_y + curr_character.curr_x).visited = true;
        if (curr_cell.y == 0) {
            System.out.println("Sorry, you can't go north!");
        } else {
            curr_cell = get(length * (curr_character.curr_y - 1) + curr_character.curr_x);
            curr_character.curr_x = curr_cell.x;
            curr_character.curr_y = curr_cell.y;
        }
    }

    public void goSouth() {
        get(length * curr_character.curr_y + curr_character.curr_x).visited = true;
        if (curr_cell.y == width - 1) {
            System.out.println("Sorry, you can't go south!");
        } else {
            curr_cell = get(length * (curr_character.curr_y + 1) + curr_character.curr_x);
            curr_character.curr_x = curr_cell.x;
            curr_character.curr_y = curr_cell.y;
        }
    }

    public void goWest() {
        get(length * curr_character.curr_y + curr_character.curr_x).visited = true;
        if (curr_cell.x == 0) {
            System.out.println("Sorry, you can't go west!");
        } else {
            curr_cell = get(length * curr_character.curr_y + curr_character.curr_x - 1);
            curr_character.curr_x = curr_cell.x;
            curr_character.curr_y = curr_cell.y;
        }
    }

    public void goEast() {
        get(length * curr_character.curr_y + curr_character.curr_x).visited = true;
        if (curr_cell.x == length - 1) {
            System.out.println("Sorry, you can't go east!");
        } else {
            curr_cell = get(length * curr_character.curr_y + curr_character.curr_x + 1);
            curr_character.curr_x = curr_cell.x;
            curr_character.curr_y = curr_cell.y;
        }
    }
}
