package cern.jet.random;

class Stack
{
  int N;
  int[] v;
  int i;
  
  public Stack(int paramInt)
  {
    this.N = paramInt;
    this.i = -1;
    this.v = new int[this.N];
  }
  
  public int pop()
  {
    if (this.i < 0) {
      throw new InternalError("Cannot pop stack!");
    }
    this.i -= 1;
    return this.v[(this.i + 1)];
  }
  
  public void push(int paramInt)
  {
    this.i += 1;
    if (this.i >= this.N) {
      throw new InternalError("Cannot push stack!");
    }
    this.v[this.i] = paramInt;
  }
  
  public int size()
  {
    return this.i + 1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Stack
 * JD-Core Version:    0.7.0.1
 */