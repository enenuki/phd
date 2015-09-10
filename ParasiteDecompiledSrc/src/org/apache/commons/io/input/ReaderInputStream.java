/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.nio.CharBuffer;
/*   8:    */ import java.nio.charset.Charset;
/*   9:    */ import java.nio.charset.CharsetEncoder;
/*  10:    */ import java.nio.charset.CoderResult;
/*  11:    */ 
/*  12:    */ public class ReaderInputStream
/*  13:    */   extends InputStream
/*  14:    */ {
/*  15:    */   private static final int DEFAULT_BUFFER_SIZE = 1024;
/*  16:    */   private final Reader reader;
/*  17:    */   private final CharsetEncoder encoder;
/*  18:    */   private final CharBuffer encoderIn;
/*  19: 94 */   private final ByteBuffer encoderOut = ByteBuffer.allocate(128);
/*  20:    */   private CoderResult lastCoderResult;
/*  21:    */   private boolean endOfInput;
/*  22:    */   
/*  23:    */   public ReaderInputStream(Reader reader, Charset charset, int bufferSize)
/*  24:    */   {
/*  25:107 */     this.reader = reader;
/*  26:108 */     this.encoder = charset.newEncoder();
/*  27:109 */     this.encoderIn = CharBuffer.allocate(bufferSize);
/*  28:110 */     this.encoderIn.flip();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ReaderInputStream(Reader reader, Charset charset)
/*  32:    */   {
/*  33:121 */     this(reader, charset, 1024);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ReaderInputStream(Reader reader, String charsetName, int bufferSize)
/*  37:    */   {
/*  38:132 */     this(reader, Charset.forName(charsetName), bufferSize);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ReaderInputStream(Reader reader, String charsetName)
/*  42:    */   {
/*  43:143 */     this(reader, charsetName, 1024);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ReaderInputStream(Reader reader)
/*  47:    */   {
/*  48:153 */     this(reader, Charset.defaultCharset());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int read(byte[] b, int off, int len)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:168 */     int read = 0;
/*  55:169 */     while (len > 0) {
/*  56:170 */       if (this.encoderOut.position() > 0)
/*  57:    */       {
/*  58:171 */         this.encoderOut.flip();
/*  59:172 */         int c = Math.min(this.encoderOut.remaining(), len);
/*  60:173 */         this.encoderOut.get(b, off, c);
/*  61:174 */         off += c;
/*  62:175 */         len -= c;
/*  63:176 */         read += c;
/*  64:177 */         this.encoderOut.compact();
/*  65:    */       }
/*  66:    */       else
/*  67:    */       {
/*  68:179 */         if ((!this.endOfInput) && ((this.lastCoderResult == null) || (this.lastCoderResult.isUnderflow())))
/*  69:    */         {
/*  70:180 */           this.encoderIn.compact();
/*  71:181 */           int position = this.encoderIn.position();
/*  72:    */           
/*  73:    */ 
/*  74:    */ 
/*  75:185 */           int c = this.reader.read(this.encoderIn.array(), position, this.encoderIn.remaining());
/*  76:186 */           if (c == -1) {
/*  77:187 */             this.endOfInput = true;
/*  78:    */           } else {
/*  79:189 */             this.encoderIn.position(position + c);
/*  80:    */           }
/*  81:191 */           this.encoderIn.flip();
/*  82:    */         }
/*  83:193 */         this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
/*  84:194 */         if ((this.endOfInput) && (this.encoderOut.position() == 0)) {
/*  85:    */           break;
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:199 */     return (read == 0) && (this.endOfInput) ? -1 : read;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int read(byte[] b)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:212 */     return read(b, 0, b.length);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int read()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:224 */     byte[] b = new byte[1];
/* 102:225 */     return read(b) == -1 ? -1 : b[0] & 0xFF;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void close()
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:235 */     this.reader.close();
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.ReaderInputStream
 * JD-Core Version:    0.7.0.1
 */