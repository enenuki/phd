/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.dtm.DTMIterator;
/*   5:    */ import org.w3c.dom.Node;
/*   6:    */ 
/*   7:    */ public class DTMNodeList
/*   8:    */   extends DTMNodeListBase
/*   9:    */ {
/*  10:    */   private DTMIterator m_iter;
/*  11:    */   
/*  12:    */   private DTMNodeList() {}
/*  13:    */   
/*  14:    */   public DTMNodeList(DTMIterator dtmIterator)
/*  15:    */   {
/*  16: 73 */     if (dtmIterator != null)
/*  17:    */     {
/*  18: 74 */       int pos = dtmIterator.getCurrentPos();
/*  19:    */       try
/*  20:    */       {
/*  21: 76 */         this.m_iter = dtmIterator.cloneWithReset();
/*  22:    */       }
/*  23:    */       catch (CloneNotSupportedException cnse)
/*  24:    */       {
/*  25: 78 */         this.m_iter = dtmIterator;
/*  26:    */       }
/*  27: 80 */       this.m_iter.setShouldCacheNodes(true);
/*  28: 81 */       this.m_iter.runTo(-1);
/*  29: 82 */       this.m_iter.setCurrentPos(pos);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public DTMIterator getDTMIterator()
/*  34:    */   {
/*  35: 92 */     return this.m_iter;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Node item(int index)
/*  39:    */   {
/*  40:109 */     if (this.m_iter != null)
/*  41:    */     {
/*  42:110 */       int handle = this.m_iter.item(index);
/*  43:111 */       if (handle == -1) {
/*  44:112 */         return null;
/*  45:    */       }
/*  46:114 */       return this.m_iter.getDTM(handle).getNode(handle);
/*  47:    */     }
/*  48:116 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getLength()
/*  52:    */   {
/*  53:125 */     return this.m_iter != null ? this.m_iter.getLength() : 0;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMNodeList
 * JD-Core Version:    0.7.0.1
 */