package cs3500.animator.view.visual.discrete;

import cs3500.animator.view.visual.ClickableInteractiveView;

/**
 * A skin over a regular interactive view that allows it to be "clicked" without needing to
 * physically press a UI button. Specifically catered towards DiscreteInteractiveView.
 */
public class DiscreteClickableInteractiveView extends DiscreteInteractiveView {

  private final ClickableInteractiveView delegate;

  /**
   * Configures itself like a normal visual view, but also adds its various components, such as
   * start buttons, pause/play button, looping button, etc.
   *
   * @param frameRate The speed that the animation should initially play at.
   */
  public DiscreteClickableInteractiveView(int frameRate) {
    super(frameRate);
    delegate = new ClickableInteractiveView(this);
  }


  /**
   * Clicks the start button.
   */
  public void clickStart() {
    delegate.clickStart();
  }

  /**
   * Clicks the pause/play button.
   */
  public void clickPausePlay() {
    delegate.clickPausePlay();
  }

  /**
   * Clicks the loop checkbox.
   */
  public void clickLoop() {
    delegate.clickLoop();
  }

  /**
   * Moves the scrollbar to the specified value.
   */
  public void moveScrollBar(int i) {
    delegate.moveScrollBar(i);
  }

  /**
   * Clicks the discrete button.
   */
  public void clickDiscrete() {
    this.discrete.doClick(1);
  }

  /**
   * Get the status of the view's discrete mode status.
   *
   * @return True if discrete mode is on, else false.
   */
  public boolean getDiscrete() {
    return super.getDiscrete();
  }
}
