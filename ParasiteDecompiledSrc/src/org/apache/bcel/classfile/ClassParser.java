/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.FilterInputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.util.zip.ZipEntry;
/*  10:    */ import java.util.zip.ZipFile;
/*  11:    */ 
/*  12:    */ public final class ClassParser
/*  13:    */ {
/*  14:    */   private DataInputStream file;
/*  15:    */   private ZipFile zip;
/*  16:    */   private String file_name;
/*  17:    */   private int class_name_index;
/*  18:    */   private int superclass_name_index;
/*  19:    */   private int major;
/*  20:    */   private int minor;
/*  21:    */   private int access_flags;
/*  22:    */   private int[] interfaces;
/*  23:    */   private ConstantPool constant_pool;
/*  24:    */   private Field[] fields;
/*  25:    */   private Method[] methods;
/*  26:    */   private Attribute[] attributes;
/*  27:    */   private boolean is_zip;
/*  28:    */   private static final int BUFSIZE = 8192;
/*  29:    */   
/*  30:    */   public ClassParser(InputStream file, String file_name)
/*  31:    */   {
/*  32:100 */     this.file_name = file_name;
/*  33:    */     
/*  34:102 */     String clazz = file.getClass().getName();
/*  35:103 */     this.is_zip = ((clazz.startsWith("java.util.zip.")) || (clazz.startsWith("java.util.jar.")));
/*  36:105 */     if ((file instanceof DataInputStream)) {
/*  37:106 */       this.file = ((DataInputStream)file);
/*  38:    */     } else {
/*  39:108 */       this.file = new DataInputStream(new BufferedInputStream(file, 8192));
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ClassParser(String file_name)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:118 */     this.is_zip = false;
/*  47:119 */     this.file_name = file_name;
/*  48:120 */     this.file = new DataInputStream(new BufferedInputStream(new FileInputStream(file_name), 8192));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ClassParser(String zip_file, String file_name)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:131 */     this.is_zip = true;
/*  55:132 */     this.zip = new ZipFile(zip_file);
/*  56:133 */     ZipEntry entry = this.zip.getEntry(file_name);
/*  57:    */     
/*  58:135 */     this.file_name = file_name;
/*  59:    */     
/*  60:137 */     this.file = new DataInputStream(new BufferedInputStream(this.zip.getInputStream(entry), 8192));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public JavaClass parse()
/*  64:    */     throws IOException, ClassFormatError
/*  65:    */   {
/*  66:156 */     readID();
/*  67:    */     
/*  68:    */ 
/*  69:159 */     readVersion();
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:163 */     readConstantPool();
/*  74:    */     
/*  75:    */ 
/*  76:166 */     readClassInfo();
/*  77:    */     
/*  78:    */ 
/*  79:169 */     readInterfaces();
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:173 */     readFields();
/*  84:    */     
/*  85:    */ 
/*  86:176 */     readMethods();
/*  87:    */     
/*  88:    */ 
/*  89:179 */     readAttributes();
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:199 */     this.file.close();
/* 110:200 */     if (this.zip != null) {
/* 111:201 */       this.zip.close();
/* 112:    */     }
/* 113:204 */     return new JavaClass(this.class_name_index, this.superclass_name_index, this.file_name, this.major, this.minor, this.access_flags, this.constant_pool, this.interfaces, this.fields, this.methods, this.attributes, (byte)(this.is_zip ? 3 : 2));
/* 114:    */   }
/* 115:    */   
/* 116:    */   private final void readAttributes()
/* 117:    */     throws IOException, ClassFormatError
/* 118:    */   {
/* 119:219 */     int attributes_count = this.file.readUnsignedShort();
/* 120:220 */     this.attributes = new Attribute[attributes_count];
/* 121:222 */     for (int i = 0; i < attributes_count; i++) {
/* 122:223 */       this.attributes[i] = Attribute.readAttribute(this.file, this.constant_pool);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   private final void readClassInfo()
/* 127:    */     throws IOException, ClassFormatError
/* 128:    */   {
/* 129:233 */     this.access_flags = this.file.readUnsignedShort();
/* 130:238 */     if ((this.access_flags & 0x200) != 0) {
/* 131:239 */       this.access_flags |= 0x400;
/* 132:    */     }
/* 133:241 */     if (((this.access_flags & 0x400) != 0) && ((this.access_flags & 0x10) != 0)) {
/* 134:243 */       throw new ClassFormatError("Class can't be both final and abstract");
/* 135:    */     }
/* 136:245 */     this.class_name_index = this.file.readUnsignedShort();
/* 137:246 */     this.superclass_name_index = this.file.readUnsignedShort();
/* 138:    */   }
/* 139:    */   
/* 140:    */   private final void readConstantPool()
/* 141:    */     throws IOException, ClassFormatError
/* 142:    */   {
/* 143:255 */     this.constant_pool = new ConstantPool(this.file);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private final void readFields()
/* 147:    */     throws IOException, ClassFormatError
/* 148:    */   {
/* 149:267 */     int fields_count = this.file.readUnsignedShort();
/* 150:268 */     this.fields = new Field[fields_count];
/* 151:270 */     for (int i = 0; i < fields_count; i++) {
/* 152:271 */       this.fields[i] = new Field(this.file, this.constant_pool);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private final void readID()
/* 157:    */     throws IOException, ClassFormatError
/* 158:    */   {
/* 159:284 */     int magic = -889275714;
/* 160:286 */     if (this.file.readInt() != magic) {
/* 161:287 */       throw new ClassFormatError(this.file_name + " is not a Java .class file");
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private final void readInterfaces()
/* 166:    */     throws IOException, ClassFormatError
/* 167:    */   {
/* 168:298 */     int interfaces_count = this.file.readUnsignedShort();
/* 169:299 */     this.interfaces = new int[interfaces_count];
/* 170:301 */     for (int i = 0; i < interfaces_count; i++) {
/* 171:302 */       this.interfaces[i] = this.file.readUnsignedShort();
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private final void readMethods()
/* 176:    */     throws IOException, ClassFormatError
/* 177:    */   {
/* 178:313 */     int methods_count = this.file.readUnsignedShort();
/* 179:314 */     this.methods = new Method[methods_count];
/* 180:316 */     for (int i = 0; i < methods_count; i++) {
/* 181:317 */       this.methods[i] = new Method(this.file, this.constant_pool);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   private final void readVersion()
/* 186:    */     throws IOException, ClassFormatError
/* 187:    */   {
/* 188:326 */     this.minor = this.file.readUnsignedShort();
/* 189:327 */     this.major = this.file.readUnsignedShort();
/* 190:    */   }
/* 191:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ClassParser
 * JD-Core Version:    0.7.0.1
 */