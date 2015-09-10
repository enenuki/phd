/*   1:    */ package org.hibernate.service.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.ListIterator;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   9:    */ import org.hibernate.service.BootstrapServiceRegistry;
/*  10:    */ import org.hibernate.service.Service;
/*  11:    */ import org.hibernate.service.ServiceRegistry;
/*  12:    */ import org.hibernate.service.UnknownServiceException;
/*  13:    */ import org.hibernate.service.jmx.spi.JmxService;
/*  14:    */ import org.hibernate.service.spi.InjectService;
/*  15:    */ import org.hibernate.service.spi.Manageable;
/*  16:    */ import org.hibernate.service.spi.ServiceBinding;
/*  17:    */ import org.hibernate.service.spi.ServiceBinding.ServiceLifecycleOwner;
/*  18:    */ import org.hibernate.service.spi.ServiceException;
/*  19:    */ import org.hibernate.service.spi.ServiceInitiator;
/*  20:    */ import org.hibernate.service.spi.ServiceRegistryAwareService;
/*  21:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  22:    */ import org.hibernate.service.spi.Startable;
/*  23:    */ import org.hibernate.service.spi.Stoppable;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ public abstract class AbstractServiceRegistryImpl
/*  27:    */   implements ServiceRegistryImplementor, ServiceBinding.ServiceLifecycleOwner
/*  28:    */ {
/*  29: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractServiceRegistryImpl.class.getName());
/*  30:    */   private final ServiceRegistryImplementor parent;
/*  31: 63 */   private final ConcurrentHashMap<Class, ServiceBinding> serviceBindingMap = CollectionHelper.concurrentMap(20);
/*  32: 68 */   private final List<ServiceBinding> serviceBindingList = CollectionHelper.arrayList(20);
/*  33:    */   
/*  34:    */   protected AbstractServiceRegistryImpl()
/*  35:    */   {
/*  36: 72 */     this((ServiceRegistryImplementor)null);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected AbstractServiceRegistryImpl(ServiceRegistryImplementor parent)
/*  40:    */   {
/*  41: 76 */     this.parent = parent;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public AbstractServiceRegistryImpl(BootstrapServiceRegistry bootstrapServiceRegistry)
/*  45:    */   {
/*  46: 80 */     if (!ServiceRegistryImplementor.class.isInstance(bootstrapServiceRegistry)) {
/*  47: 81 */       throw new IllegalArgumentException("Boot-strap registry was not ");
/*  48:    */     }
/*  49: 83 */     this.parent = ((ServiceRegistryImplementor)bootstrapServiceRegistry);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected <R extends Service> void createServiceBinding(ServiceInitiator<R> initiator)
/*  53:    */   {
/*  54: 88 */     this.serviceBindingMap.put(initiator.getServiceInitiated(), new ServiceBinding(this, initiator));
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected <R extends Service> void createServiceBinding(ProvidedService<R> providedService)
/*  58:    */   {
/*  59: 92 */     ServiceBinding<R> binding = locateServiceBinding(providedService.getServiceRole(), false);
/*  60: 93 */     if (binding == null)
/*  61:    */     {
/*  62: 94 */       binding = new ServiceBinding(this, providedService.getServiceRole(), (Service)providedService.getService());
/*  63: 95 */       this.serviceBindingMap.put(providedService.getServiceRole(), binding);
/*  64:    */     }
/*  65: 97 */     registerService(binding, (Service)providedService.getService());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ServiceRegistry getParentServiceRegistry()
/*  69:    */   {
/*  70:103 */     return this.parent;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public <R extends Service> ServiceBinding<R> locateServiceBinding(Class<R> serviceRole)
/*  74:    */   {
/*  75:109 */     return locateServiceBinding(serviceRole, true);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected <R extends Service> ServiceBinding<R> locateServiceBinding(Class<R> serviceRole, boolean checkParent)
/*  79:    */   {
/*  80:114 */     ServiceBinding<R> serviceBinding = (ServiceBinding)this.serviceBindingMap.get(serviceRole);
/*  81:115 */     if ((serviceBinding == null) && (checkParent) && (this.parent != null)) {
/*  82:117 */       serviceBinding = this.parent.locateServiceBinding(serviceRole);
/*  83:    */     }
/*  84:119 */     return serviceBinding;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public <R extends Service> R getService(Class<R> serviceRole)
/*  88:    */   {
/*  89:124 */     ServiceBinding<R> serviceBinding = locateServiceBinding(serviceRole);
/*  90:125 */     if (serviceBinding == null) {
/*  91:126 */       throw new UnknownServiceException(serviceRole);
/*  92:    */     }
/*  93:129 */     R service = serviceBinding.getService();
/*  94:130 */     if (service == null) {
/*  95:131 */       service = initializeService(serviceBinding);
/*  96:    */     }
/*  97:134 */     return service;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected <R extends Service> void registerService(ServiceBinding<R> serviceBinding, R service)
/* 101:    */   {
/* 102:138 */     serviceBinding.setService(service);
/* 103:139 */     synchronized (this.serviceBindingList)
/* 104:    */     {
/* 105:140 */       this.serviceBindingList.add(serviceBinding);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   private <R extends Service> R initializeService(ServiceBinding<R> serviceBinding)
/* 110:    */   {
/* 111:145 */     if (LOG.isTraceEnabled()) {
/* 112:146 */       LOG.tracev("Initializing service [role={0}]", serviceBinding.getServiceRole().getName());
/* 113:    */     }
/* 114:150 */     R service = createService(serviceBinding);
/* 115:151 */     if (service == null) {
/* 116:152 */       return null;
/* 117:    */     }
/* 118:156 */     serviceBinding.getLifecycleOwner().injectDependencies(serviceBinding);
/* 119:    */     
/* 120:    */ 
/* 121:159 */     serviceBinding.getLifecycleOwner().configureService(serviceBinding);
/* 122:    */     
/* 123:    */ 
/* 124:162 */     serviceBinding.getLifecycleOwner().startService(serviceBinding);
/* 125:163 */     startService(serviceBinding);
/* 126:    */     
/* 127:165 */     return service;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected <R extends Service> R createService(ServiceBinding<R> serviceBinding)
/* 131:    */   {
/* 132:170 */     ServiceInitiator<R> serviceInitiator = serviceBinding.getServiceInitiator();
/* 133:171 */     if (serviceInitiator == null) {
/* 134:173 */       throw new UnknownServiceException(serviceBinding.getServiceRole());
/* 135:    */     }
/* 136:    */     try
/* 137:    */     {
/* 138:177 */       R service = serviceBinding.getLifecycleOwner().initiateService(serviceInitiator);
/* 139:    */       
/* 140:    */ 
/* 141:180 */       registerService(serviceBinding, service);
/* 142:181 */       return service;
/* 143:    */     }
/* 144:    */     catch (ServiceException e)
/* 145:    */     {
/* 146:184 */       throw e;
/* 147:    */     }
/* 148:    */     catch (Exception e)
/* 149:    */     {
/* 150:187 */       throw new ServiceException("Unable to create requested service [" + serviceBinding.getServiceRole().getName() + "]", e);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public <R extends Service> void injectDependencies(ServiceBinding<R> serviceBinding)
/* 155:    */   {
/* 156:193 */     R service = serviceBinding.getService();
/* 157:    */     
/* 158:195 */     applyInjections(service);
/* 159:197 */     if (ServiceRegistryAwareService.class.isInstance(service)) {
/* 160:198 */       ((ServiceRegistryAwareService)service).injectServices(this);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private <R extends Service> void applyInjections(R service)
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:204 */       for (Method method : service.getClass().getMethods())
/* 169:    */       {
/* 170:205 */         InjectService injectService = (InjectService)method.getAnnotation(InjectService.class);
/* 171:206 */         if (injectService != null) {
/* 172:210 */           processInjection(service, method, injectService);
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:    */     catch (NullPointerException e)
/* 177:    */     {
/* 178:214 */       LOG.error("NPE injecting service deps : " + service.getClass().getName());
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   private <T extends Service> void processInjection(T service, Method injectionMethod, InjectService injectService)
/* 183:    */   {
/* 184:220 */     if ((injectionMethod.getParameterTypes() == null) || (injectionMethod.getParameterTypes().length != 1)) {
/* 185:221 */       throw new ServiceDependencyException("Encountered @InjectService on method with unexpected number of parameters");
/* 186:    */     }
/* 187:226 */     Class dependentServiceRole = injectService.serviceRole();
/* 188:227 */     if ((dependentServiceRole == null) || (dependentServiceRole.equals(Void.class))) {
/* 189:228 */       dependentServiceRole = injectionMethod.getParameterTypes()[0];
/* 190:    */     }
/* 191:233 */     Service dependantService = getService(dependentServiceRole);
/* 192:234 */     if (dependantService == null)
/* 193:    */     {
/* 194:235 */       if (injectService.required()) {
/* 195:236 */         throw new ServiceDependencyException("Dependency [" + dependentServiceRole + "] declared by service [" + service + "] not found");
/* 196:    */       }
/* 197:    */     }
/* 198:    */     else {
/* 199:    */       try
/* 200:    */       {
/* 201:243 */         injectionMethod.invoke(service, new Object[] { dependantService });
/* 202:    */       }
/* 203:    */       catch (Exception e)
/* 204:    */       {
/* 205:246 */         throw new ServiceDependencyException("Cannot inject dependency service", e);
/* 206:    */       }
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public <R extends Service> void startService(ServiceBinding<R> serviceBinding)
/* 211:    */   {
/* 212:254 */     if (Startable.class.isInstance(serviceBinding.getService())) {
/* 213:255 */       ((Startable)serviceBinding.getService()).start();
/* 214:    */     }
/* 215:258 */     if (Manageable.class.isInstance(serviceBinding.getService())) {
/* 216:259 */       ((JmxService)getService(JmxService.class)).registerService((Manageable)serviceBinding.getService(), serviceBinding.getServiceRole());
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void destroy()
/* 221:    */   {
/* 222:269 */     synchronized (this.serviceBindingList)
/* 223:    */     {
/* 224:270 */       ListIterator<ServiceBinding> serviceBindingsIterator = this.serviceBindingList.listIterator(this.serviceBindingList.size());
/* 225:271 */       while (serviceBindingsIterator.hasPrevious())
/* 226:    */       {
/* 227:272 */         ServiceBinding serviceBinding = (ServiceBinding)serviceBindingsIterator.previous();
/* 228:273 */         serviceBinding.getLifecycleOwner().stopService(serviceBinding);
/* 229:    */       }
/* 230:275 */       this.serviceBindingList.clear();
/* 231:    */     }
/* 232:277 */     this.serviceBindingMap.clear();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public <R extends Service> void stopService(ServiceBinding<R> binding)
/* 236:    */   {
/* 237:282 */     Service service = binding.getService();
/* 238:283 */     if (Stoppable.class.isInstance(service)) {
/* 239:    */       try
/* 240:    */       {
/* 241:285 */         ((Stoppable)service).stop();
/* 242:    */       }
/* 243:    */       catch (Exception e)
/* 244:    */       {
/* 245:288 */         LOG.unableToStopService(service.getClass(), e.toString());
/* 246:    */       }
/* 247:    */     }
/* 248:    */   }
/* 249:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.AbstractServiceRegistryImpl
 * JD-Core Version:    0.7.0.1
 */