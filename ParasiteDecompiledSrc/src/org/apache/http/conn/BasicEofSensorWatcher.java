/*   1:    */ package org.apache.http.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ 
/*   7:    */ @NotThreadSafe
/*   8:    */ public class BasicEofSensorWatcher
/*   9:    */   implements EofSensorWatcher
/*  10:    */ {
/*  11:    */   protected final ManagedClientConnection managedConn;
/*  12:    */   protected final boolean attemptReuse;
/*  13:    */   
/*  14:    */   public BasicEofSensorWatcher(ManagedClientConnection conn, boolean reuse)
/*  15:    */   {
/*  16: 58 */     if (conn == null) {
/*  17: 59 */       throw new IllegalArgumentException("Connection may not be null.");
/*  18:    */     }
/*  19: 62 */     this.managedConn = conn;
/*  20: 63 */     this.attemptReuse = reuse;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean eofDetected(InputStream wrapped)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26:    */     try
/*  27:    */     {
/*  28: 70 */       if (this.attemptReuse)
/*  29:    */       {
/*  30: 73 */         wrapped.close();
/*  31: 74 */         this.managedConn.markReusable();
/*  32:    */       }
/*  33:    */     }
/*  34:    */     finally
/*  35:    */     {
/*  36: 77 */       this.managedConn.releaseConnection();
/*  37:    */     }
/*  38: 79 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean streamClosed(InputStream wrapped)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 86 */       if (this.attemptReuse)
/*  47:    */       {
/*  48: 89 */         wrapped.close();
/*  49: 90 */         this.managedConn.markReusable();
/*  50:    */       }
/*  51:    */     }
/*  52:    */     finally
/*  53:    */     {
/*  54: 93 */       this.managedConn.releaseConnection();
/*  55:    */     }
/*  56: 95 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean streamAbort(InputStream wrapped)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:101 */     this.managedConn.abortConnection();
/*  63:102 */     return false;
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.BasicEofSensorWatcher
 * JD-Core Version:    0.7.0.1
 */