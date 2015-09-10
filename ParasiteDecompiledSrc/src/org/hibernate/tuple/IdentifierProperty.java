/*   1:    */ package org.hibernate.tuple;
/*   2:    */ 
/*   3:    */ import org.hibernate.engine.spi.IdentifierValue;
/*   4:    */ import org.hibernate.id.IdentifierGenerator;
/*   5:    */ import org.hibernate.id.PostInsertIdentifierGenerator;
/*   6:    */ import org.hibernate.type.Type;
/*   7:    */ 
/*   8:    */ public class IdentifierProperty
/*   9:    */   extends Property
/*  10:    */ {
/*  11:    */   private boolean virtual;
/*  12:    */   private boolean embedded;
/*  13:    */   private IdentifierValue unsavedValue;
/*  14:    */   private IdentifierGenerator identifierGenerator;
/*  15:    */   private boolean identifierAssignedByInsert;
/*  16:    */   private boolean hasIdentifierMapper;
/*  17:    */   
/*  18:    */   public IdentifierProperty(String name, String node, Type type, boolean embedded, IdentifierValue unsavedValue, IdentifierGenerator identifierGenerator)
/*  19:    */   {
/*  20: 66 */     super(name, node, type);
/*  21: 67 */     this.virtual = false;
/*  22: 68 */     this.embedded = embedded;
/*  23: 69 */     this.hasIdentifierMapper = false;
/*  24: 70 */     this.unsavedValue = unsavedValue;
/*  25: 71 */     this.identifierGenerator = identifierGenerator;
/*  26: 72 */     this.identifierAssignedByInsert = (identifierGenerator instanceof PostInsertIdentifierGenerator);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public IdentifierProperty(Type type, boolean embedded, boolean hasIdentifierMapper, IdentifierValue unsavedValue, IdentifierGenerator identifierGenerator)
/*  30:    */   {
/*  31: 90 */     super(null, null, type);
/*  32: 91 */     this.virtual = true;
/*  33: 92 */     this.embedded = embedded;
/*  34: 93 */     this.hasIdentifierMapper = hasIdentifierMapper;
/*  35: 94 */     this.unsavedValue = unsavedValue;
/*  36: 95 */     this.identifierGenerator = identifierGenerator;
/*  37: 96 */     this.identifierAssignedByInsert = (identifierGenerator instanceof PostInsertIdentifierGenerator);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isVirtual()
/*  41:    */   {
/*  42:100 */     return this.virtual;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isEmbedded()
/*  46:    */   {
/*  47:104 */     return this.embedded;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public IdentifierValue getUnsavedValue()
/*  51:    */   {
/*  52:108 */     return this.unsavedValue;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public IdentifierGenerator getIdentifierGenerator()
/*  56:    */   {
/*  57:112 */     return this.identifierGenerator;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isIdentifierAssignedByInsert()
/*  61:    */   {
/*  62:116 */     return this.identifierAssignedByInsert;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean hasIdentifierMapper()
/*  66:    */   {
/*  67:120 */     return this.hasIdentifierMapper;
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.IdentifierProperty
 * JD-Core Version:    0.7.0.1
 */