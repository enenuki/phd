/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class CppCharFormatter
/*  4:   */   implements CharFormatter
/*  5:   */ {
/*  6:   */   public String escapeChar(int paramInt, boolean paramBoolean)
/*  7:   */   {
/*  8:28 */     switch (paramInt)
/*  9:   */     {
/* 10:   */     case 10: 
/* 11:29 */       return "\\n";
/* 12:   */     case 9: 
/* 13:30 */       return "\\t";
/* 14:   */     case 13: 
/* 15:31 */       return "\\r";
/* 16:   */     case 92: 
/* 17:32 */       return "\\\\";
/* 18:   */     case 39: 
/* 19:33 */       return "\\'";
/* 20:   */     case 34: 
/* 21:34 */       return "\\\"";
/* 22:   */     }
/* 23:36 */     if ((paramInt < 32) || (paramInt > 126))
/* 24:   */     {
/* 25:38 */       if (paramInt > 255)
/* 26:   */       {
/* 27:40 */         String str = Integer.toString(paramInt, 16);
/* 28:42 */         while (str.length() < 4) {
/* 29:43 */           str = '0' + str;
/* 30:   */         }
/* 31:44 */         return "\\u" + str;
/* 32:   */       }
/* 33:47 */       return "\\" + Integer.toString(paramInt, 8);
/* 34:   */     }
/* 35:51 */     return String.valueOf((char)paramInt);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String escapeString(String paramString)
/* 39:   */   {
/* 40:65 */     String str = new String();
/* 41:66 */     for (int i = 0; i < paramString.length(); i++) {
/* 42:67 */       str = str + escapeChar(paramString.charAt(i), false);
/* 43:   */     }
/* 44:69 */     return str;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String literalChar(int paramInt)
/* 48:   */   {
/* 49:78 */     String str = "0x" + Integer.toString(paramInt, 16);
/* 50:79 */     if ((paramInt >= 0) && (paramInt <= 126)) {
/* 51:80 */       str = str + " /* '" + escapeChar(paramInt, true) + "' */ ";
/* 52:   */     }
/* 53:81 */     return str;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String literalString(String paramString)
/* 57:   */   {
/* 58:94 */     return "\"" + escapeString(paramString) + "\"";
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CppCharFormatter
 * JD-Core Version:    0.7.0.1
 */