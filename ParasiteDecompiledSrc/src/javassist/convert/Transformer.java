/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CannotCompileException;
/*  4:   */ import javassist.CtClass;
/*  5:   */ import javassist.bytecode.BadBytecode;
/*  6:   */ import javassist.bytecode.CodeAttribute;
/*  7:   */ import javassist.bytecode.CodeIterator;
/*  8:   */ import javassist.bytecode.ConstPool;
/*  9:   */ import javassist.bytecode.MethodInfo;
/* 10:   */ import javassist.bytecode.Opcode;
/* 11:   */ 
/* 12:   */ public abstract class Transformer
/* 13:   */   implements Opcode
/* 14:   */ {
/* 15:   */   private Transformer next;
/* 16:   */   
/* 17:   */   public Transformer(Transformer t)
/* 18:   */   {
/* 19:37 */     this.next = t;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Transformer getNext()
/* 23:   */   {
/* 24:40 */     return this.next;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void initialize(ConstPool cp, CodeAttribute attr) {}
/* 28:   */   
/* 29:   */   public void initialize(ConstPool cp, CtClass clazz, MethodInfo minfo)
/* 30:   */     throws CannotCompileException
/* 31:   */   {
/* 32:45 */     initialize(cp, minfo.getCodeAttribute());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void clean() {}
/* 36:   */   
/* 37:   */   public abstract int transform(CtClass paramCtClass, int paramInt, CodeIterator paramCodeIterator, ConstPool paramConstPool)
/* 38:   */     throws CannotCompileException, BadBytecode;
/* 39:   */   
/* 40:   */   public int extraLocals()
/* 41:   */   {
/* 42:53 */     return 0;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int extraStack()
/* 46:   */   {
/* 47:55 */     return 0;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.Transformer
 * JD-Core Version:    0.7.0.1
 */