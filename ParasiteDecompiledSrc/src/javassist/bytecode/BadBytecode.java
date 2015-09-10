/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ public class BadBytecode
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public BadBytecode(int opcode)
/*  7:   */   {
/*  8:23 */     super("bytecode " + opcode);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public BadBytecode(String msg)
/* 12:   */   {
/* 13:27 */     super(msg);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public BadBytecode(String msg, Throwable cause)
/* 17:   */   {
/* 18:31 */     super(msg, cause);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.BadBytecode
 * JD-Core Version:    0.7.0.1
 */