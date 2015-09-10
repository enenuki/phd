/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import org.apache.http.auth.AuthScope;
/*   9:    */ import org.apache.http.auth.Credentials;
/*  10:    */ import org.apache.http.auth.NTCredentials;
/*  11:    */ import org.apache.http.auth.UsernamePasswordCredentials;
/*  12:    */ import org.apache.http.client.CredentialsProvider;
/*  13:    */ 
/*  14:    */ public class DefaultCredentialsProvider
/*  15:    */   implements CredentialsProvider, Serializable
/*  16:    */ {
/*  17:    */   private final HashMap<AuthScopeProxy, CredentialsFactory> credentialsMap_;
/*  18:    */   
/*  19:    */   public DefaultCredentialsProvider()
/*  20:    */   {
/*  21: 44 */     this.credentialsMap_ = new HashMap();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void addCredentials(String username, String password)
/*  25:    */   {
/*  26: 57 */     addCredentials(username, password, AuthScope.ANY_HOST, -1, AuthScope.ANY_REALM);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void addCredentials(String username, String password, String host, int port, String realm)
/*  30:    */   {
/*  31: 72 */     AuthScope authscope = new AuthScope(host, port, realm, AuthScope.ANY_SCHEME);
/*  32: 73 */     Credentials credentials = new UsernamePasswordCredentials(username, password);
/*  33: 74 */     setCredentials(authscope, credentials);
/*  34:    */   }
/*  35:    */   
/*  36:    */   @Deprecated
/*  37:    */   public void addProxyCredentials(String username, String password)
/*  38:    */   {
/*  39: 85 */     addCredentials(username, password);
/*  40:    */   }
/*  41:    */   
/*  42:    */   @Deprecated
/*  43:    */   public void addProxyCredentials(String username, String password, String host, int port)
/*  44:    */   {
/*  45: 98 */     addCredentials(username, password, host, port, AuthScope.ANY_REALM);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void addNTLMCredentials(String username, String password, String host, int port, String workstation, String domain)
/*  49:    */   {
/*  50:115 */     AuthScope authscope = new AuthScope(host, port, AuthScope.ANY_REALM, AuthScope.ANY_SCHEME);
/*  51:116 */     Credentials credentials = new NTCredentials(username, password, workstation, domain);
/*  52:117 */     setCredentials(authscope, credentials);
/*  53:    */   }
/*  54:    */   
/*  55:    */   @Deprecated
/*  56:    */   public void addNTLMProxyCredentials(String username, String password, String host, int port, String workstation, String domain)
/*  57:    */   {
/*  58:136 */     addNTLMCredentials(username, password, host, port, workstation, domain);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized void setCredentials(AuthScope authscope, Credentials credentials)
/*  62:    */   {
/*  63:143 */     if (authscope == null) {
/*  64:144 */       throw new IllegalArgumentException("Authentication scope may not be null");
/*  65:    */     }
/*  66:    */     CredentialsFactory factory;
/*  67:147 */     if ((credentials instanceof UsernamePasswordCredentials))
/*  68:    */     {
/*  69:148 */       UsernamePasswordCredentials userCredentials = (UsernamePasswordCredentials)credentials;
/*  70:149 */       factory = new UsernamePasswordCredentialsFactory(userCredentials.getUserName(), userCredentials.getPassword());
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:    */       CredentialsFactory factory;
/*  75:152 */       if ((credentials instanceof NTCredentials))
/*  76:    */       {
/*  77:153 */         NTCredentials ntCredentials = (NTCredentials)credentials;
/*  78:154 */         factory = new NTCredentialsFactory(ntCredentials.getUserName(), ntCredentials.getPassword(), ntCredentials.getWorkstation(), ntCredentials.getDomain());
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82:158 */         throw new IllegalArgumentException("Unsupported Credential type: " + credentials.getClass().getName());
/*  83:    */       }
/*  84:    */     }
/*  85:    */     CredentialsFactory factory;
/*  86:160 */     this.credentialsMap_.put(new AuthScopeProxy(authscope), factory);
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static Credentials matchCredentials(HashMap<AuthScopeProxy, CredentialsFactory> map, AuthScope authscope)
/*  90:    */   {
/*  91:172 */     CredentialsFactory factory = (CredentialsFactory)map.get(new AuthScopeProxy(authscope));
/*  92:173 */     Credentials creds = null;
/*  93:174 */     if (factory == null)
/*  94:    */     {
/*  95:175 */       int bestMatchFactor = -1;
/*  96:176 */       AuthScope bestMatch = null;
/*  97:177 */       for (AuthScopeProxy proxy : map.keySet())
/*  98:    */       {
/*  99:178 */         AuthScope current = proxy.getAuthScope();
/* 100:179 */         int factor = authscope.match(current);
/* 101:180 */         if (factor > bestMatchFactor)
/* 102:    */         {
/* 103:181 */           bestMatchFactor = factor;
/* 104:182 */           bestMatch = current;
/* 105:    */         }
/* 106:    */       }
/* 107:185 */       if (bestMatch != null) {
/* 108:186 */         creds = ((CredentialsFactory)map.get(new AuthScopeProxy(bestMatch))).getInstance();
/* 109:    */       }
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:190 */       creds = factory.getInstance();
/* 114:    */     }
/* 115:192 */     return creds;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public synchronized Credentials getCredentials(AuthScope authscope)
/* 119:    */   {
/* 120:199 */     if (authscope == null) {
/* 121:200 */       throw new IllegalArgumentException("Authentication scope may not be null");
/* 122:    */     }
/* 123:202 */     return matchCredentials(this.credentialsMap_, authscope);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String toString()
/* 127:    */   {
/* 128:210 */     return this.credentialsMap_.toString();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public synchronized void clear()
/* 132:    */   {
/* 133:217 */     this.credentialsMap_.clear();
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static class AuthScopeProxy
/* 137:    */     implements Serializable
/* 138:    */   {
/* 139:    */     private AuthScope authScope_;
/* 140:    */     
/* 141:    */     public AuthScopeProxy(AuthScope authScope)
/* 142:    */     {
/* 143:227 */       this.authScope_ = authScope;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public AuthScope getAuthScope()
/* 147:    */     {
/* 148:230 */       return this.authScope_;
/* 149:    */     }
/* 150:    */     
/* 151:    */     private void writeObject(ObjectOutputStream stream)
/* 152:    */       throws IOException
/* 153:    */     {
/* 154:233 */       stream.writeObject(this.authScope_.getHost());
/* 155:234 */       stream.writeInt(this.authScope_.getPort());
/* 156:235 */       stream.writeObject(this.authScope_.getRealm());
/* 157:236 */       stream.writeObject(this.authScope_.getScheme());
/* 158:    */     }
/* 159:    */     
/* 160:    */     private void readObject(ObjectInputStream stream)
/* 161:    */       throws IOException, ClassNotFoundException
/* 162:    */     {
/* 163:239 */       String host = (String)stream.readObject();
/* 164:240 */       int port = stream.readInt();
/* 165:241 */       String realm = (String)stream.readObject();
/* 166:242 */       String scheme = (String)stream.readObject();
/* 167:243 */       this.authScope_ = new AuthScope(host, port, realm, scheme);
/* 168:    */     }
/* 169:    */     
/* 170:    */     public int hashCode()
/* 171:    */     {
/* 172:247 */       return this.authScope_.hashCode();
/* 173:    */     }
/* 174:    */     
/* 175:    */     public boolean equals(Object obj)
/* 176:    */     {
/* 177:251 */       return ((obj instanceof AuthScopeProxy)) && (this.authScope_.equals(((AuthScopeProxy)obj).getAuthScope()));
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static class UsernamePasswordCredentialsFactory
/* 182:    */     implements DefaultCredentialsProvider.CredentialsFactory, Serializable
/* 183:    */   {
/* 184:    */     private String username_;
/* 185:    */     private String password_;
/* 186:    */     
/* 187:    */     public UsernamePasswordCredentialsFactory(String username, String password)
/* 188:    */     {
/* 189:263 */       this.username_ = username;
/* 190:264 */       this.password_ = password;
/* 191:    */     }
/* 192:    */     
/* 193:    */     public Credentials getInstance()
/* 194:    */     {
/* 195:268 */       return new UsernamePasswordCredentials(this.username_, this.password_);
/* 196:    */     }
/* 197:    */     
/* 198:    */     public String toString()
/* 199:    */     {
/* 200:272 */       return getInstance().toString();
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   private static class NTCredentialsFactory
/* 205:    */     implements DefaultCredentialsProvider.CredentialsFactory, Serializable
/* 206:    */   {
/* 207:    */     private String username_;
/* 208:    */     private String password_;
/* 209:    */     private String workstation_;
/* 210:    */     private String domain_;
/* 211:    */     
/* 212:    */     public NTCredentialsFactory(String username, String password, String workstation, String domain)
/* 213:    */     {
/* 214:287 */       this.username_ = username;
/* 215:288 */       this.password_ = password;
/* 216:289 */       this.workstation_ = workstation;
/* 217:290 */       this.domain_ = domain;
/* 218:    */     }
/* 219:    */     
/* 220:    */     public Credentials getInstance()
/* 221:    */     {
/* 222:294 */       return new NTCredentials(this.username_, this.password_, this.workstation_, this.domain_);
/* 223:    */     }
/* 224:    */     
/* 225:    */     public String toString()
/* 226:    */     {
/* 227:298 */       return getInstance().toString();
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   private static abstract interface CredentialsFactory
/* 232:    */   {
/* 233:    */     public abstract Credentials getInstance();
/* 234:    */   }
/* 235:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.DefaultCredentialsProvider
 * JD-Core Version:    0.7.0.1
 */