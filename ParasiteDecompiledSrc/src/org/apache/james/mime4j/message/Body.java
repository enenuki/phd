package org.apache.james.mime4j.message;

public abstract interface Body
  extends Disposable
{
  public abstract Entity getParent();
  
  public abstract void setParent(Entity paramEntity);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.Body
 * JD-Core Version:    0.7.0.1
 */