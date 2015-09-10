/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class CSharpCharFormatter
/*  4:   */   implements CharFormatter
/*  5:   */ {
/*  6:   */   public String escapeChar(int paramInt, boolean paramBoolean)
/*  7:   */   {
/*  8:26 */     switch (paramInt)
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
/* 19:33 */       return paramBoolean ? "\\'" : "'";
/* 20:   */     case 34: 
/* 21:34 */       return paramBoolean ? "\"" : "\\\"";
/* 22:   */     }
/* 23:36 */     if ((paramInt < 32) || (paramInt > 126))
/* 24:   */     {
/* 25:38 */       if ((0 <= paramInt) && (paramInt <= 15)) {
/* 26:40 */         return "\\u000" + Integer.toString(paramInt, 16);
/* 27:   */       }
/* 28:42 */       if ((16 <= paramInt) && (paramInt <= 255)) {
/* 29:44 */         return "\\u00" + Integer.toString(paramInt, 16);
/* 30:   */       }
/* 31:46 */       if ((256 <= paramInt) && (paramInt <= 4095)) {
/* 32:48 */         return "\\u0" + Integer.toString(paramInt, 16);
/* 33:   */       }
/* 34:52 */       return "\\u" + Integer.toString(paramInt, 16);
/* 35:   */     }
/* 36:57 */     return String.valueOf((char)paramInt);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String escapeString(String paramString)
/* 40:   */   {
/* 41:69 */     String str = new String();
/* 42:70 */     for (int i = 0; i < paramString.length(); i++) {
/* 43:72 */       str = str + escapeChar(paramString.charAt(i), false);
/* 44:   */     }
/* 45:74 */     return str;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String literalChar(int paramInt)
/* 49:   */   {
/* 50:86 */     return "'" + escapeChar(paramInt, true) + "'";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String literalString(String paramString)
/* 54:   */   {
/* 55:98 */     return "@\"\"\"" + escapeString(paramString) + "\"\"\"";
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CSharpCharFormatter
 * JD-Core Version:    0.7.0.1
 */