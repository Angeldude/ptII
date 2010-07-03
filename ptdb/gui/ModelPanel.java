package ptdb.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ptdb.common.dto.XMLDBModel;
import ptdb.kernel.bl.load.LoadManager;
import ptolemy.actor.gui.Configuration;
import ptolemy.actor.gui.PtolemyEffigy;
import ptolemy.util.MessageHandler;

///////////////////////////////////////////////////////////////////
//// ModelPanel

/**
 * An extended JPanel displaying a single model result.
 * Multiple ModelPanel objects will be displayed as search results.
 * This class is simply for uniform layout and behavior.
 *
 * @author Lyle Holsinger
 * @since Ptolemy II 8.1
 * @version $Id$
 * @Pt.ProposedRating red (lholsing)
 * @Pt.AcceptedRating red (lholsing)
 *
 */

public class ModelPanel extends JPanel {

    /**
     * Construct a panel associated with the search result.  The panel layout
     * is taken care of in the constructor.  A listener is added for _modelLink,
     * which is a button used to open the model in a new editing frame.
     *
     * @param dbModel
     *        The model returned as a search result.
     * @param configuration
     *        The configuration under which an effigy of this model would be generated.
     */
    public ModelPanel(XMLDBModel dbModel, Configuration configuration) {
     
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setAlignmentX(LEFT_ALIGNMENT);

        setMaximumSize(new Dimension(getMaximumSize().width,30));
        setMinimumSize(getMaximumSize());

        Border border = BorderFactory.createEmptyBorder(0, 3, 0, 0);
        setBorder(border);

        _modelName = dbModel.getModelName();
        _configuration = configuration;

        JLabel modelLabel = new JLabel("Model Name:");
        add(modelLabel);

        _loadCheck = new JCheckBox();
        _loadCheck.setSelected(false);
        add(_loadCheck);

        _modelLink = new JButton("<html><u>" + _modelName + "</html></u>");
        _modelLink.setForeground(Color.BLUE);
        _modelLink.setMaximumSize(getMinimumSize());

        add(_modelLink);

        _modelLink.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                _loadModel();

            }

        });

    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Get the model name.
     *
     * @return The model name.
     */
    public String getModelName() {

        return _modelName;

    }

    /** Get the value of the _loadCheck checkbox.
     * The method, isSelected(), returns true of false.
     *
     * @return The value of the _loadCheck checkbox.
     */
    public Boolean getValue() {

        return _loadCheck.isSelected();

    }

    /**
     * Create an effigy of the model and open it in a new editing frame.
     */
    private void _loadModel() {

        try {

            PtolemyEffigy effigy = LoadManager.loadModel(_modelName,
                    _configuration);

            if(effigy != null){
                
                effigy.showTableaux();
                
            } else {
                
                JOptionPane
                .showMessageDialog((Component) this,
                        "The specified model could " +
                        "not be found in the database.",
                        "Load Error",
                        JOptionPane.INFORMATION_MESSAGE, null);
                
            }

        } catch (Exception e) {

            MessageHandler.error("Cannot load the specified model. ", e);

        }

    }

    ///////////////////////////////////////////////////////////////////
    //                    private variables                        ////

    private JCheckBox _loadCheck;
    private JButton _modelLink;
    private String _modelName;
    private Configuration _configuration;

}
