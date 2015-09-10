/*  1:   */ package org.hibernate.tuple;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Map;
/*  8:   */ import java.util.Set;
/*  9:   */ import org.hibernate.mapping.PersistentClass;
/* 10:   */ import org.hibernate.metamodel.binding.EntityBinding;
/* 11:   */ import org.hibernate.metamodel.domain.Entity;
/* 12:   */ 
/* 13:   */ public class DynamicMapInstantiator
/* 14:   */   implements Instantiator
/* 15:   */ {
/* 16:   */   public static final String KEY = "$type$";
/* 17:   */   private String entityName;
/* 18:41 */   private Set isInstanceEntityNames = new HashSet();
/* 19:   */   
/* 20:   */   public DynamicMapInstantiator()
/* 21:   */   {
/* 22:44 */     this.entityName = null;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public DynamicMapInstantiator(PersistentClass mappingInfo)
/* 26:   */   {
/* 27:48 */     this.entityName = mappingInfo.getEntityName();
/* 28:49 */     this.isInstanceEntityNames.add(this.entityName);
/* 29:50 */     if (mappingInfo.hasSubclasses())
/* 30:   */     {
/* 31:51 */       Iterator itr = mappingInfo.getSubclassClosureIterator();
/* 32:52 */       while (itr.hasNext())
/* 33:   */       {
/* 34:53 */         PersistentClass subclassInfo = (PersistentClass)itr.next();
/* 35:54 */         this.isInstanceEntityNames.add(subclassInfo.getEntityName());
/* 36:   */       }
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public DynamicMapInstantiator(EntityBinding mappingInfo)
/* 41:   */   {
/* 42:60 */     this.entityName = mappingInfo.getEntity().getName();
/* 43:61 */     this.isInstanceEntityNames.add(this.entityName);
/* 44:62 */     for (EntityBinding subEntityBinding : mappingInfo.getPostOrderSubEntityBindingClosure()) {
/* 45:63 */       this.isInstanceEntityNames.add(subEntityBinding.getEntity().getName());
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public final Object instantiate(Serializable id)
/* 50:   */   {
/* 51:68 */     return instantiate();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public final Object instantiate()
/* 55:   */   {
/* 56:72 */     Map map = generateMap();
/* 57:73 */     if (this.entityName != null) {
/* 58:74 */       map.put("$type$", this.entityName);
/* 59:   */     }
/* 60:76 */     return map;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public final boolean isInstance(Object object)
/* 64:   */   {
/* 65:80 */     if ((object instanceof Map))
/* 66:   */     {
/* 67:81 */       if (this.entityName == null) {
/* 68:82 */         return true;
/* 69:   */       }
/* 70:84 */       String type = (String)((Map)object).get("$type$");
/* 71:85 */       return (type == null) || (this.isInstanceEntityNames.contains(type));
/* 72:   */     }
/* 73:88 */     return false;
/* 74:   */   }
/* 75:   */   
/* 76:   */   protected Map generateMap()
/* 77:   */   {
/* 78:93 */     return new HashMap();
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.DynamicMapInstantiator
 * JD-Core Version:    0.7.0.1
 */