/*  1:   */ package org.junit.experimental.results;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayOutputStream;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.internal.TextListener;
/*  7:   */ import org.junit.runner.JUnitCore;
/*  8:   */ import org.junit.runner.Result;
/*  9:   */ import org.junit.runner.notification.Failure;
/* 10:   */ 
/* 11:   */ public class PrintableResult
/* 12:   */ {
/* 13:   */   private Result result;
/* 14:   */   
/* 15:   */   public static PrintableResult testResult(Class<?> type)
/* 16:   */   {
/* 17:26 */     return new PrintableResult(type);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public PrintableResult(List<Failure> failures)
/* 21:   */   {
/* 22:35 */     this(new FailureList(failures).result());
/* 23:   */   }
/* 24:   */   
/* 25:   */   private PrintableResult(Result result)
/* 26:   */   {
/* 27:39 */     this.result = result;
/* 28:   */   }
/* 29:   */   
/* 30:   */   private PrintableResult(Class<?> type)
/* 31:   */   {
/* 32:43 */     this(JUnitCore.runClasses(new Class[] { type }));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:48 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 38:49 */     new TextListener(new PrintStream(stream)).testRunFinished(this.result);
/* 39:50 */     return stream.toString();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int failureCount()
/* 43:   */   {
/* 44:57 */     return this.result.getFailures().size();
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.results.PrintableResult
 * JD-Core Version:    0.7.0.1
 */