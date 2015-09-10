/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xalan.trace.TraceManager;
/*  5:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class ElemExsltFuncResult
/* 10:   */   extends ElemVariable
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -3478311949388304563L;
/* 13:40 */   private boolean m_isResultSet = false;
/* 14:43 */   private XObject m_result = null;
/* 15:46 */   private int m_callerFrameSize = 0;
/* 16:   */   
/* 17:   */   public void execute(TransformerImpl transformer)
/* 18:   */     throws TransformerException
/* 19:   */   {
/* 20:55 */     XPathContext context = transformer.getXPathContext();
/* 21:57 */     if (transformer.getDebug()) {
/* 22:58 */       transformer.getTraceManager().fireTraceEvent(this);
/* 23:   */     }
/* 24:63 */     if (transformer.currentFuncResultSeen()) {
/* 25:64 */       throw new TransformerException("An EXSLT function cannot set more than one result!");
/* 26:   */     }
/* 27:67 */     int sourceNode = context.getCurrentNode();
/* 28:   */     
/* 29:   */ 
/* 30:70 */     XObject var = getValue(transformer, sourceNode);
/* 31:71 */     transformer.popCurrentFuncResult();
/* 32:72 */     transformer.pushCurrentFuncResult(var);
/* 33:74 */     if (transformer.getDebug()) {
/* 34:75 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getXSLToken()
/* 39:   */   {
/* 40:87 */     return 89;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getNodeName()
/* 44:   */   {
/* 45:99 */     return "result";
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemExsltFuncResult
 * JD-Core Version:    0.7.0.1
 */