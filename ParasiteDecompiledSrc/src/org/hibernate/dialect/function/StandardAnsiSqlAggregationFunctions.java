/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.QueryException;
/*   8:    */ import org.hibernate.engine.spi.Mapping;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.type.StandardBasicTypes;
/*  11:    */ import org.hibernate.type.Type;
/*  12:    */ 
/*  13:    */ public class StandardAnsiSqlAggregationFunctions
/*  14:    */ {
/*  15:    */   public static class CountFunction
/*  16:    */     extends StandardSQLFunction
/*  17:    */   {
/*  18: 48 */     public static final CountFunction INSTANCE = new CountFunction();
/*  19:    */     
/*  20:    */     public CountFunction()
/*  21:    */     {
/*  22: 51 */       super(StandardBasicTypes.LONG);
/*  23:    */     }
/*  24:    */     
/*  25:    */     public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory)
/*  26:    */     {
/*  27: 56 */       if ((arguments.size() > 1) && 
/*  28: 57 */         ("distinct".equalsIgnoreCase(arguments.get(0).toString()))) {
/*  29: 58 */         return renderCountDistinct(arguments);
/*  30:    */       }
/*  31: 61 */       return super.render(firstArgumentType, arguments, factory);
/*  32:    */     }
/*  33:    */     
/*  34:    */     private String renderCountDistinct(List arguments)
/*  35:    */     {
/*  36: 65 */       StringBuffer buffer = new StringBuffer();
/*  37: 66 */       buffer.append("count(distinct ");
/*  38: 67 */       String sep = "";
/*  39: 68 */       Iterator itr = arguments.iterator();
/*  40: 69 */       itr.next();
/*  41: 70 */       while (itr.hasNext())
/*  42:    */       {
/*  43: 71 */         buffer.append(sep).append(itr.next());
/*  44:    */         
/*  45: 73 */         sep = ", ";
/*  46:    */       }
/*  47: 75 */       return ")";
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static class AvgFunction
/*  52:    */     extends StandardSQLFunction
/*  53:    */   {
/*  54: 84 */     public static final AvgFunction INSTANCE = new AvgFunction();
/*  55:    */     
/*  56:    */     public AvgFunction()
/*  57:    */     {
/*  58: 87 */       super(StandardBasicTypes.DOUBLE);
/*  59:    */     }
/*  60:    */     
/*  61:    */     public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory)
/*  62:    */       throws QueryException
/*  63:    */     {
/*  64: 92 */       int jdbcTypeCode = determineJdbcTypeCode(firstArgumentType, factory);
/*  65: 93 */       return render(jdbcTypeCode, arguments.get(0).toString(), factory);
/*  66:    */     }
/*  67:    */     
/*  68:    */     protected final int determineJdbcTypeCode(Type firstArgumentType, SessionFactoryImplementor factory)
/*  69:    */       throws QueryException
/*  70:    */     {
/*  71:    */       try
/*  72:    */       {
/*  73: 98 */         int[] jdbcTypeCodes = firstArgumentType.sqlTypes(factory);
/*  74: 99 */         if (jdbcTypeCodes.length != 1) {
/*  75:100 */           throw new QueryException("multiple-column type in avg()");
/*  76:    */         }
/*  77:102 */         return jdbcTypeCodes[0];
/*  78:    */       }
/*  79:    */       catch (MappingException me)
/*  80:    */       {
/*  81:105 */         throw new QueryException(me);
/*  82:    */       }
/*  83:    */     }
/*  84:    */     
/*  85:    */     protected String render(int firstArgumentJdbcType, String argument, SessionFactoryImplementor factory)
/*  86:    */     {
/*  87:110 */       return "avg(" + renderArgument(argument, firstArgumentJdbcType) + ")";
/*  88:    */     }
/*  89:    */     
/*  90:    */     protected String renderArgument(String argument, int firstArgumentJdbcType)
/*  91:    */     {
/*  92:114 */       return argument;
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static class MaxFunction
/*  97:    */     extends StandardSQLFunction
/*  98:    */   {
/*  99:120 */     public static final MaxFunction INSTANCE = new MaxFunction();
/* 100:    */     
/* 101:    */     public MaxFunction()
/* 102:    */     {
/* 103:123 */       super();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static class MinFunction
/* 108:    */     extends StandardSQLFunction
/* 109:    */   {
/* 110:128 */     public static final MinFunction INSTANCE = new MinFunction();
/* 111:    */     
/* 112:    */     public MinFunction()
/* 113:    */     {
/* 114:131 */       super();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static class SumFunction
/* 119:    */     extends StandardSQLFunction
/* 120:    */   {
/* 121:137 */     public static final SumFunction INSTANCE = new SumFunction();
/* 122:    */     
/* 123:    */     public SumFunction()
/* 124:    */     {
/* 125:140 */       super();
/* 126:    */     }
/* 127:    */     
/* 128:    */     protected final int determineJdbcTypeCode(Type type, Mapping mapping)
/* 129:    */       throws QueryException
/* 130:    */     {
/* 131:    */       try
/* 132:    */       {
/* 133:145 */         int[] jdbcTypeCodes = type.sqlTypes(mapping);
/* 134:146 */         if (jdbcTypeCodes.length != 1) {
/* 135:147 */           throw new QueryException("multiple-column type in sum()");
/* 136:    */         }
/* 137:149 */         return jdbcTypeCodes[0];
/* 138:    */       }
/* 139:    */       catch (MappingException me)
/* 140:    */       {
/* 141:152 */         throw new QueryException(me);
/* 142:    */       }
/* 143:    */     }
/* 144:    */     
/* 145:    */     public Type getReturnType(Type firstArgumentType, Mapping mapping)
/* 146:    */     {
/* 147:157 */       int jdbcType = determineJdbcTypeCode(firstArgumentType, mapping);
/* 148:161 */       if (firstArgumentType == StandardBasicTypes.BIG_INTEGER) {
/* 149:162 */         return StandardBasicTypes.BIG_INTEGER;
/* 150:    */       }
/* 151:164 */       if (firstArgumentType == StandardBasicTypes.BIG_DECIMAL) {
/* 152:165 */         return StandardBasicTypes.BIG_DECIMAL;
/* 153:    */       }
/* 154:167 */       if ((firstArgumentType == StandardBasicTypes.LONG) || (firstArgumentType == StandardBasicTypes.SHORT) || (firstArgumentType == StandardBasicTypes.INTEGER)) {
/* 155:170 */         return StandardBasicTypes.LONG;
/* 156:    */       }
/* 157:172 */       if ((firstArgumentType == StandardBasicTypes.FLOAT) || (firstArgumentType == StandardBasicTypes.DOUBLE)) {
/* 158:173 */         return StandardBasicTypes.DOUBLE;
/* 159:    */       }
/* 160:180 */       if ((jdbcType == 6) || (jdbcType == 8) || (jdbcType == 3) || (jdbcType == 7)) {
/* 161:184 */         return StandardBasicTypes.DOUBLE;
/* 162:    */       }
/* 163:186 */       if ((jdbcType == -5) || (jdbcType == 4) || (jdbcType == 5) || (jdbcType == -6)) {
/* 164:190 */         return StandardBasicTypes.LONG;
/* 165:    */       }
/* 166:194 */       return firstArgumentType;
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static void primeFunctionMap(Map<String, SQLFunction> functionMap)
/* 171:    */   {
/* 172:199 */     functionMap.put(AvgFunction.INSTANCE.getName(), AvgFunction.INSTANCE);
/* 173:200 */     functionMap.put(CountFunction.INSTANCE.getName(), CountFunction.INSTANCE);
/* 174:201 */     functionMap.put(MaxFunction.INSTANCE.getName(), MaxFunction.INSTANCE);
/* 175:202 */     functionMap.put(MinFunction.INSTANCE.getName(), MinFunction.INSTANCE);
/* 176:203 */     functionMap.put(SumFunction.INSTANCE.getName(), SumFunction.INSTANCE);
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.StandardAnsiSqlAggregationFunctions
 * JD-Core Version:    0.7.0.1
 */