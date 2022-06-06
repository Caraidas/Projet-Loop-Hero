package inventory;

import java.io.Serializable;

public record Ressource(String name, Ressource next, int quantityToNext, int points) implements Serializable {

}
