/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HeaderIterator;
/*   6:    */ import org.apache.http.HttpMessage;
/*   7:    */ import org.apache.http.params.BasicHttpParams;
/*   8:    */ import org.apache.http.params.HttpParams;
/*   9:    */ 
/*  10:    */ public abstract class AbstractHttpMessage
/*  11:    */   implements HttpMessage
/*  12:    */ {
/*  13:    */   protected HeaderGroup headergroup;
/*  14:    */   protected HttpParams params;
/*  15:    */   
/*  16:    */   protected AbstractHttpMessage(HttpParams params)
/*  17:    */   {
/*  18: 51 */     this.headergroup = new HeaderGroup();
/*  19: 52 */     this.params = params;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected AbstractHttpMessage()
/*  23:    */   {
/*  24: 56 */     this(null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean containsHeader(String name)
/*  28:    */   {
/*  29: 61 */     return this.headergroup.containsHeader(name);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Header[] getHeaders(String name)
/*  33:    */   {
/*  34: 66 */     return this.headergroup.getHeaders(name);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Header getFirstHeader(String name)
/*  38:    */   {
/*  39: 71 */     return this.headergroup.getFirstHeader(name);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Header getLastHeader(String name)
/*  43:    */   {
/*  44: 76 */     return this.headergroup.getLastHeader(name);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Header[] getAllHeaders()
/*  48:    */   {
/*  49: 81 */     return this.headergroup.getAllHeaders();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addHeader(Header header)
/*  53:    */   {
/*  54: 86 */     this.headergroup.addHeader(header);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addHeader(String name, String value)
/*  58:    */   {
/*  59: 91 */     if (name == null) {
/*  60: 92 */       throw new IllegalArgumentException("Header name may not be null");
/*  61:    */     }
/*  62: 94 */     this.headergroup.addHeader(new BasicHeader(name, value));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setHeader(Header header)
/*  66:    */   {
/*  67: 99 */     this.headergroup.updateHeader(header);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setHeader(String name, String value)
/*  71:    */   {
/*  72:104 */     if (name == null) {
/*  73:105 */       throw new IllegalArgumentException("Header name may not be null");
/*  74:    */     }
/*  75:107 */     this.headergroup.updateHeader(new BasicHeader(name, value));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setHeaders(Header[] headers)
/*  79:    */   {
/*  80:112 */     this.headergroup.setHeaders(headers);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void removeHeader(Header header)
/*  84:    */   {
/*  85:117 */     this.headergroup.removeHeader(header);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void removeHeaders(String name)
/*  89:    */   {
/*  90:122 */     if (name == null) {
/*  91:123 */       return;
/*  92:    */     }
/*  93:125 */     for (Iterator i = this.headergroup.iterator(); i.hasNext();)
/*  94:    */     {
/*  95:126 */       Header header = (Header)i.next();
/*  96:127 */       if (name.equalsIgnoreCase(header.getName())) {
/*  97:128 */         i.remove();
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public HeaderIterator headerIterator()
/* 103:    */   {
/* 104:135 */     return this.headergroup.iterator();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public HeaderIterator headerIterator(String name)
/* 108:    */   {
/* 109:140 */     return this.headergroup.iterator(name);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public HttpParams getParams()
/* 113:    */   {
/* 114:145 */     if (this.params == null) {
/* 115:146 */       this.params = new BasicHttpParams();
/* 116:    */     }
/* 117:148 */     return this.params;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setParams(HttpParams params)
/* 121:    */   {
/* 122:153 */     if (params == null) {
/* 123:154 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 124:    */     }
/* 125:156 */     this.params = params;
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.AbstractHttpMessage
 * JD-Core Version:    0.7.0.1
 */