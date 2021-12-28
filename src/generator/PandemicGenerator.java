package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * A program to generate animation commands of simulating a pandemic with adjustable values for
 * lethality, spread and population.
 */
public class PandemicGenerator {

  static final int alive = 0;
  static final int affected = 1;
  static final int dead = 2;

  /**
   * Generates a pandemic animation file based off the variables set above and the variables set
   * within the method..
   *
   * @param args any runtime arguments (there won't be any for this method).
   */
  public static void main(String[] args) {
    int frame = 1;
    int side = 10;
    int canvasWidth = 500;
    int canvasHeight = 400;
    int waitTime = 15;

    Function<Integer[][], List<Integer>> getList =
        ints -> Arrays.stream(ints).flatMap(Arrays::stream).collect(Collectors.toList());

    int spread = args.length < 1 ? 20 : Integer.parseInt(args[0]);
    int lethality = args.length < 2 ? 5 : Integer.parseInt(args[1]);

    FileWriter ap;
    Consumer<String> error = (s) -> JOptionPane.showMessageDialog(
        new JFrame(), s, "Inane error", JOptionPane.ERROR_MESSAGE);

    try {
      ap = new FileWriter("pandemic4.txt");
    } catch (IOException e) {
      error.accept(e.getMessage());
      return;
    }

    Map<Frames, Integer> statusFrames = new HashMap<>();
    Integer[][] statuses = new Integer[side][side];
    for (int i = 0; i < side; i++) {
      for (int j = 0; j < side; j++) {
        statuses[i][j] = alive;
        statusFrames.put(new Frames(i, j, alive), 1);
      }
    }

    int originX = new Random().nextInt(side);
    int originY = new Random().nextInt(side);
    statuses[originX][originY] = affected;
    statusFrames.put(new Frames(originX, originY, affected), 2);

    while (getList.apply(statuses).contains(affected)) {
      frame += waitTime;
      UnaryOperator<Integer> set = i -> i < 0 ? 0 : i > side - 1 ? side - 1 : i;

      Integer[][] copy = new Integer[side][side];
      for (int i = 0; i < side; i++) {
        System.arraycopy(statuses[i], 0, copy[i], 0, side);
      }

      for (int i = 0; i < side; i++) {
        for (int j = 0; j < side; j++) {

          int rnd = new Random().nextInt(100);
          List<Integer> neighbors = new ArrayList<>();
          for (int m : Arrays.asList(-1, 0, 1)) {
            for (int n : Arrays.asList(-1, 0, 1)) {
              neighbors.add(copy[set.apply(i + m)][set.apply(j + n)]);
            }
          }

          if (statuses[i][j] == alive && neighbors.contains(affected) && rnd < spread) {
            statuses[i][j] = affected;
            statusFrames.put(new Frames(i, j, affected), frame);
          } else if (statuses[i][j] == affected && rnd < lethality) {
            statuses[i][j] = dead;
            statusFrames.put(new Frames(i, j, dead), frame);
          }
        }
      }
    }

    for (int i = 0; i < side; i++) {
      for (int j = 0; j < side; j++) {
        if (statuses[i][j] == 0) {
          statusFrames.put(new Frames(i, j, affected), -1);
          statusFrames.put(new Frames(i, j, dead), -1);
        }
      }
    }

    StringBuilder builder = new StringBuilder();
    builder.append(String.format("canvas 0 0 %s %s\n", canvasWidth, canvasHeight));
    for (int i = 0; i < side; i++) {
      for (int j = 0; j < side; j++) {
        builder.append("shape p" + i + "-" + j + " ellipse\n");
      }
    }

    drawShapes(statusFrames, builder);

    try {
      ap.append(builder.toString());
      ap.flush();
    } catch (IOException e) {
      error.accept(e.getMessage());
      return;
    }
  }

  /**
   * Appends the shape information and color values formatted for the SimpleAnimation model.
   *
   * @param statusFrames A collection of all positions of elements including their statuses.
   * @param builder      A builder to which the result is appended on to.
   */
  private static void drawShapes(Map<Frames, Integer> statusFrames, StringBuilder builder) {
    Map<Integer, String> colors = new HashMap<>();
    colors.put(0, " 050 050 255 ");
    colors.put(1, " 255 000 000 ");
    colors.put(2, " 000 000 000 ");
    String wH = " 015 015"; //change
    int side = (int) Math.pow(statusFrames.size() / 3.0, 0.5);
    BinaryOperator<Integer> gap = (a, b)
        -> (int) ((a + 0.5) * (int) (b / (side + 1.00)));

    Function<Integer[], String> append
        = i -> String.format("%3d %3d %3d %s",
        i[2], gap.apply(i[0], 500), gap.apply(i[1], 400), wH);
    int maxFrame = Collections.max(statusFrames.values());

    for (int i = 0; i < side; i++) {
      for (int j = 0; j < side; j++) {
        for (int k : Arrays.asList(0, 1, 2)) {
          int tick = statusFrames.get(new Frames(i, j, k));
          if (tick == -1) {
            continue;
          }

          builder.append(String.format("motion p%s-%s ", i, j)
              + append.apply(new Integer[]{i, j, tick}) + colors.get(k));

          int tick2 = k == 2 ? maxFrame :
              statusFrames.get(new Frames(i, j, k + 1)) - 1;
          int col = k;
          if (tick2 == -2) {
            tick2 = maxFrame;
            col = 0;
          }

          builder.append(append.apply(new Integer[]{i, j, tick2}) + colors.get(col) + "\n");

          if (tick2 != maxFrame) {
            builder.append(String.format("motion p%s-%s ", i, j)
                + append.apply(new Integer[]{i, j, tick2}) + colors.get(k));
            builder.append(append.apply(new Integer[]{i, j, tick2 + 1})
                + colors.get(col + 1) + "\n");
          }
        }
      }
    }
  }
}

