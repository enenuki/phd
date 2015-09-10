package cern.jet.random.engine;

import cern.colt.PersistentObject;
import java.io.PrintStream;

public class RandomSeedGenerator
  extends PersistentObject
{
  protected int row;
  protected int column;
  
  public RandomSeedGenerator()
  {
    this(0, 0);
  }
  
  public RandomSeedGenerator(int paramInt1, int paramInt2)
  {
    this.row = paramInt1;
    this.column = paramInt2;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    int k = Integer.parseInt(paramArrayOfString[2]);
    new RandomSeedGenerator(i, j).print(k);
  }
  
  public int nextSeed()
  {
    return RandomSeedTable.getSeedAtRowColumn(this.row++, this.column);
  }
  
  public void print(int paramInt)
  {
    System.out.println("Generating " + paramInt + " random seeds...");
    RandomSeedGenerator localRandomSeedGenerator = (RandomSeedGenerator)clone();
    for (int i = 0; i < paramInt; i++)
    {
      int j = localRandomSeedGenerator.nextSeed();
      System.out.println(j);
    }
    System.out.println("\ndone.");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.RandomSeedGenerator
 * JD-Core Version:    0.7.0.1
 */