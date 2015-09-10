/*   1:    */ package org.dom4j.swing;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import javax.swing.tree.TreeNode;
/*   5:    */ import org.dom4j.Node;
/*   6:    */ 
/*   7:    */ public class LeafTreeNode
/*   8:    */   implements TreeNode
/*   9:    */ {
/*  10: 27 */   protected static final Enumeration EMPTY_ENUMERATION = new Enumeration()
/*  11:    */   {
/*  12:    */     public boolean hasMoreElements()
/*  13:    */     {
/*  14: 29 */       return false;
/*  15:    */     }
/*  16:    */     
/*  17:    */     public Object nextElement()
/*  18:    */     {
/*  19: 33 */       return null;
/*  20:    */     }
/*  21:    */   };
/*  22:    */   private TreeNode parent;
/*  23:    */   protected Node xmlNode;
/*  24:    */   
/*  25:    */   public LeafTreeNode() {}
/*  26:    */   
/*  27:    */   public LeafTreeNode(Node xmlNode)
/*  28:    */   {
/*  29: 47 */     this.xmlNode = xmlNode;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LeafTreeNode(TreeNode parent, Node xmlNode)
/*  33:    */   {
/*  34: 51 */     this.parent = parent;
/*  35: 52 */     this.xmlNode = xmlNode;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Enumeration children()
/*  39:    */   {
/*  40: 58 */     return EMPTY_ENUMERATION;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean getAllowsChildren()
/*  44:    */   {
/*  45: 62 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public TreeNode getChildAt(int childIndex)
/*  49:    */   {
/*  50: 66 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getChildCount()
/*  54:    */   {
/*  55: 70 */     return 0;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIndex(TreeNode node)
/*  59:    */   {
/*  60: 74 */     return -1;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TreeNode getParent()
/*  64:    */   {
/*  65: 78 */     return this.parent;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isLeaf()
/*  69:    */   {
/*  70: 82 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String toString()
/*  74:    */   {
/*  75: 87 */     String text = this.xmlNode.getText();
/*  76:    */     
/*  77: 89 */     return text != null ? text.trim() : "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setParent(LeafTreeNode parent)
/*  81:    */   {
/*  82:102 */     this.parent = parent;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Node getXmlNode()
/*  86:    */   {
/*  87:106 */     return this.xmlNode;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.LeafTreeNode
 * JD-Core Version:    0.7.0.1
 */