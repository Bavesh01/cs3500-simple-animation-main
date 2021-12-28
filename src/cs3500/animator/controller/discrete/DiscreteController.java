package cs3500.animator.controller.discrete;

import cs3500.animator.controller.InteractiveFbyFController;
import cs3500.animator.view.visual.InteractiveAnimationNotifier;
import cs3500.animator.view.visual.discrete.DiscreteInteractiveView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.direction.IDirection;

/**
 * Acts as a controller listener (should be tied to an interactive view) that can enable discrete
 * viewing of the animation by showing only its start and end frames.
 */
public class DiscreteController extends InteractiveFbyFController implements IDiscreteViewListener {

  private int index;
  private boolean discrete;
  protected List<Integer> dirList;
  private int initialFrameRate;

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public DiscreteController(InteractiveAnimationNotifier view,
      int frameRate, Readable rd) {
    super(view, frameRate, rd);
    index = 0;
    discrete = true;
    dirList = getDisplayFrames();
    ((DiscreteInteractiveView) view).addDiscreteListeners(this);
    this.initialFrameRate = frameRate;
  }

  @Override
  public void startAnimation() {
    if (discrete) {
      this.frameRate = 2;
    }

    super.startAnimation();
  }

  @Override
  public void restart() {
    super.restart();
    index = 0;
  }


  @Override
  public void advanceModel() {
    if (frame == 0 && discrete) {
      super.advanceModel();
      this.frameRate = initialFrameRate;
    }

    else if (!discrete || frame >= model.getMaximumFrame()) {
      super.setAnimationSpeed(frameRate);
      super.advanceModel();
    } else {
      index++;
      int newFrame = dirList.get(index);
      while (frame < newFrame) {
        timer.setDelay(500);
        model.advanceFrame();
        frame++;
      }
      view.render(model);
    }
  }

  @Override
  public void toggleDiscrete() {
    discrete = !discrete;
  }


  /**
   * Gets the frames that should be displayed in discrete mode.
   *
   * @return The list of frames that should be displayed in discrete mode.
   */
  private List<Integer> getDisplayFrames() {
    Set<Integer> set = new HashSet<>();
    for (List<IDirection> dirList : model.getDirections().values()) {
      for (IDirection dir : dirList) {
        int str = dir.getStartFrame();
        int end = dir.getEndFrame();
        set.add(str);
        set.add(end);
      }
    }

    List<Integer> list = new ArrayList<>(set);

    Collections.sort(list);

    return list;
  }
}
