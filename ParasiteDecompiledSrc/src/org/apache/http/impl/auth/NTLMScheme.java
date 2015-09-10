/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import org.apache.http.Header;
/*   4:    */ import org.apache.http.HttpRequest;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ import org.apache.http.auth.AuthenticationException;
/*   7:    */ import org.apache.http.auth.Credentials;
/*   8:    */ import org.apache.http.auth.InvalidCredentialsException;
/*   9:    */ import org.apache.http.auth.MalformedChallengeException;
/*  10:    */ import org.apache.http.auth.NTCredentials;
/*  11:    */ import org.apache.http.message.BufferedHeader;
/*  12:    */ import org.apache.http.util.CharArrayBuffer;
/*  13:    */ 
/*  14:    */ @NotThreadSafe
/*  15:    */ public class NTLMScheme
/*  16:    */   extends AuthSchemeBase
/*  17:    */ {
/*  18:    */   private final NTLMEngine engine;
/*  19:    */   private State state;
/*  20:    */   private String challenge;
/*  21:    */   
/*  22:    */   static enum State
/*  23:    */   {
/*  24: 53 */     UNINITIATED,  CHALLENGE_RECEIVED,  MSG_TYPE1_GENERATED,  MSG_TYPE2_RECEVIED,  MSG_TYPE3_GENERATED,  FAILED;
/*  25:    */     
/*  26:    */     private State() {}
/*  27:    */   }
/*  28:    */   
/*  29:    */   public NTLMScheme(NTLMEngine engine)
/*  30:    */   {
/*  31: 68 */     if (engine == null) {
/*  32: 69 */       throw new IllegalArgumentException("NTLM engine may not be null");
/*  33:    */     }
/*  34: 71 */     this.engine = engine;
/*  35: 72 */     this.state = State.UNINITIATED;
/*  36: 73 */     this.challenge = null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getSchemeName()
/*  40:    */   {
/*  41: 77 */     return "ntlm";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getParameter(String name)
/*  45:    */   {
/*  46: 82 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getRealm()
/*  50:    */   {
/*  51: 87 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isConnectionBased()
/*  55:    */   {
/*  56: 91 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void parseChallenge(CharArrayBuffer buffer, int beginIndex, int endIndex)
/*  60:    */     throws MalformedChallengeException
/*  61:    */   {
/*  62: 98 */     String challenge = buffer.substringTrimmed(beginIndex, endIndex);
/*  63: 99 */     if (challenge.length() == 0)
/*  64:    */     {
/*  65:100 */       if (this.state == State.UNINITIATED) {
/*  66:101 */         this.state = State.CHALLENGE_RECEIVED;
/*  67:    */       } else {
/*  68:103 */         this.state = State.FAILED;
/*  69:    */       }
/*  70:105 */       this.challenge = null;
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:107 */       this.state = State.MSG_TYPE2_RECEVIED;
/*  75:108 */       this.challenge = challenge;
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Header authenticate(Credentials credentials, HttpRequest request)
/*  80:    */     throws AuthenticationException
/*  81:    */   {
/*  82:115 */     NTCredentials ntcredentials = null;
/*  83:    */     try
/*  84:    */     {
/*  85:117 */       ntcredentials = (NTCredentials)credentials;
/*  86:    */     }
/*  87:    */     catch (ClassCastException e)
/*  88:    */     {
/*  89:119 */       throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
/*  90:    */     }
/*  91:123 */     String response = null;
/*  92:124 */     if ((this.state == State.CHALLENGE_RECEIVED) || (this.state == State.FAILED))
/*  93:    */     {
/*  94:125 */       response = this.engine.generateType1Msg(ntcredentials.getDomain(), ntcredentials.getWorkstation());
/*  95:    */       
/*  96:    */ 
/*  97:128 */       this.state = State.MSG_TYPE1_GENERATED;
/*  98:    */     }
/*  99:129 */     else if (this.state == State.MSG_TYPE2_RECEVIED)
/* 100:    */     {
/* 101:130 */       response = this.engine.generateType3Msg(ntcredentials.getUserName(), ntcredentials.getPassword(), ntcredentials.getDomain(), ntcredentials.getWorkstation(), this.challenge);
/* 102:    */       
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:136 */       this.state = State.MSG_TYPE3_GENERATED;
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:138 */       throw new AuthenticationException("Unexpected state: " + this.state);
/* 112:    */     }
/* 113:140 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/* 114:141 */     if (isProxy()) {
/* 115:142 */       buffer.append("Proxy-Authorization");
/* 116:    */     } else {
/* 117:144 */       buffer.append("Authorization");
/* 118:    */     }
/* 119:146 */     buffer.append(": NTLM ");
/* 120:147 */     buffer.append(response);
/* 121:148 */     return new BufferedHeader(buffer);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isComplete()
/* 125:    */   {
/* 126:152 */     return (this.state == State.MSG_TYPE3_GENERATED) || (this.state == State.FAILED);
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NTLMScheme
 * JD-Core Version:    0.7.0.1
 */