/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.common.Assert;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ public abstract class BaseCompoundFile
/*   7:    */ {
/*   8: 33 */   private static Logger logger = Logger.getLogger(BaseCompoundFile.class);
/*   9: 38 */   protected static final byte[] IDENTIFIER = { -48, -49, 17, -32, -95, -79, 26, -31 };
/*  10:    */   protected static final int NUM_BIG_BLOCK_DEPOT_BLOCKS_POS = 44;
/*  11:    */   protected static final int SMALL_BLOCK_DEPOT_BLOCK_POS = 60;
/*  12:    */   protected static final int NUM_SMALL_BLOCK_DEPOT_BLOCKS_POS = 64;
/*  13:    */   protected static final int ROOT_START_BLOCK_POS = 48;
/*  14:    */   protected static final int BIG_BLOCK_SIZE = 512;
/*  15:    */   protected static final int SMALL_BLOCK_SIZE = 64;
/*  16:    */   protected static final int EXTENSION_BLOCK_POS = 68;
/*  17:    */   protected static final int NUM_EXTENSION_BLOCK_POS = 72;
/*  18:    */   protected static final int PROPERTY_STORAGE_BLOCK_SIZE = 128;
/*  19:    */   protected static final int BIG_BLOCK_DEPOT_BLOCKS_POS = 76;
/*  20:    */   protected static final int SMALL_BLOCK_THRESHOLD = 4096;
/*  21:    */   private static final int SIZE_OF_NAME_POS = 64;
/*  22:    */   private static final int TYPE_POS = 66;
/*  23:    */   private static final int COLOUR_POS = 67;
/*  24:    */   private static final int PREVIOUS_POS = 68;
/*  25:    */   private static final int NEXT_POS = 72;
/*  26:    */   private static final int CHILD_POS = 76;
/*  27:    */   private static final int START_BLOCK_POS = 116;
/*  28:    */   private static final int SIZE_POS = 120;
/*  29:    */   public static final String ROOT_ENTRY_NAME = "Root Entry";
/*  30:    */   public static final String WORKBOOK_NAME = "Workbook";
/*  31:    */   public static final String SUMMARY_INFORMATION_NAME = "\005SummaryInformation";
/*  32:    */   public static final String DOCUMENT_SUMMARY_INFORMATION_NAME = "\005DocumentSummaryInformation";
/*  33:    */   public static final String COMP_OBJ_NAME = "\001CompObj";
/*  34:118 */   public static final String[] STANDARD_PROPERTY_SETS = { "Root Entry", "Workbook", "\005SummaryInformation", "\005DocumentSummaryInformation" };
/*  35:    */   public static final int NONE_PS_TYPE = 0;
/*  36:    */   public static final int DIRECTORY_PS_TYPE = 1;
/*  37:    */   public static final int FILE_PS_TYPE = 2;
/*  38:    */   public static final int ROOT_ENTRY_PS_TYPE = 5;
/*  39:    */   
/*  40:    */   public class PropertyStorage
/*  41:    */   {
/*  42:    */     public String name;
/*  43:    */     public int type;
/*  44:    */     public int colour;
/*  45:    */     public int startBlock;
/*  46:    */     public int size;
/*  47:    */     public int previous;
/*  48:    */     public int next;
/*  49:    */     public int child;
/*  50:    */     public byte[] data;
/*  51:    */     
/*  52:    */     public PropertyStorage(byte[] d)
/*  53:    */     {
/*  54:183 */       this.data = d;
/*  55:184 */       int nameSize = IntegerHelper.getInt(this.data[64], this.data[65]);
/*  56:187 */       if (nameSize > 64)
/*  57:    */       {
/*  58:189 */         BaseCompoundFile.logger.warn("property set name exceeds max length - truncating");
/*  59:190 */         nameSize = 64;
/*  60:    */       }
/*  61:193 */       this.type = this.data[66];
/*  62:194 */       this.colour = this.data[67];
/*  63:    */       
/*  64:196 */       this.startBlock = IntegerHelper.getInt(this.data[116], this.data[117], this.data[118], this.data[119]);
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:201 */       this.size = IntegerHelper.getInt(this.data[120], this.data[121], this.data[122], this.data[123]);
/*  70:    */       
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:206 */       this.previous = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
/*  75:    */       
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:211 */       this.next = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
/*  80:    */       
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:216 */       this.child = IntegerHelper.getInt(this.data[76], this.data[77], this.data[78], this.data[79]);
/*  85:    */       
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:222 */       int chars = 0;
/*  91:223 */       if (nameSize > 2) {
/*  92:225 */         chars = (nameSize - 1) / 2;
/*  93:    */       }
/*  94:228 */       StringBuffer n = new StringBuffer("");
/*  95:229 */       for (int i = 0; i < chars; i++) {
/*  96:231 */         n.append((char)this.data[(i * 2)]);
/*  97:    */       }
/*  98:234 */       this.name = n.toString();
/*  99:    */     }
/* 100:    */     
/* 101:    */     public PropertyStorage(String name)
/* 102:    */     {
/* 103:244 */       this.data = new byte[''];
/* 104:    */       
/* 105:246 */       Assert.verify(name.length() < 32);
/* 106:    */       
/* 107:248 */       IntegerHelper.getTwoBytes((name.length() + 1) * 2, this.data, 64);
/* 108:253 */       for (int i = 0; i < name.length(); i++) {
/* 109:255 */         this.data[(i * 2)] = ((byte)name.charAt(i));
/* 110:    */       }
/* 111:    */     }
/* 112:    */     
/* 113:    */     public void setType(int t)
/* 114:    */     {
/* 115:266 */       this.type = t;
/* 116:267 */       this.data[66] = ((byte)t);
/* 117:    */     }
/* 118:    */     
/* 119:    */     public void setStartBlock(int sb)
/* 120:    */     {
/* 121:277 */       this.startBlock = sb;
/* 122:278 */       IntegerHelper.getFourBytes(sb, this.data, 116);
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void setSize(int s)
/* 126:    */     {
/* 127:288 */       this.size = s;
/* 128:289 */       IntegerHelper.getFourBytes(s, this.data, 120);
/* 129:    */     }
/* 130:    */     
/* 131:    */     public void setPrevious(int prev)
/* 132:    */     {
/* 133:299 */       this.previous = prev;
/* 134:300 */       IntegerHelper.getFourBytes(prev, this.data, 68);
/* 135:    */     }
/* 136:    */     
/* 137:    */     public void setNext(int nxt)
/* 138:    */     {
/* 139:310 */       this.next = nxt;
/* 140:311 */       IntegerHelper.getFourBytes(this.next, this.data, 72);
/* 141:    */     }
/* 142:    */     
/* 143:    */     public void setChild(int dir)
/* 144:    */     {
/* 145:321 */       this.child = dir;
/* 146:322 */       IntegerHelper.getFourBytes(this.child, this.data, 76);
/* 147:    */     }
/* 148:    */     
/* 149:    */     public void setColour(int col)
/* 150:    */     {
/* 151:332 */       this.colour = (col == 0 ? 0 : 1);
/* 152:333 */       this.data[67] = ((byte)this.colour);
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.BaseCompoundFile
 * JD-Core Version:    0.7.0.1
 */