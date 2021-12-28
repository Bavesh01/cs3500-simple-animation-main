package cs3500.animator.view.visual;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Util methods that would have applications across multiple visual views.
 */
public class VisualViewUtils {
  /**
   * prepares the different types of fill commands depending on the shape.
   * @param x The x coordinate of the shape.
   * @param y The y coordinate of the shape.
   * @param w The width of the shape.
   * @param h The height of the shape.
   * @return A map of the various types of fill commands.
   */
  static Map<String, Consumer<Graphics>>
  fillCommands(int x, int y, int w, int h) {
    int h2 = (int) (h * 0.5);
    int h4 = (int) (h * 0.25);

    Map<String, Consumer<Graphics>> cmds = new HashMap<>();
    cmds.putIfAbsent("RECTANGLE", g -> g.fillRect(x, y, w, h));
    cmds.putIfAbsent("OVAL",      g -> g.fillOval(x, y, w, h));
    cmds.putIfAbsent("RECTANGLE OUTLINE", g -> g.drawRect(x, y, w, h));
    cmds.putIfAbsent("OVAL OUTLINE",      g -> g.drawOval(x, y, w, h));
    cmds.putIfAbsent("PLUS", g -> {
      g.fillRect(x - h4, y - h2, (int) (w * 0.5), h);
      g.fillRect(x - h2, y - h4, w, (int) (h * 0.5));
    });
    cmds.putIfAbsent("PLUS OUTLINE", g -> g.drawPolygon(
        new int[]{x - h2, x - h2, x - h4, x - h4, x + h4, x + h4,
            x + h2, x + h2,  x + h4, x + h4,  x - h4, x - h4},
        new int[]{y + h4, y - h4, y - h4, y - h2, y - h2, y - h4,
            y - h4, y + h4, y + h4, y + h2, y + h2, y + h4}, 12));
    return cmds;
  }
}
