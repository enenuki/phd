/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ 
/*   9:    */ public class NodeCachingLinkedList
/*  10:    */   extends AbstractLinkedList
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = 6897789178562232073L;
/*  14:    */   protected static final int DEFAULT_MAXIMUM_CACHE_SIZE = 20;
/*  15:    */   protected transient AbstractLinkedList.Node firstCachedNode;
/*  16:    */   protected transient int cacheSize;
/*  17:    */   protected int maximumCacheSize;
/*  18:    */   
/*  19:    */   public NodeCachingLinkedList()
/*  20:    */   {
/*  21: 79 */     this(20);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public NodeCachingLinkedList(Collection coll)
/*  25:    */   {
/*  26: 88 */     super(coll);
/*  27: 89 */     this.maximumCacheSize = 20;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public NodeCachingLinkedList(int maximumCacheSize)
/*  31:    */   {
/*  32: 99 */     this.maximumCacheSize = maximumCacheSize;
/*  33:100 */     init();
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int getMaximumCacheSize()
/*  37:    */   {
/*  38:110 */     return this.maximumCacheSize;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void setMaximumCacheSize(int maximumCacheSize)
/*  42:    */   {
/*  43:119 */     this.maximumCacheSize = maximumCacheSize;
/*  44:120 */     shrinkCacheToMaximumSize();
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void shrinkCacheToMaximumSize()
/*  48:    */   {
/*  49:128 */     while (this.cacheSize > this.maximumCacheSize) {
/*  50:129 */       getNodeFromCache();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected AbstractLinkedList.Node getNodeFromCache()
/*  55:    */   {
/*  56:141 */     if (this.cacheSize == 0) {
/*  57:142 */       return null;
/*  58:    */     }
/*  59:144 */     AbstractLinkedList.Node cachedNode = this.firstCachedNode;
/*  60:145 */     this.firstCachedNode = cachedNode.next;
/*  61:146 */     cachedNode.next = null;
/*  62:    */     
/*  63:148 */     this.cacheSize -= 1;
/*  64:149 */     return cachedNode;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected boolean isCacheFull()
/*  68:    */   {
/*  69:158 */     return this.cacheSize >= this.maximumCacheSize;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void addNodeToCache(AbstractLinkedList.Node node)
/*  73:    */   {
/*  74:168 */     if (isCacheFull()) {
/*  75:170 */       return;
/*  76:    */     }
/*  77:173 */     AbstractLinkedList.Node nextCachedNode = this.firstCachedNode;
/*  78:174 */     node.previous = null;
/*  79:175 */     node.next = nextCachedNode;
/*  80:176 */     node.setValue(null);
/*  81:177 */     this.firstCachedNode = node;
/*  82:178 */     this.cacheSize += 1;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected AbstractLinkedList.Node createNode(Object value)
/*  86:    */   {
/*  87:190 */     AbstractLinkedList.Node cachedNode = getNodeFromCache();
/*  88:191 */     if (cachedNode == null) {
/*  89:192 */       return super.createNode(value);
/*  90:    */     }
/*  91:194 */     cachedNode.setValue(value);
/*  92:195 */     return cachedNode;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void removeNode(AbstractLinkedList.Node node)
/*  96:    */   {
/*  97:206 */     super.removeNode(node);
/*  98:207 */     addNodeToCache(node);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void removeAllNodes()
/* 102:    */   {
/* 103:220 */     int numberOfNodesToCache = Math.min(this.size, this.maximumCacheSize - this.cacheSize);
/* 104:221 */     AbstractLinkedList.Node node = this.header.next;
/* 105:222 */     for (int currentIndex = 0; currentIndex < numberOfNodesToCache; currentIndex++)
/* 106:    */     {
/* 107:223 */       AbstractLinkedList.Node oldNode = node;
/* 108:224 */       node = node.next;
/* 109:225 */       addNodeToCache(oldNode);
/* 110:    */     }
/* 111:227 */     super.removeAllNodes();
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void writeObject(ObjectOutputStream out)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:235 */     out.defaultWriteObject();
/* 118:236 */     doWriteObject(out);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void readObject(ObjectInputStream in)
/* 122:    */     throws IOException, ClassNotFoundException
/* 123:    */   {
/* 124:243 */     in.defaultReadObject();
/* 125:244 */     doReadObject(in);
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.NodeCachingLinkedList
 * JD-Core Version:    0.7.0.1
 */