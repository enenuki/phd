/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.XPath;
/*   7:    */ 
/*   8:    */ public class ElemWhen
/*   9:    */   extends ElemTemplateElement
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 5984065730262071360L;
/*  12:    */   private XPath m_test;
/*  13:    */   
/*  14:    */   public void setTest(XPath v)
/*  15:    */   {
/*  16: 57 */     this.m_test = v;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public XPath getTest()
/*  20:    */   {
/*  21: 69 */     return this.m_test;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getXSLToken()
/*  25:    */   {
/*  26: 81 */     return 38;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void compose(StylesheetRoot sroot)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32: 93 */     super.compose(sroot);
/*  33: 94 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  34: 95 */     if (null != this.m_test) {
/*  35: 96 */       this.m_test.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getNodeName()
/*  40:    */   {
/*  41:106 */     return "when";
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/*  45:    */   {
/*  46:121 */     if (callAttrs) {
/*  47:122 */       this.m_test.getExpression().callVisitors(this.m_test, visitor);
/*  48:    */     }
/*  49:123 */     super.callChildVisitors(visitor, callAttrs);
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemWhen
 * JD-Core Version:    0.7.0.1
 */