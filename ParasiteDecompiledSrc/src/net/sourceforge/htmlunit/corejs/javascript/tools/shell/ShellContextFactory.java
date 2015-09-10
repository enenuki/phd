/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ErrorReporter;
/*   6:    */ 
/*   7:    */ public class ShellContextFactory
/*   8:    */   extends ContextFactory
/*   9:    */ {
/*  10:    */   private boolean strictMode;
/*  11:    */   private boolean warningAsError;
/*  12:    */   private int languageVersion;
/*  13:    */   private int optimizationLevel;
/*  14:    */   private boolean generatingDebug;
/*  15: 51 */   private boolean allowReservedKeywords = true;
/*  16:    */   private ErrorReporter errorReporter;
/*  17:    */   private String characterEncoding;
/*  18:    */   
/*  19:    */   protected boolean hasFeature(Context cx, int featureIndex)
/*  20:    */   {
/*  21: 58 */     switch (featureIndex)
/*  22:    */     {
/*  23:    */     case 8: 
/*  24:    */     case 9: 
/*  25:    */     case 11: 
/*  26: 62 */       return this.strictMode;
/*  27:    */     case 3: 
/*  28: 65 */       return this.allowReservedKeywords;
/*  29:    */     case 12: 
/*  30: 68 */       return this.warningAsError;
/*  31:    */     case 10: 
/*  32: 71 */       return this.generatingDebug;
/*  33:    */     }
/*  34: 73 */     return super.hasFeature(cx, featureIndex);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void onContextCreated(Context cx)
/*  38:    */   {
/*  39: 79 */     cx.setLanguageVersion(this.languageVersion);
/*  40: 80 */     cx.setOptimizationLevel(this.optimizationLevel);
/*  41: 81 */     if (this.errorReporter != null) {
/*  42: 82 */       cx.setErrorReporter(this.errorReporter);
/*  43:    */     }
/*  44: 84 */     cx.setGeneratingDebug(this.generatingDebug);
/*  45: 85 */     super.onContextCreated(cx);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setStrictMode(boolean flag)
/*  49:    */   {
/*  50: 90 */     checkNotSealed();
/*  51: 91 */     this.strictMode = flag;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setWarningAsError(boolean flag)
/*  55:    */   {
/*  56: 96 */     checkNotSealed();
/*  57: 97 */     this.warningAsError = flag;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setLanguageVersion(int version)
/*  61:    */   {
/*  62:102 */     Context.checkLanguageVersion(version);
/*  63:103 */     checkNotSealed();
/*  64:104 */     this.languageVersion = version;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setOptimizationLevel(int optimizationLevel)
/*  68:    */   {
/*  69:109 */     Context.checkOptimizationLevel(optimizationLevel);
/*  70:110 */     checkNotSealed();
/*  71:111 */     this.optimizationLevel = optimizationLevel;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setErrorReporter(ErrorReporter errorReporter)
/*  75:    */   {
/*  76:116 */     if (errorReporter == null) {
/*  77:116 */       throw new IllegalArgumentException();
/*  78:    */     }
/*  79:117 */     this.errorReporter = errorReporter;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setGeneratingDebug(boolean generatingDebug)
/*  83:    */   {
/*  84:122 */     this.generatingDebug = generatingDebug;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCharacterEncoding()
/*  88:    */   {
/*  89:127 */     return this.characterEncoding;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCharacterEncoding(String characterEncoding)
/*  93:    */   {
/*  94:132 */     this.characterEncoding = characterEncoding;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setAllowReservedKeywords(boolean allowReservedKeywords)
/*  98:    */   {
/*  99:136 */     this.allowReservedKeywords = allowReservedKeywords;
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.ShellContextFactory
 * JD-Core Version:    0.7.0.1
 */