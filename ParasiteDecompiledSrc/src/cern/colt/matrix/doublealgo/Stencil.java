package cern.colt.matrix.doublealgo;

import cern.colt.function.Double27Function;
import cern.colt.function.Double9Function;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix2DProcedure;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.DoubleMatrix3DProcedure;

public class Stencil
{
  public static int stencil27(DoubleMatrix3D paramDoubleMatrix3D, Double27Function paramDouble27Function, int paramInt1, DoubleMatrix3DProcedure paramDoubleMatrix3DProcedure, int paramInt2)
  {
    DoubleMatrix3D localDoubleMatrix3D = paramDoubleMatrix3D.copy();
    if (paramInt2 <= 1) {
      paramInt2 = 2;
    }
    if (paramInt2 % 2 != 0) {
      paramInt2++;
    }
    int i = 0;
    while (i < paramInt1)
    {
      paramDoubleMatrix3D.zAssign27Neighbors(localDoubleMatrix3D, paramDouble27Function);
      localDoubleMatrix3D.zAssign27Neighbors(paramDoubleMatrix3D, paramDouble27Function);
      i += 2;
      if ((i % paramInt2 == 0) && (paramDoubleMatrix3DProcedure != null) && (paramDoubleMatrix3DProcedure.apply(paramDoubleMatrix3D))) {
        return i;
      }
    }
    return i;
  }
  
  public static int stencil9(DoubleMatrix2D paramDoubleMatrix2D, Double9Function paramDouble9Function, int paramInt1, DoubleMatrix2DProcedure paramDoubleMatrix2DProcedure, int paramInt2)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    if (paramInt2 <= 1) {
      paramInt2 = 2;
    }
    if (paramInt2 % 2 != 0) {
      paramInt2++;
    }
    int i = 0;
    while (i < paramInt1)
    {
      paramDoubleMatrix2D.zAssign8Neighbors(localDoubleMatrix2D, paramDouble9Function);
      localDoubleMatrix2D.zAssign8Neighbors(paramDoubleMatrix2D, paramDouble9Function);
      i += 2;
      if ((i % paramInt2 == 0) && (paramDoubleMatrix2DProcedure != null) && (paramDoubleMatrix2DProcedure.apply(paramDoubleMatrix2D))) {
        return i;
      }
    }
    return i;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Stencil
 * JD-Core Version:    0.7.0.1
 */