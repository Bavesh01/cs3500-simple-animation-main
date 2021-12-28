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
 * Controls all tests for a ColorDirection and its affects on a shape.
 */
public class ColorDirectionTest extends DirectionTestParams {

  private List<Triplet<ValueStorage, ColorDirection, IShape>> colorDirections;

  @Before
  public void setUp() throws Exception {
    Random randomizer;
    ovalTest = new Oval("A");
    rectangleTest = new Rectangle("B");

    colorDirections = new ArrayList<>();

    for (int i = 0; i < numTests; i++) {
      randomizer = new Random();

      Oval testOval = new Oval("A");
      testOval.create(0, 0, 1, 1, CoordinateType.CENTER, 0, 0, 0);
      testOval.setColor(
          randomizer.nextInt(256),
          randomizer.nextInt(256),
          randomizer.nextInt(256));
      Rectangle testRectangle = new Rectangle("B");
      testRectangle.create(0, 0, 1, 1, CoordinateType.CENTER, 0, 0, 0);
      testRectangle.setColor(
          randomizer.nextInt(256),
          randomizer.nextInt(256),
          randomizer.nextInt(256));

      // lower is -255, upper is 255
      int randR = randomizer.nextInt(256);
      int randG = randomizer.nextInt(255);
      int randB = randomizer.nextInt(256);

      // since randEnd will be the end frame we want to make sure it's always higher.
      int randStart = randomizer.nextInt(frameRange);
      int randEnd = randomizer.nextInt(frameRange - randStart) + randStart;

      colorDirections.add(new Triplet<>(
          new ValueStorage(randR, randG, randB, randStart, randEnd),
          new ColorDirection(testOval, randR, randG, randB, randStart, randEnd),
          testOval
      ));

      colorDirections.add(new Triplet<>(
          new ValueStorage(randR, randG, randB, randStart, randEnd),
          new ColorDirection(testRectangle, randR, randG, randB, randStart, randEnd),
          testRectangle
      ));
    }
  }

  @Test
  public void ColorDirection_WithValidVariables_CompilesProperly() {
    for (Triplet<ValueStorage, ColorDirection, IShape> test : this.colorDirections) {
      ValueStorage storage = test.getValue0();
      ColorDirection directionTest = test.getValue1();

      String expected = String.format("%s %s %s",
          storage.initR, storage.initG, storage.initB);

      String actual = directionTest.toString();

      assertTrue(actual.contains(Integer.toString(storage.initR)));
      assertTrue(actual.contains(Integer.toString(storage.initG)));
      assertTrue(actual.contains(Integer.toString(storage.initB)));

      assertEquals(storage.initStart, directionTest.getStartFrame());
      assertEquals(storage.initEnd, directionTest.getEndFrame());
    }
  }

  @Test
  public void colorDirection_WithInvalidShape_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(null, 0, 0, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithInvalidR_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(rectangleTest, -1, 0, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("R value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new ColorDirection(rectangleTest, 256, 0, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("R value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithInvalidG_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(rectangleTest, 0, -1, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("G value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new ColorDirection(rectangleTest, 0, 256, 0, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("G value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithInvalidB_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(rectangleTest, 0, 0, -1, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("B value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new ColorDirection(rectangleTest, 0, 0, 256, 0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("B value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithInvalidStartFrame_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(rectangleTest, 0, 0, 0, -1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Start frame cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithInvalidEndFrame_ThrowsIllegalArgumentException() {
    try {
      new ColorDirection(rectangleTest, 0, 0, 0, 0, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("End frame cannot be less than start frame.", e.getMessage());
    }
  }

  @Test
  public void colorDirection_WithValidCommands_setsNewColorProperly() {
    for (Triplet<ValueStorage, ColorDirection, IShape> test : this.colorDirections) {
      ValueStorage storage = test.getValue0();
      ColorDirection directionTest = test.getValue1();

      // Run all sub-commands generated to resize the shape.
      for (int i = 0; i < frameRange; i++) {
        directionTest.processCommandsAtTick(i);
      }

      Triplet<Integer, Integer, Integer> shapeTest = test.getValue2().getColor();

      assertEquals(storage.initR, (int) shapeTest.getValue0());
      assertEquals(storage.initG, (int) shapeTest.getValue1());
      assertEquals(storage.initB, (int) shapeTest.getValue2());
    }
  }

  // TEST CLASS
  protected class ValueStorage {

    public int initR;
    public int initG;
    public int initB;
    public int initStart;
    public int initEnd;

    protected ValueStorage(int initR, int initG, int initB, int initStart, int initEnd) {
      this.initR = initR;
      this.initG = initG;
      this.initB = initB;
      this.initStart = initStart;
      this.initEnd = initEnd;
    }
  }
}