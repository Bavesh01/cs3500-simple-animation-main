package cs3500.animator.controller;

import cs3500.animator.view.visual.InteractiveAnimationNotifier;
import java.util.function.Consumer;

/**
 * A controller for a view of our Animation where the controller is controlled by a series of
 * subscription events, usually tied to something like a button on a screen.
 */
public class InteractiveFbyFController extends FrameByFrameController
    implements ISimpleAnimationController, ViewListener {

  Consumer<String> p = System.out::println;
  protected boolean loop;
  private final InteractiveAnimationNotifier iVV;

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public InteractiveFbyFController(InteractiveAnimationNotifier view, int frameRate, Readable rd) {
    super(view, frameRate, rd);
    this.iVV = view;
    iVV.addListeners(this);
    loop = false;
  }

  @Override
  public void startAnimation() {
    this.timer = new javax.swing.Timer(calculateFrameDelay(), e -> advanceModel());
  }


  @Override
  public void restart() {
    frame = 0;
    model.resetFrame();
    if (!this.timer.isRunning()) {
      super.startAnimation();
    }
  }

  @Override
  public void pause() {
    timer.stop();
  }

  @Override
  public void resume() {
    timer.start();
  }

  @Override
  public void enableLoop() {
    loop = !loop;
  }

  @Override
  public void setAnimationSpeed(int frameRate) {
    this.frameRate = frameRate;
    this.timer.setDelay(calculateFrameDelay());
  }

  /**
   * Advances the state of the model by a frame. Updates all the shapes in the given frame.
   */
  @Override
  protected void advanceModel() {
    if (frame >= model.getMaximumFrame()) {
      if (!loop) {
        this.timer.stop();
        iVV.stop();
      } else {
        this.restart();
      }
    }
    view.render(model);
    model.advanceFrame();
    frame++;
  }

  public boolean isTimerRunning() {
    return this.timer.isRunning();
  }

  public int getFPSValue() {
    return this.frameRate;
  }

  public boolean isLooping() {
    return loop;
  }

  public boolean isAnimationInitialized() {
    return timer != null;
  }
}

