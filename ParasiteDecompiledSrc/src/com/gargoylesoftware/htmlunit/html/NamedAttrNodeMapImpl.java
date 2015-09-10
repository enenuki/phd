/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.MapWrapper;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import org.w3c.dom.DOMException;
/*  11:    */ import org.w3c.dom.NamedNodeMap;
/*  12:    */ import org.w3c.dom.Node;
/*  13:    */ 
/*  14:    */ class NamedAttrNodeMapImpl
/*  15:    */   extends MapWrapper<String, DomAttr>
/*  16:    */   implements NamedNodeMap, Serializable
/*  17:    */ {
/*  18:433 */   private final List<String> attrPositions_ = new ArrayList();
/*  19:    */   private final DomElement domNode_;
/*  20:435 */   public static final NamedAttrNodeMapImpl EMPTY_MAP = new NamedAttrNodeMapImpl();
/*  21:    */   private final boolean caseSensitive_;
/*  22:    */   
/*  23:    */   private NamedAttrNodeMapImpl()
/*  24:    */   {
/*  25:439 */     super(new LinkedHashMap());
/*  26:440 */     this.domNode_ = null;
/*  27:441 */     this.caseSensitive_ = true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   NamedAttrNodeMapImpl(DomElement domNode, boolean caseSensitive)
/*  31:    */   {
/*  32:445 */     super(new LinkedHashMap());
/*  33:446 */     if (domNode == null) {
/*  34:447 */       throw new IllegalArgumentException("Provided domNode can't be null.");
/*  35:    */     }
/*  36:449 */     this.domNode_ = domNode;
/*  37:450 */     this.caseSensitive_ = caseSensitive;
/*  38:    */   }
/*  39:    */   
/*  40:    */   NamedAttrNodeMapImpl(DomElement domNode, boolean caseSensitive, Map<String, DomAttr> attributes)
/*  41:    */   {
/*  42:455 */     this(domNode, caseSensitive);
/*  43:456 */     putAll(attributes);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getLength()
/*  47:    */   {
/*  48:463 */     return size();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public DomAttr getNamedItem(String name)
/*  52:    */   {
/*  53:470 */     return get(name);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private String fixName(String name)
/*  57:    */   {
/*  58:474 */     if (this.caseSensitive_) {
/*  59:475 */       return name;
/*  60:    */     }
/*  61:477 */     return name.toLowerCase();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Node getNamedItemNS(String namespaceURI, String localName)
/*  65:    */   {
/*  66:484 */     if (this.domNode_ == null) {
/*  67:485 */       return null;
/*  68:    */     }
/*  69:487 */     return get(this.domNode_.getQualifiedName(namespaceURI, fixName(localName)));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Node item(int index)
/*  73:    */   {
/*  74:494 */     if ((index < 0) || (index >= this.attrPositions_.size())) {
/*  75:495 */       return null;
/*  76:    */     }
/*  77:497 */     return (Node)super.get(this.attrPositions_.get(index));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Node removeNamedItem(String name)
/*  81:    */     throws DOMException
/*  82:    */   {
/*  83:504 */     return remove(name);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Node removeNamedItemNS(String namespaceURI, String localName)
/*  87:    */   {
/*  88:511 */     if (this.domNode_ == null) {
/*  89:512 */       return null;
/*  90:    */     }
/*  91:514 */     return remove(this.domNode_.getQualifiedName(namespaceURI, fixName(localName)));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public DomAttr setNamedItem(Node node)
/*  95:    */   {
/*  96:521 */     return put(node.getLocalName(), (DomAttr)node);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Node setNamedItemNS(Node node)
/* 100:    */     throws DOMException
/* 101:    */   {
/* 102:528 */     return put(node.getNodeName(), (DomAttr)node);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public DomAttr put(String key, DomAttr value)
/* 106:    */   {
/* 107:536 */     String name = fixName(key);
/* 108:537 */     if (!containsKey(name)) {
/* 109:538 */       this.attrPositions_.add(name);
/* 110:    */     }
/* 111:540 */     return (DomAttr)super.put(name, value);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public DomAttr remove(Object key)
/* 115:    */   {
/* 116:548 */     if (!(key instanceof String)) {
/* 117:549 */       return null;
/* 118:    */     }
/* 119:551 */     String name = fixName((String)key);
/* 120:552 */     this.attrPositions_.remove(name);
/* 121:553 */     return (DomAttr)super.remove(name);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void clear()
/* 125:    */   {
/* 126:561 */     this.attrPositions_.clear();
/* 127:562 */     super.clear();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void putAll(Map<? extends String, ? extends DomAttr> t)
/* 131:    */   {
/* 132:571 */     for (Map.Entry<? extends String, ? extends DomAttr> entry : t.entrySet()) {
/* 133:572 */       put((String)entry.getKey(), (DomAttr)entry.getValue());
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean containsKey(Object key)
/* 138:    */   {
/* 139:581 */     if (!(key instanceof String)) {
/* 140:582 */       return false;
/* 141:    */     }
/* 142:584 */     String name = fixName((String)key);
/* 143:585 */     return super.containsKey(name);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DomAttr get(Object key)
/* 147:    */   {
/* 148:593 */     if (!(key instanceof String)) {
/* 149:594 */       return null;
/* 150:    */     }
/* 151:596 */     String name = fixName((String)key);
/* 152:597 */     return (DomAttr)super.get(name);
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.NamedAttrNodeMapImpl
 * JD-Core Version:    0.7.0.1
 */