/*   1:    */ package org.apache.http.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpRequestInterceptor;
/*  10:    */ import org.apache.http.HttpResponse;
/*  11:    */ import org.apache.http.HttpResponseInterceptor;
/*  12:    */ 
/*  13:    */ public final class BasicHttpProcessor
/*  14:    */   implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable
/*  15:    */ {
/*  16: 54 */   protected final List requestInterceptors = new ArrayList();
/*  17: 55 */   protected final List responseInterceptors = new ArrayList();
/*  18:    */   
/*  19:    */   public void addRequestInterceptor(HttpRequestInterceptor itcp)
/*  20:    */   {
/*  21: 58 */     if (itcp == null) {
/*  22: 59 */       return;
/*  23:    */     }
/*  24: 61 */     this.requestInterceptors.add(itcp);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addRequestInterceptor(HttpRequestInterceptor itcp, int index)
/*  28:    */   {
/*  29: 66 */     if (itcp == null) {
/*  30: 67 */       return;
/*  31:    */     }
/*  32: 69 */     this.requestInterceptors.add(index, itcp);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void addResponseInterceptor(HttpResponseInterceptor itcp, int index)
/*  36:    */   {
/*  37: 74 */     if (itcp == null) {
/*  38: 75 */       return;
/*  39:    */     }
/*  40: 77 */     this.responseInterceptors.add(index, itcp);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void removeRequestInterceptorByClass(Class clazz)
/*  44:    */   {
/*  45: 81 */     Iterator it = this.requestInterceptors.iterator();
/*  46: 82 */     while (it.hasNext())
/*  47:    */     {
/*  48: 83 */       Object request = it.next();
/*  49: 84 */       if (request.getClass().equals(clazz)) {
/*  50: 85 */         it.remove();
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void removeResponseInterceptorByClass(Class clazz)
/*  56:    */   {
/*  57: 91 */     Iterator it = this.responseInterceptors.iterator();
/*  58: 92 */     while (it.hasNext())
/*  59:    */     {
/*  60: 93 */       Object request = it.next();
/*  61: 94 */       if (request.getClass().equals(clazz)) {
/*  62: 95 */         it.remove();
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void addInterceptor(HttpRequestInterceptor interceptor)
/*  68:    */   {
/*  69:101 */     addRequestInterceptor(interceptor);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final void addInterceptor(HttpRequestInterceptor interceptor, int index)
/*  73:    */   {
/*  74:105 */     addRequestInterceptor(interceptor, index);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getRequestInterceptorCount()
/*  78:    */   {
/*  79:109 */     return this.requestInterceptors.size();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public HttpRequestInterceptor getRequestInterceptor(int index)
/*  83:    */   {
/*  84:113 */     if ((index < 0) || (index >= this.requestInterceptors.size())) {
/*  85:114 */       return null;
/*  86:    */     }
/*  87:115 */     return (HttpRequestInterceptor)this.requestInterceptors.get(index);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void clearRequestInterceptors()
/*  91:    */   {
/*  92:119 */     this.requestInterceptors.clear();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addResponseInterceptor(HttpResponseInterceptor itcp)
/*  96:    */   {
/*  97:123 */     if (itcp == null) {
/*  98:124 */       return;
/*  99:    */     }
/* 100:126 */     this.responseInterceptors.add(itcp);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final void addInterceptor(HttpResponseInterceptor interceptor)
/* 104:    */   {
/* 105:130 */     addResponseInterceptor(interceptor);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public final void addInterceptor(HttpResponseInterceptor interceptor, int index)
/* 109:    */   {
/* 110:134 */     addResponseInterceptor(interceptor, index);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getResponseInterceptorCount()
/* 114:    */   {
/* 115:138 */     return this.responseInterceptors.size();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public HttpResponseInterceptor getResponseInterceptor(int index)
/* 119:    */   {
/* 120:142 */     if ((index < 0) || (index >= this.responseInterceptors.size())) {
/* 121:143 */       return null;
/* 122:    */     }
/* 123:144 */     return (HttpResponseInterceptor)this.responseInterceptors.get(index);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void clearResponseInterceptors()
/* 127:    */   {
/* 128:148 */     this.responseInterceptors.clear();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setInterceptors(List list)
/* 132:    */   {
/* 133:169 */     if (list == null) {
/* 134:170 */       throw new IllegalArgumentException("List must not be null.");
/* 135:    */     }
/* 136:172 */     this.requestInterceptors.clear();
/* 137:173 */     this.responseInterceptors.clear();
/* 138:174 */     for (int i = 0; i < list.size(); i++)
/* 139:    */     {
/* 140:175 */       Object obj = list.get(i);
/* 141:176 */       if ((obj instanceof HttpRequestInterceptor)) {
/* 142:177 */         addInterceptor((HttpRequestInterceptor)obj);
/* 143:    */       }
/* 144:179 */       if ((obj instanceof HttpResponseInterceptor)) {
/* 145:180 */         addInterceptor((HttpResponseInterceptor)obj);
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void clearInterceptors()
/* 151:    */   {
/* 152:189 */     clearRequestInterceptors();
/* 153:190 */     clearResponseInterceptors();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void process(HttpRequest request, HttpContext context)
/* 157:    */     throws IOException, HttpException
/* 158:    */   {
/* 159:197 */     for (int i = 0; i < this.requestInterceptors.size(); i++)
/* 160:    */     {
/* 161:198 */       HttpRequestInterceptor interceptor = (HttpRequestInterceptor)this.requestInterceptors.get(i);
/* 162:    */       
/* 163:200 */       interceptor.process(request, context);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void process(HttpResponse response, HttpContext context)
/* 168:    */     throws IOException, HttpException
/* 169:    */   {
/* 170:208 */     for (int i = 0; i < this.responseInterceptors.size(); i++)
/* 171:    */     {
/* 172:209 */       HttpResponseInterceptor interceptor = (HttpResponseInterceptor)this.responseInterceptors.get(i);
/* 173:    */       
/* 174:211 */       interceptor.process(response, context);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected void copyInterceptors(BasicHttpProcessor target)
/* 179:    */   {
/* 180:222 */     target.requestInterceptors.clear();
/* 181:223 */     target.requestInterceptors.addAll(this.requestInterceptors);
/* 182:224 */     target.responseInterceptors.clear();
/* 183:225 */     target.responseInterceptors.addAll(this.responseInterceptors);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BasicHttpProcessor copy()
/* 187:    */   {
/* 188:234 */     BasicHttpProcessor clone = new BasicHttpProcessor();
/* 189:235 */     copyInterceptors(clone);
/* 190:236 */     return clone;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Object clone()
/* 194:    */     throws CloneNotSupportedException
/* 195:    */   {
/* 196:240 */     BasicHttpProcessor clone = (BasicHttpProcessor)super.clone();
/* 197:241 */     copyInterceptors(clone);
/* 198:242 */     return clone;
/* 199:    */   }
/* 200:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.BasicHttpProcessor
 * JD-Core Version:    0.7.0.1
 */