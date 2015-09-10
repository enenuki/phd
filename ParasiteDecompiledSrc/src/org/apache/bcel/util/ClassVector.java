/*  1:   */ package org.apache.bcel.util;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.bcel.classfile.JavaClass;
/*  5:   */ 
/*  6:   */ public class ClassVector
/*  7:   */ {
/*  8:68 */   protected ArrayList vec = new ArrayList();
/*  9:   */   
/* 10:   */   public void addElement(JavaClass clazz)
/* 11:   */   {
/* 12:70 */     this.vec.add(clazz);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public JavaClass elementAt(int index)
/* 16:   */   {
/* 17:71 */     return (JavaClass)this.vec.get(index);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void removeElementAt(int index)
/* 21:   */   {
/* 22:72 */     this.vec.remove(index);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public JavaClass[] toArray()
/* 26:   */   {
/* 27:75 */     JavaClass[] classes = new JavaClass[this.vec.size()];
/* 28:76 */     this.vec.toArray(classes);
/* 29:77 */     return classes;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassVector
 * JD-Core Version:    0.7.0.1
 */