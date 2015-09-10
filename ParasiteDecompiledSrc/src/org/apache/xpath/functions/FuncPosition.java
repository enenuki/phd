/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.axes.SubContextList;
/*   9:    */ import org.apache.xpath.compiler.Compiler;
/*  10:    */ import org.apache.xpath.objects.XNumber;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ 
/*  13:    */ public class FuncPosition
/*  14:    */   extends Function
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = -9092846348197271582L;
/*  17:    */   private boolean m_isTopLevel;
/*  18:    */   
/*  19:    */   public void postCompileStep(Compiler compiler)
/*  20:    */   {
/*  21: 46 */     this.m_isTopLevel = (compiler.getLocationPathDepth() == -1);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getPositionInContextNodeList(XPathContext xctxt)
/*  25:    */   {
/*  26: 62 */     SubContextList iter = this.m_isTopLevel ? null : xctxt.getSubContextList();
/*  27: 64 */     if (null != iter)
/*  28:    */     {
/*  29: 66 */       int prox = iter.getProximityPosition(xctxt);
/*  30:    */       
/*  31:    */ 
/*  32: 69 */       return prox;
/*  33:    */     }
/*  34: 72 */     DTMIterator cnl = xctxt.getContextNodeList();
/*  35: 74 */     if (null != cnl)
/*  36:    */     {
/*  37: 76 */       int n = cnl.getCurrentNode();
/*  38: 77 */       if (n == -1)
/*  39:    */       {
/*  40: 79 */         if (cnl.getCurrentPos() == 0) {
/*  41: 80 */           return 0;
/*  42:    */         }
/*  43:    */         try
/*  44:    */         {
/*  45: 89 */           cnl = cnl.cloneWithReset();
/*  46:    */         }
/*  47:    */         catch (CloneNotSupportedException cnse)
/*  48:    */         {
/*  49: 93 */           throw new WrappedRuntimeException(cnse);
/*  50:    */         }
/*  51: 95 */         int currentNode = xctxt.getContextNode();
/*  52: 97 */         while (-1 != (n = cnl.nextNode())) {
/*  53: 99 */           if (n == currentNode) {
/*  54:    */             break;
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:105 */       return cnl.getCurrentPos();
/*  59:    */     }
/*  60:109 */     return -1;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public XObject execute(XPathContext xctxt)
/*  64:    */     throws TransformerException
/*  65:    */   {
/*  66:122 */     double pos = getPositionInContextNodeList(xctxt);
/*  67:    */     
/*  68:124 */     return new XNumber(pos);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void fixupVariables(Vector vars, int globalsSize) {}
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncPosition
 * JD-Core Version:    0.7.0.1
 */