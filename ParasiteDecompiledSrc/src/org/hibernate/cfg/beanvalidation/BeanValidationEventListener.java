/*   1:    */ package org.hibernate.cfg.beanvalidation;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Properties;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import javax.validation.ConstraintViolation;
/*   8:    */ import javax.validation.ConstraintViolationException;
/*   9:    */ import javax.validation.TraversableResolver;
/*  10:    */ import javax.validation.Validation;
/*  11:    */ import javax.validation.Validator;
/*  12:    */ import javax.validation.ValidatorContext;
/*  13:    */ import javax.validation.ValidatorFactory;
/*  14:    */ import org.hibernate.EntityMode;
/*  15:    */ import org.hibernate.cfg.Configuration;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.event.spi.EventSource;
/*  18:    */ import org.hibernate.event.spi.PreDeleteEvent;
/*  19:    */ import org.hibernate.event.spi.PreDeleteEventListener;
/*  20:    */ import org.hibernate.event.spi.PreInsertEvent;
/*  21:    */ import org.hibernate.event.spi.PreInsertEventListener;
/*  22:    */ import org.hibernate.event.spi.PreUpdateEvent;
/*  23:    */ import org.hibernate.event.spi.PreUpdateEventListener;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.persister.entity.EntityPersister;
/*  26:    */ import org.jboss.logging.Logger;
/*  27:    */ 
/*  28:    */ public class BeanValidationEventListener
/*  29:    */   implements PreInsertEventListener, PreUpdateEventListener, PreDeleteEventListener
/*  30:    */ {
/*  31: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BeanValidationEventListener.class.getName());
/*  32:    */   private ValidatorFactory factory;
/*  33: 64 */   private ConcurrentHashMap<EntityPersister, Set<String>> associationsPerEntityPersister = new ConcurrentHashMap();
/*  34:    */   private GroupsPerOperation groupsPerOperation;
/*  35:    */   boolean initialized;
/*  36:    */   
/*  37:    */   public BeanValidationEventListener() {}
/*  38:    */   
/*  39:    */   public BeanValidationEventListener(ValidatorFactory factory, Properties properties)
/*  40:    */   {
/*  41: 82 */     init(factory, properties);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void initialize(Configuration cfg)
/*  45:    */   {
/*  46: 86 */     if (!this.initialized)
/*  47:    */     {
/*  48: 87 */       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
/*  49: 88 */       Properties props = cfg.getProperties();
/*  50: 89 */       init(factory, props);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean onPreInsert(PreInsertEvent event)
/*  55:    */   {
/*  56: 94 */     validate(event.getEntity(), event.getPersister().getEntityMode(), event.getPersister(), event.getSession().getFactory(), GroupsPerOperation.Operation.INSERT);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60: 98 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean onPreUpdate(PreUpdateEvent event)
/*  64:    */   {
/*  65:102 */     validate(event.getEntity(), event.getPersister().getEntityMode(), event.getPersister(), event.getSession().getFactory(), GroupsPerOperation.Operation.UPDATE);
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:106 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean onPreDelete(PreDeleteEvent event)
/*  73:    */   {
/*  74:110 */     validate(event.getEntity(), event.getPersister().getEntityMode(), event.getPersister(), event.getSession().getFactory(), GroupsPerOperation.Operation.DELETE);
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:114 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void init(ValidatorFactory factory, Properties properties)
/*  82:    */   {
/*  83:118 */     this.factory = factory;
/*  84:119 */     this.groupsPerOperation = new GroupsPerOperation(properties);
/*  85:120 */     this.initialized = true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private <T> void validate(T object, EntityMode mode, EntityPersister persister, SessionFactoryImplementor sessionFactory, GroupsPerOperation.Operation operation)
/*  89:    */   {
/*  90:125 */     if ((object == null) || (mode != EntityMode.POJO)) {
/*  91:126 */       return;
/*  92:    */     }
/*  93:128 */     TraversableResolver tr = new HibernateTraversableResolver(persister, this.associationsPerEntityPersister, sessionFactory);
/*  94:    */     
/*  95:    */ 
/*  96:131 */     Validator validator = this.factory.usingContext().traversableResolver(tr).getValidator();
/*  97:    */     
/*  98:    */ 
/*  99:134 */     Class<?>[] groups = this.groupsPerOperation.get(operation);
/* 100:135 */     if (groups.length > 0)
/* 101:    */     {
/* 102:136 */       Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
/* 103:137 */       if (constraintViolations.size() > 0)
/* 104:    */       {
/* 105:138 */         Set<ConstraintViolation<?>> propagatedViolations = new HashSet(constraintViolations.size());
/* 106:    */         
/* 107:140 */         Set<String> classNames = new HashSet();
/* 108:141 */         for (ConstraintViolation<?> violation : constraintViolations)
/* 109:    */         {
/* 110:142 */           LOG.trace(violation);
/* 111:143 */           propagatedViolations.add(violation);
/* 112:144 */           classNames.add(violation.getLeafBean().getClass().getName());
/* 113:    */         }
/* 114:146 */         StringBuilder builder = new StringBuilder();
/* 115:147 */         builder.append("Validation failed for classes ");
/* 116:148 */         builder.append(classNames);
/* 117:149 */         builder.append(" during ");
/* 118:150 */         builder.append(operation.getName());
/* 119:151 */         builder.append(" time for groups ");
/* 120:152 */         builder.append(toString(groups));
/* 121:153 */         builder.append("\nList of constraint violations:[\n");
/* 122:154 */         for (ConstraintViolation<?> violation : constraintViolations) {
/* 123:155 */           builder.append("\t").append(violation.toString()).append("\n");
/* 124:    */         }
/* 125:157 */         builder.append("]");
/* 126:    */         
/* 127:159 */         throw new ConstraintViolationException(builder.toString(), propagatedViolations);
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   private String toString(Class<?>[] groups)
/* 133:    */   {
/* 134:167 */     StringBuilder toString = new StringBuilder("[");
/* 135:168 */     for (Class<?> group : groups) {
/* 136:169 */       toString.append(group.getName()).append(", ");
/* 137:    */     }
/* 138:171 */     toString.append("]");
/* 139:172 */     return toString.toString();
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.BeanValidationEventListener
 * JD-Core Version:    0.7.0.1
 */