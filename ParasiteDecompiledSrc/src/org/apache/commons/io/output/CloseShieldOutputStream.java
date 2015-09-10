/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.OutputStream;
/*  4:   */ 
/*  5:   */ public class CloseShieldOutputStream
/*  6:   */   extends ProxyOutputStream
/*  7:   */ {
/*  8:   */   public CloseShieldOutputStream(OutputStream out)
/*  9:   */   {
/* 10:40 */     super(out);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void close()
/* 14:   */   {
/* 15:50 */     this.out = new ClosedOutputStream();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.CloseShieldOutputStream
 * JD-Core Version:    0.7.0.1
 */