/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xml.utils.FastStringBuffer;
/*   4:    */ import org.apache.xpath.res.XPATHMessages;
/*   5:    */ import org.xml.sax.ContentHandler;
/*   6:    */ import org.xml.sax.SAXException;
/*   7:    */ import org.xml.sax.ext.LexicalHandler;
/*   8:    */ 
/*   9:    */ public class XStringForChars
/*  10:    */   extends XString
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -2235248887220850467L;
/*  13:    */   int m_start;
/*  14:    */   int m_length;
/*  15: 40 */   protected String m_strCache = null;
/*  16:    */   
/*  17:    */   public XStringForChars(char[] val, int start, int length)
/*  18:    */   {
/*  19: 51 */     super(val);
/*  20: 52 */     this.m_start = start;
/*  21: 53 */     this.m_length = length;
/*  22: 54 */     if (null == val) {
/*  23: 55 */       throw new IllegalArgumentException(XPATHMessages.createXPATHMessage("ER_FASTSTRINGBUFFER_CANNOT_BE_NULL", null));
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   private XStringForChars(String val)
/*  28:    */   {
/*  29: 67 */     super(val);
/*  30: 68 */     throw new IllegalArgumentException(XPATHMessages.createXPATHMessage("ER_XSTRINGFORCHARS_CANNOT_TAKE_STRING", null));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public FastStringBuffer fsb()
/*  34:    */   {
/*  35: 79 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_FSB_NOT_SUPPORTED_XSTRINGFORCHARS", null));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void appendToFsb(FastStringBuffer fsb)
/*  39:    */   {
/*  40: 89 */     fsb.append((char[])this.m_obj, this.m_start, this.m_length);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean hasString()
/*  44:    */   {
/*  45:100 */     return null != this.m_strCache;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String str()
/*  49:    */   {
/*  50:111 */     if (null == this.m_strCache) {
/*  51:112 */       this.m_strCache = new String((char[])this.m_obj, this.m_start, this.m_length);
/*  52:    */     }
/*  53:114 */     return this.m_strCache;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object object()
/*  57:    */   {
/*  58:126 */     return str();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void dispatchCharactersEvents(ContentHandler ch)
/*  62:    */     throws SAXException
/*  63:    */   {
/*  64:143 */     ch.characters((char[])this.m_obj, this.m_start, this.m_length);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void dispatchAsComment(LexicalHandler lh)
/*  68:    */     throws SAXException
/*  69:    */   {
/*  70:158 */     lh.comment((char[])this.m_obj, this.m_start, this.m_length);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int length()
/*  74:    */   {
/*  75:169 */     return this.m_length;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public char charAt(int index)
/*  79:    */   {
/*  80:187 */     return ((char[])this.m_obj)[(index + this.m_start)];
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
/*  84:    */   {
/*  85:213 */     System.arraycopy((char[])this.m_obj, this.m_start + srcBegin, dst, dstBegin, srcEnd);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XStringForChars
 * JD-Core Version:    0.7.0.1
 */