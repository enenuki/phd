/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xml.utils.QName;
/*   8:    */ import org.apache.xpath.Expression;
/*   9:    */ import org.apache.xpath.VariableStack;
/*  10:    */ import org.apache.xpath.XPath;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ import org.apache.xpath.objects.XRTreeFrag;
/*  14:    */ import org.apache.xpath.objects.XRTreeFragSelectWrapper;
/*  15:    */ import org.apache.xpath.objects.XString;
/*  16:    */ 
/*  17:    */ public class ElemVariable
/*  18:    */   extends ElemTemplateElement
/*  19:    */ {
/*  20:    */   static final long serialVersionUID = 9111131075322790061L;
/*  21:    */   protected int m_index;
/*  22: 67 */   int m_frameSize = -1;
/*  23:    */   private XPath m_selectPattern;
/*  24:    */   protected QName m_qname;
/*  25:    */   
/*  26:    */   public ElemVariable() {}
/*  27:    */   
/*  28:    */   public void setIndex(int index)
/*  29:    */   {
/*  30: 77 */     this.m_index = index;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getIndex()
/*  34:    */   {
/*  35: 87 */     return this.m_index;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setSelect(XPath v)
/*  39:    */   {
/*  40:108 */     this.m_selectPattern = v;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public XPath getSelect()
/*  44:    */   {
/*  45:123 */     return this.m_selectPattern;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setName(QName v)
/*  49:    */   {
/*  50:144 */     this.m_qname = v;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public QName getName()
/*  54:    */   {
/*  55:159 */     return this.m_qname;
/*  56:    */   }
/*  57:    */   
/*  58:166 */   private boolean m_isTopLevel = false;
/*  59:    */   
/*  60:    */   public void setIsTopLevel(boolean v)
/*  61:    */   {
/*  62:177 */     this.m_isTopLevel = v;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean getIsTopLevel()
/*  66:    */   {
/*  67:189 */     return this.m_isTopLevel;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getXSLToken()
/*  71:    */   {
/*  72:201 */     return 73;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getNodeName()
/*  76:    */   {
/*  77:211 */     return "variable";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ElemVariable(ElemVariable param)
/*  81:    */     throws TransformerException
/*  82:    */   {
/*  83:224 */     this.m_selectPattern = param.m_selectPattern;
/*  84:225 */     this.m_qname = param.m_qname;
/*  85:226 */     this.m_isTopLevel = param.m_isTopLevel;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void execute(TransformerImpl transformer)
/*  89:    */     throws TransformerException
/*  90:    */   {
/*  91:243 */     if (transformer.getDebug()) {
/*  92:244 */       transformer.getTraceManager().fireTraceEvent(this);
/*  93:    */     }
/*  94:246 */     int sourceNode = transformer.getXPathContext().getCurrentNode();
/*  95:    */     
/*  96:248 */     XObject var = getValue(transformer, sourceNode);
/*  97:    */     
/*  98:    */ 
/*  99:251 */     transformer.getXPathContext().getVarStack().setLocalVariable(this.m_index, var);
/* 100:253 */     if (transformer.getDebug()) {
/* 101:254 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public XObject getValue(TransformerImpl transformer, int sourceNode)
/* 106:    */     throws TransformerException
/* 107:    */   {
/* 108:272 */     XPathContext xctxt = transformer.getXPathContext();
/* 109:    */     
/* 110:274 */     xctxt.pushCurrentNode(sourceNode);
/* 111:    */     XObject var;
/* 112:    */     try
/* 113:    */     {
/* 114:278 */       if (null != this.m_selectPattern)
/* 115:    */       {
/* 116:280 */         var = this.m_selectPattern.execute(xctxt, sourceNode, this);
/* 117:    */         
/* 118:282 */         var.allowDetachToRelease(false);
/* 119:284 */         if (transformer.getDebug()) {
/* 120:285 */           transformer.getTraceManager().fireSelectedEvent(sourceNode, this, "select", this.m_selectPattern, var);
/* 121:    */         }
/* 122:    */       }
/* 123:288 */       else if (null == getFirstChildElem())
/* 124:    */       {
/* 125:290 */         var = XString.EMPTYSTRING;
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:    */         int df;
/* 130:    */         try
/* 131:    */         {
/* 132:309 */           if ((this.m_parentNode instanceof Stylesheet)) {
/* 133:310 */             df = transformer.transformToGlobalRTF(this);
/* 134:    */           } else {
/* 135:312 */             df = transformer.transformToRTF(this);
/* 136:    */           }
/* 137:    */         }
/* 138:    */         finally {}
/* 139:318 */         var = new XRTreeFrag(df, xctxt, this);
/* 140:    */       }
/* 141:    */     }
/* 142:    */     finally
/* 143:    */     {
/* 144:323 */       xctxt.popCurrentNode();
/* 145:    */     }
/* 146:326 */     return var;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void compose(StylesheetRoot sroot)
/* 150:    */     throws TransformerException
/* 151:    */   {
/* 152:339 */     if ((null == this.m_selectPattern) && (sroot.getOptimizer()))
/* 153:    */     {
/* 154:342 */       XPath newSelect = rewriteChildToExpression(this);
/* 155:343 */       if (null != newSelect) {
/* 156:344 */         this.m_selectPattern = newSelect;
/* 157:    */       }
/* 158:    */     }
/* 159:347 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:351 */     Vector vnames = cstate.getVariableNames();
/* 164:352 */     if (null != this.m_selectPattern) {
/* 165:353 */       this.m_selectPattern.fixupVariables(vnames, cstate.getGlobalsSize());
/* 166:    */     }
/* 167:357 */     if ((!(this.m_parentNode instanceof Stylesheet)) && (this.m_qname != null)) {
/* 168:359 */       this.m_index = (cstate.addVariableName(this.m_qname) - cstate.getGlobalsSize());
/* 169:361 */     } else if ((this.m_parentNode instanceof Stylesheet)) {
/* 170:366 */       cstate.resetStackFrameSize();
/* 171:    */     }
/* 172:371 */     super.compose(sroot);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void endCompose(StylesheetRoot sroot)
/* 176:    */     throws TransformerException
/* 177:    */   {
/* 178:381 */     super.endCompose(sroot);
/* 179:382 */     if ((this.m_parentNode instanceof Stylesheet))
/* 180:    */     {
/* 181:384 */       StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/* 182:385 */       this.m_frameSize = cstate.getFrameSize();
/* 183:386 */       cstate.resetStackFrameSize();
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   static XPath rewriteChildToExpression(ElemTemplateElement varElem)
/* 188:    */     throws TransformerException
/* 189:    */   {
/* 190:416 */     ElemTemplateElement t = varElem.getFirstChildElem();
/* 191:420 */     if ((null != t) && (null == t.getNextSiblingElem()))
/* 192:    */     {
/* 193:422 */       int etype = t.getXSLToken();
/* 194:424 */       if (30 == etype)
/* 195:    */       {
/* 196:426 */         ElemValueOf valueof = (ElemValueOf)t;
/* 197:429 */         if ((!valueof.getDisableOutputEscaping()) && (valueof.getDOMBackPointer() == null))
/* 198:    */         {
/* 199:432 */           varElem.m_firstChild = null;
/* 200:    */           
/* 201:434 */           return new XPath(new XRTreeFragSelectWrapper(valueof.getSelect().getExpression()));
/* 202:    */         }
/* 203:    */       }
/* 204:437 */       else if (78 == etype)
/* 205:    */       {
/* 206:439 */         ElemTextLiteral lit = (ElemTextLiteral)t;
/* 207:441 */         if ((!lit.getDisableOutputEscaping()) && (lit.getDOMBackPointer() == null))
/* 208:    */         {
/* 209:444 */           String str = lit.getNodeValue();
/* 210:445 */           XString xstr = new XString(str);
/* 211:    */           
/* 212:447 */           varElem.m_firstChild = null;
/* 213:    */           
/* 214:449 */           return new XPath(new XRTreeFragSelectWrapper(xstr));
/* 215:    */         }
/* 216:    */       }
/* 217:    */     }
/* 218:454 */     return null;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void recompose(StylesheetRoot root)
/* 222:    */   {
/* 223:464 */     root.recomposeVariables(this);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setParentElem(ElemTemplateElement p)
/* 227:    */   {
/* 228:474 */     super.setParentElem(p);
/* 229:475 */     p.m_hasVariableDecl = true;
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected boolean accept(XSLTVisitor visitor)
/* 233:    */   {
/* 234:487 */     return visitor.visitVariableOrParamDecl(this);
/* 235:    */   }
/* 236:    */   
/* 237:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 238:    */   {
/* 239:497 */     if (null != this.m_selectPattern) {
/* 240:498 */       this.m_selectPattern.getExpression().callVisitors(this.m_selectPattern, visitor);
/* 241:    */     }
/* 242:499 */     super.callChildVisitors(visitor, callAttrs);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isPsuedoVar()
/* 246:    */   {
/* 247:508 */     String ns = this.m_qname.getNamespaceURI();
/* 248:509 */     if ((null != ns) && (ns.equals("http://xml.apache.org/xalan/psuedovar"))) {
/* 249:511 */       if (this.m_qname.getLocalName().startsWith("#")) {
/* 250:512 */         return true;
/* 251:    */       }
/* 252:    */     }
/* 253:514 */     return false;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ElemTemplateElement appendChild(ElemTemplateElement elem)
/* 257:    */   {
/* 258:529 */     if (this.m_selectPattern != null)
/* 259:    */     {
/* 260:531 */       error("ER_CANT_HAVE_CONTENT_AND_SELECT", new Object[] { "xsl:" + getNodeName() });
/* 261:    */       
/* 262:533 */       return null;
/* 263:    */     }
/* 264:535 */     return super.appendChild(elem);
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemVariable
 * JD-Core Version:    0.7.0.1
 */