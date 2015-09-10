/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*   7:    */ 
/*   8:    */ final class SymbolTable
/*   9:    */ {
/*  10: 38 */   private final Hashtable _stylesheets = new Hashtable();
/*  11: 39 */   private final Hashtable _primops = new Hashtable();
/*  12: 42 */   private Hashtable _variables = null;
/*  13: 43 */   private Hashtable _templates = null;
/*  14: 44 */   private Hashtable _attributeSets = null;
/*  15: 45 */   private Hashtable _aliases = null;
/*  16: 46 */   private Hashtable _excludedURI = null;
/*  17: 47 */   private Hashtable _decimalFormats = null;
/*  18: 48 */   private Hashtable _keys = null;
/*  19:    */   
/*  20:    */   public DecimalFormatting getDecimalFormatting(QName name)
/*  21:    */   {
/*  22: 51 */     if (this._decimalFormats == null) {
/*  23: 51 */       return null;
/*  24:    */     }
/*  25: 52 */     return (DecimalFormatting)this._decimalFormats.get(name);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void addDecimalFormatting(QName name, DecimalFormatting symbols)
/*  29:    */   {
/*  30: 56 */     if (this._decimalFormats == null) {
/*  31: 56 */       this._decimalFormats = new Hashtable();
/*  32:    */     }
/*  33: 57 */     this._decimalFormats.put(name, symbols);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Key getKey(QName name)
/*  37:    */   {
/*  38: 61 */     if (this._keys == null) {
/*  39: 61 */       return null;
/*  40:    */     }
/*  41: 62 */     return (Key)this._keys.get(name);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addKey(QName name, Key key)
/*  45:    */   {
/*  46: 66 */     if (this._keys == null) {
/*  47: 66 */       this._keys = new Hashtable();
/*  48:    */     }
/*  49: 67 */     this._keys.put(name, key);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Stylesheet addStylesheet(QName name, Stylesheet node)
/*  53:    */   {
/*  54: 71 */     return (Stylesheet)this._stylesheets.put(name, node);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Stylesheet lookupStylesheet(QName name)
/*  58:    */   {
/*  59: 75 */     return (Stylesheet)this._stylesheets.get(name);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Template addTemplate(Template template)
/*  63:    */   {
/*  64: 79 */     QName name = template.getName();
/*  65: 80 */     if (this._templates == null) {
/*  66: 80 */       this._templates = new Hashtable();
/*  67:    */     }
/*  68: 81 */     return (Template)this._templates.put(name, template);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Template lookupTemplate(QName name)
/*  72:    */   {
/*  73: 85 */     if (this._templates == null) {
/*  74: 85 */       return null;
/*  75:    */     }
/*  76: 86 */     return (Template)this._templates.get(name);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Variable addVariable(Variable variable)
/*  80:    */   {
/*  81: 90 */     if (this._variables == null) {
/*  82: 90 */       this._variables = new Hashtable();
/*  83:    */     }
/*  84: 91 */     String name = variable.getName().getStringRep();
/*  85: 92 */     return (Variable)this._variables.put(name, variable);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Param addParam(Param parameter)
/*  89:    */   {
/*  90: 96 */     if (this._variables == null) {
/*  91: 96 */       this._variables = new Hashtable();
/*  92:    */     }
/*  93: 97 */     String name = parameter.getName().getStringRep();
/*  94: 98 */     return (Param)this._variables.put(name, parameter);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Variable lookupVariable(QName qname)
/*  98:    */   {
/*  99:102 */     if (this._variables == null) {
/* 100:102 */       return null;
/* 101:    */     }
/* 102:103 */     String name = qname.getStringRep();
/* 103:104 */     Object obj = this._variables.get(name);
/* 104:105 */     return (obj instanceof Variable) ? (Variable)obj : null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Param lookupParam(QName qname)
/* 108:    */   {
/* 109:109 */     if (this._variables == null) {
/* 110:109 */       return null;
/* 111:    */     }
/* 112:110 */     String name = qname.getStringRep();
/* 113:111 */     Object obj = this._variables.get(name);
/* 114:112 */     return (obj instanceof Param) ? (Param)obj : null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public SyntaxTreeNode lookupName(QName qname)
/* 118:    */   {
/* 119:116 */     if (this._variables == null) {
/* 120:116 */       return null;
/* 121:    */     }
/* 122:117 */     String name = qname.getStringRep();
/* 123:118 */     return (SyntaxTreeNode)this._variables.get(name);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public AttributeSet addAttributeSet(AttributeSet atts)
/* 127:    */   {
/* 128:122 */     if (this._attributeSets == null) {
/* 129:122 */       this._attributeSets = new Hashtable();
/* 130:    */     }
/* 131:123 */     return (AttributeSet)this._attributeSets.put(atts.getName(), atts);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public AttributeSet lookupAttributeSet(QName name)
/* 135:    */   {
/* 136:127 */     if (this._attributeSets == null) {
/* 137:127 */       return null;
/* 138:    */     }
/* 139:128 */     return (AttributeSet)this._attributeSets.get(name);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void addPrimop(String name, MethodType mtype)
/* 143:    */   {
/* 144:137 */     Vector methods = (Vector)this._primops.get(name);
/* 145:138 */     if (methods == null) {
/* 146:139 */       this._primops.put(name, methods = new Vector());
/* 147:    */     }
/* 148:141 */     methods.addElement(mtype);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Vector lookupPrimop(String name)
/* 152:    */   {
/* 153:149 */     return (Vector)this._primops.get(name);
/* 154:    */   }
/* 155:    */   
/* 156:156 */   private int _nsCounter = 0;
/* 157:    */   
/* 158:    */   public String generateNamespacePrefix()
/* 159:    */   {
/* 160:159 */     return "ns" + this._nsCounter++;
/* 161:    */   }
/* 162:    */   
/* 163:165 */   private SyntaxTreeNode _current = null;
/* 164:    */   
/* 165:    */   public void setCurrentNode(SyntaxTreeNode node)
/* 166:    */   {
/* 167:168 */     this._current = node;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String lookupNamespace(String prefix)
/* 171:    */   {
/* 172:172 */     if (this._current == null) {
/* 173:172 */       return "";
/* 174:    */     }
/* 175:173 */     return this._current.lookupNamespace(prefix);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void addPrefixAlias(String prefix, String alias)
/* 179:    */   {
/* 180:180 */     if (this._aliases == null) {
/* 181:180 */       this._aliases = new Hashtable();
/* 182:    */     }
/* 183:181 */     this._aliases.put(prefix, alias);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String lookupPrefixAlias(String prefix)
/* 187:    */   {
/* 188:188 */     if (this._aliases == null) {
/* 189:188 */       return null;
/* 190:    */     }
/* 191:189 */     return (String)this._aliases.get(prefix);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void excludeURI(String uri)
/* 195:    */   {
/* 196:198 */     if (uri == null) {
/* 197:198 */       return;
/* 198:    */     }
/* 199:201 */     if (this._excludedURI == null) {
/* 200:201 */       this._excludedURI = new Hashtable();
/* 201:    */     }
/* 202:204 */     Integer refcnt = (Integer)this._excludedURI.get(uri);
/* 203:205 */     if (refcnt == null) {
/* 204:206 */       refcnt = new Integer(1);
/* 205:    */     } else {
/* 206:208 */       refcnt = new Integer(refcnt.intValue() + 1);
/* 207:    */     }
/* 208:209 */     this._excludedURI.put(uri, refcnt);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void excludeNamespaces(String prefixes)
/* 212:    */   {
/* 213:217 */     if (prefixes != null)
/* 214:    */     {
/* 215:218 */       StringTokenizer tokens = new StringTokenizer(prefixes);
/* 216:219 */       while (tokens.hasMoreTokens())
/* 217:    */       {
/* 218:220 */         String prefix = tokens.nextToken();
/* 219:    */         String uri;
/* 220:222 */         if (prefix.equals("#default")) {
/* 221:223 */           uri = lookupNamespace("");
/* 222:    */         } else {
/* 223:225 */           uri = lookupNamespace(prefix);
/* 224:    */         }
/* 225:226 */         if (uri != null) {
/* 226:226 */           excludeURI(uri);
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean isExcludedNamespace(String uri)
/* 233:    */   {
/* 234:235 */     if ((uri != null) && (this._excludedURI != null))
/* 235:    */     {
/* 236:236 */       Integer refcnt = (Integer)this._excludedURI.get(uri);
/* 237:237 */       return (refcnt != null) && (refcnt.intValue() > 0);
/* 238:    */     }
/* 239:239 */     return false;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void unExcludeNamespaces(String prefixes)
/* 243:    */   {
/* 244:246 */     if (this._excludedURI == null) {
/* 245:246 */       return;
/* 246:    */     }
/* 247:247 */     if (prefixes != null)
/* 248:    */     {
/* 249:248 */       StringTokenizer tokens = new StringTokenizer(prefixes);
/* 250:249 */       while (tokens.hasMoreTokens())
/* 251:    */       {
/* 252:250 */         String prefix = tokens.nextToken();
/* 253:    */         String uri;
/* 254:252 */         if (prefix.equals("#default")) {
/* 255:253 */           uri = lookupNamespace("");
/* 256:    */         } else {
/* 257:255 */           uri = lookupNamespace(prefix);
/* 258:    */         }
/* 259:256 */         Integer refcnt = (Integer)this._excludedURI.get(uri);
/* 260:257 */         if (refcnt != null) {
/* 261:258 */           this._excludedURI.put(uri, new Integer(refcnt.intValue() - 1));
/* 262:    */         }
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.SymbolTable
 * JD-Core Version:    0.7.0.1
 */