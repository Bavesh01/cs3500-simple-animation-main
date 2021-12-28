package cs3500.animator.controller.slomo;

import cs3500.animator.controller.ISimpleAnimationController;

/**
 * Controllers that allow for the addition of "slow motion" which sets an animation
 * equal to a certain speed for a certain number of frames.
 */
public interface ISloMoController extends ISimpleAnimationController {

  /**
   * Parses a slow mo file into the controller that tells the controller
   * when it should go into (and exit) slow motion.
   * @param fileName The name of the slow motion file.
   * @throws IllegalArgumentException There is some issue either
   * handling the file or with the file format.
   */
  void addSloMoFile(String fileName) throws IllegalArgumentException;
}
