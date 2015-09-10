/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ListIterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class FilterListIterator
/*   8:    */   implements ListIterator
/*   9:    */ {
/*  10:    */   private ListIterator iterator;
/*  11:    */   private Predicate predicate;
/*  12:    */   private Object nextObject;
/*  13: 53 */   private boolean nextObjectSet = false;
/*  14:    */   private Object previousObject;
/*  15: 65 */   private boolean previousObjectSet = false;
/*  16: 70 */   private int nextIndex = 0;
/*  17:    */   
/*  18:    */   public FilterListIterator() {}
/*  19:    */   
/*  20:    */   public FilterListIterator(ListIterator iterator)
/*  21:    */   {
/*  22: 90 */     this.iterator = iterator;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public FilterListIterator(ListIterator iterator, Predicate predicate)
/*  26:    */   {
/*  27:101 */     this.iterator = iterator;
/*  28:102 */     this.predicate = predicate;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FilterListIterator(Predicate predicate)
/*  32:    */   {
/*  33:113 */     this.predicate = predicate;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void add(Object o)
/*  37:    */   {
/*  38:119 */     throw new UnsupportedOperationException("FilterListIterator.add(Object) is not supported.");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean hasNext()
/*  42:    */   {
/*  43:123 */     if (this.nextObjectSet) {
/*  44:124 */       return true;
/*  45:    */     }
/*  46:126 */     return setNextObject();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasPrevious()
/*  50:    */   {
/*  51:131 */     if (this.previousObjectSet) {
/*  52:132 */       return true;
/*  53:    */     }
/*  54:134 */     return setPreviousObject();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object next()
/*  58:    */   {
/*  59:139 */     if ((!this.nextObjectSet) && 
/*  60:140 */       (!setNextObject())) {
/*  61:141 */       throw new NoSuchElementException();
/*  62:    */     }
/*  63:144 */     this.nextIndex += 1;
/*  64:145 */     Object temp = this.nextObject;
/*  65:146 */     clearNextObject();
/*  66:147 */     return temp;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int nextIndex()
/*  70:    */   {
/*  71:151 */     return this.nextIndex;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object previous()
/*  75:    */   {
/*  76:155 */     if ((!this.previousObjectSet) && 
/*  77:156 */       (!setPreviousObject())) {
/*  78:157 */       throw new NoSuchElementException();
/*  79:    */     }
/*  80:160 */     this.nextIndex -= 1;
/*  81:161 */     Object temp = this.previousObject;
/*  82:162 */     clearPreviousObject();
/*  83:163 */     return temp;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int previousIndex()
/*  87:    */   {
/*  88:167 */     return this.nextIndex - 1;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void remove()
/*  92:    */   {
/*  93:172 */     throw new UnsupportedOperationException("FilterListIterator.remove() is not supported.");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void set(Object o)
/*  97:    */   {
/*  98:177 */     throw new UnsupportedOperationException("FilterListIterator.set(Object) is not supported.");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public ListIterator getListIterator()
/* 102:    */   {
/* 103:187 */     return this.iterator;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setListIterator(ListIterator iterator)
/* 107:    */   {
/* 108:197 */     this.iterator = iterator;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Predicate getPredicate()
/* 112:    */   {
/* 113:207 */     return this.predicate;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPredicate(Predicate predicate)
/* 117:    */   {
/* 118:216 */     this.predicate = predicate;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void clearNextObject()
/* 122:    */   {
/* 123:221 */     this.nextObject = null;
/* 124:222 */     this.nextObjectSet = false;
/* 125:    */   }
/* 126:    */   
/* 127:    */   private boolean setNextObject()
/* 128:    */   {
/* 129:230 */     if (this.previousObjectSet)
/* 130:    */     {
/* 131:231 */       clearPreviousObject();
/* 132:232 */       if (!setNextObject()) {
/* 133:233 */         return false;
/* 134:    */       }
/* 135:235 */       clearNextObject();
/* 136:    */     }
/* 137:239 */     while (this.iterator.hasNext())
/* 138:    */     {
/* 139:240 */       Object object = this.iterator.next();
/* 140:241 */       if (this.predicate.evaluate(object))
/* 141:    */       {
/* 142:242 */         this.nextObject = object;
/* 143:243 */         this.nextObjectSet = true;
/* 144:244 */         return true;
/* 145:    */       }
/* 146:    */     }
/* 147:247 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void clearPreviousObject()
/* 151:    */   {
/* 152:251 */     this.previousObject = null;
/* 153:252 */     this.previousObjectSet = false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private boolean setPreviousObject()
/* 157:    */   {
/* 158:260 */     if (this.nextObjectSet)
/* 159:    */     {
/* 160:261 */       clearNextObject();
/* 161:262 */       if (!setPreviousObject()) {
/* 162:263 */         return false;
/* 163:    */       }
/* 164:265 */       clearPreviousObject();
/* 165:    */     }
/* 166:269 */     while (this.iterator.hasPrevious())
/* 167:    */     {
/* 168:270 */       Object object = this.iterator.previous();
/* 169:271 */       if (this.predicate.evaluate(object))
/* 170:    */       {
/* 171:272 */         this.previousObject = object;
/* 172:273 */         this.previousObjectSet = true;
/* 173:274 */         return true;
/* 174:    */       }
/* 175:    */     }
/* 176:277 */     return false;
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.FilterListIterator
 * JD-Core Version:    0.7.0.1
 */