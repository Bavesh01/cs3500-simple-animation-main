package model.direction;

import static org.junit.Assert.assertEquals;
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
 * Controls all tests for a CreateDirection and its affects on a shape.
 */
public class CreateDirectionTest extends DirectionTestParams {

  protected int coordinateRange = 1000;

  private List<Triplet<ValueStorage, CreateDirection, IShape>> createDirections;

  @Before
  public void setUp() throws Exception {
    Random randomizer;
    ovalTest = new Oval("A");
    rectangleTest = new Rectangle("B");

    createDirections = new ArrayList<>();

    for (int i = 0; i < numTests; i++) {
      randomizer = new Random();

      Oval testOval = new Oval("A");
      Rectangle testRectangle = new Rectangle("B");

      int randFrame = randomizer.nextInt(frameRange);
      int randX = randomizer.nextInt(coordinateRange);
      int randY = randomizer.nextInt(coordinateRange);
      int randWidth = randomizer.nextInt(coordinateRange) + 1;
      int randHeight = randomizer.nextInt(coordinateRange) + 1;
      int randR = randomizer.nextInt(256);
      int randG = randomizer.nextInt(256);
      int randB = randomizer.nextInt(256);
      CoordinateType randCoordType;

      if (randomizer.nextInt(2) == 1) {
        randCoordType = CoordinateType.CENTER;
      } else {
        randCoordType = CoordinateType.CORNER;
      }

      createDirections.add(new Triplet<>(
          new ValueStorage(randFrame, randX, randY, randWidth, randHeight,
              randR, randG, randB, randCoordType),
          new CreateDirection(testOval, randFrame, randX, randY, randWidth, randHeight,
              randR, randG, randB, randCoordType),
          testOval
      ));

      createDirections.add(new Triplet<>(
          new ValueStorage(randFrame, randX, randY, randWidth, randHeight,
              randR, randG, randB, randCoordType),
          new CreateDirection(testRectangle, randFrame, randX, randY, randWidth, randHeight,
              randR, randG, randB, randCoordType),
          testRectangle
      ));
    }
  }

  @Test
  public void createDirection_WithValidVariables_CompilesProperly() {
    for (Triplet<ValueStorage, CreateDirection, IShape> test : this.createDirections) {
      ValueStorage storage = test.getValue0();
      CreateDirection directionTest = test.getValue1();
      IShape shapeTest = test.getValue2();

      String expected = String.format("create  %s %s.00 %03d %03d %03d %03d %03d %03d %03d",
          shapeTest.getName(), storage.initFrame, storage.initX, storage.initY,
          storage.initWidth, storage.initHeight, storage.initR, storage.initG, storage.initB);

      String actual = directionTest.toString();

      assertEquals(actual, expected);
    }
  }

  @Test
  public void createDirection_WithInvalidShape_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(null, 0, 0, 0, 1, 1, 0, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape cannot be null.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidStartFrame_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, -1, 0, 0, 1, 1, 0, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Start frame cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidWidth_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 0, 1, 0, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width must be 1 or greater.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidHeight_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 0, 0, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height must be 1 or greater.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidR_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, -1, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("R value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 256, 0, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("R value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidG_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 0, -1, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("G value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 0, 256, 0,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("G value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidB_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 0, 0, -1,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("B value out of range, must be (0,255).", e.getMessage());
    }
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 0, 0, 256,
          CoordinateType.CENTER);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("B value out of range, must be (0,255).", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithInvalidCoordinateType_ThrowsIllegalArgumentException() {
    try {
      new CreateDirection(ovalTest, 0, 0, 0, 1, 1, 0, 0, 0,
          null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Coordinate type cannot be null.", e.getMessage());
    }
  }

  @Test
  public void createDirection_WithValidCommands_setsNewCoordinteProperly() {
    for (Triplet<ValueStorage, CreateDirection, IShape> test : this.createDirections) {
      ValueStorage storage = test.getValue0();
      CreateDirection directionTest = test.getValue1();
      IShape shapeTest = test.getValue2();

      // Run all sub-commands generated to resize the shape.
      for (int i = 0; i < frameRange; i++) {
        directionTest.processCommandsAtTick(i);
      }

      assertEquals(storage.initX, shapeTest.getX());
      assertEquals(storage.initY, shapeTest.getY());
      assertEquals(storage.initWidth, shapeTest.getWidth());
      assertEquals(storage.initHeight, shapeTest.getHeight());
      assertEquals(storage.initR, (int) shapeTest.getColor().getValue0());
      assertEquals(storage.initG, (int) shapeTest.getColor().getValue1());
      assertEquals(storage.initB, (int) shapeTest.getColor().getValue2());
    }
  }

  // TEST CLASS
  protected class ValueStorage {

    public final int initFrame;
    public final int initX;
    public final int initY;
    public final int initWidth;
    public final int initHeight;
    public final int initR;
    public final int initG;
    public final int initB;
    public final CoordinateType initCoordType;

    protected ValueStorage(int initFrame, int initX, int initY, int initWidth, int initHeight,
        int initR, int initG, int initB, CoordinateType initCoordType) {

      this.initFrame = initFrame;
      this.initX = initX;
      this.initY = initY;
      this.initWidth = initWidth;
      this.initHeight = initHeight;
      this.initR = initR;
      this.initG = initG;
      this.initB = initB;
      this.initCoordType = initCoordType;
    }
  }
}