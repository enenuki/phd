/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ class RunProxy
/*    4:     */   implements Runnable
/*    5:     */ {
/*    6:     */   static final int OPEN_FILE = 1;
/*    7:     */   static final int LOAD_FILE = 2;
/*    8:     */   static final int UPDATE_SOURCE_TEXT = 3;
/*    9:     */   static final int ENTER_INTERRUPT = 4;
/*   10:     */   private SwingGui debugGui;
/*   11:     */   private int type;
/*   12:     */   String fileName;
/*   13:     */   String text;
/*   14:     */   Dim.SourceInfo sourceInfo;
/*   15:     */   Dim.StackFrame lastFrame;
/*   16:     */   String threadTitle;
/*   17:     */   String alertMessage;
/*   18:     */   
/*   19:     */   public RunProxy(SwingGui debugGui, int type)
/*   20:     */   {
/*   21:3538 */     this.debugGui = debugGui;
/*   22:3539 */     this.type = type;
/*   23:     */   }
/*   24:     */   
/*   25:     */   public void run()
/*   26:     */   {
/*   27:3546 */     switch (this.type)
/*   28:     */     {
/*   29:     */     case 1: 
/*   30:     */       try
/*   31:     */       {
/*   32:3549 */         this.debugGui.dim.compileScript(this.fileName, this.text);
/*   33:     */       }
/*   34:     */       catch (RuntimeException ex)
/*   35:     */       {
/*   36:3551 */         MessageDialogWrapper.showMessageDialog(this.debugGui, ex.getMessage(), "Error Compiling " + this.fileName, 0);
/*   37:     */       }
/*   38:     */     case 2: 
/*   39:     */       try
/*   40:     */       {
/*   41:3559 */         this.debugGui.dim.evalScript(this.fileName, this.text);
/*   42:     */       }
/*   43:     */       catch (RuntimeException ex)
/*   44:     */       {
/*   45:3561 */         MessageDialogWrapper.showMessageDialog(this.debugGui, ex.getMessage(), "Run error for " + this.fileName, 0);
/*   46:     */       }
/*   47:     */     case 3: 
/*   48:3569 */       String fileName = this.sourceInfo.url();
/*   49:3570 */       if ((!this.debugGui.updateFileWindow(this.sourceInfo)) && (!fileName.equals("<stdin>"))) {
/*   50:3572 */         this.debugGui.createFileWindow(this.sourceInfo, -1);
/*   51:     */       }
/*   52:3575 */       break;
/*   53:     */     case 4: 
/*   54:3578 */       this.debugGui.enterInterruptImpl(this.lastFrame, this.threadTitle, this.alertMessage);
/*   55:3579 */       break;
/*   56:     */     default: 
/*   57:3582 */       throw new IllegalArgumentException(String.valueOf(this.type));
/*   58:     */     }
/*   59:     */   }
/*   60:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.RunProxy
 * JD-Core Version:    0.7.0.1
 */