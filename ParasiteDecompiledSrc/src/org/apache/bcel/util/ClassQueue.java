/*  1:   */ package org.apache.bcel.util;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.bcel.classfile.JavaClass;
/*  5:   */ 
/*  6:   */ public class ClassQueue
/*  7:   */ {
/*  8:68 */   protected int left = 0;
/*  9:69 */   private ArrayList vec = new ArrayList();
/* 10:   */   
/* 11:   */   public void enqueue(JavaClass clazz)
/* 12:   */   {
/* 13:71 */     this.vec.add(clazz);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public JavaClass dequeue()
/* 17:   */   {
/* 18:73 */     JavaClass clazz = (JavaClass)this.vec.get(this.left);
/* 19:74 */     this.vec.remove(this.left++);
/* 20:75 */     return clazz;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean empty()
/* 24:   */   {
/* 25:77 */     return this.vec.size() <= this.left;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassQueue
 * JD-Core Version:    0.7.0.1
 */