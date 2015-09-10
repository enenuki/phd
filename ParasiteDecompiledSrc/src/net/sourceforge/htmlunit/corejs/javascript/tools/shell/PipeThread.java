/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InputStream;
/*    5:     */ import java.io.OutputStream;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*    7:     */ 
/*    8:     */ class PipeThread
/*    9:     */   extends Thread
/*   10:     */ {
/*   11:     */   private boolean fromProcess;
/*   12:     */   private InputStream from;
/*   13:     */   private OutputStream to;
/*   14:     */   
/*   15:     */   PipeThread(boolean fromProcess, InputStream from, OutputStream to)
/*   16:     */   {
/*   17:1225 */     setDaemon(true);
/*   18:1226 */     this.fromProcess = fromProcess;
/*   19:1227 */     this.from = from;
/*   20:1228 */     this.to = to;
/*   21:     */   }
/*   22:     */   
/*   23:     */   public void run()
/*   24:     */   {
/*   25:     */     try
/*   26:     */     {
/*   27:1234 */       Global.pipe(this.fromProcess, this.from, this.to);
/*   28:     */     }
/*   29:     */     catch (IOException ex)
/*   30:     */     {
/*   31:1236 */       throw Context.throwAsScriptRuntimeEx(ex);
/*   32:     */     }
/*   33:     */   }
/*   34:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.PipeThread
 * JD-Core Version:    0.7.0.1
 */