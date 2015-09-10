/*  1:   */ package org.apache.xml.serializer.dom3;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.w3c.dom.DOMError;
/*  5:   */ import org.w3c.dom.DOMErrorHandler;
/*  6:   */ 
/*  7:   */ final class DOMErrorHandlerImpl
/*  8:   */   implements DOMErrorHandler
/*  9:   */ {
/* 10:   */   public boolean handleError(DOMError error)
/* 11:   */   {
/* 12:48 */     boolean fail = true;
/* 13:49 */     String severity = null;
/* 14:50 */     if (error.getSeverity() == 1)
/* 15:   */     {
/* 16:51 */       fail = false;
/* 17:52 */       severity = "[Warning]";
/* 18:   */     }
/* 19:53 */     else if (error.getSeverity() == 2)
/* 20:   */     {
/* 21:54 */       severity = "[Error]";
/* 22:   */     }
/* 23:55 */     else if (error.getSeverity() == 3)
/* 24:   */     {
/* 25:56 */       severity = "[Fatal Error]";
/* 26:   */     }
/* 27:59 */     System.err.println(severity + ": " + error.getMessage() + "\t");
/* 28:60 */     System.err.println("Type : " + error.getType() + "\t" + "Related Data: " + error.getRelatedData() + "\t" + "Related Exception: " + error.getRelatedException());
/* 29:   */     
/* 30:   */ 
/* 31:   */ 
/* 32:64 */     return fail;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOMErrorHandlerImpl
 * JD-Core Version:    0.7.0.1
 */