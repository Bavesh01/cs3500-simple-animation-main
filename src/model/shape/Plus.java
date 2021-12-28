package model.shape;

/**
 * A plus shaped object with equal width and height parameters.
 */
public class Plus extends AbstractShape implements IShape{

  /**
   * Constructor for the plus shape.
   *
   * @param shapeName the name of the shape.
   */
  public Plus(String shapeName) {
    super(shapeName);
  }

  @Override
  public void create(int xPos, int yPos, int width, int height,
      CoordinateType coordType, int r, int g, int b) {
    super.create(xPos, yPos, width, height, coordType, r, g, b);
    if (width != height){
      throw new IllegalArgumentException("width and height must be same for a plus");
    }
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Plus
        && ((Plus) obj).getName().equals(this.getName());
  }

  @Override
  public IShape getCopy() {
    IShape plus = new Plus(shapeName);
    if (width != 0 && height != 0) {
      plus.create(xPos, yPos, width, height, coordType, r, g, b);
    }
    return plus;
  }

  @Override
  public String getShapeType() {
    return "PLUS";
  }
}
