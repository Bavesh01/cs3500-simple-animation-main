package cs3500.animator.view.svg;

import java.util.HashMap;
import java.util.Map;
import model.direction.IDirection;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.utils.Pair;
import model.utils.Triplet;

/**
 * An SvgAbstractShape is a class for handling mehtods for formatting animation commands. It
 * includes formatting and attribute mapping for all the shapes that are extending it.
 */
public abstract class SvgAbstractShape {

  private final Map<String, Pair<Integer, Integer>> lastCoord;

  /**
   * Initializes the map of last coordinates.
   */
  public SvgAbstractShape() {
    lastCoord = new HashMap<>();
  }

  /**
   * Returns all valid modifications that can be made to this shape.
   * @return The valid modifications in a map.
   */
  public Map<String, String[]> getAttributes() {
    Map<String, String[]> attrs = new HashMap<>();
    attrs.putIfAbsent("COLOR", new String[]{"fill"});
    return attrs;
  }

  /**
   * Generates the arguments that should be created based off of a starting and ending shape.
   * @param start The shape as it is before the modification.
   * @param end The shape as it is after the modification.
   * @return The generated arguments.
   */
  public Map<String, Integer[]> getArgs(IShape start, IShape end) {
    Map<String, Integer[]> args = new HashMap<>();

    args.putIfAbsent("fill", new Integer[]{
        start.getColor().getValue0(), start.getColor().getValue1(), start.getColor().getValue2(),
        end.getColor().getValue0(), end.getColor().getValue1(), end.getColor().getValue2()});
    return args;
  }

  /**
   * Modifies the x,y value for the SVG based on whether or not we need to convert the coordinate
   * point if the shape coordinate is centered or cornered.
   *
   * @param shape         The shape we're checking.
   * @param previousStart If we should compare this to the previous position in case resizing has
   *                      changed our layout.
   * @param storeValue    If we should store this value as the new "last" value of this shape.
   * @return The corrected X,Y Coordinate as a pair.
   */
  public Pair<Integer, Integer> coordinateTypeHelper(IShape shape, boolean previousStart,
      boolean storeValue) {
    int finalX = shape.getX();
    int finalY = shape.getY();

    if (previousStart && this.lastCoord.containsKey(shape.getName())) {
      Pair<Integer, Integer> lastCoord = this.lastCoord.get(shape.getName());
      finalX = lastCoord.getValue0();
      finalY = lastCoord.getValue1();
    } else {
      if ((shape.getShapeType().equals("RECTANGLE") || shape.getShapeType().equals("PLUS"))
          && shape.getCoordType() == CoordinateType.CENTER) {
        finalX = finalX - (int) Math.floor(shape.getWidth() / 2.0);
        finalY = finalY - (int) Math.floor(shape.getHeight() / 2.0);

      } else if (shape.getShapeType().equals("OVAL")
          && shape.getCoordType() == CoordinateType.CORNER) {
        finalX = finalX + (int) Math.floor(shape.getWidth() / 2.0);
        finalY = finalY + (int) Math.floor(shape.getHeight() / 2.0);

      }
    }

    Pair<Integer, Integer> newCoord = new Pair<>(finalX, finalY);

    if (storeValue) {
      this.lastCoord.put(shape.getName(), newCoord);
    }

    return newCoord;
  }

  /**
   * Creates a SVG formatted line of text representing a create direction.
   *
   * @param shape     The name of the shape we're working on.
   * @param direction The create direction.
   * @return the SVG string representation of the command.
   */
  public String svgCreate(IShape shape, IDirection direction) {
    Pair<Integer, Integer> finalCoord =
        coordinateTypeHelper(shape, false, true);
    Triplet<Integer, Integer, Integer> finalColor = shape.getColor();
    String visibility = direction.getStartFrame() == 0 ? "visible" : "hidden";
    return this.svgCreate(shape, finalCoord, finalColor, visibility);
  }

  abstract String svgCreate(IShape shape, Pair<Integer, Integer> finalCoord,
      Triplet<Integer, Integer, Integer> finalColor, String visibility);

}
