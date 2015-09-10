/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.hibernate.internal.CoreMessageLogger;
/*  5:   */ import org.jboss.logging.Logger;
/*  6:   */ 
/*  7:   */ public class Version
/*  8:   */ {
/*  9:   */   public Version()
/* 10:   */   {
/* 11:35 */     super();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static String getVersionString()
/* 15:   */   {
/* 16:   */     return "4.0.0.Final";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void logVersion()
/* 20:   */   {
/* 21:41 */     ((CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Version.class.getName())).version(Version.getVersionString());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static void main(String[] args)
/* 25:   */   {
/* 26:45 */     System.out.println("Hibernate Core {" + Version.getVersionString() + "}");
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Version
 * JD-Core Version:    0.7.0.1
 */