/*   1:    */ package org.hibernate.hql.internal.ast;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.hibernate.hql.spi.ParameterTranslations;
/*  13:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  14:    */ import org.hibernate.param.NamedParameterSpecification;
/*  15:    */ import org.hibernate.param.ParameterSpecification;
/*  16:    */ import org.hibernate.param.PositionalParameterSpecification;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ 
/*  19:    */ public class ParameterTranslationsImpl
/*  20:    */   implements ParameterTranslations
/*  21:    */ {
/*  22:    */   private final Map namedParameters;
/*  23:    */   private final ParameterInfo[] ordinalParameters;
/*  24:    */   
/*  25:    */   public boolean supportsOrdinalParameterMetadata()
/*  26:    */   {
/*  27: 54 */     return true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getOrdinalParameterCount()
/*  31:    */   {
/*  32: 58 */     return this.ordinalParameters.length;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ParameterInfo getOrdinalParameterInfo(int ordinalPosition)
/*  36:    */   {
/*  37: 63 */     return this.ordinalParameters[(ordinalPosition - 1)];
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getOrdinalParameterSqlLocation(int ordinalPosition)
/*  41:    */   {
/*  42: 67 */     return getOrdinalParameterInfo(ordinalPosition).getSqlLocations()[0];
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Type getOrdinalParameterExpectedType(int ordinalPosition)
/*  46:    */   {
/*  47: 71 */     return getOrdinalParameterInfo(ordinalPosition).getExpectedType();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Set getNamedParameterNames()
/*  51:    */   {
/*  52: 75 */     return this.namedParameters.keySet();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ParameterInfo getNamedParameterInfo(String name)
/*  56:    */   {
/*  57: 79 */     return (ParameterInfo)this.namedParameters.get(name);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int[] getNamedParameterSqlLocations(String name)
/*  61:    */   {
/*  62: 83 */     return getNamedParameterInfo(name).getSqlLocations();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Type getNamedParameterExpectedType(String name)
/*  66:    */   {
/*  67: 87 */     return getNamedParameterInfo(name).getExpectedType();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ParameterTranslationsImpl(List parameterSpecifications)
/*  71:    */   {
/*  72:107 */     int size = parameterSpecifications.size();
/*  73:108 */     List ordinalParameterList = new ArrayList();
/*  74:109 */     Map namedParameterMap = new HashMap();
/*  75:110 */     for (int i = 0; i < size; i++)
/*  76:    */     {
/*  77:111 */       ParameterSpecification spec = (ParameterSpecification)parameterSpecifications.get(i);
/*  78:112 */       if (PositionalParameterSpecification.class.isAssignableFrom(spec.getClass()))
/*  79:    */       {
/*  80:113 */         PositionalParameterSpecification ordinalSpec = (PositionalParameterSpecification)spec;
/*  81:114 */         ordinalParameterList.add(new ParameterInfo(i, ordinalSpec.getExpectedType()));
/*  82:    */       }
/*  83:116 */       else if (NamedParameterSpecification.class.isAssignableFrom(spec.getClass()))
/*  84:    */       {
/*  85:117 */         NamedParameterSpecification namedSpec = (NamedParameterSpecification)spec;
/*  86:118 */         Object paramHolder = (1NamedParamTempHolder)namedParameterMap.get(namedSpec.getName());
/*  87:119 */         if (paramHolder == null)
/*  88:    */         {
/*  89:120 */           paramHolder = new Object()
/*  90:    */           {
/*  91:    */             String name;
/*  92:    */             Type type;
/*  93:104 */             List positions = new ArrayList();
/*  94:120 */           };
/*  95:121 */           paramHolder.name = namedSpec.getName();
/*  96:122 */           paramHolder.type = namedSpec.getExpectedType();
/*  97:123 */           namedParameterMap.put(namedSpec.getName(), paramHolder);
/*  98:    */         }
/*  99:125 */         paramHolder.positions.add(Integer.valueOf(i));
/* 100:    */       }
/* 101:    */     }
/* 102:132 */     this.ordinalParameters = ((ParameterInfo[])ordinalParameterList.toArray(new ParameterInfo[ordinalParameterList.size()]));
/* 103:134 */     if (namedParameterMap.isEmpty())
/* 104:    */     {
/* 105:135 */       this.namedParameters = Collections.EMPTY_MAP;
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:138 */       Map namedParametersBacking = new HashMap(namedParameterMap.size());
/* 110:139 */       Iterator itr = namedParameterMap.values().iterator();
/* 111:140 */       while (itr.hasNext())
/* 112:    */       {
/* 113:141 */         Object holder = (1NamedParamTempHolder)itr.next();
/* 114:142 */         namedParametersBacking.put(holder.name, new ParameterInfo(ArrayHelper.toIntArray(holder.positions), holder.type));
/* 115:    */       }
/* 116:147 */       this.namedParameters = Collections.unmodifiableMap(namedParametersBacking);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static class ParameterInfo
/* 121:    */     implements Serializable
/* 122:    */   {
/* 123:    */     private final int[] sqlLocations;
/* 124:    */     private final Type expectedType;
/* 125:    */     
/* 126:    */     public ParameterInfo(int[] sqlPositions, Type expectedType)
/* 127:    */     {
/* 128:156 */       this.sqlLocations = sqlPositions;
/* 129:157 */       this.expectedType = expectedType;
/* 130:    */     }
/* 131:    */     
/* 132:    */     public ParameterInfo(int sqlPosition, Type expectedType)
/* 133:    */     {
/* 134:161 */       this.sqlLocations = new int[] { sqlPosition };
/* 135:162 */       this.expectedType = expectedType;
/* 136:    */     }
/* 137:    */     
/* 138:    */     public int[] getSqlLocations()
/* 139:    */     {
/* 140:166 */       return this.sqlLocations;
/* 141:    */     }
/* 142:    */     
/* 143:    */     public Type getExpectedType()
/* 144:    */     {
/* 145:170 */       return this.expectedType;
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.ParameterTranslationsImpl
 * JD-Core Version:    0.7.0.1
 */