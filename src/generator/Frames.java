package generator;

import java.util.Objects;

/**
 * A Frame is a Collection of the position of an element in x, y.
 * It also stores info about its status as 0 is alive, 1 is affected and 2 is dead.
 */
class Frames {

  private final int x;
  private final int y;
  private final int status;

  Frames(int x, int y, int status) {
    this.x = x;
    this.y = y;
    this.status = status;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Frames
        && x == ((Frames) obj).x
        && y == ((Frames) obj).y
        && status == ((Frames) obj).status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, status);
  }
}
