/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ public class ElemText
/*   4:    */   extends ElemTemplateElement
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = 1383140876182316711L;
/*   7: 46 */   private boolean m_disableOutputEscaping = false;
/*   8:    */   
/*   9:    */   public void setDisableOutputEscaping(boolean v)
/*  10:    */   {
/*  11: 70 */     this.m_disableOutputEscaping = v;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public boolean getDisableOutputEscaping()
/*  15:    */   {
/*  16: 95 */     return this.m_disableOutputEscaping;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public int getXSLToken()
/*  20:    */   {
/*  21:107 */     return 42;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getNodeName()
/*  25:    */   {
/*  26:117 */     return "text";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  30:    */   {
/*  31:132 */     int type = newChild.getXSLToken();
/*  32:134 */     switch (type)
/*  33:    */     {
/*  34:    */     case 78: 
/*  35:    */       break;
/*  36:    */     default: 
/*  37:139 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  38:    */     }
/*  39:146 */     return super.appendChild(newChild);
/*  40:    */   }
/*  41:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemText
 * JD-Core Version:    0.7.0.1
 */