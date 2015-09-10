/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable;
/*   2:    */ 
/*   3:    */ import javax.swing.JTree;
/*   4:    */ import javax.swing.SwingUtilities;
/*   5:    */ import javax.swing.event.TreeExpansionEvent;
/*   6:    */ import javax.swing.event.TreeExpansionListener;
/*   7:    */ import javax.swing.event.TreeModelEvent;
/*   8:    */ import javax.swing.event.TreeModelListener;
/*   9:    */ import javax.swing.table.AbstractTableModel;
/*  10:    */ import javax.swing.tree.TreePath;
/*  11:    */ 
/*  12:    */ public class TreeTableModelAdapter
/*  13:    */   extends AbstractTableModel
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = 48741114609209052L;
/*  16:    */   JTree tree;
/*  17:    */   TreeTableModel treeTableModel;
/*  18:    */   
/*  19:    */   public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree)
/*  20:    */   {
/*  21: 62 */     this.tree = tree;
/*  22: 63 */     this.treeTableModel = treeTableModel;
/*  23:    */     
/*  24: 65 */     tree.addTreeExpansionListener(new TreeExpansionListener()
/*  25:    */     {
/*  26:    */       public void treeExpanded(TreeExpansionEvent event)
/*  27:    */       {
/*  28: 69 */         TreeTableModelAdapter.this.fireTableDataChanged();
/*  29:    */       }
/*  30:    */       
/*  31:    */       public void treeCollapsed(TreeExpansionEvent event)
/*  32:    */       {
/*  33: 72 */         TreeTableModelAdapter.this.fireTableDataChanged();
/*  34:    */       }
/*  35: 79 */     });
/*  36: 80 */     treeTableModel.addTreeModelListener(new TreeModelListener()
/*  37:    */     {
/*  38:    */       public void treeNodesChanged(TreeModelEvent e)
/*  39:    */       {
/*  40: 82 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  41:    */       }
/*  42:    */       
/*  43:    */       public void treeNodesInserted(TreeModelEvent e)
/*  44:    */       {
/*  45: 86 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  46:    */       }
/*  47:    */       
/*  48:    */       public void treeNodesRemoved(TreeModelEvent e)
/*  49:    */       {
/*  50: 90 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  51:    */       }
/*  52:    */       
/*  53:    */       public void treeStructureChanged(TreeModelEvent e)
/*  54:    */       {
/*  55: 94 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  56:    */       }
/*  57:    */     });
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getColumnCount()
/*  61:    */   {
/*  62:102 */     return this.treeTableModel.getColumnCount();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getColumnName(int column)
/*  66:    */   {
/*  67:107 */     return this.treeTableModel.getColumnName(column);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Class<?> getColumnClass(int column)
/*  71:    */   {
/*  72:112 */     return this.treeTableModel.getColumnClass(column);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getRowCount()
/*  76:    */   {
/*  77:116 */     return this.tree.getRowCount();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected Object nodeForRow(int row)
/*  81:    */   {
/*  82:120 */     TreePath treePath = this.tree.getPathForRow(row);
/*  83:121 */     return treePath.getLastPathComponent();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object getValueAt(int row, int column)
/*  87:    */   {
/*  88:125 */     return this.treeTableModel.getValueAt(nodeForRow(row), column);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isCellEditable(int row, int column)
/*  92:    */   {
/*  93:130 */     return this.treeTableModel.isCellEditable(nodeForRow(row), column);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setValueAt(Object value, int row, int column)
/*  97:    */   {
/*  98:135 */     this.treeTableModel.setValueAt(value, nodeForRow(row), column);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void delayedFireTableDataChanged()
/* 102:    */   {
/* 103:143 */     SwingUtilities.invokeLater(new Runnable()
/* 104:    */     {
/* 105:    */       public void run()
/* 106:    */       {
/* 107:145 */         TreeTableModelAdapter.this.fireTableDataChanged();
/* 108:    */       }
/* 109:    */     });
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.TreeTableModelAdapter
 * JD-Core Version:    0.7.0.1
 */