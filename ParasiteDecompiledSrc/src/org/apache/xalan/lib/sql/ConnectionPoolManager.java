/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.apache.xalan.res.XSLMessages;
/*   5:    */ 
/*   6:    */ public class ConnectionPoolManager
/*   7:    */ {
/*   8: 36 */   private static Hashtable m_poolTable = null;
/*   9:    */   
/*  10:    */   public ConnectionPoolManager()
/*  11:    */   {
/*  12: 42 */     init();
/*  13:    */   }
/*  14:    */   
/*  15:    */   private synchronized void init()
/*  16:    */   {
/*  17: 55 */     if (m_poolTable == null) {
/*  18: 56 */       m_poolTable = new Hashtable();
/*  19:    */     }
/*  20:    */   }
/*  21:    */   
/*  22:    */   public synchronized void registerPool(String name, ConnectionPool pool)
/*  23:    */   {
/*  24: 74 */     if (m_poolTable.containsKey(name)) {
/*  25: 76 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_POOL_EXISTS", null));
/*  26:    */     }
/*  27: 79 */     m_poolTable.put(name, pool);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public synchronized void removePool(String name)
/*  31:    */   {
/*  32: 91 */     ConnectionPool pool = getPool(name);
/*  33: 93 */     if (null != pool)
/*  34:    */     {
/*  35: 99 */       pool.setPoolEnabled(false);
/*  36:106 */       if (!pool.hasActiveConnections()) {
/*  37:106 */         m_poolTable.remove(name);
/*  38:    */       }
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public synchronized ConnectionPool getPool(String name)
/*  43:    */   {
/*  44:122 */     return (ConnectionPool)m_poolTable.get(name);
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.ConnectionPoolManager
 * JD-Core Version:    0.7.0.1
 */