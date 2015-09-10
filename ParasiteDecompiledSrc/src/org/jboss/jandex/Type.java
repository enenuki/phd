/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ public final class Type
/*   4:    */ {
/*   5:    */   private final DotName name;
/*   6:    */   private final Kind kind;
/*   7:    */   
/*   8:    */   public static enum Kind
/*   9:    */   {
/*  10: 43 */     CLASS,  ARRAY,  PRIMITIVE,  VOID;
/*  11:    */     
/*  12:    */     private Kind() {}
/*  13:    */     
/*  14:    */     public static Kind fromOrdinal(int ordinal)
/*  15:    */     {
/*  16: 66 */       switch (ordinal)
/*  17:    */       {
/*  18:    */       case 0: 
/*  19: 68 */         return CLASS;
/*  20:    */       case 1: 
/*  21: 70 */         return ARRAY;
/*  22:    */       case 2: 
/*  23: 72 */         return PRIMITIVE;
/*  24:    */       }
/*  25: 75 */       return VOID;
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   Type(DotName name, Kind kind)
/*  30:    */   {
/*  31: 81 */     this.name = name;
/*  32: 82 */     this.kind = kind;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static final Type create(DotName name, Kind kind)
/*  36:    */   {
/*  37: 86 */     if (name == null) {
/*  38: 87 */       throw new IllegalArgumentException("name can not be null!");
/*  39:    */     }
/*  40: 89 */     if (kind == null) {
/*  41: 90 */       throw new IllegalArgumentException("kind can not be null!");
/*  42:    */     }
/*  43: 92 */     return new Type(name, kind);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DotName name()
/*  47:    */   {
/*  48:104 */     return this.name;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Kind kind()
/*  52:    */   {
/*  53:113 */     return this.kind;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toString()
/*  57:    */   {
/*  58:117 */     return this.name.toString();
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.Type
 * JD-Core Version:    0.7.0.1
 */