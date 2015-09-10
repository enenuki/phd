/*  1:   */ package org.apache.bcel.verifier.structurals;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.InstructionHandle;
/*  4:   */ import org.apache.bcel.generic.ObjectType;
/*  5:   */ 
/*  6:   */ public class ExceptionHandler
/*  7:   */ {
/*  8:   */   private ObjectType catchtype;
/*  9:   */   private InstructionHandle handlerpc;
/* 10:   */   
/* 11:   */   ExceptionHandler(ObjectType catch_type, InstructionHandle handler_pc)
/* 12:   */   {
/* 13:76 */     this.catchtype = catch_type;
/* 14:77 */     this.handlerpc = handler_pc;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ObjectType getExceptionType()
/* 18:   */   {
/* 19:84 */     return this.catchtype;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public InstructionHandle getHandlerStart()
/* 23:   */   {
/* 24:91 */     return this.handlerpc;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.ExceptionHandler
 * JD-Core Version:    0.7.0.1
 */