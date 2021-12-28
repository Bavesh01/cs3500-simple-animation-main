package cs3500.animator.view.visual.discrete;

import cs3500.animator.controller.discrete.DiscreteController;
import cs3500.animator.controller.discrete.IDiscreteViewListener;
import cs3500.animator.view.visual.InteractiveView;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

/**
 * Adds a "discrete" button to the view that allows for the view to be put into discrete or
 * continuous mode.
 */
public class DiscreteInteractiveView extends InteractiveView implements
    IDiscreteInteractiveView {

  private static final String DISCRETE = "Discrete";
  private static final String CONTINUOUS = "Continuous";

  protected final JButton discrete;
  private boolean isDiscrete;
  private List<IDiscreteViewListener> discreteVLs;

  /**
   * Configures itself like a normal visual view, but also adds its various components, such as
   * start buttons, pause/play button, looping button, etc.
   *
   * @param frameRate The speed that the animation should initially play at.
   */
  public DiscreteInteractiveView(int frameRate) {
    super(frameRate);
    discrete = new JButton(CONTINUOUS);
    discrete.setActionCommand("discrete");
    discrete.addActionListener(this);
    super.add(discrete, BorderLayout.NORTH);
    isDiscrete = false; //what
    discreteVLs = new ArrayList<>();
  }

  @Override
  public void toggleDiscrete() {
    isDiscrete = !isDiscrete;
    String str = this.getDiscrete() ? CONTINUOUS : DISCRETE;
    discrete.setText(str);
  }

  @Override
  protected Map<String, Runnable> getActions() {
    Map<String, Runnable> map = super.getActions();
    map.put("discrete", this::emitDiscrete);
    this.requestFocus();
    return map;
  }

  @Override
  public boolean getDiscrete() {
    return isDiscrete;
  }

  @Override
  public void addDiscreteListeners(DiscreteController... dVLs) {
    discreteVLs.addAll(Arrays.asList(dVLs));
  }

  /**
   * Sends the message that the discrete button has been pressed to all listeners.
   */
  private void emitDiscrete() {
    this.toggleDiscrete();
    discreteVLs.forEach(IDiscreteViewListener::toggleDiscrete);
  }
}
