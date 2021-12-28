package model.direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.shape.IShape;
import model.shape.Oval;
import model.shape.Rectangle;
import model.utils.Triplet;
import org.junit.Before;
import org.junit.Test;

/**
 * Controls all tests for a ResizeDirection and its affects on a shape.
 */
public class ResizeDirectionTest extends DirectionTestParams {

  protected int coordinateRange = 1000;

  private List<Triplet<ValueStorage, ResizeDirection, IShape>> resizeDirections;

  @Before
  public void setUp() throws Exception {
    Random randomizer;
    ovalTest = new Oval("A");
    rectangleTest = new Rectangle("B");

    resizeDirections = new ArrayList<>();

    for (int i = 0; i < numTests; i++) {
      randomizer = new Random();

      Oval testOval = new Oval("A");
      testOval.setSize(randomizer.nextInt(coordinateRange) + 1,
          randomizer.nextInt(coordinateRange) + 1);
      Rectangle testRectangle = new Rectangle("B");
      testRectangle.setSize(randomizer.nextInt(coordinateRange) + 1,
          randomizer.nextInt(coordinateRange) + 1);

      int randWidth = randomizer.nextInt(coordinateRange) + 1;
      int randHeight = randomizer.nextInt(coordinateRange) + 1;

      // since randEnd will be the end frame we want to make sure it's always higher.
      int randStart = randomizer.nextInt(frameRange);
      int randEnd = randomizer.nextInt(frameRange - randStart) + randStart;

      resizeDirections.add(new Triplet<>(
          new ValueStorage(randWidth, randHeight, randStart, randEnd),
          new ResizeDirection(testOval, randWidth, randHeight, randStart, randEnd),
          testOval
      ));

      resizeDirections.add(new Triplet<>(
          new ValueStorage(randWidth, randHeight, randStart, randEnd),
          new ResizeDirection(testRectangle, randWidth, randHeight, randStart, randEnd),
          testRectangle
      ));
    }
  }

  @Test
  public void resizeDirection_WithValidVariables_CompilesProperly() {
    for (Triplet<ValueStorage, ResizeDirection, IShape> test : this.resizeDirections) {
      ValueStorage storage = test.getValue0();
      ResizeDirection directionTest = test.getValue1();
      IShape shapeTest = test.getValue2();

      String expected = String.format("%03d %03d",
          storage.initWidth, storage.initHeight);

      String[] actualList = directionTest.toString()
          .split(String.format(" %s.00 %03d %03d ",
              storage.initEnd, shapeTest.getX(), shapeTest.getY()));

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
  public void resizeDirection_WithInvalidShape_ThrowsIllegalArgumentException() {
    try {
      new ResizeDirection(null, 1, 1, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void resizeDirection_WithInvalidStartFrame_ThrowsIllegalArgumentException() {
    try {
      new ResizeDirection(rectangleTest, 1, 1, -1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Start frame cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void resizeDirection_WithInvalidEndFrame_ThrowsIllegalArgumentException() {
    try {
      new ResizeDirection(rectangleTest, 1, 1, 0, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("End frame cannot be less than start frame.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidWidth_ThrowsIllegalArgumentException() {
    try {
      new ResizeDirection(rectangleTest, 0, 1, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width must be 1 or greater.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidHeight_ThrowsIllegalArgumentException() {
    try {
      new ResizeDirection(rectangleTest, 1, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height must be 1 or greater.", e.getMessage());
    }
  }


  @Test
  public void resizeDirection_WithValidCommands_setsNewSizeProperly() {
    for (Triplet<ValueStorage, ResizeDirection, IShape> test : this.resizeDirections) {
      ValueStorage storage = test.getValue0();
      ResizeDirection directionTest = test.getValue1();
      IShape shapeTest = test.getValue2();

      // Run all sub-commands generated to resize the shape.
      for (int i = 0; i < frameRange; i++) {
        directionTest.processCommandsAtTick(i);
      }

      assertEquals(storage.initWidth, shapeTest.getWidth());
      assertEquals(storage.initHeight, shapeTest.getHeight());
    }
  }

  // TEST CLASS
  protected class ValueStorage {

    public int initWidth;
    public int initHeight;
    public int initStart;
    public int initEnd;

    protected ValueStorage(int initWidth, int initHeight, int initStart, int initEnd) {
      this.initWidth = initWidth;
      this.initHeight = initHeight;
      this.initStart = initStart;
      this.initEnd = initEnd;
    }
  }
}