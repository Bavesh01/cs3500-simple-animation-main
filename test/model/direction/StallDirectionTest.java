package model.direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Oval;
import model.shape.Rectangle;
import model.utils.Triplet;
import org.junit.Before;
import org.junit.Test;

/**
 * Controls all tests for a StallDirection and its affects on a shape.
 */
public class StallDirectionTest extends DirectionTestParams {

  protected int coordinateRange = 1000;

  private List<Triplet<ValueStorage, StallDirection, IShape>> stallDirections;

  @Before
  public void setUp() throws Exception {
    Random randomizer;
    ovalTest = new Oval("A");
    rectangleTest = new Rectangle("B");

    stallDirections = new ArrayList<>();

    for (int i = 0; i < numTests; i++) {
      randomizer = new Random();

      Oval testOval = new Oval("A");
      testOval.create(0, 0, 1, 1, CoordinateType.CENTER, 0, 0, 0);
      testOval.move(randomizer.nextInt(coordinateRange), randomizer.nextInt(coordinateRange));

      Rectangle testRectangle = new Rectangle("B");
      testRectangle.create(0, 0, 1, 1, CoordinateType.CENTER, 0, 0, 0);
      testRectangle.move(randomizer.nextInt(coordinateRange), randomizer.nextInt(coordinateRange));

      // since randEnd will be the end frame we want to make sure it's always higher.
      int randStart = randomizer.nextInt(frameRange);
      int randEnd = randomizer.nextInt(frameRange - randStart) + randStart;

      stallDirections.add(new Triplet<>(
          new ValueStorage(testOval.getX(), testOval.getY(), randStart, randEnd),
          new StallDirection(testOval, randStart, randEnd),
          testOval
      ));

      stallDirections.add(new Triplet<>(
          new ValueStorage(testRectangle.getX(), testRectangle.getY(), randStart, randEnd),
          new StallDirection(testRectangle, randStart, randEnd),
          testRectangle
      ));
    }
  }

  @Test
  public void stallDirection_WithValidVariables_CompilesProperly() {
    for (Triplet<ValueStorage, StallDirection, IShape> test : this.stallDirections) {
      ValueStorage storage = test.getValue0();
      StallDirection directionTest = test.getValue1();

      String expected = String.format("%03d %03d",
          storage.initX, storage.initY);

      String[] actualList = directionTest.toString()
          .split(String.format(" %s.00 ", storage.initEnd));

      String actual = actualList[1];

      if (actualList.length == 3) {
        actual = actualList[2];
      }

      assertTrue(actual.startsWith(expected));

      assertEquals(storage.initStart, directionTest.getStartFrame());
      assertEquals(storage.initEnd, directionTest.getEndFrame());
    }
  }

  @Test
  public void stallDirection_WithInvalidShape_ThrowsIllegalArgumentException() {
    try {
      new StallDirection(null, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void stallDirection_WithInvalidStartFrame_ThrowsIllegalArgumentException() {
    try {
      new StallDirection(rectangleTest, -1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Start frame cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void stallDirection_WithInvalidEndFrame_ThrowsIllegalArgumentException() {
    try {
      new StallDirection(rectangleTest, 0, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("End frame cannot be less than start frame.", e.getMessage());
    }
  }

  @Test
  public void stallDirection_WithValidCommands_setsNewCoordinteProperly() {
    for (Triplet<ValueStorage, StallDirection, IShape> test : this.stallDirections) {
      ValueStorage storage = test.getValue0();
      StallDirection directionTest = test.getValue1();
      IShape shapeTest = test.getValue2();

      // Run all sub-commands generated to resize the shape.
      for (int i = 0; i < frameRange; i++) {
        directionTest.processCommandsAtTick(i);
      }

      assertEquals(storage.initX, shapeTest.getX());
      assertEquals(storage.initY, shapeTest.getY());
    }
  }

  // TEST CLASS
  protected class ValueStorage {

    public int initX;
    public int initY;
    public int initStart;
    public int initEnd;

    protected ValueStorage(int initX, int initY, int initStart, int initEnd) {
      this.initX = initX;
      this.initY = initY;
      this.initStart = initStart;
      this.initEnd = initEnd;
    }
  }
}