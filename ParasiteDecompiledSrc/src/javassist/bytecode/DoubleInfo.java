/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class DoubleInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 6;
/*   13:     */   double value;
/*   14:     */   
/*   15:     */   public DoubleInfo(double d)
/*   16:     */   {
/*   17:1517 */     this.value = d;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public DoubleInfo(DataInputStream in)
/*   21:     */     throws IOException
/*   22:     */   {
/*   23:1521 */     this.value = in.readDouble();
/*   24:     */   }
/*   25:     */   
/*   26:     */   public int getTag()
/*   27:     */   {
/*   28:1524 */     return 6;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   32:     */   {
/*   33:1527 */     return dest.addDoubleInfo(this.value);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void write(DataOutputStream out)
/*   37:     */     throws IOException
/*   38:     */   {
/*   39:1531 */     out.writeByte(6);
/*   40:1532 */     out.writeDouble(this.value);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public void print(PrintWriter out)
/*   44:     */   {
/*   45:1536 */     out.print("Double ");
/*   46:1537 */     out.println(this.value);
/*   47:     */   }
/*   48:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.DoubleInfo
 * JD-Core Version:    0.7.0.1
 */