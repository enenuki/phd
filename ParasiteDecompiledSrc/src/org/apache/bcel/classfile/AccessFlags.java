/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ public abstract class AccessFlags
/*   4:    */ {
/*   5:    */   protected int access_flags;
/*   6:    */   
/*   7:    */   public AccessFlags() {}
/*   8:    */   
/*   9:    */   public AccessFlags(int a)
/*  10:    */   {
/*  11: 75 */     this.access_flags = a;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public final int getAccessFlags()
/*  15:    */   {
/*  16: 81 */     return this.access_flags;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public final void setAccessFlags(int access_flags)
/*  20:    */   {
/*  21: 87 */     this.access_flags = access_flags;
/*  22:    */   }
/*  23:    */   
/*  24:    */   private final void setFlag(int flag, boolean set)
/*  25:    */   {
/*  26: 91 */     if ((this.access_flags & flag) != 0)
/*  27:    */     {
/*  28: 92 */       if (!set) {
/*  29: 93 */         this.access_flags ^= flag;
/*  30:    */       }
/*  31:    */     }
/*  32: 95 */     else if (set) {
/*  33: 96 */       this.access_flags |= flag;
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final void isPublic(boolean flag)
/*  38:    */   {
/*  39:100 */     setFlag(1, flag);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final boolean isPublic()
/*  43:    */   {
/*  44:102 */     return (this.access_flags & 0x1) != 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void isPrivate(boolean flag)
/*  48:    */   {
/*  49:105 */     setFlag(2, flag);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final boolean isPrivate()
/*  53:    */   {
/*  54:107 */     return (this.access_flags & 0x2) != 0;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final void isProtected(boolean flag)
/*  58:    */   {
/*  59:110 */     setFlag(4, flag);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final boolean isProtected()
/*  63:    */   {
/*  64:112 */     return (this.access_flags & 0x4) != 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void isStatic(boolean flag)
/*  68:    */   {
/*  69:115 */     setFlag(8, flag);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final boolean isStatic()
/*  73:    */   {
/*  74:117 */     return (this.access_flags & 0x8) != 0;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final void isFinal(boolean flag)
/*  78:    */   {
/*  79:120 */     setFlag(16, flag);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final boolean isFinal()
/*  83:    */   {
/*  84:122 */     return (this.access_flags & 0x10) != 0;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final void isSynchronized(boolean flag)
/*  88:    */   {
/*  89:125 */     setFlag(32, flag);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final boolean isSynchronized()
/*  93:    */   {
/*  94:127 */     return (this.access_flags & 0x20) != 0;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final void isVolatile(boolean flag)
/*  98:    */   {
/*  99:130 */     setFlag(64, flag);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final boolean isVolatile()
/* 103:    */   {
/* 104:132 */     return (this.access_flags & 0x40) != 0;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final void isTransient(boolean flag)
/* 108:    */   {
/* 109:135 */     setFlag(128, flag);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final boolean isTransient()
/* 113:    */   {
/* 114:137 */     return (this.access_flags & 0x80) != 0;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final void isNative(boolean flag)
/* 118:    */   {
/* 119:140 */     setFlag(256, flag);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final boolean isNative()
/* 123:    */   {
/* 124:142 */     return (this.access_flags & 0x100) != 0;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final void isInterface(boolean flag)
/* 128:    */   {
/* 129:145 */     setFlag(512, flag);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final boolean isInterface()
/* 133:    */   {
/* 134:147 */     return (this.access_flags & 0x200) != 0;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public final void isAbstract(boolean flag)
/* 138:    */   {
/* 139:150 */     setFlag(1024, flag);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public final boolean isAbstract()
/* 143:    */   {
/* 144:152 */     return (this.access_flags & 0x400) != 0;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public final void isStrictfp(boolean flag)
/* 148:    */   {
/* 149:155 */     setFlag(2048, flag);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final boolean isStrictfp()
/* 153:    */   {
/* 154:157 */     return (this.access_flags & 0x800) != 0;
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.AccessFlags
 * JD-Core Version:    0.7.0.1
 */