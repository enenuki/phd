/*  1:   */ package java_cup.runtime;
/*  2:   */ 
/*  3:   */ public class Symbol
/*  4:   */ {
/*  5:   */   public int sym;
/*  6:   */   public int parse_state;
/*  7:   */   
/*  8:   */   public Symbol(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
/*  9:   */   {
/* 10:30 */     this(paramInt1);
/* 11:31 */     this.left = paramInt2;
/* 12:32 */     this.right = paramInt3;
/* 13:33 */     this.value = paramObject;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Symbol(int paramInt, Object paramObject)
/* 17:   */   {
/* 18:41 */     this(paramInt, -1, -1, paramObject);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Symbol(int paramInt1, int paramInt2, int paramInt3)
/* 22:   */   {
/* 23:49 */     this(paramInt1, paramInt2, paramInt3, null);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Symbol(int paramInt)
/* 27:   */   {
/* 28:57 */     this(paramInt, -1);
/* 29:58 */     this.left = -1;
/* 30:59 */     this.right = -1;
/* 31:60 */     this.value = null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   Symbol(int paramInt1, int paramInt2)
/* 35:   */   {
/* 36:68 */     this.sym = paramInt1;
/* 37:69 */     this.parse_state = paramInt2;
/* 38:   */   }
/* 39:   */   
/* 40:86 */   boolean used_by_parser = false;
/* 41:   */   public int left;
/* 42:   */   public int right;
/* 43:   */   public Object value;
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:98 */     return "#" + this.sym;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     java_cup.runtime.Symbol
 * JD-Core Version:    0.7.0.1
 */