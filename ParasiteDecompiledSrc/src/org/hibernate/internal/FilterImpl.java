/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.Filter;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  13:    */ import org.hibernate.type.Type;
/*  14:    */ 
/*  15:    */ public class FilterImpl
/*  16:    */   implements Filter, Serializable
/*  17:    */ {
/*  18:    */   public static final String MARKER = "$FILTER_PLACEHOLDER$";
/*  19:    */   private transient FilterDefinition definition;
/*  20:    */   private String filterName;
/*  21: 49 */   private Map<String, Object> parameters = new HashMap();
/*  22:    */   
/*  23:    */   void afterDeserialize(SessionFactoryImpl factory)
/*  24:    */   {
/*  25: 52 */     this.definition = factory.getFilterDefinition(this.filterName);
/*  26: 53 */     validate();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public FilterImpl(FilterDefinition configuration)
/*  30:    */   {
/*  31: 62 */     this.definition = configuration;
/*  32: 63 */     this.filterName = this.definition.getFilterName();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public FilterDefinition getFilterDefinition()
/*  36:    */   {
/*  37: 67 */     return this.definition;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getName()
/*  41:    */   {
/*  42: 76 */     return this.definition.getFilterName();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Map<String, ?> getParameters()
/*  46:    */   {
/*  47: 80 */     return this.parameters;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Filter setParameter(String name, Object value)
/*  51:    */     throws IllegalArgumentException
/*  52:    */   {
/*  53: 95 */     Type type = this.definition.getParameterType(name);
/*  54: 96 */     if (type == null) {
/*  55: 97 */       throw new IllegalArgumentException("Undefined filter parameter [" + name + "]");
/*  56:    */     }
/*  57: 99 */     if ((value != null) && (!type.getReturnedClass().isAssignableFrom(value.getClass()))) {
/*  58:100 */       throw new IllegalArgumentException("Incorrect type for parameter [" + name + "]");
/*  59:    */     }
/*  60:102 */     this.parameters.put(name, value);
/*  61:103 */     return this;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Filter setParameterList(String name, Collection values)
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:116 */     if (values == null) {
/*  68:117 */       throw new IllegalArgumentException("Collection must be not null!");
/*  69:    */     }
/*  70:119 */     Type type = this.definition.getParameterType(name);
/*  71:120 */     if (type == null) {
/*  72:121 */       throw new HibernateException("Undefined filter parameter [" + name + "]");
/*  73:    */     }
/*  74:123 */     if (values.size() > 0)
/*  75:    */     {
/*  76:124 */       Class elementClass = values.iterator().next().getClass();
/*  77:125 */       if (!type.getReturnedClass().isAssignableFrom(elementClass)) {
/*  78:126 */         throw new HibernateException("Incorrect type for parameter [" + name + "]");
/*  79:    */       }
/*  80:    */     }
/*  81:129 */     this.parameters.put(name, values);
/*  82:130 */     return this;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Filter setParameterList(String name, Object[] values)
/*  86:    */     throws IllegalArgumentException
/*  87:    */   {
/*  88:142 */     return setParameterList(name, Arrays.asList(values));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Object getParameter(String name)
/*  92:    */   {
/*  93:152 */     return this.parameters.get(name);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void validate()
/*  97:    */     throws HibernateException
/*  98:    */   {
/*  99:164 */     Iterator itr = this.definition.getParameterNames().iterator();
/* 100:165 */     while (itr.hasNext())
/* 101:    */     {
/* 102:166 */       String parameterName = (String)itr.next();
/* 103:167 */       if (this.parameters.get(parameterName) == null) {
/* 104:168 */         throw new HibernateException("Filter [" + getName() + "] parameter [" + parameterName + "] value not set");
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.FilterImpl
 * JD-Core Version:    0.7.0.1
 */