/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.dom4j.IllegalAddException;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ 
/*  10:    */ public class ContentListFacade
/*  11:    */   extends AbstractList
/*  12:    */ {
/*  13:    */   private List branchContent;
/*  14:    */   private AbstractBranch branch;
/*  15:    */   
/*  16:    */   public ContentListFacade(AbstractBranch branch, List branchContent)
/*  17:    */   {
/*  18: 39 */     this.branch = branch;
/*  19: 40 */     this.branchContent = branchContent;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean add(Object object)
/*  23:    */   {
/*  24: 44 */     this.branch.childAdded(asNode(object));
/*  25:    */     
/*  26: 46 */     return this.branchContent.add(object);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void add(int index, Object object)
/*  30:    */   {
/*  31: 50 */     this.branch.childAdded(asNode(object));
/*  32: 51 */     this.branchContent.add(index, object);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object set(int index, Object object)
/*  36:    */   {
/*  37: 55 */     this.branch.childAdded(asNode(object));
/*  38:    */     
/*  39: 57 */     return this.branchContent.set(index, object);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean remove(Object object)
/*  43:    */   {
/*  44: 61 */     this.branch.childRemoved(asNode(object));
/*  45:    */     
/*  46: 63 */     return this.branchContent.remove(object);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object remove(int index)
/*  50:    */   {
/*  51: 67 */     Object object = this.branchContent.remove(index);
/*  52: 69 */     if (object != null) {
/*  53: 70 */       this.branch.childRemoved(asNode(object));
/*  54:    */     }
/*  55: 73 */     return object;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean addAll(Collection collection)
/*  59:    */   {
/*  60: 77 */     int count = this.branchContent.size();
/*  61: 79 */     for (Iterator iter = collection.iterator(); iter.hasNext(); count++) {
/*  62: 80 */       add(iter.next());
/*  63:    */     }
/*  64: 83 */     return count == this.branchContent.size();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean addAll(int index, Collection collection)
/*  68:    */   {
/*  69: 87 */     int count = this.branchContent.size();
/*  70: 89 */     for (Iterator iter = collection.iterator(); iter.hasNext(); count--) {
/*  71: 90 */       add(index++, iter.next());
/*  72:    */     }
/*  73: 93 */     return count == this.branchContent.size();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void clear()
/*  77:    */   {
/*  78: 97 */     for (Iterator iter = iterator(); iter.hasNext();)
/*  79:    */     {
/*  80: 98 */       Object object = iter.next();
/*  81: 99 */       this.branch.childRemoved(asNode(object));
/*  82:    */     }
/*  83:102 */     this.branchContent.clear();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean removeAll(Collection c)
/*  87:    */   {
/*  88:106 */     for (Iterator iter = c.iterator(); iter.hasNext();)
/*  89:    */     {
/*  90:107 */       Object object = iter.next();
/*  91:108 */       this.branch.childRemoved(asNode(object));
/*  92:    */     }
/*  93:111 */     return this.branchContent.removeAll(c);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int size()
/*  97:    */   {
/*  98:115 */     return this.branchContent.size();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isEmpty()
/* 102:    */   {
/* 103:119 */     return this.branchContent.isEmpty();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean contains(Object o)
/* 107:    */   {
/* 108:123 */     return this.branchContent.contains(o);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Object[] toArray()
/* 112:    */   {
/* 113:127 */     return this.branchContent.toArray();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Object[] toArray(Object[] a)
/* 117:    */   {
/* 118:131 */     return this.branchContent.toArray(a);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean containsAll(Collection c)
/* 122:    */   {
/* 123:135 */     return this.branchContent.containsAll(c);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Object get(int index)
/* 127:    */   {
/* 128:139 */     return this.branchContent.get(index);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int indexOf(Object o)
/* 132:    */   {
/* 133:143 */     return this.branchContent.indexOf(o);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int lastIndexOf(Object o)
/* 137:    */   {
/* 138:147 */     return this.branchContent.lastIndexOf(o);
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected Node asNode(Object object)
/* 142:    */   {
/* 143:151 */     if ((object instanceof Node)) {
/* 144:152 */       return (Node)object;
/* 145:    */     }
/* 146:154 */     throw new IllegalAddException("This list must contain instances of Node. Invalid type: " + object);
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected List getBackingList()
/* 150:    */   {
/* 151:161 */     return this.branchContent;
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.ContentListFacade
 * JD-Core Version:    0.7.0.1
 */