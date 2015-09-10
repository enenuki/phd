/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import javax.swing.JTable;
/*    4:     */ 
/*    5:     */ class Evaluator
/*    6:     */   extends JTable
/*    7:     */ {
/*    8:     */   private static final long serialVersionUID = 8133672432982594256L;
/*    9:     */   MyTableModel tableModel;
/*   10:     */   
/*   11:     */   public Evaluator(SwingGui debugGui)
/*   12:     */   {
/*   13:2459 */     super(new MyTableModel(debugGui));
/*   14:2460 */     this.tableModel = ((MyTableModel)getModel());
/*   15:     */   }
/*   16:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Evaluator
 * JD-Core Version:    0.7.0.1
 */