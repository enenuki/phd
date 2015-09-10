/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.http.annotation.Immutable;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class Wire
/*  11:    */ {
/*  12:    */   private final Log log;
/*  13:    */   
/*  14:    */   public Wire(Log log)
/*  15:    */   {
/*  16: 49 */     this.log = log;
/*  17:    */   }
/*  18:    */   
/*  19:    */   private void wire(String header, InputStream instream)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 54 */     StringBuilder buffer = new StringBuilder();
/*  23:    */     int ch;
/*  24: 56 */     while ((ch = instream.read()) != -1) {
/*  25: 57 */       if (ch == 13)
/*  26:    */       {
/*  27: 58 */         buffer.append("[\\r]");
/*  28:    */       }
/*  29: 59 */       else if (ch == 10)
/*  30:    */       {
/*  31: 60 */         buffer.append("[\\n]\"");
/*  32: 61 */         buffer.insert(0, "\"");
/*  33: 62 */         buffer.insert(0, header);
/*  34: 63 */         this.log.debug(buffer.toString());
/*  35: 64 */         buffer.setLength(0);
/*  36:    */       }
/*  37: 65 */       else if ((ch < 32) || (ch > 127))
/*  38:    */       {
/*  39: 66 */         buffer.append("[0x");
/*  40: 67 */         buffer.append(Integer.toHexString(ch));
/*  41: 68 */         buffer.append("]");
/*  42:    */       }
/*  43:    */       else
/*  44:    */       {
/*  45: 70 */         buffer.append((char)ch);
/*  46:    */       }
/*  47:    */     }
/*  48: 73 */     if (buffer.length() > 0)
/*  49:    */     {
/*  50: 74 */       buffer.append('"');
/*  51: 75 */       buffer.insert(0, '"');
/*  52: 76 */       buffer.insert(0, header);
/*  53: 77 */       this.log.debug(buffer.toString());
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean enabled()
/*  58:    */   {
/*  59: 83 */     return this.log.isDebugEnabled();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void output(InputStream outstream)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 88 */     if (outstream == null) {
/*  66: 89 */       throw new IllegalArgumentException("Output may not be null");
/*  67:    */     }
/*  68: 91 */     wire(">> ", outstream);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void input(InputStream instream)
/*  72:    */     throws IOException
/*  73:    */   {
/*  74: 96 */     if (instream == null) {
/*  75: 97 */       throw new IllegalArgumentException("Input may not be null");
/*  76:    */     }
/*  77: 99 */     wire("<< ", instream);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void output(byte[] b, int off, int len)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:104 */     if (b == null) {
/*  84:105 */       throw new IllegalArgumentException("Output may not be null");
/*  85:    */     }
/*  86:107 */     wire(">> ", new ByteArrayInputStream(b, off, len));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void input(byte[] b, int off, int len)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:112 */     if (b == null) {
/*  93:113 */       throw new IllegalArgumentException("Input may not be null");
/*  94:    */     }
/*  95:115 */     wire("<< ", new ByteArrayInputStream(b, off, len));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void output(byte[] b)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:120 */     if (b == null) {
/* 102:121 */       throw new IllegalArgumentException("Output may not be null");
/* 103:    */     }
/* 104:123 */     wire(">> ", new ByteArrayInputStream(b));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void input(byte[] b)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:128 */     if (b == null) {
/* 111:129 */       throw new IllegalArgumentException("Input may not be null");
/* 112:    */     }
/* 113:131 */     wire("<< ", new ByteArrayInputStream(b));
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void output(int b)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:136 */     output(new byte[] { (byte)b });
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void input(int b)
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:141 */     input(new byte[] { (byte)b });
/* 126:    */   }
/* 127:    */   
/* 128:    */   @Deprecated
/* 129:    */   public void output(String s)
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:147 */     if (s == null) {
/* 133:148 */       throw new IllegalArgumentException("Output may not be null");
/* 134:    */     }
/* 135:150 */     output(s.getBytes());
/* 136:    */   }
/* 137:    */   
/* 138:    */   @Deprecated
/* 139:    */   public void input(String s)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:156 */     if (s == null) {
/* 143:157 */       throw new IllegalArgumentException("Input may not be null");
/* 144:    */     }
/* 145:159 */     input(s.getBytes());
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.Wire
 * JD-Core Version:    0.7.0.1
 */