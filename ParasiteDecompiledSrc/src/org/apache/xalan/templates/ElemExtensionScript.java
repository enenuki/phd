/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ public class ElemExtensionScript
/*   4:    */   extends ElemTemplateElement
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -6995978265966057744L;
/*   7: 43 */   private String m_lang = null;
/*   8:    */   
/*   9:    */   public void setLang(String v)
/*  10:    */   {
/*  11: 53 */     this.m_lang = v;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public String getLang()
/*  15:    */   {
/*  16: 64 */     return this.m_lang;
/*  17:    */   }
/*  18:    */   
/*  19: 69 */   private String m_src = null;
/*  20:    */   
/*  21:    */   public void setSrc(String v)
/*  22:    */   {
/*  23: 79 */     this.m_src = v;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getSrc()
/*  27:    */   {
/*  28: 90 */     return this.m_src;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getXSLToken()
/*  32:    */   {
/*  33:101 */     return 86;
/*  34:    */   }
/*  35:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemExtensionScript
 * JD-Core Version:    0.7.0.1
 */