package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Rectangle;

/**
 * Generates a parsable animation file visualizing the Bogosort algorithm attempting to find the
 * correct ordering of a set of shapes.
 */
public class BogoSortGenerator {

  static int numberOfItems = 5;
  static int highestNumber = 380;

  static int frame = 1;
  static int canvasWidth = 500;

  static int barWidth = (int) Math.floor((canvasWidth + 0.0) / numberOfItems);
  static int canvasHeight = highestNumber;

  static int travelTime = 5;
  static int waitTime = 5;

  static String outputFileName = "bogoSort3.txt";

  /**
   * Generates a Bogosort animation file based off the variables set above.
   * @param args any runtime arguments (there won't be any for this method).
   */
  public static void main(String[] args) {

    FileWriter ap;

    Consumer<String> error =
        (s) -> JOptionPane.showMessageDialog(new JFrame(), s, "Inane error",
            JOptionPane.ERROR_MESSAGE);

    try {
      ap = new FileWriter(outputFileName);
    } catch (IOException e) {
      error.accept(e.getMessage());
      return;
    }

    StringBuilder builder = new StringBuilder();

    builder.append(String.format("canvas 0 0 %s %s\n", canvasWidth, canvasHeight));

    List<IShape> shapes = new ArrayList<>();

    for (int i = 0; i < numberOfItems; i++) {

      Random randomizer = new Random();
      IShape tempShape = new Rectangle("rect" + i);
      int tempNumber = randomizer.nextInt(highestNumber - 1) + 1;
      tempShape.create(
          i * barWidth, canvasHeight - tempNumber,
          barWidth, tempNumber,
          CoordinateType.CORNER,
          randomizer.nextInt(255),
          randomizer.nextInt(255),
          randomizer.nextInt(255));

      shapes.add(tempShape);
    }

    shapes.forEach(s -> builder.append("shape " + s.getName() + " rectangle\n"));

    while (!isSorted(shapes)) {
      Collections.shuffle(shapes);
      List<IShape> newShapes = setShapeXs(shapes);
      drawShapes(shapes, newShapes, builder, frame);

      shapes = newShapes;
      frame += travelTime + waitTime;
    }

    if (isSorted(shapes)) {
      List<IShape> newShapes = new ArrayList<>();
      shapes.forEach(s -> newShapes.add(s.getCopy()));
      newShapes.forEach(s -> s.setColor(0, 200, 0));
      drawShapes(shapes, newShapes, builder, frame);
    }

    try {
      ap.append(builder.toString());
      ap.flush();
    } catch (IOException e) {
      error.accept(e.getMessage());
      return;
    }

  }

  private static List<IShape> setShapeXs(List<IShape> shapes) {
    List<IShape> newShapes = new ArrayList<>();

    for (int i = 0; i < shapes.size(); i++) {
      IShape currentShape = shapes.get(i).getCopy();

      currentShape.move(i * barWidth, currentShape.getY());

      newShapes.add(currentShape);
    }
    return newShapes;
  }

  private static void drawShapes(List<IShape> shapes, List<IShape> newShapes,
      StringBuilder builder, int startFrame) {
    Map<String, IShape> shapeMap = new HashMap<>();
    newShapes.forEach(s -> shapeMap.put(s.getName(), s));

    for (IShape shape : shapes) {
      builder.append(
          String.format("motion %s %s %s  %s %s\n",
              shape.getName(),
              startFrame, shape.toString(),
              startFrame + waitTime,
              shape.toString()));

      builder.append(
          String.format("motion %s %s %s  %s %s\n",
              shape.getName(),
              startFrame + waitTime, shape.toString(),
              startFrame + waitTime + travelTime,
              shapeMap.get(shape.getName()).toString()));
    }
  }

  private static boolean isSorted(List<IShape> shapes) {
    int lastHeight = -1;

    for (IShape shape : shapes) {
      if (shape.getHeight() < lastHeight) {
        return false;
      } else {
        lastHeight = shape.getHeight();
      }
    }

    return true;
  }
}
