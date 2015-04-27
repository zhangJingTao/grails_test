package hello

class Catalog {

    static constraints = {
        name(unique: true)
    }

    String name;
    Integer amount;
}
