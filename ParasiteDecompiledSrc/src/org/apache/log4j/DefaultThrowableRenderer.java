/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InterruptedIOException;
/*  5:   */ import java.io.LineNumberReader;
/*  6:   */ import java.io.PrintWriter;
/*  7:   */ import java.io.StringReader;
/*  8:   */ import java.io.StringWriter;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import org.apache.log4j.spi.ThrowableRenderer;
/* 11:   */ 
/* 12:   */ public final class DefaultThrowableRenderer
/* 13:   */   implements ThrowableRenderer
/* 14:   */ {
/* 15:   */   public String[] doRender(Throwable throwable)
/* 16:   */   {
/* 17:48 */     return render(throwable);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static String[] render(Throwable throwable)
/* 21:   */   {
/* 22:57 */     StringWriter sw = new StringWriter();
/* 23:58 */     PrintWriter pw = new PrintWriter(sw);
/* 24:   */     try
/* 25:   */     {
/* 26:60 */       throwable.printStackTrace(pw);
/* 27:   */     }
/* 28:   */     catch (RuntimeException ex) {}
/* 29:63 */     pw.flush();
/* 30:64 */     LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
/* 31:   */     
/* 32:66 */     ArrayList lines = new ArrayList();
/* 33:   */     try
/* 34:   */     {
/* 35:68 */       String line = reader.readLine();
/* 36:69 */       while (line != null)
/* 37:   */       {
/* 38:70 */         lines.add(line);
/* 39:71 */         line = reader.readLine();
/* 40:   */       }
/* 41:   */     }
/* 42:   */     catch (IOException ex)
/* 43:   */     {
/* 44:74 */       if ((ex instanceof InterruptedIOException)) {
/* 45:75 */         Thread.currentThread().interrupt();
/* 46:   */       }
/* 47:77 */       lines.add(ex.toString());
/* 48:   */     }
/* 49:79 */     String[] tempRep = new String[lines.size()];
/* 50:80 */     lines.toArray(tempRep);
/* 51:81 */     return tempRep;
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.DefaultThrowableRenderer
 * JD-Core Version:    0.7.0.1
 */