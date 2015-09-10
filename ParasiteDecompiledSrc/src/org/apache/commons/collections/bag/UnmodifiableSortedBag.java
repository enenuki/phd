/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.commons.collections.Bag;
/*  11:    */ import org.apache.commons.collections.SortedBag;
/*  12:    */ import org.apache.commons.collections.Unmodifiable;
/*  13:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  14:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  15:    */ 
/*  16:    */ public final class UnmodifiableSortedBag
/*  17:    */   extends AbstractSortedBagDecorator
/*  18:    */   implements Unmodifiable, Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -3190437252665717841L;
/*  21:    */   
/*  22:    */   public static SortedBag decorate(SortedBag bag)
/*  23:    */   {
/*  24: 58 */     if ((bag instanceof Unmodifiable)) {
/*  25: 59 */       return bag;
/*  26:    */     }
/*  27: 61 */     return new UnmodifiableSortedBag(bag);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private UnmodifiableSortedBag(SortedBag bag)
/*  31:    */   {
/*  32: 72 */     super(bag);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void writeObject(ObjectOutputStream out)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 83 */     out.defaultWriteObject();
/*  39: 84 */     out.writeObject(this.collection);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void readObject(ObjectInputStream in)
/*  43:    */     throws IOException, ClassNotFoundException
/*  44:    */   {
/*  45: 95 */     in.defaultReadObject();
/*  46: 96 */     this.collection = ((Collection)in.readObject());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Iterator iterator()
/*  50:    */   {
/*  51:101 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean add(Object object)
/*  55:    */   {
/*  56:105 */     throw new UnsupportedOperationException();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean addAll(Collection coll)
/*  60:    */   {
/*  61:109 */     throw new UnsupportedOperationException();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void clear()
/*  65:    */   {
/*  66:113 */     throw new UnsupportedOperationException();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean remove(Object object)
/*  70:    */   {
/*  71:117 */     throw new UnsupportedOperationException();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean removeAll(Collection coll)
/*  75:    */   {
/*  76:121 */     throw new UnsupportedOperationException();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean retainAll(Collection coll)
/*  80:    */   {
/*  81:125 */     throw new UnsupportedOperationException();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean add(Object object, int count)
/*  85:    */   {
/*  86:130 */     throw new UnsupportedOperationException();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean remove(Object object, int count)
/*  90:    */   {
/*  91:134 */     throw new UnsupportedOperationException();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Set uniqueSet()
/*  95:    */   {
/*  96:138 */     Set set = getBag().uniqueSet();
/*  97:139 */     return UnmodifiableSet.decorate(set);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.UnmodifiableSortedBag
 * JD-Core Version:    0.7.0.1
 */