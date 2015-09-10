/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Stack;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.xml.res.XMLMessages;
/*   8:    */ import org.w3c.dom.Element;
/*   9:    */ 
/*  10:    */ public class QName
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 467434581652829920L;
/*  14:    */   protected String _localName;
/*  15:    */   protected String _namespaceURI;
/*  16:    */   protected String _prefix;
/*  17:    */   public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
/*  18:    */   private int m_hashCode;
/*  19:    */   
/*  20:    */   public QName() {}
/*  21:    */   
/*  22:    */   public QName(String namespaceURI, String localName)
/*  23:    */   {
/*  24: 93 */     this(namespaceURI, localName, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public QName(String namespaceURI, String localName, boolean validate)
/*  28:    */   {
/*  29:110 */     if (localName == null) {
/*  30:111 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*  31:    */     }
/*  32:114 */     if (validate) {
/*  33:116 */       if (!XML11Char.isXML11ValidNCName(localName)) {
/*  34:118 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*  35:    */       }
/*  36:    */     }
/*  37:123 */     this._namespaceURI = namespaceURI;
/*  38:124 */     this._localName = localName;
/*  39:125 */     this.m_hashCode = toString().hashCode();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public QName(String namespaceURI, String prefix, String localName)
/*  43:    */   {
/*  44:139 */     this(namespaceURI, prefix, localName, false);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public QName(String namespaceURI, String prefix, String localName, boolean validate)
/*  48:    */   {
/*  49:157 */     if (localName == null) {
/*  50:158 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*  51:    */     }
/*  52:161 */     if (validate)
/*  53:    */     {
/*  54:163 */       if (!XML11Char.isXML11ValidNCName(localName)) {
/*  55:165 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*  56:    */       }
/*  57:169 */       if ((null != prefix) && (!XML11Char.isXML11ValidNCName(prefix))) {
/*  58:171 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_PREFIX_INVALID", null));
/*  59:    */       }
/*  60:    */     }
/*  61:176 */     this._namespaceURI = namespaceURI;
/*  62:177 */     this._prefix = prefix;
/*  63:178 */     this._localName = localName;
/*  64:179 */     this.m_hashCode = toString().hashCode();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public QName(String localName)
/*  68:    */   {
/*  69:191 */     this(localName, false);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public QName(String localName, boolean validate)
/*  73:    */   {
/*  74:207 */     if (localName == null) {
/*  75:208 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*  76:    */     }
/*  77:211 */     if (validate) {
/*  78:213 */       if (!XML11Char.isXML11ValidNCName(localName)) {
/*  79:215 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*  80:    */       }
/*  81:    */     }
/*  82:219 */     this._namespaceURI = null;
/*  83:220 */     this._localName = localName;
/*  84:221 */     this.m_hashCode = toString().hashCode();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public QName(String qname, Stack namespaces)
/*  88:    */   {
/*  89:234 */     this(qname, namespaces, false);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public QName(String qname, Stack namespaces, boolean validate)
/*  93:    */   {
/*  94:250 */     String namespace = null;
/*  95:251 */     String prefix = null;
/*  96:252 */     int indexOfNSSep = qname.indexOf(':');
/*  97:254 */     if (indexOfNSSep > 0)
/*  98:    */     {
/*  99:256 */       prefix = qname.substring(0, indexOfNSSep);
/* 100:258 */       if (prefix.equals("xml"))
/* 101:    */       {
/* 102:260 */         namespace = "http://www.w3.org/XML/1998/namespace";
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:263 */         if (prefix.equals("xmlns")) {
/* 107:265 */           return;
/* 108:    */         }
/* 109:269 */         int depth = namespaces.size();
/* 110:271 */         for (int i = depth - 1; i >= 0; i--)
/* 111:    */         {
/* 112:273 */           NameSpace ns = (NameSpace)namespaces.elementAt(i);
/* 113:275 */           while (null != ns)
/* 114:    */           {
/* 115:277 */             if ((null != ns.m_prefix) && (prefix.equals(ns.m_prefix)))
/* 116:    */             {
/* 117:279 */               namespace = ns.m_uri;
/* 118:280 */               i = -1;
/* 119:    */               
/* 120:282 */               break;
/* 121:    */             }
/* 122:285 */             ns = ns.m_next;
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:290 */       if (null == namespace) {
/* 127:292 */         throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
/* 128:    */       }
/* 129:    */     }
/* 130:299 */     this._localName = (indexOfNSSep < 0 ? qname : qname.substring(indexOfNSSep + 1));
/* 131:302 */     if (validate) {
/* 132:304 */       if ((this._localName == null) || (!XML11Char.isXML11ValidNCName(this._localName))) {
/* 133:306 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/* 134:    */       }
/* 135:    */     }
/* 136:310 */     this._namespaceURI = namespace;
/* 137:311 */     this._prefix = prefix;
/* 138:312 */     this.m_hashCode = toString().hashCode();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public QName(String qname, Element namespaceContext, PrefixResolver resolver)
/* 142:    */   {
/* 143:327 */     this(qname, namespaceContext, resolver, false);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public QName(String qname, Element namespaceContext, PrefixResolver resolver, boolean validate)
/* 147:    */   {
/* 148:345 */     this._namespaceURI = null;
/* 149:    */     
/* 150:347 */     int indexOfNSSep = qname.indexOf(':');
/* 151:349 */     if (indexOfNSSep > 0) {
/* 152:351 */       if (null != namespaceContext)
/* 153:    */       {
/* 154:353 */         String prefix = qname.substring(0, indexOfNSSep);
/* 155:    */         
/* 156:355 */         this._prefix = prefix;
/* 157:357 */         if (prefix.equals("xml"))
/* 158:    */         {
/* 159:359 */           this._namespaceURI = "http://www.w3.org/XML/1998/namespace";
/* 160:    */         }
/* 161:    */         else
/* 162:    */         {
/* 163:363 */           if (prefix.equals("xmlns")) {
/* 164:365 */             return;
/* 165:    */           }
/* 166:369 */           this._namespaceURI = resolver.getNamespaceForPrefix(prefix, namespaceContext);
/* 167:    */         }
/* 168:373 */         if (null == this._namespaceURI) {
/* 169:375 */           throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:388 */     this._localName = (indexOfNSSep < 0 ? qname : qname.substring(indexOfNSSep + 1));
/* 174:391 */     if (validate) {
/* 175:393 */       if ((this._localName == null) || (!XML11Char.isXML11ValidNCName(this._localName))) {
/* 176:395 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/* 177:    */       }
/* 178:    */     }
/* 179:400 */     this.m_hashCode = toString().hashCode();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public QName(String qname, PrefixResolver resolver)
/* 183:    */   {
/* 184:414 */     this(qname, resolver, false);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public QName(String qname, PrefixResolver resolver, boolean validate)
/* 188:    */   {
/* 189:430 */     String prefix = null;
/* 190:431 */     this._namespaceURI = null;
/* 191:    */     
/* 192:433 */     int indexOfNSSep = qname.indexOf(':');
/* 193:435 */     if (indexOfNSSep > 0)
/* 194:    */     {
/* 195:437 */       prefix = qname.substring(0, indexOfNSSep);
/* 196:439 */       if (prefix.equals("xml")) {
/* 197:441 */         this._namespaceURI = "http://www.w3.org/XML/1998/namespace";
/* 198:    */       } else {
/* 199:445 */         this._namespaceURI = resolver.getNamespaceForPrefix(prefix);
/* 200:    */       }
/* 201:448 */       if (null == this._namespaceURI) {
/* 202:450 */         throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
/* 203:    */       }
/* 204:455 */       this._localName = qname.substring(indexOfNSSep + 1);
/* 205:    */     }
/* 206:    */     else
/* 207:    */     {
/* 208:457 */       if (indexOfNSSep == 0) {
/* 209:459 */         throw new RuntimeException(XMLMessages.createXMLMessage("ER_NAME_CANT_START_WITH_COLON", null));
/* 210:    */       }
/* 211:466 */       this._localName = qname;
/* 212:    */     }
/* 213:469 */     if (validate) {
/* 214:471 */       if ((this._localName == null) || (!XML11Char.isXML11ValidNCName(this._localName))) {
/* 215:473 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/* 216:    */       }
/* 217:    */     }
/* 218:479 */     this.m_hashCode = toString().hashCode();
/* 219:480 */     this._prefix = prefix;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getNamespaceURI()
/* 223:    */   {
/* 224:491 */     return this._namespaceURI;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getPrefix()
/* 228:    */   {
/* 229:502 */     return this._prefix;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getLocalName()
/* 233:    */   {
/* 234:512 */     return this._localName;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String toString()
/* 238:    */   {
/* 239:525 */     return this._namespaceURI != null ? "{" + this._namespaceURI + "}" + this._localName : this._prefix != null ? this._prefix + ":" + this._localName : this._localName;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String toNamespacedString()
/* 243:    */   {
/* 244:541 */     return this._namespaceURI != null ? "{" + this._namespaceURI + "}" + this._localName : this._localName;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getNamespace()
/* 248:    */   {
/* 249:553 */     return getNamespaceURI();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getLocalPart()
/* 253:    */   {
/* 254:563 */     return getLocalName();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int hashCode()
/* 258:    */   {
/* 259:573 */     return this.m_hashCode;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean equals(String ns, String localPart)
/* 263:    */   {
/* 264:589 */     String thisnamespace = getNamespaceURI();
/* 265:591 */     if (getLocalName().equals(localPart)) {}
/* 266:591 */     return ((null == thisnamespace) && (null == ns) ? 1 : (null != thisnamespace) && (null != ns) ? thisnamespace.equals(ns) : 0) != 0;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean equals(Object object)
/* 270:    */   {
/* 271:607 */     if (object == this) {
/* 272:608 */       return true;
/* 273:    */     }
/* 274:610 */     if ((object instanceof QName))
/* 275:    */     {
/* 276:611 */       QName qname = (QName)object;
/* 277:612 */       String thisnamespace = getNamespaceURI();
/* 278:613 */       String thatnamespace = qname.getNamespaceURI();
/* 279:615 */       if (getLocalName().equals(qname.getLocalName())) {}
/* 280:615 */       return ((null == thisnamespace) && (null == thatnamespace) ? 1 : (null != thisnamespace) && (null != thatnamespace) ? thisnamespace.equals(thatnamespace) : 0) != 0;
/* 281:    */     }
/* 282:621 */     return false;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public static QName getQNameFromString(String name)
/* 286:    */   {
/* 287:635 */     StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
/* 288:    */     
/* 289:637 */     String s1 = tokenizer.nextToken();
/* 290:638 */     String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/* 291:    */     QName qname;
/* 292:640 */     if (null == s2) {
/* 293:641 */       qname = new QName(null, s1);
/* 294:    */     } else {
/* 295:643 */       qname = new QName(s1, s2);
/* 296:    */     }
/* 297:645 */     return qname;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public static boolean isXMLNSDecl(String attRawName)
/* 301:    */   {
/* 302:659 */     return (attRawName.startsWith("xmlns")) && ((attRawName.equals("xmlns")) || (attRawName.startsWith("xmlns:")));
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static String getPrefixFromXMLNSDecl(String attRawName)
/* 306:    */   {
/* 307:675 */     int index = attRawName.indexOf(':');
/* 308:    */     
/* 309:677 */     return index >= 0 ? attRawName.substring(index + 1) : "";
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static String getLocalPart(String qname)
/* 313:    */   {
/* 314:690 */     int index = qname.indexOf(':');
/* 315:    */     
/* 316:692 */     return index < 0 ? qname : qname.substring(index + 1);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public static String getPrefixPart(String qname)
/* 320:    */   {
/* 321:705 */     int index = qname.indexOf(':');
/* 322:    */     
/* 323:707 */     return index >= 0 ? qname.substring(0, index) : "";
/* 324:    */   }
/* 325:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.QName
 * JD-Core Version:    0.7.0.1
 */