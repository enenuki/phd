/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import org.hibernate.AssertionFailure;
/*   4:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*   5:    */ import org.jboss.jandex.AnnotationInstance;
/*   6:    */ import org.jboss.jandex.AnnotationValue;
/*   7:    */ import org.jboss.jandex.DotName;
/*   8:    */ 
/*   9:    */ public class ColumnValues
/*  10:    */ {
/*  11: 38 */   private String name = "";
/*  12: 39 */   private boolean unique = false;
/*  13: 40 */   private boolean nullable = true;
/*  14: 41 */   private boolean insertable = true;
/*  15: 42 */   private boolean updatable = true;
/*  16: 43 */   private String columnDefinition = "";
/*  17: 44 */   private String table = null;
/*  18: 45 */   private int length = 255;
/*  19: 46 */   private int precision = 0;
/*  20: 47 */   private int scale = 0;
/*  21:    */   
/*  22:    */   ColumnValues()
/*  23:    */   {
/*  24: 50 */     this(null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ColumnValues(AnnotationInstance columnAnnotation)
/*  28:    */   {
/*  29: 54 */     if ((columnAnnotation != null) && (!JPADotNames.COLUMN.equals(columnAnnotation.name()))) {
/*  30: 55 */       throw new AssertionFailure("A @Column annotation needs to be passed to the constructor");
/*  31:    */     }
/*  32: 57 */     applyColumnValues(columnAnnotation);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void applyColumnValues(AnnotationInstance columnAnnotation)
/*  36:    */   {
/*  37: 62 */     if (columnAnnotation == null) {
/*  38: 63 */       return;
/*  39:    */     }
/*  40: 66 */     AnnotationValue nameValue = columnAnnotation.value("name");
/*  41: 67 */     if (nameValue != null) {
/*  42: 68 */       this.name = nameValue.asString();
/*  43:    */     }
/*  44: 71 */     AnnotationValue uniqueValue = columnAnnotation.value("unique");
/*  45: 72 */     if (uniqueValue != null) {
/*  46: 73 */       this.unique = nameValue.asBoolean();
/*  47:    */     }
/*  48: 76 */     AnnotationValue nullableValue = columnAnnotation.value("nullable");
/*  49: 77 */     if (nullableValue != null) {
/*  50: 78 */       this.nullable = nullableValue.asBoolean();
/*  51:    */     }
/*  52: 81 */     AnnotationValue insertableValue = columnAnnotation.value("insertable");
/*  53: 82 */     if (insertableValue != null) {
/*  54: 83 */       this.insertable = insertableValue.asBoolean();
/*  55:    */     }
/*  56: 86 */     AnnotationValue updatableValue = columnAnnotation.value("updatable");
/*  57: 87 */     if (updatableValue != null) {
/*  58: 88 */       this.updatable = updatableValue.asBoolean();
/*  59:    */     }
/*  60: 91 */     AnnotationValue columnDefinition = columnAnnotation.value("columnDefinition");
/*  61: 92 */     if (columnDefinition != null) {
/*  62: 93 */       this.columnDefinition = columnDefinition.asString();
/*  63:    */     }
/*  64: 96 */     AnnotationValue tableValue = columnAnnotation.value("table");
/*  65: 97 */     if (tableValue != null) {
/*  66: 98 */       this.table = tableValue.asString();
/*  67:    */     }
/*  68:101 */     AnnotationValue lengthValue = columnAnnotation.value("length");
/*  69:102 */     if (lengthValue != null) {
/*  70:103 */       this.length = lengthValue.asInt();
/*  71:    */     }
/*  72:106 */     AnnotationValue precisionValue = columnAnnotation.value("precision");
/*  73:107 */     if (precisionValue != null) {
/*  74:108 */       this.precision = precisionValue.asInt();
/*  75:    */     }
/*  76:111 */     AnnotationValue scaleValue = columnAnnotation.value("scale");
/*  77:112 */     if (scaleValue != null) {
/*  78:113 */       this.scale = scaleValue.asInt();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final String getName()
/*  83:    */   {
/*  84:118 */     return this.name;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final boolean isUnique()
/*  88:    */   {
/*  89:122 */     return this.unique;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final boolean isNullable()
/*  93:    */   {
/*  94:126 */     return this.nullable;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final boolean isInsertable()
/*  98:    */   {
/*  99:130 */     return this.insertable;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final boolean isUpdatable()
/* 103:    */   {
/* 104:134 */     return this.updatable;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final String getColumnDefinition()
/* 108:    */   {
/* 109:138 */     return this.columnDefinition;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final String getTable()
/* 113:    */   {
/* 114:142 */     return this.table;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final int getLength()
/* 118:    */   {
/* 119:146 */     return this.length;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final int getPrecision()
/* 123:    */   {
/* 124:150 */     return this.precision;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final int getScale()
/* 128:    */   {
/* 129:154 */     return this.scale;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setName(String name)
/* 133:    */   {
/* 134:158 */     this.name = name;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setUnique(boolean unique)
/* 138:    */   {
/* 139:162 */     this.unique = unique;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setNullable(boolean nullable)
/* 143:    */   {
/* 144:166 */     this.nullable = nullable;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setInsertable(boolean insertable)
/* 148:    */   {
/* 149:170 */     this.insertable = insertable;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setUpdatable(boolean updatable)
/* 153:    */   {
/* 154:174 */     this.updatable = updatable;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setColumnDefinition(String columnDefinition)
/* 158:    */   {
/* 159:178 */     this.columnDefinition = columnDefinition;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setTable(String table)
/* 163:    */   {
/* 164:182 */     this.table = table;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setLength(int length)
/* 168:    */   {
/* 169:186 */     this.length = length;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setPrecision(int precision)
/* 173:    */   {
/* 174:190 */     this.precision = precision;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setScale(int scale)
/* 178:    */   {
/* 179:194 */     this.scale = scale;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String toString()
/* 183:    */   {
/* 184:199 */     StringBuilder sb = new StringBuilder();
/* 185:200 */     sb.append("ColumnValues");
/* 186:201 */     sb.append("{name='").append(this.name).append('\'');
/* 187:202 */     sb.append(", unique=").append(this.unique);
/* 188:203 */     sb.append(", nullable=").append(this.nullable);
/* 189:204 */     sb.append(", insertable=").append(this.insertable);
/* 190:205 */     sb.append(", updatable=").append(this.updatable);
/* 191:206 */     sb.append(", columnDefinition='").append(this.columnDefinition).append('\'');
/* 192:207 */     sb.append(", table='").append(this.table).append('\'');
/* 193:208 */     sb.append(", length=").append(this.length);
/* 194:209 */     sb.append(", precision=").append(this.precision);
/* 195:210 */     sb.append(", scale=").append(this.scale);
/* 196:211 */     sb.append('}');
/* 197:212 */     return sb.toString();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean equals(Object o)
/* 201:    */   {
/* 202:217 */     if (this == o) {
/* 203:218 */       return true;
/* 204:    */     }
/* 205:220 */     if ((o == null) || (getClass() != o.getClass())) {
/* 206:221 */       return false;
/* 207:    */     }
/* 208:224 */     ColumnValues that = (ColumnValues)o;
/* 209:226 */     if (this.insertable != that.insertable) {
/* 210:227 */       return false;
/* 211:    */     }
/* 212:229 */     if (this.length != that.length) {
/* 213:230 */       return false;
/* 214:    */     }
/* 215:232 */     if (this.nullable != that.nullable) {
/* 216:233 */       return false;
/* 217:    */     }
/* 218:235 */     if (this.precision != that.precision) {
/* 219:236 */       return false;
/* 220:    */     }
/* 221:238 */     if (this.scale != that.scale) {
/* 222:239 */       return false;
/* 223:    */     }
/* 224:241 */     if (this.unique != that.unique) {
/* 225:242 */       return false;
/* 226:    */     }
/* 227:244 */     if (this.updatable != that.updatable) {
/* 228:245 */       return false;
/* 229:    */     }
/* 230:247 */     if (this.columnDefinition != null ? !this.columnDefinition.equals(that.columnDefinition) : that.columnDefinition != null) {
/* 231:248 */       return false;
/* 232:    */     }
/* 233:250 */     if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
/* 234:251 */       return false;
/* 235:    */     }
/* 236:253 */     if (this.table != null ? !this.table.equals(that.table) : that.table != null) {
/* 237:254 */       return false;
/* 238:    */     }
/* 239:257 */     return true;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int hashCode()
/* 243:    */   {
/* 244:262 */     int result = this.name != null ? this.name.hashCode() : 0;
/* 245:263 */     result = 31 * result + (this.unique ? 1 : 0);
/* 246:264 */     result = 31 * result + (this.nullable ? 1 : 0);
/* 247:265 */     result = 31 * result + (this.insertable ? 1 : 0);
/* 248:266 */     result = 31 * result + (this.updatable ? 1 : 0);
/* 249:267 */     result = 31 * result + (this.columnDefinition != null ? this.columnDefinition.hashCode() : 0);
/* 250:268 */     result = 31 * result + (this.table != null ? this.table.hashCode() : 0);
/* 251:269 */     result = 31 * result + this.length;
/* 252:270 */     result = 31 * result + this.precision;
/* 253:271 */     result = 31 * result + this.scale;
/* 254:272 */     return result;
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.ColumnValues
 * JD-Core Version:    0.7.0.1
 */