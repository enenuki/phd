/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class StringInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 8;
/*   13:     */   int string;
/*   14:     */   
/*   15:     */   public StringInfo(int str)
/*   16:     */   {
/*   17:1401 */     this.string = str;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public StringInfo(DataInputStream in)
/*   21:     */     throws IOException
/*   22:     */   {
/*   23:1405 */     this.string = in.readUnsignedShort();
/*   24:     */   }
/*   25:     */   
/*   26:     */   public int getTag()
/*   27:     */   {
/*   28:1408 */     return 8;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   32:     */   {
/*   33:1411 */     return dest.addStringInfo(src.getUtf8Info(this.string));
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void write(DataOutputStream out)
/*   37:     */     throws IOException
/*   38:     */   {
/*   39:1415 */     out.writeByte(8);
/*   40:1416 */     out.writeShort(this.string);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public void print(PrintWriter out)
/*   44:     */   {
/*   45:1420 */     out.print("String #");
/*   46:1421 */     out.println(this.string);
/*   47:     */   }
/*   48:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.StringInfo
 * JD-Core Version:    0.7.0.1
 */