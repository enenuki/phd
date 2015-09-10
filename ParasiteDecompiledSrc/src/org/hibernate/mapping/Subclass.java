/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.EntityMode;
/*  11:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  12:    */ import org.hibernate.internal.util.collections.SingletonIterator;
/*  13:    */ 
/*  14:    */ public class Subclass
/*  15:    */   extends PersistentClass
/*  16:    */ {
/*  17:    */   private PersistentClass superclass;
/*  18:    */   private Class classPersisterClass;
/*  19:    */   private final int subclassId;
/*  20:    */   
/*  21:    */   public Subclass(PersistentClass superclass)
/*  22:    */   {
/*  23: 46 */     this.superclass = superclass;
/*  24: 47 */     this.subclassId = superclass.nextSubclassId();
/*  25:    */   }
/*  26:    */   
/*  27:    */   int nextSubclassId()
/*  28:    */   {
/*  29: 51 */     return getSuperclass().nextSubclassId();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getSubclassId()
/*  33:    */   {
/*  34: 55 */     return this.subclassId;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getCacheConcurrencyStrategy()
/*  38:    */   {
/*  39: 59 */     return getSuperclass().getCacheConcurrencyStrategy();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public RootClass getRootClass()
/*  43:    */   {
/*  44: 63 */     return getSuperclass().getRootClass();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public PersistentClass getSuperclass()
/*  48:    */   {
/*  49: 67 */     return this.superclass;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Property getIdentifierProperty()
/*  53:    */   {
/*  54: 71 */     return getSuperclass().getIdentifierProperty();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Property getDeclaredIdentifierProperty()
/*  58:    */   {
/*  59: 75 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public KeyValue getIdentifier()
/*  63:    */   {
/*  64: 79 */     return getSuperclass().getIdentifier();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean hasIdentifierProperty()
/*  68:    */   {
/*  69: 82 */     return getSuperclass().hasIdentifierProperty();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Value getDiscriminator()
/*  73:    */   {
/*  74: 85 */     return getSuperclass().getDiscriminator();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isMutable()
/*  78:    */   {
/*  79: 88 */     return getSuperclass().isMutable();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isInherited()
/*  83:    */   {
/*  84: 91 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isPolymorphic()
/*  88:    */   {
/*  89: 94 */     return true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void addProperty(Property p)
/*  93:    */   {
/*  94: 98 */     super.addProperty(p);
/*  95: 99 */     getSuperclass().addSubclassProperty(p);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void addMappedsuperclassProperty(Property p)
/*  99:    */   {
/* 100:103 */     super.addMappedsuperclassProperty(p);
/* 101:104 */     getSuperclass().addSubclassProperty(p);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void addJoin(Join j)
/* 105:    */   {
/* 106:108 */     super.addJoin(j);
/* 107:109 */     getSuperclass().addSubclassJoin(j);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Iterator getPropertyClosureIterator()
/* 111:    */   {
/* 112:113 */     return new JoinedIterator(getSuperclass().getPropertyClosureIterator(), getPropertyIterator());
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Iterator getTableClosureIterator()
/* 116:    */   {
/* 117:119 */     return new JoinedIterator(getSuperclass().getTableClosureIterator(), new SingletonIterator(getTable()));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Iterator getKeyClosureIterator()
/* 121:    */   {
/* 122:125 */     return new JoinedIterator(getSuperclass().getKeyClosureIterator(), new SingletonIterator(getKey()));
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void addSubclassProperty(Property p)
/* 126:    */   {
/* 127:131 */     super.addSubclassProperty(p);
/* 128:132 */     getSuperclass().addSubclassProperty(p);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void addSubclassJoin(Join j)
/* 132:    */   {
/* 133:135 */     super.addSubclassJoin(j);
/* 134:136 */     getSuperclass().addSubclassJoin(j);
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected void addSubclassTable(Table table)
/* 138:    */   {
/* 139:140 */     super.addSubclassTable(table);
/* 140:141 */     getSuperclass().addSubclassTable(table);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isVersioned()
/* 144:    */   {
/* 145:145 */     return getSuperclass().isVersioned();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Property getVersion()
/* 149:    */   {
/* 150:148 */     return getSuperclass().getVersion();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Property getDeclaredVersion()
/* 154:    */   {
/* 155:152 */     return null;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean hasEmbeddedIdentifier()
/* 159:    */   {
/* 160:156 */     return getSuperclass().hasEmbeddedIdentifier();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Class getEntityPersisterClass()
/* 164:    */   {
/* 165:159 */     if (this.classPersisterClass == null) {
/* 166:160 */       return getSuperclass().getEntityPersisterClass();
/* 167:    */     }
/* 168:163 */     return this.classPersisterClass;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Table getRootTable()
/* 172:    */   {
/* 173:168 */     return getSuperclass().getRootTable();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public KeyValue getKey()
/* 177:    */   {
/* 178:172 */     return getSuperclass().getIdentifier();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isExplicitPolymorphism()
/* 182:    */   {
/* 183:176 */     return getSuperclass().isExplicitPolymorphism();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setSuperclass(PersistentClass superclass)
/* 187:    */   {
/* 188:180 */     this.superclass = superclass;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getWhere()
/* 192:    */   {
/* 193:184 */     return getSuperclass().getWhere();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isJoinedSubclass()
/* 197:    */   {
/* 198:188 */     return getTable() != getRootTable();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void createForeignKey()
/* 202:    */   {
/* 203:192 */     if (!isJoinedSubclass()) {
/* 204:193 */       throw new AssertionFailure("not a joined-subclass");
/* 205:    */     }
/* 206:195 */     getKey().createForeignKeyOfEntity(getSuperclass().getEntityName());
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setEntityPersisterClass(Class classPersisterClass)
/* 210:    */   {
/* 211:199 */     this.classPersisterClass = classPersisterClass;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean isLazyPropertiesCacheable()
/* 215:    */   {
/* 216:203 */     return getSuperclass().isLazyPropertiesCacheable();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getJoinClosureSpan()
/* 220:    */   {
/* 221:207 */     return getSuperclass().getJoinClosureSpan() + super.getJoinClosureSpan();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int getPropertyClosureSpan()
/* 225:    */   {
/* 226:211 */     return getSuperclass().getPropertyClosureSpan() + super.getPropertyClosureSpan();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Iterator getJoinClosureIterator()
/* 230:    */   {
/* 231:215 */     return new JoinedIterator(getSuperclass().getJoinClosureIterator(), super.getJoinClosureIterator());
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean isClassOrSuperclassJoin(Join join)
/* 235:    */   {
/* 236:222 */     return (super.isClassOrSuperclassJoin(join)) || (getSuperclass().isClassOrSuperclassJoin(join));
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean isClassOrSuperclassTable(Table table)
/* 240:    */   {
/* 241:226 */     return (super.isClassOrSuperclassTable(table)) || (getSuperclass().isClassOrSuperclassTable(table));
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Table getTable()
/* 245:    */   {
/* 246:230 */     return getSuperclass().getTable();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean isForceDiscriminator()
/* 250:    */   {
/* 251:234 */     return getSuperclass().isForceDiscriminator();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean isDiscriminatorInsertable()
/* 255:    */   {
/* 256:238 */     return getSuperclass().isDiscriminatorInsertable();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Set getSynchronizedTables()
/* 260:    */   {
/* 261:242 */     HashSet result = new HashSet();
/* 262:243 */     result.addAll(this.synchronizedTables);
/* 263:244 */     result.addAll(getSuperclass().getSynchronizedTables());
/* 264:245 */     return result;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Object accept(PersistentClassVisitor mv)
/* 268:    */   {
/* 269:249 */     return mv.accept(this);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Map getFilterMap()
/* 273:    */   {
/* 274:253 */     return getSuperclass().getFilterMap();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean hasSubselectLoadableCollections()
/* 278:    */   {
/* 279:257 */     return (super.hasSubselectLoadableCollections()) || (getSuperclass().hasSubselectLoadableCollections());
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getTuplizerImplClassName(EntityMode mode)
/* 283:    */   {
/* 284:262 */     String impl = super.getTuplizerImplClassName(mode);
/* 285:263 */     if (impl == null) {
/* 286:264 */       impl = getSuperclass().getTuplizerImplClassName(mode);
/* 287:    */     }
/* 288:266 */     return impl;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Map getTuplizerMap()
/* 292:    */   {
/* 293:270 */     Map specificTuplizerDefs = super.getTuplizerMap();
/* 294:271 */     Map superclassTuplizerDefs = getSuperclass().getTuplizerMap();
/* 295:272 */     if ((specificTuplizerDefs == null) && (superclassTuplizerDefs == null)) {
/* 296:273 */       return null;
/* 297:    */     }
/* 298:276 */     Map combined = new HashMap();
/* 299:277 */     if (superclassTuplizerDefs != null) {
/* 300:278 */       combined.putAll(superclassTuplizerDefs);
/* 301:    */     }
/* 302:280 */     if (specificTuplizerDefs != null) {
/* 303:281 */       combined.putAll(specificTuplizerDefs);
/* 304:    */     }
/* 305:283 */     return Collections.unmodifiableMap(combined);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Component getIdentifierMapper()
/* 309:    */   {
/* 310:288 */     return this.superclass.getIdentifierMapper();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int getOptimisticLockMode()
/* 314:    */   {
/* 315:292 */     return this.superclass.getOptimisticLockMode();
/* 316:    */   }
/* 317:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Subclass
 * JD-Core Version:    0.7.0.1
 */