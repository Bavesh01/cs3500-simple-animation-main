package model.direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.shape.IShape;

/**
 * Utils class for storing methods that are helpful across several Directions.
 */
public class DirectionUtils {

  /**
   * Calculates whether a certain frame should add an additional frame from its remainder.
   *
   * @param applied        Number of remainder frames that have already been applied.
   * @param totalRemainder Total number of remainder frames that have to be distributed.
   * @param currentFrame   Current frame that we're creating a subcommand for.
   * @param totalFrames    Total number of frames in the direction.
   * @param delta          the difference between the start position of a value and where it has to
   *                       end.
   * @return the amount of remainder frames that should be added here, (either 1,-1,or 0).
   */
  public static int calculateRemainderFrame(int applied, int totalRemainder,
      int currentFrame, int totalFrames, int delta) {
    if (applied < totalRemainder && currentFrame % Math.floor(totalFrames / totalRemainder) == 0) {
      if (delta > 0) {
        return 1;
      } else {
        return -1;
      }
    }
    return 0;
  }


  /**
   * Ensures lengths are more than 1.
   *
   * @param width  Shape's width.
   * @param height Shape's width.
   * @throws IllegalArgumentException if params < 1.
   */
  static void checkLengths(int width, int height) {
    if (width < 1) {
      throw new IllegalArgumentException("Width must be 1 or greater.");
    }
    if (height < 1) {
      throw new IllegalArgumentException("Height must be 1 or greater.");
    }
  }

  /**
   * Ensures RGB values are betweeen (0, 255) inclusive.
   *
   * @param r Red value.
   * @param g Green value.
   * @param b Blue value.
   * @throws IllegalArgumentException if the values are out of range.
   */
  static void checkColor(int r, int g, int b) {
    List<String> cols = new ArrayList<>(Arrays.asList("R", "G", "B"));
    List<Integer> ints = new ArrayList<>(Arrays.asList(r, g, b));

    for (int i = 0; i < 3; i++) {
      if (ints.get(i) < 0 || ints.get(i) > 255) {
        throw new IllegalArgumentException(cols.get(i)
            + " value out of range, must be (0,255).");
      }
    }
  }

  /**
   * Spits out a formatted version of a direction based on given parameters.
   * @param shape The shape tied to the direction.
   * @param sf The start frame of the direction.
   * @param ef The end frame of the direction.
   * @param fps The frameRate the direction is running at.
   * @param type the type of shape.
   * @param nums any variables about the direction we want to plot.
   * @return The direction with given parameters formatted as a String.
   */
  static String toString(IShape shape, int sf, int ef, int fps,
      String type, int... nums) {
    StringBuilder builder = new StringBuilder();

    builder.append(type)
        .append(shape.getName()).append(" ")
        .append(String.format("%.2f", sf / (float) fps)).append(" ")
        .append(shape.toString()).append(" ")
        .append(String.format("%.2f", ef / (float) fps));

    for (int i : nums) {
      builder.append(" ").append(String.format("%03d", i));
    }

    return builder.toString();
  }
}
