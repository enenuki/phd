/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ListIterator;
/*   4:    */ 
/*   5:    */ /**
/*   6:    */  * @deprecated
/*   7:    */  */
/*   8:    */ public class ProxyListIterator
/*   9:    */   implements ListIterator
/*  10:    */ {
/*  11:    */   private ListIterator iterator;
/*  12:    */   
/*  13:    */   public ProxyListIterator() {}
/*  14:    */   
/*  15:    */   public ProxyListIterator(ListIterator iterator)
/*  16:    */   {
/*  17: 56 */     this.iterator = iterator;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void add(Object o)
/*  21:    */   {
/*  22: 68 */     getListIterator().add(o);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean hasNext()
/*  26:    */   {
/*  27: 77 */     return getListIterator().hasNext();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean hasPrevious()
/*  31:    */   {
/*  32: 86 */     return getListIterator().hasPrevious();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object next()
/*  36:    */   {
/*  37: 95 */     return getListIterator().next();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int nextIndex()
/*  41:    */   {
/*  42:104 */     return getListIterator().nextIndex();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Object previous()
/*  46:    */   {
/*  47:113 */     return getListIterator().previous();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int previousIndex()
/*  51:    */   {
/*  52:122 */     return getListIterator().previousIndex();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void remove()
/*  56:    */   {
/*  57:131 */     getListIterator().remove();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void set(Object o)
/*  61:    */   {
/*  62:140 */     getListIterator().set(o);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ListIterator getListIterator()
/*  66:    */   {
/*  67:151 */     return this.iterator;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setListIterator(ListIterator iterator)
/*  71:    */   {
/*  72:159 */     this.iterator = iterator;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ProxyListIterator
 * JD-Core Version:    0.7.0.1
 */