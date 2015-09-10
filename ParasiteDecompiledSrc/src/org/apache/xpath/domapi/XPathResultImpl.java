/*   1:    */ package org.apache.xpath.domapi;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xpath.XPath;
/*   5:    */ import org.apache.xpath.objects.XObject;
/*   6:    */ import org.apache.xpath.res.XPATHMessages;
/*   7:    */ import org.w3c.dom.DOMException;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ import org.w3c.dom.events.Event;
/*  11:    */ import org.w3c.dom.events.EventListener;
/*  12:    */ import org.w3c.dom.events.EventTarget;
/*  13:    */ import org.w3c.dom.traversal.NodeIterator;
/*  14:    */ import org.w3c.dom.xpath.XPathException;
/*  15:    */ import org.w3c.dom.xpath.XPathResult;
/*  16:    */ 
/*  17:    */ class XPathResultImpl
/*  18:    */   implements XPathResult, EventListener
/*  19:    */ {
/*  20:    */   private final XObject m_resultObj;
/*  21:    */   private final XPath m_xpath;
/*  22:    */   private final short m_resultType;
/*  23: 79 */   private boolean m_isInvalidIteratorState = false;
/*  24:    */   private final Node m_contextNode;
/*  25: 90 */   private NodeIterator m_iterator = null;
/*  26: 95 */   private NodeList m_list = null;
/*  27:    */   
/*  28:    */   XPathResultImpl(short type, XObject result, Node contextNode, XPath xpath)
/*  29:    */   {
/*  30:105 */     if (!isValidType(type))
/*  31:    */     {
/*  32:106 */       String fmsg = XPATHMessages.createXPATHMessage("ER_INVALID_XPATH_TYPE", new Object[] { new Integer(type) });
/*  33:107 */       throw new XPathException((short)52, fmsg);
/*  34:    */     }
/*  35:111 */     if (null == result)
/*  36:    */     {
/*  37:112 */       String fmsg = XPATHMessages.createXPATHMessage("ER_EMPTY_XPATH_RESULT", null);
/*  38:113 */       throw new XPathException((short)51, fmsg);
/*  39:    */     }
/*  40:116 */     this.m_resultObj = result;
/*  41:117 */     this.m_contextNode = contextNode;
/*  42:118 */     this.m_xpath = xpath;
/*  43:121 */     if (type == 0) {
/*  44:122 */       this.m_resultType = getTypeFromXObject(result);
/*  45:    */     } else {
/*  46:124 */       this.m_resultType = type;
/*  47:    */     }
/*  48:129 */     if ((this.m_resultType == 5) || (this.m_resultType == 4)) {
/*  49:131 */       addEventListener();
/*  50:    */     }
/*  51:136 */     if ((this.m_resultType == 5) || (this.m_resultType == 4) || (this.m_resultType == 8) || (this.m_resultType == 9)) {
/*  52:    */       try
/*  53:    */       {
/*  54:142 */         this.m_iterator = this.m_resultObj.nodeset();
/*  55:    */       }
/*  56:    */       catch (TransformerException te)
/*  57:    */       {
/*  58:145 */         String fmsg = XPATHMessages.createXPATHMessage("ER_INCOMPATIBLE_TYPES", new Object[] { this.m_xpath.getPatternString(), getTypeString(getTypeFromXObject(this.m_resultObj)), getTypeString(this.m_resultType) });
/*  59:146 */         throw new XPathException((short)52, fmsg);
/*  60:    */       }
/*  61:157 */     } else if ((this.m_resultType == 6) || (this.m_resultType == 7)) {
/*  62:    */       try
/*  63:    */       {
/*  64:160 */         this.m_list = this.m_resultObj.nodelist();
/*  65:    */       }
/*  66:    */       catch (TransformerException te)
/*  67:    */       {
/*  68:163 */         String fmsg = XPATHMessages.createXPATHMessage("ER_INCOMPATIBLE_TYPES", new Object[] { this.m_xpath.getPatternString(), getTypeString(getTypeFromXObject(this.m_resultObj)), getTypeString(this.m_resultType) });
/*  69:164 */         throw new XPathException((short)52, fmsg);
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public short getResultType()
/*  75:    */   {
/*  76:173 */     return this.m_resultType;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public double getNumberValue()
/*  80:    */     throws XPathException
/*  81:    */   {
/*  82:184 */     if (getResultType() != 1)
/*  83:    */     {
/*  84:185 */       String fmsg = XPATHMessages.createXPATHMessage("ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/*  85:186 */       throw new XPathException((short)52, fmsg);
/*  86:    */     }
/*  87:    */     try
/*  88:    */     {
/*  89:190 */       return this.m_resultObj.num();
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:193 */       throw new XPathException((short)52, e.getMessage());
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getStringValue()
/*  98:    */     throws XPathException
/*  99:    */   {
/* 100:207 */     if (getResultType() != 2)
/* 101:    */     {
/* 102:208 */       String fmsg = XPATHMessages.createXPATHMessage("ER_CANT_CONVERT_TO_STRING", new Object[] { this.m_xpath.getPatternString(), this.m_resultObj.getTypeString() });
/* 103:209 */       throw new XPathException((short)52, fmsg);
/* 104:    */     }
/* 105:    */     try
/* 106:    */     {
/* 107:213 */       return this.m_resultObj.str();
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:216 */       throw new XPathException((short)52, e.getMessage());
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean getBooleanValue()
/* 116:    */     throws XPathException
/* 117:    */   {
/* 118:225 */     if (getResultType() != 3)
/* 119:    */     {
/* 120:226 */       String fmsg = XPATHMessages.createXPATHMessage("ER_CANT_CONVERT_TO_BOOLEAN", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/* 121:227 */       throw new XPathException((short)52, fmsg);
/* 122:    */     }
/* 123:    */     try
/* 124:    */     {
/* 125:231 */       return this.m_resultObj.bool();
/* 126:    */     }
/* 127:    */     catch (TransformerException e)
/* 128:    */     {
/* 129:234 */       throw new XPathException((short)52, e.getMessage());
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Node getSingleNodeValue()
/* 134:    */     throws XPathException
/* 135:    */   {
/* 136:250 */     if ((this.m_resultType != 8) && (this.m_resultType != 9))
/* 137:    */     {
/* 138:252 */       String fmsg = XPATHMessages.createXPATHMessage("ER_CANT_CONVERT_TO_SINGLENODE", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/* 139:253 */       throw new XPathException((short)52, fmsg);
/* 140:    */     }
/* 141:258 */     NodeIterator result = null;
/* 142:    */     try
/* 143:    */     {
/* 144:260 */       result = this.m_resultObj.nodeset();
/* 145:    */     }
/* 146:    */     catch (TransformerException te)
/* 147:    */     {
/* 148:262 */       throw new XPathException((short)52, te.getMessage());
/* 149:    */     }
/* 150:265 */     if (null == result) {
/* 151:265 */       return null;
/* 152:    */     }
/* 153:267 */     Node node = result.nextNode();
/* 154:270 */     if (isNamespaceNode(node)) {
/* 155:271 */       return new XPathNamespaceImpl(node);
/* 156:    */     }
/* 157:273 */     return node;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean getInvalidIteratorState()
/* 161:    */   {
/* 162:281 */     return this.m_isInvalidIteratorState;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getSnapshotLength()
/* 166:    */     throws XPathException
/* 167:    */   {
/* 168:297 */     if ((this.m_resultType != 6) && (this.m_resultType != 7))
/* 169:    */     {
/* 170:299 */       String fmsg = XPATHMessages.createXPATHMessage("ER_CANT_GET_SNAPSHOT_LENGTH", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/* 171:300 */       throw new XPathException((short)52, fmsg);
/* 172:    */     }
/* 173:304 */     return this.m_list.getLength();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Node iterateNext()
/* 177:    */     throws XPathException, DOMException
/* 178:    */   {
/* 179:321 */     if ((this.m_resultType != 4) && (this.m_resultType != 5))
/* 180:    */     {
/* 181:323 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NON_ITERATOR_TYPE", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/* 182:324 */       throw new XPathException((short)52, fmsg);
/* 183:    */     }
/* 184:329 */     if (getInvalidIteratorState())
/* 185:    */     {
/* 186:330 */       String fmsg = XPATHMessages.createXPATHMessage("ER_DOC_MUTATED", null);
/* 187:331 */       throw new DOMException((short)11, fmsg);
/* 188:    */     }
/* 189:334 */     Node node = this.m_iterator.nextNode();
/* 190:335 */     if (null == node) {
/* 191:336 */       removeEventListener();
/* 192:    */     }
/* 193:338 */     if (isNamespaceNode(node)) {
/* 194:339 */       return new XPathNamespaceImpl(node);
/* 195:    */     }
/* 196:341 */     return node;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Node snapshotItem(int index)
/* 200:    */     throws XPathException
/* 201:    */   {
/* 202:364 */     if ((this.m_resultType != 6) && (this.m_resultType != 7))
/* 203:    */     {
/* 204:366 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NON_SNAPSHOT_TYPE", new Object[] { this.m_xpath.getPatternString(), getTypeString(this.m_resultType) });
/* 205:367 */       throw new XPathException((short)52, fmsg);
/* 206:    */     }
/* 207:372 */     Node node = this.m_list.item(index);
/* 208:375 */     if (isNamespaceNode(node)) {
/* 209:376 */       return new XPathNamespaceImpl(node);
/* 210:    */     }
/* 211:378 */     return node;
/* 212:    */   }
/* 213:    */   
/* 214:    */   static boolean isValidType(short type)
/* 215:    */   {
/* 216:390 */     switch (type)
/* 217:    */     {
/* 218:    */     case 0: 
/* 219:    */     case 1: 
/* 220:    */     case 2: 
/* 221:    */     case 3: 
/* 222:    */     case 4: 
/* 223:    */     case 5: 
/* 224:    */     case 6: 
/* 225:    */     case 7: 
/* 226:    */     case 8: 
/* 227:    */     case 9: 
/* 228:400 */       return true;
/* 229:    */     }
/* 230:401 */     return false;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void handleEvent(Event event)
/* 234:    */   {
/* 235:410 */     if (event.getType().equals("DOMSubtreeModified"))
/* 236:    */     {
/* 237:412 */       this.m_isInvalidIteratorState = true;
/* 238:    */       
/* 239:    */ 
/* 240:415 */       removeEventListener();
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private String getTypeString(int type)
/* 245:    */   {
/* 246:427 */     switch (type)
/* 247:    */     {
/* 248:    */     case 0: 
/* 249:428 */       return "ANY_TYPE";
/* 250:    */     case 8: 
/* 251:429 */       return "ANY_UNORDERED_NODE_TYPE";
/* 252:    */     case 3: 
/* 253:430 */       return "BOOLEAN";
/* 254:    */     case 9: 
/* 255:431 */       return "FIRST_ORDERED_NODE_TYPE";
/* 256:    */     case 1: 
/* 257:432 */       return "NUMBER_TYPE";
/* 258:    */     case 5: 
/* 259:433 */       return "ORDERED_NODE_ITERATOR_TYPE";
/* 260:    */     case 7: 
/* 261:434 */       return "ORDERED_NODE_SNAPSHOT_TYPE";
/* 262:    */     case 2: 
/* 263:435 */       return "STRING_TYPE";
/* 264:    */     case 4: 
/* 265:436 */       return "UNORDERED_NODE_ITERATOR_TYPE";
/* 266:    */     case 6: 
/* 267:437 */       return "UNORDERED_NODE_SNAPSHOT_TYPE";
/* 268:    */     }
/* 269:438 */     return "#UNKNOWN";
/* 270:    */   }
/* 271:    */   
/* 272:    */   private short getTypeFromXObject(XObject object)
/* 273:    */   {
/* 274:448 */     switch (object.getType())
/* 275:    */     {
/* 276:    */     case 1: 
/* 277:449 */       return 3;
/* 278:    */     case 4: 
/* 279:450 */       return 4;
/* 280:    */     case 2: 
/* 281:451 */       return 1;
/* 282:    */     case 3: 
/* 283:452 */       return 2;
/* 284:    */     case 5: 
/* 285:465 */       return 4;
/* 286:    */     case -1: 
/* 287:466 */       return 0;
/* 288:    */     }
/* 289:467 */     return 0;
/* 290:    */   }
/* 291:    */   
/* 292:    */   private boolean isNamespaceNode(Node node)
/* 293:    */   {
/* 294:481 */     if ((null != node) && (node.getNodeType() == 2) && ((node.getNodeName().startsWith("xmlns:")) || (node.getNodeName().equals("xmlns")))) {
/* 295:484 */       return true;
/* 296:    */     }
/* 297:486 */     return false;
/* 298:    */   }
/* 299:    */   
/* 300:    */   private void addEventListener()
/* 301:    */   {
/* 302:495 */     if ((this.m_contextNode instanceof EventTarget)) {
/* 303:496 */       ((EventTarget)this.m_contextNode).addEventListener("DOMSubtreeModified", this, true);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private void removeEventListener()
/* 308:    */   {
/* 309:506 */     if ((this.m_contextNode instanceof EventTarget)) {
/* 310:507 */       ((EventTarget)this.m_contextNode).removeEventListener("DOMSubtreeModified", this, true);
/* 311:    */     }
/* 312:    */   }
/* 313:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.domapi.XPathResultImpl
 * JD-Core Version:    0.7.0.1
 */