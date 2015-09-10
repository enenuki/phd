/*  1:   */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*  2:   */ 
/*  3:   */ import java.awt.Component;
/*  4:   */ import javax.swing.JCheckBox;
/*  5:   */ import javax.swing.JTree;
/*  6:   */ 
/*  7:   */ public class CategoryNodeEditorRenderer
/*  8:   */   extends CategoryNodeRenderer
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6094804684259929574L;
/* 11:   */   
/* 12:   */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
/* 13:   */   {
/* 14:61 */     Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
/* 15:   */     
/* 16:   */ 
/* 17:   */ 
/* 18:65 */     return c;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public JCheckBox getCheckBox()
/* 22:   */   {
/* 23:69 */     return this._checkBox;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditorRenderer
 * JD-Core Version:    0.7.0.1
 */