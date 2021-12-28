package model.direction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests all functions within DirectionUtils.
 */
public class DirectionUtilsTest {

  @Test
  public void calculateRemainderFrame() {

    int applied = 0;
    int totalRemainder = 10;
    int totalFrames = 1000;
    int delta = 1;

    // Should apply a remainder frame every 100 frames;
    for (int currentFrame = 0; currentFrame < totalFrames; currentFrame++) {
      int remainderFrame = DirectionUtils.calculateRemainderFrame(
          applied, totalRemainder, currentFrame, totalFrames, delta);

      if (currentFrame % 100 == 0) {
        assertEquals(1, remainderFrame);
      } else {
        assertEquals(0, remainderFrame);
      }
    }
  }
}