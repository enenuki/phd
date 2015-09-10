/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Map;
/*   7:    */ import javassist.bytecode.annotation.Annotation;
/*   8:    */ import javassist.bytecode.annotation.AnnotationsWriter;
/*   9:    */ 
/*  10:    */ public class ParameterAnnotationsAttribute
/*  11:    */   extends AttributeInfo
/*  12:    */ {
/*  13:    */   public static final String visibleTag = "RuntimeVisibleParameterAnnotations";
/*  14:    */   public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";
/*  15:    */   
/*  16:    */   public ParameterAnnotationsAttribute(ConstPool cp, String attrname, byte[] info)
/*  17:    */   {
/*  18: 67 */     super(cp, attrname, info);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ParameterAnnotationsAttribute(ConstPool cp, String attrname)
/*  22:    */   {
/*  23: 82 */     this(cp, attrname, new byte[] { 0 });
/*  24:    */   }
/*  25:    */   
/*  26:    */   ParameterAnnotationsAttribute(ConstPool cp, int n, DataInputStream in)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 91 */     super(cp, n, in);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int numParameters()
/*  33:    */   {
/*  34: 98 */     return this.info[0] & 0xFF;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  38:    */   {
/*  39:105 */     AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);
/*  40:    */     try
/*  41:    */     {
/*  42:107 */       copier.parameters();
/*  43:108 */       return new ParameterAnnotationsAttribute(newCp, getName(), copier.close());
/*  44:    */     }
/*  45:    */     catch (Exception e)
/*  46:    */     {
/*  47:112 */       throw new RuntimeException(e.toString());
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Annotation[][] getAnnotations()
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55:130 */       return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
/*  56:    */     }
/*  57:    */     catch (Exception e)
/*  58:    */     {
/*  59:133 */       throw new RuntimeException(e.toString());
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setAnnotations(Annotation[][] params)
/*  64:    */   {
/*  65:147 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  66:148 */     AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);
/*  67:    */     try
/*  68:    */     {
/*  69:150 */       int n = params.length;
/*  70:151 */       writer.numParameters(n);
/*  71:152 */       for (int i = 0; i < n; i++)
/*  72:    */       {
/*  73:153 */         Annotation[] anno = params[i];
/*  74:154 */         writer.numAnnotations(anno.length);
/*  75:155 */         for (int j = 0; j < anno.length; j++) {
/*  76:156 */           anno[j].write(writer);
/*  77:    */         }
/*  78:    */       }
/*  79:159 */       writer.close();
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:162 */       throw new RuntimeException(e);
/*  84:    */     }
/*  85:165 */     set(output.toByteArray());
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ParameterAnnotationsAttribute
 * JD-Core Version:    0.7.0.1
 */