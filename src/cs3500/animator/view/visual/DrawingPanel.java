package cs3500.animator.view.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.shape.IShape;

/**
 * The canvas of the animation. Where we add all of our shapes in order to create an animation.
 */
public class DrawingPanel extends JPanel {

  private final List<IShape> shapes;
  private final int width;
  private final int height;
  private boolean outlineMode;

  /**
   * Creates a drawing panel of size width,height that allows for the visualization of our shapes
   * to the user.
   *
   * @param width The width of the drawing panel.
   * @param height The height of the drawing panel.
   */
  public DrawingPanel(int width, int height) {
    super();
    this.width = width;
    this.height = height;
    outlineMode = false;

    setBackground(new Color(255, 255, 255));
    shapes = new ArrayList<>();
  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (IShape shp : shapes) {
      g.setColor(new Color(
          shp.getColor().getValue0(),
          shp.getColor().getValue1(),
          shp.getColor().getValue2()));
      try {
        String str = outlineMode ? " OUTLINE" : "";
        VisualViewUtils.fillCommands(shp.getX(), shp.getY(), shp.getWidth(), shp.getHeight())
            .getOrDefault(shp.getShapeType() + str, null).accept(g);
      } catch (NullPointerException e) {
        throw new IllegalStateException("Unrecognizable shape.");
      }
    }
    shapes.clear();
  }

  /**
   * Adds a list of shapes to this canvas object to draw later.
   * @param shapes The list of shapes.
   */
  public void drawShapes(List<IShape> shapes) {
    this.shapes.addAll(shapes);
  }

  /**
   * Toggles whether it should be in view mode or outline mode.
   */
  public void toggleOutline() {
    outlineMode = !outlineMode;
  }

  /**
   *
   * @return whether the outlineMode is on or off.
   */
  public boolean getOutlineMode() {
    return outlineMode;
  }
}
