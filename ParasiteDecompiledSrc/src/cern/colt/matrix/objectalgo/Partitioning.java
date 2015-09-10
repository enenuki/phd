package cern.colt.matrix.objectalgo;

import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

public class Partitioning
{
  public static void partition(ObjectMatrix2D paramObjectMatrix2D, int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, Object[] paramArrayOfObject, int paramInt4, int paramInt5, int[] paramArrayOfInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 >= paramObjectMatrix2D.rows()) || (paramInt2 >= paramArrayOfInt1.length)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt3 < 0) || (paramInt3 >= paramObjectMatrix2D.columns())) {
      throw new IllegalArgumentException();
    }
    if ((paramInt4 < 0) || (paramInt5 >= paramArrayOfObject.length)) {
      throw new IllegalArgumentException();
    }
    if (paramArrayOfInt2.length < paramArrayOfObject.length) {
      throw new IllegalArgumentException();
    }
    int[] arrayOfInt = paramArrayOfInt1;
    Swapper local1 = new Swapper()
    {
      private final int[] val$g;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$g[paramAnonymousInt1];
        this.val$g[paramAnonymousInt1] = this.val$g[paramAnonymousInt2];
        this.val$g[paramAnonymousInt2] = i;
      }
    };
    ObjectMatrix1D localObjectMatrix1D = paramObjectMatrix2D.viewColumn(paramInt3);
    IntComparator local2 = new IntComparator()
    {
      private final Object[] val$splitters;
      private final ObjectMatrix1D val$columnView;
      private final int[] val$g;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$splitters[paramAnonymousInt1];
        Comparable localComparable2 = (Comparable)this.val$columnView.getQuick(this.val$g[paramAnonymousInt2]);
        int i = localComparable1.compareTo(localComparable2);
        return i == 0 ? 0 : i < 0 ? -1 : 1;
      }
    };
    IntComparator local3 = new IntComparator()
    {
      private final ObjectMatrix1D val$columnView;
      private final int[] val$g;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$columnView.getQuick(this.val$g[paramAnonymousInt1]);
        Comparable localComparable2 = (Comparable)this.val$columnView.getQuick(this.val$g[paramAnonymousInt2]);
        int i = localComparable1.compareTo(localComparable2);
        return i == 0 ? 0 : i < 0 ? -1 : 1;
      }
    };
    IntComparator local4 = new IntComparator()
    {
      private final Object[] val$splitters;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$splitters[paramAnonymousInt1];
        Comparable localComparable2 = (Comparable)this.val$splitters[paramAnonymousInt2];
        int i = localComparable1.compareTo(localComparable2);
        return i == 0 ? 0 : i < 0 ? -1 : 1;
      }
    };
    cern.colt.Partitioning.genericPartition(paramInt1, paramInt2, paramInt4, paramInt5, paramArrayOfInt2, local2, local3, local4, local1);
  }
  
  public static ObjectMatrix2D partition(ObjectMatrix2D paramObjectMatrix2D, int paramInt, Object[] paramArrayOfObject, int[] paramArrayOfInt)
  {
    int i = 0;
    int j = paramObjectMatrix2D.rows() - 1;
    int k = 0;
    int m = paramArrayOfObject.length - 1;
    int[] arrayOfInt1 = new int[paramObjectMatrix2D.rows()];
    int n = arrayOfInt1.length;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      arrayOfInt1[n] = n;
    }
    partition(paramObjectMatrix2D, arrayOfInt1, i, j, paramInt, paramArrayOfObject, k, m, paramArrayOfInt);
    int[] arrayOfInt2 = new int[paramObjectMatrix2D.columns()];
    int i1 = arrayOfInt2.length;
    for (;;)
    {
      i1--;
      if (i1 < 0) {
        break;
      }
      arrayOfInt2[i1] = i1;
    }
    return paramObjectMatrix2D.viewSelection(arrayOfInt1, arrayOfInt2);
  }
  
  private static void xPartitionOld(ObjectMatrix2D paramObjectMatrix2D, ObjectMatrix1D paramObjectMatrix1D, int paramInt1, int paramInt2, Object[] paramArrayOfObject, int paramInt3, int paramInt4, int[] paramArrayOfInt) {}
  
  private static int xPartitionOld(ObjectMatrix2D paramObjectMatrix2D, ObjectMatrix1D paramObjectMatrix1D, int paramInt1, int paramInt2, Object paramObject)
  {
    return 0;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.objectalgo.Partitioning
 * JD-Core Version:    0.7.0.1
 */