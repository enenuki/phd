/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class PythonCharFormatter
/*  4:   */   implements CharFormatter
/*  5:   */ {
/*  6:   */   public String escapeChar(int paramInt, boolean paramBoolean)
/*  7:   */   {
/*  8:12 */     String str = _escapeChar(paramInt, paramBoolean);
/*  9:   */     
/* 10:14 */     return str;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String _escapeChar(int paramInt, boolean paramBoolean)
/* 14:   */   {
/* 15:19 */     switch (paramInt)
/* 16:   */     {
/* 17:   */     case 10: 
/* 18:22 */       return "\\n";
/* 19:   */     case 9: 
/* 20:24 */       return "\\t";
/* 21:   */     case 13: 
/* 22:26 */       return "\\r";
/* 23:   */     case 92: 
/* 24:28 */       return "\\\\";
/* 25:   */     case 39: 
/* 26:30 */       return paramBoolean ? "\\'" : "'";
/* 27:   */     case 34: 
/* 28:32 */       return paramBoolean ? "\"" : "\\\"";
/* 29:   */     }
/* 30:34 */     if ((paramInt < 32) || (paramInt > 126))
/* 31:   */     {
/* 32:35 */       if ((0 <= paramInt) && (paramInt <= 15)) {
/* 33:36 */         return "\\u000" + Integer.toString(paramInt, 16);
/* 34:   */       }
/* 35:38 */       if ((16 <= paramInt) && (paramInt <= 255)) {
/* 36:39 */         return "\\u00" + Integer.toString(paramInt, 16);
/* 37:   */       }
/* 38:41 */       if ((256 <= paramInt) && (paramInt <= 4095)) {
/* 39:42 */         return "\\u0" + Integer.toString(paramInt, 16);
/* 40:   */       }
/* 41:45 */       return "\\u" + Integer.toString(paramInt, 16);
/* 42:   */     }
/* 43:49 */     return String.valueOf((char)paramInt);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String escapeString(String paramString)
/* 47:   */   {
/* 48:55 */     String str = new String();
/* 49:56 */     for (int i = 0; i < paramString.length(); i++) {
/* 50:57 */       str = str + escapeChar(paramString.charAt(i), false);
/* 51:   */     }
/* 52:59 */     return str;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String literalChar(int paramInt)
/* 56:   */   {
/* 57:63 */     return "" + escapeChar(paramInt, true) + "";
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String literalString(String paramString)
/* 61:   */   {
/* 62:67 */     return "\"" + escapeString(paramString) + "\"";
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.PythonCharFormatter
 * JD-Core Version:    0.7.0.1
 */