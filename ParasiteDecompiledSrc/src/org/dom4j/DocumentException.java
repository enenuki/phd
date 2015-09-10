/*  1:   */ package org.dom4j;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ 
/*  6:   */ public class DocumentException
/*  7:   */   extends Exception
/*  8:   */ {
/*  9:   */   private Throwable nestedException;
/* 10:   */   
/* 11:   */   public DocumentException()
/* 12:   */   {
/* 13:27 */     super("Error occurred in DOM4J application.");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public DocumentException(String message)
/* 17:   */   {
/* 18:31 */     super(message);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public DocumentException(Throwable nestedException)
/* 22:   */   {
/* 23:35 */     super(nestedException.getMessage());
/* 24:36 */     this.nestedException = nestedException;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public DocumentException(String message, Throwable nestedException)
/* 28:   */   {
/* 29:40 */     super(message);
/* 30:41 */     this.nestedException = nestedException;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Throwable getNestedException()
/* 34:   */   {
/* 35:45 */     return this.nestedException;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getMessage()
/* 39:   */   {
/* 40:49 */     if (this.nestedException != null) {
/* 41:50 */       return super.getMessage() + " Nested exception: " + this.nestedException.getMessage();
/* 42:   */     }
/* 43:53 */     return super.getMessage();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void printStackTrace()
/* 47:   */   {
/* 48:58 */     super.printStackTrace();
/* 49:60 */     if (this.nestedException != null)
/* 50:   */     {
/* 51:61 */       System.err.print("Nested exception: ");
/* 52:62 */       this.nestedException.printStackTrace();
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void printStackTrace(PrintStream out)
/* 57:   */   {
/* 58:67 */     super.printStackTrace(out);
/* 59:69 */     if (this.nestedException != null)
/* 60:   */     {
/* 61:70 */       out.println("Nested exception: ");
/* 62:71 */       this.nestedException.printStackTrace(out);
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void printStackTrace(PrintWriter writer)
/* 67:   */   {
/* 68:76 */     super.printStackTrace(writer);
/* 69:78 */     if (this.nestedException != null)
/* 70:   */     {
/* 71:79 */       writer.println("Nested exception: ");
/* 72:80 */       this.nestedException.printStackTrace(writer);
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.DocumentException
 * JD-Core Version:    0.7.0.1
 */