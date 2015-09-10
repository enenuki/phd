/*  1:   */ package org.apache.bcel.util;
/*  2:   */ 
/*  3:   */ import java.util.Stack;
/*  4:   */ import org.apache.bcel.classfile.JavaClass;
/*  5:   */ 
/*  6:   */ public class ClassStack
/*  7:   */ {
/*  8:67 */   private Stack stack = new Stack();
/*  9:   */   
/* 10:   */   public void push(JavaClass clazz)
/* 11:   */   {
/* 12:69 */     this.stack.push(clazz);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public JavaClass pop()
/* 16:   */   {
/* 17:70 */     return (JavaClass)this.stack.pop();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public JavaClass top()
/* 21:   */   {
/* 22:71 */     return (JavaClass)this.stack.peek();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean empty()
/* 26:   */   {
/* 27:72 */     return this.stack.empty();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassStack
 * JD-Core Version:    0.7.0.1
 */