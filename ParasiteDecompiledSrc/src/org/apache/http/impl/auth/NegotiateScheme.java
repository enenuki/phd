/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.codec.binary.Base64;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.http.Header;
/*   8:    */ import org.apache.http.HttpHost;
/*   9:    */ import org.apache.http.HttpRequest;
/*  10:    */ import org.apache.http.auth.AuthenticationException;
/*  11:    */ import org.apache.http.auth.Credentials;
/*  12:    */ import org.apache.http.auth.InvalidCredentialsException;
/*  13:    */ import org.apache.http.auth.MalformedChallengeException;
/*  14:    */ import org.apache.http.message.BasicHeader;
/*  15:    */ import org.apache.http.protocol.HttpContext;
/*  16:    */ import org.apache.http.util.CharArrayBuffer;
/*  17:    */ import org.ietf.jgss.GSSContext;
/*  18:    */ import org.ietf.jgss.GSSException;
/*  19:    */ import org.ietf.jgss.GSSManager;
/*  20:    */ import org.ietf.jgss.GSSName;
/*  21:    */ import org.ietf.jgss.Oid;
/*  22:    */ 
/*  23:    */ public class NegotiateScheme
/*  24:    */   extends AuthSchemeBase
/*  25:    */ {
/*  26:    */   private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
/*  27:    */   private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
/*  28:    */   
/*  29:    */   static enum State
/*  30:    */   {
/*  31: 59 */     UNINITIATED,  CHALLENGE_RECEIVED,  TOKEN_GENERATED,  FAILED;
/*  32:    */     
/*  33:    */     private State() {}
/*  34:    */   }
/*  35:    */   
/*  36: 68 */   private final Log log = LogFactory.getLog(getClass());
/*  37:    */   private final SpnegoTokenGenerator spengoGenerator;
/*  38:    */   private final boolean stripPort;
/*  39: 74 */   private GSSContext gssContext = null;
/*  40:    */   private State state;
/*  41:    */   private byte[] token;
/*  42: 82 */   private Oid negotiationOid = null;
/*  43:    */   
/*  44:    */   public NegotiateScheme(SpnegoTokenGenerator spengoGenerator, boolean stripPort)
/*  45:    */   {
/*  46: 90 */     this.state = State.UNINITIATED;
/*  47: 91 */     this.spengoGenerator = spengoGenerator;
/*  48: 92 */     this.stripPort = stripPort;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public NegotiateScheme(SpnegoTokenGenerator spengoGenerator)
/*  52:    */   {
/*  53: 96 */     this(spengoGenerator, false);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public NegotiateScheme()
/*  57:    */   {
/*  58:100 */     this(null, false);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isComplete()
/*  62:    */   {
/*  63:111 */     return (this.state == State.TOKEN_GENERATED) || (this.state == State.FAILED);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getSchemeName()
/*  67:    */   {
/*  68:120 */     return "Negotiate";
/*  69:    */   }
/*  70:    */   
/*  71:    */   @Deprecated
/*  72:    */   public Header authenticate(Credentials credentials, HttpRequest request)
/*  73:    */     throws AuthenticationException
/*  74:    */   {
/*  75:127 */     return authenticate(credentials, request, null);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected GSSManager getManager()
/*  79:    */   {
/*  80:131 */     return GSSManager.getInstance();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Header authenticate(Credentials credentials, HttpRequest request, HttpContext context)
/*  84:    */     throws AuthenticationException
/*  85:    */   {
/*  86:152 */     if (request == null) {
/*  87:153 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  88:    */     }
/*  89:155 */     if (this.state != State.CHALLENGE_RECEIVED) {
/*  90:156 */       throw new IllegalStateException("Negotiation authentication process has not been initiated");
/*  91:    */     }
/*  92:    */     try
/*  93:    */     {
/*  94:160 */       String key = null;
/*  95:161 */       if (isProxy()) {
/*  96:162 */         key = "http.proxy_host";
/*  97:    */       } else {
/*  98:164 */         key = "http.target_host";
/*  99:    */       }
/* 100:166 */       HttpHost host = (HttpHost)context.getAttribute(key);
/* 101:167 */       if (host == null) {
/* 102:168 */         throw new AuthenticationException("Authentication host is not set in the execution context");
/* 103:    */       }
/* 104:    */       String authServer;
/* 105:    */       String authServer;
/* 106:172 */       if ((!this.stripPort) && (host.getPort() > 0)) {
/* 107:173 */         authServer = host.toHostString();
/* 108:    */       } else {
/* 109:175 */         authServer = host.getHostName();
/* 110:    */       }
/* 111:178 */       if (this.log.isDebugEnabled()) {
/* 112:179 */         this.log.debug("init " + authServer);
/* 113:    */       }
/* 114:196 */       this.negotiationOid = new Oid("1.3.6.1.5.5.2");
/* 115:    */       
/* 116:198 */       boolean tryKerberos = false;
/* 117:    */       try
/* 118:    */       {
/* 119:200 */         GSSManager manager = getManager();
/* 120:201 */         GSSName serverName = manager.createName("HTTP@" + authServer, GSSName.NT_HOSTBASED_SERVICE);
/* 121:202 */         this.gssContext = manager.createContext(serverName.canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
/* 122:    */         
/* 123:    */ 
/* 124:205 */         this.gssContext.requestMutualAuth(true);
/* 125:206 */         this.gssContext.requestCredDeleg(true);
/* 126:    */       }
/* 127:    */       catch (GSSException ex)
/* 128:    */       {
/* 129:210 */         if (ex.getMajor() == 2)
/* 130:    */         {
/* 131:211 */           this.log.debug("GSSException BAD_MECH, retry with Kerberos MECH");
/* 132:212 */           tryKerberos = true;
/* 133:    */         }
/* 134:    */         else
/* 135:    */         {
/* 136:214 */           throw ex;
/* 137:    */         }
/* 138:    */       }
/* 139:218 */       if (tryKerberos)
/* 140:    */       {
/* 141:220 */         this.log.debug("Using Kerberos MECH 1.2.840.113554.1.2.2");
/* 142:221 */         this.negotiationOid = new Oid("1.2.840.113554.1.2.2");
/* 143:222 */         GSSManager manager = getManager();
/* 144:223 */         GSSName serverName = manager.createName("HTTP@" + authServer, GSSName.NT_HOSTBASED_SERVICE);
/* 145:224 */         this.gssContext = manager.createContext(serverName.canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
/* 146:    */         
/* 147:    */ 
/* 148:227 */         this.gssContext.requestMutualAuth(true);
/* 149:228 */         this.gssContext.requestCredDeleg(true);
/* 150:    */       }
/* 151:230 */       if (this.token == null) {
/* 152:231 */         this.token = new byte[0];
/* 153:    */       }
/* 154:233 */       this.token = this.gssContext.initSecContext(this.token, 0, this.token.length);
/* 155:234 */       if (this.token == null)
/* 156:    */       {
/* 157:235 */         this.state = State.FAILED;
/* 158:236 */         throw new AuthenticationException("GSS security context initialization failed");
/* 159:    */       }
/* 160:243 */       if ((this.spengoGenerator != null) && (this.negotiationOid.toString().equals("1.2.840.113554.1.2.2"))) {
/* 161:244 */         this.token = this.spengoGenerator.generateSpnegoDERObject(this.token);
/* 162:    */       }
/* 163:247 */       this.state = State.TOKEN_GENERATED;
/* 164:248 */       String tokenstr = new String(Base64.encodeBase64(this.token, false));
/* 165:249 */       if (this.log.isDebugEnabled()) {
/* 166:250 */         this.log.debug("Sending response '" + tokenstr + "' back to the auth server");
/* 167:    */       }
/* 168:252 */       return new BasicHeader("Authorization", "Negotiate " + tokenstr);
/* 169:    */     }
/* 170:    */     catch (GSSException gsse)
/* 171:    */     {
/* 172:254 */       this.state = State.FAILED;
/* 173:255 */       if ((gsse.getMajor() == 9) || (gsse.getMajor() == 8)) {
/* 174:257 */         throw new InvalidCredentialsException(gsse.getMessage(), gsse);
/* 175:    */       }
/* 176:258 */       if (gsse.getMajor() == 13) {
/* 177:259 */         throw new InvalidCredentialsException(gsse.getMessage(), gsse);
/* 178:    */       }
/* 179:260 */       if ((gsse.getMajor() == 10) || (gsse.getMajor() == 19) || (gsse.getMajor() == 20)) {
/* 180:263 */         throw new AuthenticationException(gsse.getMessage(), gsse);
/* 181:    */       }
/* 182:265 */       throw new AuthenticationException(gsse.getMessage());
/* 183:    */     }
/* 184:    */     catch (IOException ex)
/* 185:    */     {
/* 186:267 */       this.state = State.FAILED;
/* 187:268 */       throw new AuthenticationException(ex.getMessage());
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getParameter(String name)
/* 192:    */   {
/* 193:284 */     if (name == null) {
/* 194:285 */       throw new IllegalArgumentException("Parameter name may not be null");
/* 195:    */     }
/* 196:287 */     return null;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getRealm()
/* 200:    */   {
/* 201:297 */     return null;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isConnectionBased()
/* 205:    */   {
/* 206:307 */     return true;
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void parseChallenge(CharArrayBuffer buffer, int beginIndex, int endIndex)
/* 210:    */     throws MalformedChallengeException
/* 211:    */   {
/* 212:314 */     String challenge = buffer.substringTrimmed(beginIndex, endIndex);
/* 213:315 */     if (this.log.isDebugEnabled()) {
/* 214:316 */       this.log.debug("Received challenge '" + challenge + "' from the auth server");
/* 215:    */     }
/* 216:318 */     if (this.state == State.UNINITIATED)
/* 217:    */     {
/* 218:319 */       this.token = new Base64().decode(challenge.getBytes());
/* 219:320 */       this.state = State.CHALLENGE_RECEIVED;
/* 220:    */     }
/* 221:    */     else
/* 222:    */     {
/* 223:322 */       this.log.debug("Authentication already attempted");
/* 224:323 */       this.state = State.FAILED;
/* 225:    */     }
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NegotiateScheme
 * JD-Core Version:    0.7.0.1
 */