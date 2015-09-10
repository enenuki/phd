/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   5:    */ import org.apache.xml.serializer.ExtendedLexicalHandler;
/*   6:    */ import org.apache.xml.serializer.NamespaceMappings;
/*   7:    */ import org.apache.xml.serializer.SerializationHandler;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.xml.sax.ContentHandler;
/*  11:    */ import org.xml.sax.DTDHandler;
/*  12:    */ import org.xml.sax.EntityResolver;
/*  13:    */ import org.xml.sax.ErrorHandler;
/*  14:    */ import org.xml.sax.InputSource;
/*  15:    */ import org.xml.sax.Locator;
/*  16:    */ import org.xml.sax.SAXException;
/*  17:    */ import org.xml.sax.SAXNotRecognizedException;
/*  18:    */ import org.xml.sax.SAXNotSupportedException;
/*  19:    */ import org.xml.sax.XMLReader;
/*  20:    */ import org.xml.sax.ext.LexicalHandler;
/*  21:    */ 
/*  22:    */ public class DOM2TO
/*  23:    */   implements XMLReader, Locator
/*  24:    */ {
/*  25:    */   private static final String EMPTYSTRING = "";
/*  26:    */   private static final String XMLNS_PREFIX = "xmlns";
/*  27:    */   private Node _dom;
/*  28:    */   private SerializationHandler _handler;
/*  29:    */   
/*  30:    */   public DOM2TO(Node root, SerializationHandler handler)
/*  31:    */   {
/*  32: 60 */     this._dom = root;
/*  33: 61 */     this._handler = handler;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ContentHandler getContentHandler()
/*  37:    */   {
/*  38: 65 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setContentHandler(ContentHandler handler) {}
/*  42:    */   
/*  43:    */   public void parse(InputSource unused)
/*  44:    */     throws IOException, SAXException
/*  45:    */   {
/*  46: 73 */     parse(this._dom);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void parse()
/*  50:    */     throws IOException, SAXException
/*  51:    */   {
/*  52: 77 */     if (this._dom != null)
/*  53:    */     {
/*  54: 78 */       boolean isIncomplete = this._dom.getNodeType() != 9;
/*  55: 81 */       if (isIncomplete)
/*  56:    */       {
/*  57: 82 */         this._handler.startDocument();
/*  58: 83 */         parse(this._dom);
/*  59: 84 */         this._handler.endDocument();
/*  60:    */       }
/*  61:    */       else
/*  62:    */       {
/*  63: 87 */         parse(this._dom);
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void parse(Node node)
/*  69:    */     throws IOException, SAXException
/*  70:    */   {
/*  71: 99 */     if (node == null) {
/*  72:    */       return;
/*  73:    */     }
/*  74:    */     Node next;
/*  75:101 */     switch (node.getNodeType())
/*  76:    */     {
/*  77:    */     case 2: 
/*  78:    */     case 5: 
/*  79:    */     case 6: 
/*  80:    */     case 10: 
/*  81:    */     case 12: 
/*  82:    */       break;
/*  83:    */     case 4: 
/*  84:110 */       this._handler.startCDATA();
/*  85:111 */       this._handler.characters(node.getNodeValue());
/*  86:112 */       this._handler.endCDATA();
/*  87:113 */       break;
/*  88:    */     case 8: 
/*  89:116 */       this._handler.comment(node.getNodeValue());
/*  90:117 */       break;
/*  91:    */     case 9: 
/*  92:120 */       this._handler.startDocument();
/*  93:121 */       next = node.getFirstChild();
/*  94:122 */       while (next != null)
/*  95:    */       {
/*  96:123 */         parse(next);
/*  97:124 */         next = next.getNextSibling();
/*  98:    */       }
/*  99:126 */       this._handler.endDocument();
/* 100:127 */       break;
/* 101:    */     case 11: 
/* 102:130 */       next = node.getFirstChild();
/* 103:131 */       while (next != null)
/* 104:    */       {
/* 105:132 */         parse(next);
/* 106:133 */         next = next.getNextSibling();
/* 107:    */       }
/* 108:135 */       break;
/* 109:    */     case 1: 
/* 110:139 */       String qname = node.getNodeName();
/* 111:140 */       this._handler.startElement(null, null, qname);
/* 112:    */       
/* 113:    */ 
/* 114:    */ 
/* 115:144 */       NamedNodeMap map = node.getAttributes();
/* 116:145 */       int length = map.getLength();
/* 117:    */       int colon;
/* 118:    */       String prefix;
/* 119:148 */       for (int i = 0; i < length; i++)
/* 120:    */       {
/* 121:149 */         Node attr = map.item(i);
/* 122:150 */         String qnameAttr = attr.getNodeName();
/* 123:153 */         if (qnameAttr.startsWith("xmlns"))
/* 124:    */         {
/* 125:154 */           String uriAttr = attr.getNodeValue();
/* 126:155 */           colon = qnameAttr.lastIndexOf(':');
/* 127:156 */           prefix = colon > 0 ? qnameAttr.substring(colon + 1) : "";
/* 128:    */           
/* 129:158 */           this._handler.namespaceAfterStartElement(prefix, uriAttr);
/* 130:    */         }
/* 131:    */       }
/* 132:163 */       NamespaceMappings nm = new NamespaceMappings();
/* 133:164 */       for (int i = 0; i < length; i++)
/* 134:    */       {
/* 135:165 */         Node attr = map.item(i);
/* 136:166 */         String qnameAttr = attr.getNodeName();
/* 137:169 */         if (!qnameAttr.startsWith("xmlns"))
/* 138:    */         {
/* 139:170 */           String uriAttr = attr.getNamespaceURI();
/* 140:172 */           if ((uriAttr != null) && (!uriAttr.equals("")))
/* 141:    */           {
/* 142:173 */             colon = qnameAttr.lastIndexOf(':');
/* 143:    */             
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:179 */             String newPrefix = nm.lookupPrefix(uriAttr);
/* 149:180 */             if (newPrefix == null) {
/* 150:181 */               newPrefix = nm.generateNextPrefix();
/* 151:    */             }
/* 152:182 */             prefix = colon > 0 ? qnameAttr.substring(0, colon) : newPrefix;
/* 153:    */             
/* 154:184 */             this._handler.namespaceAfterStartElement(prefix, uriAttr);
/* 155:185 */             this._handler.addAttribute(prefix + ":" + qnameAttr, attr.getNodeValue());
/* 156:    */           }
/* 157:    */           else
/* 158:    */           {
/* 159:188 */             this._handler.addAttribute(qnameAttr, attr.getNodeValue());
/* 160:    */           }
/* 161:    */         }
/* 162:    */       }
/* 163:194 */       String uri = node.getNamespaceURI();
/* 164:195 */       String localName = node.getLocalName();
/* 165:198 */       if (uri != null)
/* 166:    */       {
/* 167:199 */         colon = qname.lastIndexOf(':');
/* 168:200 */         prefix = colon > 0 ? qname.substring(0, colon) : "";
/* 169:201 */         this._handler.namespaceAfterStartElement(prefix, uri);
/* 170:    */       }
/* 171:208 */       else if ((uri == null) && (localName != null))
/* 172:    */       {
/* 173:209 */         prefix = "";
/* 174:210 */         this._handler.namespaceAfterStartElement(prefix, "");
/* 175:    */       }
/* 176:215 */       next = node.getFirstChild();
/* 177:216 */       while (next != null)
/* 178:    */       {
/* 179:217 */         parse(next);
/* 180:218 */         next = next.getNextSibling();
/* 181:    */       }
/* 182:222 */       this._handler.endElement(qname);
/* 183:223 */       break;
/* 184:    */     case 7: 
/* 185:226 */       this._handler.processingInstruction(node.getNodeName(), node.getNodeValue());
/* 186:    */       
/* 187:228 */       break;
/* 188:    */     case 3: 
/* 189:231 */       this._handler.characters(node.getNodeValue());
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public DTDHandler getDTDHandler()
/* 194:    */   {
/* 195:241 */     return null;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public ErrorHandler getErrorHandler()
/* 199:    */   {
/* 200:249 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean getFeature(String name)
/* 204:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 205:    */   {
/* 206:259 */     return false;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setFeature(String name, boolean value)
/* 210:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 211:    */   {}
/* 212:    */   
/* 213:    */   public void parse(String sysId)
/* 214:    */     throws IOException, SAXException
/* 215:    */   {
/* 216:276 */     throw new IOException("This method is not yet implemented.");
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDTDHandler(DTDHandler handler)
/* 220:    */     throws NullPointerException
/* 221:    */   {}
/* 222:    */   
/* 223:    */   public void setEntityResolver(EntityResolver resolver)
/* 224:    */     throws NullPointerException
/* 225:    */   {}
/* 226:    */   
/* 227:    */   public EntityResolver getEntityResolver()
/* 228:    */   {
/* 229:300 */     return null;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setErrorHandler(ErrorHandler handler)
/* 233:    */     throws NullPointerException
/* 234:    */   {}
/* 235:    */   
/* 236:    */   public void setProperty(String name, Object value)
/* 237:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 238:    */   {}
/* 239:    */   
/* 240:    */   public Object getProperty(String name)
/* 241:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 242:    */   {
/* 243:327 */     return null;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public int getColumnNumber()
/* 247:    */   {
/* 248:335 */     return 0;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public int getLineNumber()
/* 252:    */   {
/* 253:343 */     return 0;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getPublicId()
/* 257:    */   {
/* 258:351 */     return null;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getSystemId()
/* 262:    */   {
/* 263:359 */     return null;
/* 264:    */   }
/* 265:    */   
/* 266:    */   private String getNodeTypeFromCode(short code)
/* 267:    */   {
/* 268:364 */     String retval = null;
/* 269:365 */     switch (code)
/* 270:    */     {
/* 271:    */     case 2: 
/* 272:367 */       retval = "ATTRIBUTE_NODE"; break;
/* 273:    */     case 4: 
/* 274:369 */       retval = "CDATA_SECTION_NODE"; break;
/* 275:    */     case 8: 
/* 276:371 */       retval = "COMMENT_NODE"; break;
/* 277:    */     case 11: 
/* 278:373 */       retval = "DOCUMENT_FRAGMENT_NODE"; break;
/* 279:    */     case 9: 
/* 280:375 */       retval = "DOCUMENT_NODE"; break;
/* 281:    */     case 10: 
/* 282:377 */       retval = "DOCUMENT_TYPE_NODE"; break;
/* 283:    */     case 1: 
/* 284:379 */       retval = "ELEMENT_NODE"; break;
/* 285:    */     case 6: 
/* 286:381 */       retval = "ENTITY_NODE"; break;
/* 287:    */     case 5: 
/* 288:383 */       retval = "ENTITY_REFERENCE_NODE"; break;
/* 289:    */     case 12: 
/* 290:385 */       retval = "NOTATION_NODE"; break;
/* 291:    */     case 7: 
/* 292:387 */       retval = "PROCESSING_INSTRUCTION_NODE"; break;
/* 293:    */     case 3: 
/* 294:389 */       retval = "TEXT_NODE";
/* 295:    */     }
/* 296:391 */     return retval;
/* 297:    */   }
/* 298:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.DOM2TO
 * JD-Core Version:    0.7.0.1
 */