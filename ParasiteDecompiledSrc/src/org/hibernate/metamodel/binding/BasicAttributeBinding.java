/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.id.IdentifierGenerator;
/*   6:    */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*   7:    */ import org.hibernate.mapping.PropertyGeneration;
/*   8:    */ import org.hibernate.metamodel.domain.Entity;
/*   9:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  10:    */ import org.hibernate.metamodel.relational.Column;
/*  11:    */ import org.hibernate.metamodel.relational.Identifier;
/*  12:    */ import org.hibernate.metamodel.relational.Schema;
/*  13:    */ import org.hibernate.metamodel.relational.Schema.Name;
/*  14:    */ import org.hibernate.metamodel.relational.SimpleValue;
/*  15:    */ import org.hibernate.metamodel.relational.TableSpecification;
/*  16:    */ import org.hibernate.metamodel.relational.Value;
/*  17:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  18:    */ 
/*  19:    */ public class BasicAttributeBinding
/*  20:    */   extends AbstractSingularAttributeBinding
/*  21:    */   implements KeyValueBinding
/*  22:    */ {
/*  23:    */   private String unsavedValue;
/*  24:    */   private PropertyGeneration generation;
/*  25:    */   private boolean includedInOptimisticLocking;
/*  26:    */   private boolean forceNonNullable;
/*  27:    */   private boolean forceUnique;
/*  28:    */   private boolean keyCascadeDeleteEnabled;
/*  29:    */   private MetaAttributeContext metaAttributeContext;
/*  30:    */   
/*  31:    */   BasicAttributeBinding(AttributeBindingContainer container, SingularAttribute attribute, boolean forceNonNullable, boolean forceUnique)
/*  32:    */   {
/*  33: 63 */     super(container, attribute);
/*  34: 64 */     this.forceNonNullable = forceNonNullable;
/*  35: 65 */     this.forceUnique = forceUnique;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isAssociation()
/*  39:    */   {
/*  40: 70 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getUnsavedValue()
/*  44:    */   {
/*  45: 75 */     return this.unsavedValue;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setUnsavedValue(String unsavedValue)
/*  49:    */   {
/*  50: 79 */     this.unsavedValue = unsavedValue;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public PropertyGeneration getGeneration()
/*  54:    */   {
/*  55: 84 */     return this.generation;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setGeneration(PropertyGeneration generation)
/*  59:    */   {
/*  60: 88 */     this.generation = generation;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isIncludedInOptimisticLocking()
/*  64:    */   {
/*  65: 92 */     return this.includedInOptimisticLocking;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIncludedInOptimisticLocking(boolean includedInOptimisticLocking)
/*  69:    */   {
/*  70: 96 */     this.includedInOptimisticLocking = includedInOptimisticLocking;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isKeyCascadeDeleteEnabled()
/*  74:    */   {
/*  75:101 */     return this.keyCascadeDeleteEnabled;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setKeyCascadeDeleteEnabled(boolean keyCascadeDeleteEnabled)
/*  79:    */   {
/*  80:105 */     this.keyCascadeDeleteEnabled = keyCascadeDeleteEnabled;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean forceNonNullable()
/*  84:    */   {
/*  85:109 */     return this.forceNonNullable;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean forceUnique()
/*  89:    */   {
/*  90:113 */     return this.forceUnique;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public MetaAttributeContext getMetaAttributeContext()
/*  94:    */   {
/*  95:117 */     return this.metaAttributeContext;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setMetaAttributeContext(MetaAttributeContext metaAttributeContext)
/*  99:    */   {
/* 100:121 */     this.metaAttributeContext = metaAttributeContext;
/* 101:    */   }
/* 102:    */   
/* 103:    */   IdentifierGenerator createIdentifierGenerator(IdGenerator idGenerator, IdentifierGeneratorFactory identifierGeneratorFactory, Properties properties)
/* 104:    */   {
/* 105:128 */     Properties params = new Properties();
/* 106:129 */     params.putAll(properties);
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:134 */     Schema schema = getValue().getTable().getSchema();
/* 112:135 */     if (schema != null)
/* 113:    */     {
/* 114:136 */       if (schema.getName().getSchema() != null) {
/* 115:137 */         params.setProperty("schema", schema.getName().getSchema().getName());
/* 116:    */       }
/* 117:139 */       if (schema.getName().getCatalog() != null) {
/* 118:140 */         params.setProperty("catalog", schema.getName().getCatalog().getName());
/* 119:    */       }
/* 120:    */     }
/* 121:147 */     params.setProperty("entity_name", getContainer().seekEntityBinding().getEntity().getName());
/* 122:    */     
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:153 */     String tableName = getValue().getTable().getQualifiedName(identifierGeneratorFactory.getDialect());
/* 128:154 */     params.setProperty("target_table", tableName);
/* 129:157 */     if (getSimpleValueSpan() > 1) {
/* 130:158 */       throw new MappingException("A SimpleAttributeBinding used for an identifier has more than 1 Value: " + getAttribute().getName());
/* 131:    */     }
/* 132:162 */     SimpleValue simpleValue = (SimpleValue)getValue();
/* 133:163 */     if (!Column.class.isInstance(simpleValue)) {
/* 134:164 */       throw new MappingException("Cannot create an IdentifierGenerator because the value is not a column: " + simpleValue.toLoggableString());
/* 135:    */     }
/* 136:169 */     params.setProperty("target_column", ((Column)simpleValue).getColumnName().encloseInQuotesIfQuoted(identifierGeneratorFactory.getDialect()));
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:188 */     params.setProperty("identity_tables", tableName);
/* 156:    */     
/* 157:    */ 
/* 158:191 */     params.putAll(idGenerator.getParameters());
/* 159:    */     
/* 160:193 */     return identifierGeneratorFactory.createIdentifierGenerator(idGenerator.getStrategy(), getHibernateTypeDescriptor().getResolvedTypeMapping(), params);
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.BasicAttributeBinding
 * JD-Core Version:    0.7.0.1
 */