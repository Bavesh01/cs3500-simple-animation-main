package cs3500.animator.view.svg;
import java.util.Map;
import model.shape.IShape;
import model.utils.Pair;
import model.utils.Triplet;

public class SvgPlus extends SvgAbstractShape {

  @Override
  public String svgCreate(IShape shape, Pair<Integer, Integer> finalCoord,
      Triplet<Integer, Integer, Integer> finalColor, String visibility) {
    return new SvgRectangle().svgCreate(shape, finalCoord, finalColor, visibility);
  }

  @Override
  public Map<String, String[]> getAttributes() {
    return new SvgRectangle().getAttributes();
  }

  @Override
  public Map<String, Integer[]> getArgs(IShape start, IShape end) {
    return new SvgRectangle().getArgs(start, end);
  }

}
