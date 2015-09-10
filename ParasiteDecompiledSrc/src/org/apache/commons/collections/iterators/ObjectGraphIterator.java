/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ArrayStack;
/*   6:    */ import org.apache.commons.collections.Transformer;
/*   7:    */ 
/*   8:    */ public class ObjectGraphIterator
/*   9:    */   implements Iterator
/*  10:    */ {
/*  11: 81 */   protected final ArrayStack stack = new ArrayStack(8);
/*  12:    */   protected Object root;
/*  13:    */   protected Transformer transformer;
/*  14: 88 */   protected boolean hasNext = false;
/*  15:    */   protected Iterator currentIterator;
/*  16:    */   protected Object currentValue;
/*  17:    */   protected Iterator lastUsedIterator;
/*  18:    */   
/*  19:    */   public ObjectGraphIterator(Object root, Transformer transformer)
/*  20:    */   {
/*  21:108 */     if ((root instanceof Iterator)) {
/*  22:109 */       this.currentIterator = ((Iterator)root);
/*  23:    */     } else {
/*  24:111 */       this.root = root;
/*  25:    */     }
/*  26:113 */     this.transformer = transformer;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ObjectGraphIterator(Iterator rootIterator)
/*  30:    */   {
/*  31:128 */     this.currentIterator = rootIterator;
/*  32:129 */     this.transformer = null;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void updateCurrentIterator()
/*  36:    */   {
/*  37:137 */     if (this.hasNext) {
/*  38:138 */       return;
/*  39:    */     }
/*  40:140 */     if (this.currentIterator == null)
/*  41:    */     {
/*  42:141 */       if (this.root != null)
/*  43:    */       {
/*  44:144 */         if (this.transformer == null) {
/*  45:145 */           findNext(this.root);
/*  46:    */         } else {
/*  47:147 */           findNext(this.transformer.transform(this.root));
/*  48:    */         }
/*  49:149 */         this.root = null;
/*  50:    */       }
/*  51:    */     }
/*  52:    */     else {
/*  53:152 */       findNextByIterator(this.currentIterator);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void findNext(Object value)
/*  58:    */   {
/*  59:162 */     if ((value instanceof Iterator))
/*  60:    */     {
/*  61:164 */       findNextByIterator((Iterator)value);
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65:167 */       this.currentValue = value;
/*  66:168 */       this.hasNext = true;
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void findNextByIterator(Iterator iterator)
/*  71:    */   {
/*  72:178 */     if (iterator != this.currentIterator)
/*  73:    */     {
/*  74:180 */       if (this.currentIterator != null) {
/*  75:181 */         this.stack.push(this.currentIterator);
/*  76:    */       }
/*  77:183 */       this.currentIterator = iterator;
/*  78:    */     }
/*  79:186 */     while ((this.currentIterator.hasNext()) && (!this.hasNext))
/*  80:    */     {
/*  81:187 */       Object next = this.currentIterator.next();
/*  82:188 */       if (this.transformer != null) {
/*  83:189 */         next = this.transformer.transform(next);
/*  84:    */       }
/*  85:191 */       findNext(next);
/*  86:    */     }
/*  87:193 */     if (!this.hasNext) {
/*  88:195 */       if (!this.stack.isEmpty())
/*  89:    */       {
/*  90:199 */         this.currentIterator = ((Iterator)this.stack.pop());
/*  91:200 */         findNextByIterator(this.currentIterator);
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean hasNext()
/*  97:    */   {
/*  98:211 */     updateCurrentIterator();
/*  99:212 */     return this.hasNext;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object next()
/* 103:    */   {
/* 104:222 */     updateCurrentIterator();
/* 105:223 */     if (!this.hasNext) {
/* 106:224 */       throw new NoSuchElementException("No more elements in the iteration");
/* 107:    */     }
/* 108:226 */     this.lastUsedIterator = this.currentIterator;
/* 109:227 */     Object result = this.currentValue;
/* 110:228 */     this.currentValue = null;
/* 111:229 */     this.hasNext = false;
/* 112:230 */     return result;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void remove()
/* 116:    */   {
/* 117:247 */     if (this.lastUsedIterator == null) {
/* 118:248 */       throw new IllegalStateException("Iterator remove() cannot be called at this time");
/* 119:    */     }
/* 120:250 */     this.lastUsedIterator.remove();
/* 121:251 */     this.lastUsedIterator = null;
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ObjectGraphIterator
 * JD-Core Version:    0.7.0.1
 */