/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import java.util.Vector;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xalan.processor.StylesheetHandler;
/*   9:    */ import org.apache.xalan.res.XSLMessages;
/*  10:    */ import org.apache.xml.utils.FastStringBuffer;
/*  11:    */ import org.apache.xml.utils.PrefixResolver;
/*  12:    */ import org.apache.xpath.XPath;
/*  13:    */ import org.apache.xpath.XPathContext;
/*  14:    */ import org.xml.sax.SAXException;
/*  15:    */ 
/*  16:    */ public class AVT
/*  17:    */   implements Serializable, XSLTVisitable
/*  18:    */ {
/*  19:    */   static final long serialVersionUID = 5167607155517042691L;
/*  20:    */   private static final boolean USE_OBJECT_POOL = false;
/*  21:    */   private static final int INIT_BUFFER_CHUNK_BITS = 8;
/*  22: 61 */   private String m_simpleString = null;
/*  23: 67 */   private Vector m_parts = null;
/*  24:    */   private String m_rawName;
/*  25:    */   private String m_name;
/*  26:    */   private String m_uri;
/*  27:    */   
/*  28:    */   public String getRawName()
/*  29:    */   {
/*  30: 84 */     return this.m_rawName;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setRawName(String rawName)
/*  34:    */   {
/*  35: 94 */     this.m_rawName = rawName;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getName()
/*  39:    */   {
/*  40:110 */     return this.m_name;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setName(String name)
/*  44:    */   {
/*  45:120 */     this.m_name = name;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getURI()
/*  49:    */   {
/*  50:136 */     return this.m_uri;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setURI(String uri)
/*  54:    */   {
/*  55:146 */     this.m_uri = uri;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public AVT(StylesheetHandler handler, String uri, String name, String rawName, String stringedValue, ElemTemplateElement owner)
/*  59:    */     throws TransformerException
/*  60:    */   {
/*  61:168 */     this.m_uri = uri;
/*  62:169 */     this.m_name = name;
/*  63:170 */     this.m_rawName = rawName;
/*  64:    */     
/*  65:172 */     StringTokenizer tokenizer = new StringTokenizer(stringedValue, "{}\"'", true);
/*  66:    */     
/*  67:174 */     int nTokens = tokenizer.countTokens();
/*  68:176 */     if (nTokens < 2)
/*  69:    */     {
/*  70:178 */       this.m_simpleString = stringedValue;
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:182 */       FastStringBuffer buffer = null;
/*  75:183 */       FastStringBuffer exprBuffer = null;
/*  76:    */       
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:188 */       buffer = new FastStringBuffer(6);
/*  81:189 */       exprBuffer = new FastStringBuffer(6);
/*  82:    */       try
/*  83:    */       {
/*  84:193 */         this.m_parts = new Vector(nTokens + 1);
/*  85:    */         
/*  86:195 */         String t = null;
/*  87:196 */         String lookahead = null;
/*  88:197 */         String error = null;
/*  89:199 */         while (tokenizer.hasMoreTokens())
/*  90:    */         {
/*  91:201 */           if (lookahead != null)
/*  92:    */           {
/*  93:203 */             t = lookahead;
/*  94:204 */             lookahead = null;
/*  95:    */           }
/*  96:    */           else
/*  97:    */           {
/*  98:207 */             t = tokenizer.nextToken();
/*  99:    */           }
/* 100:209 */           if (t.length() == 1) {
/* 101:211 */             switch (t.charAt(0))
/* 102:    */             {
/* 103:    */             case '"': 
/* 104:    */             case '\'': 
/* 105:218 */               buffer.append(t);
/* 106:    */               
/* 107:220 */               break;
/* 108:    */             case '{': 
/* 109:    */               try
/* 110:    */               {
/* 111:228 */                 lookahead = tokenizer.nextToken();
/* 112:230 */                 if (lookahead.equals("{"))
/* 113:    */                 {
/* 114:234 */                   buffer.append(lookahead);
/* 115:    */                   
/* 116:236 */                   lookahead = null;
/* 117:    */                 }
/* 118:    */                 else
/* 119:    */                 {
/* 120:251 */                   if (buffer.length() > 0)
/* 121:    */                   {
/* 122:253 */                     this.m_parts.addElement(new AVTPartSimple(buffer.toString()));
/* 123:254 */                     buffer.setLength(0);
/* 124:    */                   }
/* 125:257 */                   exprBuffer.setLength(0);
/* 126:259 */                   while (null != lookahead) {
/* 127:261 */                     if (lookahead.length() == 1)
/* 128:    */                     {
/* 129:263 */                       switch (lookahead.charAt(0))
/* 130:    */                       {
/* 131:    */                       case '"': 
/* 132:    */                       case '\'': 
/* 133:270 */                         exprBuffer.append(lookahead);
/* 134:    */                         
/* 135:272 */                         String quote = lookahead;
/* 136:    */                         
/* 137:    */ 
/* 138:275 */                         lookahead = tokenizer.nextToken();
/* 139:277 */                         while (!lookahead.equals(quote))
/* 140:    */                         {
/* 141:279 */                           exprBuffer.append(lookahead);
/* 142:    */                           
/* 143:281 */                           lookahead = tokenizer.nextToken();
/* 144:    */                         }
/* 145:284 */                         exprBuffer.append(lookahead);
/* 146:    */                         
/* 147:286 */                         lookahead = tokenizer.nextToken();
/* 148:    */                         
/* 149:288 */                         break;
/* 150:    */                       case '{': 
/* 151:294 */                         error = XSLMessages.createMessage("ER_NO_CURLYBRACE", null);
/* 152:    */                         
/* 153:    */ 
/* 154:297 */                         lookahead = null;
/* 155:    */                         
/* 156:299 */                         break;
/* 157:    */                       case '}': 
/* 158:306 */                         buffer.setLength(0);
/* 159:    */                         
/* 160:308 */                         XPath xpath = handler.createXPath(exprBuffer.toString(), owner);
/* 161:    */                         
/* 162:    */ 
/* 163:311 */                         this.m_parts.addElement(new AVTPartXPath(xpath));
/* 164:    */                         
/* 165:313 */                         lookahead = null;
/* 166:    */                         
/* 167:315 */                         break;
/* 168:    */                       default: 
/* 169:321 */                         exprBuffer.append(lookahead);
/* 170:    */                         
/* 171:323 */                         lookahead = tokenizer.nextToken(); break;
/* 172:    */                       }
/* 173:    */                     }
/* 174:    */                     else
/* 175:    */                     {
/* 176:331 */                       exprBuffer.append(lookahead);
/* 177:    */                       
/* 178:333 */                       lookahead = tokenizer.nextToken();
/* 179:    */                     }
/* 180:    */                   }
/* 181:337 */                   if (error == null) {
/* 182:    */                     break;
/* 183:    */                   }
/* 184:    */                 }
/* 185:    */               }
/* 186:    */               catch (NoSuchElementException ex)
/* 187:    */               {
/* 188:347 */                 error = XSLMessages.createMessage("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { name, stringedValue });
/* 189:    */               }
/* 190:    */             case '}': 
/* 191:353 */               lookahead = tokenizer.nextToken();
/* 192:355 */               if (lookahead.equals("}"))
/* 193:    */               {
/* 194:359 */                 buffer.append(lookahead);
/* 195:    */                 
/* 196:361 */                 lookahead = null;
/* 197:    */               }
/* 198:    */               else
/* 199:    */               {
/* 200:    */                 try
/* 201:    */                 {
/* 202:369 */                   handler.warn("WG_FOUND_CURLYBRACE", null);
/* 203:    */                 }
/* 204:    */                 catch (SAXException se)
/* 205:    */                 {
/* 206:373 */                   throw new TransformerException(se);
/* 207:    */                 }
/* 208:376 */                 buffer.append("}");
/* 209:    */               }
/* 210:381 */               break;
/* 211:    */             default: 
/* 212:387 */               buffer.append(t); break;
/* 213:    */             }
/* 214:    */           } else {
/* 215:395 */             buffer.append(t);
/* 216:    */           }
/* 217:398 */           if (null != error)
/* 218:    */           {
/* 219:    */             try
/* 220:    */             {
/* 221:402 */               handler.warn("WG_ATTR_TEMPLATE", new Object[] { error });
/* 222:    */             }
/* 223:    */             catch (SAXException se)
/* 224:    */             {
/* 225:407 */               throw new TransformerException(se);
/* 226:    */             }
/* 227:410 */             break;
/* 228:    */           }
/* 229:    */         }
/* 230:414 */         if (buffer.length() > 0)
/* 231:    */         {
/* 232:416 */           this.m_parts.addElement(new AVTPartSimple(buffer.toString()));
/* 233:417 */           buffer.setLength(0);
/* 234:    */         }
/* 235:    */       }
/* 236:    */       finally
/* 237:    */       {
/* 238:426 */         buffer = null;
/* 239:427 */         exprBuffer = null;
/* 240:    */       }
/* 241:    */     }
/* 242:432 */     if ((null == this.m_parts) && (null == this.m_simpleString)) {
/* 243:436 */       this.m_simpleString = "";
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getSimpleString()
/* 248:    */   {
/* 249:448 */     if (null != this.m_simpleString) {
/* 250:449 */       return this.m_simpleString;
/* 251:    */     }
/* 252:451 */     if (null != this.m_parts)
/* 253:    */     {
/* 254:452 */       FastStringBuffer buf = getBuffer();
/* 255:453 */       String out = null;
/* 256:    */       
/* 257:455 */       int n = this.m_parts.size();
/* 258:    */       try
/* 259:    */       {
/* 260:457 */         for (int i = 0; i < n; i++)
/* 261:    */         {
/* 262:458 */           AVTPart part = (AVTPart)this.m_parts.elementAt(i);
/* 263:459 */           buf.append(part.getSimpleString());
/* 264:    */         }
/* 265:461 */         out = buf.toString();
/* 266:    */       }
/* 267:    */       finally
/* 268:    */       {
/* 269:466 */         buf.setLength(0);
/* 270:    */       }
/* 271:469 */       return out;
/* 272:    */     }
/* 273:471 */     return "";
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String evaluate(XPathContext xctxt, int context, PrefixResolver nsNode)
/* 277:    */     throws TransformerException
/* 278:    */   {
/* 279:490 */     if (null != this.m_simpleString) {
/* 280:491 */       return this.m_simpleString;
/* 281:    */     }
/* 282:492 */     if (null != this.m_parts)
/* 283:    */     {
/* 284:493 */       FastStringBuffer buf = getBuffer();
/* 285:494 */       String out = null;
/* 286:495 */       int n = this.m_parts.size();
/* 287:    */       try
/* 288:    */       {
/* 289:497 */         for (int i = 0; i < n; i++)
/* 290:    */         {
/* 291:498 */           AVTPart part = (AVTPart)this.m_parts.elementAt(i);
/* 292:499 */           part.evaluate(xctxt, buf, context, nsNode);
/* 293:    */         }
/* 294:501 */         out = buf.toString();
/* 295:    */       }
/* 296:    */       finally
/* 297:    */       {
/* 298:506 */         buf.setLength(0);
/* 299:    */       }
/* 300:509 */       return out;
/* 301:    */     }
/* 302:511 */     return "";
/* 303:    */   }
/* 304:    */   
/* 305:    */   public boolean isContextInsensitive()
/* 306:    */   {
/* 307:528 */     return null != this.m_simpleString;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean canTraverseOutsideSubtree()
/* 311:    */   {
/* 312:540 */     if (null != this.m_parts)
/* 313:    */     {
/* 314:542 */       int n = this.m_parts.size();
/* 315:544 */       for (int i = 0; i < n; i++)
/* 316:    */       {
/* 317:546 */         AVTPart part = (AVTPart)this.m_parts.elementAt(i);
/* 318:548 */         if (part.canTraverseOutsideSubtree()) {
/* 319:549 */           return true;
/* 320:    */         }
/* 321:    */       }
/* 322:    */     }
/* 323:553 */     return false;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 327:    */   {
/* 328:568 */     if (null != this.m_parts)
/* 329:    */     {
/* 330:570 */       int n = this.m_parts.size();
/* 331:572 */       for (int i = 0; i < n; i++)
/* 332:    */       {
/* 333:574 */         AVTPart part = (AVTPart)this.m_parts.elementAt(i);
/* 334:    */         
/* 335:576 */         part.fixupVariables(vars, globalsSize);
/* 336:    */       }
/* 337:    */     }
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void callVisitors(XSLTVisitor visitor)
/* 341:    */   {
/* 342:586 */     if ((visitor.visitAVT(this)) && (null != this.m_parts))
/* 343:    */     {
/* 344:588 */       int n = this.m_parts.size();
/* 345:590 */       for (int i = 0; i < n; i++)
/* 346:    */       {
/* 347:592 */         AVTPart part = (AVTPart)this.m_parts.elementAt(i);
/* 348:    */         
/* 349:594 */         part.callVisitors(visitor);
/* 350:    */       }
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean isSimple()
/* 355:    */   {
/* 356:604 */     return this.m_simpleString != null;
/* 357:    */   }
/* 358:    */   
/* 359:    */   private final FastStringBuffer getBuffer()
/* 360:    */   {
/* 361:611 */     return new FastStringBuffer(8);
/* 362:    */   }
/* 363:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.AVT
 * JD-Core Version:    0.7.0.1
 */