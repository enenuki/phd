/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public final class SWITCH
/*   4:    */   implements CompoundInstruction
/*   5:    */ {
/*   6:    */   private int[] match;
/*   7:    */   private InstructionHandle[] targets;
/*   8:    */   private Select instruction;
/*   9:    */   private int match_length;
/*  10:    */   
/*  11:    */   public SWITCH(int[] match, InstructionHandle[] targets, InstructionHandle target, int max_gap)
/*  12:    */   {
/*  13: 88 */     this.match = ((int[])match.clone());
/*  14: 89 */     this.targets = ((InstructionHandle[])targets.clone());
/*  15: 91 */     if ((this.match_length = match.length) < 2)
/*  16:    */     {
/*  17: 92 */       this.instruction = new TABLESWITCH(match, targets, target);
/*  18:    */     }
/*  19:    */     else
/*  20:    */     {
/*  21: 94 */       sort(0, this.match_length - 1);
/*  22: 96 */       if (matchIsOrdered(max_gap))
/*  23:    */       {
/*  24: 97 */         fillup(max_gap, target);
/*  25:    */         
/*  26: 99 */         this.instruction = new TABLESWITCH(this.match, this.targets, target);
/*  27:    */       }
/*  28:    */       else
/*  29:    */       {
/*  30:102 */         this.instruction = new LOOKUPSWITCH(this.match, this.targets, target);
/*  31:    */       }
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public SWITCH(int[] match, InstructionHandle[] targets, InstructionHandle target)
/*  36:    */   {
/*  37:108 */     this(match, targets, target, 1);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private final void fillup(int max_gap, InstructionHandle target)
/*  41:    */   {
/*  42:112 */     int max_size = this.match_length + this.match_length * max_gap;
/*  43:113 */     int[] m_vec = new int[max_size];
/*  44:114 */     InstructionHandle[] t_vec = new InstructionHandle[max_size];
/*  45:115 */     int count = 1;
/*  46:    */     
/*  47:117 */     m_vec[0] = this.match[0];
/*  48:118 */     t_vec[0] = this.targets[0];
/*  49:120 */     for (int i = 1; i < this.match_length; i++)
/*  50:    */     {
/*  51:121 */       int prev = this.match[(i - 1)];
/*  52:122 */       int gap = this.match[i] - prev;
/*  53:124 */       for (int j = 1; j < gap; j++)
/*  54:    */       {
/*  55:125 */         m_vec[count] = (prev + j);
/*  56:126 */         t_vec[count] = target;
/*  57:127 */         count++;
/*  58:    */       }
/*  59:130 */       m_vec[count] = this.match[i];
/*  60:131 */       t_vec[count] = this.targets[i];
/*  61:132 */       count++;
/*  62:    */     }
/*  63:135 */     this.match = new int[count];
/*  64:136 */     this.targets = new InstructionHandle[count];
/*  65:    */     
/*  66:138 */     System.arraycopy(m_vec, 0, this.match, 0, count);
/*  67:139 */     System.arraycopy(t_vec, 0, this.targets, 0, count);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private final void sort(int l, int r)
/*  71:    */   {
/*  72:146 */     int i = l;int j = r;
/*  73:147 */     int m = this.match[((l + r) / 2)];
/*  74:    */     do
/*  75:    */     {
/*  76:151 */       while (this.match[i] < m) {
/*  77:151 */         i++;
/*  78:    */       }
/*  79:152 */       while (m < this.match[j]) {
/*  80:152 */         j--;
/*  81:    */       }
/*  82:154 */       if (i <= j)
/*  83:    */       {
/*  84:155 */         int h = this.match[i];this.match[i] = this.match[j];this.match[j] = h;
/*  85:156 */         InstructionHandle h2 = this.targets[i];this.targets[i] = this.targets[j];this.targets[j] = h2;
/*  86:157 */         i++;j--;
/*  87:    */       }
/*  88:159 */     } while (i <= j);
/*  89:161 */     if (l < j) {
/*  90:161 */       sort(l, j);
/*  91:    */     }
/*  92:162 */     if (i < r) {
/*  93:162 */       sort(i, r);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private final boolean matchIsOrdered(int max_gap)
/*  98:    */   {
/*  99:169 */     for (int i = 1; i < this.match_length; i++) {
/* 100:170 */       if (this.match[i] - this.match[(i - 1)] > max_gap) {
/* 101:171 */         return false;
/* 102:    */       }
/* 103:    */     }
/* 104:173 */     return true;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final InstructionList getInstructionList()
/* 108:    */   {
/* 109:177 */     return new InstructionList(this.instruction);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final Instruction getInstruction()
/* 113:    */   {
/* 114:181 */     return this.instruction;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.SWITCH
 * JD-Core Version:    0.7.0.1
 */