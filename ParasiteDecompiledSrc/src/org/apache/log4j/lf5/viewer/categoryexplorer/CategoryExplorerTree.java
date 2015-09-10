/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.event.MouseEvent;
/*   4:    */ import javax.swing.JComponent;
/*   5:    */ import javax.swing.JTree;
/*   6:    */ import javax.swing.event.TreeModelEvent;
/*   7:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*   8:    */ import javax.swing.tree.DefaultTreeModel;
/*   9:    */ import javax.swing.tree.TreePath;
/*  10:    */ 
/*  11:    */ public class CategoryExplorerTree
/*  12:    */   extends JTree
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 8066257446951323576L;
/*  15:    */   protected CategoryExplorerModel _model;
/*  16: 46 */   protected boolean _rootAlreadyExpanded = false;
/*  17:    */   
/*  18:    */   public CategoryExplorerTree(CategoryExplorerModel model)
/*  19:    */   {
/*  20: 60 */     super(model);
/*  21:    */     
/*  22: 62 */     this._model = model;
/*  23: 63 */     init();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CategoryExplorerTree()
/*  27:    */   {
/*  28: 72 */     CategoryNode rootNode = new CategoryNode("Categories");
/*  29:    */     
/*  30: 74 */     this._model = new CategoryExplorerModel(rootNode);
/*  31:    */     
/*  32: 76 */     setModel(this._model);
/*  33:    */     
/*  34: 78 */     init();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CategoryExplorerModel getExplorerModel()
/*  38:    */   {
/*  39: 86 */     return this._model;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getToolTipText(MouseEvent e)
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 92 */       return super.getToolTipText(e);
/*  47:    */     }
/*  48:    */     catch (Exception ex) {}
/*  49: 94 */     return "";
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void init()
/*  53:    */   {
/*  54:105 */     putClientProperty("JTree.lineStyle", "Angled");
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:109 */     CategoryNodeRenderer renderer = new CategoryNodeRenderer();
/*  59:110 */     setEditable(true);
/*  60:111 */     setCellRenderer(renderer);
/*  61:    */     
/*  62:113 */     CategoryNodeEditor editor = new CategoryNodeEditor(this._model);
/*  63:    */     
/*  64:115 */     setCellEditor(new CategoryImmediateEditor(this, new CategoryNodeRenderer(), editor));
/*  65:    */     
/*  66:    */ 
/*  67:118 */     setShowsRootHandles(true);
/*  68:    */     
/*  69:120 */     setToolTipText("");
/*  70:    */     
/*  71:122 */     ensureRootExpansion();
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void expandRootNode()
/*  75:    */   {
/*  76:127 */     if (this._rootAlreadyExpanded) {
/*  77:128 */       return;
/*  78:    */     }
/*  79:130 */     this._rootAlreadyExpanded = true;
/*  80:131 */     TreePath path = new TreePath(this._model.getRootCategoryNode().getPath());
/*  81:132 */     expandPath(path);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void ensureRootExpansion()
/*  85:    */   {
/*  86:136 */     this._model.addTreeModelListener(new TreeModelAdapter()
/*  87:    */     {
/*  88:    */       public void treeNodesInserted(TreeModelEvent e)
/*  89:    */       {
/*  90:138 */         CategoryExplorerTree.this.expandRootNode();
/*  91:    */       }
/*  92:    */     });
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree
 * JD-Core Version:    0.7.0.1
 */