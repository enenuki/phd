/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.trace.TraceManager;
/*   5:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   6:    */ import org.apache.xpath.VariableStack;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.objects.XObject;
/*   9:    */ 
/*  10:    */ public class ElemParam
/*  11:    */   extends ElemVariable
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -1131781475589006431L;
/*  14:    */   int m_qnameID;
/*  15:    */   
/*  16:    */   public ElemParam() {}
/*  17:    */   
/*  18:    */   public int getXSLToken()
/*  19:    */   {
/*  20: 60 */     return 41;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getNodeName()
/*  24:    */   {
/*  25: 70 */     return "param";
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ElemParam(ElemParam param)
/*  29:    */     throws TransformerException
/*  30:    */   {
/*  31: 82 */     super(param);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void compose(StylesheetRoot sroot)
/*  35:    */     throws TransformerException
/*  36:    */   {
/*  37: 93 */     super.compose(sroot);
/*  38: 94 */     this.m_qnameID = sroot.getComposeState().getQNameID(this.m_qname);
/*  39: 95 */     int parentToken = this.m_parentNode.getXSLToken();
/*  40: 96 */     if ((parentToken == 19) || (parentToken == 88)) {
/*  41: 98 */       ((ElemTemplate)this.m_parentNode).m_inArgsSize += 1;
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void execute(TransformerImpl transformer)
/*  46:    */     throws TransformerException
/*  47:    */   {
/*  48:111 */     if (transformer.getDebug()) {
/*  49:112 */       transformer.getTraceManager().fireTraceEvent(this);
/*  50:    */     }
/*  51:114 */     VariableStack vars = transformer.getXPathContext().getVarStack();
/*  52:116 */     if (!vars.isLocalSet(this.m_index))
/*  53:    */     {
/*  54:119 */       int sourceNode = transformer.getXPathContext().getCurrentNode();
/*  55:120 */       XObject var = getValue(transformer, sourceNode);
/*  56:    */       
/*  57:    */ 
/*  58:123 */       transformer.getXPathContext().getVarStack().setLocalVariable(this.m_index, var);
/*  59:    */     }
/*  60:126 */     if (transformer.getDebug()) {
/*  61:127 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  62:    */     }
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemParam
 * JD-Core Version:    0.7.0.1
 */