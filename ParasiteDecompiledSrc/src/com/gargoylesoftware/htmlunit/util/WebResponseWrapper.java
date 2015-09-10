/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class WebResponseWrapper
/*   9:    */   extends WebResponse
/*  10:    */ {
/*  11:    */   private final WebResponse wrappedWebResponse_;
/*  12:    */   
/*  13:    */   public WebResponseWrapper(WebResponse webResponse)
/*  14:    */     throws IllegalArgumentException
/*  15:    */   {
/*  16: 43 */     super(null, null, 0L);
/*  17: 44 */     if (webResponse == null) {
/*  18: 45 */       throw new IllegalArgumentException("Wrapped WebResponse can't be null");
/*  19:    */     }
/*  20: 47 */     this.wrappedWebResponse_ = webResponse;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public InputStream getContentAsStream()
/*  24:    */   {
/*  25: 56 */     return this.wrappedWebResponse_.getContentAsStream();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getContentAsString()
/*  29:    */   {
/*  30: 65 */     return this.wrappedWebResponse_.getContentAsString();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getContentAsString(String encoding)
/*  34:    */   {
/*  35: 74 */     return this.wrappedWebResponse_.getContentAsString(encoding);
/*  36:    */   }
/*  37:    */   
/*  38:    */   @Deprecated
/*  39:    */   public byte[] getContentAsBytes()
/*  40:    */   {
/*  41: 85 */     return this.wrappedWebResponse_.getContentAsBytes();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getContentCharsetOrNull()
/*  45:    */   {
/*  46: 94 */     return this.wrappedWebResponse_.getContentCharsetOrNull();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getContentCharset()
/*  50:    */   {
/*  51:103 */     return this.wrappedWebResponse_.getContentCharset();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getContentType()
/*  55:    */   {
/*  56:112 */     return this.wrappedWebResponse_.getContentType();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public long getLoadTime()
/*  60:    */   {
/*  61:121 */     return this.wrappedWebResponse_.getLoadTime();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<NameValuePair> getResponseHeaders()
/*  65:    */   {
/*  66:130 */     return this.wrappedWebResponse_.getResponseHeaders();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getResponseHeaderValue(String headerName)
/*  70:    */   {
/*  71:139 */     return this.wrappedWebResponse_.getResponseHeaderValue(headerName);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getStatusCode()
/*  75:    */   {
/*  76:148 */     return this.wrappedWebResponse_.getStatusCode();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getStatusMessage()
/*  80:    */   {
/*  81:157 */     return this.wrappedWebResponse_.getStatusMessage();
/*  82:    */   }
/*  83:    */   
/*  84:    */   @Deprecated
/*  85:    */   public WebRequest getRequestSettings()
/*  86:    */   {
/*  87:168 */     return this.wrappedWebResponse_.getWebRequest();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public WebRequest getWebRequest()
/*  91:    */   {
/*  92:177 */     return this.wrappedWebResponse_.getWebRequest();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.WebResponseWrapper
 * JD-Core Version:    0.7.0.1
 */