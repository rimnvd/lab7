package data;

import java.io.Serializable;

/**
 * X-Y coordinates.
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 21L;
    private final Integer x;
    private final Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X-coordinate.
     */
    public Integer getX() {
        return x;
    }

    /**
     * @return Y-coordinate.
     */
    public Integer getY() {
        return y;
    }


}
