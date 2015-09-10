/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManagerImpl;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.ListIterator;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ 
/*  13:    */ public abstract class WebWindowImpl
/*  14:    */   implements WebWindow
/*  15:    */ {
/*  16: 43 */   private static final Log LOG = LogFactory.getLog(WebWindowImpl.class);
/*  17:    */   private WebClient webClient_;
/*  18:    */   private Page enclosedPage_;
/*  19:    */   private Object scriptObject_;
/*  20:    */   private JavaScriptJobManager jobManager_;
/*  21: 49 */   private List<WebWindowImpl> childWindows_ = new ArrayList();
/*  22: 50 */   private String name_ = "";
/*  23: 51 */   private History history_ = new History(this);
/*  24:    */   private boolean closed_;
/*  25:    */   
/*  26:    */   @Deprecated
/*  27:    */   protected WebWindowImpl() {}
/*  28:    */   
/*  29:    */   public WebWindowImpl(WebClient webClient)
/*  30:    */   {
/*  31: 69 */     WebAssert.notNull("webClient", webClient);
/*  32: 70 */     this.webClient_ = webClient;
/*  33: 71 */     this.jobManager_ = new JavaScriptJobManagerImpl(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void performRegistration()
/*  37:    */   {
/*  38: 78 */     this.webClient_.registerWebWindow(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public WebClient getWebClient()
/*  42:    */   {
/*  43: 85 */     return this.webClient_;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Page getEnclosedPage()
/*  47:    */   {
/*  48: 92 */     return this.enclosedPage_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setEnclosedPage(Page page)
/*  52:    */   {
/*  53: 99 */     if (LOG.isDebugEnabled()) {
/*  54:100 */       LOG.debug("setEnclosedPage: " + page);
/*  55:    */     }
/*  56:102 */     if (page == this.enclosedPage_) {
/*  57:103 */       return;
/*  58:    */     }
/*  59:105 */     destroyChildren();
/*  60:106 */     this.enclosedPage_ = page;
/*  61:107 */     this.history_.addPage(page);
/*  62:108 */     if (isJavaScriptInitializationNeeded()) {
/*  63:109 */       this.webClient_.initialize(this);
/*  64:    */     }
/*  65:111 */     this.webClient_.initialize(page);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected abstract boolean isJavaScriptInitializationNeeded();
/*  69:    */   
/*  70:    */   public void setScriptObject(Object scriptObject)
/*  71:    */   {
/*  72:124 */     this.scriptObject_ = scriptObject;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object getScriptObject()
/*  76:    */   {
/*  77:131 */     return this.scriptObject_;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public JavaScriptJobManager getJobManager()
/*  81:    */   {
/*  82:138 */     return this.jobManager_;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setJobManager(JavaScriptJobManager jobManager)
/*  86:    */   {
/*  87:149 */     this.jobManager_ = jobManager;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addChildWindow(FrameWindow child)
/*  91:    */   {
/*  92:160 */     this.childWindows_.add(child);
/*  93:    */   }
/*  94:    */   
/*  95:    */   void destroyChildren()
/*  96:    */   {
/*  97:164 */     if (LOG.isDebugEnabled()) {
/*  98:165 */       LOG.debug("destroyChildren");
/*  99:    */     }
/* 100:167 */     getJobManager().removeAllJobs();
/* 101:168 */     for (ListIterator<WebWindowImpl> iter = this.childWindows_.listIterator(); iter.hasNext();)
/* 102:    */     {
/* 103:169 */       WebWindowImpl window = (WebWindowImpl)iter.next();
/* 104:170 */       if (LOG.isDebugEnabled()) {
/* 105:171 */         LOG.debug("closing child window: " + window);
/* 106:    */       }
/* 107:173 */       window.setClosed();
/* 108:174 */       window.getJobManager().shutdown();
/* 109:175 */       Page page = window.getEnclosedPage();
/* 110:176 */       if ((page instanceof HtmlPage)) {
/* 111:177 */         ((HtmlPage)page).cleanUp();
/* 112:    */       }
/* 113:179 */       window.destroyChildren();
/* 114:180 */       iter.remove();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getName()
/* 119:    */   {
/* 120:188 */     return this.name_;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setName(String name)
/* 124:    */   {
/* 125:195 */     this.name_ = name;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public History getHistory()
/* 129:    */   {
/* 130:203 */     return this.history_;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isClosed()
/* 134:    */   {
/* 135:210 */     return this.closed_;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void setClosed()
/* 139:    */   {
/* 140:217 */     this.closed_ = true;
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebWindowImpl
 * JD-Core Version:    0.7.0.1
 */