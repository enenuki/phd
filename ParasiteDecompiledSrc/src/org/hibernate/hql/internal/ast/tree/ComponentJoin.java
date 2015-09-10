/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import org.hibernate.QueryException;
/*   4:    */ import org.hibernate.hql.internal.NameGenerator;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ import org.hibernate.persister.collection.QueryableCollection;
/*   7:    */ import org.hibernate.persister.entity.PropertyMapping;
/*   8:    */ import org.hibernate.type.ComponentType;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class ComponentJoin
/*  12:    */   extends FromElement
/*  13:    */ {
/*  14:    */   private final String componentPath;
/*  15:    */   private final ComponentType componentType;
/*  16:    */   private final String componentProperty;
/*  17:    */   private final String columns;
/*  18:    */   
/*  19:    */   public ComponentJoin(FromClause fromClause, FromElement origin, String alias, String componentPath, ComponentType componentType)
/*  20:    */   {
/*  21: 52 */     super(fromClause, origin, alias);
/*  22: 53 */     this.componentPath = componentPath;
/*  23: 54 */     this.componentType = componentType;
/*  24: 55 */     this.componentProperty = StringHelper.unqualify(componentPath);
/*  25: 56 */     fromClause.addJoinByPathMap(componentPath, this);
/*  26: 57 */     initializeComponentJoin(new ComponentFromElementType(this));
/*  27:    */     
/*  28: 59 */     String[] cols = origin.getPropertyMapping("").toColumns(getTableAlias(), this.componentProperty);
/*  29: 60 */     StringBuffer buf = new StringBuffer();
/*  30: 61 */     for (int j = 0; j < cols.length; j++)
/*  31:    */     {
/*  32: 62 */       String column = cols[j];
/*  33: 63 */       if (j > 0) {
/*  34: 64 */         buf.append(", ");
/*  35:    */       }
/*  36: 66 */       buf.append(column);
/*  37:    */     }
/*  38: 68 */     this.columns = buf.toString();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getComponentPath()
/*  42:    */   {
/*  43: 72 */     return this.componentPath;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getComponentProperty()
/*  47:    */   {
/*  48: 76 */     return this.componentProperty;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ComponentType getComponentType()
/*  52:    */   {
/*  53: 80 */     return this.componentType;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Type getDataType()
/*  57:    */   {
/*  58: 86 */     return getComponentType();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getIdentityColumn()
/*  62:    */   {
/*  63: 99 */     return this.columns;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getDisplayText()
/*  67:    */   {
/*  68:107 */     return "ComponentJoin{path=" + getComponentPath() + ", type=" + this.componentType.getReturnedClass() + "}";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public class ComponentFromElementType
/*  72:    */     extends FromElementType
/*  73:    */   {
/*  74:111 */     private final PropertyMapping propertyMapping = new ComponentJoin.ComponentPropertyMapping(ComponentJoin.this, null);
/*  75:    */     
/*  76:    */     public ComponentFromElementType(FromElement fromElement)
/*  77:    */     {
/*  78:114 */       super();
/*  79:    */     }
/*  80:    */     
/*  81:    */     public Type getDataType()
/*  82:    */     {
/*  83:119 */       return ComponentJoin.this.getComponentType();
/*  84:    */     }
/*  85:    */     
/*  86:    */     public QueryableCollection getQueryableCollection()
/*  87:    */     {
/*  88:127 */       return null;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public PropertyMapping getPropertyMapping(String propertyName)
/*  92:    */     {
/*  93:135 */       return this.propertyMapping;
/*  94:    */     }
/*  95:    */     
/*  96:    */     public Type getPropertyType(String propertyName, String propertyPath)
/*  97:    */     {
/*  98:143 */       int index = ComponentJoin.this.getComponentType().getPropertyIndex(propertyName);
/*  99:144 */       return ComponentJoin.this.getComponentType().getSubtypes()[index];
/* 100:    */     }
/* 101:    */     
/* 102:    */     public String renderScalarIdentifierSelect(int i)
/* 103:    */     {
/* 104:149 */       String[] cols = ComponentJoin.this.getBasePropertyMapping().toColumns(ComponentJoin.this.getTableAlias(), ComponentJoin.this.getComponentProperty());
/* 105:150 */       StringBuffer buf = new StringBuffer();
/* 106:152 */       for (int j = 0; j < cols.length; j++)
/* 107:    */       {
/* 108:153 */         String column = cols[j];
/* 109:154 */         if (j > 0) {
/* 110:155 */           buf.append(", ");
/* 111:    */         }
/* 112:157 */         buf.append(column).append(" as ").append(NameGenerator.scalarName(i, j));
/* 113:    */       }
/* 114:159 */       return buf.toString();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected PropertyMapping getBasePropertyMapping()
/* 119:    */   {
/* 120:164 */     return getOrigin().getPropertyMapping("");
/* 121:    */   }
/* 122:    */   
/* 123:    */   private final class ComponentPropertyMapping
/* 124:    */     implements PropertyMapping
/* 125:    */   {
/* 126:    */     private ComponentPropertyMapping() {}
/* 127:    */     
/* 128:    */     public Type getType()
/* 129:    */     {
/* 130:169 */       return ComponentJoin.this.getComponentType();
/* 131:    */     }
/* 132:    */     
/* 133:    */     public Type toType(String propertyName)
/* 134:    */       throws QueryException
/* 135:    */     {
/* 136:173 */       return ComponentJoin.this.getBasePropertyMapping().toType(getPropertyPath(propertyName));
/* 137:    */     }
/* 138:    */     
/* 139:    */     protected String getPropertyPath(String propertyName)
/* 140:    */     {
/* 141:177 */       return ComponentJoin.this.getComponentPath() + '.' + propertyName;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public String[] toColumns(String alias, String propertyName)
/* 145:    */       throws QueryException
/* 146:    */     {
/* 147:181 */       return ComponentJoin.this.getBasePropertyMapping().toColumns(alias, getPropertyPath(propertyName));
/* 148:    */     }
/* 149:    */     
/* 150:    */     public String[] toColumns(String propertyName)
/* 151:    */       throws QueryException, UnsupportedOperationException
/* 152:    */     {
/* 153:185 */       return ComponentJoin.this.getBasePropertyMapping().toColumns(getPropertyPath(propertyName));
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ComponentJoin
 * JD-Core Version:    0.7.0.1
 */