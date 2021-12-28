package model.utils;

import java.util.Objects;

/**
 * Represents a Pair of variables connected to each other (basically a Tuple).
 * @param <X> The first variable.
 * @param <Y> The second variable.
 */
public class Pair<X,Y> {

  private final X x;
  private final Y y;

  /**
   * Creates a pair of tied variables.
   * @param x The first variable.
   * @param y The second variable.
   */
  public Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the first added variable.
   * @return The first variable.
   */
  public X getValue0() {
    return this.x;
  }

  /**
   * Gets the second added variable.
   * @return The second variable.
   */
  public Y getValue1() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pair)) {
      return false;
    }
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(x, pair.x) && Objects.equals(y, pair.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}