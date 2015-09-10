/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import org.hibernate.AssertionFailure;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*   6:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*   7:    */ import org.jboss.jandex.AnnotationInstance;
/*   8:    */ import org.jboss.jandex.DotName;
/*   9:    */ 
/*  10:    */ public class AttributeOverride
/*  11:    */ {
/*  12:    */   private static final String PROPERTY_PATH_SEPARATOR = ".";
/*  13:    */   private final ColumnValues columnValues;
/*  14:    */   private final String attributePath;
/*  15:    */   
/*  16:    */   public AttributeOverride(AnnotationInstance attributeOverrideAnnotation)
/*  17:    */   {
/*  18: 23 */     this(null, attributeOverrideAnnotation);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public AttributeOverride(String prefix, AnnotationInstance attributeOverrideAnnotation)
/*  22:    */   {
/*  23: 27 */     if (attributeOverrideAnnotation == null) {
/*  24: 28 */       throw new IllegalArgumentException("An AnnotationInstance needs to be passed");
/*  25:    */     }
/*  26: 31 */     if (!JPADotNames.ATTRIBUTE_OVERRIDE.equals(attributeOverrideAnnotation.name())) {
/*  27: 32 */       throw new AssertionFailure("A @AttributeOverride annotation needs to be passed to the constructor");
/*  28:    */     }
/*  29: 35 */     this.columnValues = new ColumnValues((AnnotationInstance)JandexHelper.getValue(attributeOverrideAnnotation, "column", AnnotationInstance.class));
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36: 42 */     this.attributePath = createAttributePath(prefix, (String)JandexHelper.getValue(attributeOverrideAnnotation, "name", String.class));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ColumnValues getColumnValues()
/*  40:    */   {
/*  41: 49 */     return this.columnValues;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getAttributePath()
/*  45:    */   {
/*  46: 53 */     return this.attributePath;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toString()
/*  50:    */   {
/*  51: 58 */     StringBuilder sb = new StringBuilder();
/*  52: 59 */     sb.append("AttributeOverride");
/*  53: 60 */     sb.append("{columnValues=").append(this.columnValues);
/*  54: 61 */     sb.append(", attributePath='").append(this.attributePath).append('\'');
/*  55: 62 */     sb.append('}');
/*  56: 63 */     return sb.toString();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean equals(Object o)
/*  60:    */   {
/*  61: 68 */     if (this == o) {
/*  62: 69 */       return true;
/*  63:    */     }
/*  64: 71 */     if ((o == null) || (getClass() != o.getClass())) {
/*  65: 72 */       return false;
/*  66:    */     }
/*  67: 75 */     AttributeOverride that = (AttributeOverride)o;
/*  68: 77 */     if (this.attributePath != null ? !this.attributePath.equals(that.attributePath) : that.attributePath != null) {
/*  69: 78 */       return false;
/*  70:    */     }
/*  71: 80 */     if (this.columnValues != null ? !this.columnValues.equals(that.columnValues) : that.columnValues != null) {
/*  72: 81 */       return false;
/*  73:    */     }
/*  74: 84 */     return true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int hashCode()
/*  78:    */   {
/*  79: 89 */     int result = this.columnValues != null ? this.columnValues.hashCode() : 0;
/*  80: 90 */     result = 31 * result + (this.attributePath != null ? this.attributePath.hashCode() : 0);
/*  81: 91 */     return result;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private String createAttributePath(String prefix, String name)
/*  85:    */   {
/*  86: 95 */     String path = "";
/*  87: 96 */     if (StringHelper.isNotEmpty(prefix)) {
/*  88: 97 */       path = path + prefix;
/*  89:    */     }
/*  90: 99 */     if ((StringHelper.isNotEmpty(path)) && (!path.endsWith("."))) {
/*  91:100 */       path = path + ".";
/*  92:    */     }
/*  93:102 */     path = path + name;
/*  94:103 */     return path;
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.AttributeOverride
 * JD-Core Version:    0.7.0.1
 */