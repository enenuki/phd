/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.Vector;
/*   4:    */ 
/*   5:    */ class AlternativeBlock
/*   6:    */   extends AlternativeElement
/*   7:    */ {
/*   8: 14 */   protected String initAction = null;
/*   9:    */   protected Vector alternatives;
/*  10:    */   protected String label;
/*  11:    */   protected int alti;
/*  12:    */   protected int altj;
/*  13:    */   protected int analysisAlt;
/*  14: 23 */   protected boolean hasAnAction = false;
/*  15: 24 */   protected boolean hasASynPred = false;
/*  16: 26 */   protected int ID = 0;
/*  17:    */   protected static int nblks;
/*  18: 28 */   boolean not = false;
/*  19: 30 */   boolean greedy = true;
/*  20: 31 */   boolean greedySet = false;
/*  21: 33 */   protected boolean doAutoGen = true;
/*  22: 35 */   protected boolean warnWhenFollowAmbig = true;
/*  23: 37 */   protected boolean generateAmbigWarnings = true;
/*  24:    */   
/*  25:    */   public AlternativeBlock(Grammar paramGrammar)
/*  26:    */   {
/*  27: 43 */     super(paramGrammar);
/*  28: 44 */     this.alternatives = new Vector(5);
/*  29: 45 */     this.not = false;
/*  30: 46 */     nblks += 1;
/*  31: 47 */     this.ID = nblks;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AlternativeBlock(Grammar paramGrammar, Token paramToken, boolean paramBoolean)
/*  35:    */   {
/*  36: 51 */     super(paramGrammar, paramToken);
/*  37: 52 */     this.alternatives = new Vector(5);
/*  38:    */     
/*  39:    */ 
/*  40: 55 */     this.not = paramBoolean;
/*  41: 56 */     nblks += 1;
/*  42: 57 */     this.ID = nblks;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addAlternative(Alternative paramAlternative)
/*  46:    */   {
/*  47: 61 */     this.alternatives.appendElement(paramAlternative);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void generate()
/*  51:    */   {
/*  52: 65 */     this.grammar.generator.gen(this);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Alternative getAlternativeAt(int paramInt)
/*  56:    */   {
/*  57: 69 */     return (Alternative)this.alternatives.elementAt(paramInt);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Vector getAlternatives()
/*  61:    */   {
/*  62: 73 */     return this.alternatives;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean getAutoGen()
/*  66:    */   {
/*  67: 77 */     return this.doAutoGen;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getInitAction()
/*  71:    */   {
/*  72: 81 */     return this.initAction;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getLabel()
/*  76:    */   {
/*  77: 85 */     return this.label;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Lookahead look(int paramInt)
/*  81:    */   {
/*  82: 89 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void prepareForAnalysis()
/*  86:    */   {
/*  87: 93 */     for (int i = 0; i < this.alternatives.size(); i++)
/*  88:    */     {
/*  89: 95 */       Alternative localAlternative = (Alternative)this.alternatives.elementAt(i);
/*  90: 96 */       localAlternative.cache = new Lookahead[this.grammar.maxk + 1];
/*  91: 97 */       localAlternative.lookaheadDepth = -1;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void removeTrackingOfRuleRefs(Grammar paramGrammar)
/*  96:    */   {
/*  97:106 */     for (int i = 0; i < this.alternatives.size(); i++)
/*  98:    */     {
/*  99:107 */       Alternative localAlternative = getAlternativeAt(i);
/* 100:108 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 101:109 */       while (localAlternativeElement != null)
/* 102:    */       {
/* 103:110 */         if ((localAlternativeElement instanceof RuleRefElement))
/* 104:    */         {
/* 105:111 */           RuleRefElement localRuleRefElement = (RuleRefElement)localAlternativeElement;
/* 106:112 */           RuleSymbol localRuleSymbol = (RuleSymbol)paramGrammar.getSymbol(localRuleRefElement.targetRule);
/* 107:113 */           if (localRuleSymbol == null) {
/* 108:114 */             this.grammar.antlrTool.error("rule " + localRuleRefElement.targetRule + " referenced in (...)=>, but not defined");
/* 109:    */           } else {
/* 110:117 */             localRuleSymbol.references.removeElement(localRuleRefElement);
/* 111:    */           }
/* 112:    */         }
/* 113:120 */         else if ((localAlternativeElement instanceof AlternativeBlock))
/* 114:    */         {
/* 115:121 */           ((AlternativeBlock)localAlternativeElement).removeTrackingOfRuleRefs(paramGrammar);
/* 116:    */         }
/* 117:123 */         localAlternativeElement = localAlternativeElement.next;
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setAlternatives(Vector paramVector)
/* 123:    */   {
/* 124:129 */     this.alternatives = paramVector;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setAutoGen(boolean paramBoolean)
/* 128:    */   {
/* 129:133 */     this.doAutoGen = paramBoolean;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setInitAction(String paramString)
/* 133:    */   {
/* 134:137 */     this.initAction = paramString;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setLabel(String paramString)
/* 138:    */   {
/* 139:141 */     this.label = paramString;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setOption(Token paramToken1, Token paramToken2)
/* 143:    */   {
/* 144:145 */     if (paramToken1.getText().equals("warnWhenFollowAmbig"))
/* 145:    */     {
/* 146:146 */       if (paramToken2.getText().equals("true")) {
/* 147:147 */         this.warnWhenFollowAmbig = true;
/* 148:149 */       } else if (paramToken2.getText().equals("false")) {
/* 149:150 */         this.warnWhenFollowAmbig = false;
/* 150:    */       } else {
/* 151:153 */         this.grammar.antlrTool.error("Value for warnWhenFollowAmbig must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 152:    */       }
/* 153:    */     }
/* 154:156 */     else if (paramToken1.getText().equals("generateAmbigWarnings"))
/* 155:    */     {
/* 156:157 */       if (paramToken2.getText().equals("true")) {
/* 157:158 */         this.generateAmbigWarnings = true;
/* 158:160 */       } else if (paramToken2.getText().equals("false")) {
/* 159:161 */         this.generateAmbigWarnings = false;
/* 160:    */       } else {
/* 161:164 */         this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 162:    */       }
/* 163:    */     }
/* 164:167 */     else if (paramToken1.getText().equals("greedy"))
/* 165:    */     {
/* 166:168 */       if (paramToken2.getText().equals("true"))
/* 167:    */       {
/* 168:169 */         this.greedy = true;
/* 169:170 */         this.greedySet = true;
/* 170:    */       }
/* 171:172 */       else if (paramToken2.getText().equals("false"))
/* 172:    */       {
/* 173:173 */         this.greedy = false;
/* 174:174 */         this.greedySet = true;
/* 175:    */       }
/* 176:    */       else
/* 177:    */       {
/* 178:177 */         this.grammar.antlrTool.error("Value for greedy must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 179:    */       }
/* 180:    */     }
/* 181:    */     else {
/* 182:181 */       this.grammar.antlrTool.error("Invalid subrule option: " + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String toString()
/* 187:    */   {
/* 188:186 */     String str1 = " (";
/* 189:187 */     if (this.initAction != null) {
/* 190:188 */       str1 = str1 + this.initAction;
/* 191:    */     }
/* 192:190 */     for (int i = 0; i < this.alternatives.size(); i++)
/* 193:    */     {
/* 194:191 */       Alternative localAlternative = getAlternativeAt(i);
/* 195:192 */       Lookahead[] arrayOfLookahead = localAlternative.cache;
/* 196:193 */       int j = localAlternative.lookaheadDepth;
/* 197:195 */       if (j != -1) {
/* 198:197 */         if (j == 2147483647)
/* 199:    */         {
/* 200:198 */           str1 = str1 + "{?}:";
/* 201:    */         }
/* 202:    */         else
/* 203:    */         {
/* 204:201 */           str1 = str1 + " {";
/* 205:202 */           for (int k = 1; k <= j; k++)
/* 206:    */           {
/* 207:203 */             str1 = str1 + arrayOfLookahead[k].toString(",", this.grammar.tokenManager.getVocabulary());
/* 208:204 */             if ((k < j) && (arrayOfLookahead[(k + 1)] != null)) {
/* 209:204 */               str1 = str1 + ";";
/* 210:    */             }
/* 211:    */           }
/* 212:206 */           str1 = str1 + "}:";
/* 213:    */         }
/* 214:    */       }
/* 215:209 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 216:210 */       String str2 = localAlternative.semPred;
/* 217:211 */       if (str2 != null) {
/* 218:212 */         str1 = str1 + str2;
/* 219:    */       }
/* 220:214 */       while (localAlternativeElement != null)
/* 221:    */       {
/* 222:215 */         str1 = str1 + localAlternativeElement;
/* 223:216 */         localAlternativeElement = localAlternativeElement.next;
/* 224:    */       }
/* 225:218 */       if (i < this.alternatives.size() - 1) {
/* 226:219 */         str1 = str1 + " |";
/* 227:    */       }
/* 228:    */     }
/* 229:222 */     str1 = str1 + " )";
/* 230:223 */     return str1;
/* 231:    */   }
/* 232:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.AlternativeBlock
 * JD-Core Version:    0.7.0.1
 */