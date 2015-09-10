/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import org.apache.commons.collections.BoundedCollection;
/*   8:    */ import org.apache.commons.collections.iterators.AbstractListIteratorDecorator;
/*   9:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  10:    */ 
/*  11:    */ public class FixedSizeList
/*  12:    */   extends AbstractSerializableListDecorator
/*  13:    */   implements BoundedCollection
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = -2218010673611160319L;
/*  16:    */   
/*  17:    */   public static List decorate(List list)
/*  18:    */   {
/*  19: 56 */     return new FixedSizeList(list);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected FixedSizeList(List list)
/*  23:    */   {
/*  24: 67 */     super(list);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean add(Object object)
/*  28:    */   {
/*  29: 72 */     throw new UnsupportedOperationException("List is fixed size");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void add(int index, Object object)
/*  33:    */   {
/*  34: 76 */     throw new UnsupportedOperationException("List is fixed size");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean addAll(Collection coll)
/*  38:    */   {
/*  39: 80 */     throw new UnsupportedOperationException("List is fixed size");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean addAll(int index, Collection coll)
/*  43:    */   {
/*  44: 84 */     throw new UnsupportedOperationException("List is fixed size");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void clear()
/*  48:    */   {
/*  49: 88 */     throw new UnsupportedOperationException("List is fixed size");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object get(int index)
/*  53:    */   {
/*  54: 92 */     return getList().get(index);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int indexOf(Object object)
/*  58:    */   {
/*  59: 96 */     return getList().indexOf(object);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Iterator iterator()
/*  63:    */   {
/*  64:100 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int lastIndexOf(Object object)
/*  68:    */   {
/*  69:104 */     return getList().lastIndexOf(object);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ListIterator listIterator()
/*  73:    */   {
/*  74:108 */     return new FixedSizeListIterator(getList().listIterator(0));
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ListIterator listIterator(int index)
/*  78:    */   {
/*  79:112 */     return new FixedSizeListIterator(getList().listIterator(index));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object remove(int index)
/*  83:    */   {
/*  84:116 */     throw new UnsupportedOperationException("List is fixed size");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean remove(Object object)
/*  88:    */   {
/*  89:120 */     throw new UnsupportedOperationException("List is fixed size");
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean removeAll(Collection coll)
/*  93:    */   {
/*  94:124 */     throw new UnsupportedOperationException("List is fixed size");
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean retainAll(Collection coll)
/*  98:    */   {
/*  99:128 */     throw new UnsupportedOperationException("List is fixed size");
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object set(int index, Object object)
/* 103:    */   {
/* 104:132 */     return getList().set(index, object);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List subList(int fromIndex, int toIndex)
/* 108:    */   {
/* 109:136 */     List sub = getList().subList(fromIndex, toIndex);
/* 110:137 */     return new FixedSizeList(sub);
/* 111:    */   }
/* 112:    */   
/* 113:    */   static class FixedSizeListIterator
/* 114:    */     extends AbstractListIteratorDecorator
/* 115:    */   {
/* 116:    */     protected FixedSizeListIterator(ListIterator iterator)
/* 117:    */     {
/* 118:145 */       super();
/* 119:    */     }
/* 120:    */     
/* 121:    */     public void remove()
/* 122:    */     {
/* 123:148 */       throw new UnsupportedOperationException("List is fixed size");
/* 124:    */     }
/* 125:    */     
/* 126:    */     public void add(Object object)
/* 127:    */     {
/* 128:151 */       throw new UnsupportedOperationException("List is fixed size");
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isFull()
/* 133:    */   {
/* 134:156 */     return true;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int maxSize()
/* 138:    */   {
/* 139:160 */     return size();
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.FixedSizeList
 * JD-Core Version:    0.7.0.1
 */