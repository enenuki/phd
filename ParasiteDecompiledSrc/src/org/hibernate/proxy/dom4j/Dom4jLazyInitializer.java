/*  1:   */ package org.hibernate.proxy.dom4j;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  6:   */ import org.hibernate.proxy.AbstractLazyInitializer;
/*  7:   */ 
/*  8:   */ public class Dom4jLazyInitializer
/*  9:   */   extends AbstractLazyInitializer
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   Dom4jLazyInitializer(String entityName, Serializable id, SessionImplementor session)
/* 13:   */   {
/* 14:41 */     super(entityName, id, session);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Element getElement()
/* 18:   */   {
/* 19:45 */     return (Element)getImplementation();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class getPersistentClass()
/* 23:   */   {
/* 24:49 */     throw new UnsupportedOperationException("dom4j entity representation");
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.dom4j.Dom4jLazyInitializer
 * JD-Core Version:    0.7.0.1
 */