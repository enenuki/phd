/*  1:   */ package org.hibernate.engine.loading.internal;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.jboss.logging.Logger;
/*  8:   */ 
/*  9:   */ public class EntityLoadContext
/* 10:   */ {
/* 11:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityLoadContext.class.getName());
/* 12:   */   private final LoadContexts loadContexts;
/* 13:   */   private final ResultSet resultSet;
/* 14:45 */   private final List hydratingEntities = new ArrayList(20);
/* 15:   */   
/* 16:   */   public EntityLoadContext(LoadContexts loadContexts, ResultSet resultSet)
/* 17:   */   {
/* 18:48 */     this.loadContexts = loadContexts;
/* 19:49 */     this.resultSet = resultSet;
/* 20:   */   }
/* 21:   */   
/* 22:   */   void cleanup()
/* 23:   */   {
/* 24:53 */     if (!this.hydratingEntities.isEmpty()) {
/* 25:53 */       LOG.hydratingEntitiesCount(this.hydratingEntities.size());
/* 26:   */     }
/* 27:54 */     this.hydratingEntities.clear();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:60 */     return super.toString() + "<rs=" + this.resultSet + ">";
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.loading.internal.EntityLoadContext
 * JD-Core Version:    0.7.0.1
 */