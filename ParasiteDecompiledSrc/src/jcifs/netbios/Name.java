/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import jcifs.Config;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ 
/*   7:    */ public class Name
/*   8:    */ {
/*   9:    */   private static final int TYPE_OFFSET = 31;
/*  10:    */   private static final int SCOPE_OFFSET = 33;
/*  11: 30 */   private static final String DEFAULT_SCOPE = Config.getProperty("jcifs.netbios.scope");
/*  12: 32 */   static final String OEM_ENCODING = Config.getProperty("jcifs.encoding", System.getProperty("file.encoding"));
/*  13:    */   public String name;
/*  14:    */   public String scope;
/*  15:    */   public int hexCode;
/*  16:    */   int srcHashCode;
/*  17:    */   
/*  18:    */   Name() {}
/*  19:    */   
/*  20:    */   public Name(String name, int hexCode, String scope)
/*  21:    */   {
/*  22: 45 */     if (name.length() > 15) {
/*  23: 46 */       name = name.substring(0, 15);
/*  24:    */     }
/*  25: 48 */     this.name = name.toUpperCase();
/*  26: 49 */     this.hexCode = hexCode;
/*  27: 50 */     this.scope = ((scope != null) && (scope.length() > 0) ? scope : DEFAULT_SCOPE);
/*  28: 51 */     this.srcHashCode = 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   int writeWireFormat(byte[] dst, int dstIndex)
/*  32:    */   {
/*  33: 56 */     dst[dstIndex] = 32;
/*  34:    */     try
/*  35:    */     {
/*  36: 60 */       byte[] tmp = this.name.getBytes(OEM_ENCODING);
/*  37: 62 */       for (int i = 0; i < tmp.length; i++)
/*  38:    */       {
/*  39: 63 */         dst[(dstIndex + (2 * i + 1))] = ((byte)(((tmp[i] & 0xF0) >> 4) + 65));
/*  40: 64 */         dst[(dstIndex + (2 * i + 2))] = ((byte)((tmp[i] & 0xF) + 65));
/*  41:    */       }
/*  42: 66 */       for (; i < 15; i++)
/*  43:    */       {
/*  44: 67 */         dst[(dstIndex + (2 * i + 1))] = 67;
/*  45: 68 */         dst[(dstIndex + (2 * i + 2))] = 65;
/*  46:    */       }
/*  47: 70 */       dst[(dstIndex + 31)] = ((byte)(((this.hexCode & 0xF0) >> 4) + 65));
/*  48: 71 */       dst[(dstIndex + 31 + 1)] = ((byte)((this.hexCode & 0xF) + 65));
/*  49:    */     }
/*  50:    */     catch (UnsupportedEncodingException uee) {}
/*  51: 74 */     return 33 + writeScopeWireFormat(dst, dstIndex + 33);
/*  52:    */   }
/*  53:    */   
/*  54:    */   int readWireFormat(byte[] src, int srcIndex)
/*  55:    */   {
/*  56: 79 */     byte[] tmp = new byte[33];
/*  57: 80 */     int length = 15;
/*  58: 81 */     for (int i = 0; i < 15; i++)
/*  59:    */     {
/*  60: 82 */       tmp[i] = ((byte)((src[(srcIndex + (2 * i + 1))] & 0xFF) - 65 << 4)); int 
/*  61: 83 */         tmp46_44 = i; byte[] tmp46_43 = tmp;tmp46_43[tmp46_44] = ((byte)(tmp46_43[tmp46_44] | (byte)((src[(srcIndex + (2 * i + 2))] & 0xFF) - 65 & 0xF)));
/*  62: 84 */       if (tmp[i] != 32) {
/*  63: 85 */         length = i + 1;
/*  64:    */       }
/*  65:    */     }
/*  66:    */     try
/*  67:    */     {
/*  68: 89 */       this.name = new String(tmp, 0, length, OEM_ENCODING);
/*  69:    */     }
/*  70:    */     catch (UnsupportedEncodingException uee) {}
/*  71: 92 */     this.hexCode = ((src[(srcIndex + 31)] & 0xFF) - 65 << 4);
/*  72: 93 */     this.hexCode |= (src[(srcIndex + 31 + 1)] & 0xFF) - 65 & 0xF;
/*  73: 94 */     return 33 + readScopeWireFormat(src, srcIndex + 33);
/*  74:    */   }
/*  75:    */   
/*  76:    */   int writeScopeWireFormat(byte[] dst, int dstIndex)
/*  77:    */   {
/*  78: 97 */     if (this.scope == null)
/*  79:    */     {
/*  80: 98 */       dst[dstIndex] = 0;
/*  81: 99 */       return 1;
/*  82:    */     }
/*  83:103 */     dst[(dstIndex++)] = 46;
/*  84:    */     try
/*  85:    */     {
/*  86:105 */       System.arraycopy(this.scope.getBytes(OEM_ENCODING), 0, dst, dstIndex, this.scope.length());
/*  87:    */     }
/*  88:    */     catch (UnsupportedEncodingException uee) {}
/*  89:108 */     dstIndex += this.scope.length();
/*  90:    */     
/*  91:110 */     dst[(dstIndex++)] = 0;
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:114 */     int i = dstIndex - 2;
/*  96:115 */     int e = i - this.scope.length();
/*  97:116 */     int c = 0;
/*  98:    */     do
/*  99:    */     {
/* 100:119 */       if (dst[i] == 46)
/* 101:    */       {
/* 102:120 */         dst[i] = ((byte)c);
/* 103:121 */         c = 0;
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:123 */         c++;
/* 108:    */       }
/* 109:125 */     } while (i-- > e);
/* 110:126 */     return this.scope.length() + 2;
/* 111:    */   }
/* 112:    */   
/* 113:    */   int readScopeWireFormat(byte[] src, int srcIndex)
/* 114:    */   {
/* 115:129 */     int start = srcIndex;
/* 116:    */     int n;
/* 117:133 */     if ((n = src[(srcIndex++)] & 0xFF) == 0)
/* 118:    */     {
/* 119:134 */       this.scope = null;
/* 120:135 */       return 1;
/* 121:    */     }
/* 122:    */     try
/* 123:    */     {
/* 124:139 */       StringBuffer sb = new StringBuffer(new String(src, srcIndex, n, OEM_ENCODING));
/* 125:140 */       srcIndex += n;
/* 126:141 */       while ((n = src[(srcIndex++)] & 0xFF) != 0)
/* 127:    */       {
/* 128:142 */         sb.append('.').append(new String(src, srcIndex, n, OEM_ENCODING));
/* 129:143 */         srcIndex += n;
/* 130:    */       }
/* 131:145 */       this.scope = sb.toString();
/* 132:    */     }
/* 133:    */     catch (UnsupportedEncodingException uee) {}
/* 134:149 */     return srcIndex - start;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int hashCode()
/* 138:    */   {
/* 139:155 */     int result = this.name.hashCode();
/* 140:156 */     result += 65599 * this.hexCode;
/* 141:157 */     result += 65599 * this.srcHashCode;
/* 142:160 */     if ((this.scope != null) && (this.scope.length() != 0)) {
/* 143:161 */       result += this.scope.hashCode();
/* 144:    */     }
/* 145:163 */     return result;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean equals(Object obj)
/* 149:    */   {
/* 150:168 */     if (!(obj instanceof Name)) {
/* 151:169 */       return false;
/* 152:    */     }
/* 153:171 */     Name n = (Name)obj;
/* 154:172 */     if ((this.scope == null) && (n.scope == null)) {
/* 155:173 */       return (this.name.equals(n.name)) && (this.hexCode == n.hexCode);
/* 156:    */     }
/* 157:175 */     return (this.name.equals(n.name)) && (this.hexCode == n.hexCode) && (this.scope.equals(n.scope));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String toString()
/* 161:    */   {
/* 162:178 */     StringBuffer sb = new StringBuffer();
/* 163:179 */     String n = this.name;
/* 164:182 */     if (n == null)
/* 165:    */     {
/* 166:183 */       n = "null";
/* 167:    */     }
/* 168:184 */     else if (n.charAt(0) == '\001')
/* 169:    */     {
/* 170:185 */       char[] c = n.toCharArray();
/* 171:186 */       c[0] = '.';
/* 172:187 */       c[1] = '.';
/* 173:188 */       c[14] = '.';
/* 174:189 */       n = new String(c);
/* 175:    */     }
/* 176:192 */     sb.append(n).append("<").append(Hexdump.toHexString(this.hexCode, 2)).append(">");
/* 177:193 */     if (this.scope != null) {
/* 178:194 */       sb.append(".").append(this.scope);
/* 179:    */     }
/* 180:196 */     return sb.toString();
/* 181:    */   }
/* 182:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.Name
 * JD-Core Version:    0.7.0.1
 */