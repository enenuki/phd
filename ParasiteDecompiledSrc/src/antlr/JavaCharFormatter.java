/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class JavaCharFormatter
/*  4:   */   implements CharFormatter
/*  5:   */ {
/*  6:   */   public String escapeChar(int paramInt, boolean paramBoolean)
/*  7:   */   {
/*  8:22 */     switch (paramInt)
/*  9:   */     {
/* 10:   */     case 10: 
/* 11:25 */       return "\\n";
/* 12:   */     case 9: 
/* 13:27 */       return "\\t";
/* 14:   */     case 13: 
/* 15:29 */       return "\\r";
/* 16:   */     case 92: 
/* 17:31 */       return "\\\\";
/* 18:   */     case 39: 
/* 19:33 */       return paramBoolean ? "\\'" : "'";
/* 20:   */     case 34: 
/* 21:35 */       return paramBoolean ? "\"" : "\\\"";
/* 22:   */     }
/* 23:37 */     if ((paramInt < 32) || (paramInt > 126))
/* 24:   */     {
/* 25:38 */       if ((0 <= paramInt) && (paramInt <= 15)) {
/* 26:39 */         return "\\u000" + Integer.toString(paramInt, 16);
/* 27:   */       }
/* 28:41 */       if ((16 <= paramInt) && (paramInt <= 255)) {
/* 29:42 */         return "\\u00" + Integer.toString(paramInt, 16);
/* 30:   */       }
/* 31:44 */       if ((256 <= paramInt) && (paramInt <= 4095)) {
/* 32:45 */         return "\\u0" + Integer.toString(paramInt, 16);
/* 33:   */       }
/* 34:48 */       return "\\u" + Integer.toString(paramInt, 16);
/* 35:   */     }
/* 36:52 */     return String.valueOf((char)paramInt);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String escapeString(String paramString)
/* 40:   */   {
/* 41:62 */     String str = new String();
/* 42:63 */     for (int i = 0; i < paramString.length(); i++) {
/* 43:64 */       str = str + escapeChar(paramString.charAt(i), false);
/* 44:   */     }
/* 45:66 */     return str;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String literalChar(int paramInt)
/* 49:   */   {
/* 50:76 */     return "'" + escapeChar(paramInt, true) + "'";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String literalString(String paramString)
/* 54:   */   {
/* 55:85 */     return "\"" + escapeString(paramString) + "\"";
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.JavaCharFormatter
 * JD-Core Version:    0.7.0.1
 */