/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import org.apache.xerces.util.XMLChar;
/*   4:    */ import org.apache.xerces.util.XMLStringBuffer;
/*   5:    */ import org.apache.xerces.xni.Augmentations;
/*   6:    */ import org.apache.xerces.xni.NamespaceContext;
/*   7:    */ import org.apache.xerces.xni.QName;
/*   8:    */ import org.apache.xerces.xni.XMLAttributes;
/*   9:    */ import org.apache.xerces.xni.XMLLocator;
/*  10:    */ import org.apache.xerces.xni.XMLString;
/*  11:    */ import org.apache.xerces.xni.XNIException;
/*  12:    */ import org.apache.xerces.xni.parser.XMLComponentManager;
/*  13:    */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*  14:    */ import org.cyberneko.html.HTMLAugmentations;
/*  15:    */ import org.cyberneko.html.HTMLEventInfo;
/*  16:    */ import org.cyberneko.html.HTMLEventInfo.SynthesizedItem;
/*  17:    */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*  18:    */ 
/*  19:    */ public class Purifier
/*  20:    */   extends DefaultFilter
/*  21:    */ {
/*  22:    */   public static final String SYNTHESIZED_NAMESPACE_PREFX = "http://cyberneko.org/html/ns/synthesized/";
/*  23:    */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*  24:    */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*  25: 91 */   private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/namespaces", "http://cyberneko.org/html/features/augmentations" };
/*  26: 99 */   protected static final HTMLEventInfo SYNTHESIZED_ITEM = new HTMLEventInfo.SynthesizedItem();
/*  27:    */   protected boolean fNamespaces;
/*  28:    */   protected boolean fAugmentations;
/*  29:    */   protected boolean fSeenDoctype;
/*  30:    */   protected boolean fSeenRootElement;
/*  31:    */   protected boolean fInCDATASection;
/*  32:    */   protected String fPublicId;
/*  33:    */   protected String fSystemId;
/*  34:    */   protected NamespaceContext fNamespaceContext;
/*  35:    */   protected int fSynthesizedNamespaceCount;
/*  36:144 */   private QName fQName = new QName();
/*  37:147 */   private final HTMLAugmentations fInfosetAugs = new HTMLAugmentations();
/*  38:150 */   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*  39:    */   
/*  40:    */   public void reset(XMLComponentManager manager)
/*  41:    */     throws XMLConfigurationException
/*  42:    */   {
/*  43:160 */     this.fInCDATASection = false;
/*  44:    */     
/*  45:    */ 
/*  46:163 */     this.fNamespaces = manager.getFeature("http://xml.org/sax/features/namespaces");
/*  47:164 */     this.fAugmentations = manager.getFeature("http://cyberneko.org/html/features/augmentations");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/*  51:    */     throws XNIException
/*  52:    */   {
/*  53:175 */     this.fNamespaceContext = (this.fNamespaces ? new NamespaceBinder.NamespaceSupport() : null);
/*  54:    */     
/*  55:177 */     this.fSynthesizedNamespaceCount = 0;
/*  56:178 */     handleStartDocument();
/*  57:179 */     super.startDocument(locator, encoding, augs);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/*  61:    */     throws XNIException
/*  62:    */   {
/*  63:186 */     this.fNamespaceContext = nscontext;
/*  64:187 */     this.fSynthesizedNamespaceCount = 0;
/*  65:188 */     handleStartDocument();
/*  66:189 */     super.startDocument(locator, encoding, nscontext, augs);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
/*  70:    */     throws XNIException
/*  71:    */   {
/*  72:195 */     if ((version == null) || (!version.equals("1.0"))) {
/*  73:196 */       version = "1.0";
/*  74:    */     }
/*  75:198 */     if ((encoding != null) && (encoding.length() == 0)) {
/*  76:199 */       encoding = null;
/*  77:    */     }
/*  78:201 */     if (standalone != null) {
/*  79:202 */       if ((!standalone.equalsIgnoreCase("true")) && (!standalone.equalsIgnoreCase("false"))) {
/*  80:204 */         standalone = null;
/*  81:    */       } else {
/*  82:207 */         standalone = standalone.toLowerCase();
/*  83:    */       }
/*  84:    */     }
/*  85:210 */     super.xmlDecl(version, encoding, standalone, augs);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void comment(XMLString text, Augmentations augs)
/*  89:    */     throws XNIException
/*  90:    */   {
/*  91:216 */     StringBuffer str = new StringBuffer(purifyText(text).toString());
/*  92:217 */     int length = str.length();
/*  93:218 */     for (int i = length - 1; i >= 0; i--)
/*  94:    */     {
/*  95:219 */       char c = str.charAt(i);
/*  96:220 */       if (c == '-') {
/*  97:221 */         str.insert(i + 1, ' ');
/*  98:    */       }
/*  99:    */     }
/* 100:224 */     this.fStringBuffer.length = 0;
/* 101:225 */     this.fStringBuffer.append(str.toString());
/* 102:226 */     text = this.fStringBuffer;
/* 103:227 */     super.comment(text, augs);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void processingInstruction(String target, XMLString data, Augmentations augs)
/* 107:    */     throws XNIException
/* 108:    */   {
/* 109:234 */     target = purifyName(target, true);
/* 110:235 */     data = purifyText(data);
/* 111:236 */     super.processingInstruction(target, data, augs);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void doctypeDecl(String root, String pubid, String sysid, Augmentations augs)
/* 115:    */     throws XNIException
/* 116:    */   {
/* 117:242 */     this.fSeenDoctype = true;
/* 118:    */     
/* 119:    */ 
/* 120:245 */     this.fPublicId = pubid;
/* 121:246 */     this.fSystemId = sysid;
/* 122:249 */     if ((this.fPublicId != null) && (this.fSystemId == null)) {
/* 123:250 */       this.fSystemId = "";
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void startElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 128:    */     throws XNIException
/* 129:    */   {
/* 130:259 */     handleStartElement(element, attrs);
/* 131:260 */     super.startElement(element, attrs, augs);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void emptyElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 135:    */     throws XNIException
/* 136:    */   {
/* 137:266 */     handleStartElement(element, attrs);
/* 138:267 */     super.emptyElement(element, attrs, augs);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void startCDATA(Augmentations augs)
/* 142:    */     throws XNIException
/* 143:    */   {
/* 144:272 */     this.fInCDATASection = true;
/* 145:273 */     super.startCDATA(augs);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void endCDATA(Augmentations augs)
/* 149:    */     throws XNIException
/* 150:    */   {
/* 151:278 */     this.fInCDATASection = false;
/* 152:279 */     super.endCDATA(augs);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void characters(XMLString text, Augmentations augs)
/* 156:    */     throws XNIException
/* 157:    */   {
/* 158:285 */     text = purifyText(text);
/* 159:286 */     if (this.fInCDATASection)
/* 160:    */     {
/* 161:287 */       StringBuffer str = new StringBuffer(text.toString());
/* 162:288 */       int length = str.length();
/* 163:289 */       for (int i = length - 1; i >= 0; i--)
/* 164:    */       {
/* 165:290 */         char c = str.charAt(i);
/* 166:291 */         if (c == ']') {
/* 167:292 */           str.insert(i + 1, ' ');
/* 168:    */         }
/* 169:    */       }
/* 170:295 */       this.fStringBuffer.length = 0;
/* 171:296 */       this.fStringBuffer.append(str.toString());
/* 172:297 */       text = this.fStringBuffer;
/* 173:    */     }
/* 174:299 */     super.characters(text, augs);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void endElement(QName element, Augmentations augs)
/* 178:    */     throws XNIException
/* 179:    */   {
/* 180:305 */     element = purifyQName(element);
/* 181:306 */     if ((this.fNamespaces) && 
/* 182:307 */       (element.prefix != null) && (element.uri == null)) {
/* 183:308 */       element.uri = this.fNamespaceContext.getURI(element.prefix);
/* 184:    */     }
/* 185:311 */     super.endElement(element, augs);
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected void handleStartDocument()
/* 189:    */   {
/* 190:320 */     this.fSeenDoctype = false;
/* 191:321 */     this.fSeenRootElement = false;
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected void handleStartElement(QName element, XMLAttributes attrs)
/* 195:    */   {
/* 196:328 */     element = purifyQName(element);
/* 197:329 */     int attrCount = attrs != null ? attrs.getLength() : 0;
/* 198:330 */     for (int i = attrCount - 1; i >= 0; i--)
/* 199:    */     {
/* 200:332 */       attrs.getName(i, this.fQName);
/* 201:333 */       attrs.setName(i, purifyQName(this.fQName));
/* 202:336 */       if ((this.fNamespaces) && 
/* 203:337 */         (!this.fQName.rawname.equals("xmlns")) && (!this.fQName.rawname.startsWith("xmlns:")))
/* 204:    */       {
/* 205:342 */         attrs.getName(i, this.fQName);
/* 206:343 */         if ((this.fQName.prefix != null) && (this.fQName.uri == null)) {
/* 207:344 */           synthesizeBinding(attrs, this.fQName.prefix);
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:351 */     if ((this.fNamespaces) && 
/* 212:352 */       (element.prefix != null) && (element.uri == null)) {
/* 213:353 */       synthesizeBinding(attrs, element.prefix);
/* 214:    */     }
/* 215:358 */     if ((!this.fSeenRootElement) && (this.fSeenDoctype))
/* 216:    */     {
/* 217:359 */       Augmentations augs = synthesizedAugs();
/* 218:360 */       super.doctypeDecl(element.rawname, this.fPublicId, this.fSystemId, augs);
/* 219:    */     }
/* 220:364 */     this.fSeenRootElement = true;
/* 221:    */   }
/* 222:    */   
/* 223:    */   protected void synthesizeBinding(XMLAttributes attrs, String ns)
/* 224:    */   {
/* 225:370 */     String prefix = "xmlns";
/* 226:371 */     String localpart = ns;
/* 227:372 */     String qname = prefix + ':' + localpart;
/* 228:373 */     String uri = "http://cyberneko.org/html/properties/namespaces-uri";
/* 229:374 */     String atype = "CDATA";
/* 230:375 */     String avalue = "http://cyberneko.org/html/ns/synthesized/" + this.fSynthesizedNamespaceCount++;
/* 231:    */     
/* 232:    */ 
/* 233:378 */     this.fQName.setValues(prefix, localpart, qname, uri);
/* 234:379 */     attrs.addAttribute(this.fQName, atype, avalue);
/* 235:    */     
/* 236:    */ 
/* 237:382 */     XercesBridge.getInstance().NamespaceContext_declarePrefix(this.fNamespaceContext, ns, avalue);
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected final Augmentations synthesizedAugs()
/* 241:    */   {
/* 242:388 */     HTMLAugmentations augs = null;
/* 243:389 */     if (this.fAugmentations)
/* 244:    */     {
/* 245:390 */       augs = this.fInfosetAugs;
/* 246:391 */       augs.removeAllItems();
/* 247:392 */       augs.putItem("http://cyberneko.org/html/features/augmentations", SYNTHESIZED_ITEM);
/* 248:    */     }
/* 249:394 */     return augs;
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected QName purifyQName(QName qname)
/* 253:    */   {
/* 254:403 */     qname.prefix = purifyName(qname.prefix, true);
/* 255:404 */     qname.localpart = purifyName(qname.localpart, true);
/* 256:405 */     qname.rawname = purifyName(qname.rawname, false);
/* 257:406 */     return qname;
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected String purifyName(String name, boolean localpart)
/* 261:    */   {
/* 262:411 */     if (name == null) {
/* 263:412 */       return name;
/* 264:    */     }
/* 265:414 */     StringBuffer str = new StringBuffer();
/* 266:415 */     int length = name.length();
/* 267:416 */     boolean seenColon = localpart;
/* 268:417 */     for (int i = 0; i < length; i++)
/* 269:    */     {
/* 270:418 */       char c = name.charAt(i);
/* 271:419 */       if (i == 0)
/* 272:    */       {
/* 273:420 */         if (!XMLChar.isNameStart(c)) {
/* 274:421 */           str.append("_u" + toHexString(c, 4) + "_");
/* 275:    */         } else {
/* 276:424 */           str.append(c);
/* 277:    */         }
/* 278:    */       }
/* 279:    */       else
/* 280:    */       {
/* 281:428 */         if (((this.fNamespaces) && (c == ':') && (seenColon)) || (!XMLChar.isName(c))) {
/* 282:429 */           str.append("_u" + toHexString(c, 4) + "_");
/* 283:    */         } else {
/* 284:432 */           str.append(c);
/* 285:    */         }
/* 286:434 */         seenColon = (seenColon) || (c == ':');
/* 287:    */       }
/* 288:    */     }
/* 289:437 */     return str.toString();
/* 290:    */   }
/* 291:    */   
/* 292:    */   protected XMLString purifyText(XMLString text)
/* 293:    */   {
/* 294:442 */     this.fStringBuffer.length = 0;
/* 295:443 */     for (int i = 0; i < text.length; i++)
/* 296:    */     {
/* 297:444 */       char c = text.ch[(text.offset + i)];
/* 298:445 */       if (XMLChar.isInvalid(c)) {
/* 299:446 */         this.fStringBuffer.append("\\u" + toHexString(c, 4));
/* 300:    */       } else {
/* 301:449 */         this.fStringBuffer.append(c);
/* 302:    */       }
/* 303:    */     }
/* 304:452 */     return this.fStringBuffer;
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected static String toHexString(int c, int padlen)
/* 308:    */   {
/* 309:461 */     StringBuffer str = new StringBuffer(padlen);
/* 310:462 */     str.append(Integer.toHexString(c));
/* 311:463 */     int len = padlen - str.length();
/* 312:464 */     for (int i = 0; i < len; i++) {
/* 313:465 */       str.insert(0, '0');
/* 314:    */     }
/* 315:467 */     return str.toString().toUpperCase();
/* 316:    */   }
/* 317:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.Purifier
 * JD-Core Version:    0.7.0.1
 */