/*   1:    */ package org.apache.http.entity.mime;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.nio.charset.Charset;
/*   7:    */ import java.util.Random;
/*   8:    */ import org.apache.http.Header;
/*   9:    */ import org.apache.http.HttpEntity;
/*  10:    */ import org.apache.http.entity.mime.content.ContentBody;
/*  11:    */ import org.apache.http.message.BasicHeader;
/*  12:    */ 
/*  13:    */ public class MultipartEntity
/*  14:    */   implements HttpEntity
/*  15:    */ {
/*  16: 52 */   private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
/*  17:    */   private final HttpMultipart multipart;
/*  18:    */   private final Header contentType;
/*  19:    */   private long length;
/*  20:    */   private volatile boolean dirty;
/*  21:    */   
/*  22:    */   public MultipartEntity(HttpMultipartMode mode, String boundary, Charset charset)
/*  23:    */   {
/*  24: 74 */     if (boundary == null) {
/*  25: 75 */       boundary = generateBoundary();
/*  26:    */     }
/*  27: 77 */     if (mode == null) {
/*  28: 78 */       mode = HttpMultipartMode.STRICT;
/*  29:    */     }
/*  30: 80 */     this.multipart = new HttpMultipart("form-data", charset, boundary, mode);
/*  31: 81 */     this.contentType = new BasicHeader("Content-Type", generateContentType(boundary, charset));
/*  32:    */     
/*  33:    */ 
/*  34: 84 */     this.dirty = true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public MultipartEntity(HttpMultipartMode mode)
/*  38:    */   {
/*  39: 93 */     this(mode, null, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public MultipartEntity()
/*  43:    */   {
/*  44:100 */     this(HttpMultipartMode.STRICT, null, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected String generateContentType(String boundary, Charset charset)
/*  48:    */   {
/*  49:106 */     StringBuilder buffer = new StringBuilder();
/*  50:107 */     buffer.append("multipart/form-data; boundary=");
/*  51:108 */     buffer.append(boundary);
/*  52:109 */     if (charset != null)
/*  53:    */     {
/*  54:110 */       buffer.append("; charset=");
/*  55:111 */       buffer.append(charset.name());
/*  56:    */     }
/*  57:113 */     return buffer.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected String generateBoundary()
/*  61:    */   {
/*  62:117 */     StringBuilder buffer = new StringBuilder();
/*  63:118 */     Random rand = new Random();
/*  64:119 */     int count = rand.nextInt(11) + 30;
/*  65:120 */     for (int i = 0; i < count; i++) {
/*  66:121 */       buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
/*  67:    */     }
/*  68:123 */     return buffer.toString();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void addPart(FormBodyPart bodyPart)
/*  72:    */   {
/*  73:127 */     this.multipart.addBodyPart(bodyPart);
/*  74:128 */     this.dirty = true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void addPart(String name, ContentBody contentBody)
/*  78:    */   {
/*  79:132 */     addPart(new FormBodyPart(name, contentBody));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isRepeatable()
/*  83:    */   {
/*  84:136 */     for (FormBodyPart part : this.multipart.getBodyParts())
/*  85:    */     {
/*  86:137 */       ContentBody body = part.getBody();
/*  87:138 */       if (body.getContentLength() < 0L) {
/*  88:139 */         return false;
/*  89:    */       }
/*  90:    */     }
/*  91:142 */     return true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isChunked()
/*  95:    */   {
/*  96:146 */     return !isRepeatable();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isStreaming()
/* 100:    */   {
/* 101:150 */     return !isRepeatable();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public long getContentLength()
/* 105:    */   {
/* 106:154 */     if (this.dirty)
/* 107:    */     {
/* 108:155 */       this.length = this.multipart.getTotalLength();
/* 109:156 */       this.dirty = false;
/* 110:    */     }
/* 111:158 */     return this.length;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Header getContentType()
/* 115:    */   {
/* 116:162 */     return this.contentType;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Header getContentEncoding()
/* 120:    */   {
/* 121:166 */     return null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void consumeContent()
/* 125:    */     throws IOException, UnsupportedOperationException
/* 126:    */   {
/* 127:171 */     if (isStreaming()) {
/* 128:172 */       throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public InputStream getContent()
/* 133:    */     throws IOException, UnsupportedOperationException
/* 134:    */   {
/* 135:178 */     throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void writeTo(OutputStream outstream)
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:183 */     this.multipart.writeTo(outstream);
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.MultipartEntity
 * JD-Core Version:    0.7.0.1
 */