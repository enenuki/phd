/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import javax.servlet.ServletConfig;
/*   6:    */ import javax.servlet.ServletException;
/*   7:    */ import javax.servlet.http.HttpServlet;
/*   8:    */ import javax.servlet.http.HttpServletRequest;
/*   9:    */ import javax.servlet.http.HttpServletResponse;
/*  10:    */ import javax.servlet.http.HttpSession;
/*  11:    */ import jcifs.Config;
/*  12:    */ import jcifs.UniAddress;
/*  13:    */ import jcifs.netbios.NbtAddress;
/*  14:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  15:    */ import jcifs.smb.SmbAuthException;
/*  16:    */ import jcifs.smb.SmbSession;
/*  17:    */ import jcifs.util.Base64;
/*  18:    */ 
/*  19:    */ public abstract class NtlmServlet
/*  20:    */   extends HttpServlet
/*  21:    */ {
/*  22:    */   private String defaultDomain;
/*  23:    */   private String domainController;
/*  24:    */   private boolean loadBalance;
/*  25:    */   private boolean enableBasic;
/*  26:    */   private boolean insecureBasic;
/*  27:    */   private String realm;
/*  28:    */   
/*  29:    */   public void init(ServletConfig config)
/*  30:    */     throws ServletException
/*  31:    */   {
/*  32: 75 */     super.init(config);
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36: 79 */     Config.setProperty("jcifs.smb.client.soTimeout", "300000");
/*  37: 80 */     Config.setProperty("jcifs.netbios.cachePolicy", "600");
/*  38:    */     
/*  39: 82 */     Enumeration e = config.getInitParameterNames();
/*  40: 84 */     while (e.hasMoreElements())
/*  41:    */     {
/*  42: 85 */       String name = (String)e.nextElement();
/*  43: 86 */       if (name.startsWith("jcifs.")) {
/*  44: 87 */         Config.setProperty(name, config.getInitParameter(name));
/*  45:    */       }
/*  46:    */     }
/*  47: 90 */     this.defaultDomain = Config.getProperty("jcifs.smb.client.domain");
/*  48: 91 */     this.domainController = Config.getProperty("jcifs.http.domainController");
/*  49: 92 */     if (this.domainController == null)
/*  50:    */     {
/*  51: 93 */       this.domainController = this.defaultDomain;
/*  52: 94 */       this.loadBalance = Config.getBoolean("jcifs.http.loadBalance", true);
/*  53:    */     }
/*  54: 96 */     this.enableBasic = Boolean.valueOf(Config.getProperty("jcifs.http.enableBasic")).booleanValue();
/*  55:    */     
/*  56: 98 */     this.insecureBasic = Boolean.valueOf(Config.getProperty("jcifs.http.insecureBasic")).booleanValue();
/*  57:    */     
/*  58:100 */     this.realm = Config.getProperty("jcifs.http.basicRealm");
/*  59:101 */     if (this.realm == null) {
/*  60:101 */       this.realm = "jCIFS";
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*  65:    */     throws ServletException, IOException
/*  66:    */   {
/*  67:107 */     boolean offerBasic = (this.enableBasic) && ((this.insecureBasic) || (request.isSecure()));
/*  68:    */     
/*  69:109 */     String msg = request.getHeader("Authorization");
/*  70:110 */     if ((msg != null) && ((msg.startsWith("NTLM ")) || ((offerBasic) && (msg.startsWith("Basic ")))))
/*  71:    */     {
/*  72:    */       UniAddress dc;
/*  73:    */       UniAddress dc;
/*  74:112 */       if (this.loadBalance) {
/*  75:113 */         dc = new UniAddress(NbtAddress.getByName(this.domainController, 28, null));
/*  76:    */       } else {
/*  77:115 */         dc = UniAddress.getByName(this.domainController, true);
/*  78:    */       }
/*  79:    */       NtlmPasswordAuthentication ntlm;
/*  80:118 */       if (msg.startsWith("NTLM "))
/*  81:    */       {
/*  82:119 */         byte[] challenge = SmbSession.getChallenge(dc);
/*  83:120 */         NtlmPasswordAuthentication ntlm = NtlmSsp.authenticate(request, response, challenge);
/*  84:121 */         if (ntlm == null) {
/*  85:121 */           return;
/*  86:    */         }
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:123 */         String auth = new String(Base64.decode(msg.substring(6)), "US-ASCII");
/*  91:    */         
/*  92:125 */         int index = auth.indexOf(':');
/*  93:126 */         String user = index != -1 ? auth.substring(0, index) : auth;
/*  94:127 */         String password = index != -1 ? auth.substring(index + 1) : "";
/*  95:    */         
/*  96:129 */         index = user.indexOf('\\');
/*  97:130 */         if (index == -1) {
/*  98:130 */           index = user.indexOf('/');
/*  99:    */         }
/* 100:131 */         String domain = index != -1 ? user.substring(0, index) : this.defaultDomain;
/* 101:    */         
/* 102:133 */         user = index != -1 ? user.substring(index + 1) : user;
/* 103:134 */         ntlm = new NtlmPasswordAuthentication(domain, user, password);
/* 104:    */       }
/* 105:    */       try
/* 106:    */       {
/* 107:137 */         SmbSession.logon(dc, ntlm);
/* 108:    */       }
/* 109:    */       catch (SmbAuthException sae)
/* 110:    */       {
/* 111:139 */         response.setHeader("WWW-Authenticate", "NTLM");
/* 112:140 */         if (offerBasic) {
/* 113:141 */           response.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 114:    */         }
/* 115:144 */         response.setHeader("Connection", "close");
/* 116:145 */         response.setStatus(401);
/* 117:146 */         response.flushBuffer();
/* 118:147 */         return;
/* 119:    */       }
/* 120:149 */       HttpSession ssn = request.getSession();
/* 121:150 */       ssn.setAttribute("NtlmHttpAuth", ntlm);
/* 122:151 */       ssn.setAttribute("ntlmdomain", ntlm.getDomain());
/* 123:152 */       ssn.setAttribute("ntlmuser", ntlm.getUsername());
/* 124:    */     }
/* 125:    */     else
/* 126:    */     {
/* 127:154 */       HttpSession ssn = request.getSession(false);
/* 128:155 */       if ((ssn == null) || (ssn.getAttribute("NtlmHttpAuth") == null))
/* 129:    */       {
/* 130:156 */         response.setHeader("WWW-Authenticate", "NTLM");
/* 131:157 */         if (offerBasic) {
/* 132:158 */           response.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 133:    */         }
/* 134:161 */         response.setStatus(401);
/* 135:162 */         response.flushBuffer();
/* 136:163 */         return;
/* 137:    */       }
/* 138:    */     }
/* 139:166 */     super.service(request, response);
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NtlmServlet
 * JD-Core Version:    0.7.0.1
 */