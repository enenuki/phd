/*   1:    */ package org.apache.commons.lang.builder;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collection;
/*   9:    */ import org.apache.commons.lang.ArrayUtils;
/*  10:    */ 
/*  11:    */ public class ReflectionToStringBuilder
/*  12:    */   extends ToStringBuilder
/*  13:    */ {
/*  14:    */   public static String toString(Object object)
/*  15:    */   {
/*  16:121 */     return toString(object, null, false, false, null);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static String toString(Object object, ToStringStyle style)
/*  20:    */   {
/*  21:153 */     return toString(object, style, false, false, null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String toString(Object object, ToStringStyle style, boolean outputTransients)
/*  25:    */   {
/*  26:191 */     return toString(object, style, outputTransients, false, null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String toString(Object object, ToStringStyle style, boolean outputTransients, boolean outputStatics)
/*  30:    */   {
/*  31:237 */     return toString(object, style, outputTransients, outputStatics, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String toString(Object object, ToStringStyle style, boolean outputTransients, boolean outputStatics, Class reflectUpToClass)
/*  35:    */   {
/*  36:287 */     return new ReflectionToStringBuilder(object, style, null, reflectUpToClass, outputTransients, outputStatics).toString();
/*  37:    */   }
/*  38:    */   
/*  39:    */   /**
/*  40:    */    * @deprecated
/*  41:    */    */
/*  42:    */   public static String toString(Object object, ToStringStyle style, boolean outputTransients, Class reflectUpToClass)
/*  43:    */   {
/*  44:334 */     return new ReflectionToStringBuilder(object, style, null, reflectUpToClass, outputTransients).toString();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String toStringExclude(Object object, String excludeFieldName)
/*  48:    */   {
/*  49:347 */     return toStringExclude(object, new String[] { excludeFieldName });
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String toStringExclude(Object object, Collection excludeFieldNames)
/*  53:    */   {
/*  54:360 */     return toStringExclude(object, toNoNullStringArray(excludeFieldNames));
/*  55:    */   }
/*  56:    */   
/*  57:    */   static String[] toNoNullStringArray(Collection collection)
/*  58:    */   {
/*  59:373 */     if (collection == null) {
/*  60:374 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  61:    */     }
/*  62:376 */     return toNoNullStringArray(collection.toArray());
/*  63:    */   }
/*  64:    */   
/*  65:    */   static String[] toNoNullStringArray(Object[] array)
/*  66:    */   {
/*  67:389 */     ArrayList list = new ArrayList(array.length);
/*  68:390 */     for (int i = 0; i < array.length; i++)
/*  69:    */     {
/*  70:391 */       Object e = array[i];
/*  71:392 */       if (e != null) {
/*  72:393 */         list.add(e.toString());
/*  73:    */       }
/*  74:    */     }
/*  75:396 */     return (String[])list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String toStringExclude(Object object, String[] excludeFieldNames)
/*  79:    */   {
/*  80:410 */     return new ReflectionToStringBuilder(object).setExcludeFieldNames(excludeFieldNames).toString();
/*  81:    */   }
/*  82:    */   
/*  83:416 */   private boolean appendStatics = false;
/*  84:421 */   private boolean appendTransients = false;
/*  85:    */   private String[] excludeFieldNames;
/*  86:431 */   private Class upToClass = null;
/*  87:    */   
/*  88:    */   public ReflectionToStringBuilder(Object object)
/*  89:    */   {
/*  90:448 */     super(object);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public ReflectionToStringBuilder(Object object, ToStringStyle style)
/*  94:    */   {
/*  95:468 */     super(object, style);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer)
/*  99:    */   {
/* 100:494 */     super(object, style, buffer);
/* 101:    */   }
/* 102:    */   
/* 103:    */   /**
/* 104:    */    * @deprecated
/* 105:    */    */
/* 106:    */   public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class reflectUpToClass, boolean outputTransients)
/* 107:    */   {
/* 108:515 */     super(object, style, buffer);
/* 109:516 */     setUpToClass(reflectUpToClass);
/* 110:517 */     setAppendTransients(outputTransients);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public ReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class reflectUpToClass, boolean outputTransients, boolean outputStatics)
/* 114:    */   {
/* 115:539 */     super(object, style, buffer);
/* 116:540 */     setUpToClass(reflectUpToClass);
/* 117:541 */     setAppendTransients(outputTransients);
/* 118:542 */     setAppendStatics(outputStatics);
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected boolean accept(Field field)
/* 122:    */   {
/* 123:558 */     if (field.getName().indexOf('$') != -1) {
/* 124:560 */       return false;
/* 125:    */     }
/* 126:562 */     if ((Modifier.isTransient(field.getModifiers())) && (!isAppendTransients())) {
/* 127:564 */       return false;
/* 128:    */     }
/* 129:566 */     if ((Modifier.isStatic(field.getModifiers())) && (!isAppendStatics())) {
/* 130:568 */       return false;
/* 131:    */     }
/* 132:570 */     if ((getExcludeFieldNames() != null) && (Arrays.binarySearch(getExcludeFieldNames(), field.getName()) >= 0)) {
/* 133:573 */       return false;
/* 134:    */     }
/* 135:575 */     return true;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void appendFieldsIn(Class clazz)
/* 139:    */   {
/* 140:592 */     if (clazz.isArray())
/* 141:    */     {
/* 142:593 */       reflectionAppendArray(getObject());
/* 143:594 */       return;
/* 144:    */     }
/* 145:596 */     Field[] fields = clazz.getDeclaredFields();
/* 146:597 */     AccessibleObject.setAccessible(fields, true);
/* 147:598 */     for (int i = 0; i < fields.length; i++)
/* 148:    */     {
/* 149:599 */       Field field = fields[i];
/* 150:600 */       String fieldName = field.getName();
/* 151:601 */       if (accept(field)) {
/* 152:    */         try
/* 153:    */         {
/* 154:605 */           Object fieldValue = getValue(field);
/* 155:606 */           append(fieldName, fieldValue);
/* 156:    */         }
/* 157:    */         catch (IllegalAccessException ex)
/* 158:    */         {
/* 159:612 */           throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
/* 160:    */         }
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String[] getExcludeFieldNames()
/* 166:    */   {
/* 167:622 */     return this.excludeFieldNames;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Class getUpToClass()
/* 171:    */   {
/* 172:633 */     return this.upToClass;
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected Object getValue(Field field)
/* 176:    */     throws IllegalArgumentException, IllegalAccessException
/* 177:    */   {
/* 178:653 */     return field.get(getObject());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isAppendStatics()
/* 182:    */   {
/* 183:665 */     return this.appendStatics;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isAppendTransients()
/* 187:    */   {
/* 188:676 */     return this.appendTransients;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public ToStringBuilder reflectionAppendArray(Object array)
/* 192:    */   {
/* 193:689 */     getStyle().reflectionAppendArrayDetail(getStringBuffer(), null, array);
/* 194:690 */     return this;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setAppendStatics(boolean appendStatics)
/* 198:    */   {
/* 199:703 */     this.appendStatics = appendStatics;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setAppendTransients(boolean appendTransients)
/* 203:    */   {
/* 204:715 */     this.appendTransients = appendTransients;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public ReflectionToStringBuilder setExcludeFieldNames(String[] excludeFieldNamesParam)
/* 208:    */   {
/* 209:726 */     if (excludeFieldNamesParam == null)
/* 210:    */     {
/* 211:727 */       this.excludeFieldNames = null;
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:729 */       this.excludeFieldNames = toNoNullStringArray(excludeFieldNamesParam);
/* 216:730 */       Arrays.sort(this.excludeFieldNames);
/* 217:    */     }
/* 218:732 */     return this;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setUpToClass(Class clazz)
/* 222:    */   {
/* 223:744 */     if (clazz != null)
/* 224:    */     {
/* 225:745 */       Object object = getObject();
/* 226:746 */       if ((object != null) && (!clazz.isInstance(object))) {
/* 227:747 */         throw new IllegalArgumentException("Specified class is not a superclass of the object");
/* 228:    */       }
/* 229:    */     }
/* 230:750 */     this.upToClass = clazz;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:761 */     if (getObject() == null) {
/* 236:762 */       return getStyle().getNullText();
/* 237:    */     }
/* 238:764 */     Class clazz = getObject().getClass();
/* 239:765 */     appendFieldsIn(clazz);
/* 240:766 */     while ((clazz.getSuperclass() != null) && (clazz != getUpToClass()))
/* 241:    */     {
/* 242:767 */       clazz = clazz.getSuperclass();
/* 243:768 */       appendFieldsIn(clazz);
/* 244:    */     }
/* 245:770 */     return super.toString();
/* 246:    */   }
/* 247:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.ReflectionToStringBuilder
 * JD-Core Version:    0.7.0.1
 */