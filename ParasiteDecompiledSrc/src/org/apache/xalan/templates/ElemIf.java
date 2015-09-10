/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.XPath;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ 
/*  12:    */ public class ElemIf
/*  13:    */   extends ElemTemplateElement
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 2158774632427453022L;
/*  16: 50 */   private XPath m_test = null;
/*  17:    */   
/*  18:    */   public void setTest(XPath v)
/*  19:    */   {
/*  20: 60 */     this.m_test = v;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public XPath getTest()
/*  24:    */   {
/*  25: 71 */     return this.m_test;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void compose(StylesheetRoot sroot)
/*  29:    */     throws TransformerException
/*  30:    */   {
/*  31: 87 */     super.compose(sroot);
/*  32:    */     
/*  33: 89 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  34: 91 */     if (null != this.m_test) {
/*  35: 92 */       this.m_test.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getXSLToken()
/*  40:    */   {
/*  41:103 */     return 36;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getNodeName()
/*  45:    */   {
/*  46:113 */     return "if";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void execute(TransformerImpl transformer)
/*  50:    */     throws TransformerException
/*  51:    */   {
/*  52:130 */     XPathContext xctxt = transformer.getXPathContext();
/*  53:131 */     int sourceNode = xctxt.getCurrentNode();
/*  54:133 */     if (transformer.getDebug())
/*  55:    */     {
/*  56:135 */       XObject test = this.m_test.execute(xctxt, sourceNode, this);
/*  57:137 */       if (transformer.getDebug()) {
/*  58:138 */         transformer.getTraceManager().fireSelectedEvent(sourceNode, this, "test", this.m_test, test);
/*  59:    */       }
/*  60:144 */       if (transformer.getDebug()) {
/*  61:145 */         transformer.getTraceManager().fireTraceEvent(this);
/*  62:    */       }
/*  63:147 */       if (test.bool()) {
/*  64:149 */         transformer.executeChildTemplates(this, true);
/*  65:    */       }
/*  66:152 */       if (transformer.getDebug()) {
/*  67:153 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  68:    */       }
/*  69:    */     }
/*  70:160 */     else if (this.m_test.bool(xctxt, sourceNode, this))
/*  71:    */     {
/*  72:162 */       transformer.executeChildTemplates(this, true);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/*  77:    */   {
/*  78:173 */     if (callAttrs) {
/*  79:174 */       this.m_test.getExpression().callVisitors(this.m_test, visitor);
/*  80:    */     }
/*  81:175 */     super.callChildVisitors(visitor, callAttrs);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemIf
 * JD-Core Version:    0.7.0.1
 */