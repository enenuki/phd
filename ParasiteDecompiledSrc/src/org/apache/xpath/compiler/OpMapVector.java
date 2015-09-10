/*   1:    */ package org.apache.xpath.compiler;
/*   2:    */ 
/*   3:    */ public class OpMapVector
/*   4:    */ {
/*   5:    */   protected int m_blocksize;
/*   6:    */   protected int[] m_map;
/*   7: 40 */   protected int m_lengthPos = 0;
/*   8:    */   protected int m_mapSize;
/*   9:    */   
/*  10:    */   public OpMapVector(int blocksize, int increaseSize, int lengthPos)
/*  11:    */   {
/*  12: 53 */     this.m_blocksize = increaseSize;
/*  13: 54 */     this.m_mapSize = blocksize;
/*  14: 55 */     this.m_lengthPos = lengthPos;
/*  15: 56 */     this.m_map = new int[blocksize];
/*  16:    */   }
/*  17:    */   
/*  18:    */   public final int elementAt(int i)
/*  19:    */   {
/*  20: 68 */     return this.m_map[i];
/*  21:    */   }
/*  22:    */   
/*  23:    */   public final void setElementAt(int value, int index)
/*  24:    */   {
/*  25: 83 */     if (index >= this.m_mapSize)
/*  26:    */     {
/*  27: 85 */       int oldSize = this.m_mapSize;
/*  28:    */       
/*  29: 87 */       this.m_mapSize += this.m_blocksize;
/*  30:    */       
/*  31: 89 */       int[] newMap = new int[this.m_mapSize];
/*  32:    */       
/*  33: 91 */       System.arraycopy(this.m_map, 0, newMap, 0, oldSize);
/*  34:    */       
/*  35: 93 */       this.m_map = newMap;
/*  36:    */     }
/*  37: 96 */     this.m_map[index] = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final void setToSize(int size)
/*  41:    */   {
/*  42:107 */     int[] newMap = new int[size];
/*  43:    */     
/*  44:109 */     System.arraycopy(this.m_map, 0, newMap, 0, this.m_map[this.m_lengthPos]);
/*  45:    */     
/*  46:111 */     this.m_mapSize = size;
/*  47:112 */     this.m_map = newMap;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.OpMapVector
 * JD-Core Version:    0.7.0.1
 */