/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.event.ActionEvent;
/*    4:     */ import java.awt.event.ActionListener;
/*    5:     */ import java.io.InputStream;
/*    6:     */ import java.io.PrintStream;
/*    7:     */ import javax.swing.JInternalFrame;
/*    8:     */ import javax.swing.JScrollPane;
/*    9:     */ import javax.swing.event.InternalFrameAdapter;
/*   10:     */ import javax.swing.event.InternalFrameEvent;
/*   11:     */ import javax.swing.text.Caret;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleTextArea;
/*   13:     */ 
/*   14:     */ class JSInternalConsole
/*   15:     */   extends JInternalFrame
/*   16:     */   implements ActionListener
/*   17:     */ {
/*   18:     */   private static final long serialVersionUID = -5523468828771087292L;
/*   19:     */   ConsoleTextArea consoleTextArea;
/*   20:     */   
/*   21:     */   public JSInternalConsole(String name)
/*   22:     */   {
/*   23:1316 */     super(name, true, false, true, true);
/*   24:1317 */     this.consoleTextArea = new ConsoleTextArea(null);
/*   25:1318 */     this.consoleTextArea.setRows(24);
/*   26:1319 */     this.consoleTextArea.setColumns(80);
/*   27:1320 */     JScrollPane scroller = new JScrollPane(this.consoleTextArea);
/*   28:1321 */     setContentPane(scroller);
/*   29:1322 */     pack();
/*   30:1323 */     addInternalFrameListener(new InternalFrameAdapter()
/*   31:     */     {
/*   32:     */       public void internalFrameActivated(InternalFrameEvent e)
/*   33:     */       {
/*   34:1327 */         if (JSInternalConsole.this.consoleTextArea.hasFocus())
/*   35:     */         {
/*   36:1328 */           JSInternalConsole.this.consoleTextArea.getCaret().setVisible(false);
/*   37:1329 */           JSInternalConsole.this.consoleTextArea.getCaret().setVisible(true);
/*   38:     */         }
/*   39:     */       }
/*   40:     */     });
/*   41:     */   }
/*   42:     */   
/*   43:     */   public InputStream getIn()
/*   44:     */   {
/*   45:1344 */     return this.consoleTextArea.getIn();
/*   46:     */   }
/*   47:     */   
/*   48:     */   public PrintStream getOut()
/*   49:     */   {
/*   50:1351 */     return this.consoleTextArea.getOut();
/*   51:     */   }
/*   52:     */   
/*   53:     */   public PrintStream getErr()
/*   54:     */   {
/*   55:1358 */     return this.consoleTextArea.getErr();
/*   56:     */   }
/*   57:     */   
/*   58:     */   public void actionPerformed(ActionEvent e)
/*   59:     */   {
/*   60:1367 */     String cmd = e.getActionCommand();
/*   61:1368 */     if (cmd.equals("Cut")) {
/*   62:1369 */       this.consoleTextArea.cut();
/*   63:1370 */     } else if (cmd.equals("Copy")) {
/*   64:1371 */       this.consoleTextArea.copy();
/*   65:1372 */     } else if (cmd.equals("Paste")) {
/*   66:1373 */       this.consoleTextArea.paste();
/*   67:     */     }
/*   68:     */   }
/*   69:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.JSInternalConsole
 * JD-Core Version:    0.7.0.1
 */