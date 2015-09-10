/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.trace.TraceManager;
/*   5:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   6:    */ import org.apache.xml.serializer.ExtendedLexicalHandler;
/*   7:    */ import org.xml.sax.SAXException;
/*   8:    */ 
/*   9:    */ public class ElemComment
/*  10:    */   extends ElemTemplateElement
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -8813199122875770142L;
/*  13:    */   
/*  14:    */   public int getXSLToken()
/*  15:    */   {
/*  16: 49 */     return 59;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getNodeName()
/*  20:    */   {
/*  21: 59 */     return "comment";
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void execute(TransformerImpl transformer)
/*  25:    */     throws TransformerException
/*  26:    */   {
/*  27: 74 */     if (transformer.getDebug()) {
/*  28: 75 */       transformer.getTraceManager().fireTraceEvent(this);
/*  29:    */     }
/*  30:    */     try
/*  31:    */     {
/*  32: 86 */       String data = transformer.transformToString(this);
/*  33:    */       
/*  34: 88 */       transformer.getResultTreeHandler().comment(data);
/*  35:    */     }
/*  36:    */     catch (SAXException se)
/*  37:    */     {
/*  38: 92 */       throw new TransformerException(se);
/*  39:    */     }
/*  40:    */     finally
/*  41:    */     {
/*  42: 96 */       if (transformer.getDebug()) {
/*  43: 97 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  49:    */   {
/*  50:113 */     int type = newChild.getXSLToken();
/*  51:115 */     switch (type)
/*  52:    */     {
/*  53:    */     case 9: 
/*  54:    */     case 17: 
/*  55:    */     case 28: 
/*  56:    */     case 30: 
/*  57:    */     case 35: 
/*  58:    */     case 36: 
/*  59:    */     case 37: 
/*  60:    */     case 42: 
/*  61:    */     case 50: 
/*  62:    */     case 72: 
/*  63:    */     case 73: 
/*  64:    */     case 74: 
/*  65:    */     case 75: 
/*  66:    */     case 78: 
/*  67:    */       break;
/*  68:    */     default: 
/*  69:141 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  70:    */     }
/*  71:148 */     return super.appendChild(newChild);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemComment
 * JD-Core Version:    0.7.0.1
 */