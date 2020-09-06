import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IMFrame extends JFrame implements ActionListener
{
    private IMPane myImagePane;
    private JComboBox myProcessPopup;
    private JButton loadFileButton;
    private JButton swapButton;
    private JButton doActionNowButton;
    private JButton saveButton;


    public IMFrame()
    {
        super("Image Management");
        setSize(800,800);
        setResizable(true);
        buildInterface();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void buildInterface()
    {
        getContentPane().setLayout(new BorderLayout());
        myImagePane = new IMPane();

        getContentPane().add(myImagePane,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();


        loadFileButton = new JButton("Load Source");
        loadFileButton.addActionListener(this);
        bottomPanel.add(loadFileButton);


        myProcessPopup = new JComboBox(myImagePane.getImageActions());
        myProcessPopup.addActionListener(this);
        bottomPanel.add(myProcessPopup);

        doActionNowButton = new JButton("↻");
        doActionNowButton.addActionListener(this);
        bottomPanel.add(doActionNowButton);

        swapButton = new JButton("⬄");
        swapButton.addActionListener(this);
        bottomPanel.add(swapButton);

        saveButton = new JButton("Save Result");
        saveButton.addActionListener(this);
        bottomPanel.add(saveButton);


        getContentPane().add(bottomPanel,BorderLayout.SOUTH);


    }


    public void actionPerformed(ActionEvent aEvt)
    {
        if (aEvt.getSource() == myProcessPopup || aEvt.getSource() == doActionNowButton)
            myImagePane.performProcessForIndex(myProcessPopup.getSelectedIndex());

        if (aEvt.getSource() == swapButton)
            myImagePane.swap();

        if (aEvt.getSource() == loadFileButton)
            myImagePane.load();

        if (aEvt.getSource() == saveButton)
            myImagePane.save();
    }

}
