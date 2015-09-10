/*  1:   */ package jcifs.util.transport;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ import java.io.StringWriter;
/*  6:   */ 
/*  7:   */ public class TransportException
/*  8:   */   extends IOException
/*  9:   */ {
/* 10:   */   private Throwable rootCause;
/* 11:   */   
/* 12:   */   public TransportException() {}
/* 13:   */   
/* 14:   */   public TransportException(String msg)
/* 15:   */   {
/* 16:14 */     super(msg);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TransportException(Throwable rootCause)
/* 20:   */   {
/* 21:17 */     this.rootCause = rootCause;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public TransportException(String msg, Throwable rootCause)
/* 25:   */   {
/* 26:20 */     super(msg);
/* 27:21 */     this.rootCause = rootCause;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Throwable getRootCause()
/* 31:   */   {
/* 32:25 */     return this.rootCause;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:28 */     if (this.rootCause != null)
/* 38:   */     {
/* 39:29 */       StringWriter sw = new StringWriter();
/* 40:30 */       PrintWriter pw = new PrintWriter(sw);
/* 41:31 */       this.rootCause.printStackTrace(pw);
/* 42:32 */       return super.toString() + "\n" + sw;
/* 43:   */     }
/* 44:34 */     return super.toString();
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.transport.TransportException
 * JD-Core Version:    0.7.0.1
 */