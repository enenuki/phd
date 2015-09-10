/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ public class Datatype
/*  4:   */ {
/*  5:   */   private final int typeCode;
/*  6:   */   private final String typeName;
/*  7:   */   private final Class javaType;
/*  8:   */   private final int hashCode;
/*  9:   */   
/* 10:   */   public Datatype(int typeCode, String typeName, Class javaType)
/* 11:   */   {
/* 12:40 */     this.typeCode = typeCode;
/* 13:41 */     this.typeName = typeName;
/* 14:42 */     this.javaType = javaType;
/* 15:43 */     this.hashCode = generateHashCode();
/* 16:   */   }
/* 17:   */   
/* 18:   */   private int generateHashCode()
/* 19:   */   {
/* 20:47 */     int result = this.typeCode;
/* 21:48 */     if (this.typeName != null) {
/* 22:49 */       result = 31 * result + this.typeName.hashCode();
/* 23:   */     }
/* 24:51 */     if (this.javaType != null) {
/* 25:52 */       result = 31 * result + this.javaType.hashCode();
/* 26:   */     }
/* 27:54 */     return result;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getTypeCode()
/* 31:   */   {
/* 32:58 */     return this.typeCode;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getTypeName()
/* 36:   */   {
/* 37:62 */     return this.typeName;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Class getJavaType()
/* 41:   */   {
/* 42:66 */     return this.javaType;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean equals(Object o)
/* 46:   */   {
/* 47:71 */     if (this == o) {
/* 48:72 */       return true;
/* 49:   */     }
/* 50:74 */     if ((o == null) || (getClass() != o.getClass())) {
/* 51:75 */       return false;
/* 52:   */     }
/* 53:78 */     Datatype datatype = (Datatype)o;
/* 54:   */     
/* 55:80 */     return (this.typeCode == datatype.typeCode) && (this.javaType.equals(datatype.javaType)) && (this.typeName.equals(datatype.typeName));
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int hashCode()
/* 59:   */   {
/* 60:88 */     return this.hashCode;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String toString()
/* 64:   */   {
/* 65:93 */     return super.toString() + "[code=" + this.typeCode + ", name=" + this.typeName + ", javaClass=" + this.javaType.getName() + "]";
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Datatype
 * JD-Core Version:    0.7.0.1
 */