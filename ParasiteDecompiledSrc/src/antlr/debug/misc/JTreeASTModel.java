/*  1:   */ package antlr.debug.misc;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.util.NoSuchElementException;
/*  6:   */ import javax.swing.event.TreeModelListener;
/*  7:   */ import javax.swing.tree.TreeModel;
/*  8:   */ import javax.swing.tree.TreePath;
/*  9:   */ 
/* 10:   */ public class JTreeASTModel
/* 11:   */   implements TreeModel
/* 12:   */ {
/* 13:18 */   AST root = null;
/* 14:   */   
/* 15:   */   public JTreeASTModel(AST paramAST)
/* 16:   */   {
/* 17:21 */     if (paramAST == null) {
/* 18:22 */       throw new IllegalArgumentException("root is null");
/* 19:   */     }
/* 20:24 */     this.root = paramAST;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void addTreeModelListener(TreeModelListener paramTreeModelListener) {}
/* 24:   */   
/* 25:   */   public Object getChild(Object paramObject, int paramInt)
/* 26:   */   {
/* 27:31 */     if (paramObject == null) {
/* 28:32 */       return null;
/* 29:   */     }
/* 30:34 */     AST localAST1 = (AST)paramObject;
/* 31:35 */     AST localAST2 = localAST1.getFirstChild();
/* 32:36 */     if (localAST2 == null) {
/* 33:37 */       throw new ArrayIndexOutOfBoundsException("node has no children");
/* 34:   */     }
/* 35:39 */     int i = 0;
/* 36:40 */     while ((localAST2 != null) && (i < paramInt))
/* 37:   */     {
/* 38:41 */       localAST2 = localAST2.getNextSibling();
/* 39:42 */       i++;
/* 40:   */     }
/* 41:44 */     return localAST2;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getChildCount(Object paramObject)
/* 45:   */   {
/* 46:48 */     if (paramObject == null) {
/* 47:49 */       throw new IllegalArgumentException("root is null");
/* 48:   */     }
/* 49:51 */     AST localAST1 = (AST)paramObject;
/* 50:52 */     AST localAST2 = localAST1.getFirstChild();
/* 51:53 */     int i = 0;
/* 52:54 */     while (localAST2 != null)
/* 53:   */     {
/* 54:55 */       localAST2 = localAST2.getNextSibling();
/* 55:56 */       i++;
/* 56:   */     }
/* 57:58 */     return i;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getIndexOfChild(Object paramObject1, Object paramObject2)
/* 61:   */   {
/* 62:62 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 63:63 */       throw new IllegalArgumentException("root or child is null");
/* 64:   */     }
/* 65:65 */     AST localAST1 = (AST)paramObject1;
/* 66:66 */     AST localAST2 = localAST1.getFirstChild();
/* 67:67 */     if (localAST2 == null) {
/* 68:68 */       throw new ArrayIndexOutOfBoundsException("node has no children");
/* 69:   */     }
/* 70:70 */     int i = 0;
/* 71:71 */     while ((localAST2 != null) && (localAST2 != paramObject2))
/* 72:   */     {
/* 73:72 */       localAST2 = localAST2.getNextSibling();
/* 74:73 */       i++;
/* 75:   */     }
/* 76:75 */     if (localAST2 == paramObject2) {
/* 77:76 */       return i;
/* 78:   */     }
/* 79:78 */     throw new NoSuchElementException("node is not a child");
/* 80:   */   }
/* 81:   */   
/* 82:   */   public Object getRoot()
/* 83:   */   {
/* 84:82 */     return this.root;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public boolean isLeaf(Object paramObject)
/* 88:   */   {
/* 89:86 */     if (paramObject == null) {
/* 90:87 */       throw new IllegalArgumentException("node is null");
/* 91:   */     }
/* 92:89 */     AST localAST = (AST)paramObject;
/* 93:90 */     return localAST.getFirstChild() == null;
/* 94:   */   }
/* 95:   */   
/* 96:   */   public void removeTreeModelListener(TreeModelListener paramTreeModelListener) {}
/* 97:   */   
/* 98:   */   public void valueForPathChanged(TreePath paramTreePath, Object paramObject)
/* 99:   */   {
/* :0:97 */     System.out.println("heh, who is calling this mystery method?");
/* :1:   */   }
/* :2:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.misc.JTreeASTModel
 * JD-Core Version:    0.7.0.1
 */