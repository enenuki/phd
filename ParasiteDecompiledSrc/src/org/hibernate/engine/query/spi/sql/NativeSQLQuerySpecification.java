/*   1:    */ package org.hibernate.engine.query.spi.sql;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   9:    */ 
/*  10:    */ public class NativeSQLQuerySpecification
/*  11:    */ {
/*  12:    */   private final String queryString;
/*  13:    */   private final NativeSQLQueryReturn[] queryReturns;
/*  14:    */   private final Set querySpaces;
/*  15:    */   private final int hashCode;
/*  16:    */   
/*  17:    */   public NativeSQLQuerySpecification(String queryString, NativeSQLQueryReturn[] queryReturns, Collection querySpaces)
/*  18:    */   {
/*  19: 53 */     this.queryString = queryString;
/*  20: 54 */     this.queryReturns = queryReturns;
/*  21: 55 */     if (querySpaces == null)
/*  22:    */     {
/*  23: 56 */       this.querySpaces = Collections.EMPTY_SET;
/*  24:    */     }
/*  25:    */     else
/*  26:    */     {
/*  27: 59 */       Set tmp = new HashSet();
/*  28: 60 */       tmp.addAll(querySpaces);
/*  29: 61 */       this.querySpaces = Collections.unmodifiableSet(tmp);
/*  30:    */     }
/*  31: 65 */     int hashCode = queryString.hashCode();
/*  32: 66 */     hashCode = 29 * hashCode + this.querySpaces.hashCode();
/*  33: 67 */     if (this.queryReturns != null) {
/*  34: 68 */       hashCode = 29 * hashCode + ArrayHelper.toList(this.queryReturns).hashCode();
/*  35:    */     }
/*  36: 70 */     this.hashCode = hashCode;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getQueryString()
/*  40:    */   {
/*  41: 74 */     return this.queryString;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public NativeSQLQueryReturn[] getQueryReturns()
/*  45:    */   {
/*  46: 78 */     return this.queryReturns;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Set getQuerySpaces()
/*  50:    */   {
/*  51: 82 */     return this.querySpaces;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean equals(Object o)
/*  55:    */   {
/*  56: 87 */     if (this == o) {
/*  57: 88 */       return true;
/*  58:    */     }
/*  59: 90 */     if ((o == null) || (getClass() != o.getClass())) {
/*  60: 91 */       return false;
/*  61:    */     }
/*  62: 94 */     NativeSQLQuerySpecification that = (NativeSQLQuerySpecification)o;
/*  63:    */     
/*  64: 96 */     return (this.querySpaces.equals(that.querySpaces)) && (this.queryString.equals(that.queryString)) && (Arrays.equals(this.queryReturns, that.queryReturns));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int hashCode()
/*  68:    */   {
/*  69:104 */     return this.hashCode;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification
 * JD-Core Version:    0.7.0.1
 */