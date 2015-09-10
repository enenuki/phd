package cern.colt.matrix.doublealgo;

import cern.colt.PersistentObject;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;

/**
 * @deprecated
 */
public class Transform
  extends PersistentObject
{
  public static final Transform transform = new Transform();
  private static final Functions F = Functions.functions;
  
  public static DoubleMatrix1D abs(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return paramDoubleMatrix1D.assign(Functions.abs);
  }
  
  public static DoubleMatrix2D abs(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return paramDoubleMatrix2D.assign(Functions.abs);
  }
  
  public static DoubleMatrix1D div(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    return paramDoubleMatrix1D.assign(Functions.div(paramDouble));
  }
  
  public static DoubleMatrix1D div(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.div);
  }
  
  public static DoubleMatrix2D div(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.div(paramDouble));
  }
  
  public static DoubleMatrix2D div(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.div);
  }
  
  public static DoubleMatrix2D equals(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.equals(paramDouble));
  }
  
  public static DoubleMatrix2D equals(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.equals);
  }
  
  public static DoubleMatrix2D greater(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.greater(paramDouble));
  }
  
  public static DoubleMatrix2D greater(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.greater);
  }
  
  public static DoubleMatrix2D less(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.less(paramDouble));
  }
  
  public static DoubleMatrix2D less(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.less);
  }
  
  public static DoubleMatrix1D minus(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    return paramDoubleMatrix1D.assign(Functions.minus(paramDouble));
  }
  
  public static DoubleMatrix1D minus(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.minus);
  }
  
  public static DoubleMatrix2D minus(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.minus(paramDouble));
  }
  
  public static DoubleMatrix2D minus(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.minus);
  }
  
  public static DoubleMatrix1D minusMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.minusMult(paramDouble));
  }
  
  public static DoubleMatrix2D minusMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.minusMult(paramDouble));
  }
  
  public static DoubleMatrix1D mult(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    return paramDoubleMatrix1D.assign(Functions.mult(paramDouble));
  }
  
  public static DoubleMatrix1D mult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.mult);
  }
  
  public static DoubleMatrix2D mult(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.mult(paramDouble));
  }
  
  public static DoubleMatrix2D mult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.mult);
  }
  
  public static DoubleMatrix1D negate(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return paramDoubleMatrix1D.assign(Functions.mult(-1.0D));
  }
  
  public static DoubleMatrix2D negate(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return paramDoubleMatrix2D.assign(Functions.mult(-1.0D));
  }
  
  public static DoubleMatrix1D plus(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    return paramDoubleMatrix1D.assign(Functions.plus(paramDouble));
  }
  
  public static DoubleMatrix1D plus(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.plus);
  }
  
  public static DoubleMatrix2D plus(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.plus(paramDouble));
  }
  
  public static DoubleMatrix2D plus(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.plus);
  }
  
  public static DoubleMatrix1D plusMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.plusMult(paramDouble));
  }
  
  public static DoubleMatrix2D plusMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.plusMult(paramDouble));
  }
  
  public static DoubleMatrix1D pow(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    return paramDoubleMatrix1D.assign(Functions.pow(paramDouble));
  }
  
  public static DoubleMatrix1D pow(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.pow);
  }
  
  public static DoubleMatrix2D pow(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    return paramDoubleMatrix2D.assign(Functions.pow(paramDouble));
  }
  
  public static DoubleMatrix2D pow(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, Functions.pow);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Transform
 * JD-Core Version:    0.7.0.1
 */