package model;

import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.direction.ColorDirection;
import model.direction.CreateDirection;
import model.direction.IDirection;
import model.direction.MoveDirection;
import model.direction.ResizeDirection;
import model.direction.StallDirection;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.utils.Pair;

/**
 * Represents a model for a simple animation. Stores all associated shapes and the actions that
 * affect those shapes during the animation runtime.
 */
public class SimpleAnimationModel implements ISimpleAnimationModel {

  private final Map<String, List<IDirection>> directions;
  private final Map<String, IShape> shapes;
  private final Map<String, Integer> removalFrames;
  private boolean animationInitialized;
  private int currentFrame = 0;
  private int maxFrame;
  private List<IShape> orderedShapes;

  private final Map<String, Map<Integer, IShape>> preRenderedShapes;

  private int width = 500;
  private int height = 500;

  private int xFirst = 0;
  private int yFirst = 0;

  /**
   * Creates an instance of SimpleAnimation that controls the backend of an animation.
   */
  public SimpleAnimationModel() {
    this.directions = new HashMap<>();
    this.shapes = new HashMap<>();
    this.removalFrames = new HashMap<>();
    this.animationInitialized = false;
    this.maxFrame = 0;
    this.orderedShapes = new ArrayList<>();
    this.preRenderedShapes = new HashMap<>();
  }


  /**
   * Create a predefined private model with the given parameters.
   */
  private SimpleAnimationModel(Map<String, List<IDirection>> directions, Map<String, IShape> shapes,
      Map<String, Integer> removalFrames, boolean animationInitialized, int currentFrame,
      int maxFrame, List<IShape> orderedShapes) {

    this.shapes = new HashMap<>();
    this.directions = new HashMap<>();
    this.orderedShapes = new ArrayList<>();

    for (IShape shape : orderedShapes) {
      String shapeName = shape.getName();
      IShape newShape = shape.getCopy();

      this.shapes.put(shapeName, newShape);
      this.directions.put(shapeName, new ArrayList<>());
      this.orderedShapes.add(newShape);

      for (IDirection direction : directions.get(shapeName)) {
        this.directions.get(shapeName).add(direction.getCopy(newShape));
      }
    }

    this.removalFrames = removalFrames;
    this.animationInitialized = animationInitialized;
    this.currentFrame = currentFrame;
    this.maxFrame = maxFrame;
    this.preRenderedShapes = new HashMap<>();
  }

  @Override
  public ISimpleAnimationModel getCopy() {
    return new SimpleAnimationModel(
        this.directions, this.shapes, this.removalFrames, this.animationInitialized,
        this.currentFrame, this.maxFrame, this.orderedShapes);
  }

  @Override
  public void initializeAnimation() throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    for (String shapeName : directions.keySet()) {
      List<IDirection> shapeDirections = directions.get(shapeName);

      // Sorts by start frame.
      Collections.sort(shapeDirections);

      // Check for overlaps in directions for this shape.
      for (int i = 0; i < shapeDirections.size(); i++) {
        IDirection direction = shapeDirections.get(i);

        this.maxFrame = Math.max(this.maxFrame, direction.getEndFrame());

        // Every direction after the current one, means the second direction will have >= startFrame
        for (int j = i + 1; j < shapeDirections.size(); j++) {
          IDirection secondDirection = shapeDirections.get(j);

          if (direction.getType().equals("CREATE")
              && secondDirection.getStartFrame() <= direction.getEndFrame()) {
            throw new IllegalArgumentException(
                String.format("Shape '%s' has a %s change overlap.",
                    shapeName, direction.getType()));
          } else if (secondDirection.getStartFrame() >= direction.getEndFrame()) {
            break;
          } else if (direction.getType().equals(secondDirection.getType())) {
            throw new IllegalArgumentException(
                String.format("Shape '%s' has a %s change overlap.",
                    shapeName, direction.getType()));
          }
        }
      }

      // Check for frame gap in directions for this shape.
      for (int i = 0; i < shapeDirections.size() - 1; i++) {
        IDirection firstDirection = shapeDirections.get(i);
        IDirection secondDirection = shapeDirections.get(i + 1);

        if (firstDirection.getEndFrame() >= secondDirection.getStartFrame()) {
          continue;
        }
        if (firstDirection.getEndFrame() < secondDirection.getStartFrame() - 1) {
          throw new IllegalArgumentException(
              String.format("Shape '%s' has a frame gap.", shapeName));
        }
      }
    }

    for (int frame : this.removalFrames.values()) {
      this.maxFrame = Math.max(maxFrame, frame);
    }

    // Prevents more actions from being added.
    this.animationInitialized = true;
  }

  @Override
  public void advanceShapes() {
    if (!animationInitialized) {
      throw new IllegalStateException("Animation has not been initialized yet.");
    }

    for (String shapeName : directions.keySet()) {
      List<IDirection> shapeDirections = directions.get(shapeName);

      for (IDirection direction : shapeDirections) {
        if (direction.getStartFrame() > this.currentFrame) {
          break;
        } else if (direction.getEndFrame() < this.currentFrame) {
          continue;
        }

        direction.processCommandsAtTick(currentFrame);
      }
    }

    for (String shapeName : this.removalFrames.keySet()) {
      if (this.removalFrames.get(shapeName) <= currentFrame) {
        this.orderedShapes.remove(this.shapes.get(shapeName));
        this.shapes.remove(shapeName);
        this.directions.remove(shapeName);
        this.removalFrames.remove(shapeName);
      }
    }

    currentFrame++;
  }

  @Override
  public void advanceFrame() {
    this.currentFrame ++;
  }


  @Override
  public void resetFrame() {
    this.currentFrame = 0;
  }

  @Override
  public void initShape(String shapeName, String shapeType)
      throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();
    Map<String, IShape> knownShapes = ModelUtil.getKnownShapes(shapeName);
    if (this.directions.containsKey(shapeName)) {
      throw new IllegalArgumentException("Shape with that name already exists.");
    }

    IShape shape = knownShapes.getOrDefault(shapeType.toLowerCase(), null);
    if (shape == null) {
      throw new IllegalArgumentException("Shape type doesn't exist.");
    } else {
      this.directions.put(shapeName, new ArrayList<>());
      this.shapes.put(shapeName, shape);
      this.orderedShapes.add(shape);
    }
  }

  @Override
  public void createShape(String shapeName, int frame, int xPos, int yPos, int width, int height,
      CoordinateType coordType, int r, int g, int b)
      throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    this.checkExistingShapeName(shapeName);

    List<IDirection> shapeDirections = this.directions.get(shapeName);

    shapeDirections.add(new CreateDirection(
        this.shapes.get(shapeName), frame, xPos, yPos, width, height, r, g, b, coordType));
  }

  @Override
  public void moveShape(String shapeName, int xPos, int newY, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    this.checkValidShape(shapeName, startFrame, endFrame);

    List<IDirection> shapeDirections = this.directions.get(shapeName);

    shapeDirections.add(new MoveDirection(
        this.shapes.get(shapeName), xPos - xFirst, newY - yFirst, startFrame, endFrame));
  }

  @Override
  public void stallShape(String shapeName, int startFrame, int endFrame) {
    checkAnimationInitialized();

    this.checkValidShape(shapeName, startFrame, endFrame);

    List<IDirection> shapeDirections = this.directions.get(shapeName);

    shapeDirections.add(new StallDirection(
        this.shapes.get(shapeName), startFrame, endFrame));
  }

  @Override
  public void resizeShape(String shapeName, int newWidth, int newHeight,
      int startFrame, int endFrame) throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    this.checkValidShape(shapeName, startFrame, endFrame);

    List<IDirection> shapeDirections = this.directions.get(shapeName);

    shapeDirections.add(new ResizeDirection(this.shapes.get(shapeName),
        newWidth, newHeight, startFrame, endFrame));
  }

  @Override
  public void recolorShape(String shapeName, int r, int g, int b, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    this.checkValidShape(shapeName, startFrame, endFrame);

    List<IDirection> shapeDirections = this.directions.get(shapeName);

    shapeDirections.add(new ColorDirection(this.shapes.get(shapeName),
        r, g, b, startFrame, endFrame));
  }

  @Override
  public void removeShape(String shapeName, int frame)
      throws IllegalArgumentException, IllegalStateException {
    checkAnimationInitialized();

    this.checkExistingShapeName(shapeName);
    this.checkCompliesWithCreate(shapeName, frame);

    if (this.removalFrames.containsKey(shapeName)) {
      throw new IllegalArgumentException("Shape already has a removal set.");
    }

    List<IDirection> directions = this.directions.get(shapeName);
    Collections.sort(directions);

    if (frame <= directions.get(directions.size() - 1).getEndFrame()) {
      throw new
          IllegalArgumentException("Removal occurs before all queued directions are complete.");
    }

    this.removalFrames.put(shapeName, frame);
  }

  @Override
  public Map<String, List<IDirection>> getDirections() {
    return new HashMap<>(this.directions);
  }

  @Override
  public List<IDirection> getDirectionsForShape(String shapeName) throws IllegalArgumentException {
    List<IDirection> directions = this.directions.getOrDefault(shapeName, null);

    if (directions == null) {
      throw new IllegalArgumentException("Shape doesn't exist.");
    }

    return directions;
  }

  @Override
  public void removeDirection(String shapeName, String directionType, int startFrame, int endFrame)
      throws IllegalArgumentException {

    List<IDirection> directions = this.getDirectionsForShape(shapeName);

    if (directionType.equals("CREATE") && directions.size() > 1) {
      throw new IllegalArgumentException(
          "Cannot remove create while function still has other directions.");
    }

    for (int i = 0; i < directions.size(); i++) {
      IDirection direction = directions.get(i);

      if (direction.getType().equals(directionType)
          && direction.getStartFrame() == startFrame
          && direction.getEndFrame() == endFrame) {
        directions.remove(i);
        return;
      }
    }

    throw new IllegalArgumentException("Direction not found.");
  }

  @Override
  public List<IShape> getShapes() {
    List<IShape> shapes = new ArrayList<>();

    for (IShape shape : this.orderedShapes) {
      shapes.add(shape.getCopy());
    }

    return shapes;
  }

  @Override
  public List<IShape> getShapesAtCurrentFrame() {
    return this.getShapesAtFrame(currentFrame);
  }

  @Override
  public List<IShape> getShapesAtFrame(int frame) {
    List<IShape> shapesAtCurrentFrame = new ArrayList<>();

    for (IShape shape : this.orderedShapes) {
      IShape tempShape = this.getShapeAtFrame(shape.getName(), frame);

      if (tempShape != null) {
        shapesAtCurrentFrame.add(tempShape);
      }
    }

    return shapesAtCurrentFrame;
  }

  @Override
  public IShape getShapeAtFrame(String shapeName, int frame) {
    if (this.preRenderedShapes.keySet().size() == 0) {
      this.preRenderShapes();
    }

    return this.preRenderedShapes.get(shapeName).getOrDefault(frame, null);
  }

  /**
   * Renders a model and updates all the shapes and directions.
   */
  private void preRenderShapes() {
    ISimpleAnimationModel tempModel = this.getCopy();

    try {
      tempModel.initializeAnimation();
    } catch (IllegalStateException e) {
      e.getMessage();
    }

    for (IShape shape : tempModel.getShapes()) {
      this.preRenderedShapes.put(shape.getName(), new HashMap<>());
    }

    for (int i = 0; i <= tempModel.getMaximumFrame(); i++) {
      tempModel.advanceShapes();

      for (IShape shape: tempModel.getShapes()) {
        Map<Integer, IShape> newMap = this.preRenderedShapes.get(shape.getName());

        newMap.put(i, shape);

        this.preRenderedShapes.put(shape.getName(), newMap);
      }
    }
  }

  /**
   * Checks if initializeAnimation has been run.
   * @throws IllegalStateException if it has already been initialized.
   */
  private void checkAnimationInitialized() throws IllegalStateException {
    if (this.animationInitialized) {
      throw new IllegalStateException("Action cannot be done, animation is already initialized.");
    }
  }

  /**
   * Checks a variety of parameters about a given shape name to check if we can add a direction.
   *
   * @param shapeName  The name of the shape.
   * @param startFrame The frame the direction would start on.
   * @param endFrame   The frame the direction would end on.
   * @throws IllegalArgumentException If the valid parameters aren't met.
   */
  private void checkValidShape(String shapeName, int startFrame, int endFrame)
      throws IllegalArgumentException {
    this.checkExistingShapeName(shapeName);
    this.checkAfterRemovalFrame(shapeName, startFrame, endFrame);
    this.checkCompliesWithCreate(shapeName, startFrame);
  }

  /**
   * Checks if a shape has been init'ed with the given name.
   *
   * @param shapeName The shape of the name.
   * @throws IllegalArgumentException If the shape hasn't been init'ed.
   */
  private void checkExistingShapeName(String shapeName) throws IllegalArgumentException {
    if (!this.directions.containsKey(shapeName)) {
      throw new IllegalArgumentException("No shape with that name exists.");
    }
  }

  /**
   * Check that the direction wouldn't exist after a removal direction.
   *
   * @param shapeName  The name of the shape.
   * @param startFrame The start frame of the proposed direction.
   * @param endFrame   THe end frame of the proposed direction.
   * @throws IllegalArgumentException If it would take place after a removal.
   */
  private void checkAfterRemovalFrame(String shapeName, int startFrame, int endFrame)
      throws IllegalArgumentException {
    Integer removalFrame = this.removalFrames.getOrDefault(shapeName, null);
    if (removalFrame == null) {
      return;
    }

    if (startFrame >= removalFrame || endFrame >= removalFrame) {
      throw new IllegalArgumentException("Command takes place after shape removal.");
    }
  }

  /**
   * Check that the direction takes place after a create.
   *
   * @param shapeName  The name of the shape.
   * @param startFrame The start frame of the proposed direction.
   * @throws IllegalArgumentException If it would take before a create.
   */
  private void checkCompliesWithCreate(String shapeName, int startFrame) {
    List<IDirection> directions = this.directions.getOrDefault(shapeName, null);
    if (directions == null) {
      return;
    }

    if (directions.size() == 0) {
      throw new IllegalArgumentException("Create direction hasn't been queued yet.");
    } else if (startFrame < directions.get(0).getStartFrame()) {
      throw new IllegalArgumentException("Action would happen before create direction.");
    }
  }

  @Override
  public boolean isAnimationInitialized() {
    return animationInitialized;
  }

  @Override
  public int getMaximumFrame() {
    if (!this.isAnimationInitialized()) {
      throw new IllegalStateException("Model must be initialized before running.");
    }
    return this.maxFrame;
  }

  @Override
  public void setBoundaries(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public void setTopCoord(int x, int y) {
    this.xFirst = x;
    this.yFirst = y;
  }

  @Override
  public Pair<Integer, Integer> getBoundaries() {
    return new Pair<>(this.width, this.height);
  }

  @Override
  public IShape getShape(String shapeName) {
    return shapes.get(shapeName).getCopy();
  }


  /**
   * An implementation of AnimationBuilder used to tie SimpleAnimationModel to AnimationReader.
   * Allows for the parsing of a specified type of text into the model.
   */
  public static final class Builder implements AnimationBuilder<ISimpleAnimationModel> {

    private final ISimpleAnimationModel model = new SimpleAnimationModel();

    @Override
    public ISimpleAnimationModel build() {
      return model;
    }

    @Override
    public AnimationBuilder<ISimpleAnimationModel> setBounds(int x, int y, int width, int height) {
      model.setBoundaries(width, height);
      model.setTopCoord(x, y);
      return this;
    }

    @Override
    public AnimationBuilder<ISimpleAnimationModel> declareShape(String name, String type) {
      type = type.equals("ellipse") ? "oval" : type;
      model.initShape(name, type.toLowerCase());
      return this;
    }

    @Override
    public AnimationBuilder<ISimpleAnimationModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {

      if (model.getDirectionsForShape(name).size() == 0) {

        CoordinateType coordType = ModelUtil.coordtypes().get(model.getShape(name).getShapeType());

        model.createShape(name, t1, x1, y1, w1, h1, coordType, r1, g1, b1);
        if (t2 - t1 == 0) {
          return this;
        } else {
          t1++;
        }
      } else {
        t1 = Math.max(t1, model.getDirectionsForShape(name).get(0).getEndFrame() + 1);
      }

      boolean noneHappened = true;

      if (x1 != x2 || y1 != y2) {
        model.moveShape(name, x2, y2, t1, t2);
        noneHappened = false;
      }

      if (w1 != w2 || h1 != h2) {
        model.resizeShape(name, w2, h2, t1, t2);
        noneHappened = false;
      }

      if (r1 != r2 || g1 != g2 || b1 != b2) {
        model.recolorShape(name, r2, g2, b2, t1, t2);
        noneHappened = false;
      }

      if (noneHappened) {
        model.stallShape(name, t1, t2);
      }

      return this;
    }
  }
}