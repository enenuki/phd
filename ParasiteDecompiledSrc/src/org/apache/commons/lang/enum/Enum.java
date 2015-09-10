/*   1:    */ package org.apache.commons.lang.enum;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.WeakHashMap;
/*  13:    */ import org.apache.commons.lang.ClassUtils;
/*  14:    */ import org.apache.commons.lang.StringUtils;
/*  15:    */ 
/*  16:    */ /**
/*  17:    */  * @deprecated
/*  18:    */  */
/*  19:    */ public abstract class Enum
/*  20:    */   implements Comparable, Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = -487045951170455942L;
/*  23:254 */   private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
/*  24:259 */   private static Map cEnumClasses = new WeakHashMap();
/*  25:    */   private final String iName;
/*  26:    */   private final transient int iHashCode;
/*  27:279 */   protected transient String iToString = null;
/*  28:    */   
/*  29:    */   private static class Entry
/*  30:    */   {
/*  31:288 */     final Map map = new HashMap();
/*  32:292 */     final Map unmodifiableMap = Collections.unmodifiableMap(this.map);
/*  33:296 */     final List list = new ArrayList(25);
/*  34:300 */     final List unmodifiableList = Collections.unmodifiableList(this.list);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected Enum(String name)
/*  38:    */   {
/*  39:322 */     init(name);
/*  40:323 */     this.iName = name;
/*  41:324 */     this.iHashCode = (7 + getEnumClass().hashCode() + 3 * name.hashCode());
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void init(String name)
/*  45:    */   {
/*  46:336 */     if (StringUtils.isEmpty(name)) {
/*  47:337 */       throw new IllegalArgumentException("The Enum name must not be empty or null");
/*  48:    */     }
/*  49:340 */     Class enumClass = getEnumClass();
/*  50:341 */     if (enumClass == null) {
/*  51:342 */       throw new IllegalArgumentException("getEnumClass() must not be null");
/*  52:    */     }
/*  53:344 */     Class cls = getClass();
/*  54:345 */     boolean ok = false;
/*  55:346 */     while ((cls != null) && (cls != Enum.class) && (cls != ValuedEnum.class))
/*  56:    */     {
/*  57:347 */       if (cls == enumClass)
/*  58:    */       {
/*  59:348 */         ok = true;
/*  60:349 */         break;
/*  61:    */       }
/*  62:351 */       cls = cls.getSuperclass();
/*  63:    */     }
/*  64:353 */     if (!ok) {
/*  65:354 */       throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
/*  66:    */     }
/*  67:    */     Entry entry;
/*  68:358 */     synchronized (Enum.class)
/*  69:    */     {
/*  70:360 */       entry = (Entry)cEnumClasses.get(enumClass);
/*  71:361 */       if (entry == null)
/*  72:    */       {
/*  73:362 */         entry = createEntry(enumClass);
/*  74:363 */         Map myMap = new WeakHashMap();
/*  75:364 */         myMap.putAll(cEnumClasses);
/*  76:365 */         myMap.put(enumClass, entry);
/*  77:366 */         cEnumClasses = myMap;
/*  78:    */       }
/*  79:    */     }
/*  80:369 */     if (entry.map.containsKey(name)) {
/*  81:370 */       throw new IllegalArgumentException("The Enum name must be unique, '" + name + "' has already been added");
/*  82:    */     }
/*  83:372 */     entry.map.put(name, this);
/*  84:373 */     entry.list.add(this);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected Object readResolve()
/*  88:    */   {
/*  89:383 */     Entry entry = (Entry)cEnumClasses.get(getEnumClass());
/*  90:384 */     if (entry == null) {
/*  91:385 */       return null;
/*  92:    */     }
/*  93:387 */     return entry.map.get(getName());
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected static Enum getEnum(Class enumClass, String name)
/*  97:    */   {
/*  98:404 */     Entry entry = getEntry(enumClass);
/*  99:405 */     if (entry == null) {
/* 100:406 */       return null;
/* 101:    */     }
/* 102:408 */     return (Enum)entry.map.get(name);
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected static Map getEnumMap(Class enumClass)
/* 106:    */   {
/* 107:425 */     Entry entry = getEntry(enumClass);
/* 108:426 */     if (entry == null) {
/* 109:427 */       return EMPTY_MAP;
/* 110:    */     }
/* 111:429 */     return entry.unmodifiableMap;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected static List getEnumList(Class enumClass)
/* 115:    */   {
/* 116:447 */     Entry entry = getEntry(enumClass);
/* 117:448 */     if (entry == null) {
/* 118:449 */       return Collections.EMPTY_LIST;
/* 119:    */     }
/* 120:451 */     return entry.unmodifiableList;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected static Iterator iterator(Class enumClass)
/* 124:    */   {
/* 125:469 */     return getEnumList(enumClass).iterator();
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static Entry getEntry(Class enumClass)
/* 129:    */   {
/* 130:480 */     if (enumClass == null) {
/* 131:481 */       throw new IllegalArgumentException("The Enum Class must not be null");
/* 132:    */     }
/* 133:483 */     if (!Enum.class.isAssignableFrom(enumClass)) {
/* 134:484 */       throw new IllegalArgumentException("The Class must be a subclass of Enum");
/* 135:    */     }
/* 136:486 */     Entry entry = (Entry)cEnumClasses.get(enumClass);
/* 137:488 */     if (entry == null) {
/* 138:    */       try
/* 139:    */       {
/* 140:491 */         Class.forName(enumClass.getName(), true, enumClass.getClassLoader());
/* 141:492 */         entry = (Entry)cEnumClasses.get(enumClass);
/* 142:    */       }
/* 143:    */       catch (Exception e) {}
/* 144:    */     }
/* 145:498 */     return entry;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static Entry createEntry(Class enumClass)
/* 149:    */   {
/* 150:510 */     Entry entry = new Entry();
/* 151:511 */     Class cls = enumClass.getSuperclass();
/* 152:512 */     while ((cls != null) && (cls != Enum.class) && (cls != ValuedEnum.class))
/* 153:    */     {
/* 154:513 */       Entry loopEntry = (Entry)cEnumClasses.get(cls);
/* 155:514 */       if (loopEntry != null)
/* 156:    */       {
/* 157:515 */         entry.list.addAll(loopEntry.list);
/* 158:516 */         entry.map.putAll(loopEntry.map);
/* 159:517 */         break;
/* 160:    */       }
/* 161:519 */       cls = cls.getSuperclass();
/* 162:    */     }
/* 163:521 */     return entry;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public final String getName()
/* 167:    */   {
/* 168:531 */     return this.iName;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Class getEnumClass()
/* 172:    */   {
/* 173:545 */     return getClass();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public final boolean equals(Object other)
/* 177:    */   {
/* 178:562 */     if (other == this) {
/* 179:563 */       return true;
/* 180:    */     }
/* 181:564 */     if (other == null) {
/* 182:565 */       return false;
/* 183:    */     }
/* 184:566 */     if (other.getClass() == getClass()) {
/* 185:570 */       return this.iName.equals(((Enum)other).iName);
/* 186:    */     }
/* 187:573 */     if (!other.getClass().getName().equals(getClass().getName())) {
/* 188:574 */       return false;
/* 189:    */     }
/* 190:576 */     return this.iName.equals(getNameInOtherClassLoader(other));
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final int hashCode()
/* 194:    */   {
/* 195:586 */     return this.iHashCode;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int compareTo(Object other)
/* 199:    */   {
/* 200:606 */     if (other == this) {
/* 201:607 */       return 0;
/* 202:    */     }
/* 203:609 */     if (other.getClass() != getClass())
/* 204:    */     {
/* 205:610 */       if (other.getClass().getName().equals(getClass().getName())) {
/* 206:611 */         return this.iName.compareTo(getNameInOtherClassLoader(other));
/* 207:    */       }
/* 208:613 */       throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
/* 209:    */     }
/* 210:616 */     return this.iName.compareTo(((Enum)other).iName);
/* 211:    */   }
/* 212:    */   
/* 213:    */   private String getNameInOtherClassLoader(Object other)
/* 214:    */   {
/* 215:    */     try
/* 216:    */     {
/* 217:627 */       Method mth = other.getClass().getMethod("getName", null);
/* 218:628 */       return (String)mth.invoke(other, null);
/* 219:    */     }
/* 220:    */     catch (NoSuchMethodException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/* 221:637 */     throw new IllegalStateException("This should not happen");
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String toString()
/* 225:    */   {
/* 226:648 */     if (this.iToString == null)
/* 227:    */     {
/* 228:649 */       String shortName = ClassUtils.getShortClassName(getEnumClass());
/* 229:650 */       this.iToString = (shortName + "[" + getName() + "]");
/* 230:    */     }
/* 231:652 */     return this.iToString;
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enum.Enum
 * JD-Core Version:    0.7.0.1
 */