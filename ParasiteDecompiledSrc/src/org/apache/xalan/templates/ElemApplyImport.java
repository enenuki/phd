/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.trace.TraceManager;
/*   5:    */ import org.apache.xalan.transformer.MsgMgr;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ 
/*   9:    */ public class ElemApplyImport
/*  10:    */   extends ElemTemplateElement
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = 3764728663373024038L;
/*  13:    */   
/*  14:    */   public int getXSLToken()
/*  15:    */   {
/*  16: 49 */     return 72;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getNodeName()
/*  20:    */   {
/*  21: 59 */     return "apply-imports";
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void execute(TransformerImpl transformer)
/*  25:    */     throws TransformerException
/*  26:    */   {
/*  27: 74 */     if (transformer.currentTemplateRuleIsNull()) {
/*  28: 76 */       transformer.getMsgMgr().error(this, "ER_NO_APPLY_IMPORT_IN_FOR_EACH");
/*  29:    */     }
/*  30: 80 */     if (transformer.getDebug()) {
/*  31: 81 */       transformer.getTraceManager().fireTraceEvent(this);
/*  32:    */     }
/*  33: 83 */     int sourceNode = transformer.getXPathContext().getCurrentNode();
/*  34: 84 */     if (-1 != sourceNode)
/*  35:    */     {
/*  36: 87 */       ElemTemplate matchTemplate = transformer.getMatchedTemplate();
/*  37: 88 */       transformer.applyTemplateToNode(this, matchTemplate, sourceNode);
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41: 92 */       transformer.getMsgMgr().error(this, "ER_NULL_SOURCENODE_APPLYIMPORTS");
/*  42:    */     }
/*  43: 95 */     if (transformer.getDebug()) {
/*  44: 96 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  49:    */   {
/*  50:110 */     error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:115 */     return null;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemApplyImport
 * JD-Core Version:    0.7.0.1
 */