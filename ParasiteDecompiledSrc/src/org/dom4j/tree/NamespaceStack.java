/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.dom4j.DocumentFactory;
/*   8:    */ import org.dom4j.Namespace;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ 
/*  11:    */ public class NamespaceStack
/*  12:    */ {
/*  13:    */   private DocumentFactory documentFactory;
/*  14: 31 */   private ArrayList namespaceStack = new ArrayList();
/*  15: 34 */   private ArrayList namespaceCacheList = new ArrayList();
/*  16:    */   private Map currentNamespaceCache;
/*  17: 46 */   private Map rootNamespaceCache = new HashMap();
/*  18:    */   private Namespace defaultNamespace;
/*  19:    */   
/*  20:    */   public NamespaceStack()
/*  21:    */   {
/*  22: 52 */     this.documentFactory = DocumentFactory.getInstance();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public NamespaceStack(DocumentFactory documentFactory)
/*  26:    */   {
/*  27: 56 */     this.documentFactory = documentFactory;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void push(Namespace namespace)
/*  31:    */   {
/*  32: 67 */     this.namespaceStack.add(namespace);
/*  33: 68 */     this.namespaceCacheList.add(null);
/*  34: 69 */     this.currentNamespaceCache = null;
/*  35:    */     
/*  36: 71 */     String prefix = namespace.getPrefix();
/*  37: 73 */     if ((prefix == null) || (prefix.length() == 0)) {
/*  38: 74 */       this.defaultNamespace = namespace;
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Namespace pop()
/*  43:    */   {
/*  44: 84 */     return remove(this.namespaceStack.size() - 1);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int size()
/*  48:    */   {
/*  49: 93 */     return this.namespaceStack.size();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void clear()
/*  53:    */   {
/*  54:100 */     this.namespaceStack.clear();
/*  55:101 */     this.namespaceCacheList.clear();
/*  56:102 */     this.rootNamespaceCache.clear();
/*  57:103 */     this.currentNamespaceCache = null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Namespace getNamespace(int index)
/*  61:    */   {
/*  62:115 */     return (Namespace)this.namespaceStack.get(index);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Namespace getNamespaceForPrefix(String prefix)
/*  66:    */   {
/*  67:128 */     if (prefix == null) {
/*  68:129 */       prefix = "";
/*  69:    */     }
/*  70:132 */     for (int i = this.namespaceStack.size() - 1; i >= 0; i--)
/*  71:    */     {
/*  72:133 */       Namespace namespace = (Namespace)this.namespaceStack.get(i);
/*  73:135 */       if (prefix.equals(namespace.getPrefix())) {
/*  74:136 */         return namespace;
/*  75:    */       }
/*  76:    */     }
/*  77:140 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getURI(String prefix)
/*  81:    */   {
/*  82:152 */     Namespace namespace = getNamespaceForPrefix(prefix);
/*  83:    */     
/*  84:154 */     return namespace != null ? namespace.getURI() : null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean contains(Namespace namespace)
/*  88:    */   {
/*  89:166 */     String prefix = namespace.getPrefix();
/*  90:167 */     Namespace current = null;
/*  91:169 */     if ((prefix == null) || (prefix.length() == 0)) {
/*  92:170 */       current = getDefaultNamespace();
/*  93:    */     } else {
/*  94:172 */       current = getNamespaceForPrefix(prefix);
/*  95:    */     }
/*  96:175 */     if (current == null) {
/*  97:176 */       return false;
/*  98:    */     }
/*  99:179 */     if (current == namespace) {
/* 100:180 */       return true;
/* 101:    */     }
/* 102:183 */     return namespace.getURI().equals(current.getURI());
/* 103:    */   }
/* 104:    */   
/* 105:    */   public QName getQName(String namespaceURI, String localName, String qualifiedName)
/* 106:    */   {
/* 107:188 */     if (localName == null) {
/* 108:189 */       localName = qualifiedName;
/* 109:190 */     } else if (qualifiedName == null) {
/* 110:191 */       qualifiedName = localName;
/* 111:    */     }
/* 112:194 */     if (namespaceURI == null) {
/* 113:195 */       namespaceURI = "";
/* 114:    */     }
/* 115:198 */     String prefix = "";
/* 116:199 */     int index = qualifiedName.indexOf(":");
/* 117:201 */     if (index > 0)
/* 118:    */     {
/* 119:202 */       prefix = qualifiedName.substring(0, index);
/* 120:204 */       if (localName.trim().length() == 0) {
/* 121:205 */         localName = qualifiedName.substring(index + 1);
/* 122:    */       }
/* 123:    */     }
/* 124:207 */     else if (localName.trim().length() == 0)
/* 125:    */     {
/* 126:208 */       localName = qualifiedName;
/* 127:    */     }
/* 128:211 */     Namespace namespace = createNamespace(prefix, namespaceURI);
/* 129:    */     
/* 130:213 */     return pushQName(localName, qualifiedName, namespace, prefix);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public QName getAttributeQName(String namespaceURI, String localName, String qualifiedName)
/* 134:    */   {
/* 135:218 */     if (qualifiedName == null) {
/* 136:219 */       qualifiedName = localName;
/* 137:    */     }
/* 138:222 */     Map map = getNamespaceCache();
/* 139:223 */     QName answer = (QName)map.get(qualifiedName);
/* 140:225 */     if (answer != null) {
/* 141:226 */       return answer;
/* 142:    */     }
/* 143:229 */     if (localName == null) {
/* 144:230 */       localName = qualifiedName;
/* 145:    */     }
/* 146:233 */     if (namespaceURI == null) {
/* 147:234 */       namespaceURI = "";
/* 148:    */     }
/* 149:237 */     Namespace namespace = null;
/* 150:238 */     String prefix = "";
/* 151:239 */     int index = qualifiedName.indexOf(":");
/* 152:241 */     if (index > 0)
/* 153:    */     {
/* 154:242 */       prefix = qualifiedName.substring(0, index);
/* 155:243 */       namespace = createNamespace(prefix, namespaceURI);
/* 156:245 */       if (localName.trim().length() == 0) {
/* 157:246 */         localName = qualifiedName.substring(index + 1);
/* 158:    */       }
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:250 */       namespace = Namespace.NO_NAMESPACE;
/* 163:252 */       if (localName.trim().length() == 0) {
/* 164:253 */         localName = qualifiedName;
/* 165:    */       }
/* 166:    */     }
/* 167:257 */     answer = pushQName(localName, qualifiedName, namespace, prefix);
/* 168:258 */     map.put(qualifiedName, answer);
/* 169:    */     
/* 170:260 */     return answer;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void push(String prefix, String uri)
/* 174:    */   {
/* 175:272 */     if (uri == null) {
/* 176:273 */       uri = "";
/* 177:    */     }
/* 178:276 */     Namespace namespace = createNamespace(prefix, uri);
/* 179:277 */     push(namespace);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Namespace addNamespace(String prefix, String uri)
/* 183:    */   {
/* 184:291 */     Namespace namespace = createNamespace(prefix, uri);
/* 185:292 */     push(namespace);
/* 186:    */     
/* 187:294 */     return namespace;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Namespace pop(String prefix)
/* 191:    */   {
/* 192:306 */     if (prefix == null) {
/* 193:307 */       prefix = "";
/* 194:    */     }
/* 195:310 */     Namespace namespace = null;
/* 196:312 */     for (int i = this.namespaceStack.size() - 1; i >= 0; i--)
/* 197:    */     {
/* 198:313 */       Namespace ns = (Namespace)this.namespaceStack.get(i);
/* 199:315 */       if (prefix.equals(ns.getPrefix()))
/* 200:    */       {
/* 201:316 */         remove(i);
/* 202:317 */         namespace = ns;
/* 203:    */         
/* 204:319 */         break;
/* 205:    */       }
/* 206:    */     }
/* 207:323 */     if (namespace == null) {
/* 208:324 */       System.out.println("Warning: missing namespace prefix ignored: " + prefix);
/* 209:    */     }
/* 210:328 */     return namespace;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String toString()
/* 214:    */   {
/* 215:332 */     return super.toString() + " Stack: " + this.namespaceStack.toString();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public DocumentFactory getDocumentFactory()
/* 219:    */   {
/* 220:336 */     return this.documentFactory;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 224:    */   {
/* 225:340 */     this.documentFactory = documentFactory;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Namespace getDefaultNamespace()
/* 229:    */   {
/* 230:344 */     if (this.defaultNamespace == null) {
/* 231:345 */       this.defaultNamespace = findDefaultNamespace();
/* 232:    */     }
/* 233:348 */     return this.defaultNamespace;
/* 234:    */   }
/* 235:    */   
/* 236:    */   protected QName pushQName(String localName, String qualifiedName, Namespace namespace, String prefix)
/* 237:    */   {
/* 238:370 */     if ((prefix == null) || (prefix.length() == 0)) {
/* 239:371 */       this.defaultNamespace = null;
/* 240:    */     }
/* 241:374 */     return createQName(localName, qualifiedName, namespace);
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected QName createQName(String localName, String qualifiedName, Namespace namespace)
/* 245:    */   {
/* 246:392 */     return this.documentFactory.createQName(localName, namespace);
/* 247:    */   }
/* 248:    */   
/* 249:    */   protected Namespace createNamespace(String prefix, String namespaceURI)
/* 250:    */   {
/* 251:407 */     return this.documentFactory.createNamespace(prefix, namespaceURI);
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected Namespace findDefaultNamespace()
/* 255:    */   {
/* 256:417 */     for (int i = this.namespaceStack.size() - 1; i >= 0; i--)
/* 257:    */     {
/* 258:418 */       Namespace namespace = (Namespace)this.namespaceStack.get(i);
/* 259:420 */       if (namespace != null)
/* 260:    */       {
/* 261:421 */         String prefix = namespace.getPrefix();
/* 262:423 */         if ((prefix == null) || (namespace.getPrefix().length() == 0)) {
/* 263:424 */           return namespace;
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:429 */     return null;
/* 268:    */   }
/* 269:    */   
/* 270:    */   protected Namespace remove(int index)
/* 271:    */   {
/* 272:441 */     Namespace namespace = (Namespace)this.namespaceStack.remove(index);
/* 273:442 */     this.namespaceCacheList.remove(index);
/* 274:443 */     this.defaultNamespace = null;
/* 275:444 */     this.currentNamespaceCache = null;
/* 276:    */     
/* 277:446 */     return namespace;
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected Map getNamespaceCache()
/* 281:    */   {
/* 282:450 */     if (this.currentNamespaceCache == null)
/* 283:    */     {
/* 284:451 */       int index = this.namespaceStack.size() - 1;
/* 285:453 */       if (index < 0)
/* 286:    */       {
/* 287:454 */         this.currentNamespaceCache = this.rootNamespaceCache;
/* 288:    */       }
/* 289:    */       else
/* 290:    */       {
/* 291:456 */         this.currentNamespaceCache = ((Map)this.namespaceCacheList.get(index));
/* 292:458 */         if (this.currentNamespaceCache == null)
/* 293:    */         {
/* 294:459 */           this.currentNamespaceCache = new HashMap();
/* 295:460 */           this.namespaceCacheList.set(index, this.currentNamespaceCache);
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:465 */     return this.currentNamespaceCache;
/* 300:    */   }
/* 301:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.NamespaceStack
 * JD-Core Version:    0.7.0.1
 */