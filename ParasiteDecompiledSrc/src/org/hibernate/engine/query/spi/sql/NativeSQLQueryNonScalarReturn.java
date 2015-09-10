/*   1:    */ package org.hibernate.engine.query.spi.sql;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ 
/*  10:    */ public abstract class NativeSQLQueryNonScalarReturn
/*  11:    */   implements NativeSQLQueryReturn, Serializable
/*  12:    */ {
/*  13:    */   private final String alias;
/*  14:    */   private final LockMode lockMode;
/*  15: 43 */   private final Map<String, String[]> propertyResults = new HashMap();
/*  16:    */   private final int hashCode;
/*  17:    */   
/*  18:    */   protected NativeSQLQueryNonScalarReturn(String alias, Map<String, String[]> propertyResults, LockMode lockMode)
/*  19:    */   {
/*  20: 54 */     this.alias = alias;
/*  21: 55 */     if (alias == null) {
/*  22: 56 */       throw new HibernateException("alias must be specified");
/*  23:    */     }
/*  24: 58 */     this.lockMode = lockMode;
/*  25: 59 */     if (propertyResults != null) {
/*  26: 60 */       this.propertyResults.putAll(propertyResults);
/*  27:    */     }
/*  28: 62 */     this.hashCode = determineHashCode();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getAlias()
/*  32:    */   {
/*  33: 71 */     return this.alias;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public LockMode getLockMode()
/*  37:    */   {
/*  38: 80 */     return this.lockMode;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Map<String, String[]> getPropertyResultsMap()
/*  42:    */   {
/*  43: 89 */     return Collections.unmodifiableMap(this.propertyResults);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int hashCode()
/*  47:    */   {
/*  48: 93 */     return this.hashCode;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private int determineHashCode()
/*  52:    */   {
/*  53: 97 */     int result = this.alias != null ? this.alias.hashCode() : 0;
/*  54: 98 */     result = 31 * result + getClass().getName().hashCode();
/*  55: 99 */     result = 31 * result + (this.lockMode != null ? this.lockMode.hashCode() : 0);
/*  56:100 */     result = 31 * result + (this.propertyResults != null ? this.propertyResults.hashCode() : 0);
/*  57:101 */     return result;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean equals(Object o)
/*  61:    */   {
/*  62:105 */     if (this == o) {
/*  63:106 */       return true;
/*  64:    */     }
/*  65:108 */     if ((o == null) || (getClass() != o.getClass())) {
/*  66:109 */       return false;
/*  67:    */     }
/*  68:112 */     NativeSQLQueryNonScalarReturn that = (NativeSQLQueryNonScalarReturn)o;
/*  69:114 */     if (this.alias != null ? !this.alias.equals(that.alias) : that.alias != null) {
/*  70:115 */       return false;
/*  71:    */     }
/*  72:117 */     if (this.lockMode != null ? !this.lockMode.equals(that.lockMode) : that.lockMode != null) {
/*  73:118 */       return false;
/*  74:    */     }
/*  75:120 */     if (this.propertyResults != null ? !this.propertyResults.equals(that.propertyResults) : that.propertyResults != null) {
/*  76:121 */       return false;
/*  77:    */     }
/*  78:124 */     return true;
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQueryNonScalarReturn
 * JD-Core Version:    0.7.0.1
 */