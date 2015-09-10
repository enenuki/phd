/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import org.apache.xml.serializer.utils.Messages;
/*   7:    */ import org.apache.xml.serializer.utils.Utils;
/*   8:    */ import org.xml.sax.Attributes;
/*   9:    */ import org.xml.sax.SAXException;
/*  10:    */ 
/*  11:    */ public class ToTextStream
/*  12:    */   extends ToStream
/*  13:    */ {
/*  14:    */   protected void startDocumentInternal()
/*  15:    */     throws SAXException
/*  16:    */   {
/*  17: 65 */     super.startDocumentInternal();
/*  18:    */     
/*  19: 67 */     this.m_needToCallStartDocument = false;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void endDocument()
/*  23:    */     throws SAXException
/*  24:    */   {
/*  25: 88 */     flushPending();
/*  26: 89 */     flushWriter();
/*  27: 90 */     if (this.m_tracer != null) {
/*  28: 91 */       super.fireEndDoc();
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void startElement(String namespaceURI, String localName, String name, Attributes atts)
/*  33:    */     throws SAXException
/*  34:    */   {
/*  35:131 */     if (this.m_tracer != null)
/*  36:    */     {
/*  37:132 */       super.fireStartElem(name);
/*  38:133 */       firePseudoAttributes();
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void endElement(String namespaceURI, String localName, String name)
/*  43:    */     throws SAXException
/*  44:    */   {
/*  45:166 */     if (this.m_tracer != null) {
/*  46:167 */       super.fireEndElem(name);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void characters(char[] ch, int start, int length)
/*  51:    */     throws SAXException
/*  52:    */   {
/*  53:199 */     flushPending();
/*  54:    */     try
/*  55:    */     {
/*  56:203 */       if (inTemporaryOutputState()) {
/*  57:215 */         this.m_writer.write(ch, start, length);
/*  58:    */       } else {
/*  59:219 */         writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/*  60:    */       }
/*  61:222 */       if (this.m_tracer != null) {
/*  62:223 */         super.fireCharEvent(ch, start, length);
/*  63:    */       }
/*  64:    */     }
/*  65:    */     catch (IOException ioe)
/*  66:    */     {
/*  67:227 */       throw new SAXException(ioe);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void charactersRaw(char[] ch, int start, int length)
/*  72:    */     throws SAXException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:248 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/*  77:    */     }
/*  78:    */     catch (IOException ioe)
/*  79:    */     {
/*  80:252 */       throw new SAXException(ioe);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   void writeNormalizedChars(char[] ch, int start, int length, boolean useLineSep)
/*  85:    */     throws IOException, SAXException
/*  86:    */   {
/*  87:277 */     String encoding = getEncoding();
/*  88:278 */     Writer writer = this.m_writer;
/*  89:279 */     int end = start + length;
/*  90:    */     
/*  91:    */ 
/*  92:282 */     char S_LINEFEED = '\n';
/*  93:288 */     for (int i = start; i < end; i++)
/*  94:    */     {
/*  95:289 */       char c = ch[i];
/*  96:291 */       if (('\n' == c) && (useLineSep))
/*  97:    */       {
/*  98:292 */         writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  99:    */       }
/* 100:294 */       else if (this.m_encodingInfo.isInEncoding(c))
/* 101:    */       {
/* 102:295 */         writer.write(c);
/* 103:    */       }
/* 104:297 */       else if (Encodings.isHighUTF16Surrogate(c))
/* 105:    */       {
/* 106:298 */         int codePoint = writeUTF16Surrogate(c, ch, i, end);
/* 107:299 */         if (codePoint != 0)
/* 108:    */         {
/* 109:302 */           String integralValue = Integer.toString(codePoint);
/* 110:303 */           String msg = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[] { integralValue, encoding });
/* 111:    */           
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:310 */           System.err.println(msg);
/* 118:    */         }
/* 119:313 */         i++;
/* 120:    */       }
/* 121:318 */       else if (encoding != null)
/* 122:    */       {
/* 123:324 */         writer.write(38);
/* 124:325 */         writer.write(35);
/* 125:326 */         writer.write(Integer.toString(c));
/* 126:327 */         writer.write(59);
/* 127:    */         
/* 128:    */ 
/* 129:    */ 
/* 130:331 */         String integralValue = Integer.toString(c);
/* 131:332 */         String msg = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[] { integralValue, encoding });
/* 132:    */         
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:339 */         System.err.println(msg);
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:344 */         writer.write(c);
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void cdata(char[] ch, int start, int length)
/* 148:    */     throws SAXException
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:382 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/* 153:383 */       if (this.m_tracer != null) {
/* 154:384 */         super.fireCDATAEvent(ch, start, length);
/* 155:    */       }
/* 156:    */     }
/* 157:    */     catch (IOException ioe)
/* 158:    */     {
/* 159:388 */       throw new SAXException(ioe);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 164:    */     throws SAXException
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:424 */       writeNormalizedChars(ch, start, length, this.m_lineSepUse);
/* 169:    */     }
/* 170:    */     catch (IOException ioe)
/* 171:    */     {
/* 172:428 */       throw new SAXException(ioe);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void processingInstruction(String target, String data)
/* 177:    */     throws SAXException
/* 178:    */   {
/* 179:455 */     flushPending();
/* 180:457 */     if (this.m_tracer != null) {
/* 181:458 */       super.fireEscapingEvent(target, data);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void comment(String data)
/* 186:    */     throws SAXException
/* 187:    */   {
/* 188:472 */     int length = data.length();
/* 189:473 */     if (length > this.m_charsBuff.length) {
/* 190:475 */       this.m_charsBuff = new char[length * 2 + 1];
/* 191:    */     }
/* 192:477 */     data.getChars(0, length, this.m_charsBuff, 0);
/* 193:478 */     comment(this.m_charsBuff, 0, length);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void comment(char[] ch, int start, int length)
/* 197:    */     throws SAXException
/* 198:    */   {
/* 199:497 */     flushPending();
/* 200:498 */     if (this.m_tracer != null) {
/* 201:499 */       super.fireCommentEvent(ch, start, length);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void entityReference(String name)
/* 206:    */     throws SAXException
/* 207:    */   {
/* 208:511 */     if (this.m_tracer != null) {
/* 209:512 */       super.fireEntityReference(name);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute) {}
/* 214:    */   
/* 215:    */   public void endCDATA()
/* 216:    */     throws SAXException
/* 217:    */   {}
/* 218:    */   
/* 219:    */   public void endElement(String elemName)
/* 220:    */     throws SAXException
/* 221:    */   {
/* 222:542 */     if (this.m_tracer != null) {
/* 223:543 */       super.fireEndElem(elemName);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName)
/* 228:    */     throws SAXException
/* 229:    */   {
/* 230:555 */     if (this.m_needToCallStartDocument) {
/* 231:556 */       startDocumentInternal();
/* 232:    */     }
/* 233:558 */     if (this.m_tracer != null)
/* 234:    */     {
/* 235:559 */       super.fireStartElem(elementName);
/* 236:560 */       firePseudoAttributes();
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void characters(String characters)
/* 241:    */     throws SAXException
/* 242:    */   {
/* 243:573 */     int length = characters.length();
/* 244:574 */     if (length > this.m_charsBuff.length) {
/* 245:576 */       this.m_charsBuff = new char[length * 2 + 1];
/* 246:    */     }
/* 247:578 */     characters.getChars(0, length, this.m_charsBuff, 0);
/* 248:579 */     characters(this.m_charsBuff, 0, length);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void addAttribute(String name, String value) {}
/* 252:    */   
/* 253:    */   public void addUniqueAttribute(String qName, String value, int flags)
/* 254:    */     throws SAXException
/* 255:    */   {}
/* 256:    */   
/* 257:    */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 258:    */     throws SAXException
/* 259:    */   {
/* 260:607 */     return false;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void startPrefixMapping(String prefix, String uri)
/* 264:    */     throws SAXException
/* 265:    */   {}
/* 266:    */   
/* 267:    */   public void namespaceAfterStartElement(String prefix, String uri)
/* 268:    */     throws SAXException
/* 269:    */   {}
/* 270:    */   
/* 271:    */   public void flushPending()
/* 272:    */     throws SAXException
/* 273:    */   {
/* 274:628 */     if (this.m_needToCallStartDocument)
/* 275:    */     {
/* 276:630 */       startDocumentInternal();
/* 277:631 */       this.m_needToCallStartDocument = false;
/* 278:    */     }
/* 279:    */   }
/* 280:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToTextStream
 * JD-Core Version:    0.7.0.1
 */