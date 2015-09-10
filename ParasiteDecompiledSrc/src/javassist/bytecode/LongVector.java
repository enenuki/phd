/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ final class LongVector
/*  4:   */ {
/*  5:   */   static final int ASIZE = 128;
/*  6:   */   static final int ABITS = 7;
/*  7:   */   static final int VSIZE = 8;
/*  8:   */   private ConstInfo[][] objects;
/*  9:   */   private int elements;
/* 10:   */   
/* 11:   */   public LongVector()
/* 12:   */   {
/* 13:26 */     this.objects = new ConstInfo[8][];
/* 14:27 */     this.elements = 0;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public LongVector(int initialSize)
/* 18:   */   {
/* 19:31 */     int vsize = (initialSize >> 7 & 0xFFFFFFF8) + 8;
/* 20:32 */     this.objects = new ConstInfo[vsize][];
/* 21:33 */     this.elements = 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int size()
/* 25:   */   {
/* 26:36 */     return this.elements;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int capacity()
/* 30:   */   {
/* 31:38 */     return this.objects.length * 128;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ConstInfo elementAt(int i)
/* 35:   */   {
/* 36:41 */     if ((i < 0) || (this.elements <= i)) {
/* 37:42 */       return null;
/* 38:   */     }
/* 39:44 */     return this.objects[(i >> 7)][(i & 0x7F)];
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void addElement(ConstInfo value)
/* 43:   */   {
/* 44:48 */     int nth = this.elements >> 7;
/* 45:49 */     int offset = this.elements & 0x7F;
/* 46:50 */     int len = this.objects.length;
/* 47:51 */     if (nth >= len)
/* 48:   */     {
/* 49:52 */       ConstInfo[][] newObj = new ConstInfo[len + 8][];
/* 50:53 */       System.arraycopy(this.objects, 0, newObj, 0, len);
/* 51:54 */       this.objects = newObj;
/* 52:   */     }
/* 53:57 */     if (this.objects[nth] == null) {
/* 54:58 */       this.objects[nth] = new ConstInfo[''];
/* 55:   */     }
/* 56:60 */     this.objects[nth][offset] = value;
/* 57:61 */     this.elements += 1;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.LongVector
 * JD-Core Version:    0.7.0.1
 */