/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import org.apache.james.mime4j.codec.CodecUtil;
/*   6:    */ import org.apache.james.mime4j.field.ContentTypeField;
/*   7:    */ import org.apache.james.mime4j.parser.Field;
/*   8:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*   9:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  10:    */ import org.apache.james.mime4j.util.ContentUtil;
/*  11:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  12:    */ 
/*  13:    */ public class MessageWriter
/*  14:    */ {
/*  15: 45 */   private static final byte[] CRLF = { 13, 10 };
/*  16: 46 */   private static final byte[] DASHES = { 45, 45 };
/*  17: 51 */   public static final MessageWriter DEFAULT = new MessageWriter();
/*  18:    */   
/*  19:    */   public void writeBody(Body body, OutputStream out)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 71 */     if ((body instanceof Message)) {
/*  23: 72 */       writeEntity((Message)body, out);
/*  24: 73 */     } else if ((body instanceof Multipart)) {
/*  25: 74 */       writeMultipart((Multipart)body, out);
/*  26: 75 */     } else if ((body instanceof SingleBody)) {
/*  27: 76 */       ((SingleBody)body).writeTo(out);
/*  28:    */     } else {
/*  29: 78 */       throw new IllegalArgumentException("Unsupported body class");
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void writeEntity(Entity entity, OutputStream out)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 93 */     Header header = entity.getHeader();
/*  37: 94 */     if (header == null) {
/*  38: 95 */       throw new IllegalArgumentException("Missing header");
/*  39:    */     }
/*  40: 97 */     writeHeader(header, out);
/*  41:    */     
/*  42: 99 */     Body body = entity.getBody();
/*  43:100 */     if (body == null) {
/*  44:101 */       throw new IllegalArgumentException("Missing body");
/*  45:    */     }
/*  46:103 */     boolean binaryBody = body instanceof BinaryBody;
/*  47:104 */     OutputStream encOut = encodeStream(out, entity.getContentTransferEncoding(), binaryBody);
/*  48:    */     
/*  49:    */ 
/*  50:107 */     writeBody(body, encOut);
/*  51:110 */     if (encOut != out) {
/*  52:111 */       encOut.close();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void writeMultipart(Multipart multipart, OutputStream out)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:127 */     ContentTypeField contentType = getContentType(multipart);
/*  60:    */     
/*  61:129 */     ByteSequence boundary = getBoundary(contentType);
/*  62:    */     
/*  63:131 */     writeBytes(multipart.getPreambleRaw(), out);
/*  64:132 */     out.write(CRLF);
/*  65:134 */     for (BodyPart bodyPart : multipart.getBodyParts())
/*  66:    */     {
/*  67:135 */       out.write(DASHES);
/*  68:136 */       writeBytes(boundary, out);
/*  69:137 */       out.write(CRLF);
/*  70:    */       
/*  71:139 */       writeEntity(bodyPart, out);
/*  72:140 */       out.write(CRLF);
/*  73:    */     }
/*  74:143 */     out.write(DASHES);
/*  75:144 */     writeBytes(boundary, out);
/*  76:145 */     out.write(DASHES);
/*  77:146 */     out.write(CRLF);
/*  78:    */     
/*  79:148 */     writeBytes(multipart.getEpilogueRaw(), out);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void writeHeader(Header header, OutputStream out)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:163 */     for (Field field : header)
/*  86:    */     {
/*  87:164 */       writeBytes(field.getRaw(), out);
/*  88:165 */       out.write(CRLF);
/*  89:    */     }
/*  90:168 */     out.write(CRLF);
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected OutputStream encodeStream(OutputStream out, String encoding, boolean binaryBody)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:173 */     if (MimeUtil.isBase64Encoding(encoding)) {
/*  97:174 */       return CodecUtil.wrapBase64(out);
/*  98:    */     }
/*  99:175 */     if (MimeUtil.isQuotedPrintableEncoded(encoding)) {
/* 100:176 */       return CodecUtil.wrapQuotedPrintable(out, binaryBody);
/* 101:    */     }
/* 102:178 */     return out;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private ContentTypeField getContentType(Multipart multipart)
/* 106:    */   {
/* 107:183 */     Entity parent = multipart.getParent();
/* 108:184 */     if (parent == null) {
/* 109:185 */       throw new IllegalArgumentException("Missing parent entity in multipart");
/* 110:    */     }
/* 111:188 */     Header header = parent.getHeader();
/* 112:189 */     if (header == null) {
/* 113:190 */       throw new IllegalArgumentException("Missing header in parent entity");
/* 114:    */     }
/* 115:193 */     ContentTypeField contentType = (ContentTypeField)header.getField("Content-Type");
/* 116:195 */     if (contentType == null) {
/* 117:196 */       throw new IllegalArgumentException("Content-Type field not specified");
/* 118:    */     }
/* 119:199 */     return contentType;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private ByteSequence getBoundary(ContentTypeField contentType)
/* 123:    */   {
/* 124:203 */     String boundary = contentType.getBoundary();
/* 125:204 */     if (boundary == null) {
/* 126:205 */       throw new IllegalArgumentException("Multipart boundary not specified");
/* 127:    */     }
/* 128:208 */     return ContentUtil.encode(boundary);
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void writeBytes(ByteSequence byteSequence, OutputStream out)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:213 */     if ((byteSequence instanceof ByteArrayBuffer))
/* 135:    */     {
/* 136:214 */       ByteArrayBuffer bab = (ByteArrayBuffer)byteSequence;
/* 137:215 */       out.write(bab.buffer(), 0, bab.length());
/* 138:    */     }
/* 139:    */     else
/* 140:    */     {
/* 141:217 */       out.write(byteSequence.toByteArray());
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.MessageWriter
 * JD-Core Version:    0.7.0.1
 */