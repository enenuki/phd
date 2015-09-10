/*   1:    */ package org.dom4j.swing;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.swing.tree.TreeNode;
/*   7:    */ import org.dom4j.Branch;
/*   8:    */ import org.dom4j.CharacterData;
/*   9:    */ import org.dom4j.Node;
/*  10:    */ 
/*  11:    */ public class BranchTreeNode
/*  12:    */   extends LeafTreeNode
/*  13:    */ {
/*  14:    */   protected List children;
/*  15:    */   
/*  16:    */   public BranchTreeNode() {}
/*  17:    */   
/*  18:    */   public BranchTreeNode(Branch xmlNode)
/*  19:    */   {
/*  20: 39 */     super(xmlNode);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public BranchTreeNode(TreeNode parent, Branch xmlNode)
/*  24:    */   {
/*  25: 43 */     super(parent, xmlNode);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Enumeration children()
/*  29:    */   {
/*  30: 49 */     new Enumeration()
/*  31:    */     {
/*  32: 50 */       private int index = -1;
/*  33:    */       
/*  34:    */       public boolean hasMoreElements()
/*  35:    */       {
/*  36: 53 */         return this.index + 1 < BranchTreeNode.this.getChildCount();
/*  37:    */       }
/*  38:    */       
/*  39:    */       public Object nextElement()
/*  40:    */       {
/*  41: 57 */         return BranchTreeNode.this.getChildAt(++this.index);
/*  42:    */       }
/*  43:    */     };
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean getAllowsChildren()
/*  47:    */   {
/*  48: 63 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TreeNode getChildAt(int childIndex)
/*  52:    */   {
/*  53: 67 */     return (TreeNode)getChildList().get(childIndex);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getChildCount()
/*  57:    */   {
/*  58: 71 */     return getChildList().size();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIndex(TreeNode node)
/*  62:    */   {
/*  63: 75 */     return getChildList().indexOf(node);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isLeaf()
/*  67:    */   {
/*  68: 79 */     return getXmlBranch().nodeCount() <= 0;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String toString()
/*  72:    */   {
/*  73: 83 */     return this.xmlNode.getName();
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected List getChildList()
/*  77:    */   {
/*  78: 98 */     if (this.children == null) {
/*  79: 99 */       this.children = createChildList();
/*  80:    */     }
/*  81:102 */     return this.children;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected List createChildList()
/*  85:    */   {
/*  86:112 */     Branch branch = getXmlBranch();
/*  87:113 */     int size = branch.nodeCount();
/*  88:114 */     List childList = new ArrayList(size);
/*  89:116 */     for (int i = 0; i < size; i++)
/*  90:    */     {
/*  91:117 */       Node node = branch.node(i);
/*  92:120 */       if ((node instanceof CharacterData))
/*  93:    */       {
/*  94:121 */         String text = node.getText();
/*  95:123 */         if (text != null)
/*  96:    */         {
/*  97:127 */           text = text.trim();
/*  98:129 */           if (text.length() <= 0) {}
/*  99:    */         }
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:134 */         childList.add(createChildTreeNode(node));
/* 104:    */       }
/* 105:    */     }
/* 106:137 */     return childList;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected TreeNode createChildTreeNode(Node xmlNode)
/* 110:    */   {
/* 111:149 */     if ((xmlNode instanceof Branch)) {
/* 112:150 */       return new BranchTreeNode(this, (Branch)xmlNode);
/* 113:    */     }
/* 114:152 */     return new LeafTreeNode(this, xmlNode);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Branch getXmlBranch()
/* 118:    */   {
/* 119:157 */     return (Branch)this.xmlNode;
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.BranchTreeNode
 * JD-Core Version:    0.7.0.1
 */