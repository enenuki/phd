package net.sourceforge.htmlunit.corejs.javascript.tools.shell;

import net.sourceforge.htmlunit.corejs.javascript.Context;
import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
import net.sourceforge.htmlunit.corejs.javascript.SecurityController;

public abstract class SecurityProxy
  extends SecurityController
{
  protected abstract void callProcessFileSecure(Context paramContext, Scriptable paramScriptable, String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.SecurityProxy
 * JD-Core Version:    0.7.0.1
 */