/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.EmptyStackException;
/*   4:    */ 
/*   5:    */ public class IntStack
/*   6:    */   extends IntVector
/*   7:    */ {
/*   8:    */   public IntStack() {}
/*   9:    */   
/*  10:    */   public IntStack(int blocksize)
/*  11:    */   {
/*  12: 53 */     super(blocksize);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public IntStack(IntStack v)
/*  16:    */   {
/*  17: 63 */     super(v);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public int push(int i)
/*  21:    */   {
/*  22: 75 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  23:    */     {
/*  24: 77 */       this.m_mapSize += this.m_blocksize;
/*  25:    */       
/*  26: 79 */       int[] newMap = new int[this.m_mapSize];
/*  27:    */       
/*  28: 81 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  29:    */       
/*  30: 83 */       this.m_map = newMap;
/*  31:    */     }
/*  32: 86 */     this.m_map[this.m_firstFree] = i;
/*  33:    */     
/*  34: 88 */     this.m_firstFree += 1;
/*  35:    */     
/*  36: 90 */     return i;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final int pop()
/*  40:    */   {
/*  41:101 */     return this.m_map[(--this.m_firstFree)];
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final void quickPop(int n)
/*  45:    */   {
/*  46:110 */     this.m_firstFree -= n;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final int peek()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53:123 */       return this.m_map[(this.m_firstFree - 1)];
/*  54:    */     }
/*  55:    */     catch (ArrayIndexOutOfBoundsException e)
/*  56:    */     {
/*  57:127 */       throw new EmptyStackException();
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int peek(int n)
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65:141 */       return this.m_map[(this.m_firstFree - (1 + n))];
/*  66:    */     }
/*  67:    */     catch (ArrayIndexOutOfBoundsException e)
/*  68:    */     {
/*  69:145 */       throw new EmptyStackException();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setTop(int val)
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:159 */       this.m_map[(this.m_firstFree - 1)] = val;
/*  78:    */     }
/*  79:    */     catch (ArrayIndexOutOfBoundsException e)
/*  80:    */     {
/*  81:163 */       throw new EmptyStackException();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean empty()
/*  86:    */   {
/*  87:176 */     return this.m_firstFree == 0;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int search(int o)
/*  91:    */   {
/*  92:191 */     int i = lastIndexOf(o);
/*  93:193 */     if (i >= 0) {
/*  94:195 */       return size() - i;
/*  95:    */     }
/*  96:198 */     return -1;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Object clone()
/* 100:    */     throws CloneNotSupportedException
/* 101:    */   {
/* 102:209 */     return (IntStack)super.clone();
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.IntStack
 * JD-Core Version:    0.7.0.1
 */