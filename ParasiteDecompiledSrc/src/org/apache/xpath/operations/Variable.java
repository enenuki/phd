/*   1:    */ package org.apache.xpath.operations;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   6:    */ import org.apache.xalan.templates.ElemVariable;
/*   7:    */ import org.apache.xalan.templates.Stylesheet;
/*   8:    */ import org.apache.xalan.templates.StylesheetRoot;
/*   9:    */ import org.apache.xml.utils.PrefixResolver;
/*  10:    */ import org.apache.xml.utils.QName;
/*  11:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  12:    */ import org.apache.xpath.Expression;
/*  13:    */ import org.apache.xpath.ExpressionNode;
/*  14:    */ import org.apache.xpath.ExpressionOwner;
/*  15:    */ import org.apache.xpath.VariableStack;
/*  16:    */ import org.apache.xpath.XPath;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.XPathVisitor;
/*  19:    */ import org.apache.xpath.axes.PathComponent;
/*  20:    */ import org.apache.xpath.objects.XNodeSet;
/*  21:    */ import org.apache.xpath.objects.XObject;
/*  22:    */ import org.apache.xpath.res.XPATHMessages;
/*  23:    */ 
/*  24:    */ public class Variable
/*  25:    */   extends Expression
/*  26:    */   implements PathComponent
/*  27:    */ {
/*  28:    */   static final long serialVersionUID = -4334975375609297049L;
/*  29: 47 */   private boolean m_fixUpWasCalled = false;
/*  30:    */   protected QName m_qname;
/*  31:    */   protected int m_index;
/*  32:    */   
/*  33:    */   public void setIndex(int index)
/*  34:    */   {
/*  35: 68 */     this.m_index = index;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getIndex()
/*  39:    */   {
/*  40: 78 */     return this.m_index;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setIsGlobal(boolean isGlobal)
/*  44:    */   {
/*  45: 88 */     this.m_isGlobal = isGlobal;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean getGlobal()
/*  49:    */   {
/*  50: 98 */     return this.m_isGlobal;
/*  51:    */   }
/*  52:    */   
/*  53:105 */   protected boolean m_isGlobal = false;
/*  54:    */   static final String PSUEDOVARNAMESPACE = "http://xml.apache.org/xalan/psuedovar";
/*  55:    */   
/*  56:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  57:    */   {
/*  58:119 */     this.m_fixUpWasCalled = true;
/*  59:120 */     int sz = vars.size();
/*  60:122 */     for (int i = vars.size() - 1; i >= 0; i--)
/*  61:    */     {
/*  62:124 */       QName qn = (QName)vars.elementAt(i);
/*  63:126 */       if (qn.equals(this.m_qname))
/*  64:    */       {
/*  65:129 */         if (i < globalsSize)
/*  66:    */         {
/*  67:131 */           this.m_isGlobal = true;
/*  68:132 */           this.m_index = i;
/*  69:    */         }
/*  70:    */         else
/*  71:    */         {
/*  72:136 */           this.m_index = (i - globalsSize);
/*  73:    */         }
/*  74:139 */         return;
/*  75:    */       }
/*  76:    */     }
/*  77:143 */     String msg = XPATHMessages.createXPATHMessage("ER_COULD_NOT_FIND_VAR", new Object[] { this.m_qname.toString() });
/*  78:    */     
/*  79:    */ 
/*  80:146 */     TransformerException te = new TransformerException(msg, this);
/*  81:    */     
/*  82:148 */     throw new WrappedRuntimeException(te);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setQName(QName qname)
/*  86:    */   {
/*  87:160 */     this.m_qname = qname;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public QName getQName()
/*  91:    */   {
/*  92:170 */     return this.m_qname;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public XObject execute(XPathContext xctxt)
/*  96:    */     throws TransformerException
/*  97:    */   {
/*  98:188 */     return execute(xctxt, false);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public XObject execute(XPathContext xctxt, boolean destructiveOK)
/* 102:    */     throws TransformerException
/* 103:    */   {
/* 104:206 */     PrefixResolver xprefixResolver = xctxt.getNamespaceContext();
/* 105:    */     XObject result;
/* 106:211 */     if (this.m_fixUpWasCalled)
/* 107:    */     {
/* 108:213 */       if (this.m_isGlobal) {
/* 109:214 */         result = xctxt.getVarStack().getGlobalVariable(xctxt, this.m_index, destructiveOK);
/* 110:    */       } else {
/* 111:216 */         result = xctxt.getVarStack().getLocalVariable(xctxt, this.m_index, destructiveOK);
/* 112:    */       }
/* 113:    */     }
/* 114:    */     else {
/* 115:219 */       result = xctxt.getVarStack().getVariableOrParam(xctxt, this.m_qname);
/* 116:    */     }
/* 117:222 */     if (null == result)
/* 118:    */     {
/* 119:225 */       warn(xctxt, "WG_ILLEGAL_VARIABLE_REFERENCE", new Object[] { this.m_qname.getLocalPart() });
/* 120:    */       
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:231 */       result = new XNodeSet(xctxt.getDTMManager());
/* 126:    */     }
/* 127:234 */     return result;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ElemVariable getElemVariable()
/* 131:    */   {
/* 132:269 */     ElemVariable vvar = null;
/* 133:270 */     ExpressionNode owner = getExpressionOwner();
/* 134:272 */     if ((null != owner) && ((owner instanceof ElemTemplateElement)))
/* 135:    */     {
/* 136:275 */       ElemTemplateElement prev = (ElemTemplateElement)owner;
/* 137:278 */       if (!(prev instanceof Stylesheet)) {
/* 138:280 */         while ((prev != null) && (!(prev.getParentNode() instanceof Stylesheet)))
/* 139:    */         {
/* 140:282 */           ElemTemplateElement savedprev = prev;
/* 141:284 */           while (null != (prev = prev.getPreviousSiblingElem())) {
/* 142:286 */             if ((prev instanceof ElemVariable))
/* 143:    */             {
/* 144:288 */               vvar = (ElemVariable)prev;
/* 145:290 */               if (vvar.getName().equals(this.m_qname)) {
/* 146:292 */                 return vvar;
/* 147:    */               }
/* 148:294 */               vvar = null;
/* 149:    */             }
/* 150:    */           }
/* 151:297 */           prev = savedprev.getParentElem();
/* 152:    */         }
/* 153:    */       }
/* 154:300 */       if (prev != null) {
/* 155:301 */         vvar = prev.getStylesheetRoot().getVariableOrParamComposed(this.m_qname);
/* 156:    */       }
/* 157:    */     }
/* 158:303 */     return vvar;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isStableNumber()
/* 162:    */   {
/* 163:317 */     return true;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getAnalysisBits()
/* 167:    */   {
/* 168:326 */     ElemVariable vvar = getElemVariable();
/* 169:327 */     if (null != vvar)
/* 170:    */     {
/* 171:329 */       XPath xpath = vvar.getSelect();
/* 172:330 */       if (null != xpath)
/* 173:    */       {
/* 174:332 */         Expression expr = xpath.getExpression();
/* 175:333 */         if ((null != expr) && ((expr instanceof PathComponent))) {
/* 176:335 */           return ((PathComponent)expr).getAnalysisBits();
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:339 */     return 67108864;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 184:    */   {
/* 185:348 */     visitor.visitVariableRef(owner, this);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean deepEquals(Expression expr)
/* 189:    */   {
/* 190:355 */     if (!isSameClass(expr)) {
/* 191:356 */       return false;
/* 192:    */     }
/* 193:358 */     if (!this.m_qname.equals(((Variable)expr).m_qname)) {
/* 194:359 */       return false;
/* 195:    */     }
/* 196:363 */     if (getElemVariable() != ((Variable)expr).getElemVariable()) {
/* 197:364 */       return false;
/* 198:    */     }
/* 199:366 */     return true;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isPsuedoVarRef()
/* 203:    */   {
/* 204:377 */     String ns = this.m_qname.getNamespaceURI();
/* 205:378 */     if ((null != ns) && (ns.equals("http://xml.apache.org/xalan/psuedovar"))) {
/* 206:380 */       if (this.m_qname.getLocalName().startsWith("#")) {
/* 207:381 */         return true;
/* 208:    */       }
/* 209:    */     }
/* 210:383 */     return false;
/* 211:    */   }
/* 212:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Variable
 * JD-Core Version:    0.7.0.1
 */