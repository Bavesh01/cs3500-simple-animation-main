package model.direction;

import java.util.HashMap;
import java.util.Map;
import model.shape.IShape;
import model.utils.Triplet;

/**
 * Represents a direction on a shape that changes the color of the shape during an interval.
 */
public class ColorDirection extends AbstractDirection implements IDirection {

  private final int r;
  private final int g;
  private final int b;
  private final Map<Integer, Triplet<Integer, Integer, Integer>> subCommands;

  /**
   * Sets up a direction for changing the color of a shape.
   *
   * @param shape      The shape object that should be moved.
   * @param r          What the red value of the shape should end as.
   * @param g          What the green value of the shape should end as.
   * @param b          What the blue value of the shape should end as.
   * @param startFrame The frame the direction should start.
   * @param endFrame   The frame the direction should end.
   */
  public ColorDirection(IShape shape, int r, int g, int b, int startFrame, int endFrame)
      throws IllegalArgumentException {
    super(shape, startFrame, endFrame, "COLOR");
    DirectionUtils.checkColor(r, g, b);
    this.r = r;
    this.g = g;
    this.b = b;
    this.subCommands = new HashMap<>();
  }

  private ColorDirection(IShape shape, int r, int g, int b, int startFrame, int endFrame,
      Map<Integer, Triplet<Integer, Integer, Integer>> subCommands) {
    super(shape, startFrame, endFrame, "COLOR");
    this.r = r;
    this.g = g;
    this.b = b;
    this.subCommands = subCommands;
  }

  /**
   * Breaks up the large direction into a list of commands that happen on each frame of execution.
   */
  private void generateSubCommands() {
    int totalTicks = this.endFrame - this.startFrame - 1;

    int deltaR = this.r - this.shape.getColor().getValue0();
    int deltaG = this.g - this.shape.getColor().getValue1();
    int deltaB = this.b - this.shape.getColor().getValue2();

    if (totalTicks <= 0) {
      this.subCommands.put(startFrame,
          new Triplet<>(deltaR, deltaG, deltaB));
      return;
    }

    int previousR = 0;
    int previousG = 0;
    int previousB = 0;
    float totalR = 0;
    float totalG = 0;
    float totalB = 0;

    for (int i = 0; i < totalTicks; i++) {
      totalR += deltaR / ((double) totalTicks);
      totalG += deltaG / ((double) totalTicks);
      totalB += deltaB / ((double) totalB);

      float rCap = deltaR <= 0 ? Math.max(deltaR, totalR) : Math.min(deltaR, totalR);
      float gCap = deltaG <= 0 ? Math.max(deltaG, totalG) : Math.min(deltaG, totalG);
      float bCap = deltaB <= 0 ? Math.max(deltaB, totalB) : Math.min(deltaB, totalB);

      int currentR = Math.round(rCap - previousR);
      int currentG = Math.round(gCap - previousG);
      int currentB = Math.round(bCap - previousB);

      this.subCommands.put(startFrame + i, new Triplet<>(currentR, currentG, currentB));

      previousR += currentR;
      previousG += currentG;
      previousB += currentB;
    }
  }

  @Override
  public void processCommandsAtTick(int frame) {
    if (this.subCommands.keySet().size() == 0) {
      this.generateSubCommands();
    }

    Triplet<Integer, Integer, Integer> command
        = this.subCommands.getOrDefault(frame, null);

    if (command == null) {
      return;
    }

    this.shape.shiftColor(command.getValue0(), command.getValue1(), command.getValue2());
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    return DirectionUtils.toString(shape,
        startFrame, endFrame, frameRate, "recolor ",
        shape.getX(), shape.getY(),
        shape.getWidth(), shape.getHeight(),
        r, g, b);
  }

  @Override
  public IDirection getCopy(IShape shape) {
    return new ColorDirection(shape, this.r, this.g, this.b, this.startFrame, this.endFrame,
        this.subCommands);
  }
}
