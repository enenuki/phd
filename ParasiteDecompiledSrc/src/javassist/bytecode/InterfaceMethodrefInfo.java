/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ 
/*    6:     */ class InterfaceMethodrefInfo
/*    7:     */   extends MemberrefInfo
/*    8:     */ {
/*    9:     */   static final int tag = 11;
/*   10:     */   
/*   11:     */   public InterfaceMethodrefInfo(int cindex, int ntindex)
/*   12:     */   {
/*   13:1380 */     super(cindex, ntindex);
/*   14:     */   }
/*   15:     */   
/*   16:     */   public InterfaceMethodrefInfo(DataInputStream in)
/*   17:     */     throws IOException
/*   18:     */   {
/*   19:1384 */     super(in);
/*   20:     */   }
/*   21:     */   
/*   22:     */   public int getTag()
/*   23:     */   {
/*   24:1387 */     return 11;
/*   25:     */   }
/*   26:     */   
/*   27:     */   public String getTagName()
/*   28:     */   {
/*   29:1389 */     return "Interface";
/*   30:     */   }
/*   31:     */   
/*   32:     */   protected int copy2(ConstPool dest, int cindex, int ntindex)
/*   33:     */   {
/*   34:1392 */     return dest.addInterfaceMethodrefInfo(cindex, ntindex);
/*   35:     */   }
/*   36:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.InterfaceMethodrefInfo
 * JD-Core Version:    0.7.0.1
 */