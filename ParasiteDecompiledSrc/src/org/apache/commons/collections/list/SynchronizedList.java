/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.ListIterator;
/*   6:    */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*   7:    */ 
/*   8:    */ public class SynchronizedList
/*   9:    */   extends SynchronizedCollection
/*  10:    */   implements List
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -1403835447328619437L;
/*  13:    */   
/*  14:    */   public static List decorate(List list)
/*  15:    */   {
/*  16: 50 */     return new SynchronizedList(list);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected SynchronizedList(List list)
/*  20:    */   {
/*  21: 61 */     super(list);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected SynchronizedList(List list, Object lock)
/*  25:    */   {
/*  26: 72 */     super(list, lock);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected List getList()
/*  30:    */   {
/*  31: 81 */     return (List)this.collection;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void add(int index, Object object)
/*  35:    */   {
/*  36: 86 */     synchronized (this.lock)
/*  37:    */     {
/*  38: 87 */       getList().add(index, object);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean addAll(int index, Collection coll)
/*  43:    */   {
/*  44: 92 */     synchronized (this.lock)
/*  45:    */     {
/*  46: 93 */       return getList().addAll(index, coll);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object get(int index)
/*  51:    */   {
/*  52: 98 */     synchronized (this.lock)
/*  53:    */     {
/*  54: 99 */       return getList().get(index);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int indexOf(Object object)
/*  59:    */   {
/*  60:104 */     synchronized (this.lock)
/*  61:    */     {
/*  62:105 */       return getList().indexOf(object);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int lastIndexOf(Object object)
/*  67:    */   {
/*  68:110 */     synchronized (this.lock)
/*  69:    */     {
/*  70:111 */       return getList().lastIndexOf(object);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ListIterator listIterator()
/*  75:    */   {
/*  76:126 */     return getList().listIterator();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ListIterator listIterator(int index)
/*  80:    */   {
/*  81:140 */     return getList().listIterator(index);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Object remove(int index)
/*  85:    */   {
/*  86:144 */     synchronized (this.lock)
/*  87:    */     {
/*  88:145 */       return getList().remove(index);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object set(int index, Object object)
/*  93:    */   {
/*  94:150 */     synchronized (this.lock)
/*  95:    */     {
/*  96:151 */       return getList().set(index, object);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List subList(int fromIndex, int toIndex)
/* 101:    */   {
/* 102:156 */     synchronized (this.lock)
/* 103:    */     {
/* 104:157 */       List list = getList().subList(fromIndex, toIndex);
/* 105:    */       
/* 106:    */ 
/* 107:160 */       return new SynchronizedList(list, this.lock);
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.SynchronizedList
 * JD-Core Version:    0.7.0.1
 */