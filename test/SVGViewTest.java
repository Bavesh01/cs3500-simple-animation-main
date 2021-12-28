import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.view.svg.SvgView;
import model.SimpleAnimationModel;
import model.shape.CoordinateType;
import org.junit.Test;

/**
 * Tests for SimpleAnimationView.
 */
public class SVGViewTest {

  @Test
  public void SimpleAnimationView_WithValidModel_PrintsModel() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();

    testModel.initShape("A", "rectangle");
    testModel.createShape("A", 1, 10, 10, 50, 50,
        CoordinateType.CORNER, 255, 128, 64);
    testModel.moveShape("A", 70, 70, 2, 55);
    testModel.recolorShape("A", 0, 0, 0, 60, 65);
    testModel.moveShape("A", 50, 50, 55, 100);
    testModel.recolorShape("A", 255, 255, 255, 65, 80);
    testModel.resizeShape("A", 100, 92, 75, 80);
    SvgView view = new SvgView();
    view.initialize(500, 500, 60);

    assertEquals(""
            + "<svg width=\"500\" height=\"500\">\n"
            + "<rect id=\"A\" x=\"10\" y=\"10\" width=\"50\" height=\"50\" "
            + "visibility=\"hidden\" style=\"fill:rgb(255 128 64)\">\n"
            + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\" "
            + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" "
            + "dur=\"0.8833333333333333s\" fill=\"freeze\" from=\"10\" to=\"70\" />\n"
            + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" "
            + "dur=\"0.8833333333333333s\" fill=\"freeze\" from=\"10\" to=\"70\" />\n"
            + "<animate attributeName=\"x\" begin=\"0.9166666666666666s\""
            + " dur=\"0.75s\" fill=\"freeze\" from=\"70\" to=\"50\" />\n"
            + "<animate attributeName=\"y\" begin=\"0.9166666666666666s\""
            + " dur=\"0.75s\" fill=\"freeze\" from=\"70\" to=\"50\" />\n"
            + "<animate attributeName=\"fill\" begin=\"1.0s\" dur=\"0.08333333333333333s\" "
            + "fill=\"freeze\" from=\"rgb(255 128 64)\" to=\"rgb(0 0 0)\" />\n"
            + "<animate attributeName=\"fill\" begin=\"1.0833333333333333s\" dur=\"0.25s\" "
            + "fill=\"freeze\" from=\"rgb(0 0 0)\" to=\"rgb(255 255 255)\" />\n"
            + "<animate attributeName=\"width\" begin=\"1.25s\" dur=\"0.08333333333333333s\" "
            + "fill=\"freeze\" from=\"50\" to=\"100\" />\n"
            + "<animate attributeName=\"height\" begin=\"1.25s\" dur=\"0.08333333333333333s\" "
            + "fill=\"freeze\" from=\"50\" to=\"92\" />\n"
            + "</rect>\n"
            + "</svg>",
        view.toString(testModel));
  }

  @Test
  public void viewNotStarted() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();
    SvgView view = new SvgView();
    view.initialize(500, 500, 60);
    assertEquals("<svg width=\"500\" height=\"500\">\n</svg>",
        view.toString(testModel));
  }

  @Test
  public void viewJustCreate() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();
    testModel.initShape("A", "rectangle");
    SvgView view = new SvgView();
    view.initialize(500, 500, 60);
    assertEquals("<svg width=\"500\" height=\"500\">\n</svg>",
        view.toString(testModel));
  }

  @Test
  public void viewWithTwoShapes() {
    SimpleAnimationModel testModel = new SimpleAnimationModel();

    testModel.initShape("A", "rectangle");
    testModel.createShape("A", 1, 10, 10, 50, 50,
        CoordinateType.CORNER, 255, 128, 64);
    testModel.moveShape("A", 50, 50, 2, 100);
    testModel.recolorShape("A", 255, 255, 255, 70, 80);
    testModel.resizeShape("A", 100, 92, 75, 80);
    testModel.removeShape("A", 101);
    testModel.initShape("O", "oval");
    testModel.createShape("O", 5, 10, 10, 50, 50,
        CoordinateType.CENTER, 255, 128, 64);
    testModel.moveShape("O", 50, 50, 6, 100);
    testModel.recolorShape("O", 255, 255, 255, 70, 80);

    SvgView view = new SvgView();
    view.initialize(500, 500, 60);

    assertEquals(""
            + "<svg width=\"500\" height=\"500\">\n"
            + "<rect id=\"A\" x=\"10\" y=\"10\" width=\"50\" height=\"50\" visibility=\"hidden\""
            + " style=\"fill:rgb(255 128 64)\">\n"
            + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.033333s\""
            + " from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeName=\"x\" begin=\"0.03333333333333333s\" "
            + "dur=\"1.6333333333333333s\" fill=\"freeze\" from=\"10\" to=\"50\" />\n"
            + "<animate attributeName=\"y\" begin=\"0.03333333333333333s\" "
            + "dur=\"1.6333333333333333s\" fill=\"freeze\" from=\"10\" to=\"50\" />\n"
            + "<animate attributeName=\"fill\" begin=\"1.1666666666666667s\" "
            + "dur=\"0.16666666666666666s\" fill=\"freeze\" from=\"rgb(255 128 64)\" "
            + "to=\"rgb(255 255 255)\" />\n"
            + "<animate attributeName=\"width\" begin=\"1.25s\" "
            + "dur=\"0.08333333333333333s\" fill=\"freeze\" from=\"50\" to=\"100\" />\n"
            + "<animate attributeName=\"height\" begin=\"1.25s\" "
            + "dur=\"0.08333333333333333s\" fill=\"freeze\" from=\"50\" to=\"92\" />\n"
            + "</rect>\n"
            + "<ellipse id=\"O\" cx=\"10\" cy=\"10\" rx=\"25\" ry=\"25\" visibility=\"hidden\" "
            + "style=\"fill:rgb(255 128 64)\">\n"
            + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"0.100000s\" "
            + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeName=\"cx\" begin=\"0.1s\" dur=\"1.5666666666666667s\" "
            + "fill=\"freeze\" from=\"10\" to=\"50\" />\n"
            + "<animate attributeName=\"cy\" begin=\"0.1s\" dur=\"1.5666666666666667s\" "
            + "fill=\"freeze\" from=\"10\" to=\"50\" />\n"
            + "<animate attributeName=\"fill\" begin=\"1.1666666666666667s\" "
            + "dur=\"0.16666666666666666s\" fill=\"freeze\" "
            + "from=\"rgb(255 128 64)\" to=\"rgb(255 255 255)\" />\n"
            + "</ellipse>\n"
            + "</svg>",
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
    SvgView view = new SvgView();
    view.initialize(500, 500, 60);

    assertEquals(""
            + "<svg width=\"500\" height=\"500\">\n"
            + "<rect id=\"A\" x=\"10\" y=\"10\" width=\"50\" height=\"50\" visibility=\"hidden\" "
            + "style=\"fill:rgb(255 128 64)\">\n"
            + "<animate attributeName=\"visibility\" begin=\"0s\" dur=\"1.166667s\" "
            + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeName=\"x\" begin=\"1.1666666666666667s\" "
            + "dur=\"0.16666666666666666s\" fill=\"freeze\" from=\"10\" to=\"70\" />\n"
            + "<animate attributeName=\"y\" begin=\"1.1666666666666667s\" "
            + "dur=\"0.16666666666666666s\" fill=\"freeze\" from=\"10\" to=\"70\" />\n"
            + "<animate attributeName=\"fill\" begin=\"1.2s\" dur=\"0.05s\" "
            + "fill=\"freeze\" from=\"rgb(255 128 64)\" to=\"rgb(0 0 0)\" />\n"
            + "</rect>\n"
            + "</svg>",
        view.toString(testModel));
  }


}