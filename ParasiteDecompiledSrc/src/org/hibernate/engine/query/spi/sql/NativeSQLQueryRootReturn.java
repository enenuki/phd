/*   1:    */ package org.hibernate.engine.query.spi.sql;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ 
/*   6:    */ public class NativeSQLQueryRootReturn
/*   7:    */   extends NativeSQLQueryNonScalarReturn
/*   8:    */ {
/*   9:    */   private final String returnEntityName;
/*  10:    */   private final int hashCode;
/*  11:    */   
/*  12:    */   public NativeSQLQueryRootReturn(String alias, String entityName, LockMode lockMode)
/*  13:    */   {
/*  14: 50 */     this(alias, entityName, null, lockMode);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public NativeSQLQueryRootReturn(String alias, String entityName, Map<String, String[]> propertyResults, LockMode lockMode)
/*  18:    */   {
/*  19: 61 */     super(alias, propertyResults, lockMode);
/*  20: 62 */     this.returnEntityName = entityName;
/*  21: 63 */     this.hashCode = determineHashCode();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getReturnEntityName()
/*  25:    */   {
/*  26: 72 */     return this.returnEntityName;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean equals(Object o)
/*  30:    */   {
/*  31: 76 */     if (this == o) {
/*  32: 77 */       return true;
/*  33:    */     }
/*  34: 79 */     if ((o == null) || (getClass() != o.getClass())) {
/*  35: 80 */       return false;
/*  36:    */     }
/*  37: 82 */     if (!super.equals(o)) {
/*  38: 83 */       return false;
/*  39:    */     }
/*  40: 86 */     NativeSQLQueryRootReturn that = (NativeSQLQueryRootReturn)o;
/*  41: 88 */     if (this.returnEntityName != null ? !this.returnEntityName.equals(that.returnEntityName) : that.returnEntityName != null) {
/*  42: 89 */       return false;
/*  43:    */     }
/*  44: 92 */     return true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int hashCode()
/*  48:    */   {
/*  49: 96 */     return this.hashCode;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private int determineHashCode()
/*  53:    */   {
/*  54:100 */     int result = super.hashCode();
/*  55:101 */     result = 31 * result + (this.returnEntityName != null ? this.returnEntityName.hashCode() : 0);
/*  56:102 */     return result;
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn
 * JD-Core Version:    0.7.0.1
 */