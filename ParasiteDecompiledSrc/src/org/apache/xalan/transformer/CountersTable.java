/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.templates.ElemNumber;
/*   7:    */ import org.apache.xpath.NodeSetDTM;
/*   8:    */ import org.apache.xpath.XPathContext;
/*   9:    */ 
/*  10:    */ public class CountersTable
/*  11:    */   extends Hashtable
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 2159100770924179875L;
/*  14:    */   private transient NodeSetDTM m_newFound;
/*  15:    */   
/*  16:    */   Vector getCounters(ElemNumber numberElem)
/*  17:    */   {
/*  18: 61 */     Vector counters = (Vector)get(numberElem);
/*  19:    */     
/*  20: 63 */     return null == counters ? putElemNumber(numberElem) : counters;
/*  21:    */   }
/*  22:    */   
/*  23:    */   Vector putElemNumber(ElemNumber numberElem)
/*  24:    */   {
/*  25: 78 */     Vector counters = new Vector();
/*  26:    */     
/*  27: 80 */     put(numberElem, counters);
/*  28:    */     
/*  29: 82 */     return counters;
/*  30:    */   }
/*  31:    */   
/*  32:    */   void appendBtoFList(NodeSetDTM flist, NodeSetDTM blist)
/*  33:    */   {
/*  34:101 */     int n = blist.size();
/*  35:103 */     for (int i = n - 1; i >= 0; i--) {
/*  36:105 */       flist.addElement(blist.item(i));
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:112 */   transient int m_countersMade = 0;
/*  41:    */   
/*  42:    */   public int countNode(XPathContext support, ElemNumber numberElem, int node)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:130 */     int count = 0;
/*  46:131 */     Vector counters = getCounters(numberElem);
/*  47:132 */     int nCounters = counters.size();
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:136 */     int target = numberElem.getTargetNode(support, node);
/*  52:138 */     if (-1 != target)
/*  53:    */     {
/*  54:140 */       for (int i = 0; i < nCounters; i++)
/*  55:    */       {
/*  56:142 */         Counter counter = (Counter)counters.elementAt(i);
/*  57:    */         
/*  58:144 */         count = counter.getPreviouslyCounted(support, target);
/*  59:146 */         if (count > 0) {
/*  60:147 */           return count;
/*  61:    */         }
/*  62:    */       }
/*  63:156 */       count = 0;
/*  64:157 */       if (this.m_newFound == null) {
/*  65:158 */         this.m_newFound = new NodeSetDTM(support.getDTMManager());
/*  66:    */       }
/*  67:160 */       for (; -1 != target; target = numberElem.getPreviousNode(support, target))
/*  68:    */       {
/*  69:167 */         if (0 != count) {
/*  70:169 */           for (int i = 0; i < nCounters; i++)
/*  71:    */           {
/*  72:171 */             Counter counter = (Counter)counters.elementAt(i);
/*  73:172 */             int cacheLen = counter.m_countNodes.size();
/*  74:174 */             if ((cacheLen > 0) && (counter.m_countNodes.elementAt(cacheLen - 1) == target))
/*  75:    */             {
/*  76:178 */               count += cacheLen + counter.m_countNodesStartCount;
/*  77:180 */               if (cacheLen > 0) {
/*  78:181 */                 appendBtoFList(counter.m_countNodes, this.m_newFound);
/*  79:    */               }
/*  80:183 */               this.m_newFound.removeAllElements();
/*  81:    */               
/*  82:185 */               return count;
/*  83:    */             }
/*  84:    */           }
/*  85:    */         }
/*  86:190 */         this.m_newFound.addElement(target);
/*  87:    */         
/*  88:192 */         count++;
/*  89:    */       }
/*  90:197 */       Counter counter = new Counter(numberElem, new NodeSetDTM(support.getDTMManager()));
/*  91:    */       
/*  92:199 */       this.m_countersMade += 1;
/*  93:    */       
/*  94:201 */       appendBtoFList(counter.m_countNodes, this.m_newFound);
/*  95:202 */       this.m_newFound.removeAllElements();
/*  96:203 */       counters.addElement(counter);
/*  97:    */     }
/*  98:206 */     return count;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.CountersTable
 * JD-Core Version:    0.7.0.1
 */