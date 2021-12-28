package cs3500.animator.view.visual;

import cs3500.animator.view.IView;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import model.ISimpleAnimationModel;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Plus;

/**
 * A view whose job is to represent a given model within swing, a library of java used to create
 * visuals. Contrary to the other views, this view has no text output.
 */
public class SimpleAnimationView extends JFrame implements IView {

  protected DrawingPanel drawingPanel;
  protected boolean initialized;

  /**
   * Creates an instance of this view that preps the window for drawing.
   */
  public SimpleAnimationView() {
    super();
    this.initialized = false;
    this.drawingPanel = new DrawingPanel(1600, 1600);
  }

  @Override
  public void initialize(int width, int height, int frameRate) {

    setPreferredSize(new Dimension(500, 500));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    drawingPanel = new DrawingPanel(width, height);
    JScrollPane scrollPane = new JScrollPane(drawingPanel);
    add(scrollPane);
    pack();

    setVisible(true);
    this.initialized = true;
  }

  @Override
  public String toString(ISimpleAnimationModel model) {
    return "";
  }

  @Override
  public void render(ISimpleAnimationModel model) {
    if (!this.initialized) {
      throw new IllegalStateException("View is not initialized.");
    }

    drawingPanel.drawShapes(model.getShapesAtCurrentFrame());
    repaint();
  }


}
