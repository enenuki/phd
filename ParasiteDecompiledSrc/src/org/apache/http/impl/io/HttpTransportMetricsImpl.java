/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import org.apache.http.io.HttpTransportMetrics;
/*  4:   */ 
/*  5:   */ public class HttpTransportMetricsImpl
/*  6:   */   implements HttpTransportMetrics
/*  7:   */ {
/*  8:39 */   private long bytesTransferred = 0L;
/*  9:   */   
/* 10:   */   public long getBytesTransferred()
/* 11:   */   {
/* 12:46 */     return this.bytesTransferred;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setBytesTransferred(long count)
/* 16:   */   {
/* 17:50 */     this.bytesTransferred = count;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void incrementBytesTransferred(long count)
/* 21:   */   {
/* 22:54 */     this.bytesTransferred += count;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void reset()
/* 26:   */   {
/* 27:58 */     this.bytesTransferred = 0L;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.HttpTransportMetricsImpl
 * JD-Core Version:    0.7.0.1
 */