package javassist.compiler;

import javassist.bytecode.Bytecode;
import javassist.compiler.ast.ASTList;

public abstract interface ProceedHandler
{
  public abstract void doit(JvstCodeGen paramJvstCodeGen, Bytecode paramBytecode, ASTList paramASTList)
    throws CompileError;
  
  public abstract void setReturnType(JvstTypeChecker paramJvstTypeChecker, ASTList paramASTList)
    throws CompileError;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ProceedHandler
 * JD-Core Version:    0.7.0.1
 */