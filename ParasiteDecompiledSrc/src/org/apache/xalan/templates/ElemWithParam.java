/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xml.utils.QName;
/*   8:    */ import org.apache.xpath.Expression;
/*   9:    */ import org.apache.xpath.XPath;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ import org.apache.xpath.objects.XRTreeFrag;
/*  13:    */ import org.apache.xpath.objects.XString;
/*  14:    */ 
/*  15:    */ public class ElemWithParam
/*  16:    */   extends ElemTemplateElement
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = -1070355175864326257L;
/*  19:    */   int m_index;
/*  20: 61 */   private XPath m_selectPattern = null;
/*  21:    */   
/*  22:    */   public void setSelect(XPath v)
/*  23:    */   {
/*  24: 72 */     this.m_selectPattern = v;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public XPath getSelect()
/*  28:    */   {
/*  29: 84 */     return this.m_selectPattern;
/*  30:    */   }
/*  31:    */   
/*  32: 94 */   private QName m_qname = null;
/*  33:    */   int m_qnameID;
/*  34:    */   
/*  35:    */   public void setName(QName v)
/*  36:    */   {
/*  37:106 */     this.m_qname = v;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public QName getName()
/*  41:    */   {
/*  42:117 */     return this.m_qname;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getXSLToken()
/*  46:    */   {
/*  47:129 */     return 2;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getNodeName()
/*  51:    */   {
/*  52:140 */     return "with-param";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void compose(StylesheetRoot sroot)
/*  56:    */     throws TransformerException
/*  57:    */   {
/*  58:152 */     if ((null == this.m_selectPattern) && (sroot.getOptimizer()))
/*  59:    */     {
/*  60:155 */       XPath newSelect = ElemVariable.rewriteChildToExpression(this);
/*  61:156 */       if (null != newSelect) {
/*  62:157 */         this.m_selectPattern = newSelect;
/*  63:    */       }
/*  64:    */     }
/*  65:159 */     this.m_qnameID = sroot.getComposeState().getQNameID(this.m_qname);
/*  66:160 */     super.compose(sroot);
/*  67:    */     
/*  68:162 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  69:163 */     if (null != this.m_selectPattern) {
/*  70:164 */       this.m_selectPattern.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setParentElem(ElemTemplateElement p)
/*  75:    */   {
/*  76:176 */     super.setParentElem(p);
/*  77:177 */     p.m_hasVariableDecl = true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public XObject getValue(TransformerImpl transformer, int sourceNode)
/*  81:    */     throws TransformerException
/*  82:    */   {
/*  83:195 */     XPathContext xctxt = transformer.getXPathContext();
/*  84:    */     
/*  85:197 */     xctxt.pushCurrentNode(sourceNode);
/*  86:    */     XObject var;
/*  87:    */     try
/*  88:    */     {
/*  89:201 */       if (null != this.m_selectPattern)
/*  90:    */       {
/*  91:203 */         var = this.m_selectPattern.execute(xctxt, sourceNode, this);
/*  92:    */         
/*  93:205 */         var.allowDetachToRelease(false);
/*  94:207 */         if (transformer.getDebug()) {
/*  95:208 */           transformer.getTraceManager().fireSelectedEvent(sourceNode, this, "select", this.m_selectPattern, var);
/*  96:    */         }
/*  97:    */       }
/*  98:211 */       else if (null == getFirstChildElem())
/*  99:    */       {
/* 100:213 */         var = XString.EMPTYSTRING;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:219 */         int df = transformer.transformToRTF(this);
/* 105:    */         
/* 106:221 */         var = new XRTreeFrag(df, xctxt, this);
/* 107:    */       }
/* 108:    */     }
/* 109:    */     finally
/* 110:    */     {
/* 111:226 */       xctxt.popCurrentNode();
/* 112:    */     }
/* 113:229 */     return var;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 117:    */   {
/* 118:238 */     if ((callAttrs) && (null != this.m_selectPattern)) {
/* 119:239 */       this.m_selectPattern.getExpression().callVisitors(this.m_selectPattern, visitor);
/* 120:    */     }
/* 121:240 */     super.callChildVisitors(visitor, callAttrs);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public ElemTemplateElement appendChild(ElemTemplateElement elem)
/* 125:    */   {
/* 126:255 */     if (this.m_selectPattern != null)
/* 127:    */     {
/* 128:257 */       error("ER_CANT_HAVE_CONTENT_AND_SELECT", new Object[] { "xsl:" + getNodeName() });
/* 129:    */       
/* 130:259 */       return null;
/* 131:    */     }
/* 132:261 */     return super.appendChild(elem);
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemWithParam
 * JD-Core Version:    0.7.0.1
 */