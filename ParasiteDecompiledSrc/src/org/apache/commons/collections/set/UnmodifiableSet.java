/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.apache.commons.collections.Unmodifiable;
/*  7:   */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  8:   */ 
/*  9:   */ public final class UnmodifiableSet
/* 10:   */   extends AbstractSerializableSetDecorator
/* 11:   */   implements Unmodifiable
/* 12:   */ {
/* 13:   */   private static final long serialVersionUID = 6499119872185240161L;
/* 14:   */   
/* 15:   */   public static Set decorate(Set set)
/* 16:   */   {
/* 17:50 */     if ((set instanceof Unmodifiable)) {
/* 18:51 */       return set;
/* 19:   */     }
/* 20:53 */     return new UnmodifiableSet(set);
/* 21:   */   }
/* 22:   */   
/* 23:   */   private UnmodifiableSet(Set set)
/* 24:   */   {
/* 25:64 */     super(set);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Iterator iterator()
/* 29:   */   {
/* 30:69 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean add(Object object)
/* 34:   */   {
/* 35:73 */     throw new UnsupportedOperationException();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean addAll(Collection coll)
/* 39:   */   {
/* 40:77 */     throw new UnsupportedOperationException();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void clear()
/* 44:   */   {
/* 45:81 */     throw new UnsupportedOperationException();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean remove(Object object)
/* 49:   */   {
/* 50:85 */     throw new UnsupportedOperationException();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean removeAll(Collection coll)
/* 54:   */   {
/* 55:89 */     throw new UnsupportedOperationException();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean retainAll(Collection coll)
/* 59:   */   {
/* 60:93 */     throw new UnsupportedOperationException();
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.UnmodifiableSet
 * JD-Core Version:    0.7.0.1
 */