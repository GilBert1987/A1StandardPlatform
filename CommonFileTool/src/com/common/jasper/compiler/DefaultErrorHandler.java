package com.common.jasper.compiler;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.Localizer;

/**
 * Default implementation of ErrorHandler interface.
 *
 * @author Jan Luehe
 */
class DefaultErrorHandler implements ErrorHandler {
    
    /*
     * Processes the given JSP parse error.
     *
     * @param fname Name of the JSP file in which the parse error occurred
     * @param line Parse error line number
     * @param column Parse error column number
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    @Override
    public void jspError(String fname, int line, int column, String errMsg,
            Exception ex) throws JasperException {
        throw new JasperException(fname + " (" +
                Localizer.getMessage("jsp.error.location",
                        Integer.toString(line), Integer.toString(column)) +
                ") " + errMsg, ex);
    }
    
    /*
     * Processes the given JSP parse error.
     *
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    @Override
    public void jspError(String errMsg, Exception ex) throws JasperException {
        throw new JasperException(errMsg, ex);
    }
    
    /*
     * Processes the given javac compilation errors.
     *
     * @param details Array of JavacErrorDetail instances corresponding to the
     * compilation errors
     */
    @Override
    public void javacError(JavacErrorDetail[] details) throws JasperException {
        
        if (details == null) {
            return;
        }
        
        Object[] args = null;
        StringBuilder buf = new StringBuilder();
        
        for (int i=0; i < details.length; i++) {
            if (details[i].getJspBeginLineNumber() >= 0) {
                args = new Object[] {
                        Integer.valueOf(details[i].getJspBeginLineNumber()), 
                        details[i].getJspFileName() };
                buf.append(Constants.NEWLINE);
                buf.append(Constants.NEWLINE);
                buf.append(Localizer.getMessage("jsp.error.single.line.number",
                        args));
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getErrorMessage());
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getJspExtract());
            } else {
                args = new Object[] {
                        Integer.valueOf(details[i].getJavaLineNumber()),
                        details[i].getJavaFileName() };
                buf.append(Constants.NEWLINE);
                buf.append(Constants.NEWLINE);
                buf.append(Localizer.getMessage("jsp.error.java.line.number",
                        args));
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getErrorMessage());
            }
        }
        buf.append(Constants.NEWLINE);
        buf.append(Constants.NEWLINE);
        buf.append("Stacktrace:");
        throw new JasperException(
                Localizer.getMessage("jsp.error.unable.compile") + ": " + buf);
    }
    
    /**
     * Processes the given javac error report and exception.
     *
     * @param errorReport Compilation error report
     * @param exception Compilation exception
     */
    @Override
    public void javacError(String errorReport, Exception exception)
    throws JasperException {
        
        throw new JasperException(
                Localizer.getMessage("jsp.error.unable.compile"), exception);
    }
    
}
