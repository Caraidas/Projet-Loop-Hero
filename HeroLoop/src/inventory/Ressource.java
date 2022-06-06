package inventory;

public record Ressource(String name, Ressource next, int quantityToNext, int points) {

}
