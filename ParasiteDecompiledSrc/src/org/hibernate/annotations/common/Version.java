/*  1:   */ package org.hibernate.annotations.common;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.hibernate.annotations.common.util.impl.Log;
/*  5:   */ import org.hibernate.annotations.common.util.impl.LoggerFactory;
/*  6:   */ 
/*  7:   */ public class Version
/*  8:   */ {
/*  9:   */   private static final Log log;
/* 10:   */   
/* 11:   */   public Version()
/* 12:   */   {
/* 13:32 */     super();
/* 14:   */   }
/* 15:   */   
/* 16:   */   static
/* 17:   */   {
/* 18:34 */     Version.log = LoggerFactory.make(Version.class.getName());
/* 19:   */     
/* 20:   */ 
/* 21:37 */     Version.log.version(Version.getVersionString());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static void main(String[] args)
/* 25:   */   {
/* 26:48 */     System.out.println("Hibernate Commons Annotations {" + Version.getVersionString() + "}");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String getVersionString()
/* 30:   */   {
/* 31:   */     return "4.0.1.Final";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void touch() {}
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.Version
 * JD-Core Version:    0.7.0.1
 */