/*    1:     */ package org.apache.commons.lang.builder;
/*    2:     */ 
/*    3:     */ import org.apache.commons.lang.BooleanUtils;
/*    4:     */ import org.apache.commons.lang.ObjectUtils;
/*    5:     */ 
/*    6:     */ public class ToStringBuilder
/*    7:     */ {
/*    8:  98 */   private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
/*    9:     */   private final StringBuffer buffer;
/*   10:     */   private final Object object;
/*   11:     */   private final ToStringStyle style;
/*   12:     */   
/*   13:     */   public static ToStringStyle getDefaultStyle()
/*   14:     */   {
/*   15: 121 */     return defaultStyle;
/*   16:     */   }
/*   17:     */   
/*   18:     */   public static void setDefaultStyle(ToStringStyle style)
/*   19:     */   {
/*   20: 140 */     if (style == null) {
/*   21: 141 */       throw new IllegalArgumentException("The style must not be null");
/*   22:     */     }
/*   23: 143 */     defaultStyle = style;
/*   24:     */   }
/*   25:     */   
/*   26:     */   public static String reflectionToString(Object object)
/*   27:     */   {
/*   28: 156 */     return ReflectionToStringBuilder.toString(object);
/*   29:     */   }
/*   30:     */   
/*   31:     */   public static String reflectionToString(Object object, ToStringStyle style)
/*   32:     */   {
/*   33: 169 */     return ReflectionToStringBuilder.toString(object, style);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public static String reflectionToString(Object object, ToStringStyle style, boolean outputTransients)
/*   37:     */   {
/*   38: 183 */     return ReflectionToStringBuilder.toString(object, style, outputTransients, false, null);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public static String reflectionToString(Object object, ToStringStyle style, boolean outputTransients, Class reflectUpToClass)
/*   42:     */   {
/*   43: 203 */     return ReflectionToStringBuilder.toString(object, style, outputTransients, false, reflectUpToClass);
/*   44:     */   }
/*   45:     */   
/*   46:     */   public ToStringBuilder(Object object)
/*   47:     */   {
/*   48: 229 */     this(object, null, null);
/*   49:     */   }
/*   50:     */   
/*   51:     */   public ToStringBuilder(Object object, ToStringStyle style)
/*   52:     */   {
/*   53: 241 */     this(object, style, null);
/*   54:     */   }
/*   55:     */   
/*   56:     */   public ToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer)
/*   57:     */   {
/*   58: 256 */     if (style == null) {
/*   59: 257 */       style = getDefaultStyle();
/*   60:     */     }
/*   61: 259 */     if (buffer == null) {
/*   62: 260 */       buffer = new StringBuffer(512);
/*   63:     */     }
/*   64: 262 */     this.buffer = buffer;
/*   65: 263 */     this.style = style;
/*   66: 264 */     this.object = object;
/*   67:     */     
/*   68: 266 */     style.appendStart(buffer, object);
/*   69:     */   }
/*   70:     */   
/*   71:     */   public ToStringBuilder append(boolean value)
/*   72:     */   {
/*   73: 279 */     this.style.append(this.buffer, null, value);
/*   74: 280 */     return this;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public ToStringBuilder append(boolean[] array)
/*   78:     */   {
/*   79: 293 */     this.style.append(this.buffer, null, array, null);
/*   80: 294 */     return this;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public ToStringBuilder append(byte value)
/*   84:     */   {
/*   85: 307 */     this.style.append(this.buffer, null, value);
/*   86: 308 */     return this;
/*   87:     */   }
/*   88:     */   
/*   89:     */   public ToStringBuilder append(byte[] array)
/*   90:     */   {
/*   91: 321 */     this.style.append(this.buffer, null, array, null);
/*   92: 322 */     return this;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public ToStringBuilder append(char value)
/*   96:     */   {
/*   97: 335 */     this.style.append(this.buffer, null, value);
/*   98: 336 */     return this;
/*   99:     */   }
/*  100:     */   
/*  101:     */   public ToStringBuilder append(char[] array)
/*  102:     */   {
/*  103: 349 */     this.style.append(this.buffer, null, array, null);
/*  104: 350 */     return this;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public ToStringBuilder append(double value)
/*  108:     */   {
/*  109: 363 */     this.style.append(this.buffer, null, value);
/*  110: 364 */     return this;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public ToStringBuilder append(double[] array)
/*  114:     */   {
/*  115: 377 */     this.style.append(this.buffer, null, array, null);
/*  116: 378 */     return this;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public ToStringBuilder append(float value)
/*  120:     */   {
/*  121: 391 */     this.style.append(this.buffer, null, value);
/*  122: 392 */     return this;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public ToStringBuilder append(float[] array)
/*  126:     */   {
/*  127: 405 */     this.style.append(this.buffer, null, array, null);
/*  128: 406 */     return this;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public ToStringBuilder append(int value)
/*  132:     */   {
/*  133: 419 */     this.style.append(this.buffer, null, value);
/*  134: 420 */     return this;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public ToStringBuilder append(int[] array)
/*  138:     */   {
/*  139: 433 */     this.style.append(this.buffer, null, array, null);
/*  140: 434 */     return this;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public ToStringBuilder append(long value)
/*  144:     */   {
/*  145: 447 */     this.style.append(this.buffer, null, value);
/*  146: 448 */     return this;
/*  147:     */   }
/*  148:     */   
/*  149:     */   public ToStringBuilder append(long[] array)
/*  150:     */   {
/*  151: 461 */     this.style.append(this.buffer, null, array, null);
/*  152: 462 */     return this;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public ToStringBuilder append(Object obj)
/*  156:     */   {
/*  157: 475 */     this.style.append(this.buffer, null, obj, null);
/*  158: 476 */     return this;
/*  159:     */   }
/*  160:     */   
/*  161:     */   public ToStringBuilder append(Object[] array)
/*  162:     */   {
/*  163: 489 */     this.style.append(this.buffer, null, array, null);
/*  164: 490 */     return this;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public ToStringBuilder append(short value)
/*  168:     */   {
/*  169: 503 */     this.style.append(this.buffer, null, value);
/*  170: 504 */     return this;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public ToStringBuilder append(short[] array)
/*  174:     */   {
/*  175: 517 */     this.style.append(this.buffer, null, array, null);
/*  176: 518 */     return this;
/*  177:     */   }
/*  178:     */   
/*  179:     */   public ToStringBuilder append(String fieldName, boolean value)
/*  180:     */   {
/*  181: 530 */     this.style.append(this.buffer, fieldName, value);
/*  182: 531 */     return this;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public ToStringBuilder append(String fieldName, boolean[] array)
/*  186:     */   {
/*  187: 543 */     this.style.append(this.buffer, fieldName, array, null);
/*  188: 544 */     return this;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public ToStringBuilder append(String fieldName, boolean[] array, boolean fullDetail)
/*  192:     */   {
/*  193: 563 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  194: 564 */     return this;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public ToStringBuilder append(String fieldName, byte value)
/*  198:     */   {
/*  199: 576 */     this.style.append(this.buffer, fieldName, value);
/*  200: 577 */     return this;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public ToStringBuilder append(String fieldName, byte[] array)
/*  204:     */   {
/*  205: 588 */     this.style.append(this.buffer, fieldName, array, null);
/*  206: 589 */     return this;
/*  207:     */   }
/*  208:     */   
/*  209:     */   public ToStringBuilder append(String fieldName, byte[] array, boolean fullDetail)
/*  210:     */   {
/*  211: 608 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  212: 609 */     return this;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public ToStringBuilder append(String fieldName, char value)
/*  216:     */   {
/*  217: 621 */     this.style.append(this.buffer, fieldName, value);
/*  218: 622 */     return this;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public ToStringBuilder append(String fieldName, char[] array)
/*  222:     */   {
/*  223: 634 */     this.style.append(this.buffer, fieldName, array, null);
/*  224: 635 */     return this;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public ToStringBuilder append(String fieldName, char[] array, boolean fullDetail)
/*  228:     */   {
/*  229: 654 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  230: 655 */     return this;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public ToStringBuilder append(String fieldName, double value)
/*  234:     */   {
/*  235: 667 */     this.style.append(this.buffer, fieldName, value);
/*  236: 668 */     return this;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public ToStringBuilder append(String fieldName, double[] array)
/*  240:     */   {
/*  241: 680 */     this.style.append(this.buffer, fieldName, array, null);
/*  242: 681 */     return this;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public ToStringBuilder append(String fieldName, double[] array, boolean fullDetail)
/*  246:     */   {
/*  247: 700 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  248: 701 */     return this;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public ToStringBuilder append(String fieldName, float value)
/*  252:     */   {
/*  253: 713 */     this.style.append(this.buffer, fieldName, value);
/*  254: 714 */     return this;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public ToStringBuilder append(String fieldName, float[] array)
/*  258:     */   {
/*  259: 726 */     this.style.append(this.buffer, fieldName, array, null);
/*  260: 727 */     return this;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public ToStringBuilder append(String fieldName, float[] array, boolean fullDetail)
/*  264:     */   {
/*  265: 746 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  266: 747 */     return this;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public ToStringBuilder append(String fieldName, int value)
/*  270:     */   {
/*  271: 759 */     this.style.append(this.buffer, fieldName, value);
/*  272: 760 */     return this;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public ToStringBuilder append(String fieldName, int[] array)
/*  276:     */   {
/*  277: 772 */     this.style.append(this.buffer, fieldName, array, null);
/*  278: 773 */     return this;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public ToStringBuilder append(String fieldName, int[] array, boolean fullDetail)
/*  282:     */   {
/*  283: 792 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  284: 793 */     return this;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public ToStringBuilder append(String fieldName, long value)
/*  288:     */   {
/*  289: 805 */     this.style.append(this.buffer, fieldName, value);
/*  290: 806 */     return this;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public ToStringBuilder append(String fieldName, long[] array)
/*  294:     */   {
/*  295: 818 */     this.style.append(this.buffer, fieldName, array, null);
/*  296: 819 */     return this;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public ToStringBuilder append(String fieldName, long[] array, boolean fullDetail)
/*  300:     */   {
/*  301: 838 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  302: 839 */     return this;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public ToStringBuilder append(String fieldName, Object obj)
/*  306:     */   {
/*  307: 851 */     this.style.append(this.buffer, fieldName, obj, null);
/*  308: 852 */     return this;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public ToStringBuilder append(String fieldName, Object obj, boolean fullDetail)
/*  312:     */   {
/*  313: 866 */     this.style.append(this.buffer, fieldName, obj, BooleanUtils.toBooleanObject(fullDetail));
/*  314: 867 */     return this;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public ToStringBuilder append(String fieldName, Object[] array)
/*  318:     */   {
/*  319: 879 */     this.style.append(this.buffer, fieldName, array, null);
/*  320: 880 */     return this;
/*  321:     */   }
/*  322:     */   
/*  323:     */   public ToStringBuilder append(String fieldName, Object[] array, boolean fullDetail)
/*  324:     */   {
/*  325: 899 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  326: 900 */     return this;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public ToStringBuilder append(String fieldName, short value)
/*  330:     */   {
/*  331: 912 */     this.style.append(this.buffer, fieldName, value);
/*  332: 913 */     return this;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public ToStringBuilder append(String fieldName, short[] array)
/*  336:     */   {
/*  337: 925 */     this.style.append(this.buffer, fieldName, array, null);
/*  338: 926 */     return this;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public ToStringBuilder append(String fieldName, short[] array, boolean fullDetail)
/*  342:     */   {
/*  343: 945 */     this.style.append(this.buffer, fieldName, array, BooleanUtils.toBooleanObject(fullDetail));
/*  344: 946 */     return this;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public ToStringBuilder appendAsObjectToString(Object object)
/*  348:     */   {
/*  349: 959 */     ObjectUtils.identityToString(getStringBuffer(), object);
/*  350: 960 */     return this;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public ToStringBuilder appendSuper(String superToString)
/*  354:     */   {
/*  355: 978 */     if (superToString != null) {
/*  356: 979 */       this.style.appendSuper(this.buffer, superToString);
/*  357:     */     }
/*  358: 981 */     return this;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public ToStringBuilder appendToString(String toString)
/*  362:     */   {
/*  363:1012 */     if (toString != null) {
/*  364:1013 */       this.style.appendToString(this.buffer, toString);
/*  365:     */     }
/*  366:1015 */     return this;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public Object getObject()
/*  370:     */   {
/*  371:1025 */     return this.object;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public StringBuffer getStringBuffer()
/*  375:     */   {
/*  376:1034 */     return this.buffer;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public ToStringStyle getStyle()
/*  380:     */   {
/*  381:1046 */     return this.style;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public String toString()
/*  385:     */   {
/*  386:1060 */     if (getObject() == null) {
/*  387:1061 */       getStringBuffer().append(getStyle().getNullText());
/*  388:     */     } else {
/*  389:1063 */       this.style.appendEnd(getStringBuffer(), getObject());
/*  390:     */     }
/*  391:1065 */     return getStringBuffer().toString();
/*  392:     */   }
/*  393:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.ToStringBuilder
 * JD-Core Version:    0.7.0.1
 */