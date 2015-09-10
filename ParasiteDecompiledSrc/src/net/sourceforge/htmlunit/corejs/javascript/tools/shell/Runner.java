/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*    2:     */ 
/*    3:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*    4:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*    5:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*    9:     */ 
/*   10:     */ class Runner
/*   11:     */   implements Runnable, ContextAction
/*   12:     */ {
/*   13:     */   ContextFactory factory;
/*   14:     */   private Scriptable scope;
/*   15:     */   private Function f;
/*   16:     */   private Script s;
/*   17:     */   private Object[] args;
/*   18:     */   
/*   19:     */   Runner(Scriptable scope, Function func, Object[] args)
/*   20:     */   {
/*   21:1192 */     this.scope = scope;
/*   22:1193 */     this.f = func;
/*   23:1194 */     this.args = args;
/*   24:     */   }
/*   25:     */   
/*   26:     */   Runner(Scriptable scope, Script script)
/*   27:     */   {
/*   28:1198 */     this.scope = scope;
/*   29:1199 */     this.s = script;
/*   30:     */   }
/*   31:     */   
/*   32:     */   public void run()
/*   33:     */   {
/*   34:1204 */     this.factory.call(this);
/*   35:     */   }
/*   36:     */   
/*   37:     */   public Object run(Context cx)
/*   38:     */   {
/*   39:1209 */     if (this.f != null) {
/*   40:1210 */       return this.f.call(cx, this.scope, this.scope, this.args);
/*   41:     */     }
/*   42:1212 */     return this.s.exec(cx, this.scope);
/*   43:     */   }
/*   44:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.Runner
 * JD-Core Version:    0.7.0.1
 */