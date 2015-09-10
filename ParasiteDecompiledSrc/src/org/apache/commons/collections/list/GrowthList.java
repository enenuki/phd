/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class GrowthList
/*   9:    */   extends AbstractSerializableListDecorator
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -3620001881672L;
/*  12:    */   
/*  13:    */   public static List decorate(List list)
/*  14:    */   {
/*  15: 70 */     return new GrowthList(list);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public GrowthList()
/*  19:    */   {
/*  20: 78 */     super(new ArrayList());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public GrowthList(int initialSize)
/*  24:    */   {
/*  25: 88 */     super(new ArrayList(initialSize));
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected GrowthList(List list)
/*  29:    */   {
/*  30: 98 */     super(list);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void add(int index, Object element)
/*  34:    */   {
/*  35:121 */     int size = getList().size();
/*  36:122 */     if (index > size) {
/*  37:123 */       getList().addAll(Collections.nCopies(index - size, null));
/*  38:    */     }
/*  39:125 */     getList().add(index, element);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean addAll(int index, Collection coll)
/*  43:    */   {
/*  44:149 */     int size = getList().size();
/*  45:150 */     boolean result = false;
/*  46:151 */     if (index > size)
/*  47:    */     {
/*  48:152 */       getList().addAll(Collections.nCopies(index - size, null));
/*  49:153 */       result = true;
/*  50:    */     }
/*  51:155 */     return getList().addAll(index, coll) | result;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object set(int index, Object element)
/*  55:    */   {
/*  56:179 */     int size = getList().size();
/*  57:180 */     if (index >= size) {
/*  58:181 */       getList().addAll(Collections.nCopies(index - size + 1, null));
/*  59:    */     }
/*  60:183 */     return getList().set(index, element);
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.GrowthList
 * JD-Core Version:    0.7.0.1
 */