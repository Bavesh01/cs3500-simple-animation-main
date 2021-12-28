package model.direction;

import model.shape.IShape;

/**
 * A direction to keep a shape in place. Extends movement since it's technically a movement of no
 * movement.
 */
public class StallDirection extends MoveDirection {

  /**
   * Sets up a direction for stalling a shape.
   *
   * @param shape      The shape to be moved.
   * @param startFrame What frame of the animation the move should start on.
   * @param endFrame   What frame of the animation the move should end on.
   */
  public StallDirection(IShape shape, int startFrame, int endFrame)
      throws IllegalArgumentException {
    super(shape,
        shape == null ? 0 : shape.getX(),
        shape == null ? 0 : shape.getY(),
        startFrame, endFrame);
  }

  /**
   * make sure no subCommands are created so that the shape stays in place.
   */
  @Override
  protected void generateSubCommands() {
    // We want this to not happen on a stall, so we make it empty.
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    return DirectionUtils.toString(shape,
        startFrame, endFrame, frameRate, "stall   ",
        shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight(),
        shape.getColor().getValue0(),
        shape.getColor().getValue1(),
        shape.getColor().getValue2());
  }

  @Override
  public String getType() {
    return "STALL";
  }

  @Override
  public IDirection getCopy(IShape shape) {
    return new StallDirection(shape, this.startFrame, this.endFrame);
  }
}
