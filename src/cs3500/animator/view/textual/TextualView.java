package cs3500.animator.view.textual;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.ISimpleAnimationModel;
import model.direction.IDirection;
import model.shape.IShape;

/**
 * A view to display the user inputs as a log.
 * Depicts the model as a set of commands with data of the elements.
 */
public class TextualView implements ITextualView {

  private int frameRate;
  private Appendable out = System.out;
  private boolean initialized;

  /**
   * Constructor for the view.
   */
  public TextualView() {
    // Nothing needs to be setup in this case, as most of it sets up in initialize.
  }

  /**
   * Constructor for the view, with an appendable for rendering.
   * @param ap Appendable to have the render attach to for output.
   */
  public TextualView(Appendable ap) {
    this();
    this.out = ap;
  }

  @Override
  public String toString(ISimpleAnimationModel model) {
    if (!this.initialized) {
      throw new IllegalStateException("View is not initialized.");
    }

    if (model.getShapes().size() == 0) {
      return "There are no shapes to display.\n";
    }
    Map<String, List<IDirection>> directions = model.getDirections();
    StringBuilder builder = new StringBuilder();
    builder.append(""
        + "# (st) == start time; (et) == end time (fps : " + frameRate + ")\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "#                  start                           end");

    for (IShape shape : model.getShapes()) {
      String shapeName = shape.getName();

      builder.append("\n");
      builder.append("shape ").append(shapeName).append(" ")
          .append(model.getShape(shapeName).getShapeType()).append("\n")
          .append("#          st   x   y   w   h   r   g   b")
          .append("   et   x   y   w   h   r   g   b\n");
      List<IDirection> shapeDirections = directions.get(shapeName);

      for (IDirection direction : shapeDirections) {
        builder.append(
            direction.toString(frameRate,
                model.getShapeAtFrame(shapeName, direction.getStartFrame() - 1))).append("\n");
      }
    }
    return builder.toString();
  }

  @Override
  public void render(ISimpleAnimationModel model) {
    if (!this.initialized) {
      throw new IllegalStateException("View is not initialized.");
    }

    try {
      this.out.append(this.toString(model));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to output.");
    }
  }

  @Override
  public void initialize(int width, int height, int frameRate) {
    this.frameRate = frameRate;
    this.initialized = true;
  }
}
