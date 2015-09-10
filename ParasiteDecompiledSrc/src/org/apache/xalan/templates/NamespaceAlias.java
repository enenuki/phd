/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ public class NamespaceAlias
/*   4:    */   extends ElemTemplateElement
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = 456173966637810718L;
/*   7:    */   private String m_StylesheetPrefix;
/*   8:    */   private String m_StylesheetNamespace;
/*   9:    */   private String m_ResultPrefix;
/*  10:    */   private String m_ResultNamespace;
/*  11:    */   
/*  12:    */   public NamespaceAlias(int docOrderNumber)
/*  13:    */   {
/*  14: 42 */     this.m_docOrderNumber = docOrderNumber;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void setStylesheetPrefix(String v)
/*  18:    */   {
/*  19: 58 */     this.m_StylesheetPrefix = v;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getStylesheetPrefix()
/*  23:    */   {
/*  24: 68 */     return this.m_StylesheetPrefix;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setStylesheetNamespace(String v)
/*  28:    */   {
/*  29: 84 */     this.m_StylesheetNamespace = v;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getStylesheetNamespace()
/*  33:    */   {
/*  34: 94 */     return this.m_StylesheetNamespace;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setResultPrefix(String v)
/*  38:    */   {
/*  39:110 */     this.m_ResultPrefix = v;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getResultPrefix()
/*  43:    */   {
/*  44:120 */     return this.m_ResultPrefix;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setResultNamespace(String v)
/*  48:    */   {
/*  49:136 */     this.m_ResultNamespace = v;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getResultNamespace()
/*  53:    */   {
/*  54:146 */     return this.m_ResultNamespace;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void recompose(StylesheetRoot root)
/*  58:    */   {
/*  59:156 */     root.recomposeNamespaceAliases(this);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.NamespaceAlias
 * JD-Core Version:    0.7.0.1
 */