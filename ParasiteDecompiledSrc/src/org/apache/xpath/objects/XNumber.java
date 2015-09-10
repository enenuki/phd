/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.XPathVisitor;
/*   8:    */ 
/*   9:    */ public class XNumber
/*  10:    */   extends XObject
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -2720400709619020193L;
/*  13:    */   double m_val;
/*  14:    */   
/*  15:    */   public XNumber(double d)
/*  16:    */   {
/*  17: 49 */     this.m_val = d;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public XNumber(Number num)
/*  21:    */   {
/*  22: 62 */     this.m_val = num.doubleValue();
/*  23: 63 */     setObject(num);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getType()
/*  27:    */   {
/*  28: 73 */     return 2;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getTypeString()
/*  32:    */   {
/*  33: 84 */     return "#NUMBER";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public double num()
/*  37:    */   {
/*  38: 94 */     return this.m_val;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public double num(XPathContext xctxt)
/*  42:    */     throws TransformerException
/*  43:    */   {
/*  44:108 */     return this.m_val;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean bool()
/*  48:    */   {
/*  49:118 */     return (!Double.isNaN(this.m_val)) && (this.m_val != 0.0D);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String str()
/*  53:    */   {
/*  54:280 */     if (Double.isNaN(this.m_val)) {
/*  55:282 */       return "NaN";
/*  56:    */     }
/*  57:284 */     if (Double.isInfinite(this.m_val))
/*  58:    */     {
/*  59:286 */       if (this.m_val > 0.0D) {
/*  60:287 */         return "Infinity";
/*  61:    */       }
/*  62:289 */       return "-Infinity";
/*  63:    */     }
/*  64:292 */     double num = this.m_val;
/*  65:293 */     String s = Double.toString(num);
/*  66:294 */     int len = s.length();
/*  67:296 */     if ((s.charAt(len - 2) == '.') && (s.charAt(len - 1) == '0'))
/*  68:    */     {
/*  69:298 */       s = s.substring(0, len - 2);
/*  70:300 */       if (s.equals("-0")) {
/*  71:301 */         return "0";
/*  72:    */       }
/*  73:303 */       return s;
/*  74:    */     }
/*  75:306 */     int e = s.indexOf('E');
/*  76:308 */     if (e < 0)
/*  77:    */     {
/*  78:310 */       if (s.charAt(len - 1) == '0') {
/*  79:311 */         return s.substring(0, len - 1);
/*  80:    */       }
/*  81:313 */       return s;
/*  82:    */     }
/*  83:316 */     int exp = Integer.parseInt(s.substring(e + 1));
/*  84:    */     String sign;
/*  85:319 */     if (s.charAt(0) == '-')
/*  86:    */     {
/*  87:321 */       sign = "-";
/*  88:322 */       s = s.substring(1);
/*  89:    */       
/*  90:324 */       e--;
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:327 */       sign = "";
/*  95:    */     }
/*  96:329 */     int nDigits = e - 2;
/*  97:331 */     if (exp >= nDigits) {
/*  98:332 */       return sign + s.substring(0, 1) + s.substring(2, e) + zeros(exp - nDigits);
/*  99:    */     }
/* 100:336 */     while (s.charAt(e - 1) == '0') {
/* 101:337 */       e--;
/* 102:    */     }
/* 103:339 */     if (exp > 0) {
/* 104:340 */       return sign + s.substring(0, 1) + s.substring(2, 2 + exp) + "." + s.substring(2 + exp, e);
/* 105:    */     }
/* 106:343 */     return sign + "0." + zeros(-1 - exp) + s.substring(0, 1) + s.substring(2, e);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static String zeros(int n)
/* 110:    */   {
/* 111:358 */     if (n < 1) {
/* 112:359 */       return "";
/* 113:    */     }
/* 114:361 */     char[] buf = new char[n];
/* 115:363 */     for (int i = 0; i < n; i++) {
/* 116:365 */       buf[i] = '0';
/* 117:    */     }
/* 118:368 */     return new String(buf);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Object object()
/* 122:    */   {
/* 123:379 */     if (null == this.m_obj) {
/* 124:380 */       setObject(new Double(this.m_val));
/* 125:    */     }
/* 126:381 */     return this.m_obj;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean equals(XObject obj2)
/* 130:    */   {
/* 131:399 */     int t = obj2.getType();
/* 132:    */     try
/* 133:    */     {
/* 134:402 */       if (t == 4) {
/* 135:403 */         return obj2.equals(this);
/* 136:    */       }
/* 137:404 */       if (t == 1) {
/* 138:405 */         return obj2.bool() == bool();
/* 139:    */       }
/* 140:407 */       return this.m_val == obj2.num();
/* 141:    */     }
/* 142:    */     catch (TransformerException te)
/* 143:    */     {
/* 144:411 */       throw new WrappedRuntimeException(te);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isStableNumber()
/* 149:    */   {
/* 150:425 */     return true;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 154:    */   {
/* 155:433 */     visitor.visitNumberLiteral(owner, this);
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XNumber
 * JD-Core Version:    0.7.0.1
 */