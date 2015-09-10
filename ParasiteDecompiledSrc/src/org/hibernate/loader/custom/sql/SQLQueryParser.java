/*   1:    */ package org.hibernate.loader.custom.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.QueryException;
/*   8:    */ import org.hibernate.cfg.Settings;
/*   9:    */ import org.hibernate.engine.query.spi.ParameterParser;
/*  10:    */ import org.hibernate.engine.query.spi.ParameterParser.Recognizer;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.persister.collection.SQLLoadableCollection;
/*  13:    */ import org.hibernate.persister.entity.SQLLoadable;
/*  14:    */ 
/*  15:    */ public class SQLQueryParser
/*  16:    */ {
/*  17:    */   private static final String HIBERNATE_PLACEHOLDER_PREFIX = "h-";
/*  18:    */   private static final String DOMAIN_PLACEHOLDER = "h-domain";
/*  19:    */   private static final String CATALOG_PLACEHOLDER = "h-catalog";
/*  20:    */   private static final String SCHEMA_PLACEHOLDER = "h-schema";
/*  21:    */   private final SessionFactoryImplementor factory;
/*  22:    */   private final String originalQueryString;
/*  23:    */   private final ParserContext context;
/*  24: 53 */   private final Map namedParameters = new HashMap();
/*  25: 54 */   private long aliasesFound = 0L;
/*  26:    */   
/*  27:    */   public SQLQueryParser(String queryString, ParserContext context, SessionFactoryImplementor factory)
/*  28:    */   {
/*  29: 67 */     this.originalQueryString = queryString;
/*  30: 68 */     this.context = context;
/*  31: 69 */     this.factory = factory;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Map getNamedParameters()
/*  35:    */   {
/*  36: 73 */     return this.namedParameters;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean queryHasAliases()
/*  40:    */   {
/*  41: 77 */     return this.aliasesFound > 0L;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String process()
/*  45:    */   {
/*  46: 81 */     String processedSql = substituteBrackets(this.originalQueryString);
/*  47: 82 */     processedSql = substituteParams(processedSql);
/*  48: 83 */     return processedSql;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private String substituteBrackets(String sqlQuery)
/*  52:    */     throws QueryException
/*  53:    */   {
/*  54: 90 */     StringBuilder result = new StringBuilder(sqlQuery.length() + 20);
/*  55:    */     int right;
/*  56: 94 */     for (int curr = 0; curr < sqlQuery.length(); curr = right + 1)
/*  57:    */     {
/*  58:    */       int left;
/*  59: 95 */       if ((left = sqlQuery.indexOf('{', curr)) < 0)
/*  60:    */       {
/*  61: 98 */         result.append(sqlQuery.substring(curr));
/*  62: 99 */         break;
/*  63:    */       }
/*  64:103 */       result.append(sqlQuery.substring(curr, left));
/*  65:105 */       if ((right = sqlQuery.indexOf('}', left + 1)) < 0) {
/*  66:106 */         throw new QueryException("Unmatched braces for alias path", sqlQuery);
/*  67:    */       }
/*  68:109 */       String aliasPath = sqlQuery.substring(left + 1, right);
/*  69:110 */       boolean isPlaceholder = aliasPath.startsWith("h-");
/*  70:112 */       if (isPlaceholder)
/*  71:    */       {
/*  72:114 */         if ("h-domain".equals(aliasPath))
/*  73:    */         {
/*  74:115 */           String catalogName = this.factory.getSettings().getDefaultCatalogName();
/*  75:116 */           if (catalogName != null)
/*  76:    */           {
/*  77:117 */             result.append(catalogName);
/*  78:118 */             result.append(".");
/*  79:    */           }
/*  80:120 */           String schemaName = this.factory.getSettings().getDefaultSchemaName();
/*  81:121 */           if (schemaName != null)
/*  82:    */           {
/*  83:122 */             result.append(schemaName);
/*  84:123 */             result.append(".");
/*  85:    */           }
/*  86:    */         }
/*  87:127 */         else if ("h-schema".equals(aliasPath))
/*  88:    */         {
/*  89:128 */           String schemaName = this.factory.getSettings().getDefaultSchemaName();
/*  90:129 */           if (schemaName != null)
/*  91:    */           {
/*  92:130 */             result.append(schemaName);
/*  93:131 */             result.append(".");
/*  94:    */           }
/*  95:    */         }
/*  96:135 */         else if ("h-catalog".equals(aliasPath))
/*  97:    */         {
/*  98:136 */           String catalogName = this.factory.getSettings().getDefaultCatalogName();
/*  99:137 */           if (catalogName != null)
/* 100:    */           {
/* 101:138 */             result.append(catalogName);
/* 102:139 */             result.append(".");
/* 103:    */           }
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:143 */           throw new QueryException("Unknown placeholder ", aliasPath);
/* 108:    */         }
/* 109:    */       }
/* 110:    */       else
/* 111:    */       {
/* 112:147 */         int firstDot = aliasPath.indexOf('.');
/* 113:148 */         if (firstDot == -1)
/* 114:    */         {
/* 115:149 */           if (this.context.isEntityAlias(aliasPath))
/* 116:    */           {
/* 117:151 */             result.append(aliasPath);
/* 118:152 */             this.aliasesFound += 1L;
/* 119:    */           }
/* 120:    */           else
/* 121:    */           {
/* 122:156 */             result.append('{').append(aliasPath).append('}');
/* 123:    */           }
/* 124:    */         }
/* 125:    */         else
/* 126:    */         {
/* 127:160 */           String aliasName = aliasPath.substring(0, firstDot);
/* 128:161 */           if (this.context.isCollectionAlias(aliasName))
/* 129:    */           {
/* 130:163 */             String propertyName = aliasPath.substring(firstDot + 1);
/* 131:164 */             result.append(resolveCollectionProperties(aliasName, propertyName));
/* 132:165 */             this.aliasesFound += 1L;
/* 133:    */           }
/* 134:167 */           else if (this.context.isEntityAlias(aliasName))
/* 135:    */           {
/* 136:169 */             String propertyName = aliasPath.substring(firstDot + 1);
/* 137:170 */             result.append(resolveProperties(aliasName, propertyName));
/* 138:171 */             this.aliasesFound += 1L;
/* 139:    */           }
/* 140:    */           else
/* 141:    */           {
/* 142:175 */             result.append('{').append(aliasPath).append('}');
/* 143:    */           }
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:183 */     return result.toString();
/* 148:    */   }
/* 149:    */   
/* 150:    */   private String resolveCollectionProperties(String aliasName, String propertyName)
/* 151:    */   {
/* 152:190 */     Map fieldResults = this.context.getPropertyResultsMapByAlias(aliasName);
/* 153:191 */     SQLLoadableCollection collectionPersister = this.context.getCollectionPersisterByAlias(aliasName);
/* 154:192 */     String collectionSuffix = this.context.getCollectionSuffixByAlias(aliasName);
/* 155:194 */     if ("*".equals(propertyName))
/* 156:    */     {
/* 157:195 */       if (!fieldResults.isEmpty()) {
/* 158:196 */         throw new QueryException("Using return-propertys together with * syntax is not supported.");
/* 159:    */       }
/* 160:199 */       String selectFragment = collectionPersister.selectFragment(aliasName, collectionSuffix);
/* 161:200 */       this.aliasesFound += 1L;
/* 162:201 */       return selectFragment + ", " + resolveProperties(aliasName, propertyName);
/* 163:    */     }
/* 164:205 */     if ("element.*".equals(propertyName)) {
/* 165:206 */       return resolveProperties(aliasName, "*");
/* 166:    */     }
/* 167:212 */     String[] columnAliases = (String[])fieldResults.get(propertyName);
/* 168:213 */     if (columnAliases == null) {
/* 169:214 */       columnAliases = collectionPersister.getCollectionPropertyColumnAliases(propertyName, collectionSuffix);
/* 170:    */     }
/* 171:217 */     if ((columnAliases == null) || (columnAliases.length == 0)) {
/* 172:218 */       throw new QueryException("No column name found for property [" + propertyName + "] for alias [" + aliasName + "]", this.originalQueryString);
/* 173:    */     }
/* 174:223 */     if (columnAliases.length != 1) {
/* 175:225 */       throw new QueryException("SQL queries only support properties mapped to a single column - property [" + propertyName + "] is mapped to " + columnAliases.length + " columns.", this.originalQueryString);
/* 176:    */     }
/* 177:231 */     this.aliasesFound += 1L;
/* 178:232 */     return columnAliases[0];
/* 179:    */   }
/* 180:    */   
/* 181:    */   private String resolveProperties(String aliasName, String propertyName)
/* 182:    */   {
/* 183:239 */     Map fieldResults = this.context.getPropertyResultsMapByAlias(aliasName);
/* 184:240 */     SQLLoadable persister = this.context.getEntityPersisterByAlias(aliasName);
/* 185:241 */     String suffix = this.context.getEntitySuffixByAlias(aliasName);
/* 186:243 */     if ("*".equals(propertyName))
/* 187:    */     {
/* 188:244 */       if (!fieldResults.isEmpty()) {
/* 189:245 */         throw new QueryException("Using return-propertys together with * syntax is not supported.");
/* 190:    */       }
/* 191:247 */       this.aliasesFound += 1L;
/* 192:248 */       return persister.selectFragment(aliasName, suffix);
/* 193:    */     }
/* 194:255 */     String[] columnAliases = (String[])fieldResults.get(propertyName);
/* 195:256 */     if (columnAliases == null) {
/* 196:257 */       columnAliases = persister.getSubclassPropertyColumnAliases(propertyName, suffix);
/* 197:    */     }
/* 198:260 */     if ((columnAliases == null) || (columnAliases.length == 0)) {
/* 199:261 */       throw new QueryException("No column name found for property [" + propertyName + "] for alias [" + aliasName + "]", this.originalQueryString);
/* 200:    */     }
/* 201:266 */     if (columnAliases.length != 1) {
/* 202:268 */       throw new QueryException("SQL queries only support properties mapped to a single column - property [" + propertyName + "] is mapped to " + columnAliases.length + " columns.", this.originalQueryString);
/* 203:    */     }
/* 204:273 */     this.aliasesFound += 1L;
/* 205:274 */     return columnAliases[0];
/* 206:    */   }
/* 207:    */   
/* 208:    */   private String substituteParams(String sqlString)
/* 209:    */   {
/* 210:288 */     ParameterSubstitutionRecognizer recognizer = new ParameterSubstitutionRecognizer();
/* 211:289 */     ParameterParser.parse(sqlString, recognizer);
/* 212:    */     
/* 213:291 */     this.namedParameters.clear();
/* 214:292 */     this.namedParameters.putAll(recognizer.namedParameterBindPoints);
/* 215:    */     
/* 216:294 */     return recognizer.result.toString();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static class ParameterSubstitutionRecognizer
/* 220:    */     implements ParameterParser.Recognizer
/* 221:    */   {
/* 222:298 */     StringBuilder result = new StringBuilder();
/* 223:299 */     Map namedParameterBindPoints = new HashMap();
/* 224:300 */     int parameterCount = 0;
/* 225:    */     
/* 226:    */     public void outParameter(int position)
/* 227:    */     {
/* 228:303 */       this.result.append('?');
/* 229:    */     }
/* 230:    */     
/* 231:    */     public void ordinalParameter(int position)
/* 232:    */     {
/* 233:307 */       this.result.append('?');
/* 234:    */     }
/* 235:    */     
/* 236:    */     public void namedParameter(String name, int position)
/* 237:    */     {
/* 238:311 */       addNamedParameter(name);
/* 239:312 */       this.result.append('?');
/* 240:    */     }
/* 241:    */     
/* 242:    */     public void jpaPositionalParameter(String name, int position)
/* 243:    */     {
/* 244:316 */       namedParameter(name, position);
/* 245:    */     }
/* 246:    */     
/* 247:    */     public void other(char character)
/* 248:    */     {
/* 249:320 */       this.result.append(character);
/* 250:    */     }
/* 251:    */     
/* 252:    */     private void addNamedParameter(String name)
/* 253:    */     {
/* 254:324 */       Integer loc = Integer.valueOf(this.parameterCount++);
/* 255:325 */       Object o = this.namedParameterBindPoints.get(name);
/* 256:326 */       if (o == null)
/* 257:    */       {
/* 258:327 */         this.namedParameterBindPoints.put(name, loc);
/* 259:    */       }
/* 260:329 */       else if ((o instanceof Integer))
/* 261:    */       {
/* 262:330 */         ArrayList list = new ArrayList(4);
/* 263:331 */         list.add(o);
/* 264:332 */         list.add(loc);
/* 265:333 */         this.namedParameterBindPoints.put(name, list);
/* 266:    */       }
/* 267:    */       else
/* 268:    */       {
/* 269:336 */         ((List)o).add(loc);
/* 270:    */       }
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   static abstract interface ParserContext
/* 275:    */   {
/* 276:    */     public abstract boolean isEntityAlias(String paramString);
/* 277:    */     
/* 278:    */     public abstract SQLLoadable getEntityPersisterByAlias(String paramString);
/* 279:    */     
/* 280:    */     public abstract String getEntitySuffixByAlias(String paramString);
/* 281:    */     
/* 282:    */     public abstract boolean isCollectionAlias(String paramString);
/* 283:    */     
/* 284:    */     public abstract SQLLoadableCollection getCollectionPersisterByAlias(String paramString);
/* 285:    */     
/* 286:    */     public abstract String getCollectionSuffixByAlias(String paramString);
/* 287:    */     
/* 288:    */     public abstract Map getPropertyResultsMapByAlias(String paramString);
/* 289:    */   }
/* 290:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.sql.SQLQueryParser
 * JD-Core Version:    0.7.0.1
 */