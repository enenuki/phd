/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.Instruction;
/*  5:   */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  6:   */ 
/*  7:   */ public final class FilterGenerator
/*  8:   */   extends ClassGenerator
/*  9:   */ {
/* 10:36 */   private static int TRANSLET_INDEX = 5;
/* 11:   */   private final Instruction _aloadTranslet;
/* 12:   */   
/* 13:   */   public FilterGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet)
/* 14:   */   {
/* 15:43 */     super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
/* 16:   */     
/* 17:   */ 
/* 18:46 */     this._aloadTranslet = new ALOAD(TRANSLET_INDEX);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final Instruction loadTranslet()
/* 22:   */   {
/* 23:54 */     return this._aloadTranslet;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isExternal()
/* 27:   */   {
/* 28:62 */     return true;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.FilterGenerator
 * JD-Core Version:    0.7.0.1
 */