package model;

import java.util.HashMap;
import java.util.Map;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Oval;
import model.shape.Plus;
import model.shape.Rectangle;

/**
 * Class for utility methods.
 */
class ModelUtil {

  /**
   * Useful for adding new shapes.
   * @param shapeName Shape's name.
   * @return The map of shapes and their associated "type" String.
   */
  static Map<String, IShape> getKnownShapes(String shapeName) {
    Map<String, IShape> knownShapes = new HashMap<>();
    knownShapes.putIfAbsent("rectangle", new Rectangle(shapeName));
    knownShapes.putIfAbsent("oval", new Oval(shapeName));
    knownShapes.putIfAbsent("plus", new Plus(shapeName));
    return knownShapes;
  }

  public static Map<String , CoordinateType> coordtypes() {
    Map<String, CoordinateType> coords = new HashMap<>();
    coords.putIfAbsent("RECTANGLE", CoordinateType.CORNER);
    coords.putIfAbsent("OVAL", CoordinateType.CENTER);
    coords.putIfAbsent("PLUS", CoordinateType.CENTER);
    return coords;
  }
}
