/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.Vector;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class RuleBlock
/*   7:    */   extends AlternativeBlock
/*   8:    */ {
/*   9:    */   protected String ruleName;
/*  10: 19 */   protected String argAction = null;
/*  11: 20 */   protected String throwsSpec = null;
/*  12: 21 */   protected String returnAction = null;
/*  13:    */   protected RuleEndElement endNode;
/*  14: 25 */   protected boolean testLiterals = false;
/*  15:    */   Vector labeledElements;
/*  16:    */   protected boolean[] lock;
/*  17:    */   protected Lookahead[] cache;
/*  18:    */   Hashtable exceptionSpecs;
/*  19: 43 */   protected boolean defaultErrorHandler = true;
/*  20: 44 */   protected String ignoreRule = null;
/*  21:    */   
/*  22:    */   public RuleBlock(Grammar paramGrammar, String paramString)
/*  23:    */   {
/*  24: 48 */     super(paramGrammar);
/*  25: 49 */     this.ruleName = paramString;
/*  26: 50 */     this.labeledElements = new Vector();
/*  27: 51 */     this.cache = new Lookahead[paramGrammar.maxk + 1];
/*  28: 52 */     this.exceptionSpecs = new Hashtable();
/*  29: 53 */     setAutoGen(paramGrammar instanceof ParserGrammar);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public RuleBlock(Grammar paramGrammar, String paramString, int paramInt, boolean paramBoolean)
/*  33:    */   {
/*  34: 58 */     this(paramGrammar, paramString);
/*  35: 59 */     this.line = paramInt;
/*  36: 60 */     setAutoGen(paramBoolean);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addExceptionSpec(ExceptionSpec paramExceptionSpec)
/*  40:    */   {
/*  41: 64 */     if (findExceptionSpec(paramExceptionSpec.label) != null)
/*  42:    */     {
/*  43: 65 */       if (paramExceptionSpec.label != null) {
/*  44: 66 */         this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler for label: " + paramExceptionSpec.label);
/*  45:    */       } else {
/*  46: 69 */         this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler");
/*  47:    */       }
/*  48:    */     }
/*  49:    */     else {
/*  50: 73 */       this.exceptionSpecs.put(paramExceptionSpec.label == null ? "" : paramExceptionSpec.label.getText(), paramExceptionSpec);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ExceptionSpec findExceptionSpec(Token paramToken)
/*  55:    */   {
/*  56: 78 */     return (ExceptionSpec)this.exceptionSpecs.get(paramToken == null ? "" : paramToken.getText());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public ExceptionSpec findExceptionSpec(String paramString)
/*  60:    */   {
/*  61: 82 */     return (ExceptionSpec)this.exceptionSpecs.get(paramString == null ? "" : paramString);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void generate()
/*  65:    */   {
/*  66: 86 */     this.grammar.generator.gen(this);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean getDefaultErrorHandler()
/*  70:    */   {
/*  71: 90 */     return this.defaultErrorHandler;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public RuleEndElement getEndElement()
/*  75:    */   {
/*  76: 94 */     return this.endNode;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getIgnoreRule()
/*  80:    */   {
/*  81: 98 */     return this.ignoreRule;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getRuleName()
/*  85:    */   {
/*  86:102 */     return this.ruleName;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean getTestLiterals()
/*  90:    */   {
/*  91:106 */     return this.testLiterals;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isLexerAutoGenRule()
/*  95:    */   {
/*  96:110 */     return this.ruleName.equals("nextToken");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Lookahead look(int paramInt)
/* 100:    */   {
/* 101:114 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void prepareForAnalysis()
/* 105:    */   {
/* 106:118 */     super.prepareForAnalysis();
/* 107:119 */     this.lock = new boolean[this.grammar.maxk + 1];
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDefaultErrorHandler(boolean paramBoolean)
/* 111:    */   {
/* 112:124 */     this.defaultErrorHandler = paramBoolean;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setEndElement(RuleEndElement paramRuleEndElement)
/* 116:    */   {
/* 117:128 */     this.endNode = paramRuleEndElement;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setOption(Token paramToken1, Token paramToken2)
/* 121:    */   {
/* 122:132 */     if (paramToken1.getText().equals("defaultErrorHandler"))
/* 123:    */     {
/* 124:133 */       if (paramToken2.getText().equals("true")) {
/* 125:134 */         this.defaultErrorHandler = true;
/* 126:136 */       } else if (paramToken2.getText().equals("false")) {
/* 127:137 */         this.defaultErrorHandler = false;
/* 128:    */       } else {
/* 129:140 */         this.grammar.antlrTool.error("Value for defaultErrorHandler must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 130:    */       }
/* 131:    */     }
/* 132:143 */     else if (paramToken1.getText().equals("testLiterals"))
/* 133:    */     {
/* 134:144 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 135:145 */         this.grammar.antlrTool.error("testLiterals option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 136:148 */       } else if (paramToken2.getText().equals("true")) {
/* 137:149 */         this.testLiterals = true;
/* 138:151 */       } else if (paramToken2.getText().equals("false")) {
/* 139:152 */         this.testLiterals = false;
/* 140:    */       } else {
/* 141:155 */         this.grammar.antlrTool.error("Value for testLiterals must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 142:    */       }
/* 143:    */     }
/* 144:159 */     else if (paramToken1.getText().equals("ignore"))
/* 145:    */     {
/* 146:160 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 147:161 */         this.grammar.antlrTool.error("ignore option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 148:    */       } else {
/* 149:164 */         this.ignoreRule = paramToken2.getText();
/* 150:    */       }
/* 151:    */     }
/* 152:167 */     else if (paramToken1.getText().equals("paraphrase"))
/* 153:    */     {
/* 154:168 */       if (!(this.grammar instanceof LexerGrammar))
/* 155:    */       {
/* 156:169 */         this.grammar.antlrTool.error("paraphrase option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:173 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.ruleName);
/* 161:174 */         if (localTokenSymbol == null) {
/* 162:175 */           this.grammar.antlrTool.panic("cannot find token associated with rule " + this.ruleName);
/* 163:    */         }
/* 164:177 */         localTokenSymbol.setParaphrase(paramToken2.getText());
/* 165:    */       }
/* 166:    */     }
/* 167:180 */     else if (paramToken1.getText().equals("generateAmbigWarnings"))
/* 168:    */     {
/* 169:181 */       if (paramToken2.getText().equals("true")) {
/* 170:182 */         this.generateAmbigWarnings = true;
/* 171:184 */       } else if (paramToken2.getText().equals("false")) {
/* 172:185 */         this.generateAmbigWarnings = false;
/* 173:    */       } else {
/* 174:188 */         this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 175:    */       }
/* 176:    */     }
/* 177:    */     else {
/* 178:192 */       this.grammar.antlrTool.error("Invalid rule option: " + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String toString()
/* 183:    */   {
/* 184:197 */     String str = " FOLLOW={";
/* 185:198 */     Lookahead[] arrayOfLookahead = this.endNode.cache;
/* 186:199 */     int i = this.grammar.maxk;
/* 187:200 */     int j = 1;
/* 188:201 */     for (int k = 1; k <= i; k++) {
/* 189:202 */       if (arrayOfLookahead[k] != null)
/* 190:    */       {
/* 191:203 */         str = str + arrayOfLookahead[k].toString(",", this.grammar.tokenManager.getVocabulary());
/* 192:204 */         j = 0;
/* 193:205 */         if ((k < i) && (arrayOfLookahead[(k + 1)] != null)) {
/* 194:205 */           str = str + ";";
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:207 */     str = str + "}";
/* 199:208 */     if (j != 0) {
/* 200:208 */       str = "";
/* 201:    */     }
/* 202:209 */     return this.ruleName + ": " + super.toString() + " ;" + str;
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.RuleBlock
 * JD-Core Version:    0.7.0.1
 */