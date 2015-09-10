/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.SACParserCSS2;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import org.w3c.css.sac.CSSParseException;
/*  10:    */ import org.w3c.css.sac.InputSource;
/*  11:    */ import org.w3c.css.sac.SACMediaList;
/*  12:    */ import org.w3c.dom.DOMException;
/*  13:    */ import org.w3c.dom.stylesheets.MediaList;
/*  14:    */ 
/*  15:    */ public class MediaListImpl
/*  16:    */   extends CSSOMObjectImpl
/*  17:    */   implements MediaList, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 6662784733573034870L;
/*  20: 57 */   private List<String> media = new ArrayList();
/*  21:    */   
/*  22:    */   public void setMedia(List<String> media)
/*  23:    */   {
/*  24: 61 */     this.media = media;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private void setMediaList(SACMediaList mediaList)
/*  28:    */   {
/*  29: 66 */     for (int i = 0; i < mediaList.getLength(); i++) {
/*  30: 68 */       this.media.add(mediaList.item(i));
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public MediaListImpl(SACMediaList mediaList)
/*  35:    */   {
/*  36: 74 */     setMediaList(mediaList);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public MediaListImpl() {}
/*  40:    */   
/*  41:    */   public String getMediaText()
/*  42:    */   {
/*  43: 83 */     StringBuffer sb = new StringBuffer("");
/*  44: 84 */     for (int i = 0; i < this.media.size(); i++)
/*  45:    */     {
/*  46: 85 */       sb.append((String)this.media.get(i));
/*  47: 86 */       if (i < this.media.size() - 1) {
/*  48: 87 */         sb.append(", ");
/*  49:    */       }
/*  50:    */     }
/*  51: 90 */     return sb.toString();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setMediaText(String mediaText)
/*  55:    */     throws DOMException
/*  56:    */   {
/*  57: 94 */     InputSource source = new InputSource(new StringReader(mediaText));
/*  58:    */     try
/*  59:    */     {
/*  60: 98 */       SACMediaList sml = new SACParserCSS2().parseMedia(source);
/*  61: 99 */       setMediaList(sml);
/*  62:    */     }
/*  63:    */     catch (CSSParseException e)
/*  64:    */     {
/*  65:103 */       throw new DOMException((short)12, e.getLocalizedMessage());
/*  66:    */     }
/*  67:    */     catch (IOException e)
/*  68:    */     {
/*  69:108 */       throw new DOMException((short)8, e.getLocalizedMessage());
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getLength()
/*  74:    */   {
/*  75:114 */     return this.media.size();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String item(int index)
/*  79:    */   {
/*  80:118 */     return index < this.media.size() ? (String)this.media.get(index) : null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void deleteMedium(String oldMedium)
/*  84:    */     throws DOMException
/*  85:    */   {
/*  86:122 */     for (int i = 0; i < this.media.size(); i++)
/*  87:    */     {
/*  88:123 */       String str = (String)this.media.get(i);
/*  89:124 */       if (str.equalsIgnoreCase(oldMedium))
/*  90:    */       {
/*  91:125 */         this.media.remove(i);
/*  92:126 */         return;
/*  93:    */       }
/*  94:    */     }
/*  95:129 */     throw new DOMExceptionImpl((short)8, 18);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void appendMedium(String newMedium)
/*  99:    */     throws DOMException
/* 100:    */   {
/* 101:135 */     this.media.add(newMedium);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String toString()
/* 105:    */   {
/* 106:139 */     return getMediaText();
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.MediaListImpl
 * JD-Core Version:    0.7.0.1
 */