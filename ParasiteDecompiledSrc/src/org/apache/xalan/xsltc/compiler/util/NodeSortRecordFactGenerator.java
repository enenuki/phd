/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  4:   */ 
/*  5:   */ public final class NodeSortRecordFactGenerator
/*  6:   */   extends ClassGenerator
/*  7:   */ {
/*  8:   */   public NodeSortRecordFactGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet)
/*  9:   */   {
/* 10:36 */     super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isExternal()
/* 14:   */   {
/* 15:45 */     return true;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NodeSortRecordFactGenerator
 * JD-Core Version:    0.7.0.1
 */