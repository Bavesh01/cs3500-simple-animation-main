import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.controller.ReadModelController;
import cs3500.animator.controller.ShapeByShapeController;
import cs3500.animator.view.svg.SvgViewWithPlus;
import cs3500.animator.view.visual.outline.OutlineClickableInteractiveView;
import cs3500.animator.view.visual.outline.OutlineInteractiveView;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests that verify that Outline and the Plus shape work properly.
 */
public class Level1Tests {

  private ShapeByShapeController controller;
  private SvgViewWithPlus view;
  private StringBuilder builder;


  private void setupSVG(String s) {
    builder = new StringBuilder();
    this.view = new SvgViewWithPlus(builder);
    this.controller = new ShapeByShapeController(view, 60,
        new StringReader(s));
    this.controller.startAnimation();
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionUnequalWidthHeightOfPlus() {
    setupSVG(""
        + "canvas 0 0 500 400\n"
        + "shape p plus\n"
        + "shape r rectangle\n"
        + "motion p 1 100 100  50  25 149 039 191  100 250 250  50  50 000 233 002\n"
        + "motion r 1 415 116 083 284 228 113 222    6 115 116 083 284 228 113 222\n");
  }

  @Test
  public void testSvgWithPlusMotionAndColor() {
    setupSVG(""
        + "canvas 0 0 500 400\n"
        + "shape p plus\n"
        + "shape r rectangle\n"
        + "motion p 1 100 100  50  50 149 039 191  100 250 250  50  50 000 233 002\n"
        + "motion r 1 415 116 083 284 228 113 222    6 115 116 083 284 228 113 222\n");
    String s
        = "<svg width=\"500\" height=\"400\">\n"
        + "<rect id=\"p true\" x=\"88\" y=\"75\" width=\"25\" height=\"50\" "
        + "visibility=\"hidden\" style=\"fill:rgb(149 39 191)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" "
        + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"1.6333333333333333s\""
        + " fill=\"freeze\" from=\"88\" to=\"238\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"1.6333333333333333s\""
        + " fill=\"freeze\" from=\"75\" to=\"225\" />\n"
        + "<animate attributeName=\"fill\" begin=\"0.03333333333333333s\" dur=\"1.6333333333"
        + "333333s\" fill=\"freeze\" from=\"rgb(149 39 191)\" to=\"rgb(0 233 2)\" />\n"
        + "</rect>\n"
        + "<rect id=\"p false\" x=\"75\" y=\"88\" width=\"50\" height=\"25\" visibility=\"hidden\""
        + " style=\"fill:rgb(149 39 191)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" from=\"hidden\""
        + " to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"1.6333333333333333s\""
        + " fill=\"freeze\" from=\"75\" to=\"225\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"1.6333333333333333s\""
        + " fill=\"freeze\" from=\"88\" to=\"238\" />\n"
        + "<animate attributeName=\"fill\" begin=\"0.03333333333333333s\" dur=\"1.633333333"
        + "3333333s\" fill=\"freeze\" from=\"rgb(149 39 191)\" to=\"rgb(0 233 2)\" />\n"
        + "</rect>\n"
        + "<rect id=\"r\" x=\"415\" y=\"116\" width=\"83\" height=\"284\" visibility=\"hidden\""
        + " style=\"fill:rgb(228 113 222)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" from=\"hidden\""
        + " to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"0.06666666666666667s\""
        + " fill=\"freeze\" from=\"415\" to=\"115\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"0.06666666666666667s\""
        + " fill=\"freeze\" from=\"116\" to=\"116\" />\n"
        + "</rect>\n"
        + "</svg>";
    assertEquals(s, builder.toString());
  }

  @Test
  public void testSvgWithPlusResize() {
    setupSVG(""
        + "canvas 0 0 500 400\n"
        + "shape p plus\n"
        + "shape r rectangle\n"
        + "motion p 1 100 100  50  50 149 039 191    50 250 250  50  50 000 233 002\n"
        + "motion p 50 250 250  50  50 000 233 002  100 250 250  100  100 000 233 002\n"
        + "motion r 1 415 116 083 284 228 113 222    6 115 116 083 284 228 113 222\n");
    String s
        = "<svg width=\"500\" height=\"400\">\n"
        + "<rect id=\"p true\" x=\"88\" y=\"75\" width=\"25\" height=\"50\" visibility=\"hidden\""
        + " style=\"fill:rgb(149 39 191)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" from=\"hidden\""
        + " to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"0.8s\" fill=\"freeze\""
        + " from=\"88\" to=\"238\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"0.8s\" fill=\"freeze\""
        + " from=\"75\" to=\"225\" />\n"
        + "<animate attributeName=\"fill\" begin=\"0.03333333333333333s\" dur=\"0.8s\""
        + " fill=\"freeze\" from=\"rgb(149 39 191)\" to=\"rgb(0 233 2)\" />\n"
        + "<animate attributeName=\"width\" begin=\"0.8333333333333334s\" dur=\"0.833333333"
        + "3333334s\" fill=\"freeze\" from=\"25\" to=\"50\" />\n"
        + "<animate attributeName=\"height\" begin=\"0.8333333333333334s\" dur=\"0.83333333333"
        + "33334s\" fill=\"freeze\" from=\"50\" to=\"100\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.8333333333333334s\" dur=\"0.8333333333333334s\""
        + " fill=\"freeze\" from=\"238\" to=\"226\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.8333333333333334s\" dur=\"0.8333333333333334s\""
        + " fill=\"freeze\" from=\"225\" to=\"200\" />\n"
        + "</rect>\n"
        + "<rect id=\"p false\" x=\"75\" y=\"88\" width=\"50\" height=\"25\" visibility=\"hidden\""
        + " style=\"fill:rgb(149 39 191)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" from=\"hidden\""
        + " to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"0.8s\" fill=\"freeze\""
        + " from=\"75\" to=\"225\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"0.8s\" fill=\"freeze\""
        + " from=\"88\" to=\"238\" />\n"
        + "<animate attributeName=\"fill\" begin=\"0.03333333333333333s\" dur=\"0.8s\""
        + " fill=\"freeze\" from=\"rgb(149 39 191)\" to=\"rgb(0 233 2)\" />\n"
        + "<animate attributeName=\"width\" begin=\"0.8333333333333334s\" dur=\"0.833333333"
        + "3333334s\" fill=\"freeze\" from=\"50\" to=\"100\" />\n"
        + "<animate attributeName=\"height\" begin=\"0.8333333333333334s\" dur=\"0.833333333333"
        + "3334s\" fill=\"freeze\" from=\"25\" to=\"50\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.8333333333333334s\" dur=\"0.8333333333333334s\""
        + " fill=\"freeze\" from=\"225\" to=\"200\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.8333333333333334s\" dur=\"0.8333333333333334s\""
        + " fill=\"freeze\" from=\"238\" to=\"226\" />\n"
        + "</rect>\n"
        + "<rect id=\"r\" x=\"415\" y=\"116\" width=\"83\" height=\"284\" visibility=\"hidden\""
        + " style=\"fill:rgb(228 113 222)\">\n"
        + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" from=\"hidden\""
        + " to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" dur=\"0.06666666666666667s\""
        + " fill=\"freeze\" from=\"415\" to=\"115\" />\n"
        + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" dur=\"0.06666666666666667s\""
        + " fill=\"freeze\" from=\"116\" to=\"116\" />\n"
        + "</rect>\n"
        + "</svg>";
    assertEquals(s, builder.toString());
  }

  @Test
  public void testVisualToggleOutlineFill() {
    OutlineClickableInteractiveView view =
        new OutlineClickableInteractiveView(new OutlineInteractiveView(60));
    ReadModelController read = new ReadModelController(view, 60, new StringReader(""));
    read.startAnimation();
    assertFalse(view.getOutlineMode());
    view.clickOutline();
    assertTrue(view.getOutlineMode());
    view.clickOutline();
    assertFalse(view.getOutlineMode());
  }

  @Test
  public void testVisualHasPlus() {
    OutlineClickableInteractiveView view =
        new OutlineClickableInteractiveView(new OutlineInteractiveView(60));
    ReadModelController read = new ReadModelController(view, 60, new StringReader(""
        + "canvas 0 0 500 400\n"
        + "shape p plus\n"
        + "shape r rectangle\n"
        + "motion p 1 100 100  50  50 149 039 191  100 250 250  50  50 000 233 002\n"
        + "motion r 1 415 116 083 284 228 113 222    6 415 116 083 284 228 113 222\n"));
    read.startAnimation();
    assertEquals("PLUS", read.getModel().getShape("p").getShapeType());
  }
}
