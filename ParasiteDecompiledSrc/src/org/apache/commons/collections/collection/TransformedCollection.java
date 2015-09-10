/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.collections.Transformer;
/*   8:    */ 
/*   9:    */ public class TransformedCollection
/*  10:    */   extends AbstractSerializableCollectionDecorator
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 8692300188161871514L;
/*  13:    */   protected final Transformer transformer;
/*  14:    */   
/*  15:    */   public static Collection decorate(Collection coll, Transformer transformer)
/*  16:    */   {
/*  17: 61 */     return new TransformedCollection(coll, transformer);
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected TransformedCollection(Collection coll, Transformer transformer)
/*  21:    */   {
/*  22: 76 */     super(coll);
/*  23: 77 */     if (transformer == null) {
/*  24: 78 */       throw new IllegalArgumentException("Transformer must not be null");
/*  25:    */     }
/*  26: 80 */     this.transformer = transformer;
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected Object transform(Object object)
/*  30:    */   {
/*  31: 92 */     return this.transformer.transform(object);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected Collection transform(Collection coll)
/*  35:    */   {
/*  36:104 */     List list = new ArrayList(coll.size());
/*  37:105 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/*  38:106 */       list.add(transform(it.next()));
/*  39:    */     }
/*  40:108 */     return list;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean add(Object object)
/*  44:    */   {
/*  45:113 */     object = transform(object);
/*  46:114 */     return getCollection().add(object);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean addAll(Collection coll)
/*  50:    */   {
/*  51:118 */     coll = transform(coll);
/*  52:119 */     return getCollection().addAll(coll);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.TransformedCollection
 * JD-Core Version:    0.7.0.1
 */