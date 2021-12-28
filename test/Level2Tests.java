import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.controller.ReadModelController;
import cs3500.animator.controller.discrete.DiscreteController;
import cs3500.animator.controller.discrete.MockDiscreteController;
import cs3500.animator.view.visual.discrete.DiscreteClickableInteractiveView;
import cs3500.animator.view.visual.discrete.DiscreteInteractiveView;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that verify that Discrete will work properly.
 */
public class Level2Tests {

  private MockDiscreteController controller;

  @Before
  public void setup() {
    DiscreteInteractiveView view = new DiscreteInteractiveView(60);

    this.controller = new MockDiscreteController(view, 60,
        new StringReader(
            "canvas 0 0 500 400\n"
                + "shape plus1 plus\n"
                + "motion plus1 1 100 100 50 50 149 039 191  100 250 250 100 100 000 233 002\n"
                + "motion plus1 100 100 100 50 50 149 039 191  200 250 250 100 100 000 233 002\n"));
    this.controller.startAnimation();
  }

  @Test
  public void discreteGetsCorrectFrames() {
    List<Integer> discreteFrames = this.controller.getDiscreteFrames();

    assertTrue(discreteFrames.contains(1));
    assertTrue(discreteFrames.contains(100));
    assertTrue(discreteFrames.contains(200));
  }

  @Test
  public void discreteJumpsFramesProperly() {
    this.controller.advanceModel();

    assertEquals(1, this.controller.getFrame());

    this.controller.advanceModel();

    assertEquals(2, this.controller.getFrame());

    this.controller.advanceModel();

    assertEquals(100, this.controller.getFrame());

    this.controller.advanceModel();

    assertEquals(200, this.controller.getFrame());
  }

  @Test
  public void testToggleDiscreteMode() {
    DiscreteClickableInteractiveView view =
        new DiscreteClickableInteractiveView(60);
    ReadModelController read = new ReadModelController(view, 60, new StringReader(""));
    read.startAnimation();
    assertFalse(view.getDiscrete());
    view.clickDiscrete();
    assertTrue(view.getDiscrete());
    view.clickDiscrete();
    assertFalse(view.getDiscrete());
  }

  @Test
  public void testPauseDiscreteMode() {
    DiscreteClickableInteractiveView view =
        new DiscreteClickableInteractiveView(60
        );
    MockDiscreteController controller
        = new MockDiscreteController(view, 60, new StringReader(""));
    controller.startAnimation();
    view.clickStart();
    view.clickDiscrete();
    view.clickPausePlay();
    assertTrue(view.getDiscrete() && !controller.isTimerRunning());
  }

  @Test
  public void testResumeDiscreteMode() {
    DiscreteClickableInteractiveView view =
        new DiscreteClickableInteractiveView(60);
    DiscreteController controller
        = new DiscreteController(view, 60, new StringReader("canvas 0 0 500 400\n"
        + "shape plus1 plus\n"
        + "motion plus1 1 100 100 50 50 149 039 191  100 250 250 100 100 000 233 002\n"
        + "motion plus1 100 100 100 50 50 149 039 191  200 250 250 100 100 000 233 002\n"));
    controller.startAnimation();
    view.clickStart();
    view.clickDiscrete();
    view.clickPausePlay();
    view.clickPausePlay();
    assertTrue(view.getDiscrete() && controller.isTimerRunning());
  }
}
