package cs3500.animator.view.svg;

import java.util.HashMap;
import java.util.Map;

/**
 * Utils class for SVG view. Involves mapping SvgAbstractShapes to Shape types and formatting.
 * Can be extended to incorporate more shapes.
 */
public class SvgUtils {

  /**
   * All the shapes and their corresponding SvgAbstractShape class.
   * @return All the shapes and their corresponding SvgAbstractShape class.
   */
  public static Map<String, SvgAbstractShape> getShapeClass() {
    Map<String, SvgAbstractShape> shps = new HashMap<>();
    shps.putIfAbsent("RECTANGLE", new SvgRectangle());
    shps.putIfAbsent("OVAL",      new SvgOval());
    shps.putIfAbsent("PLUS",      new SvgPlus());
    return shps;
  }

  /**
   * SVG formatted end tags for the corresponding shape.
   * @return SVG formatted end tags for the corresponding shape.
   */
  public static Map<String, String> getEndTags() {
    Map<String, String> tags = new HashMap<>();
    tags.putIfAbsent("RECTANGLE", "</rect>\n");
    tags.putIfAbsent("OVAL",      "</ellipse>\n");
    tags.putIfAbsent("PLUS",      "</rect>\n");
    return tags;
  }
}
