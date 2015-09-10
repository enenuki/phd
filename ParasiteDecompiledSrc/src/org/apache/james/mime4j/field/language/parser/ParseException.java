/*   1:    */ package org.apache.james.mime4j.field.language.parser;
/*   2:    */ 
/*   3:    */ public class ParseException
/*   4:    */   extends org.apache.james.mime4j.field.ParseException
/*   5:    */ {
/*   6:    */   private static final long serialVersionUID = 1L;
/*   7:    */   protected boolean specialConstructor;
/*   8:    */   public Token currentToken;
/*   9:    */   public int[][] expectedTokenSequences;
/*  10:    */   public String[] tokenImage;
/*  11:    */   
/*  12:    */   public ParseException(Token currentTokenVal, int[][] expectedTokenSequencesVal, String[] tokenImageVal)
/*  13:    */   {
/*  14: 55 */     super("");
/*  15: 56 */     this.specialConstructor = true;
/*  16: 57 */     this.currentToken = currentTokenVal;
/*  17: 58 */     this.expectedTokenSequences = expectedTokenSequencesVal;
/*  18: 59 */     this.tokenImage = tokenImageVal;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ParseException()
/*  22:    */   {
/*  23: 73 */     super("Cannot parse field");
/*  24: 74 */     this.specialConstructor = false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ParseException(Throwable cause)
/*  28:    */   {
/*  29: 78 */     super(cause);
/*  30: 79 */     this.specialConstructor = false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ParseException(String message)
/*  34:    */   {
/*  35: 83 */     super(message);
/*  36: 84 */     this.specialConstructor = false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getMessage()
/*  40:    */   {
/*  41:126 */     if (!this.specialConstructor) {
/*  42:127 */       return super.getMessage();
/*  43:    */     }
/*  44:129 */     StringBuffer expected = new StringBuffer();
/*  45:130 */     int maxSize = 0;
/*  46:131 */     for (int i = 0; i < this.expectedTokenSequences.length; i++)
/*  47:    */     {
/*  48:132 */       if (maxSize < this.expectedTokenSequences[i].length) {
/*  49:133 */         maxSize = this.expectedTokenSequences[i].length;
/*  50:    */       }
/*  51:135 */       for (int j = 0; j < this.expectedTokenSequences[i].length; j++) {
/*  52:136 */         expected.append(this.tokenImage[this.expectedTokenSequences[i][j]]).append(" ");
/*  53:    */       }
/*  54:138 */       if (this.expectedTokenSequences[i][(this.expectedTokenSequences[i].length - 1)] != 0) {
/*  55:139 */         expected.append("...");
/*  56:    */       }
/*  57:141 */       expected.append(this.eol).append("    ");
/*  58:    */     }
/*  59:143 */     String retval = "Encountered \"";
/*  60:144 */     Token tok = this.currentToken.next;
/*  61:145 */     for (int i = 0; i < maxSize; i++)
/*  62:    */     {
/*  63:146 */       if (i != 0) {
/*  64:146 */         retval = retval + " ";
/*  65:    */       }
/*  66:147 */       if (tok.kind == 0)
/*  67:    */       {
/*  68:148 */         retval = retval + this.tokenImage[0];
/*  69:149 */         break;
/*  70:    */       }
/*  71:151 */       retval = retval + add_escapes(tok.image);
/*  72:152 */       tok = tok.next;
/*  73:    */     }
/*  74:154 */     retval = retval + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn;
/*  75:155 */     retval = retval + "." + this.eol;
/*  76:156 */     if (this.expectedTokenSequences.length == 1) {
/*  77:157 */       retval = retval + "Was expecting:" + this.eol + "    ";
/*  78:    */     } else {
/*  79:159 */       retval = retval + "Was expecting one of:" + this.eol + "    ";
/*  80:    */     }
/*  81:161 */     retval = retval + expected.toString();
/*  82:162 */     return retval;
/*  83:    */   }
/*  84:    */   
/*  85:168 */   protected String eol = System.getProperty("line.separator", "\n");
/*  86:    */   
/*  87:    */   protected String add_escapes(String str)
/*  88:    */   {
/*  89:176 */     StringBuffer retval = new StringBuffer();
/*  90:178 */     for (int i = 0; i < str.length(); i++) {
/*  91:179 */       switch (str.charAt(i))
/*  92:    */       {
/*  93:    */       case '\000': 
/*  94:    */         break;
/*  95:    */       case '\b': 
/*  96:184 */         retval.append("\\b");
/*  97:185 */         break;
/*  98:    */       case '\t': 
/*  99:187 */         retval.append("\\t");
/* 100:188 */         break;
/* 101:    */       case '\n': 
/* 102:190 */         retval.append("\\n");
/* 103:191 */         break;
/* 104:    */       case '\f': 
/* 105:193 */         retval.append("\\f");
/* 106:194 */         break;
/* 107:    */       case '\r': 
/* 108:196 */         retval.append("\\r");
/* 109:197 */         break;
/* 110:    */       case '"': 
/* 111:199 */         retval.append("\\\"");
/* 112:200 */         break;
/* 113:    */       case '\'': 
/* 114:202 */         retval.append("\\'");
/* 115:203 */         break;
/* 116:    */       case '\\': 
/* 117:205 */         retval.append("\\\\");
/* 118:206 */         break;
/* 119:    */       default: 
/* 120:    */         char ch;
/* 121:208 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~'))
/* 122:    */         {
/* 123:209 */           String s = "0000" + Integer.toString(ch, 16);
/* 124:210 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/* 125:    */         }
/* 126:    */         else
/* 127:    */         {
/* 128:212 */           retval.append(ch);
/* 129:    */         }
/* 130:    */         break;
/* 131:    */       }
/* 132:    */     }
/* 133:217 */     return retval.toString();
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.language.parser.ParseException
 * JD-Core Version:    0.7.0.1
 */