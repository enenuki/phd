/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import jxl.biff.IntegerHelper;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ class Sp
/*   7:    */   extends EscherAtom
/*   8:    */ {
/*   9: 34 */   private static Logger logger = Logger.getLogger(Sp.class);
/*  10:    */   private byte[] data;
/*  11:    */   private int shapeType;
/*  12:    */   private int shapeId;
/*  13:    */   private int persistenceFlags;
/*  14:    */   
/*  15:    */   public Sp(EscherRecordData erd)
/*  16:    */   {
/*  17: 63 */     super(erd);
/*  18: 64 */     this.shapeType = getInstance();
/*  19: 65 */     byte[] bytes = getBytes();
/*  20: 66 */     this.shapeId = IntegerHelper.getInt(bytes[0], bytes[1], bytes[2], bytes[3]);
/*  21: 67 */     this.persistenceFlags = IntegerHelper.getInt(bytes[4], bytes[5], bytes[6], bytes[7]);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Sp(ShapeType st, int sid, int p)
/*  25:    */   {
/*  26: 80 */     super(EscherRecordType.SP);
/*  27: 81 */     setVersion(2);
/*  28: 82 */     this.shapeType = st.getValue();
/*  29: 83 */     this.shapeId = sid;
/*  30: 84 */     this.persistenceFlags = p;
/*  31: 85 */     setInstance(this.shapeType);
/*  32:    */   }
/*  33:    */   
/*  34:    */   int getShapeId()
/*  35:    */   {
/*  36: 95 */     return this.shapeId;
/*  37:    */   }
/*  38:    */   
/*  39:    */   int getShapeType()
/*  40:    */   {
/*  41:105 */     return this.shapeType;
/*  42:    */   }
/*  43:    */   
/*  44:    */   byte[] getData()
/*  45:    */   {
/*  46:115 */     this.data = new byte[8];
/*  47:116 */     IntegerHelper.getFourBytes(this.shapeId, this.data, 0);
/*  48:117 */     IntegerHelper.getFourBytes(this.persistenceFlags, this.data, 4);
/*  49:118 */     return setHeaderData(this.data);
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.Sp
 * JD-Core Version:    0.7.0.1
 */