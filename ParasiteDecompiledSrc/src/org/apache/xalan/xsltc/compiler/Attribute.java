/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  4:   */ 
/*  5:   */ final class Attribute
/*  6:   */   extends Instruction
/*  7:   */ {
/*  8:   */   private QName _name;
/*  9:   */   
/* 10:   */   public void display(int indent)
/* 11:   */   {
/* 12:34 */     indent(indent);
/* 13:35 */     Util.println("Attribute " + this._name);
/* 14:36 */     displayContents(indent + 4);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void parseContents(Parser parser)
/* 18:   */   {
/* 19:40 */     this._name = parser.getQName(getAttribute("name"));
/* 20:41 */     parseChildren(parser);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Attribute
 * JD-Core Version:    0.7.0.1
 */