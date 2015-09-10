/*  1:   */ package antlr.debug.misc;
/*  2:   */ 
/*  3:   */ import java.awt.BorderLayout;
/*  4:   */ import javax.swing.JPanel;
/*  5:   */ import javax.swing.JScrollPane;
/*  6:   */ import javax.swing.JTree;
/*  7:   */ import javax.swing.JViewport;
/*  8:   */ import javax.swing.event.TreeSelectionListener;
/*  9:   */ import javax.swing.tree.TreeModel;
/* 10:   */ 
/* 11:   */ public class JTreeASTPanel
/* 12:   */   extends JPanel
/* 13:   */ {
/* 14:   */   JTree tree;
/* 15:   */   
/* 16:   */   public JTreeASTPanel(TreeModel paramTreeModel, TreeSelectionListener paramTreeSelectionListener)
/* 17:   */   {
/* 18:20 */     setLayout(new BorderLayout());
/* 19:   */     
/* 20:   */ 
/* 21:23 */     this.tree = new JTree(paramTreeModel);
/* 22:   */     
/* 23:   */ 
/* 24:26 */     this.tree.putClientProperty("JTree.lineStyle", "Angled");
/* 25:29 */     if (paramTreeSelectionListener != null) {
/* 26:30 */       this.tree.addTreeSelectionListener(paramTreeSelectionListener);
/* 27:   */     }
/* 28:33 */     JScrollPane localJScrollPane = new JScrollPane();
/* 29:34 */     localJScrollPane.getViewport().add(this.tree);
/* 30:   */     
/* 31:36 */     add(localJScrollPane, "Center");
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.misc.JTreeASTPanel
 * JD-Core Version:    0.7.0.1
 */