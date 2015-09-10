/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public final class BoolStack
/*   4:    */   implements Cloneable
/*   5:    */ {
/*   6:    */   private boolean[] m_values;
/*   7:    */   private int m_allocatedSize;
/*   8:    */   private int m_index;
/*   9:    */   
/*  10:    */   public BoolStack()
/*  11:    */   {
/*  12: 46 */     this(32);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public BoolStack(int size)
/*  16:    */   {
/*  17: 57 */     this.m_allocatedSize = size;
/*  18: 58 */     this.m_values = new boolean[size];
/*  19: 59 */     this.m_index = -1;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public final int size()
/*  23:    */   {
/*  24: 69 */     return this.m_index + 1;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public final void clear()
/*  28:    */   {
/*  29: 78 */     this.m_index = -1;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final boolean push(boolean val)
/*  33:    */   {
/*  34: 91 */     if (this.m_index == this.m_allocatedSize - 1) {
/*  35: 92 */       grow();
/*  36:    */     }
/*  37: 94 */     return this.m_values[(++this.m_index)] = val;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final boolean pop()
/*  41:    */   {
/*  42:106 */     return this.m_values[(this.m_index--)];
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final boolean popAndTop()
/*  46:    */   {
/*  47:119 */     this.m_index -= 1;
/*  48:    */     
/*  49:121 */     return this.m_index >= 0 ? this.m_values[this.m_index] : false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final void setTop(boolean b)
/*  53:    */   {
/*  54:132 */     this.m_values[this.m_index] = b;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final boolean peek()
/*  58:    */   {
/*  59:144 */     return this.m_values[this.m_index];
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final boolean peekOrFalse()
/*  63:    */   {
/*  64:155 */     return this.m_index > -1 ? this.m_values[this.m_index] : false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final boolean peekOrTrue()
/*  68:    */   {
/*  69:166 */     return this.m_index > -1 ? this.m_values[this.m_index] : true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isEmpty()
/*  73:    */   {
/*  74:177 */     return this.m_index == -1;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void grow()
/*  78:    */   {
/*  79:187 */     this.m_allocatedSize *= 2;
/*  80:    */     
/*  81:189 */     boolean[] newVector = new boolean[this.m_allocatedSize];
/*  82:    */     
/*  83:191 */     System.arraycopy(this.m_values, 0, newVector, 0, this.m_index + 1);
/*  84:    */     
/*  85:193 */     this.m_values = newVector;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object clone()
/*  89:    */     throws CloneNotSupportedException
/*  90:    */   {
/*  91:199 */     return super.clone();
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.BoolStack
 * JD-Core Version:    0.7.0.1
 */