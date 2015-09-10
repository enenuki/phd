/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.dom4j.IllegalAddException;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ 
/*  10:    */ public class BackedList
/*  11:    */   extends ArrayList
/*  12:    */ {
/*  13:    */   private List branchContent;
/*  14:    */   private AbstractBranch branch;
/*  15:    */   
/*  16:    */   public BackedList(AbstractBranch branch, List branchContent)
/*  17:    */   {
/*  18: 36 */     this(branch, branchContent, branchContent.size());
/*  19:    */   }
/*  20:    */   
/*  21:    */   public BackedList(AbstractBranch branch, List branchContent, int capacity)
/*  22:    */   {
/*  23: 40 */     super(capacity);
/*  24: 41 */     this.branch = branch;
/*  25: 42 */     this.branchContent = branchContent;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public BackedList(AbstractBranch branch, List branchContent, List initialContent)
/*  29:    */   {
/*  30: 47 */     super(initialContent);
/*  31: 48 */     this.branch = branch;
/*  32: 49 */     this.branchContent = branchContent;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean add(Object object)
/*  36:    */   {
/*  37: 53 */     this.branch.addNode(asNode(object));
/*  38:    */     
/*  39: 55 */     return super.add(object);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void add(int index, Object object)
/*  43:    */   {
/*  44: 59 */     int size = size();
/*  45: 61 */     if (index < 0) {
/*  46: 62 */       throw new IndexOutOfBoundsException("Index value: " + index + " is less than zero");
/*  47:    */     }
/*  48: 64 */     if (index > size) {
/*  49: 65 */       throw new IndexOutOfBoundsException("Index value: " + index + " cannot be greater than " + "the size: " + size);
/*  50:    */     }
/*  51:    */     int realIndex;
/*  52:    */     int realIndex;
/*  53: 71 */     if (size == 0)
/*  54:    */     {
/*  55: 72 */       realIndex = this.branchContent.size();
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59:    */       int realIndex;
/*  60: 73 */       if (index < size) {
/*  61: 74 */         realIndex = this.branchContent.indexOf(get(index));
/*  62:    */       } else {
/*  63: 76 */         realIndex = this.branchContent.indexOf(get(size - 1)) + 1;
/*  64:    */       }
/*  65:    */     }
/*  66: 79 */     this.branch.addNode(realIndex, asNode(object));
/*  67: 80 */     super.add(index, object);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object set(int index, Object object)
/*  71:    */   {
/*  72: 84 */     int realIndex = this.branchContent.indexOf(get(index));
/*  73: 86 */     if (realIndex < 0) {
/*  74: 87 */       realIndex = index == 0 ? 0 : 2147483647;
/*  75:    */     }
/*  76: 90 */     if (realIndex < this.branchContent.size())
/*  77:    */     {
/*  78: 91 */       this.branch.removeNode(asNode(get(index)));
/*  79: 92 */       this.branch.addNode(realIndex, asNode(object));
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 94 */       this.branch.removeNode(asNode(get(index)));
/*  84: 95 */       this.branch.addNode(asNode(object));
/*  85:    */     }
/*  86: 98 */     this.branch.childAdded(asNode(object));
/*  87:    */     
/*  88:100 */     return super.set(index, object);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean remove(Object object)
/*  92:    */   {
/*  93:104 */     this.branch.removeNode(asNode(object));
/*  94:    */     
/*  95:106 */     return super.remove(object);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object remove(int index)
/*  99:    */   {
/* 100:110 */     Object object = super.remove(index);
/* 101:112 */     if (object != null) {
/* 102:113 */       this.branch.removeNode(asNode(object));
/* 103:    */     }
/* 104:116 */     return object;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean addAll(Collection collection)
/* 108:    */   {
/* 109:120 */     ensureCapacity(size() + collection.size());
/* 110:    */     
/* 111:122 */     int count = size();
/* 112:124 */     for (Iterator iter = collection.iterator(); iter.hasNext(); count--) {
/* 113:125 */       add(iter.next());
/* 114:    */     }
/* 115:128 */     return count != 0;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean addAll(int index, Collection collection)
/* 119:    */   {
/* 120:132 */     ensureCapacity(size() + collection.size());
/* 121:    */     
/* 122:134 */     int count = size();
/* 123:136 */     for (Iterator iter = collection.iterator(); iter.hasNext(); count--) {
/* 124:137 */       add(index++, iter.next());
/* 125:    */     }
/* 126:140 */     return count != 0;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void clear()
/* 130:    */   {
/* 131:144 */     for (Iterator iter = iterator(); iter.hasNext();)
/* 132:    */     {
/* 133:145 */       Object object = iter.next();
/* 134:146 */       this.branchContent.remove(object);
/* 135:147 */       this.branch.childRemoved(asNode(object));
/* 136:    */     }
/* 137:150 */     super.clear();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void addLocal(Object object)
/* 141:    */   {
/* 142:161 */     super.add(object);
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected Node asNode(Object object)
/* 146:    */   {
/* 147:165 */     if ((object instanceof Node)) {
/* 148:166 */       return (Node)object;
/* 149:    */     }
/* 150:168 */     throw new IllegalAddException("This list must contain instances of Node. Invalid type: " + object);
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.BackedList
 * JD-Core Version:    0.7.0.1
 */