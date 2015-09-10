/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import org.hibernate.InstantiationException;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.engine.spi.IdentifierValue;
/*   8:    */ import org.hibernate.engine.spi.VersionValue;
/*   9:    */ import org.hibernate.property.Getter;
/*  10:    */ import org.hibernate.type.IdentifierType;
/*  11:    */ import org.hibernate.type.PrimitiveType;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ import org.hibernate.type.VersionType;
/*  14:    */ 
/*  15:    */ public class UnsavedValueFactory
/*  16:    */ {
/*  17:    */   private static Object instantiate(Constructor constructor)
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 46 */       return constructor.newInstance((Object[])null);
/*  22:    */     }
/*  23:    */     catch (Exception e)
/*  24:    */     {
/*  25: 49 */       throw new InstantiationException("could not instantiate test object", constructor.getDeclaringClass(), e);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static IdentifierValue getUnsavedIdentifierValue(String unsavedValue, Getter identifierGetter, Type identifierType, Constructor constructor)
/*  30:    */   {
/*  31: 65 */     if (unsavedValue == null)
/*  32:    */     {
/*  33: 66 */       if ((identifierGetter != null) && (constructor != null))
/*  34:    */       {
/*  35: 68 */         Serializable defaultValue = (Serializable)identifierGetter.get(instantiate(constructor));
/*  36: 69 */         return new IdentifierValue(defaultValue);
/*  37:    */       }
/*  38: 71 */       if ((identifierGetter != null) && ((identifierType instanceof PrimitiveType)))
/*  39:    */       {
/*  40: 72 */         Serializable defaultValue = ((PrimitiveType)identifierType).getDefaultValue();
/*  41: 73 */         return new IdentifierValue(defaultValue);
/*  42:    */       }
/*  43: 76 */       return IdentifierValue.NULL;
/*  44:    */     }
/*  45: 79 */     if ("null".equals(unsavedValue)) {
/*  46: 80 */       return IdentifierValue.NULL;
/*  47:    */     }
/*  48: 82 */     if ("undefined".equals(unsavedValue)) {
/*  49: 83 */       return IdentifierValue.UNDEFINED;
/*  50:    */     }
/*  51: 85 */     if ("none".equals(unsavedValue)) {
/*  52: 86 */       return IdentifierValue.NONE;
/*  53:    */     }
/*  54: 88 */     if ("any".equals(unsavedValue)) {
/*  55: 89 */       return IdentifierValue.ANY;
/*  56:    */     }
/*  57:    */     try
/*  58:    */     {
/*  59: 93 */       return new IdentifierValue((Serializable)((IdentifierType)identifierType).stringToObject(unsavedValue));
/*  60:    */     }
/*  61:    */     catch (ClassCastException cce)
/*  62:    */     {
/*  63: 96 */       throw new MappingException("Bad identifier type: " + identifierType.getName());
/*  64:    */     }
/*  65:    */     catch (Exception e)
/*  66:    */     {
/*  67: 99 */       throw new MappingException("Could not parse identifier unsaved-value: " + unsavedValue);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static VersionValue getUnsavedVersionValue(String versionUnsavedValue, Getter versionGetter, VersionType versionType, Constructor constructor)
/*  72:    */   {
/*  73:110 */     if (versionUnsavedValue == null)
/*  74:    */     {
/*  75:111 */       if (constructor != null)
/*  76:    */       {
/*  77:112 */         Object defaultValue = versionGetter.get(instantiate(constructor));
/*  78:    */         
/*  79:    */ 
/*  80:115 */         return versionType.isEqual(versionType.seed(null), defaultValue) ? VersionValue.UNDEFINED : new VersionValue(defaultValue);
/*  81:    */       }
/*  82:120 */       return VersionValue.UNDEFINED;
/*  83:    */     }
/*  84:123 */     if ("undefined".equals(versionUnsavedValue)) {
/*  85:124 */       return VersionValue.UNDEFINED;
/*  86:    */     }
/*  87:126 */     if ("null".equals(versionUnsavedValue)) {
/*  88:127 */       return VersionValue.NULL;
/*  89:    */     }
/*  90:129 */     if ("negative".equals(versionUnsavedValue)) {
/*  91:130 */       return VersionValue.NEGATIVE;
/*  92:    */     }
/*  93:134 */     throw new MappingException("Could not parse version unsaved-value: " + versionUnsavedValue);
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.UnsavedValueFactory
 * JD-Core Version:    0.7.0.1
 */