/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class FilterIterator
/*   8:    */   implements Iterator
/*   9:    */ {
/*  10:    */   private Iterator iterator;
/*  11:    */   private Predicate predicate;
/*  12:    */   private Object nextObject;
/*  13: 47 */   private boolean nextObjectSet = false;
/*  14:    */   
/*  15:    */   public FilterIterator() {}
/*  16:    */   
/*  17:    */   public FilterIterator(Iterator iterator)
/*  18:    */   {
/*  19: 66 */     this.iterator = iterator;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public FilterIterator(Iterator iterator, Predicate predicate)
/*  23:    */   {
/*  24: 78 */     this.iterator = iterator;
/*  25: 79 */     this.predicate = predicate;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean hasNext()
/*  29:    */   {
/*  30: 91 */     if (this.nextObjectSet) {
/*  31: 92 */       return true;
/*  32:    */     }
/*  33: 94 */     return setNextObject();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object next()
/*  37:    */   {
/*  38:107 */     if ((!this.nextObjectSet) && 
/*  39:108 */       (!setNextObject())) {
/*  40:109 */       throw new NoSuchElementException();
/*  41:    */     }
/*  42:112 */     this.nextObjectSet = false;
/*  43:113 */     return this.nextObject;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void remove()
/*  47:    */   {
/*  48:128 */     if (this.nextObjectSet) {
/*  49:129 */       throw new IllegalStateException("remove() cannot be called");
/*  50:    */     }
/*  51:131 */     this.iterator.remove();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Iterator getIterator()
/*  55:    */   {
/*  56:141 */     return this.iterator;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIterator(Iterator iterator)
/*  60:    */   {
/*  61:151 */     this.iterator = iterator;
/*  62:152 */     this.nextObject = null;
/*  63:153 */     this.nextObjectSet = false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Predicate getPredicate()
/*  67:    */   {
/*  68:163 */     return this.predicate;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setPredicate(Predicate predicate)
/*  72:    */   {
/*  73:172 */     this.predicate = predicate;
/*  74:173 */     this.nextObject = null;
/*  75:174 */     this.nextObjectSet = false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private boolean setNextObject()
/*  79:    */   {
/*  80:183 */     while (this.iterator.hasNext())
/*  81:    */     {
/*  82:184 */       Object object = this.iterator.next();
/*  83:185 */       if (this.predicate.evaluate(object))
/*  84:    */       {
/*  85:186 */         this.nextObject = object;
/*  86:187 */         this.nextObjectSet = true;
/*  87:188 */         return true;
/*  88:    */       }
/*  89:    */     }
/*  90:191 */     return false;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.FilterIterator
 * JD-Core Version:    0.7.0.1
 */