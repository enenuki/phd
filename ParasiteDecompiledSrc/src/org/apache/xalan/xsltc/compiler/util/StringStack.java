/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import java.util.Stack;
/*  4:   */ 
/*  5:   */ public final class StringStack
/*  6:   */   extends Stack
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = -1506910875640317898L;
/*  9:   */   
/* 10:   */   public String peekString()
/* 11:   */   {
/* 12:33 */     return (String)super.peek();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String popString()
/* 16:   */   {
/* 17:37 */     return (String)super.pop();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String pushString(String val)
/* 21:   */   {
/* 22:41 */     return (String)super.push(val);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.StringStack
 * JD-Core Version:    0.7.0.1
 */