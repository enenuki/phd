/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.utils.XMLString;
/*   6:    */ import org.apache.xpath.Expression;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.objects.XObject;
/*   9:    */ import org.apache.xpath.objects.XString;
/*  10:    */ import org.apache.xpath.res.XPATHMessages;
/*  11:    */ 
/*  12:    */ public class FunctionDef1Arg
/*  13:    */   extends FunctionOneArg
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 2325189412814149264L;
/*  16:    */   
/*  17:    */   protected int getArg0AsNode(XPathContext xctxt)
/*  18:    */     throws TransformerException
/*  19:    */   {
/*  20: 55 */     return null == this.m_arg0 ? xctxt.getCurrentNode() : this.m_arg0.asNode(xctxt);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean Arg0IsNodesetExpr()
/*  24:    */   {
/*  25: 65 */     return null == this.m_arg0 ? true : this.m_arg0.isNodesetExpr();
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected XMLString getArg0AsString(XPathContext xctxt)
/*  29:    */     throws TransformerException
/*  30:    */   {
/*  31: 84 */     if (null == this.m_arg0)
/*  32:    */     {
/*  33: 86 */       int currentNode = xctxt.getCurrentNode();
/*  34: 87 */       if (-1 == currentNode) {
/*  35: 88 */         return XString.EMPTYSTRING;
/*  36:    */       }
/*  37: 91 */       DTM dtm = xctxt.getDTM(currentNode);
/*  38: 92 */       return dtm.getStringValue(currentNode);
/*  39:    */     }
/*  40: 97 */     return this.m_arg0.execute(xctxt).xstr();
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected double getArg0AsNumber(XPathContext xctxt)
/*  44:    */     throws TransformerException
/*  45:    */   {
/*  46:117 */     if (null == this.m_arg0)
/*  47:    */     {
/*  48:119 */       int currentNode = xctxt.getCurrentNode();
/*  49:120 */       if (-1 == currentNode) {
/*  50:121 */         return 0.0D;
/*  51:    */       }
/*  52:124 */       DTM dtm = xctxt.getDTM(currentNode);
/*  53:125 */       XMLString str = dtm.getStringValue(currentNode);
/*  54:126 */       return str.toDouble();
/*  55:    */     }
/*  56:131 */     return this.m_arg0.execute(xctxt).num();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void checkNumberArgs(int argNum)
/*  60:    */     throws WrongNumberArgsException
/*  61:    */   {
/*  62:143 */     if (argNum > 1) {
/*  63:144 */       reportWrongNumberArgs();
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void reportWrongNumberArgs()
/*  68:    */     throws WrongNumberArgsException
/*  69:    */   {
/*  70:154 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("ER_ZERO_OR_ONE", null));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean canTraverseOutsideSubtree()
/*  74:    */   {
/*  75:165 */     return null == this.m_arg0 ? false : super.canTraverseOutsideSubtree();
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FunctionDef1Arg
 * JD-Core Version:    0.7.0.1
 */