package cs3500.animator.controller.discrete;

import cs3500.animator.view.visual.InteractiveAnimationNotifier;
import java.util.List;

/**
 * Allows for testing of the discrete controller by making some privately available information
 * publicly accessible.
 */
public class MockDiscreteController extends DiscreteController implements IDiscreteViewListener {

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public MockDiscreteController(InteractiveAnimationNotifier view,
      int frameRate, Readable rd) {
    super(view, frameRate, rd);
  }

  /**
   * Get which frames were designated as "discrete frames" by the controller.
   * @return whichever frames were designated as "discrete frames" by the controller.
   */
  public List<Integer> getDiscreteFrames() {
    return this.dirList;
  }

  /**
   * Advances the model forward one frame in the controller, also doing whatever is required of
   * the controller within this function.
   */
  public void advanceModel() {
    super.advanceModel();
  }

  /**
   * Gets the current frame rate set in the controller.
   * @return The current frame rate.
   */
  public int getFrame() {
    return this.frame;
  }
}
