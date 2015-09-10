/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.event.ActionEvent;
/*    4:     */ import java.awt.event.ActionListener;
/*    5:     */ import javax.swing.JInternalFrame;
/*    6:     */ import javax.swing.JScrollPane;
/*    7:     */ 
/*    8:     */ class EvalWindow
/*    9:     */   extends JInternalFrame
/*   10:     */   implements ActionListener
/*   11:     */ {
/*   12:     */   private static final long serialVersionUID = -2860585845212160176L;
/*   13:     */   private EvalTextArea evalTextArea;
/*   14:     */   
/*   15:     */   public EvalWindow(String name, SwingGui debugGui)
/*   16:     */   {
/*   17:1265 */     super(name, true, false, true, true);
/*   18:1266 */     this.evalTextArea = new EvalTextArea(debugGui);
/*   19:1267 */     this.evalTextArea.setRows(24);
/*   20:1268 */     this.evalTextArea.setColumns(80);
/*   21:1269 */     JScrollPane scroller = new JScrollPane(this.evalTextArea);
/*   22:1270 */     setContentPane(scroller);
/*   23:     */     
/*   24:1272 */     pack();
/*   25:1273 */     setVisible(true);
/*   26:     */   }
/*   27:     */   
/*   28:     */   public void setEnabled(boolean b)
/*   29:     */   {
/*   30:1281 */     super.setEnabled(b);
/*   31:1282 */     this.evalTextArea.setEnabled(b);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public void actionPerformed(ActionEvent e)
/*   35:     */   {
/*   36:1291 */     String cmd = e.getActionCommand();
/*   37:1292 */     if (cmd.equals("Cut")) {
/*   38:1293 */       this.evalTextArea.cut();
/*   39:1294 */     } else if (cmd.equals("Copy")) {
/*   40:1295 */       this.evalTextArea.copy();
/*   41:1296 */     } else if (cmd.equals("Paste")) {
/*   42:1297 */       this.evalTextArea.paste();
/*   43:     */     }
/*   44:     */   }
/*   45:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.EvalWindow
 * JD-Core Version:    0.7.0.1
 */