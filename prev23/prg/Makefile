JAVA            = java --enable-preview
ANTLRDIR        = ../lib/antlr-4.11.1-complete.jar
XSLTPROC	= xsltproc
RM              = 'rm' -fr

# lexan synan seman codegen debuginfo
PHASE		= debuginfo
LOG		= all
NREGS		= 44

.PHONY	: %
%	: %.p23
	$(RM) -fr $@.*.xml $@.*.html
	if $(JAVA) -cp ../bin:../src:$(ANTLRDIR) prev23.Compiler --xsl=../lib/xsl/ --logged-phase=$(LOG) --target-phase=$(PHASE) --num-regs=$(NREGS) $< ; then for XML in `'ls' -1 $@.*.xml` ; do $(XSLTPROC) $$XML -o `basename $$XML xml`html; done ; fi

.PHONY  : clean
clean   :
	$(RM) *~
	$(RM) *xml *html

