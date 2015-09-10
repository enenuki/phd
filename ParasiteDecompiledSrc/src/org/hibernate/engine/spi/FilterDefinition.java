/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class FilterDefinition
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private final String filterName;
/* 13:   */   private final String defaultFilterCondition;
/* 14:42 */   private final Map<String, Type> parameterTypes = new HashMap();
/* 15:   */   
/* 16:   */   public FilterDefinition(String name, String defaultCondition, Map<String, Type> parameterTypes)
/* 17:   */   {
/* 18:50 */     this.filterName = name;
/* 19:51 */     this.defaultFilterCondition = defaultCondition;
/* 20:52 */     this.parameterTypes.putAll(parameterTypes);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getFilterName()
/* 24:   */   {
/* 25:61 */     return this.filterName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Set getParameterNames()
/* 29:   */   {
/* 30:70 */     return this.parameterTypes.keySet();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Type getParameterType(String parameterName)
/* 34:   */   {
/* 35:80 */     return (Type)this.parameterTypes.get(parameterName);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getDefaultFilterCondition()
/* 39:   */   {
/* 40:84 */     return this.defaultFilterCondition;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Map<String, Type> getParameterTypes()
/* 44:   */   {
/* 45:88 */     return this.parameterTypes;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.FilterDefinition
 * JD-Core Version:    0.7.0.1
 */