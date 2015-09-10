/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.background;
/*   2:    */ 
/*   3:    */ public abstract class JavaScriptJob
/*   4:    */   implements Runnable, Comparable<JavaScriptJob>
/*   5:    */ {
/*   6:    */   private Integer id_;
/*   7:    */   private final int initialDelay_;
/*   8:    */   private final Integer period_;
/*   9:    */   private final boolean executeAsap_;
/*  10:    */   private long targetExecutionTime_;
/*  11:    */   
/*  12:    */   public JavaScriptJob()
/*  13:    */   {
/*  14: 48 */     this(0, null);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public JavaScriptJob(int initialDelay, Integer period)
/*  18:    */   {
/*  19: 57 */     this.initialDelay_ = initialDelay;
/*  20: 58 */     this.period_ = period;
/*  21: 59 */     setTargetExecutionTime(initialDelay + System.currentTimeMillis());
/*  22: 60 */     this.executeAsap_ = (initialDelay == 0);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setId(Integer id)
/*  26:    */   {
/*  27: 68 */     this.id_ = id;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Integer getId()
/*  31:    */   {
/*  32: 76 */     return this.id_;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getInitialDelay()
/*  36:    */   {
/*  37: 84 */     return this.initialDelay_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Integer getPeriod()
/*  41:    */   {
/*  42: 92 */     return this.period_;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isPeriodic()
/*  46:    */   {
/*  47:100 */     return this.period_ != null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toString()
/*  51:    */   {
/*  52:106 */     return "JavaScript Job " + this.id_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int compareTo(JavaScriptJob other)
/*  56:    */   {
/*  57:111 */     boolean xhr1 = this.executeAsap_;
/*  58:112 */     boolean xhr2 = other.executeAsap_;
/*  59:114 */     if ((xhr1) && (xhr2)) {
/*  60:115 */       return getId().intValue() - other.getId().intValue();
/*  61:    */     }
/*  62:118 */     if (xhr1) {
/*  63:119 */       return -1;
/*  64:    */     }
/*  65:122 */     if (xhr2) {
/*  66:123 */       return 1;
/*  67:    */     }
/*  68:126 */     return (int)(this.targetExecutionTime_ - other.getTargetExecutionTime());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public long getTargetExecutionTime()
/*  72:    */   {
/*  73:134 */     return this.targetExecutionTime_;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setTargetExecutionTime(long targetExecutionTime)
/*  77:    */   {
/*  78:142 */     this.targetExecutionTime_ = targetExecutionTime;
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJob
 * JD-Core Version:    0.7.0.1
 */