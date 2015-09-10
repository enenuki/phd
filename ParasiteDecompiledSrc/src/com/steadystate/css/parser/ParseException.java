/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ public class ParseException
/*   4:    */   extends Exception
/*   5:    */ {
/*   6:    */   protected boolean specialConstructor;
/*   7:    */   public Token currentToken;
/*   8:    */   public int[][] expectedTokenSequences;
/*   9:    */   public String[] tokenImage;
/*  10:    */   
/*  11:    */   public ParseException(Token currentTokenVal, int[][] expectedTokenSequencesVal, String[] tokenImageVal)
/*  12:    */   {
/*  13: 32 */     super("");
/*  14: 33 */     this.specialConstructor = true;
/*  15: 34 */     this.currentToken = currentTokenVal;
/*  16: 35 */     this.expectedTokenSequences = expectedTokenSequencesVal;
/*  17: 36 */     this.tokenImage = tokenImageVal;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ParseException()
/*  21:    */   {
/*  22: 51 */     this.specialConstructor = false;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ParseException(String message)
/*  26:    */   {
/*  27: 55 */     super(message);
/*  28: 56 */     this.specialConstructor = false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getMessage()
/*  32:    */   {
/*  33: 98 */     if (!this.specialConstructor) {
/*  34: 99 */       return super.getMessage();
/*  35:    */     }
/*  36:101 */     StringBuffer expected = new StringBuffer();
/*  37:102 */     int maxSize = 0;
/*  38:103 */     for (int i = 0; i < this.expectedTokenSequences.length; i++)
/*  39:    */     {
/*  40:104 */       if (maxSize < this.expectedTokenSequences[i].length) {
/*  41:105 */         maxSize = this.expectedTokenSequences[i].length;
/*  42:    */       }
/*  43:107 */       for (int j = 0; j < this.expectedTokenSequences[i].length; j++) {
/*  44:108 */         expected.append(this.tokenImage[this.expectedTokenSequences[i][j]]).append(" ");
/*  45:    */       }
/*  46:110 */       if (this.expectedTokenSequences[i][(this.expectedTokenSequences[i].length - 1)] != 0) {
/*  47:111 */         expected.append("...");
/*  48:    */       }
/*  49:113 */       expected.append(this.eol).append("    ");
/*  50:    */     }
/*  51:115 */     String retval = "Encountered \"";
/*  52:116 */     Token tok = this.currentToken.next;
/*  53:117 */     for (int i = 0; i < maxSize; i++)
/*  54:    */     {
/*  55:118 */       if (i != 0) {
/*  56:118 */         retval = retval + " ";
/*  57:    */       }
/*  58:119 */       if (tok.kind == 0)
/*  59:    */       {
/*  60:120 */         retval = retval + this.tokenImage[0];
/*  61:121 */         break;
/*  62:    */       }
/*  63:123 */       retval = retval + add_escapes(tok.image);
/*  64:124 */       tok = tok.next;
/*  65:    */     }
/*  66:126 */     retval = retval + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn;
/*  67:127 */     retval = retval + "." + this.eol;
/*  68:128 */     if (this.expectedTokenSequences.length == 1) {
/*  69:129 */       retval = retval + "Was expecting:" + this.eol + "    ";
/*  70:    */     } else {
/*  71:131 */       retval = retval + "Was expecting one of:" + this.eol + "    ";
/*  72:    */     }
/*  73:133 */     retval = retval + expected.toString();
/*  74:134 */     return retval;
/*  75:    */   }
/*  76:    */   
/*  77:140 */   protected String eol = System.getProperty("line.separator", "\n");
/*  78:    */   
/*  79:    */   protected String add_escapes(String str)
/*  80:    */   {
/*  81:148 */     StringBuffer retval = new StringBuffer();
/*  82:150 */     for (int i = 0; i < str.length(); i++) {
/*  83:151 */       switch (str.charAt(i))
/*  84:    */       {
/*  85:    */       case '\000': 
/*  86:    */         break;
/*  87:    */       case '\b': 
/*  88:156 */         retval.append("\\b");
/*  89:157 */         break;
/*  90:    */       case '\t': 
/*  91:159 */         retval.append("\\t");
/*  92:160 */         break;
/*  93:    */       case '\n': 
/*  94:162 */         retval.append("\\n");
/*  95:163 */         break;
/*  96:    */       case '\f': 
/*  97:165 */         retval.append("\\f");
/*  98:166 */         break;
/*  99:    */       case '\r': 
/* 100:168 */         retval.append("\\r");
/* 101:169 */         break;
/* 102:    */       case '"': 
/* 103:171 */         retval.append("\\\"");
/* 104:172 */         break;
/* 105:    */       case '\'': 
/* 106:174 */         retval.append("\\'");
/* 107:175 */         break;
/* 108:    */       case '\\': 
/* 109:177 */         retval.append("\\\\");
/* 110:178 */         break;
/* 111:    */       default: 
/* 112:    */         char ch;
/* 113:180 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~'))
/* 114:    */         {
/* 115:181 */           String s = "0000" + Integer.toString(ch, 16);
/* 116:182 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/* 117:    */         }
/* 118:    */         else
/* 119:    */         {
/* 120:184 */           retval.append(ch);
/* 121:    */         }
/* 122:    */         break;
/* 123:    */       }
/* 124:    */     }
/* 125:189 */     return retval.toString();
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.ParseException
 * JD-Core Version:    0.7.0.1
 */