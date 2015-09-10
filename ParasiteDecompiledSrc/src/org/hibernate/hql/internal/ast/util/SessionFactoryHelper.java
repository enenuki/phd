/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.QueryException;
/*   9:    */ import org.hibernate.cfg.Settings;
/*  10:    */ import org.hibernate.dialect.function.SQLFunction;
/*  11:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  12:    */ import org.hibernate.engine.internal.JoinSequence;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.hql.internal.NameGenerator;
/*  15:    */ import org.hibernate.hql.internal.ast.DetailedSemanticException;
/*  16:    */ import org.hibernate.hql.internal.ast.QuerySyntaxException;
/*  17:    */ import org.hibernate.hql.internal.ast.tree.SqlNode;
/*  18:    */ import org.hibernate.persister.collection.CollectionPropertyMapping;
/*  19:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  20:    */ import org.hibernate.persister.entity.EntityPersister;
/*  21:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  22:    */ import org.hibernate.persister.entity.Queryable;
/*  23:    */ import org.hibernate.sql.JoinType;
/*  24:    */ import org.hibernate.type.AssociationType;
/*  25:    */ import org.hibernate.type.CollectionType;
/*  26:    */ import org.hibernate.type.EntityType;
/*  27:    */ import org.hibernate.type.Type;
/*  28:    */ import org.hibernate.type.TypeResolver;
/*  29:    */ 
/*  30:    */ public class SessionFactoryHelper
/*  31:    */ {
/*  32:    */   private SessionFactoryImplementor sfi;
/*  33:    */   private Map collectionPropertyMappingByRole;
/*  34:    */   
/*  35:    */   public SessionFactoryHelper(SessionFactoryImplementor sfi)
/*  36:    */   {
/*  37: 70 */     this.sfi = sfi;
/*  38: 71 */     this.collectionPropertyMappingByRole = new HashMap();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public SessionFactoryImplementor getFactory()
/*  42:    */   {
/*  43: 80 */     return this.sfi;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean hasPhysicalDiscriminatorColumn(Queryable persister)
/*  47:    */   {
/*  48: 91 */     if (persister.getDiscriminatorType() != null)
/*  49:    */     {
/*  50: 92 */       String discrimColumnName = persister.getDiscriminatorColumnName();
/*  51: 95 */       if ((discrimColumnName != null) && (!"clazz_".equals(discrimColumnName))) {
/*  52: 96 */         return true;
/*  53:    */       }
/*  54:    */     }
/*  55: 99 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getImportedClassName(String className)
/*  59:    */   {
/*  60:109 */     return this.sfi.getImportedClassName(className);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Queryable findQueryableUsingImports(String className)
/*  64:    */   {
/*  65:119 */     return findQueryableUsingImports(this.sfi, className);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Queryable findQueryableUsingImports(SessionFactoryImplementor sfi, String className)
/*  69:    */   {
/*  70:131 */     String importedClassName = sfi.getImportedClassName(className);
/*  71:132 */     if (importedClassName == null) {
/*  72:133 */       return null;
/*  73:    */     }
/*  74:    */     try
/*  75:    */     {
/*  76:136 */       return (Queryable)sfi.getEntityPersister(importedClassName);
/*  77:    */     }
/*  78:    */     catch (MappingException me) {}
/*  79:139 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private EntityPersister findEntityPersisterByName(String name)
/*  83:    */     throws MappingException
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:153 */       return this.sfi.getEntityPersister(name);
/*  88:    */     }
/*  89:    */     catch (MappingException ignore)
/*  90:    */     {
/*  91:160 */       String importedClassName = this.sfi.getImportedClassName(name);
/*  92:161 */       if (importedClassName == null) {
/*  93:162 */         return null;
/*  94:    */       }
/*  95:164 */       return this.sfi.getEntityPersister(importedClassName);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public EntityPersister requireClassPersister(String name)
/* 100:    */     throws SemanticException
/* 101:    */   {
/* 102:    */     EntityPersister cp;
/* 103:    */     try
/* 104:    */     {
/* 105:178 */       cp = findEntityPersisterByName(name);
/* 106:179 */       if (cp == null) {
/* 107:180 */         throw new QuerySyntaxException(name + " is not mapped");
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (MappingException e)
/* 111:    */     {
/* 112:184 */       throw new DetailedSemanticException(e.getMessage(), e);
/* 113:    */     }
/* 114:186 */     return cp;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public QueryableCollection getCollectionPersister(String role)
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:197 */       return (QueryableCollection)this.sfi.getCollectionPersister(role);
/* 122:    */     }
/* 123:    */     catch (ClassCastException cce)
/* 124:    */     {
/* 125:200 */       throw new QueryException("collection is not queryable: " + role);
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:203 */       throw new QueryException("collection not found: " + role);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public QueryableCollection requireQueryableCollection(String role)
/* 134:    */     throws QueryException
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:217 */       QueryableCollection queryableCollection = (QueryableCollection)this.sfi.getCollectionPersister(role);
/* 139:218 */       if (queryableCollection != null) {
/* 140:219 */         this.collectionPropertyMappingByRole.put(role, new CollectionPropertyMapping(queryableCollection));
/* 141:    */       }
/* 142:221 */       return queryableCollection;
/* 143:    */     }
/* 144:    */     catch (ClassCastException cce)
/* 145:    */     {
/* 146:224 */       throw new QueryException("collection role is not queryable: " + role);
/* 147:    */     }
/* 148:    */     catch (Exception e)
/* 149:    */     {
/* 150:227 */       throw new QueryException("collection role not found: " + role);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public PropertyMapping getCollectionPropertyMapping(String role)
/* 155:    */   {
/* 156:238 */     return (PropertyMapping)this.collectionPropertyMappingByRole.get(role);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String[] getCollectionElementColumns(String role, String roleAlias)
/* 160:    */   {
/* 161:250 */     return getCollectionPropertyMapping(role).toColumns(roleAlias, "elements");
/* 162:    */   }
/* 163:    */   
/* 164:    */   public JoinSequence createJoinSequence()
/* 165:    */   {
/* 166:259 */     return new JoinSequence(this.sfi);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public JoinSequence createJoinSequence(boolean implicit, AssociationType associationType, String tableAlias, JoinType joinType, String[] columns)
/* 170:    */   {
/* 171:273 */     JoinSequence joinSequence = createJoinSequence();
/* 172:274 */     joinSequence.setUseThetaStyle(implicit);
/* 173:275 */     joinSequence.addJoin(associationType, tableAlias, joinType, columns);
/* 174:276 */     return joinSequence;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public JoinSequence createCollectionJoinSequence(QueryableCollection collPersister, String collectionName)
/* 178:    */   {
/* 179:287 */     JoinSequence joinSequence = createJoinSequence();
/* 180:288 */     joinSequence.setRoot(collPersister, collectionName);
/* 181:289 */     joinSequence.setUseThetaStyle(true);
/* 182:    */     
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:294 */     return joinSequence;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getIdentifierOrUniqueKeyPropertyName(EntityType entityType)
/* 190:    */   {
/* 191:    */     try
/* 192:    */     {
/* 193:307 */       return entityType.getIdentifierOrUniqueKeyPropertyName(this.sfi);
/* 194:    */     }
/* 195:    */     catch (MappingException me)
/* 196:    */     {
/* 197:310 */       throw new QueryException(me);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getColumnSpan(Type type)
/* 202:    */   {
/* 203:321 */     return type.getColumnSpan(this.sfi);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getAssociatedEntityName(CollectionType collectionType)
/* 207:    */   {
/* 208:332 */     return collectionType.getAssociatedEntityName(this.sfi);
/* 209:    */   }
/* 210:    */   
/* 211:    */   private Type getElementType(CollectionType collectionType)
/* 212:    */   {
/* 213:343 */     return collectionType.getElementType(this.sfi);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public AssociationType getElementAssociationType(CollectionType collectionType)
/* 217:    */   {
/* 218:354 */     return (AssociationType)getElementType(collectionType);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public SQLFunction findSQLFunction(String functionName)
/* 222:    */   {
/* 223:364 */     return this.sfi.getSqlFunctionRegistry().findSQLFunction(functionName.toLowerCase());
/* 224:    */   }
/* 225:    */   
/* 226:    */   private SQLFunction requireSQLFunction(String functionName)
/* 227:    */   {
/* 228:375 */     SQLFunction f = findSQLFunction(functionName);
/* 229:376 */     if (f == null) {
/* 230:377 */       throw new QueryException("Unable to find SQL function: " + functionName);
/* 231:    */     }
/* 232:379 */     return f;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Type findFunctionReturnType(String functionName, AST first)
/* 236:    */   {
/* 237:390 */     SQLFunction sqlFunction = requireSQLFunction(functionName);
/* 238:391 */     return findFunctionReturnType(functionName, sqlFunction, first);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Type findFunctionReturnType(String functionName, SQLFunction sqlFunction, AST firstArgument)
/* 242:    */   {
/* 243:396 */     Type argumentType = null;
/* 244:397 */     if (firstArgument != null) {
/* 245:398 */       if ("cast".equals(functionName)) {
/* 246:399 */         argumentType = this.sfi.getTypeResolver().heuristicType(firstArgument.getNextSibling().getText());
/* 247:401 */       } else if (SqlNode.class.isInstance(firstArgument)) {
/* 248:402 */         argumentType = ((SqlNode)firstArgument).getDataType();
/* 249:    */       }
/* 250:    */     }
/* 251:406 */     return sqlFunction.getReturnType(argumentType, this.sfi);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String[][] generateColumnNames(Type[] sqlResultTypes)
/* 255:    */   {
/* 256:410 */     return NameGenerator.generateColumnNames(sqlResultTypes, this.sfi);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean isStrictJPAQLComplianceEnabled()
/* 260:    */   {
/* 261:414 */     return this.sfi.getSettings().isStrictJPAQLCompliance();
/* 262:    */   }
/* 263:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.SessionFactoryHelper
 * JD-Core Version:    0.7.0.1
 */