package model.direction;

import java.util.HashMap;
import java.util.Map;
import model.shape.IShape;
import model.utils.Pair;

/**
 * Represents a direction on a shape that changes the size of the shape during an interval.
 */
public class ResizeDirection extends AbstractDirection implements IDirection {

  private final int newWidth;
  private final int newHeight;
  private final Map<Integer, Pair<Integer, Integer>> subCommands;

  /**
   * Sets up a direction for changing the size of a shape.
   *
   * @param shape      The shape to be transformed.
   * @param newWidth   The width the shape should end at.
   * @param newHeight  The height the shape should end at.
   * @param startFrame The frame that the transformation of the shape should start on.
   * @param endFrame   The frame that the transformation of the shape should end on.
   */
  public ResizeDirection(IShape shape, int newWidth, int newHeight, int startFrame, int endFrame)
      throws IllegalArgumentException {
    super(shape, startFrame, endFrame, "RESIZE");
    DirectionUtils.checkLengths(newWidth, newHeight);
    this.newWidth = newWidth;
    this.newHeight = newHeight;
    this.subCommands = new HashMap<>();
  }

  /**
   * Used for making a copy of this direction.
   *
   * @param shape      The shape to be transformed.
   * @param newWidth   The width the shape should end at.
   * @param newHeight  The height the shape should end at.
   * @param startFrame The frame that the transformation of the shape should start on.
   * @param endFrame   The frame that the transformation of the shape should end on.
   * @param subCommands In case commands have already been generated, the subcommands.
   */
  private ResizeDirection(IShape shape, int newWidth, int newHeight, int startFrame, int endFrame,
      Map<Integer, Pair<Integer, Integer>> subCommands) {
    super(shape, startFrame, endFrame, "RESIZE");
    DirectionUtils.checkLengths(newWidth, newHeight);
    this.newWidth = newWidth;
    this.newHeight = newHeight;
    this.subCommands = subCommands;
  }

  /**
   * Breaks up the large direction into a list of commands that happen on each frame of execution.
   */
  private void generateSubCommands() {
    int totalTicks = this.endFrame - this.startFrame - 1;

    int deltaWidth = this.newWidth - shape.getWidth();
    int deltaHeight = this.newHeight - shape.getHeight();

    if (totalTicks <= 0) {
      this.subCommands.put(startFrame, new Pair<>(deltaWidth, deltaHeight));
      return;
    }

    int previousWidth = 0;
    int previousHeight = 0;
    float totalWidth = 0;
    float totalHeight = 0;

    for (int i = 0; i < totalTicks; i++) {
      totalWidth += deltaWidth / ((double) totalTicks);
      totalHeight += deltaHeight / ((double) totalTicks);

      float widthCap = deltaWidth < 0
          ? Math.max(deltaWidth, totalWidth) : Math.min(deltaWidth, totalWidth);
      float heightCap = deltaHeight < 0
          ? Math.max(deltaHeight, totalHeight) : Math.min(deltaHeight, totalHeight);

      int currentWidth = Math.round(widthCap - previousWidth);
      int currentHeight = Math.round(heightCap - previousHeight);

      this.subCommands.put(startFrame + i, new Pair<>(currentWidth, currentHeight));

      previousWidth += currentWidth;
      previousHeight += currentHeight;
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

    this.shape.shiftSize(command.getValue0(), command.getValue1());
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    return DirectionUtils.toString(shape,
        startFrame, endFrame, frameRate, "resize  ",
        shape.getX(), shape.getY(), newWidth, newHeight,
        shape.getColor().getValue0(),
        shape.getColor().getValue1(),
        shape.getColor().getValue2());
  }

  @Override
  public IDirection getCopy(IShape shape) {
    return new ResizeDirection(shape, this.newWidth, this.newHeight, this.startFrame, this.endFrame,
        this.subCommands);
  }
}
