/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileReader;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.io.Reader;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ import jcifs.Config;
/*  12:    */ import jcifs.smb.SmbFileInputStream;
/*  13:    */ import jcifs.util.LogStream;
/*  14:    */ 
/*  15:    */ public class Lmhosts
/*  16:    */ {
/*  17: 36 */   private static final String FILENAME = Config.getProperty("jcifs.netbios.lmhosts");
/*  18: 37 */   private static final Hashtable TAB = new Hashtable();
/*  19: 38 */   private static long lastModified = 1L;
/*  20:    */   private static int alt;
/*  21: 40 */   private static LogStream log = LogStream.getInstance();
/*  22:    */   
/*  23:    */   public static synchronized NbtAddress getByName(String host)
/*  24:    */   {
/*  25: 50 */     return getByName(new Name(host, 32, null));
/*  26:    */   }
/*  27:    */   
/*  28:    */   static synchronized NbtAddress getByName(Name name)
/*  29:    */   {
/*  30: 54 */     NbtAddress result = null;
/*  31:    */     try
/*  32:    */     {
/*  33: 57 */       if (FILENAME != null)
/*  34:    */       {
/*  35: 58 */         File f = new File(FILENAME);
/*  36:    */         long lm;
/*  37: 61 */         if ((lm = f.lastModified()) > lastModified)
/*  38:    */         {
/*  39: 62 */           lastModified = lm;
/*  40: 63 */           TAB.clear();
/*  41: 64 */           alt = 0;
/*  42: 65 */           populate(new FileReader(f));
/*  43:    */         }
/*  44: 67 */         result = (NbtAddress)TAB.get(name);
/*  45:    */       }
/*  46:    */     }
/*  47:    */     catch (FileNotFoundException fnfe)
/*  48:    */     {
/*  49: 70 */       if (LogStream.level > 1)
/*  50:    */       {
/*  51: 71 */         log.println("lmhosts file: " + FILENAME);
/*  52: 72 */         fnfe.printStackTrace(log);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (IOException ioe)
/*  56:    */     {
/*  57: 75 */       if (LogStream.level > 0) {
/*  58: 76 */         ioe.printStackTrace(log);
/*  59:    */       }
/*  60:    */     }
/*  61: 78 */     return result;
/*  62:    */   }
/*  63:    */   
/*  64:    */   static void populate(Reader r)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67: 83 */     BufferedReader br = new BufferedReader(r);
/*  68:    */     String line;
/*  69: 85 */     while ((line = br.readLine()) != null)
/*  70:    */     {
/*  71: 86 */       line = line.toUpperCase().trim();
/*  72: 87 */       if (line.length() != 0) {
/*  73: 89 */         if (line.charAt(0) == '#')
/*  74:    */         {
/*  75: 90 */           if (line.startsWith("#INCLUDE "))
/*  76:    */           {
/*  77: 91 */             line = line.substring(line.indexOf('\\'));
/*  78: 92 */             String url = "smb:" + line.replace('\\', '/');
/*  79: 94 */             if (alt > 0)
/*  80:    */             {
/*  81:    */               try
/*  82:    */               {
/*  83: 96 */                 populate(new InputStreamReader(new SmbFileInputStream(url)));
/*  84:    */               }
/*  85:    */               catch (IOException ioe)
/*  86:    */               {
/*  87: 98 */                 log.println("lmhosts URL: " + url);
/*  88: 99 */                 ioe.printStackTrace(log);
/*  89:    */               }
/*  90:100 */               continue;
/*  91:    */               
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:107 */               alt -= 1;
/*  98:    */               do
/*  99:    */               {
/* 100:108 */                 if ((line = br.readLine()) == null) {
/* 101:    */                   break;
/* 102:    */                 }
/* 103:109 */                 line = line.toUpperCase().trim();
/* 104:110 */               } while (!line.startsWith("#END_ALTERNATE"));
/* 105:    */             }
/* 106:    */             else
/* 107:    */             {
/* 108:115 */               populate(new InputStreamReader(new SmbFileInputStream(url)));
/* 109:    */             }
/* 110:    */           }
/* 111:117 */           else if (line.startsWith("#BEGIN_ALTERNATE"))
/* 112:    */           {
/* 113:118 */             alt += 1;
/* 114:    */           }
/* 115:119 */           else if ((line.startsWith("#END_ALTERNATE")) && (alt > 0))
/* 116:    */           {
/* 117:120 */             alt -= 1;
/* 118:121 */             throw new IOException("no lmhosts alternate includes loaded");
/* 119:    */           }
/* 120:    */         }
/* 121:123 */         else if (Character.isDigit(line.charAt(0)))
/* 122:    */         {
/* 123:124 */           char[] data = line.toCharArray();
/* 124:    */           
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:130 */           char c = '.';
/* 130:    */           int i;
/* 131:131 */           int ip = i = 0;
/* 132:132 */           for (; (i < data.length) && (c == '.'); i++)
/* 133:    */           {
/* 134:133 */             int b = 0;
/* 135:135 */             for (; (i < data.length) && ((c = data[i]) >= '0') && (c <= '9'); i++) {
/* 136:136 */               b = b * 10 + c - 48;
/* 137:    */             }
/* 138:138 */             ip = (ip << 8) + b;
/* 139:    */           }
/* 140:140 */           while ((i < data.length) && (Character.isWhitespace(data[i]))) {
/* 141:141 */             i++;
/* 142:    */           }
/* 143:143 */           int j = i;
/* 144:144 */           while ((j < data.length) && (!Character.isWhitespace(data[j]))) {
/* 145:145 */             j++;
/* 146:    */           }
/* 147:148 */           Name name = new Name(line.substring(i, j), 32, null);
/* 148:149 */           NbtAddress addr = new NbtAddress(name, ip, false, 0, false, false, true, true, NbtAddress.UNKNOWN_MAC_ADDRESS);
/* 149:    */           
/* 150:    */ 
/* 151:152 */           TAB.put(name, addr);
/* 152:    */         }
/* 153:    */       }
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.Lmhosts
 * JD-Core Version:    0.7.0.1
 */