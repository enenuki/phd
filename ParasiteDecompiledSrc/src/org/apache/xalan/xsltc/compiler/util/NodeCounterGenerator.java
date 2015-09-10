/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.Instruction;
/*  5:   */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  6:   */ 
/*  7:   */ public final class NodeCounterGenerator
/*  8:   */   extends ClassGenerator
/*  9:   */ {
/* 10:   */   private Instruction _aloadTranslet;
/* 11:   */   
/* 12:   */   public NodeCounterGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet)
/* 13:   */   {
/* 14:45 */     super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setTransletIndex(int index)
/* 18:   */   {
/* 19:54 */     this._aloadTranslet = new ALOAD(index);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Instruction loadTranslet()
/* 23:   */   {
/* 24:63 */     return this._aloadTranslet;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isExternal()
/* 28:   */   {
/* 29:71 */     return true;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NodeCounterGenerator
 * JD-Core Version:    0.7.0.1
 */