/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.res.XSLMessages;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xml.utils.QName;
/*   8:    */ 
/*   9:    */ public class ElemAttributeSet
/*  10:    */   extends ElemUse
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -426740318278164496L;
/*  13: 50 */   public QName m_qname = null;
/*  14:    */   
/*  15:    */   public void setName(QName name)
/*  16:    */   {
/*  17: 60 */     this.m_qname = name;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public QName getName()
/*  21:    */   {
/*  22: 71 */     return this.m_qname;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getXSLToken()
/*  26:    */   {
/*  27: 82 */     return 40;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getNodeName()
/*  31:    */   {
/*  32: 92 */     return "attribute-set";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void execute(TransformerImpl transformer)
/*  36:    */     throws TransformerException
/*  37:    */   {
/*  38:107 */     if (transformer.getDebug()) {
/*  39:108 */       transformer.getTraceManager().fireTraceEvent(this);
/*  40:    */     }
/*  41:110 */     if (transformer.isRecursiveAttrSet(this)) {
/*  42:112 */       throw new TransformerException(XSLMessages.createMessage("ER_XSLATTRSET_USED_ITSELF", new Object[] { this.m_qname.getLocalPart() }));
/*  43:    */     }
/*  44:118 */     transformer.pushElemAttributeSet(this);
/*  45:119 */     super.execute(transformer);
/*  46:    */     
/*  47:121 */     ElemAttribute attr = (ElemAttribute)getFirstChildElem();
/*  48:123 */     while (null != attr)
/*  49:    */     {
/*  50:125 */       attr.execute(transformer);
/*  51:    */       
/*  52:127 */       attr = (ElemAttribute)attr.getNextSiblingElem();
/*  53:    */     }
/*  54:130 */     transformer.popElemAttributeSet();
/*  55:132 */     if (transformer.getDebug()) {
/*  56:133 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ElemTemplateElement appendChildElem(ElemTemplateElement newChild)
/*  61:    */   {
/*  62:154 */     int type = newChild.getXSLToken();
/*  63:156 */     switch (type)
/*  64:    */     {
/*  65:    */     case 48: 
/*  66:    */       break;
/*  67:    */     default: 
/*  68:161 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  69:    */     }
/*  70:168 */     return super.appendChild(newChild);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void recompose(StylesheetRoot root)
/*  74:    */   {
/*  75:178 */     root.recomposeAttributeSets(this);
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemAttributeSet
 * JD-Core Version:    0.7.0.1
 */