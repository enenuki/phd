/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import org.apache.xerces.xni.Augmentations;
/*   4:    */ import org.apache.xerces.xni.NamespaceContext;
/*   5:    */ import org.apache.xerces.xni.QName;
/*   6:    */ import org.apache.xerces.xni.XMLAttributes;
/*   7:    */ import org.apache.xerces.xni.XMLDocumentHandler;
/*   8:    */ import org.apache.xerces.xni.XMLLocator;
/*   9:    */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*  10:    */ import org.apache.xerces.xni.XMLString;
/*  11:    */ import org.apache.xerces.xni.XNIException;
/*  12:    */ import org.apache.xerces.xni.parser.XMLComponentManager;
/*  13:    */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*  14:    */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*  15:    */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/*  16:    */ import org.cyberneko.html.HTMLComponent;
/*  17:    */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*  18:    */ 
/*  19:    */ public class DefaultFilter
/*  20:    */   implements XMLDocumentFilter, HTMLComponent
/*  21:    */ {
/*  22:    */   protected XMLDocumentHandler fDocumentHandler;
/*  23:    */   protected XMLDocumentSource fDocumentSource;
/*  24:    */   
/*  25:    */   public void setDocumentHandler(XMLDocumentHandler handler)
/*  26:    */   {
/*  27: 63 */     this.fDocumentHandler = handler;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public XMLDocumentHandler getDocumentHandler()
/*  31:    */   {
/*  32: 70 */     return this.fDocumentHandler;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setDocumentSource(XMLDocumentSource source)
/*  36:    */   {
/*  37: 75 */     this.fDocumentSource = source;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public XMLDocumentSource getDocumentSource()
/*  41:    */   {
/*  42: 80 */     return this.fDocumentSource;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/*  46:    */     throws XNIException
/*  47:    */   {
/*  48: 93 */     if (this.fDocumentHandler != null) {
/*  49: 94 */       XercesBridge.getInstance().XMLDocumentHandler_startDocument(this.fDocumentHandler, locator, encoding, nscontext, augs);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
/*  54:    */     throws XNIException
/*  55:    */   {
/*  56:103 */     if (this.fDocumentHandler != null) {
/*  57:104 */       this.fDocumentHandler.xmlDecl(version, encoding, standalone, augs);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void doctypeDecl(String root, String publicId, String systemId, Augmentations augs)
/*  62:    */     throws XNIException
/*  63:    */   {
/*  64:111 */     if (this.fDocumentHandler != null) {
/*  65:112 */       this.fDocumentHandler.doctypeDecl(root, publicId, systemId, augs);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void comment(XMLString text, Augmentations augs)
/*  70:    */     throws XNIException
/*  71:    */   {
/*  72:119 */     if (this.fDocumentHandler != null) {
/*  73:120 */       this.fDocumentHandler.comment(text, augs);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void processingInstruction(String target, XMLString data, Augmentations augs)
/*  78:    */     throws XNIException
/*  79:    */   {
/*  80:127 */     if (this.fDocumentHandler != null) {
/*  81:128 */       this.fDocumentHandler.processingInstruction(target, data, augs);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  86:    */     throws XNIException
/*  87:    */   {
/*  88:135 */     if (this.fDocumentHandler != null) {
/*  89:136 */       this.fDocumentHandler.startElement(element, attributes, augs);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  94:    */     throws XNIException
/*  95:    */   {
/*  96:143 */     if (this.fDocumentHandler != null) {
/*  97:144 */       this.fDocumentHandler.emptyElement(element, attributes, augs);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void characters(XMLString text, Augmentations augs)
/* 102:    */     throws XNIException
/* 103:    */   {
/* 104:151 */     if (this.fDocumentHandler != null) {
/* 105:152 */       this.fDocumentHandler.characters(text, augs);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void ignorableWhitespace(XMLString text, Augmentations augs)
/* 110:    */     throws XNIException
/* 111:    */   {
/* 112:159 */     if (this.fDocumentHandler != null) {
/* 113:160 */       this.fDocumentHandler.ignorableWhitespace(text, augs);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
/* 118:    */     throws XNIException
/* 119:    */   {
/* 120:167 */     if (this.fDocumentHandler != null) {
/* 121:168 */       this.fDocumentHandler.startGeneralEntity(name, id, encoding, augs);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void textDecl(String version, String encoding, Augmentations augs)
/* 126:    */     throws XNIException
/* 127:    */   {
/* 128:175 */     if (this.fDocumentHandler != null) {
/* 129:176 */       this.fDocumentHandler.textDecl(version, encoding, augs);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void endGeneralEntity(String name, Augmentations augs)
/* 134:    */     throws XNIException
/* 135:    */   {
/* 136:183 */     if (this.fDocumentHandler != null) {
/* 137:184 */       this.fDocumentHandler.endGeneralEntity(name, augs);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void startCDATA(Augmentations augs)
/* 142:    */     throws XNIException
/* 143:    */   {
/* 144:190 */     if (this.fDocumentHandler != null) {
/* 145:191 */       this.fDocumentHandler.startCDATA(augs);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void endCDATA(Augmentations augs)
/* 150:    */     throws XNIException
/* 151:    */   {
/* 152:197 */     if (this.fDocumentHandler != null) {
/* 153:198 */       this.fDocumentHandler.endCDATA(augs);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void endElement(QName element, Augmentations augs)
/* 158:    */     throws XNIException
/* 159:    */   {
/* 160:205 */     if (this.fDocumentHandler != null) {
/* 161:206 */       this.fDocumentHandler.endElement(element, augs);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void endDocument(Augmentations augs)
/* 166:    */     throws XNIException
/* 167:    */   {
/* 168:212 */     if (this.fDocumentHandler != null) {
/* 169:213 */       this.fDocumentHandler.endDocument(augs);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/* 174:    */     throws XNIException
/* 175:    */   {
/* 176:222 */     startDocument(locator, encoding, null, augs);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void startPrefixMapping(String prefix, String uri, Augmentations augs)
/* 180:    */     throws XNIException
/* 181:    */   {
/* 182:228 */     if (this.fDocumentHandler != null) {
/* 183:229 */       XercesBridge.getInstance().XMLDocumentHandler_startPrefixMapping(this.fDocumentHandler, prefix, uri, augs);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void endPrefixMapping(String prefix, Augmentations augs)
/* 188:    */     throws XNIException
/* 189:    */   {
/* 190:236 */     if (this.fDocumentHandler != null) {
/* 191:237 */       XercesBridge.getInstance().XMLDocumentHandler_endPrefixMapping(this.fDocumentHandler, prefix, augs);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String[] getRecognizedFeatures()
/* 196:    */   {
/* 197:251 */     return null;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Boolean getFeatureDefault(String featureId)
/* 201:    */   {
/* 202:260 */     return null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String[] getRecognizedProperties()
/* 206:    */   {
/* 207:269 */     return null;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Object getPropertyDefault(String propertyId)
/* 211:    */   {
/* 212:278 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void reset(XMLComponentManager componentManager)
/* 216:    */     throws XMLConfigurationException
/* 217:    */   {}
/* 218:    */   
/* 219:    */   public void setFeature(String featureId, boolean state)
/* 220:    */     throws XMLConfigurationException
/* 221:    */   {}
/* 222:    */   
/* 223:    */   public void setProperty(String propertyId, Object value)
/* 224:    */     throws XMLConfigurationException
/* 225:    */   {}
/* 226:    */   
/* 227:    */   protected static String[] merge(String[] array1, String[] array2)
/* 228:    */   {
/* 229:345 */     if (array1 == array2) {
/* 230:346 */       return array1;
/* 231:    */     }
/* 232:348 */     if (array1 == null) {
/* 233:349 */       return array2;
/* 234:    */     }
/* 235:351 */     if (array2 == null) {
/* 236:352 */       return array1;
/* 237:    */     }
/* 238:356 */     String[] array3 = new String[array1.length + array2.length];
/* 239:357 */     System.arraycopy(array1, 0, array3, 0, array1.length);
/* 240:358 */     System.arraycopy(array2, 0, array3, array1.length, array2.length);
/* 241:    */     
/* 242:360 */     return array3;
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.DefaultFilter
 * JD-Core Version:    0.7.0.1
 */