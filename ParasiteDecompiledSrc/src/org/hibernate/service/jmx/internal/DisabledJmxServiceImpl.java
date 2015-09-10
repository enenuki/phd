/*  1:   */ package org.hibernate.service.jmx.internal;
/*  2:   */ 
/*  3:   */ import javax.management.ObjectName;
/*  4:   */ import org.hibernate.service.jmx.spi.JmxService;
/*  5:   */ import org.hibernate.service.spi.Manageable;
/*  6:   */ 
/*  7:   */ public class DisabledJmxServiceImpl
/*  8:   */   implements JmxService
/*  9:   */ {
/* 10:36 */   public static final DisabledJmxServiceImpl INSTANCE = new DisabledJmxServiceImpl();
/* 11:   */   
/* 12:   */   public void registerService(Manageable service, Class serviceRole) {}
/* 13:   */   
/* 14:   */   public void registerMBean(ObjectName objectName, Object mBean) {}
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jmx.internal.DisabledJmxServiceImpl
 * JD-Core Version:    0.7.0.1
 */