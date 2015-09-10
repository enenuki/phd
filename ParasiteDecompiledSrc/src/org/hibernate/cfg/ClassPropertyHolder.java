/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import javax.persistence.JoinTable;
/*   6:    */ import org.hibernate.annotations.common.AssertionFailure;
/*   7:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*   8:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   9:    */ import org.hibernate.cfg.annotations.EntityBinder;
/*  10:    */ import org.hibernate.mapping.Component;
/*  11:    */ import org.hibernate.mapping.Join;
/*  12:    */ import org.hibernate.mapping.KeyValue;
/*  13:    */ import org.hibernate.mapping.MappedSuperclass;
/*  14:    */ import org.hibernate.mapping.PersistentClass;
/*  15:    */ import org.hibernate.mapping.Property;
/*  16:    */ import org.hibernate.mapping.Table;
/*  17:    */ import org.hibernate.mapping.Value;
/*  18:    */ 
/*  19:    */ public class ClassPropertyHolder
/*  20:    */   extends AbstractPropertyHolder
/*  21:    */ {
/*  22:    */   private PersistentClass persistentClass;
/*  23:    */   private Map<String, Join> joins;
/*  24:    */   private transient Map<String, Join> joinsPerRealTableName;
/*  25:    */   private EntityBinder entityBinder;
/*  26:    */   private final Map<XClass, InheritanceState> inheritanceStatePerClass;
/*  27:    */   
/*  28:    */   public ClassPropertyHolder(PersistentClass persistentClass, XClass clazzToProcess, Map<String, Join> joins, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/*  29:    */   {
/*  30: 56 */     super(persistentClass.getEntityName(), null, clazzToProcess, mappings);
/*  31: 57 */     this.persistentClass = persistentClass;
/*  32: 58 */     this.joins = joins;
/*  33: 59 */     this.inheritanceStatePerClass = inheritanceStatePerClass;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ClassPropertyHolder(PersistentClass persistentClass, XClass clazzToProcess, EntityBinder entityBinder, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/*  37:    */   {
/*  38: 68 */     this(persistentClass, clazzToProcess, entityBinder.getSecondaryTables(), mappings, inheritanceStatePerClass);
/*  39: 69 */     this.entityBinder = entityBinder;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getEntityName()
/*  43:    */   {
/*  44: 73 */     return this.persistentClass.getEntityName();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addProperty(Property prop, Ejb3Column[] columns, XClass declaringClass)
/*  48:    */   {
/*  49: 78 */     if ((columns != null) && (columns[0].isSecondary()))
/*  50:    */     {
/*  51: 80 */       Join join = columns[0].getJoin();
/*  52: 81 */       addPropertyToJoin(prop, declaringClass, join);
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 84 */       addProperty(prop, declaringClass);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addProperty(Property prop, XClass declaringClass)
/*  61:    */   {
/*  62: 89 */     if ((prop.getValue() instanceof Component))
/*  63:    */     {
/*  64: 91 */       String tableName = prop.getValue().getTable().getName();
/*  65: 92 */       if (getJoinsPerRealTableName().containsKey(tableName))
/*  66:    */       {
/*  67: 93 */         Join join = (Join)getJoinsPerRealTableName().get(tableName);
/*  68: 94 */         addPropertyToJoin(prop, declaringClass, join);
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72: 97 */         addPropertyToPersistentClass(prop, declaringClass);
/*  73:    */       }
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:101 */       addPropertyToPersistentClass(prop, declaringClass);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Join addJoin(JoinTable joinTableAnn, boolean noDelayInPkColumnCreation)
/*  82:    */   {
/*  83:106 */     Join join = this.entityBinder.addJoin(joinTableAnn, this, noDelayInPkColumnCreation);
/*  84:107 */     this.joins = this.entityBinder.getSecondaryTables();
/*  85:108 */     return join;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void addPropertyToPersistentClass(Property prop, XClass declaringClass)
/*  89:    */   {
/*  90:112 */     if (declaringClass != null)
/*  91:    */     {
/*  92:113 */       InheritanceState inheritanceState = (InheritanceState)this.inheritanceStatePerClass.get(declaringClass);
/*  93:114 */       if (inheritanceState == null) {
/*  94:115 */         throw new AssertionFailure("Declaring class is not found in the inheritance state hierarchy: " + declaringClass);
/*  95:    */       }
/*  96:119 */       if (inheritanceState.isEmbeddableSuperclass())
/*  97:    */       {
/*  98:120 */         this.persistentClass.addMappedsuperclassProperty(prop);
/*  99:121 */         addPropertyToMappedSuperclass(prop, declaringClass);
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:124 */         this.persistentClass.addProperty(prop);
/* 104:    */       }
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:128 */       this.persistentClass.addProperty(prop);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void addPropertyToMappedSuperclass(Property prop, XClass declaringClass)
/* 113:    */   {
/* 114:133 */     Mappings mappings = getMappings();
/* 115:134 */     Class type = mappings.getReflectionManager().toClass(declaringClass);
/* 116:135 */     MappedSuperclass superclass = mappings.getMappedSuperclass(type);
/* 117:136 */     superclass.addDeclaredProperty(prop);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void addPropertyToJoin(Property prop, XClass declaringClass, Join join)
/* 121:    */   {
/* 122:140 */     if (declaringClass != null)
/* 123:    */     {
/* 124:141 */       InheritanceState inheritanceState = (InheritanceState)this.inheritanceStatePerClass.get(declaringClass);
/* 125:142 */       if (inheritanceState == null) {
/* 126:143 */         throw new AssertionFailure("Declaring class is not found in the inheritance state hierarchy: " + declaringClass);
/* 127:    */       }
/* 128:147 */       if (inheritanceState.isEmbeddableSuperclass())
/* 129:    */       {
/* 130:148 */         join.addMappedsuperclassProperty(prop);
/* 131:149 */         addPropertyToMappedSuperclass(prop, declaringClass);
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:152 */         join.addProperty(prop);
/* 136:    */       }
/* 137:    */     }
/* 138:    */     else
/* 139:    */     {
/* 140:156 */       join.addProperty(prop);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   private Map<String, Join> getJoinsPerRealTableName()
/* 145:    */   {
/* 146:165 */     if (this.joinsPerRealTableName == null)
/* 147:    */     {
/* 148:166 */       this.joinsPerRealTableName = new HashMap(this.joins.size());
/* 149:167 */       for (Join join : this.joins.values()) {
/* 150:168 */         this.joinsPerRealTableName.put(join.getTable().getName(), join);
/* 151:    */       }
/* 152:    */     }
/* 153:171 */     return this.joinsPerRealTableName;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getClassName()
/* 157:    */   {
/* 158:175 */     return this.persistentClass.getClassName();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getEntityOwnerClassName()
/* 162:    */   {
/* 163:179 */     return getClassName();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Table getTable()
/* 167:    */   {
/* 168:183 */     return this.persistentClass.getTable();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isComponent()
/* 172:    */   {
/* 173:187 */     return false;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isEntity()
/* 177:    */   {
/* 178:191 */     return true;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public PersistentClass getPersistentClass()
/* 182:    */   {
/* 183:195 */     return this.persistentClass;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public KeyValue getIdentifier()
/* 187:    */   {
/* 188:199 */     return this.persistentClass.getIdentifier();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isOrWithinEmbeddedId()
/* 192:    */   {
/* 193:203 */     return false;
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ClassPropertyHolder
 * JD-Core Version:    0.7.0.1
 */