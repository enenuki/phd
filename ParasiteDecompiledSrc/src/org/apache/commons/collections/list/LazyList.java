/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.commons.collections.Factory;
/*   5:    */ 
/*   6:    */ public class LazyList
/*   7:    */   extends AbstractSerializableListDecorator
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -1708388017160694542L;
/*  10:    */   protected final Factory factory;
/*  11:    */   
/*  12:    */   public static List decorate(List list, Factory factory)
/*  13:    */   {
/*  14: 80 */     return new LazyList(list, factory);
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected LazyList(List list, Factory factory)
/*  18:    */   {
/*  19: 92 */     super(list);
/*  20: 93 */     if (factory == null) {
/*  21: 94 */       throw new IllegalArgumentException("Factory must not be null");
/*  22:    */     }
/*  23: 96 */     this.factory = factory;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object get(int index)
/*  27:    */   {
/*  28:111 */     int size = getList().size();
/*  29:112 */     if (index < size)
/*  30:    */     {
/*  31:114 */       Object object = getList().get(index);
/*  32:115 */       if (object == null)
/*  33:    */       {
/*  34:117 */         object = this.factory.create();
/*  35:118 */         getList().set(index, object);
/*  36:119 */         return object;
/*  37:    */       }
/*  38:122 */       return object;
/*  39:    */     }
/*  40:126 */     for (int i = size; i < index; i++) {
/*  41:127 */       getList().add(null);
/*  42:    */     }
/*  43:130 */     Object object = this.factory.create();
/*  44:131 */     getList().add(object);
/*  45:132 */     return object;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List subList(int fromIndex, int toIndex)
/*  49:    */   {
/*  50:138 */     List sub = getList().subList(fromIndex, toIndex);
/*  51:139 */     return new LazyList(sub, this.factory);
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.LazyList
 * JD-Core Version:    0.7.0.1
 */