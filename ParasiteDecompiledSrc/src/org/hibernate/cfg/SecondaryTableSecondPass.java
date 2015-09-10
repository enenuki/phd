/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  6:   */ import org.hibernate.cfg.annotations.EntityBinder;
/*  7:   */ 
/*  8:   */ public class SecondaryTableSecondPass
/*  9:   */   implements SecondPass
/* 10:   */ {
/* 11:   */   private EntityBinder entityBinder;
/* 12:   */   private PropertyHolder propertyHolder;
/* 13:   */   private XAnnotatedElement annotatedClass;
/* 14:   */   
/* 15:   */   public SecondaryTableSecondPass(EntityBinder entityBinder, PropertyHolder propertyHolder, XAnnotatedElement annotatedClass)
/* 16:   */   {
/* 17:40 */     this.entityBinder = entityBinder;
/* 18:41 */     this.propertyHolder = propertyHolder;
/* 19:42 */     this.annotatedClass = annotatedClass;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void doSecondPass(Map persistentClasses)
/* 23:   */     throws MappingException
/* 24:   */   {
/* 25:46 */     this.entityBinder.finalSecondaryTableBinding(this.propertyHolder);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.SecondaryTableSecondPass
 * JD-Core Version:    0.7.0.1
 */