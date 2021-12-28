package cs3500.animator.controller;

/**
 * A interface for something that subscribes to an animationView and waits for input from it.
 */
public interface ViewListener {

  /**
   * For handling the restarting of the animation.
   */
  void restart();

  /**
   * For handling the pausing of the animation.
   */
  void pause();

  /**
   * For handling the resuming of the animation.
   */
  void resume();

  /**
   * For handling the looping of the animation.
   */
  void enableLoop();

  /**
   * For setting the playback speed of the animation.
   * @param frameRate What the playback speed should be set to.
   */
  void setAnimationSpeed(int frameRate);
}
