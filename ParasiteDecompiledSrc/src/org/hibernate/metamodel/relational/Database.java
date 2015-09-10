/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.internal.util.StringHelper;
/*  13:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  14:    */ import org.hibernate.metamodel.Metadata.Options;
/*  15:    */ 
/*  16:    */ public class Database
/*  17:    */ {
/*  18:    */   private final Schema.Name implicitSchemaName;
/*  19: 49 */   private final Map<Schema.Name, Schema> schemaMap = new HashMap();
/*  20: 50 */   private final List<AuxiliaryDatabaseObject> auxiliaryDatabaseObjects = new ArrayList();
/*  21:    */   
/*  22:    */   public Database(Metadata.Options options)
/*  23:    */   {
/*  24: 53 */     String schemaName = options.getDefaultSchemaName();
/*  25: 54 */     String catalogName = options.getDefaultCatalogName();
/*  26: 55 */     if (options.isGloballyQuotedIdentifiers())
/*  27:    */     {
/*  28: 56 */       schemaName = StringHelper.quote(schemaName);
/*  29: 57 */       catalogName = StringHelper.quote(catalogName);
/*  30:    */     }
/*  31: 59 */     this.implicitSchemaName = new Schema.Name(schemaName, catalogName);
/*  32: 60 */     makeSchema(this.implicitSchemaName);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Schema getDefaultSchema()
/*  36:    */   {
/*  37: 64 */     return (Schema)this.schemaMap.get(this.implicitSchemaName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Schema locateSchema(Schema.Name name)
/*  41:    */   {
/*  42: 68 */     if ((name.getSchema() == null) && (name.getCatalog() == null)) {
/*  43: 69 */       return getDefaultSchema();
/*  44:    */     }
/*  45: 71 */     Schema schema = (Schema)this.schemaMap.get(name);
/*  46: 72 */     if (schema == null) {
/*  47: 73 */       schema = makeSchema(name);
/*  48:    */     }
/*  49: 75 */     return schema;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private Schema makeSchema(Schema.Name name)
/*  53:    */   {
/*  54: 80 */     Schema schema = new Schema(name);
/*  55: 81 */     this.schemaMap.put(name, schema);
/*  56: 82 */     return schema;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Schema getSchema(Identifier schema, Identifier catalog)
/*  60:    */   {
/*  61: 86 */     return locateSchema(new Schema.Name(schema, catalog));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Schema getSchema(String schema, String catalog)
/*  65:    */   {
/*  66: 90 */     return locateSchema(new Schema.Name(Identifier.toIdentifier(schema), Identifier.toIdentifier(catalog)));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addAuxiliaryDatabaseObject(AuxiliaryDatabaseObject auxiliaryDatabaseObject)
/*  70:    */   {
/*  71: 94 */     if (auxiliaryDatabaseObject == null) {
/*  72: 95 */       throw new IllegalArgumentException("Auxiliary database object is null.");
/*  73:    */     }
/*  74: 97 */     this.auxiliaryDatabaseObjects.add(auxiliaryDatabaseObject);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Iterable<AuxiliaryDatabaseObject> getAuxiliaryDatabaseObjects()
/*  78:    */   {
/*  79:101 */     return this.auxiliaryDatabaseObjects;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String[] generateSchemaCreationScript(Dialect dialect)
/*  83:    */   {
/*  84:105 */     Set<String> exportIdentifiers = new HashSet(50);
/*  85:106 */     List<String> script = new ArrayList(50);
/*  86:108 */     for (Schema schema : this.schemaMap.values()) {
/*  87:110 */       for (Table table : schema.getTables()) {
/*  88:111 */         addSqlCreateStrings(dialect, exportIdentifiers, script, table);
/*  89:    */       }
/*  90:    */     }
/*  91:115 */     for (Schema schema : this.schemaMap.values()) {
/*  92:116 */       for (Table table : schema.getTables())
/*  93:    */       {
/*  94:118 */         if (!dialect.supportsUniqueConstraintInCreateAlterTable()) {
/*  95:119 */           for (UniqueKey uniqueKey : table.getUniqueKeys()) {
/*  96:120 */             addSqlCreateStrings(dialect, exportIdentifiers, script, uniqueKey);
/*  97:    */           }
/*  98:    */         }
/*  99:124 */         for (Index index : table.getIndexes()) {
/* 100:125 */           addSqlCreateStrings(dialect, exportIdentifiers, script, index);
/* 101:    */         }
/* 102:128 */         if (dialect.hasAlterTable()) {
/* 103:129 */           for (ForeignKey foreignKey : table.getForeignKeys()) {
/* 104:131 */             if (Table.class.isInstance(foreignKey.getTargetTable())) {
/* 105:132 */               addSqlCreateStrings(dialect, exportIdentifiers, script, foreignKey);
/* 106:    */             }
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:142 */     for (AuxiliaryDatabaseObject auxiliaryDatabaseObject : this.auxiliaryDatabaseObjects) {
/* 112:143 */       if (auxiliaryDatabaseObject.appliesToDialect(dialect)) {
/* 113:144 */         addSqlCreateStrings(dialect, exportIdentifiers, script, auxiliaryDatabaseObject);
/* 114:    */       }
/* 115:    */     }
/* 116:148 */     return ArrayHelper.toStringArray(script);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String[] generateDropSchemaScript(Dialect dialect)
/* 120:    */   {
/* 121:152 */     Set<String> exportIdentifiers = new HashSet(50);
/* 122:153 */     List<String> script = new ArrayList(50);
/* 123:157 */     for (int i = this.auxiliaryDatabaseObjects.size() - 1; i >= 0; i--)
/* 124:    */     {
/* 125:158 */       AuxiliaryDatabaseObject object = (AuxiliaryDatabaseObject)this.auxiliaryDatabaseObjects.get(i);
/* 126:159 */       if (object.appliesToDialect(dialect)) {
/* 127:160 */         addSqlDropStrings(dialect, exportIdentifiers, script, object);
/* 128:    */       }
/* 129:    */     }
/* 130:164 */     if (dialect.dropConstraints()) {
/* 131:165 */       for (Schema schema : this.schemaMap.values()) {
/* 132:166 */         for (Table table : schema.getTables()) {
/* 133:167 */           for (ForeignKey foreignKey : table.getForeignKeys()) {
/* 134:169 */             if ((foreignKey.getTargetTable() instanceof Table)) {
/* 135:170 */               addSqlDropStrings(dialect, exportIdentifiers, script, foreignKey);
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:177 */     for (Schema schema : this.schemaMap.values()) {
/* 142:178 */       for (Table table : schema.getTables()) {
/* 143:179 */         addSqlDropStrings(dialect, exportIdentifiers, script, table);
/* 144:    */       }
/* 145:    */     }
/* 146:187 */     return ArrayHelper.toStringArray(script);
/* 147:    */   }
/* 148:    */   
/* 149:    */   private static void addSqlDropStrings(Dialect dialect, Set<String> exportIdentifiers, List<String> script, Exportable exportable)
/* 150:    */   {
/* 151:195 */     addSqlStrings(exportIdentifiers, script, exportable.getExportIdentifier(), exportable.sqlDropStrings(dialect));
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static void addSqlCreateStrings(Dialect dialect, Set<String> exportIdentifiers, List<String> script, Exportable exportable)
/* 155:    */   {
/* 156:205 */     addSqlStrings(exportIdentifiers, script, exportable.getExportIdentifier(), exportable.sqlCreateStrings(dialect));
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static void addSqlStrings(Set<String> exportIdentifiers, List<String> script, String exportIdentifier, String[] sqlStrings)
/* 160:    */   {
/* 161:215 */     if (sqlStrings == null) {
/* 162:216 */       return;
/* 163:    */     }
/* 164:218 */     if (exportIdentifiers.contains(exportIdentifier)) {
/* 165:219 */       throw new MappingException("SQL strings added more than once for: " + exportIdentifier);
/* 166:    */     }
/* 167:223 */     exportIdentifiers.add(exportIdentifier);
/* 168:224 */     script.addAll(Arrays.asList(sqlStrings));
/* 169:    */   }
/* 170:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Database
 * JD-Core Version:    0.7.0.1
 */