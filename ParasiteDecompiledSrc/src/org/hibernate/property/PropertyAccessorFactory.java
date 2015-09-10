/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.internal.util.ReflectHelper;
/*   7:    */ import org.hibernate.internal.util.StringHelper;
/*   8:    */ import org.hibernate.mapping.Property;
/*   9:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  10:    */ 
/*  11:    */ public final class PropertyAccessorFactory
/*  12:    */ {
/*  13: 43 */   private static final PropertyAccessor BASIC_PROPERTY_ACCESSOR = new BasicPropertyAccessor();
/*  14: 44 */   private static final PropertyAccessor DIRECT_PROPERTY_ACCESSOR = new DirectPropertyAccessor();
/*  15: 45 */   private static final PropertyAccessor MAP_ACCESSOR = new MapAccessor();
/*  16: 46 */   private static final PropertyAccessor NOOP_ACCESSOR = new NoopAccessor();
/*  17: 47 */   private static final PropertyAccessor EMBEDDED_PROPERTY_ACCESSOR = new EmbeddedPropertyAccessor();
/*  18:    */   
/*  19:    */   public static PropertyAccessor getPropertyAccessor(Property property, EntityMode mode)
/*  20:    */     throws MappingException
/*  21:    */   {
/*  22: 73 */     if ((null == mode) || (EntityMode.POJO.equals(mode))) {
/*  23: 74 */       return getPojoPropertyAccessor(property.getPropertyAccessorName());
/*  24:    */     }
/*  25: 76 */     if (EntityMode.MAP.equals(mode)) {
/*  26: 77 */       return getDynamicMapPropertyAccessor();
/*  27:    */     }
/*  28: 80 */     throw new MappingException("Unknown entity mode [" + mode + "]");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static PropertyAccessor getPropertyAccessor(AttributeBinding property, EntityMode mode)
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 95 */     if ((null == mode) || (EntityMode.POJO.equals(mode))) {
/*  35: 96 */       return getPojoPropertyAccessor(property.getPropertyAccessorName());
/*  36:    */     }
/*  37: 98 */     if (EntityMode.MAP.equals(mode)) {
/*  38: 99 */       return getDynamicMapPropertyAccessor();
/*  39:    */     }
/*  40:102 */     throw new MappingException("Unknown entity mode [" + mode + "]");
/*  41:    */   }
/*  42:    */   
/*  43:    */   private static PropertyAccessor getPojoPropertyAccessor(String pojoAccessorStrategy)
/*  44:    */   {
/*  45:113 */     if ((StringHelper.isEmpty(pojoAccessorStrategy)) || ("property".equals(pojoAccessorStrategy))) {
/*  46:114 */       return BASIC_PROPERTY_ACCESSOR;
/*  47:    */     }
/*  48:116 */     if ("field".equals(pojoAccessorStrategy)) {
/*  49:117 */       return DIRECT_PROPERTY_ACCESSOR;
/*  50:    */     }
/*  51:119 */     if ("embedded".equals(pojoAccessorStrategy)) {
/*  52:120 */       return EMBEDDED_PROPERTY_ACCESSOR;
/*  53:    */     }
/*  54:122 */     if ("noop".equals(pojoAccessorStrategy)) {
/*  55:123 */       return NOOP_ACCESSOR;
/*  56:    */     }
/*  57:126 */     return resolveCustomAccessor(pojoAccessorStrategy);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static PropertyAccessor getDynamicMapPropertyAccessor()
/*  61:    */     throws MappingException
/*  62:    */   {
/*  63:131 */     return MAP_ACCESSOR;
/*  64:    */   }
/*  65:    */   
/*  66:    */   private static PropertyAccessor resolveCustomAccessor(String accessorName)
/*  67:    */   {
/*  68:    */     Class accessorClass;
/*  69:    */     try
/*  70:    */     {
/*  71:137 */       accessorClass = ReflectHelper.classForName(accessorName);
/*  72:    */     }
/*  73:    */     catch (ClassNotFoundException cnfe)
/*  74:    */     {
/*  75:140 */       throw new MappingException("could not find PropertyAccessor class: " + accessorName, cnfe);
/*  76:    */     }
/*  77:    */     try
/*  78:    */     {
/*  79:143 */       return (PropertyAccessor)accessorClass.newInstance();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:146 */       throw new MappingException("could not instantiate PropertyAccessor class: " + accessorName, e);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static PropertyAccessor getPropertyAccessor(Class optionalClass, String type)
/*  88:    */     throws MappingException
/*  89:    */   {
/*  90:154 */     if (type == null) {
/*  91:154 */       type = (optionalClass == null) || (optionalClass == Map.class) ? "map" : "property";
/*  92:    */     }
/*  93:155 */     return getPropertyAccessor(type);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static PropertyAccessor getPropertyAccessor(String type)
/*  97:    */     throws MappingException
/*  98:    */   {
/*  99:160 */     if ((type == null) || ("property".equals(type))) {
/* 100:160 */       return BASIC_PROPERTY_ACCESSOR;
/* 101:    */     }
/* 102:161 */     if ("field".equals(type)) {
/* 103:161 */       return DIRECT_PROPERTY_ACCESSOR;
/* 104:    */     }
/* 105:162 */     if ("map".equals(type)) {
/* 106:162 */       return MAP_ACCESSOR;
/* 107:    */     }
/* 108:163 */     if ("embedded".equals(type)) {
/* 109:163 */       return EMBEDDED_PROPERTY_ACCESSOR;
/* 110:    */     }
/* 111:164 */     if ("noop".equals(type)) {
/* 112:164 */       return NOOP_ACCESSOR;
/* 113:    */     }
/* 114:166 */     return resolveCustomAccessor(type);
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.PropertyAccessorFactory
 * JD-Core Version:    0.7.0.1
 */