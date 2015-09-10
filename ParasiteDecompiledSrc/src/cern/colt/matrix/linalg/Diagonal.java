package cern.colt.matrix.linalg;

import cern.colt.matrix.DoubleMatrix2D;

class Diagonal
{
  protected Diagonal()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static boolean inverse(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    boolean bool = true;
    int i = paramDoubleMatrix2D.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = paramDoubleMatrix2D.getQuick(i, i);
      bool &= d != 0.0D;
      paramDoubleMatrix2D.setQuick(i, i, 1.0D / d);
    }
    return bool;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.Diagonal
 * JD-Core Version:    0.7.0.1
 */