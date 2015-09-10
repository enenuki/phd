/*  1:   */ package org.apache.commons.collections.collection;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.commons.collections.Unmodifiable;
/*  6:   */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  7:   */ 
/*  8:   */ public final class UnmodifiableCollection
/*  9:   */   extends AbstractSerializableCollectionDecorator
/* 10:   */   implements Unmodifiable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = -239892006883819945L;
/* 13:   */   
/* 14:   */   public static Collection decorate(Collection coll)
/* 15:   */   {
/* 16:52 */     if ((coll instanceof Unmodifiable)) {
/* 17:53 */       return coll;
/* 18:   */     }
/* 19:55 */     return new UnmodifiableCollection(coll);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private UnmodifiableCollection(Collection coll)
/* 23:   */   {
/* 24:66 */     super(coll);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Iterator iterator()
/* 28:   */   {
/* 29:71 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean add(Object object)
/* 33:   */   {
/* 34:75 */     throw new UnsupportedOperationException();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean addAll(Collection coll)
/* 38:   */   {
/* 39:79 */     throw new UnsupportedOperationException();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void clear()
/* 43:   */   {
/* 44:83 */     throw new UnsupportedOperationException();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean remove(Object object)
/* 48:   */   {
/* 49:87 */     throw new UnsupportedOperationException();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean removeAll(Collection coll)
/* 53:   */   {
/* 54:91 */     throw new UnsupportedOperationException();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean retainAll(Collection coll)
/* 58:   */   {
/* 59:95 */     throw new UnsupportedOperationException();
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.UnmodifiableCollection
 * JD-Core Version:    0.7.0.1
 */