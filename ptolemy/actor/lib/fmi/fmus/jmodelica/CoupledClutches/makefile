# Makefile for Java Ptolemy II Functional Mock-up Unit (FMU) 
#
# @Author: cxh (makefile only)
#
# @Version: $Id$
#
# @Copyright (c) 2014-2015 The Regents of the University of California.
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

FMU_NAME = CoupledClutches

OTHER_OTHER_FILES_TO_BE_JARED = \
	CoupledClutches.mo \
	CoupledClutchesToFMU

include ../fmus.mk

# To test this:
fmuCheck:
	if [ ! -d resources ]; then \
		mkdir resources; \
	fi
	# Need -z . so we can find the resources/ directory
	fmuCheck -z . -s 1.2 -f -h 0.01 -o fmuCheckResult.csv $(FMU_NAME).fmu

fmusdk:
	$(PTII)/bin/ptinvoke org.ptolemy.fmi.driver.FMUModelExchange CoupledClutches.fmu 1.2 0.01 0 c fmusdkResults.csv 

compare: fmuCheck fmusdk
