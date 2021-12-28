package cs3500.animator.view.svg;

import java.util.Map;
import model.shape.IShape;
import model.utils.Pair;
import model.utils.Triplet;

/**
 * Class for handling the SVG methods for the oval shape. Involves mapping the rectangles attributes
 * to the IDirections, along with the formatting.
 */
public class SvgRectangle extends SvgAbstractShape {

  @Override
  public Map<String, String[]> getAttributes() {
    Map<String, String[]> attrs = super.getAttributes();
    attrs.putIfAbsent("MOVE", new String[]{"x", "y"});
    attrs.putIfAbsent("RESIZE", new String[]{"width", "height"});
    return attrs;
  }

  @Override
  public Map<String, Integer[]> getArgs(IShape start, IShape end) {
    Pair<Integer, Integer> startCoord = coordinateTypeHelper(start, true, false);
    Pair<Integer, Integer> finalCoord = coordinateTypeHelper(end, false, true);

    Map<String, Integer[]> args = super.getArgs(start, end);
    args.putIfAbsent("x", new Integer[]{startCoord.getValue0(), finalCoord.getValue0()});
    args.putIfAbsent("y", new Integer[]{startCoord.getValue1(), finalCoord.getValue1()});
    args.putIfAbsent("width", new Integer[]{start.getWidth(), end.getWidth()});
    args.putIfAbsent("height", new Integer[]{start.getHeight(), end.getHeight()});
    return args;
  }

  @Override
  public String svgCreate(IShape shape, Pair<Integer, Integer> finalCoord,
      Triplet<Integer, Integer, Integer> finalColor, String visibility) {
    return String.format(
        "<rect id=\"%s\" x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" "
            + "visibility=\"%s\" style=\"fill:rgb(%s %s %s)\">\n",
        shape.getName(), finalCoord.getValue0(), finalCoord.getValue1(),
        shape.getWidth(), shape.getHeight(), visibility,
        finalColor.getValue0(), finalColor.getValue1(), finalColor.getValue2());
  }
}
