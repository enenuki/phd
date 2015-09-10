/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.ObjectInputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ 
/*  11:    */ public class History
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private final WebWindow window_;
/*  15: 41 */   private final List<String> urls_ = new ArrayList();
/*  16:    */   private transient ThreadLocal<Boolean> ignoreNewPages_;
/*  17: 51 */   private int index_ = -1;
/*  18:    */   
/*  19:    */   public History(WebWindow window)
/*  20:    */   {
/*  21: 58 */     this.window_ = window;
/*  22: 59 */     initTransientFields();
/*  23:    */   }
/*  24:    */   
/*  25:    */   private void initTransientFields()
/*  26:    */   {
/*  27: 66 */     this.ignoreNewPages_ = new ThreadLocal();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getLength()
/*  31:    */   {
/*  32: 74 */     return this.urls_.size();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getIndex()
/*  36:    */   {
/*  37: 82 */     return this.index_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public URL getUrl(int index)
/*  41:    */   {
/*  42: 91 */     if ((index >= 0) && (index < this.urls_.size())) {
/*  43: 92 */       return UrlUtils.toUrlSafe((String)this.urls_.get(index));
/*  44:    */     }
/*  45: 94 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public History back()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:103 */     if (this.index_ > 0)
/*  52:    */     {
/*  53:104 */       this.index_ -= 1;
/*  54:105 */       goToUrlAtCurrentIndex();
/*  55:    */     }
/*  56:107 */     return this;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public History forward()
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:116 */     if (this.index_ < this.urls_.size() - 1)
/*  63:    */     {
/*  64:117 */       this.index_ += 1;
/*  65:118 */       goToUrlAtCurrentIndex();
/*  66:    */     }
/*  67:120 */     return this;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public History go(int relativeIndex)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:131 */     int i = this.index_ + relativeIndex;
/*  74:132 */     if ((i < this.urls_.size()) && (i >= 0)) {
/*  75:133 */       this.index_ = i;
/*  76:    */     }
/*  77:135 */     goToUrlAtCurrentIndex();
/*  78:136 */     return this;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toString()
/*  82:    */   {
/*  83:144 */     return this.urls_.toString();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void removeCurrent()
/*  87:    */   {
/*  88:151 */     if ((this.index_ >= 0) && (this.index_ < this.urls_.size()))
/*  89:    */     {
/*  90:152 */       this.urls_.remove(this.index_);
/*  91:153 */       if (this.index_ > 0) {
/*  92:154 */         this.index_ -= 1;
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void addPage(Page page)
/*  98:    */   {
/*  99:164 */     Boolean ignoreNewPages = (Boolean)this.ignoreNewPages_.get();
/* 100:165 */     if ((ignoreNewPages != null) && (ignoreNewPages.booleanValue())) {
/* 101:166 */       return;
/* 102:    */     }
/* 103:168 */     this.index_ += 1;
/* 104:169 */     while (this.urls_.size() > this.index_) {
/* 105:170 */       this.urls_.remove(this.index_);
/* 106:    */     }
/* 107:172 */     this.urls_.add(page.getWebResponse().getWebRequest().getUrl().toExternalForm());
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void goToUrlAtCurrentIndex()
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:180 */     URL url = UrlUtils.toUrlSafe((String)this.urls_.get(this.index_));
/* 114:181 */     WebRequest wrs = new WebRequest(url);
/* 115:182 */     Boolean old = (Boolean)this.ignoreNewPages_.get();
/* 116:    */     try
/* 117:    */     {
/* 118:184 */       this.ignoreNewPages_.set(Boolean.TRUE);
/* 119:185 */       this.window_.getWebClient().getPage(this.window_, wrs);
/* 120:    */     }
/* 121:    */     finally
/* 122:    */     {
/* 123:188 */       this.ignoreNewPages_.set(old);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void readObject(ObjectInputStream in)
/* 128:    */     throws IOException, ClassNotFoundException
/* 129:    */   {
/* 130:196 */     in.defaultReadObject();
/* 131:197 */     initTransientFields();
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.History
 * JD-Core Version:    0.7.0.1
 */