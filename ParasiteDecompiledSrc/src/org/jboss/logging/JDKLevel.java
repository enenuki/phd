/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.logging.Level;
/*  4:   */ 
/*  5:   */ final class JDKLevel
/*  6:   */   extends Level
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   
/* 10:   */   protected JDKLevel(String name, int value)
/* 11:   */   {
/* 12:35 */     super(name, value);
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected JDKLevel(String name, int value, String resourceBundleName)
/* 16:   */   {
/* 17:39 */     super(name, value, resourceBundleName);
/* 18:   */   }
/* 19:   */   
/* 20:42 */   public static final JDKLevel FATAL = new JDKLevel("FATAL", 1100);
/* 21:43 */   public static final JDKLevel ERROR = new JDKLevel("ERROR", 1000);
/* 22:44 */   public static final JDKLevel WARN = new JDKLevel("WARN", 900);
/* 23:46 */   public static final JDKLevel INFO = new JDKLevel("INFO", 800);
/* 24:47 */   public static final JDKLevel DEBUG = new JDKLevel("DEBUG", 500);
/* 25:48 */   public static final JDKLevel TRACE = new JDKLevel("TRACE", 400);
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JDKLevel
 * JD-Core Version:    0.7.0.1
 */