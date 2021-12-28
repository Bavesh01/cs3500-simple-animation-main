package cs3500.animator.view.svg;

import java.util.Map;
import java.util.function.Function;
import model.shape.IShape;
import model.utils.Pair;
import model.utils.Triplet;

/**
 * Class for handling the SVG methods for the oval shape. Involves mapping the ellipse attributes to
 * the IDirections, along with the formatting.
 */
public class SvgOval extends SvgAbstractShape {

  static Function<Integer, Integer> half = (i) -> (int) Math.round(i / 2.0);

  @Override
  public Map<String, String[]> getAttributes() {
    Map<String, String[]> attrs = super.getAttributes();
    attrs.putIfAbsent("MOVE", new String[]{"cx", "cy"});
    attrs.putIfAbsent("RESIZE", new String[]{"rx", "ry"});
    return attrs;
  }

  @Override
  public Map<String, Integer[]> getArgs(IShape start, IShape end) {
    Pair<Integer, Integer> startCoord = coordinateTypeHelper(start, true, false);
    Pair<Integer, Integer> finalCoord = coordinateTypeHelper(end, false, true);

    Map<String, Integer[]> args = super.getArgs(start, end);
    args.putIfAbsent("cx", new Integer[]{startCoord.getValue0(), finalCoord.getValue0()});
    args.putIfAbsent("cy", new Integer[]{startCoord.getValue1(), finalCoord.getValue1()});
    args.putIfAbsent("rx",
        new Integer[]{half.apply(start.getWidth()), half.apply(end.getWidth())});
    args.putIfAbsent("ry",
        new Integer[]{half.apply(start.getHeight()), half.apply(end.getHeight())});
    return args;
  }

  @Override
  public String svgCreate(IShape shape, Pair<Integer, Integer> finalCoord,
      Triplet<Integer, Integer, Integer> finalColor, String visibility) {
    return String.format(
        "<ellipse id=\"%s\" cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" "
            + "visibility=\"%s\" style=\"fill:rgb(%s %s %s)\">\n",
        shape.getName(), finalCoord.getValue0(), finalCoord.getValue1(),
        half.apply(shape.getWidth()), half.apply(shape.getHeight()), visibility,
        finalColor.getValue0(), finalColor.getValue1(), finalColor.getValue2());
  }
}
