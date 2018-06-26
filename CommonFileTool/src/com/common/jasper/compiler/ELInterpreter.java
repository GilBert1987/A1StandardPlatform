package com.common.jasper.compiler;


/**
 * Defines the interface for the expression language interpreter. This allows
 * users to provide custom EL interpreter implementations that can optimise
 * EL processing for an application by , for example, performing code generation
 * for simple expressions.
 */
public interface ELInterpreter {

    /**
     * Returns the string representing the code that will be inserted into the
     * servlet generated for JSP. The default implementation creates a call to
     * {@link org.apache.jasper.runtime.PageContextImpl#proprietaryEvaluate(
     * String, Class, javax.servlet.jsp.PageContext,
     * org.apache.jasper.runtime.ProtectedFunctionMapper, boolean)} but other
     * implementations may produce more optimised code.
     *
     * @param expression a String containing zero or more "${}" expressions
     * @param expectedType the expected type of the interpreted result
     * @param fnmapvar Variable pointing to a function map.
     * @param xmlEscape True if the result should do XML escaping
     * @return a String representing a call to the EL interpreter.
     */
    public String interpreterCall(JspCompilationContext context,
            boolean isTagFile, String expression,
            Class<?> expectedType, String fnmapvar, boolean xmlEscape);
}

