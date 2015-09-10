/*   1:    */ package org.hibernate.tuple.component;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.mapping.Component;
/*  10:    */ import org.hibernate.mapping.Property;
/*  11:    */ import org.hibernate.tuple.PropertyFactory;
/*  12:    */ import org.hibernate.tuple.StandardProperty;
/*  13:    */ 
/*  14:    */ public class ComponentMetamodel
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private final String role;
/*  18:    */   private final boolean isKey;
/*  19:    */   private final StandardProperty[] properties;
/*  20:    */   private final EntityMode entityMode;
/*  21:    */   private final ComponentTuplizer componentTuplizer;
/*  22:    */   private final int propertySpan;
/*  23: 57 */   private final Map propertyIndexes = new HashMap();
/*  24:    */   
/*  25:    */   public ComponentMetamodel(Component component)
/*  26:    */   {
/*  27: 62 */     this.role = component.getRoleName();
/*  28: 63 */     this.isKey = component.isKey();
/*  29: 64 */     this.propertySpan = component.getPropertySpan();
/*  30: 65 */     this.properties = new StandardProperty[this.propertySpan];
/*  31: 66 */     Iterator itr = component.getPropertyIterator();
/*  32: 67 */     int i = 0;
/*  33: 68 */     while (itr.hasNext())
/*  34:    */     {
/*  35: 69 */       Property property = (Property)itr.next();
/*  36: 70 */       this.properties[i] = PropertyFactory.buildStandardProperty(property, false);
/*  37: 71 */       this.propertyIndexes.put(property.getName(), Integer.valueOf(i));
/*  38: 72 */       i++;
/*  39:    */     }
/*  40: 75 */     this.entityMode = (component.hasPojoRepresentation() ? EntityMode.POJO : EntityMode.MAP);
/*  41:    */     
/*  42:    */ 
/*  43: 78 */     ComponentTuplizerFactory componentTuplizerFactory = new ComponentTuplizerFactory();
/*  44: 79 */     String tuplizerClassName = component.getTuplizerImplClassName(this.entityMode);
/*  45: 80 */     if (tuplizerClassName == null) {
/*  46: 81 */       this.componentTuplizer = componentTuplizerFactory.constructDefaultTuplizer(this.entityMode, component);
/*  47:    */     } else {
/*  48: 84 */       this.componentTuplizer = componentTuplizerFactory.constructTuplizer(tuplizerClassName, component);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isKey()
/*  53:    */   {
/*  54: 89 */     return this.isKey;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getPropertySpan()
/*  58:    */   {
/*  59: 93 */     return this.propertySpan;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public StandardProperty[] getProperties()
/*  63:    */   {
/*  64: 97 */     return this.properties;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public StandardProperty getProperty(int index)
/*  68:    */   {
/*  69:101 */     if ((index < 0) || (index >= this.propertySpan)) {
/*  70:102 */       throw new IllegalArgumentException("illegal index value for component property access [request=" + index + ", span=" + this.propertySpan + "]");
/*  71:    */     }
/*  72:104 */     return this.properties[index];
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getPropertyIndex(String propertyName)
/*  76:    */   {
/*  77:108 */     Integer index = (Integer)this.propertyIndexes.get(propertyName);
/*  78:109 */     if (index == null) {
/*  79:110 */       throw new HibernateException("component does not contain such a property [" + propertyName + "]");
/*  80:    */     }
/*  81:112 */     return index.intValue();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public StandardProperty getProperty(String propertyName)
/*  85:    */   {
/*  86:116 */     return getProperty(getPropertyIndex(propertyName));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EntityMode getEntityMode()
/*  90:    */   {
/*  91:120 */     return this.entityMode;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ComponentTuplizer getComponentTuplizer()
/*  95:    */   {
/*  96:124 */     return this.componentTuplizer;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.ComponentMetamodel
 * JD-Core Version:    0.7.0.1
 */