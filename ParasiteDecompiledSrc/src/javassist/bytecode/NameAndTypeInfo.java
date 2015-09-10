/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class NameAndTypeInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 12;
/*   13:     */   int memberName;
/*   14:     */   int typeDescriptor;
/*   15:     */   
/*   16:     */   public NameAndTypeInfo(int name, int type)
/*   17:     */   {
/*   18:1247 */     this.memberName = name;
/*   19:1248 */     this.typeDescriptor = type;
/*   20:     */   }
/*   21:     */   
/*   22:     */   public NameAndTypeInfo(DataInputStream in)
/*   23:     */     throws IOException
/*   24:     */   {
/*   25:1252 */     this.memberName = in.readUnsignedShort();
/*   26:1253 */     this.typeDescriptor = in.readUnsignedShort();
/*   27:     */   }
/*   28:     */   
/*   29:     */   boolean hashCheck(int a, int b)
/*   30:     */   {
/*   31:1256 */     return (a == this.memberName) && (b == this.typeDescriptor);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public int getTag()
/*   35:     */   {
/*   36:1258 */     return 12;
/*   37:     */   }
/*   38:     */   
/*   39:     */   public void renameClass(ConstPool cp, String oldName, String newName)
/*   40:     */   {
/*   41:1261 */     String type = cp.getUtf8Info(this.typeDescriptor);
/*   42:1262 */     String type2 = Descriptor.rename(type, oldName, newName);
/*   43:1263 */     if (type != type2) {
/*   44:1264 */       this.typeDescriptor = cp.addUtf8Info(type2);
/*   45:     */     }
/*   46:     */   }
/*   47:     */   
/*   48:     */   public void renameClass(ConstPool cp, Map map)
/*   49:     */   {
/*   50:1268 */     String type = cp.getUtf8Info(this.typeDescriptor);
/*   51:1269 */     String type2 = Descriptor.rename(type, map);
/*   52:1270 */     if (type != type2) {
/*   53:1271 */       this.typeDescriptor = cp.addUtf8Info(type2);
/*   54:     */     }
/*   55:     */   }
/*   56:     */   
/*   57:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   58:     */   {
/*   59:1275 */     String mname = src.getUtf8Info(this.memberName);
/*   60:1276 */     String tdesc = src.getUtf8Info(this.typeDescriptor);
/*   61:1277 */     tdesc = Descriptor.rename(tdesc, map);
/*   62:1278 */     return dest.addNameAndTypeInfo(dest.addUtf8Info(mname), dest.addUtf8Info(tdesc));
/*   63:     */   }
/*   64:     */   
/*   65:     */   public void write(DataOutputStream out)
/*   66:     */     throws IOException
/*   67:     */   {
/*   68:1283 */     out.writeByte(12);
/*   69:1284 */     out.writeShort(this.memberName);
/*   70:1285 */     out.writeShort(this.typeDescriptor);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public void print(PrintWriter out)
/*   74:     */   {
/*   75:1289 */     out.print("NameAndType #");
/*   76:1290 */     out.print(this.memberName);
/*   77:1291 */     out.print(", type #");
/*   78:1292 */     out.println(this.typeDescriptor);
/*   79:     */   }
/*   80:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.NameAndTypeInfo
 * JD-Core Version:    0.7.0.1
 */