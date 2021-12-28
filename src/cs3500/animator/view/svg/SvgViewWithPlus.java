package cs3500.animator.view.svg;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import model.ISimpleAnimationModel;
import model.direction.IDirection;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Rectangle;
import model.utils.Triplet;

public class SvgViewWithPlus extends SvgView {
  private boolean vertical;

  public SvgViewWithPlus(Appendable ap) {
    super(ap);
    vertical = true;
  }

  UnaryOperator<Integer> width = i -> vertical ? (int) (i / 2.0) : i;
  UnaryOperator<Integer> height = i -> vertical ? i : (int) (i / 2.0);

  @Override
  protected String svgShapeHelper(String shapeName, List<IDirection> directions) {
    vertical = true;
    String str = !model.getShape(shapeName).getShapeType().equals("PLUS") ?
        "" : super.svgShapeHelper(shapeName, directions);
    vertical = false;
    return str + super.svgShapeHelper(shapeName, directions);
  }

  @Override
  protected String svgDirectionHelper(String shapeName, IDirection direction) {
    StringBuilder s = new StringBuilder(super.svgDirectionHelper(shapeName, direction));
    if (model.getShape(shapeName).getShapeType().equals("PLUS") &&
        direction.getType().equals("RESIZE")) {
      IShape startShape = getShape(model, shapeName, direction.getStartFrame() - 1);
      IShape endShape = getShape(model, shapeName, direction.getEndFrame() - 1);
      int x = startShape.getX() - (int) Math.floor(startShape.getWidth() / 2.0);
      int y = startShape.getY() - (int) Math.floor(startShape.getHeight() / 2.0);
      s.append(super.svgFormat("x", direction.getStartFrame(), direction.getEndFrame(),
          x,  x - (int) ((endShape.getWidth() - startShape.getWidth()) / 2.0)));
      s.append(super.svgFormat("y", direction.getStartFrame(), direction.getEndFrame(),
          y,  y - (int) ((endShape.getHeight() - startShape.getHeight()) / 2.0)));
    }
    return s.toString();
  }

  @Override
  protected IShape getShape(ISimpleAnimationModel model, String shapeName, int frame) {
    if (!model.getShape(shapeName).getShapeType().equals("PLUS")) {
      return super.getShape(model, shapeName, frame);
    }
    IShape s = model.getShapeAtFrame(shapeName, frame);
    Rectangle r = new Rectangle(shapeName + " " + vertical);
    Triplet<Integer, Integer, Integer> col = s.getColor();
    r.create(s.getX(), s.getY(), width.apply(s.getWidth()), height.apply(s.getWidth()),
        CoordinateType.CENTER, col.getValue0(), col.getValue1(), col.getValue2());
    return r;
  }
}
