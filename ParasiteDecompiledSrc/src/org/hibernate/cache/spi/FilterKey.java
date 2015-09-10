/*  1:   */ package org.hibernate.cache.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Map.Entry;
/*  8:   */ import java.util.Set;
/*  9:   */ import org.hibernate.Filter;
/* 10:   */ import org.hibernate.engine.spi.FilterDefinition;
/* 11:   */ import org.hibernate.engine.spi.TypedValue;
/* 12:   */ import org.hibernate.internal.FilterImpl;
/* 13:   */ import org.hibernate.type.Type;
/* 14:   */ 
/* 15:   */ public final class FilterKey
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   private String filterName;
/* 19:44 */   private Map<String, TypedValue> filterParameters = new HashMap();
/* 20:   */   
/* 21:   */   public FilterKey(String name, Map<String, ?> params, Map<String, Type> types)
/* 22:   */   {
/* 23:47 */     this.filterName = name;
/* 24:48 */     for (Map.Entry<String, ?> paramEntry : params.entrySet())
/* 25:   */     {
/* 26:49 */       Type type = (Type)types.get(paramEntry.getKey());
/* 27:50 */       this.filterParameters.put(paramEntry.getKey(), new TypedValue(type, paramEntry.getValue()));
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int hashCode()
/* 32:   */   {
/* 33:55 */     int result = 13;
/* 34:56 */     result = 37 * result + this.filterName.hashCode();
/* 35:57 */     result = 37 * result + this.filterParameters.hashCode();
/* 36:58 */     return result;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean equals(Object other)
/* 40:   */   {
/* 41:62 */     if (!(other instanceof FilterKey)) {
/* 42:62 */       return false;
/* 43:   */     }
/* 44:63 */     FilterKey that = (FilterKey)other;
/* 45:64 */     if (!that.filterName.equals(this.filterName)) {
/* 46:64 */       return false;
/* 47:   */     }
/* 48:65 */     if (!that.filterParameters.equals(this.filterParameters)) {
/* 49:65 */       return false;
/* 50:   */     }
/* 51:66 */     return true;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:70 */     return "FilterKey[" + this.filterName + this.filterParameters + ']';
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static Set<FilterKey> createFilterKeys(Map<String, Filter> enabledFilters)
/* 60:   */   {
/* 61:74 */     if (enabledFilters.size() == 0) {
/* 62:75 */       return null;
/* 63:   */     }
/* 64:77 */     Set<FilterKey> result = new HashSet();
/* 65:78 */     for (Filter filter : enabledFilters.values())
/* 66:   */     {
/* 67:79 */       FilterKey key = new FilterKey(filter.getName(), ((FilterImpl)filter).getParameters(), filter.getFilterDefinition().getParameterTypes());
/* 68:   */       
/* 69:   */ 
/* 70:   */ 
/* 71:   */ 
/* 72:84 */       result.add(key);
/* 73:   */     }
/* 74:86 */     return result;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.FilterKey
 * JD-Core Version:    0.7.0.1
 */