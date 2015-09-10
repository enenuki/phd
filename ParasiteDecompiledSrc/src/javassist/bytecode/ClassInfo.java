/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.Map;
/*    9:     */ 
/*   10:     */ class ClassInfo
/*   11:     */   extends ConstInfo
/*   12:     */ {
/*   13:     */   static final int tag = 7;
/*   14:     */   int name;
/*   15:     */   int index;
/*   16:     */   
/*   17:     */   public ClassInfo(int className, int i)
/*   18:     */   {
/*   19:1174 */     this.name = className;
/*   20:1175 */     this.index = i;
/*   21:     */   }
/*   22:     */   
/*   23:     */   public ClassInfo(DataInputStream in, int i)
/*   24:     */     throws IOException
/*   25:     */   {
/*   26:1179 */     this.name = in.readUnsignedShort();
/*   27:1180 */     this.index = i;
/*   28:     */   }
/*   29:     */   
/*   30:     */   public int getTag()
/*   31:     */   {
/*   32:1183 */     return 7;
/*   33:     */   }
/*   34:     */   
/*   35:     */   public String getClassName(ConstPool cp)
/*   36:     */   {
/*   37:1186 */     return cp.getUtf8Info(this.name);
/*   38:     */   }
/*   39:     */   
/*   40:     */   public void renameClass(ConstPool cp, String oldName, String newName)
/*   41:     */   {
/*   42:1190 */     String nameStr = cp.getUtf8Info(this.name);
/*   43:1191 */     if (nameStr.equals(oldName))
/*   44:     */     {
/*   45:1192 */       this.name = cp.addUtf8Info(newName);
/*   46:     */     }
/*   47:1193 */     else if (nameStr.charAt(0) == '[')
/*   48:     */     {
/*   49:1194 */       String nameStr2 = Descriptor.rename(nameStr, oldName, newName);
/*   50:1195 */       if (nameStr != nameStr2) {
/*   51:1196 */         this.name = cp.addUtf8Info(nameStr2);
/*   52:     */       }
/*   53:     */     }
/*   54:     */   }
/*   55:     */   
/*   56:     */   public void renameClass(ConstPool cp, Map map)
/*   57:     */   {
/*   58:1201 */     String oldName = cp.getUtf8Info(this.name);
/*   59:1202 */     if (oldName.charAt(0) == '[')
/*   60:     */     {
/*   61:1203 */       String newName = Descriptor.rename(oldName, map);
/*   62:1204 */       if (oldName != newName) {
/*   63:1205 */         this.name = cp.addUtf8Info(newName);
/*   64:     */       }
/*   65:     */     }
/*   66:     */     else
/*   67:     */     {
/*   68:1208 */       String newName = (String)map.get(oldName);
/*   69:1209 */       if ((newName != null) && (!newName.equals(oldName))) {
/*   70:1210 */         this.name = cp.addUtf8Info(newName);
/*   71:     */       }
/*   72:     */     }
/*   73:     */   }
/*   74:     */   
/*   75:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   76:     */   {
/*   77:1215 */     String classname = src.getUtf8Info(this.name);
/*   78:1216 */     if (map != null)
/*   79:     */     {
/*   80:1217 */       String newname = (String)map.get(classname);
/*   81:1218 */       if (newname != null) {
/*   82:1219 */         classname = newname;
/*   83:     */       }
/*   84:     */     }
/*   85:1222 */     return dest.addClassInfo(classname);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void write(DataOutputStream out)
/*   89:     */     throws IOException
/*   90:     */   {
/*   91:1226 */     out.writeByte(7);
/*   92:1227 */     out.writeShort(this.name);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public void print(PrintWriter out)
/*   96:     */   {
/*   97:1231 */     out.print("Class #");
/*   98:1232 */     out.println(this.name);
/*   99:     */   }
/*  100:     */   
/*  101:     */   void makeHashtable(ConstPool cp)
/*  102:     */   {
/*  103:1236 */     String name = Descriptor.toJavaName(getClassName(cp));
/*  104:1237 */     cp.classes.put(name, this);
/*  105:     */   }
/*  106:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ClassInfo
 * JD-Core Version:    0.7.0.1
 */