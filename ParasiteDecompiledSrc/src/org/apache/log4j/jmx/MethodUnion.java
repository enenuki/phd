/*  1:   */ package org.apache.log4j.jmx;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ 
/*  5:   */ class MethodUnion
/*  6:   */ {
/*  7:   */   Method readMethod;
/*  8:   */   Method writeMethod;
/*  9:   */   
/* 10:   */   MethodUnion(Method readMethod, Method writeMethod)
/* 11:   */   {
/* 12:28 */     this.readMethod = readMethod;
/* 13:29 */     this.writeMethod = writeMethod;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.MethodUnion
 * JD-Core Version:    0.7.0.1
 */