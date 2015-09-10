/*  1:   */ package org.hibernate.proxy.map;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  6:   */ import org.hibernate.proxy.AbstractLazyInitializer;
/*  7:   */ 
/*  8:   */ public class MapLazyInitializer
/*  9:   */   extends AbstractLazyInitializer
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   MapLazyInitializer(String entityName, Serializable id, SessionImplementor session)
/* 13:   */   {
/* 14:40 */     super(entityName, id, session);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Map getMap()
/* 18:   */   {
/* 19:44 */     return (Map)getImplementation();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class getPersistentClass()
/* 23:   */   {
/* 24:48 */     throw new UnsupportedOperationException("dynamic-map entity representation");
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.map.MapLazyInitializer
 * JD-Core Version:    0.7.0.1
 */