/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.QueryException;
/*   9:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  10:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  11:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  12:    */ import org.hibernate.persister.entity.Queryable;
/*  13:    */ import org.hibernate.type.ComponentType;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ 
/*  16:    */ public class IntoClause
/*  17:    */   extends HqlSqlWalkerNode
/*  18:    */   implements DisplayableNode
/*  19:    */ {
/*  20:    */   private Queryable persister;
/*  21: 50 */   private String columnSpec = "";
/*  22:    */   private Type[] types;
/*  23:    */   private boolean discriminated;
/*  24:    */   private boolean explicitIdInsertion;
/*  25:    */   private boolean explicitVersionInsertion;
/*  26:    */   private Set componentIds;
/*  27:    */   private List explicitComponentIds;
/*  28:    */   
/*  29:    */   public void initialize(Queryable persister)
/*  30:    */   {
/*  31: 61 */     if (persister.isAbstract()) {
/*  32: 62 */       throw new QueryException("cannot insert into abstract class (no table)");
/*  33:    */     }
/*  34: 64 */     this.persister = persister;
/*  35: 65 */     initializeColumns();
/*  36: 67 */     if (getWalker().getSessionFactoryHelper().hasPhysicalDiscriminatorColumn(persister))
/*  37:    */     {
/*  38: 68 */       this.discriminated = true;
/*  39: 69 */       this.columnSpec = (this.columnSpec + ", " + persister.getDiscriminatorColumnName());
/*  40:    */     }
/*  41: 72 */     resetText();
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void resetText()
/*  45:    */   {
/*  46: 76 */     setText("into " + getTableName() + " ( " + this.columnSpec + " )");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getTableName()
/*  50:    */   {
/*  51: 80 */     return this.persister.getSubclassTableName(0);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Queryable getQueryable()
/*  55:    */   {
/*  56: 84 */     return this.persister;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getEntityName()
/*  60:    */   {
/*  61: 88 */     return this.persister.getEntityName();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Type[] getInsertionTypes()
/*  65:    */   {
/*  66: 92 */     return this.types;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isDiscriminated()
/*  70:    */   {
/*  71: 96 */     return this.discriminated;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isExplicitIdInsertion()
/*  75:    */   {
/*  76:100 */     return this.explicitIdInsertion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isExplicitVersionInsertion()
/*  80:    */   {
/*  81:104 */     return this.explicitVersionInsertion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void prependIdColumnSpec()
/*  85:    */   {
/*  86:108 */     this.columnSpec = (this.persister.getIdentifierColumnNames()[0] + ", " + this.columnSpec);
/*  87:109 */     resetText();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void prependVersionColumnSpec()
/*  91:    */   {
/*  92:113 */     this.columnSpec = (this.persister.getPropertyColumnNames(this.persister.getVersionProperty())[0] + ", " + this.columnSpec);
/*  93:114 */     resetText();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void validateTypes(SelectClause selectClause)
/*  97:    */     throws QueryException
/*  98:    */   {
/*  99:118 */     Type[] selectTypes = selectClause.getQueryReturnTypes();
/* 100:119 */     if (selectTypes.length != this.types.length) {
/* 101:120 */       throw new QueryException("number of select types did not match those for insert");
/* 102:    */     }
/* 103:123 */     for (int i = 0; i < this.types.length; i++) {
/* 104:124 */       if (!areCompatible(this.types[i], selectTypes[i])) {
/* 105:125 */         throw new QueryException("insertion type [" + this.types[i] + "] and selection type [" + selectTypes[i] + "] at position " + i + " are not compatible");
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getDisplayText()
/* 111:    */   {
/* 112:141 */     StringBuffer buf = new StringBuffer();
/* 113:142 */     buf.append("IntoClause{");
/* 114:143 */     buf.append("entityName=").append(getEntityName());
/* 115:144 */     buf.append(",tableName=").append(getTableName());
/* 116:145 */     buf.append(",columns={").append(this.columnSpec).append("}");
/* 117:146 */     buf.append("}");
/* 118:147 */     return buf.toString();
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void initializeColumns()
/* 122:    */   {
/* 123:151 */     AST propertySpec = getFirstChild();
/* 124:152 */     List types = new ArrayList();
/* 125:153 */     visitPropertySpecNodes(propertySpec.getFirstChild(), types);
/* 126:154 */     this.types = ArrayHelper.toTypeArray(types);
/* 127:155 */     this.columnSpec = this.columnSpec.substring(0, this.columnSpec.length() - 2);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void visitPropertySpecNodes(AST propertyNode, List types)
/* 131:    */   {
/* 132:159 */     if (propertyNode == null) {
/* 133:160 */       return;
/* 134:    */     }
/* 135:170 */     String name = propertyNode.getText();
/* 136:171 */     if (isSuperclassProperty(name)) {
/* 137:172 */       throw new QueryException("INSERT statements cannot refer to superclass/joined properties [" + name + "]");
/* 138:    */     }
/* 139:175 */     if (!this.explicitIdInsertion) {
/* 140:176 */       if ((this.persister.getIdentifierType() instanceof ComponentType))
/* 141:    */       {
/* 142:177 */         if (this.componentIds == null)
/* 143:    */         {
/* 144:178 */           String[] propertyNames = ((ComponentType)this.persister.getIdentifierType()).getPropertyNames();
/* 145:179 */           this.componentIds = new HashSet();
/* 146:180 */           for (int i = 0; i < propertyNames.length; i++) {
/* 147:181 */             this.componentIds.add(propertyNames[i]);
/* 148:    */           }
/* 149:    */         }
/* 150:184 */         if (this.componentIds.contains(name))
/* 151:    */         {
/* 152:185 */           if (this.explicitComponentIds == null) {
/* 153:186 */             this.explicitComponentIds = new ArrayList(this.componentIds.size());
/* 154:    */           }
/* 155:188 */           this.explicitComponentIds.add(name);
/* 156:189 */           this.explicitIdInsertion = (this.explicitComponentIds.size() == this.componentIds.size());
/* 157:    */         }
/* 158:    */       }
/* 159:191 */       else if (name.equals(this.persister.getIdentifierPropertyName()))
/* 160:    */       {
/* 161:192 */         this.explicitIdInsertion = true;
/* 162:    */       }
/* 163:    */     }
/* 164:196 */     if ((this.persister.isVersioned()) && 
/* 165:197 */       (name.equals(this.persister.getPropertyNames()[this.persister.getVersionProperty()]))) {
/* 166:198 */       this.explicitVersionInsertion = true;
/* 167:    */     }
/* 168:202 */     String[] columnNames = this.persister.toColumns(name);
/* 169:203 */     renderColumns(columnNames);
/* 170:204 */     types.add(this.persister.toType(name));
/* 171:    */     
/* 172:    */ 
/* 173:207 */     visitPropertySpecNodes(propertyNode.getNextSibling(), types);
/* 174:208 */     visitPropertySpecNodes(propertyNode.getFirstChild(), types);
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void renderColumns(String[] columnNames)
/* 178:    */   {
/* 179:212 */     for (int i = 0; i < columnNames.length; i++) {
/* 180:213 */       this.columnSpec = (this.columnSpec + columnNames[i] + ", ");
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   private boolean isSuperclassProperty(String propertyName)
/* 185:    */   {
/* 186:229 */     return this.persister.getSubclassPropertyTableNumber(propertyName) != 0;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private boolean areCompatible(Type target, Type source)
/* 190:    */   {
/* 191:240 */     if (target.equals(source)) {
/* 192:242 */       return true;
/* 193:    */     }
/* 194:247 */     if (!target.getReturnedClass().isAssignableFrom(source.getReturnedClass())) {
/* 195:248 */       return false;
/* 196:    */     }
/* 197:251 */     int[] targetDatatypes = target.sqlTypes(getSessionFactoryHelper().getFactory());
/* 198:252 */     int[] sourceDatatypes = source.sqlTypes(getSessionFactoryHelper().getFactory());
/* 199:254 */     if (targetDatatypes.length != sourceDatatypes.length) {
/* 200:255 */       return false;
/* 201:    */     }
/* 202:258 */     for (int i = 0; i < targetDatatypes.length; i++) {
/* 203:259 */       if (!areSqlTypesCompatible(targetDatatypes[i], sourceDatatypes[i])) {
/* 204:260 */         return false;
/* 205:    */       }
/* 206:    */     }
/* 207:264 */     return true;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private boolean areSqlTypesCompatible(int target, int source)
/* 211:    */   {
/* 212:268 */     switch (target)
/* 213:    */     {
/* 214:    */     case 93: 
/* 215:270 */       return (source == 91) || (source == 92) || (source == 93);
/* 216:    */     case 91: 
/* 217:272 */       return (source == 91) || (source == 93);
/* 218:    */     case 92: 
/* 219:274 */       return (source == 92) || (source == 93);
/* 220:    */     }
/* 221:276 */     return target == source;
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.IntoClause
 * JD-Core Version:    0.7.0.1
 */