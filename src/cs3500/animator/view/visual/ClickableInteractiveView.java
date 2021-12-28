package cs3500.animator.view.visual;

import cs3500.animator.controller.ViewListener;
import model.ISimpleAnimationModel;

/**
 * Class responsible for interacting with the interactive view.
 * Uses methods instead of buttons. Used for testing.
 */
public class ClickableInteractiveView implements InteractiveAnimationNotifier {

  protected final InteractiveView thisView;

  /**
   * Configures itself like a normal visual view, but also adds its various components.
   * includes start buttons, pause/play button, looping button, etc.
   */
  public ClickableInteractiveView(InteractiveView view) {
    thisView = view;
  }

  /**
   * Clicks the start button.
   */
  public void clickStart() {
    thisView.startAnimation.doClick(1);
  }

  /**
   * Clicks the pause/play button.
   */
  public void clickPausePlay() {
    thisView.pausePlay.doClick(1);
  }

  /**
   * Clicks the loop checkbox.
   */
  public void clickLoop() {
    thisView.enableLoop.doClick(1);
  }

  /**
   * Moves the scrollbar to the specified value.
   */
  public void moveScrollBar(int i) {
    thisView.speedBar.setValue(i);
  }



  @Override
  public void render(ISimpleAnimationModel model) {
    thisView.render(model);
  }

  @Override
  public void initialize(int width, int height, int frameRate) {
    thisView.initialize(width, height, frameRate);
  }

  @Override
  public String toString(ISimpleAnimationModel model) {
    return thisView.toString(model);
  }

  @Override
  public void addListeners(ViewListener... vl) {
    thisView.addListeners(vl);
  }

  @Override
  public void emitStart() {
    thisView.emitStart();
  }

  @Override
  public void emitPausePlay() {
    thisView.emitPausePlay();
  }

  @Override
  public void emitLoop() {
    thisView.emitLoop();
  }

  @Override
  public void stop() {
    thisView.stop();
  }

  @Override
  public void start() {
    thisView.start();
  }
}
