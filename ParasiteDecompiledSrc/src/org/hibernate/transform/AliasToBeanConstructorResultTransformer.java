/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Constructor;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.QueryException;
/*  6:   */ 
/*  7:   */ public class AliasToBeanConstructorResultTransformer
/*  8:   */   implements ResultTransformer
/*  9:   */ {
/* 10:   */   private final Constructor constructor;
/* 11:   */   
/* 12:   */   public AliasToBeanConstructorResultTransformer(Constructor constructor)
/* 13:   */   {
/* 14:46 */     this.constructor = constructor;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 18:   */   {
/* 19:   */     try
/* 20:   */     {
/* 21:54 */       return this.constructor.newInstance(tuple);
/* 22:   */     }
/* 23:   */     catch (Exception e)
/* 24:   */     {
/* 25:57 */       throw new QueryException("could not instantiate class [" + this.constructor.getDeclaringClass().getName() + "] from tuple", e);
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List transformList(List collection)
/* 30:   */   {
/* 31:68 */     return collection;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int hashCode()
/* 35:   */   {
/* 36:77 */     return this.constructor.hashCode();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean equals(Object other)
/* 40:   */   {
/* 41:88 */     return ((other instanceof AliasToBeanConstructorResultTransformer)) && (this.constructor.equals(((AliasToBeanConstructorResultTransformer)other).constructor));
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.AliasToBeanConstructorResultTransformer
 * JD-Core Version:    0.7.0.1
 */