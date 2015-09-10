/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.SourceLocator;
/*   4:    */ 
/*   5:    */ public class NodeLocator
/*   6:    */   implements SourceLocator
/*   7:    */ {
/*   8:    */   protected String m_publicId;
/*   9:    */   protected String m_systemId;
/*  10:    */   protected int m_lineNumber;
/*  11:    */   protected int m_columnNumber;
/*  12:    */   
/*  13:    */   public NodeLocator(String publicId, String systemId, int lineNumber, int columnNumber)
/*  14:    */   {
/*  15: 51 */     this.m_publicId = publicId;
/*  16: 52 */     this.m_systemId = systemId;
/*  17: 53 */     this.m_lineNumber = lineNumber;
/*  18: 54 */     this.m_columnNumber = columnNumber;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getPublicId()
/*  22:    */   {
/*  23: 64 */     return this.m_publicId;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getSystemId()
/*  27:    */   {
/*  28: 74 */     return this.m_systemId;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getLineNumber()
/*  32:    */   {
/*  33: 84 */     return this.m_lineNumber;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getColumnNumber()
/*  37:    */   {
/*  38: 95 */     return this.m_columnNumber;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String toString()
/*  42:    */   {
/*  43:106 */     return "file '" + this.m_systemId + "', line #" + this.m_lineNumber + ", column #" + this.m_columnNumber;
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.NodeLocator
 * JD-Core Version:    0.7.0.1
 */