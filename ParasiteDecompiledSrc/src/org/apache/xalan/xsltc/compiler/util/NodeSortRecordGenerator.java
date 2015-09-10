/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.Instruction;
/*  5:   */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  6:   */ 
/*  7:   */ public final class NodeSortRecordGenerator
/*  8:   */   extends ClassGenerator
/*  9:   */ {
/* 10:   */   private static final int TRANSLET_INDEX = 4;
/* 11:   */   private final Instruction _aloadTranslet;
/* 12:   */   
/* 13:   */   public NodeSortRecordGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet)
/* 14:   */   {
/* 15:42 */     super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
/* 16:   */     
/* 17:44 */     this._aloadTranslet = new ALOAD(4);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Instruction loadTranslet()
/* 21:   */   {
/* 22:52 */     return this._aloadTranslet;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean isExternal()
/* 26:   */   {
/* 27:60 */     return true;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NodeSortRecordGenerator
 * JD-Core Version:    0.7.0.1
 */