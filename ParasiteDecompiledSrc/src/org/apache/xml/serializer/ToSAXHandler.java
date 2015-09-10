/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ import org.xml.sax.Attributes;
/*   6:    */ import org.xml.sax.ContentHandler;
/*   7:    */ import org.xml.sax.ErrorHandler;
/*   8:    */ import org.xml.sax.SAXException;
/*   9:    */ import org.xml.sax.SAXParseException;
/*  10:    */ import org.xml.sax.ext.LexicalHandler;
/*  11:    */ 
/*  12:    */ public abstract class ToSAXHandler
/*  13:    */   extends SerializerBase
/*  14:    */ {
/*  15:    */   protected ContentHandler m_saxHandler;
/*  16:    */   protected LexicalHandler m_lexHandler;
/*  17:    */   
/*  18:    */   public ToSAXHandler() {}
/*  19:    */   
/*  20:    */   public ToSAXHandler(ContentHandler hdlr, LexicalHandler lex, String encoding)
/*  21:    */   {
/*  22: 51 */     setContentHandler(hdlr);
/*  23: 52 */     setLexHandler(lex);
/*  24: 53 */     setEncoding(encoding);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ToSAXHandler(ContentHandler handler, String encoding)
/*  28:    */   {
/*  29: 57 */     setContentHandler(handler);
/*  30: 58 */     setEncoding(encoding);
/*  31:    */   }
/*  32:    */   
/*  33: 80 */   private boolean m_shouldGenerateNSAttribute = true;
/*  34: 86 */   protected TransformStateSetter m_state = null;
/*  35:    */   
/*  36:    */   protected void startDocumentInternal()
/*  37:    */     throws SAXException
/*  38:    */   {
/*  39: 93 */     if (this.m_needToCallStartDocument)
/*  40:    */     {
/*  41: 95 */       super.startDocumentInternal();
/*  42:    */       
/*  43: 97 */       this.m_saxHandler.startDocument();
/*  44: 98 */       this.m_needToCallStartDocument = false;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void startDTD(String arg0, String arg1, String arg2)
/*  49:    */     throws SAXException
/*  50:    */   {}
/*  51:    */   
/*  52:    */   public void characters(String characters)
/*  53:    */     throws SAXException
/*  54:    */   {
/*  55:122 */     int len = characters.length();
/*  56:123 */     if (len > this.m_charsBuff.length) {
/*  57:125 */       this.m_charsBuff = new char[len * 2 + 1];
/*  58:    */     }
/*  59:127 */     characters.getChars(0, len, this.m_charsBuff, 0);
/*  60:128 */     characters(this.m_charsBuff, 0, len);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void comment(String comment)
/*  64:    */     throws SAXException
/*  65:    */   {
/*  66:138 */     flushPending();
/*  67:141 */     if (this.m_lexHandler != null)
/*  68:    */     {
/*  69:143 */       int len = comment.length();
/*  70:144 */       if (len > this.m_charsBuff.length) {
/*  71:146 */         this.m_charsBuff = new char[len * 2 + 1];
/*  72:    */       }
/*  73:148 */       comment.getChars(0, len, this.m_charsBuff, 0);
/*  74:149 */       this.m_lexHandler.comment(this.m_charsBuff, 0, len);
/*  75:151 */       if (this.m_tracer != null) {
/*  76:152 */         super.fireCommentEvent(this.m_charsBuff, 0, len);
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void processingInstruction(String target, String data)
/*  82:    */     throws SAXException
/*  83:    */   {}
/*  84:    */   
/*  85:    */   protected void closeStartTag()
/*  86:    */     throws SAXException
/*  87:    */   {}
/*  88:    */   
/*  89:    */   protected void closeCDATA()
/*  90:    */     throws SAXException
/*  91:    */   {}
/*  92:    */   
/*  93:    */   public void startElement(String arg0, String arg1, String arg2, Attributes arg3)
/*  94:    */     throws SAXException
/*  95:    */   {
/*  96:199 */     if (this.m_state != null) {
/*  97:200 */       this.m_state.resetState(getTransformer());
/*  98:    */     }
/*  99:204 */     if (this.m_tracer != null) {
/* 100:205 */       super.fireStartElem(arg2);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setLexHandler(LexicalHandler _lexHandler)
/* 105:    */   {
/* 106:214 */     this.m_lexHandler = _lexHandler;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setContentHandler(ContentHandler _saxHandler)
/* 110:    */   {
/* 111:223 */     this.m_saxHandler = _saxHandler;
/* 112:224 */     if ((this.m_lexHandler == null) && ((_saxHandler instanceof LexicalHandler))) {
/* 113:228 */       this.m_lexHandler = ((LexicalHandler)_saxHandler);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setCdataSectionElements(Vector URI_and_localNames) {}
/* 118:    */   
/* 119:    */   public void setShouldOutputNSAttr(boolean doOutputNSAttr)
/* 120:    */   {
/* 121:250 */     this.m_shouldGenerateNSAttribute = doOutputNSAttr;
/* 122:    */   }
/* 123:    */   
/* 124:    */   boolean getShouldOutputNSAttr()
/* 125:    */   {
/* 126:261 */     return this.m_shouldGenerateNSAttribute;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void flushPending()
/* 130:    */     throws SAXException
/* 131:    */   {
/* 132:271 */     if (this.m_needToCallStartDocument)
/* 133:    */     {
/* 134:273 */       startDocumentInternal();
/* 135:274 */       this.m_needToCallStartDocument = false;
/* 136:    */     }
/* 137:277 */     if (this.m_elemContext.m_startTagOpen)
/* 138:    */     {
/* 139:279 */       closeStartTag();
/* 140:280 */       this.m_elemContext.m_startTagOpen = false;
/* 141:    */     }
/* 142:283 */     if (this.m_cdataTagOpen)
/* 143:    */     {
/* 144:285 */       closeCDATA();
/* 145:286 */       this.m_cdataTagOpen = false;
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setTransformState(TransformStateSetter ts)
/* 150:    */   {
/* 151:300 */     this.m_state = ts;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void startElement(String uri, String localName, String qName)
/* 155:    */     throws SAXException
/* 156:    */   {
/* 157:316 */     if (this.m_state != null) {
/* 158:317 */       this.m_state.resetState(getTransformer());
/* 159:    */     }
/* 160:321 */     if (this.m_tracer != null) {
/* 161:322 */       super.fireStartElem(qName);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void startElement(String qName)
/* 166:    */     throws SAXException
/* 167:    */   {
/* 168:333 */     if (this.m_state != null) {
/* 169:334 */       this.m_state.resetState(getTransformer());
/* 170:    */     }
/* 171:337 */     if (this.m_tracer != null) {
/* 172:338 */       super.fireStartElem(qName);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void characters(Node node)
/* 177:    */     throws SAXException
/* 178:    */   {
/* 179:351 */     if (this.m_state != null) {
/* 180:353 */       this.m_state.setCurrentNode(node);
/* 181:    */     }
/* 182:358 */     String data = node.getNodeValue();
/* 183:359 */     if (data != null) {
/* 184:360 */       characters(data);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void fatalError(SAXParseException exc)
/* 189:    */     throws SAXException
/* 190:    */   {
/* 191:368 */     super.fatalError(exc);
/* 192:    */     
/* 193:370 */     this.m_needToCallStartDocument = false;
/* 194:372 */     if ((this.m_saxHandler instanceof ErrorHandler)) {
/* 195:373 */       ((ErrorHandler)this.m_saxHandler).fatalError(exc);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void error(SAXParseException exc)
/* 200:    */     throws SAXException
/* 201:    */   {
/* 202:381 */     super.error(exc);
/* 203:383 */     if ((this.m_saxHandler instanceof ErrorHandler)) {
/* 204:384 */       ((ErrorHandler)this.m_saxHandler).error(exc);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void warning(SAXParseException exc)
/* 209:    */     throws SAXException
/* 210:    */   {
/* 211:392 */     super.warning(exc);
/* 212:394 */     if ((this.m_saxHandler instanceof ErrorHandler)) {
/* 213:395 */       ((ErrorHandler)this.m_saxHandler).warning(exc);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean reset()
/* 218:    */   {
/* 219:409 */     boolean wasReset = false;
/* 220:410 */     if (super.reset())
/* 221:    */     {
/* 222:412 */       resetToSAXHandler();
/* 223:413 */       wasReset = true;
/* 224:    */     }
/* 225:415 */     return wasReset;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private void resetToSAXHandler()
/* 229:    */   {
/* 230:424 */     this.m_lexHandler = null;
/* 231:425 */     this.m_saxHandler = null;
/* 232:426 */     this.m_state = null;
/* 233:427 */     this.m_shouldGenerateNSAttribute = false;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void addUniqueAttribute(String qName, String value, int flags)
/* 237:    */     throws SAXException
/* 238:    */   {
/* 239:436 */     addAttribute(qName, value);
/* 240:    */   }
/* 241:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToSAXHandler
 * JD-Core Version:    0.7.0.1
 */