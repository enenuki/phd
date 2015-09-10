/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import java.util.UUID;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.id.uuid.StandardRandomStrategy;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.hibernate.internal.util.ReflectHelper;
/*  13:    */ import org.hibernate.type.Type;
/*  14:    */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.PassThroughTransformer;
/*  15:    */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToBytesTransformer;
/*  16:    */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToStringTransformer;
/*  17:    */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ValueTransformer;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class UUIDGenerator
/*  21:    */   implements IdentifierGenerator, Configurable
/*  22:    */ {
/*  23:    */   public static final String UUID_GEN_STRATEGY = "uuid_gen_strategy";
/*  24:    */   public static final String UUID_GEN_STRATEGY_CLASS = "uuid_gen_strategy_class";
/*  25: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, UUIDGenerator.class.getName());
/*  26:    */   private UUIDGenerationStrategy strategy;
/*  27:    */   private UUIDTypeDescriptor.ValueTransformer valueTransformer;
/*  28:    */   
/*  29:    */   public static UUIDGenerator buildSessionFactoryUniqueIdentifierGenerator()
/*  30:    */   {
/*  31: 69 */     UUIDGenerator generator = new UUIDGenerator();
/*  32: 70 */     generator.strategy = StandardRandomStrategy.INSTANCE;
/*  33: 71 */     generator.valueTransformer = UUIDTypeDescriptor.ToStringTransformer.INSTANCE;
/*  34: 72 */     return generator;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void configure(Type type, Properties params, Dialect d)
/*  38:    */     throws MappingException
/*  39:    */   {
/*  40: 77 */     this.strategy = ((UUIDGenerationStrategy)params.get("uuid_gen_strategy"));
/*  41: 78 */     if (this.strategy == null)
/*  42:    */     {
/*  43: 80 */       String strategyClassName = params.getProperty("uuid_gen_strategy_class");
/*  44: 81 */       if (strategyClassName != null) {
/*  45:    */         try
/*  46:    */         {
/*  47: 83 */           Class strategyClass = ReflectHelper.classForName(strategyClassName);
/*  48:    */           try
/*  49:    */           {
/*  50: 85 */             this.strategy = ((UUIDGenerationStrategy)strategyClass.newInstance());
/*  51:    */           }
/*  52:    */           catch (Exception ignore)
/*  53:    */           {
/*  54: 88 */             LOG.unableToInstantiateUuidGenerationStrategy(ignore);
/*  55:    */           }
/*  56:    */         }
/*  57:    */         catch (ClassNotFoundException ignore)
/*  58:    */         {
/*  59: 92 */           LOG.unableToLocateUuidGenerationStrategy(strategyClassName);
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63: 96 */     if (this.strategy == null) {
/*  64: 98 */       this.strategy = StandardRandomStrategy.INSTANCE;
/*  65:    */     }
/*  66:101 */     if (UUID.class.isAssignableFrom(type.getReturnedClass())) {
/*  67:102 */       this.valueTransformer = UUIDTypeDescriptor.PassThroughTransformer.INSTANCE;
/*  68:104 */     } else if (String.class.isAssignableFrom(type.getReturnedClass())) {
/*  69:105 */       this.valueTransformer = UUIDTypeDescriptor.ToStringTransformer.INSTANCE;
/*  70:107 */     } else if ([B.class.isAssignableFrom(type.getReturnedClass())) {
/*  71:108 */       this.valueTransformer = UUIDTypeDescriptor.ToBytesTransformer.INSTANCE;
/*  72:    */     } else {
/*  73:111 */       throw new HibernateException("Unanticipated return type [" + type.getReturnedClass().getName() + "] for UUID conversion");
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Serializable generate(SessionImplementor session, Object object)
/*  78:    */     throws HibernateException
/*  79:    */   {
/*  80:116 */     return this.valueTransformer.transform(this.strategy.generateUUID(session));
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.UUIDGenerator
 * JD-Core Version:    0.7.0.1
 */