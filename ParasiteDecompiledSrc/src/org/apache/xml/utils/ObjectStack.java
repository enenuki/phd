/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.EmptyStackException;
/*   4:    */ 
/*   5:    */ public class ObjectStack
/*   6:    */   extends ObjectVector
/*   7:    */ {
/*   8:    */   public ObjectStack() {}
/*   9:    */   
/*  10:    */   public ObjectStack(int blocksize)
/*  11:    */   {
/*  12: 53 */     super(blocksize);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public ObjectStack(ObjectStack v)
/*  16:    */   {
/*  17: 63 */     super(v);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Object push(Object i)
/*  21:    */   {
/*  22: 75 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  23:    */     {
/*  24: 77 */       this.m_mapSize += this.m_blocksize;
/*  25:    */       
/*  26: 79 */       Object[] newMap = new Object[this.m_mapSize];
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
/*  39:    */   public Object pop()
/*  40:    */   {
/*  41:101 */     Object val = this.m_map[(--this.m_firstFree)];
/*  42:102 */     this.m_map[this.m_firstFree] = null;
/*  43:    */     
/*  44:104 */     return val;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void quickPop(int n)
/*  48:    */   {
/*  49:113 */     this.m_firstFree -= n;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object peek()
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56:126 */       return this.m_map[(this.m_firstFree - 1)];
/*  57:    */     }
/*  58:    */     catch (ArrayIndexOutOfBoundsException e)
/*  59:    */     {
/*  60:130 */       throw new EmptyStackException();
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object peek(int n)
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:144 */       return this.m_map[(this.m_firstFree - (1 + n))];
/*  69:    */     }
/*  70:    */     catch (ArrayIndexOutOfBoundsException e)
/*  71:    */     {
/*  72:148 */       throw new EmptyStackException();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setTop(Object val)
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:162 */       this.m_map[(this.m_firstFree - 1)] = val;
/*  81:    */     }
/*  82:    */     catch (ArrayIndexOutOfBoundsException e)
/*  83:    */     {
/*  84:166 */       throw new EmptyStackException();
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean empty()
/*  89:    */   {
/*  90:179 */     return this.m_firstFree == 0;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int search(Object o)
/*  94:    */   {
/*  95:194 */     int i = lastIndexOf(o);
/*  96:196 */     if (i >= 0) {
/*  97:198 */       return size() - i;
/*  98:    */     }
/*  99:201 */     return -1;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object clone()
/* 103:    */     throws CloneNotSupportedException
/* 104:    */   {
/* 105:212 */     return (ObjectStack)super.clone();
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.ObjectStack
 * JD-Core Version:    0.7.0.1
 */