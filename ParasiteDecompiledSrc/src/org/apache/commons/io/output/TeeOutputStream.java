/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class TeeOutputStream
/*  7:   */   extends ProxyOutputStream
/*  8:   */ {
/*  9:   */   protected OutputStream branch;
/* 10:   */   
/* 11:   */   public TeeOutputStream(OutputStream out, OutputStream branch)
/* 12:   */   {
/* 13:40 */     super(out);
/* 14:41 */     this.branch = branch;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public synchronized void write(byte[] b)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:51 */     super.write(b);
/* 21:52 */     this.branch.write(b);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public synchronized void write(byte[] b, int off, int len)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:64 */     super.write(b, off, len);
/* 28:65 */     this.branch.write(b, off, len);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public synchronized void write(int b)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:75 */     super.write(b);
/* 35:76 */     this.branch.write(b);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void flush()
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:85 */     super.flush();
/* 42:86 */     this.branch.flush();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void close()
/* 46:   */     throws IOException
/* 47:   */   {
/* 48:95 */     super.close();
/* 49:96 */     this.branch.close();
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.TeeOutputStream
 * JD-Core Version:    0.7.0.1
 */