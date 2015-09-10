/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Vector;
/*   8:    */ 
/*   9:    */ public class VerifierFactory
/*  10:    */ {
/*  11: 75 */   private static HashMap hashMap = new HashMap();
/*  12: 80 */   private static Vector observers = new Vector();
/*  13:    */   
/*  14:    */   public static Verifier getVerifier(String fully_qualified_classname)
/*  15:    */   {
/*  16: 93 */     fully_qualified_classname = fully_qualified_classname;
/*  17:    */     
/*  18: 95 */     Verifier v = (Verifier)hashMap.get(fully_qualified_classname);
/*  19: 96 */     if (v == null)
/*  20:    */     {
/*  21: 97 */       v = new Verifier(fully_qualified_classname);
/*  22: 98 */       hashMap.put(fully_qualified_classname, v);
/*  23: 99 */       notify(fully_qualified_classname);
/*  24:    */     }
/*  25:102 */     return v;
/*  26:    */   }
/*  27:    */   
/*  28:    */   private static void notify(String fully_qualified_classname)
/*  29:    */   {
/*  30:110 */     Iterator i = observers.iterator();
/*  31:111 */     while (i.hasNext())
/*  32:    */     {
/*  33:112 */       VerifierFactoryObserver vfo = (VerifierFactoryObserver)i.next();
/*  34:113 */       vfo.update(fully_qualified_classname);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static Verifier[] getVerifiers()
/*  39:    */   {
/*  40:125 */     Verifier[] vs = new Verifier[hashMap.values().size()];
/*  41:126 */     return (Verifier[])hashMap.values().toArray(vs);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void attach(VerifierFactoryObserver o)
/*  45:    */   {
/*  46:133 */     observers.addElement(o);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void detach(VerifierFactoryObserver o)
/*  50:    */   {
/*  51:140 */     observers.removeElement(o);
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.VerifierFactory
 * JD-Core Version:    0.7.0.1
 */