/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class DemuxOutputStream
/*  7:   */   extends OutputStream
/*  8:   */ {
/*  9:32 */   private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal();
/* 10:   */   
/* 11:   */   public OutputStream bindStream(OutputStream output)
/* 12:   */   {
/* 13:42 */     OutputStream stream = (OutputStream)this.m_streams.get();
/* 14:43 */     this.m_streams.set(output);
/* 15:44 */     return stream;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void close()
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:56 */     OutputStream output = (OutputStream)this.m_streams.get();
/* 22:57 */     if (null != output) {
/* 23:59 */       output.close();
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void flush()
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:72 */     OutputStream output = (OutputStream)this.m_streams.get();
/* 31:73 */     if (null != output) {
/* 32:75 */       output.flush();
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void write(int ch)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:89 */     OutputStream output = (OutputStream)this.m_streams.get();
/* 40:90 */     if (null != output) {
/* 41:92 */       output.write(ch);
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.DemuxOutputStream
 * JD-Core Version:    0.7.0.1
 */