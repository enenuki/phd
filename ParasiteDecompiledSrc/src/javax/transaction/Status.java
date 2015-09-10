package javax.transaction;

public abstract interface Status
{
  public static final int STATUS_ACTIVE = 0;
  public static final int STATUS_MARKED_ROLLBACK = 1;
  public static final int STATUS_PREPARED = 2;
  public static final int STATUS_COMMITTED = 3;
  public static final int STATUS_ROLLEDBACK = 4;
  public static final int STATUS_UNKNOWN = 5;
  public static final int STATUS_NO_TRANSACTION = 6;
  public static final int STATUS_PREPARING = 7;
  public static final int STATUS_COMMITTING = 8;
  public static final int STATUS_ROLLING_BACK = 9;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.Status
 * JD-Core Version:    0.7.0.1
 */