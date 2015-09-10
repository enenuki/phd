/*   1:    */ package org.apache.http.entity.mime;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.nio.CharBuffer;
/*   8:    */ import java.nio.charset.Charset;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import org.apache.http.entity.mime.content.ContentBody;
/*  12:    */ import org.apache.http.util.ByteArrayBuffer;
/*  13:    */ 
/*  14:    */ public class HttpMultipart
/*  15:    */ {
/*  16:    */   private static ByteArrayBuffer encode(Charset charset, String string)
/*  17:    */   {
/*  18: 53 */     ByteBuffer encoded = charset.encode(CharBuffer.wrap(string));
/*  19: 54 */     ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
/*  20: 55 */     bab.append(encoded.array(), encoded.position(), encoded.remaining());
/*  21: 56 */     return bab;
/*  22:    */   }
/*  23:    */   
/*  24:    */   private static void writeBytes(ByteArrayBuffer b, OutputStream out)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 61 */     out.write(b.buffer(), 0, b.length());
/*  28:    */   }
/*  29:    */   
/*  30:    */   private static void writeBytes(String s, Charset charset, OutputStream out)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 66 */     ByteArrayBuffer b = encode(charset, s);
/*  34: 67 */     writeBytes(b, out);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static void writeBytes(String s, OutputStream out)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 72 */     ByteArrayBuffer b = encode(MIME.DEFAULT_CHARSET, s);
/*  41: 73 */     writeBytes(b, out);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static void writeField(MinimalField field, OutputStream out)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 78 */     writeBytes(field.getName(), out);
/*  48: 79 */     writeBytes(FIELD_SEP, out);
/*  49: 80 */     writeBytes(field.getBody(), out);
/*  50: 81 */     writeBytes(CR_LF, out);
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static void writeField(MinimalField field, Charset charset, OutputStream out)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 86 */     writeBytes(field.getName(), charset, out);
/*  57: 87 */     writeBytes(FIELD_SEP, out);
/*  58: 88 */     writeBytes(field.getBody(), charset, out);
/*  59: 89 */     writeBytes(CR_LF, out);
/*  60:    */   }
/*  61:    */   
/*  62: 92 */   private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
/*  63: 93 */   private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
/*  64: 94 */   private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");
/*  65:    */   private final String subType;
/*  66:    */   private final Charset charset;
/*  67:    */   private final String boundary;
/*  68:    */   private final List<FormBodyPart> parts;
/*  69:    */   private final HttpMultipartMode mode;
/*  70:    */   
/*  71:    */   public HttpMultipart(String subType, Charset charset, String boundary, HttpMultipartMode mode)
/*  72:    */   {
/*  73:115 */     if (subType == null) {
/*  74:116 */       throw new IllegalArgumentException("Multipart subtype may not be null");
/*  75:    */     }
/*  76:118 */     if (boundary == null) {
/*  77:119 */       throw new IllegalArgumentException("Multipart boundary may not be null");
/*  78:    */     }
/*  79:121 */     this.subType = subType;
/*  80:122 */     this.charset = (charset != null ? charset : MIME.DEFAULT_CHARSET);
/*  81:123 */     this.boundary = boundary;
/*  82:124 */     this.parts = new ArrayList();
/*  83:125 */     this.mode = mode;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public HttpMultipart(String subType, Charset charset, String boundary)
/*  87:    */   {
/*  88:138 */     this(subType, charset, boundary, HttpMultipartMode.STRICT);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public HttpMultipart(String subType, String boundary)
/*  92:    */   {
/*  93:142 */     this(subType, null, boundary);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getSubType()
/*  97:    */   {
/*  98:146 */     return this.subType;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Charset getCharset()
/* 102:    */   {
/* 103:150 */     return this.charset;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public HttpMultipartMode getMode()
/* 107:    */   {
/* 108:154 */     return this.mode;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<FormBodyPart> getBodyParts()
/* 112:    */   {
/* 113:158 */     return this.parts;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void addBodyPart(FormBodyPart part)
/* 117:    */   {
/* 118:162 */     if (part == null) {
/* 119:163 */       return;
/* 120:    */     }
/* 121:165 */     this.parts.add(part);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getBoundary()
/* 125:    */   {
/* 126:169 */     return this.boundary;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void doWriteTo(HttpMultipartMode mode, OutputStream out, boolean writeContent)
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:177 */     ByteArrayBuffer boundary = encode(this.charset, getBoundary());
/* 133:178 */     for (FormBodyPart part : this.parts)
/* 134:    */     {
/* 135:179 */       writeBytes(TWO_DASHES, out);
/* 136:180 */       writeBytes(boundary, out);
/* 137:181 */       writeBytes(CR_LF, out);
/* 138:    */       
/* 139:183 */       Header header = part.getHeader();
/* 140:185 */       switch (1.$SwitchMap$org$apache$http$entity$mime$HttpMultipartMode[mode.ordinal()])
/* 141:    */       {
/* 142:    */       case 1: 
/* 143:187 */         for (MinimalField field : header) {
/* 144:188 */           writeField(field, out);
/* 145:    */         }
/* 146:190 */         break;
/* 147:    */       case 2: 
/* 148:194 */         MinimalField cd = part.getHeader().getField("Content-Disposition");
/* 149:195 */         writeField(cd, this.charset, out);
/* 150:196 */         String filename = part.getBody().getFilename();
/* 151:197 */         if (filename != null)
/* 152:    */         {
/* 153:198 */           MinimalField ct = part.getHeader().getField("Content-Type");
/* 154:199 */           writeField(ct, this.charset, out);
/* 155:    */         }
/* 156:    */         break;
/* 157:    */       }
/* 158:203 */       writeBytes(CR_LF, out);
/* 159:205 */       if (writeContent) {
/* 160:206 */         part.getBody().writeTo(out);
/* 161:    */       }
/* 162:208 */       writeBytes(CR_LF, out);
/* 163:    */     }
/* 164:210 */     writeBytes(TWO_DASHES, out);
/* 165:211 */     writeBytes(boundary, out);
/* 166:212 */     writeBytes(TWO_DASHES, out);
/* 167:213 */     writeBytes(CR_LF, out);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void writeTo(OutputStream out)
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:224 */     doWriteTo(this.mode, out, true);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public long getTotalLength()
/* 177:    */   {
/* 178:241 */     long contentLen = 0L;
/* 179:242 */     for (FormBodyPart part : this.parts)
/* 180:    */     {
/* 181:243 */       ContentBody body = part.getBody();
/* 182:244 */       long len = body.getContentLength();
/* 183:245 */       if (len >= 0L) {
/* 184:246 */         contentLen += len;
/* 185:    */       } else {
/* 186:248 */         return -1L;
/* 187:    */       }
/* 188:    */     }
/* 189:251 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 190:    */     try
/* 191:    */     {
/* 192:253 */       doWriteTo(this.mode, out, false);
/* 193:254 */       byte[] extra = out.toByteArray();
/* 194:255 */       return contentLen + extra.length;
/* 195:    */     }
/* 196:    */     catch (IOException ex) {}
/* 197:258 */     return -1L;
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.HttpMultipart
 * JD-Core Version:    0.7.0.1
 */