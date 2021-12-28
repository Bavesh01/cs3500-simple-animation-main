package model;

import java.util.List;
import java.util.Map;
import model.direction.IDirection;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.utils.Pair;

/**
 * A representation of the basic things a model would need in order to handle simple animation. A
 * SimpleAnimation model works by having a current frame = 0 and a list of directions. The model
 * updates the current frame, and the processCommandsAtCurrentTick updates the shapes.
 */
public interface ISimpleAnimationModel {

  /**
   * Gets a deep copy of the model.
   *
   * @return Gets a model with similar data.
   */
  ISimpleAnimationModel getCopy();

  /**
   * Initializes the model. Should be ran after all desired shapes and modifications have been added
   * to the model.
   *
   * @throws IllegalArgumentException Model has frame gaps. Model has frame overlaps. Model has
   *                                  shapes without "create" as their first direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void initializeAnimation() throws IllegalArgumentException, IllegalStateException;

  /**
   * Moves all shapes in the model forward one frame.
   *
   * @throws IllegalStateException if the model hasn't been initialized.
   */
  void advanceShapes() throws IllegalStateException;

  /**
   * Advances the frame of the model by one.
   */
  void advanceFrame();

  /**
   * Resets the frame of the model back to zero.
   */
  void resetFrame();

  /**
   * Adds a new shape to the model. The shape still has to be "created" to show up in the view.
   *
   * @param shapeName The name that should be assigned to this shape. Must be unique.
   * @param shapeType The type of shape that is requested.
   * @throws IllegalArgumentException Shape name wasn't unique or invalid shape type.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void initShape(String shapeName, String shapeType)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * "Creates" a shape by giving it all of its initial parameters.
   *
   * @param shapeName Name of the initShape.
   * @param frame     Frame the shape should be "created" on.
   * @param xPos      Horizontal position the shape should be rendered at.
   * @param yPos      Vertical position the shape should be rendered at.
   * @param width     Initial width of the shape.
   * @param height    Initial height of the shape.
   * @param coordType Whether this shape's coord is in its corner, or in its center.
   * @param r         Initial red value of the shape.
   * @param g         Initial green value of the shape.
   * @param b         Initial blue value of the shape.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void createShape(String shapeName, int frame, int xPos, int yPos, int width, int height,
      CoordinateType coordType, int r, int g, int b)
      throws IllegalArgumentException, IllegalStateException;


  /**
   * Moves a shape to a new position in a given frame range.
   *
   * @param shapeName  Name of the initShape.
   * @param xPos       The horizontal position this shape should be at after this direction.
   * @param newY       The vertical position this shape should be at after this direction.
   * @param startFrame The first frame the shape should start moving.
   * @param endFrame   The last frame the shape should be moving.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void moveShape(String shapeName, int xPos, int newY, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Stalls a shape at a given coordinate.
   *
   * @param shapeName  Name of the initShape.
   * @param startFrame The first frame the shape should stall.
   * @param endFrame   The last frame the shape should stall.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void stallShape(String shapeName, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Resizes a shape in a given frame range.
   *
   * @param shapeName  Name of the initShape.
   * @param newWidth   The width of the shape after this direction is completed.
   * @param newHeight  The height of the shape after this direction is completed.
   * @param startFrame The first frame that the shape should start changing size.
   * @param endFrame   The last frame that the shape should be changing size.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void resizeShape(String shapeName, int newWidth, int newHeight, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException;


  /**
   * Recolors a shape in a given frame range.
   *
   * @param shapeName  Name of the initShape.
   * @param r          The r value that the shape's color should end at.
   * @param g          The g value that the shape's color should end at.
   * @param b          The b value that the shape's color should end at.
   * @param startFrame The first frame that the shape should start changing color.
   * @param endFrame   The last frame that the shape should be changing color.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void recolorShape(String shapeName, int r, int g, int b, int startFrame, int endFrame)
      throws IllegalArgumentException, IllegalStateException;


  /**
   * Removes a shape from the model.
   *
   * @param shapeName Name of the initShape.
   * @param frame     The frame that the shape should be removed.
   * @throws IllegalArgumentException Shape with given name doesn't exist. Action would overlap with
   *                                  a remove argument. Action would come before a "create"
   *                                  direction. Shape doesn't have a "create" direction.
   * @throws IllegalStateException    Model has already been initialized.
   */
  void removeShape(String shapeName, int frame)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets all directions that have currently been given to the model.
   *
   * @return A list of all directions across all shapes.
   */
  Map<String, List<IDirection>> getDirections();

  /**
   * Gets list of the directions for the shape.
   *
   * @param shapeName The shape ID for the shape.
   * @return returns the list of directions for the shape.
   * @throws IllegalArgumentException if shapename is invalid.
   */
  List<IDirection> getDirectionsForShape(String shapeName) throws IllegalArgumentException;

  void removeDirection(String shapeName, String directionType, int startFrame, int endFrame)
      throws IllegalArgumentException;

  /**
   * Returns all shapes in the model.
   *
   * @return all shapes in the model.
   */
  List<IShape> getShapes();

  /**
   * Returns all shapes that are visible on a certain frame.
   *
   * @return all shapes that are visible on a certain frame.
   */
  List<IShape> getShapesAtCurrentFrame();

  /**
   * Returns all shapes that are visible on a certain frame, given a frame.
   *
   * @return all shapes that are visible on a certain frame.
   */
  List<IShape> getShapesAtFrame(int frame);

  /**
   * Returns a shape at a given frame.
   *
   * @return a shape at a given frame.
   */
  IShape getShapeAtFrame(String shapeName, int frame);

  /**
   * Shows if the animation is initialized.
   *
   * @return true if the animation has been initialized.
   */
  boolean isAnimationInitialized();

  /**
   * Returns the last frame that a motion occurs in the animation.
   *
   * @return the last frame of animation.
   */
  int getMaximumFrame();

  /**
   * Sets the boundaries on the model.
   *
   * @param width  width of the boundary in pixels.
   * @param height height of the boundary in pixels.
   */
  void setBoundaries(int width, int height);

  /**
   * Sets the corner coordinate.
   *
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  void setTopCoord(int x, int y);

  /**
   * returns the width and height the view should be.
   *
   * @return the width [0] and height [1] the view should be.
   */
  Pair<Integer, Integer> getBoundaries();

  /**
   * Gets a Shape given its ID.
   *
   * @param shapeName the name of the shape.
   * @return The IShape the shape is
   */
  IShape getShape(String shapeName);
}
