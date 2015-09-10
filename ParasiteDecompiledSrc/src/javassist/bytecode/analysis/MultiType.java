/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import javassist.CtClass;
/*   9:    */ 
/*  10:    */ public class MultiType
/*  11:    */   extends Type
/*  12:    */ {
/*  13:    */   private Map interfaces;
/*  14:    */   private Type resolved;
/*  15:    */   private Type potentialClass;
/*  16:    */   private MultiType mergeSource;
/*  17: 53 */   private boolean changed = false;
/*  18:    */   
/*  19:    */   public MultiType(Map interfaces)
/*  20:    */   {
/*  21: 56 */     this(interfaces, null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public MultiType(Map interfaces, Type potentialClass)
/*  25:    */   {
/*  26: 60 */     super(null);
/*  27: 61 */     this.interfaces = interfaces;
/*  28: 62 */     this.potentialClass = potentialClass;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CtClass getCtClass()
/*  32:    */   {
/*  33: 70 */     if (this.resolved != null) {
/*  34: 71 */       return this.resolved.getCtClass();
/*  35:    */     }
/*  36: 73 */     return Type.OBJECT.getCtClass();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Type getComponent()
/*  40:    */   {
/*  41: 80 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getSize()
/*  45:    */   {
/*  46: 87 */     return 1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isArray()
/*  50:    */   {
/*  51: 94 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   boolean popChanged()
/*  55:    */   {
/*  56:101 */     boolean changed = this.changed;
/*  57:102 */     this.changed = false;
/*  58:103 */     return changed;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isAssignableFrom(Type type)
/*  62:    */   {
/*  63:107 */     throw new UnsupportedOperationException("Not implemented");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isAssignableTo(Type type)
/*  67:    */   {
/*  68:111 */     if (this.resolved != null) {
/*  69:112 */       return type.isAssignableFrom(this.resolved);
/*  70:    */     }
/*  71:114 */     if (Type.OBJECT.equals(type)) {
/*  72:115 */       return true;
/*  73:    */     }
/*  74:117 */     if ((this.potentialClass != null) && (!type.isAssignableFrom(this.potentialClass))) {
/*  75:118 */       this.potentialClass = null;
/*  76:    */     }
/*  77:120 */     Map map = mergeMultiAndSingle(this, type);
/*  78:122 */     if ((map.size() == 1) && (this.potentialClass == null))
/*  79:    */     {
/*  80:124 */       this.resolved = Type.get((CtClass)map.values().iterator().next());
/*  81:125 */       propogateResolved();
/*  82:    */       
/*  83:127 */       return true;
/*  84:    */     }
/*  85:131 */     if (map.size() >= 1)
/*  86:    */     {
/*  87:132 */       this.interfaces = map;
/*  88:133 */       propogateState();
/*  89:    */       
/*  90:135 */       return true;
/*  91:    */     }
/*  92:138 */     if (this.potentialClass != null)
/*  93:    */     {
/*  94:139 */       this.resolved = this.potentialClass;
/*  95:140 */       propogateResolved();
/*  96:    */       
/*  97:142 */       return true;
/*  98:    */     }
/*  99:145 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void propogateState()
/* 103:    */   {
/* 104:149 */     MultiType source = this.mergeSource;
/* 105:150 */     while (source != null)
/* 106:    */     {
/* 107:151 */       source.interfaces = this.interfaces;
/* 108:152 */       source.potentialClass = this.potentialClass;
/* 109:153 */       source = source.mergeSource;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void propogateResolved()
/* 114:    */   {
/* 115:158 */     MultiType source = this.mergeSource;
/* 116:159 */     while (source != null)
/* 117:    */     {
/* 118:160 */       source.resolved = this.resolved;
/* 119:161 */       source = source.mergeSource;
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isReference()
/* 124:    */   {
/* 125:171 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private Map getAllMultiInterfaces(MultiType type)
/* 129:    */   {
/* 130:175 */     Map map = new HashMap();
/* 131:    */     
/* 132:177 */     Iterator iter = type.interfaces.values().iterator();
/* 133:178 */     while (iter.hasNext())
/* 134:    */     {
/* 135:179 */       CtClass intf = (CtClass)iter.next();
/* 136:180 */       map.put(intf.getName(), intf);
/* 137:181 */       getAllInterfaces(intf, map);
/* 138:    */     }
/* 139:184 */     return map;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private Map mergeMultiInterfaces(MultiType type1, MultiType type2)
/* 143:    */   {
/* 144:189 */     Map map1 = getAllMultiInterfaces(type1);
/* 145:190 */     Map map2 = getAllMultiInterfaces(type2);
/* 146:    */     
/* 147:192 */     return findCommonInterfaces(map1, map2);
/* 148:    */   }
/* 149:    */   
/* 150:    */   private Map mergeMultiAndSingle(MultiType multi, Type single)
/* 151:    */   {
/* 152:196 */     Map map1 = getAllMultiInterfaces(multi);
/* 153:197 */     Map map2 = getAllInterfaces(single.getCtClass(), null);
/* 154:    */     
/* 155:199 */     return findCommonInterfaces(map1, map2);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private boolean inMergeSource(MultiType source)
/* 159:    */   {
/* 160:203 */     while (source != null)
/* 161:    */     {
/* 162:204 */       if (source == this) {
/* 163:205 */         return true;
/* 164:    */       }
/* 165:207 */       source = source.mergeSource;
/* 166:    */     }
/* 167:210 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Type merge(Type type)
/* 171:    */   {
/* 172:214 */     if (this == type) {
/* 173:215 */       return this;
/* 174:    */     }
/* 175:217 */     if (type == UNINIT) {
/* 176:218 */       return this;
/* 177:    */     }
/* 178:220 */     if (type == BOGUS) {
/* 179:221 */       return BOGUS;
/* 180:    */     }
/* 181:223 */     if (type == null) {
/* 182:224 */       return this;
/* 183:    */     }
/* 184:226 */     if (this.resolved != null) {
/* 185:227 */       return this.resolved.merge(type);
/* 186:    */     }
/* 187:229 */     if (this.potentialClass != null)
/* 188:    */     {
/* 189:230 */       Type mergePotential = this.potentialClass.merge(type);
/* 190:231 */       if ((!mergePotential.equals(this.potentialClass)) || (mergePotential.popChanged()))
/* 191:    */       {
/* 192:232 */         this.potentialClass = (Type.OBJECT.equals(mergePotential) ? null : mergePotential);
/* 193:233 */         this.changed = true;
/* 194:    */       }
/* 195:    */     }
/* 196:    */     Map merged;
/* 197:239 */     if ((type instanceof MultiType))
/* 198:    */     {
/* 199:240 */       MultiType multi = (MultiType)type;
/* 200:    */       Map merged;
/* 201:242 */       if (multi.resolved != null)
/* 202:    */       {
/* 203:243 */         merged = mergeMultiAndSingle(this, multi.resolved);
/* 204:    */       }
/* 205:    */       else
/* 206:    */       {
/* 207:245 */         Map merged = mergeMultiInterfaces(multi, this);
/* 208:246 */         if (!inMergeSource(multi)) {
/* 209:247 */           this.mergeSource = multi;
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:250 */       merged = mergeMultiAndSingle(this, type);
/* 216:    */     }
/* 217:254 */     if ((merged.size() > 1) || ((merged.size() == 1) && (this.potentialClass != null)))
/* 218:    */     {
/* 219:256 */       if (merged.size() != this.interfaces.size())
/* 220:    */       {
/* 221:257 */         this.changed = true;
/* 222:    */       }
/* 223:258 */       else if (!this.changed)
/* 224:    */       {
/* 225:259 */         Iterator iter = merged.keySet().iterator();
/* 226:260 */         while (iter.hasNext()) {
/* 227:261 */           if (!this.interfaces.containsKey(iter.next())) {
/* 228:262 */             this.changed = true;
/* 229:    */           }
/* 230:    */         }
/* 231:    */       }
/* 232:265 */       this.interfaces = merged;
/* 233:266 */       propogateState();
/* 234:    */       
/* 235:268 */       return this;
/* 236:    */     }
/* 237:271 */     if (merged.size() == 1) {
/* 238:272 */       this.resolved = Type.get((CtClass)merged.values().iterator().next());
/* 239:273 */     } else if (this.potentialClass != null) {
/* 240:274 */       this.resolved = this.potentialClass;
/* 241:    */     } else {
/* 242:276 */       this.resolved = OBJECT;
/* 243:    */     }
/* 244:279 */     propogateResolved();
/* 245:    */     
/* 246:281 */     return this.resolved;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean equals(Object o)
/* 250:    */   {
/* 251:285 */     if (!(o instanceof MultiType)) {
/* 252:286 */       return false;
/* 253:    */     }
/* 254:288 */     MultiType multi = (MultiType)o;
/* 255:289 */     if (this.resolved != null) {
/* 256:290 */       return this.resolved.equals(multi.resolved);
/* 257:    */     }
/* 258:291 */     if (multi.resolved != null) {
/* 259:292 */       return false;
/* 260:    */     }
/* 261:294 */     return this.interfaces.keySet().equals(multi.interfaces.keySet());
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String toString()
/* 265:    */   {
/* 266:298 */     if (this.resolved != null) {
/* 267:299 */       return this.resolved.toString();
/* 268:    */     }
/* 269:301 */     StringBuffer buffer = new StringBuffer("{");
/* 270:302 */     Iterator iter = this.interfaces.keySet().iterator();
/* 271:303 */     while (iter.hasNext())
/* 272:    */     {
/* 273:304 */       buffer.append(iter.next());
/* 274:305 */       buffer.append(", ");
/* 275:    */     }
/* 276:307 */     buffer.setLength(buffer.length() - 2);
/* 277:308 */     if (this.potentialClass != null) {
/* 278:309 */       buffer.append(", *").append(this.potentialClass.toString());
/* 279:    */     }
/* 280:310 */     buffer.append("}");
/* 281:311 */     return buffer.toString();
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.MultiType
 * JD-Core Version:    0.7.0.1
 */