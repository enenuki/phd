/*   1:    */ package org.hibernate.tuple;
/*   2:    */ 
/*   3:    */ import org.hibernate.FetchMode;
/*   4:    */ import org.hibernate.engine.spi.CascadeStyle;
/*   5:    */ import org.hibernate.type.Type;
/*   6:    */ 
/*   7:    */ public class StandardProperty
/*   8:    */   extends Property
/*   9:    */ {
/*  10:    */   private final boolean lazy;
/*  11:    */   private final boolean insertable;
/*  12:    */   private final boolean updateable;
/*  13:    */   private final boolean insertGenerated;
/*  14:    */   private final boolean updateGenerated;
/*  15:    */   private final boolean nullable;
/*  16:    */   private final boolean dirtyCheckable;
/*  17:    */   private final boolean versionable;
/*  18:    */   private final CascadeStyle cascadeStyle;
/*  19:    */   private final FetchMode fetchMode;
/*  20:    */   
/*  21:    */   public StandardProperty(String name, String node, Type type, boolean lazy, boolean insertable, boolean updateable, boolean insertGenerated, boolean updateGenerated, boolean nullable, boolean checkable, boolean versionable, CascadeStyle cascadeStyle, FetchMode fetchMode)
/*  22:    */   {
/*  23: 81 */     super(name, node, type);
/*  24: 82 */     this.lazy = lazy;
/*  25: 83 */     this.insertable = insertable;
/*  26: 84 */     this.updateable = updateable;
/*  27: 85 */     this.insertGenerated = insertGenerated;
/*  28: 86 */     this.updateGenerated = updateGenerated;
/*  29: 87 */     this.nullable = nullable;
/*  30: 88 */     this.dirtyCheckable = checkable;
/*  31: 89 */     this.versionable = versionable;
/*  32: 90 */     this.cascadeStyle = cascadeStyle;
/*  33: 91 */     this.fetchMode = fetchMode;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isLazy()
/*  37:    */   {
/*  38: 95 */     return this.lazy;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isInsertable()
/*  42:    */   {
/*  43: 99 */     return this.insertable;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isUpdateable()
/*  47:    */   {
/*  48:103 */     return this.updateable;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isInsertGenerated()
/*  52:    */   {
/*  53:107 */     return this.insertGenerated;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isUpdateGenerated()
/*  57:    */   {
/*  58:111 */     return this.updateGenerated;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isNullable()
/*  62:    */   {
/*  63:115 */     return this.nullable;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isDirtyCheckable(boolean hasUninitializedProperties)
/*  67:    */   {
/*  68:119 */     return (isDirtyCheckable()) && ((!hasUninitializedProperties) || (!isLazy()));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isDirtyCheckable()
/*  72:    */   {
/*  73:123 */     return this.dirtyCheckable;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isVersionable()
/*  77:    */   {
/*  78:127 */     return this.versionable;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public CascadeStyle getCascadeStyle()
/*  82:    */   {
/*  83:131 */     return this.cascadeStyle;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public FetchMode getFetchMode()
/*  87:    */   {
/*  88:135 */     return this.fetchMode;
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.StandardProperty
 * JD-Core Version:    0.7.0.1
 */