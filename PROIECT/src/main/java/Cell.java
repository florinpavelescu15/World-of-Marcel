public class Cell {
    public int x;
    public int y;
    public CellEnum type;
    public CellElement element;
    public boolean visited;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = CellEnum.EMPTY;
        this.visited = false;
        this.element = null;
    }

    public String toString() {
        if (visited == false) {
            return "?";
        }
        if (element == null) {
            return "N";
        }
        if (element != null) {
            return element.toCharacter();
        }
        return "";
    }
}
