/*  1:   */ package javassist.bytecode.analysis;
/*  2:   */ 
/*  3:   */ import javassist.bytecode.CodeIterator;
/*  4:   */ import javassist.bytecode.Opcode;
/*  5:   */ 
/*  6:   */ public class Util
/*  7:   */   implements Opcode
/*  8:   */ {
/*  9:   */   public static int getJumpTarget(int pos, CodeIterator iter)
/* 10:   */   {
/* 11:27 */     int opcode = iter.byteAt(pos);
/* 12:28 */     pos += ((opcode == 201) || (opcode == 200) ? iter.s32bitAt(pos + 1) : iter.s16bitAt(pos + 1));
/* 13:29 */     return pos;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static boolean isJumpInstruction(int opcode)
/* 17:   */   {
/* 18:33 */     return ((opcode >= 153) && (opcode <= 168)) || (opcode == 198) || (opcode == 199) || (opcode == 201) || (opcode == 200);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static boolean isGoto(int opcode)
/* 22:   */   {
/* 23:37 */     return (opcode == 167) || (opcode == 200);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static boolean isJsr(int opcode)
/* 27:   */   {
/* 28:41 */     return (opcode == 168) || (opcode == 201);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean isReturn(int opcode)
/* 32:   */   {
/* 33:45 */     return (opcode >= 172) && (opcode <= 177);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Util
 * JD-Core Version:    0.7.0.1
 */