/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*   8:    */ 
/*   9:    */ public class IteratorChain
/*  10:    */   implements Iterator
/*  11:    */ {
/*  12: 54 */   protected final List iteratorChain = new ArrayList();
/*  13: 56 */   protected int currentIteratorIndex = 0;
/*  14: 58 */   protected Iterator currentIterator = null;
/*  15: 64 */   protected Iterator lastUsedIterator = null;
/*  16: 69 */   protected boolean isLocked = false;
/*  17:    */   
/*  18:    */   public IteratorChain() {}
/*  19:    */   
/*  20:    */   public IteratorChain(Iterator iterator)
/*  21:    */   {
/*  22: 90 */     addIterator(iterator);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public IteratorChain(Iterator a, Iterator b)
/*  26:    */   {
/*  27:103 */     addIterator(a);
/*  28:104 */     addIterator(b);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IteratorChain(Iterator[] iterators)
/*  32:    */   {
/*  33:116 */     for (int i = 0; i < iterators.length; i++) {
/*  34:117 */       addIterator(iterators[i]);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public IteratorChain(Collection iterators)
/*  39:    */   {
/*  40:131 */     for (Iterator it = iterators.iterator(); it.hasNext();)
/*  41:    */     {
/*  42:132 */       Iterator item = (Iterator)it.next();
/*  43:133 */       addIterator(item);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addIterator(Iterator iterator)
/*  48:    */   {
/*  49:146 */     checkLocked();
/*  50:147 */     if (iterator == null) {
/*  51:148 */       throw new NullPointerException("Iterator must not be null");
/*  52:    */     }
/*  53:150 */     this.iteratorChain.add(iterator);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIterator(int index, Iterator iterator)
/*  57:    */     throws IndexOutOfBoundsException
/*  58:    */   {
/*  59:163 */     checkLocked();
/*  60:164 */     if (iterator == null) {
/*  61:165 */       throw new NullPointerException("Iterator must not be null");
/*  62:    */     }
/*  63:167 */     this.iteratorChain.set(index, iterator);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List getIterators()
/*  67:    */   {
/*  68:176 */     return UnmodifiableList.decorate(this.iteratorChain);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int size()
/*  72:    */   {
/*  73:185 */     return this.iteratorChain.size();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isLocked()
/*  77:    */   {
/*  78:196 */     return this.isLocked;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void checkLocked()
/*  82:    */   {
/*  83:203 */     if (this.isLocked == true) {
/*  84:204 */       throw new UnsupportedOperationException("IteratorChain cannot be changed after the first use of a method from the Iterator interface");
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void lockChain()
/*  89:    */   {
/*  90:213 */     if (!this.isLocked) {
/*  91:214 */       this.isLocked = true;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void updateCurrentIterator()
/*  96:    */   {
/*  97:223 */     if (this.currentIterator == null)
/*  98:    */     {
/*  99:224 */       if (this.iteratorChain.isEmpty()) {
/* 100:225 */         this.currentIterator = EmptyIterator.INSTANCE;
/* 101:    */       } else {
/* 102:227 */         this.currentIterator = ((Iterator)this.iteratorChain.get(0));
/* 103:    */       }
/* 104:231 */       this.lastUsedIterator = this.currentIterator;
/* 105:    */     }
/* 106:234 */     while ((!this.currentIterator.hasNext()) && (this.currentIteratorIndex < this.iteratorChain.size() - 1))
/* 107:    */     {
/* 108:235 */       this.currentIteratorIndex += 1;
/* 109:236 */       this.currentIterator = ((Iterator)this.iteratorChain.get(this.currentIteratorIndex));
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean hasNext()
/* 114:    */   {
/* 115:247 */     lockChain();
/* 116:248 */     updateCurrentIterator();
/* 117:249 */     this.lastUsedIterator = this.currentIterator;
/* 118:    */     
/* 119:251 */     return this.currentIterator.hasNext();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Object next()
/* 123:    */   {
/* 124:261 */     lockChain();
/* 125:262 */     updateCurrentIterator();
/* 126:263 */     this.lastUsedIterator = this.currentIterator;
/* 127:    */     
/* 128:265 */     return this.currentIterator.next();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void remove()
/* 132:    */   {
/* 133:283 */     lockChain();
/* 134:284 */     if (this.currentIterator == null) {
/* 135:285 */       updateCurrentIterator();
/* 136:    */     }
/* 137:287 */     this.lastUsedIterator.remove();
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.IteratorChain
 * JD-Core Version:    0.7.0.1
 */