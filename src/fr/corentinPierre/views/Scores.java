package fr.corentinPierre.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Fen�tre affichant la r�gle de comptage des points pour le score final
 * <br>H�rite de JDialog
 * @author Corentin
 * @author Pierre
 *
 */
public class Scores extends JDialog {

	private final JPanel contentPanel = new JPanel();


	/**
	 * Cr�er la fen�ntre de r�gles
	 * <br>Lie un �v�nement de clic au bouton ok pour fermer la fen�tre lorsque ce dernier est cliqu�.
	 */
	public Scores() {
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(42, 11, 600, 300);
			contentPanel.add(lblNewLabel);
			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResource("/score.PNG"));
			} catch (IOException e) {
			    e.printStackTrace();
			}
			Image dimg = img.getScaledInstance(600, 300,Image.SCALE_SMOOTH);
			lblNewLabel.setIcon(new ImageIcon(dimg));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}
}
