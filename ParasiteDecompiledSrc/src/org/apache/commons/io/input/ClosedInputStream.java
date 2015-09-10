/*  1:   */ package org.apache.commons.io.input;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ 
/*  5:   */ public class ClosedInputStream
/*  6:   */   extends InputStream
/*  7:   */ {
/*  8:37 */   public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();
/*  9:   */   
/* 10:   */   public int read()
/* 11:   */   {
/* 12:46 */     return -1;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.ClosedInputStream
 * JD-Core Version:    0.7.0.1
 */