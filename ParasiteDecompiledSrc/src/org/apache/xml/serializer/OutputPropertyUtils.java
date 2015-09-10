/*  1:   */ package org.apache.xml.serializer;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ 
/*  5:   */ public final class OutputPropertyUtils
/*  6:   */ {
/*  7:   */   public static boolean getBooleanProperty(String key, Properties props)
/*  8:   */   {
/*  9:52 */     String s = props.getProperty(key);
/* 10:54 */     if ((null == s) || (!s.equals("yes"))) {
/* 11:55 */       return false;
/* 12:   */     }
/* 13:57 */     return true;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static int getIntProperty(String key, Properties props)
/* 17:   */   {
/* 18:75 */     String s = props.getProperty(key);
/* 19:77 */     if (null == s) {
/* 20:78 */       return 0;
/* 21:   */     }
/* 22:80 */     return Integer.parseInt(s);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.OutputPropertyUtils
 * JD-Core Version:    0.7.0.1
 */