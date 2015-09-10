/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.ErrorListener;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.trace.TraceManager;
/*   7:    */ import org.apache.xalan.transformer.StackGuard;
/*   8:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   9:    */ import org.apache.xml.dtm.DTM;
/*  10:    */ import org.apache.xml.dtm.DTMIterator;
/*  11:    */ import org.apache.xml.serializer.SerializationHandler;
/*  12:    */ import org.apache.xml.utils.IntStack;
/*  13:    */ import org.apache.xml.utils.QName;
/*  14:    */ import org.apache.xpath.Expression;
/*  15:    */ import org.apache.xpath.VariableStack;
/*  16:    */ import org.apache.xpath.XPath;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.objects.XNodeSet;
/*  19:    */ import org.apache.xpath.objects.XObject;
/*  20:    */ import org.xml.sax.SAXException;
/*  21:    */ 
/*  22:    */ public class ElemApplyTemplates
/*  23:    */   extends ElemCallTemplate
/*  24:    */ {
/*  25:    */   static final long serialVersionUID = 2903125371542621004L;
/*  26: 60 */   private QName m_mode = null;
/*  27:    */   
/*  28:    */   public void setMode(QName mode)
/*  29:    */   {
/*  30: 69 */     this.m_mode = mode;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public QName getMode()
/*  34:    */   {
/*  35: 79 */     return this.m_mode;
/*  36:    */   }
/*  37:    */   
/*  38: 89 */   private boolean m_isDefaultTemplate = false;
/*  39:    */   
/*  40:    */   public void setIsDefaultTemplate(boolean b)
/*  41:    */   {
/*  42:107 */     this.m_isDefaultTemplate = b;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getXSLToken()
/*  46:    */   {
/*  47:118 */     return 50;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void compose(StylesheetRoot sroot)
/*  51:    */     throws TransformerException
/*  52:    */   {
/*  53:129 */     super.compose(sroot);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getNodeName()
/*  57:    */   {
/*  58:139 */     return "apply-templates";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void execute(TransformerImpl transformer)
/*  62:    */     throws TransformerException
/*  63:    */   {
/*  64:153 */     transformer.pushCurrentTemplateRuleIsNull(false);
/*  65:    */     
/*  66:155 */     boolean pushMode = false;
/*  67:    */     try
/*  68:    */     {
/*  69:163 */       QName mode = transformer.getMode();
/*  70:165 */       if (!this.m_isDefaultTemplate) {
/*  71:167 */         if (((null == mode) && (null != this.m_mode)) || ((null != mode) && (!mode.equals(this.m_mode))))
/*  72:    */         {
/*  73:170 */           pushMode = true;
/*  74:    */           
/*  75:172 */           transformer.pushMode(this.m_mode);
/*  76:    */         }
/*  77:    */       }
/*  78:175 */       if (transformer.getDebug()) {
/*  79:176 */         transformer.getTraceManager().fireTraceEvent(this);
/*  80:    */       }
/*  81:178 */       transformSelectedNodes(transformer);
/*  82:    */     }
/*  83:    */     finally
/*  84:    */     {
/*  85:182 */       if (transformer.getDebug()) {
/*  86:183 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  87:    */       }
/*  88:185 */       if (pushMode) {
/*  89:186 */         transformer.popMode();
/*  90:    */       }
/*  91:188 */       transformer.popCurrentTemplateRuleIsNull();
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void transformSelectedNodes(TransformerImpl transformer)
/*  96:    */     throws TransformerException
/*  97:    */   {
/*  98:205 */     XPathContext xctxt = transformer.getXPathContext();
/*  99:206 */     int sourceNode = xctxt.getCurrentNode();
/* 100:207 */     DTMIterator sourceNodes = this.m_selectExpression.asIterator(xctxt, sourceNode);
/* 101:208 */     VariableStack vars = xctxt.getVarStack();
/* 102:209 */     int nParams = getParamElemCount();
/* 103:210 */     int thisframe = vars.getStackFrame();
/* 104:211 */     StackGuard guard = transformer.getStackGuard();
/* 105:212 */     boolean check = guard.getRecursionLimit() > -1;
/* 106:    */     
/* 107:214 */     boolean pushContextNodeListFlag = false;
/* 108:    */     try
/* 109:    */     {
/* 110:219 */       xctxt.pushCurrentNode(-1);
/* 111:220 */       xctxt.pushCurrentExpressionNode(-1);
/* 112:221 */       xctxt.pushSAXLocatorNull();
/* 113:222 */       transformer.pushElemTemplateElement(null);
/* 114:223 */       Vector keys = this.m_sortElems == null ? null : transformer.processSortKeys(this, sourceNode);
/* 115:228 */       if (null != keys) {
/* 116:229 */         sourceNodes = sortNodes(xctxt, keys, sourceNodes);
/* 117:    */       }
/* 118:231 */       if (transformer.getDebug()) {
/* 119:233 */         transformer.getTraceManager().fireSelectedEvent(sourceNode, this, "select", new XPath(this.m_selectExpression), new XNodeSet(sourceNodes));
/* 120:    */       }
/* 121:238 */       SerializationHandler rth = transformer.getSerializationHandler();
/* 122:    */       
/* 123:240 */       StylesheetRoot sroot = transformer.getStylesheet();
/* 124:241 */       TemplateList tl = sroot.getTemplateListComposed();
/* 125:242 */       boolean quiet = transformer.getQuietConflictWarnings();
/* 126:    */       
/* 127:    */ 
/* 128:245 */       DTM dtm = xctxt.getDTM(sourceNode);
/* 129:    */       
/* 130:247 */       int argsFrame = -1;
/* 131:    */       XObject obj;
/* 132:248 */       if (nParams > 0)
/* 133:    */       {
/* 134:253 */         argsFrame = vars.link(nParams);
/* 135:254 */         vars.setStackFrame(thisframe);
/* 136:256 */         for (int i = 0; i < nParams; i++)
/* 137:    */         {
/* 138:258 */           ElemWithParam ewp = this.m_paramElems[i];
/* 139:259 */           if (transformer.getDebug()) {
/* 140:260 */             transformer.getTraceManager().fireTraceEvent(ewp);
/* 141:    */           }
/* 142:261 */           obj = ewp.getValue(transformer, sourceNode);
/* 143:262 */           if (transformer.getDebug()) {
/* 144:263 */             transformer.getTraceManager().fireTraceEndEvent(ewp);
/* 145:    */           }
/* 146:265 */           vars.setLocalVariable(i, obj, argsFrame);
/* 147:    */         }
/* 148:267 */         vars.setStackFrame(argsFrame);
/* 149:    */       }
/* 150:270 */       xctxt.pushContextNodeList(sourceNodes);
/* 151:271 */       pushContextNodeListFlag = true;
/* 152:    */       
/* 153:273 */       IntStack currentNodes = xctxt.getCurrentNodeStack();
/* 154:    */       
/* 155:275 */       IntStack currentExpressionNodes = xctxt.getCurrentExpressionNodeStack();
/* 156:    */       int child;
/* 157:280 */       while (-1 != (child = sourceNodes.nextNode()))
/* 158:    */       {
/* 159:282 */         currentNodes.setTop(obj);
/* 160:283 */         currentExpressionNodes.setTop(obj);
/* 161:285 */         if (xctxt.getDTM(obj) != dtm) {
/* 162:287 */           dtm = xctxt.getDTM(obj);
/* 163:    */         }
/* 164:290 */         int exNodeType = dtm.getExpandedTypeID(obj);
/* 165:    */         
/* 166:292 */         int nodeType = dtm.getNodeType(obj);
/* 167:    */         
/* 168:294 */         QName mode = transformer.getMode();
/* 169:    */         
/* 170:296 */         ElemTemplate template = tl.getTemplateFast(xctxt, obj, exNodeType, mode, -1, quiet, dtm);
/* 171:301 */         if (null == template) {
/* 172:303 */           switch (nodeType)
/* 173:    */           {
/* 174:    */           case 1: 
/* 175:    */           case 11: 
/* 176:307 */             template = sroot.getDefaultRule();
/* 177:    */             
/* 178:309 */             break;
/* 179:    */           case 2: 
/* 180:    */           case 3: 
/* 181:    */           case 4: 
/* 182:315 */             transformer.pushPairCurrentMatched(sroot.getDefaultTextRule(), obj);
/* 183:316 */             transformer.setCurrentElement(sroot.getDefaultTextRule());
/* 184:    */             
/* 185:318 */             dtm.dispatchCharactersEvents(obj, rth, false);
/* 186:319 */             transformer.popCurrentMatched();
/* 187:320 */             break;
/* 188:    */           case 9: 
/* 189:322 */             template = sroot.getDefaultRootRule();
/* 190:323 */             break;
/* 191:    */           }
/* 192:    */         } else {
/* 193:332 */           transformer.setCurrentElement(template);
/* 194:    */         }
/* 195:335 */         transformer.pushPairCurrentMatched(template, obj);
/* 196:336 */         if (check) {
/* 197:337 */           guard.checkForInfinateLoop();
/* 198:    */         }
/* 199:    */         int currentFrameBottom;
/* 200:340 */         if (template.m_frameSize > 0)
/* 201:    */         {
/* 202:342 */           xctxt.pushRTFContext();
/* 203:343 */           currentFrameBottom = vars.getStackFrame();
/* 204:344 */           vars.link(template.m_frameSize);
/* 205:347 */           if (template.m_inArgsSize > 0)
/* 206:    */           {
/* 207:349 */             int paramIndex = 0;
/* 208:350 */             for (ElemTemplateElement elem = template.getFirstChildElem(); null != elem; elem = elem.getNextSiblingElem())
/* 209:    */             {
/* 210:353 */               if (41 != elem.getXSLToken()) {
/* 211:    */                 break;
/* 212:    */               }
/* 213:355 */               ElemParam ep = (ElemParam)elem;
/* 214:358 */               for (int i = 0; i < nParams; i++)
/* 215:    */               {
/* 216:360 */                 ElemWithParam ewp = this.m_paramElems[i];
/* 217:361 */                 if (ewp.m_qnameID == ep.m_qnameID)
/* 218:    */                 {
/* 219:363 */                   XObject obj = vars.getLocalVariable(i, argsFrame);
/* 220:364 */                   vars.setLocalVariable(paramIndex, obj);
/* 221:365 */                   break;
/* 222:    */                 }
/* 223:    */               }
/* 224:368 */               if (i == nParams) {
/* 225:369 */                 vars.setLocalVariable(paramIndex, null);
/* 226:    */               }
/* 227:373 */               paramIndex++;
/* 228:    */             }
/* 229:    */           }
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:379 */           currentFrameBottom = 0;
/* 234:    */         }
/* 235:382 */         if (transformer.getDebug()) {
/* 236:383 */           transformer.getTraceManager().fireTraceEvent(template);
/* 237:    */         }
/* 238:388 */         for (ElemTemplateElement t = template.m_firstChild; t != null; t = t.m_nextSibling)
/* 239:    */         {
/* 240:391 */           xctxt.setSAXLocator(t);
/* 241:    */           try
/* 242:    */           {
/* 243:394 */             transformer.pushElemTemplateElement(t);
/* 244:395 */             t.execute(transformer);
/* 245:    */           }
/* 246:    */           finally
/* 247:    */           {
/* 248:399 */             transformer.popElemTemplateElement();
/* 249:    */           }
/* 250:    */         }
/* 251:403 */         if (transformer.getDebug()) {
/* 252:404 */           transformer.getTraceManager().fireTraceEndEvent(template);
/* 253:    */         }
/* 254:406 */         if (template.m_frameSize > 0)
/* 255:    */         {
/* 256:423 */           vars.unlink(currentFrameBottom);
/* 257:424 */           xctxt.popRTFContext();
/* 258:    */         }
/* 259:427 */         transformer.popCurrentMatched();
/* 260:    */       }
/* 261:    */     }
/* 262:    */     catch (SAXException se)
/* 263:    */     {
/* 264:433 */       transformer.getErrorListener().fatalError(new TransformerException(se));
/* 265:    */     }
/* 266:    */     finally
/* 267:    */     {
/* 268:437 */       if (transformer.getDebug()) {
/* 269:438 */         transformer.getTraceManager().fireSelectedEndEvent(sourceNode, this, "select", new XPath(this.m_selectExpression), new XNodeSet(sourceNodes));
/* 270:    */       }
/* 271:443 */       if (nParams > 0) {
/* 272:444 */         vars.unlink(thisframe);
/* 273:    */       }
/* 274:445 */       xctxt.popSAXLocator();
/* 275:446 */       if (pushContextNodeListFlag) {
/* 276:446 */         xctxt.popContextNodeList();
/* 277:    */       }
/* 278:447 */       transformer.popElemTemplateElement();
/* 279:448 */       xctxt.popCurrentExpressionNode();
/* 280:449 */       xctxt.popCurrentNode();
/* 281:450 */       sourceNodes.detach();
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemApplyTemplates
 * JD-Core Version:    0.7.0.1
 */