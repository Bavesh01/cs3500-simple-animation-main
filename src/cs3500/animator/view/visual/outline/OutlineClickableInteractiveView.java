package cs3500.animator.view.visual.outline;

import cs3500.animator.view.visual.ClickableInteractiveView;
import cs3500.animator.view.visual.InteractiveView;

/**
 * A skin over a regular interactive view that allows it to be "clicked" without needing
 * to physically press a UI button. Specifically catered towards DiscreteInteractiveView.
 */
public class OutlineClickableInteractiveView extends ClickableInteractiveView {

  /**
   * Configures itself like a normal visual view, but also adds its various components.
   * includes start buttons, pause/play button, looping button, etc.
   *
   * @param view the view we're wrapping to allow for functional "clicking"
   */
  public OutlineClickableInteractiveView(InteractiveView view) {
    super(view);
  }

  /**
   * Clicks the fill button.
   */
  public void clickOutline() {
    ((OutlineInteractiveView) thisView).outlineMode.doClick(1);
  }


  /**
   * Gets the current outline mode of the given view.
   * @return TRUE - in OUTLINE Mode, FALSE - in FILL mode.
   */
  public boolean getOutlineMode() {
    return ((OutlineInteractiveView) thisView).getOutlineMode();
  }
}
