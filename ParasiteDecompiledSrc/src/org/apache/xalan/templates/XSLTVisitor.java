/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import org.apache.xpath.XPathVisitor;
/*   4:    */ 
/*   5:    */ public class XSLTVisitor
/*   6:    */   extends XPathVisitor
/*   7:    */ {
/*   8:    */   public boolean visitInstruction(ElemTemplateElement elem)
/*   9:    */   {
/*  10: 43 */     return true;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public boolean visitStylesheet(ElemTemplateElement elem)
/*  14:    */   {
/*  15: 54 */     return true;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean visitTopLevelInstruction(ElemTemplateElement elem)
/*  19:    */   {
/*  20: 66 */     return true;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean visitTopLevelVariableOrParamDecl(ElemTemplateElement elem)
/*  24:    */   {
/*  25: 77 */     return true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean visitVariableOrParamDecl(ElemVariable elem)
/*  29:    */   {
/*  30: 89 */     return true;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean visitLiteralResultElement(ElemLiteralResult elem)
/*  34:    */   {
/*  35:100 */     return true;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean visitAVT(AVT elem)
/*  39:    */   {
/*  40:111 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean visitExtensionElement(ElemExtensionCall elem)
/*  44:    */   {
/*  45:122 */     return true;
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.XSLTVisitor
 * JD-Core Version:    0.7.0.1
 */