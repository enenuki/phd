/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.util.Vector;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xalan.trace.TraceManager;
/*   8:    */ import org.apache.xalan.transformer.NodeSorter;
/*   9:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  10:    */ import org.apache.xml.dtm.DTM;
/*  11:    */ import org.apache.xml.dtm.DTMIterator;
/*  12:    */ import org.apache.xml.utils.IntStack;
/*  13:    */ import org.apache.xpath.Expression;
/*  14:    */ import org.apache.xpath.ExpressionOwner;
/*  15:    */ import org.apache.xpath.SourceTreeManager;
/*  16:    */ import org.apache.xpath.XPath;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.objects.XNodeSet;
/*  19:    */ import org.apache.xpath.objects.XObject;
/*  20:    */ 
/*  21:    */ public class ElemForEach
/*  22:    */   extends ElemTemplateElement
/*  23:    */   implements ExpressionOwner
/*  24:    */ {
/*  25:    */   static final long serialVersionUID = 6018140636363583690L;
/*  26:    */   static final boolean DEBUG = false;
/*  27: 75 */   public boolean m_doc_cache_off = false;
/*  28: 86 */   protected Expression m_selectExpression = null;
/*  29: 93 */   protected XPath m_xpath = null;
/*  30:    */   
/*  31:    */   public void setSelect(XPath xpath)
/*  32:    */   {
/*  33:102 */     this.m_selectExpression = xpath.getExpression();
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37:106 */     this.m_xpath = xpath;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Expression getSelect()
/*  41:    */   {
/*  42:116 */     return this.m_selectExpression;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void compose(StylesheetRoot sroot)
/*  46:    */     throws TransformerException
/*  47:    */   {
/*  48:132 */     super.compose(sroot);
/*  49:    */     
/*  50:134 */     int length = getSortElemCount();
/*  51:136 */     for (int i = 0; i < length; i++) {
/*  52:138 */       getSortElem(i).compose(sroot);
/*  53:    */     }
/*  54:141 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  55:143 */     if (null != this.m_selectExpression) {
/*  56:144 */       this.m_selectExpression.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  57:    */     } else {
/*  58:148 */       this.m_selectExpression = getStylesheetRoot().m_selectDefault.getExpression();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void endCompose(StylesheetRoot sroot)
/*  63:    */     throws TransformerException
/*  64:    */   {
/*  65:158 */     int length = getSortElemCount();
/*  66:160 */     for (int i = 0; i < length; i++) {
/*  67:162 */       getSortElem(i).endCompose(sroot);
/*  68:    */     }
/*  69:165 */     super.endCompose(sroot);
/*  70:    */   }
/*  71:    */   
/*  72:191 */   protected Vector m_sortElems = null;
/*  73:    */   
/*  74:    */   public int getSortElemCount()
/*  75:    */   {
/*  76:199 */     return this.m_sortElems == null ? 0 : this.m_sortElems.size();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ElemSort getSortElem(int i)
/*  80:    */   {
/*  81:211 */     return (ElemSort)this.m_sortElems.elementAt(i);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setSortElem(ElemSort sortElem)
/*  85:    */   {
/*  86:222 */     if (null == this.m_sortElems) {
/*  87:223 */       this.m_sortElems = new Vector();
/*  88:    */     }
/*  89:225 */     this.m_sortElems.addElement(sortElem);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getXSLToken()
/*  93:    */   {
/*  94:236 */     return 28;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getNodeName()
/*  98:    */   {
/*  99:246 */     return "for-each";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void execute(TransformerImpl transformer)
/* 103:    */     throws TransformerException
/* 104:    */   {
/* 105:259 */     transformer.pushCurrentTemplateRuleIsNull(true);
/* 106:260 */     if (transformer.getDebug()) {
/* 107:261 */       transformer.getTraceManager().fireTraceEvent(this);
/* 108:    */     }
/* 109:    */     try
/* 110:    */     {
/* 111:265 */       transformSelectedNodes(transformer);
/* 112:    */     }
/* 113:    */     finally
/* 114:    */     {
/* 115:269 */       if (transformer.getDebug()) {
/* 116:270 */         transformer.getTraceManager().fireTraceEndEvent(this);
/* 117:    */       }
/* 118:271 */       transformer.popCurrentTemplateRuleIsNull();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected ElemTemplateElement getTemplateMatch()
/* 123:    */   {
/* 124:283 */     return this;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DTMIterator sortNodes(XPathContext xctxt, Vector keys, DTMIterator sourceNodes)
/* 128:    */     throws TransformerException
/* 129:    */   {
/* 130:303 */     NodeSorter sorter = new NodeSorter(xctxt);
/* 131:304 */     sourceNodes.setShouldCacheNodes(true);
/* 132:305 */     sourceNodes.runTo(-1);
/* 133:306 */     xctxt.pushContextNodeList(sourceNodes);
/* 134:    */     try
/* 135:    */     {
/* 136:310 */       sorter.sort(sourceNodes, keys, xctxt);
/* 137:311 */       sourceNodes.setCurrentPos(0);
/* 138:    */     }
/* 139:    */     finally
/* 140:    */     {
/* 141:315 */       xctxt.popContextNodeList();
/* 142:    */     }
/* 143:318 */     return sourceNodes;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void transformSelectedNodes(TransformerImpl transformer)
/* 147:    */     throws TransformerException
/* 148:    */   {
/* 149:333 */     XPathContext xctxt = transformer.getXPathContext();
/* 150:334 */     int sourceNode = xctxt.getCurrentNode();
/* 151:335 */     DTMIterator sourceNodes = this.m_selectExpression.asIterator(xctxt, sourceNode);
/* 152:    */     try
/* 153:    */     {
/* 154:341 */       Vector keys = this.m_sortElems == null ? null : transformer.processSortKeys(this, sourceNode);
/* 155:346 */       if (null != keys) {
/* 156:347 */         sourceNodes = sortNodes(xctxt, keys, sourceNodes);
/* 157:    */       }
/* 158:349 */       if (transformer.getDebug())
/* 159:    */       {
/* 160:366 */         Expression expr = this.m_xpath.getExpression();
/* 161:367 */         XObject xObject = expr.execute(xctxt);
/* 162:368 */         int current = xctxt.getCurrentNode();
/* 163:369 */         transformer.getTraceManager().fireSelectedEvent(current, this, "select", this.m_xpath, xObject);
/* 164:    */       }
/* 165:379 */       xctxt.pushCurrentNode(-1);
/* 166:    */       
/* 167:381 */       IntStack currentNodes = xctxt.getCurrentNodeStack();
/* 168:    */       
/* 169:383 */       xctxt.pushCurrentExpressionNode(-1);
/* 170:    */       
/* 171:385 */       IntStack currentExpressionNodes = xctxt.getCurrentExpressionNodeStack();
/* 172:    */       
/* 173:387 */       xctxt.pushSAXLocatorNull();
/* 174:388 */       xctxt.pushContextNodeList(sourceNodes);
/* 175:389 */       transformer.pushElemTemplateElement(null);
/* 176:    */       
/* 177:    */ 
/* 178:    */ 
/* 179:393 */       DTM dtm = xctxt.getDTM(sourceNode);
/* 180:394 */       int docID = sourceNode & 0xFFFF0000;
/* 181:    */       int child;
/* 182:397 */       while (-1 != (child = sourceNodes.nextNode()))
/* 183:    */       {
/* 184:    */         int i;
/* 185:399 */         currentNodes.setTop(i);
/* 186:400 */         currentExpressionNodes.setTop(i);
/* 187:402 */         if ((i & 0xFFFF0000) != docID)
/* 188:    */         {
/* 189:404 */           dtm = xctxt.getDTM(i);
/* 190:405 */           docID = i & 0xFFFF0000;
/* 191:    */         }
/* 192:409 */         int nodeType = dtm.getNodeType(i);
/* 193:412 */         if (transformer.getDebug()) {
/* 194:414 */           transformer.getTraceManager().fireTraceEvent(this);
/* 195:    */         }
/* 196:420 */         for (ElemTemplateElement t = this.m_firstChild; t != null; t = t.m_nextSibling)
/* 197:    */         {
/* 198:423 */           xctxt.setSAXLocator(t);
/* 199:424 */           transformer.setCurrentElement(t);
/* 200:425 */           t.execute(transformer);
/* 201:    */         }
/* 202:428 */         if (transformer.getDebug())
/* 203:    */         {
/* 204:432 */           transformer.setCurrentElement(null);
/* 205:433 */           transformer.getTraceManager().fireTraceEndEvent(this);
/* 206:    */         }
/* 207:447 */         if (this.m_doc_cache_off)
/* 208:    */         {
/* 209:455 */           xctxt.getSourceTreeManager().removeDocumentFromCache(dtm.getDocument());
/* 210:456 */           xctxt.release(dtm, false);
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:    */     finally
/* 215:    */     {
/* 216:462 */       if (transformer.getDebug()) {
/* 217:463 */         transformer.getTraceManager().fireSelectedEndEvent(sourceNode, this, "select", new XPath(this.m_selectExpression), new XNodeSet(sourceNodes));
/* 218:    */       }
/* 219:467 */       xctxt.popSAXLocator();
/* 220:468 */       xctxt.popContextNodeList();
/* 221:469 */       transformer.popElemTemplateElement();
/* 222:470 */       xctxt.popCurrentExpressionNode();
/* 223:471 */       xctxt.popCurrentNode();
/* 224:472 */       sourceNodes.detach();
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/* 229:    */   {
/* 230:491 */     int type = newChild.getXSLToken();
/* 231:493 */     if (64 == type)
/* 232:    */     {
/* 233:495 */       setSortElem((ElemSort)newChild);
/* 234:    */       
/* 235:497 */       return newChild;
/* 236:    */     }
/* 237:500 */     return super.appendChild(newChild);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void callChildVisitors(XSLTVisitor visitor, boolean callAttributes)
/* 241:    */   {
/* 242:509 */     if ((callAttributes) && (null != this.m_selectExpression)) {
/* 243:510 */       this.m_selectExpression.callVisitors(this, visitor);
/* 244:    */     }
/* 245:512 */     int length = getSortElemCount();
/* 246:514 */     for (int i = 0; i < length; i++) {
/* 247:516 */       getSortElem(i).callVisitors(visitor);
/* 248:    */     }
/* 249:519 */     super.callChildVisitors(visitor, callAttributes);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public Expression getExpression()
/* 253:    */   {
/* 254:527 */     return this.m_selectExpression;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setExpression(Expression exp)
/* 258:    */   {
/* 259:535 */     exp.exprSetParent(this);
/* 260:536 */     this.m_selectExpression = exp;
/* 261:    */   }
/* 262:    */   
/* 263:    */   private void readObject(ObjectInputStream os)
/* 264:    */     throws IOException, ClassNotFoundException
/* 265:    */   {
/* 266:546 */     os.defaultReadObject();
/* 267:547 */     this.m_xpath = null;
/* 268:    */   }
/* 269:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemForEach
 * JD-Core Version:    0.7.0.1
 */