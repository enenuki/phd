/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.utils.NodeConsumer;
/*   5:    */ import org.apache.xml.utils.XMLString;
/*   6:    */ import org.xml.sax.ContentHandler;
/*   7:    */ import org.xml.sax.SAXException;
/*   8:    */ import org.xml.sax.ext.LexicalHandler;
/*   9:    */ import org.xml.sax.helpers.AttributesImpl;
/*  10:    */ 
/*  11:    */ public class DTMTreeWalker
/*  12:    */ {
/*  13: 43 */   private ContentHandler m_contentHandler = null;
/*  14:    */   protected DTM m_dtm;
/*  15:    */   
/*  16:    */   public void setDTM(DTM dtm)
/*  17:    */   {
/*  18: 55 */     this.m_dtm = dtm;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ContentHandler getcontentHandler()
/*  22:    */   {
/*  23: 65 */     return this.m_contentHandler;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setcontentHandler(ContentHandler ch)
/*  27:    */   {
/*  28: 75 */     this.m_contentHandler = ch;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public DTMTreeWalker() {}
/*  32:    */   
/*  33:    */   public DTMTreeWalker(ContentHandler contentHandler, DTM dtm)
/*  34:    */   {
/*  35: 93 */     this.m_contentHandler = contentHandler;
/*  36: 94 */     this.m_dtm = dtm;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void traverse(int pos)
/*  40:    */     throws SAXException
/*  41:    */   {
/*  42:110 */     int top = pos;
/*  43:112 */     while (-1 != pos)
/*  44:    */     {
/*  45:114 */       startNode(pos);
/*  46:115 */       int nextNode = this.m_dtm.getFirstChild(pos);
/*  47:116 */       while (-1 == nextNode)
/*  48:    */       {
/*  49:118 */         endNode(pos);
/*  50:120 */         if (top == pos) {
/*  51:    */           break;
/*  52:    */         }
/*  53:123 */         nextNode = this.m_dtm.getNextSibling(pos);
/*  54:125 */         if (-1 == nextNode)
/*  55:    */         {
/*  56:127 */           pos = this.m_dtm.getParent(pos);
/*  57:129 */           if ((-1 == pos) || (top == pos))
/*  58:    */           {
/*  59:133 */             if (-1 != pos) {
/*  60:134 */               endNode(pos);
/*  61:    */             }
/*  62:136 */             nextNode = -1;
/*  63:    */             
/*  64:138 */             break;
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:143 */       pos = nextNode;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void traverse(int pos, int top)
/*  73:    */     throws SAXException
/*  74:    */   {
/*  75:165 */     while (-1 != pos)
/*  76:    */     {
/*  77:167 */       startNode(pos);
/*  78:168 */       int nextNode = this.m_dtm.getFirstChild(pos);
/*  79:169 */       while (-1 == nextNode)
/*  80:    */       {
/*  81:171 */         endNode(pos);
/*  82:173 */         if ((-1 != top) && (top == pos)) {
/*  83:    */           break;
/*  84:    */         }
/*  85:176 */         nextNode = this.m_dtm.getNextSibling(pos);
/*  86:178 */         if (-1 == nextNode)
/*  87:    */         {
/*  88:180 */           pos = this.m_dtm.getParent(pos);
/*  89:182 */           if ((-1 == pos) || ((-1 != top) && (top == pos)))
/*  90:    */           {
/*  91:184 */             nextNode = -1;
/*  92:    */             
/*  93:186 */             break;
/*  94:    */           }
/*  95:    */         }
/*  96:    */       }
/*  97:191 */       pos = nextNode;
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:196 */   boolean nextIsRaw = false;
/* 102:    */   
/* 103:    */   private final void dispatachChars(int node)
/* 104:    */     throws SAXException
/* 105:    */   {
/* 106:204 */     this.m_dtm.dispatchCharactersEvents(node, this.m_contentHandler, false);
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void startNode(int node)
/* 110:    */     throws SAXException
/* 111:    */   {
/* 112:218 */     if ((this.m_contentHandler instanceof NodeConsumer)) {}
/* 113:224 */     switch (this.m_dtm.getNodeType(node))
/* 114:    */     {
/* 115:    */     case 8: 
/* 116:228 */       XMLString data = this.m_dtm.getStringValue(node);
/* 117:230 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 118:    */       {
/* 119:232 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 120:233 */         data.dispatchAsComment(lh);
/* 121:    */       }
/* 122:236 */       break;
/* 123:    */     case 11: 
/* 124:    */       break;
/* 125:    */     case 9: 
/* 126:242 */       this.m_contentHandler.startDocument();
/* 127:243 */       break;
/* 128:    */     case 1: 
/* 129:245 */       DTM dtm = this.m_dtm;
/* 130:247 */       for (int nsn = dtm.getFirstNamespaceNode(node, true); -1 != nsn; nsn = dtm.getNextNamespaceNode(node, nsn, true))
/* 131:    */       {
/* 132:251 */         String prefix = dtm.getNodeNameX(nsn);
/* 133:    */         
/* 134:253 */         this.m_contentHandler.startPrefixMapping(prefix, dtm.getNodeValue(nsn));
/* 135:    */       }
/* 136:259 */       String ns = dtm.getNamespaceURI(node);
/* 137:260 */       if (null == ns) {
/* 138:261 */         ns = "";
/* 139:    */       }
/* 140:264 */       AttributesImpl attrs = new AttributesImpl();
/* 141:267 */       for (int i = dtm.getFirstAttribute(node); i != -1; i = dtm.getNextAttribute(i)) {
/* 142:271 */         attrs.addAttribute(dtm.getNamespaceURI(i), dtm.getLocalName(i), dtm.getNodeName(i), "CDATA", dtm.getNodeValue(i));
/* 143:    */       }
/* 144:279 */       this.m_contentHandler.startElement(ns, this.m_dtm.getLocalName(node), this.m_dtm.getNodeName(node), attrs);
/* 145:    */       
/* 146:    */ 
/* 147:    */ 
/* 148:283 */       break;
/* 149:    */     case 7: 
/* 150:286 */       String name = this.m_dtm.getNodeName(node);
/* 151:289 */       if (name.equals("xslt-next-is-raw")) {
/* 152:291 */         this.nextIsRaw = true;
/* 153:    */       } else {
/* 154:295 */         this.m_contentHandler.processingInstruction(name, this.m_dtm.getNodeValue(node));
/* 155:    */       }
/* 156:299 */       break;
/* 157:    */     case 4: 
/* 158:302 */       boolean isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 159:303 */       LexicalHandler lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/* 160:306 */       if (isLexH) {
/* 161:308 */         lh.startCDATA();
/* 162:    */       }
/* 163:311 */       dispatachChars(node);
/* 164:314 */       if (isLexH) {
/* 165:316 */         lh.endCDATA();
/* 166:    */       }
/* 167:320 */       break;
/* 168:    */     case 3: 
/* 169:323 */       if (this.nextIsRaw)
/* 170:    */       {
/* 171:325 */         this.nextIsRaw = false;
/* 172:    */         
/* 173:327 */         this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 174:328 */         dispatachChars(node);
/* 175:329 */         this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/* 176:    */       }
/* 177:    */       else
/* 178:    */       {
/* 179:333 */         dispatachChars(node);
/* 180:    */       }
/* 181:336 */       break;
/* 182:    */     case 5: 
/* 183:339 */       if ((this.m_contentHandler instanceof LexicalHandler)) {
/* 184:341 */         ((LexicalHandler)this.m_contentHandler).startEntity(this.m_dtm.getNodeName(node));
/* 185:    */       }
/* 186:    */       break;
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected void endNode(int node)
/* 191:    */     throws SAXException
/* 192:    */   {
/* 193:366 */     switch (this.m_dtm.getNodeType(node))
/* 194:    */     {
/* 195:    */     case 9: 
/* 196:369 */       this.m_contentHandler.endDocument();
/* 197:370 */       break;
/* 198:    */     case 1: 
/* 199:372 */       String ns = this.m_dtm.getNamespaceURI(node);
/* 200:373 */       if (null == ns) {
/* 201:374 */         ns = "";
/* 202:    */       }
/* 203:375 */       this.m_contentHandler.endElement(ns, this.m_dtm.getLocalName(node), this.m_dtm.getNodeName(node));
/* 204:379 */       for (int nsn = this.m_dtm.getFirstNamespaceNode(node, true); -1 != nsn; nsn = this.m_dtm.getNextNamespaceNode(node, nsn, true))
/* 205:    */       {
/* 206:383 */         String prefix = this.m_dtm.getNodeNameX(nsn);
/* 207:    */         
/* 208:385 */         this.m_contentHandler.endPrefixMapping(prefix);
/* 209:    */       }
/* 210:387 */       break;
/* 211:    */     case 4: 
/* 212:    */       break;
/* 213:    */     case 5: 
/* 214:392 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 215:    */       {
/* 216:394 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 217:    */         
/* 218:396 */         lh.endEntity(this.m_dtm.getNodeName(node));
/* 219:    */       }
/* 220:    */       break;
/* 221:    */     }
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMTreeWalker
 * JD-Core Version:    0.7.0.1
 */