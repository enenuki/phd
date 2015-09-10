/*  1:   */ package org.hibernate.tuple.component;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.mapping.Component;
/*  5:   */ import org.hibernate.mapping.Property;
/*  6:   */ import org.hibernate.property.Getter;
/*  7:   */ import org.hibernate.property.PropertyAccessor;
/*  8:   */ import org.hibernate.property.PropertyAccessorFactory;
/*  9:   */ import org.hibernate.property.Setter;
/* 10:   */ import org.hibernate.tuple.DynamicMapInstantiator;
/* 11:   */ import org.hibernate.tuple.Instantiator;
/* 12:   */ 
/* 13:   */ public class DynamicMapComponentTuplizer
/* 14:   */   extends AbstractComponentTuplizer
/* 15:   */ {
/* 16:   */   public Class getMappedClass()
/* 17:   */   {
/* 18:46 */     return Map.class;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected Instantiator buildInstantiator(Component component)
/* 22:   */   {
/* 23:50 */     return new DynamicMapInstantiator();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public DynamicMapComponentTuplizer(Component component)
/* 27:   */   {
/* 28:54 */     super(component);
/* 29:   */   }
/* 30:   */   
/* 31:   */   private PropertyAccessor buildPropertyAccessor(Property property)
/* 32:   */   {
/* 33:58 */     return PropertyAccessorFactory.getDynamicMapPropertyAccessor();
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected Getter buildGetter(Component component, Property prop)
/* 37:   */   {
/* 38:62 */     return buildPropertyAccessor(prop).getGetter(null, prop.getName());
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected Setter buildSetter(Component component, Property prop)
/* 42:   */   {
/* 43:66 */     return buildPropertyAccessor(prop).getSetter(null, prop.getName());
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.DynamicMapComponentTuplizer
 * JD-Core Version:    0.7.0.1
 */