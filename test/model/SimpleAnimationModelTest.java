package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import model.direction.IDirection;
import model.shape.CoordinateType;
import model.shape.IShape;
import org.junit.Before;
import org.junit.Test;


/**
 * Class for testing methods in SimpleAnimationModel.
 */
public class SimpleAnimationModelTest {

  private SimpleAnimationModel modelWithShapesAndDirections;
  private SimpleAnimationModel modelWithShapes;
  private SimpleAnimationModel emptyModel;

  @Before
  public void setUp() {
    this.modelWithShapesAndDirections = new SimpleAnimationModel();
    this.modelWithShapesAndDirections.initShape("A", "rectangle");
    this.modelWithShapesAndDirections.initShape("B", "oval");
    this.modelWithShapesAndDirections.createShape("A", 1, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);
    this.modelWithShapesAndDirections.createShape("B", 1, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);
    this.modelWithShapesAndDirections.moveShape("A", 1, 1, 2, 2);
    this.modelWithShapesAndDirections.moveShape("B", 2, 2, 2, 2);
    this.modelWithShapesAndDirections.recolorShape("A", 1, 1, 1, 2, 2);
    this.modelWithShapesAndDirections.recolorShape("B", 2, 2, 2, 2, 2);

    this.modelWithShapes = new SimpleAnimationModel();
    this.modelWithShapes.initShape("A", "rectangle");
    this.modelWithShapes.initShape("B", "oval");

    this.emptyModel = new SimpleAnimationModel();
  }

  @Test
  public void initializeAnimation_WithFrameGap_ThrowsIllegalArgumentException() {
    this.modelWithShapesAndDirections.moveShape("A", 2, 2, 4, 4);

    try {
      this.modelWithShapesAndDirections.initializeAnimation();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape 'A' has a frame gap.", e.getMessage());
    }
  }

  @Test
  public void initializeAnimation_WithSameTypeOverlap_ThrowsIllegalArgumentException() {
    this.modelWithShapesAndDirections.moveShape("A", 2, 2, 2, 3);
    this.modelWithShapesAndDirections.moveShape("A", 2, 2, 2, 4);

    try {
      this.modelWithShapesAndDirections.initializeAnimation();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape 'A' has a MOVE change overlap.", e.getMessage());
    }
  }

  @Test
  public void initializeAnimation_WithInitializeAlreadyRan_ThrowsIllegalStateException() {
    this.modelWithShapesAndDirections.initializeAnimation();

    try {
      this.modelWithShapesAndDirections.initializeAnimation();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void initializeAnimation_WithValidInitialization_RunsSuccessfully() {
    try {
      this.modelWithShapesAndDirections.initializeAnimation();
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void advanceFrame_WithoutInitialization_ThrowsIllegalStateException() {
    try {
      this.modelWithShapesAndDirections.advanceShapes();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Animation has not been initialized yet.", e.getMessage());
    }
  }

  @Test
  public void advanceFrame_WithInitialization_AdvancesFrameOfAllShapes() {
    this.modelWithShapesAndDirections.initializeAnimation();
    this.modelWithShapesAndDirections.advanceShapes();

    List<IShape> shapes = this.modelWithShapesAndDirections.getShapes();

    for (IShape shape : shapes) {
      if (shape.getName().equals("A")) {
        assertEquals(0, shape.getX());
        assertEquals(0, shape.getY());
        assertEquals(0, (int) shape.getColor().getValue0());
        assertEquals(0, (int) shape.getColor().getValue1());
        assertEquals(0, (int) shape.getColor().getValue2());
      } else if (shape.getName().equals("B")) {
        assertEquals(0, shape.getX());
        assertEquals(0, shape.getY());
        assertEquals(0, (int) shape.getColor().getValue0());
        assertEquals(0, (int) shape.getColor().getValue1());
        assertEquals(0, (int) shape.getColor().getValue2());
      }
    }

    this.modelWithShapesAndDirections.advanceShapes();
    this.modelWithShapesAndDirections.advanceShapes();
    shapes = this.modelWithShapesAndDirections.getShapes();

    for (IShape shape : shapes) {
      if (shape.getName().equals("A")) {
        assertEquals(1, shape.getX());
        assertEquals(1, shape.getY());
        assertEquals(1, (int) shape.getColor().getValue0());
        assertEquals(1, (int) shape.getColor().getValue1());
        assertEquals(1, (int) shape.getColor().getValue2());
      } else if (shape.getName().equals("B")) {
        assertEquals(2, shape.getX());
        assertEquals(2, shape.getY());
        assertEquals(2, (int) shape.getColor().getValue0());
        assertEquals(2, (int) shape.getColor().getValue1());
        assertEquals(2, (int) shape.getColor().getValue2());
      }
    }
  }

  @Test
  public void initShape_WithInitialization_ThrowsIllegalStateException() {
    this.emptyModel.initializeAnimation();

    try {
      this.emptyModel.initShape("A", "rectangle");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void initShape_WithRepeatShapeName_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapes.initShape("A", "oval");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape with that name already exists.", e.getMessage());
    }
  }

  @Test
  public void initShape_WithInvalidShapeType_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.initShape("A", "woeirufhweiuh");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape type doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void initShape_WithValidParameters_InitializesNewShape() {
    assertEquals(0, this.emptyModel.getShapes().size());

    this.emptyModel.initShape("A", "oval");

    assertEquals(1, this.emptyModel.getShapes().size());
  }

  @Test
  public void createShape_WithInitialization_ThrowsIllegalStateException() {
    this.modelWithShapes.initializeAnimation();

    try {
      this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
          CoordinateType.CENTER, 0, 0, 0);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void createShape_WithoutInitShape_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.createShape("A", 0, 0, 0, 1, 1,
          CoordinateType.CENTER, 0, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No shape with that name exists.", e.getMessage());
    }
  }

  @Test
  public void createShape_WithValidParameters_CreatesDirection() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);

    assertEquals(1, this.modelWithShapes.getDirectionsForShape("A").size());

    IDirection create = this.modelWithShapes.getDirectionsForShape("A").get(0);

    assertEquals("CREATE", create.getType());
    assertEquals(0, create.getStartFrame());
    assertEquals(0, create.getEndFrame());
  }

  @Test
  public void moveShape_WithInitialization_ThrowsIllegalStateException() {
    this.modelWithShapes.initializeAnimation();

    try {
      this.modelWithShapes.moveShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void moveShape_WithoutInitShape_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.moveShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No shape with that name exists.", e.getMessage());
    }
  }

  @Test
  public void moveShape_WithoutCreateShape_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapes.moveShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Create direction hasn't been queued yet.", e.getMessage());
    }
  }

  @Test
  public void moveShape_AfterRemoval_ThrowsIllegalArgumentException() {
    this.modelWithShapesAndDirections.removeShape("A", 3);

    try {
      this.modelWithShapesAndDirections.moveShape("A", 1, 1,
          3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Command takes place after shape removal.", e.getMessage());
    }
  }

  @Test
  public void moveShape_WithValidSetup_AddsNewDirection() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);

    this.modelWithShapes.moveShape("A", 1, 1, 1, 1);

    assertEquals(2, this.modelWithShapes.getDirectionsForShape("A").size());

    IDirection create = this.modelWithShapes.getDirectionsForShape("A").get(1);

    assertEquals("MOVE", create.getType());
    assertEquals(1, create.getStartFrame());
    assertEquals(1, create.getEndFrame());
  }


  @Test
  public void resizeShape_WithInitialization_ThrowsIllegalStateException() {
    this.modelWithShapes.initializeAnimation();

    try {
      this.modelWithShapes.resizeShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void resizeShape_WithoutInitShape_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.resizeShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No shape with that name exists.", e.getMessage());
    }
  }

  @Test
  public void resizeShape_WithoutCreateShape_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapes.resizeShape("A", 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Create direction hasn't been queued yet.", e.getMessage());
    }
  }

  @Test
  public void resizeShape_AfterRemoval_ThrowsIllegalArgumentException() {
    this.modelWithShapesAndDirections.removeShape("A", 3);

    try {
      this.modelWithShapesAndDirections.resizeShape("A", 1, 1,
          3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Command takes place after shape removal.", e.getMessage());
    }
  }

  @Test
  public void resizeShape_WithValidSetup_AddsNewDirection() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);

    this.modelWithShapes.resizeShape("A", 1, 1, 1, 1);

    assertEquals(2, this.modelWithShapes.getDirectionsForShape("A").size());

    IDirection create = this.modelWithShapes.getDirectionsForShape("A").get(1);

    assertEquals("RESIZE", create.getType());
    assertEquals(1, create.getStartFrame());
    assertEquals(1, create.getEndFrame());
  }

  @Test
  public void recolorShape_WithInitialization_ThrowsIllegalStateException() {
    this.modelWithShapes.initializeAnimation();

    try {
      this.modelWithShapes.recolorShape("A", 1, 1, 1, 1, 1);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void recolorShape_WithoutInitShape_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.recolorShape("A", 1, 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No shape with that name exists.", e.getMessage());
    }
  }

  @Test
  public void recolorShape_WithoutCreateShape_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapes.recolorShape("A", 1, 1, 1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Create direction hasn't been queued yet.", e.getMessage());
    }
  }

  @Test
  public void recolorShape_AfterRemoval_ThrowsIllegalArgumentException() {
    this.modelWithShapesAndDirections.removeShape("A", 3);

    try {
      this.modelWithShapesAndDirections.recolorShape("A", 1, 1, 1,
          3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Command takes place after shape removal.", e.getMessage());
    }
  }

  @Test
  public void recolorShape_WithValidSetup_AddsNewDirection() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);

    this.modelWithShapes.recolorShape("A", 1, 1, 1, 1, 1);

    assertEquals(2, this.modelWithShapes.getDirectionsForShape("A").size());

    IDirection create = this.modelWithShapes.getDirectionsForShape("A").get(1);

    assertEquals("COLOR", create.getType());
    assertEquals(1, create.getStartFrame());
    assertEquals(1, create.getEndFrame());
  }

  @Test
  public void removeShape_WithInitialization_ThrowsIllegalStateException() {
    this.modelWithShapes.initializeAnimation();

    try {
      this.modelWithShapes.removeShape("A", 1);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Action cannot be done, animation is already initialized.",
          e.getMessage());
    }
  }

  @Test
  public void removeShape_WithoutInitShape_ThrowsIllegalArgumentException() {
    try {
      this.emptyModel.removeShape("A", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No shape with that name exists.", e.getMessage());
    }
  }

  @Test
  public void removeShape_WithoutCreateShape_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapes.removeShape("A", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Create direction hasn't been queued yet.", e.getMessage());
    }
  }

  @Test
  public void removeShape_WithAlreadyQueuedRemoval_ThrowsIllegalArgumentException() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);
    this.modelWithShapes.removeShape("A", 1);

    try {
      this.modelWithShapes.removeShape("A", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape already has a removal set.", e.getMessage());
    }
  }


  @Test
  public void removeShape_WithInterruptingRemoval_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapesAndDirections.removeShape("A", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Removal occurs before all queued directions are complete.",
          e.getMessage());
    }
  }

  @Test
  public void removeShape_WithValidSetup_AddsToRemoveQueue() {
    this.modelWithShapes.createShape("A", 0, 0, 0, 1, 1,
        CoordinateType.CENTER, 0, 0, 0);
    this.modelWithShapes.removeShape("A", 1);
    this.modelWithShapes.initializeAnimation();

    assertEquals(2, this.modelWithShapes.getShapes().size());

    this.modelWithShapes.advanceShapes();
    this.modelWithShapes.advanceShapes();

    assertEquals(1, this.modelWithShapes.getShapes().size());
  }

  @Test
  public void getDirections_returnsAllDirections() {

    Map<String, List<IDirection>> directions = this.modelWithShapesAndDirections.getDirections();

    assertEquals(2, directions.size());

    int totalCommands = 0;

    for (String shapeName : directions.keySet()) {
      for (IDirection direction : directions.get(shapeName)) {
        totalCommands++;
      }
    }

    assertEquals(6, totalCommands);
  }

  @Test
  public void getDirectionsForShape_returnsDirectionsForParticularShape() {
    List<IDirection> directions = this.modelWithShapesAndDirections
        .getDirectionsForShape("A");

    for (IDirection direction : directions) {
      assertTrue(direction.toString().contains(" A "));
    }
  }

  @Test
  public void removeDirection_WithNonexistentDirection_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapesAndDirections.removeDirection(
          "A", "MOVE", 3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Direction not found.", e.getMessage());
    }
  }

  @Test
  public void removeDirection_RemoveCreateWithQueuedDirections_ThrowsIllegalArgumentException() {
    try {
      this.modelWithShapesAndDirections.removeDirection(
          "A", "CREATE", 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot remove create while function still has other directions.",
          e.getMessage());
    }
  }

  @Test
  public void removeDirection_RemovesDirection() {
    assertEquals(3,
        this.modelWithShapesAndDirections.getDirectionsForShape("A").size());

    this.modelWithShapesAndDirections.removeDirection(
        "A", "MOVE", 2, 2);

    assertEquals(2,
        this.modelWithShapesAndDirections.getDirectionsForShape("A").size());
  }

  @Test
  public void getShapes_ReturnsShapes() {
    assertEquals("A B",
        this.modelWithShapes.getShapes().get(0).getName() + " "
            + this.modelWithShapes.getShapes().get(1).getName());
  }
}