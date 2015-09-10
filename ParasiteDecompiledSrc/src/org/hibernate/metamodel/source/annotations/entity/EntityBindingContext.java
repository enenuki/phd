/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.ResolvedTypeWithMembers;
/*   5:    */ import org.hibernate.cfg.NamingStrategy;
/*   6:    */ import org.hibernate.internal.jaxb.Origin;
/*   7:    */ import org.hibernate.internal.jaxb.SourceType;
/*   8:    */ import org.hibernate.internal.util.Value;
/*   9:    */ import org.hibernate.metamodel.domain.Type;
/*  10:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  11:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  12:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  13:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  14:    */ import org.hibernate.service.ServiceRegistry;
/*  15:    */ import org.jboss.jandex.ClassInfo;
/*  16:    */ import org.jboss.jandex.Index;
/*  17:    */ 
/*  18:    */ public class EntityBindingContext
/*  19:    */   implements LocalBindingContext, AnnotationBindingContext
/*  20:    */ {
/*  21:    */   private final AnnotationBindingContext contextDelegate;
/*  22:    */   private final Origin origin;
/*  23:    */   
/*  24:    */   public EntityBindingContext(AnnotationBindingContext contextDelegate, ConfiguredClass source)
/*  25:    */   {
/*  26: 52 */     this.contextDelegate = contextDelegate;
/*  27: 53 */     this.origin = new Origin(SourceType.ANNOTATION, source.getName());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Origin getOrigin()
/*  31:    */   {
/*  32: 58 */     return this.origin;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ServiceRegistry getServiceRegistry()
/*  36:    */   {
/*  37: 63 */     return this.contextDelegate.getServiceRegistry();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public NamingStrategy getNamingStrategy()
/*  41:    */   {
/*  42: 68 */     return this.contextDelegate.getNamingStrategy();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MappingDefaults getMappingDefaults()
/*  46:    */   {
/*  47: 73 */     return this.contextDelegate.getMappingDefaults();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public MetadataImplementor getMetadataImplementor()
/*  51:    */   {
/*  52: 78 */     return this.contextDelegate.getMetadataImplementor();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public <T> Class<T> locateClassByName(String name)
/*  56:    */   {
/*  57: 83 */     return this.contextDelegate.locateClassByName(name);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Type makeJavaType(String className)
/*  61:    */   {
/*  62: 88 */     return this.contextDelegate.makeJavaType(className);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isGloballyQuotedIdentifiers()
/*  66:    */   {
/*  67: 93 */     return this.contextDelegate.isGloballyQuotedIdentifiers();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Value<Class<?>> makeClassReference(String className)
/*  71:    */   {
/*  72: 98 */     return this.contextDelegate.makeClassReference(className);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String qualifyClassName(String name)
/*  76:    */   {
/*  77:103 */     return this.contextDelegate.qualifyClassName(name);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Index getIndex()
/*  81:    */   {
/*  82:108 */     return this.contextDelegate.getIndex();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ClassInfo getClassInfo(String name)
/*  86:    */   {
/*  87:113 */     return this.contextDelegate.getClassInfo(name);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void resolveAllTypes(String className)
/*  91:    */   {
/*  92:118 */     this.contextDelegate.resolveAllTypes(className);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ResolvedType getResolvedType(Class<?> clazz)
/*  96:    */   {
/*  97:123 */     return this.contextDelegate.getResolvedType(clazz);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ResolvedTypeWithMembers resolveMemberTypes(ResolvedType type)
/* 101:    */   {
/* 102:128 */     return this.contextDelegate.resolveMemberTypes(type);
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.EntityBindingContext
 * JD-Core Version:    0.7.0.1
 */