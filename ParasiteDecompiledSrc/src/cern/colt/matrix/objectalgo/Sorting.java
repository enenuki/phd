package cern.colt.matrix.objectalgo;

import cern.colt.GenericSorting;
import cern.colt.PersistentObject;
import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.ObjectMatrix3D;
import java.util.Comparator;

public class Sorting
  extends PersistentObject
{
  public static final Sorting quickSort = new Sorting();
  public static final Sorting mergeSort = new Sorting()
  {
    protected void runSort(int[] paramAnonymousArrayOfInt, int paramAnonymousInt1, int paramAnonymousInt2, IntComparator paramAnonymousIntComparator)
    {
      cern.colt.Sorting.mergeSort(paramAnonymousArrayOfInt, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousIntComparator);
    }
    
    protected void runSort(int paramAnonymousInt1, int paramAnonymousInt2, IntComparator paramAnonymousIntComparator, Swapper paramAnonymousSwapper)
    {
      GenericSorting.mergeSort(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousIntComparator, paramAnonymousSwapper);
    }
  };
  
  protected void runSort(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    cern.colt.Sorting.quickSort(paramArrayOfInt, paramInt1, paramInt2, paramIntComparator);
  }
  
  protected void runSort(int paramInt1, int paramInt2, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    GenericSorting.quickSort(paramInt1, paramInt2, paramIntComparator, paramSwapper);
  }
  
  public ObjectMatrix1D sort(ObjectMatrix1D paramObjectMatrix1D)
  {
    int[] arrayOfInt = new int[paramObjectMatrix1D.size()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    IntComparator local2 = new IntComparator()
    {
      private final ObjectMatrix1D val$vector;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$vector.getQuick(paramAnonymousInt1);
        Comparable localComparable2 = (Comparable)this.val$vector.getQuick(paramAnonymousInt2);
        int i = localComparable1.compareTo(localComparable2);
        return i > 0 ? 1 : i < 0 ? -1 : 0;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local2);
    return paramObjectMatrix1D.viewSelection(arrayOfInt);
  }
  
  public ObjectMatrix1D sort(ObjectMatrix1D paramObjectMatrix1D, Comparator paramComparator)
  {
    int[] arrayOfInt = new int[paramObjectMatrix1D.size()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    IntComparator local3 = new IntComparator()
    {
      private final Comparator val$c;
      private final ObjectMatrix1D val$vector;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$vector.getQuick(paramAnonymousInt1), this.val$vector.getQuick(paramAnonymousInt2));
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local3);
    return paramObjectMatrix1D.viewSelection(arrayOfInt);
  }
  
  public ObjectMatrix2D sort(ObjectMatrix2D paramObjectMatrix2D, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= paramObjectMatrix2D.columns())) {
      throw new IndexOutOfBoundsException("column=" + paramInt + ", matrix=" + Formatter.shape(paramObjectMatrix2D));
    }
    int[] arrayOfInt = new int[paramObjectMatrix2D.rows()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    ObjectMatrix1D localObjectMatrix1D = paramObjectMatrix2D.viewColumn(paramInt);
    IntComparator local4 = new IntComparator()
    {
      private final ObjectMatrix1D val$col;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$col.getQuick(paramAnonymousInt1);
        Comparable localComparable2 = (Comparable)this.val$col.getQuick(paramAnonymousInt2);
        int i = localComparable1.compareTo(localComparable2);
        return i > 0 ? 1 : i < 0 ? -1 : 0;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local4);
    return paramObjectMatrix2D.viewSelection(arrayOfInt, null);
  }
  
  public ObjectMatrix2D sort(ObjectMatrix2D paramObjectMatrix2D, ObjectMatrix1DComparator paramObjectMatrix1DComparator)
  {
    int[] arrayOfInt = new int[paramObjectMatrix2D.rows()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    ObjectMatrix1D[] arrayOfObjectMatrix1D = new ObjectMatrix1D[paramObjectMatrix2D.rows()];
    int j = arrayOfObjectMatrix1D.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfObjectMatrix1D[j] = paramObjectMatrix2D.viewRow(j);
    }
    IntComparator local5 = new IntComparator()
    {
      private final ObjectMatrix1DComparator val$c;
      private final ObjectMatrix1D[] val$views;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$views[paramAnonymousInt1], this.val$views[paramAnonymousInt2]);
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local5);
    return paramObjectMatrix2D.viewSelection(arrayOfInt, null);
  }
  
  public ObjectMatrix3D sort(ObjectMatrix3D paramObjectMatrix3D, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= paramObjectMatrix3D.rows())) {
      throw new IndexOutOfBoundsException("row=" + paramInt1 + ", matrix=" + Formatter.shape(paramObjectMatrix3D));
    }
    if ((paramInt2 < 0) || (paramInt2 >= paramObjectMatrix3D.columns())) {
      throw new IndexOutOfBoundsException("column=" + paramInt2 + ", matrix=" + Formatter.shape(paramObjectMatrix3D));
    }
    int[] arrayOfInt = new int[paramObjectMatrix3D.slices()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    ObjectMatrix1D localObjectMatrix1D = paramObjectMatrix3D.viewRow(paramInt1).viewColumn(paramInt2);
    IntComparator local6 = new IntComparator()
    {
      private final ObjectMatrix1D val$sliceView;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Comparable localComparable1 = (Comparable)this.val$sliceView.getQuick(paramAnonymousInt1);
        Comparable localComparable2 = (Comparable)this.val$sliceView.getQuick(paramAnonymousInt2);
        int i = localComparable1.compareTo(localComparable2);
        return i > 0 ? 1 : i < 0 ? -1 : 0;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local6);
    return paramObjectMatrix3D.viewSelection(arrayOfInt, null, null);
  }
  
  public ObjectMatrix3D sort(ObjectMatrix3D paramObjectMatrix3D, ObjectMatrix2DComparator paramObjectMatrix2DComparator)
  {
    int[] arrayOfInt = new int[paramObjectMatrix3D.slices()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    ObjectMatrix2D[] arrayOfObjectMatrix2D = new ObjectMatrix2D[paramObjectMatrix3D.slices()];
    int j = arrayOfObjectMatrix2D.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfObjectMatrix2D[j] = paramObjectMatrix3D.viewSlice(j);
    }
    IntComparator local7 = new IntComparator()
    {
      private final ObjectMatrix2DComparator val$c;
      private final ObjectMatrix2D[] val$views;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$views[paramAnonymousInt1], this.val$views[paramAnonymousInt2]);
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local7);
    return paramObjectMatrix3D.viewSelection(arrayOfInt, null, null);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.objectalgo.Sorting
 * JD-Core Version:    0.7.0.1
 */