/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import org.apache.xml.utils.QName;
/*   6:    */ 
/*   7:    */ public class XSLTElementDef
/*   8:    */ {
/*   9:    */   static final int T_ELEMENT = 1;
/*  10:    */   static final int T_PCDATA = 2;
/*  11:    */   static final int T_ANY = 3;
/*  12:    */   
/*  13:    */   XSLTElementDef() {}
/*  14:    */   
/*  15:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject)
/*  16:    */   {
/*  17: 59 */     build(namespace, name, nameAlias, elements, attributes, contentHandler, classObject);
/*  18: 61 */     if ((null != namespace) && ((namespace.equals("http://www.w3.org/1999/XSL/Transform")) || (namespace.equals("http://xml.apache.org/xalan")) || (namespace.equals("http://xml.apache.org/xslt"))))
/*  19:    */     {
/*  20: 66 */       schema.addAvailableElement(new QName(namespace, name));
/*  21: 67 */       if (null != nameAlias) {
/*  22: 68 */         schema.addAvailableElement(new QName(namespace, nameAlias));
/*  23:    */       }
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, boolean has_required)
/*  28:    */   {
/*  29: 88 */     this.m_has_required = has_required;
/*  30: 89 */     build(namespace, name, nameAlias, elements, attributes, contentHandler, classObject);
/*  31: 91 */     if ((null != namespace) && ((namespace.equals("http://www.w3.org/1999/XSL/Transform")) || (namespace.equals("http://xml.apache.org/xalan")) || (namespace.equals("http://xml.apache.org/xslt"))))
/*  32:    */     {
/*  33: 96 */       schema.addAvailableElement(new QName(namespace, name));
/*  34: 97 */       if (null != nameAlias) {
/*  35: 98 */         schema.addAvailableElement(new QName(namespace, nameAlias));
/*  36:    */       }
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, boolean has_required, boolean required)
/*  41:    */   {
/*  42:121 */     this(schema, namespace, name, nameAlias, elements, attributes, contentHandler, classObject, has_required);
/*  43:    */     
/*  44:    */ 
/*  45:124 */     this.m_required = required;
/*  46:    */   }
/*  47:    */   
/*  48:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, boolean has_required, boolean required, int order, boolean multiAllowed)
/*  49:    */   {
/*  50:148 */     this(schema, namespace, name, nameAlias, elements, attributes, contentHandler, classObject, has_required, required);
/*  51:    */     
/*  52:    */ 
/*  53:151 */     this.m_order = order;
/*  54:152 */     this.m_multiAllowed = multiAllowed;
/*  55:    */   }
/*  56:    */   
/*  57:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, boolean has_required, boolean required, boolean has_order, int order, boolean multiAllowed)
/*  58:    */   {
/*  59:177 */     this(schema, namespace, name, nameAlias, elements, attributes, contentHandler, classObject, has_required, required);
/*  60:    */     
/*  61:    */ 
/*  62:180 */     this.m_order = order;
/*  63:181 */     this.m_multiAllowed = multiAllowed;
/*  64:182 */     this.m_isOrdered = has_order;
/*  65:    */   }
/*  66:    */   
/*  67:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, boolean has_order, int order, boolean multiAllowed)
/*  68:    */   {
/*  69:204 */     this(schema, namespace, name, nameAlias, elements, attributes, contentHandler, classObject, order, multiAllowed);
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:208 */     this.m_isOrdered = has_order;
/*  74:    */   }
/*  75:    */   
/*  76:    */   XSLTElementDef(XSLTSchema schema, String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject, int order, boolean multiAllowed)
/*  77:    */   {
/*  78:229 */     this(schema, namespace, name, nameAlias, elements, attributes, contentHandler, classObject);
/*  79:    */     
/*  80:231 */     this.m_order = order;
/*  81:232 */     this.m_multiAllowed = multiAllowed;
/*  82:    */   }
/*  83:    */   
/*  84:    */   XSLTElementDef(Class classObject, XSLTElementProcessor contentHandler, int type)
/*  85:    */   {
/*  86:246 */     this.m_classObject = classObject;
/*  87:247 */     this.m_type = type;
/*  88:    */     
/*  89:249 */     setElementProcessor(contentHandler);
/*  90:    */   }
/*  91:    */   
/*  92:    */   void build(String namespace, String name, String nameAlias, XSLTElementDef[] elements, XSLTAttributeDef[] attributes, XSLTElementProcessor contentHandler, Class classObject)
/*  93:    */   {
/*  94:268 */     this.m_namespace = namespace;
/*  95:269 */     this.m_name = name;
/*  96:270 */     this.m_nameAlias = nameAlias;
/*  97:271 */     this.m_elements = elements;
/*  98:272 */     this.m_attributes = attributes;
/*  99:    */     
/* 100:274 */     setElementProcessor(contentHandler);
/* 101:    */     
/* 102:276 */     this.m_classObject = classObject;
/* 103:278 */     if ((hasRequired()) && (this.m_elements != null))
/* 104:    */     {
/* 105:280 */       int n = this.m_elements.length;
/* 106:281 */       for (int i = 0; i < n; i++)
/* 107:    */       {
/* 108:283 */         XSLTElementDef def = this.m_elements[i];
/* 109:285 */         if ((def != null) && (def.getRequired()))
/* 110:    */         {
/* 111:287 */           if (this.m_requiredFound == null) {
/* 112:288 */             this.m_requiredFound = new Hashtable();
/* 113:    */           }
/* 114:289 */           this.m_requiredFound.put(def.getName(), "xsl:" + def.getName());
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static boolean equalsMayBeNull(Object obj1, Object obj2)
/* 121:    */   {
/* 122:307 */     return (obj2 == obj1) || ((null != obj1) && (null != obj2) && (obj2.equals(obj1)));
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static boolean equalsMayBeNullOrZeroLen(String s1, String s2)
/* 126:    */   {
/* 127:329 */     int len1 = s1 == null ? 0 : s1.length();
/* 128:330 */     int len2 = s2 == null ? 0 : s2.length();
/* 129:    */     
/* 130:332 */     return len1 == 0 ? true : len1 != len2 ? false : s1.equals(s2);
/* 131:    */   }
/* 132:    */   
/* 133:343 */   private int m_type = 1;
/* 134:    */   private String m_namespace;
/* 135:    */   private String m_name;
/* 136:    */   private String m_nameAlias;
/* 137:    */   private XSLTElementDef[] m_elements;
/* 138:    */   private XSLTAttributeDef[] m_attributes;
/* 139:    */   private XSLTElementProcessor m_elementProcessor;
/* 140:    */   private Class m_classObject;
/* 141:    */   
/* 142:    */   int getType()
/* 143:    */   {
/* 144:352 */     return this.m_type;
/* 145:    */   }
/* 146:    */   
/* 147:    */   void setType(int t)
/* 148:    */   {
/* 149:362 */     this.m_type = t;
/* 150:    */   }
/* 151:    */   
/* 152:    */   String getNamespace()
/* 153:    */   {
/* 154:377 */     return this.m_namespace;
/* 155:    */   }
/* 156:    */   
/* 157:    */   String getName()
/* 158:    */   {
/* 159:392 */     return this.m_name;
/* 160:    */   }
/* 161:    */   
/* 162:    */   String getNameAlias()
/* 163:    */   {
/* 164:407 */     return this.m_nameAlias;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public XSLTElementDef[] getElements()
/* 168:    */   {
/* 169:423 */     return this.m_elements;
/* 170:    */   }
/* 171:    */   
/* 172:    */   void setElements(XSLTElementDef[] defs)
/* 173:    */   {
/* 174:433 */     this.m_elements = defs;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private boolean QNameEquals(String uri, String localName)
/* 178:    */   {
/* 179:448 */     return (equalsMayBeNullOrZeroLen(this.m_namespace, uri)) && ((equalsMayBeNullOrZeroLen(this.m_name, localName)) || (equalsMayBeNullOrZeroLen(this.m_nameAlias, localName)));
/* 180:    */   }
/* 181:    */   
/* 182:    */   XSLTElementProcessor getProcessorFor(String uri, String localName)
/* 183:    */   {
/* 184:465 */     XSLTElementProcessor elemDef = null;
/* 185:467 */     if (null == this.m_elements) {
/* 186:468 */       return null;
/* 187:    */     }
/* 188:470 */     int n = this.m_elements.length;
/* 189:471 */     int order = -1;
/* 190:472 */     boolean multiAllowed = true;
/* 191:473 */     for (int i = 0; i < n; i++)
/* 192:    */     {
/* 193:475 */       XSLTElementDef def = this.m_elements[i];
/* 194:480 */       if (def.m_name.equals("*"))
/* 195:    */       {
/* 196:484 */         if (!equalsMayBeNullOrZeroLen(uri, "http://www.w3.org/1999/XSL/Transform"))
/* 197:    */         {
/* 198:486 */           elemDef = def.m_elementProcessor;
/* 199:487 */           order = def.getOrder();
/* 200:488 */           multiAllowed = def.getMultiAllowed();
/* 201:    */         }
/* 202:    */       }
/* 203:491 */       else if (def.QNameEquals(uri, localName))
/* 204:    */       {
/* 205:493 */         if (def.getRequired()) {
/* 206:494 */           setRequiredFound(def.getName(), true);
/* 207:    */         }
/* 208:495 */         order = def.getOrder();
/* 209:496 */         multiAllowed = def.getMultiAllowed();
/* 210:497 */         elemDef = def.m_elementProcessor;
/* 211:498 */         break;
/* 212:    */       }
/* 213:    */     }
/* 214:502 */     if ((elemDef != null) && (isOrdered()))
/* 215:    */     {
/* 216:504 */       int lastOrder = getLastOrder();
/* 217:505 */       if (order > lastOrder)
/* 218:    */       {
/* 219:506 */         setLastOrder(order);
/* 220:    */       }
/* 221:    */       else
/* 222:    */       {
/* 223:507 */         if ((order == lastOrder) && (!multiAllowed)) {
/* 224:509 */           return null;
/* 225:    */         }
/* 226:511 */         if ((order < lastOrder) && (order > 0)) {
/* 227:513 */           return null;
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:517 */     return elemDef;
/* 232:    */   }
/* 233:    */   
/* 234:    */   XSLTElementProcessor getProcessorForUnknown(String uri, String localName)
/* 235:    */   {
/* 236:534 */     if (null == this.m_elements) {
/* 237:535 */       return null;
/* 238:    */     }
/* 239:537 */     int n = this.m_elements.length;
/* 240:539 */     for (int i = 0; i < n; i++)
/* 241:    */     {
/* 242:541 */       XSLTElementDef def = this.m_elements[i];
/* 243:543 */       if ((def.m_name.equals("unknown")) && (uri.length() > 0)) {
/* 244:545 */         return def.m_elementProcessor;
/* 245:    */       }
/* 246:    */     }
/* 247:549 */     return null;
/* 248:    */   }
/* 249:    */   
/* 250:    */   XSLTAttributeDef[] getAttributes()
/* 251:    */   {
/* 252:564 */     return this.m_attributes;
/* 253:    */   }
/* 254:    */   
/* 255:    */   XSLTAttributeDef getAttributeDef(String uri, String localName)
/* 256:    */   {
/* 257:579 */     XSLTAttributeDef defaultDef = null;
/* 258:580 */     XSLTAttributeDef[] attrDefs = getAttributes();
/* 259:581 */     int nAttrDefs = attrDefs.length;
/* 260:583 */     for (int k = 0; k < nAttrDefs; k++)
/* 261:    */     {
/* 262:585 */       XSLTAttributeDef attrDef = attrDefs[k];
/* 263:586 */       String uriDef = attrDef.getNamespace();
/* 264:587 */       String nameDef = attrDef.getName();
/* 265:589 */       if ((nameDef.equals("*")) && ((equalsMayBeNullOrZeroLen(uri, uriDef)) || ((uriDef != null) && (uriDef.equals("*")) && (uri != null) && (uri.length() > 0)))) {
/* 266:592 */         return attrDef;
/* 267:    */       }
/* 268:594 */       if ((nameDef.equals("*")) && (uriDef == null)) {
/* 269:599 */         defaultDef = attrDef;
/* 270:601 */       } else if ((equalsMayBeNullOrZeroLen(uri, uriDef)) && (localName.equals(nameDef))) {
/* 271:604 */         return attrDef;
/* 272:    */       }
/* 273:    */     }
/* 274:608 */     if (null == defaultDef) {
/* 275:610 */       if ((uri.length() > 0) && (!equalsMayBeNullOrZeroLen(uri, "http://www.w3.org/1999/XSL/Transform"))) {
/* 276:612 */         return XSLTAttributeDef.m_foreignAttr;
/* 277:    */       }
/* 278:    */     }
/* 279:616 */     return defaultDef;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public XSLTElementProcessor getElementProcessor()
/* 283:    */   {
/* 284:632 */     return this.m_elementProcessor;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setElementProcessor(XSLTElementProcessor handler)
/* 288:    */   {
/* 289:644 */     if (handler != null)
/* 290:    */     {
/* 291:646 */       this.m_elementProcessor = handler;
/* 292:    */       
/* 293:648 */       this.m_elementProcessor.setElemDef(this);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   Class getClassObject()
/* 298:    */   {
/* 299:666 */     return this.m_classObject;
/* 300:    */   }
/* 301:    */   
/* 302:672 */   private boolean m_has_required = false;
/* 303:    */   
/* 304:    */   boolean hasRequired()
/* 305:    */   {
/* 306:681 */     return this.m_has_required;
/* 307:    */   }
/* 308:    */   
/* 309:687 */   private boolean m_required = false;
/* 310:    */   Hashtable m_requiredFound;
/* 311:    */   
/* 312:    */   boolean getRequired()
/* 313:    */   {
/* 314:696 */     return this.m_required;
/* 315:    */   }
/* 316:    */   
/* 317:    */   void setRequiredFound(String elem, boolean found)
/* 318:    */   {
/* 319:707 */     if (this.m_requiredFound.get(elem) != null) {
/* 320:708 */       this.m_requiredFound.remove(elem);
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   boolean getRequiredFound()
/* 325:    */   {
/* 326:718 */     if (this.m_requiredFound == null) {
/* 327:719 */       return true;
/* 328:    */     }
/* 329:720 */     return this.m_requiredFound.isEmpty();
/* 330:    */   }
/* 331:    */   
/* 332:    */   String getRequiredElem()
/* 333:    */   {
/* 334:730 */     if (this.m_requiredFound == null) {
/* 335:731 */       return null;
/* 336:    */     }
/* 337:732 */     Enumeration elems = this.m_requiredFound.elements();
/* 338:733 */     String s = "";
/* 339:734 */     boolean first = true;
/* 340:735 */     while (elems.hasMoreElements())
/* 341:    */     {
/* 342:737 */       if (first) {
/* 343:738 */         first = false;
/* 344:    */       } else {
/* 345:740 */         s = s + ", ";
/* 346:    */       }
/* 347:741 */       s = s + (String)elems.nextElement();
/* 348:    */     }
/* 349:743 */     return s;
/* 350:    */   }
/* 351:    */   
/* 352:746 */   boolean m_isOrdered = false;
/* 353:    */   
/* 354:    */   boolean isOrdered()
/* 355:    */   {
/* 356:775 */     return this.m_isOrdered;
/* 357:    */   }
/* 358:    */   
/* 359:781 */   private int m_order = -1;
/* 360:    */   
/* 361:    */   int getOrder()
/* 362:    */   {
/* 363:790 */     return this.m_order;
/* 364:    */   }
/* 365:    */   
/* 366:797 */   private int m_lastOrder = -1;
/* 367:    */   
/* 368:    */   int getLastOrder()
/* 369:    */   {
/* 370:806 */     return this.m_lastOrder;
/* 371:    */   }
/* 372:    */   
/* 373:    */   void setLastOrder(int order)
/* 374:    */   {
/* 375:816 */     this.m_lastOrder = order;
/* 376:    */   }
/* 377:    */   
/* 378:822 */   private boolean m_multiAllowed = true;
/* 379:    */   
/* 380:    */   boolean getMultiAllowed()
/* 381:    */   {
/* 382:831 */     return this.m_multiAllowed;
/* 383:    */   }
/* 384:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.XSLTElementDef
 * JD-Core Version:    0.7.0.1
 */