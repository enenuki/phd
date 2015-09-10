/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class Utf8Info
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 1;
/*   13:     */   String string;
/*   14:     */   int index;
/*   15:     */   
/*   16:     */   public Utf8Info(String utf8, int i)
/*   17:     */   {
/*   18:1547 */     this.string = utf8;
/*   19:1548 */     this.index = i;
/*   20:     */   }
/*   21:     */   
/*   22:     */   public Utf8Info(DataInputStream in, int i)
/*   23:     */     throws IOException
/*   24:     */   {
/*   25:1552 */     this.string = in.readUTF();
/*   26:1553 */     this.index = i;
/*   27:     */   }
/*   28:     */   
/*   29:     */   public int getTag()
/*   30:     */   {
/*   31:1556 */     return 1;
/*   32:     */   }
/*   33:     */   
/*   34:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   35:     */   {
/*   36:1559 */     return dest.addUtf8Info(this.string);
/*   37:     */   }
/*   38:     */   
/*   39:     */   public void write(DataOutputStream out)
/*   40:     */     throws IOException
/*   41:     */   {
/*   42:1563 */     out.writeByte(1);
/*   43:1564 */     out.writeUTF(this.string);
/*   44:     */   }
/*   45:     */   
/*   46:     */   public void print(PrintWriter out)
/*   47:     */   {
/*   48:1568 */     out.print("UTF8 \"");
/*   49:1569 */     out.print(this.string);
/*   50:1570 */     out.println("\"");
/*   51:     */   }
/*   52:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.Utf8Info
 * JD-Core Version:    0.7.0.1
 */