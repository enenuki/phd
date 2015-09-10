/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ abstract class AttributeValue
/*  4:   */   extends Expression
/*  5:   */ {
/*  6:   */   public static final AttributeValue create(SyntaxTreeNode parent, String text, Parser parser)
/*  7:   */   {
/*  8:   */     AttributeValue result;
/*  9:35 */     if (text.indexOf('{') != -1)
/* 10:   */     {
/* 11:36 */       result = new AttributeValueTemplate(text, parser, parent);
/* 12:   */     }
/* 13:38 */     else if (text.indexOf('}') != -1)
/* 14:   */     {
/* 15:39 */       result = new AttributeValueTemplate(text, parser, parent);
/* 16:   */     }
/* 17:   */     else
/* 18:   */     {
/* 19:42 */       result = new SimpleAttributeValue(text);
/* 20:43 */       result.setParser(parser);
/* 21:44 */       result.setParent(parent);
/* 22:   */     }
/* 23:46 */     return result;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AttributeValue
 * JD-Core Version:    0.7.0.1
 */