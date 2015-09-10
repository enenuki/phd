/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.commons.collections.BoundedCollection;
/*   6:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*   7:    */ 
/*   8:    */ public final class UnmodifiableBoundedCollection
/*   9:    */   extends AbstractSerializableCollectionDecorator
/*  10:    */   implements BoundedCollection
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -7112672385450340330L;
/*  13:    */   
/*  14:    */   public static BoundedCollection decorate(BoundedCollection coll)
/*  15:    */   {
/*  16: 57 */     return new UnmodifiableBoundedCollection(coll);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static BoundedCollection decorateUsing(Collection coll)
/*  20:    */   {
/*  21: 71 */     if (coll == null) {
/*  22: 72 */       throw new IllegalArgumentException("The collection must not be null");
/*  23:    */     }
/*  24: 76 */     for (int i = 0; i < 1000; i++)
/*  25:    */     {
/*  26: 77 */       if ((coll instanceof BoundedCollection)) {
/*  27:    */         break;
/*  28:    */       }
/*  29: 79 */       if ((coll instanceof AbstractCollectionDecorator))
/*  30:    */       {
/*  31: 80 */         coll = ((AbstractCollectionDecorator)coll).collection;
/*  32:    */       }
/*  33:    */       else
/*  34:    */       {
/*  35: 81 */         if (!(coll instanceof SynchronizedCollection)) {
/*  36:    */           break;
/*  37:    */         }
/*  38: 82 */         coll = ((SynchronizedCollection)coll).collection;
/*  39:    */       }
/*  40:    */     }
/*  41: 88 */     if (!(coll instanceof BoundedCollection)) {
/*  42: 89 */       throw new IllegalArgumentException("The collection is not a bounded collection");
/*  43:    */     }
/*  44: 91 */     return new UnmodifiableBoundedCollection((BoundedCollection)coll);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private UnmodifiableBoundedCollection(BoundedCollection coll)
/*  48:    */   {
/*  49:101 */     super(coll);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Iterator iterator()
/*  53:    */   {
/*  54:106 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean add(Object object)
/*  58:    */   {
/*  59:110 */     throw new UnsupportedOperationException();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean addAll(Collection coll)
/*  63:    */   {
/*  64:114 */     throw new UnsupportedOperationException();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void clear()
/*  68:    */   {
/*  69:118 */     throw new UnsupportedOperationException();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean remove(Object object)
/*  73:    */   {
/*  74:122 */     throw new UnsupportedOperationException();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean removeAll(Collection coll)
/*  78:    */   {
/*  79:126 */     throw new UnsupportedOperationException();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean retainAll(Collection coll)
/*  83:    */   {
/*  84:130 */     throw new UnsupportedOperationException();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isFull()
/*  88:    */   {
/*  89:135 */     return ((BoundedCollection)this.collection).isFull();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int maxSize()
/*  93:    */   {
/*  94:139 */     return ((BoundedCollection)this.collection).maxSize();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.UnmodifiableBoundedCollection
 * JD-Core Version:    0.7.0.1
 */