/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import org.apache.bcel.generic.CodeExceptionGen;
/*   7:    */ import org.apache.bcel.generic.InstructionHandle;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ 
/*  10:    */ public class ExceptionHandlers
/*  11:    */ {
/*  12:    */   private Hashtable exceptionhandlers;
/*  13:    */   
/*  14:    */   public ExceptionHandlers(MethodGen mg)
/*  15:    */   {
/*  16: 78 */     this.exceptionhandlers = new Hashtable();
/*  17: 79 */     CodeExceptionGen[] cegs = mg.getExceptionHandlers();
/*  18: 80 */     for (int i = 0; i < cegs.length; i++)
/*  19:    */     {
/*  20: 81 */       ExceptionHandler eh = new ExceptionHandler(cegs[i].getCatchType(), cegs[i].getHandlerPC());
/*  21: 82 */       for (InstructionHandle ih = cegs[i].getStartPC(); ih != cegs[i].getEndPC().getNext(); ih = ih.getNext())
/*  22:    */       {
/*  23: 84 */         HashSet hs = (HashSet)this.exceptionhandlers.get(ih);
/*  24: 85 */         if (hs == null)
/*  25:    */         {
/*  26: 86 */           hs = new HashSet();
/*  27: 87 */           this.exceptionhandlers.put(ih, hs);
/*  28:    */         }
/*  29: 89 */         hs.add(eh);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ExceptionHandler[] getExceptionHandlers(InstructionHandle ih)
/*  35:    */   {
/*  36: 99 */     HashSet hs = (HashSet)this.exceptionhandlers.get(ih);
/*  37:100 */     if (hs == null) {
/*  38:100 */       return new ExceptionHandler[0];
/*  39:    */     }
/*  40:102 */     ExceptionHandler[] ret = new ExceptionHandler[hs.size()];
/*  41:103 */     return (ExceptionHandler[])hs.toArray(ret);
/*  42:    */   }
/*  43:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.ExceptionHandlers
 * JD-Core Version:    0.7.0.1
 */