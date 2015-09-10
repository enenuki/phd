/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.ObjectInputStream;
/*   8:    */ import java.io.ObjectOutputStream;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.io.Serializable;
/*  11:    */ 
/*  12:    */ public class SerializationUtils
/*  13:    */ {
/*  14:    */   public static Object clone(Serializable object)
/*  15:    */   {
/*  16: 81 */     return deserialize(serialize(object));
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void serialize(Serializable obj, OutputStream outputStream)
/*  20:    */   {
/*  21:102 */     if (outputStream == null) {
/*  22:103 */       throw new IllegalArgumentException("The OutputStream must not be null");
/*  23:    */     }
/*  24:105 */     ObjectOutputStream out = null;
/*  25:    */     try
/*  26:    */     {
/*  27:108 */       out = new ObjectOutputStream(outputStream);
/*  28:109 */       out.writeObject(obj); return;
/*  29:    */     }
/*  30:    */     catch (IOException ex)
/*  31:    */     {
/*  32:112 */       throw new SerializationException(ex);
/*  33:    */     }
/*  34:    */     finally
/*  35:    */     {
/*  36:    */       try
/*  37:    */       {
/*  38:115 */         if (out != null) {
/*  39:116 */           out.close();
/*  40:    */         }
/*  41:    */       }
/*  42:    */       catch (IOException ex) {}
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static byte[] serialize(Serializable obj)
/*  47:    */   {
/*  48:133 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
/*  49:134 */     serialize(obj, baos);
/*  50:135 */     return baos.toByteArray();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Object deserialize(InputStream inputStream)
/*  54:    */   {
/*  55:156 */     if (inputStream == null) {
/*  56:157 */       throw new IllegalArgumentException("The InputStream must not be null");
/*  57:    */     }
/*  58:159 */     ObjectInputStream in = null;
/*  59:    */     try
/*  60:    */     {
/*  61:162 */       in = new ObjectInputStream(inputStream);
/*  62:163 */       return in.readObject();
/*  63:    */     }
/*  64:    */     catch (ClassNotFoundException ex)
/*  65:    */     {
/*  66:166 */       throw new SerializationException(ex);
/*  67:    */     }
/*  68:    */     catch (IOException ex)
/*  69:    */     {
/*  70:168 */       throw new SerializationException(ex);
/*  71:    */     }
/*  72:    */     finally
/*  73:    */     {
/*  74:    */       try
/*  75:    */       {
/*  76:171 */         if (in != null) {
/*  77:172 */           in.close();
/*  78:    */         }
/*  79:    */       }
/*  80:    */       catch (IOException ex) {}
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static Object deserialize(byte[] objectData)
/*  85:    */   {
/*  86:189 */     if (objectData == null) {
/*  87:190 */       throw new IllegalArgumentException("The byte[] must not be null");
/*  88:    */     }
/*  89:192 */     ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
/*  90:193 */     return deserialize(bais);
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.SerializationUtils
 * JD-Core Version:    0.7.0.1
 */