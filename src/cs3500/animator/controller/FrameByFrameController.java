package cs3500.animator.controller;

import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import javax.swing.Timer;
import model.ISimpleAnimationModel;
import model.SimpleAnimationModel.Builder;
import model.utils.Pair;

/**
 * A controller for views that progress through the model frame-by-frame (such as a visual view).
 * If the view needs the update of every shape on every frame, this is the correct controller
 * to use.
 */
public class FrameByFrameController implements ISimpleAnimationController {

  protected final ISimpleAnimationModel model;
  protected final IView view;
  protected int frameRate;
  protected Timer timer;
  protected int frame;

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd Where text should be input from.
   */
  public FrameByFrameController(IView view, int frameRate, Readable rd) {
    this.view = view;
    this.frameRate = frameRate;
    this.frame = 0;

    this.model = AnimationReader.parseFile(rd, new Builder());
    this.model.initializeAnimation();

    Pair<Integer, Integer> bounds = this.model.getBoundaries();
    this.view.initialize(bounds.getValue0(), bounds.getValue1(), frameRate);
  }

  @Override
  public void startAnimation() {
    this.timer = new javax.swing.Timer(calculateFrameDelay(), e -> advanceModel());
    this.timer.start();
  }

  /**
   * turns a framerate, which is tracked in terms of seconds, into a number that is
   * in terms of milliseconds.
   * @return The framerate interpreted in millisecond notation.
   */
  protected int calculateFrameDelay() {
    return (int) Math.floor(1000.0 / frameRate);
  }

  /**
   * Advances the state of the model by a frame.
   * Updates all the shapes in the given frame.
   */
  protected void advanceModel() {
    if (frame > model.getMaximumFrame()) {
      this.timer.stop();
    }

    view.render(model);
    model.advanceFrame();
  }
}
