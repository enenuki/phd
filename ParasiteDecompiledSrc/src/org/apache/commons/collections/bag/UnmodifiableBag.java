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
/*  11:    */ import org.apache.commons.collections.Unmodifiable;
/*  12:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  13:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  14:    */ 
/*  15:    */ public final class UnmodifiableBag
/*  16:    */   extends AbstractBagDecorator
/*  17:    */   implements Unmodifiable, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -1873799975157099624L;
/*  20:    */   
/*  21:    */   public static Bag decorate(Bag bag)
/*  22:    */   {
/*  23: 58 */     if ((bag instanceof Unmodifiable)) {
/*  24: 59 */       return bag;
/*  25:    */     }
/*  26: 61 */     return new UnmodifiableBag(bag);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private UnmodifiableBag(Bag bag)
/*  30:    */   {
/*  31: 72 */     super(bag);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void writeObject(ObjectOutputStream out)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 83 */     out.defaultWriteObject();
/*  38: 84 */     out.writeObject(this.collection);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void readObject(ObjectInputStream in)
/*  42:    */     throws IOException, ClassNotFoundException
/*  43:    */   {
/*  44: 95 */     in.defaultReadObject();
/*  45: 96 */     this.collection = ((Collection)in.readObject());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Iterator iterator()
/*  49:    */   {
/*  50:101 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean add(Object object)
/*  54:    */   {
/*  55:105 */     throw new UnsupportedOperationException();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean addAll(Collection coll)
/*  59:    */   {
/*  60:109 */     throw new UnsupportedOperationException();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void clear()
/*  64:    */   {
/*  65:113 */     throw new UnsupportedOperationException();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean remove(Object object)
/*  69:    */   {
/*  70:117 */     throw new UnsupportedOperationException();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean removeAll(Collection coll)
/*  74:    */   {
/*  75:121 */     throw new UnsupportedOperationException();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean retainAll(Collection coll)
/*  79:    */   {
/*  80:125 */     throw new UnsupportedOperationException();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean add(Object object, int count)
/*  84:    */   {
/*  85:130 */     throw new UnsupportedOperationException();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean remove(Object object, int count)
/*  89:    */   {
/*  90:134 */     throw new UnsupportedOperationException();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Set uniqueSet()
/*  94:    */   {
/*  95:138 */     Set set = getBag().uniqueSet();
/*  96:139 */     return UnmodifiableSet.decorate(set);
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.UnmodifiableBag
 * JD-Core Version:    0.7.0.1
 */