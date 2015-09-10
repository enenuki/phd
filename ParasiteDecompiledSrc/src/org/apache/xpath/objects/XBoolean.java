/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   5:    */ 
/*   6:    */ public class XBoolean
/*   7:    */   extends XObject
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = -2964933058866100881L;
/*  10: 36 */   public static final XBoolean S_TRUE = new XBooleanStatic(true);
/*  11: 42 */   public static final XBoolean S_FALSE = new XBooleanStatic(false);
/*  12:    */   private final boolean m_val;
/*  13:    */   
/*  14:    */   public XBoolean(boolean b)
/*  15:    */   {
/*  16: 58 */     this.m_val = b;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public XBoolean(Boolean b)
/*  20:    */   {
/*  21: 71 */     this.m_val = b.booleanValue();
/*  22: 72 */     setObject(b);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getType()
/*  26:    */   {
/*  27: 83 */     return 1;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getTypeString()
/*  31:    */   {
/*  32: 94 */     return "#BOOLEAN";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public double num()
/*  36:    */   {
/*  37:104 */     return this.m_val ? 1.0D : 0.0D;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean bool()
/*  41:    */   {
/*  42:114 */     return this.m_val;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String str()
/*  46:    */   {
/*  47:124 */     return this.m_val ? "true" : "false";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object object()
/*  51:    */   {
/*  52:135 */     if (null == this.m_obj) {
/*  53:136 */       setObject(new Boolean(this.m_val));
/*  54:    */     }
/*  55:137 */     return this.m_obj;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean equals(XObject obj2)
/*  59:    */   {
/*  60:155 */     if (obj2.getType() == 4) {
/*  61:156 */       return obj2.equals(this);
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65:160 */       return this.m_val == obj2.bool();
/*  66:    */     }
/*  67:    */     catch (TransformerException te)
/*  68:    */     {
/*  69:164 */       throw new WrappedRuntimeException(te);
/*  70:    */     }
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XBoolean
 * JD-Core Version:    0.7.0.1
 */