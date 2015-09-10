/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Dimension;
/*    4:     */ import java.awt.Rectangle;
/*    5:     */ import java.awt.event.MouseEvent;
/*    6:     */ import java.util.EventObject;
/*    7:     */ import javax.swing.JTree;
/*    8:     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable.ListToTreeSelectionModelWrapper;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable.TreeTableCellEditor;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable.TreeTableCellRenderer;
/*   13:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.TreeTableModel;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.TreeTableModelAdapter;
/*   15:     */ 
/*   16:     */ class MyTreeTable
/*   17:     */   extends JTreeTable
/*   18:     */ {
/*   19:     */   private static final long serialVersionUID = 3457265548184453049L;
/*   20:     */   
/*   21:     */   public MyTreeTable(VariableModel model)
/*   22:     */   {
/*   23:2756 */     super(model);
/*   24:     */   }
/*   25:     */   
/*   26:     */   public JTree resetTree(TreeTableModel treeTableModel)
/*   27:     */   {
/*   28:2763 */     this.tree = new JTreeTable.TreeTableCellRenderer(this, treeTableModel);
/*   29:     */     
/*   30:     */ 
/*   31:2766 */     super.setModel(new TreeTableModelAdapter(treeTableModel, this.tree));
/*   32:     */     
/*   33:     */ 
/*   34:2769 */     JTreeTable.ListToTreeSelectionModelWrapper selectionWrapper = new JTreeTable.ListToTreeSelectionModelWrapper(this);
/*   35:     */     
/*   36:2771 */     this.tree.setSelectionModel(selectionWrapper);
/*   37:2772 */     setSelectionModel(selectionWrapper.getListSelectionModel());
/*   38:2775 */     if (this.tree.getRowHeight() < 1) {
/*   39:2777 */       setRowHeight(18);
/*   40:     */     }
/*   41:2781 */     setDefaultRenderer(TreeTableModel.class, this.tree);
/*   42:2782 */     setDefaultEditor(TreeTableModel.class, new JTreeTable.TreeTableCellEditor(this));
/*   43:2783 */     setShowGrid(true);
/*   44:2784 */     setIntercellSpacing(new Dimension(1, 1));
/*   45:2785 */     this.tree.setRootVisible(false);
/*   46:2786 */     this.tree.setShowsRootHandles(true);
/*   47:2787 */     DefaultTreeCellRenderer r = (DefaultTreeCellRenderer)this.tree.getCellRenderer();
/*   48:2788 */     r.setOpenIcon(null);
/*   49:2789 */     r.setClosedIcon(null);
/*   50:2790 */     r.setLeafIcon(null);
/*   51:2791 */     return this.tree;
/*   52:     */   }
/*   53:     */   
/*   54:     */   public boolean isCellEditable(EventObject e)
/*   55:     */   {
/*   56:2799 */     if ((e instanceof MouseEvent))
/*   57:     */     {
/*   58:2800 */       MouseEvent me = (MouseEvent)e;
/*   59:2807 */       if ((me.getModifiers() == 0) || (((me.getModifiers() & 0x410) != 0) && ((me.getModifiers() & 0x1ACF) == 0)))
/*   60:     */       {
/*   61:2821 */         int row = rowAtPoint(me.getPoint());
/*   62:2822 */         for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
/*   63:2824 */           if (TreeTableModel.class == getColumnClass(counter))
/*   64:     */           {
/*   65:2825 */             MouseEvent newME = new MouseEvent(this.tree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() - getCellRect(row, counter, true).x, me.getY(), me.getClickCount(), me.isPopupTrigger());
/*   66:     */             
/*   67:     */ 
/*   68:     */ 
/*   69:     */ 
/*   70:     */ 
/*   71:2831 */             this.tree.dispatchEvent(newME);
/*   72:2832 */             break;
/*   73:     */           }
/*   74:     */         }
/*   75:     */       }
/*   76:2836 */       if (me.getClickCount() >= 3) {
/*   77:2837 */         return true;
/*   78:     */       }
/*   79:2839 */       return false;
/*   80:     */     }
/*   81:2841 */     if (e == null) {
/*   82:2842 */       return true;
/*   83:     */     }
/*   84:2844 */     return false;
/*   85:     */   }
/*   86:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.MyTreeTable
 * JD-Core Version:    0.7.0.1
 */