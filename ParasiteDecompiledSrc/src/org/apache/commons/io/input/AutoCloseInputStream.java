/*  1:   */ package org.apache.commons.io.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ public class AutoCloseInputStream
/*  7:   */   extends ProxyInputStream
/*  8:   */ {
/*  9:   */   public AutoCloseInputStream(InputStream in)
/* 10:   */   {
/* 11:45 */     super(in);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void close()
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:63 */     this.in.close();
/* 18:64 */     this.in = new ClosedInputStream();
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void afterRead(int n)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:76 */     if (n == -1) {
/* 25:77 */       close();
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void finalize()
/* 30:   */     throws Throwable
/* 31:   */   {
/* 32:89 */     close();
/* 33:90 */     super.finalize();
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.AutoCloseInputStream
 * JD-Core Version:    0.7.0.1
 */