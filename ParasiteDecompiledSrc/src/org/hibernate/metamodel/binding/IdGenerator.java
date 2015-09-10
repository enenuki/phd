/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  7:   */ 
/*  8:   */ public class IdGenerator
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final String name;
/* 12:   */   private final String strategy;
/* 13:   */   private final Map<String, String> parameters;
/* 14:   */   
/* 15:   */   public IdGenerator(String name, String strategy, Map<String, String> parameters)
/* 16:   */   {
/* 17:45 */     this.name = name;
/* 18:46 */     this.strategy = strategy;
/* 19:47 */     if (CollectionHelper.isEmpty(parameters)) {
/* 20:48 */       this.parameters = Collections.emptyMap();
/* 21:   */     } else {
/* 22:51 */       this.parameters = Collections.unmodifiableMap(parameters);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getStrategy()
/* 27:   */   {
/* 28:59 */     return this.strategy;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getName()
/* 32:   */   {
/* 33:66 */     return this.name;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Map<String, String> getParameters()
/* 37:   */   {
/* 38:73 */     return this.parameters;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.IdGenerator
 * JD-Core Version:    0.7.0.1
 */