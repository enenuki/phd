/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.util.Hexdump;
/*   4:    */ 
/*   5:    */ public class ACE
/*   6:    */ {
/*   7:    */   public static final int FILE_READ_DATA = 1;
/*   8:    */   public static final int FILE_WRITE_DATA = 2;
/*   9:    */   public static final int FILE_APPEND_DATA = 4;
/*  10:    */   public static final int FILE_READ_EA = 8;
/*  11:    */   public static final int FILE_WRITE_EA = 16;
/*  12:    */   public static final int FILE_EXECUTE = 32;
/*  13:    */   public static final int FILE_DELETE = 64;
/*  14:    */   public static final int FILE_READ_ATTRIBUTES = 128;
/*  15:    */   public static final int FILE_WRITE_ATTRIBUTES = 256;
/*  16:    */   public static final int DELETE = 65536;
/*  17:    */   public static final int READ_CONTROL = 131072;
/*  18:    */   public static final int WRITE_DAC = 262144;
/*  19:    */   public static final int WRITE_OWNER = 524288;
/*  20:    */   public static final int SYNCHRONIZE = 1048576;
/*  21:    */   public static final int GENERIC_ALL = 268435456;
/*  22:    */   public static final int GENERIC_EXECUTE = 536870912;
/*  23:    */   public static final int GENERIC_WRITE = 1073741824;
/*  24:    */   public static final int GENERIC_READ = -2147483648;
/*  25:    */   public static final int FLAGS_OBJECT_INHERIT = 1;
/*  26:    */   public static final int FLAGS_CONTAINER_INHERIT = 2;
/*  27:    */   public static final int FLAGS_NO_PROPAGATE = 4;
/*  28:    */   public static final int FLAGS_INHERIT_ONLY = 8;
/*  29:    */   public static final int FLAGS_INHERITED = 16;
/*  30:    */   boolean allow;
/*  31:    */   int flags;
/*  32:    */   int access;
/*  33:    */   SID sid;
/*  34:    */   
/*  35:    */   public boolean isAllow()
/*  36:    */   {
/*  37: 84 */     return this.allow;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isInherited()
/*  41:    */   {
/*  42: 95 */     return (this.flags & 0x10) != 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getFlags()
/*  46:    */   {
/*  47:102 */     return this.flags;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getApplyToText()
/*  51:    */   {
/*  52:110 */     switch (this.flags & 0xB)
/*  53:    */     {
/*  54:    */     case 0: 
/*  55:112 */       return "This folder only";
/*  56:    */     case 3: 
/*  57:114 */       return "This folder, subfolders and files";
/*  58:    */     case 11: 
/*  59:116 */       return "Subfolders and files only";
/*  60:    */     case 2: 
/*  61:118 */       return "This folder and subfolders";
/*  62:    */     case 10: 
/*  63:120 */       return "Subfolders only";
/*  64:    */     case 1: 
/*  65:122 */       return "This folder and files";
/*  66:    */     case 9: 
/*  67:124 */       return "Files only";
/*  68:    */     }
/*  69:126 */     return "Invalid";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getAccessMask()
/*  73:    */   {
/*  74:135 */     return this.access;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public SID getSID()
/*  78:    */   {
/*  79:142 */     return this.sid;
/*  80:    */   }
/*  81:    */   
/*  82:    */   int decode(byte[] buf, int bi)
/*  83:    */   {
/*  84:146 */     this.allow = (buf[(bi++)] == 0);
/*  85:147 */     this.flags = (buf[(bi++)] & 0xFF);
/*  86:148 */     int size = ServerMessageBlock.readInt2(buf, bi);
/*  87:149 */     bi += 2;
/*  88:150 */     this.access = ServerMessageBlock.readInt4(buf, bi);
/*  89:151 */     bi += 4;
/*  90:152 */     this.sid = new SID(buf, bi);
/*  91:153 */     return size;
/*  92:    */   }
/*  93:    */   
/*  94:    */   void appendCol(StringBuffer sb, String str, int width)
/*  95:    */   {
/*  96:157 */     sb.append(str);
/*  97:158 */     int count = width - str.length();
/*  98:159 */     for (int i = 0; i < count; i++) {
/*  99:160 */       sb.append(' ');
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String toString()
/* 104:    */   {
/* 105:173 */     StringBuffer sb = new StringBuffer();
/* 106:174 */     sb.append(isAllow() ? "Allow " : "Deny  ");
/* 107:175 */     appendCol(sb, this.sid.toDisplayString(), 25);
/* 108:176 */     sb.append(" 0x").append(Hexdump.toHexString(this.access, 8)).append(' ');
/* 109:177 */     sb.append(isInherited() ? "Inherited " : "Direct    ");
/* 110:178 */     appendCol(sb, getApplyToText(), 34);
/* 111:179 */     return sb.toString();
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.ACE
 * JD-Core Version:    0.7.0.1
 */