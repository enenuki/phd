/*  1:   */ package org.apache.james.mime4j.io;
/*  2:   */ 
/*  3:   */ import java.io.FilterInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ 
/*  7:   */ public class LineNumberInputStream
/*  8:   */   extends FilterInputStream
/*  9:   */   implements LineNumberSource
/* 10:   */ {
/* 11:32 */   private int lineNumber = 1;
/* 12:   */   
/* 13:   */   public LineNumberInputStream(InputStream is)
/* 14:   */   {
/* 15:41 */     super(is);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getLineNumber()
/* 19:   */   {
/* 20:45 */     return this.lineNumber;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int read()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:50 */     int b = this.in.read();
/* 27:51 */     if (b == 10) {
/* 28:52 */       this.lineNumber += 1;
/* 29:   */     }
/* 30:54 */     return b;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int read(byte[] b, int off, int len)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:59 */     int n = this.in.read(b, off, len);
/* 37:60 */     for (int i = off; i < off + n; i++) {
/* 38:61 */       if (b[i] == 10) {
/* 39:62 */         this.lineNumber += 1;
/* 40:   */       }
/* 41:   */     }
/* 42:65 */     return n;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.LineNumberInputStream
 * JD-Core Version:    0.7.0.1
 */