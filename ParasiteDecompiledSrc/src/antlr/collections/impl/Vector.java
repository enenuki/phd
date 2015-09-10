/*   1:    */ package antlr.collections.impl;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ 
/*   6:    */ public class Vector
/*   7:    */   implements Cloneable
/*   8:    */ {
/*   9:    */   protected Object[] data;
/*  10: 17 */   protected int lastElement = -1;
/*  11:    */   
/*  12:    */   public Vector()
/*  13:    */   {
/*  14: 20 */     this(10);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Vector(int paramInt)
/*  18:    */   {
/*  19: 24 */     this.data = new Object[paramInt];
/*  20:    */   }
/*  21:    */   
/*  22:    */   public synchronized void appendElement(Object paramObject)
/*  23:    */   {
/*  24: 28 */     ensureCapacity(this.lastElement + 2);
/*  25: 29 */     this.data[(++this.lastElement)] = paramObject;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int capacity()
/*  29:    */   {
/*  30: 36 */     return this.data.length;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object clone()
/*  34:    */   {
/*  35: 40 */     Vector localVector = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 42 */       localVector = (Vector)super.clone();
/*  39:    */     }
/*  40:    */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*  41:    */     {
/*  42: 45 */       System.err.println("cannot clone Vector.super");
/*  43: 46 */       return null;
/*  44:    */     }
/*  45: 48 */     localVector.data = new Object[size()];
/*  46: 49 */     System.arraycopy(this.data, 0, localVector.data, 0, size());
/*  47: 50 */     return localVector;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public synchronized Object elementAt(int paramInt)
/*  51:    */   {
/*  52: 60 */     if (paramInt >= this.data.length) {
/*  53: 61 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.data.length);
/*  54:    */     }
/*  55: 63 */     if (paramInt < 0) {
/*  56: 64 */       throw new ArrayIndexOutOfBoundsException(paramInt + " < 0 ");
/*  57:    */     }
/*  58: 66 */     return this.data[paramInt];
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized Enumeration elements()
/*  62:    */   {
/*  63: 70 */     return new VectorEnumerator(this);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public synchronized void ensureCapacity(int paramInt)
/*  67:    */   {
/*  68: 74 */     if (paramInt + 1 > this.data.length)
/*  69:    */     {
/*  70: 75 */       Object[] arrayOfObject = this.data;
/*  71: 76 */       int i = this.data.length * 2;
/*  72: 77 */       if (paramInt + 1 > i) {
/*  73: 78 */         i = paramInt + 1;
/*  74:    */       }
/*  75: 80 */       this.data = new Object[i];
/*  76: 81 */       System.arraycopy(arrayOfObject, 0, this.data, 0, arrayOfObject.length);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized boolean removeElement(Object paramObject)
/*  81:    */   {
/*  82: 88 */     for (int i = 0; (i <= this.lastElement) && (this.data[i] != paramObject); i++) {}
/*  83: 91 */     if (i <= this.lastElement)
/*  84:    */     {
/*  85: 92 */       this.data[i] = null;
/*  86: 93 */       int j = this.lastElement - i;
/*  87: 94 */       if (j > 0) {
/*  88: 95 */         System.arraycopy(this.data, i + 1, this.data, i, j);
/*  89:    */       }
/*  90: 97 */       this.lastElement -= 1;
/*  91: 98 */       return true;
/*  92:    */     }
/*  93:101 */     return false;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public synchronized void setElementAt(Object paramObject, int paramInt)
/*  97:    */   {
/*  98:106 */     if (paramInt >= this.data.length) {
/*  99:107 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.data.length);
/* 100:    */     }
/* 101:109 */     this.data[paramInt] = paramObject;
/* 102:111 */     if (paramInt > this.lastElement) {
/* 103:112 */       this.lastElement = paramInt;
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int size()
/* 108:    */   {
/* 109:119 */     return this.lastElement + 1;
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.Vector
 * JD-Core Version:    0.7.0.1
 */