/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.cfg.Mappings;
/*   8:    */ import org.hibernate.type.EntityType;
/*   9:    */ import org.hibernate.type.ForeignKeyDirection;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ import org.hibernate.type.TypeFactory;
/*  12:    */ import org.hibernate.type.TypeResolver;
/*  13:    */ 
/*  14:    */ public class OneToOne
/*  15:    */   extends ToOne
/*  16:    */ {
/*  17:    */   private boolean constrained;
/*  18:    */   private ForeignKeyDirection foreignKeyType;
/*  19:    */   private KeyValue identifier;
/*  20:    */   private String propertyName;
/*  21:    */   private String entityName;
/*  22:    */   
/*  23:    */   public OneToOne(Mappings mappings, Table table, PersistentClass owner)
/*  24:    */     throws MappingException
/*  25:    */   {
/*  26: 47 */     super(mappings, table);
/*  27: 48 */     this.identifier = owner.getKey();
/*  28: 49 */     this.entityName = owner.getEntityName();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getPropertyName()
/*  32:    */   {
/*  33: 53 */     return this.propertyName;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setPropertyName(String propertyName)
/*  37:    */   {
/*  38: 57 */     this.propertyName = (propertyName == null ? null : propertyName.intern());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getEntityName()
/*  42:    */   {
/*  43: 61 */     return this.entityName;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setEntityName(String propertyName)
/*  47:    */   {
/*  48: 65 */     this.entityName = (this.entityName == null ? null : this.entityName.intern());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Type getType()
/*  52:    */     throws MappingException
/*  53:    */   {
/*  54: 69 */     if (getColumnIterator().hasNext()) {
/*  55: 70 */       return getMappings().getTypeResolver().getTypeFactory().specialOneToOne(getReferencedEntityName(), this.foreignKeyType, this.referencedPropertyName, isLazy(), isUnwrapProxy(), this.entityName, this.propertyName);
/*  56:    */     }
/*  57: 81 */     return getMappings().getTypeResolver().getTypeFactory().oneToOne(getReferencedEntityName(), this.foreignKeyType, this.referencedPropertyName, isLazy(), isUnwrapProxy(), isEmbedded(), this.entityName, this.propertyName);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void createForeignKey()
/*  61:    */     throws MappingException
/*  62:    */   {
/*  63: 95 */     if ((this.constrained) && (this.referencedPropertyName == null)) {
/*  64: 97 */       createForeignKeyOfEntity(((EntityType)getType()).getAssociatedEntityName());
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List getConstraintColumns()
/*  69:    */   {
/*  70:102 */     ArrayList list = new ArrayList();
/*  71:103 */     Iterator iter = this.identifier.getColumnIterator();
/*  72:104 */     while (iter.hasNext()) {
/*  73:104 */       list.add(iter.next());
/*  74:    */     }
/*  75:105 */     return list;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isConstrained()
/*  79:    */   {
/*  80:112 */     return this.constrained;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public ForeignKeyDirection getForeignKeyType()
/*  84:    */   {
/*  85:120 */     return this.foreignKeyType;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public KeyValue getIdentifier()
/*  89:    */   {
/*  90:128 */     return this.identifier;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setConstrained(boolean constrained)
/*  94:    */   {
/*  95:136 */     this.constrained = constrained;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setForeignKeyType(ForeignKeyDirection foreignKeyType)
/*  99:    */   {
/* 100:144 */     this.foreignKeyType = foreignKeyType;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdentifier(KeyValue identifier)
/* 104:    */   {
/* 105:152 */     this.identifier = identifier;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isNullable()
/* 109:    */   {
/* 110:156 */     return !this.constrained;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object accept(ValueVisitor visitor)
/* 114:    */   {
/* 115:160 */     return visitor.accept(this);
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.OneToOne
 * JD-Core Version:    0.7.0.1
 */