/*   1:    */ package org.apache.james.mime4j.field.datetime.parser;
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
/*  14: 59 */     StringBuffer retval = new StringBuffer();
/*  15: 61 */     for (int i = 0; i < str.length(); i++) {
/*  16: 62 */       switch (str.charAt(i))
/*  17:    */       {
/*  18:    */       case '\000': 
/*  19:    */         break;
/*  20:    */       case '\b': 
/*  21: 67 */         retval.append("\\b");
/*  22: 68 */         break;
/*  23:    */       case '\t': 
/*  24: 70 */         retval.append("\\t");
/*  25: 71 */         break;
/*  26:    */       case '\n': 
/*  27: 73 */         retval.append("\\n");
/*  28: 74 */         break;
/*  29:    */       case '\f': 
/*  30: 76 */         retval.append("\\f");
/*  31: 77 */         break;
/*  32:    */       case '\r': 
/*  33: 79 */         retval.append("\\r");
/*  34: 80 */         break;
/*  35:    */       case '"': 
/*  36: 82 */         retval.append("\\\"");
/*  37: 83 */         break;
/*  38:    */       case '\'': 
/*  39: 85 */         retval.append("\\'");
/*  40: 86 */         break;
/*  41:    */       case '\\': 
/*  42: 88 */         retval.append("\\\\");
/*  43: 89 */         break;
/*  44:    */       default: 
/*  45:    */         char ch;
/*  46: 91 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~'))
/*  47:    */         {
/*  48: 92 */           String s = "0000" + Integer.toString(ch, 16);
/*  49: 93 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/*  50:    */         }
/*  51:    */         else
/*  52:    */         {
/*  53: 95 */           retval.append(ch);
/*  54:    */         }
/*  55:    */         break;
/*  56:    */       }
/*  57:    */     }
/*  58:100 */     return retval.toString();
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar)
/*  62:    */   {
/*  63:116 */     return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF> " : new StringBuilder().append("\"").append(addEscapes(String.valueOf(curChar))).append("\"").append(" (").append(curChar).append("), ").toString()) + "after : \"" + addEscapes(errorAfter) + "\"";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getMessage()
/*  67:    */   {
/*  68:133 */     return super.getMessage();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public TokenMgrError() {}
/*  72:    */   
/*  73:    */   public TokenMgrError(String message, int reason)
/*  74:    */   {
/*  75:144 */     super(message);
/*  76:145 */     this.errorCode = reason;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason)
/*  80:    */   {
/*  81:149 */     this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.datetime.parser.TokenMgrError
 * JD-Core Version:    0.7.0.1
 */