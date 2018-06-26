package com.common.jasper.compiler;

import javax.servlet.ServletContext;
/**
 * Provides {@link ELInterpreter} instances for JSP compilation.
 *
 * The search order is as follows:
 * <ol>
 * <li>ELInterpreter instance or implementation class name provided as a
 *     ServletContext attribute</li>
 * <li>Implementation class named in a ServletContext initialisation parameter
 *     </li>
 * <li>Default implementation</li>
 * </ol>
 */
public class ELInterpreterFactory {

    public static final String EL_INTERPRETER_CLASS_NAME =
            ELInterpreter.class.getName();

    private static final ELInterpreter DEFAULT_INSTANCE =
            new DefaultELInterpreter();


    /**
     * Obtain the correct EL Interpreter for the given web application.
     */
    public static ELInterpreter getELInterpreter(ServletContext context)
            throws Exception {

        ELInterpreter result = null;

        // Search for an implementation
        // 1. ServletContext attribute (set by application or cached by a
        //    previous call to this method).
        Object attribute = context.getAttribute(EL_INTERPRETER_CLASS_NAME);
        if (attribute instanceof ELInterpreter) {
            return (ELInterpreter) attribute;
        } else if (attribute instanceof String) {
            result = createInstance(context, (String) attribute);
        }

        // 2. ServletContext init parameter
        if (result == null) {
            String className =
                    context.getInitParameter(EL_INTERPRETER_CLASS_NAME);
            if (className != null) {
                result = createInstance(context, className);
            }
        }

        // 3. Default
        if (result == null) {
            result = DEFAULT_INSTANCE;
        }

        // Cache the result for next time
        context.setAttribute(EL_INTERPRETER_CLASS_NAME, result);
        return result;
    }


    private static ELInterpreter createInstance(ServletContext context,
            String className) throws Exception {
        return (ELInterpreter) context.getClassLoader().loadClass(
                    className).newInstance();
    }


    private ELInterpreterFactory() {
        // Utility class. Hide default constructor.
    }


    public static class DefaultELInterpreter implements ELInterpreter {

        @Override
        public String interpreterCall(JspCompilationContext context,
                boolean isTagFile, String expression,
                Class<?> expectedType, String fnmapvar, boolean xmlEscape) {
            return JspUtil.interpreterCall(isTagFile, expression, expectedType,
                    fnmapvar, xmlEscape);
        }
    }
}

