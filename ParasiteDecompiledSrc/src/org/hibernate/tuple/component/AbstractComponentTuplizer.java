/*   1:    */ package org.hibernate.tuple.component;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.mapping.Component;
/*   8:    */ import org.hibernate.mapping.Property;
/*   9:    */ import org.hibernate.property.Getter;
/*  10:    */ import org.hibernate.property.Setter;
/*  11:    */ import org.hibernate.tuple.Instantiator;
/*  12:    */ 
/*  13:    */ public abstract class AbstractComponentTuplizer
/*  14:    */   implements ComponentTuplizer
/*  15:    */ {
/*  16:    */   protected final Getter[] getters;
/*  17:    */   protected final Setter[] setters;
/*  18:    */   protected final int propertySpan;
/*  19:    */   protected final Instantiator instantiator;
/*  20:    */   protected final boolean hasCustomAccessors;
/*  21:    */   
/*  22:    */   protected abstract Instantiator buildInstantiator(Component paramComponent);
/*  23:    */   
/*  24:    */   protected abstract Getter buildGetter(Component paramComponent, Property paramProperty);
/*  25:    */   
/*  26:    */   protected abstract Setter buildSetter(Component paramComponent, Property paramProperty);
/*  27:    */   
/*  28:    */   protected AbstractComponentTuplizer(Component component)
/*  29:    */   {
/*  30: 55 */     this.propertySpan = component.getPropertySpan();
/*  31: 56 */     this.getters = new Getter[this.propertySpan];
/*  32: 57 */     this.setters = new Setter[this.propertySpan];
/*  33:    */     
/*  34: 59 */     Iterator iter = component.getPropertyIterator();
/*  35: 60 */     boolean foundCustomAccessor = false;
/*  36: 61 */     int i = 0;
/*  37: 62 */     while (iter.hasNext())
/*  38:    */     {
/*  39: 63 */       Property prop = (Property)iter.next();
/*  40: 64 */       this.getters[i] = buildGetter(component, prop);
/*  41: 65 */       this.setters[i] = buildSetter(component, prop);
/*  42: 66 */       if (!prop.isBasicPropertyAccessor()) {
/*  43: 67 */         foundCustomAccessor = true;
/*  44:    */       }
/*  45: 69 */       i++;
/*  46:    */     }
/*  47: 71 */     this.hasCustomAccessors = foundCustomAccessor;
/*  48: 72 */     this.instantiator = buildInstantiator(component);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object getPropertyValue(Object component, int i)
/*  52:    */     throws HibernateException
/*  53:    */   {
/*  54: 76 */     return this.getters[i].get(component);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object[] getPropertyValues(Object component)
/*  58:    */     throws HibernateException
/*  59:    */   {
/*  60: 80 */     Object[] values = new Object[this.propertySpan];
/*  61: 81 */     for (int i = 0; i < this.propertySpan; i++) {
/*  62: 82 */       values[i] = getPropertyValue(component, i);
/*  63:    */     }
/*  64: 84 */     return values;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isInstance(Object object)
/*  68:    */   {
/*  69: 88 */     return this.instantiator.isInstance(object);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setPropertyValues(Object component, Object[] values)
/*  73:    */     throws HibernateException
/*  74:    */   {
/*  75: 92 */     for (int i = 0; i < this.propertySpan; i++) {
/*  76: 93 */       this.setters[i].set(component, values[i], null);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Object instantiate()
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:101 */     return this.instantiator.instantiate();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object getParent(Object component)
/*  87:    */   {
/*  88:105 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean hasParentProperty()
/*  92:    */   {
/*  93:109 */     return false;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isMethodOf(Method method)
/*  97:    */   {
/*  98:113 */     return false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setParent(Object component, Object parent, SessionFactoryImplementor factory)
/* 102:    */   {
/* 103:117 */     throw new UnsupportedOperationException();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Getter getGetter(int i)
/* 107:    */   {
/* 108:121 */     return this.getters[i];
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.AbstractComponentTuplizer
 * JD-Core Version:    0.7.0.1
 */