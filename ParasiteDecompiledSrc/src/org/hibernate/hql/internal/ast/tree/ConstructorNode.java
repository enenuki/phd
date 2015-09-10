/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.lang.reflect.Constructor;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.PropertyNotFoundException;
/*  10:    */ import org.hibernate.QueryException;
/*  11:    */ import org.hibernate.hql.internal.ast.DetailedSemanticException;
/*  12:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  13:    */ import org.hibernate.internal.util.ReflectHelper;
/*  14:    */ import org.hibernate.internal.util.StringHelper;
/*  15:    */ import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
/*  16:    */ import org.hibernate.transform.ResultTransformer;
/*  17:    */ import org.hibernate.transform.Transformers;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class ConstructorNode
/*  21:    */   extends SelectExpressionList
/*  22:    */   implements AggregatedSelectExpression
/*  23:    */ {
/*  24:    */   private Class resultType;
/*  25:    */   private Constructor constructor;
/*  26:    */   private Type[] constructorArgumentTypes;
/*  27:    */   private boolean isMap;
/*  28:    */   private boolean isList;
/*  29:    */   private String[] aggregatedAliases;
/*  30:    */   
/*  31:    */   public ResultTransformer getResultTransformer()
/*  32:    */   {
/*  33: 57 */     if (this.constructor != null) {
/*  34: 58 */       return new AliasToBeanConstructorResultTransformer(this.constructor);
/*  35:    */     }
/*  36: 60 */     if (this.isMap) {
/*  37: 61 */       return Transformers.ALIAS_TO_ENTITY_MAP;
/*  38:    */     }
/*  39: 63 */     if (this.isList) {
/*  40: 64 */       return Transformers.TO_LIST;
/*  41:    */     }
/*  42: 66 */     throw new QueryException("Unable to determine proper dynamic-instantiation tranformer to use.");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String[] getAggregatedAliases()
/*  46:    */   {
/*  47: 72 */     if (this.aggregatedAliases == null) {
/*  48: 73 */       this.aggregatedAliases = buildAggregatedAliases();
/*  49:    */     }
/*  50: 75 */     return this.aggregatedAliases;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private String[] buildAggregatedAliases()
/*  54:    */   {
/*  55: 79 */     SelectExpression[] selectExpressions = collectSelectExpressions();
/*  56: 80 */     String[] aliases = new String[selectExpressions.length];
/*  57: 81 */     for (int i = 0; i < selectExpressions.length; i++)
/*  58:    */     {
/*  59: 82 */       String alias = selectExpressions[i].getAlias();
/*  60: 83 */       aliases[i] = (alias == null ? Integer.toString(i) : alias);
/*  61:    */     }
/*  62: 85 */     return aliases;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setScalarColumn(int i)
/*  66:    */     throws SemanticException
/*  67:    */   {
/*  68: 89 */     SelectExpression[] selectExpressions = collectSelectExpressions();
/*  69: 91 */     for (int j = 0; j < selectExpressions.length; j++)
/*  70:    */     {
/*  71: 92 */       SelectExpression selectExpression = selectExpressions[j];
/*  72: 93 */       selectExpression.setScalarColumn(j);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getScalarColumnIndex()
/*  77:    */   {
/*  78: 98 */     return -1;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setScalarColumnText(int i)
/*  82:    */     throws SemanticException
/*  83:    */   {
/*  84:102 */     SelectExpression[] selectExpressions = collectSelectExpressions();
/*  85:104 */     for (int j = 0; j < selectExpressions.length; j++)
/*  86:    */     {
/*  87:105 */       SelectExpression selectExpression = selectExpressions[j];
/*  88:106 */       selectExpression.setScalarColumnText(j);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected AST getFirstSelectExpression()
/*  93:    */   {
/*  94:113 */     return getFirstChild().getNextSibling();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Class getAggregationResultType()
/*  98:    */   {
/*  99:118 */     return this.resultType;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @Deprecated
/* 103:    */   public Type getDataType()
/* 104:    */   {
/* 105:137 */     throw new UnsupportedOperationException("getDataType() is not supported by ConstructorNode!");
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void prepare()
/* 109:    */     throws SemanticException
/* 110:    */   {
/* 111:141 */     this.constructorArgumentTypes = resolveConstructorArgumentTypes();
/* 112:142 */     String path = ((PathNode)getFirstChild()).getPath();
/* 113:143 */     if ("map".equals(path.toLowerCase()))
/* 114:    */     {
/* 115:144 */       this.isMap = true;
/* 116:145 */       this.resultType = Map.class;
/* 117:    */     }
/* 118:147 */     else if ("list".equals(path.toLowerCase()))
/* 119:    */     {
/* 120:148 */       this.isList = true;
/* 121:149 */       this.resultType = List.class;
/* 122:    */     }
/* 123:    */     else
/* 124:    */     {
/* 125:152 */       this.constructor = resolveConstructor(path);
/* 126:153 */       this.resultType = this.constructor.getDeclaringClass();
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   private Type[] resolveConstructorArgumentTypes()
/* 131:    */     throws SemanticException
/* 132:    */   {
/* 133:158 */     SelectExpression[] argumentExpressions = collectSelectExpressions();
/* 134:159 */     if (argumentExpressions == null) {
/* 135:161 */       return new Type[0];
/* 136:    */     }
/* 137:164 */     Type[] types = new Type[argumentExpressions.length];
/* 138:165 */     for (int x = 0; x < argumentExpressions.length; x++) {
/* 139:166 */       types[x] = argumentExpressions[x].getDataType();
/* 140:    */     }
/* 141:168 */     return types;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private Constructor resolveConstructor(String path)
/* 145:    */     throws SemanticException
/* 146:    */   {
/* 147:172 */     String importedClassName = getSessionFactoryHelper().getImportedClassName(path);
/* 148:173 */     String className = StringHelper.isEmpty(importedClassName) ? path : importedClassName;
/* 149:174 */     if (className == null) {
/* 150:175 */       throw new SemanticException("Unable to locate class [" + path + "]");
/* 151:    */     }
/* 152:    */     try
/* 153:    */     {
/* 154:178 */       Class holderClass = ReflectHelper.classForName(className);
/* 155:179 */       return ReflectHelper.getConstructor(holderClass, this.constructorArgumentTypes);
/* 156:    */     }
/* 157:    */     catch (ClassNotFoundException e)
/* 158:    */     {
/* 159:182 */       throw new DetailedSemanticException("Unable to locate class [" + className + "]", e);
/* 160:    */     }
/* 161:    */     catch (PropertyNotFoundException e)
/* 162:    */     {
/* 163:187 */       throw new DetailedSemanticException("Unable to locate appropriate constructor on class [" + className + "]", e);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Constructor getConstructor()
/* 168:    */   {
/* 169:192 */     return this.constructor;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List getConstructorArgumentTypeList()
/* 173:    */   {
/* 174:196 */     return Arrays.asList(this.constructorArgumentTypes);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List getAggregatedSelectionTypeList()
/* 178:    */   {
/* 179:200 */     return getConstructorArgumentTypeList();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public FromElement getFromElement()
/* 183:    */   {
/* 184:204 */     return null;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isConstructor()
/* 188:    */   {
/* 189:208 */     return true;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isReturnableEntity()
/* 193:    */     throws SemanticException
/* 194:    */   {
/* 195:212 */     return false;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isScalar()
/* 199:    */   {
/* 200:217 */     return true;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setAlias(String alias)
/* 204:    */   {
/* 205:221 */     throw new UnsupportedOperationException("constructor may not be aliased");
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getAlias()
/* 209:    */   {
/* 210:225 */     throw new UnsupportedOperationException("constructor may not be aliased");
/* 211:    */   }
/* 212:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ConstructorNode
 * JD-Core Version:    0.7.0.1
 */