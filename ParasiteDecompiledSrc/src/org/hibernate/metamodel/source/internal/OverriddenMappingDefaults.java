/*   1:    */ package org.hibernate.metamodel.source.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.cache.spi.access.AccessType;
/*   4:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*   5:    */ 
/*   6:    */ public class OverriddenMappingDefaults
/*   7:    */   implements MappingDefaults
/*   8:    */ {
/*   9:    */   private MappingDefaults overriddenValues;
/*  10:    */   private final String packageName;
/*  11:    */   private final String schemaName;
/*  12:    */   private final String catalogName;
/*  13:    */   private final String idColumnName;
/*  14:    */   private final String discriminatorColumnName;
/*  15:    */   private final String cascade;
/*  16:    */   private final String propertyAccess;
/*  17:    */   private final Boolean associationLaziness;
/*  18:    */   
/*  19:    */   public OverriddenMappingDefaults(MappingDefaults overriddenValues, String packageName, String schemaName, String catalogName, String idColumnName, String discriminatorColumnName, String cascade, String propertyAccess, Boolean associationLaziness)
/*  20:    */   {
/*  21: 56 */     if (overriddenValues == null) {
/*  22: 57 */       throw new IllegalArgumentException("Overridden values cannot be null");
/*  23:    */     }
/*  24: 59 */     this.overriddenValues = overriddenValues;
/*  25: 60 */     this.packageName = packageName;
/*  26: 61 */     this.schemaName = schemaName;
/*  27: 62 */     this.catalogName = catalogName;
/*  28: 63 */     this.idColumnName = idColumnName;
/*  29: 64 */     this.discriminatorColumnName = discriminatorColumnName;
/*  30: 65 */     this.cascade = cascade;
/*  31: 66 */     this.propertyAccess = propertyAccess;
/*  32: 67 */     this.associationLaziness = associationLaziness;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getPackageName()
/*  36:    */   {
/*  37: 72 */     return this.packageName == null ? this.overriddenValues.getPackageName() : this.packageName;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getSchemaName()
/*  41:    */   {
/*  42: 77 */     return this.schemaName == null ? this.overriddenValues.getSchemaName() : this.schemaName;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getCatalogName()
/*  46:    */   {
/*  47: 82 */     return this.catalogName == null ? this.overriddenValues.getCatalogName() : this.catalogName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getIdColumnName()
/*  51:    */   {
/*  52: 87 */     return this.idColumnName == null ? this.overriddenValues.getIdColumnName() : this.idColumnName;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getDiscriminatorColumnName()
/*  56:    */   {
/*  57: 92 */     return this.discriminatorColumnName == null ? this.overriddenValues.getDiscriminatorColumnName() : this.discriminatorColumnName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getCascadeStyle()
/*  61:    */   {
/*  62: 97 */     return this.cascade == null ? this.overriddenValues.getCascadeStyle() : this.cascade;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getPropertyAccessorName()
/*  66:    */   {
/*  67:102 */     return this.propertyAccess == null ? this.overriddenValues.getPropertyAccessorName() : this.propertyAccess;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean areAssociationsLazy()
/*  71:    */   {
/*  72:107 */     return this.associationLaziness == null ? this.overriddenValues.areAssociationsLazy() : this.associationLaziness.booleanValue();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public AccessType getCacheAccessType()
/*  76:    */   {
/*  77:112 */     return this.overriddenValues.getCacheAccessType();
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.OverriddenMappingDefaults
 * JD-Core Version:    0.7.0.1
 */