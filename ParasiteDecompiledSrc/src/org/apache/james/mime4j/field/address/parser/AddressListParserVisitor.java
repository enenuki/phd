package org.apache.james.mime4j.field.address.parser;

public abstract interface AddressListParserVisitor
{
  public abstract Object visit(SimpleNode paramSimpleNode, Object paramObject);
  
  public abstract Object visit(ASTaddress_list paramASTaddress_list, Object paramObject);
  
  public abstract Object visit(ASTaddress paramASTaddress, Object paramObject);
  
  public abstract Object visit(ASTmailbox paramASTmailbox, Object paramObject);
  
  public abstract Object visit(ASTname_addr paramASTname_addr, Object paramObject);
  
  public abstract Object visit(ASTgroup_body paramASTgroup_body, Object paramObject);
  
  public abstract Object visit(ASTangle_addr paramASTangle_addr, Object paramObject);
  
  public abstract Object visit(ASTroute paramASTroute, Object paramObject);
  
  public abstract Object visit(ASTphrase paramASTphrase, Object paramObject);
  
  public abstract Object visit(ASTaddr_spec paramASTaddr_spec, Object paramObject);
  
  public abstract Object visit(ASTlocal_part paramASTlocal_part, Object paramObject);
  
  public abstract Object visit(ASTdomain paramASTdomain, Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.AddressListParserVisitor
 * JD-Core Version:    0.7.0.1
 */