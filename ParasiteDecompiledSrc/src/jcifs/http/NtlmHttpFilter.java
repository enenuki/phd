/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import javax.servlet.Filter;
/*   6:    */ import javax.servlet.FilterChain;
/*   7:    */ import javax.servlet.FilterConfig;
/*   8:    */ import javax.servlet.ServletException;
/*   9:    */ import javax.servlet.ServletRequest;
/*  10:    */ import javax.servlet.ServletResponse;
/*  11:    */ import javax.servlet.http.HttpServletRequest;
/*  12:    */ import javax.servlet.http.HttpServletResponse;
/*  13:    */ import javax.servlet.http.HttpSession;
/*  14:    */ import jcifs.Config;
/*  15:    */ import jcifs.UniAddress;
/*  16:    */ import jcifs.smb.NtlmChallenge;
/*  17:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  18:    */ import jcifs.smb.SmbAuthException;
/*  19:    */ import jcifs.smb.SmbSession;
/*  20:    */ import jcifs.util.Base64;
/*  21:    */ import jcifs.util.Hexdump;
/*  22:    */ import jcifs.util.LogStream;
/*  23:    */ 
/*  24:    */ public class NtlmHttpFilter
/*  25:    */   implements Filter
/*  26:    */ {
/*  27: 50 */   private static LogStream log = ;
/*  28:    */   private String defaultDomain;
/*  29:    */   private String domainController;
/*  30:    */   private boolean loadBalance;
/*  31:    */   private boolean enableBasic;
/*  32:    */   private boolean insecureBasic;
/*  33:    */   private String realm;
/*  34:    */   
/*  35:    */   public void init(FilterConfig filterConfig)
/*  36:    */     throws ServletException
/*  37:    */   {
/*  38: 65 */     Config.setProperty("jcifs.smb.client.soTimeout", "1800000");
/*  39: 66 */     Config.setProperty("jcifs.netbios.cachePolicy", "1200");
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 72 */     Config.setProperty("jcifs.smb.lmCompatibility", "0");
/*  46: 73 */     Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
/*  47:    */     
/*  48: 75 */     Enumeration e = filterConfig.getInitParameterNames();
/*  49: 76 */     while (e.hasMoreElements())
/*  50:    */     {
/*  51: 77 */       String name = (String)e.nextElement();
/*  52: 78 */       if (name.startsWith("jcifs.")) {
/*  53: 79 */         Config.setProperty(name, filterConfig.getInitParameter(name));
/*  54:    */       }
/*  55:    */     }
/*  56: 82 */     this.defaultDomain = Config.getProperty("jcifs.smb.client.domain");
/*  57: 83 */     this.domainController = Config.getProperty("jcifs.http.domainController");
/*  58: 84 */     if (this.domainController == null)
/*  59:    */     {
/*  60: 85 */       this.domainController = this.defaultDomain;
/*  61: 86 */       this.loadBalance = Config.getBoolean("jcifs.http.loadBalance", true);
/*  62:    */     }
/*  63: 88 */     this.enableBasic = Boolean.valueOf(Config.getProperty("jcifs.http.enableBasic")).booleanValue();
/*  64:    */     
/*  65: 90 */     this.insecureBasic = Boolean.valueOf(Config.getProperty("jcifs.http.insecureBasic")).booleanValue();
/*  66:    */     
/*  67: 92 */     this.realm = Config.getProperty("jcifs.http.basicRealm");
/*  68: 93 */     if (this.realm == null) {
/*  69: 93 */       this.realm = "jCIFS";
/*  70:    */     }
/*  71:    */     int level;
/*  72: 95 */     if ((level = Config.getInt("jcifs.util.loglevel", -1)) != -1) {
/*  73: 96 */       LogStream.setLevel(level);
/*  74:    */     }
/*  75: 98 */     if (LogStream.level > 2) {
/*  76:    */       try
/*  77:    */       {
/*  78:100 */         Config.store(log, "JCIFS PROPERTIES");
/*  79:    */       }
/*  80:    */       catch (IOException ioe) {}
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void destroy() {}
/*  85:    */   
/*  86:    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*  87:    */     throws IOException, ServletException
/*  88:    */   {
/*  89:117 */     HttpServletRequest req = (HttpServletRequest)request;
/*  90:118 */     HttpServletResponse resp = (HttpServletResponse)response;
/*  91:    */     NtlmPasswordAuthentication ntlm;
/*  92:121 */     if ((ntlm = negotiate(req, resp, false)) == null) {
/*  93:122 */       return;
/*  94:    */     }
/*  95:125 */     chain.doFilter(new NtlmHttpServletRequest(req, ntlm), response);
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected NtlmPasswordAuthentication negotiate(HttpServletRequest req, HttpServletResponse resp, boolean skipAuthentication)
/*  99:    */     throws IOException, ServletException
/* 100:    */   {
/* 101:144 */     NtlmPasswordAuthentication ntlm = null;
/* 102:145 */     String msg = req.getHeader("Authorization");
/* 103:146 */     boolean offerBasic = (this.enableBasic) && ((this.insecureBasic) || (req.isSecure()));
/* 104:148 */     if ((msg != null) && ((msg.startsWith("NTLM ")) || ((offerBasic) && (msg.startsWith("Basic ")))))
/* 105:    */     {
/* 106:    */       UniAddress dc;
/* 107:150 */       if (msg.startsWith("NTLM "))
/* 108:    */       {
/* 109:151 */         HttpSession ssn = req.getSession();
/* 110:    */         byte[] challenge;
/* 111:    */         byte[] challenge;
/* 112:154 */         if (this.loadBalance)
/* 113:    */         {
/* 114:155 */           NtlmChallenge chal = (NtlmChallenge)ssn.getAttribute("NtlmHttpChal");
/* 115:156 */           if (chal == null)
/* 116:    */           {
/* 117:157 */             chal = SmbSession.getChallengeForDomain();
/* 118:158 */             ssn.setAttribute("NtlmHttpChal", chal);
/* 119:    */           }
/* 120:160 */           UniAddress dc = chal.dc;
/* 121:161 */           challenge = chal.challenge;
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:163 */           UniAddress dc = UniAddress.getByName(this.domainController, true);
/* 126:164 */           challenge = SmbSession.getChallenge(dc);
/* 127:    */         }
/* 128:167 */         if ((ntlm = NtlmSsp.authenticate(req, resp, challenge)) == null) {
/* 129:168 */           return null;
/* 130:    */         }
/* 131:171 */         ssn.removeAttribute("NtlmHttpChal");
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:173 */         String auth = new String(Base64.decode(msg.substring(6)), "US-ASCII");
/* 136:    */         
/* 137:175 */         int index = auth.indexOf(':');
/* 138:176 */         String user = index != -1 ? auth.substring(0, index) : auth;
/* 139:177 */         String password = index != -1 ? auth.substring(index + 1) : "";
/* 140:    */         
/* 141:179 */         index = user.indexOf('\\');
/* 142:180 */         if (index == -1) {
/* 143:180 */           index = user.indexOf('/');
/* 144:    */         }
/* 145:181 */         String domain = index != -1 ? user.substring(0, index) : this.defaultDomain;
/* 146:    */         
/* 147:183 */         user = index != -1 ? user.substring(index + 1) : user;
/* 148:184 */         ntlm = new NtlmPasswordAuthentication(domain, user, password);
/* 149:185 */         dc = UniAddress.getByName(this.domainController, true);
/* 150:    */       }
/* 151:    */       try
/* 152:    */       {
/* 153:189 */         SmbSession.logon(dc, ntlm);
/* 154:191 */         if (LogStream.level > 2) {
/* 155:192 */           log.println("NtlmHttpFilter: " + ntlm + " successfully authenticated against " + dc);
/* 156:    */         }
/* 157:    */       }
/* 158:    */       catch (SmbAuthException sae)
/* 159:    */       {
/* 160:196 */         if (LogStream.level > 1) {
/* 161:197 */           log.println("NtlmHttpFilter: " + ntlm.getName() + ": 0x" + Hexdump.toHexString(sae.getNtStatus(), 8) + ": " + sae);
/* 162:    */         }
/* 163:201 */         if (sae.getNtStatus() == -1073741819)
/* 164:    */         {
/* 165:205 */           HttpSession ssn = req.getSession(false);
/* 166:206 */           if (ssn != null) {
/* 167:207 */             ssn.removeAttribute("NtlmHttpAuth");
/* 168:    */           }
/* 169:    */         }
/* 170:210 */         resp.setHeader("WWW-Authenticate", "NTLM");
/* 171:211 */         if (offerBasic) {
/* 172:212 */           resp.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 173:    */         }
/* 174:215 */         resp.setStatus(401);
/* 175:216 */         resp.setContentLength(0);
/* 176:217 */         resp.flushBuffer();
/* 177:218 */         return null;
/* 178:    */       }
/* 179:220 */       req.getSession().setAttribute("NtlmHttpAuth", ntlm);
/* 180:    */     }
/* 181:222 */     else if (!skipAuthentication)
/* 182:    */     {
/* 183:223 */       HttpSession ssn = req.getSession(false);
/* 184:224 */       if ((ssn == null) || ((ntlm = (NtlmPasswordAuthentication)ssn.getAttribute("NtlmHttpAuth")) == null))
/* 185:    */       {
/* 186:226 */         resp.setHeader("WWW-Authenticate", "NTLM");
/* 187:227 */         if (offerBasic) {
/* 188:228 */           resp.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 189:    */         }
/* 190:231 */         resp.setStatus(401);
/* 191:232 */         resp.setContentLength(0);
/* 192:233 */         resp.flushBuffer();
/* 193:234 */         return null;
/* 194:    */       }
/* 195:    */     }
/* 196:239 */     return ntlm;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setFilterConfig(FilterConfig f)
/* 200:    */   {
/* 201:    */     try
/* 202:    */     {
/* 203:245 */       init(f);
/* 204:    */     }
/* 205:    */     catch (Exception e)
/* 206:    */     {
/* 207:247 */       e.printStackTrace();
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public FilterConfig getFilterConfig()
/* 212:    */   {
/* 213:251 */     return null;
/* 214:    */   }
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NtlmHttpFilter
 * JD-Core Version:    0.7.0.1
 */