/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.ListIterator;
/*   6:    */ import org.apache.commons.collections.Transformer;
/*   7:    */ import org.apache.commons.collections.collection.TransformedCollection;
/*   8:    */ import org.apache.commons.collections.iterators.AbstractListIteratorDecorator;
/*   9:    */ 
/*  10:    */ public class TransformedList
/*  11:    */   extends TransformedCollection
/*  12:    */   implements List
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 1077193035000013141L;
/*  15:    */   
/*  16:    */   public static List decorate(List list, Transformer transformer)
/*  17:    */   {
/*  18: 58 */     return new TransformedList(list, transformer);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected TransformedList(List list, Transformer transformer)
/*  22:    */   {
/*  23: 73 */     super(list, transformer);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected List getList()
/*  27:    */   {
/*  28: 82 */     return (List)this.collection;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object get(int index)
/*  32:    */   {
/*  33: 87 */     return getList().get(index);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int indexOf(Object object)
/*  37:    */   {
/*  38: 91 */     return getList().indexOf(object);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int lastIndexOf(Object object)
/*  42:    */   {
/*  43: 95 */     return getList().lastIndexOf(object);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object remove(int index)
/*  47:    */   {
/*  48: 99 */     return getList().remove(index);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void add(int index, Object object)
/*  52:    */   {
/*  53:104 */     object = transform(object);
/*  54:105 */     getList().add(index, object);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean addAll(int index, Collection coll)
/*  58:    */   {
/*  59:109 */     coll = transform(coll);
/*  60:110 */     return getList().addAll(index, coll);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ListIterator listIterator()
/*  64:    */   {
/*  65:114 */     return listIterator(0);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ListIterator listIterator(int i)
/*  69:    */   {
/*  70:118 */     return new TransformedListIterator(getList().listIterator(i));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object set(int index, Object object)
/*  74:    */   {
/*  75:122 */     object = transform(object);
/*  76:123 */     return getList().set(index, object);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List subList(int fromIndex, int toIndex)
/*  80:    */   {
/*  81:127 */     List sub = getList().subList(fromIndex, toIndex);
/*  82:128 */     return new TransformedList(sub, this.transformer);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected class TransformedListIterator
/*  86:    */     extends AbstractListIteratorDecorator
/*  87:    */   {
/*  88:    */     protected TransformedListIterator(ListIterator iterator)
/*  89:    */     {
/*  90:137 */       super();
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void add(Object object)
/*  94:    */     {
/*  95:141 */       object = TransformedList.this.transform(object);
/*  96:142 */       this.iterator.add(object);
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void set(Object object)
/* 100:    */     {
/* 101:146 */       object = TransformedList.this.transform(object);
/* 102:147 */       this.iterator.set(object);
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.TransformedList
 * JD-Core Version:    0.7.0.1
 */