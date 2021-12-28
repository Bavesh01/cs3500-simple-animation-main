package model.shape;


import model.utils.Triplet;

/**
 * Depicts a shape involved in an Animation.
 * A shape has details about it's position in a canvas, the dimensions and the colors.
 */
public interface IShape {

  /**
   * Modifies a shape to have these properties.
   * @param xPos position on the x coordinate (>= 0).
   * @param yPos position on the y coordinate (>= 0).
   * @param width width of the shape (> 0).
   * @param height height of the shape (> 0).
   * @param coordType reference origin : either at a corner or the center.
   * @param r red value  (< 256 && >= 0).
   * @param g green value  (< 256 && >= 0).
   * @param b blue value  (< 256 && >= 0).
   * @throws IllegalArgumentException if the values are not in specified ranges.
   */
  void create(int xPos, int yPos, int width, int height,
      CoordinateType coordType, int r, int g, int b) throws IllegalArgumentException;

  /**
   * Assigns the shape a new x and y location.
   * @param newX new x coordinate
   * @param newY new y coordinate
   */
  void move(int newX, int newY) throws IllegalArgumentException;

  /**
   * Shifts the parameters by a given deviation.
   * @param deltaX Shift in the x coordinate.
   * @param deltaY Shift in the y coordinate.
   */
  void shift(int deltaX, int deltaY) throws IllegalArgumentException;

  /**
   * Sets the color for the shape.
   * @param r the red value.
   * @param g the green value.
   * @param b the blue value.
   */
  void setColor(int r, int g, int b) throws IllegalArgumentException;

  /**
   * Shifts the given color parameters by given dr, dg, db.
   * @param dr shift in the red value.
   * @param dg shift in the green value.
   * @param db shift in the blue value.
   */
  void shiftColor(int dr, int dg, int db) throws IllegalArgumentException;

  /**
   * Sets the size of the shape.
   * @param newWidth The new width of the shape.
   * @param newHeight The new height of the shape.
   */
  void setSize(int newWidth, int newHeight) throws IllegalArgumentException;

  /**
   * Shifts the size of the given shape.
   * @param deltaWidth Shift in width
   * @param deltaHeight Shift in height
   */
  void shiftSize(int deltaWidth, int deltaHeight) throws IllegalArgumentException;

  /**
   * Gets the name, id, of the shape.
   * @return the name of the Shape as a String.
   */
  String getName();

  /**
   * gets the x coordinate.
   * @return gets the x coordinate.
   */
  int getX();

  /**
   * gets the y coordinate.
   * @return gets the y coordinate.
   */
  int getY();

  /**
   * Gets the coordinate type.
   * @return the Coordinate type.
   */
  CoordinateType getCoordType();

  /**
   * gets the width.
   * @return gets the width.
   */
  int getWidth();

  /**
   * gets the height.
   * @return gets the height.
   */
  int getHeight();

  /**
   * gets the color.
   * @return gets the color as a triple.
   */
  Triplet<Integer,Integer,Integer> getColor();

  /**
   * Shows the image as a Stirng.
   * @return the String representation of the shape.
   */
  String toString();

  /**
   * Used for equality.
   * @return the hashcode for the object.
   */
  int hashCode();

  /**
   * Checks equality only based on the name & the type.
   * @param obj Any Object that is compared to.
   * @return true if the objects are equal.
   */
  boolean equals(Object obj);

  /**
   * Returns a copy of the given shape.
   * @return copy of the shape.
   */
  IShape getCopy();

  /**
   * Returns whether or not the shape is visible in its current state.
   * @return A boolean representing whether the shape is currently visible.
   */
  boolean isVisible();

  /**
   * Gets the type of the shape.
   * @return the type of the shape as a string in all uppercase (i.e. "OVAL", "RECTANGLE").
   */
  String getShapeType();
}
