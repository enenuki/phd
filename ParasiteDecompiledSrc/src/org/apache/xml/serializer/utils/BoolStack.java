/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ public final class BoolStack
/*   4:    */ {
/*   5:    */   private boolean[] m_values;
/*   6:    */   private int m_allocatedSize;
/*   7:    */   private int m_index;
/*   8:    */   
/*   9:    */   public BoolStack()
/*  10:    */   {
/*  11: 55 */     this(32);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public BoolStack(int size)
/*  15:    */   {
/*  16: 66 */     this.m_allocatedSize = size;
/*  17: 67 */     this.m_values = new boolean[size];
/*  18: 68 */     this.m_index = -1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public final int size()
/*  22:    */   {
/*  23: 78 */     return this.m_index + 1;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public final void clear()
/*  27:    */   {
/*  28: 87 */     this.m_index = -1;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final boolean push(boolean val)
/*  32:    */   {
/*  33:100 */     if (this.m_index == this.m_allocatedSize - 1) {
/*  34:101 */       grow();
/*  35:    */     }
/*  36:103 */     return this.m_values[(++this.m_index)] = val;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final boolean pop()
/*  40:    */   {
/*  41:115 */     return this.m_values[(this.m_index--)];
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final boolean popAndTop()
/*  45:    */   {
/*  46:128 */     this.m_index -= 1;
/*  47:    */     
/*  48:130 */     return this.m_index >= 0 ? this.m_values[this.m_index] : false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final void setTop(boolean b)
/*  52:    */   {
/*  53:141 */     this.m_values[this.m_index] = b;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final boolean peek()
/*  57:    */   {
/*  58:153 */     return this.m_values[this.m_index];
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final boolean peekOrFalse()
/*  62:    */   {
/*  63:164 */     return this.m_index > -1 ? this.m_values[this.m_index] : false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final boolean peekOrTrue()
/*  67:    */   {
/*  68:175 */     return this.m_index > -1 ? this.m_values[this.m_index] : true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isEmpty()
/*  72:    */   {
/*  73:186 */     return this.m_index == -1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void grow()
/*  77:    */   {
/*  78:196 */     this.m_allocatedSize *= 2;
/*  79:    */     
/*  80:198 */     boolean[] newVector = new boolean[this.m_allocatedSize];
/*  81:    */     
/*  82:200 */     System.arraycopy(this.m_values, 0, newVector, 0, this.m_index + 1);
/*  83:    */     
/*  84:202 */     this.m_values = newVector;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.BoolStack
 * JD-Core Version:    0.7.0.1
 */