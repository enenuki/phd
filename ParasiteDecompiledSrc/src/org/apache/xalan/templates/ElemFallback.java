/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ 
/*   8:    */ public class ElemFallback
/*   9:    */   extends ElemTemplateElement
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 1782962139867340703L;
/*  12:    */   
/*  13:    */   public int getXSLToken()
/*  14:    */   {
/*  15: 48 */     return 57;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getNodeName()
/*  19:    */   {
/*  20: 58 */     return "fallback";
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void execute(TransformerImpl transformer)
/*  24:    */     throws TransformerException
/*  25:    */   {}
/*  26:    */   
/*  27:    */   public void executeFallback(TransformerImpl transformer)
/*  28:    */     throws TransformerException
/*  29:    */   {
/*  30: 94 */     int parentElemType = this.m_parentNode.getXSLToken();
/*  31: 95 */     if ((79 == parentElemType) || (-1 == parentElemType))
/*  32:    */     {
/*  33: 99 */       if (transformer.getDebug()) {
/*  34:100 */         transformer.getTraceManager().fireTraceEvent(this);
/*  35:    */       }
/*  36:102 */       transformer.executeChildTemplates(this, true);
/*  37:104 */       if (transformer.getDebug()) {
/*  38:105 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  39:    */       }
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43:111 */       System.out.println("Error!  parent of xsl:fallback must be an extension or unknown element!");
/*  44:    */     }
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemFallback
 * JD-Core Version:    0.7.0.1
 */