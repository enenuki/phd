/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayOutputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintWriter;
/*    7:     */ import java.util.Map;
/*    8:     */ 
/*    9:     */ abstract class ConstInfo
/*   10:     */ {
/*   11:     */   public abstract int getTag();
/*   12:     */   
/*   13:     */   public String getClassName(ConstPool cp)
/*   14:     */   {
/*   15:1131 */     return null;
/*   16:     */   }
/*   17:     */   
/*   18:     */   public void renameClass(ConstPool cp, String oldName, String newName) {}
/*   19:     */   
/*   20:     */   public void renameClass(ConstPool cp, Map classnames) {}
/*   21:     */   
/*   22:     */   public abstract int copy(ConstPool paramConstPool1, ConstPool paramConstPool2, Map paramMap);
/*   23:     */   
/*   24:     */   public abstract void write(DataOutputStream paramDataOutputStream)
/*   25:     */     throws IOException;
/*   26:     */   
/*   27:     */   public abstract void print(PrintWriter paramPrintWriter);
/*   28:     */   
/*   29:     */   void makeHashtable(ConstPool cp) {}
/*   30:     */   
/*   31:     */   boolean hashCheck(int a, int b)
/*   32:     */   {
/*   33:1142 */     return false;
/*   34:     */   }
/*   35:     */   
/*   36:     */   public String toString()
/*   37:     */   {
/*   38:1145 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*   39:1146 */     PrintWriter out = new PrintWriter(bout);
/*   40:1147 */     print(out);
/*   41:1148 */     return bout.toString();
/*   42:     */   }
/*   43:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ConstInfo
 * JD-Core Version:    0.7.0.1
 */