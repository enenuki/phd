/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ 
/*   5:    */ /**
/*   6:    */  * @deprecated
/*   7:    */  */
/*   8:    */ public class ProxyIterator
/*   9:    */   implements Iterator
/*  10:    */ {
/*  11:    */   private Iterator iterator;
/*  12:    */   
/*  13:    */   public ProxyIterator() {}
/*  14:    */   
/*  15:    */   public ProxyIterator(Iterator iterator)
/*  16:    */   {
/*  17: 54 */     this.iterator = iterator;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean hasNext()
/*  21:    */   {
/*  22: 66 */     return getIterator().hasNext();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Object next()
/*  26:    */   {
/*  27: 77 */     return getIterator().next();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void remove()
/*  31:    */   {
/*  32: 85 */     getIterator().remove();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Iterator getIterator()
/*  36:    */   {
/*  37: 94 */     return this.iterator;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setIterator(Iterator iterator)
/*  41:    */   {
/*  42:100 */     this.iterator = iterator;
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ProxyIterator
 * JD-Core Version:    0.7.0.1
 */