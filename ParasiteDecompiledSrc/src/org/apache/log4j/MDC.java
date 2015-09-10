/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.apache.log4j.helpers.Loader;
/*   5:    */ import org.apache.log4j.helpers.ThreadLocalMap;
/*   6:    */ 
/*   7:    */ public class MDC
/*   8:    */ {
/*   9: 45 */   static final MDC mdc = new MDC();
/*  10:    */   static final int HT_SIZE = 7;
/*  11:    */   boolean java1;
/*  12:    */   Object tlm;
/*  13:    */   
/*  14:    */   private MDC()
/*  15:    */   {
/*  16: 55 */     this.java1 = Loader.isJava1();
/*  17: 56 */     if (!this.java1) {
/*  18: 57 */       this.tlm = new ThreadLocalMap();
/*  19:    */     }
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static void put(String key, Object o)
/*  23:    */   {
/*  24: 73 */     if (mdc != null) {
/*  25: 74 */       mdc.put0(key, o);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Object get(String key)
/*  30:    */   {
/*  31: 86 */     if (mdc != null) {
/*  32: 87 */       return mdc.get0(key);
/*  33:    */     }
/*  34: 89 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void remove(String key)
/*  38:    */   {
/*  39:100 */     if (mdc != null) {
/*  40:101 */       mdc.remove0(key);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Hashtable getContext()
/*  45:    */   {
/*  46:111 */     if (mdc != null) {
/*  47:112 */       return mdc.getContext0();
/*  48:    */     }
/*  49:114 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void clear()
/*  53:    */   {
/*  54:123 */     if (mdc != null) {
/*  55:124 */       mdc.clear0();
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void put0(String key, Object o)
/*  60:    */   {
/*  61:131 */     if ((this.java1) || (this.tlm == null)) {
/*  62:132 */       return;
/*  63:    */     }
/*  64:134 */     Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/*  65:135 */     if (ht == null)
/*  66:    */     {
/*  67:136 */       ht = new Hashtable(7);
/*  68:137 */       ((ThreadLocalMap)this.tlm).set(ht);
/*  69:    */     }
/*  70:139 */     ht.put(key, o);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private Object get0(String key)
/*  74:    */   {
/*  75:145 */     if ((this.java1) || (this.tlm == null)) {
/*  76:146 */       return null;
/*  77:    */     }
/*  78:148 */     Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/*  79:149 */     if ((ht != null) && (key != null)) {
/*  80:150 */       return ht.get(key);
/*  81:    */     }
/*  82:152 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void remove0(String key)
/*  86:    */   {
/*  87:159 */     if ((!this.java1) && (this.tlm != null))
/*  88:    */     {
/*  89:160 */       Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/*  90:161 */       if (ht != null) {
/*  91:162 */         ht.remove(key);
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   private Hashtable getContext0()
/*  97:    */   {
/*  98:170 */     if ((this.java1) || (this.tlm == null)) {
/*  99:171 */       return null;
/* 100:    */     }
/* 101:173 */     return (Hashtable)((ThreadLocalMap)this.tlm).get();
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void clear0()
/* 105:    */   {
/* 106:179 */     if ((!this.java1) && (this.tlm != null))
/* 107:    */     {
/* 108:180 */       Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/* 109:181 */       if (ht != null) {
/* 110:182 */         ht.clear();
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.MDC
 * JD-Core Version:    0.7.0.1
 */