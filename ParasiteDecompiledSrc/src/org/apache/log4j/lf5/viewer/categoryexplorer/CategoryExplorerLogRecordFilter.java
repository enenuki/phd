/*  1:   */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import javax.swing.tree.DefaultMutableTreeNode;
/*  5:   */ import javax.swing.tree.DefaultTreeModel;
/*  6:   */ import org.apache.log4j.lf5.LogRecord;
/*  7:   */ import org.apache.log4j.lf5.LogRecordFilter;
/*  8:   */ 
/*  9:   */ public class CategoryExplorerLogRecordFilter
/* 10:   */   implements LogRecordFilter
/* 11:   */ {
/* 12:   */   protected CategoryExplorerModel _model;
/* 13:   */   
/* 14:   */   public CategoryExplorerLogRecordFilter(CategoryExplorerModel model)
/* 15:   */   {
/* 16:52 */     this._model = model;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean passes(LogRecord record)
/* 20:   */   {
/* 21:66 */     CategoryPath path = new CategoryPath(record.getCategory());
/* 22:67 */     return this._model.isCategoryPathActive(path);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void reset()
/* 26:   */   {
/* 27:74 */     resetAllNodes();
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void resetAllNodes()
/* 31:   */   {
/* 32:82 */     Enumeration nodes = this._model.getRootCategoryNode().depthFirstEnumeration();
/* 33:84 */     while (nodes.hasMoreElements())
/* 34:   */     {
/* 35:85 */       CategoryNode current = (CategoryNode)nodes.nextElement();
/* 36:86 */       current.resetNumberOfContainedRecords();
/* 37:87 */       this._model.nodeChanged(current);
/* 38:   */     }
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerLogRecordFilter
 * JD-Core Version:    0.7.0.1
 */