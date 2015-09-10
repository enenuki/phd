/*   1:    */ package org.hibernate.service;
/*   2:    */ 
/*   3:    */ import java.util.LinkedHashSet;
/*   4:    */ import org.hibernate.integrator.internal.IntegratorServiceImpl;
/*   5:    */ import org.hibernate.integrator.spi.Integrator;
/*   6:    */ import org.hibernate.service.classloading.internal.ClassLoaderServiceImpl;
/*   7:    */ import org.hibernate.service.internal.BootstrapServiceRegistryImpl;
/*   8:    */ 
/*   9:    */ public class BootstrapServiceRegistryBuilder
/*  10:    */ {
/*  11: 42 */   private final LinkedHashSet<Integrator> providedIntegrators = new LinkedHashSet();
/*  12:    */   private ClassLoader applicationClassLoader;
/*  13:    */   private ClassLoader resourcesClassLoader;
/*  14:    */   private ClassLoader hibernateClassLoader;
/*  15:    */   private ClassLoader environmentClassLoader;
/*  16:    */   
/*  17:    */   public BootstrapServiceRegistryBuilder with(Integrator integrator)
/*  18:    */   {
/*  19: 55 */     this.providedIntegrators.add(integrator);
/*  20: 56 */     return this;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public BootstrapServiceRegistryBuilder withApplicationClassLoader(ClassLoader classLoader)
/*  24:    */   {
/*  25: 67 */     this.applicationClassLoader = classLoader;
/*  26: 68 */     return this;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BootstrapServiceRegistryBuilder withResourceClassLoader(ClassLoader classLoader)
/*  30:    */   {
/*  31: 79 */     this.resourcesClassLoader = classLoader;
/*  32: 80 */     return this;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public BootstrapServiceRegistryBuilder withHibernateClassLoader(ClassLoader classLoader)
/*  36:    */   {
/*  37: 91 */     this.hibernateClassLoader = classLoader;
/*  38: 92 */     return this;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public BootstrapServiceRegistryBuilder withEnvironmentClassLoader(ClassLoader classLoader)
/*  42:    */   {
/*  43:103 */     this.environmentClassLoader = classLoader;
/*  44:104 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public BootstrapServiceRegistry build()
/*  48:    */   {
/*  49:113 */     ClassLoaderServiceImpl classLoaderService = new ClassLoaderServiceImpl(this.applicationClassLoader, this.resourcesClassLoader, this.hibernateClassLoader, this.environmentClassLoader);
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:120 */     IntegratorServiceImpl integratorService = new IntegratorServiceImpl(this.providedIntegrators, classLoaderService);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:125 */     return new BootstrapServiceRegistryImpl(classLoaderService, integratorService);
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.BootstrapServiceRegistryBuilder
 * JD-Core Version:    0.7.0.1
 */