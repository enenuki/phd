/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.james.mime4j.util.ByteSequence;
/*   7:    */ import org.apache.james.mime4j.util.ContentUtil;
/*   8:    */ 
/*   9:    */ public class Multipart
/*  10:    */   implements Body
/*  11:    */ {
/*  12: 38 */   private List<BodyPart> bodyParts = new LinkedList();
/*  13: 39 */   private Entity parent = null;
/*  14:    */   private ByteSequence preamble;
/*  15:    */   private transient String preambleStrCache;
/*  16:    */   private ByteSequence epilogue;
/*  17:    */   private transient String epilogueStrCache;
/*  18:    */   private String subType;
/*  19:    */   
/*  20:    */   public Multipart(String subType)
/*  21:    */   {
/*  22: 52 */     this.preamble = ByteSequence.EMPTY;
/*  23: 53 */     this.preambleStrCache = "";
/*  24: 54 */     this.epilogue = ByteSequence.EMPTY;
/*  25: 55 */     this.epilogueStrCache = "";
/*  26:    */     
/*  27: 57 */     this.subType = subType;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Multipart(Multipart other)
/*  31:    */   {
/*  32: 79 */     this.preamble = other.preamble;
/*  33: 80 */     this.preambleStrCache = other.preambleStrCache;
/*  34: 81 */     this.epilogue = other.epilogue;
/*  35: 82 */     this.epilogueStrCache = other.epilogueStrCache;
/*  36: 84 */     for (BodyPart otherBodyPart : other.bodyParts)
/*  37:    */     {
/*  38: 85 */       BodyPart bodyPartCopy = new BodyPart(otherBodyPart);
/*  39: 86 */       addBodyPart(bodyPartCopy);
/*  40:    */     }
/*  41: 89 */     this.subType = other.subType;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getSubType()
/*  45:    */   {
/*  46:100 */     return this.subType;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setSubType(String subType)
/*  50:    */   {
/*  51:112 */     this.subType = subType;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Entity getParent()
/*  55:    */   {
/*  56:119 */     return this.parent;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setParent(Entity parent)
/*  60:    */   {
/*  61:126 */     this.parent = parent;
/*  62:127 */     for (BodyPart bodyPart : this.bodyParts) {
/*  63:128 */       bodyPart.setParent(parent);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getCount()
/*  68:    */   {
/*  69:138 */     return this.bodyParts.size();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<BodyPart> getBodyParts()
/*  73:    */   {
/*  74:147 */     return Collections.unmodifiableList(this.bodyParts);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setBodyParts(List<BodyPart> bodyParts)
/*  78:    */   {
/*  79:157 */     this.bodyParts = bodyParts;
/*  80:158 */     for (BodyPart bodyPart : bodyParts) {
/*  81:159 */       bodyPart.setParent(this.parent);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void addBodyPart(BodyPart bodyPart)
/*  86:    */   {
/*  87:170 */     if (bodyPart == null) {
/*  88:171 */       throw new IllegalArgumentException();
/*  89:    */     }
/*  90:173 */     this.bodyParts.add(bodyPart);
/*  91:174 */     bodyPart.setParent(this.parent);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void addBodyPart(BodyPart bodyPart, int index)
/*  95:    */   {
/*  96:189 */     if (bodyPart == null) {
/*  97:190 */       throw new IllegalArgumentException();
/*  98:    */     }
/*  99:192 */     this.bodyParts.add(index, bodyPart);
/* 100:193 */     bodyPart.setParent(this.parent);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BodyPart removeBodyPart(int index)
/* 104:    */   {
/* 105:208 */     BodyPart bodyPart = (BodyPart)this.bodyParts.remove(index);
/* 106:209 */     bodyPart.setParent(null);
/* 107:210 */     return bodyPart;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public BodyPart replaceBodyPart(BodyPart bodyPart, int index)
/* 111:    */   {
/* 112:227 */     if (bodyPart == null) {
/* 113:228 */       throw new IllegalArgumentException();
/* 114:    */     }
/* 115:230 */     BodyPart replacedBodyPart = (BodyPart)this.bodyParts.set(index, bodyPart);
/* 116:231 */     if (bodyPart == replacedBodyPart) {
/* 117:232 */       throw new IllegalArgumentException("Cannot replace body part with itself");
/* 118:    */     }
/* 119:235 */     bodyPart.setParent(this.parent);
/* 120:236 */     replacedBodyPart.setParent(null);
/* 121:    */     
/* 122:238 */     return replacedBodyPart;
/* 123:    */   }
/* 124:    */   
/* 125:    */   ByteSequence getPreambleRaw()
/* 126:    */   {
/* 127:243 */     return this.preamble;
/* 128:    */   }
/* 129:    */   
/* 130:    */   void setPreambleRaw(ByteSequence preamble)
/* 131:    */   {
/* 132:247 */     this.preamble = preamble;
/* 133:248 */     this.preambleStrCache = null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getPreamble()
/* 137:    */   {
/* 138:257 */     if (this.preambleStrCache == null) {
/* 139:258 */       this.preambleStrCache = ContentUtil.decode(this.preamble);
/* 140:    */     }
/* 141:260 */     return this.preambleStrCache;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPreamble(String preamble)
/* 145:    */   {
/* 146:270 */     this.preamble = ContentUtil.encode(preamble);
/* 147:271 */     this.preambleStrCache = preamble;
/* 148:    */   }
/* 149:    */   
/* 150:    */   ByteSequence getEpilogueRaw()
/* 151:    */   {
/* 152:276 */     return this.epilogue;
/* 153:    */   }
/* 154:    */   
/* 155:    */   void setEpilogueRaw(ByteSequence epilogue)
/* 156:    */   {
/* 157:280 */     this.epilogue = epilogue;
/* 158:281 */     this.epilogueStrCache = null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getEpilogue()
/* 162:    */   {
/* 163:290 */     if (this.epilogueStrCache == null) {
/* 164:291 */       this.epilogueStrCache = ContentUtil.decode(this.epilogue);
/* 165:    */     }
/* 166:293 */     return this.epilogueStrCache;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setEpilogue(String epilogue)
/* 170:    */   {
/* 171:303 */     this.epilogue = ContentUtil.encode(epilogue);
/* 172:304 */     this.epilogueStrCache = epilogue;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void dispose()
/* 176:    */   {
/* 177:314 */     for (BodyPart bodyPart : this.bodyParts) {
/* 178:315 */       bodyPart.dispose();
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.Multipart
 * JD-Core Version:    0.7.0.1
 */