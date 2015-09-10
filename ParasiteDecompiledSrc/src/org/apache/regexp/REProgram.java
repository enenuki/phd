/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ public class REProgram
/*   4:    */ {
/*   5:    */   static final int OPT_HASBACKREFS = 1;
/*   6:    */   char[] instruction;
/*   7:    */   int lenInstruction;
/*   8:    */   char[] prefix;
/*   9:    */   int flags;
/*  10:    */   
/*  11:    */   public REProgram(char[] paramArrayOfChar)
/*  12:    */   {
/*  13: 90 */     this(paramArrayOfChar, paramArrayOfChar.length);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public REProgram(char[] paramArrayOfChar, int paramInt)
/*  17:    */   {
/*  18:100 */     setInstructions(paramArrayOfChar, paramInt);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public char[] getInstructions()
/*  22:    */   {
/*  23:112 */     if (this.lenInstruction != 0)
/*  24:    */     {
/*  25:115 */       char[] arrayOfChar = new char[this.lenInstruction];
/*  26:116 */       System.arraycopy(this.instruction, 0, arrayOfChar, 0, this.lenInstruction);
/*  27:117 */       return arrayOfChar;
/*  28:    */     }
/*  29:119 */     return null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setInstructions(char[] paramArrayOfChar, int paramInt)
/*  33:    */   {
/*  34:135 */     this.instruction = paramArrayOfChar;
/*  35:136 */     this.lenInstruction = paramInt;
/*  36:    */     
/*  37:    */ 
/*  38:139 */     this.flags = 0;
/*  39:140 */     this.prefix = null;
/*  40:143 */     if ((paramArrayOfChar != null) && (paramInt != 0))
/*  41:    */     {
/*  42:146 */       if ((paramInt >= 3) && (paramArrayOfChar[0] == '|'))
/*  43:    */       {
/*  44:149 */         i = paramArrayOfChar[2];
/*  45:150 */         if (paramArrayOfChar[i] == 'E') {
/*  46:153 */           if ((paramInt >= 6) && (paramArrayOfChar[3] == 'A'))
/*  47:    */           {
/*  48:156 */             int j = paramArrayOfChar[4];
/*  49:157 */             this.prefix = new char[j];
/*  50:158 */             System.arraycopy(paramArrayOfChar, 6, this.prefix, 0, j);
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54:166 */       for (int i = 0; i < paramInt; i += 3) {
/*  55:168 */         switch (paramArrayOfChar[i])
/*  56:    */         {
/*  57:    */         case '[': 
/*  58:171 */           i += paramArrayOfChar[(i + 1)] * '\002';
/*  59:172 */           break;
/*  60:    */         case 'A': 
/*  61:175 */           i += paramArrayOfChar[(i + 1)];
/*  62:176 */           break;
/*  63:    */         case '#': 
/*  64:179 */           this.flags |= 0x1; return;
/*  65:    */         }
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.REProgram
 * JD-Core Version:    0.7.0.1
 */