/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import javax.swing.JComponent;
/*    4:     */ import javax.swing.JMenuItem;
/*    5:     */ import javax.swing.JPopupMenu;
/*    6:     */ 
/*    7:     */ class FilePopupMenu
/*    8:     */   extends JPopupMenu
/*    9:     */ {
/*   10:     */   private static final long serialVersionUID = 3589525009546013565L;
/*   11:     */   int x;
/*   12:     */   int y;
/*   13:     */   
/*   14:     */   public FilePopupMenu(FileTextArea w)
/*   15:     */   {
/*   16:     */     JMenuItem item;
/*   17:1403 */     add(item = new JMenuItem("Set Breakpoint"));
/*   18:1404 */     item.addActionListener(w);
/*   19:1405 */     add(item = new JMenuItem("Clear Breakpoint"));
/*   20:1406 */     item.addActionListener(w);
/*   21:1407 */     add(item = new JMenuItem("Run"));
/*   22:1408 */     item.addActionListener(w);
/*   23:     */   }
/*   24:     */   
/*   25:     */   public void show(JComponent comp, int x, int y)
/*   26:     */   {
/*   27:1415 */     this.x = x;
/*   28:1416 */     this.y = y;
/*   29:1417 */     super.show(comp, x, y);
/*   30:     */   }
/*   31:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.FilePopupMenu
 * JD-Core Version:    0.7.0.1
 */