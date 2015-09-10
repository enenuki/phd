/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.utils.IntVector;
/*   6:    */ import org.w3c.dom.Node;
/*   7:    */ 
/*   8:    */ public class DTMAxisIterNodeList
/*   9:    */   extends DTMNodeListBase
/*  10:    */ {
/*  11:    */   private DTM m_dtm;
/*  12:    */   private DTMAxisIterator m_iter;
/*  13:    */   private IntVector m_cachedNodes;
/*  14: 62 */   private int m_last = -1;
/*  15:    */   
/*  16:    */   private DTMAxisIterNodeList() {}
/*  17:    */   
/*  18:    */   public DTMAxisIterNodeList(DTM dtm, DTMAxisIterator dtmAxisIterator)
/*  19:    */   {
/*  20: 73 */     if (dtmAxisIterator == null)
/*  21:    */     {
/*  22: 74 */       this.m_last = 0;
/*  23:    */     }
/*  24:    */     else
/*  25:    */     {
/*  26: 76 */       this.m_cachedNodes = new IntVector();
/*  27: 77 */       this.m_dtm = dtm;
/*  28:    */     }
/*  29: 79 */     this.m_iter = dtmAxisIterator;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public DTMAxisIterator getDTMAxisIterator()
/*  33:    */   {
/*  34: 88 */     return this.m_iter;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Node item(int index)
/*  38:    */   {
/*  39:105 */     if (this.m_iter != null)
/*  40:    */     {
/*  41:107 */       int count = this.m_cachedNodes.size();
/*  42:    */       int node;
/*  43:109 */       if (count > index)
/*  44:    */       {
/*  45:110 */         node = this.m_cachedNodes.elementAt(index);
/*  46:111 */         return this.m_dtm.getNode(node);
/*  47:    */       }
/*  48:112 */       if (this.m_last == -1)
/*  49:    */       {
/*  50:114 */         while (((node = this.m_iter.next()) != -1) && (count <= index))
/*  51:    */         {
/*  52:115 */           this.m_cachedNodes.addElement(node);
/*  53:116 */           count++;
/*  54:    */         }
/*  55:118 */         if (node == -1) {
/*  56:119 */           this.m_last = count;
/*  57:    */         } else {
/*  58:121 */           return this.m_dtm.getNode(node);
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:125 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getLength()
/*  66:    */   {
/*  67:133 */     if (this.m_last == -1)
/*  68:    */     {
/*  69:    */       int node;
/*  70:135 */       while ((node = this.m_iter.next()) != -1)
/*  71:    */       {
/*  72:    */         int i;
/*  73:136 */         this.m_cachedNodes.addElement(i);
/*  74:    */       }
/*  75:138 */       this.m_last = this.m_cachedNodes.size();
/*  76:    */     }
/*  77:140 */     return this.m_last;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMAxisIterNodeList
 * JD-Core Version:    0.7.0.1
 */