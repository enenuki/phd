/*   1:    */ package org.junit.experimental.theories.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.junit.experimental.theories.ParameterSignature;
/*   7:    */ import org.junit.experimental.theories.ParameterSupplier;
/*   8:    */ import org.junit.experimental.theories.ParametersSuppliedBy;
/*   9:    */ import org.junit.experimental.theories.PotentialAssignment;
/*  10:    */ import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
/*  11:    */ import org.junit.runners.model.TestClass;
/*  12:    */ 
/*  13:    */ public class Assignments
/*  14:    */ {
/*  15:    */   private List<PotentialAssignment> fAssigned;
/*  16:    */   private final List<ParameterSignature> fUnassigned;
/*  17:    */   private final TestClass fClass;
/*  18:    */   
/*  19:    */   private Assignments(List<PotentialAssignment> assigned, List<ParameterSignature> unassigned, TestClass testClass)
/*  20:    */   {
/*  21: 30 */     this.fUnassigned = unassigned;
/*  22: 31 */     this.fAssigned = assigned;
/*  23: 32 */     this.fClass = testClass;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Assignments allUnassigned(Method testMethod, TestClass testClass)
/*  27:    */     throws Exception
/*  28:    */   {
/*  29: 42 */     List<ParameterSignature> signatures = ParameterSignature.signatures(testClass.getOnlyConstructor());
/*  30:    */     
/*  31: 44 */     signatures.addAll(ParameterSignature.signatures(testMethod));
/*  32: 45 */     return new Assignments(new ArrayList(), signatures, testClass);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isComplete()
/*  36:    */   {
/*  37: 50 */     return this.fUnassigned.size() == 0;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ParameterSignature nextUnassigned()
/*  41:    */   {
/*  42: 54 */     return (ParameterSignature)this.fUnassigned.get(0);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Assignments assignNext(PotentialAssignment source)
/*  46:    */   {
/*  47: 58 */     List<PotentialAssignment> assigned = new ArrayList(this.fAssigned);
/*  48:    */     
/*  49: 60 */     assigned.add(source);
/*  50:    */     
/*  51: 62 */     return new Assignments(assigned, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object[] getActualValues(int start, int stop, boolean nullsOk)
/*  55:    */     throws PotentialAssignment.CouldNotGenerateValueException
/*  56:    */   {
/*  57: 68 */     Object[] values = new Object[stop - start];
/*  58: 69 */     for (int i = start; i < stop; i++)
/*  59:    */     {
/*  60: 70 */       Object value = ((PotentialAssignment)this.fAssigned.get(i)).getValue();
/*  61: 71 */       if ((value == null) && (!nullsOk)) {
/*  62: 72 */         throw new PotentialAssignment.CouldNotGenerateValueException();
/*  63:    */       }
/*  64: 73 */       values[(i - start)] = value;
/*  65:    */     }
/*  66: 75 */     return values;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<PotentialAssignment> potentialsForNextUnassigned()
/*  70:    */     throws InstantiationException, IllegalAccessException
/*  71:    */   {
/*  72: 80 */     ParameterSignature unassigned = nextUnassigned();
/*  73: 81 */     return getSupplier(unassigned).getValueSources(unassigned);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public ParameterSupplier getSupplier(ParameterSignature unassigned)
/*  77:    */     throws InstantiationException, IllegalAccessException
/*  78:    */   {
/*  79: 86 */     ParameterSupplier supplier = getAnnotatedSupplier(unassigned);
/*  80: 87 */     if (supplier != null) {
/*  81: 88 */       return supplier;
/*  82:    */     }
/*  83: 90 */     return new AllMembersSupplier(this.fClass);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ParameterSupplier getAnnotatedSupplier(ParameterSignature unassigned)
/*  87:    */     throws InstantiationException, IllegalAccessException
/*  88:    */   {
/*  89: 95 */     ParametersSuppliedBy annotation = (ParametersSuppliedBy)unassigned.findDeepAnnotation(ParametersSuppliedBy.class);
/*  90: 97 */     if (annotation == null) {
/*  91: 98 */       return null;
/*  92:    */     }
/*  93: 99 */     return (ParameterSupplier)annotation.value().newInstance();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Object[] getConstructorArguments(boolean nullsOk)
/*  97:    */     throws PotentialAssignment.CouldNotGenerateValueException
/*  98:    */   {
/*  99:104 */     return getActualValues(0, getConstructorParameterCount(), nullsOk);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object[] getMethodArguments(boolean nullsOk)
/* 103:    */     throws PotentialAssignment.CouldNotGenerateValueException
/* 104:    */   {
/* 105:109 */     return getActualValues(getConstructorParameterCount(), this.fAssigned.size(), nullsOk);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Object[] getAllArguments(boolean nullsOk)
/* 109:    */     throws PotentialAssignment.CouldNotGenerateValueException
/* 110:    */   {
/* 111:115 */     return getActualValues(0, this.fAssigned.size(), nullsOk);
/* 112:    */   }
/* 113:    */   
/* 114:    */   private int getConstructorParameterCount()
/* 115:    */   {
/* 116:119 */     List<ParameterSignature> signatures = ParameterSignature.signatures(this.fClass.getOnlyConstructor());
/* 117:    */     
/* 118:121 */     int constructorParameterCount = signatures.size();
/* 119:122 */     return constructorParameterCount;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Object[] getArgumentStrings(boolean nullsOk)
/* 123:    */     throws PotentialAssignment.CouldNotGenerateValueException
/* 124:    */   {
/* 125:127 */     Object[] values = new Object[this.fAssigned.size()];
/* 126:128 */     for (int i = 0; i < values.length; i++) {
/* 127:129 */       values[i] = ((PotentialAssignment)this.fAssigned.get(i)).getDescription();
/* 128:    */     }
/* 129:131 */     return values;
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.internal.Assignments
 * JD-Core Version:    0.7.0.1
 */