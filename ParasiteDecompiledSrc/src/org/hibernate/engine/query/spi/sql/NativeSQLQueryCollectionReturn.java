/*   1:    */ package org.hibernate.engine.query.spi.sql;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ 
/*   6:    */ public class NativeSQLQueryCollectionReturn
/*   7:    */   extends NativeSQLQueryNonScalarReturn
/*   8:    */ {
/*   9:    */   private final String ownerEntityName;
/*  10:    */   private final String ownerProperty;
/*  11:    */   private final int hashCode;
/*  12:    */   
/*  13:    */   public NativeSQLQueryCollectionReturn(String alias, String ownerEntityName, String ownerProperty, Map propertyResults, LockMode lockMode)
/*  14:    */   {
/*  15: 61 */     super(alias, propertyResults, lockMode);
/*  16: 62 */     this.ownerEntityName = ownerEntityName;
/*  17: 63 */     this.ownerProperty = ownerProperty;
/*  18: 64 */     this.hashCode = determineHashCode();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getOwnerEntityName()
/*  22:    */   {
/*  23: 73 */     return this.ownerEntityName;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getOwnerProperty()
/*  27:    */   {
/*  28: 82 */     return this.ownerProperty;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean equals(Object o)
/*  32:    */   {
/*  33: 86 */     if (this == o) {
/*  34: 87 */       return true;
/*  35:    */     }
/*  36: 89 */     if ((o == null) || (getClass() != o.getClass())) {
/*  37: 90 */       return false;
/*  38:    */     }
/*  39: 92 */     if (!super.equals(o)) {
/*  40: 93 */       return false;
/*  41:    */     }
/*  42: 96 */     NativeSQLQueryCollectionReturn that = (NativeSQLQueryCollectionReturn)o;
/*  43: 98 */     if (this.ownerEntityName != null ? !this.ownerEntityName.equals(that.ownerEntityName) : that.ownerEntityName != null) {
/*  44: 99 */       return false;
/*  45:    */     }
/*  46:101 */     if (this.ownerProperty != null ? !this.ownerProperty.equals(that.ownerProperty) : that.ownerProperty != null) {
/*  47:102 */       return false;
/*  48:    */     }
/*  49:105 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int hashCode()
/*  53:    */   {
/*  54:109 */     return this.hashCode;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private int determineHashCode()
/*  58:    */   {
/*  59:113 */     int result = super.hashCode();
/*  60:114 */     result = 31 * result + (this.ownerEntityName != null ? this.ownerEntityName.hashCode() : 0);
/*  61:115 */     result = 31 * result + (this.ownerProperty != null ? this.ownerProperty.hashCode() : 0);
/*  62:116 */     return result;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn
 * JD-Core Version:    0.7.0.1
 */