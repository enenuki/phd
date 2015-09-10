/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class NullOutputStream
/*  7:   */   extends OutputStream
/*  8:   */ {
/*  9:36 */   public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();
/* 10:   */   
/* 11:   */   public void write(byte[] b, int off, int len) {}
/* 12:   */   
/* 13:   */   public void write(int b) {}
/* 14:   */   
/* 15:   */   public void write(byte[] b)
/* 16:   */     throws IOException
/* 17:   */   {}
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.NullOutputStream
 * JD-Core Version:    0.7.0.1
 */