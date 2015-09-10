/*  1:   */ package org.hibernate.integrator.internal;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashSet;
/*  4:   */ import org.hibernate.cfg.beanvalidation.BeanValidationIntegrator;
/*  5:   */ import org.hibernate.integrator.spi.Integrator;
/*  6:   */ import org.hibernate.integrator.spi.IntegratorService;
/*  7:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  8:   */ import org.jboss.logging.Logger;
/*  9:   */ 
/* 10:   */ public class IntegratorServiceImpl
/* 11:   */   implements IntegratorService
/* 12:   */ {
/* 13:39 */   private static final Logger LOG = Logger.getLogger(IntegratorServiceImpl.class.getName());
/* 14:41 */   private final LinkedHashSet<Integrator> integrators = new LinkedHashSet();
/* 15:   */   
/* 16:   */   public IntegratorServiceImpl(LinkedHashSet<Integrator> providedIntegrators, ClassLoaderService classLoaderService)
/* 17:   */   {
/* 18:46 */     addIntegrator(new BeanValidationIntegrator());
/* 19:49 */     for (Integrator integrator : providedIntegrators) {
/* 20:50 */       addIntegrator(integrator);
/* 21:   */     }
/* 22:53 */     for (Integrator integrator : classLoaderService.loadJavaServices(Integrator.class)) {
/* 23:54 */       addIntegrator(integrator);
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   private void addIntegrator(Integrator integrator)
/* 28:   */   {
/* 29:59 */     LOG.debugf("Adding Integrator [%s].", integrator.getClass().getName());
/* 30:60 */     this.integrators.add(integrator);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Iterable<Integrator> getIntegrators()
/* 34:   */   {
/* 35:65 */     return this.integrators;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.integrator.internal.IntegratorServiceImpl
 * JD-Core Version:    0.7.0.1
 */