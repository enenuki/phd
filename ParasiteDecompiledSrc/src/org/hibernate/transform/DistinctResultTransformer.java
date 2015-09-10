/*   1:    */ package org.hibernate.transform;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.jboss.logging.Logger;
/*   9:    */ 
/*  10:    */ public class DistinctResultTransformer
/*  11:    */   extends BasicTransformerAdapter
/*  12:    */ {
/*  13: 46 */   public static final DistinctResultTransformer INSTANCE = new DistinctResultTransformer();
/*  14: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DistinctResultTransformer.class.getName());
/*  15:    */   
/*  16:    */   private static final class Identity
/*  17:    */   {
/*  18:    */     final Object entity;
/*  19:    */     
/*  20:    */     private Identity(Object entity)
/*  21:    */     {
/*  22: 58 */       this.entity = entity;
/*  23:    */     }
/*  24:    */     
/*  25:    */     public boolean equals(Object other)
/*  26:    */     {
/*  27: 66 */       return (Identity.class.isInstance(other)) && (this.entity == ((Identity)other).entity);
/*  28:    */     }
/*  29:    */     
/*  30:    */     public int hashCode()
/*  31:    */     {
/*  32: 75 */       return System.identityHashCode(this.entity);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List transformList(List list)
/*  37:    */   {
/*  38: 90 */     List result = new ArrayList(list.size());
/*  39: 91 */     Set distinct = new HashSet();
/*  40: 92 */     for (int i = 0; i < list.size(); i++)
/*  41:    */     {
/*  42: 93 */       Object entity = list.get(i);
/*  43: 94 */       if (distinct.add(new Identity(entity, null))) {
/*  44: 95 */         result.add(entity);
/*  45:    */       }
/*  46:    */     }
/*  47: 98 */     LOG.debugf("Transformed: %s rows to: %s distinct results", Integer.valueOf(list.size()), Integer.valueOf(result.size()));
/*  48: 99 */     return result;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private Object readResolve()
/*  52:    */   {
/*  53:108 */     return INSTANCE;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.DistinctResultTransformer
 * JD-Core Version:    0.7.0.1
 */