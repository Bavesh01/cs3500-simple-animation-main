package cs3500.animator.controller;

/**
 * Controllers for SimpleAnimation. These controllers rely on input provided by AnimationReader
 * from pre-compiled files in order to properly parse the shapes into their
 * correct type, location, color, etc.
 */
public interface ISimpleAnimationController {

  /**
   * Configures the view so that it can output its data correctly, then tells the view to display.
   */
  void startAnimation();
}
