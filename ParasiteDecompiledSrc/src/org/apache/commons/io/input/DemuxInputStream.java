/*  1:   */ package org.apache.commons.io.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ public class DemuxInputStream
/*  7:   */   extends InputStream
/*  8:   */ {
/*  9:32 */   private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal();
/* 10:   */   
/* 11:   */   public InputStream bindStream(InputStream input)
/* 12:   */   {
/* 13:42 */     InputStream oldValue = (InputStream)this.m_streams.get();
/* 14:43 */     this.m_streams.set(input);
/* 15:44 */     return oldValue;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void close()
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:56 */     InputStream input = (InputStream)this.m_streams.get();
/* 22:57 */     if (null != input) {
/* 23:59 */       input.close();
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int read()
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:73 */     InputStream input = (InputStream)this.m_streams.get();
/* 31:74 */     if (null != input) {
/* 32:76 */       return input.read();
/* 33:   */     }
/* 34:80 */     return -1;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.DemuxInputStream
 * JD-Core Version:    0.7.0.1
 */