/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  5:   */ import org.hibernate.annotations.common.reflection.XProperty;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ 
/*  8:   */ public class WrappedInferredData
/*  9:   */   implements PropertyData
/* 10:   */ {
/* 11:   */   private PropertyData wrappedInferredData;
/* 12:   */   private String propertyName;
/* 13:   */   
/* 14:   */   public XClass getClassOrElement()
/* 15:   */     throws MappingException
/* 16:   */   {
/* 17:38 */     return this.wrappedInferredData.getClassOrElement();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getClassOrElementName()
/* 21:   */     throws MappingException
/* 22:   */   {
/* 23:42 */     return this.wrappedInferredData.getClassOrElementName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public AccessType getDefaultAccess()
/* 27:   */   {
/* 28:46 */     return this.wrappedInferredData.getDefaultAccess();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public XProperty getProperty()
/* 32:   */   {
/* 33:50 */     return this.wrappedInferredData.getProperty();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public XClass getDeclaringClass()
/* 37:   */   {
/* 38:54 */     return this.wrappedInferredData.getDeclaringClass();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public XClass getPropertyClass()
/* 42:   */     throws MappingException
/* 43:   */   {
/* 44:58 */     return this.wrappedInferredData.getPropertyClass();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getPropertyName()
/* 48:   */     throws MappingException
/* 49:   */   {
/* 50:62 */     return this.propertyName;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getTypeName()
/* 54:   */     throws MappingException
/* 55:   */   {
/* 56:66 */     return this.wrappedInferredData.getTypeName();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public WrappedInferredData(PropertyData inferredData, String suffix)
/* 60:   */   {
/* 61:70 */     this.wrappedInferredData = inferredData;
/* 62:71 */     this.propertyName = StringHelper.qualify(inferredData.getPropertyName(), suffix);
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.WrappedInferredData
 * JD-Core Version:    0.7.0.1
 */