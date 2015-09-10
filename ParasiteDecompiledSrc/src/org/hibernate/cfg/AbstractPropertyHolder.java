/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import javax.persistence.AssociationOverride;
/*   6:    */ import javax.persistence.AssociationOverrides;
/*   7:    */ import javax.persistence.AttributeOverride;
/*   8:    */ import javax.persistence.AttributeOverrides;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Embeddable;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.JoinTable;
/*  14:    */ import javax.persistence.MappedSuperclass;
/*  15:    */ import org.hibernate.AssertionFailure;
/*  16:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  17:    */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  18:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  19:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  20:    */ import org.hibernate.internal.util.StringHelper;
/*  21:    */ 
/*  22:    */ public abstract class AbstractPropertyHolder
/*  23:    */   implements PropertyHolder
/*  24:    */ {
/*  25:    */   protected AbstractPropertyHolder parent;
/*  26:    */   private Map<String, Column[]> holderColumnOverride;
/*  27:    */   private Map<String, Column[]> currentPropertyColumnOverride;
/*  28:    */   private Map<String, JoinColumn[]> holderJoinColumnOverride;
/*  29:    */   private Map<String, JoinColumn[]> currentPropertyJoinColumnOverride;
/*  30:    */   private Map<String, JoinTable> holderJoinTableOverride;
/*  31:    */   private Map<String, JoinTable> currentPropertyJoinTableOverride;
/*  32:    */   private String path;
/*  33:    */   private Mappings mappings;
/*  34:    */   private Boolean isInIdClass;
/*  35:    */   
/*  36:    */   public AbstractPropertyHolder(String path, PropertyHolder parent, XClass clazzToProcess, Mappings mappings)
/*  37:    */   {
/*  38: 65 */     this.path = path;
/*  39: 66 */     this.parent = ((AbstractPropertyHolder)parent);
/*  40: 67 */     this.mappings = mappings;
/*  41: 68 */     buildHierarchyColumnOverride(clazzToProcess);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isInIdClass()
/*  45:    */   {
/*  46: 73 */     return this.parent != null ? this.parent.isInIdClass() : this.isInIdClass != null ? this.isInIdClass.booleanValue() : false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setInIdClass(Boolean isInIdClass)
/*  50:    */   {
/*  51: 77 */     this.isInIdClass = isInIdClass;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getPath()
/*  55:    */   {
/*  56: 81 */     return this.path;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Mappings getMappings()
/*  60:    */   {
/*  61: 85 */     return this.mappings;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void setCurrentProperty(XProperty property)
/*  65:    */   {
/*  66: 92 */     if (property == null)
/*  67:    */     {
/*  68: 93 */       this.currentPropertyColumnOverride = null;
/*  69: 94 */       this.currentPropertyJoinColumnOverride = null;
/*  70: 95 */       this.currentPropertyJoinTableOverride = null;
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 98 */       this.currentPropertyColumnOverride = buildColumnOverride(property, getPath());
/*  75:102 */       if (this.currentPropertyColumnOverride.size() == 0) {
/*  76:103 */         this.currentPropertyColumnOverride = null;
/*  77:    */       }
/*  78:105 */       this.currentPropertyJoinColumnOverride = buildJoinColumnOverride(property, getPath());
/*  79:109 */       if (this.currentPropertyJoinColumnOverride.size() == 0) {
/*  80:110 */         this.currentPropertyJoinColumnOverride = null;
/*  81:    */       }
/*  82:112 */       this.currentPropertyJoinTableOverride = buildJoinTableOverride(property, getPath());
/*  83:116 */       if (this.currentPropertyJoinTableOverride.size() == 0) {
/*  84:117 */         this.currentPropertyJoinTableOverride = null;
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Column[] getOverriddenColumn(String propertyName)
/*  90:    */   {
/*  91:130 */     Column[] result = getExactOverriddenColumn(propertyName);
/*  92:131 */     if (result == null) {
/*  93:163 */       if ((result == null) && (propertyName.contains(".collection&&element."))) {
/*  94:166 */         result = getExactOverriddenColumn(propertyName.replace(".collection&&element.", "."));
/*  95:    */       }
/*  96:    */     }
/*  97:169 */     return result;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private Column[] getExactOverriddenColumn(String propertyName)
/* 101:    */   {
/* 102:177 */     Column[] override = null;
/* 103:178 */     if (this.parent != null) {
/* 104:179 */       override = this.parent.getExactOverriddenColumn(propertyName);
/* 105:    */     }
/* 106:181 */     if ((override == null) && (this.currentPropertyColumnOverride != null)) {
/* 107:182 */       override = (Column[])this.currentPropertyColumnOverride.get(propertyName);
/* 108:    */     }
/* 109:184 */     if ((override == null) && (this.holderColumnOverride != null)) {
/* 110:185 */       override = (Column[])this.holderColumnOverride.get(propertyName);
/* 111:    */     }
/* 112:187 */     return override;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public JoinColumn[] getOverriddenJoinColumn(String propertyName)
/* 116:    */   {
/* 117:198 */     JoinColumn[] result = getExactOverriddenJoinColumn(propertyName);
/* 118:199 */     if ((result == null) && (propertyName.contains(".collection&&element."))) {
/* 119:202 */       result = getExactOverriddenJoinColumn(propertyName.replace(".collection&&element.", "."));
/* 120:    */     }
/* 121:204 */     return result;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private JoinColumn[] getExactOverriddenJoinColumn(String propertyName)
/* 125:    */   {
/* 126:211 */     JoinColumn[] override = null;
/* 127:212 */     if (this.parent != null) {
/* 128:213 */       override = this.parent.getExactOverriddenJoinColumn(propertyName);
/* 129:    */     }
/* 130:215 */     if ((override == null) && (this.currentPropertyJoinColumnOverride != null)) {
/* 131:216 */       override = (JoinColumn[])this.currentPropertyJoinColumnOverride.get(propertyName);
/* 132:    */     }
/* 133:218 */     if ((override == null) && (this.holderJoinColumnOverride != null)) {
/* 134:219 */       override = (JoinColumn[])this.holderJoinColumnOverride.get(propertyName);
/* 135:    */     }
/* 136:221 */     return override;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public JoinTable getJoinTable(XProperty property)
/* 140:    */   {
/* 141:232 */     String propertyName = StringHelper.qualify(getPath(), property.getName());
/* 142:233 */     JoinTable result = getOverriddenJoinTable(propertyName);
/* 143:234 */     if (result == null) {
/* 144:235 */       result = (JoinTable)property.getAnnotation(JoinTable.class);
/* 145:    */     }
/* 146:237 */     return result;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public JoinTable getOverriddenJoinTable(String propertyName)
/* 150:    */   {
/* 151:248 */     JoinTable result = getExactOverriddenJoinTable(propertyName);
/* 152:249 */     if ((result == null) && (propertyName.contains(".collection&&element."))) {
/* 153:252 */       result = getExactOverriddenJoinTable(propertyName.replace(".collection&&element.", "."));
/* 154:    */     }
/* 155:254 */     return result;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private JoinTable getExactOverriddenJoinTable(String propertyName)
/* 159:    */   {
/* 160:261 */     JoinTable override = null;
/* 161:262 */     if (this.parent != null) {
/* 162:263 */       override = this.parent.getExactOverriddenJoinTable(propertyName);
/* 163:    */     }
/* 164:265 */     if ((override == null) && (this.currentPropertyJoinTableOverride != null)) {
/* 165:266 */       override = (JoinTable)this.currentPropertyJoinTableOverride.get(propertyName);
/* 166:    */     }
/* 167:268 */     if ((override == null) && (this.holderJoinTableOverride != null)) {
/* 168:269 */       override = (JoinTable)this.holderJoinTableOverride.get(propertyName);
/* 169:    */     }
/* 170:271 */     return override;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private void buildHierarchyColumnOverride(XClass element)
/* 174:    */   {
/* 175:275 */     XClass current = element;
/* 176:276 */     Map<String, Column[]> columnOverride = new HashMap();
/* 177:277 */     Map<String, JoinColumn[]> joinColumnOverride = new HashMap();
/* 178:278 */     Map<String, JoinTable> joinTableOverride = new HashMap();
/* 179:279 */     while ((current != null) && (!this.mappings.getReflectionManager().toXClass(Object.class).equals(current)))
/* 180:    */     {
/* 181:280 */       if ((current.isAnnotationPresent(Entity.class)) || (current.isAnnotationPresent(MappedSuperclass.class)) || (current.isAnnotationPresent(Embeddable.class)))
/* 182:    */       {
/* 183:283 */         Map<String, Column[]> currentOverride = buildColumnOverride(current, getPath());
/* 184:284 */         Map<String, JoinColumn[]> currentJoinOverride = buildJoinColumnOverride(current, getPath());
/* 185:285 */         Map<String, JoinTable> currentJoinTableOverride = buildJoinTableOverride(current, getPath());
/* 186:286 */         currentOverride.putAll(columnOverride);
/* 187:287 */         currentJoinOverride.putAll(joinColumnOverride);
/* 188:288 */         currentJoinOverride.putAll(joinColumnOverride);
/* 189:289 */         columnOverride = currentOverride;
/* 190:290 */         joinColumnOverride = currentJoinOverride;
/* 191:291 */         joinTableOverride = currentJoinTableOverride;
/* 192:    */       }
/* 193:293 */       current = current.getSuperclass();
/* 194:    */     }
/* 195:296 */     this.holderColumnOverride = (columnOverride.size() > 0 ? columnOverride : null);
/* 196:297 */     this.holderJoinColumnOverride = (joinColumnOverride.size() > 0 ? joinColumnOverride : null);
/* 197:298 */     this.holderJoinTableOverride = (joinTableOverride.size() > 0 ? joinTableOverride : null);
/* 198:    */   }
/* 199:    */   
/* 200:    */   private static Map<String, Column[]> buildColumnOverride(XAnnotatedElement element, String path)
/* 201:    */   {
/* 202:302 */     Map<String, Column[]> columnOverride = new HashMap();
/* 203:303 */     if (element == null) {
/* 204:303 */       return columnOverride;
/* 205:    */     }
/* 206:304 */     AttributeOverride singleOverride = (AttributeOverride)element.getAnnotation(AttributeOverride.class);
/* 207:305 */     AttributeOverrides multipleOverrides = (AttributeOverrides)element.getAnnotation(AttributeOverrides.class);
/* 208:    */     AttributeOverride[] overrides;
/* 209:    */     AttributeOverride[] overrides;
/* 210:307 */     if (singleOverride != null)
/* 211:    */     {
/* 212:308 */       overrides = new AttributeOverride[] { singleOverride };
/* 213:    */     }
/* 214:    */     else
/* 215:    */     {
/* 216:    */       AttributeOverride[] overrides;
/* 217:310 */       if (multipleOverrides != null) {
/* 218:311 */         overrides = multipleOverrides.value();
/* 219:    */       } else {
/* 220:314 */         overrides = null;
/* 221:    */       }
/* 222:    */     }
/* 223:318 */     if (overrides != null) {
/* 224:319 */       for (AttributeOverride depAttr : overrides) {
/* 225:320 */         columnOverride.put(StringHelper.qualify(path, depAttr.name()), new Column[] { depAttr.column() });
/* 226:    */       }
/* 227:    */     }
/* 228:326 */     return columnOverride;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private static Map<String, JoinColumn[]> buildJoinColumnOverride(XAnnotatedElement element, String path)
/* 232:    */   {
/* 233:330 */     Map<String, JoinColumn[]> columnOverride = new HashMap();
/* 234:331 */     if (element == null) {
/* 235:331 */       return columnOverride;
/* 236:    */     }
/* 237:332 */     AssociationOverride singleOverride = (AssociationOverride)element.getAnnotation(AssociationOverride.class);
/* 238:333 */     AssociationOverrides multipleOverrides = (AssociationOverrides)element.getAnnotation(AssociationOverrides.class);
/* 239:    */     AssociationOverride[] overrides;
/* 240:    */     AssociationOverride[] overrides;
/* 241:335 */     if (singleOverride != null)
/* 242:    */     {
/* 243:336 */       overrides = new AssociationOverride[] { singleOverride };
/* 244:    */     }
/* 245:    */     else
/* 246:    */     {
/* 247:    */       AssociationOverride[] overrides;
/* 248:338 */       if (multipleOverrides != null) {
/* 249:339 */         overrides = multipleOverrides.value();
/* 250:    */       } else {
/* 251:342 */         overrides = null;
/* 252:    */       }
/* 253:    */     }
/* 254:346 */     if (overrides != null) {
/* 255:347 */       for (AssociationOverride depAttr : overrides) {
/* 256:348 */         columnOverride.put(StringHelper.qualify(path, depAttr.name()), depAttr.joinColumns());
/* 257:    */       }
/* 258:    */     }
/* 259:354 */     return columnOverride;
/* 260:    */   }
/* 261:    */   
/* 262:    */   private static Map<String, JoinTable> buildJoinTableOverride(XAnnotatedElement element, String path)
/* 263:    */   {
/* 264:358 */     Map<String, JoinTable> tableOverride = new HashMap();
/* 265:359 */     if (element == null) {
/* 266:359 */       return tableOverride;
/* 267:    */     }
/* 268:360 */     AssociationOverride singleOverride = (AssociationOverride)element.getAnnotation(AssociationOverride.class);
/* 269:361 */     AssociationOverrides multipleOverrides = (AssociationOverrides)element.getAnnotation(AssociationOverrides.class);
/* 270:    */     AssociationOverride[] overrides;
/* 271:    */     AssociationOverride[] overrides;
/* 272:363 */     if (singleOverride != null)
/* 273:    */     {
/* 274:364 */       overrides = new AssociationOverride[] { singleOverride };
/* 275:    */     }
/* 276:    */     else
/* 277:    */     {
/* 278:    */       AssociationOverride[] overrides;
/* 279:366 */       if (multipleOverrides != null) {
/* 280:367 */         overrides = multipleOverrides.value();
/* 281:    */       } else {
/* 282:370 */         overrides = null;
/* 283:    */       }
/* 284:    */     }
/* 285:374 */     if (overrides != null) {
/* 286:375 */       for (AssociationOverride depAttr : overrides) {
/* 287:376 */         if (depAttr.joinColumns().length == 0) {
/* 288:377 */           tableOverride.put(StringHelper.qualify(path, depAttr.name()), depAttr.joinTable());
/* 289:    */         }
/* 290:    */       }
/* 291:    */     }
/* 292:384 */     return tableOverride;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setParentProperty(String parentProperty)
/* 296:    */   {
/* 297:388 */     throw new AssertionFailure("Setting the parent property to a non component");
/* 298:    */   }
/* 299:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.AbstractPropertyHolder
 * JD-Core Version:    0.7.0.1
 */