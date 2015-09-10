/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class XMLCharacterRecognizer
/*   4:    */ {
/*   5:    */   public static boolean isWhiteSpace(char ch)
/*   6:    */   {
/*   7: 40 */     return (ch == ' ') || (ch == '\t') || (ch == '\r') || (ch == '\n');
/*   8:    */   }
/*   9:    */   
/*  10:    */   public static boolean isWhiteSpace(char[] ch, int start, int length)
/*  11:    */   {
/*  12: 55 */     int end = start + length;
/*  13: 57 */     for (int s = start; s < end; s++) {
/*  14: 59 */       if (!isWhiteSpace(ch[s])) {
/*  15: 60 */         return false;
/*  16:    */       }
/*  17:    */     }
/*  18: 63 */     return true;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static boolean isWhiteSpace(StringBuffer buf)
/*  22:    */   {
/*  23: 75 */     int n = buf.length();
/*  24: 77 */     for (int i = 0; i < n; i++) {
/*  25: 79 */       if (!isWhiteSpace(buf.charAt(i))) {
/*  26: 80 */         return false;
/*  27:    */       }
/*  28:    */     }
/*  29: 83 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static boolean isWhiteSpace(String s)
/*  33:    */   {
/*  34: 95 */     if (null != s)
/*  35:    */     {
/*  36: 97 */       int n = s.length();
/*  37: 99 */       for (int i = 0; i < n; i++) {
/*  38:101 */         if (!isWhiteSpace(s.charAt(i))) {
/*  39:102 */           return false;
/*  40:    */         }
/*  41:    */       }
/*  42:    */     }
/*  43:106 */     return true;
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLCharacterRecognizer
 * JD-Core Version:    0.7.0.1
 */