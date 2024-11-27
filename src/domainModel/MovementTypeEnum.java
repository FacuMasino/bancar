package domainModel;

public enum MovementTypeEnum
{
    NEW_ACCOUNT(1),
    NEW_LOAN(2),
    LOAN_PAYMENT(3),
    TRANSFER(4);

    private final int id;

    MovementTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
