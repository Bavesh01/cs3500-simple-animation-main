package model.direction;

import model.shape.Oval;
import model.shape.Rectangle;

/**
 * Params that every direction testing class will rely on.
 */
public class DirectionTestParams {

  // Number of test directions to generate.
  protected int numTests = 10000;

  // The frame range of those directions (meaning the highest end frame will be is this variable).
  protected int frameRange = 1000;

  // A generic oval to test against.
  protected Oval ovalTest;

  // A generic rectangle to test against.
  protected Rectangle rectangleTest;

}
