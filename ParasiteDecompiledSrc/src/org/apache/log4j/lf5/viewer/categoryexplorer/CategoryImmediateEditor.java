/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Rectangle;
/*   5:    */ import java.awt.event.MouseEvent;
/*   6:    */ import java.util.EventObject;
/*   7:    */ import javax.swing.Icon;
/*   8:    */ import javax.swing.JLabel;
/*   9:    */ import javax.swing.JTree;
/*  10:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*  11:    */ import javax.swing.tree.DefaultTreeCellEditor;
/*  12:    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*  13:    */ import javax.swing.tree.TreePath;
/*  14:    */ 
/*  15:    */ public class CategoryImmediateEditor
/*  16:    */   extends DefaultTreeCellEditor
/*  17:    */ {
/*  18:    */   private CategoryNodeRenderer renderer;
/*  19: 47 */   protected Icon editingIcon = null;
/*  20:    */   
/*  21:    */   public CategoryImmediateEditor(JTree tree, CategoryNodeRenderer renderer, CategoryNodeEditor editor)
/*  22:    */   {
/*  23: 59 */     super(tree, renderer, editor);
/*  24: 60 */     this.renderer = renderer;
/*  25: 61 */     renderer.setIcon(null);
/*  26: 62 */     renderer.setLeafIcon(null);
/*  27: 63 */     renderer.setOpenIcon(null);
/*  28: 64 */     renderer.setClosedIcon(null);
/*  29:    */     
/*  30: 66 */     this.editingIcon = null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean shouldSelectCell(EventObject e)
/*  34:    */   {
/*  35: 73 */     boolean rv = false;
/*  36: 75 */     if ((e instanceof MouseEvent))
/*  37:    */     {
/*  38: 76 */       MouseEvent me = (MouseEvent)e;
/*  39: 77 */       TreePath path = this.tree.getPathForLocation(me.getX(), me.getY());
/*  40:    */       
/*  41: 79 */       CategoryNode node = (CategoryNode)path.getLastPathComponent();
/*  42:    */       
/*  43:    */ 
/*  44: 82 */       rv = node.isLeaf();
/*  45:    */     }
/*  46: 84 */     return rv;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean inCheckBoxHitRegion(MouseEvent e)
/*  50:    */   {
/*  51: 88 */     TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());
/*  52: 90 */     if (path == null) {
/*  53: 91 */       return false;
/*  54:    */     }
/*  55: 93 */     CategoryNode node = (CategoryNode)path.getLastPathComponent();
/*  56: 94 */     boolean rv = false;
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:100 */     Rectangle bounds = this.tree.getRowBounds(this.lastRow);
/*  63:101 */     Dimension checkBoxOffset = this.renderer.getCheckBoxOffset();
/*  64:    */     
/*  65:    */ 
/*  66:104 */     bounds.translate(this.offset + checkBoxOffset.width, checkBoxOffset.height);
/*  67:    */     
/*  68:    */ 
/*  69:107 */     rv = bounds.contains(e.getPoint());
/*  70:    */     
/*  71:109 */     return true;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected boolean canEditImmediately(EventObject e)
/*  75:    */   {
/*  76:117 */     boolean rv = false;
/*  77:119 */     if ((e instanceof MouseEvent))
/*  78:    */     {
/*  79:120 */       MouseEvent me = (MouseEvent)e;
/*  80:121 */       rv = inCheckBoxHitRegion(me);
/*  81:    */     }
/*  82:124 */     return rv;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void determineOffset(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
/*  86:    */   {
/*  87:131 */     this.offset = 0;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryImmediateEditor
 * JD-Core Version:    0.7.0.1
 */