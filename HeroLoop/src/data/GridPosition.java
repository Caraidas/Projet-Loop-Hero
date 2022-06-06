package data;

import java.io.Serializable;
import java.util.Objects;

public record GridPosition(int line, int column) implements Serializable { // represents a position on a grid

	@Override
	public int hashCode() {
		return Objects.hash(column, line);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridPosition other = (GridPosition) obj;
		return column == other.column && line == other.line;
	}
	
	
	
}
