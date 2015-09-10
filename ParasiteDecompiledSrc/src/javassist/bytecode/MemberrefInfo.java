/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ abstract class MemberrefInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   int classIndex;
/*   13:     */   int nameAndTypeIndex;
/*   14:     */   
/*   15:     */   public MemberrefInfo(int cindex, int ntindex)
/*   16:     */   {
/*   17:1301 */     this.classIndex = cindex;
/*   18:1302 */     this.nameAndTypeIndex = ntindex;
/*   19:     */   }
/*   20:     */   
/*   21:     */   public MemberrefInfo(DataInputStream in)
/*   22:     */     throws IOException
/*   23:     */   {
/*   24:1306 */     this.classIndex = in.readUnsignedShort();
/*   25:1307 */     this.nameAndTypeIndex = in.readUnsignedShort();
/*   26:     */   }
/*   27:     */   
/*   28:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   29:     */   {
/*   30:1311 */     int classIndex2 = src.getItem(this.classIndex).copy(src, dest, map);
/*   31:1312 */     int ntIndex2 = src.getItem(this.nameAndTypeIndex).copy(src, dest, map);
/*   32:1313 */     return copy2(dest, classIndex2, ntIndex2);
/*   33:     */   }
/*   34:     */   
/*   35:     */   boolean hashCheck(int a, int b)
/*   36:     */   {
/*   37:1316 */     return (a == this.classIndex) && (b == this.nameAndTypeIndex);
/*   38:     */   }
/*   39:     */   
/*   40:     */   protected abstract int copy2(ConstPool paramConstPool, int paramInt1, int paramInt2);
/*   41:     */   
/*   42:     */   public void write(DataOutputStream out)
/*   43:     */     throws IOException
/*   44:     */   {
/*   45:1321 */     out.writeByte(getTag());
/*   46:1322 */     out.writeShort(this.classIndex);
/*   47:1323 */     out.writeShort(this.nameAndTypeIndex);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public void print(PrintWriter out)
/*   51:     */   {
/*   52:1327 */     out.print(getTagName() + " #");
/*   53:1328 */     out.print(this.classIndex);
/*   54:1329 */     out.print(", name&type #");
/*   55:1330 */     out.println(this.nameAndTypeIndex);
/*   56:     */   }
/*   57:     */   
/*   58:     */   public abstract String getTagName();
/*   59:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.MemberrefInfo
 * JD-Core Version:    0.7.0.1
 */