/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.Hashtable;
/*  5:   */ 
/*  6:   */ public class IndexedVector
/*  7:   */ {
/*  8:   */   protected Vector elements;
/*  9:   */   protected Hashtable index;
/* 10:   */   
/* 11:   */   public IndexedVector()
/* 12:   */   {
/* 13:29 */     this.elements = new Vector(10);
/* 14:30 */     this.index = new Hashtable(10);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public IndexedVector(int paramInt)
/* 18:   */   {
/* 19:38 */     this.elements = new Vector(paramInt);
/* 20:39 */     this.index = new Hashtable(paramInt);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public synchronized void appendElement(Object paramObject1, Object paramObject2)
/* 24:   */   {
/* 25:43 */     this.elements.appendElement(paramObject2);
/* 26:44 */     this.index.put(paramObject1, paramObject2);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object elementAt(int paramInt)
/* 30:   */   {
/* 31:54 */     return this.elements.elementAt(paramInt);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Enumeration elements()
/* 35:   */   {
/* 36:58 */     return this.elements.elements();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Object getElement(Object paramObject)
/* 40:   */   {
/* 41:62 */     Object localObject = this.index.get(paramObject);
/* 42:63 */     return localObject;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public synchronized boolean removeElement(Object paramObject)
/* 46:   */   {
/* 47:68 */     Object localObject = this.index.get(paramObject);
/* 48:69 */     if (localObject == null) {
/* 49:70 */       return false;
/* 50:   */     }
/* 51:72 */     this.index.remove(paramObject);
/* 52:73 */     this.elements.removeElement(localObject);
/* 53:74 */     return false;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int size()
/* 57:   */   {
/* 58:78 */     return this.elements.size();
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.IndexedVector
 * JD-Core Version:    0.7.0.1
 */