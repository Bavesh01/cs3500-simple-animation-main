package model.direction;

import model.shape.AbstractShape;
import model.shape.CoordinateType;
import model.shape.IShape;

/**
 * Represents a direction on a shape that initializes the shape at a frame.
 */
public class CreateDirection extends AbstractDirection implements IDirection {

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int r;
  private final int g;
  private final int b;
  private final CoordinateType coordinateType;
  private final AbstractShape finalShape;

  /**
   * Sets up a direction for initializing a shape.
   *
   * @param shape          The shape to be initialized.
   * @param frame          The frame the shape should be initialized on.
   * @param x              The initial X coordinate of the shape.
   * @param y              The initial Y coordinate of the shape.
   * @param width          The initial width of the shape.
   * @param height         The initial height of the shape.
   * @param r              The initial red value of the shape.
   * @param g              The initial green value of the shape.
   * @param b              The initial blue value of the shape.
   * @param coordinateType Where the shape should be placed relative to its coordinate. CENTER = the
   *                       coordinate should be in the middle of the shape. CORNER = the coordinate
   *                       should be in the bottom left corner of the shape.
   */
  public CreateDirection(
      IShape shape,
      int frame,
      int x, int y,
      int width, int height,
      int r, int g, int b,
      CoordinateType coordinateType) throws IllegalArgumentException {
    super(shape, frame, frame, "CREATE");
    DirectionUtils.checkLengths(width, height);
    DirectionUtils.checkColor(r, g, b);
    if (coordinateType == null) {
      throw new IllegalArgumentException("Coordinate type cannot be null.");
    }

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.r = r;
    this.g = g;
    this.b = b;
    this.coordinateType = coordinateType;

    this.finalShape = new AbstractShape(shape.getName());
    finalShape.create(this.x, this.y, this.width, this.height, this.coordinateType, r, g, b);
  }

  @Override
  public void processCommandsAtTick(int frame) {
    if (frame == this.startFrame) {
      this.shape.create(this.x, this.y, this.width, this.height, this.coordinateType, r, g, b);
    }
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    StringBuilder builder = new StringBuilder();
    builder.append("create  ")
        .append(shape.getName()).append(" ")
        .append(String.format("%.2f", startFrame / (float) frameRate)).append(" ")
        .append(this.finalShape.toString());

    return builder.toString();
  }

  @Override
  public IDirection getCopy(IShape shape) {
    return new CreateDirection(shape, this.startFrame, this.x, this.y, this.width, this.height,
        this.r,
        this.g, this.b, this.coordinateType);
  }
}
