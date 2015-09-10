/*   1:    */ package org.apache.commons.lang.enums;
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
/*  16:    */ public abstract class Enum
/*  17:    */   implements Comparable, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -487045951170455942L;
/*  20:300 */   private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
/*  21:305 */   private static Map cEnumClasses = new WeakHashMap();
/*  22:    */   private final String iName;
/*  23:    */   private final transient int iHashCode;
/*  24:325 */   protected transient String iToString = null;
/*  25:    */   
/*  26:    */   private static class Entry
/*  27:    */   {
/*  28:334 */     final Map map = new HashMap();
/*  29:338 */     final Map unmodifiableMap = Collections.unmodifiableMap(this.map);
/*  30:342 */     final List list = new ArrayList(25);
/*  31:346 */     final List unmodifiableList = Collections.unmodifiableList(this.list);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected Enum(String name)
/*  35:    */   {
/*  36:368 */     init(name);
/*  37:369 */     this.iName = name;
/*  38:370 */     this.iHashCode = (7 + getEnumClass().hashCode() + 3 * name.hashCode());
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void init(String name)
/*  42:    */   {
/*  43:382 */     if (StringUtils.isEmpty(name)) {
/*  44:383 */       throw new IllegalArgumentException("The Enum name must not be empty or null");
/*  45:    */     }
/*  46:386 */     Class enumClass = getEnumClass();
/*  47:387 */     if (enumClass == null) {
/*  48:388 */       throw new IllegalArgumentException("getEnumClass() must not be null");
/*  49:    */     }
/*  50:390 */     Class cls = getClass();
/*  51:391 */     boolean ok = false;
/*  52:392 */     while ((cls != null) && (cls != Enum.class) && (cls != ValuedEnum.class))
/*  53:    */     {
/*  54:393 */       if (cls == enumClass)
/*  55:    */       {
/*  56:394 */         ok = true;
/*  57:395 */         break;
/*  58:    */       }
/*  59:397 */       cls = cls.getSuperclass();
/*  60:    */     }
/*  61:399 */     if (!ok) {
/*  62:400 */       throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
/*  63:    */     }
/*  64:    */     Entry entry;
/*  65:404 */     synchronized (Enum.class)
/*  66:    */     {
/*  67:406 */       entry = (Entry)cEnumClasses.get(enumClass);
/*  68:407 */       if (entry == null)
/*  69:    */       {
/*  70:408 */         entry = createEntry(enumClass);
/*  71:409 */         Map myMap = new WeakHashMap();
/*  72:410 */         myMap.putAll(cEnumClasses);
/*  73:411 */         myMap.put(enumClass, entry);
/*  74:412 */         cEnumClasses = myMap;
/*  75:    */       }
/*  76:    */     }
/*  77:415 */     if (entry.map.containsKey(name)) {
/*  78:416 */       throw new IllegalArgumentException("The Enum name must be unique, '" + name + "' has already been added");
/*  79:    */     }
/*  80:418 */     entry.map.put(name, this);
/*  81:419 */     entry.list.add(this);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected Object readResolve()
/*  85:    */   {
/*  86:429 */     Entry entry = (Entry)cEnumClasses.get(getEnumClass());
/*  87:430 */     if (entry == null) {
/*  88:431 */       return null;
/*  89:    */     }
/*  90:433 */     return entry.map.get(getName());
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected static Enum getEnum(Class enumClass, String name)
/*  94:    */   {
/*  95:450 */     Entry entry = getEntry(enumClass);
/*  96:451 */     if (entry == null) {
/*  97:452 */       return null;
/*  98:    */     }
/*  99:454 */     return (Enum)entry.map.get(name);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected static Map getEnumMap(Class enumClass)
/* 103:    */   {
/* 104:471 */     Entry entry = getEntry(enumClass);
/* 105:472 */     if (entry == null) {
/* 106:473 */       return EMPTY_MAP;
/* 107:    */     }
/* 108:475 */     return entry.unmodifiableMap;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected static List getEnumList(Class enumClass)
/* 112:    */   {
/* 113:493 */     Entry entry = getEntry(enumClass);
/* 114:494 */     if (entry == null) {
/* 115:495 */       return Collections.EMPTY_LIST;
/* 116:    */     }
/* 117:497 */     return entry.unmodifiableList;
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected static Iterator iterator(Class enumClass)
/* 121:    */   {
/* 122:515 */     return getEnumList(enumClass).iterator();
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static Entry getEntry(Class enumClass)
/* 126:    */   {
/* 127:526 */     if (enumClass == null) {
/* 128:527 */       throw new IllegalArgumentException("The Enum Class must not be null");
/* 129:    */     }
/* 130:529 */     if (!Enum.class.isAssignableFrom(enumClass)) {
/* 131:530 */       throw new IllegalArgumentException("The Class must be a subclass of Enum");
/* 132:    */     }
/* 133:532 */     Entry entry = (Entry)cEnumClasses.get(enumClass);
/* 134:534 */     if (entry == null) {
/* 135:    */       try
/* 136:    */       {
/* 137:537 */         Class.forName(enumClass.getName(), true, enumClass.getClassLoader());
/* 138:538 */         entry = (Entry)cEnumClasses.get(enumClass);
/* 139:    */       }
/* 140:    */       catch (Exception e) {}
/* 141:    */     }
/* 142:544 */     return entry;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private static Entry createEntry(Class enumClass)
/* 146:    */   {
/* 147:556 */     Entry entry = new Entry();
/* 148:557 */     Class cls = enumClass.getSuperclass();
/* 149:558 */     while ((cls != null) && (cls != Enum.class) && (cls != ValuedEnum.class))
/* 150:    */     {
/* 151:559 */       Entry loopEntry = (Entry)cEnumClasses.get(cls);
/* 152:560 */       if (loopEntry != null)
/* 153:    */       {
/* 154:561 */         entry.list.addAll(loopEntry.list);
/* 155:562 */         entry.map.putAll(loopEntry.map);
/* 156:563 */         break;
/* 157:    */       }
/* 158:565 */       cls = cls.getSuperclass();
/* 159:    */     }
/* 160:567 */     return entry;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final String getName()
/* 164:    */   {
/* 165:577 */     return this.iName;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Class getEnumClass()
/* 169:    */   {
/* 170:591 */     return getClass();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final boolean equals(Object other)
/* 174:    */   {
/* 175:608 */     if (other == this) {
/* 176:609 */       return true;
/* 177:    */     }
/* 178:610 */     if (other == null) {
/* 179:611 */       return false;
/* 180:    */     }
/* 181:612 */     if (other.getClass() == getClass()) {
/* 182:616 */       return this.iName.equals(((Enum)other).iName);
/* 183:    */     }
/* 184:619 */     if (!other.getClass().getName().equals(getClass().getName())) {
/* 185:620 */       return false;
/* 186:    */     }
/* 187:622 */     return this.iName.equals(getNameInOtherClassLoader(other));
/* 188:    */   }
/* 189:    */   
/* 190:    */   public final int hashCode()
/* 191:    */   {
/* 192:632 */     return this.iHashCode;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int compareTo(Object other)
/* 196:    */   {
/* 197:652 */     if (other == this) {
/* 198:653 */       return 0;
/* 199:    */     }
/* 200:655 */     if (other.getClass() != getClass())
/* 201:    */     {
/* 202:656 */       if (other.getClass().getName().equals(getClass().getName())) {
/* 203:657 */         return this.iName.compareTo(getNameInOtherClassLoader(other));
/* 204:    */       }
/* 205:659 */       throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
/* 206:    */     }
/* 207:662 */     return this.iName.compareTo(((Enum)other).iName);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private String getNameInOtherClassLoader(Object other)
/* 211:    */   {
/* 212:    */     try
/* 213:    */     {
/* 214:673 */       Method mth = other.getClass().getMethod("getName", null);
/* 215:674 */       return (String)mth.invoke(other, null);
/* 216:    */     }
/* 217:    */     catch (NoSuchMethodException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/* 218:683 */     throw new IllegalStateException("This should not happen");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String toString()
/* 222:    */   {
/* 223:694 */     if (this.iToString == null)
/* 224:    */     {
/* 225:695 */       String shortName = ClassUtils.getShortClassName(getEnumClass());
/* 226:696 */       this.iToString = (shortName + "[" + getName() + "]");
/* 227:    */     }
/* 228:698 */     return this.iToString;
/* 229:    */   }
/* 230:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enums.Enum
 * JD-Core Version:    0.7.0.1
 */