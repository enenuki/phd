/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.FetchMode;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.cfg.Mappings;
/*   7:    */ import org.hibernate.engine.spi.Mapping;
/*   8:    */ import org.hibernate.type.EntityType;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ import org.hibernate.type.TypeFactory;
/*  11:    */ import org.hibernate.type.TypeResolver;
/*  12:    */ 
/*  13:    */ public class OneToMany
/*  14:    */   implements Value
/*  15:    */ {
/*  16:    */   private final Mappings mappings;
/*  17:    */   private final Table referencingTable;
/*  18:    */   private String referencedEntityName;
/*  19:    */   private PersistentClass associatedClass;
/*  20:    */   private boolean embedded;
/*  21:    */   private boolean ignoreNotFound;
/*  22:    */   
/*  23:    */   private EntityType getEntityType()
/*  24:    */   {
/*  25: 49 */     return this.mappings.getTypeResolver().getTypeFactory().manyToOne(getReferencedEntityName(), null, false, false, isEmbedded(), isIgnoreNotFound(), false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public OneToMany(Mappings mappings, PersistentClass owner)
/*  29:    */     throws MappingException
/*  30:    */   {
/*  31: 61 */     this.mappings = mappings;
/*  32: 62 */     this.referencingTable = (owner == null ? null : owner.getTable());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public PersistentClass getAssociatedClass()
/*  36:    */   {
/*  37: 66 */     return this.associatedClass;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setAssociatedClass(PersistentClass associatedClass)
/*  41:    */   {
/*  42: 73 */     this.associatedClass = associatedClass;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void createForeignKey() {}
/*  46:    */   
/*  47:    */   public Iterator getColumnIterator()
/*  48:    */   {
/*  49: 81 */     return this.associatedClass.getKey().getColumnIterator();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getColumnSpan()
/*  53:    */   {
/*  54: 85 */     return this.associatedClass.getKey().getColumnSpan();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public FetchMode getFetchMode()
/*  58:    */   {
/*  59: 89 */     return FetchMode.JOIN;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Table getTable()
/*  63:    */   {
/*  64: 96 */     return this.referencingTable;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Type getType()
/*  68:    */   {
/*  69:100 */     return getEntityType();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isNullable()
/*  73:    */   {
/*  74:104 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isSimpleValue()
/*  78:    */   {
/*  79:108 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isAlternateUniqueKey()
/*  83:    */   {
/*  84:112 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean hasFormula()
/*  88:    */   {
/*  89:116 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isValid(Mapping mapping)
/*  93:    */     throws MappingException
/*  94:    */   {
/*  95:120 */     if (this.referencedEntityName == null) {
/*  96:121 */       throw new MappingException("one to many association must specify the referenced entity");
/*  97:    */     }
/*  98:123 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getReferencedEntityName()
/* 102:    */   {
/* 103:127 */     return this.referencedEntityName;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setReferencedEntityName(String referencedEntityName)
/* 107:    */   {
/* 108:134 */     this.referencedEntityName = (referencedEntityName == null ? null : referencedEntityName.intern());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setTypeUsingReflection(String className, String propertyName) {}
/* 112:    */   
/* 113:    */   public Object accept(ValueVisitor visitor)
/* 114:    */   {
/* 115:140 */     return visitor.accept(this);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean[] getColumnInsertability()
/* 119:    */   {
/* 120:146 */     throw new UnsupportedOperationException();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean[] getColumnUpdateability()
/* 124:    */   {
/* 125:151 */     throw new UnsupportedOperationException();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isEmbedded()
/* 129:    */   {
/* 130:155 */     return this.embedded;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setEmbedded(boolean embedded)
/* 134:    */   {
/* 135:159 */     this.embedded = embedded;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isIgnoreNotFound()
/* 139:    */   {
/* 140:163 */     return this.ignoreNotFound;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIgnoreNotFound(boolean ignoreNotFound)
/* 144:    */   {
/* 145:167 */     this.ignoreNotFound = ignoreNotFound;
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.OneToMany
 * JD-Core Version:    0.7.0.1
 */