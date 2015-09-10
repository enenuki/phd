/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.apache.commons.collections.Transformer;
/*   5:    */ 
/*   6:    */ public class TransformIterator
/*   7:    */   implements Iterator
/*   8:    */ {
/*   9:    */   private Iterator iterator;
/*  10:    */   private Transformer transformer;
/*  11:    */   
/*  12:    */   public TransformIterator() {}
/*  13:    */   
/*  14:    */   public TransformIterator(Iterator iterator)
/*  15:    */   {
/*  16: 57 */     this.iterator = iterator;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public TransformIterator(Iterator iterator, Transformer transformer)
/*  20:    */   {
/*  21: 70 */     this.iterator = iterator;
/*  22: 71 */     this.transformer = transformer;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean hasNext()
/*  26:    */   {
/*  27: 76 */     return this.iterator.hasNext();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object next()
/*  31:    */   {
/*  32: 88 */     return transform(this.iterator.next());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void remove()
/*  36:    */   {
/*  37: 92 */     this.iterator.remove();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Iterator getIterator()
/*  41:    */   {
/*  42:102 */     return this.iterator;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIterator(Iterator iterator)
/*  46:    */   {
/*  47:112 */     this.iterator = iterator;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Transformer getTransformer()
/*  51:    */   {
/*  52:122 */     return this.transformer;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setTransformer(Transformer transformer)
/*  56:    */   {
/*  57:132 */     this.transformer = transformer;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Object transform(Object source)
/*  61:    */   {
/*  62:144 */     if (this.transformer != null) {
/*  63:145 */       return this.transformer.transform(source);
/*  64:    */     }
/*  65:147 */     return source;
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.TransformIterator
 * JD-Core Version:    0.7.0.1
 */