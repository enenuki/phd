/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public final class FieldInfo
/*  10:    */ {
/*  11:    */   ConstPool constPool;
/*  12:    */   int accessFlags;
/*  13:    */   int name;
/*  14:    */   String cachedName;
/*  15:    */   String cachedType;
/*  16:    */   int descriptor;
/*  17:    */   ArrayList attribute;
/*  18:    */   
/*  19:    */   private FieldInfo(ConstPool cp)
/*  20:    */   {
/*  21: 39 */     this.constPool = cp;
/*  22: 40 */     this.accessFlags = 0;
/*  23: 41 */     this.attribute = null;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public FieldInfo(ConstPool cp, String fieldName, String desc)
/*  27:    */   {
/*  28: 54 */     this(cp);
/*  29: 55 */     this.name = cp.addUtf8Info(fieldName);
/*  30: 56 */     this.cachedName = fieldName;
/*  31: 57 */     this.descriptor = cp.addUtf8Info(desc);
/*  32:    */   }
/*  33:    */   
/*  34:    */   FieldInfo(ConstPool cp, DataInputStream in)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 61 */     this(cp);
/*  38: 62 */     read(in);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String toString()
/*  42:    */   {
/*  43: 69 */     return getName() + " " + getDescriptor();
/*  44:    */   }
/*  45:    */   
/*  46:    */   void compact(ConstPool cp)
/*  47:    */   {
/*  48: 81 */     this.name = cp.addUtf8Info(getName());
/*  49: 82 */     this.descriptor = cp.addUtf8Info(getDescriptor());
/*  50: 83 */     this.attribute = AttributeInfo.copyAll(this.attribute, cp);
/*  51: 84 */     this.constPool = cp;
/*  52:    */   }
/*  53:    */   
/*  54:    */   void prune(ConstPool cp)
/*  55:    */   {
/*  56: 88 */     ArrayList newAttributes = new ArrayList();
/*  57: 89 */     AttributeInfo invisibleAnnotations = getAttribute("RuntimeInvisibleAnnotations");
/*  58: 91 */     if (invisibleAnnotations != null)
/*  59:    */     {
/*  60: 92 */       invisibleAnnotations = invisibleAnnotations.copy(cp, null);
/*  61: 93 */       newAttributes.add(invisibleAnnotations);
/*  62:    */     }
/*  63: 96 */     AttributeInfo visibleAnnotations = getAttribute("RuntimeVisibleAnnotations");
/*  64: 98 */     if (visibleAnnotations != null)
/*  65:    */     {
/*  66: 99 */       visibleAnnotations = visibleAnnotations.copy(cp, null);
/*  67:100 */       newAttributes.add(visibleAnnotations);
/*  68:    */     }
/*  69:103 */     AttributeInfo signature = getAttribute("Signature");
/*  70:105 */     if (signature != null)
/*  71:    */     {
/*  72:106 */       signature = signature.copy(cp, null);
/*  73:107 */       newAttributes.add(signature);
/*  74:    */     }
/*  75:110 */     int index = getConstantValue();
/*  76:111 */     if (index != 0)
/*  77:    */     {
/*  78:112 */       index = this.constPool.copy(index, cp, null);
/*  79:113 */       newAttributes.add(new ConstantAttribute(cp, index));
/*  80:    */     }
/*  81:116 */     this.attribute = newAttributes;
/*  82:117 */     this.name = cp.addUtf8Info(getName());
/*  83:118 */     this.descriptor = cp.addUtf8Info(getDescriptor());
/*  84:119 */     this.constPool = cp;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ConstPool getConstPool()
/*  88:    */   {
/*  89:127 */     return this.constPool;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getName()
/*  93:    */   {
/*  94:134 */     if (this.cachedName == null) {
/*  95:135 */       this.cachedName = this.constPool.getUtf8Info(this.name);
/*  96:    */     }
/*  97:137 */     return this.cachedName;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setName(String newName)
/* 101:    */   {
/* 102:144 */     this.name = this.constPool.addUtf8Info(newName);
/* 103:145 */     this.cachedName = newName;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getAccessFlags()
/* 107:    */   {
/* 108:154 */     return this.accessFlags;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setAccessFlags(int acc)
/* 112:    */   {
/* 113:163 */     this.accessFlags = acc;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescriptor()
/* 117:    */   {
/* 118:172 */     return this.constPool.getUtf8Info(this.descriptor);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescriptor(String desc)
/* 122:    */   {
/* 123:181 */     if (!desc.equals(getDescriptor())) {
/* 124:182 */       this.descriptor = this.constPool.addUtf8Info(desc);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getConstantValue()
/* 129:    */   {
/* 130:192 */     if ((this.accessFlags & 0x8) == 0) {
/* 131:193 */       return 0;
/* 132:    */     }
/* 133:195 */     ConstantAttribute attr = (ConstantAttribute)getAttribute("ConstantValue");
/* 134:197 */     if (attr == null) {
/* 135:198 */       return 0;
/* 136:    */     }
/* 137:200 */     return attr.getConstantValue();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List getAttributes()
/* 141:    */   {
/* 142:214 */     if (this.attribute == null) {
/* 143:215 */       this.attribute = new ArrayList();
/* 144:    */     }
/* 145:217 */     return this.attribute;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public AttributeInfo getAttribute(String name)
/* 149:    */   {
/* 150:228 */     return AttributeInfo.lookup(this.attribute, name);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void addAttribute(AttributeInfo info)
/* 154:    */   {
/* 155:238 */     if (this.attribute == null) {
/* 156:239 */       this.attribute = new ArrayList();
/* 157:    */     }
/* 158:241 */     AttributeInfo.remove(this.attribute, info.getName());
/* 159:242 */     this.attribute.add(info);
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void read(DataInputStream in)
/* 163:    */     throws IOException
/* 164:    */   {
/* 165:246 */     this.accessFlags = in.readUnsignedShort();
/* 166:247 */     this.name = in.readUnsignedShort();
/* 167:248 */     this.descriptor = in.readUnsignedShort();
/* 168:249 */     int n = in.readUnsignedShort();
/* 169:250 */     this.attribute = new ArrayList();
/* 170:251 */     for (int i = 0; i < n; i++) {
/* 171:252 */       this.attribute.add(AttributeInfo.read(this.constPool, in));
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   void write(DataOutputStream out)
/* 176:    */     throws IOException
/* 177:    */   {
/* 178:256 */     out.writeShort(this.accessFlags);
/* 179:257 */     out.writeShort(this.name);
/* 180:258 */     out.writeShort(this.descriptor);
/* 181:259 */     if (this.attribute == null)
/* 182:    */     {
/* 183:260 */       out.writeShort(0);
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:262 */       out.writeShort(this.attribute.size());
/* 188:263 */       AttributeInfo.writeAll(this.attribute, out);
/* 189:    */     }
/* 190:    */   }
/* 191:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.FieldInfo
 * JD-Core Version:    0.7.0.1
 */