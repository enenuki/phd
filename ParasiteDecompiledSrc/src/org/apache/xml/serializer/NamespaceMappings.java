/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import org.xml.sax.ContentHandler;
/*   7:    */ import org.xml.sax.SAXException;
/*   8:    */ 
/*   9:    */ public class NamespaceMappings
/*  10:    */ {
/*  11: 70 */   private int count = 0;
/*  12: 80 */   private Hashtable m_namespaces = new Hashtable();
/*  13: 95 */   private Stack m_nodeStack = new Stack();
/*  14:    */   private static final String EMPTYSTRING = "";
/*  15:    */   private static final String XML_PREFIX = "xml";
/*  16:    */   
/*  17:    */   public NamespaceMappings()
/*  18:    */   {
/*  19:106 */     initNamespaces();
/*  20:    */   }
/*  21:    */   
/*  22:    */   private void initNamespaces()
/*  23:    */   {
/*  24:121 */     MappingRecord nn = new MappingRecord("", "", -1);
/*  25:122 */     Stack stack = createPrefixStack("");
/*  26:123 */     stack.push(nn);
/*  27:    */     
/*  28:    */ 
/*  29:126 */     nn = new MappingRecord("xml", "http://www.w3.org/XML/1998/namespace", -1);
/*  30:127 */     stack = createPrefixStack("xml");
/*  31:128 */     stack.push(nn);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String lookupNamespace(String prefix)
/*  35:    */   {
/*  36:140 */     String uri = null;
/*  37:141 */     Stack stack = getPrefixStack(prefix);
/*  38:142 */     if ((stack != null) && (!stack.isEmpty())) {
/*  39:143 */       uri = ((MappingRecord)stack.peek()).m_uri;
/*  40:    */     }
/*  41:145 */     if (uri == null) {
/*  42:146 */       uri = "";
/*  43:    */     }
/*  44:147 */     return uri;
/*  45:    */   }
/*  46:    */   
/*  47:    */   MappingRecord getMappingFromPrefix(String prefix)
/*  48:    */   {
/*  49:152 */     Stack stack = (Stack)this.m_namespaces.get(prefix);
/*  50:153 */     return (stack != null) && (!stack.isEmpty()) ? (MappingRecord)stack.peek() : null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String lookupPrefix(String uri)
/*  54:    */   {
/*  55:167 */     String foundPrefix = null;
/*  56:168 */     Enumeration prefixes = this.m_namespaces.keys();
/*  57:169 */     while (prefixes.hasMoreElements())
/*  58:    */     {
/*  59:171 */       String prefix = (String)prefixes.nextElement();
/*  60:172 */       String uri2 = lookupNamespace(prefix);
/*  61:173 */       if ((uri2 != null) && (uri2.equals(uri)))
/*  62:    */       {
/*  63:175 */         foundPrefix = prefix;
/*  64:176 */         break;
/*  65:    */       }
/*  66:    */     }
/*  67:179 */     return foundPrefix;
/*  68:    */   }
/*  69:    */   
/*  70:    */   MappingRecord getMappingFromURI(String uri)
/*  71:    */   {
/*  72:184 */     MappingRecord foundMap = null;
/*  73:185 */     Enumeration prefixes = this.m_namespaces.keys();
/*  74:186 */     while (prefixes.hasMoreElements())
/*  75:    */     {
/*  76:188 */       String prefix = (String)prefixes.nextElement();
/*  77:189 */       MappingRecord map2 = getMappingFromPrefix(prefix);
/*  78:190 */       if ((map2 != null) && (map2.m_uri.equals(uri)))
/*  79:    */       {
/*  80:192 */         foundMap = map2;
/*  81:193 */         break;
/*  82:    */       }
/*  83:    */     }
/*  84:196 */     return foundMap;
/*  85:    */   }
/*  86:    */   
/*  87:    */   boolean popNamespace(String prefix)
/*  88:    */   {
/*  89:205 */     if (prefix.startsWith("xml")) {
/*  90:207 */       return false;
/*  91:    */     }
/*  92:    */     Stack stack;
/*  93:211 */     if ((stack = getPrefixStack(prefix)) != null)
/*  94:    */     {
/*  95:213 */       stack.pop();
/*  96:214 */       return true;
/*  97:    */     }
/*  98:216 */     return false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean pushNamespace(String prefix, String uri, int elemDepth)
/* 102:    */   {
/* 103:228 */     if (prefix.startsWith("xml")) {
/* 104:230 */       return false;
/* 105:    */     }
/* 106:    */     Stack stack;
/* 107:235 */     if ((stack = (Stack)this.m_namespaces.get(prefix)) == null) {
/* 108:237 */       this.m_namespaces.put(prefix, stack = new Stack());
/* 109:    */     }
/* 110:240 */     if (!stack.empty())
/* 111:    */     {
/* 112:242 */       MappingRecord mr = (MappingRecord)stack.peek();
/* 113:243 */       if ((uri.equals(mr.m_uri)) || (elemDepth == mr.m_declarationDepth)) {
/* 114:248 */         return false;
/* 115:    */       }
/* 116:    */     }
/* 117:251 */     MappingRecord map = new MappingRecord(prefix, uri, elemDepth);
/* 118:252 */     stack.push(map);
/* 119:253 */     this.m_nodeStack.push(map);
/* 120:254 */     return true;
/* 121:    */   }
/* 122:    */   
/* 123:    */   void popNamespaces(int elemDepth, ContentHandler saxHandler)
/* 124:    */   {
/* 125:    */     for (;;)
/* 126:    */     {
/* 127:269 */       if (this.m_nodeStack.isEmpty()) {
/* 128:270 */         return;
/* 129:    */       }
/* 130:271 */       MappingRecord map = (MappingRecord)this.m_nodeStack.peek();
/* 131:272 */       int depth = map.m_declarationDepth;
/* 132:273 */       if ((elemDepth < 1) || (map.m_declarationDepth < elemDepth)) {
/* 133:    */         break;
/* 134:    */       }
/* 135:279 */       MappingRecord nm1 = (MappingRecord)this.m_nodeStack.pop();
/* 136:    */       
/* 137:281 */       String prefix = map.m_prefix;
/* 138:    */       
/* 139:283 */       Stack prefixStack = getPrefixStack(prefix);
/* 140:284 */       MappingRecord nm2 = (MappingRecord)prefixStack.peek();
/* 141:285 */       if (nm1 == nm2)
/* 142:    */       {
/* 143:300 */         prefixStack.pop();
/* 144:301 */         if (saxHandler != null) {
/* 145:    */           try
/* 146:    */           {
/* 147:305 */             saxHandler.endPrefixMapping(prefix);
/* 148:    */           }
/* 149:    */           catch (SAXException e) {}
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String generateNextPrefix()
/* 156:    */   {
/* 157:323 */     return "ns" + this.count++;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Object clone()
/* 161:    */     throws CloneNotSupportedException
/* 162:    */   {
/* 163:332 */     NamespaceMappings clone = new NamespaceMappings();
/* 164:333 */     clone.m_nodeStack = ((Stack)this.m_nodeStack.clone());
/* 165:334 */     clone.count = this.count;
/* 166:335 */     clone.m_namespaces = ((Hashtable)this.m_namespaces.clone());
/* 167:    */     
/* 168:337 */     clone.count = this.count;
/* 169:338 */     return clone;
/* 170:    */   }
/* 171:    */   
/* 172:    */   final void reset()
/* 173:    */   {
/* 174:344 */     this.count = 0;
/* 175:345 */     this.m_namespaces.clear();
/* 176:346 */     this.m_nodeStack.clear();
/* 177:    */     
/* 178:348 */     initNamespaces();
/* 179:    */   }
/* 180:    */   
/* 181:    */   class MappingRecord
/* 182:    */   {
/* 183:    */     final String m_prefix;
/* 184:    */     final String m_uri;
/* 185:    */     final int m_declarationDepth;
/* 186:    */     
/* 187:    */     MappingRecord(String prefix, String uri, int depth)
/* 188:    */     {
/* 189:363 */       this.m_prefix = prefix;
/* 190:364 */       this.m_uri = (uri == null ? "" : uri);
/* 191:365 */       this.m_declarationDepth = depth;
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private class Stack
/* 196:    */   {
/* 197:375 */     private int top = -1;
/* 198:376 */     private int max = 20;
/* 199:377 */     Object[] m_stack = new Object[this.max];
/* 200:    */     
/* 201:    */     public Object clone()
/* 202:    */       throws CloneNotSupportedException
/* 203:    */     {
/* 204:380 */       Stack clone = new Stack(NamespaceMappings.this);
/* 205:381 */       clone.max = this.max;
/* 206:382 */       clone.top = this.top;
/* 207:383 */       clone.m_stack = new Object[clone.max];
/* 208:384 */       for (int i = 0; i <= this.top; i++) {
/* 209:387 */         clone.m_stack[i] = this.m_stack[i];
/* 210:    */       }
/* 211:389 */       return clone;
/* 212:    */     }
/* 213:    */     
/* 214:    */     public Stack() {}
/* 215:    */     
/* 216:    */     public Object push(Object o)
/* 217:    */     {
/* 218:397 */       this.top += 1;
/* 219:398 */       if (this.max <= this.top)
/* 220:    */       {
/* 221:399 */         int newMax = 2 * this.max + 1;
/* 222:400 */         Object[] newArray = new Object[newMax];
/* 223:401 */         System.arraycopy(this.m_stack, 0, newArray, 0, this.max);
/* 224:402 */         this.max = newMax;
/* 225:403 */         this.m_stack = newArray;
/* 226:    */       }
/* 227:405 */       this.m_stack[this.top] = o;
/* 228:406 */       return o;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public Object pop()
/* 232:    */     {
/* 233:    */       Object o;
/* 234:411 */       if (0 <= this.top)
/* 235:    */       {
/* 236:412 */         o = this.m_stack[this.top];
/* 237:    */         
/* 238:414 */         this.top -= 1;
/* 239:    */       }
/* 240:    */       else
/* 241:    */       {
/* 242:417 */         o = null;
/* 243:    */       }
/* 244:418 */       return o;
/* 245:    */     }
/* 246:    */     
/* 247:    */     public Object peek()
/* 248:    */     {
/* 249:    */       Object o;
/* 250:423 */       if (0 <= this.top) {
/* 251:424 */         o = this.m_stack[this.top];
/* 252:    */       } else {
/* 253:427 */         o = null;
/* 254:    */       }
/* 255:428 */       return o;
/* 256:    */     }
/* 257:    */     
/* 258:    */     public Object peek(int idx)
/* 259:    */     {
/* 260:432 */       return this.m_stack[idx];
/* 261:    */     }
/* 262:    */     
/* 263:    */     public boolean isEmpty()
/* 264:    */     {
/* 265:436 */       return this.top < 0;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public boolean empty()
/* 269:    */     {
/* 270:439 */       return this.top < 0;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public void clear()
/* 274:    */     {
/* 275:443 */       for (int i = 0; i <= this.top; i++) {
/* 276:444 */         this.m_stack[i] = null;
/* 277:    */       }
/* 278:445 */       this.top = -1;
/* 279:    */     }
/* 280:    */     
/* 281:    */     public Object getElement(int index)
/* 282:    */     {
/* 283:449 */       return this.m_stack[index];
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   private Stack getPrefixStack(String prefix)
/* 288:    */   {
/* 289:459 */     Stack fs = (Stack)this.m_namespaces.get(prefix);
/* 290:460 */     return fs;
/* 291:    */   }
/* 292:    */   
/* 293:    */   private Stack createPrefixStack(String prefix)
/* 294:    */   {
/* 295:469 */     Stack fs = new Stack();
/* 296:470 */     this.m_namespaces.put(prefix, fs);
/* 297:471 */     return fs;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public String[] lookupAllPrefixes(String uri)
/* 301:    */   {
/* 302:485 */     ArrayList foundPrefixes = new ArrayList();
/* 303:486 */     Enumeration prefixes = this.m_namespaces.keys();
/* 304:487 */     while (prefixes.hasMoreElements())
/* 305:    */     {
/* 306:489 */       String prefix = (String)prefixes.nextElement();
/* 307:490 */       String uri2 = lookupNamespace(prefix);
/* 308:491 */       if ((uri2 != null) && (uri2.equals(uri))) {
/* 309:493 */         foundPrefixes.add(prefix);
/* 310:    */       }
/* 311:    */     }
/* 312:496 */     String[] prefixArray = new String[foundPrefixes.size()];
/* 313:497 */     foundPrefixes.toArray(prefixArray);
/* 314:498 */     return prefixArray;
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.NamespaceMappings
 * JD-Core Version:    0.7.0.1
 */