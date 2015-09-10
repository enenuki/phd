/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Iterator;
/*   6:    */ 
/*   7:    */ public class EnumerationIterator
/*   8:    */   implements Iterator
/*   9:    */ {
/*  10:    */   private Collection collection;
/*  11:    */   private Enumeration enumeration;
/*  12:    */   private Object last;
/*  13:    */   
/*  14:    */   public EnumerationIterator()
/*  15:    */   {
/*  16: 49 */     this(null, null);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public EnumerationIterator(Enumeration enumeration)
/*  20:    */   {
/*  21: 59 */     this(enumeration, null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public EnumerationIterator(Enumeration enumeration, Collection collection)
/*  25:    */   {
/*  26: 71 */     this.enumeration = enumeration;
/*  27: 72 */     this.collection = collection;
/*  28: 73 */     this.last = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean hasNext()
/*  32:    */   {
/*  33: 85 */     return this.enumeration.hasMoreElements();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object next()
/*  37:    */   {
/*  38: 95 */     this.last = this.enumeration.nextElement();
/*  39: 96 */     return this.last;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void remove()
/*  43:    */   {
/*  44:110 */     if (this.collection != null)
/*  45:    */     {
/*  46:111 */       if (this.last != null) {
/*  47:112 */         this.collection.remove(this.last);
/*  48:    */       } else {
/*  49:114 */         throw new IllegalStateException("next() must have been called for remove() to function");
/*  50:    */       }
/*  51:    */     }
/*  52:    */     else {
/*  53:117 */       throw new UnsupportedOperationException("No Collection associated with this Iterator");
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Enumeration getEnumeration()
/*  58:    */   {
/*  59:129 */     return this.enumeration;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setEnumeration(Enumeration enumeration)
/*  63:    */   {
/*  64:138 */     this.enumeration = enumeration;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EnumerationIterator
 * JD-Core Version:    0.7.0.1
 */