/*   1:    */ package org.cyberneko.html;
/*   2:    */ 
/*   3:    */ public abstract interface HTMLEventInfo
/*   4:    */ {
/*   5:    */   public abstract int getBeginLineNumber();
/*   6:    */   
/*   7:    */   public abstract int getBeginColumnNumber();
/*   8:    */   
/*   9:    */   public abstract int getBeginCharacterOffset();
/*  10:    */   
/*  11:    */   public abstract int getEndLineNumber();
/*  12:    */   
/*  13:    */   public abstract int getEndColumnNumber();
/*  14:    */   
/*  15:    */   public abstract int getEndCharacterOffset();
/*  16:    */   
/*  17:    */   public abstract boolean isSynthesized();
/*  18:    */   
/*  19:    */   public static class SynthesizedItem
/*  20:    */     implements HTMLEventInfo
/*  21:    */   {
/*  22:    */     public int getBeginLineNumber()
/*  23:    */     {
/*  24: 74 */       return -1;
/*  25:    */     }
/*  26:    */     
/*  27:    */     public int getBeginColumnNumber()
/*  28:    */     {
/*  29: 79 */       return -1;
/*  30:    */     }
/*  31:    */     
/*  32:    */     public int getBeginCharacterOffset()
/*  33:    */     {
/*  34: 84 */       return -1;
/*  35:    */     }
/*  36:    */     
/*  37:    */     public int getEndLineNumber()
/*  38:    */     {
/*  39: 89 */       return -1;
/*  40:    */     }
/*  41:    */     
/*  42:    */     public int getEndColumnNumber()
/*  43:    */     {
/*  44: 94 */       return -1;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public int getEndCharacterOffset()
/*  48:    */     {
/*  49: 99 */       return -1;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public boolean isSynthesized()
/*  53:    */     {
/*  54:106 */       return true;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public String toString()
/*  58:    */     {
/*  59:115 */       return "synthesized";
/*  60:    */     }
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLEventInfo
 * JD-Core Version:    0.7.0.1
 */