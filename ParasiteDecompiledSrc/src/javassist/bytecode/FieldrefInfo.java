/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataInputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ 
/*    6:     */ class FieldrefInfo
/*    7:     */   extends MemberrefInfo
/*    8:     */ {
/*    9:     */   static final int tag = 9;
/*   10:     */   
/*   11:     */   public FieldrefInfo(int cindex, int ntindex)
/*   12:     */   {
/*   13:1340 */     super(cindex, ntindex);
/*   14:     */   }
/*   15:     */   
/*   16:     */   public FieldrefInfo(DataInputStream in)
/*   17:     */     throws IOException
/*   18:     */   {
/*   19:1344 */     super(in);
/*   20:     */   }
/*   21:     */   
/*   22:     */   public int getTag()
/*   23:     */   {
/*   24:1347 */     return 9;
/*   25:     */   }
/*   26:     */   
/*   27:     */   public String getTagName()
/*   28:     */   {
/*   29:1349 */     return "Field";
/*   30:     */   }
/*   31:     */   
/*   32:     */   protected int copy2(ConstPool dest, int cindex, int ntindex)
/*   33:     */   {
/*   34:1352 */     return dest.addFieldrefInfo(cindex, ntindex);
/*   35:     */   }
/*   36:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.FieldrefInfo
 * JD-Core Version:    0.7.0.1
 */