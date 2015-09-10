/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.DataOutputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.PrintWriter;
/*    6:     */ import java.util.Map;
/*    7:     */ 
/*    8:     */ class ConstInfoPadding
/*    9:     */   extends ConstInfo
/*   10:     */ {
/*   11:     */   public int getTag()
/*   12:     */   {
/*   13:1155 */     return 0;
/*   14:     */   }
/*   15:     */   
/*   16:     */   public int copy(ConstPool src, ConstPool dest, Map map)
/*   17:     */   {
/*   18:1158 */     return dest.addConstInfoPadding();
/*   19:     */   }
/*   20:     */   
/*   21:     */   public void write(DataOutputStream out)
/*   22:     */     throws IOException
/*   23:     */   {}
/*   24:     */   
/*   25:     */   public void print(PrintWriter out)
/*   26:     */   {
/*   27:1164 */     out.println("padding");
/*   28:     */   }
/*   29:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ConstInfoPadding
 * JD-Core Version:    0.7.0.1
 */