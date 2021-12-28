import static org.junit.Assert.assertEquals;

import java.util.function.BiFunction;
import model.shape.CoordinateType;
import model.shape.IShape;
import model.shape.Oval;
import model.shape.Rectangle;
import model.utils.Triplet;
import org.junit.Test;

/**
 * Tests for the shape.
 */
public class ShapesTest {

  @Test
  public void testConstructor1() {
    IShape r = new Rectangle("R");
    assertEquals(0, r.getHeight());
    assertEquals(new Triplet<>(0, 0, 0), r.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    new Oval("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    new Rectangle(null);
  }

  @Test
  public void testCreate() {
    IShape r = new Rectangle("R");
    assertEquals(0, r.getWidth());
    r.create(5, 5, 5, 5,
        CoordinateType.CORNER, 10, 10, 10);
    assertEquals(5, r.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNegativeArg() {
    IShape r = new Rectangle("R");
    r.create(5, 5, -5, 5,
        CoordinateType.CORNER, 10, 10, 10);
  }

  @Test
  public void testCreateNegativeArgAll() {
    BiFunction<Integer, Integer, Integer> n =
        (x, y) -> x == y ? -5 : 5;
    int counter = 0;
    IShape r = new Rectangle("R");
    for (int i = 1; i < 8; i++) {
      try {
        r.create(n.apply(1, i), n.apply(2, i),
            n.apply(3, i), n.apply(4, i),
            CoordinateType.CORNER,
            n.apply(5, i), n.apply(6, i), n.apply(7, i));
      } catch (IllegalArgumentException e) {
        counter++;
      }
    }
    assertEquals(5, counter);
  }

  @Test
  public void testCreateZeroArg() {
    IShape r = new Rectangle("R");
    StringBuilder s = new StringBuilder();
    try {
      r.create(0, 0, 0, 5,
          CoordinateType.CORNER, 0, 0, 0);
    } catch (IllegalArgumentException e) {
      s.append(e.getMessage(), 0, 6);
    }
    try {
      r.create(0, 0, 5, 0,
          CoordinateType.CORNER, 0, 0, 0);
    } catch (IllegalArgumentException e) {
      s.append(e.getMessage(), 0, 6);
    }
    assertEquals("width height", s.toString());
  }

  @Test
  public void testCreate256() {
    BiFunction<Integer, Integer, Integer> n =
        (x, y) -> x == y ? 256 : 5;
    StringBuilder s = new StringBuilder();
    int counter = 0;
    IShape r = new Rectangle("R");
    for (int i = 1; i < 8; i++) {
      try {
        r.create(n.apply(1, i), n.apply(2, i),
            n.apply(3, i), n.apply(4, i),
            CoordinateType.CORNER,
            n.apply(5, i), n.apply(7, i), n.apply(6, i));
      } catch (IllegalArgumentException e) {
        s.append(e.getMessage(), 0, 5);
      }
    }
    assertEquals("red sblue green", s.toString());
  }

  ////////// test setters
  IShape rr = new Rectangle("RR");
  IShape oo = new Oval("OO");

  private void setInit() {
    oo.create(1, 2, 3, 4, CoordinateType.CORNER, 6, 7, 8);
    rr.create(8, 7, 6, 5, CoordinateType.CENTER, 3, 2, 1);
  }

  @Test
  public void testMove() {
    setInit();
    assertEquals("1 2", oo.getX() + " " + oo.getY());
    oo.move(3, 4);
    assertEquals("3 4", oo.getX() + " " + oo.getY());
  }

  @Test
  public void testShift() {
    setInit();
    assertEquals("1 2", oo.getX() + " " + oo.getY());
    oo.shift(3, 4);
    assertEquals("4 6", oo.getX() + " " + oo.getY());
  }

  @Test
  public void testSetColor() {
    setInit();
    assertEquals(new Triplet<>(6, 7, 8), oo.getColor());
    oo.setColor(3, 4, 7);
    assertEquals(new Triplet<>(3, 4, 7), oo.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorInvalid() {
    setInit();
    oo.setColor(-3, 4, 7);
  }

  @Test
  public void testShiftColor() {
    setInit();
    assertEquals(new Triplet<>(6, 7, 8), oo.getColor());
    oo.shiftColor(3, 4, 300);
    assertEquals(new Triplet<>(9, 11, 255), oo.getColor());
  }

  @Test
  public void testSize() {
    setInit();
    assertEquals("3 4", oo.getWidth() + " " + oo.getHeight());
    oo.setSize(5, 6);
    assertEquals("5 6", oo.getWidth() + " " + oo.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSizeInvalid() {
    setInit();
    oo.setSize(-3, 4);
  }

  @Test
  public void testShiftSize() {
    setInit();
    assertEquals("3 4", oo.getWidth() + " " + oo.getHeight());
    oo.shiftSize(5, 6);
    assertEquals("8 10", oo.getWidth() + " " + oo.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShiftSizeInvalid() {
    setInit();
    oo.shiftSize(-3, 4);
  }

  ////////// test getters
  IShape r = new Rectangle("R");
  IShape o = new Oval("O");

  private void init() {
    o.create(1, 2, 3, 4, CoordinateType.CORNER, 6, 7, 8);
    r.create(8, 7, 6, 5, CoordinateType.CENTER, 3, 2, 1);
  }


  @Test
  public void testGetName() {
    assertEquals("O", o.getName());
    assertEquals("R", r.getName());
  }

  @Test
  public void testGetX() {
    init();
    assertEquals(1, o.getX());
    assertEquals(8, r.getX());
  }

  @Test
  public void testGetY() {
    init();
    assertEquals(2, o.getY());
    assertEquals(7, r.getY());
  }

  @Test
  public void testGetColor() {
    init();
    assertEquals(new Triplet<>(6, 7, 8), o.getColor());
    assertEquals(new Triplet<>(3, 2, 1), r.getColor());
  }

  @Test
  public void testToString() {
    init();
    assertEquals("001 002 003 004 006 007 008", o.toString());
    assertEquals("008 007 006 005 003 002 001", r.toString());
  }

  @Test
  public void testEquality() {
    init();
    assertEquals(false, r.equals(o));
    assertEquals(true,
        r.equals(new Rectangle("R")));
    assertEquals(false, o == null);
    assertEquals(true,
        o.equals(new Oval("O")));

  }
}
