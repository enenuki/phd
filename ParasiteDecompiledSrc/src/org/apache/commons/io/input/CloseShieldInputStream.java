/*  1:   */ package org.apache.commons.io.input;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ 
/*  5:   */ public class CloseShieldInputStream
/*  6:   */   extends ProxyInputStream
/*  7:   */ {
/*  8:   */   public CloseShieldInputStream(InputStream in)
/*  9:   */   {
/* 10:40 */     super(in);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void close()
/* 14:   */   {
/* 15:50 */     this.in = new ClosedInputStream();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.CloseShieldInputStream
 * JD-Core Version:    0.7.0.1
 */