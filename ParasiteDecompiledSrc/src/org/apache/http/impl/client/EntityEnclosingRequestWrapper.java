/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HttpEntity;
/*   8:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   9:    */ import org.apache.http.ProtocolException;
/*  10:    */ import org.apache.http.annotation.NotThreadSafe;
/*  11:    */ import org.apache.http.entity.HttpEntityWrapper;
/*  12:    */ 
/*  13:    */ @NotThreadSafe
/*  14:    */ public class EntityEnclosingRequestWrapper
/*  15:    */   extends RequestWrapper
/*  16:    */   implements HttpEntityEnclosingRequest
/*  17:    */ {
/*  18:    */   private HttpEntity entity;
/*  19:    */   private boolean consumed;
/*  20:    */   
/*  21:    */   public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest request)
/*  22:    */     throws ProtocolException
/*  23:    */   {
/*  24: 63 */     super(request);
/*  25: 64 */     setEntity(request.getEntity());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public HttpEntity getEntity()
/*  29:    */   {
/*  30: 68 */     return this.entity;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setEntity(HttpEntity entity)
/*  34:    */   {
/*  35: 72 */     this.entity = (entity != null ? new EntityWrapper(entity) : null);
/*  36: 73 */     this.consumed = false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean expectContinue()
/*  40:    */   {
/*  41: 77 */     Header expect = getFirstHeader("Expect");
/*  42: 78 */     return (expect != null) && ("100-continue".equalsIgnoreCase(expect.getValue()));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isRepeatable()
/*  46:    */   {
/*  47: 83 */     return (this.entity == null) || (this.entity.isRepeatable()) || (!this.consumed);
/*  48:    */   }
/*  49:    */   
/*  50:    */   class EntityWrapper
/*  51:    */     extends HttpEntityWrapper
/*  52:    */   {
/*  53:    */     EntityWrapper(HttpEntity entity)
/*  54:    */     {
/*  55: 89 */       super();
/*  56:    */     }
/*  57:    */     
/*  58:    */     @Deprecated
/*  59:    */     public void consumeContent()
/*  60:    */       throws IOException
/*  61:    */     {
/*  62: 95 */       EntityEnclosingRequestWrapper.this.consumed = true;
/*  63: 96 */       super.consumeContent();
/*  64:    */     }
/*  65:    */     
/*  66:    */     public InputStream getContent()
/*  67:    */       throws IOException
/*  68:    */     {
/*  69:101 */       EntityEnclosingRequestWrapper.this.consumed = true;
/*  70:102 */       return super.getContent();
/*  71:    */     }
/*  72:    */     
/*  73:    */     public void writeTo(OutputStream outstream)
/*  74:    */       throws IOException
/*  75:    */     {
/*  76:107 */       EntityEnclosingRequestWrapper.this.consumed = true;
/*  77:108 */       super.writeTo(outstream);
/*  78:    */     }
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.EntityEnclosingRequestWrapper
 * JD-Core Version:    0.7.0.1
 */