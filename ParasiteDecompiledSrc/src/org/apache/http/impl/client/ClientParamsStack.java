/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import org.apache.http.annotation.NotThreadSafe;
/*   4:    */ import org.apache.http.params.AbstractHttpParams;
/*   5:    */ import org.apache.http.params.HttpParams;
/*   6:    */ 
/*   7:    */ @NotThreadSafe
/*   8:    */ public class ClientParamsStack
/*   9:    */   extends AbstractHttpParams
/*  10:    */ {
/*  11:    */   protected final HttpParams applicationParams;
/*  12:    */   protected final HttpParams clientParams;
/*  13:    */   protected final HttpParams requestParams;
/*  14:    */   protected final HttpParams overrideParams;
/*  15:    */   
/*  16:    */   public ClientParamsStack(HttpParams aparams, HttpParams cparams, HttpParams rparams, HttpParams oparams)
/*  17:    */   {
/*  18: 99 */     this.applicationParams = aparams;
/*  19:100 */     this.clientParams = cparams;
/*  20:101 */     this.requestParams = rparams;
/*  21:102 */     this.overrideParams = oparams;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ClientParamsStack(ClientParamsStack stack)
/*  25:    */   {
/*  26:114 */     this(stack.getApplicationParams(), stack.getClientParams(), stack.getRequestParams(), stack.getOverrideParams());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ClientParamsStack(ClientParamsStack stack, HttpParams aparams, HttpParams cparams, HttpParams rparams, HttpParams oparams)
/*  30:    */   {
/*  31:137 */     this(aparams != null ? aparams : stack.getApplicationParams(), cparams != null ? cparams : stack.getClientParams(), rparams != null ? rparams : stack.getRequestParams(), oparams != null ? oparams : stack.getOverrideParams());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final HttpParams getApplicationParams()
/*  35:    */   {
/*  36:150 */     return this.applicationParams;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final HttpParams getClientParams()
/*  40:    */   {
/*  41:159 */     return this.clientParams;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final HttpParams getRequestParams()
/*  45:    */   {
/*  46:168 */     return this.requestParams;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final HttpParams getOverrideParams()
/*  50:    */   {
/*  51:177 */     return this.overrideParams;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object getParameter(String name)
/*  55:    */   {
/*  56:191 */     if (name == null) {
/*  57:192 */       throw new IllegalArgumentException("Parameter name must not be null.");
/*  58:    */     }
/*  59:196 */     Object result = null;
/*  60:198 */     if (this.overrideParams != null) {
/*  61:199 */       result = this.overrideParams.getParameter(name);
/*  62:    */     }
/*  63:201 */     if ((result == null) && (this.requestParams != null)) {
/*  64:202 */       result = this.requestParams.getParameter(name);
/*  65:    */     }
/*  66:204 */     if ((result == null) && (this.clientParams != null)) {
/*  67:205 */       result = this.clientParams.getParameter(name);
/*  68:    */     }
/*  69:207 */     if ((result == null) && (this.applicationParams != null)) {
/*  70:208 */       result = this.applicationParams.getParameter(name);
/*  71:    */     }
/*  72:210 */     return result;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public HttpParams setParameter(String name, Object value)
/*  76:    */     throws UnsupportedOperationException
/*  77:    */   {
/*  78:229 */     throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean removeParameter(String name)
/*  82:    */   {
/*  83:247 */     throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
/*  84:    */   }
/*  85:    */   
/*  86:    */   public HttpParams copy()
/*  87:    */   {
/*  88:266 */     return this;
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.ClientParamsStack
 * JD-Core Version:    0.7.0.1
 */