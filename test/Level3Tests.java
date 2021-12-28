import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.slomo.TextInputSloMoController;
import cs3500.animator.view.visual.InteractiveView;
import cs3500.animator.view.visual.outline.OutlineClickableInteractiveView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that SloMo Properly adjusts the framerate of the controller.
 */
public class Level3Tests {

  private TextInputSloMoController controller;

  @Before
  public void setup() {
    OutlineClickableInteractiveView view = new OutlineClickableInteractiveView(
        new InteractiveView(60));
    this.controller = new TextInputSloMoController(view, 60,
        new StringReader(
            "canvas 0 0 500 400\n"
            + "shape plus1 plus\n"
            + "motion plus1 1 100 100 50 50 149 039 191  100 250 250 100 100 000 233 002"));
    this.controller.addSloMoFile(
        "30 2 3\n"
            + "10 3 4");
    this.controller.startAnimation();
    assertEquals(60, this.controller.getFramerate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void speedZeroThrowsException() {
    this.controller.addSloMoFile("0 1 1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void startZeroThrowsException() {
    this.controller.addSloMoFile("1 0 1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void endZeroThrowsException() {
    this.controller.addSloMoFile("1 1 0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void startAfterEndThrowsException() {
    this.controller.addSloMoFile("1 1 0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void outOfRangeFrameStartThrowsException() {
    this.controller.addSloMoFile("1 100 150");
  }

  @Test
  public void controllerTransitionsToSloMoAndOutOfSloMo() {

    this.controller.advanceModel();

    assertEquals(60, this.controller.getFramerate());

    this.controller.advanceModel();

    assertEquals(30, this.controller.getFramerate());

    this.controller.advanceModel();

    assertEquals(10, this.controller.getFramerate());

    for (int i = 0; i < 97; i++) {
      this.controller.advanceModel();
      assertEquals(60, this.controller.getFramerate());
    }
  }

  @Test
  public void restartResetsFrameRate() {
    this.controller.advanceModel();

    assertEquals(60, this.controller.getFramerate());

    this.controller.advanceModel();

    assertEquals(30, this.controller.getFramerate());

    this.controller.restart();

    assertEquals(60, this.controller.getFramerate());
  }
}
