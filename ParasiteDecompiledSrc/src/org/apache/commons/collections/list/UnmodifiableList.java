/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import org.apache.commons.collections.Unmodifiable;
/*   8:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*   9:    */ import org.apache.commons.collections.iterators.UnmodifiableListIterator;
/*  10:    */ 
/*  11:    */ public final class UnmodifiableList
/*  12:    */   extends AbstractSerializableListDecorator
/*  13:    */   implements Unmodifiable
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = 6595182819922443652L;
/*  16:    */   
/*  17:    */   public static List decorate(List list)
/*  18:    */   {
/*  19: 52 */     if ((list instanceof Unmodifiable)) {
/*  20: 53 */       return list;
/*  21:    */     }
/*  22: 55 */     return new UnmodifiableList(list);
/*  23:    */   }
/*  24:    */   
/*  25:    */   private UnmodifiableList(List list)
/*  26:    */   {
/*  27: 66 */     super(list);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Iterator iterator()
/*  31:    */   {
/*  32: 71 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean add(Object object)
/*  36:    */   {
/*  37: 75 */     throw new UnsupportedOperationException();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean addAll(Collection coll)
/*  41:    */   {
/*  42: 79 */     throw new UnsupportedOperationException();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void clear()
/*  46:    */   {
/*  47: 83 */     throw new UnsupportedOperationException();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean remove(Object object)
/*  51:    */   {
/*  52: 87 */     throw new UnsupportedOperationException();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean removeAll(Collection coll)
/*  56:    */   {
/*  57: 91 */     throw new UnsupportedOperationException();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean retainAll(Collection coll)
/*  61:    */   {
/*  62: 95 */     throw new UnsupportedOperationException();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ListIterator listIterator()
/*  66:    */   {
/*  67:100 */     return UnmodifiableListIterator.decorate(getList().listIterator());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ListIterator listIterator(int index)
/*  71:    */   {
/*  72:104 */     return UnmodifiableListIterator.decorate(getList().listIterator(index));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void add(int index, Object object)
/*  76:    */   {
/*  77:108 */     throw new UnsupportedOperationException();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean addAll(int index, Collection coll)
/*  81:    */   {
/*  82:112 */     throw new UnsupportedOperationException();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object remove(int index)
/*  86:    */   {
/*  87:116 */     throw new UnsupportedOperationException();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object set(int index, Object object)
/*  91:    */   {
/*  92:120 */     throw new UnsupportedOperationException();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List subList(int fromIndex, int toIndex)
/*  96:    */   {
/*  97:124 */     List sub = getList().subList(fromIndex, toIndex);
/*  98:125 */     return new UnmodifiableList(sub);
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.UnmodifiableList
 * JD-Core Version:    0.7.0.1
 */