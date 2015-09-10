/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.TemporalType;
/*   6:    */ import org.hibernate.AnnotationException;
/*   7:    */ import org.hibernate.AssertionFailure;
/*   8:    */ import org.hibernate.cfg.NotYetImplementedException;
/*   9:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  10:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  11:    */ import org.hibernate.metamodel.source.annotations.attribute.MappedAttribute;
/*  12:    */ import org.hibernate.type.CalendarDateType;
/*  13:    */ import org.hibernate.type.CalendarType;
/*  14:    */ import org.hibernate.type.DateType;
/*  15:    */ import org.hibernate.type.StandardBasicTypes;
/*  16:    */ import org.hibernate.type.TimeType;
/*  17:    */ import org.hibernate.type.TimestampType;
/*  18:    */ import org.jboss.jandex.AnnotationInstance;
/*  19:    */ 
/*  20:    */ public class TemporalTypeResolver
/*  21:    */   extends AbstractAttributeTypeResolver
/*  22:    */ {
/*  23:    */   private final MappedAttribute mappedAttribute;
/*  24:    */   private final boolean isMapKey;
/*  25:    */   
/*  26:    */   public TemporalTypeResolver(MappedAttribute mappedAttribute)
/*  27:    */   {
/*  28: 49 */     if (mappedAttribute == null) {
/*  29: 50 */       throw new AssertionFailure("MappedAttribute is null");
/*  30:    */     }
/*  31: 52 */     this.mappedAttribute = mappedAttribute;
/*  32: 53 */     this.isMapKey = false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String resolveHibernateTypeName(AnnotationInstance temporalAnnotation)
/*  36:    */   {
/*  37: 59 */     if (isTemporalType(this.mappedAttribute.getAttributeType()))
/*  38:    */     {
/*  39: 60 */       if (temporalAnnotation == null) {
/*  40: 62 */         throw new AnnotationException("Attribute " + this.mappedAttribute.getName() + " is a Temporal type, but no @Temporal annotation found.");
/*  41:    */       }
/*  42: 64 */       TemporalType temporalType = (TemporalType)JandexHelper.getEnumValue(temporalAnnotation, "value", TemporalType.class);
/*  43: 65 */       boolean isDate = Date.class.isAssignableFrom(this.mappedAttribute.getAttributeType());
/*  44:    */       String type;
/*  45: 67 */       switch (1.$SwitchMap$javax$persistence$TemporalType[temporalType.ordinal()])
/*  46:    */       {
/*  47:    */       case 1: 
/*  48: 69 */         type = isDate ? StandardBasicTypes.DATE.getName() : StandardBasicTypes.CALENDAR_DATE.getName();
/*  49: 70 */         break;
/*  50:    */       case 2: 
/*  51: 72 */         type = StandardBasicTypes.TIME.getName();
/*  52: 73 */         if (!isDate) {
/*  53: 74 */           throw new NotYetImplementedException("Calendar cannot persist TIME only");
/*  54:    */         }
/*  55:    */         break;
/*  56:    */       case 3: 
/*  57: 78 */         type = isDate ? StandardBasicTypes.TIMESTAMP.getName() : StandardBasicTypes.CALENDAR.getName();
/*  58: 79 */         break;
/*  59:    */       default: 
/*  60: 81 */         throw new AssertionFailure("Unknown temporal type: " + temporalType);
/*  61:    */       }
/*  62: 83 */       return type;
/*  63:    */     }
/*  64: 86 */     if (temporalAnnotation != null) {
/*  65: 87 */       throw new AnnotationException("@Temporal should only be set on a java.util.Date or java.util.Calendar property: " + this.mappedAttribute.getName());
/*  66:    */     }
/*  67: 93 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected AnnotationInstance getTypeDeterminingAnnotationInstance()
/*  71:    */   {
/*  72: 98 */     return JandexHelper.getSingleAnnotation(this.mappedAttribute.annotations(), JPADotNames.TEMPORAL);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static boolean isTemporalType(Class type)
/*  76:    */   {
/*  77:105 */     return (Date.class.isAssignableFrom(type)) || (Calendar.class.isAssignableFrom(type));
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.TemporalTypeResolver
 * JD-Core Version:    0.7.0.1
 */