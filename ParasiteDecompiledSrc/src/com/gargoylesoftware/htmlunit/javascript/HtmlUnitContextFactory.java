/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ScriptPreProcessor;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.regexp.HtmlUnitRegExpProxy;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.ErrorReporter;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.Evaluator;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  17:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  18:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.WrapFactory;
/*  20:    */ import net.sourceforge.htmlunit.corejs.javascript.debug.Debugger;
/*  21:    */ 
/*  22:    */ public class HtmlUnitContextFactory
/*  23:    */   extends ContextFactory
/*  24:    */ {
/*  25:    */   private static final int INSTRUCTION_COUNT_THRESHOLD = 10000;
/*  26:    */   private final BrowserVersion browserVersion_;
/*  27:    */   private final WebClient webClient_;
/*  28:    */   private long timeout_;
/*  29:    */   private Debugger debugger_;
/*  30:    */   private final ErrorReporter errorReporter_;
/*  31: 55 */   private final WrapFactory wrapFactory_ = new HtmlUnitWrapFactory();
/*  32:    */   
/*  33:    */   public HtmlUnitContextFactory(WebClient webClient)
/*  34:    */   {
/*  35: 63 */     WebAssert.notNull("webClient", webClient);
/*  36: 64 */     this.webClient_ = webClient;
/*  37: 65 */     this.browserVersion_ = webClient.getBrowserVersion();
/*  38: 66 */     this.errorReporter_ = new StrictErrorReporter();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setTimeout(long timeout)
/*  42:    */   {
/*  43: 76 */     this.timeout_ = timeout;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public long getTimeout()
/*  47:    */   {
/*  48: 86 */     return this.timeout_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setDebugger(Debugger debugger)
/*  52:    */   {
/*  53: 97 */     this.debugger_ = debugger;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Debugger getDebugger()
/*  57:    */   {
/*  58:107 */     return this.debugger_;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private class TimeoutContext
/*  62:    */     extends Context
/*  63:    */   {
/*  64:    */     private long startTime_;
/*  65:    */     
/*  66:    */     protected TimeoutContext(ContextFactory factory)
/*  67:    */     {
/*  68:116 */       super();
/*  69:    */     }
/*  70:    */     
/*  71:    */     public void startClock()
/*  72:    */     {
/*  73:119 */       this.startTime_ = System.currentTimeMillis();
/*  74:    */     }
/*  75:    */     
/*  76:    */     public void terminateScriptIfNecessary()
/*  77:    */     {
/*  78:122 */       if (HtmlUnitContextFactory.this.timeout_ > 0L)
/*  79:    */       {
/*  80:123 */         long currentTime = System.currentTimeMillis();
/*  81:124 */         if (currentTime - this.startTime_ > HtmlUnitContextFactory.this.timeout_) {
/*  82:127 */           throw new TimeoutError(HtmlUnitContextFactory.this.timeout_, currentTime - this.startTime_);
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:    */     
/*  87:    */     protected Script compileString(String source, Evaluator compiler, ErrorReporter compilationErrorReporter, String sourceName, int lineno, Object securityDomain)
/*  88:    */     {
/*  89:138 */       boolean isWindowEval = compiler != null;
/*  90:141 */       if (!isWindowEval)
/*  91:    */       {
/*  92:142 */         String sourceCodeTrimmed = source.trim();
/*  93:143 */         if (sourceCodeTrimmed.startsWith("<!--")) {
/*  94:144 */           source = source.replaceFirst("<!--", "// <!--");
/*  95:    */         }
/*  96:147 */         if ((HtmlUnitContextFactory.this.browserVersion_.hasFeature(BrowserVersionFeatures.GENERATED_140)) && (sourceCodeTrimmed.endsWith("-->")))
/*  97:    */         {
/*  98:149 */           int lastDoubleSlash = source.lastIndexOf("//");
/*  99:150 */           int lastNewLine = Math.max(source.lastIndexOf('\n'), source.lastIndexOf('\r'));
/* 100:151 */           if (lastNewLine > lastDoubleSlash) {
/* 101:152 */             source = source.substring(0, lastNewLine);
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:158 */       HtmlPage page = (HtmlPage)Context.getCurrentContext().getThreadLocal("startingPage");
/* 106:    */       
/* 107:160 */       source = HtmlUnitContextFactory.this.preProcess(page, source, sourceName, lineno, null);
/* 108:166 */       if (HtmlUnitContextFactory.this.browserVersion_.hasFeature(BrowserVersionFeatures.GENERATED_141))
/* 109:    */       {
/* 110:167 */         ScriptPreProcessor ieCCPreProcessor = new IEConditionalCompilationScriptPreProcessor();
/* 111:168 */         source = ieCCPreProcessor.preProcess(page, source, sourceName, lineno, null);
/* 112:    */       }
/* 113:173 */       return super.compileString(source, compiler, compilationErrorReporter, sourceName, lineno, securityDomain);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected String preProcess(HtmlPage htmlPage, String sourceCode, String sourceName, int lineNumber, HtmlElement htmlElement)
/* 118:    */   {
/* 119:195 */     String newSourceCode = sourceCode;
/* 120:196 */     ScriptPreProcessor preProcessor = this.webClient_.getScriptPreProcessor();
/* 121:197 */     if (preProcessor != null)
/* 122:    */     {
/* 123:198 */       newSourceCode = preProcessor.preProcess(htmlPage, sourceCode, sourceName, lineNumber, htmlElement);
/* 124:199 */       if (newSourceCode == null) {
/* 125:200 */         newSourceCode = "";
/* 126:    */       }
/* 127:    */     }
/* 128:203 */     return newSourceCode;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected Context makeContext()
/* 132:    */   {
/* 133:211 */     TimeoutContext cx = new TimeoutContext(this);
/* 134:    */     
/* 135:    */ 
/* 136:214 */     cx.setOptimizationLevel(-1);
/* 137:    */     
/* 138:    */ 
/* 139:217 */     cx.setInstructionObserverThreshold(10000);
/* 140:    */     
/* 141:219 */     configureErrorReporter(cx);
/* 142:220 */     cx.setWrapFactory(this.wrapFactory_);
/* 143:222 */     if (this.debugger_ != null) {
/* 144:223 */       cx.setDebugger(this.debugger_, null);
/* 145:    */     }
/* 146:227 */     ScriptRuntime.setRegExpProxy(cx, new HtmlUnitRegExpProxy(ScriptRuntime.getRegExpProxy(cx)));
/* 147:    */     
/* 148:229 */     cx.setMaximumInterpreterStackDepth(10000);
/* 149:    */     
/* 150:231 */     return cx;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void configureErrorReporter(Context context)
/* 154:    */   {
/* 155:240 */     context.setErrorReporter(this.errorReporter_);
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected void observeInstructionCount(Context cx, int instructionCount)
/* 159:    */   {
/* 160:253 */     TimeoutContext tcx = (TimeoutContext)cx;
/* 161:254 */     tcx.terminateScriptIfNecessary();
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 165:    */   {
/* 166:265 */     TimeoutContext tcx = (TimeoutContext)cx;
/* 167:266 */     tcx.startClock();
/* 168:267 */     return super.doTopCall(callable, cx, scope, thisObj, args);
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected boolean hasFeature(Context cx, int featureIndex)
/* 172:    */   {
/* 173:275 */     switch (featureIndex)
/* 174:    */     {
/* 175:    */     case 3: 
/* 176:277 */       return true;
/* 177:    */     case 5: 
/* 178:279 */       return !this.browserVersion_.hasFeature(BrowserVersionFeatures.GENERATED_142);
/* 179:    */     case 1: 
/* 180:281 */       return this.browserVersion_.hasFeature(BrowserVersionFeatures.GENERATED_143);
/* 181:    */     case 14: 
/* 182:283 */       return this.browserVersion_.hasFeature(BrowserVersionFeatures.SET_READONLY_PROPERTIES);
/* 183:    */     case 15: 
/* 184:285 */       return false;
/* 185:    */     }
/* 186:287 */     return super.hasFeature(cx, featureIndex);
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory
 * JD-Core Version:    0.7.0.1
 */