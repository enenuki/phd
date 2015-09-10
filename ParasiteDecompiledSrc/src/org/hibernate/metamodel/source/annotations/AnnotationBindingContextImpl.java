/*   1:    */ package org.hibernate.metamodel.source.annotations;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.MemberResolver;
/*   4:    */ import com.fasterxml.classmate.ResolvedType;
/*   5:    */ import com.fasterxml.classmate.ResolvedTypeWithMembers;
/*   6:    */ import com.fasterxml.classmate.TypeResolver;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.cfg.NamingStrategy;
/*  10:    */ import org.hibernate.internal.util.Value;
/*  11:    */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*  12:    */ import org.hibernate.metamodel.domain.Type;
/*  13:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  14:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  15:    */ import org.hibernate.service.ServiceRegistry;
/*  16:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  17:    */ import org.jboss.jandex.ClassInfo;
/*  18:    */ import org.jboss.jandex.DotName;
/*  19:    */ import org.jboss.jandex.Index;
/*  20:    */ 
/*  21:    */ public class AnnotationBindingContextImpl
/*  22:    */   implements AnnotationBindingContext
/*  23:    */ {
/*  24:    */   private final MetadataImplementor metadata;
/*  25:    */   private final Value<ClassLoaderService> classLoaderService;
/*  26:    */   private final Index index;
/*  27: 52 */   private final TypeResolver typeResolver = new TypeResolver();
/*  28: 53 */   private final Map<Class<?>, ResolvedType> resolvedTypeCache = new HashMap();
/*  29:    */   
/*  30:    */   public AnnotationBindingContextImpl(MetadataImplementor metadata, Index index)
/*  31:    */   {
/*  32: 56 */     this.metadata = metadata;
/*  33: 57 */     this.classLoaderService = new Value(new Value.DeferredInitializer()
/*  34:    */     {
/*  35:    */       public ClassLoaderService initialize()
/*  36:    */       {
/*  37: 61 */         return (ClassLoaderService)AnnotationBindingContextImpl.this.metadata.getServiceRegistry().getService(ClassLoaderService.class);
/*  38:    */       }
/*  39: 66 */     });
/*  40: 67 */     this.index = index;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Index getIndex()
/*  44:    */   {
/*  45: 72 */     return this.index;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ClassInfo getClassInfo(String name)
/*  49:    */   {
/*  50: 77 */     DotName dotName = DotName.createSimple(name);
/*  51: 78 */     return this.index.getClassByName(dotName);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void resolveAllTypes(String className)
/*  55:    */   {
/*  56: 84 */     Class<?> clazz = ((ClassLoaderService)this.classLoaderService.getValue()).classForName(className);
/*  57: 85 */     ResolvedType resolvedType = this.typeResolver.resolve(clazz);
/*  58: 86 */     while (resolvedType != null)
/*  59:    */     {
/*  60: 88 */       this.resolvedTypeCache.put(clazz, resolvedType);
/*  61: 89 */       resolvedType = resolvedType.getParentClass();
/*  62: 90 */       if (resolvedType != null) {
/*  63: 91 */         clazz = resolvedType.getErasedType();
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ResolvedType getResolvedType(Class<?> clazz)
/*  69:    */   {
/*  70: 99 */     return (ResolvedType)this.resolvedTypeCache.get(clazz);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ResolvedTypeWithMembers resolveMemberTypes(ResolvedType type)
/*  74:    */   {
/*  75:105 */     MemberResolver memberResolver = new MemberResolver(this.typeResolver);
/*  76:106 */     return memberResolver.resolve(type, null, null);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ServiceRegistry getServiceRegistry()
/*  80:    */   {
/*  81:111 */     return getMetadataImplementor().getServiceRegistry();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public NamingStrategy getNamingStrategy()
/*  85:    */   {
/*  86:116 */     return this.metadata.getNamingStrategy();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public MappingDefaults getMappingDefaults()
/*  90:    */   {
/*  91:121 */     return this.metadata.getMappingDefaults();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public MetadataImplementor getMetadataImplementor()
/*  95:    */   {
/*  96:126 */     return this.metadata;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public <T> Class<T> locateClassByName(String name)
/* 100:    */   {
/* 101:131 */     return ((ClassLoaderService)this.classLoaderService.getValue()).classForName(name);
/* 102:    */   }
/* 103:    */   
/* 104:134 */   private Map<String, Type> nameToJavaTypeMap = new HashMap();
/* 105:    */   
/* 106:    */   public Type makeJavaType(String className)
/* 107:    */   {
/* 108:138 */     Type javaType = (Type)this.nameToJavaTypeMap.get(className);
/* 109:139 */     if (javaType == null)
/* 110:    */     {
/* 111:140 */       javaType = this.metadata.makeJavaType(className);
/* 112:141 */       this.nameToJavaTypeMap.put(className, javaType);
/* 113:    */     }
/* 114:143 */     return javaType;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Value<Class<?>> makeClassReference(String className)
/* 118:    */   {
/* 119:148 */     return new Value(locateClassByName(className));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String qualifyClassName(String name)
/* 123:    */   {
/* 124:153 */     return name;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isGloballyQuotedIdentifiers()
/* 128:    */   {
/* 129:158 */     return this.metadata.isGloballyQuotedIdentifiers();
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.AnnotationBindingContextImpl
 * JD-Core Version:    0.7.0.1
 */