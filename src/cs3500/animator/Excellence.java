package cs3500.animator;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * The class used for testing the multiple {@code IViews}. It takes in command line arguments to set
 * the parameters for the animation. Including a file to be read from, a view, (and optionally,
 * speed / output).
 */
public class Excellence {

  /**
   * It is the main method that takes in the command line arguments and runs the animation. Either
   * to your console or as a SVG execution.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    String view = null;
    int speed = 1;
    Readable rd = null;
    Appendable ap = System.out;
    String fileInName = "";
    boolean outputtingToFile = false;

    Consumer<String> error = (s) -> {
      JOptionPane.showMessageDialog(new JFrame(), s, "Inane error",
          JOptionPane.ERROR_MESSAGE);

      System.exit(-1);
    };

    for (int i = 0; i < args.length; i++) {
      String argument = args[i];

      switch (argument) {
        case "-in":
          try {
            fileInName = args[i + 1];
            rd = new InputStreamReader(new FileInputStream(fileInName));
          } catch (java.io.FileNotFoundException e) {
            error.accept("File not found: " + args[i + 1]);
            return;
          }
          i++;
          break;
        case "-out":
          try {
            ap = new FileWriter(args[i + 1]);
            outputtingToFile = true;
          } catch (java.io.IOException e) {
            error.accept("Writing to file failed: " + args[i + 1]);
            return;
          }
          i++;
          break;
        case "-view":
          view = args[i + 1];
          i++;
          break;
        case "-speed":
          speed = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          error.accept("Invalid Runtime Argument.");
          return;
      }
    }

    if (view == null) {
      error.accept("View type not specified.");
      return;
    }
    if (rd == null) {
      error.accept("Read location not specified.");
      return;
    }

    try {
      ViewUtils.getBuildCommands(ap, speed, rd, fileInName).get(view).get().startAnimation();
    } catch (NullPointerException e) {
      error.accept("View type invalid.");
      return;
    } catch (IllegalArgumentException e) {
      error.accept(e.getMessage());
      return;
    }

    if (outputtingToFile) {
      try {
        ((FileWriter) ap).flush();
      } catch (IOException e) {
        error.accept("Failed to write to file.");
      }
    }
  }
}
