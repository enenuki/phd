/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class IntegerInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 3;
/*   13:     */   int value;
/*   14:     */   
/*   15:     */   public IntegerInfo(int i)
/*   16:     */   {
/*   17:1430 */     this.value = i;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public IntegerInfo(DataInputStream in)
/*   21:     */     throws IOException
/*   22:     */   {
/*   23:1434 */     this.value = in.readInt();
/*   24:     */   }
/*   25:     */   
/*   26:     */   public int getTag()
/*   27:     */   {
/*   28:1437 */     return 3;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   32:     */   {
/*   33:1440 */     return dest.addIntegerInfo(this.value);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void write(DataOutputStream out)
/*   37:     */     throws IOException
/*   38:     */   {
/*   39:1444 */     out.writeByte(3);
/*   40:1445 */     out.writeInt(this.value);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public void print(PrintWriter out)
/*   44:     */   {
/*   45:1449 */     out.print("Integer ");
/*   46:1450 */     out.println(this.value);
/*   47:     */   }
/*   48:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.IntegerInfo
 * JD-Core Version:    0.7.0.1
 */