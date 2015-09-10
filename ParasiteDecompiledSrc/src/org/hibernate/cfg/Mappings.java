/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ import org.hibernate.AnnotationException;
/*  10:    */ import org.hibernate.DuplicateMappingException;
/*  11:    */ import org.hibernate.MappingException;
/*  12:    */ import org.hibernate.annotations.AnyMetaDef;
/*  13:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  14:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  15:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  16:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  17:    */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*  18:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  19:    */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*  20:    */ import org.hibernate.mapping.AuxiliaryDatabaseObject;
/*  21:    */ import org.hibernate.mapping.Collection;
/*  22:    */ import org.hibernate.mapping.Column;
/*  23:    */ import org.hibernate.mapping.FetchProfile;
/*  24:    */ import org.hibernate.mapping.IdGenerator;
/*  25:    */ import org.hibernate.mapping.Join;
/*  26:    */ import org.hibernate.mapping.MappedSuperclass;
/*  27:    */ import org.hibernate.mapping.MetadataSource;
/*  28:    */ import org.hibernate.mapping.PersistentClass;
/*  29:    */ import org.hibernate.mapping.Table;
/*  30:    */ import org.hibernate.mapping.TypeDef;
/*  31:    */ import org.hibernate.type.TypeResolver;
/*  32:    */ 
/*  33:    */ public abstract interface Mappings
/*  34:    */ {
/*  35:    */   public abstract TypeResolver getTypeResolver();
/*  36:    */   
/*  37:    */   public abstract NamingStrategy getNamingStrategy();
/*  38:    */   
/*  39:    */   public abstract void setNamingStrategy(NamingStrategy paramNamingStrategy);
/*  40:    */   
/*  41:    */   public abstract String getSchemaName();
/*  42:    */   
/*  43:    */   public abstract void setSchemaName(String paramString);
/*  44:    */   
/*  45:    */   public abstract String getCatalogName();
/*  46:    */   
/*  47:    */   public abstract void setCatalogName(String paramString);
/*  48:    */   
/*  49:    */   public abstract String getDefaultPackage();
/*  50:    */   
/*  51:    */   public abstract void setDefaultPackage(String paramString);
/*  52:    */   
/*  53:    */   public abstract boolean isAutoImport();
/*  54:    */   
/*  55:    */   public abstract void setAutoImport(boolean paramBoolean);
/*  56:    */   
/*  57:    */   public abstract boolean isDefaultLazy();
/*  58:    */   
/*  59:    */   public abstract void setDefaultLazy(boolean paramBoolean);
/*  60:    */   
/*  61:    */   public abstract String getDefaultCascade();
/*  62:    */   
/*  63:    */   public abstract void setDefaultCascade(String paramString);
/*  64:    */   
/*  65:    */   public abstract String getDefaultAccess();
/*  66:    */   
/*  67:    */   public abstract void setDefaultAccess(String paramString);
/*  68:    */   
/*  69:    */   public abstract Iterator<PersistentClass> iterateClasses();
/*  70:    */   
/*  71:    */   public abstract PersistentClass getClass(String paramString);
/*  72:    */   
/*  73:    */   public abstract PersistentClass locatePersistentClassByEntityName(String paramString);
/*  74:    */   
/*  75:    */   public abstract void addClass(PersistentClass paramPersistentClass)
/*  76:    */     throws DuplicateMappingException;
/*  77:    */   
/*  78:    */   public abstract void addImport(String paramString1, String paramString2)
/*  79:    */     throws DuplicateMappingException;
/*  80:    */   
/*  81:    */   public abstract Collection getCollection(String paramString);
/*  82:    */   
/*  83:    */   public abstract Iterator<Collection> iterateCollections();
/*  84:    */   
/*  85:    */   public abstract void addCollection(Collection paramCollection)
/*  86:    */     throws DuplicateMappingException;
/*  87:    */   
/*  88:    */   public abstract Table getTable(String paramString1, String paramString2, String paramString3);
/*  89:    */   
/*  90:    */   public abstract Iterator<Table> iterateTables();
/*  91:    */   
/*  92:    */   public abstract Table addTable(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean);
/*  93:    */   
/*  94:    */   public abstract Table addDenormalizedTable(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, Table paramTable)
/*  95:    */     throws DuplicateMappingException;
/*  96:    */   
/*  97:    */   public abstract NamedQueryDefinition getQuery(String paramString);
/*  98:    */   
/*  99:    */   public abstract void addQuery(String paramString, NamedQueryDefinition paramNamedQueryDefinition)
/* 100:    */     throws DuplicateMappingException;
/* 101:    */   
/* 102:    */   public abstract NamedSQLQueryDefinition getSQLQuery(String paramString);
/* 103:    */   
/* 104:    */   public abstract void addSQLQuery(String paramString, NamedSQLQueryDefinition paramNamedSQLQueryDefinition)
/* 105:    */     throws DuplicateMappingException;
/* 106:    */   
/* 107:    */   public abstract ResultSetMappingDefinition getResultSetMapping(String paramString);
/* 108:    */   
/* 109:    */   public abstract void addResultSetMapping(ResultSetMappingDefinition paramResultSetMappingDefinition)
/* 110:    */     throws DuplicateMappingException;
/* 111:    */   
/* 112:    */   public abstract TypeDef getTypeDef(String paramString);
/* 113:    */   
/* 114:    */   public abstract void addTypeDef(String paramString1, String paramString2, Properties paramProperties);
/* 115:    */   
/* 116:    */   public abstract Map getFilterDefinitions();
/* 117:    */   
/* 118:    */   public abstract FilterDefinition getFilterDefinition(String paramString);
/* 119:    */   
/* 120:    */   public abstract void addFilterDefinition(FilterDefinition paramFilterDefinition);
/* 121:    */   
/* 122:    */   public abstract FetchProfile findOrCreateFetchProfile(String paramString, MetadataSource paramMetadataSource);
/* 123:    */   
/* 124:    */   @Deprecated
/* 125:    */   public abstract Iterator<AuxiliaryDatabaseObject> iterateAuxliaryDatabaseObjects();
/* 126:    */   
/* 127:    */   public abstract Iterator<AuxiliaryDatabaseObject> iterateAuxiliaryDatabaseObjects();
/* 128:    */   
/* 129:    */   @Deprecated
/* 130:    */   public abstract ListIterator<AuxiliaryDatabaseObject> iterateAuxliaryDatabaseObjectsInReverse();
/* 131:    */   
/* 132:    */   public abstract ListIterator<AuxiliaryDatabaseObject> iterateAuxiliaryDatabaseObjectsInReverse();
/* 133:    */   
/* 134:    */   public abstract void addAuxiliaryDatabaseObject(AuxiliaryDatabaseObject paramAuxiliaryDatabaseObject);
/* 135:    */   
/* 136:    */   public abstract String getLogicalTableName(Table paramTable)
/* 137:    */     throws MappingException;
/* 138:    */   
/* 139:    */   public abstract void addTableBinding(String paramString1, String paramString2, String paramString3, String paramString4, Table paramTable)
/* 140:    */     throws DuplicateMappingException;
/* 141:    */   
/* 142:    */   public abstract void addColumnBinding(String paramString, Column paramColumn, Table paramTable)
/* 143:    */     throws DuplicateMappingException;
/* 144:    */   
/* 145:    */   public abstract String getPhysicalColumnName(String paramString, Table paramTable)
/* 146:    */     throws MappingException;
/* 147:    */   
/* 148:    */   public abstract String getLogicalColumnName(String paramString, Table paramTable)
/* 149:    */     throws MappingException;
/* 150:    */   
/* 151:    */   public abstract void addSecondPass(SecondPass paramSecondPass);
/* 152:    */   
/* 153:    */   public abstract void addSecondPass(SecondPass paramSecondPass, boolean paramBoolean);
/* 154:    */   
/* 155:    */   public abstract void addPropertyReference(String paramString1, String paramString2);
/* 156:    */   
/* 157:    */   public abstract void addUniquePropertyReference(String paramString1, String paramString2);
/* 158:    */   
/* 159:    */   public abstract void addToExtendsQueue(ExtendsQueueEntry paramExtendsQueueEntry);
/* 160:    */   
/* 161:    */   public abstract MutableIdentifierGeneratorFactory getIdentifierGeneratorFactory();
/* 162:    */   
/* 163:    */   public abstract void addMappedSuperclass(Class paramClass, MappedSuperclass paramMappedSuperclass);
/* 164:    */   
/* 165:    */   public abstract MappedSuperclass getMappedSuperclass(Class paramClass);
/* 166:    */   
/* 167:    */   public abstract ObjectNameNormalizer getObjectNameNormalizer();
/* 168:    */   
/* 169:    */   public abstract Properties getConfigurationProperties();
/* 170:    */   
/* 171:    */   public abstract void addDefaultGenerator(IdGenerator paramIdGenerator);
/* 172:    */   
/* 173:    */   public abstract IdGenerator getGenerator(String paramString);
/* 174:    */   
/* 175:    */   public abstract IdGenerator getGenerator(String paramString, Map<String, IdGenerator> paramMap);
/* 176:    */   
/* 177:    */   public abstract void addGenerator(IdGenerator paramIdGenerator);
/* 178:    */   
/* 179:    */   public abstract void addGeneratorTable(String paramString, Properties paramProperties);
/* 180:    */   
/* 181:    */   public abstract Properties getGeneratorTableProperties(String paramString, Map<String, Properties> paramMap);
/* 182:    */   
/* 183:    */   public abstract Map<String, Join> getJoins(String paramString);
/* 184:    */   
/* 185:    */   public abstract void addJoins(PersistentClass paramPersistentClass, Map<String, Join> paramMap);
/* 186:    */   
/* 187:    */   public abstract AnnotatedClassType getClassType(XClass paramXClass);
/* 188:    */   
/* 189:    */   public abstract AnnotatedClassType addClassType(XClass paramXClass);
/* 190:    */   
/* 191:    */   @Deprecated
/* 192:    */   public abstract Map<Table, List<String[]>> getTableUniqueConstraints();
/* 193:    */   
/* 194:    */   public abstract Map<Table, List<UniqueConstraintHolder>> getUniqueConstraintHoldersByTable();
/* 195:    */   
/* 196:    */   @Deprecated
/* 197:    */   public abstract void addUniqueConstraints(Table paramTable, List paramList);
/* 198:    */   
/* 199:    */   public abstract void addUniqueConstraintHolders(Table paramTable, List<UniqueConstraintHolder> paramList);
/* 200:    */   
/* 201:    */   public abstract void addMappedBy(String paramString1, String paramString2, String paramString3);
/* 202:    */   
/* 203:    */   public abstract String getFromMappedBy(String paramString1, String paramString2);
/* 204:    */   
/* 205:    */   public abstract void addPropertyReferencedAssociation(String paramString1, String paramString2, String paramString3);
/* 206:    */   
/* 207:    */   public abstract String getPropertyReferencedAssociation(String paramString1, String paramString2);
/* 208:    */   
/* 209:    */   public abstract ReflectionManager getReflectionManager();
/* 210:    */   
/* 211:    */   public abstract void addDefaultQuery(String paramString, NamedQueryDefinition paramNamedQueryDefinition);
/* 212:    */   
/* 213:    */   public abstract void addDefaultSQLQuery(String paramString, NamedSQLQueryDefinition paramNamedSQLQueryDefinition);
/* 214:    */   
/* 215:    */   public abstract void addDefaultResultSetMapping(ResultSetMappingDefinition paramResultSetMappingDefinition);
/* 216:    */   
/* 217:    */   public abstract Map getClasses();
/* 218:    */   
/* 219:    */   public abstract void addAnyMetaDef(AnyMetaDef paramAnyMetaDef)
/* 220:    */     throws AnnotationException;
/* 221:    */   
/* 222:    */   public abstract AnyMetaDef getAnyMetaDef(String paramString);
/* 223:    */   
/* 224:    */   public abstract boolean isInSecondPass();
/* 225:    */   
/* 226:    */   public abstract PropertyData getPropertyAnnotatedWithMapsId(XClass paramXClass, String paramString);
/* 227:    */   
/* 228:    */   public abstract void addPropertyAnnotatedWithMapsId(XClass paramXClass, PropertyData paramPropertyData);
/* 229:    */   
/* 230:    */   public abstract void addPropertyAnnotatedWithMapsIdSpecj(XClass paramXClass, PropertyData paramPropertyData, String paramString);
/* 231:    */   
/* 232:    */   public abstract boolean isSpecjProprietarySyntaxEnabled();
/* 233:    */   
/* 234:    */   public abstract boolean useNewGeneratorMappings();
/* 235:    */   
/* 236:    */   public abstract PropertyData getPropertyAnnotatedWithIdAndToOne(XClass paramXClass, String paramString);
/* 237:    */   
/* 238:    */   public abstract void addToOneAndIdProperty(XClass paramXClass, PropertyData paramPropertyData);
/* 239:    */   
/* 240:    */   public static final class PropertyReference
/* 241:    */     implements Serializable
/* 242:    */   {
/* 243:    */     public final String referencedClass;
/* 244:    */     public final String propertyName;
/* 245:    */     public final boolean unique;
/* 246:    */     
/* 247:    */     public PropertyReference(String referencedClass, String propertyName, boolean unique)
/* 248:    */     {
/* 249:523 */       this.referencedClass = referencedClass;
/* 250:524 */       this.propertyName = propertyName;
/* 251:525 */       this.unique = unique;
/* 252:    */     }
/* 253:    */   }
/* 254:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Mappings
 * JD-Core Version:    0.7.0.1
 */