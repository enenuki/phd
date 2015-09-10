/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ public class UUID
/*  4:   */   extends rpc.uuid_t
/*  5:   */ {
/*  6:   */   public static int hex_to_bin(char[] arr, int offset, int length)
/*  7:   */   {
/*  8:27 */     int value = 0;
/*  9:   */     
/* 10:   */ 
/* 11:30 */     int count = 0;
/* 12:31 */     for (int ai = offset; (ai < arr.length) && (count < length); ai++)
/* 13:   */     {
/* 14:32 */       value <<= 4;
/* 15:33 */       switch (arr[ai])
/* 16:   */       {
/* 17:   */       case '0': 
/* 18:   */       case '1': 
/* 19:   */       case '2': 
/* 20:   */       case '3': 
/* 21:   */       case '4': 
/* 22:   */       case '5': 
/* 23:   */       case '6': 
/* 24:   */       case '7': 
/* 25:   */       case '8': 
/* 26:   */       case '9': 
/* 27:36 */         value += arr[ai] - '0';
/* 28:37 */         break;
/* 29:   */       case 'A': 
/* 30:   */       case 'B': 
/* 31:   */       case 'C': 
/* 32:   */       case 'D': 
/* 33:   */       case 'E': 
/* 34:   */       case 'F': 
/* 35:39 */         value += 10 + (arr[ai] - 'A');
/* 36:40 */         break;
/* 37:   */       case 'a': 
/* 38:   */       case 'b': 
/* 39:   */       case 'c': 
/* 40:   */       case 'd': 
/* 41:   */       case 'e': 
/* 42:   */       case 'f': 
/* 43:42 */         value += 10 + (arr[ai] - 'a');
/* 44:43 */         break;
/* 45:   */       case ':': 
/* 46:   */       case ';': 
/* 47:   */       case '<': 
/* 48:   */       case '=': 
/* 49:   */       case '>': 
/* 50:   */       case '?': 
/* 51:   */       case '@': 
/* 52:   */       case 'G': 
/* 53:   */       case 'H': 
/* 54:   */       case 'I': 
/* 55:   */       case 'J': 
/* 56:   */       case 'K': 
/* 57:   */       case 'L': 
/* 58:   */       case 'M': 
/* 59:   */       case 'N': 
/* 60:   */       case 'O': 
/* 61:   */       case 'P': 
/* 62:   */       case 'Q': 
/* 63:   */       case 'R': 
/* 64:   */       case 'S': 
/* 65:   */       case 'T': 
/* 66:   */       case 'U': 
/* 67:   */       case 'V': 
/* 68:   */       case 'W': 
/* 69:   */       case 'X': 
/* 70:   */       case 'Y': 
/* 71:   */       case 'Z': 
/* 72:   */       case '[': 
/* 73:   */       case '\\': 
/* 74:   */       case ']': 
/* 75:   */       case '^': 
/* 76:   */       case '_': 
/* 77:   */       case '`': 
/* 78:   */       default: 
/* 79:45 */         throw new IllegalArgumentException(new String(arr, offset, length));
/* 80:   */       }
/* 81:47 */       count++;
/* 82:   */     }
/* 83:50 */     return value;
/* 84:   */   }
/* 85:   */   
/* 86:52 */   static final char[] HEXCHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/* 87:   */   
/* 88:   */   public static String bin_to_hex(int value, int length)
/* 89:   */   {
/* 90:56 */     char[] arr = new char[length];
/* 91:57 */     int ai = arr.length;
/* 92:58 */     while (ai-- > 0)
/* 93:   */     {
/* 94:59 */       arr[ai] = HEXCHARS[(value & 0xF)];
/* 95:60 */       value >>>= 4;
/* 96:   */     }
/* 97:62 */     return new String(arr);
/* 98:   */   }
/* 99:   */   
/* :0:   */   private static byte B(int i)
/* :1:   */   {
/* :2:64 */     return (byte)(i & 0xFF);
/* :3:   */   }
/* :4:   */   
/* :5:   */   private static short S(int i)
/* :6:   */   {
/* :7:65 */     return (short)(i & 0xFFFF);
/* :8:   */   }
/* :9:   */   
/* ;0:   */   public UUID(rpc.uuid_t uuid)
/* ;1:   */   {
/* ;2:68 */     this.time_low = uuid.time_low;
/* ;3:69 */     this.time_mid = uuid.time_mid;
/* ;4:70 */     this.time_hi_and_version = uuid.time_hi_and_version;
/* ;5:71 */     this.clock_seq_hi_and_reserved = uuid.clock_seq_hi_and_reserved;
/* ;6:72 */     this.clock_seq_low = uuid.clock_seq_low;
/* ;7:73 */     this.node = new byte[6];
/* ;8:74 */     this.node[0] = uuid.node[0];
/* ;9:75 */     this.node[1] = uuid.node[1];
/* <0:76 */     this.node[2] = uuid.node[2];
/* <1:77 */     this.node[3] = uuid.node[3];
/* <2:78 */     this.node[4] = uuid.node[4];
/* <3:79 */     this.node[5] = uuid.node[5];
/* <4:   */   }
/* <5:   */   
/* <6:   */   public UUID(String str)
/* <7:   */   {
/* <8:82 */     char[] arr = str.toCharArray();
/* <9:83 */     this.time_low = hex_to_bin(arr, 0, 8);
/* =0:84 */     this.time_mid = S(hex_to_bin(arr, 9, 4));
/* =1:85 */     this.time_hi_and_version = S(hex_to_bin(arr, 14, 4));
/* =2:86 */     this.clock_seq_hi_and_reserved = B(hex_to_bin(arr, 19, 2));
/* =3:87 */     this.clock_seq_low = B(hex_to_bin(arr, 21, 2));
/* =4:88 */     this.node = new byte[6];
/* =5:89 */     this.node[0] = B(hex_to_bin(arr, 24, 2));
/* =6:90 */     this.node[1] = B(hex_to_bin(arr, 26, 2));
/* =7:91 */     this.node[2] = B(hex_to_bin(arr, 28, 2));
/* =8:92 */     this.node[3] = B(hex_to_bin(arr, 30, 2));
/* =9:93 */     this.node[4] = B(hex_to_bin(arr, 32, 2));
/* >0:94 */     this.node[5] = B(hex_to_bin(arr, 34, 2));
/* >1:   */   }
/* >2:   */   
/* >3:   */   public String toString()
/* >4:   */   {
/* >5:98 */     return bin_to_hex(this.time_low, 8) + '-' + bin_to_hex(this.time_mid, 4) + '-' + bin_to_hex(this.time_hi_and_version, 4) + '-' + bin_to_hex(this.clock_seq_hi_and_reserved, 2) + bin_to_hex(this.clock_seq_low, 2) + '-' + bin_to_hex(this.node[0], 2) + bin_to_hex(this.node[1], 2) + bin_to_hex(this.node[2], 2) + bin_to_hex(this.node[3], 2) + bin_to_hex(this.node[4], 2) + bin_to_hex(this.node[5], 2);
/* >6:   */   }
/* >7:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.UUID
 * JD-Core Version:    0.7.0.1
 */