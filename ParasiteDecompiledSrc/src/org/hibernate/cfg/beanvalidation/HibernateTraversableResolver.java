/*   1:    */ package org.hibernate.cfg.beanvalidation;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.ElementType;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import javax.validation.Path;
/*   8:    */ import javax.validation.Path.Node;
/*   9:    */ import javax.validation.TraversableResolver;
/*  10:    */ import org.hibernate.Hibernate;
/*  11:    */ import org.hibernate.annotations.common.AssertionFailure;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.persister.entity.EntityPersister;
/*  14:    */ import org.hibernate.type.CollectionType;
/*  15:    */ import org.hibernate.type.CompositeType;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public class HibernateTraversableResolver
/*  19:    */   implements TraversableResolver
/*  20:    */ {
/*  21:    */   private Set<String> associations;
/*  22:    */   
/*  23:    */   public HibernateTraversableResolver(EntityPersister persister, ConcurrentHashMap<EntityPersister, Set<String>> associationsPerEntityPersister, SessionFactoryImplementor factory)
/*  24:    */   {
/*  25: 55 */     this.associations = ((Set)associationsPerEntityPersister.get(persister));
/*  26: 56 */     if (this.associations == null)
/*  27:    */     {
/*  28: 57 */       this.associations = new HashSet();
/*  29: 58 */       addAssociationsToTheSetForAllProperties(persister.getPropertyNames(), persister.getPropertyTypes(), "", factory);
/*  30: 59 */       associationsPerEntityPersister.put(persister, this.associations);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void addAssociationsToTheSetForAllProperties(String[] names, Type[] types, String prefix, SessionFactoryImplementor factory)
/*  35:    */   {
/*  36: 64 */     int length = names.length;
/*  37: 65 */     for (int index = 0; index < length; index++) {
/*  38: 66 */       addAssociationsToTheSetForOneProperty(names[index], types[index], prefix, factory);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void addAssociationsToTheSetForOneProperty(String name, Type type, String prefix, SessionFactoryImplementor factory)
/*  43:    */   {
/*  44: 72 */     if (type.isCollectionType())
/*  45:    */     {
/*  46: 73 */       CollectionType collType = (CollectionType)type;
/*  47: 74 */       Type assocType = collType.getElementType(factory);
/*  48: 75 */       addAssociationsToTheSetForOneProperty(name, assocType, prefix, factory);
/*  49:    */     }
/*  50: 78 */     else if ((type.isEntityType()) || (type.isAnyType()))
/*  51:    */     {
/*  52: 79 */       this.associations.add(prefix + name);
/*  53:    */     }
/*  54: 80 */     else if (type.isComponentType())
/*  55:    */     {
/*  56: 81 */       CompositeType componentType = (CompositeType)type;
/*  57: 82 */       addAssociationsToTheSetForAllProperties(componentType.getPropertyNames(), componentType.getSubtypes(), (prefix.equals("") ? name : new StringBuilder().append(prefix).append(name).toString()) + ".", factory);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private String getStringBasedPath(Path.Node traversableProperty, Path pathToTraversableObject)
/*  62:    */   {
/*  63: 91 */     StringBuilder path = new StringBuilder();
/*  64: 92 */     for (Path.Node node : pathToTraversableObject) {
/*  65: 93 */       if (node.getName() != null) {
/*  66: 94 */         path.append(node.getName()).append(".");
/*  67:    */       }
/*  68:    */     }
/*  69: 97 */     if (traversableProperty.getName() == null) {
/*  70: 98 */       throw new AssertionFailure("TraversableResolver being passed a traversableProperty with null name. pathToTraversableObject: " + path.toString());
/*  71:    */     }
/*  72:102 */     path.append(traversableProperty.getName());
/*  73:    */     
/*  74:104 */     return path.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType)
/*  78:    */   {
/*  79:113 */     return (Hibernate.isInitialized(traversableObject)) && (Hibernate.isPropertyInitialized(traversableObject, traversableProperty.getName()));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType)
/*  83:    */   {
/*  84:122 */     String path = getStringBasedPath(traversableProperty, pathToTraversableObject);
/*  85:123 */     return !this.associations.contains(path);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.HibernateTraversableResolver
 * JD-Core Version:    0.7.0.1
 */