/*   1:    */ package org.apache.xalan.client;
/*   2:    */ 
/*   3:    */ import java.applet.Applet;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.ObjectInputStream;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.PrintWriter;
/*   9:    */ import java.io.StringReader;
/*  10:    */ import java.io.StringWriter;
/*  11:    */ import java.net.MalformedURLException;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.util.Enumeration;
/*  14:    */ import java.util.Hashtable;
/*  15:    */ import javax.xml.transform.Templates;
/*  16:    */ import javax.xml.transform.Transformer;
/*  17:    */ import javax.xml.transform.TransformerConfigurationException;
/*  18:    */ import javax.xml.transform.TransformerException;
/*  19:    */ import javax.xml.transform.TransformerFactory;
/*  20:    */ import javax.xml.transform.stream.StreamResult;
/*  21:    */ import javax.xml.transform.stream.StreamSource;
/*  22:    */ import org.apache.xalan.res.XSLMessages;
/*  23:    */ 
/*  24:    */ public class XSLTProcessorApplet
/*  25:    */   extends Applet
/*  26:    */ {
/*  27: 70 */   transient TransformerFactory m_tfactory = null;
/*  28:    */   private String m_styleURL;
/*  29:    */   private String m_documentURL;
/*  30: 89 */   private final String PARAM_styleURL = "styleURL";
/*  31: 94 */   private final String PARAM_documentURL = "documentURL";
/*  32:103 */   private String m_styleURLOfCached = null;
/*  33:108 */   private String m_documentURLOfCached = null;
/*  34:114 */   private URL m_codeBase = null;
/*  35:119 */   private String m_treeURL = null;
/*  36:125 */   private URL m_documentBase = null;
/*  37:130 */   private transient Thread m_callThread = null;
/*  38:134 */   private transient TrustedAgent m_trustedAgent = null;
/*  39:139 */   private transient Thread m_trustedWorker = null;
/*  40:144 */   private transient String m_htmlText = null;
/*  41:149 */   private transient String m_sourceText = null;
/*  42:154 */   private transient String m_nameOfIDAttrOfElemToModify = null;
/*  43:158 */   private transient String m_elemIdToModify = null;
/*  44:162 */   private transient String m_attrNameToSet = null;
/*  45:166 */   private transient String m_attrValueToSet = null;
/*  46:    */   transient Hashtable m_parameters;
/*  47:    */   private static final long serialVersionUID = 4618876841979251422L;
/*  48:    */   
/*  49:    */   public String getAppletInfo()
/*  50:    */   {
/*  51:179 */     return "Name: XSLTProcessorApplet\r\nAuthor: Scott Boag";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String[][] getParameterInfo()
/*  55:    */   {
/*  56:190 */     String[][] info = { { "styleURL", "String", "URL to an XSL stylesheet" }, { "documentURL", "String", "URL to an XML document" } };
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:196 */     return info;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void init()
/*  66:    */   {
/*  67:214 */     String param = getParameter("styleURL");
/*  68:    */     
/*  69:    */ 
/*  70:217 */     this.m_parameters = new Hashtable();
/*  71:219 */     if (param != null) {
/*  72:220 */       setStyleURL(param);
/*  73:    */     }
/*  74:224 */     param = getParameter("documentURL");
/*  75:226 */     if (param != null) {
/*  76:227 */       setDocumentURL(param);
/*  77:    */     }
/*  78:229 */     this.m_codeBase = getCodeBase();
/*  79:230 */     this.m_documentBase = getDocumentBase();
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:238 */     resize(320, 240);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void start()
/*  91:    */   {
/*  92:248 */     this.m_trustedAgent = new TrustedAgent();
/*  93:249 */     Thread currentThread = Thread.currentThread();
/*  94:250 */     this.m_trustedWorker = new Thread(currentThread.getThreadGroup(), this.m_trustedAgent);
/*  95:    */     
/*  96:252 */     this.m_trustedWorker.start();
/*  97:    */     try
/*  98:    */     {
/*  99:255 */       this.m_tfactory = TransformerFactory.newInstance();
/* 100:256 */       showStatus("Causing Transformer and Parser to Load and JIT...");
/* 101:    */       
/* 102:    */ 
/* 103:259 */       StringReader xmlbuf = new StringReader("<?xml version='1.0'?><foo/>");
/* 104:260 */       StringReader xslbuf = new StringReader("<?xml version='1.0'?><xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'><xsl:template match='foo'><out/></xsl:template></xsl:stylesheet>");
/* 105:    */       
/* 106:262 */       PrintWriter pw = new PrintWriter(new StringWriter());
/* 107:264 */       synchronized (this.m_tfactory)
/* 108:    */       {
/* 109:266 */         Templates templates = this.m_tfactory.newTemplates(new StreamSource(xslbuf));
/* 110:267 */         Transformer transformer = templates.newTransformer();
/* 111:268 */         transformer.transform(new StreamSource(xmlbuf), new StreamResult(pw));
/* 112:    */       }
/* 113:270 */       System.out.println("Primed the pump!");
/* 114:271 */       showStatus("Ready to go!");
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:275 */       showStatus("Could not prime the pump!");
/* 119:276 */       System.out.println("Could not prime the pump!");
/* 120:277 */       e.printStackTrace();
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void paint(Graphics g) {}
/* 125:    */   
/* 126:    */   public void stop()
/* 127:    */   {
/* 128:293 */     if (null != this.m_trustedWorker)
/* 129:    */     {
/* 130:295 */       this.m_trustedWorker.stop();
/* 131:    */       
/* 132:    */ 
/* 133:298 */       this.m_trustedWorker = null;
/* 134:    */     }
/* 135:301 */     this.m_styleURLOfCached = null;
/* 136:302 */     this.m_documentURLOfCached = null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void destroy()
/* 140:    */   {
/* 141:310 */     if (null != this.m_trustedWorker)
/* 142:    */     {
/* 143:312 */       this.m_trustedWorker.stop();
/* 144:    */       
/* 145:    */ 
/* 146:315 */       this.m_trustedWorker = null;
/* 147:    */     }
/* 148:317 */     this.m_styleURLOfCached = null;
/* 149:318 */     this.m_documentURLOfCached = null;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setStyleURL(String urlString)
/* 153:    */   {
/* 154:328 */     this.m_styleURL = urlString;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDocumentURL(String urlString)
/* 158:    */   {
/* 159:338 */     this.m_documentURL = urlString;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void freeCache()
/* 163:    */   {
/* 164:348 */     this.m_styleURLOfCached = null;
/* 165:349 */     this.m_documentURLOfCached = null;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setStyleSheetAttribute(String nameOfIDAttrOfElemToModify, String elemId, String attrName, String value)
/* 169:    */   {
/* 170:364 */     this.m_nameOfIDAttrOfElemToModify = nameOfIDAttrOfElemToModify;
/* 171:365 */     this.m_elemIdToModify = elemId;
/* 172:366 */     this.m_attrNameToSet = attrName;
/* 173:367 */     this.m_attrValueToSet = value;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setStylesheetParam(String key, String expr)
/* 177:    */   {
/* 178:385 */     this.m_parameters.put(key, expr);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String escapeString(String s)
/* 182:    */   {
/* 183:398 */     StringBuffer sb = new StringBuffer();
/* 184:399 */     int length = s.length();
/* 185:401 */     for (int i = 0; i < length; i++)
/* 186:    */     {
/* 187:403 */       char ch = s.charAt(i);
/* 188:405 */       if ('<' == ch)
/* 189:    */       {
/* 190:407 */         sb.append("&lt;");
/* 191:    */       }
/* 192:409 */       else if ('>' == ch)
/* 193:    */       {
/* 194:411 */         sb.append("&gt;");
/* 195:    */       }
/* 196:413 */       else if ('&' == ch)
/* 197:    */       {
/* 198:415 */         sb.append("&amp;");
/* 199:    */       }
/* 200:417 */       else if ((55296 <= ch) && (ch < 56320))
/* 201:    */       {
/* 202:422 */         if (i + 1 >= length) {
/* 203:424 */           throw new RuntimeException(XSLMessages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(ch) }));
/* 204:    */         }
/* 205:433 */         int next = s.charAt(++i);
/* 206:435 */         if ((56320 > next) || (next >= 57344)) {
/* 207:436 */           throw new RuntimeException(XSLMessages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(ch) + " " + Integer.toHexString(next) }));
/* 208:    */         }
/* 209:444 */         next = (ch - 55296 << 10) + next - 56320 + 65536;
/* 210:    */         
/* 211:446 */         sb.append("&#x");
/* 212:447 */         sb.append(Integer.toHexString(next));
/* 213:448 */         sb.append(";");
/* 214:    */       }
/* 215:    */       else
/* 216:    */       {
/* 217:452 */         sb.append(ch);
/* 218:    */       }
/* 219:    */     }
/* 220:455 */     return sb.toString();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getHtmlText()
/* 224:    */   {
/* 225:467 */     this.m_trustedAgent.m_getData = true;
/* 226:468 */     this.m_callThread = Thread.currentThread();
/* 227:    */     try
/* 228:    */     {
/* 229:471 */       synchronized (this.m_callThread)
/* 230:    */       {
/* 231:473 */         this.m_callThread.wait();
/* 232:    */       }
/* 233:    */     }
/* 234:    */     catch (InterruptedException ie)
/* 235:    */     {
/* 236:478 */       System.out.println(ie.getMessage());
/* 237:    */     }
/* 238:480 */     return this.m_htmlText;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getTreeAsText(String treeURL)
/* 242:    */     throws IOException
/* 243:    */   {
/* 244:494 */     this.m_treeURL = treeURL;
/* 245:495 */     this.m_trustedAgent.m_getData = true;
/* 246:496 */     this.m_trustedAgent.m_getSource = true;
/* 247:497 */     this.m_callThread = Thread.currentThread();
/* 248:    */     try
/* 249:    */     {
/* 250:500 */       synchronized (this.m_callThread)
/* 251:    */       {
/* 252:502 */         this.m_callThread.wait();
/* 253:    */       }
/* 254:    */     }
/* 255:    */     catch (InterruptedException ie)
/* 256:    */     {
/* 257:507 */       System.out.println(ie.getMessage());
/* 258:    */     }
/* 259:509 */     return this.m_sourceText;
/* 260:    */   }
/* 261:    */   
/* 262:    */   private String getSource()
/* 263:    */     throws TransformerException
/* 264:    */   {
/* 265:520 */     StringWriter osw = new StringWriter();
/* 266:521 */     PrintWriter pw = new PrintWriter(osw, false);
/* 267:522 */     String text = "";
/* 268:    */     try
/* 269:    */     {
/* 270:525 */       URL docURL = new URL(this.m_documentBase, this.m_treeURL);
/* 271:526 */       synchronized (this.m_tfactory)
/* 272:    */       {
/* 273:528 */         Transformer transformer = this.m_tfactory.newTransformer();
/* 274:529 */         StreamSource source = new StreamSource(docURL.toString());
/* 275:530 */         StreamResult result = new StreamResult(pw);
/* 276:531 */         transformer.transform(source, result);
/* 277:532 */         text = osw.toString();
/* 278:    */       }
/* 279:    */     }
/* 280:    */     catch (MalformedURLException e)
/* 281:    */     {
/* 282:537 */       e.printStackTrace();
/* 283:538 */       throw new RuntimeException(e.getMessage());
/* 284:    */     }
/* 285:    */     catch (Exception any_error)
/* 286:    */     {
/* 287:542 */       any_error.printStackTrace();
/* 288:    */     }
/* 289:544 */     return text;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String getSourceTreeAsText()
/* 293:    */     throws Exception
/* 294:    */   {
/* 295:557 */     return getTreeAsText(this.m_documentURL);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String getStyleTreeAsText()
/* 299:    */     throws Exception
/* 300:    */   {
/* 301:570 */     return getTreeAsText(this.m_styleURL);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String getResultTreeAsText()
/* 305:    */     throws Exception
/* 306:    */   {
/* 307:583 */     return escapeString(getHtmlText());
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String transformToHtml(String doc, String style)
/* 311:    */   {
/* 312:599 */     if (null != doc) {
/* 313:601 */       this.m_documentURL = doc;
/* 314:    */     }
/* 315:604 */     if (null != style) {
/* 316:606 */       this.m_styleURL = style;
/* 317:    */     }
/* 318:609 */     return getHtmlText();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String transformToHtml(String doc)
/* 322:    */   {
/* 323:624 */     if (null != doc) {
/* 324:626 */       this.m_documentURL = doc;
/* 325:    */     }
/* 326:629 */     this.m_styleURL = null;
/* 327:    */     
/* 328:631 */     return getHtmlText();
/* 329:    */   }
/* 330:    */   
/* 331:    */   private String processTransformation()
/* 332:    */     throws TransformerException
/* 333:    */   {
/* 334:644 */     String htmlData = null;
/* 335:645 */     showStatus("Waiting for Transformer and Parser to finish loading and JITing...");
/* 336:647 */     synchronized (this.m_tfactory)
/* 337:    */     {
/* 338:649 */       URL documentURL = null;
/* 339:650 */       URL styleURL = null;
/* 340:651 */       StringWriter osw = new StringWriter();
/* 341:652 */       PrintWriter pw = new PrintWriter(osw, false);
/* 342:653 */       StreamResult result = new StreamResult(pw);
/* 343:    */       
/* 344:655 */       showStatus("Begin Transformation...");
/* 345:    */       try
/* 346:    */       {
/* 347:658 */         documentURL = new URL(this.m_codeBase, this.m_documentURL);
/* 348:659 */         StreamSource xmlSource = new StreamSource(documentURL.toString());
/* 349:    */         
/* 350:661 */         styleURL = new URL(this.m_codeBase, this.m_styleURL);
/* 351:662 */         StreamSource xslSource = new StreamSource(styleURL.toString());
/* 352:    */         
/* 353:664 */         Transformer transformer = this.m_tfactory.newTransformer(xslSource);
/* 354:    */         
/* 355:    */ 
/* 356:667 */         Enumeration m_keys = this.m_parameters.keys();
/* 357:668 */         while (m_keys.hasMoreElements())
/* 358:    */         {
/* 359:669 */           Object key = m_keys.nextElement();
/* 360:670 */           Object expression = this.m_parameters.get(key);
/* 361:671 */           transformer.setParameter((String)key, expression);
/* 362:    */         }
/* 363:673 */         transformer.transform(xmlSource, result);
/* 364:    */       }
/* 365:    */       catch (TransformerConfigurationException tfe)
/* 366:    */       {
/* 367:677 */         tfe.printStackTrace();
/* 368:678 */         throw new RuntimeException(tfe.getMessage());
/* 369:    */       }
/* 370:    */       catch (MalformedURLException e)
/* 371:    */       {
/* 372:682 */         e.printStackTrace();
/* 373:683 */         throw new RuntimeException(e.getMessage());
/* 374:    */       }
/* 375:686 */       showStatus("Transformation Done!");
/* 376:687 */       htmlData = osw.toString();
/* 377:    */     }
/* 378:689 */     return htmlData;
/* 379:    */   }
/* 380:    */   
/* 381:    */   class TrustedAgent
/* 382:    */     implements Runnable
/* 383:    */   {
/* 384:704 */     public boolean m_getData = false;
/* 385:709 */     public boolean m_getSource = false;
/* 386:    */     
/* 387:    */     TrustedAgent() {}
/* 388:    */     
/* 389:    */     public void run()
/* 390:    */     {
/* 391:    */       for (;;)
/* 392:    */       {
/* 393:719 */         Thread.yield();
/* 394:721 */         if (this.m_getData) {
/* 395:    */           try
/* 396:    */           {
/* 397:725 */             this.m_getData = false;
/* 398:726 */             XSLTProcessorApplet.this.m_htmlText = null;
/* 399:727 */             XSLTProcessorApplet.this.m_sourceText = null;
/* 400:728 */             if (this.m_getSource)
/* 401:    */             {
/* 402:730 */               this.m_getSource = false;
/* 403:731 */               XSLTProcessorApplet.this.m_sourceText = XSLTProcessorApplet.this.getSource();
/* 404:    */             }
/* 405:    */             else
/* 406:    */             {
/* 407:734 */               XSLTProcessorApplet.this.m_htmlText = XSLTProcessorApplet.this.processTransformation();
/* 408:    */             }
/* 409:    */           }
/* 410:    */           catch (Exception e)
/* 411:    */           {
/* 412:738 */             e.printStackTrace();
/* 413:    */           }
/* 414:    */           finally
/* 415:    */           {
/* 416:742 */             synchronized (XSLTProcessorApplet.this.m_callThread)
/* 417:    */             {
/* 418:744 */               XSLTProcessorApplet.this.m_callThread.notify();
/* 419:    */             }
/* 420:    */           }
/* 421:    */         } else {
/* 422:    */           try
/* 423:    */           {
/* 424:752 */             Thread.sleep(50L);
/* 425:    */           }
/* 426:    */           catch (InterruptedException ie)
/* 427:    */           {
/* 428:756 */             ie.printStackTrace();
/* 429:    */           }
/* 430:    */         }
/* 431:    */       }
/* 432:    */     }
/* 433:    */   }
/* 434:    */   
/* 435:    */   private void readObject(ObjectInputStream inStream)
/* 436:    */     throws IOException, ClassNotFoundException
/* 437:    */   {
/* 438:771 */     inStream.defaultReadObject();
/* 439:    */     
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:777 */     this.m_tfactory = TransformerFactory.newInstance();
/* 445:    */   }
/* 446:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.client.XSLTProcessorApplet
 * JD-Core Version:    0.7.0.1
 */