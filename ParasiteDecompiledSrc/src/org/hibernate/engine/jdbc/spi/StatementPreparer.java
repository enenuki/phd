package org.hibernate.engine.jdbc.spi;

import java.sql.PreparedStatement;
import org.hibernate.ScrollMode;

public abstract interface StatementPreparer
{
  public abstract PreparedStatement prepareStatement(String paramString);
  
  public abstract PreparedStatement prepareStatement(String paramString, boolean paramBoolean);
  
  public abstract PreparedStatement prepareStatement(String paramString, int paramInt);
  
  public abstract PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString);
  
  public abstract PreparedStatement prepareQueryStatement(String paramString, boolean paramBoolean, ScrollMode paramScrollMode);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.StatementPreparer
 * JD-Core Version:    0.7.0.1
 */