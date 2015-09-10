/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*   5:    */ import javax.swing.tree.TreeNode;
/*   6:    */ 
/*   7:    */ public class CategoryNode
/*   8:    */   extends DefaultMutableTreeNode
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 5958994817693177319L;
/*  11: 41 */   protected boolean _selected = true;
/*  12: 42 */   protected int _numberOfContainedRecords = 0;
/*  13: 43 */   protected int _numberOfRecordsFromChildren = 0;
/*  14: 44 */   protected boolean _hasFatalChildren = false;
/*  15: 45 */   protected boolean _hasFatalRecords = false;
/*  16:    */   
/*  17:    */   public CategoryNode(String title)
/*  18:    */   {
/*  19: 59 */     setUserObject(title);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getTitle()
/*  23:    */   {
/*  24: 66 */     return (String)getUserObject();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setSelected(boolean s)
/*  28:    */   {
/*  29: 70 */     if (s != this._selected) {
/*  30: 71 */       this._selected = s;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isSelected()
/*  35:    */   {
/*  36: 76 */     return this._selected;
/*  37:    */   }
/*  38:    */   
/*  39:    */   /**
/*  40:    */    * @deprecated
/*  41:    */    */
/*  42:    */   public void setAllDescendantsSelected()
/*  43:    */   {
/*  44: 83 */     Enumeration children = children();
/*  45: 84 */     while (children.hasMoreElements())
/*  46:    */     {
/*  47: 85 */       CategoryNode node = (CategoryNode)children.nextElement();
/*  48: 86 */       node.setSelected(true);
/*  49: 87 */       node.setAllDescendantsSelected();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   /**
/*  54:    */    * @deprecated
/*  55:    */    */
/*  56:    */   public void setAllDescendantsDeSelected()
/*  57:    */   {
/*  58: 95 */     Enumeration children = children();
/*  59: 96 */     while (children.hasMoreElements())
/*  60:    */     {
/*  61: 97 */       CategoryNode node = (CategoryNode)children.nextElement();
/*  62: 98 */       node.setSelected(false);
/*  63: 99 */       node.setAllDescendantsDeSelected();
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:104 */     return getTitle();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean equals(Object obj)
/*  73:    */   {
/*  74:108 */     if ((obj instanceof CategoryNode))
/*  75:    */     {
/*  76:109 */       CategoryNode node = (CategoryNode)obj;
/*  77:110 */       String tit1 = getTitle().toLowerCase();
/*  78:111 */       String tit2 = node.getTitle().toLowerCase();
/*  79:113 */       if (tit1.equals(tit2)) {
/*  80:114 */         return true;
/*  81:    */       }
/*  82:    */     }
/*  83:117 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int hashCode()
/*  87:    */   {
/*  88:121 */     return getTitle().hashCode();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void addRecord()
/*  92:    */   {
/*  93:125 */     this._numberOfContainedRecords += 1;
/*  94:126 */     addRecordToParent();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getNumberOfContainedRecords()
/*  98:    */   {
/*  99:130 */     return this._numberOfContainedRecords;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void resetNumberOfContainedRecords()
/* 103:    */   {
/* 104:134 */     this._numberOfContainedRecords = 0;
/* 105:135 */     this._numberOfRecordsFromChildren = 0;
/* 106:136 */     this._hasFatalRecords = false;
/* 107:137 */     this._hasFatalChildren = false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean hasFatalRecords()
/* 111:    */   {
/* 112:141 */     return this._hasFatalRecords;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean hasFatalChildren()
/* 116:    */   {
/* 117:145 */     return this._hasFatalChildren;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setHasFatalRecords(boolean flag)
/* 121:    */   {
/* 122:149 */     this._hasFatalRecords = flag;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setHasFatalChildren(boolean flag)
/* 126:    */   {
/* 127:153 */     this._hasFatalChildren = flag;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected int getTotalNumberOfRecords()
/* 131:    */   {
/* 132:161 */     return getNumberOfRecordsFromChildren() + getNumberOfContainedRecords();
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected void addRecordFromChild()
/* 136:    */   {
/* 137:168 */     this._numberOfRecordsFromChildren += 1;
/* 138:169 */     addRecordToParent();
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected int getNumberOfRecordsFromChildren()
/* 142:    */   {
/* 143:173 */     return this._numberOfRecordsFromChildren;
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected void addRecordToParent()
/* 147:    */   {
/* 148:177 */     TreeNode parent = getParent();
/* 149:178 */     if (parent == null) {
/* 150:179 */       return;
/* 151:    */     }
/* 152:181 */     ((CategoryNode)parent).addRecordFromChild();
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNode
 * JD-Core Version:    0.7.0.1
 */