import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.controller.InteractiveFbyFController;
import cs3500.animator.view.visual.ClickableInteractiveView;
import cs3500.animator.view.visual.InteractiveView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the interactive view and controller. It mainly does this by checking
 * that the listener (controller) is properly receiving event notifications after an
 * action triggers that event within the published (view).
 */
public class InteractiveViewControllerTest {
  InteractiveFbyFController controller;
  ClickableInteractiveView view;

  @Before
  public void setup() {
    this.view = new ClickableInteractiveView(new InteractiveView(60));
    this.controller = new InteractiveFbyFController(view,60,
        new StringReader(""));
    assertFalse(this.controller.isAnimationInitialized());
    this.controller.startAnimation();
    assertTrue(this.controller.isAnimationInitialized());
  }

  @Test
  public void testTriggerAnimationStart() {
    assertFalse(controller.isTimerRunning());
    view.clickStart();
    assertTrue(controller.isTimerRunning());
  }

  @Test
  public void testTriggerAnimationRestart() {
    assertFalse(controller.isTimerRunning());
    view.clickStart();
    view.clickStart();
    assertTrue(controller.isTimerRunning());
  }

  @Test
  public void testTriggerAnimationPause_WithoutStart_StartsAnimation() {
    assertFalse(controller.isTimerRunning());
    view.clickPausePlay();
    assertTrue(controller.isTimerRunning());
  }

  @Test
  public void testTriggerAnimationPause_WithStart_PausesAnimation() {
    view.clickStart();
    view.clickPausePlay();
    assertFalse(controller.isTimerRunning());
  }

  @Test
  public void testTriggerAnimationResume() {
    view.clickStart();
    view.clickPausePlay();
    view.clickPausePlay();
    assertTrue(controller.isTimerRunning());
  }

  @Test
  public void testLooping() {
    assertFalse(controller.isLooping());
    view.clickLoop();
    assertTrue(controller.isLooping());
  }

  @Test
  public void testSetAnimationSpeed() {
    assertEquals(controller.getFPSValue(), 60);
    view.moveScrollBar(7);
    assertEquals(controller.getFPSValue(), 7);
  }
}
