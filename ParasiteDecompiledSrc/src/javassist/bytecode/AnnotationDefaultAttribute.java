/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Map;
/*   7:    */ import javassist.bytecode.annotation.AnnotationsWriter;
/*   8:    */ import javassist.bytecode.annotation.MemberValue;
/*   9:    */ 
/*  10:    */ public class AnnotationDefaultAttribute
/*  11:    */   extends AttributeInfo
/*  12:    */ {
/*  13:    */   public static final String tag = "AnnotationDefault";
/*  14:    */   
/*  15:    */   public AnnotationDefaultAttribute(ConstPool cp, byte[] info)
/*  16:    */   {
/*  17: 80 */     super(cp, "AnnotationDefault", info);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public AnnotationDefaultAttribute(ConstPool cp)
/*  21:    */   {
/*  22: 91 */     this(cp, new byte[] { 0, 0 });
/*  23:    */   }
/*  24:    */   
/*  25:    */   AnnotationDefaultAttribute(ConstPool cp, int n, DataInputStream in)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28:100 */     super(cp, n, in);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  32:    */   {
/*  33:107 */     AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);
/*  34:    */     try
/*  35:    */     {
/*  36:110 */       copier.memberValue(0);
/*  37:111 */       return new AnnotationDefaultAttribute(newCp, copier.close());
/*  38:    */     }
/*  39:    */     catch (Exception e)
/*  40:    */     {
/*  41:114 */       throw new RuntimeException(e.toString());
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MemberValue getDefaultValue()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49:124 */       return new AnnotationsAttribute.Parser(this.info, this.constPool).parseMemberValue();
/*  50:    */     }
/*  51:    */     catch (Exception e)
/*  52:    */     {
/*  53:128 */       throw new RuntimeException(e.toString());
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setDefaultValue(MemberValue value)
/*  58:    */   {
/*  59:139 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  60:140 */     AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);
/*  61:    */     try
/*  62:    */     {
/*  63:142 */       value.write(writer);
/*  64:143 */       writer.close();
/*  65:    */     }
/*  66:    */     catch (IOException e)
/*  67:    */     {
/*  68:146 */       throw new RuntimeException(e);
/*  69:    */     }
/*  70:149 */     set(output.toByteArray());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String toString()
/*  74:    */   {
/*  75:157 */     return getDefaultValue().toString();
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.AnnotationDefaultAttribute
 * JD-Core Version:    0.7.0.1
 */