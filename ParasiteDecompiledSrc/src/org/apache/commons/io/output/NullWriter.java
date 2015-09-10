/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.Writer;
/*  4:   */ 
/*  5:   */ public class NullWriter
/*  6:   */   extends Writer
/*  7:   */ {
/*  8:34 */   public static final NullWriter NULL_WRITER = new NullWriter();
/*  9:   */   
/* 10:   */   public Writer append(char c)
/* 11:   */   {
/* 12:51 */     return this;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Writer append(CharSequence csq, int start, int end)
/* 16:   */   {
/* 17:65 */     return this;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Writer append(CharSequence csq)
/* 21:   */   {
/* 22:77 */     return this;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void write(int idx) {}
/* 26:   */   
/* 27:   */   public void write(char[] chr) {}
/* 28:   */   
/* 29:   */   public void write(char[] chr, int st, int end) {}
/* 30:   */   
/* 31:   */   public void write(String str) {}
/* 32:   */   
/* 33:   */   public void write(String str, int st, int end) {}
/* 34:   */   
/* 35:   */   public void flush() {}
/* 36:   */   
/* 37:   */   public void close() {}
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.NullWriter
 * JD-Core Version:    0.7.0.1
 */