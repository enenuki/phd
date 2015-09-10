/*  1:   */ package org.dom4j.swing;
/*  2:   */ 
/*  3:   */ import javax.swing.tree.DefaultTreeModel;
/*  4:   */ import org.dom4j.Document;
/*  5:   */ 
/*  6:   */ public class DocumentTreeModel
/*  7:   */   extends DefaultTreeModel
/*  8:   */ {
/*  9:   */   protected Document document;
/* 10:   */   
/* 11:   */   public DocumentTreeModel(Document document)
/* 12:   */   {
/* 13:29 */     super(new BranchTreeNode(document));
/* 14:30 */     this.document = document;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Document getDocument()
/* 18:   */   {
/* 19:43 */     return this.document;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setDocument(Document document)
/* 23:   */   {
/* 24:54 */     this.document = document;
/* 25:55 */     setRoot(new BranchTreeNode(document));
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.DocumentTreeModel
 * JD-Core Version:    0.7.0.1
 */