/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ 
/*   6:    */ public class DTMChildIterNodeList
/*   7:    */   extends DTMNodeListBase
/*   8:    */ {
/*   9:    */   private int m_firstChild;
/*  10:    */   private DTM m_parentDTM;
/*  11:    */   
/*  12:    */   private DTMChildIterNodeList() {}
/*  13:    */   
/*  14:    */   public DTMChildIterNodeList(DTM parentDTM, int parentHandle)
/*  15:    */   {
/*  16: 78 */     this.m_parentDTM = parentDTM;
/*  17: 79 */     this.m_firstChild = parentDTM.getFirstChild(parentHandle);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Node item(int index)
/*  21:    */   {
/*  22: 96 */     int handle = this.m_firstChild;
/*  23:    */     do
/*  24:    */     {
/*  25: 98 */       handle = this.m_parentDTM.getNextSibling(handle);index--;
/*  26: 97 */     } while ((index >= 0) && (handle != -1));
/*  27:100 */     if (handle == -1) {
/*  28:101 */       return null;
/*  29:    */     }
/*  30:103 */     return this.m_parentDTM.getNode(handle);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getLength()
/*  34:    */   {
/*  35:111 */     int count = 0;
/*  36:112 */     for (int handle = this.m_firstChild; handle != -1; handle = this.m_parentDTM.getNextSibling(handle)) {
/*  37:115 */       count++;
/*  38:    */     }
/*  39:117 */     return count;
/*  40:    */   }
/*  41:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMChildIterNodeList
 * JD-Core Version:    0.7.0.1
 */