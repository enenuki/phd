/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.ErrorListener;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.trace.TraceManager;
/*   7:    */ import org.apache.xalan.transformer.MsgMgr;
/*   8:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   9:    */ 
/*  10:    */ public class ElemMessage
/*  11:    */   extends ElemTemplateElement
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 1530472462155060023L;
/*  14: 51 */   private boolean m_terminate = false;
/*  15:    */   
/*  16:    */   public void setTerminate(boolean v)
/*  17:    */   {
/*  18: 63 */     this.m_terminate = v;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean getTerminate()
/*  22:    */   {
/*  23: 76 */     return this.m_terminate;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getXSLToken()
/*  27:    */   {
/*  28: 87 */     return 75;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getNodeName()
/*  32:    */   {
/*  33: 97 */     return "message";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void execute(TransformerImpl transformer)
/*  37:    */     throws TransformerException
/*  38:    */   {
/*  39:117 */     if (transformer.getDebug()) {
/*  40:118 */       transformer.getTraceManager().fireTraceEvent(this);
/*  41:    */     }
/*  42:120 */     String data = transformer.transformToString(this);
/*  43:    */     
/*  44:122 */     transformer.getMsgMgr().message(this, data, this.m_terminate);
/*  45:124 */     if (this.m_terminate) {
/*  46:125 */       transformer.getErrorListener().fatalError(new TransformerException(XSLMessages.createMessage("ER_STYLESHEET_DIRECTED_TERMINATION", null)));
/*  47:    */     }
/*  48:127 */     if (transformer.getDebug()) {
/*  49:128 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  50:    */     }
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemMessage
 * JD-Core Version:    0.7.0.1
 */