/*   1:    */ package org.hibernate.tuple.component;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.bytecode.spi.BasicProxyFactory;
/*   8:    */ import org.hibernate.bytecode.spi.BytecodeProvider;
/*   9:    */ import org.hibernate.bytecode.spi.ProxyFactoryFactory;
/*  10:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer;
/*  11:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
/*  12:    */ import org.hibernate.cfg.Environment;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.internal.util.ReflectHelper;
/*  15:    */ import org.hibernate.mapping.Component;
/*  16:    */ import org.hibernate.mapping.Property;
/*  17:    */ import org.hibernate.property.BackrefPropertyAccessor;
/*  18:    */ import org.hibernate.property.Getter;
/*  19:    */ import org.hibernate.property.PropertyAccessor;
/*  20:    */ import org.hibernate.property.PropertyAccessorFactory;
/*  21:    */ import org.hibernate.property.Setter;
/*  22:    */ import org.hibernate.tuple.Instantiator;
/*  23:    */ import org.hibernate.tuple.PojoInstantiator;
/*  24:    */ 
/*  25:    */ public class PojoComponentTuplizer
/*  26:    */   extends AbstractComponentTuplizer
/*  27:    */ {
/*  28:    */   private final Class componentClass;
/*  29:    */   private ReflectionOptimizer optimizer;
/*  30:    */   private final Getter parentGetter;
/*  31:    */   private final Setter parentSetter;
/*  32:    */   
/*  33:    */   public PojoComponentTuplizer(Component component)
/*  34:    */   {
/*  35: 59 */     super(component);
/*  36:    */     
/*  37: 61 */     this.componentClass = component.getComponentClass();
/*  38:    */     
/*  39: 63 */     String[] getterNames = new String[this.propertySpan];
/*  40: 64 */     String[] setterNames = new String[this.propertySpan];
/*  41: 65 */     Class[] propTypes = new Class[this.propertySpan];
/*  42: 66 */     for (int i = 0; i < this.propertySpan; i++)
/*  43:    */     {
/*  44: 67 */       getterNames[i] = this.getters[i].getMethodName();
/*  45: 68 */       setterNames[i] = this.setters[i].getMethodName();
/*  46: 69 */       propTypes[i] = this.getters[i].getReturnType();
/*  47:    */     }
/*  48: 72 */     String parentPropertyName = component.getParentProperty();
/*  49: 73 */     if (parentPropertyName == null)
/*  50:    */     {
/*  51: 74 */       this.parentSetter = null;
/*  52: 75 */       this.parentGetter = null;
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 78 */       PropertyAccessor pa = PropertyAccessorFactory.getPropertyAccessor(null);
/*  57: 79 */       this.parentSetter = pa.getSetter(this.componentClass, parentPropertyName);
/*  58: 80 */       this.parentGetter = pa.getGetter(this.componentClass, parentPropertyName);
/*  59:    */     }
/*  60: 83 */     if ((this.hasCustomAccessors) || (!Environment.useReflectionOptimizer())) {
/*  61: 84 */       this.optimizer = null;
/*  62:    */     } else {
/*  63: 89 */       this.optimizer = Environment.getBytecodeProvider().getReflectionOptimizer(this.componentClass, getterNames, setterNames, propTypes);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Class getMappedClass()
/*  68:    */   {
/*  69: 96 */     return this.componentClass;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Object[] getPropertyValues(Object component)
/*  73:    */     throws HibernateException
/*  74:    */   {
/*  75:100 */     if (component == BackrefPropertyAccessor.UNKNOWN) {
/*  76:101 */       return new Object[this.propertySpan];
/*  77:    */     }
/*  78:103 */     if ((this.optimizer != null) && (this.optimizer.getAccessOptimizer() != null)) {
/*  79:104 */       return this.optimizer.getAccessOptimizer().getPropertyValues(component);
/*  80:    */     }
/*  81:107 */     return super.getPropertyValues(component);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setPropertyValues(Object component, Object[] values)
/*  85:    */     throws HibernateException
/*  86:    */   {
/*  87:112 */     if ((this.optimizer != null) && (this.optimizer.getAccessOptimizer() != null)) {
/*  88:113 */       this.optimizer.getAccessOptimizer().setPropertyValues(component, values);
/*  89:    */     } else {
/*  90:116 */       super.setPropertyValues(component, values);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object getParent(Object component)
/*  95:    */   {
/*  96:121 */     return this.parentGetter.get(component);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean hasParentProperty()
/* 100:    */   {
/* 101:125 */     return this.parentGetter != null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isMethodOf(Method method)
/* 105:    */   {
/* 106:129 */     for (int i = 0; i < this.propertySpan; i++)
/* 107:    */     {
/* 108:130 */       Method getterMethod = this.getters[i].getMethod();
/* 109:131 */       if ((getterMethod != null) && (getterMethod.equals(method))) {
/* 110:132 */         return true;
/* 111:    */       }
/* 112:    */     }
/* 113:135 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setParent(Object component, Object parent, SessionFactoryImplementor factory)
/* 117:    */   {
/* 118:139 */     this.parentSetter.set(component, parent, factory);
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected Instantiator buildInstantiator(Component component)
/* 122:    */   {
/* 123:143 */     if ((component.isEmbedded()) && (ReflectHelper.isAbstractClass(component.getComponentClass()))) {
/* 124:144 */       return new ProxiedInstantiator(component);
/* 125:    */     }
/* 126:146 */     if (this.optimizer == null) {
/* 127:147 */       return new PojoInstantiator(component, null);
/* 128:    */     }
/* 129:150 */     return new PojoInstantiator(component, this.optimizer.getInstantiationOptimizer());
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Getter buildGetter(Component component, Property prop)
/* 133:    */   {
/* 134:155 */     return prop.getGetter(component.getComponentClass());
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Setter buildSetter(Component component, Property prop)
/* 138:    */   {
/* 139:159 */     return prop.getSetter(component.getComponentClass());
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static class ProxiedInstantiator
/* 143:    */     implements Instantiator
/* 144:    */   {
/* 145:    */     private final Class proxiedClass;
/* 146:    */     private final BasicProxyFactory factory;
/* 147:    */     
/* 148:    */     public ProxiedInstantiator(Component component)
/* 149:    */     {
/* 150:167 */       this.proxiedClass = component.getComponentClass();
/* 151:168 */       if (this.proxiedClass.isInterface()) {
/* 152:169 */         this.factory = Environment.getBytecodeProvider().getProxyFactoryFactory().buildBasicProxyFactory(null, new Class[] { this.proxiedClass });
/* 153:    */       } else {
/* 154:174 */         this.factory = Environment.getBytecodeProvider().getProxyFactoryFactory().buildBasicProxyFactory(this.proxiedClass, null);
/* 155:    */       }
/* 156:    */     }
/* 157:    */     
/* 158:    */     public Object instantiate(Serializable id)
/* 159:    */     {
/* 160:181 */       throw new AssertionFailure("ProxiedInstantiator can only be used to instantiate component");
/* 161:    */     }
/* 162:    */     
/* 163:    */     public Object instantiate()
/* 164:    */     {
/* 165:185 */       return this.factory.getProxy();
/* 166:    */     }
/* 167:    */     
/* 168:    */     public boolean isInstance(Object object)
/* 169:    */     {
/* 170:189 */       return this.proxiedClass.isInstance(object);
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.PojoComponentTuplizer
 * JD-Core Version:    0.7.0.1
 */