/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.EmbeddedId;
/*   5:    */ import javax.persistence.Id;
/*   6:    */ import javax.persistence.JoinColumn;
/*   7:    */ import javax.persistence.JoinTable;
/*   8:    */ import org.hibernate.AnnotationException;
/*   9:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  10:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  11:    */ import org.hibernate.mapping.Component;
/*  12:    */ import org.hibernate.mapping.Join;
/*  13:    */ import org.hibernate.mapping.KeyValue;
/*  14:    */ import org.hibernate.mapping.PersistentClass;
/*  15:    */ import org.hibernate.mapping.Property;
/*  16:    */ import org.hibernate.mapping.Table;
/*  17:    */ 
/*  18:    */ public class ComponentPropertyHolder
/*  19:    */   extends AbstractPropertyHolder
/*  20:    */ {
/*  21:    */   private Component component;
/*  22:    */   private boolean isOrWithinEmbeddedId;
/*  23:    */   
/*  24:    */   public String getEntityName()
/*  25:    */   {
/*  26: 53 */     return this.component.getComponentClassName();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void addProperty(Property prop, Ejb3Column[] columns, XClass declaringClass)
/*  30:    */   {
/*  31: 63 */     if (columns != null)
/*  32:    */     {
/*  33: 64 */       Table table = columns[0].getTable();
/*  34: 65 */       if (!table.equals(this.component.getTable())) {
/*  35: 66 */         if (this.component.getPropertySpan() == 0) {
/*  36: 67 */           this.component.setTable(table);
/*  37:    */         } else {
/*  38: 70 */           throw new AnnotationException("A component cannot hold properties split into 2 different tables: " + getPath());
/*  39:    */         }
/*  40:    */       }
/*  41:    */     }
/*  42: 77 */     addProperty(prop, declaringClass);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Join addJoin(JoinTable joinTableAnn, boolean noDelayInPkColumnCreation)
/*  46:    */   {
/*  47: 81 */     return this.parent.addJoin(joinTableAnn, noDelayInPkColumnCreation);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ComponentPropertyHolder(Component component, String path, PropertyData inferredData, PropertyHolder parent, Mappings mappings)
/*  51:    */   {
/*  52: 91 */     super(path, parent, inferredData.getPropertyClass(), mappings);
/*  53: 92 */     XProperty property = inferredData.getProperty();
/*  54: 93 */     setCurrentProperty(property);
/*  55: 94 */     this.component = component;
/*  56: 95 */     this.isOrWithinEmbeddedId = ((parent.isOrWithinEmbeddedId()) || ((property != null) && ((property.isAnnotationPresent(Id.class)) || (property.isAnnotationPresent(EmbeddedId.class)))));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getClassName()
/*  60:    */   {
/*  61:103 */     return this.component.getComponentClassName();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getEntityOwnerClassName()
/*  65:    */   {
/*  66:107 */     return this.component.getOwner().getClassName();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Table getTable()
/*  70:    */   {
/*  71:111 */     return this.component.getTable();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void addProperty(Property prop, XClass declaringClass)
/*  75:    */   {
/*  76:115 */     this.component.addProperty(prop);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public KeyValue getIdentifier()
/*  80:    */   {
/*  81:119 */     return this.component.getOwner().getIdentifier();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isOrWithinEmbeddedId()
/*  85:    */   {
/*  86:123 */     return this.isOrWithinEmbeddedId;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public PersistentClass getPersistentClass()
/*  90:    */   {
/*  91:127 */     return this.component.getOwner();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isComponent()
/*  95:    */   {
/*  96:131 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isEntity()
/* 100:    */   {
/* 101:135 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setParentProperty(String parentProperty)
/* 105:    */   {
/* 106:139 */     this.component.setParentProperty(parentProperty);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Column[] getOverriddenColumn(String propertyName)
/* 110:    */   {
/* 111:145 */     Column[] result = super.getOverriddenColumn(propertyName);
/* 112:146 */     if (result == null)
/* 113:    */     {
/* 114:147 */       String userPropertyName = extractUserPropertyName("id", propertyName);
/* 115:148 */       if (userPropertyName != null) {
/* 116:148 */         result = super.getOverriddenColumn(userPropertyName);
/* 117:    */       }
/* 118:    */     }
/* 119:150 */     if (result == null)
/* 120:    */     {
/* 121:151 */       String userPropertyName = extractUserPropertyName("_identifierMapper", propertyName);
/* 122:152 */       if (userPropertyName != null) {
/* 123:152 */         result = super.getOverriddenColumn(userPropertyName);
/* 124:    */       }
/* 125:    */     }
/* 126:154 */     return result;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private String extractUserPropertyName(String redundantString, String propertyName)
/* 130:    */   {
/* 131:158 */     String result = null;
/* 132:159 */     String className = this.component.getOwner().getClassName();
/* 133:160 */     if ((propertyName.startsWith(className)) && (propertyName.length() > className.length() + 2 + redundantString.length()) && (propertyName.substring(className.length() + 1, className.length() + 1 + redundantString.length()).equals(redundantString))) {
/* 134:167 */       result = className + propertyName.substring(className.length() + 1 + redundantString.length());
/* 135:    */     }
/* 136:169 */     return result;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public JoinColumn[] getOverriddenJoinColumn(String propertyName)
/* 140:    */   {
/* 141:174 */     return super.getOverriddenJoinColumn(propertyName);
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ComponentPropertyHolder
 * JD-Core Version:    0.7.0.1
 */