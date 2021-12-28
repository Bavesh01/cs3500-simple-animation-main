import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.view.textual.TextualView;
import model.SimpleAnimationModel;
import model.shape.CoordinateType;
import org.junit.Test;

/**
 * Tests for SimpleAnimationView.
 */
public class TextualViewTest {

  @Test
  public void SimpleAnimationView_WithValidModel_PrintsModel() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();

    testModel.initShape("A", "rectangle");
    testModel.createShape("A", 1, 10, 10, 50, 50,
        CoordinateType.CENTER, 255, 128, 64);
    testModel.moveShape("A", 70, 70, 2, 55);
    testModel.recolorShape("A", 0, 0, 0, 60, 65);
    testModel.moveShape("A", 50, 50, 55, 100);
    testModel.recolorShape("A", 255, 255, 255, 65, 80);
    testModel.resizeShape("A", 100, 92, 75, 80);
    TextualView view = new TextualView();
    view.initialize(0, 0, 60);

    assertEquals(""
            + "# (st) == start time; (et) == end time (fps : 60)\n"
            + "# (x,y) == position\n"
            + "# (w,h) == dimensions\n"
            + "# (r,g,b) == color (with values between 0 and 255)\n"
            + "#                  start                           end\n"
            + "shape A RECTANGLE\n"
            + "#          st   x   y   w   h   r   g   b   et   x   y   w   h   r   g   b\n"
            + "create  A 0.02 010 010 050 050 255 128 064\n"
            + "move    A 0.03 010 010 050 050 255 128 064 0.92 070 070 050 050 255 128 064\n"
            + "recolor A 1.00 068 068 050 050 255 128 064 1.08 068 068 050 050 000 000 000\n"
            + "move    A 0.92 070 070 050 050 255 128 064 1.67 050 050 050 050 255 128 064\n"
            + "recolor A 1.08 065 065 050 050 000 000 000 1.33 065 065 050 050 255 255 255\n"
            + "resize  A 1.25 061 061 050 050 182 182 255 1.33 061 061 100 092 182 182 255\n",
        view.toString(testModel));
  }

  @Test
  public void viewNotStarted() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();
    TextualView view = new TextualView();
    view.initialize(0, 0, 60);
    assertEquals("There are no shapes to display.\n",
        view.toString(testModel));
  }

  @Test
  public void viewJustCreate() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();
    testModel.initShape("A", "rectangle");
    TextualView view = new TextualView();
    view.initialize(0, 0, 60);
    assertEquals(""
            + "# (st) == start time; (et) == end time (fps : 60)\n"
            + "# (x,y) == position\n"
            + "# (w,h) == dimensions\n"
            + "# (r,g,b) == color (with values between 0 and 255)\n"
            + "#                  start                           end\n"
            + "shape A RECTANGLE\n"
            + "#          st   x   y   w   h   r   g   b   et   x   y   w   h   r   g   b\n",
        view.toString(testModel));
  }

  @Test
  public void viewWithTwoShapes() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();

    testModel.initShape("A", "rectangle");
    testModel.createShape("A", 1, 10, 10, 50, 50,
        CoordinateType.CENTER, 255, 128, 64);
    testModel.moveShape("A", 50, 50, 2, 100);
    testModel.recolorShape("A", 255, 255, 255, 70, 80);
    testModel.resizeShape("A", 100, 92, 75, 80);
    testModel.removeShape("A", 101);
    testModel.initShape("O", "oval");
    testModel.createShape("O", 5, 10, 10, 50, 50,
        CoordinateType.CENTER, 255, 128, 64);
    testModel.moveShape("O", 50, 50, 6, 100);
    testModel.recolorShape("O", 255, 255, 255, 70, 80);

    TextualView view = new TextualView();
    view.initialize(0, 0, 60);
    assertEquals(""
            + "# (st) == start time; (et) == end time (fps : 60)\n"
            + "# (x,y) == position\n"
            + "# (w,h) == dimensions\n"
            + "# (r,g,b) == color (with values between 0 and 255)\n"
            + "#                  start                           end\n"
            + "shape A RECTANGLE\n"
            + "#          st   x   y   w   h   r   g   b   et   x   y   w   h   r   g   b\n"
            + "create  A 0.02 010 010 050 050 255 128 064\n"
            + "move    A 0.03 010 010 050 050 255 128 064 1.67 050 050 050 050 255 128 064\n"
            + "recolor A 1.17 038 038 050 050 255 128 064 1.33 038 038 050 050 255 255 255\n"
            + "resize  A 1.25 040 040 050 050 255 199 255 1.33 040 040 100 092 255 199 255\n"
            + "\n"
            + "shape O OVAL\n"
            + "#          st   x   y   w   h   r   g   b   et   x   y   w   h   r   g   b\n"
            + "create  O 0.08 010 010 050 050 255 128 064\n"
            + "move    O 0.10 010 010 050 050 255 128 064 1.67 050 050 050 050 255 128 064\n"
            + "recolor O 1.17 038 038 050 050 255 128 064 1.33 038 038 050 050 255 255 255\n",
        view.toString(testModel));
  }

  @Test
  public void viewWithError2() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();
    testModel.initShape("A", "rectangle");
    testModel.createShape("A", 69, 10, 10, 50, 50,
        CoordinateType.CORNER, 255, 128, 64);
    testModel.moveShape("A", 70, 70, 70, 80);
    testModel.recolorShape("A", 0, 0, 0, 72, 75);
    try {
      testModel.initShape("A", "oval");
      fail();
    } catch (IllegalArgumentException e) {
      e.getMessage();
    }
    TextualView view = new TextualView();
    view.initialize(0, 0, 60);
    assertEquals(""
            + "# (st) == start time; (et) == end time (fps : 60)\n"
            + "# (x,y) == position\n"
            + "# (w,h) == dimensions\n"
            + "# (r,g,b) == color (with values between 0 and 255)\n"
            + "#                  start                           end\n"
            + "shape A RECTANGLE\n"
            + "#          st   x   y   w   h   r   g   b   et   x   y   w   h   r   g   b\n"
            + "create  A 1.15 010 010 050 050 255 128 064\n"
            + "move    A 1.17 010 010 050 050 255 128 064 1.33 070 070 050 050 255 128 064\n"
            + "recolor A 1.20 023 023 050 050 255 128 064 1.25 023 023 050 050 000 000 000\n",
        view.toString(testModel));
  }
}