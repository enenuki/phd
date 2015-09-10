/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ class FloatInfo
/*   10:     */   extends ConstInfo
/*   11:     */ {
/*   12:     */   static final int tag = 4;
/*   13:     */   float value;
/*   14:     */   
/*   15:     */   public FloatInfo(float f)
/*   16:     */   {
/*   17:1459 */     this.value = f;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public FloatInfo(DataInputStream in)
/*   21:     */     throws IOException
/*   22:     */   {
/*   23:1463 */     this.value = in.readFloat();
/*   24:     */   }
/*   25:     */   
/*   26:     */   public int getTag()
/*   27:     */   {
/*   28:1466 */     return 4;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   32:     */   {
/*   33:1469 */     return dest.addFloatInfo(this.value);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void write(DataOutputStream out)
/*   37:     */     throws IOException
/*   38:     */   {
/*   39:1473 */     out.writeByte(4);
/*   40:1474 */     out.writeFloat(this.value);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public void print(PrintWriter out)
/*   44:     */   {
/*   45:1478 */     out.print("Float ");
/*   46:1479 */     out.println(this.value);
/*   47:     */   }
/*   48:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.FloatInfo
 * JD-Core Version:    0.7.0.1
 */