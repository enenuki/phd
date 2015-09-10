/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.SortedSet;
/*  10:    */ import org.apache.commons.collections.Unmodifiable;
/*  11:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  12:    */ 
/*  13:    */ public final class UnmodifiableSortedSet
/*  14:    */   extends AbstractSortedSetDecorator
/*  15:    */   implements Unmodifiable, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -725356885467962424L;
/*  18:    */   
/*  19:    */   public static SortedSet decorate(SortedSet set)
/*  20:    */   {
/*  21: 54 */     if ((set instanceof Unmodifiable)) {
/*  22: 55 */       return set;
/*  23:    */     }
/*  24: 57 */     return new UnmodifiableSortedSet(set);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private void writeObject(ObjectOutputStream out)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 68 */     out.defaultWriteObject();
/*  31: 69 */     out.writeObject(this.collection);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void readObject(ObjectInputStream in)
/*  35:    */     throws IOException, ClassNotFoundException
/*  36:    */   {
/*  37: 80 */     in.defaultReadObject();
/*  38: 81 */     this.collection = ((Collection)in.readObject());
/*  39:    */   }
/*  40:    */   
/*  41:    */   private UnmodifiableSortedSet(SortedSet set)
/*  42:    */   {
/*  43: 92 */     super(set);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Iterator iterator()
/*  47:    */   {
/*  48: 97 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean add(Object object)
/*  52:    */   {
/*  53:101 */     throw new UnsupportedOperationException();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean addAll(Collection coll)
/*  57:    */   {
/*  58:105 */     throw new UnsupportedOperationException();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void clear()
/*  62:    */   {
/*  63:109 */     throw new UnsupportedOperationException();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean remove(Object object)
/*  67:    */   {
/*  68:113 */     throw new UnsupportedOperationException();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean removeAll(Collection coll)
/*  72:    */   {
/*  73:117 */     throw new UnsupportedOperationException();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean retainAll(Collection coll)
/*  77:    */   {
/*  78:121 */     throw new UnsupportedOperationException();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SortedSet subSet(Object fromElement, Object toElement)
/*  82:    */   {
/*  83:126 */     SortedSet sub = getSortedSet().subSet(fromElement, toElement);
/*  84:127 */     return new UnmodifiableSortedSet(sub);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public SortedSet headSet(Object toElement)
/*  88:    */   {
/*  89:131 */     SortedSet sub = getSortedSet().headSet(toElement);
/*  90:132 */     return new UnmodifiableSortedSet(sub);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public SortedSet tailSet(Object fromElement)
/*  94:    */   {
/*  95:136 */     SortedSet sub = getSortedSet().tailSet(fromElement);
/*  96:137 */     return new UnmodifiableSortedSet(sub);
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.UnmodifiableSortedSet
 * JD-Core Version:    0.7.0.1
 */