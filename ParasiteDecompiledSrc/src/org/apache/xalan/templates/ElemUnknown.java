/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.ErrorListener;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ 
/*   8:    */ public class ElemUnknown
/*   9:    */   extends ElemLiteralResult
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = -4573981712648730168L;
/*  12:    */   
/*  13:    */   public int getXSLToken()
/*  14:    */   {
/*  15: 46 */     return -1;
/*  16:    */   }
/*  17:    */   
/*  18:    */   private void executeFallbacks(TransformerImpl transformer)
/*  19:    */     throws TransformerException
/*  20:    */   {
/*  21: 60 */     for (ElemTemplateElement child = this.m_firstChild; child != null; child = child.m_nextSibling) {
/*  22: 63 */       if (child.getXSLToken() == 57) {
/*  23:    */         try
/*  24:    */         {
/*  25: 67 */           transformer.pushElemTemplateElement(child);
/*  26: 68 */           ((ElemFallback)child).executeFallback(transformer);
/*  27:    */         }
/*  28:    */         finally
/*  29:    */         {
/*  30: 72 */           transformer.popElemTemplateElement();
/*  31:    */         }
/*  32:    */       }
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   private boolean hasFallbackChildren()
/*  37:    */   {
/*  38: 86 */     for (ElemTemplateElement child = this.m_firstChild; child != null; child = child.m_nextSibling) {
/*  39: 89 */       if (child.getXSLToken() == 57) {
/*  40: 90 */         return true;
/*  41:    */       }
/*  42:    */     }
/*  43: 93 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void execute(TransformerImpl transformer)
/*  47:    */     throws TransformerException
/*  48:    */   {
/*  49:110 */     if (transformer.getDebug()) {
/*  50:111 */       transformer.getTraceManager().fireTraceEvent(this);
/*  51:    */     }
/*  52:    */     try
/*  53:    */     {
/*  54:115 */       if (hasFallbackChildren()) {
/*  55:116 */         executeFallbacks(transformer);
/*  56:    */       }
/*  57:    */     }
/*  58:    */     catch (TransformerException e)
/*  59:    */     {
/*  60:122 */       transformer.getErrorListener().fatalError(e);
/*  61:    */     }
/*  62:124 */     if (transformer.getDebug()) {
/*  63:125 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  64:    */     }
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemUnknown
 * JD-Core Version:    0.7.0.1
 */