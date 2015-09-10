/*   1:    */ package antlr.preprocessor;
/*   2:    */ 
/*   3:    */ import antlr.CodeGenerator;
/*   4:    */ import antlr.Tool;
/*   5:    */ import antlr.collections.impl.IndexedVector;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ 
/*   9:    */ class Grammar
/*  10:    */ {
/*  11:    */   protected String name;
/*  12:    */   protected String fileName;
/*  13:    */   protected String superGrammar;
/*  14:    */   protected String type;
/*  15:    */   protected IndexedVector rules;
/*  16:    */   protected IndexedVector options;
/*  17:    */   protected String tokenSection;
/*  18:    */   protected String preambleAction;
/*  19:    */   protected String memberAction;
/*  20:    */   protected Hierarchy hier;
/*  21: 27 */   protected boolean predefined = false;
/*  22: 28 */   protected boolean alreadyExpanded = false;
/*  23: 29 */   protected boolean specifiedVocabulary = false;
/*  24: 34 */   protected String superClass = null;
/*  25: 36 */   protected String importVocab = null;
/*  26: 37 */   protected String exportVocab = null;
/*  27:    */   protected Tool antlrTool;
/*  28:    */   
/*  29:    */   public Grammar(Tool paramTool, String paramString1, String paramString2, IndexedVector paramIndexedVector)
/*  30:    */   {
/*  31: 41 */     this.name = paramString1;
/*  32: 42 */     this.superGrammar = paramString2;
/*  33: 43 */     this.rules = paramIndexedVector;
/*  34: 44 */     this.antlrTool = paramTool;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void addOption(Option paramOption)
/*  38:    */   {
/*  39: 48 */     if (this.options == null) {
/*  40: 49 */       this.options = new IndexedVector();
/*  41:    */     }
/*  42: 51 */     this.options.appendElement(paramOption.getName(), paramOption);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addRule(Rule paramRule)
/*  46:    */   {
/*  47: 55 */     this.rules.appendElement(paramRule.getName(), paramRule);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void expandInPlace()
/*  51:    */   {
/*  52: 65 */     if (this.alreadyExpanded) {
/*  53: 66 */       return;
/*  54:    */     }
/*  55: 70 */     Grammar localGrammar = getSuperGrammar();
/*  56: 71 */     if (localGrammar == null) {
/*  57: 72 */       return;
/*  58:    */     }
/*  59: 73 */     if (this.exportVocab == null) {
/*  60: 75 */       this.exportVocab = getName();
/*  61:    */     }
/*  62: 77 */     if (localGrammar.isPredefined()) {
/*  63: 78 */       return;
/*  64:    */     }
/*  65: 79 */     localGrammar.expandInPlace();
/*  66:    */     
/*  67:    */ 
/*  68: 82 */     this.alreadyExpanded = true;
/*  69:    */     
/*  70: 84 */     GrammarFile localGrammarFile = this.hier.getFile(getFileName());
/*  71: 85 */     localGrammarFile.setExpanded(true);
/*  72:    */     
/*  73:    */ 
/*  74: 88 */     IndexedVector localIndexedVector = localGrammar.getRules();
/*  75: 89 */     for (Object localObject1 = localIndexedVector.elements(); ((Enumeration)localObject1).hasMoreElements();)
/*  76:    */     {
/*  77: 90 */       localObject2 = (Rule)((Enumeration)localObject1).nextElement();
/*  78: 91 */       inherit((Rule)localObject2, localGrammar);
/*  79:    */     }
/*  80:    */     Object localObject2;
/*  81: 96 */     localObject1 = localGrammar.getOptions();
/*  82: 97 */     if (localObject1 != null) {
/*  83: 98 */       for (localObject2 = ((IndexedVector)localObject1).elements(); ((Enumeration)localObject2).hasMoreElements();)
/*  84:    */       {
/*  85: 99 */         localObject3 = (Option)((Enumeration)localObject2).nextElement();
/*  86:100 */         inherit((Option)localObject3, localGrammar);
/*  87:    */       }
/*  88:    */     }
/*  89:    */     Object localObject3;
/*  90:105 */     if (((this.options != null) && (this.options.getElement("importVocab") == null)) || (this.options == null))
/*  91:    */     {
/*  92:107 */       localObject2 = new Option("importVocab", localGrammar.exportVocab + ";", this);
/*  93:108 */       addOption((Option)localObject2);
/*  94:    */       
/*  95:110 */       localObject3 = localGrammar.getFileName();
/*  96:111 */       String str1 = this.antlrTool.pathToFile((String)localObject3);
/*  97:112 */       String str2 = str1 + localGrammar.exportVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt;
/*  98:    */       
/*  99:    */ 
/* 100:115 */       String str3 = this.antlrTool.fileMinusPath(str2);
/* 101:116 */       if (!str1.equals("." + System.getProperty("file.separator"))) {
/* 102:    */         try
/* 103:    */         {
/* 104:122 */           this.antlrTool.copyFile(str2, str3);
/* 105:    */         }
/* 106:    */         catch (IOException localIOException)
/* 107:    */         {
/* 108:125 */           this.antlrTool.toolError("cannot find/copy importVocab file " + str2);
/* 109:126 */           return;
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:132 */     inherit(localGrammar.memberAction, localGrammar);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getFileName()
/* 117:    */   {
/* 118:136 */     return this.fileName;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getName()
/* 122:    */   {
/* 123:140 */     return this.name;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public IndexedVector getOptions()
/* 127:    */   {
/* 128:144 */     return this.options;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public IndexedVector getRules()
/* 132:    */   {
/* 133:148 */     return this.rules;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Grammar getSuperGrammar()
/* 137:    */   {
/* 138:152 */     if (this.superGrammar == null) {
/* 139:152 */       return null;
/* 140:    */     }
/* 141:153 */     Grammar localGrammar = this.hier.getGrammar(this.superGrammar);
/* 142:154 */     return localGrammar;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getSuperGrammarName()
/* 146:    */   {
/* 147:158 */     return this.superGrammar;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getType()
/* 151:    */   {
/* 152:162 */     return this.type;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void inherit(Option paramOption, Grammar paramGrammar)
/* 156:    */   {
/* 157:167 */     if ((paramOption.getName().equals("importVocab")) || (paramOption.getName().equals("exportVocab"))) {
/* 158:169 */       return;
/* 159:    */     }
/* 160:172 */     Option localOption = null;
/* 161:173 */     if (this.options != null) {
/* 162:174 */       localOption = (Option)this.options.getElement(paramOption.getName());
/* 163:    */     }
/* 164:177 */     if (localOption == null) {
/* 165:178 */       addOption(paramOption);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void inherit(Rule paramRule, Grammar paramGrammar)
/* 170:    */   {
/* 171:184 */     Rule localRule = (Rule)this.rules.getElement(paramRule.getName());
/* 172:185 */     if (localRule != null)
/* 173:    */     {
/* 174:187 */       if (!localRule.sameSignature(paramRule)) {
/* 175:189 */         this.antlrTool.warning("rule " + getName() + "." + localRule.getName() + " has different signature than " + paramGrammar.getName() + "." + localRule.getName());
/* 176:    */       }
/* 177:    */     }
/* 178:    */     else {
/* 179:195 */       addRule(paramRule);
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void inherit(String paramString, Grammar paramGrammar)
/* 184:    */   {
/* 185:200 */     if (this.memberAction != null) {
/* 186:200 */       return;
/* 187:    */     }
/* 188:201 */     if (paramString != null) {
/* 189:202 */       this.memberAction = paramString;
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean isPredefined()
/* 194:    */   {
/* 195:207 */     return this.predefined;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setFileName(String paramString)
/* 199:    */   {
/* 200:211 */     this.fileName = paramString;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setHierarchy(Hierarchy paramHierarchy)
/* 204:    */   {
/* 205:215 */     this.hier = paramHierarchy;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setMemberAction(String paramString)
/* 209:    */   {
/* 210:219 */     this.memberAction = paramString;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setOptions(IndexedVector paramIndexedVector)
/* 214:    */   {
/* 215:223 */     this.options = paramIndexedVector;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setPreambleAction(String paramString)
/* 219:    */   {
/* 220:227 */     this.preambleAction = paramString;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setPredefined(boolean paramBoolean)
/* 224:    */   {
/* 225:231 */     this.predefined = paramBoolean;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTokenSection(String paramString)
/* 229:    */   {
/* 230:235 */     this.tokenSection = paramString;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setType(String paramString)
/* 234:    */   {
/* 235:239 */     this.type = paramString;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String toString()
/* 239:    */   {
/* 240:243 */     StringBuffer localStringBuffer = new StringBuffer(10000);
/* 241:244 */     if (this.preambleAction != null) {
/* 242:245 */       localStringBuffer.append(this.preambleAction);
/* 243:    */     }
/* 244:247 */     if (this.superGrammar == null) {
/* 245:248 */       return "class " + this.name + ";";
/* 246:    */     }
/* 247:250 */     if (this.superClass != null) {
/* 248:253 */       localStringBuffer.append("class " + this.name + " extends " + this.superClass + ";");
/* 249:    */     } else {
/* 250:256 */       localStringBuffer.append("class " + this.name + " extends " + this.type + ";");
/* 251:    */     }
/* 252:258 */     localStringBuffer.append(System.getProperty("line.separator") + System.getProperty("line.separator"));
/* 253:261 */     if (this.options != null) {
/* 254:262 */       localStringBuffer.append(Hierarchy.optionsToString(this.options));
/* 255:    */     }
/* 256:264 */     if (this.tokenSection != null) {
/* 257:265 */       localStringBuffer.append(this.tokenSection + "\n");
/* 258:    */     }
/* 259:267 */     if (this.memberAction != null) {
/* 260:268 */       localStringBuffer.append(this.memberAction + System.getProperty("line.separator"));
/* 261:    */     }
/* 262:270 */     for (int i = 0; i < this.rules.size(); i++)
/* 263:    */     {
/* 264:271 */       Rule localRule = (Rule)this.rules.elementAt(i);
/* 265:272 */       if (!getName().equals(localRule.enclosingGrammar.getName())) {
/* 266:273 */         localStringBuffer.append("// inherited from grammar " + localRule.enclosingGrammar.getName() + System.getProperty("line.separator"));
/* 267:    */       }
/* 268:275 */       localStringBuffer.append(localRule + System.getProperty("line.separator") + System.getProperty("line.separator"));
/* 269:    */     }
/* 270:279 */     return localStringBuffer.toString();
/* 271:    */   }
/* 272:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Grammar
 * JD-Core Version:    0.7.0.1
 */