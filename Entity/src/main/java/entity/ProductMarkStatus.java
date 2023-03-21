package entity;

public enum ProductMarkStatus {

    BEST(1),
    GOOD(2),
    BAD(3),
    DONT_MARK(4);


    private int id;

    ProductMarkStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
