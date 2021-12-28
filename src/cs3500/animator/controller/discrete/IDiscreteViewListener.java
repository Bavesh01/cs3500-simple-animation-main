package cs3500.animator.controller.discrete;

import cs3500.animator.controller.ViewListener;

/**
 * Controllers that allow for the addition of "discrete" animations which shows only
 * the start and stop frames of each shapes movement.
 */
public interface IDiscreteViewListener extends ViewListener {

  /**
   * Turns discrete mode of the view either on or off depending on its current mode.
   */
  void toggleDiscrete();
}
