/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.Vector;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ 
/*   8:    */ public abstract class Grammar
/*   9:    */ {
/*  10:    */   protected Tool antlrTool;
/*  11:    */   protected CodeGenerator generator;
/*  12:    */   protected LLkGrammarAnalyzer theLLkAnalyzer;
/*  13:    */   protected Hashtable symbols;
/*  14: 26 */   protected boolean buildAST = false;
/*  15: 27 */   protected boolean analyzerDebug = false;
/*  16: 28 */   protected boolean interactive = false;
/*  17: 29 */   protected String superClass = null;
/*  18:    */   protected TokenManager tokenManager;
/*  19: 41 */   protected String exportVocab = null;
/*  20: 45 */   protected String importVocab = null;
/*  21:    */   protected Hashtable options;
/*  22:    */   protected Vector rules;
/*  23: 52 */   protected Token preambleAction = new CommonToken(0, "");
/*  24: 53 */   protected String className = null;
/*  25: 54 */   protected String fileName = null;
/*  26: 55 */   protected Token classMemberAction = new CommonToken(0, "");
/*  27: 56 */   protected boolean hasSyntacticPredicate = false;
/*  28: 57 */   protected boolean hasUserErrorHandling = false;
/*  29: 60 */   protected int maxk = 1;
/*  30: 63 */   protected boolean traceRules = false;
/*  31: 64 */   protected boolean debuggingOutput = false;
/*  32: 65 */   protected boolean defaultErrorHandler = true;
/*  33: 67 */   protected String comment = null;
/*  34:    */   
/*  35:    */   public Grammar(String paramString1, Tool paramTool, String paramString2)
/*  36:    */   {
/*  37: 70 */     this.className = paramString1;
/*  38: 71 */     this.antlrTool = paramTool;
/*  39: 72 */     this.symbols = new Hashtable();
/*  40: 73 */     this.options = new Hashtable();
/*  41: 74 */     this.rules = new Vector(100);
/*  42: 75 */     this.superClass = paramString2;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void define(RuleSymbol paramRuleSymbol)
/*  46:    */   {
/*  47: 80 */     this.rules.appendElement(paramRuleSymbol);
/*  48:    */     
/*  49: 82 */     this.symbols.put(paramRuleSymbol.getId(), paramRuleSymbol);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public abstract void generate()
/*  53:    */     throws IOException;
/*  54:    */   
/*  55:    */   protected String getClassName()
/*  56:    */   {
/*  57: 89 */     return this.className;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean getDefaultErrorHandler()
/*  61:    */   {
/*  62: 94 */     return this.defaultErrorHandler;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getFilename()
/*  66:    */   {
/*  67: 98 */     return this.fileName;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIntegerOption(String paramString)
/*  71:    */     throws NumberFormatException
/*  72:    */   {
/*  73:108 */     Token localToken = (Token)this.options.get(paramString);
/*  74:109 */     if ((localToken == null) || (localToken.getType() != 20)) {
/*  75:110 */       throw new NumberFormatException();
/*  76:    */     }
/*  77:113 */     return Integer.parseInt(localToken.getText());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Token getOption(String paramString)
/*  81:    */   {
/*  82:122 */     return (Token)this.options.get(paramString);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected abstract String getSuperClass();
/*  86:    */   
/*  87:    */   public GrammarSymbol getSymbol(String paramString)
/*  88:    */   {
/*  89:129 */     return (GrammarSymbol)this.symbols.get(paramString);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Enumeration getSymbols()
/*  93:    */   {
/*  94:133 */     return this.symbols.elements();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean hasOption(String paramString)
/*  98:    */   {
/*  99:141 */     return this.options.containsKey(paramString);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean isDefined(String paramString)
/* 103:    */   {
/* 104:146 */     return this.symbols.containsKey(paramString);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public abstract void processArguments(String[] paramArrayOfString);
/* 108:    */   
/* 109:    */   public void setCodeGenerator(CodeGenerator paramCodeGenerator)
/* 110:    */   {
/* 111:153 */     this.generator = paramCodeGenerator;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setFilename(String paramString)
/* 115:    */   {
/* 116:157 */     this.fileName = paramString;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setGrammarAnalyzer(LLkGrammarAnalyzer paramLLkGrammarAnalyzer)
/* 120:    */   {
/* 121:161 */     this.theLLkAnalyzer = paramLLkGrammarAnalyzer;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean setOption(String paramString, Token paramToken)
/* 125:    */   {
/* 126:175 */     this.options.put(paramString, paramToken);
/* 127:176 */     String str = paramToken.getText();
/* 128:178 */     if (paramString.equals("k"))
/* 129:    */     {
/* 130:    */       try
/* 131:    */       {
/* 132:180 */         this.maxk = getIntegerOption("k");
/* 133:181 */         if (this.maxk <= 0)
/* 134:    */         {
/* 135:182 */           this.antlrTool.error("option 'k' must be greater than 0 (was " + paramToken.getText() + ")", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 136:    */           
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:187 */           this.maxk = 1;
/* 141:    */         }
/* 142:    */       }
/* 143:    */       catch (NumberFormatException localNumberFormatException1)
/* 144:    */       {
/* 145:191 */         this.antlrTool.error("option 'k' must be an integer (was " + paramToken.getText() + ")", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 146:    */       }
/* 147:193 */       return true;
/* 148:    */     }
/* 149:    */     int i;
/* 150:195 */     if (paramString.equals("codeGenMakeSwitchThreshold"))
/* 151:    */     {
/* 152:    */       try
/* 153:    */       {
/* 154:197 */         i = getIntegerOption("codeGenMakeSwitchThreshold");
/* 155:    */       }
/* 156:    */       catch (NumberFormatException localNumberFormatException2)
/* 157:    */       {
/* 158:200 */         this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 159:    */       }
/* 160:202 */       return true;
/* 161:    */     }
/* 162:204 */     if (paramString.equals("codeGenBitsetTestThreshold"))
/* 163:    */     {
/* 164:    */       try
/* 165:    */       {
/* 166:206 */         i = getIntegerOption("codeGenBitsetTestThreshold");
/* 167:    */       }
/* 168:    */       catch (NumberFormatException localNumberFormatException3)
/* 169:    */       {
/* 170:209 */         this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 171:    */       }
/* 172:211 */       return true;
/* 173:    */     }
/* 174:213 */     if (paramString.equals("defaultErrorHandler"))
/* 175:    */     {
/* 176:214 */       if (str.equals("true")) {
/* 177:215 */         this.defaultErrorHandler = true;
/* 178:217 */       } else if (str.equals("false")) {
/* 179:218 */         this.defaultErrorHandler = false;
/* 180:    */       } else {
/* 181:221 */         this.antlrTool.error("Value for defaultErrorHandler must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 182:    */       }
/* 183:223 */       return true;
/* 184:    */     }
/* 185:225 */     if (paramString.equals("analyzerDebug"))
/* 186:    */     {
/* 187:226 */       if (str.equals("true")) {
/* 188:227 */         this.analyzerDebug = true;
/* 189:229 */       } else if (str.equals("false")) {
/* 190:230 */         this.analyzerDebug = false;
/* 191:    */       } else {
/* 192:233 */         this.antlrTool.error("option 'analyzerDebug' must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 193:    */       }
/* 194:235 */       return true;
/* 195:    */     }
/* 196:237 */     if (paramString.equals("codeGenDebug"))
/* 197:    */     {
/* 198:238 */       if (str.equals("true")) {
/* 199:239 */         this.analyzerDebug = true;
/* 200:241 */       } else if (str.equals("false")) {
/* 201:242 */         this.analyzerDebug = false;
/* 202:    */       } else {
/* 203:245 */         this.antlrTool.error("option 'codeGenDebug' must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 204:    */       }
/* 205:247 */       return true;
/* 206:    */     }
/* 207:249 */     if (paramString.equals("classHeaderSuffix")) {
/* 208:250 */       return true;
/* 209:    */     }
/* 210:252 */     if (paramString.equals("classHeaderPrefix")) {
/* 211:253 */       return true;
/* 212:    */     }
/* 213:255 */     if (paramString.equals("namespaceAntlr")) {
/* 214:256 */       return true;
/* 215:    */     }
/* 216:258 */     if (paramString.equals("namespaceStd")) {
/* 217:259 */       return true;
/* 218:    */     }
/* 219:261 */     if (paramString.equals("genHashLines")) {
/* 220:262 */       return true;
/* 221:    */     }
/* 222:264 */     if (paramString.equals("noConstructors")) {
/* 223:265 */       return true;
/* 224:    */     }
/* 225:267 */     return false;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTokenManager(TokenManager paramTokenManager)
/* 229:    */   {
/* 230:271 */     this.tokenManager = paramTokenManager;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:276 */     StringBuffer localStringBuffer = new StringBuffer(20000);
/* 236:277 */     Enumeration localEnumeration = this.rules.elements();
/* 237:278 */     while (localEnumeration.hasMoreElements())
/* 238:    */     {
/* 239:279 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 240:280 */       if (!localRuleSymbol.id.equals("mnextToken"))
/* 241:    */       {
/* 242:281 */         localStringBuffer.append(localRuleSymbol.getBlock().toString());
/* 243:282 */         localStringBuffer.append("\n\n");
/* 244:    */       }
/* 245:    */     }
/* 246:285 */     return localStringBuffer.toString();
/* 247:    */   }
/* 248:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Grammar
 * JD-Core Version:    0.7.0.1
 */