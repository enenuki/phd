package org.apache.bcel.generic;

public abstract interface InstructionTargeter
{
  public abstract boolean containsTarget(InstructionHandle paramInstructionHandle);
  
  public abstract void updateTarget(InstructionHandle paramInstructionHandle1, InstructionHandle paramInstructionHandle2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.InstructionTargeter
 * JD-Core Version:    0.7.0.1
 */