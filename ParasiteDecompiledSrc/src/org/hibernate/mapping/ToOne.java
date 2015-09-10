/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import org.hibernate.FetchMode;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.cfg.Mappings;
/*   6:    */ import org.hibernate.engine.spi.Mapping;
/*   7:    */ import org.hibernate.internal.util.ReflectHelper;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ 
/*  10:    */ public abstract class ToOne
/*  11:    */   extends SimpleValue
/*  12:    */   implements Fetchable
/*  13:    */ {
/*  14:    */   private FetchMode fetchMode;
/*  15:    */   protected String referencedPropertyName;
/*  16:    */   private String referencedEntityName;
/*  17:    */   private boolean embedded;
/*  18: 42 */   private boolean lazy = true;
/*  19:    */   protected boolean unwrapProxy;
/*  20:    */   
/*  21:    */   protected ToOne(Mappings mappings, Table table)
/*  22:    */   {
/*  23: 46 */     super(mappings, table);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public FetchMode getFetchMode()
/*  27:    */   {
/*  28: 50 */     return this.fetchMode;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setFetchMode(FetchMode fetchMode)
/*  32:    */   {
/*  33: 54 */     this.fetchMode = fetchMode;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public abstract void createForeignKey()
/*  37:    */     throws MappingException;
/*  38:    */   
/*  39:    */   public abstract Type getType()
/*  40:    */     throws MappingException;
/*  41:    */   
/*  42:    */   public String getReferencedPropertyName()
/*  43:    */   {
/*  44: 61 */     return this.referencedPropertyName;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setReferencedPropertyName(String name)
/*  48:    */   {
/*  49: 65 */     this.referencedPropertyName = (name == null ? null : name.intern());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getReferencedEntityName()
/*  53:    */   {
/*  54: 69 */     return this.referencedEntityName;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setReferencedEntityName(String referencedEntityName)
/*  58:    */   {
/*  59: 73 */     this.referencedEntityName = (referencedEntityName == null ? null : referencedEntityName.intern());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setTypeUsingReflection(String className, String propertyName)
/*  63:    */     throws MappingException
/*  64:    */   {
/*  65: 79 */     if (this.referencedEntityName == null) {
/*  66: 80 */       this.referencedEntityName = ReflectHelper.reflectedPropertyClass(className, propertyName).getName();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isTypeSpecified()
/*  71:    */   {
/*  72: 85 */     return this.referencedEntityName != null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object accept(ValueVisitor visitor)
/*  76:    */   {
/*  77: 89 */     return visitor.accept(this);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isEmbedded()
/*  81:    */   {
/*  82: 93 */     return this.embedded;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setEmbedded(boolean embedded)
/*  86:    */   {
/*  87: 97 */     this.embedded = embedded;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isValid(Mapping mapping)
/*  91:    */     throws MappingException
/*  92:    */   {
/*  93:101 */     if (this.referencedEntityName == null) {
/*  94:102 */       throw new MappingException("association must specify the referenced entity");
/*  95:    */     }
/*  96:104 */     return super.isValid(mapping);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isLazy()
/* 100:    */   {
/* 101:108 */     return this.lazy;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setLazy(boolean lazy)
/* 105:    */   {
/* 106:112 */     this.lazy = lazy;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isUnwrapProxy()
/* 110:    */   {
/* 111:116 */     return this.unwrapProxy;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setUnwrapProxy(boolean unwrapProxy)
/* 115:    */   {
/* 116:120 */     this.unwrapProxy = unwrapProxy;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.ToOne
 * JD-Core Version:    0.7.0.1
 */