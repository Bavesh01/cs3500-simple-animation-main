package cs3500.animator.view.visual.outline;

import cs3500.animator.view.visual.InteractiveView;
import java.awt.BorderLayout;
import java.util.Map;
import javax.swing.JButton;

/**
 * Adds an "Outline" Button to a regular interactive view that allows for toggling shapes between
 * filled and outlined.
 */
public class OutlineInteractiveView extends InteractiveView implements IOutlineInteractiveView {

  private static final String OUTLINE = "Outline";
  private static final String FILL = "Fill";

  protected final JButton outlineMode;

  /**
   * Configures itself like a normal visual view, but also adds its various components, such as start
   * buttons, pause/play button, looping button, etc.
   *
   * @param frameRate The speed that the animation should initially play at.
   */
  public OutlineInteractiveView(int frameRate) {
    super(frameRate);
    outlineMode = new JButton(OUTLINE);
    outlineMode.setActionCommand("outlineMode");
    outlineMode.addActionListener(this);
    super.add(outlineMode, BorderLayout.NORTH);
  }

  @Override
  public boolean getOutlineMode() {
    return drawingPanel.getOutlineMode();
  }

  @Override
  protected Map<String, Runnable> getActions() {
    Map<String, Runnable> map = super.getActions();
    map.put("outlineMode", this::toggleOutline);
    this.requestFocus();
    return map;
  }

  /**
   * Toggles the state between OUTLINE mode and FILL mode.
   */
  protected void toggleOutline() {
    drawingPanel.toggleOutline();
    String str = drawingPanel.getOutlineMode() ? FILL : OUTLINE;
    outlineMode.setText(str);
  }
}
