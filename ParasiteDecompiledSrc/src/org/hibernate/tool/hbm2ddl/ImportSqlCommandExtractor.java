package org.hibernate.tool.hbm2ddl;

import java.io.Reader;
import org.hibernate.service.Service;

public abstract interface ImportSqlCommandExtractor
  extends Service
{
  public abstract String[] extractCommands(Reader paramReader);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor
 * JD-Core Version:    0.7.0.1
 */