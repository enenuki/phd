package javax.persistence;

public enum LockModeType
{
  READ,  WRITE,  OPTIMISTIC,  OPTIMISTIC_FORCE_INCREMENT,  PESSIMISTIC_READ,  PESSIMISTIC_WRITE,  PESSIMISTIC_FORCE_INCREMENT,  NONE;
  
  private LockModeType() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.LockModeType
 * JD-Core Version:    0.7.0.1
 */