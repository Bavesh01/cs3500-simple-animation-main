package cs3500.animator.view.visual;

import cs3500.animator.controller.ViewListener;
import cs3500.animator.view.IView;
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * Adds a series of buttons and interactables to our basic visual view that allow for the
 * modification of the animation that is occurring with the use of listeners.
 */
public class InteractiveView extends SimpleAnimationView
    implements IView, ActionListener, AdjustmentListener, InteractiveAnimationNotifier {

  protected final JButton startAnimation;
  protected final JButton pausePlay;
  private boolean pause;
  protected List<ViewListener> viewListeners;
  protected final JCheckBox enableLoop;
  protected final JScrollBar speedBar;
  private int fps;
  private boolean start;

  private static final String START = "Start";
  private static final String PAUSE = "Pause";
  private static final String RESTART = "Restart";
  private static final String PLAY = "Play";
  protected static JLabel speedBarLabel = new JLabel("SPEED");
  protected static Consumer<Integer> setSpeedLabel
      = i -> speedBarLabel.setText("SPEED: " + i + " FPS");

  /**
   * Configures itself like a normal visual view, but also adds its various components, such as
   * start buttons, pause/play button, looping button, etc.
   * @param frameRate The speed that the animation should initially play at.
   */
  public InteractiveView(int frameRate) {
    super();

    start = false;
    fps = frameRate;
    pause = false;
    viewListeners = new ArrayList<>();

    this.startAnimation = new JButton(START);
    startAnimation.setActionCommand("start");
    startAnimation.addActionListener(this);

    pausePlay = new JButton(PLAY);
    pausePlay.setActionCommand("pause/play");
    pausePlay.addActionListener(this);

    enableLoop = new JCheckBox("Loop");
    enableLoop.addActionListener(this);
    enableLoop.setActionCommand("loop");

    speedBar = new JScrollBar(Adjustable.HORIZONTAL);
    speedBar.setName("speed");
    speedBar.addAdjustmentListener(this);
    speedBar.setValue(fps);
    speedBar.setMaximum(130);
    speedBar.setMinimum(1);
    speedBar.setUnitIncrement(1);

    designPanels();
  }

  protected void designPanels() {
    JPanel speedLabel = new JPanel();
    speedLabel.add(speedBarLabel, BorderLayout.CENTER);

    JPanel scroll = new JPanel();
    scroll.setLayout(new BorderLayout());
    scroll.add(speedBar, BorderLayout.CENTER);

    scroll.add(new JLabel(" 1 FPS "), BorderLayout.WEST);
    scroll.add(new JLabel(" 120 FPS "), BorderLayout.EAST);
    scroll.add(speedLabel, BorderLayout.NORTH);

    JPanel outline = new JPanel();
    outline.setLayout(new BorderLayout());
    outline.add(pausePlay, BorderLayout.CENTER);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(startAnimation, BorderLayout.WEST);
    panel.add(outline, BorderLayout.CENTER);
    panel.add(enableLoop, BorderLayout.EAST);
    panel.add(scroll, BorderLayout.SOUTH);

    setLayout( new BorderLayout());
    add(drawingPanel, BorderLayout.CENTER);
    add(panel, BorderLayout.PAGE_END);
  }

  @Override
  public void addListeners(ViewListener... vl) {
    viewListeners.addAll(Arrays.asList(vl));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Objects.requireNonNull(getActions().get(e.getActionCommand()),
        e.getActionCommand() + ": not a valid command").run();
  }

  protected Map<String, Runnable> getActions() {
    Map<String, Runnable> map = new HashMap<>();
    map.put("start", this::emitStart);
    map.put("pause/play", start ? this::emitPausePlay : this::emitStart);
    map.put("loop", this::emitLoop);
    this.requestFocus();
    return map;
  }


  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    viewListeners.forEach(vl -> vl.setAnimationSpeed(e.getValue()));
    fps = e.getValue();
    setSpeedLabel.accept(fps);
  }

  //emitters
  @Override
  public void emitStart() {
    viewListeners.forEach(ViewListener::restart);
    this.start();
  }

  @Override
  public void emitPausePlay() {
    viewListeners.forEach(pause ? ViewListener::resume : ViewListener::pause);
    switchPause();
  }

  @Override
  public void emitLoop() {
    viewListeners.forEach(ViewListener::enableLoop);
  }

  //emitters helpers
  private void switchPause() {
    pause = !pause;
    pausePlay.setText(pause ? PLAY : PAUSE);
  }

  @Override
  public void stop() {
    start = false;
    pausePlay.setText(PLAY);
  }

  @Override
  public void start() {
    start = true;
    startAnimation.setText(RESTART);
    pausePlay.setText(PAUSE);
  }
}
