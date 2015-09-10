/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.apache.commons.collections.MapIterator;
/*   8:    */ import org.apache.commons.collections.ResettableIterator;
/*   9:    */ 
/*  10:    */ public class EntrySetMapIterator
/*  11:    */   implements MapIterator, ResettableIterator
/*  12:    */ {
/*  13:    */   private final Map map;
/*  14:    */   private Iterator iterator;
/*  15:    */   private Map.Entry last;
/*  16: 47 */   private boolean canRemove = false;
/*  17:    */   
/*  18:    */   public EntrySetMapIterator(Map map)
/*  19:    */   {
/*  20: 56 */     this.map = map;
/*  21: 57 */     this.iterator = map.entrySet().iterator();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean hasNext()
/*  25:    */   {
/*  26: 67 */     return this.iterator.hasNext();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object next()
/*  30:    */   {
/*  31: 77 */     this.last = ((Map.Entry)this.iterator.next());
/*  32: 78 */     this.canRemove = true;
/*  33: 79 */     return this.last.getKey();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void remove()
/*  37:    */   {
/*  38: 94 */     if (!this.canRemove) {
/*  39: 95 */       throw new IllegalStateException("Iterator remove() can only be called once after next()");
/*  40:    */     }
/*  41: 97 */     this.iterator.remove();
/*  42: 98 */     this.last = null;
/*  43: 99 */     this.canRemove = false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object getKey()
/*  47:    */   {
/*  48:111 */     if (this.last == null) {
/*  49:112 */       throw new IllegalStateException("Iterator getKey() can only be called after next() and before remove()");
/*  50:    */     }
/*  51:114 */     return this.last.getKey();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object getValue()
/*  55:    */   {
/*  56:125 */     if (this.last == null) {
/*  57:126 */       throw new IllegalStateException("Iterator getValue() can only be called after next() and before remove()");
/*  58:    */     }
/*  59:128 */     return this.last.getValue();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object setValue(Object value)
/*  63:    */   {
/*  64:142 */     if (this.last == null) {
/*  65:143 */       throw new IllegalStateException("Iterator setValue() can only be called after next() and before remove()");
/*  66:    */     }
/*  67:145 */     return this.last.setValue(value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void reset()
/*  71:    */   {
/*  72:153 */     this.iterator = this.map.entrySet().iterator();
/*  73:154 */     this.last = null;
/*  74:155 */     this.canRemove = false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:164 */     if (this.last != null) {
/*  80:165 */       return "MapIterator[" + getKey() + "=" + getValue() + "]";
/*  81:    */     }
/*  82:167 */     return "MapIterator[]";
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EntrySetMapIterator
 * JD-Core Version:    0.7.0.1
 */