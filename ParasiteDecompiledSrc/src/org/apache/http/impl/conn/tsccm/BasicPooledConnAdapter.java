/*  1:   */ package org.apache.http.impl.conn.tsccm;
/*  2:   */ 
/*  3:   */ import org.apache.http.conn.ClientConnectionManager;
/*  4:   */ import org.apache.http.impl.conn.AbstractPoolEntry;
/*  5:   */ import org.apache.http.impl.conn.AbstractPooledConnAdapter;
/*  6:   */ 
/*  7:   */ public class BasicPooledConnAdapter
/*  8:   */   extends AbstractPooledConnAdapter
/*  9:   */ {
/* 10:   */   protected BasicPooledConnAdapter(ThreadSafeClientConnManager tsccm, AbstractPoolEntry entry)
/* 11:   */   {
/* 12:50 */     super(tsccm, entry);
/* 13:51 */     markReusable();
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected ClientConnectionManager getManager()
/* 17:   */   {
/* 18:57 */     return super.getManager();
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected AbstractPoolEntry getPoolEntry()
/* 22:   */   {
/* 23:63 */     return super.getPoolEntry();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void detach()
/* 27:   */   {
/* 28:69 */     super.detach();
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.BasicPooledConnAdapter
 * JD-Core Version:    0.7.0.1
 */