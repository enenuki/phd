package net.sourceforge.htmlunit.corejs.javascript.commonjs.module;

import java.net.URI;
import net.sourceforge.htmlunit.corejs.javascript.Context;
import net.sourceforge.htmlunit.corejs.javascript.Scriptable;

public abstract interface ModuleScriptProvider
{
  public abstract ModuleScript getModuleScript(Context paramContext, String paramString, URI paramURI, Scriptable paramScriptable)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScriptProvider
 * JD-Core Version:    0.7.0.1
 */