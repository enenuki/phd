/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.NamedMethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  16:    */ import org.apache.xml.utils.XML11Char;
/*  17:    */ 
/*  18:    */ public final class Template
/*  19:    */   extends TopLevelElement
/*  20:    */ {
/*  21:    */   private QName _name;
/*  22:    */   private QName _mode;
/*  23:    */   private Pattern _pattern;
/*  24:    */   private double _priority;
/*  25:    */   private int _position;
/*  26: 53 */   private boolean _disabled = false;
/*  27: 54 */   private boolean _compiled = false;
/*  28: 55 */   private boolean _simplified = false;
/*  29: 59 */   private boolean _isSimpleNamedTemplate = false;
/*  30: 63 */   private Vector _parameters = new Vector();
/*  31:    */   
/*  32:    */   public boolean hasParams()
/*  33:    */   {
/*  34: 66 */     return this._parameters.size() > 0;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isSimplified()
/*  38:    */   {
/*  39: 70 */     return this._simplified;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setSimplified()
/*  43:    */   {
/*  44: 74 */     this._simplified = true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isSimpleNamedTemplate()
/*  48:    */   {
/*  49: 78 */     return this._isSimpleNamedTemplate;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addParameter(Param param)
/*  53:    */   {
/*  54: 82 */     this._parameters.addElement(param);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Vector getParameters()
/*  58:    */   {
/*  59: 86 */     return this._parameters;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void disable()
/*  63:    */   {
/*  64: 90 */     this._disabled = true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean disabled()
/*  68:    */   {
/*  69: 94 */     return this._disabled;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public double getPriority()
/*  73:    */   {
/*  74: 98 */     return this._priority;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getPosition()
/*  78:    */   {
/*  79:102 */     return this._position;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isNamed()
/*  83:    */   {
/*  84:106 */     return this._name != null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Pattern getPattern()
/*  88:    */   {
/*  89:110 */     return this._pattern;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public QName getName()
/*  93:    */   {
/*  94:114 */     return this._name;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setName(QName qname)
/*  98:    */   {
/*  99:118 */     if (this._name == null) {
/* 100:118 */       this._name = qname;
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public QName getModeName()
/* 105:    */   {
/* 106:122 */     return this._mode;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int compareTo(Object template)
/* 110:    */   {
/* 111:129 */     Template other = (Template)template;
/* 112:130 */     if (this._priority > other._priority) {
/* 113:131 */       return 1;
/* 114:    */     }
/* 115:132 */     if (this._priority < other._priority) {
/* 116:133 */       return -1;
/* 117:    */     }
/* 118:134 */     if (this._position > other._position) {
/* 119:135 */       return 1;
/* 120:    */     }
/* 121:136 */     if (this._position < other._position) {
/* 122:137 */       return -1;
/* 123:    */     }
/* 124:139 */     return 0;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void display(int indent)
/* 128:    */   {
/* 129:143 */     Util.println('\n');
/* 130:144 */     indent(indent);
/* 131:145 */     if (this._name != null)
/* 132:    */     {
/* 133:146 */       indent(indent);
/* 134:147 */       Util.println("name = " + this._name);
/* 135:    */     }
/* 136:149 */     else if (this._pattern != null)
/* 137:    */     {
/* 138:150 */       indent(indent);
/* 139:151 */       Util.println("match = " + this._pattern.toString());
/* 140:    */     }
/* 141:153 */     if (this._mode != null)
/* 142:    */     {
/* 143:154 */       indent(indent);
/* 144:155 */       Util.println("mode = " + this._mode);
/* 145:    */     }
/* 146:157 */     displayContents(indent + 4);
/* 147:    */   }
/* 148:    */   
/* 149:    */   private boolean resolveNamedTemplates(Template other, Parser parser)
/* 150:    */   {
/* 151:162 */     if (other == null) {
/* 152:162 */       return true;
/* 153:    */     }
/* 154:164 */     SymbolTable stable = parser.getSymbolTable();
/* 155:    */     
/* 156:166 */     int us = getImportPrecedence();
/* 157:167 */     int them = other.getImportPrecedence();
/* 158:169 */     if (us > them)
/* 159:    */     {
/* 160:170 */       other.disable();
/* 161:171 */       return true;
/* 162:    */     }
/* 163:173 */     if (us < them)
/* 164:    */     {
/* 165:174 */       stable.addTemplate(other);
/* 166:175 */       disable();
/* 167:176 */       return true;
/* 168:    */     }
/* 169:179 */     return false;
/* 170:    */   }
/* 171:    */   
/* 172:183 */   private Stylesheet _stylesheet = null;
/* 173:    */   
/* 174:    */   public Stylesheet getStylesheet()
/* 175:    */   {
/* 176:186 */     return this._stylesheet;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void parseContents(Parser parser)
/* 180:    */   {
/* 181:191 */     String name = getAttribute("name");
/* 182:192 */     String mode = getAttribute("mode");
/* 183:193 */     String match = getAttribute("match");
/* 184:194 */     String priority = getAttribute("priority");
/* 185:    */     
/* 186:196 */     this._stylesheet = super.getStylesheet();
/* 187:198 */     if (name.length() > 0)
/* 188:    */     {
/* 189:199 */       if (!XML11Char.isXML11ValidQName(name))
/* 190:    */       {
/* 191:200 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/* 192:201 */         parser.reportError(3, err);
/* 193:    */       }
/* 194:203 */       this._name = parser.getQNameIgnoreDefaultNs(name);
/* 195:    */     }
/* 196:206 */     if (mode.length() > 0)
/* 197:    */     {
/* 198:207 */       if (!XML11Char.isXML11ValidQName(mode))
/* 199:    */       {
/* 200:208 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", mode, this);
/* 201:209 */         parser.reportError(3, err);
/* 202:    */       }
/* 203:211 */       this._mode = parser.getQNameIgnoreDefaultNs(mode);
/* 204:    */     }
/* 205:214 */     if (match.length() > 0) {
/* 206:215 */       this._pattern = parser.parsePattern(this, "match", null);
/* 207:    */     }
/* 208:218 */     if (priority.length() > 0) {
/* 209:219 */       this._priority = Double.parseDouble(priority);
/* 210:222 */     } else if (this._pattern != null) {
/* 211:223 */       this._priority = this._pattern.getPriority();
/* 212:    */     } else {
/* 213:225 */       this._priority = (0.0D / 0.0D);
/* 214:    */     }
/* 215:228 */     this._position = parser.getTemplateIndex();
/* 216:231 */     if (this._name != null)
/* 217:    */     {
/* 218:232 */       Template other = parser.getSymbolTable().addTemplate(this);
/* 219:233 */       if (!resolveNamedTemplates(other, parser))
/* 220:    */       {
/* 221:234 */         ErrorMsg err = new ErrorMsg("TEMPLATE_REDEF_ERR", this._name, this);
/* 222:    */         
/* 223:236 */         parser.reportError(3, err);
/* 224:    */       }
/* 225:239 */       if ((this._pattern == null) && (this._mode == null)) {
/* 226:240 */         this._isSimpleNamedTemplate = true;
/* 227:    */       }
/* 228:    */     }
/* 229:244 */     if ((this._parent instanceof Stylesheet)) {
/* 230:245 */       ((Stylesheet)this._parent).addTemplate(this);
/* 231:    */     }
/* 232:248 */     parser.setTemplate(this);
/* 233:249 */     parseChildren(parser);
/* 234:250 */     parser.setTemplate(null);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void parseSimplified(Stylesheet stylesheet, Parser parser)
/* 238:    */   {
/* 239:267 */     this._stylesheet = stylesheet;
/* 240:268 */     setParent(stylesheet);
/* 241:    */     
/* 242:270 */     this._name = null;
/* 243:271 */     this._mode = null;
/* 244:272 */     this._priority = (0.0D / 0.0D);
/* 245:273 */     this._pattern = parser.parsePattern(this, "/");
/* 246:    */     
/* 247:275 */     Vector contents = this._stylesheet.getContents();
/* 248:276 */     SyntaxTreeNode root = (SyntaxTreeNode)contents.elementAt(0);
/* 249:278 */     if ((root instanceof LiteralElement))
/* 250:    */     {
/* 251:279 */       addElement(root);
/* 252:280 */       root.setParent(this);
/* 253:281 */       contents.set(0, this);
/* 254:282 */       parser.setTemplate(this);
/* 255:283 */       root.parseContents(parser);
/* 256:284 */       parser.setTemplate(null);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Type typeCheck(SymbolTable stable)
/* 261:    */     throws TypeCheckError
/* 262:    */   {
/* 263:289 */     if (this._pattern != null) {
/* 264:290 */       this._pattern.typeCheck(stable);
/* 265:    */     }
/* 266:293 */     return typeCheckContents(stable);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 270:    */   {
/* 271:297 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 272:298 */     InstructionList il = methodGen.getInstructionList();
/* 273:300 */     if (this._disabled) {
/* 274:300 */       return;
/* 275:    */     }
/* 276:302 */     String className = classGen.getClassName();
/* 277:304 */     if ((this._compiled) && (isNamed()))
/* 278:    */     {
/* 279:305 */       String methodName = Util.escape(this._name.toString());
/* 280:306 */       il.append(classGen.loadTranslet());
/* 281:307 */       il.append(methodGen.loadDOM());
/* 282:308 */       il.append(methodGen.loadIterator());
/* 283:309 */       il.append(methodGen.loadHandler());
/* 284:310 */       il.append(methodGen.loadCurrentNode());
/* 285:311 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref(className, methodName, "(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + "I)V")));
/* 286:    */       
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:318 */       return;
/* 293:    */     }
/* 294:321 */     if (this._compiled) {
/* 295:321 */       return;
/* 296:    */     }
/* 297:322 */     this._compiled = true;
/* 298:325 */     if ((this._isSimpleNamedTemplate) && ((methodGen instanceof NamedMethodGenerator)))
/* 299:    */     {
/* 300:326 */       int numParams = this._parameters.size();
/* 301:327 */       NamedMethodGenerator namedMethodGen = (NamedMethodGenerator)methodGen;
/* 302:330 */       for (int i = 0; i < numParams; i++)
/* 303:    */       {
/* 304:331 */         Param param = (Param)this._parameters.elementAt(i);
/* 305:332 */         param.setLoadInstruction(namedMethodGen.loadParameter(i));
/* 306:333 */         param.setStoreInstruction(namedMethodGen.storeParameter(i));
/* 307:    */       }
/* 308:    */     }
/* 309:337 */     translateContents(classGen, methodGen);
/* 310:338 */     il.setPositions(true);
/* 311:    */   }
/* 312:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Template
 * JD-Core Version:    0.7.0.1
 */