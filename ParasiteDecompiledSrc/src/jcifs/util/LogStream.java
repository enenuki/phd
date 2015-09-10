/*  1:   */ package jcifs.util;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class LogStream
/*  6:   */   extends PrintStream
/*  7:   */ {
/*  8:   */   private static LogStream inst;
/*  9:35 */   public static int level = 1;
/* 10:   */   
/* 11:   */   public LogStream(PrintStream stream)
/* 12:   */   {
/* 13:38 */     super(stream);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static void setLevel(int level)
/* 17:   */   {
/* 18:42 */     level = level;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void setInstance(PrintStream stream)
/* 22:   */   {
/* 23:49 */     inst = new LogStream(stream);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static LogStream getInstance()
/* 27:   */   {
/* 28:52 */     if (inst == null) {
/* 29:53 */       setInstance(System.err);
/* 30:   */     }
/* 31:55 */     return inst;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.LogStream
 * JD-Core Version:    0.7.0.1
 */