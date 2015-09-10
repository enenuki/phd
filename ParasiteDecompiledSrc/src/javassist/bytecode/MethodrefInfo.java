/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ 
/*    6:     */ class MethodrefInfo
/*    7:     */   extends MemberrefInfo
/*    8:     */ {
/*    9:     */   static final int tag = 10;
/*   10:     */   
/*   11:     */   public MethodrefInfo(int cindex, int ntindex)
/*   12:     */   {
/*   13:1360 */     super(cindex, ntindex);
/*   14:     */   }
/*   15:     */   
/*   16:     */   public MethodrefInfo(DataInputStream in)
/*   17:     */     throws IOException
/*   18:     */   {
/*   19:1364 */     super(in);
/*   20:     */   }
/*   21:     */   
/*   22:     */   public int getTag()
/*   23:     */   {
/*   24:1367 */     return 10;
/*   25:     */   }
/*   26:     */   
/*   27:     */   public String getTagName()
/*   28:     */   {
/*   29:1369 */     return "Method";
/*   30:     */   }
/*   31:     */   
/*   32:     */   protected int copy2(ConstPool dest, int cindex, int ntindex)
/*   33:     */   {
/*   34:1372 */     return dest.addMethodrefInfo(cindex, ntindex);
/*   35:     */   }
/*   36:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.MethodrefInfo
 * JD-Core Version:    0.7.0.1
 */