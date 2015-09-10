/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class ClosedOutputStream
/*  7:   */   extends OutputStream
/*  8:   */ {
/*  9:38 */   public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();
/* 10:   */   
/* 11:   */   public void write(int b)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:48 */     throw new IOException("write(" + b + ") failed: stream is closed");
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.ClosedOutputStream
 * JD-Core Version:    0.7.0.1
 */