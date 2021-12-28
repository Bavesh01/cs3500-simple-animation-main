package model.direction;

import model.shape.IShape;

/**
 * Abstract class encompassing all possible directions. A direction is an Object with data about the
 * shape and the kind of motion it'll go through.
 */
public abstract class AbstractDirection implements IDirection {

  protected IShape shape;
  protected final int startFrame;
  protected final int endFrame;
  protected final String type;

  /**
   * Constructor for the class.
   *
   * @param shape      The shape the motion is for.
   * @param startFrame the start frame.
   * @param endFrame   the end frame.
   * @param type       Classification of the type of motion.
   */
  public AbstractDirection(IShape shape, int startFrame, int endFrame, String type) {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    if (startFrame < 0) {
      throw new IllegalArgumentException("Start frame cannot be negative.");
    }
    if (endFrame < startFrame) {
      throw new IllegalArgumentException("End frame cannot be less than start frame.");
    }
    this.shape = shape;
    this.startFrame = startFrame;
    this.endFrame = endFrame;
    this.type = type;
  }

  @Override
  public int getStartFrame() {
    return startFrame;
  }

  @Override
  public int getEndFrame() {
    return endFrame;
  }

  @Override
  public int compareTo(IDirection direction) {
    if (this.startFrame == direction.getStartFrame()) {
      return this.endFrame - direction.getEndFrame();
    }
    return this.startFrame - direction.getStartFrame();
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return this.toString(1);
  }

  @Override
  public String toString(int frameRate) {
    return toString(frameRate, this.shape);
  }

  @Override
  public String toString(int frameRate, IShape shape) {
    return "";
  }
}
