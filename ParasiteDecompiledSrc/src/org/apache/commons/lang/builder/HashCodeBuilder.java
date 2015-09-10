/*    1:     */ package org.apache.commons.lang.builder;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.AccessibleObject;
/*    4:     */ import java.lang.reflect.Field;
/*    5:     */ import java.lang.reflect.Modifier;
/*    6:     */ import java.util.Collection;
/*    7:     */ import java.util.HashSet;
/*    8:     */ import java.util.Set;
/*    9:     */ import org.apache.commons.lang.ArrayUtils;
/*   10:     */ 
/*   11:     */ public class HashCodeBuilder
/*   12:     */ {
/*   13: 111 */   private static final ThreadLocal REGISTRY = new ThreadLocal();
/*   14:     */   private final int iConstant;
/*   15:     */   
/*   16:     */   static Set getRegistry()
/*   17:     */   {
/*   18: 139 */     return (Set)REGISTRY.get();
/*   19:     */   }
/*   20:     */   
/*   21:     */   static boolean isRegistered(Object value)
/*   22:     */   {
/*   23: 154 */     Set registry = getRegistry();
/*   24: 155 */     return (registry != null) && (registry.contains(new IDKey(value)));
/*   25:     */   }
/*   26:     */   
/*   27:     */   private static void reflectionAppend(Object object, Class clazz, HashCodeBuilder builder, boolean useTransients, String[] excludeFields)
/*   28:     */   {
/*   29: 176 */     if (isRegistered(object)) {
/*   30: 177 */       return;
/*   31:     */     }
/*   32:     */     try
/*   33:     */     {
/*   34: 180 */       register(object);
/*   35: 181 */       Field[] fields = clazz.getDeclaredFields();
/*   36: 182 */       AccessibleObject.setAccessible(fields, true);
/*   37: 183 */       for (int i = 0; i < fields.length; i++)
/*   38:     */       {
/*   39: 184 */         Field field = fields[i];
/*   40: 185 */         if ((!ArrayUtils.contains(excludeFields, field.getName())) && (field.getName().indexOf('$') == -1) && ((useTransients) || (!Modifier.isTransient(field.getModifiers()))) && (!Modifier.isStatic(field.getModifiers()))) {
/*   41:     */           try
/*   42:     */           {
/*   43: 190 */             Object fieldValue = field.get(object);
/*   44: 191 */             builder.append(fieldValue);
/*   45:     */           }
/*   46:     */           catch (IllegalAccessException e)
/*   47:     */           {
/*   48: 195 */             throw new InternalError("Unexpected IllegalAccessException");
/*   49:     */           }
/*   50:     */         }
/*   51:     */       }
/*   52:     */     }
/*   53:     */     finally
/*   54:     */     {
/*   55: 200 */       unregister(object);
/*   56:     */     }
/*   57:     */   }
/*   58:     */   
/*   59:     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object)
/*   60:     */   {
/*   61: 242 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null, null);
/*   62:     */   }
/*   63:     */   
/*   64:     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients)
/*   65:     */   {
/*   66: 286 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null, null);
/*   67:     */   }
/*   68:     */   
/*   69:     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients, Class reflectUpToClass)
/*   70:     */   {
/*   71: 308 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, reflectUpToClass, null);
/*   72:     */   }
/*   73:     */   
/*   74:     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients, Class reflectUpToClass, String[] excludeFields)
/*   75:     */   {
/*   76: 360 */     if (object == null) {
/*   77: 361 */       throw new IllegalArgumentException("The object to build a hash code for must not be null");
/*   78:     */     }
/*   79: 363 */     HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
/*   80: 364 */     Class clazz = object.getClass();
/*   81: 365 */     reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/*   82: 366 */     while ((clazz.getSuperclass() != null) && (clazz != reflectUpToClass))
/*   83:     */     {
/*   84: 367 */       clazz = clazz.getSuperclass();
/*   85: 368 */       reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/*   86:     */     }
/*   87: 370 */     return builder.toHashCode();
/*   88:     */   }
/*   89:     */   
/*   90:     */   public static int reflectionHashCode(Object object)
/*   91:     */   {
/*   92: 404 */     return reflectionHashCode(17, 37, object, false, null, null);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static int reflectionHashCode(Object object, boolean testTransients)
/*   96:     */   {
/*   97: 440 */     return reflectionHashCode(17, 37, object, testTransients, null, null);
/*   98:     */   }
/*   99:     */   
/*  100:     */   public static int reflectionHashCode(Object object, Collection excludeFields)
/*  101:     */   {
/*  102: 476 */     return reflectionHashCode(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
/*  103:     */   }
/*  104:     */   
/*  105:     */   public static int reflectionHashCode(Object object, String[] excludeFields)
/*  106:     */   {
/*  107: 514 */     return reflectionHashCode(17, 37, object, false, null, excludeFields);
/*  108:     */   }
/*  109:     */   
/*  110:     */   static void register(Object value)
/*  111:     */   {
/*  112: 526 */     synchronized (HashCodeBuilder.class)
/*  113:     */     {
/*  114: 527 */       if (getRegistry() == null) {
/*  115: 528 */         REGISTRY.set(new HashSet());
/*  116:     */       }
/*  117:     */     }
/*  118: 531 */     getRegistry().add(new IDKey(value));
/*  119:     */   }
/*  120:     */   
/*  121:     */   static void unregister(Object value)
/*  122:     */   {
/*  123: 547 */     Set registry = getRegistry();
/*  124: 548 */     if (registry != null)
/*  125:     */     {
/*  126: 549 */       registry.remove(new IDKey(value));
/*  127: 550 */       synchronized (HashCodeBuilder.class)
/*  128:     */       {
/*  129: 552 */         registry = getRegistry();
/*  130: 553 */         if ((registry != null) && (registry.isEmpty())) {
/*  131: 554 */           REGISTRY.set(null);
/*  132:     */         }
/*  133:     */       }
/*  134:     */     }
/*  135:     */   }
/*  136:     */   
/*  137: 568 */   private int iTotal = 0;
/*  138:     */   
/*  139:     */   public HashCodeBuilder()
/*  140:     */   {
/*  141: 576 */     this.iConstant = 37;
/*  142: 577 */     this.iTotal = 17;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public HashCodeBuilder(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber)
/*  146:     */   {
/*  147: 598 */     if (initialNonZeroOddNumber == 0) {
/*  148: 599 */       throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
/*  149:     */     }
/*  150: 601 */     if (initialNonZeroOddNumber % 2 == 0) {
/*  151: 602 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
/*  152:     */     }
/*  153: 604 */     if (multiplierNonZeroOddNumber == 0) {
/*  154: 605 */       throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
/*  155:     */     }
/*  156: 607 */     if (multiplierNonZeroOddNumber % 2 == 0) {
/*  157: 608 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
/*  158:     */     }
/*  159: 610 */     this.iConstant = multiplierNonZeroOddNumber;
/*  160: 611 */     this.iTotal = initialNonZeroOddNumber;
/*  161:     */   }
/*  162:     */   
/*  163:     */   public HashCodeBuilder append(boolean value)
/*  164:     */   {
/*  165: 636 */     this.iTotal = (this.iTotal * this.iConstant + (value ? 0 : 1));
/*  166: 637 */     return this;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public HashCodeBuilder append(boolean[] array)
/*  170:     */   {
/*  171: 650 */     if (array == null) {
/*  172: 651 */       this.iTotal *= this.iConstant;
/*  173:     */     } else {
/*  174: 653 */       for (int i = 0; i < array.length; i++) {
/*  175: 654 */         append(array[i]);
/*  176:     */       }
/*  177:     */     }
/*  178: 657 */     return this;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public HashCodeBuilder append(byte value)
/*  182:     */   {
/*  183: 672 */     this.iTotal = (this.iTotal * this.iConstant + value);
/*  184: 673 */     return this;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public HashCodeBuilder append(byte[] array)
/*  188:     */   {
/*  189: 688 */     if (array == null) {
/*  190: 689 */       this.iTotal *= this.iConstant;
/*  191:     */     } else {
/*  192: 691 */       for (int i = 0; i < array.length; i++) {
/*  193: 692 */         append(array[i]);
/*  194:     */       }
/*  195:     */     }
/*  196: 695 */     return this;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public HashCodeBuilder append(char value)
/*  200:     */   {
/*  201: 708 */     this.iTotal = (this.iTotal * this.iConstant + value);
/*  202: 709 */     return this;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public HashCodeBuilder append(char[] array)
/*  206:     */   {
/*  207: 722 */     if (array == null) {
/*  208: 723 */       this.iTotal *= this.iConstant;
/*  209:     */     } else {
/*  210: 725 */       for (int i = 0; i < array.length; i++) {
/*  211: 726 */         append(array[i]);
/*  212:     */       }
/*  213:     */     }
/*  214: 729 */     return this;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public HashCodeBuilder append(double value)
/*  218:     */   {
/*  219: 742 */     return append(Double.doubleToLongBits(value));
/*  220:     */   }
/*  221:     */   
/*  222:     */   public HashCodeBuilder append(double[] array)
/*  223:     */   {
/*  224: 755 */     if (array == null) {
/*  225: 756 */       this.iTotal *= this.iConstant;
/*  226:     */     } else {
/*  227: 758 */       for (int i = 0; i < array.length; i++) {
/*  228: 759 */         append(array[i]);
/*  229:     */       }
/*  230:     */     }
/*  231: 762 */     return this;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public HashCodeBuilder append(float value)
/*  235:     */   {
/*  236: 775 */     this.iTotal = (this.iTotal * this.iConstant + Float.floatToIntBits(value));
/*  237: 776 */     return this;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public HashCodeBuilder append(float[] array)
/*  241:     */   {
/*  242: 789 */     if (array == null) {
/*  243: 790 */       this.iTotal *= this.iConstant;
/*  244:     */     } else {
/*  245: 792 */       for (int i = 0; i < array.length; i++) {
/*  246: 793 */         append(array[i]);
/*  247:     */       }
/*  248:     */     }
/*  249: 796 */     return this;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public HashCodeBuilder append(int value)
/*  253:     */   {
/*  254: 809 */     this.iTotal = (this.iTotal * this.iConstant + value);
/*  255: 810 */     return this;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public HashCodeBuilder append(int[] array)
/*  259:     */   {
/*  260: 823 */     if (array == null) {
/*  261: 824 */       this.iTotal *= this.iConstant;
/*  262:     */     } else {
/*  263: 826 */       for (int i = 0; i < array.length; i++) {
/*  264: 827 */         append(array[i]);
/*  265:     */       }
/*  266:     */     }
/*  267: 830 */     return this;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public HashCodeBuilder append(long value)
/*  271:     */   {
/*  272: 847 */     this.iTotal = (this.iTotal * this.iConstant + (int)(value ^ value >> 32));
/*  273: 848 */     return this;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public HashCodeBuilder append(long[] array)
/*  277:     */   {
/*  278: 861 */     if (array == null) {
/*  279: 862 */       this.iTotal *= this.iConstant;
/*  280:     */     } else {
/*  281: 864 */       for (int i = 0; i < array.length; i++) {
/*  282: 865 */         append(array[i]);
/*  283:     */       }
/*  284:     */     }
/*  285: 868 */     return this;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public HashCodeBuilder append(Object object)
/*  289:     */   {
/*  290: 881 */     if (object == null) {
/*  291: 882 */       this.iTotal *= this.iConstant;
/*  292: 885 */     } else if (object.getClass().isArray())
/*  293:     */     {
/*  294: 888 */       if ((object instanceof long[])) {
/*  295: 889 */         append((long[])object);
/*  296: 890 */       } else if ((object instanceof int[])) {
/*  297: 891 */         append((int[])object);
/*  298: 892 */       } else if ((object instanceof short[])) {
/*  299: 893 */         append((short[])object);
/*  300: 894 */       } else if ((object instanceof char[])) {
/*  301: 895 */         append((char[])object);
/*  302: 896 */       } else if ((object instanceof byte[])) {
/*  303: 897 */         append((byte[])object);
/*  304: 898 */       } else if ((object instanceof double[])) {
/*  305: 899 */         append((double[])object);
/*  306: 900 */       } else if ((object instanceof float[])) {
/*  307: 901 */         append((float[])object);
/*  308: 902 */       } else if ((object instanceof boolean[])) {
/*  309: 903 */         append((boolean[])object);
/*  310:     */       } else {
/*  311: 906 */         append((Object[])object);
/*  312:     */       }
/*  313:     */     }
/*  314:     */     else {
/*  315: 909 */       this.iTotal = (this.iTotal * this.iConstant + object.hashCode());
/*  316:     */     }
/*  317: 912 */     return this;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public HashCodeBuilder append(Object[] array)
/*  321:     */   {
/*  322: 925 */     if (array == null) {
/*  323: 926 */       this.iTotal *= this.iConstant;
/*  324:     */     } else {
/*  325: 928 */       for (int i = 0; i < array.length; i++) {
/*  326: 929 */         append(array[i]);
/*  327:     */       }
/*  328:     */     }
/*  329: 932 */     return this;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public HashCodeBuilder append(short value)
/*  333:     */   {
/*  334: 945 */     this.iTotal = (this.iTotal * this.iConstant + value);
/*  335: 946 */     return this;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public HashCodeBuilder append(short[] array)
/*  339:     */   {
/*  340: 959 */     if (array == null) {
/*  341: 960 */       this.iTotal *= this.iConstant;
/*  342:     */     } else {
/*  343: 962 */       for (int i = 0; i < array.length; i++) {
/*  344: 963 */         append(array[i]);
/*  345:     */       }
/*  346:     */     }
/*  347: 966 */     return this;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public HashCodeBuilder appendSuper(int superHashCode)
/*  351:     */   {
/*  352: 980 */     this.iTotal = (this.iTotal * this.iConstant + superHashCode);
/*  353: 981 */     return this;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public int toHashCode()
/*  357:     */   {
/*  358: 992 */     return this.iTotal;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public int hashCode()
/*  362:     */   {
/*  363:1005 */     return toHashCode();
/*  364:     */   }
/*  365:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.HashCodeBuilder
 * JD-Core Version:    0.7.0.1
 */