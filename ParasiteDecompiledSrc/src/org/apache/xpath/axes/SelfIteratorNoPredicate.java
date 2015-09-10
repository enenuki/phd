/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xpath.XPathContext;
/*   5:    */ import org.apache.xpath.compiler.Compiler;
/*   6:    */ 
/*   7:    */ public class SelfIteratorNoPredicate
/*   8:    */   extends LocPathIterator
/*   9:    */ {
/*  10:    */   static final long serialVersionUID = -4226887905279814201L;
/*  11:    */   
/*  12:    */   SelfIteratorNoPredicate(Compiler compiler, int opPos, int analysis)
/*  13:    */     throws TransformerException
/*  14:    */   {
/*  15: 50 */     super(compiler, opPos, analysis, false);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public SelfIteratorNoPredicate()
/*  19:    */     throws TransformerException
/*  20:    */   {
/*  21: 61 */     super(null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int nextNode()
/*  25:    */   {
/*  26: 75 */     if (this.m_foundLast) {
/*  27: 76 */       return -1;
/*  28:    */     }
/*  29:    */     int next;
/*  30: 80 */     this.m_lastFetched = (next = -1 == this.m_lastFetched ? this.m_context : -1);
/*  31: 85 */     if (-1 != next)
/*  32:    */     {
/*  33: 87 */       this.m_pos += 1;
/*  34:    */       
/*  35: 89 */       return next;
/*  36:    */     }
/*  37: 93 */     this.m_foundLast = true;
/*  38:    */     
/*  39: 95 */     return -1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int asNode(XPathContext xctxt)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:110 */     return xctxt.getCurrentNode();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getLastPos(XPathContext xctxt)
/*  49:    */   {
/*  50:123 */     return 1;
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.SelfIteratorNoPredicate
 * JD-Core Version:    0.7.0.1
 */