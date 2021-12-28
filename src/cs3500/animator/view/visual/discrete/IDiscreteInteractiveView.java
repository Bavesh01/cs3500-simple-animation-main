package cs3500.animator.view.visual.discrete;

import cs3500.animator.controller.discrete.DiscreteController;
import cs3500.animator.view.visual.InteractiveAnimationNotifier;

public interface IDiscreteInteractiveView extends InteractiveAnimationNotifier {

  /**
   * Switches the view into "discrete" playback mode
   */
  void toggleDiscrete();

  /**
   * Gets whether or not the view is currently in discrete mode.
   * @return TRUE - in discrete Mode, FALSE - not in discrete mode.
   */
  boolean getDiscrete();

  /**
   * Add classes that will be listening for the press of the discrete button.
   * @param dVLs The controllers that should be added as listeners.
   */
  void addDiscreteListeners(DiscreteController... dVLs);
}
