/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  5:   */ import org.hibernate.annotations.common.reflection.XProperty;
/*  6:   */ import org.hibernate.cfg.annotations.EntityBinder;
/*  7:   */ import org.hibernate.mapping.Collection;
/*  8:   */ import org.hibernate.mapping.Component;
/*  9:   */ import org.hibernate.mapping.Join;
/* 10:   */ import org.hibernate.mapping.PersistentClass;
/* 11:   */ 
/* 12:   */ public final class PropertyHolderBuilder
/* 13:   */ {
/* 14:   */   public static PropertyHolder buildPropertyHolder(XClass clazzToProcess, PersistentClass persistentClass, EntityBinder entityBinder, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 15:   */   {
/* 16:50 */     return new ClassPropertyHolder(persistentClass, clazzToProcess, entityBinder, mappings, inheritanceStatePerClass);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static PropertyHolder buildPropertyHolder(Component component, String path, PropertyData inferredData, PropertyHolder parent, Mappings mappings)
/* 20:   */   {
/* 21:69 */     return new ComponentPropertyHolder(component, path, inferredData, parent, mappings);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static PropertyHolder buildPropertyHolder(Collection collection, String path, XClass clazzToProcess, XProperty property, PropertyHolder parentPropertyHolder, Mappings mappings)
/* 25:   */   {
/* 26:82 */     return new CollectionPropertyHolder(collection, path, clazzToProcess, property, parentPropertyHolder, mappings);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static PropertyHolder buildPropertyHolder(PersistentClass persistentClass, Map<String, Join> joins, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 30:   */   {
/* 31:95 */     return new ClassPropertyHolder(persistentClass, null, joins, mappings, inheritanceStatePerClass);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyHolderBuilder
 * JD-Core Version:    0.7.0.1
 */