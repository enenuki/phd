/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.ListIterator;
/*   6:    */ import org.apache.commons.collections.collection.AbstractCollectionDecorator;
/*   7:    */ 
/*   8:    */ public abstract class AbstractListDecorator
/*   9:    */   extends AbstractCollectionDecorator
/*  10:    */   implements List
/*  11:    */ {
/*  12:    */   protected AbstractListDecorator() {}
/*  13:    */   
/*  14:    */   protected AbstractListDecorator(List list)
/*  15:    */   {
/*  16: 52 */     super(list);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected List getList()
/*  20:    */   {
/*  21: 61 */     return (List)getCollection();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void add(int index, Object object)
/*  25:    */   {
/*  26: 66 */     getList().add(index, object);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean addAll(int index, Collection coll)
/*  30:    */   {
/*  31: 70 */     return getList().addAll(index, coll);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Object get(int index)
/*  35:    */   {
/*  36: 74 */     return getList().get(index);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int indexOf(Object object)
/*  40:    */   {
/*  41: 78 */     return getList().indexOf(object);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int lastIndexOf(Object object)
/*  45:    */   {
/*  46: 82 */     return getList().lastIndexOf(object);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ListIterator listIterator()
/*  50:    */   {
/*  51: 86 */     return getList().listIterator();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ListIterator listIterator(int index)
/*  55:    */   {
/*  56: 90 */     return getList().listIterator(index);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Object remove(int index)
/*  60:    */   {
/*  61: 94 */     return getList().remove(index);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object set(int index, Object object)
/*  65:    */   {
/*  66: 98 */     return getList().set(index, object);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List subList(int fromIndex, int toIndex)
/*  70:    */   {
/*  71:102 */     return getList().subList(fromIndex, toIndex);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.AbstractListDecorator
 * JD-Core Version:    0.7.0.1
 */