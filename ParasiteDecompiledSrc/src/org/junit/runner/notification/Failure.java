/*  1:   */ package org.junit.runner.notification;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ import org.junit.runner.Description;
/*  6:   */ 
/*  7:   */ public class Failure
/*  8:   */ {
/*  9:   */   private final Description fDescription;
/* 10:   */   private final Throwable fThrownException;
/* 11:   */   
/* 12:   */   public Failure(Description description, Throwable thrownException)
/* 13:   */   {
/* 14:25 */     this.fThrownException = thrownException;
/* 15:26 */     this.fDescription = description;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getTestHeader()
/* 19:   */   {
/* 20:33 */     return this.fDescription.getDisplayName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Description getDescription()
/* 24:   */   {
/* 25:40 */     return this.fDescription;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Throwable getException()
/* 29:   */   {
/* 30:48 */     return this.fThrownException;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toString()
/* 34:   */   {
/* 35:53 */     StringBuffer buffer = new StringBuffer();
/* 36:54 */     buffer.append(getTestHeader() + ": " + this.fThrownException.getMessage());
/* 37:55 */     return buffer.toString();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getTrace()
/* 41:   */   {
/* 42:63 */     StringWriter stringWriter = new StringWriter();
/* 43:64 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 44:65 */     getException().printStackTrace(writer);
/* 45:66 */     StringBuffer buffer = stringWriter.getBuffer();
/* 46:67 */     return buffer.toString();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getMessage()
/* 50:   */   {
/* 51:75 */     return getException().getMessage();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.notification.Failure
 * JD-Core Version:    0.7.0.1
 */