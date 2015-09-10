/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ public class TokenMgrError
/*   4:    */   extends Error
/*   5:    */ {
/*   6:    */   static final int LEXICAL_ERROR = 0;
/*   7:    */   static final int STATIC_LEXER_ERROR = 1;
/*   8:    */   static final int INVALID_LEXICAL_STATE = 2;
/*   9:    */   static final int LOOP_DETECTED = 3;
/*  10:    */   int errorCode;
/*  11:    */   
/*  12:    */   protected static final String addEscapes(String str)
/*  13:    */   {
/*  14: 41 */     StringBuffer retval = new StringBuffer();
/*  15: 43 */     for (int i = 0; i < str.length(); i++) {
/*  16: 44 */       switch (str.charAt(i))
/*  17:    */       {
/*  18:    */       case '\000': 
/*  19:    */         break;
/*  20:    */       case '\b': 
/*  21: 49 */         retval.append("\\b");
/*  22: 50 */         break;
/*  23:    */       case '\t': 
/*  24: 52 */         retval.append("\\t");
/*  25: 53 */         break;
/*  26:    */       case '\n': 
/*  27: 55 */         retval.append("\\n");
/*  28: 56 */         break;
/*  29:    */       case '\f': 
/*  30: 58 */         retval.append("\\f");
/*  31: 59 */         break;
/*  32:    */       case '\r': 
/*  33: 61 */         retval.append("\\r");
/*  34: 62 */         break;
/*  35:    */       case '"': 
/*  36: 64 */         retval.append("\\\"");
/*  37: 65 */         break;
/*  38:    */       case '\'': 
/*  39: 67 */         retval.append("\\'");
/*  40: 68 */         break;
/*  41:    */       case '\\': 
/*  42: 70 */         retval.append("\\\\");
/*  43: 71 */         break;
/*  44:    */       default: 
/*  45:    */         char ch;
/*  46: 73 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~'))
/*  47:    */         {
/*  48: 74 */           String s = "0000" + Integer.toString(ch, 16);
/*  49: 75 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/*  50:    */         }
/*  51:    */         else
/*  52:    */         {
/*  53: 77 */           retval.append(ch);
/*  54:    */         }
/*  55:    */         break;
/*  56:    */       }
/*  57:    */     }
/*  58: 82 */     return retval.toString();
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar)
/*  62:    */   {
/*  63: 98 */     return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF> " : new StringBuilder().append("\"").append(addEscapes(String.valueOf(curChar))).append("\"").append(" (").append(curChar).append("), ").toString()) + "after : \"" + addEscapes(errorAfter) + "\"";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getMessage()
/*  67:    */   {
/*  68:115 */     return super.getMessage();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public TokenMgrError() {}
/*  72:    */   
/*  73:    */   public TokenMgrError(String message, int reason)
/*  74:    */   {
/*  75:126 */     super(message);
/*  76:127 */     this.errorCode = reason;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason)
/*  80:    */   {
/*  81:131 */     this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.TokenMgrError
 * JD-Core Version:    0.7.0.1
 */