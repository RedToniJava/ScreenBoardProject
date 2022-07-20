import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddImageButton {
    private final JButton addImageButton = new JButton("Add image");
    private JFileChooser fileImageChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    private File selectedFile;

   public File getSelectedFile(){
       return selectedFile;
   }

    public JButton getAddImageButton() {
        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int returnValue = fileImageChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileImageChooser.getSelectedFile();
                }


            }
        });
        return addImageButton;
    }

}
