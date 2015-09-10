/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.templates.ElemNumber;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xpath.NodeSetDTM;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ 
/*   9:    */ public class Counter
/*  10:    */ {
/*  11:    */   static final int MAXCOUNTNODES = 500;
/*  12: 52 */   int m_countNodesStartCount = 0;
/*  13:    */   NodeSetDTM m_countNodes;
/*  14: 64 */   int m_fromNode = -1;
/*  15:    */   ElemNumber m_numberElem;
/*  16:    */   int m_countResult;
/*  17:    */   
/*  18:    */   Counter(ElemNumber numberElem, NodeSetDTM countNodes)
/*  19:    */     throws TransformerException
/*  20:    */   {
/*  21: 88 */     this.m_countNodes = countNodes;
/*  22: 89 */     this.m_numberElem = numberElem;
/*  23:    */   }
/*  24:    */   
/*  25:    */   int getPreviouslyCounted(XPathContext support, int node)
/*  26:    */   {
/*  27:116 */     int n = this.m_countNodes.size();
/*  28:    */     
/*  29:118 */     this.m_countResult = 0;
/*  30:120 */     for (int i = n - 1; i >= 0; i--)
/*  31:    */     {
/*  32:122 */       int countedNode = this.m_countNodes.elementAt(i);
/*  33:124 */       if (node == countedNode)
/*  34:    */       {
/*  35:129 */         this.m_countResult = (i + 1 + this.m_countNodesStartCount);
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39:134 */         DTM dtm = support.getDTM(countedNode);
/*  40:138 */         if (dtm.isNodeAfter(countedNode, node)) {
/*  41:    */           break;
/*  42:    */         }
/*  43:    */       }
/*  44:    */     }
/*  45:142 */     return this.m_countResult;
/*  46:    */   }
/*  47:    */   
/*  48:    */   int getLast()
/*  49:    */   {
/*  50:153 */     int size = this.m_countNodes.size();
/*  51:    */     
/*  52:155 */     return size > 0 ? this.m_countNodes.elementAt(size - 1) : -1;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.Counter
 * JD-Core Version:    0.7.0.1
 */