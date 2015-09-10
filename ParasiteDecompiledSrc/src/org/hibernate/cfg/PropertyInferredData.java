/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import javax.persistence.Access;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.annotations.Target;
/*   6:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*   7:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   8:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*   9:    */ 
/*  10:    */ public class PropertyInferredData
/*  11:    */   implements PropertyData
/*  12:    */ {
/*  13:    */   private final AccessType defaultAccess;
/*  14:    */   private final XProperty property;
/*  15:    */   private final ReflectionManager reflectionManager;
/*  16:    */   private final XClass declaringClass;
/*  17:    */   
/*  18:    */   public String toString()
/*  19:    */   {
/*  20: 48 */     StringBuilder sb = new StringBuilder();
/*  21: 49 */     sb.append("PropertyInferredData");
/*  22: 50 */     sb.append("{property=").append(this.property);
/*  23: 51 */     sb.append(", declaringClass=").append(this.declaringClass);
/*  24: 52 */     sb.append('}');
/*  25: 53 */     return sb.toString();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public PropertyInferredData(XClass declaringClass, XProperty property, String propertyAccessor, ReflectionManager reflectionManager)
/*  29:    */   {
/*  30: 60 */     this.declaringClass = declaringClass;
/*  31: 61 */     this.property = property;
/*  32: 62 */     this.defaultAccess = AccessType.getAccessStrategy(propertyAccessor);
/*  33: 63 */     this.reflectionManager = reflectionManager;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AccessType getDefaultAccess()
/*  37:    */     throws MappingException
/*  38:    */   {
/*  39: 67 */     AccessType accessType = this.defaultAccess;
/*  40:    */     
/*  41: 69 */     AccessType hibernateAccessType = AccessType.DEFAULT;
/*  42: 70 */     AccessType jpaAccessType = AccessType.DEFAULT;
/*  43:    */     
/*  44: 72 */     org.hibernate.annotations.AccessType accessTypeAnnotation = (org.hibernate.annotations.AccessType)this.property.getAnnotation(org.hibernate.annotations.AccessType.class);
/*  45: 73 */     if (accessTypeAnnotation != null) {
/*  46: 74 */       hibernateAccessType = AccessType.getAccessStrategy(accessTypeAnnotation.value());
/*  47:    */     }
/*  48: 77 */     Access access = (Access)this.property.getAnnotation(Access.class);
/*  49: 78 */     if (access != null) {
/*  50: 79 */       jpaAccessType = AccessType.getAccessStrategy(access.value());
/*  51:    */     }
/*  52: 82 */     if ((hibernateAccessType != AccessType.DEFAULT) && (jpaAccessType != AccessType.DEFAULT) && (hibernateAccessType != jpaAccessType))
/*  53:    */     {
/*  54: 86 */       StringBuilder builder = new StringBuilder();
/*  55: 87 */       builder.append(this.property.toString());
/*  56: 88 */       builder.append(" defines @AccessType and @Access with contradicting values. Use of @Access only is recommended.");
/*  57:    */       
/*  58:    */ 
/*  59: 91 */       throw new MappingException(builder.toString());
/*  60:    */     }
/*  61: 94 */     if (hibernateAccessType != AccessType.DEFAULT) {
/*  62: 95 */       accessType = hibernateAccessType;
/*  63: 97 */     } else if (jpaAccessType != AccessType.DEFAULT) {
/*  64: 98 */       accessType = jpaAccessType;
/*  65:    */     }
/*  66:100 */     return accessType;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getPropertyName()
/*  70:    */     throws MappingException
/*  71:    */   {
/*  72:104 */     return this.property.getName();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public XClass getPropertyClass()
/*  76:    */     throws MappingException
/*  77:    */   {
/*  78:108 */     if (this.property.isAnnotationPresent(Target.class)) {
/*  79:109 */       return this.reflectionManager.toXClass(((Target)this.property.getAnnotation(Target.class)).value());
/*  80:    */     }
/*  81:112 */     return this.property.getType();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public XClass getClassOrElement()
/*  85:    */     throws MappingException
/*  86:    */   {
/*  87:117 */     if (this.property.isAnnotationPresent(Target.class)) {
/*  88:118 */       return this.reflectionManager.toXClass(((Target)this.property.getAnnotation(Target.class)).value());
/*  89:    */     }
/*  90:121 */     return this.property.getClassOrElementClass();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getClassOrElementName()
/*  94:    */     throws MappingException
/*  95:    */   {
/*  96:126 */     return getClassOrElement().getName();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getTypeName()
/* 100:    */     throws MappingException
/* 101:    */   {
/* 102:130 */     return getPropertyClass().getName();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public XProperty getProperty()
/* 106:    */   {
/* 107:134 */     return this.property;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public XClass getDeclaringClass()
/* 111:    */   {
/* 112:138 */     return this.declaringClass;
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyInferredData
 * JD-Core Version:    0.7.0.1
 */