/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import org.apache.bcel.util.ByteSequence;
/*  6:   */ 
/*  7:   */ public class LDC_W
/*  8:   */   extends LDC
/*  9:   */ {
/* 10:   */   LDC_W() {}
/* 11:   */   
/* 12:   */   public LDC_W(int index)
/* 13:   */   {
/* 14:75 */     super(index);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void initFromFile(ByteSequence bytes, boolean wide)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:84 */     setIndex(bytes.readUnsignedShort());
/* 21:85 */     this.length = 3;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LDC_W
 * JD-Core Version:    0.7.0.1
 */