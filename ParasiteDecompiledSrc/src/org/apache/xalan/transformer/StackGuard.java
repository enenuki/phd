/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.res.XSLMessages;
/*   5:    */ import org.apache.xalan.templates.ElemTemplate;
/*   6:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   7:    */ import org.apache.xml.utils.ObjectStack;
/*   8:    */ import org.apache.xml.utils.ObjectVector;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xpath.XPath;
/*  11:    */ 
/*  12:    */ public class StackGuard
/*  13:    */ {
/*  14: 46 */   private int m_recursionLimit = -1;
/*  15:    */   TransformerImpl m_transformer;
/*  16:    */   
/*  17:    */   public int getRecursionLimit()
/*  18:    */   {
/*  19: 64 */     return this.m_recursionLimit;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setRecursionLimit(int limit)
/*  23:    */   {
/*  24: 81 */     this.m_recursionLimit = limit;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public StackGuard(TransformerImpl transformerImpl)
/*  28:    */   {
/*  29: 90 */     this.m_transformer = transformerImpl;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int countLikeTemplates(ElemTemplate templ, int pos)
/*  33:    */   {
/*  34: 99 */     ObjectStack elems = this.m_transformer.getCurrentTemplateElements();
/*  35:100 */     int count = 1;
/*  36:101 */     for (int i = pos - 1; i >= 0; i--) {
/*  37:103 */       if ((ElemTemplateElement)elems.elementAt(i) == templ) {
/*  38:104 */         count++;
/*  39:    */       }
/*  40:    */     }
/*  41:107 */     return count;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private ElemTemplate getNextMatchOrNamedTemplate(int pos)
/*  45:    */   {
/*  46:120 */     ObjectStack elems = this.m_transformer.getCurrentTemplateElements();
/*  47:121 */     for (int i = pos; i >= 0; i--)
/*  48:    */     {
/*  49:123 */       ElemTemplateElement elem = (ElemTemplateElement)elems.elementAt(i);
/*  50:124 */       if (null != elem) {
/*  51:126 */         if (elem.getXSLToken() == 19) {
/*  52:128 */           return (ElemTemplate)elem;
/*  53:    */         }
/*  54:    */       }
/*  55:    */     }
/*  56:132 */     return null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void checkForInfinateLoop()
/*  60:    */     throws TransformerException
/*  61:    */   {
/*  62:142 */     int nTemplates = this.m_transformer.getCurrentTemplateElementsCount();
/*  63:143 */     if (nTemplates < this.m_recursionLimit) {
/*  64:144 */       return;
/*  65:    */     }
/*  66:146 */     if (this.m_recursionLimit <= 0) {
/*  67:147 */       return;
/*  68:    */     }
/*  69:151 */     for (int i = nTemplates - 1; i >= this.m_recursionLimit; i--)
/*  70:    */     {
/*  71:153 */       ElemTemplate template = getNextMatchOrNamedTemplate(i);
/*  72:155 */       if (null == template) {
/*  73:    */         break;
/*  74:    */       }
/*  75:158 */       int loopCount = countLikeTemplates(template, i);
/*  76:160 */       if (loopCount >= this.m_recursionLimit)
/*  77:    */       {
/*  78:167 */         String idIs = XSLMessages.createMessage(null != template.getName() ? "nameIs" : "matchPatternIs", null);
/*  79:168 */         Object[] msgArgs = { new Integer(loopCount), idIs, null != template.getName() ? template.getName().toString() : template.getMatch().getPatternString() };
/*  80:    */         
/*  81:    */ 
/*  82:171 */         String msg = XSLMessages.createMessage("recursionTooDeep", msgArgs);
/*  83:    */         
/*  84:173 */         throw new TransformerException(msg);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.StackGuard
 * JD-Core Version:    0.7.0.1
 */