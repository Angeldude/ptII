# Makefile for the Ptolemy Viptos runtime configuration 
#
# @Authors: Christopher Brooks, based on a file by Thomas M. Parks
#
# @Version: $Id$
#
# @Copyright (c) 2005-2009 The Regents of the University of California.
# All rights reserved.
#
# Permission is hereby granted, without written agreement and without
# license or royalty fees, to use, copy, modify, and distribute this
# software and its documentation for any purpose, provided that the
# above copyright notice and the following two paragraphs appear in all
# copies of this software.
#
# IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
# FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
# ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
# THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
# SUCH DAMAGE.
#
# THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
# INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
# PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
# CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
# ENHANCEMENTS, OR MODIFICATIONS.
#
# 						PT_COPYRIGHT_VERSION_2
# 						COPYRIGHTENDKEY

ME =		ptolemy/configs/viptos

# There is no demo directory, put demos in the corresponding domain
# directory so that we can find them if we are looking at domains.
# An alternative would be to split the demo subdirectory into
# directories by domain
DIRS =		#demo

# Root of the Java directory
ROOT =		../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	viptos
PTCLASSJAR =	$(PTPACKAGE).jar

JSRCS =


OTHER_FILES_TO_BE_JARED = \
	about.htm \
	authors.htm \
	configuration.xml \
	copyright.htm \
	demos.htm \
	directors.xml \
	docs.htm \
	help.htm \
	intro.htm \
	install.htm \
	library.xml \
	makefile \
	overview.htm \
	viptos.gif \
	viptosModels.txt \
	welcomeWindow.xml \
	whatsNew.htm

EXTRA_SRCS =	$(JSRCS) $(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES =

JCLASS = $(JSRCS:%.java=%.class)

all: jclass viptosModels.txt
	@echo "nothing need be done for make all in this directory"

install: all jars
	@echo "nothing need be done for make install in this directory"

# viptosModels.txt contains a list of model file names
# Use AUXCLASSPATH so that we work under codecoverage with JavaScope
viptosModels.txt: $(ROOT)/ptolemy/actor/gui/HTMLAbout.class intro.htm
	"$(JAVA)" "-Dptolemy.ptII.dir=$(PTII)" \
		-classpath "$(CLASSPATH)$(CLASSPATHSEPARATOR)$(AUXCLASSPATH)" \
		ptolemy.actor.gui.HTMLAbout intro.htm $@

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
