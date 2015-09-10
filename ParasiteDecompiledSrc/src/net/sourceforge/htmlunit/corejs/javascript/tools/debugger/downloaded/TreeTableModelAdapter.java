/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded;
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
/*  15:    */   JTree tree;
/*  16:    */   TreeTableModel treeTableModel;
/*  17:    */   
/*  18:    */   public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree)
/*  19:    */   {
/*  20: 44 */     this.tree = tree;
/*  21: 45 */     this.treeTableModel = treeTableModel;
/*  22:    */     
/*  23: 47 */     tree.addTreeExpansionListener(new TreeExpansionListener()
/*  24:    */     {
/*  25:    */       public void treeExpanded(TreeExpansionEvent event)
/*  26:    */       {
/*  27: 51 */         TreeTableModelAdapter.this.fireTableDataChanged();
/*  28:    */       }
/*  29:    */       
/*  30:    */       public void treeCollapsed(TreeExpansionEvent event)
/*  31:    */       {
/*  32: 54 */         TreeTableModelAdapter.this.fireTableDataChanged();
/*  33:    */       }
/*  34: 61 */     });
/*  35: 62 */     treeTableModel.addTreeModelListener(new TreeModelListener()
/*  36:    */     {
/*  37:    */       public void treeNodesChanged(TreeModelEvent e)
/*  38:    */       {
/*  39: 64 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  40:    */       }
/*  41:    */       
/*  42:    */       public void treeNodesInserted(TreeModelEvent e)
/*  43:    */       {
/*  44: 68 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  45:    */       }
/*  46:    */       
/*  47:    */       public void treeNodesRemoved(TreeModelEvent e)
/*  48:    */       {
/*  49: 72 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  50:    */       }
/*  51:    */       
/*  52:    */       public void treeStructureChanged(TreeModelEvent e)
/*  53:    */       {
/*  54: 76 */         TreeTableModelAdapter.this.delayedFireTableDataChanged();
/*  55:    */       }
/*  56:    */     });
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getColumnCount()
/*  60:    */   {
/*  61: 84 */     return this.treeTableModel.getColumnCount();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getColumnName(int column)
/*  65:    */   {
/*  66: 88 */     return this.treeTableModel.getColumnName(column);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Class getColumnClass(int column)
/*  70:    */   {
/*  71: 92 */     return this.treeTableModel.getColumnClass(column);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getRowCount()
/*  75:    */   {
/*  76: 96 */     return this.tree.getRowCount();
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Object nodeForRow(int row)
/*  80:    */   {
/*  81:100 */     TreePath treePath = this.tree.getPathForRow(row);
/*  82:101 */     return treePath.getLastPathComponent();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object getValueAt(int row, int column)
/*  86:    */   {
/*  87:105 */     return this.treeTableModel.getValueAt(nodeForRow(row), column);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isCellEditable(int row, int column)
/*  91:    */   {
/*  92:109 */     return this.treeTableModel.isCellEditable(nodeForRow(row), column);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setValueAt(Object value, int row, int column)
/*  96:    */   {
/*  97:113 */     this.treeTableModel.setValueAt(value, nodeForRow(row), column);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void delayedFireTableDataChanged()
/* 101:    */   {
/* 102:121 */     SwingUtilities.invokeLater(new Runnable()
/* 103:    */     {
/* 104:    */       public void run()
/* 105:    */       {
/* 106:123 */         TreeTableModelAdapter.this.fireTableDataChanged();
/* 107:    */       }
/* 108:    */     });
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded.TreeTableModelAdapter
 * JD-Core Version:    0.7.0.1
 */