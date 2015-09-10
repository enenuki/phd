/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.dtm.DTMDOMException;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   7:    */ import org.w3c.dom.DOMException;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.traversal.NodeFilter;
/*  10:    */ import org.w3c.dom.traversal.NodeIterator;
/*  11:    */ 
/*  12:    */ public class DTMNodeIterator
/*  13:    */   implements NodeIterator
/*  14:    */ {
/*  15:    */   private DTMIterator dtm_iter;
/*  16: 62 */   private boolean valid = true;
/*  17:    */   
/*  18:    */   public DTMNodeIterator(DTMIterator dtmIterator)
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 74 */       this.dtm_iter = ((DTMIterator)dtmIterator.clone());
/*  23:    */     }
/*  24:    */     catch (CloneNotSupportedException cnse)
/*  25:    */     {
/*  26: 78 */       throw new WrappedRuntimeException(cnse);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public DTMIterator getDTMIterator()
/*  31:    */   {
/*  32: 87 */     return this.dtm_iter;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void detach()
/*  36:    */   {
/*  37:103 */     this.valid = false;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean getExpandEntityReferences()
/*  41:    */   {
/*  42:113 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public NodeFilter getFilter()
/*  46:    */   {
/*  47:130 */     throw new DTMDOMException((short)9);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Node getRoot()
/*  51:    */   {
/*  52:139 */     int handle = this.dtm_iter.getRoot();
/*  53:140 */     return this.dtm_iter.getDTM(handle).getNode(handle);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getWhatToShow()
/*  57:    */   {
/*  58:149 */     return this.dtm_iter.getWhatToShow();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Node nextNode()
/*  62:    */     throws DOMException
/*  63:    */   {
/*  64:160 */     if (!this.valid) {
/*  65:161 */       throw new DTMDOMException((short)11);
/*  66:    */     }
/*  67:163 */     int handle = this.dtm_iter.nextNode();
/*  68:164 */     if (handle == -1) {
/*  69:165 */       return null;
/*  70:    */     }
/*  71:166 */     return this.dtm_iter.getDTM(handle).getNode(handle);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Node previousNode()
/*  75:    */   {
/*  76:178 */     if (!this.valid) {
/*  77:179 */       throw new DTMDOMException((short)11);
/*  78:    */     }
/*  79:181 */     int handle = this.dtm_iter.previousNode();
/*  80:182 */     if (handle == -1) {
/*  81:183 */       return null;
/*  82:    */     }
/*  83:184 */     return this.dtm_iter.getDTM(handle).getNode(handle);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMNodeIterator
 * JD-Core Version:    0.7.0.1
 */