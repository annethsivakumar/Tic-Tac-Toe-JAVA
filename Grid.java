import java.awt.*;

public class Grid {
  private Color colorTroysGrid2, colorFill; // colors of grid
  private int x, y; // coordinates of upper left corner
  private int row, col; // dimensions
  private int cellSize, cellHeight, cellWidth; // cell sizes
  private int lineThick; // thickness of grid lines

  /**
   * Constructor for objects of class Grid
   */
  public Grid() {
    colorTroysGrid2 = Color.black;
    colorFill = Color.white;
    x = 0;
    y = 0;
    cellHeight = 40;
    cellWidth = 40;
    row = 3;
    col = 3;
    lineThick = 5;
  }

  /**
   * Sets the (x,y) coordinates of the upper left corner of the Grid
   *
   * @param x the new x value for the upper left corner
   * @param y the new y value for the upper left corner
   */
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Sets the color of the grid lines
   *
   * @param lineColor the new color for the grid lines
   */
  public void setColor(Color lineColor) {
    colorTroysGrid2 = lineColor;
  }

  /**
   * Sets the color of the grid lines
   *
   * @param lineColor the new color for the grid lines
   */
  public void setLineColor(Color lineColor) {
    colorTroysGrid2 = lineColor;
  }

  /**
   * Sets the color of the cells
   *
   * @param cellColor the new color for the interior of the cells
   */
  public void setFillColor(Color cellColor) {
    colorFill = cellColor;
  }

  /**
   * Sets the color of the cells
   *
   * @param cellColor the new color for the interior of the cells
   */
  public void setCellColor(Color cellColor) {
    colorFill = cellColor;
  }

  /**
   * Sets the number of rows and columns
   *
   * @param r the new number of rows
   * @param c the new number of columns
   */
  public void setDimensions(int r, int c) {
    row = r;
    col = c;
  }

  /**
   * Sets the cell size, square
   *
   * @param size new value for the height and width of the cells
   */
  public void setCellSize(int size) {
    cellWidth = size;
    cellHeight = size;
  }

  /**
   * Sets the cell size, rectangle
   *
   * @param width  the new value for the width of the cells
   * @param height the new value for the height of the cells
   */
  public void setCellSize(int width, int height) {
    cellWidth = width;
    cellHeight = height;
  }

  /**
   * Sets the thickness of the grid lines
   *
   * @param thickness new value the thickness of the grid lines
   */
  public void setLineThickness(int thickness) {
    lineThick = thickness;
  }

  /**
   * Gets the x cooridinate of the left side of the cell
   *
   * @param column the column that the x value will calculated for
   * @return the x cooridinate of the left side of the cell
   */
  public int getCellX(int column) {
    int cellX;
    cellX = (cellWidth * (column - 1)) + x + (lineThick * (column));
    return cellX;
  }

  /**
   * Gets the cell width
   *
   * @return the width of a cell
   */
  public int getCellWidth() {
    return cellWidth;
  }

  /**
   * Gets the cell height
   *
   * @return the height of a cell
   */
  public int getCellHeight() {
    return cellHeight;
  }

  /**
   * Gets the x cooridinate of the right side of the cell
   *
   * @param column the column that the x value will calculated for
   * @return the x cooridinate of the right side of the cell
   */
  public int getCellRightSideX(int column) {
    int cellX;
    cellX = (cellWidth * column) + x + (lineThick * (column));
    return cellX;
  }

  /**
   * Gets the y cooridinate of the top of the cell
   *
   * @param row the column that the y value will calculated for
   * @return the y cooridinate of the top of the cell
   */
  public int getCellY(int row) {
    int cellY;
    cellY = (cellHeight * (row - 1)) + y + (lineThick * row);
    return cellY;
  }

  /**
   * Gets the y cooridinate of the bottom of the cell
   *
   * @param row the column that the y value will calculated for
   * @return the y cooridinate of the bottom of the cell
   */
  public int getCellBottomY(int row) {
    int cellY;
    cellY = (cellHeight * row) + y + (lineThick * row);
    return cellY;
  }

  /**
   * Gets the column number for a given x cooridinate
   *
   * @param x the x value that is used to determine the column number
   * @return the column number of which the x cooridinate is located
   */
  public int getColumn(int x) {
    if (x >= this.x) {
      double colNum;
      colNum = ((x - this.x) - lineThick) / (cellWidth + lineThick);

      return (int) (colNum + 1);
    } else {
      return 0;
    }
  }

  /**
   * Gets the row number fo a given y cooridinate
   *
   * @param y the y value that is used to determine the row number
   * @return the row number of which the y cooridinate is located
   */
  public int getRow(int y) {
    if (y >= this.y) {
      double rowNum;
      rowNum = ((y - this.y) - lineThick) / (cellHeight + lineThick);

      return (int) (rowNum + 1);
    } else {
      return 0;
    }
  }

  /**
   * Gets the row number fo a given y cooridinate
   *
   * @param y the y value that is used to determine the row number
   * @return the row number of which the y cooridinate is located
   */
  public boolean contains(int x, int y) {
    if (getRow(y) > 0 && getRow(y) <= row && getColumn(x) > 0 && getColumn(x) <= col) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Draws the Grid
   *
   * @param c the Graphics Context to draw on
   */
  public void draw(Graphics g) {
    int cellHeight1, cellWidth1;
    g.setColor(colorTroysGrid2);

    g.fillRect(x, y, (col * cellWidth) + ((col + 1) * lineThick), lineThick);
    g.fillRect(x, y, lineThick, (row * cellHeight) + ((row + 1) * lineThick));
    cellHeight1 = cellHeight + lineThick;
    cellWidth1 = cellWidth + lineThick;
    for (int i = 1; i <= row; i = i + 1) {
      g.fillRect(x, cellHeight1 + y, (col * cellWidth) + ((col + 1) * lineThick), lineThick);
      cellHeight1 = cellHeight1 + cellHeight + lineThick;
    }
    for (int i = 1; i <= col; i = i + 1) {
      g.fillRect(cellWidth1 + x, y + lineThick, lineThick, (row * cellHeight) + ((row) * lineThick));
      cellWidth1 = cellWidth1 + cellWidth + lineThick;
    }

    for (int i = 1; i <= col; i++) {
      for (int z = 1; z <= row; z++) {
        g.setColor(colorFill);
        g.fillRect(getCellX(i), getCellY(z), cellWidth, cellHeight);
      }
    }
  }

}