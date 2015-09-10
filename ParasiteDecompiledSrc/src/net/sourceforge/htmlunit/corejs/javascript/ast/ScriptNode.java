/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   7:    */ 
/*   8:    */ public class ScriptNode
/*   9:    */   extends Scope
/*  10:    */ {
/*  11: 57 */   private int encodedSourceStart = -1;
/*  12: 58 */   private int encodedSourceEnd = -1;
/*  13:    */   private String sourceName;
/*  14:    */   private String encodedSource;
/*  15: 61 */   private int endLineno = -1;
/*  16:    */   private List<FunctionNode> functions;
/*  17:    */   private List<RegExpLiteral> regexps;
/*  18: 65 */   private List<FunctionNode> EMPTY_LIST = Collections.emptyList();
/*  19: 67 */   private List<Symbol> symbols = new ArrayList(4);
/*  20: 68 */   private int paramCount = 0;
/*  21:    */   private String[] variableNames;
/*  22:    */   private boolean[] isConsts;
/*  23:    */   private Object compilerData;
/*  24: 73 */   private int tempNumber = 0;
/*  25:    */   
/*  26:    */   public ScriptNode()
/*  27:    */   {
/*  28: 77 */     this.top = this;
/*  29: 78 */     this.type = 136;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ScriptNode(int pos)
/*  33:    */   {
/*  34: 85 */     super(pos);this.top = this;this.type = 136;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getSourceName()
/*  38:    */   {
/*  39: 93 */     return this.sourceName;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setSourceName(String sourceName)
/*  43:    */   {
/*  44:101 */     this.sourceName = sourceName;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getEncodedSourceStart()
/*  48:    */   {
/*  49:109 */     return this.encodedSourceStart;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setEncodedSourceStart(int start)
/*  53:    */   {
/*  54:117 */     this.encodedSourceStart = start;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getEncodedSourceEnd()
/*  58:    */   {
/*  59:125 */     return this.encodedSourceEnd;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setEncodedSourceEnd(int end)
/*  63:    */   {
/*  64:133 */     this.encodedSourceEnd = end;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setEncodedSourceBounds(int start, int end)
/*  68:    */   {
/*  69:141 */     this.encodedSourceStart = start;
/*  70:142 */     this.encodedSourceEnd = end;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setEncodedSource(String encodedSource)
/*  74:    */   {
/*  75:150 */     this.encodedSource = encodedSource;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getEncodedSource()
/*  79:    */   {
/*  80:169 */     return this.encodedSource;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getBaseLineno()
/*  84:    */   {
/*  85:173 */     return this.lineno;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setBaseLineno(int lineno)
/*  89:    */   {
/*  90:182 */     if ((lineno < 0) || (this.lineno >= 0)) {
/*  91:182 */       codeBug();
/*  92:    */     }
/*  93:183 */     this.lineno = lineno;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getEndLineno()
/*  97:    */   {
/*  98:187 */     return this.endLineno;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setEndLineno(int lineno)
/* 102:    */   {
/* 103:192 */     if ((lineno < 0) || (this.endLineno >= 0)) {
/* 104:192 */       codeBug();
/* 105:    */     }
/* 106:193 */     this.endLineno = lineno;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getFunctionCount()
/* 110:    */   {
/* 111:197 */     return this.functions == null ? 0 : this.functions.size();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public FunctionNode getFunctionNode(int i)
/* 115:    */   {
/* 116:201 */     return (FunctionNode)this.functions.get(i);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<FunctionNode> getFunctions()
/* 120:    */   {
/* 121:205 */     return this.functions == null ? this.EMPTY_LIST : this.functions;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int addFunction(FunctionNode fnNode)
/* 125:    */   {
/* 126:214 */     if (fnNode == null) {
/* 127:214 */       codeBug();
/* 128:    */     }
/* 129:215 */     if (this.functions == null) {
/* 130:216 */       this.functions = new ArrayList();
/* 131:    */     }
/* 132:217 */     this.functions.add(fnNode);
/* 133:218 */     return this.functions.size() - 1;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getRegexpCount()
/* 137:    */   {
/* 138:222 */     return this.regexps == null ? 0 : this.regexps.size();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getRegexpString(int index)
/* 142:    */   {
/* 143:226 */     return ((RegExpLiteral)this.regexps.get(index)).getValue();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getRegexpFlags(int index)
/* 147:    */   {
/* 148:230 */     return ((RegExpLiteral)this.regexps.get(index)).getFlags();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void addRegExp(RegExpLiteral re)
/* 152:    */   {
/* 153:237 */     if (re == null) {
/* 154:237 */       codeBug();
/* 155:    */     }
/* 156:238 */     if (this.regexps == null) {
/* 157:239 */       this.regexps = new ArrayList();
/* 158:    */     }
/* 159:240 */     this.regexps.add(re);
/* 160:241 */     re.putIntProp(4, this.regexps.size() - 1);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getIndexForNameNode(Node nameNode)
/* 164:    */   {
/* 165:245 */     if (this.variableNames == null) {
/* 166:245 */       codeBug();
/* 167:    */     }
/* 168:246 */     Scope node = nameNode.getScope();
/* 169:247 */     Symbol symbol = node == null ? null : node.getSymbol(((Name)nameNode).getIdentifier());
/* 170:    */     
/* 171:    */ 
/* 172:250 */     return symbol == null ? -1 : symbol.getIndex();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getParamOrVarName(int index)
/* 176:    */   {
/* 177:254 */     if (this.variableNames == null) {
/* 178:254 */       codeBug();
/* 179:    */     }
/* 180:255 */     return this.variableNames[index];
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getParamCount()
/* 184:    */   {
/* 185:259 */     return this.paramCount;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getParamAndVarCount()
/* 189:    */   {
/* 190:263 */     if (this.variableNames == null) {
/* 191:263 */       codeBug();
/* 192:    */     }
/* 193:264 */     return this.symbols.size();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String[] getParamAndVarNames()
/* 197:    */   {
/* 198:268 */     if (this.variableNames == null) {
/* 199:268 */       codeBug();
/* 200:    */     }
/* 201:269 */     return this.variableNames;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean[] getParamAndVarConst()
/* 205:    */   {
/* 206:273 */     if (this.variableNames == null) {
/* 207:273 */       codeBug();
/* 208:    */     }
/* 209:274 */     return this.isConsts;
/* 210:    */   }
/* 211:    */   
/* 212:    */   void addSymbol(Symbol symbol)
/* 213:    */   {
/* 214:278 */     if (this.variableNames != null) {
/* 215:278 */       codeBug();
/* 216:    */     }
/* 217:279 */     if (symbol.getDeclType() == 87) {
/* 218:280 */       this.paramCount += 1;
/* 219:    */     }
/* 220:282 */     this.symbols.add(symbol);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<Symbol> getSymbols()
/* 224:    */   {
/* 225:286 */     return this.symbols;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setSymbols(List<Symbol> symbols)
/* 229:    */   {
/* 230:290 */     this.symbols = symbols;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void flattenSymbolTable(boolean flattenAllTables)
/* 234:    */   {
/* 235:302 */     if (!flattenAllTables)
/* 236:    */     {
/* 237:303 */       List<Symbol> newSymbols = new ArrayList();
/* 238:304 */       if (this.symbolTable != null) {
/* 239:308 */         for (int i = 0; i < this.symbols.size(); i++)
/* 240:    */         {
/* 241:309 */           Symbol symbol = (Symbol)this.symbols.get(i);
/* 242:310 */           if (symbol.getContainingTable() == this) {
/* 243:311 */             newSymbols.add(symbol);
/* 244:    */           }
/* 245:    */         }
/* 246:    */       }
/* 247:315 */       this.symbols = newSymbols;
/* 248:    */     }
/* 249:317 */     this.variableNames = new String[this.symbols.size()];
/* 250:318 */     this.isConsts = new boolean[this.symbols.size()];
/* 251:319 */     for (int i = 0; i < this.symbols.size(); i++)
/* 252:    */     {
/* 253:320 */       Symbol symbol = (Symbol)this.symbols.get(i);
/* 254:321 */       this.variableNames[i] = symbol.getName();
/* 255:322 */       this.isConsts[i] = (symbol.getDeclType() == 154 ? 1 : false);
/* 256:323 */       symbol.setIndex(i);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Object getCompilerData()
/* 261:    */   {
/* 262:328 */     return this.compilerData;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setCompilerData(Object data)
/* 266:    */   {
/* 267:332 */     assertNotNull(data);
/* 268:334 */     if (this.compilerData != null) {
/* 269:335 */       throw new IllegalStateException();
/* 270:    */     }
/* 271:336 */     this.compilerData = data;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public String getNextTempName()
/* 275:    */   {
/* 276:340 */     return "$" + this.tempNumber++;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void visit(NodeVisitor v)
/* 280:    */   {
/* 281:345 */     if (v.visit(this)) {
/* 282:346 */       for (Node kid : this) {
/* 283:347 */         ((AstNode)kid).visit(v);
/* 284:    */       }
/* 285:    */     }
/* 286:    */   }
/* 287:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode
 * JD-Core Version:    0.7.0.1
 */