/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.SortedSet;
/*   4:    */ import java.util.TreeSet;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   6:    */ 
/*   7:    */ public class AstRoot
/*   8:    */   extends ScriptNode
/*   9:    */ {
/*  10:    */   private SortedSet<Comment> comments;
/*  11:    */   private boolean inStrictMode;
/*  12:    */   
/*  13:    */   public AstRoot()
/*  14:    */   {
/*  15: 63 */     this.type = 136;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public AstRoot(int pos)
/*  19:    */   {
/*  20: 70 */     super(pos);this.type = 136;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public SortedSet<Comment> getComments()
/*  24:    */   {
/*  25: 78 */     return this.comments;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setComments(SortedSet<Comment> comments)
/*  29:    */   {
/*  30: 87 */     if (comments == null)
/*  31:    */     {
/*  32: 88 */       this.comments = null;
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36: 90 */       if (this.comments != null) {
/*  37: 91 */         this.comments.clear();
/*  38:    */       }
/*  39: 92 */       for (Comment c : comments) {
/*  40: 93 */         addComment(c);
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addComment(Comment comment)
/*  46:    */   {
/*  47:103 */     assertNotNull(comment);
/*  48:104 */     if (this.comments == null) {
/*  49:105 */       this.comments = new TreeSet(new AstNode.PositionComparator());
/*  50:    */     }
/*  51:107 */     this.comments.add(comment);
/*  52:108 */     comment.setParent(this);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setInStrictMode(boolean inStrictMode)
/*  56:    */   {
/*  57:112 */     this.inStrictMode = inStrictMode;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isInStrictMode()
/*  61:    */   {
/*  62:116 */     return this.inStrictMode;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void visitComments(NodeVisitor visitor)
/*  66:    */   {
/*  67:127 */     if (this.comments != null) {
/*  68:128 */       for (Comment c : this.comments) {
/*  69:129 */         visitor.visit(c);
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void visitAll(NodeVisitor visitor)
/*  75:    */   {
/*  76:142 */     visit(visitor);
/*  77:143 */     visitComments(visitor);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toSource(int depth)
/*  81:    */   {
/*  82:148 */     StringBuilder sb = new StringBuilder();
/*  83:149 */     for (Node node : this) {
/*  84:150 */       sb.append(((AstNode)node).toSource(depth));
/*  85:    */     }
/*  86:152 */     return sb.toString();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String debugPrint()
/*  90:    */   {
/*  91:160 */     AstNode.DebugPrintVisitor dpv = new AstNode.DebugPrintVisitor(new StringBuilder(1000));
/*  92:161 */     visitAll(dpv);
/*  93:162 */     return dpv.toString();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void checkParentLinks()
/*  97:    */   {
/*  98:171 */     visit(new NodeVisitor()
/*  99:    */     {
/* 100:    */       public boolean visit(AstNode node)
/* 101:    */       {
/* 102:173 */         int type = node.getType();
/* 103:174 */         if (type == 136) {
/* 104:175 */           return true;
/* 105:    */         }
/* 106:176 */         if (node.getParent() == null) {
/* 107:177 */           throw new IllegalStateException("No parent for node: " + node + "\n" + node.toSource(0));
/* 108:    */         }
/* 109:180 */         return true;
/* 110:    */       }
/* 111:    */     });
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot
 * JD-Core Version:    0.7.0.1
 */