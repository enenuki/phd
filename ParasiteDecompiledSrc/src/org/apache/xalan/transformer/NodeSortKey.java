/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.text.Collator;
/*   4:    */ import java.util.Locale;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xml.utils.PrefixResolver;
/*   7:    */ import org.apache.xpath.XPath;
/*   8:    */ 
/*   9:    */ class NodeSortKey
/*  10:    */ {
/*  11:    */   XPath m_selectPat;
/*  12:    */   boolean m_treatAsNumbers;
/*  13:    */   boolean m_descending;
/*  14:    */   boolean m_caseOrderUpper;
/*  15:    */   Collator m_col;
/*  16:    */   Locale m_locale;
/*  17:    */   PrefixResolver m_namespaceContext;
/*  18:    */   TransformerImpl m_processor;
/*  19:    */   
/*  20:    */   NodeSortKey(TransformerImpl transformer, XPath selectPat, boolean treatAsNumbers, boolean descending, String langValue, boolean caseOrderUpper, PrefixResolver namespaceContext)
/*  21:    */     throws TransformerException
/*  22:    */   {
/*  23: 81 */     this.m_processor = transformer;
/*  24: 82 */     this.m_namespaceContext = namespaceContext;
/*  25: 83 */     this.m_selectPat = selectPat;
/*  26: 84 */     this.m_treatAsNumbers = treatAsNumbers;
/*  27: 85 */     this.m_descending = descending;
/*  28: 86 */     this.m_caseOrderUpper = caseOrderUpper;
/*  29: 88 */     if ((null != langValue) && (!this.m_treatAsNumbers))
/*  30:    */     {
/*  31:100 */       this.m_locale = new Locale(langValue.toLowerCase(), Locale.getDefault().getCountry());
/*  32:107 */       if (null == this.m_locale) {
/*  33:111 */         this.m_locale = Locale.getDefault();
/*  34:    */       }
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38:116 */       this.m_locale = Locale.getDefault();
/*  39:    */     }
/*  40:119 */     this.m_col = Collator.getInstance(this.m_locale);
/*  41:121 */     if (null == this.m_col)
/*  42:    */     {
/*  43:123 */       this.m_processor.getMsgMgr().warn(null, "WG_CANNOT_FIND_COLLATOR", new Object[] { langValue });
/*  44:    */       
/*  45:    */ 
/*  46:126 */       this.m_col = Collator.getInstance();
/*  47:    */     }
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.NodeSortKey
 * JD-Core Version:    0.7.0.1
 */