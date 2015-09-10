/*   1:    */ package org.hibernate.cache.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.engine.spi.QueryParameters;
/*  10:    */ import org.hibernate.engine.spi.RowSelection;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.engine.spi.TypedValue;
/*  13:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  14:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*  15:    */ import org.hibernate.transform.CacheableResultTransformer;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public class QueryKey
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private final String sqlQueryString;
/*  22:    */   private final Type[] positionalParameterTypes;
/*  23:    */   private final Object[] positionalParameterValues;
/*  24:    */   private final Map namedParameters;
/*  25:    */   private final Integer firstRow;
/*  26:    */   private final Integer maxRows;
/*  27:    */   private final String tenantIdentifier;
/*  28:    */   private final Set filterKeys;
/*  29:    */   private final CacheableResultTransformer customTransformer;
/*  30:    */   private transient int hashCode;
/*  31:    */   
/*  32:    */   public static QueryKey generateQueryKey(String queryString, QueryParameters queryParameters, Set filterKeys, SessionImplementor session, CacheableResultTransformer customTransformer)
/*  33:    */   {
/*  34: 86 */     int positionalParameterCount = queryParameters.getPositionalParameterTypes().length;
/*  35: 87 */     Type[] types = new Type[positionalParameterCount];
/*  36: 88 */     Object[] values = new Object[positionalParameterCount];
/*  37: 89 */     for (int i = 0; i < positionalParameterCount; i++)
/*  38:    */     {
/*  39: 90 */       types[i] = queryParameters.getPositionalParameterTypes()[i];
/*  40: 91 */       values[i] = types[i].disassemble(queryParameters.getPositionalParameterValues()[i], session, null);
/*  41:    */     }
/*  42:    */     Map<String, TypedValue> namedParameters;
/*  43:    */     Map<String, TypedValue> namedParameters;
/*  44: 96 */     if (queryParameters.getNamedParameters() == null)
/*  45:    */     {
/*  46: 97 */       namedParameters = null;
/*  47:    */     }
/*  48:    */     else
/*  49:    */     {
/*  50:100 */       namedParameters = CollectionHelper.mapOfSize(queryParameters.getNamedParameters().size());
/*  51:101 */       for (Map.Entry<String, TypedValue> namedParameterEntry : queryParameters.getNamedParameters().entrySet()) {
/*  52:102 */         namedParameters.put(namedParameterEntry.getKey(), new TypedValue(((TypedValue)namedParameterEntry.getValue()).getType(), ((TypedValue)namedParameterEntry.getValue()).getType().disassemble(((TypedValue)namedParameterEntry.getValue()).getValue(), session, null)));
/*  53:    */       }
/*  54:    */     }
/*  55:117 */     RowSelection selection = queryParameters.getRowSelection();
/*  56:    */     Integer maxRows;
/*  57:    */     Integer firstRow;
/*  58:    */     Integer maxRows;
/*  59:120 */     if (selection != null)
/*  60:    */     {
/*  61:121 */       Integer firstRow = selection.getFirstRow();
/*  62:122 */       maxRows = selection.getMaxRows();
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66:125 */       firstRow = null;
/*  67:126 */       maxRows = null;
/*  68:    */     }
/*  69:129 */     return new QueryKey(queryString, types, values, namedParameters, firstRow, maxRows, filterKeys, session.getTenantIdentifier(), customTransformer);
/*  70:    */   }
/*  71:    */   
/*  72:    */   QueryKey(String sqlQueryString, Type[] positionalParameterTypes, Object[] positionalParameterValues, Map namedParameters, Integer firstRow, Integer maxRows, Set filterKeys, String tenantIdentifier, CacheableResultTransformer customTransformer)
/*  73:    */   {
/*  74:165 */     this.sqlQueryString = sqlQueryString;
/*  75:166 */     this.positionalParameterTypes = positionalParameterTypes;
/*  76:167 */     this.positionalParameterValues = positionalParameterValues;
/*  77:168 */     this.namedParameters = namedParameters;
/*  78:169 */     this.firstRow = firstRow;
/*  79:170 */     this.maxRows = maxRows;
/*  80:171 */     this.tenantIdentifier = tenantIdentifier;
/*  81:172 */     this.filterKeys = filterKeys;
/*  82:173 */     this.customTransformer = customTransformer;
/*  83:174 */     this.hashCode = generateHashCode();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public CacheableResultTransformer getResultTransformer()
/*  87:    */   {
/*  88:178 */     return this.customTransformer;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void readObject(ObjectInputStream in)
/*  92:    */     throws IOException, ClassNotFoundException
/*  93:    */   {
/*  94:190 */     in.defaultReadObject();
/*  95:191 */     this.hashCode = generateHashCode();
/*  96:    */   }
/*  97:    */   
/*  98:    */   private int generateHashCode()
/*  99:    */   {
/* 100:195 */     int result = 13;
/* 101:196 */     result = 37 * result + (this.firstRow == null ? 0 : this.firstRow.hashCode());
/* 102:197 */     result = 37 * result + (this.maxRows == null ? 0 : this.maxRows.hashCode());
/* 103:198 */     for (int i = 0; i < this.positionalParameterValues.length; i++) {
/* 104:199 */       result = 37 * result + (this.positionalParameterValues[i] == null ? 0 : this.positionalParameterTypes[i].getHashCode(this.positionalParameterValues[i]));
/* 105:    */     }
/* 106:201 */     result = 37 * result + (this.namedParameters == null ? 0 : this.namedParameters.hashCode());
/* 107:202 */     result = 37 * result + (this.filterKeys == null ? 0 : this.filterKeys.hashCode());
/* 108:203 */     result = 37 * result + (this.customTransformer == null ? 0 : this.customTransformer.hashCode());
/* 109:204 */     result = 37 * result + (this.tenantIdentifier == null ? 0 : this.tenantIdentifier.hashCode());
/* 110:205 */     result = 37 * result + this.sqlQueryString.hashCode();
/* 111:206 */     return result;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean equals(Object other)
/* 115:    */   {
/* 116:214 */     if (!(other instanceof QueryKey)) {
/* 117:215 */       return false;
/* 118:    */     }
/* 119:217 */     QueryKey that = (QueryKey)other;
/* 120:218 */     if (!this.sqlQueryString.equals(that.sqlQueryString)) {
/* 121:219 */       return false;
/* 122:    */     }
/* 123:221 */     if ((!EqualsHelper.equals(this.firstRow, that.firstRow)) || (!EqualsHelper.equals(this.maxRows, that.maxRows))) {
/* 124:222 */       return false;
/* 125:    */     }
/* 126:224 */     if (!EqualsHelper.equals(this.customTransformer, that.customTransformer)) {
/* 127:225 */       return false;
/* 128:    */     }
/* 129:227 */     if (this.positionalParameterTypes == null)
/* 130:    */     {
/* 131:228 */       if (that.positionalParameterTypes != null) {
/* 132:229 */         return false;
/* 133:    */       }
/* 134:    */     }
/* 135:    */     else
/* 136:    */     {
/* 137:233 */       if (that.positionalParameterTypes == null) {
/* 138:234 */         return false;
/* 139:    */       }
/* 140:236 */       if (this.positionalParameterTypes.length != that.positionalParameterTypes.length) {
/* 141:237 */         return false;
/* 142:    */       }
/* 143:239 */       for (int i = 0; i < this.positionalParameterTypes.length; i++)
/* 144:    */       {
/* 145:240 */         if (this.positionalParameterTypes[i].getReturnedClass() != that.positionalParameterTypes[i].getReturnedClass()) {
/* 146:241 */           return false;
/* 147:    */         }
/* 148:243 */         if (!this.positionalParameterTypes[i].isEqual(this.positionalParameterValues[i], that.positionalParameterValues[i])) {
/* 149:244 */           return false;
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:249 */     return (EqualsHelper.equals(this.filterKeys, that.filterKeys)) && (EqualsHelper.equals(this.namedParameters, that.namedParameters)) && (EqualsHelper.equals(this.tenantIdentifier, that.tenantIdentifier));
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int hashCode()
/* 157:    */   {
/* 158:259 */     return this.hashCode;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String toString()
/* 162:    */   {
/* 163:267 */     StringBuilder buffer = new StringBuilder("sql: ").append(this.sqlQueryString);
/* 164:268 */     if (this.positionalParameterValues != null)
/* 165:    */     {
/* 166:269 */       buffer.append("; parameters: ");
/* 167:270 */       for (Object positionalParameterValue : this.positionalParameterValues) {
/* 168:271 */         buffer.append(positionalParameterValue).append(", ");
/* 169:    */       }
/* 170:    */     }
/* 171:274 */     if (this.namedParameters != null) {
/* 172:275 */       buffer.append("; named parameters: ").append(this.namedParameters);
/* 173:    */     }
/* 174:277 */     if (this.filterKeys != null) {
/* 175:278 */       buffer.append("; filterKeys: ").append(this.filterKeys);
/* 176:    */     }
/* 177:280 */     if (this.firstRow != null) {
/* 178:281 */       buffer.append("; first row: ").append(this.firstRow);
/* 179:    */     }
/* 180:283 */     if (this.maxRows != null) {
/* 181:284 */       buffer.append("; max rows: ").append(this.maxRows);
/* 182:    */     }
/* 183:286 */     if (this.customTransformer != null) {
/* 184:287 */       buffer.append("; transformer: ").append(this.customTransformer);
/* 185:    */     }
/* 186:289 */     return buffer.toString();
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.QueryKey
 * JD-Core Version:    0.7.0.1
 */