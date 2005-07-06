/* A helper class for actor.lib.Uniform

@Copyright (c) 2005 The Regents of the University of California.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the
above copyright notice and the following two paragraphs appear in all
copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.

PT_COPYRIGHT_VERSION_2
COPYRIGHTENDKEY


*/
/*
 * Created on Apr 23, 2005
 *
 */
package ptolemy.codegen.c.actor.lib;

import java.util.HashSet;
import java.util.Set;

import ptolemy.codegen.kernel.CCodeGeneratorHelper;
import ptolemy.kernel.util.IllegalActionException;

/**
 * @author Man-Kit Leung
 * @version $Id$
 */
public class Uniform extends CCodeGeneratorHelper {

    /**
     * Constructor method for the Uniform helper
     * @param actor the associated actor
     */
    public Uniform(ptolemy.actor.lib.Uniform actor) {
        super(actor);
    }

    /**
     * Generate fire code
     * The method reads in <code>codeBlock1</code> from Uniform.c 
     * and puts into the given stream buffer
     * @param stream the given buffer to append the code to
     */
    public void  generateFireCode(StringBuffer stream)
        throws IllegalActionException {
        ptolemy.actor.lib.Uniform actor = 
            (ptolemy.actor.lib.Uniform) getComponent();
        
        CodeStream tmpStream = new CodeStream(this);
        tmpStream.appendCodeBlock("codeBlock1");

        stream.append(processCode(tmpStream.toString()));
    }

    /** Generate initialization code.
     *  This method reads the <code>setSeed</code> from Uniform.c,
     *  replaces macros with their values and returns the results.
     *  @return The processed code block.
     */
    public String generateInitializeCode()
        throws IllegalActionException {
        super.generateInitializeCode();
        ptolemy.actor.lib.Gaussian actor = 
            (ptolemy.actor.lib.Gaussian) getComponent();
        
        long seedValue;
        CodeStream tmpStream = new CodeStream(this);

        if (Long.parseLong( actor.seed.getExpression()) == 0) {
            tmpStream.append("$actorSymbol(seed) = time (NULL) + " + actor.hashCode());
        } else {
            tmpStream.appendCodeBlock("setSeedBlock");            
        }
        return processCode(tmpStream.toString());
    }
    
    /** Generate preinitialization code.
     *  This method reads the <code>preinitBlock</code> from Uniform.c,
     *  replaces macros with their values and returns the results.
     *  @return The processed code block.
     */
    public String generatePreinitializeCode() throws IllegalActionException {
        super.generatePreinitializeCode();

        CodeStream tmpStream = new CodeStream(this);
        tmpStream.appendCodeBlock("preinitBlock");

        return processCode(tmpStream.toString());
    }


    /** Get the files needed by the code generated for the
     *  Uniform actor.
     *  @return A set of strings that are names of the files
     *   needed by the code generated for the Uniform actor.
     */
    public Set getIncludingFiles() {
        Set files = new HashSet();
        files.add("\"time.h\"");
        files.add("\"math.h\"");

        return files;
    }
}
