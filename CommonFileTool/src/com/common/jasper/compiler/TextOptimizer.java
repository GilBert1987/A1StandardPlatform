package com.common.jasper.compiler;
import org.apache.jasper.JasperException;
import com.common.jasper.Options;

/**
 */
public class TextOptimizer {

    /**
     * A visitor to concatenate contiguous template texts.
     */
    static class TextCatVisitor extends Node.Visitor {

        private static final String EMPTY_TEXT = "";

        private Options options;
        private PageInfo pageInfo;
        private int textNodeCount = 0;
        private Node.TemplateText firstTextNode = null;
        private StringBuilder textBuffer;

        public TextCatVisitor(Compiler compiler) {
            options = compiler.getCompilationContext().getOptions();
            pageInfo = compiler.getPageInfo();
        }

        @Override
        public void doVisit(Node n) throws JasperException {
            collectText();
        }

        /*
         * The following directives are ignored in text concatenation
         */

        @Override
        public void visit(Node.PageDirective n) throws JasperException {
        }

        @Override
        public void visit(Node.TagDirective n) throws JasperException {
        }

        @Override
        public void visit(Node.TaglibDirective n) throws JasperException {
        }

        @Override
        public void visit(Node.AttributeDirective n) throws JasperException {
        }

        @Override
        public void visit(Node.VariableDirective n) throws JasperException {
        }

        /*
         * Don't concatenate text across body boundaries
         */
        @Override
        public void visitBody(Node n) throws JasperException {
            super.visitBody(n);
            collectText();
        }

        @Override
        public void visit(Node.TemplateText n) throws JasperException {
            if ((options.getTrimSpaces() || pageInfo.isTrimDirectiveWhitespaces()) 
                    && n.isAllSpace()) {
                n.setText(EMPTY_TEXT);
                return;
            }

            if (textNodeCount++ == 0) {
                firstTextNode = n;
                textBuffer = new StringBuilder(n.getText());
            } else {
                // Append text to text buffer
                textBuffer.append(n.getText());
                n.setText(EMPTY_TEXT);
            }
        }

        /**
         * This method breaks concatenation mode.  As a side effect it copies
         * the concatenated string to the first text node 
         */
        private void collectText() {

            if (textNodeCount > 1) {
                // Copy the text in buffer into the first template text node.
                firstTextNode.setText(textBuffer.toString());
            }
            textNodeCount = 0;
        }

    }

    public static void concatenate(Compiler compiler, Node.Nodes page)
            throws JasperException {

        TextCatVisitor v = new TextCatVisitor(compiler);
        page.visit(v);

        // Cleanup, in case the page ends with a template text
        v.collectText();
    }
}
