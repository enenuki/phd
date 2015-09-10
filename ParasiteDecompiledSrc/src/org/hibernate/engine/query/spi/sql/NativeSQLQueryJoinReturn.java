/*   1:    */ package org.hibernate.engine.query.spi.sql;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ 
/*   6:    */ public class NativeSQLQueryJoinReturn
/*   7:    */   extends NativeSQLQueryNonScalarReturn
/*   8:    */ {
/*   9:    */   private final String ownerAlias;
/*  10:    */   private final String ownerProperty;
/*  11:    */   private final int hashCode;
/*  12:    */   
/*  13:    */   public NativeSQLQueryJoinReturn(String alias, String ownerAlias, String ownerProperty, Map propertyResults, LockMode lockMode)
/*  14:    */   {
/*  15: 55 */     super(alias, propertyResults, lockMode);
/*  16: 56 */     this.ownerAlias = ownerAlias;
/*  17: 57 */     this.ownerProperty = ownerProperty;
/*  18: 58 */     this.hashCode = determineHashCode();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getOwnerAlias()
/*  22:    */   {
/*  23: 67 */     return this.ownerAlias;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getOwnerProperty()
/*  27:    */   {
/*  28: 77 */     return this.ownerProperty;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean equals(Object o)
/*  32:    */   {
/*  33: 81 */     if (this == o) {
/*  34: 82 */       return true;
/*  35:    */     }
/*  36: 84 */     if ((o == null) || (getClass() != o.getClass())) {
/*  37: 85 */       return false;
/*  38:    */     }
/*  39: 87 */     if (!super.equals(o)) {
/*  40: 88 */       return false;
/*  41:    */     }
/*  42: 91 */     NativeSQLQueryJoinReturn that = (NativeSQLQueryJoinReturn)o;
/*  43: 93 */     if (this.ownerAlias != null ? !this.ownerAlias.equals(that.ownerAlias) : that.ownerAlias != null) {
/*  44: 94 */       return false;
/*  45:    */     }
/*  46: 96 */     if (this.ownerProperty != null ? !this.ownerProperty.equals(that.ownerProperty) : that.ownerProperty != null) {
/*  47: 97 */       return false;
/*  48:    */     }
/*  49:100 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int hashCode()
/*  53:    */   {
/*  54:104 */     return this.hashCode;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private int determineHashCode()
/*  58:    */   {
/*  59:108 */     int result = super.hashCode();
/*  60:109 */     result = 31 * result + (this.ownerAlias != null ? this.ownerAlias.hashCode() : 0);
/*  61:110 */     result = 31 * result + (this.ownerProperty != null ? this.ownerProperty.hashCode() : 0);
/*  62:111 */     return result;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQueryJoinReturn
 * JD-Core Version:    0.7.0.1
 */