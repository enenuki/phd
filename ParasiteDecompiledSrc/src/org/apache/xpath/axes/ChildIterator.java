/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xpath.XPathContext;
/*   6:    */ import org.apache.xpath.compiler.Compiler;
/*   7:    */ import org.apache.xpath.patterns.NodeTest;
/*   8:    */ 
/*   9:    */ public class ChildIterator
/*  10:    */   extends LocPathIterator
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -6935428015142993583L;
/*  13:    */   
/*  14:    */   ChildIterator(Compiler compiler, int opPos, int analysis)
/*  15:    */     throws TransformerException
/*  16:    */   {
/*  17: 52 */     super(compiler, opPos, analysis, false);
/*  18:    */     
/*  19:    */ 
/*  20: 55 */     initNodeTest(-1);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int asNode(XPathContext xctxt)
/*  24:    */     throws TransformerException
/*  25:    */   {
/*  26: 69 */     int current = xctxt.getCurrentNode();
/*  27:    */     
/*  28: 71 */     DTM dtm = xctxt.getDTM(current);
/*  29:    */     
/*  30: 73 */     return dtm.getFirstChild(current);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int nextNode()
/*  34:    */   {
/*  35: 86 */     if (this.m_foundLast) {
/*  36: 87 */       return -1;
/*  37:    */     }
/*  38:    */     int next;
/*  39: 91 */     this.m_lastFetched = (next = -1 == this.m_lastFetched ? this.m_cdtm.getFirstChild(this.m_context) : this.m_cdtm.getNextSibling(this.m_lastFetched));
/*  40: 96 */     if (-1 != next)
/*  41:    */     {
/*  42: 98 */       this.m_pos += 1;
/*  43: 99 */       return next;
/*  44:    */     }
/*  45:103 */     this.m_foundLast = true;
/*  46:    */     
/*  47:105 */     return -1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getAxis()
/*  51:    */   {
/*  52:117 */     return 3;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.ChildIterator
 * JD-Core Version:    0.7.0.1
 */