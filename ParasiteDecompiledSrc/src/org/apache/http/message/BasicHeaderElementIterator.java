/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.NoSuchElementException;
/*   4:    */ import org.apache.http.FormattedHeader;
/*   5:    */ import org.apache.http.Header;
/*   6:    */ import org.apache.http.HeaderElement;
/*   7:    */ import org.apache.http.HeaderElementIterator;
/*   8:    */ import org.apache.http.HeaderIterator;
/*   9:    */ import org.apache.http.util.CharArrayBuffer;
/*  10:    */ 
/*  11:    */ public class BasicHeaderElementIterator
/*  12:    */   implements HeaderElementIterator
/*  13:    */ {
/*  14:    */   private final HeaderIterator headerIt;
/*  15:    */   private final HeaderValueParser parser;
/*  16: 49 */   private HeaderElement currentElement = null;
/*  17: 50 */   private CharArrayBuffer buffer = null;
/*  18: 51 */   private ParserCursor cursor = null;
/*  19:    */   
/*  20:    */   public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser parser)
/*  21:    */   {
/*  22: 59 */     if (headerIterator == null) {
/*  23: 60 */       throw new IllegalArgumentException("Header iterator may not be null");
/*  24:    */     }
/*  25: 62 */     if (parser == null) {
/*  26: 63 */       throw new IllegalArgumentException("Parser may not be null");
/*  27:    */     }
/*  28: 65 */     this.headerIt = headerIterator;
/*  29: 66 */     this.parser = parser;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BasicHeaderElementIterator(HeaderIterator headerIterator)
/*  33:    */   {
/*  34: 71 */     this(headerIterator, BasicHeaderValueParser.DEFAULT);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void bufferHeaderValue()
/*  38:    */   {
/*  39: 76 */     this.cursor = null;
/*  40: 77 */     this.buffer = null;
/*  41: 78 */     while (this.headerIt.hasNext())
/*  42:    */     {
/*  43: 79 */       Header h = this.headerIt.nextHeader();
/*  44: 80 */       if ((h instanceof FormattedHeader))
/*  45:    */       {
/*  46: 81 */         this.buffer = ((FormattedHeader)h).getBuffer();
/*  47: 82 */         this.cursor = new ParserCursor(0, this.buffer.length());
/*  48: 83 */         this.cursor.updatePos(((FormattedHeader)h).getValuePos());
/*  49: 84 */         break;
/*  50:    */       }
/*  51: 86 */       String value = h.getValue();
/*  52: 87 */       if (value != null)
/*  53:    */       {
/*  54: 88 */         this.buffer = new CharArrayBuffer(value.length());
/*  55: 89 */         this.buffer.append(value);
/*  56: 90 */         this.cursor = new ParserCursor(0, this.buffer.length());
/*  57: 91 */         break;
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void parseNextElement()
/*  63:    */   {
/*  64: 99 */     while ((this.headerIt.hasNext()) || (this.cursor != null))
/*  65:    */     {
/*  66:100 */       if ((this.cursor == null) || (this.cursor.atEnd())) {
/*  67:102 */         bufferHeaderValue();
/*  68:    */       }
/*  69:105 */       if (this.cursor != null)
/*  70:    */       {
/*  71:107 */         while (!this.cursor.atEnd())
/*  72:    */         {
/*  73:108 */           HeaderElement e = this.parser.parseHeaderElement(this.buffer, this.cursor);
/*  74:109 */           if ((e.getName().length() != 0) || (e.getValue() != null))
/*  75:    */           {
/*  76:111 */             this.currentElement = e;
/*  77:112 */             return;
/*  78:    */           }
/*  79:    */         }
/*  80:116 */         if (this.cursor.atEnd())
/*  81:    */         {
/*  82:118 */           this.cursor = null;
/*  83:119 */           this.buffer = null;
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean hasNext()
/*  90:    */   {
/*  91:126 */     if (this.currentElement == null) {
/*  92:127 */       parseNextElement();
/*  93:    */     }
/*  94:129 */     return this.currentElement != null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public HeaderElement nextElement()
/*  98:    */     throws NoSuchElementException
/*  99:    */   {
/* 100:133 */     if (this.currentElement == null) {
/* 101:134 */       parseNextElement();
/* 102:    */     }
/* 103:137 */     if (this.currentElement == null) {
/* 104:138 */       throw new NoSuchElementException("No more header elements available");
/* 105:    */     }
/* 106:141 */     HeaderElement element = this.currentElement;
/* 107:142 */     this.currentElement = null;
/* 108:143 */     return element;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public final Object next()
/* 112:    */     throws NoSuchElementException
/* 113:    */   {
/* 114:147 */     return nextElement();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void remove()
/* 118:    */     throws UnsupportedOperationException
/* 119:    */   {
/* 120:151 */     throw new UnsupportedOperationException("Remove not supported");
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeaderElementIterator
 * JD-Core Version:    0.7.0.1
 */