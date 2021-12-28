package cs3500.animator;

import cs3500.animator.controller.discrete.DiscreteController;
import cs3500.animator.controller.FrameByFrameController;
import cs3500.animator.controller.ISimpleAnimationController;
import cs3500.animator.controller.InteractiveFbyFController;
import cs3500.animator.controller.ShapeByShapeController;
import cs3500.animator.controller.slomo.SloMoController;
import cs3500.animator.view.svg.SvgView;
import cs3500.animator.view.svg.SvgViewWithPlus;
import cs3500.animator.view.textual.TextualView;
import cs3500.animator.view.visual.InteractiveView;
import cs3500.animator.view.visual.outline.OutlineInteractiveView;
import cs3500.animator.view.visual.discrete.DiscreteInteractiveView;
import cs3500.animator.view.visual.SimpleAnimationView;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utils class for Excellence Class.
 */
public class ViewUtils {

  /**
   * Gets an instance of every type of view tied to its text name as its key.
   *
   * @param ap    Where the view should output to, if asked for.
   * @param speed The framerate / tick rate of the view.
   * @return The map of each type of view.
   */
  public static Map<String, Supplier<ISimpleAnimationController>>
      getBuildCommands(Appendable ap, int speed, Readable rd, String fileName) {
    Map<String, Supplier<ISimpleAnimationController>> blds = new HashMap<>();
    blds.putIfAbsent("interactive",
        () -> new InteractiveFbyFController(new InteractiveView(speed), speed, rd));
    blds.putIfAbsent("interactiveoutline",
        () -> new InteractiveFbyFController(new OutlineInteractiveView(speed), speed, rd));
    blds.putIfAbsent("slomo",
        () -> {
      String fileNameWithoutExtension = fileName.split(".txt")[0];
      SloMoController controller = new SloMoController(
          new OutlineInteractiveView(speed), speed, rd);
      controller.addSloMoFile(fileNameWithoutExtension + "SloMo.txt");
      return controller;
        });
    blds.putIfAbsent("visual",
        () -> new FrameByFrameController(new SimpleAnimationView(), speed, rd));
    blds.putIfAbsent("svg",
        () -> new ShapeByShapeController(new SvgView(ap), speed, rd));
    blds.putIfAbsent("svgplus",
        () -> new ShapeByShapeController(new SvgViewWithPlus(ap), speed, rd));
    blds.putIfAbsent("text",
        () -> new ShapeByShapeController(new TextualView(ap), speed, rd));
    blds.putIfAbsent("discrete",
        () -> new DiscreteController(new DiscreteInteractiveView(speed), speed, rd));
    return blds;
  }
}
