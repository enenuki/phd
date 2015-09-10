/*  1:   */ package org.hibernate.internal;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Set;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  9:   */ import org.hibernate.internal.util.StringHelper;
/* 10:   */ import org.hibernate.sql.Template;
/* 11:   */ 
/* 12:   */ public class FilterHelper
/* 13:   */ {
/* 14:   */   private final String[] filterNames;
/* 15:   */   private final String[] filterConditions;
/* 16:   */   
/* 17:   */   public FilterHelper(Map filters, Dialect dialect, SQLFunctionRegistry functionRegistry)
/* 18:   */   {
/* 19:55 */     int filterCount = filters.size();
/* 20:56 */     this.filterNames = new String[filterCount];
/* 21:57 */     this.filterConditions = new String[filterCount];
/* 22:58 */     Iterator iter = filters.entrySet().iterator();
/* 23:59 */     filterCount = 0;
/* 24:60 */     while (iter.hasNext())
/* 25:   */     {
/* 26:61 */       Map.Entry entry = (Map.Entry)iter.next();
/* 27:62 */       this.filterNames[filterCount] = ((String)entry.getKey());
/* 28:63 */       this.filterConditions[filterCount] = Template.renderWhereStringTemplate((String)entry.getValue(), "$FILTER_PLACEHOLDER$", dialect, functionRegistry);
/* 29:   */       
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:69 */       this.filterConditions[filterCount] = StringHelper.replace(this.filterConditions[filterCount], ":", ":" + this.filterNames[filterCount] + ".");
/* 35:   */       
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:74 */       filterCount++;
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isAffectedBy(Map enabledFilters)
/* 44:   */   {
/* 45:79 */     int i = 0;
/* 46:79 */     for (int max = this.filterNames.length; i < max; i++) {
/* 47:80 */       if (enabledFilters.containsKey(this.filterNames[i])) {
/* 48:81 */         return true;
/* 49:   */       }
/* 50:   */     }
/* 51:84 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String render(String alias, Map enabledFilters)
/* 55:   */   {
/* 56:88 */     StringBuilder buffer = new StringBuilder();
/* 57:89 */     render(buffer, alias, enabledFilters);
/* 58:90 */     return buffer.toString();
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void render(StringBuilder buffer, String alias, Map enabledFilters)
/* 62:   */   {
/* 63:94 */     if ((this.filterNames != null) && (this.filterNames.length > 0))
/* 64:   */     {
/* 65:95 */       int i = 0;
/* 66:95 */       for (int max = this.filterNames.length; i < max; i++) {
/* 67:96 */         if (enabledFilters.containsKey(this.filterNames[i]))
/* 68:   */         {
/* 69:97 */           String condition = this.filterConditions[i];
/* 70:98 */           if (StringHelper.isNotEmpty(condition)) {
/* 71:99 */             buffer.append(" and ").append(StringHelper.replace(condition, "$FILTER_PLACEHOLDER$", alias));
/* 72:   */           }
/* 73:   */         }
/* 74:   */       }
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.FilterHelper
 * JD-Core Version:    0.7.0.1
 */