/*   1:    */ package org.hibernate.metamodel.domain;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.LinkedHashSet;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.cfg.NotYetImplementedException;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ import org.hibernate.internal.util.Value;
/*  10:    */ 
/*  11:    */ public abstract class AbstractAttributeContainer
/*  12:    */   implements AttributeContainer, Hierarchical
/*  13:    */ {
/*  14:    */   private final String name;
/*  15:    */   private final String className;
/*  16:    */   private final Value<Class<?>> classReference;
/*  17:    */   private final Hierarchical superType;
/*  18: 47 */   private LinkedHashSet<Attribute> attributeSet = new LinkedHashSet();
/*  19: 48 */   private HashMap<String, Attribute> attributeMap = new HashMap();
/*  20:    */   
/*  21:    */   public AbstractAttributeContainer(String name, String className, Value<Class<?>> classReference, Hierarchical superType)
/*  22:    */   {
/*  23: 51 */     this.name = name;
/*  24: 52 */     this.className = className;
/*  25: 53 */     this.classReference = classReference;
/*  26: 54 */     this.superType = superType;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getName()
/*  30:    */   {
/*  31: 59 */     return this.name;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getClassName()
/*  35:    */   {
/*  36: 64 */     return this.className;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Class<?> getClassReference()
/*  40:    */   {
/*  41: 69 */     return (Class)this.classReference.getValue();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Value<Class<?>> getClassReferenceUnresolved()
/*  45:    */   {
/*  46: 74 */     return this.classReference;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Hierarchical getSuperType()
/*  50:    */   {
/*  51: 79 */     return this.superType;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Set<Attribute> attributes()
/*  55:    */   {
/*  56: 84 */     return Collections.unmodifiableSet(this.attributeSet);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getRoleBaseName()
/*  60:    */   {
/*  61: 89 */     return getClassName();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Attribute locateAttribute(String name)
/*  65:    */   {
/*  66: 94 */     return (Attribute)this.attributeMap.get(name);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SingularAttribute locateSingularAttribute(String name)
/*  70:    */   {
/*  71: 99 */     return (SingularAttribute)locateAttribute(name);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public SingularAttribute createSingularAttribute(String name)
/*  75:    */   {
/*  76:104 */     SingularAttribute attribute = new SingularAttributeImpl(name, this);
/*  77:105 */     addAttribute(attribute);
/*  78:106 */     return attribute;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SingularAttribute createVirtualSingularAttribute(String name)
/*  82:    */   {
/*  83:111 */     throw new NotYetImplementedException();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public SingularAttribute locateComponentAttribute(String name)
/*  87:    */   {
/*  88:116 */     return (SingularAttributeImpl)locateAttribute(name);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public SingularAttribute createComponentAttribute(String name, Component component)
/*  92:    */   {
/*  93:121 */     SingularAttributeImpl attribute = new SingularAttributeImpl(name, this);
/*  94:122 */     attribute.resolveType(component);
/*  95:123 */     addAttribute(attribute);
/*  96:124 */     return attribute;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public PluralAttribute locatePluralAttribute(String name)
/* 100:    */   {
/* 101:129 */     return (PluralAttribute)locateAttribute(name);
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected PluralAttribute createPluralAttribute(String name, PluralAttributeNature nature)
/* 105:    */   {
/* 106:133 */     PluralAttribute attribute = nature.isIndexed() ? new IndexedPluralAttributeImpl(name, nature, this) : new PluralAttributeImpl(name, nature, this);
/* 107:    */     
/* 108:    */ 
/* 109:136 */     addAttribute(attribute);
/* 110:137 */     return attribute;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public PluralAttribute locateBag(String name)
/* 114:    */   {
/* 115:142 */     return locatePluralAttribute(name);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PluralAttribute createBag(String name)
/* 119:    */   {
/* 120:147 */     return createPluralAttribute(name, PluralAttributeNature.BAG);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public PluralAttribute locateSet(String name)
/* 124:    */   {
/* 125:152 */     return locatePluralAttribute(name);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public PluralAttribute createSet(String name)
/* 129:    */   {
/* 130:157 */     return createPluralAttribute(name, PluralAttributeNature.SET);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public IndexedPluralAttribute locateList(String name)
/* 134:    */   {
/* 135:162 */     return (IndexedPluralAttribute)locatePluralAttribute(name);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public IndexedPluralAttribute createList(String name)
/* 139:    */   {
/* 140:167 */     return (IndexedPluralAttribute)createPluralAttribute(name, PluralAttributeNature.LIST);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public IndexedPluralAttribute locateMap(String name)
/* 144:    */   {
/* 145:172 */     return (IndexedPluralAttribute)locatePluralAttribute(name);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public IndexedPluralAttribute createMap(String name)
/* 149:    */   {
/* 150:177 */     return (IndexedPluralAttribute)createPluralAttribute(name, PluralAttributeNature.MAP);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String toString()
/* 154:    */   {
/* 155:182 */     StringBuilder sb = new StringBuilder();
/* 156:183 */     sb.append("AbstractAttributeContainer");
/* 157:184 */     sb.append("{name='").append(this.name).append('\'');
/* 158:185 */     sb.append(", superType=").append(this.superType);
/* 159:186 */     sb.append('}');
/* 160:187 */     return sb.toString();
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected void addAttribute(Attribute attribute)
/* 164:    */   {
/* 165:192 */     if (this.attributeMap.put(attribute.getName(), attribute) != null) {
/* 166:193 */       throw new IllegalArgumentException("Attribute with name [" + attribute.getName() + "] already registered");
/* 167:    */     }
/* 168:195 */     this.attributeSet.add(attribute);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static class SingularAttributeImpl
/* 172:    */     implements SingularAttribute
/* 173:    */   {
/* 174:    */     private final AttributeContainer attributeContainer;
/* 175:    */     private final String name;
/* 176:    */     private Type type;
/* 177:    */     
/* 178:    */     public SingularAttributeImpl(String name, AttributeContainer attributeContainer)
/* 179:    */     {
/* 180:206 */       this.name = name;
/* 181:207 */       this.attributeContainer = attributeContainer;
/* 182:    */     }
/* 183:    */     
/* 184:    */     public boolean isTypeResolved()
/* 185:    */     {
/* 186:211 */       return this.type != null;
/* 187:    */     }
/* 188:    */     
/* 189:    */     public void resolveType(Type type)
/* 190:    */     {
/* 191:215 */       if (type == null) {
/* 192:216 */         throw new IllegalArgumentException("Attempt to resolve with null type");
/* 193:    */       }
/* 194:218 */       this.type = type;
/* 195:    */     }
/* 196:    */     
/* 197:    */     public Type getSingularAttributeType()
/* 198:    */     {
/* 199:223 */       return this.type;
/* 200:    */     }
/* 201:    */     
/* 202:    */     public String getName()
/* 203:    */     {
/* 204:228 */       return this.name;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public AttributeContainer getAttributeContainer()
/* 208:    */     {
/* 209:233 */       return this.attributeContainer;
/* 210:    */     }
/* 211:    */     
/* 212:    */     public boolean isSingular()
/* 213:    */     {
/* 214:238 */       return true;
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static class PluralAttributeImpl
/* 219:    */     implements PluralAttribute
/* 220:    */   {
/* 221:    */     private final AttributeContainer attributeContainer;
/* 222:    */     private final PluralAttributeNature nature;
/* 223:    */     private final String name;
/* 224:    */     private Type elementType;
/* 225:    */     
/* 226:    */     public PluralAttributeImpl(String name, PluralAttributeNature nature, AttributeContainer attributeContainer)
/* 227:    */     {
/* 228:250 */       this.name = name;
/* 229:251 */       this.nature = nature;
/* 230:252 */       this.attributeContainer = attributeContainer;
/* 231:    */     }
/* 232:    */     
/* 233:    */     public AttributeContainer getAttributeContainer()
/* 234:    */     {
/* 235:257 */       return this.attributeContainer;
/* 236:    */     }
/* 237:    */     
/* 238:    */     public boolean isSingular()
/* 239:    */     {
/* 240:262 */       return false;
/* 241:    */     }
/* 242:    */     
/* 243:    */     public PluralAttributeNature getNature()
/* 244:    */     {
/* 245:267 */       return this.nature;
/* 246:    */     }
/* 247:    */     
/* 248:    */     public String getName()
/* 249:    */     {
/* 250:272 */       return this.name;
/* 251:    */     }
/* 252:    */     
/* 253:    */     public String getRole()
/* 254:    */     {
/* 255:277 */       return StringHelper.qualify(this.attributeContainer.getRoleBaseName(), this.name);
/* 256:    */     }
/* 257:    */     
/* 258:    */     public Type getElementType()
/* 259:    */     {
/* 260:282 */       return this.elementType;
/* 261:    */     }
/* 262:    */     
/* 263:    */     public void setElementType(Type elementType)
/* 264:    */     {
/* 265:287 */       this.elementType = elementType;
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public static class IndexedPluralAttributeImpl
/* 270:    */     extends AbstractAttributeContainer.PluralAttributeImpl
/* 271:    */     implements IndexedPluralAttribute
/* 272:    */   {
/* 273:    */     private Type indexType;
/* 274:    */     
/* 275:    */     public IndexedPluralAttributeImpl(String name, PluralAttributeNature nature, AttributeContainer attributeContainer)
/* 276:    */     {
/* 277:295 */       super(nature, attributeContainer);
/* 278:    */     }
/* 279:    */     
/* 280:    */     public Type getIndexType()
/* 281:    */     {
/* 282:300 */       return this.indexType;
/* 283:    */     }
/* 284:    */     
/* 285:    */     public void setIndexType(Type indexType)
/* 286:    */     {
/* 287:305 */       this.indexType = indexType;
/* 288:    */     }
/* 289:    */   }
/* 290:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.AbstractAttributeContainer
 * JD-Core Version:    0.7.0.1
 */