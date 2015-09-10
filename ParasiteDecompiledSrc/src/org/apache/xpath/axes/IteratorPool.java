/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   7:    */ 
/*   8:    */ public final class IteratorPool
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = -460927331149566998L;
/*  12:    */   private final DTMIterator m_orig;
/*  13:    */   private final ArrayList m_freeStack;
/*  14:    */   
/*  15:    */   public IteratorPool(DTMIterator original)
/*  16:    */   {
/*  17: 53 */     this.m_orig = original;
/*  18: 54 */     this.m_freeStack = new ArrayList();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public synchronized DTMIterator getInstanceOrThrow()
/*  22:    */     throws CloneNotSupportedException
/*  23:    */   {
/*  24: 66 */     if (this.m_freeStack.isEmpty()) {
/*  25: 70 */       return (DTMIterator)this.m_orig.clone();
/*  26:    */     }
/*  27: 75 */     DTMIterator result = (DTMIterator)this.m_freeStack.remove(this.m_freeStack.size() - 1);
/*  28: 76 */     return result;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public synchronized DTMIterator getInstance()
/*  32:    */   {
/*  33: 88 */     if (this.m_freeStack.isEmpty()) {
/*  34:    */       try
/*  35:    */       {
/*  36: 94 */         return (DTMIterator)this.m_orig.clone();
/*  37:    */       }
/*  38:    */       catch (Exception ex)
/*  39:    */       {
/*  40: 98 */         throw new WrappedRuntimeException(ex);
/*  41:    */       }
/*  42:    */     }
/*  43:104 */     DTMIterator result = (DTMIterator)this.m_freeStack.remove(this.m_freeStack.size() - 1);
/*  44:105 */     return result;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public synchronized void freeInstance(DTMIterator obj)
/*  48:    */   {
/*  49:117 */     this.m_freeStack.add(obj);
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.IteratorPool
 * JD-Core Version:    0.7.0.1
 */