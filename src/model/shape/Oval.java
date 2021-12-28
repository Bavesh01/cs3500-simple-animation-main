package model.shape;

import java.util.Objects;

/**
 * An oval shape.
 */
public class Oval extends AbstractShape implements IShape {

  /**
   * Constructor for the object.
   * @param shapeName the name of the object.
   */
  public Oval(String shapeName) {
    super(shapeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getName(), "oval");
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Oval
        && ((Oval) obj).getName().equals(this.getName());
  }

  @Override
  public IShape getCopy() {
    IShape oval = new Oval(shapeName);

    if (width != 0 && height != 0) {
      oval.create(xPos, yPos, width, height, coordType, r, g, b);
    }
    return oval;
  }

  @Override
  public String getShapeType() {
    return "OVAL";
  }
}
