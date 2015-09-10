/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class PredicatedCollection
/*   8:    */   extends AbstractSerializableCollectionDecorator
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -5259182142076705162L;
/*  11:    */   protected final Predicate predicate;
/*  12:    */   
/*  13:    */   public static Collection decorate(Collection coll, Predicate predicate)
/*  14:    */   {
/*  15: 64 */     return new PredicatedCollection(coll, predicate);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected PredicatedCollection(Collection coll, Predicate predicate)
/*  19:    */   {
/*  20: 80 */     super(coll);
/*  21: 81 */     if (predicate == null) {
/*  22: 82 */       throw new IllegalArgumentException("Predicate must not be null");
/*  23:    */     }
/*  24: 84 */     this.predicate = predicate;
/*  25: 85 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/*  26: 86 */       validate(it.next());
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void validate(Object object)
/*  31:    */   {
/*  32:100 */     if (!this.predicate.evaluate(object)) {
/*  33:101 */       throw new IllegalArgumentException("Cannot add Object '" + object + "' - Predicate rejected it");
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean add(Object object)
/*  38:    */   {
/*  39:115 */     validate(object);
/*  40:116 */     return getCollection().add(object);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean addAll(Collection coll)
/*  44:    */   {
/*  45:129 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/*  46:130 */       validate(it.next());
/*  47:    */     }
/*  48:132 */     return getCollection().addAll(coll);
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.PredicatedCollection
 * JD-Core Version:    0.7.0.1
 */