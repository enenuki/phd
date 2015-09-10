/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class LongInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 5;
/*   13:     */   long value;
/*   14:     */   
/*   15:     */   public LongInfo(long l)
/*   16:     */   {
/*   17:1488 */     this.value = l;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public LongInfo(DataInputStream in)
/*   21:     */     throws IOException
/*   22:     */   {
/*   23:1492 */     this.value = in.readLong();
/*   24:     */   }
/*   25:     */   
/*   26:     */   public int getTag()
/*   27:     */   {
/*   28:1495 */     return 5;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   32:     */   {
/*   33:1498 */     return dest.addLongInfo(this.value);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void write(DataOutputStream out)
/*   37:     */     throws IOException
/*   38:     */   {
/*   39:1502 */     out.writeByte(5);
/*   40:1503 */     out.writeLong(this.value);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public void print(PrintWriter out)
/*   44:     */   {
/*   45:1507 */     out.print("Long ");
/*   46:1508 */     out.println(this.value);
/*   47:     */   }
/*   48:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.LongInfo
 * JD-Core Version:    0.7.0.1
 */