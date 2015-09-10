package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;

public abstract interface GuiCallback
{
  public abstract void updateSourceText(Dim.SourceInfo paramSourceInfo);
  
  public abstract void enterInterrupt(Dim.StackFrame paramStackFrame, String paramString1, String paramString2);
  
  public abstract boolean isGuiEventThread();
  
  public abstract void dispatchNextGuiEvent()
    throws InterruptedException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.GuiCallback
 * JD-Core Version:    0.7.0.1
 */