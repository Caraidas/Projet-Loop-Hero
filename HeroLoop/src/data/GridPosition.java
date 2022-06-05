package data;

import java.io.Serializable;

public record GridPosition(int line, int column) implements Serializable { // represents a position on a grid

}
