package model.direction;

import java.util.HashMap;
import java.util.Map;
import model.shape.IShape;
import model.utils.Pair;

/**
 * Represents a direction on a shape that changes the position of the shape during an interval.
 */
public class MoveDirection extends AbstractDirection implements IDirection {

  private final int newX;
  private final int newY;
  private final Map<Integer, Pair<Integer, Integer>> subCommands;

  /**
   * Sets up a direction for moving a shape.
   *
   * @param shape      The shape to be moved.
   * @param newX       Where the shape should end up on the X coordinate at the end of the
   *                   direction.
   * @param newY       Where the shape should end up on the Y coordinate at the end of the
   *                   direction.
   * @param startFrame What frame of the animation the move should start on.
   * @param endFrame   What frame of the animation the move should end on.
   */
  public MoveDirection(IShape shape, int newX, int newY, int startFrame, int endFrame)
      throws IllegalArgumentException {
    super(shape, startFrame, endFrame, "MOVE");
    this.newX = newX;
    this.newY = newY;
    this.subCommands = new HashMap<>();
  }

  private MoveDirection(IShape shape, int newX, int newY, int startFrame, int endFrame,
      Map<Integer, Pair<Integer, Integer>> subCommands)
      throws IllegalArgumentException {
    super(shape, startFrame, endFrame, "MOVE");
    this.newX = newX;
    this.newY = newY;
    this.subCommands = subCommands;
  }

  /**
   * Breaks up the large direction into a list of commands that happen on each frame of execution.
   */
  protected void generateSubCommands() {
    int totalTicks = this.endFrame - this.startFrame - 1;

    int deltaX = this.newX - this.shape.getX();
    int deltaY = this.newY - this.shape.getY();

    if (totalTicks <= 0) {
      this.subCommands.put(startFrame, new Pair<>(deltaX, deltaY));
      return;
    }

    int previousX = 0;
    int previousY = 0;
    float totalX = 0;
    float totalY = 0;

    for (int i = 0; i < totalTicks; i++) {
      totalX += deltaX / ((double) totalTicks);
      totalY += deltaY / ((double) totalTicks);

      float xCap = deltaX <= 0 ? Math.max(deltaX, totalX) : Math.min(deltaX, totalX);
      float yCap = deltaY <= 0 ? Math.max(deltaY, totalY) : Math.min(deltaY, totalY);

      int currentX = Math.round(xCap - previousX);
      int currentY = Math.round(yCap - previousY);

      this.subCommands.put(startFrame + i, new Pair<>(currentX, currentY));

      previousX += currentX;
      previousY += currentY;
    }
  }

  @Override
  public void processCommandsAtTick(int frame) {
    if (this.subCommands.keySet().size() == 0) {
      this.generateSubCommands();
    }

    Pair<Integer, Integer> command = this.subCommands.getOrDefault(frame, null);

    if (command == null) {
      return;
    }

    this.shape.shift(command.getValue0(), command.getValue1());
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    return DirectionUtils.toString(shape,
        startFrame, endFrame, frameRate, "move    ",
        newX, newY, shape.getWidth(), shape.getHeight(),
        shape.getColor().getValue0(),
        shape.getColor().getValue1(),
        shape.getColor().getValue2());
  }

  @Override
  public IDirection getCopy(IShape shape) {
    return new MoveDirection(shape, this.newX, this.newY, this.startFrame, this.endFrame,
        this.subCommands);
  }
}
