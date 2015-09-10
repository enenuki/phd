/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Stack;
/*   6:    */ import org.apache.james.mime4j.MimeException;
/*   7:    */ import org.apache.james.mime4j.codec.Base64InputStream;
/*   8:    */ import org.apache.james.mime4j.codec.QuotedPrintableInputStream;
/*   9:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*  10:    */ import org.apache.james.mime4j.field.AbstractField;
/*  11:    */ import org.apache.james.mime4j.parser.ContentHandler;
/*  12:    */ import org.apache.james.mime4j.parser.Field;
/*  13:    */ import org.apache.james.mime4j.storage.StorageProvider;
/*  14:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*  15:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  16:    */ 
/*  17:    */ public class MessageBuilder
/*  18:    */   implements ContentHandler
/*  19:    */ {
/*  20:    */   private final Entity entity;
/*  21:    */   private final BodyFactory bodyFactory;
/*  22: 47 */   private Stack<Object> stack = new Stack();
/*  23:    */   
/*  24:    */   public MessageBuilder(Entity entity)
/*  25:    */   {
/*  26: 50 */     this.entity = entity;
/*  27: 51 */     this.bodyFactory = new BodyFactory();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public MessageBuilder(Entity entity, StorageProvider storageProvider)
/*  31:    */   {
/*  32: 55 */     this.entity = entity;
/*  33: 56 */     this.bodyFactory = new BodyFactory(storageProvider);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void expect(Class<?> c)
/*  37:    */   {
/*  38: 60 */     if (!c.isInstance(this.stack.peek())) {
/*  39: 61 */       throw new IllegalStateException("Internal stack error: Expected '" + c.getName() + "' found '" + this.stack.peek().getClass().getName() + "'");
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void startMessage()
/*  44:    */     throws MimeException
/*  45:    */   {
/*  46: 71 */     if (this.stack.isEmpty())
/*  47:    */     {
/*  48: 72 */       this.stack.push(this.entity);
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 74 */       expect(Entity.class);
/*  53: 75 */       Message m = new Message();
/*  54: 76 */       ((Entity)this.stack.peek()).setBody(m);
/*  55: 77 */       this.stack.push(m);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void endMessage()
/*  60:    */     throws MimeException
/*  61:    */   {
/*  62: 85 */     expect(Message.class);
/*  63: 86 */     this.stack.pop();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void startHeader()
/*  67:    */     throws MimeException
/*  68:    */   {
/*  69: 93 */     this.stack.push(new Header());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void field(Field field)
/*  73:    */     throws MimeException
/*  74:    */   {
/*  75:100 */     expect(Header.class);
/*  76:101 */     Field parsedField = AbstractField.parse(field.getRaw());
/*  77:102 */     ((Header)this.stack.peek()).addField(parsedField);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void endHeader()
/*  81:    */     throws MimeException
/*  82:    */   {
/*  83:109 */     expect(Header.class);
/*  84:110 */     Header h = (Header)this.stack.pop();
/*  85:111 */     expect(Entity.class);
/*  86:112 */     ((Entity)this.stack.peek()).setHeader(h);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void startMultipart(BodyDescriptor bd)
/*  90:    */     throws MimeException
/*  91:    */   {
/*  92:119 */     expect(Entity.class);
/*  93:    */     
/*  94:121 */     Entity e = (Entity)this.stack.peek();
/*  95:122 */     String subType = bd.getSubType();
/*  96:123 */     Multipart multiPart = new Multipart(subType);
/*  97:124 */     e.setBody(multiPart);
/*  98:125 */     this.stack.push(multiPart);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void body(BodyDescriptor bd, InputStream is)
/* 102:    */     throws MimeException, IOException
/* 103:    */   {
/* 104:132 */     expect(Entity.class);
/* 105:    */     
/* 106:134 */     String enc = bd.getTransferEncoding();
/* 107:    */     InputStream decodedStream;
/* 108:    */     InputStream decodedStream;
/* 109:139 */     if ("base64".equals(enc))
/* 110:    */     {
/* 111:140 */       decodedStream = new Base64InputStream(is);
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:    */       InputStream decodedStream;
/* 116:141 */       if ("quoted-printable".equals(enc)) {
/* 117:142 */         decodedStream = new QuotedPrintableInputStream(is);
/* 118:    */       } else {
/* 119:144 */         decodedStream = is;
/* 120:    */       }
/* 121:    */     }
/* 122:    */     Body body;
/* 123:    */     Body body;
/* 124:147 */     if (bd.getMimeType().startsWith("text/")) {
/* 125:148 */       body = this.bodyFactory.textBody(decodedStream, bd.getCharset());
/* 126:    */     } else {
/* 127:150 */       body = this.bodyFactory.binaryBody(decodedStream);
/* 128:    */     }
/* 129:153 */     Entity entity = (Entity)this.stack.peek();
/* 130:154 */     entity.setBody(body);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void endMultipart()
/* 134:    */     throws MimeException
/* 135:    */   {
/* 136:161 */     this.stack.pop();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void startBodyPart()
/* 140:    */     throws MimeException
/* 141:    */   {
/* 142:168 */     expect(Multipart.class);
/* 143:    */     
/* 144:170 */     BodyPart bodyPart = new BodyPart();
/* 145:171 */     ((Multipart)this.stack.peek()).addBodyPart(bodyPart);
/* 146:172 */     this.stack.push(bodyPart);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void endBodyPart()
/* 150:    */     throws MimeException
/* 151:    */   {
/* 152:179 */     expect(BodyPart.class);
/* 153:180 */     this.stack.pop();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void epilogue(InputStream is)
/* 157:    */     throws MimeException, IOException
/* 158:    */   {
/* 159:187 */     expect(Multipart.class);
/* 160:188 */     ByteSequence bytes = loadStream(is);
/* 161:189 */     ((Multipart)this.stack.peek()).setEpilogueRaw(bytes);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void preamble(InputStream is)
/* 165:    */     throws MimeException, IOException
/* 166:    */   {
/* 167:196 */     expect(Multipart.class);
/* 168:197 */     ByteSequence bytes = loadStream(is);
/* 169:198 */     ((Multipart)this.stack.peek()).setPreambleRaw(bytes);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void raw(InputStream is)
/* 173:    */     throws MimeException, IOException
/* 174:    */   {
/* 175:206 */     throw new UnsupportedOperationException("Not supported");
/* 176:    */   }
/* 177:    */   
/* 178:    */   private static ByteSequence loadStream(InputStream in)
/* 179:    */     throws IOException
/* 180:    */   {
/* 181:210 */     ByteArrayBuffer bab = new ByteArrayBuffer(64);
/* 182:    */     int b;
/* 183:213 */     while ((b = in.read()) != -1) {
/* 184:214 */       bab.append(b);
/* 185:    */     }
/* 186:217 */     return bab;
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.MessageBuilder
 * JD-Core Version:    0.7.0.1
 */