/*   1:    */ package org.junit.experimental.theories.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.junit.experimental.theories.DataPoint;
/*   9:    */ import org.junit.experimental.theories.DataPoints;
/*  10:    */ import org.junit.experimental.theories.ParameterSignature;
/*  11:    */ import org.junit.experimental.theories.ParameterSupplier;
/*  12:    */ import org.junit.experimental.theories.PotentialAssignment;
/*  13:    */ import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
/*  14:    */ import org.junit.runners.model.FrameworkMethod;
/*  15:    */ import org.junit.runners.model.TestClass;
/*  16:    */ 
/*  17:    */ public class AllMembersSupplier
/*  18:    */   extends ParameterSupplier
/*  19:    */ {
/*  20:    */   private final TestClass fClass;
/*  21:    */   
/*  22:    */   static class MethodParameterValue
/*  23:    */     extends PotentialAssignment
/*  24:    */   {
/*  25:    */     private final FrameworkMethod fMethod;
/*  26:    */     
/*  27:    */     private MethodParameterValue(FrameworkMethod dataPointMethod)
/*  28:    */     {
/*  29: 28 */       this.fMethod = dataPointMethod;
/*  30:    */     }
/*  31:    */     
/*  32:    */     public Object getValue()
/*  33:    */       throws PotentialAssignment.CouldNotGenerateValueException
/*  34:    */     {
/*  35:    */       try
/*  36:    */       {
/*  37: 34 */         return this.fMethod.invokeExplosively(null, new Object[0]);
/*  38:    */       }
/*  39:    */       catch (IllegalArgumentException e)
/*  40:    */       {
/*  41: 36 */         throw new RuntimeException("unexpected: argument length is checked");
/*  42:    */       }
/*  43:    */       catch (IllegalAccessException e)
/*  44:    */       {
/*  45: 39 */         throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
/*  46:    */       }
/*  47:    */       catch (Throwable e)
/*  48:    */       {
/*  49: 42 */         throw new PotentialAssignment.CouldNotGenerateValueException();
/*  50:    */       }
/*  51:    */     }
/*  52:    */     
/*  53:    */     public String getDescription()
/*  54:    */       throws PotentialAssignment.CouldNotGenerateValueException
/*  55:    */     {
/*  56: 49 */       return this.fMethod.getName();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AllMembersSupplier(TestClass type)
/*  61:    */   {
/*  62: 59 */     this.fClass = type;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<PotentialAssignment> getValueSources(ParameterSignature sig)
/*  66:    */   {
/*  67: 64 */     List<PotentialAssignment> list = new ArrayList();
/*  68:    */     
/*  69: 66 */     addFields(sig, list);
/*  70: 67 */     addSinglePointMethods(sig, list);
/*  71: 68 */     addMultiPointMethods(list);
/*  72:    */     
/*  73: 70 */     return list;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void addMultiPointMethods(List<PotentialAssignment> list)
/*  77:    */   {
/*  78: 74 */     for (FrameworkMethod dataPointsMethod : this.fClass.getAnnotatedMethods(DataPoints.class)) {
/*  79:    */       try
/*  80:    */       {
/*  81: 77 */         addArrayValues(dataPointsMethod.getName(), list, dataPointsMethod.invokeExplosively(null, new Object[0]));
/*  82:    */       }
/*  83:    */       catch (Throwable e) {}
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void addSinglePointMethods(ParameterSignature sig, List<PotentialAssignment> list)
/*  88:    */   {
/*  89: 85 */     for (FrameworkMethod dataPointMethod : this.fClass.getAnnotatedMethods(DataPoint.class))
/*  90:    */     {
/*  91: 87 */       Class<?> type = sig.getType();
/*  92: 88 */       if (dataPointMethod.producesType(type)) {
/*  93: 89 */         list.add(new MethodParameterValue(dataPointMethod, null));
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void addFields(ParameterSignature sig, List<PotentialAssignment> list)
/*  99:    */   {
/* 100: 95 */     for (Field field : this.fClass.getJavaClass().getFields()) {
/* 101: 96 */       if (Modifier.isStatic(field.getModifiers()))
/* 102:    */       {
/* 103: 97 */         Class<?> type = field.getType();
/* 104: 98 */         if ((sig.canAcceptArrayType(type)) && (field.getAnnotation(DataPoints.class) != null)) {
/* 105:100 */           addArrayValues(field.getName(), list, getStaticFieldValue(field));
/* 106:101 */         } else if ((sig.canAcceptType(type)) && (field.getAnnotation(DataPoint.class) != null)) {
/* 107:103 */           list.add(PotentialAssignment.forValue(field.getName(), getStaticFieldValue(field)));
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void addArrayValues(String name, List<PotentialAssignment> list, Object array)
/* 114:    */   {
/* 115:111 */     for (int i = 0; i < Array.getLength(array); i++) {
/* 116:112 */       list.add(PotentialAssignment.forValue(name + "[" + i + "]", Array.get(array, i)));
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private Object getStaticFieldValue(Field field)
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:117 */       return field.get(null);
/* 125:    */     }
/* 126:    */     catch (IllegalArgumentException e)
/* 127:    */     {
/* 128:119 */       throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
/* 129:    */     }
/* 130:    */     catch (IllegalAccessException e)
/* 131:    */     {
/* 132:122 */       throw new RuntimeException("unexpected: getFields returned an inaccessible field");
/* 133:    */     }
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.internal.AllMembersSupplier
 * JD-Core Version:    0.7.0.1
 */