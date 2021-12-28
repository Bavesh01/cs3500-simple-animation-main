package model.utils;

import java.util.Objects;

/**
 * Represents a Triple of variables connected to each other (basically a Truple).
 * @param <X> The first variable.
 * @param <Y> The second variable.
 * @param <Z> The third variable.
 */
public class Triplet<X,Y,Z> {
  private final X x;
  private final Y y;
  private final Z z;

  /**
   * Creates a triplet of tied variables.
   * @param x The first variable.
   * @param y The second variable.
   * @param z The third variable.
   */
  public Triplet(X x, Y y, Z z) {
    this.x = x;
    this.y = y;
    this.z = z;
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

  /**
   * Gets the third added variable.
   * @return The third variable.
   */
  public Z getValue2() {
    return this.z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Triplet)) {
      return false;
    }
    Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
    return Objects.equals(x, triplet.x) && Objects.equals(y, triplet.y)
        && Objects.equals(z, triplet.z);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
