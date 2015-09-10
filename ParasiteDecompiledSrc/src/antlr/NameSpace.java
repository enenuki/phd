/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.util.Enumeration;
/*  5:   */ import java.util.StringTokenizer;
/*  6:   */ import java.util.Vector;
/*  7:   */ 
/*  8:   */ public class NameSpace
/*  9:   */ {
/* 10:22 */   private Vector names = new Vector();
/* 11:   */   private String _name;
/* 12:   */   
/* 13:   */   public NameSpace(String paramString)
/* 14:   */   {
/* 15:26 */     this._name = new String(paramString);
/* 16:27 */     parse(paramString);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:32 */     return this._name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void parse(String paramString)
/* 25:   */   {
/* 26:42 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "::");
/* 27:43 */     while (localStringTokenizer.hasMoreTokens()) {
/* 28:44 */       this.names.addElement(localStringTokenizer.nextToken());
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   void emitDeclarations(PrintWriter paramPrintWriter)
/* 33:   */   {
/* 34:51 */     for (Enumeration localEnumeration = this.names.elements(); localEnumeration.hasMoreElements();)
/* 35:   */     {
/* 36:52 */       String str = (String)localEnumeration.nextElement();
/* 37:53 */       paramPrintWriter.println("ANTLR_BEGIN_NAMESPACE(" + str + ")");
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   void emitClosures(PrintWriter paramPrintWriter)
/* 42:   */   {
/* 43:61 */     for (int i = 0; i < this.names.size(); i++) {
/* 44:62 */       paramPrintWriter.println("ANTLR_END_NAMESPACE");
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.NameSpace
 * JD-Core Version:    0.7.0.1
 */