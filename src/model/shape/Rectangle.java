package model.shape;

import java.util.Objects;

/**
 * Represents a rectangle.
 */
public class Rectangle extends AbstractShape implements IShape {

  /**
   * Constructor for the rectangle.
   * @param shapeName name of the shape.
   */
  public Rectangle(String shapeName) {
    super(shapeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getName(), "rectangle");
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Rectangle
        && ((Rectangle) obj).getName().equals(this.getName());
  }

  @Override
  public IShape getCopy() {
    IShape rect = new Rectangle(shapeName);

    if (width != 0 && height != 0) {
      rect.create(xPos, yPos, width, height, coordType, r, g, b);
    }
    return rect;
  }

  @Override
  public String getShapeType() {
    return "RECTANGLE";
  }
}
