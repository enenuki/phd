/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.apache.xerces.xni.Augmentations;
/*   5:    */ import org.apache.xerces.xni.NamespaceContext;
/*   6:    */ import org.apache.xerces.xni.QName;
/*   7:    */ import org.apache.xerces.xni.XMLAttributes;
/*   8:    */ import org.apache.xerces.xni.XMLLocator;
/*   9:    */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*  10:    */ import org.apache.xerces.xni.XMLString;
/*  11:    */ import org.apache.xerces.xni.XNIException;
/*  12:    */ 
/*  13:    */ public class ElementRemover
/*  14:    */   extends DefaultFilter
/*  15:    */ {
/*  16:102 */   protected static final Object NULL = new Object();
/*  17:111 */   protected Hashtable fAcceptedElements = new Hashtable();
/*  18:114 */   protected Hashtable fRemovedElements = new Hashtable();
/*  19:    */   protected int fElementDepth;
/*  20:    */   protected int fRemovalElementDepth;
/*  21:    */   
/*  22:    */   public void acceptElement(String element, String[] attributes)
/*  23:    */   {
/*  24:139 */     Object key = element.toLowerCase();
/*  25:140 */     Object value = NULL;
/*  26:141 */     if (attributes != null)
/*  27:    */     {
/*  28:142 */       String[] newarray = new String[attributes.length];
/*  29:143 */       for (int i = 0; i < attributes.length; i++) {
/*  30:144 */         newarray[i] = attributes[i].toLowerCase();
/*  31:    */       }
/*  32:146 */       value = attributes;
/*  33:    */     }
/*  34:148 */     this.fAcceptedElements.put(key, value);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void removeElement(String element)
/*  38:    */   {
/*  39:160 */     Object key = element.toLowerCase();
/*  40:161 */     Object value = NULL;
/*  41:162 */     this.fRemovedElements.put(key, value);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/*  45:    */     throws XNIException
/*  46:    */   {
/*  47:175 */     this.fElementDepth = 0;
/*  48:176 */     this.fRemovalElementDepth = 2147483647;
/*  49:177 */     super.startDocument(locator, encoding, nscontext, augs);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/*  53:    */     throws XNIException
/*  54:    */   {
/*  55:185 */     startDocument(locator, encoding, null, augs);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void startPrefixMapping(String prefix, String uri, Augmentations augs)
/*  59:    */     throws XNIException
/*  60:    */   {
/*  61:191 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/*  62:192 */       super.startPrefixMapping(prefix, uri, augs);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  67:    */     throws XNIException
/*  68:    */   {
/*  69:199 */     if ((this.fElementDepth <= this.fRemovalElementDepth) && (handleOpenTag(element, attributes))) {
/*  70:200 */       super.startElement(element, attributes, augs);
/*  71:    */     }
/*  72:202 */     this.fElementDepth += 1;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  76:    */     throws XNIException
/*  77:    */   {
/*  78:208 */     if ((this.fElementDepth <= this.fRemovalElementDepth) && (handleOpenTag(element, attributes))) {
/*  79:209 */       super.emptyElement(element, attributes, augs);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void comment(XMLString text, Augmentations augs)
/*  84:    */     throws XNIException
/*  85:    */   {
/*  86:216 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/*  87:217 */       super.comment(text, augs);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void processingInstruction(String target, XMLString data, Augmentations augs)
/*  92:    */     throws XNIException
/*  93:    */   {
/*  94:224 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/*  95:225 */       super.processingInstruction(target, data, augs);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void characters(XMLString text, Augmentations augs)
/* 100:    */     throws XNIException
/* 101:    */   {
/* 102:232 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 103:233 */       super.characters(text, augs);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void ignorableWhitespace(XMLString text, Augmentations augs)
/* 108:    */     throws XNIException
/* 109:    */   {
/* 110:240 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 111:241 */       super.ignorableWhitespace(text, augs);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
/* 116:    */     throws XNIException
/* 117:    */   {
/* 118:248 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 119:249 */       super.startGeneralEntity(name, id, encoding, augs);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void textDecl(String version, String encoding, Augmentations augs)
/* 124:    */     throws XNIException
/* 125:    */   {
/* 126:256 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 127:257 */       super.textDecl(version, encoding, augs);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void endGeneralEntity(String name, Augmentations augs)
/* 132:    */     throws XNIException
/* 133:    */   {
/* 134:264 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 135:265 */       super.endGeneralEntity(name, augs);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void startCDATA(Augmentations augs)
/* 140:    */     throws XNIException
/* 141:    */   {
/* 142:271 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 143:272 */       super.startCDATA(augs);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void endCDATA(Augmentations augs)
/* 148:    */     throws XNIException
/* 149:    */   {
/* 150:278 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 151:279 */       super.endCDATA(augs);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void endElement(QName element, Augmentations augs)
/* 156:    */     throws XNIException
/* 157:    */   {
/* 158:286 */     if ((this.fElementDepth <= this.fRemovalElementDepth) && (elementAccepted(element.rawname))) {
/* 159:287 */       super.endElement(element, augs);
/* 160:    */     }
/* 161:289 */     this.fElementDepth -= 1;
/* 162:290 */     if (this.fElementDepth == this.fRemovalElementDepth) {
/* 163:291 */       this.fRemovalElementDepth = 2147483647;
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void endPrefixMapping(String prefix, Augmentations augs)
/* 168:    */     throws XNIException
/* 169:    */   {
/* 170:298 */     if (this.fElementDepth <= this.fRemovalElementDepth) {
/* 171:299 */       super.endPrefixMapping(prefix, augs);
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected boolean elementAccepted(String element)
/* 176:    */   {
/* 177:309 */     Object key = element.toLowerCase();
/* 178:310 */     return this.fAcceptedElements.containsKey(key);
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected boolean elementRemoved(String element)
/* 182:    */   {
/* 183:315 */     Object key = element.toLowerCase();
/* 184:316 */     return this.fRemovedElements.containsKey(key);
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean handleOpenTag(QName element, XMLAttributes attributes)
/* 188:    */   {
/* 189:321 */     if (elementAccepted(element.rawname))
/* 190:    */     {
/* 191:322 */       Object key = element.rawname.toLowerCase();
/* 192:323 */       Object value = this.fAcceptedElements.get(key);
/* 193:324 */       if (value != NULL)
/* 194:    */       {
/* 195:325 */         String[] anames = (String[])value;
/* 196:326 */         int attributeCount = attributes.getLength();
/* 197:    */         label125:
/* 198:327 */         for (int i = 0; i < attributeCount; i++)
/* 199:    */         {
/* 200:328 */           String aname = attributes.getQName(i).toLowerCase();
/* 201:329 */           for (int j = 0; j < anames.length; j++) {
/* 202:330 */             if (anames[j].equals(aname)) {
/* 203:    */               break label125;
/* 204:    */             }
/* 205:    */           }
/* 206:334 */           attributes.removeAttributeAt(i--);
/* 207:335 */           attributeCount--;
/* 208:    */         }
/* 209:    */       }
/* 210:    */       else
/* 211:    */       {
/* 212:339 */         attributes.removeAllAttributes();
/* 213:    */       }
/* 214:341 */       return true;
/* 215:    */     }
/* 216:343 */     if (elementRemoved(element.rawname)) {
/* 217:344 */       this.fRemovalElementDepth = this.fElementDepth;
/* 218:    */     }
/* 219:346 */     return false;
/* 220:    */   }
/* 221:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.ElementRemover
 * JD-Core Version:    0.7.0.1
 */