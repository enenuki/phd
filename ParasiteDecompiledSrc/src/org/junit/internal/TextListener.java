/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.text.NumberFormat;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.runner.Description;
/*  7:   */ import org.junit.runner.Result;
/*  8:   */ import org.junit.runner.notification.Failure;
/*  9:   */ import org.junit.runner.notification.RunListener;
/* 10:   */ 
/* 11:   */ public class TextListener
/* 12:   */   extends RunListener
/* 13:   */ {
/* 14:   */   private final PrintStream fWriter;
/* 15:   */   
/* 16:   */   public TextListener(JUnitSystem system)
/* 17:   */   {
/* 18:17 */     this(system.out());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TextListener(PrintStream writer)
/* 22:   */   {
/* 23:21 */     this.fWriter = writer;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void testRunFinished(Result result)
/* 27:   */   {
/* 28:26 */     printHeader(result.getRunTime());
/* 29:27 */     printFailures(result);
/* 30:28 */     printFooter(result);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void testStarted(Description description)
/* 34:   */   {
/* 35:33 */     this.fWriter.append('.');
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void testFailure(Failure failure)
/* 39:   */   {
/* 40:38 */     this.fWriter.append('E');
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void testIgnored(Description description)
/* 44:   */   {
/* 45:43 */     this.fWriter.append('I');
/* 46:   */   }
/* 47:   */   
/* 48:   */   private PrintStream getWriter()
/* 49:   */   {
/* 50:51 */     return this.fWriter;
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected void printHeader(long runTime)
/* 54:   */   {
/* 55:55 */     getWriter().println();
/* 56:56 */     getWriter().println("Time: " + elapsedTimeAsString(runTime));
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected void printFailures(Result result)
/* 60:   */   {
/* 61:60 */     List<Failure> failures = result.getFailures();
/* 62:61 */     if (failures.size() == 0) {
/* 63:62 */       return;
/* 64:   */     }
/* 65:63 */     if (failures.size() == 1) {
/* 66:64 */       getWriter().println("There was " + failures.size() + " failure:");
/* 67:   */     } else {
/* 68:66 */       getWriter().println("There were " + failures.size() + " failures:");
/* 69:   */     }
/* 70:67 */     int i = 1;
/* 71:68 */     for (Failure each : failures) {
/* 72:69 */       printFailure(each, "" + i++);
/* 73:   */     }
/* 74:   */   }
/* 75:   */   
/* 76:   */   protected void printFailure(Failure each, String prefix)
/* 77:   */   {
/* 78:73 */     getWriter().println(prefix + ") " + each.getTestHeader());
/* 79:74 */     getWriter().print(each.getTrace());
/* 80:   */   }
/* 81:   */   
/* 82:   */   protected void printFooter(Result result)
/* 83:   */   {
/* 84:78 */     if (result.wasSuccessful())
/* 85:   */     {
/* 86:79 */       getWriter().println();
/* 87:80 */       getWriter().print("OK");
/* 88:81 */       getWriter().println(" (" + result.getRunCount() + " test" + (result.getRunCount() == 1 ? "" : "s") + ")");
/* 89:   */     }
/* 90:   */     else
/* 91:   */     {
/* 92:84 */       getWriter().println();
/* 93:85 */       getWriter().println("FAILURES!!!");
/* 94:86 */       getWriter().println("Tests run: " + result.getRunCount() + ",  Failures: " + result.getFailureCount());
/* 95:   */     }
/* 96:88 */     getWriter().println();
/* 97:   */   }
/* 98:   */   
/* 99:   */   protected String elapsedTimeAsString(long runTime)
/* :0:   */   {
/* :1:96 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/* :2:   */   }
/* :3:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.TextListener
 * JD-Core Version:    0.7.0.1
 */