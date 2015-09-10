package org.hibernate.tool.hbm2ddl;

abstract interface Exporter
{
  public abstract boolean acceptsImportScripts();
  
  public abstract void export(String paramString)
    throws Exception;
  
  public abstract void release()
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.Exporter
 * JD-Core Version:    0.7.0.1
 */