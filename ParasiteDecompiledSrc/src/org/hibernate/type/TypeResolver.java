/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.internal.util.ReflectHelper;
/*   8:    */ import org.hibernate.usertype.CompositeUserType;
/*   9:    */ import org.hibernate.usertype.UserType;
/*  10:    */ 
/*  11:    */ public class TypeResolver
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private final BasicTypeRegistry basicTypeRegistry;
/*  15:    */   private final TypeFactory typeFactory;
/*  16:    */   
/*  17:    */   public TypeResolver()
/*  18:    */   {
/*  19: 46 */     this(new BasicTypeRegistry(), new TypeFactory());
/*  20:    */   }
/*  21:    */   
/*  22:    */   public TypeResolver(BasicTypeRegistry basicTypeRegistry, TypeFactory typeFactory)
/*  23:    */   {
/*  24: 50 */     this.basicTypeRegistry = basicTypeRegistry;
/*  25: 51 */     this.typeFactory = typeFactory;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TypeResolver scope(SessionFactoryImplementor factory)
/*  29:    */   {
/*  30: 55 */     this.typeFactory.injectSessionFactory(factory);
/*  31: 56 */     return new TypeResolver(this.basicTypeRegistry.shallowCopy(), this.typeFactory);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void registerTypeOverride(BasicType type)
/*  35:    */   {
/*  36: 60 */     this.basicTypeRegistry.register(type);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void registerTypeOverride(UserType type, String[] keys)
/*  40:    */   {
/*  41: 64 */     this.basicTypeRegistry.register(type, keys);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void registerTypeOverride(CompositeUserType type, String[] keys)
/*  45:    */   {
/*  46: 68 */     this.basicTypeRegistry.register(type, keys);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public TypeFactory getTypeFactory()
/*  50:    */   {
/*  51: 72 */     return this.typeFactory;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public BasicType basic(String name)
/*  55:    */   {
/*  56: 83 */     return this.basicTypeRegistry.getRegisteredType(name);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Type heuristicType(String typeName)
/*  60:    */     throws MappingException
/*  61:    */   {
/*  62: 96 */     return heuristicType(typeName, null);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Type heuristicType(String typeName, Properties parameters)
/*  66:    */     throws MappingException
/*  67:    */   {
/*  68:122 */     Type type = basic(typeName);
/*  69:123 */     if (type != null) {
/*  70:124 */       return type;
/*  71:    */     }
/*  72:    */     try
/*  73:    */     {
/*  74:128 */       Class typeClass = ReflectHelper.classForName(typeName);
/*  75:129 */       if (typeClass != null) {
/*  76:130 */         return this.typeFactory.byClass(typeClass, parameters);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (ClassNotFoundException ignore) {}
/*  80:136 */     return null;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TypeResolver
 * JD-Core Version:    0.7.0.1
 */