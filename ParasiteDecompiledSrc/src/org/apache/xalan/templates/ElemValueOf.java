/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xml.serializer.SerializationHandler;
/*   8:    */ import org.apache.xpath.Expression;
/*   9:    */ import org.apache.xpath.XPath;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ import org.xml.sax.ContentHandler;
/*  13:    */ import org.xml.sax.SAXException;
/*  14:    */ 
/*  15:    */ public class ElemValueOf
/*  16:    */   extends ElemTemplateElement
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = 3490728458007586786L;
/*  19: 55 */   private XPath m_selectExpression = null;
/*  20: 61 */   private boolean m_isDot = false;
/*  21:    */   
/*  22:    */   public void setSelect(XPath v)
/*  23:    */   {
/*  24: 74 */     if (null != v)
/*  25:    */     {
/*  26: 76 */       String s = v.getPatternString();
/*  27:    */       
/*  28: 78 */       this.m_isDot = ((null != s) && (s.equals(".")));
/*  29:    */     }
/*  30: 81 */     this.m_selectExpression = v;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public XPath getSelect()
/*  34:    */   {
/*  35: 94 */     return this.m_selectExpression;
/*  36:    */   }
/*  37:    */   
/*  38:101 */   private boolean m_disableOutputEscaping = false;
/*  39:    */   
/*  40:    */   public void setDisableOutputEscaping(boolean v)
/*  41:    */   {
/*  42:125 */     this.m_disableOutputEscaping = v;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean getDisableOutputEscaping()
/*  46:    */   {
/*  47:150 */     return this.m_disableOutputEscaping;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getXSLToken()
/*  51:    */   {
/*  52:162 */     return 30;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void compose(StylesheetRoot sroot)
/*  56:    */     throws TransformerException
/*  57:    */   {
/*  58:178 */     super.compose(sroot);
/*  59:    */     
/*  60:180 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  61:182 */     if (null != this.m_selectExpression) {
/*  62:183 */       this.m_selectExpression.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getNodeName()
/*  67:    */   {
/*  68:194 */     return "value-of";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void execute(TransformerImpl transformer)
/*  72:    */     throws TransformerException
/*  73:    */   {
/*  74:215 */     XPathContext xctxt = transformer.getXPathContext();
/*  75:216 */     SerializationHandler rth = transformer.getResultTreeHandler();
/*  76:218 */     if (transformer.getDebug()) {
/*  77:219 */       transformer.getTraceManager().fireTraceEvent(this);
/*  78:    */     }
/*  79:    */     try
/*  80:    */     {
/*  81:250 */       xctxt.pushNamespaceContext(this);
/*  82:    */       
/*  83:252 */       int current = xctxt.getCurrentNode();
/*  84:    */       
/*  85:254 */       xctxt.pushCurrentNodeAndExpression(current, current);
/*  86:256 */       if (this.m_disableOutputEscaping) {
/*  87:257 */         rth.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/*  88:    */       }
/*  89:    */       try
/*  90:    */       {
/*  91:262 */         Expression expr = this.m_selectExpression.getExpression();
/*  92:264 */         if (transformer.getDebug())
/*  93:    */         {
/*  94:266 */           XObject obj = expr.execute(xctxt);
/*  95:    */           
/*  96:268 */           transformer.getTraceManager().fireSelectedEvent(current, this, "select", this.m_selectExpression, obj);
/*  97:    */           
/*  98:270 */           obj.dispatchCharactersEvents(rth);
/*  99:    */         }
/* 100:    */         else
/* 101:    */         {
/* 102:274 */           expr.executeCharsToContentHandler(xctxt, rth);
/* 103:    */         }
/* 104:    */       }
/* 105:    */       finally
/* 106:    */       {
/* 107:279 */         if (this.m_disableOutputEscaping) {
/* 108:280 */           rth.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/* 109:    */         }
/* 110:283 */         xctxt.popNamespaceContext();
/* 111:284 */         xctxt.popCurrentNodeAndExpression();
/* 112:    */       }
/* 113:    */     }
/* 114:    */     catch (SAXException se)
/* 115:    */     {
/* 116:290 */       throw new TransformerException(se);
/* 117:    */     }
/* 118:    */     catch (RuntimeException re)
/* 119:    */     {
/* 120:293 */       TransformerException te = new TransformerException(re);
/* 121:294 */       te.setLocator(this);
/* 122:295 */       throw te;
/* 123:    */     }
/* 124:    */     finally
/* 125:    */     {
/* 126:299 */       if (transformer.getDebug()) {
/* 127:300 */         transformer.getTraceManager().fireTraceEndEvent(this);
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/* 133:    */   {
/* 134:316 */     error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:321 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 143:    */   {
/* 144:330 */     if (callAttrs) {
/* 145:331 */       this.m_selectExpression.getExpression().callVisitors(this.m_selectExpression, visitor);
/* 146:    */     }
/* 147:332 */     super.callChildVisitors(visitor, callAttrs);
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemValueOf
 * JD-Core Version:    0.7.0.1
 */