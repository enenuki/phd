/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ public class Transform
/*   4:    */ {
/*   5:    */   private static final String CDATA_START = "<![CDATA[";
/*   6:    */   private static final String CDATA_END = "]]>";
/*   7:    */   private static final String CDATA_PSEUDO_END = "]]&gt;";
/*   8:    */   private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
/*   9: 32 */   private static final int CDATA_END_LEN = "]]>".length();
/*  10:    */   
/*  11:    */   public static String escapeTags(String input)
/*  12:    */   {
/*  13: 47 */     if ((input == null) || (input.length() == 0) || ((input.indexOf('"') == -1) && (input.indexOf('&') == -1) && (input.indexOf('<') == -1) && (input.indexOf('>') == -1))) {
/*  14: 53 */       return input;
/*  15:    */     }
/*  16: 59 */     StringBuffer buf = new StringBuffer(input.length() + 6);
/*  17: 60 */     char ch = ' ';
/*  18:    */     
/*  19: 62 */     int len = input.length();
/*  20: 63 */     for (int i = 0; i < len; i++)
/*  21:    */     {
/*  22: 64 */       ch = input.charAt(i);
/*  23: 65 */       if (ch > '>') {
/*  24: 66 */         buf.append(ch);
/*  25: 67 */       } else if (ch == '<') {
/*  26: 68 */         buf.append("&lt;");
/*  27: 69 */       } else if (ch == '>') {
/*  28: 70 */         buf.append("&gt;");
/*  29: 71 */       } else if (ch == '&') {
/*  30: 72 */         buf.append("&amp;");
/*  31: 73 */       } else if (ch == '"') {
/*  32: 74 */         buf.append("&quot;");
/*  33:    */       } else {
/*  34: 76 */         buf.append(ch);
/*  35:    */       }
/*  36:    */     }
/*  37: 79 */     return buf.toString();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void appendEscapingCDATA(StringBuffer buf, String str)
/*  41:    */   {
/*  42: 93 */     if (str != null)
/*  43:    */     {
/*  44: 94 */       int end = str.indexOf("]]>");
/*  45: 95 */       if (end < 0)
/*  46:    */       {
/*  47: 96 */         buf.append(str);
/*  48:    */       }
/*  49:    */       else
/*  50:    */       {
/*  51: 98 */         int start = 0;
/*  52: 99 */         while (end > -1)
/*  53:    */         {
/*  54:100 */           buf.append(str.substring(start, end));
/*  55:101 */           buf.append("]]>]]&gt;<![CDATA[");
/*  56:102 */           start = end + CDATA_END_LEN;
/*  57:103 */           if (start < str.length()) {
/*  58:104 */             end = str.indexOf("]]>", start);
/*  59:    */           } else {
/*  60:106 */             return;
/*  61:    */           }
/*  62:    */         }
/*  63:109 */         buf.append(str.substring(start));
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.Transform
 * JD-Core Version:    0.7.0.1
 */