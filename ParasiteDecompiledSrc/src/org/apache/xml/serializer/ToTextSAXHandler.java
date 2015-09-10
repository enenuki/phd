/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.xml.sax.Attributes;
/*   9:    */ import org.xml.sax.ContentHandler;
/*  10:    */ import org.xml.sax.Locator;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ import org.xml.sax.ext.LexicalHandler;
/*  13:    */ 
/*  14:    */ /**
/*  15:    */  * @deprecated
/*  16:    */  */
/*  17:    */ public final class ToTextSAXHandler
/*  18:    */   extends ToSAXHandler
/*  19:    */ {
/*  20:    */   public void endElement(String elemName)
/*  21:    */     throws SAXException
/*  22:    */   {
/*  23: 53 */     if (this.m_tracer != null) {
/*  24: 54 */       super.fireEndElem(elemName);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void endElement(String arg0, String arg1, String arg2)
/*  29:    */     throws SAXException
/*  30:    */   {
/*  31: 63 */     if (this.m_tracer != null) {
/*  32: 64 */       super.fireEndElem(arg2);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ToTextSAXHandler(ContentHandler hdlr, LexicalHandler lex, String encoding)
/*  37:    */   {
/*  38: 69 */     super(hdlr, lex, encoding);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ToTextSAXHandler(ContentHandler handler, String encoding)
/*  42:    */   {
/*  43: 77 */     super(handler, encoding);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void comment(char[] ch, int start, int length)
/*  47:    */     throws SAXException
/*  48:    */   {
/*  49: 83 */     if (this.m_tracer != null) {
/*  50: 84 */       super.fireCommentEvent(ch, start, length);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void comment(String data)
/*  55:    */     throws SAXException
/*  56:    */   {
/*  57: 89 */     int length = data.length();
/*  58: 90 */     if (length > this.m_charsBuff.length) {
/*  59: 92 */       this.m_charsBuff = new char[length * 2 + 1];
/*  60:    */     }
/*  61: 94 */     data.getChars(0, length, this.m_charsBuff, 0);
/*  62: 95 */     comment(this.m_charsBuff, 0, length);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Properties getOutputFormat()
/*  66:    */   {
/*  67:103 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public OutputStream getOutputStream()
/*  71:    */   {
/*  72:111 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Writer getWriter()
/*  76:    */   {
/*  77:119 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void indent(int n)
/*  81:    */     throws SAXException
/*  82:    */   {}
/*  83:    */   
/*  84:    */   public boolean reset()
/*  85:    */   {
/*  86:136 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void serialize(Node node)
/*  90:    */     throws IOException
/*  91:    */   {}
/*  92:    */   
/*  93:    */   public boolean setEscaping(boolean escape)
/*  94:    */   {
/*  95:151 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIndent(boolean indent) {}
/*  99:    */   
/* 100:    */   public void setOutputFormat(Properties format) {}
/* 101:    */   
/* 102:    */   public void setOutputStream(OutputStream output) {}
/* 103:    */   
/* 104:    */   public void setWriter(Writer writer) {}
/* 105:    */   
/* 106:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute) {}
/* 107:    */   
/* 108:    */   public void attributeDecl(String arg0, String arg1, String arg2, String arg3, String arg4)
/* 109:    */     throws SAXException
/* 110:    */   {}
/* 111:    */   
/* 112:    */   public void elementDecl(String arg0, String arg1)
/* 113:    */     throws SAXException
/* 114:    */   {}
/* 115:    */   
/* 116:    */   public void externalEntityDecl(String arg0, String arg1, String arg2)
/* 117:    */     throws SAXException
/* 118:    */   {}
/* 119:    */   
/* 120:    */   public void internalEntityDecl(String arg0, String arg1)
/* 121:    */     throws SAXException
/* 122:    */   {}
/* 123:    */   
/* 124:    */   public void endPrefixMapping(String arg0)
/* 125:    */     throws SAXException
/* 126:    */   {}
/* 127:    */   
/* 128:    */   public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
/* 129:    */     throws SAXException
/* 130:    */   {}
/* 131:    */   
/* 132:    */   public void processingInstruction(String arg0, String arg1)
/* 133:    */     throws SAXException
/* 134:    */   {
/* 135:253 */     if (this.m_tracer != null) {
/* 136:254 */       super.fireEscapingEvent(arg0, arg1);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDocumentLocator(Locator arg0) {}
/* 141:    */   
/* 142:    */   public void skippedEntity(String arg0)
/* 143:    */     throws SAXException
/* 144:    */   {}
/* 145:    */   
/* 146:    */   public void startElement(String arg0, String arg1, String arg2, Attributes arg3)
/* 147:    */     throws SAXException
/* 148:    */   {
/* 149:281 */     flushPending();
/* 150:282 */     super.startElement(arg0, arg1, arg2, arg3);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void endCDATA()
/* 154:    */     throws SAXException
/* 155:    */   {}
/* 156:    */   
/* 157:    */   public void endDTD()
/* 158:    */     throws SAXException
/* 159:    */   {}
/* 160:    */   
/* 161:    */   public void startCDATA()
/* 162:    */     throws SAXException
/* 163:    */   {}
/* 164:    */   
/* 165:    */   public void startEntity(String arg0)
/* 166:    */     throws SAXException
/* 167:    */   {}
/* 168:    */   
/* 169:    */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName)
/* 170:    */     throws SAXException
/* 171:    */   {
/* 172:324 */     super.startElement(elementNamespaceURI, elementLocalName, elementName);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void startElement(String elementName)
/* 176:    */     throws SAXException
/* 177:    */   {
/* 178:330 */     super.startElement(elementName);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void endDocument()
/* 182:    */     throws SAXException
/* 183:    */   {
/* 184:340 */     flushPending();
/* 185:341 */     this.m_saxHandler.endDocument();
/* 186:343 */     if (this.m_tracer != null) {
/* 187:344 */       super.fireEndDoc();
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void characters(String characters)
/* 192:    */     throws SAXException
/* 193:    */   {
/* 194:354 */     int length = characters.length();
/* 195:355 */     if (length > this.m_charsBuff.length) {
/* 196:357 */       this.m_charsBuff = new char[length * 2 + 1];
/* 197:    */     }
/* 198:359 */     characters.getChars(0, length, this.m_charsBuff, 0);
/* 199:    */     
/* 200:361 */     this.m_saxHandler.characters(this.m_charsBuff, 0, length);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void characters(char[] characters, int offset, int length)
/* 204:    */     throws SAXException
/* 205:    */   {
/* 206:371 */     this.m_saxHandler.characters(characters, offset, length);
/* 207:374 */     if (this.m_tracer != null) {
/* 208:375 */       super.fireCharEvent(characters, offset, length);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void addAttribute(String name, String value) {}
/* 213:    */   
/* 214:    */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 215:    */     throws SAXException
/* 216:    */   {
/* 217:394 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void startPrefixMapping(String prefix, String uri)
/* 221:    */     throws SAXException
/* 222:    */   {}
/* 223:    */   
/* 224:    */   public void namespaceAfterStartElement(String prefix, String uri)
/* 225:    */     throws SAXException
/* 226:    */   {}
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToTextSAXHandler
 * JD-Core Version:    0.7.0.1
 */