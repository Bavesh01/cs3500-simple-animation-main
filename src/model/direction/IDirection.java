package model.direction;

import model.shape.IShape;

/**
 * Represents a direction given by a user on how to use a shape.
 * A direction
 */
public interface IDirection extends Comparable<IDirection> {

  /**
   * Tells the direction to process the movement that should happen for a shape on a certain frame.
   *
   * @param frame which tick number should be processed.
   */
  void processCommandsAtTick(int frame);

  /**
   * Returns the direction as a list of stats about the shape before and after the direction.
   *
   * @return A string interpretation of the direction.
   */
  String toString();

  /**
   * Returns the direction as a list of stats about the shape before and after the direction.
   * Replaces the tick present in normal toString with the time it would occur in seconds according
   * to the frame rate.
   * @param frameRate The speed of the direction.
   *
   * @return A string interpretation of the direction.
   */
  String toString(int frameRate);


  /**
   * Prints the toString with how it would affect a given shape.
   * @param frameRate The speed of the direction.
   * @param shape The shape the direction should be applied to.
   * @return The interpretation of the direction on the given shape.
   */
  String toString(int frameRate, IShape shape);

  /**
   * Returns the frame that this direction is supposed to start on.
   *
   * @return The frame the direction starts on.
   */
  int getStartFrame();

  /**
   * Returns the frame that this direction is supposed to end on.
   *
   * @return The frame the direction ends on.
   */
  int getEndFrame();

  /**
   * Allows for sorting of directions by their start frame.
   *
   * @param direction A different direction.
   * @return positive if this direction has a greater start time, negative if opposite,
   *         equal if same.
   */
  int compareTo(IDirection direction);

  /**
   * Gets the type of direction.
   *
   * @return The direction type as a string.
   */
  String getType();

  /**
   * Returns a copy of the given direction using the given shape.
   *
   * @param shape The shape to use with this copy.
   * @return A copy of the direction.
   */
  IDirection getCopy(IShape shape);
}
