/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import org.apache.commons.collections.Predicate;
/*   8:    */ import org.apache.commons.collections.collection.PredicatedCollection;
/*   9:    */ import org.apache.commons.collections.iterators.AbstractListIteratorDecorator;
/*  10:    */ 
/*  11:    */ public class PredicatedList
/*  12:    */   extends PredicatedCollection
/*  13:    */   implements List
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = -5722039223898659102L;
/*  16:    */   
/*  17:    */   public static List decorate(List list, Predicate predicate)
/*  18:    */   {
/*  19: 64 */     return new PredicatedList(list, predicate);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected PredicatedList(List list, Predicate predicate)
/*  23:    */   {
/*  24: 80 */     super(list, predicate);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected List getList()
/*  28:    */   {
/*  29: 89 */     return (List)getCollection();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object get(int index)
/*  33:    */   {
/*  34: 94 */     return getList().get(index);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int indexOf(Object object)
/*  38:    */   {
/*  39: 98 */     return getList().indexOf(object);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int lastIndexOf(Object object)
/*  43:    */   {
/*  44:102 */     return getList().lastIndexOf(object);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object remove(int index)
/*  48:    */   {
/*  49:106 */     return getList().remove(index);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void add(int index, Object object)
/*  53:    */   {
/*  54:111 */     validate(object);
/*  55:112 */     getList().add(index, object);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean addAll(int index, Collection coll)
/*  59:    */   {
/*  60:116 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/*  61:117 */       validate(it.next());
/*  62:    */     }
/*  63:119 */     return getList().addAll(index, coll);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ListIterator listIterator()
/*  67:    */   {
/*  68:123 */     return listIterator(0);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ListIterator listIterator(int i)
/*  72:    */   {
/*  73:127 */     return new PredicatedListIterator(getList().listIterator(i));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object set(int index, Object object)
/*  77:    */   {
/*  78:131 */     validate(object);
/*  79:132 */     return getList().set(index, object);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List subList(int fromIndex, int toIndex)
/*  83:    */   {
/*  84:136 */     List sub = getList().subList(fromIndex, toIndex);
/*  85:137 */     return new PredicatedList(sub, this.predicate);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected class PredicatedListIterator
/*  89:    */     extends AbstractListIteratorDecorator
/*  90:    */   {
/*  91:    */     protected PredicatedListIterator(ListIterator iterator)
/*  92:    */     {
/*  93:146 */       super();
/*  94:    */     }
/*  95:    */     
/*  96:    */     public void add(Object object)
/*  97:    */     {
/*  98:150 */       PredicatedList.this.validate(object);
/*  99:151 */       this.iterator.add(object);
/* 100:    */     }
/* 101:    */     
/* 102:    */     public void set(Object object)
/* 103:    */     {
/* 104:155 */       PredicatedList.this.validate(object);
/* 105:156 */       this.iterator.set(object);
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.PredicatedList
 * JD-Core Version:    0.7.0.1
 */