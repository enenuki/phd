/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.dialect.Dialect;
/*   7:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*   8:    */ import org.hibernate.engine.spi.Mapping;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ 
/*  12:    */ public class Column
/*  13:    */   implements Selectable, Serializable, Cloneable
/*  14:    */ {
/*  15:    */   public static final int DEFAULT_LENGTH = 255;
/*  16:    */   public static final int DEFAULT_PRECISION = 19;
/*  17:    */   public static final int DEFAULT_SCALE = 2;
/*  18: 45 */   private int length = 255;
/*  19: 46 */   private int precision = 19;
/*  20: 47 */   private int scale = 2;
/*  21:    */   private Value value;
/*  22: 49 */   private int typeIndex = 0;
/*  23:    */   private String name;
/*  24: 51 */   private boolean nullable = true;
/*  25: 52 */   private boolean unique = false;
/*  26:    */   private String sqlType;
/*  27:    */   private Integer sqlTypeCode;
/*  28: 55 */   private boolean quoted = false;
/*  29:    */   int uniqueInteger;
/*  30:    */   private String checkConstraint;
/*  31:    */   private String comment;
/*  32:    */   private String defaultValue;
/*  33:    */   private String customWrite;
/*  34:    */   private String customRead;
/*  35:    */   
/*  36:    */   public Column() {}
/*  37:    */   
/*  38:    */   public Column(String columnName)
/*  39:    */   {
/*  40: 67 */     setName(columnName);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getLength()
/*  44:    */   {
/*  45: 71 */     return this.length;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setLength(int length)
/*  49:    */   {
/*  50: 74 */     this.length = length;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Value getValue()
/*  54:    */   {
/*  55: 77 */     return this.value;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setValue(Value value)
/*  59:    */   {
/*  60: 80 */     this.value = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getName()
/*  64:    */   {
/*  65: 83 */     return this.name;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setName(String name)
/*  69:    */   {
/*  70: 86 */     if ((name.charAt(0) == '`') || ("`\"[".indexOf(name.charAt(0)) > -1))
/*  71:    */     {
/*  72: 90 */       this.quoted = true;
/*  73: 91 */       this.name = name.substring(1, name.length() - 1);
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77: 94 */       this.name = name;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getQuotedName()
/*  82:    */   {
/*  83:100 */     return this.quoted ? "`" + this.name + "`" : this.name;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getQuotedName(Dialect d)
/*  87:    */   {
/*  88:106 */     return this.quoted ? d.openQuote() + this.name + d.closeQuote() : this.name;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getAlias(Dialect dialect)
/*  92:    */   {
/*  93:117 */     String alias = this.name;
/*  94:118 */     String unique = Integer.toString(this.uniqueInteger) + '_';
/*  95:119 */     int lastLetter = StringHelper.lastIndexOfLetter(this.name);
/*  96:120 */     if (lastLetter == -1) {
/*  97:121 */       alias = "column";
/*  98:123 */     } else if (lastLetter < this.name.length() - 1) {
/*  99:124 */       alias = this.name.substring(0, lastLetter + 1);
/* 100:    */     }
/* 101:126 */     if (alias.length() > dialect.getMaxAliasLength()) {
/* 102:127 */       alias = alias.substring(0, dialect.getMaxAliasLength() - unique.length());
/* 103:    */     }
/* 104:129 */     boolean useRawName = (this.name.equals(alias)) && (!this.quoted) && (!this.name.toLowerCase().equals("rowid"));
/* 105:132 */     if (useRawName) {
/* 106:133 */       return alias;
/* 107:    */     }
/* 108:136 */     return alias + unique;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getAlias(Dialect dialect, Table table)
/* 112:    */   {
/* 113:144 */     return getAlias(dialect) + table.getUniqueInteger() + '_';
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isNullable()
/* 117:    */   {
/* 118:148 */     return this.nullable;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNullable(boolean nullable)
/* 122:    */   {
/* 123:152 */     this.nullable = nullable;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getTypeIndex()
/* 127:    */   {
/* 128:156 */     return this.typeIndex;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTypeIndex(int typeIndex)
/* 132:    */   {
/* 133:159 */     this.typeIndex = typeIndex;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isUnique()
/* 137:    */   {
/* 138:163 */     return this.unique;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int hashCode()
/* 142:    */   {
/* 143:168 */     return isQuoted() ? this.name.hashCode() : this.name.toLowerCase().hashCode();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean equals(Object object)
/* 147:    */   {
/* 148:174 */     return ((object instanceof Column)) && (equals((Column)object));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean equals(Column column)
/* 152:    */   {
/* 153:178 */     if (null == column) {
/* 154:178 */       return false;
/* 155:    */     }
/* 156:179 */     if (this == column) {
/* 157:179 */       return true;
/* 158:    */     }
/* 159:181 */     return isQuoted() ? this.name.equals(column.name) : this.name.equalsIgnoreCase(column.name);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getSqlTypeCode(Mapping mapping)
/* 163:    */     throws MappingException
/* 164:    */   {
/* 165:187 */     Type type = getValue().getType();
/* 166:    */     try
/* 167:    */     {
/* 168:189 */       int sqlTypeCode = type.sqlTypes(mapping)[getTypeIndex()];
/* 169:190 */       if ((getSqlTypeCode() != null) && (getSqlTypeCode().intValue() != sqlTypeCode)) {
/* 170:191 */         throw new MappingException("SQLType code's does not match. mapped as " + sqlTypeCode + " but is " + getSqlTypeCode());
/* 171:    */       }
/* 172:193 */       return sqlTypeCode;
/* 173:    */     }
/* 174:    */     catch (Exception e)
/* 175:    */     {
/* 176:196 */       throw new MappingException("Could not determine type for column " + this.name + " of type " + type.getClass().getName() + ": " + e.getClass().getName(), e);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Integer getSqlTypeCode()
/* 181:    */   {
/* 182:218 */     return this.sqlTypeCode;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setSqlTypeCode(Integer typeCode)
/* 186:    */   {
/* 187:222 */     this.sqlTypeCode = typeCode;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getSqlType(Dialect dialect, Mapping mapping)
/* 191:    */     throws HibernateException
/* 192:    */   {
/* 193:226 */     if (this.sqlType == null) {
/* 194:227 */       this.sqlType = dialect.getTypeName(getSqlTypeCode(mapping), getLength(), getPrecision(), getScale());
/* 195:    */     }
/* 196:229 */     return this.sqlType;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getSqlType()
/* 200:    */   {
/* 201:233 */     return this.sqlType;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setSqlType(String sqlType)
/* 205:    */   {
/* 206:237 */     this.sqlType = sqlType;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setUnique(boolean unique)
/* 210:    */   {
/* 211:241 */     this.unique = unique;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean isQuoted()
/* 215:    */   {
/* 216:245 */     return this.quoted;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String toString()
/* 220:    */   {
/* 221:249 */     return getClass().getName() + '(' + getName() + ')';
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getCheckConstraint()
/* 225:    */   {
/* 226:253 */     return this.checkConstraint;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setCheckConstraint(String checkConstraint)
/* 230:    */   {
/* 231:257 */     this.checkConstraint = checkConstraint;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean hasCheckConstraint()
/* 235:    */   {
/* 236:261 */     return this.checkConstraint != null;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String getTemplate(Dialect dialect, SQLFunctionRegistry functionRegistry)
/* 240:    */   {
/* 241:265 */     return "$PlaceHolder$." + getQuotedName(dialect);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean hasCustomRead()
/* 245:    */   {
/* 246:271 */     return (this.customRead != null) && (this.customRead.length() > 0);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getReadExpr(Dialect dialect)
/* 250:    */   {
/* 251:275 */     return hasCustomRead() ? this.customRead : getQuotedName(dialect);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String getWriteExpr()
/* 255:    */   {
/* 256:279 */     return (this.customWrite != null) && (this.customWrite.length() > 0) ? this.customWrite : "?";
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean isFormula()
/* 260:    */   {
/* 261:283 */     return false;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getText(Dialect d)
/* 265:    */   {
/* 266:287 */     return getQuotedName(d);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getText()
/* 270:    */   {
/* 271:290 */     return getName();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getPrecision()
/* 275:    */   {
/* 276:294 */     return this.precision;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setPrecision(int scale)
/* 280:    */   {
/* 281:297 */     this.precision = scale;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public int getScale()
/* 285:    */   {
/* 286:301 */     return this.scale;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setScale(int scale)
/* 290:    */   {
/* 291:304 */     this.scale = scale;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String getComment()
/* 295:    */   {
/* 296:308 */     return this.comment;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setComment(String comment)
/* 300:    */   {
/* 301:312 */     this.comment = comment;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String getDefaultValue()
/* 305:    */   {
/* 306:316 */     return this.defaultValue;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setDefaultValue(String defaultValue)
/* 310:    */   {
/* 311:320 */     this.defaultValue = defaultValue;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public String getCustomWrite()
/* 315:    */   {
/* 316:324 */     return this.customWrite;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setCustomWrite(String customWrite)
/* 320:    */   {
/* 321:328 */     this.customWrite = customWrite;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public String getCustomRead()
/* 325:    */   {
/* 326:332 */     return this.customRead;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setCustomRead(String customRead)
/* 330:    */   {
/* 331:336 */     this.customRead = customRead;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public String getCanonicalName()
/* 335:    */   {
/* 336:340 */     return this.quoted ? this.name : this.name.toLowerCase();
/* 337:    */   }
/* 338:    */   
/* 339:    */   protected Object clone()
/* 340:    */   {
/* 341:347 */     Column copy = new Column();
/* 342:348 */     copy.setLength(this.length);
/* 343:349 */     copy.setScale(this.scale);
/* 344:350 */     copy.setValue(this.value);
/* 345:351 */     copy.setTypeIndex(this.typeIndex);
/* 346:352 */     copy.setName(getQuotedName());
/* 347:353 */     copy.setNullable(this.nullable);
/* 348:354 */     copy.setPrecision(this.precision);
/* 349:355 */     copy.setUnique(this.unique);
/* 350:356 */     copy.setSqlType(this.sqlType);
/* 351:357 */     copy.setSqlTypeCode(this.sqlTypeCode);
/* 352:358 */     copy.uniqueInteger = this.uniqueInteger;
/* 353:359 */     copy.setCheckConstraint(this.checkConstraint);
/* 354:360 */     copy.setComment(this.comment);
/* 355:361 */     copy.setDefaultValue(this.defaultValue);
/* 356:362 */     copy.setCustomRead(this.customRead);
/* 357:363 */     copy.setCustomWrite(this.customWrite);
/* 358:364 */     return copy;
/* 359:    */   }
/* 360:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Column
 * JD-Core Version:    0.7.0.1
 */