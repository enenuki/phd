/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Set;
/*  12:    */ import javax.persistence.ColumnResult;
/*  13:    */ import javax.persistence.EntityResult;
/*  14:    */ import javax.persistence.FieldResult;
/*  15:    */ import javax.persistence.SqlResultSetMapping;
/*  16:    */ import org.hibernate.LockMode;
/*  17:    */ import org.hibernate.MappingException;
/*  18:    */ import org.hibernate.cfg.BinderHelper;
/*  19:    */ import org.hibernate.cfg.Mappings;
/*  20:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  21:    */ import org.hibernate.cfg.QuerySecondPass;
/*  22:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  23:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  24:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn;
/*  25:    */ import org.hibernate.internal.CoreMessageLogger;
/*  26:    */ import org.hibernate.internal.util.StringHelper;
/*  27:    */ import org.hibernate.mapping.Component;
/*  28:    */ import org.hibernate.mapping.PersistentClass;
/*  29:    */ import org.hibernate.mapping.Property;
/*  30:    */ import org.hibernate.mapping.ToOne;
/*  31:    */ import org.hibernate.mapping.Value;
/*  32:    */ import org.jboss.logging.Logger;
/*  33:    */ 
/*  34:    */ public class ResultsetMappingSecondPass
/*  35:    */   implements QuerySecondPass
/*  36:    */ {
/*  37: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ResultsetMappingSecondPass.class.getName());
/*  38:    */   private SqlResultSetMapping ann;
/*  39:    */   private Mappings mappings;
/*  40:    */   private boolean isDefault;
/*  41:    */   
/*  42:    */   public ResultsetMappingSecondPass(SqlResultSetMapping ann, Mappings mappings, boolean isDefault)
/*  43:    */   {
/*  44: 68 */     this.ann = ann;
/*  45: 69 */     this.mappings = mappings;
/*  46: 70 */     this.isDefault = isDefault;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void doSecondPass(Map persistentClasses)
/*  50:    */     throws MappingException
/*  51:    */   {
/*  52: 75 */     if (this.ann == null) {
/*  53: 75 */       return;
/*  54:    */     }
/*  55: 76 */     ResultSetMappingDefinition definition = new ResultSetMappingDefinition(this.ann.name());
/*  56: 77 */     LOG.debugf("Binding result set mapping: %s", definition.getName());
/*  57:    */     
/*  58: 79 */     int entityAliasIndex = 0;
/*  59: 81 */     for (EntityResult entity : this.ann.entities())
/*  60:    */     {
/*  61: 83 */       List<FieldResult> properties = new ArrayList();
/*  62: 84 */       List<String> propertyNames = new ArrayList();
/*  63: 85 */       for (FieldResult field : entity.fields())
/*  64:    */       {
/*  65: 87 */         String name = field.name();
/*  66: 88 */         if (name.indexOf('.') == -1)
/*  67:    */         {
/*  68: 90 */           properties.add(field);
/*  69: 91 */           propertyNames.add(name);
/*  70:    */         }
/*  71:    */         else
/*  72:    */         {
/*  73:100 */           PersistentClass pc = this.mappings.getClass(entity.entityClass().getName());
/*  74:101 */           if (pc == null) {
/*  75:102 */             throw new MappingException("Entity not found " + entity.entityClass().getName() + " in SqlResultsetMapping " + this.ann.name());
/*  76:    */           }
/*  77:107 */           int dotIndex = name.lastIndexOf('.');
/*  78:108 */           String reducedName = name.substring(0, dotIndex);
/*  79:109 */           Iterator parentPropIter = getSubPropertyIterator(pc, reducedName);
/*  80:110 */           List followers = getFollowers(parentPropIter, reducedName, name);
/*  81:    */           
/*  82:112 */           int index = propertyNames.size();
/*  83:113 */           int followersSize = followers.size();
/*  84:114 */           for (int loop = 0; loop < followersSize; loop++)
/*  85:    */           {
/*  86:115 */             String follower = (String)followers.get(loop);
/*  87:116 */             int currentIndex = getIndexOfFirstMatchingProperty(propertyNames, follower);
/*  88:117 */             index = (currentIndex != -1) && (currentIndex < index) ? currentIndex : index;
/*  89:    */           }
/*  90:119 */           propertyNames.add(index, name);
/*  91:120 */           properties.add(index, field);
/*  92:    */         }
/*  93:    */       }
/*  94:124 */       Set<String> uniqueReturnProperty = new HashSet();
/*  95:125 */       Map<String, ArrayList<String>> propertyResultsTmp = new HashMap();
/*  96:126 */       for (Object property : properties)
/*  97:    */       {
/*  98:127 */         FieldResult propertyresult = (FieldResult)property;
/*  99:128 */         String name = propertyresult.name();
/* 100:129 */         if ("class".equals(name)) {
/* 101:130 */           throw new MappingException("class is not a valid property name to use in a @FieldResult, use @Entity(discriminatorColumn) instead");
/* 102:    */         }
/* 103:135 */         if (uniqueReturnProperty.contains(name)) {
/* 104:136 */           throw new MappingException("duplicate @FieldResult for property " + name + " on @Entity " + entity.entityClass().getName() + " in " + this.ann.name());
/* 105:    */         }
/* 106:141 */         uniqueReturnProperty.add(name);
/* 107:    */         
/* 108:143 */         String quotingNormalizedColumnName = this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(propertyresult.column());
/* 109:    */         
/* 110:    */ 
/* 111:146 */         String key = StringHelper.root(name);
/* 112:147 */         ArrayList<String> intermediateResults = (ArrayList)propertyResultsTmp.get(key);
/* 113:148 */         if (intermediateResults == null)
/* 114:    */         {
/* 115:149 */           intermediateResults = new ArrayList();
/* 116:150 */           propertyResultsTmp.put(key, intermediateResults);
/* 117:    */         }
/* 118:152 */         intermediateResults.add(quotingNormalizedColumnName);
/* 119:    */       }
/* 120:155 */       Map<String, String[]> propertyResults = new HashMap();
/* 121:156 */       for (Map.Entry<String, ArrayList<String>> entry : propertyResultsTmp.entrySet()) {
/* 122:157 */         propertyResults.put(entry.getKey(), ((ArrayList)entry.getValue()).toArray(new String[((ArrayList)entry.getValue()).size()]));
/* 123:    */       }
/* 124:163 */       if (!BinderHelper.isEmptyAnnotationValue(entity.discriminatorColumn()))
/* 125:    */       {
/* 126:164 */         String quotingNormalizedName = this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(entity.discriminatorColumn());
/* 127:    */         
/* 128:    */ 
/* 129:167 */         propertyResults.put("class", new String[] { quotingNormalizedName });
/* 130:    */       }
/* 131:170 */       if (propertyResults.isEmpty()) {
/* 132:171 */         propertyResults = Collections.emptyMap();
/* 133:    */       }
/* 134:174 */       NativeSQLQueryRootReturn result = new NativeSQLQueryRootReturn("alias" + entityAliasIndex++, entity.entityClass().getName(), propertyResults, LockMode.READ);
/* 135:    */       
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:180 */       definition.addQueryReturn(result);
/* 141:    */     }
/* 142:183 */     for (ColumnResult column : this.ann.columns()) {
/* 143:184 */       definition.addQueryReturn(new NativeSQLQueryScalarReturn(this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(column.name()), null));
/* 144:    */     }
/* 145:194 */     if (this.isDefault) {
/* 146:195 */       this.mappings.addDefaultResultSetMapping(definition);
/* 147:    */     } else {
/* 148:198 */       this.mappings.addResultSetMapping(definition);
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   private List getFollowers(Iterator parentPropIter, String reducedName, String name)
/* 153:    */   {
/* 154:204 */     boolean hasFollowers = false;
/* 155:205 */     List followers = new ArrayList();
/* 156:206 */     while (parentPropIter.hasNext())
/* 157:    */     {
/* 158:207 */       String currentPropertyName = ((Property)parentPropIter.next()).getName();
/* 159:208 */       String currentName = reducedName + '.' + currentPropertyName;
/* 160:209 */       if (hasFollowers) {
/* 161:210 */         followers.add(currentName);
/* 162:    */       }
/* 163:212 */       if (name.equals(currentName)) {
/* 164:212 */         hasFollowers = true;
/* 165:    */       }
/* 166:    */     }
/* 167:214 */     return followers;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private Iterator getSubPropertyIterator(PersistentClass pc, String reducedName)
/* 171:    */   {
/* 172:218 */     Value value = pc.getRecursiveProperty(reducedName).getValue();
/* 173:    */     Iterator parentPropIter;
/* 174:220 */     if ((value instanceof Component))
/* 175:    */     {
/* 176:221 */       Component comp = (Component)value;
/* 177:222 */       parentPropIter = comp.getPropertyIterator();
/* 178:    */     }
/* 179:224 */     else if ((value instanceof ToOne))
/* 180:    */     {
/* 181:225 */       ToOne toOne = (ToOne)value;
/* 182:226 */       PersistentClass referencedPc = this.mappings.getClass(toOne.getReferencedEntityName());
/* 183:    */       Iterator parentPropIter;
/* 184:227 */       if (toOne.getReferencedPropertyName() != null) {
/* 185:    */         try
/* 186:    */         {
/* 187:229 */           parentPropIter = ((Component)referencedPc.getRecursiveProperty(toOne.getReferencedPropertyName()).getValue()).getPropertyIterator();
/* 188:    */         }
/* 189:    */         catch (ClassCastException e)
/* 190:    */         {
/* 191:234 */           throw new MappingException("dotted notation reference neither a component nor a many/one to one", e);
/* 192:    */         }
/* 193:    */       } else {
/* 194:    */         try
/* 195:    */         {
/* 196:241 */           if (referencedPc.getIdentifierMapper() == null) {
/* 197:242 */             parentPropIter = ((Component)referencedPc.getIdentifierProperty().getValue()).getPropertyIterator();
/* 198:    */           } else {
/* 199:246 */             parentPropIter = referencedPc.getIdentifierMapper().getPropertyIterator();
/* 200:    */           }
/* 201:    */         }
/* 202:    */         catch (ClassCastException e)
/* 203:    */         {
/* 204:    */           Iterator parentPropIter;
/* 205:250 */           throw new MappingException("dotted notation reference neither a component nor a many/one to one", e);
/* 206:    */         }
/* 207:    */       }
/* 208:    */     }
/* 209:    */     else
/* 210:    */     {
/* 211:257 */       throw new MappingException("dotted notation reference neither a component nor a many/one to one");
/* 212:    */     }
/* 213:    */     Iterator parentPropIter;
/* 214:259 */     return parentPropIter;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private static int getIndexOfFirstMatchingProperty(List propertyNames, String follower)
/* 218:    */   {
/* 219:263 */     int propertySize = propertyNames.size();
/* 220:264 */     for (int propIndex = 0; propIndex < propertySize; propIndex++) {
/* 221:265 */       if (((String)propertyNames.get(propIndex)).startsWith(follower)) {
/* 222:266 */         return propIndex;
/* 223:    */       }
/* 224:    */     }
/* 225:269 */     return -1;
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.ResultsetMappingSecondPass
 * JD-Core Version:    0.7.0.1
 */