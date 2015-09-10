/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.HttpEntity;
/*   5:    */ import org.apache.http.HttpResponse;
/*   6:    */ import org.apache.http.ProtocolVersion;
/*   7:    */ import org.apache.http.ReasonPhraseCatalog;
/*   8:    */ import org.apache.http.StatusLine;
/*   9:    */ 
/*  10:    */ public class BasicHttpResponse
/*  11:    */   extends AbstractHttpMessage
/*  12:    */   implements HttpResponse
/*  13:    */ {
/*  14:    */   private StatusLine statusline;
/*  15:    */   private HttpEntity entity;
/*  16:    */   private ReasonPhraseCatalog reasonCatalog;
/*  17:    */   private Locale locale;
/*  18:    */   
/*  19:    */   public BasicHttpResponse(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale)
/*  20:    */   {
/*  21: 67 */     if (statusline == null) {
/*  22: 68 */       throw new IllegalArgumentException("Status line may not be null.");
/*  23:    */     }
/*  24: 70 */     this.statusline = statusline;
/*  25: 71 */     this.reasonCatalog = catalog;
/*  26: 72 */     this.locale = (locale != null ? locale : Locale.getDefault());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BasicHttpResponse(StatusLine statusline)
/*  30:    */   {
/*  31: 83 */     this(statusline, null, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public BasicHttpResponse(ProtocolVersion ver, int code, String reason)
/*  35:    */   {
/*  36: 99 */     this(new BasicStatusLine(ver, code, reason), null, null);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ProtocolVersion getProtocolVersion()
/*  40:    */   {
/*  41:105 */     return this.statusline.getProtocolVersion();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public StatusLine getStatusLine()
/*  45:    */   {
/*  46:110 */     return this.statusline;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public HttpEntity getEntity()
/*  50:    */   {
/*  51:115 */     return this.entity;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Locale getLocale()
/*  55:    */   {
/*  56:120 */     return this.locale;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setStatusLine(StatusLine statusline)
/*  60:    */   {
/*  61:125 */     if (statusline == null) {
/*  62:126 */       throw new IllegalArgumentException("Status line may not be null");
/*  63:    */     }
/*  64:128 */     this.statusline = statusline;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setStatusLine(ProtocolVersion ver, int code)
/*  68:    */   {
/*  69:134 */     this.statusline = new BasicStatusLine(ver, code, getReason(code));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setStatusLine(ProtocolVersion ver, int code, String reason)
/*  73:    */   {
/*  74:141 */     this.statusline = new BasicStatusLine(ver, code, reason);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setStatusCode(int code)
/*  78:    */   {
/*  79:147 */     ProtocolVersion ver = this.statusline.getProtocolVersion();
/*  80:148 */     this.statusline = new BasicStatusLine(ver, code, getReason(code));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setReasonPhrase(String reason)
/*  84:    */   {
/*  85:154 */     if ((reason != null) && ((reason.indexOf('\n') >= 0) || (reason.indexOf('\r') >= 0))) {
/*  86:157 */       throw new IllegalArgumentException("Line break in reason phrase.");
/*  87:    */     }
/*  88:159 */     this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), reason);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setEntity(HttpEntity entity)
/*  92:    */   {
/*  93:166 */     this.entity = entity;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setLocale(Locale loc)
/*  97:    */   {
/*  98:171 */     if (loc == null) {
/*  99:172 */       throw new IllegalArgumentException("Locale may not be null.");
/* 100:    */     }
/* 101:174 */     this.locale = loc;
/* 102:175 */     int code = this.statusline.getStatusCode();
/* 103:176 */     this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), code, getReason(code));
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected String getReason(int code)
/* 107:    */   {
/* 108:190 */     return this.reasonCatalog == null ? null : this.reasonCatalog.getReason(code, this.locale);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String toString()
/* 112:    */   {
/* 113:195 */     return this.statusline + " " + this.headergroup;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHttpResponse
 * JD-Core Version:    0.7.0.1
 */