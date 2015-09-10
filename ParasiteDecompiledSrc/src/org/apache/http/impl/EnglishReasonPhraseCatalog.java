/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.ReasonPhraseCatalog;
/*   5:    */ 
/*   6:    */ public class EnglishReasonPhraseCatalog
/*   7:    */   implements ReasonPhraseCatalog
/*   8:    */ {
/*   9: 51 */   public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
/*  10:    */   
/*  11:    */   public String getReason(int status, Locale loc)
/*  12:    */   {
/*  13: 73 */     if ((status < 100) || (status >= 600)) {
/*  14: 74 */       throw new IllegalArgumentException("Unknown category for status code " + status + ".");
/*  15:    */     }
/*  16: 78 */     int category = status / 100;
/*  17: 79 */     int subcode = status - 100 * category;
/*  18:    */     
/*  19: 81 */     String reason = null;
/*  20: 82 */     if (REASON_PHRASES[category].length > subcode) {
/*  21: 83 */       reason = REASON_PHRASES[category][subcode];
/*  22:    */     }
/*  23: 85 */     return reason;
/*  24:    */   }
/*  25:    */   
/*  26: 90 */   private static final String[][] REASON_PHRASES = { null, new String[3], new String[8], new String[8], new String[25], new String[8] };
/*  27:    */   
/*  28:    */   private static void setReason(int status, String reason)
/*  29:    */   {
/*  30:109 */     int category = status / 100;
/*  31:110 */     int subcode = status - 100 * category;
/*  32:111 */     REASON_PHRASES[category][subcode] = reason;
/*  33:    */   }
/*  34:    */   
/*  35:    */   static
/*  36:    */   {
/*  37:120 */     setReason(200, "OK");
/*  38:    */     
/*  39:122 */     setReason(201, "Created");
/*  40:    */     
/*  41:124 */     setReason(202, "Accepted");
/*  42:    */     
/*  43:126 */     setReason(204, "No Content");
/*  44:    */     
/*  45:128 */     setReason(301, "Moved Permanently");
/*  46:    */     
/*  47:130 */     setReason(302, "Moved Temporarily");
/*  48:    */     
/*  49:132 */     setReason(304, "Not Modified");
/*  50:    */     
/*  51:134 */     setReason(400, "Bad Request");
/*  52:    */     
/*  53:136 */     setReason(401, "Unauthorized");
/*  54:    */     
/*  55:138 */     setReason(403, "Forbidden");
/*  56:    */     
/*  57:140 */     setReason(404, "Not Found");
/*  58:    */     
/*  59:142 */     setReason(500, "Internal Server Error");
/*  60:    */     
/*  61:144 */     setReason(501, "Not Implemented");
/*  62:    */     
/*  63:146 */     setReason(502, "Bad Gateway");
/*  64:    */     
/*  65:148 */     setReason(503, "Service Unavailable");
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:152 */     setReason(100, "Continue");
/*  70:    */     
/*  71:154 */     setReason(307, "Temporary Redirect");
/*  72:    */     
/*  73:156 */     setReason(405, "Method Not Allowed");
/*  74:    */     
/*  75:158 */     setReason(409, "Conflict");
/*  76:    */     
/*  77:160 */     setReason(412, "Precondition Failed");
/*  78:    */     
/*  79:162 */     setReason(413, "Request Too Long");
/*  80:    */     
/*  81:164 */     setReason(414, "Request-URI Too Long");
/*  82:    */     
/*  83:166 */     setReason(415, "Unsupported Media Type");
/*  84:    */     
/*  85:168 */     setReason(300, "Multiple Choices");
/*  86:    */     
/*  87:170 */     setReason(303, "See Other");
/*  88:    */     
/*  89:172 */     setReason(305, "Use Proxy");
/*  90:    */     
/*  91:174 */     setReason(402, "Payment Required");
/*  92:    */     
/*  93:176 */     setReason(406, "Not Acceptable");
/*  94:    */     
/*  95:178 */     setReason(407, "Proxy Authentication Required");
/*  96:    */     
/*  97:180 */     setReason(408, "Request Timeout");
/*  98:    */     
/*  99:    */ 
/* 100:183 */     setReason(101, "Switching Protocols");
/* 101:    */     
/* 102:185 */     setReason(203, "Non Authoritative Information");
/* 103:    */     
/* 104:187 */     setReason(205, "Reset Content");
/* 105:    */     
/* 106:189 */     setReason(206, "Partial Content");
/* 107:    */     
/* 108:191 */     setReason(504, "Gateway Timeout");
/* 109:    */     
/* 110:193 */     setReason(505, "Http Version Not Supported");
/* 111:    */     
/* 112:195 */     setReason(410, "Gone");
/* 113:    */     
/* 114:197 */     setReason(411, "Length Required");
/* 115:    */     
/* 116:199 */     setReason(416, "Requested Range Not Satisfiable");
/* 117:    */     
/* 118:201 */     setReason(417, "Expectation Failed");
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:205 */     setReason(102, "Processing");
/* 123:    */     
/* 124:207 */     setReason(207, "Multi-Status");
/* 125:    */     
/* 126:209 */     setReason(422, "Unprocessable Entity");
/* 127:    */     
/* 128:211 */     setReason(419, "Insufficient Space On Resource");
/* 129:    */     
/* 130:213 */     setReason(420, "Method Failure");
/* 131:    */     
/* 132:215 */     setReason(423, "Locked");
/* 133:    */     
/* 134:217 */     setReason(507, "Insufficient Storage");
/* 135:    */     
/* 136:219 */     setReason(424, "Failed Dependency");
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.EnglishReasonPhraseCatalog
 * JD-Core Version:    0.7.0.1
 */