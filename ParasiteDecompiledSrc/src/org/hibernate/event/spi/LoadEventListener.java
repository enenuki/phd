/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ 
/*   6:    */ public abstract interface LoadEventListener
/*   7:    */   extends Serializable
/*   8:    */ {
/*   9: 45 */   public static final LoadType RELOAD = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("GET", null), false), false), true).setNakedEntityReturned(false);
/*  10: 51 */   public static final LoadType GET = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("GET", null), true), false), true).setNakedEntityReturned(false);
/*  11: 57 */   public static final LoadType LOAD = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("LOAD", null), false), true), true).setNakedEntityReturned(false);
/*  12: 63 */   public static final LoadType IMMEDIATE_LOAD = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("IMMEDIATE_LOAD", null), true), false), false).setNakedEntityReturned(true);
/*  13: 69 */   public static final LoadType INTERNAL_LOAD_EAGER = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("INTERNAL_LOAD_EAGER", null), false), false), false).setNakedEntityReturned(false);
/*  14: 75 */   public static final LoadType INTERNAL_LOAD_LAZY = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("INTERNAL_LOAD_LAZY", null), false), true), false).setNakedEntityReturned(false);
/*  15: 81 */   public static final LoadType INTERNAL_LOAD_NULLABLE = LoadType.access$300(LoadType.access$200(LoadType.access$100(new LoadType("INTERNAL_LOAD_NULLABLE", null), true), false), false).setNakedEntityReturned(false);
/*  16:    */   
/*  17:    */   public abstract void onLoad(LoadEvent paramLoadEvent, LoadType paramLoadType)
/*  18:    */     throws HibernateException;
/*  19:    */   
/*  20:    */   public static final class LoadType
/*  21:    */   {
/*  22:    */     private String name;
/*  23:    */     private boolean nakedEntityReturned;
/*  24:    */     private boolean allowNulls;
/*  25:    */     private boolean checkDeleted;
/*  26:    */     private boolean allowProxyCreation;
/*  27:    */     
/*  28:    */     private LoadType(String name)
/*  29:    */     {
/*  30: 96 */       this.name = name;
/*  31:    */     }
/*  32:    */     
/*  33:    */     public boolean isAllowNulls()
/*  34:    */     {
/*  35:100 */       return this.allowNulls;
/*  36:    */     }
/*  37:    */     
/*  38:    */     private LoadType setAllowNulls(boolean allowNulls)
/*  39:    */     {
/*  40:104 */       this.allowNulls = allowNulls;
/*  41:105 */       return this;
/*  42:    */     }
/*  43:    */     
/*  44:    */     public boolean isNakedEntityReturned()
/*  45:    */     {
/*  46:109 */       return this.nakedEntityReturned;
/*  47:    */     }
/*  48:    */     
/*  49:    */     private LoadType setNakedEntityReturned(boolean immediateLoad)
/*  50:    */     {
/*  51:113 */       this.nakedEntityReturned = immediateLoad;
/*  52:114 */       return this;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public boolean isCheckDeleted()
/*  56:    */     {
/*  57:118 */       return this.checkDeleted;
/*  58:    */     }
/*  59:    */     
/*  60:    */     private LoadType setCheckDeleted(boolean checkDeleted)
/*  61:    */     {
/*  62:122 */       this.checkDeleted = checkDeleted;
/*  63:123 */       return this;
/*  64:    */     }
/*  65:    */     
/*  66:    */     public boolean isAllowProxyCreation()
/*  67:    */     {
/*  68:127 */       return this.allowProxyCreation;
/*  69:    */     }
/*  70:    */     
/*  71:    */     private LoadType setAllowProxyCreation(boolean allowProxyCreation)
/*  72:    */     {
/*  73:131 */       this.allowProxyCreation = allowProxyCreation;
/*  74:132 */       return this;
/*  75:    */     }
/*  76:    */     
/*  77:    */     public String getName()
/*  78:    */     {
/*  79:136 */       return this.name;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public String toString()
/*  83:    */     {
/*  84:140 */       return this.name;
/*  85:    */     }
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.LoadEventListener
 * JD-Core Version:    0.7.0.1
 */