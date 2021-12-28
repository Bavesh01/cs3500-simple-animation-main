package cs3500.animator.view.visual.outline;

import cs3500.animator.view.visual.InteractiveAnimationNotifier;

public interface IOutlineInteractiveView extends InteractiveAnimationNotifier {

  /**
   * Gets the current state of the views outline mode.
   * @return TRUE - in OUTLINE Mode, FALSE - in FILL mode.
   */
  boolean getOutlineMode();
}
