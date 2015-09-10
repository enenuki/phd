/*   1:    */ package org.apache.james.mime4j.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.james.mime4j.MimeException;
/*   6:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*   7:    */ 
/*   8:    */ public class MimeStreamParser
/*   9:    */ {
/*  10: 44 */   private ContentHandler handler = null;
/*  11:    */   private boolean contentDecoding;
/*  12:    */   private final MimeTokenStream mimeTokenStream;
/*  13:    */   
/*  14:    */   public MimeStreamParser(MimeEntityConfig config)
/*  15:    */   {
/*  16:    */     MimeEntityConfig localConfig;
/*  17:    */     MimeEntityConfig localConfig;
/*  18: 52 */     if (config != null) {
/*  19: 53 */       localConfig = config.clone();
/*  20:    */     } else {
/*  21: 55 */       localConfig = new MimeEntityConfig();
/*  22:    */     }
/*  23: 57 */     this.mimeTokenStream = new MimeTokenStream(localConfig);
/*  24: 58 */     this.contentDecoding = false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public MimeStreamParser()
/*  28:    */   {
/*  29: 62 */     this(null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isContentDecoding()
/*  33:    */   {
/*  34: 70 */     return this.contentDecoding;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setContentDecoding(boolean b)
/*  38:    */   {
/*  39: 78 */     this.contentDecoding = b;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void parse(InputStream is)
/*  43:    */     throws MimeException, IOException
/*  44:    */   {
/*  45: 89 */     this.mimeTokenStream.parse(is);
/*  46:    */     for (;;)
/*  47:    */     {
/*  48: 91 */       int state = this.mimeTokenStream.getState();
/*  49: 92 */       switch (state)
/*  50:    */       {
/*  51:    */       case 12: 
/*  52: 94 */         BodyDescriptor desc = this.mimeTokenStream.getBodyDescriptor();
/*  53:    */         InputStream bodyContent;
/*  54:    */         InputStream bodyContent;
/*  55: 96 */         if (this.contentDecoding) {
/*  56: 97 */           bodyContent = this.mimeTokenStream.getDecodedInputStream();
/*  57:    */         } else {
/*  58: 99 */           bodyContent = this.mimeTokenStream.getInputStream();
/*  59:    */         }
/*  60:101 */         this.handler.body(desc, bodyContent);
/*  61:102 */         break;
/*  62:    */       case 11: 
/*  63:104 */         this.handler.endBodyPart();
/*  64:105 */         break;
/*  65:    */       case 5: 
/*  66:107 */         this.handler.endHeader();
/*  67:108 */         break;
/*  68:    */       case 1: 
/*  69:110 */         this.handler.endMessage();
/*  70:111 */         break;
/*  71:    */       case 7: 
/*  72:113 */         this.handler.endMultipart();
/*  73:114 */         break;
/*  74:    */       case -1: 
/*  75:    */         break;
/*  76:    */       case 9: 
/*  77:118 */         this.handler.epilogue(this.mimeTokenStream.getInputStream());
/*  78:119 */         break;
/*  79:    */       case 4: 
/*  80:121 */         this.handler.field(this.mimeTokenStream.getField());
/*  81:122 */         break;
/*  82:    */       case 8: 
/*  83:124 */         this.handler.preamble(this.mimeTokenStream.getInputStream());
/*  84:125 */         break;
/*  85:    */       case 2: 
/*  86:127 */         this.handler.raw(this.mimeTokenStream.getInputStream());
/*  87:128 */         break;
/*  88:    */       case 10: 
/*  89:130 */         this.handler.startBodyPart();
/*  90:131 */         break;
/*  91:    */       case 3: 
/*  92:133 */         this.handler.startHeader();
/*  93:134 */         break;
/*  94:    */       case 0: 
/*  95:136 */         this.handler.startMessage();
/*  96:137 */         break;
/*  97:    */       case 6: 
/*  98:139 */         this.handler.startMultipart(this.mimeTokenStream.getBodyDescriptor());
/*  99:140 */         break;
/* 100:    */       default: 
/* 101:142 */         throw new IllegalStateException("Invalid state: " + state);
/* 102:    */       }
/* 103:144 */       state = this.mimeTokenStream.next();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isRaw()
/* 108:    */   {
/* 109:156 */     return this.mimeTokenStream.isRaw();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setRaw(boolean raw)
/* 113:    */   {
/* 114:170 */     this.mimeTokenStream.setRecursionMode(2);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void stop()
/* 118:    */   {
/* 119:186 */     this.mimeTokenStream.stop();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setContentHandler(ContentHandler h)
/* 123:    */   {
/* 124:196 */     this.handler = h;
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.MimeStreamParser
 * JD-Core Version:    0.7.0.1
 */