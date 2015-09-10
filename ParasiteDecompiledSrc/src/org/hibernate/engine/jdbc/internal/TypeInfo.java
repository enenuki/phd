/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ public class TypeInfo
/*   4:    */ {
/*   5:    */   private final String typeName;
/*   6:    */   private final int jdbcTypeCode;
/*   7:    */   private final String[] createParams;
/*   8:    */   private final boolean unsigned;
/*   9:    */   private final int precision;
/*  10:    */   private final short minimumScale;
/*  11:    */   private final short maximumScale;
/*  12:    */   private final boolean fixedPrecisionScale;
/*  13:    */   private final String literalPrefix;
/*  14:    */   private final String literalSuffix;
/*  15:    */   private final boolean caseSensitive;
/*  16:    */   private final TypeSearchability searchability;
/*  17:    */   private final TypeNullability nullability;
/*  18:    */   
/*  19:    */   public TypeInfo(String typeName, int jdbcTypeCode, String[] createParams, boolean unsigned, int precision, short minimumScale, short maximumScale, boolean fixedPrecisionScale, String literalPrefix, String literalSuffix, boolean caseSensitive, TypeSearchability searchability, TypeNullability nullability)
/*  20:    */   {
/*  21: 61 */     this.typeName = typeName;
/*  22: 62 */     this.jdbcTypeCode = jdbcTypeCode;
/*  23: 63 */     this.createParams = createParams;
/*  24: 64 */     this.unsigned = unsigned;
/*  25: 65 */     this.precision = precision;
/*  26: 66 */     this.minimumScale = minimumScale;
/*  27: 67 */     this.maximumScale = maximumScale;
/*  28: 68 */     this.fixedPrecisionScale = fixedPrecisionScale;
/*  29: 69 */     this.literalPrefix = literalPrefix;
/*  30: 70 */     this.literalSuffix = literalSuffix;
/*  31: 71 */     this.caseSensitive = caseSensitive;
/*  32: 72 */     this.searchability = searchability;
/*  33: 73 */     this.nullability = nullability;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getTypeName()
/*  37:    */   {
/*  38: 77 */     return this.typeName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getJdbcTypeCode()
/*  42:    */   {
/*  43: 81 */     return this.jdbcTypeCode;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String[] getCreateParams()
/*  47:    */   {
/*  48: 85 */     return this.createParams;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isUnsigned()
/*  52:    */   {
/*  53: 89 */     return this.unsigned;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getPrecision()
/*  57:    */   {
/*  58: 93 */     return this.precision;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public short getMinimumScale()
/*  62:    */   {
/*  63: 97 */     return this.minimumScale;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public short getMaximumScale()
/*  67:    */   {
/*  68:101 */     return this.maximumScale;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isFixedPrecisionScale()
/*  72:    */   {
/*  73:105 */     return this.fixedPrecisionScale;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getLiteralPrefix()
/*  77:    */   {
/*  78:109 */     return this.literalPrefix;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getLiteralSuffix()
/*  82:    */   {
/*  83:113 */     return this.literalSuffix;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isCaseSensitive()
/*  87:    */   {
/*  88:117 */     return this.caseSensitive;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public TypeSearchability getSearchability()
/*  92:    */   {
/*  93:121 */     return this.searchability;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TypeNullability getNullability()
/*  97:    */   {
/*  98:125 */     return this.nullability;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.TypeInfo
 * JD-Core Version:    0.7.0.1
 */