/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public final class AnnotationInstance
/*   9:    */ {
/*  10: 44 */   private static final AnnotationValue[] ANNOTATION_VALUES_TYPE = new AnnotationValue[0];
/*  11:    */   private final DotName name;
/*  12:    */   private final AnnotationTarget target;
/*  13:    */   private final AnnotationValue[] values;
/*  14:    */   
/*  15:    */   AnnotationInstance(DotName name, AnnotationTarget target, AnnotationValue[] values)
/*  16:    */   {
/*  17: 51 */     this.name = name;
/*  18: 52 */     this.target = target;
/*  19: 53 */     this.values = (values.length > 0 ? values : AnnotationValue.EMPTY_VALUE_ARRAY);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static final AnnotationInstance create(DotName name, AnnotationTarget target, AnnotationValue[] values)
/*  23:    */   {
/*  24: 65 */     if (name == null) {
/*  25: 66 */       throw new IllegalArgumentException("Name can't be null");
/*  26:    */     }
/*  27: 68 */     if (values == null) {
/*  28: 69 */       throw new IllegalArgumentException("Values can't be null");
/*  29:    */     }
/*  30: 71 */     values = (AnnotationValue[])values.clone();
/*  31:    */     
/*  32:    */ 
/*  33: 74 */     Arrays.sort(values, new Comparator()
/*  34:    */     {
/*  35:    */       public int compare(AnnotationValue o1, AnnotationValue o2)
/*  36:    */       {
/*  37: 76 */         return o1.name().compareTo(o2.name());
/*  38:    */       }
/*  39: 79 */     });
/*  40: 80 */     return new AnnotationInstance(name, target, values);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static final AnnotationInstance create(DotName name, AnnotationTarget target, List<AnnotationValue> values)
/*  44:    */   {
/*  45: 92 */     if (name == null) {
/*  46: 93 */       throw new IllegalArgumentException("Name can't be null");
/*  47:    */     }
/*  48: 95 */     if (values == null) {
/*  49: 96 */       throw new IllegalArgumentException("Values can't be null");
/*  50:    */     }
/*  51: 98 */     return create(name, target, (AnnotationValue[])values.toArray(ANNOTATION_VALUES_TYPE));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public DotName name()
/*  55:    */   {
/*  56:107 */     return this.name;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public AnnotationTarget target()
/*  60:    */   {
/*  61:119 */     return this.target;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AnnotationValue value(final String name)
/*  65:    */   {
/*  66:132 */     int result = Arrays.binarySearch(this.values, name, new Comparator()
/*  67:    */     {
/*  68:    */       public int compare(Object o1, Object o2)
/*  69:    */       {
/*  70:134 */         return ((AnnotationValue)o1).name().compareTo(name);
/*  71:    */       }
/*  72:136 */     });
/*  73:137 */     return result >= 0 ? this.values[result] : null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AnnotationValue value()
/*  77:    */   {
/*  78:147 */     return value("value");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<AnnotationValue> values()
/*  82:    */   {
/*  83:159 */     return Collections.unmodifiableList(Arrays.asList(this.values));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String toString()
/*  87:    */   {
/*  88:163 */     StringBuilder builder = new StringBuilder("@").append(this.name).append("(");
/*  89:164 */     for (int i = 0; i < this.values.length; i++)
/*  90:    */     {
/*  91:165 */       builder.append(this.values[i]);
/*  92:166 */       if (i < this.values.length - 1) {
/*  93:167 */         builder.append(",");
/*  94:    */       }
/*  95:    */     }
/*  96:169 */     builder.append(')');
/*  97:170 */     if (this.target != null) {
/*  98:171 */       builder.append(" on ").append(this.target);
/*  99:    */     }
/* 100:173 */     return builder.toString();
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.AnnotationInstance
 * JD-Core Version:    0.7.0.1
 */