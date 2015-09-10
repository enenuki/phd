/*   1:    */ package org.hibernate.id.factory.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import java.util.concurrent.ConcurrentHashMap;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   9:    */ import org.hibernate.id.Assigned;
/*  10:    */ import org.hibernate.id.Configurable;
/*  11:    */ import org.hibernate.id.ForeignGenerator;
/*  12:    */ import org.hibernate.id.GUIDGenerator;
/*  13:    */ import org.hibernate.id.IdentifierGenerator;
/*  14:    */ import org.hibernate.id.IdentityGenerator;
/*  15:    */ import org.hibernate.id.IncrementGenerator;
/*  16:    */ import org.hibernate.id.SelectGenerator;
/*  17:    */ import org.hibernate.id.SequenceGenerator;
/*  18:    */ import org.hibernate.id.SequenceHiLoGenerator;
/*  19:    */ import org.hibernate.id.SequenceIdentityGenerator;
/*  20:    */ import org.hibernate.id.TableHiLoGenerator;
/*  21:    */ import org.hibernate.id.UUIDGenerator;
/*  22:    */ import org.hibernate.id.UUIDHexGenerator;
/*  23:    */ import org.hibernate.id.enhanced.SequenceStyleGenerator;
/*  24:    */ import org.hibernate.id.enhanced.TableGenerator;
/*  25:    */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*  26:    */ import org.hibernate.internal.CoreMessageLogger;
/*  27:    */ import org.hibernate.internal.util.ReflectHelper;
/*  28:    */ import org.hibernate.service.spi.ServiceRegistryAwareService;
/*  29:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  30:    */ import org.hibernate.type.Type;
/*  31:    */ import org.jboss.logging.Logger;
/*  32:    */ 
/*  33:    */ public class DefaultIdentifierGeneratorFactory
/*  34:    */   implements MutableIdentifierGeneratorFactory, Serializable, ServiceRegistryAwareService
/*  35:    */ {
/*  36: 65 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultIdentifierGeneratorFactory.class.getName());
/*  37:    */   private transient Dialect dialect;
/*  38: 69 */   private ConcurrentHashMap<String, Class> generatorStrategyToClassNameMap = new ConcurrentHashMap();
/*  39:    */   
/*  40:    */   public DefaultIdentifierGeneratorFactory()
/*  41:    */   {
/*  42: 75 */     register("uuid2", UUIDGenerator.class);
/*  43: 76 */     register("guid", GUIDGenerator.class);
/*  44: 77 */     register("uuid", UUIDHexGenerator.class);
/*  45: 78 */     register("uuid.hex", UUIDHexGenerator.class);
/*  46: 79 */     register("hilo", TableHiLoGenerator.class);
/*  47: 80 */     register("assigned", Assigned.class);
/*  48: 81 */     register("identity", IdentityGenerator.class);
/*  49: 82 */     register("select", SelectGenerator.class);
/*  50: 83 */     register("sequence", SequenceGenerator.class);
/*  51: 84 */     register("seqhilo", SequenceHiLoGenerator.class);
/*  52: 85 */     register("increment", IncrementGenerator.class);
/*  53: 86 */     register("foreign", ForeignGenerator.class);
/*  54: 87 */     register("sequence-identity", SequenceIdentityGenerator.class);
/*  55: 88 */     register("enhanced-sequence", SequenceStyleGenerator.class);
/*  56: 89 */     register("enhanced-table", TableGenerator.class);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void register(String strategy, Class generatorClass)
/*  60:    */   {
/*  61: 93 */     LOG.debugf("Registering IdentifierGenerator strategy [%s] -> [%s]", strategy, generatorClass.getName());
/*  62: 94 */     Class previous = (Class)this.generatorStrategyToClassNameMap.put(strategy, generatorClass);
/*  63: 95 */     if (previous != null) {
/*  64: 96 */       LOG.debugf("    - overriding [%s]", previous.getName());
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Dialect getDialect()
/*  69:    */   {
/*  70:102 */     return this.dialect;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setDialect(Dialect dialect)
/*  74:    */   {
/*  75:107 */     LOG.debugf("Setting dialect [%s]", dialect);
/*  76:108 */     this.dialect = dialect;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public IdentifierGenerator createIdentifierGenerator(String strategy, Type type, Properties config)
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:114 */       Class clazz = getIdentifierGeneratorClass(strategy);
/*  84:115 */       IdentifierGenerator identifierGenerator = (IdentifierGenerator)clazz.newInstance();
/*  85:116 */       if ((identifierGenerator instanceof Configurable)) {
/*  86:117 */         ((Configurable)identifierGenerator).configure(type, config, this.dialect);
/*  87:    */       }
/*  88:119 */       return identifierGenerator;
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:122 */       String entityName = config.getProperty("entity_name");
/*  93:123 */       throw new MappingException(String.format("Could not instantiate id generator [entity-name=%s]", new Object[] { entityName }), e);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Class getIdentifierGeneratorClass(String strategy)
/*  98:    */   {
/*  99:129 */     if ("native".equals(strategy)) {
/* 100:130 */       return this.dialect.getNativeIdentifierGeneratorClass();
/* 101:    */     }
/* 102:133 */     Class generatorClass = (Class)this.generatorStrategyToClassNameMap.get(strategy);
/* 103:    */     try
/* 104:    */     {
/* 105:135 */       if (generatorClass == null) {
/* 106:136 */         generatorClass = ReflectHelper.classForName(strategy);
/* 107:    */       }
/* 108:    */     }
/* 109:    */     catch (ClassNotFoundException e)
/* 110:    */     {
/* 111:140 */       throw new MappingException(String.format("Could not interpret id generator strategy [%s]", new Object[] { strategy }));
/* 112:    */     }
/* 113:142 */     return generatorClass;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void injectServices(ServiceRegistryImplementor serviceRegistry)
/* 117:    */   {
/* 118:147 */     this.dialect = ((JdbcServices)serviceRegistry.getService(JdbcServices.class)).getDialect();
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory
 * JD-Core Version:    0.7.0.1
 */