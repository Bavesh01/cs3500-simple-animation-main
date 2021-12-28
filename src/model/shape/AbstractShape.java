package model.shape;

import java.util.function.Function;
import model.utils.Triplet;

/**
 * Represents a basic shape, with data regarding it's position, size, color.
 * Furthermore, data about it's coordinate type is also noted.
 * Used for making incremental mutation during an animation.
 */
public class AbstractShape implements IShape {

  protected final String shapeName;
  protected int xPos;
  protected int yPos;
  protected int width;
  protected int height;
  protected CoordinateType coordType;
  protected int r;
  protected int g;
  protected int b;
  protected boolean isVisible;

  /**
   * Constructor for the object.
   * @param shapeName the name of the shape.
   */
  public AbstractShape(String shapeName) {
    if (shapeName == null || shapeName.equals("")) {
      throw new IllegalArgumentException(
          "Shape name cannot be null or empty");
    }
    this.shapeName = shapeName;
    xPos = 0;
    yPos = 0;
    width = 0;
    height = 0;
    coordType = CoordinateType.CORNER;
    r = 0;
    g = 0;
    b = 0;
    isVisible = false;
  }


  @Override
  public void create(int xPos, int yPos, int width, int height,
      CoordinateType coordType, int r, int g, int b) {

    this.xPos   = xPos;
    this.yPos   = yPos;
    this.width  = enforceArguments(width, "width");
    this.height = enforceArguments(height, "height");
    this.coordType = coordType;
    this.r = enforceArguments(r, "red");
    this.g = enforceArguments(g, "green");
    this.b = enforceArguments(b, "blue");
    this.isVisible = true;
  }

  /**
   * Enforces arguments.
   * @param x the integer that is sent in.
   * @param msg the name of the variable.
   * @return x.
   * @throws IllegalArgumentException if x < 0 or if its a color & x > 256
   */
  private int enforceArguments(int x, String msg) {
    if (x < 0) {
      throw new IllegalArgumentException(
          msg + " can't be negative!");
    }
    if ("width height".contains(msg) && x == 0) {
      throw new IllegalArgumentException(
          msg + " can't be zero!");
    }
    if ("red green blue".contains(msg) && x > 255) {
      throw new IllegalArgumentException(
          msg + " should be less than 256!");
    }
    else {
      return x;
    }
  }

  ////// setters

  @Override
  public void move(int newX, int newY)
      throws IllegalArgumentException {
    this.create(newX, newY, width, height, coordType, r, g, b);
  }

  @Override
  public void shift(int deltaX, int deltaY)
      throws IllegalArgumentException {
    this.create(xPos += deltaX, yPos += deltaY,
        width, height, coordType, r, g, b);
  }

  @Override
  public void setColor(int r, int g, int b)
      throws IllegalArgumentException {
    this.create(xPos, yPos, width, height, coordType, r, g, b);
  }

  @Override
  public void shiftColor(int deltaR, int deltaG, int deltaB) {
    Function<Integer, Integer> c = (i) -> Math.min(255, Math.max(0, i));
    this.create(xPos, yPos, width, height, coordType,
        c.apply(r += deltaR), c.apply(g += deltaG), c.apply(b += deltaB));
  }

  @Override
  public void setSize(int newWidth, int newHeight)
      throws IllegalArgumentException {
    this.create(xPos, yPos, newWidth, newHeight, coordType, r, g, b);
  }

  @Override
  public void shiftSize(int deltaWidth, int deltaHeight)
      throws IllegalArgumentException {
    this.create(xPos, yPos,
        width += deltaWidth, height += deltaHeight,
        coordType, r, g, b);
  }

  /// GETTERS

  @Override
  public String getName() {
    return this.shapeName;
  }

  @Override
  public int getX() {
    return this.xPos;
  }

  @Override
  public int getY() {
    return this.yPos;
  }

  @Override
  public Triplet<Integer,Integer,Integer> getColor() {
    return new Triplet<>(r,g,b);
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public CoordinateType getCoordType() {
    return this.coordType;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i : new int[]{xPos, yPos, width, height, r, g, b}) {
      builder.append(" ").append(String.format("%03d", i));
    }
    return builder.substring(1);
  }

  @Override
  public IShape getCopy() {
    IShape shp = new AbstractShape(shapeName);
    shp.create(xPos, yPos, width, height, coordType, r, g, b);
    return shp;
  }

  @Override
  public boolean isVisible() {
    return this.isVisible;
  }

  @Override
  public String getShapeType() {
    return null;
  }
}
