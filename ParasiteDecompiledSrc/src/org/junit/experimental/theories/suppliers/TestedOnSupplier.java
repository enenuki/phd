/*  1:   */ package org.junit.experimental.theories.suppliers;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.experimental.theories.ParameterSignature;
/*  7:   */ import org.junit.experimental.theories.ParameterSupplier;
/*  8:   */ import org.junit.experimental.theories.PotentialAssignment;
/*  9:   */ 
/* 10:   */ public class TestedOnSupplier
/* 11:   */   extends ParameterSupplier
/* 12:   */ {
/* 13:   */   public List<PotentialAssignment> getValueSources(ParameterSignature sig)
/* 14:   */   {
/* 15:15 */     List<PotentialAssignment> list = new ArrayList();
/* 16:16 */     TestedOn testedOn = (TestedOn)sig.getAnnotation(TestedOn.class);
/* 17:17 */     int[] ints = testedOn.ints();
/* 18:18 */     for (int i : ints) {
/* 19:19 */       list.add(PotentialAssignment.forValue(Arrays.asList(new int[][] { ints }).toString(), Integer.valueOf(i)));
/* 20:   */     }
/* 21:21 */     return list;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.suppliers.TestedOnSupplier
 * JD-Core Version:    0.7.0.1
 */