/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   4:    */ 
/*   5:    */ public final class ScriptResult
/*   6:    */ {
/*   7:    */   private final Object javaScriptResult_;
/*   8:    */   private final Page newPage_;
/*   9:    */   
/*  10:    */   public ScriptResult(Object javaScriptResult, Page newPage)
/*  11:    */   {
/*  12: 41 */     this.javaScriptResult_ = javaScriptResult;
/*  13: 42 */     this.newPage_ = newPage;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Object getJavaScriptResult()
/*  17:    */   {
/*  18: 50 */     return this.javaScriptResult_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Page getNewPage()
/*  22:    */   {
/*  23: 58 */     return this.newPage_;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String toString()
/*  27:    */   {
/*  28: 66 */     return "ScriptResult[result=" + this.javaScriptResult_ + " page=" + this.newPage_ + "]";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static boolean isFalse(ScriptResult scriptResult)
/*  32:    */   {
/*  33: 75 */     return (scriptResult != null) && (Boolean.FALSE.equals(scriptResult.getJavaScriptResult()));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static boolean isUndefined(ScriptResult scriptResult)
/*  37:    */   {
/*  38: 84 */     return (scriptResult != null) && ((scriptResult.getJavaScriptResult() instanceof Undefined));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static ScriptResult combine(ScriptResult newResult, ScriptResult originalResult, boolean ie)
/*  42:    */   {
/*  43:    */     Object jsResult;
/*  44:    */     Object jsResult;
/*  45:106 */     if (ie)
/*  46:    */     {
/*  47:    */       Object jsResult;
/*  48:107 */       if ((newResult != null) && (!isUndefined(newResult)))
/*  49:    */       {
/*  50:108 */         jsResult = newResult.getJavaScriptResult();
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54:    */         Object jsResult;
/*  55:110 */         if (originalResult != null) {
/*  56:111 */           jsResult = originalResult.getJavaScriptResult();
/*  57:    */         } else {
/*  58:114 */           jsResult = null;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64:    */       Object jsResult;
/*  65:118 */       if (isFalse(newResult))
/*  66:    */       {
/*  67:119 */         jsResult = newResult.getJavaScriptResult();
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71:    */         Object jsResult;
/*  72:121 */         if (originalResult != null) {
/*  73:122 */           jsResult = originalResult.getJavaScriptResult();
/*  74:    */         } else {
/*  75:125 */           jsResult = null;
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */     Page page;
/*  80:    */     Page page;
/*  81:130 */     if (newResult != null)
/*  82:    */     {
/*  83:131 */       page = newResult.getNewPage();
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:    */       Page page;
/*  88:133 */       if (originalResult != null) {
/*  89:134 */         page = originalResult.getNewPage();
/*  90:    */       } else {
/*  91:137 */         page = null;
/*  92:    */       }
/*  93:    */     }
/*  94:141 */     if ((jsResult == null) && (page == null)) {
/*  95:142 */       return null;
/*  96:    */     }
/*  97:144 */     return new ScriptResult(jsResult, page);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ScriptResult
 * JD-Core Version:    0.7.0.1
 */