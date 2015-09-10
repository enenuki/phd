/*   1:    */ package jcifs.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public class MimeMap
/*   7:    */ {
/*   8:    */   private static final int IN_SIZE = 7000;
/*   9:    */   private static final int ST_START = 1;
/*  10:    */   private static final int ST_COMM = 2;
/*  11:    */   private static final int ST_TYPE = 3;
/*  12:    */   private static final int ST_GAP = 4;
/*  13:    */   private static final int ST_EXT = 5;
/*  14:    */   private byte[] in;
/*  15:    */   private int inLen;
/*  16:    */   
/*  17:    */   public MimeMap()
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 40 */     this.in = new byte[7000];
/*  21: 41 */     InputStream is = getClass().getClassLoader().getResourceAsStream("jcifs/util/mime.map");
/*  22:    */     
/*  23: 43 */     this.inLen = 0;
/*  24:    */     int n;
/*  25: 44 */     while ((n = is.read(this.in, this.inLen, 7000 - this.inLen)) != -1) {
/*  26: 45 */       this.inLen += n;
/*  27:    */     }
/*  28: 47 */     if ((this.inLen < 100) || (this.inLen == 7000)) {
/*  29: 48 */       throw new IOException("Error reading jcifs/util/mime.map resource");
/*  30:    */     }
/*  31: 50 */     is.close();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getMimeType(String extension)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 54 */     return getMimeType(extension, "application/octet-stream");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getMimeType(String extension, String def)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 59 */     byte[] type = new byte[''];
/*  44: 60 */     byte[] buf = new byte[16];
/*  45: 61 */     byte[] ext = extension.toLowerCase().getBytes("ASCII");
/*  46:    */     
/*  47: 63 */     int state = 1;
/*  48:    */     int i;
/*  49:    */     int x;
/*  50: 64 */     int t = x = i = 0;
/*  51: 65 */     for (int off = 0; off < this.inLen; off++)
/*  52:    */     {
/*  53: 66 */       byte ch = this.in[off];
/*  54: 67 */       switch (state)
/*  55:    */       {
/*  56:    */       case 1: 
/*  57: 69 */         if ((ch != 32) && (ch != 9)) {
/*  58: 71 */           if (ch == 35) {
/*  59: 72 */             state = 2;
/*  60:    */           } else {
/*  61: 75 */             state = 3;
/*  62:    */           }
/*  63:    */         }
/*  64:    */         break;
/*  65:    */       case 3: 
/*  66: 77 */         if ((ch == 32) || (ch == 9)) {
/*  67: 78 */           state = 4;
/*  68:    */         } else {
/*  69: 80 */           type[(t++)] = ch;
/*  70:    */         }
/*  71: 82 */         break;
/*  72:    */       case 2: 
/*  73: 84 */         if (ch == 10)
/*  74:    */         {
/*  75: 85 */           t = x = i = 0;
/*  76: 86 */           state = 1;
/*  77:    */         }
/*  78:    */         break;
/*  79:    */       case 4: 
/*  80: 90 */         if ((ch != 32) && (ch != 9)) {
/*  81: 93 */           state = 5;
/*  82:    */         }
/*  83:    */         break;
/*  84:    */       case 5: 
/*  85: 95 */         switch (ch)
/*  86:    */         {
/*  87:    */         case 9: 
/*  88:    */         case 10: 
/*  89:    */         case 32: 
/*  90:    */         case 35: 
/*  91:100 */           for (i = 0; (i < x) && (x == ext.length) && (buf[i] == ext[i]); i++) {}
/*  92:103 */           if (i == ext.length) {
/*  93:104 */             return new String(type, 0, t, "ASCII");
/*  94:    */           }
/*  95:106 */           if (ch == 35)
/*  96:    */           {
/*  97:107 */             state = 2;
/*  98:    */           }
/*  99:108 */           else if (ch == 10)
/* 100:    */           {
/* 101:109 */             t = x = i = 0;
/* 102:110 */             state = 1;
/* 103:    */           }
/* 104:112 */           x = 0;
/* 105:113 */           break;
/* 106:    */         default: 
/* 107:115 */           buf[(x++)] = ch;
/* 108:    */         }
/* 109:    */         break;
/* 110:    */       }
/* 111:    */     }
/* 112:120 */     return def;
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.MimeMap
 * JD-Core Version:    0.7.0.1
 */