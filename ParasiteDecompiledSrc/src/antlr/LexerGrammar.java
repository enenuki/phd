/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ class LexerGrammar
/*   7:    */   extends Grammar
/*   8:    */ {
/*   9:    */   protected BitSet charVocabulary;
/*  10: 22 */   protected boolean testLiterals = true;
/*  11: 24 */   protected boolean caseSensitiveLiterals = true;
/*  12: 26 */   protected boolean caseSensitive = true;
/*  13: 28 */   protected boolean filterMode = false;
/*  14: 34 */   protected String filterRule = null;
/*  15:    */   
/*  16:    */   LexerGrammar(String paramString1, Tool paramTool, String paramString2)
/*  17:    */   {
/*  18: 37 */     super(paramString1, paramTool, paramString2);
/*  19:    */     
/*  20: 39 */     BitSet localBitSet = new BitSet();
/*  21: 40 */     for (int i = 0; i <= 127; i++) {
/*  22: 41 */       localBitSet.add(i);
/*  23:    */     }
/*  24: 43 */     setCharVocabulary(localBitSet);
/*  25:    */     
/*  26:    */ 
/*  27: 46 */     this.defaultErrorHandler = false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void generate()
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 51 */     this.generator.gen(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getSuperClass()
/*  37:    */   {
/*  38: 56 */     if (this.debuggingOutput) {
/*  39: 57 */       return "debug.DebuggingCharScanner";
/*  40:    */     }
/*  41: 58 */     return "CharScanner";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean getTestLiterals()
/*  45:    */   {
/*  46: 63 */     return this.testLiterals;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void processArguments(String[] paramArrayOfString)
/*  50:    */   {
/*  51: 72 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  52: 73 */       if (paramArrayOfString[i].equals("-trace"))
/*  53:    */       {
/*  54: 74 */         this.traceRules = true;
/*  55: 75 */         this.antlrTool.setArgOK(i);
/*  56:    */       }
/*  57: 77 */       else if (paramArrayOfString[i].equals("-traceLexer"))
/*  58:    */       {
/*  59: 78 */         this.traceRules = true;
/*  60: 79 */         this.antlrTool.setArgOK(i);
/*  61:    */       }
/*  62: 81 */       else if (paramArrayOfString[i].equals("-debug"))
/*  63:    */       {
/*  64: 82 */         this.debuggingOutput = true;
/*  65: 83 */         this.antlrTool.setArgOK(i);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setCharVocabulary(BitSet paramBitSet)
/*  71:    */   {
/*  72: 90 */     this.charVocabulary = paramBitSet;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean setOption(String paramString, Token paramToken)
/*  76:    */   {
/*  77: 95 */     String str = paramToken.getText();
/*  78: 96 */     if (paramString.equals("buildAST"))
/*  79:    */     {
/*  80: 97 */       this.antlrTool.warning("buildAST option is not valid for lexer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  81: 98 */       return true;
/*  82:    */     }
/*  83:100 */     if (paramString.equals("testLiterals"))
/*  84:    */     {
/*  85:101 */       if (str.equals("true")) {
/*  86:102 */         this.testLiterals = true;
/*  87:104 */       } else if (str.equals("false")) {
/*  88:105 */         this.testLiterals = false;
/*  89:    */       } else {
/*  90:108 */         this.antlrTool.warning("testLiterals option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  91:    */       }
/*  92:110 */       return true;
/*  93:    */     }
/*  94:112 */     if (paramString.equals("interactive"))
/*  95:    */     {
/*  96:113 */       if (str.equals("true")) {
/*  97:114 */         this.interactive = true;
/*  98:116 */       } else if (str.equals("false")) {
/*  99:117 */         this.interactive = false;
/* 100:    */       } else {
/* 101:120 */         this.antlrTool.error("interactive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 102:    */       }
/* 103:122 */       return true;
/* 104:    */     }
/* 105:124 */     if (paramString.equals("caseSensitive"))
/* 106:    */     {
/* 107:125 */       if (str.equals("true")) {
/* 108:126 */         this.caseSensitive = true;
/* 109:128 */       } else if (str.equals("false")) {
/* 110:129 */         this.caseSensitive = false;
/* 111:    */       } else {
/* 112:132 */         this.antlrTool.warning("caseSensitive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 113:    */       }
/* 114:134 */       return true;
/* 115:    */     }
/* 116:136 */     if (paramString.equals("caseSensitiveLiterals"))
/* 117:    */     {
/* 118:137 */       if (str.equals("true")) {
/* 119:138 */         this.caseSensitiveLiterals = true;
/* 120:140 */       } else if (str.equals("false")) {
/* 121:141 */         this.caseSensitiveLiterals = false;
/* 122:    */       } else {
/* 123:144 */         this.antlrTool.warning("caseSensitiveLiterals option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 124:    */       }
/* 125:146 */       return true;
/* 126:    */     }
/* 127:148 */     if (paramString.equals("filter"))
/* 128:    */     {
/* 129:149 */       if (str.equals("true"))
/* 130:    */       {
/* 131:150 */         this.filterMode = true;
/* 132:    */       }
/* 133:152 */       else if (str.equals("false"))
/* 134:    */       {
/* 135:153 */         this.filterMode = false;
/* 136:    */       }
/* 137:155 */       else if (paramToken.getType() == 24)
/* 138:    */       {
/* 139:156 */         this.filterMode = true;
/* 140:157 */         this.filterRule = str;
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:160 */         this.antlrTool.warning("filter option must be true, false, or a lexer rule name", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 145:    */       }
/* 146:162 */       return true;
/* 147:    */     }
/* 148:164 */     if (paramString.equals("longestPossible"))
/* 149:    */     {
/* 150:165 */       this.antlrTool.warning("longestPossible option has been deprecated; ignoring it...", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 151:166 */       return true;
/* 152:    */     }
/* 153:168 */     if (paramString.equals("className"))
/* 154:    */     {
/* 155:169 */       super.setOption(paramString, paramToken);
/* 156:170 */       return true;
/* 157:    */     }
/* 158:172 */     if (super.setOption(paramString, paramToken)) {
/* 159:173 */       return true;
/* 160:    */     }
/* 161:175 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 162:176 */     return false;
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.LexerGrammar
 * JD-Core Version:    0.7.0.1
 */