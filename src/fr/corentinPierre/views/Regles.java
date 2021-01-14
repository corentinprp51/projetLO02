package fr.corentinPierre.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Regles extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Regles dialog = new Regles();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Regles() {
		setBounds(100, 100, 1260, 800);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scroll);
		
		JTextArea txtrRglePartieClassique = new JTextArea(16,58);
		contentPanel.add(txtrRglePartieClassique);
		txtrRglePartieClassique.setEditable(false);
		txtrRglePartieClassique.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtrRglePartieClassique.setText("R\u00E8gle partie Classique : \r\n\r\nAu d\u00E9but de la partie une carte victoire vous sera attribu\u00E9e, et a tour de r\u00F4le vous allez piocher une carte du deck !\r\n\r\n\r\nVous allez alors devoir placer la carte pioch\u00E9e sur le plateau.\r\nAvant OU apr\u00E8s avoir plac\u00E9 la carte piocher, vous pouvez d\u00E9placer une carte d\u00E9j\u00E0 pr\u00E9sente sur le plateau. \r\n\r\n\r\nMais attention vous devrez rescepter deux r\u00E8gles pour placer et d\u00E9placer des cartes :\r\n \t-Vous devez (de)placer votre carte sur une position adjacente a une autre carte d\u00E9j\u00E0 presente sur le plateau (La premi\u00E8re carte du jeu peut cependant \u00EAtre plac\u00E9 librement) \r\n\t-Apr\u00E8s que vous ailler (de)placer une carte la grille de jeu doit rester dans les limites (5x3 ou 3x5)\r\n\r\nLorsque vous d\u00E9placer une carte, vous pouvez briser des chaines et laisser des cartes seules, tant que les deux conditions plus haut sont toujours v\u00E9rifi\u00E9es !\r\n\r\n\r\nLa partie est termin\u00E9e quand la derni\u00E8re carte du deck a \u00E9t\u00E9 pioch\u00E9 et plac\u00E9e dans la grille !\r\n\r\nCalcul des scores : \r\nLe calcul du score va maintenant s'effectuer ! Reveler votre carte victoires \u00E0 votre adversaire !\r\nEn suivant votre carte victoires, regarder maintenant combien de cartes de m\u00EAme couleurs, de m\u00EAme type et de m\u00EAme remplissage vous avez r\u00E9ussi \u00E0 aligner. \r\nPlus le nombre de carte align\u00E9es est important plus vous gagner de point ! \r\n\r\n\r\nR\u00E8gle partie Expert :\r\n\r\nVous avez des parfaitement maitris\u00E9 les r\u00E8gles du shapeup Classiques ? Alors notre mode expert est fait pour vous ! \r\n\r\nVotre carte victoire n'est plus choisie al\u00E9atoirement au d\u00E9but de la partie, a la place vous allez recevoir une main de trois cartes.\r\nComme dans la version normale vous pouvez d\u00E9placer une carte du plateau et devez-y placer une carte de votre main ! \r\n\r\nAinsi lors de votre tour vous pouvez choisir quelle carte de votre main vous allez placer ! \r\n\r\nMais attention comme dans la version Classique, les deux r\u00E8gles ci-dessous doivent toujours \u00EAtre rescepter que se sois pour placer ou d\u00E9placer une carte : \r\n \t-Vous devez (de)placer votre carte sur une position adjacente a une autre carte d\u00E9j\u00E0 presente sur le plateau (la premi\u00E8re carte du jeu peut cependant \u00EAtre plac\u00E9e librement) \r\n\t-Apr\u00E8s que vous ailler (de)placer une carte la grille de jeu doit rester dans les limites (5x3 ou 3x5)\r\n\r\n\r\nM\u00EAme quand le deck est vide, la partie continue ! Elle se terminera quand il ne restera qu'une seule carte dans la main de chaque joueur !\r\nEt cette carte finale deviendra votre carte Victoire ! Jusqu'\u00E0 la derni\u00E8re seconde votre strat\u00E9gie peut changer ! \r\n\r\nCalcul des scores : \r\nLe calcul du score va maintenant s'effectuer ! Reveler votre carte victoires \u00E0 votre adversaire !\r\nEn suivant votre carte victoires, regarder maintenant combien de cartes de m\u00EAme couleurs, de m\u00EAme type et de m\u00EAme remplissage vous avez r\u00E9ussi \u00E0 aligner. \r\nPlus le nombre de cartes align\u00E9es est important plus vous gagner de point ! ");
		txtrRglePartieClassique.setBounds(10, 11, 1084, 661);
		//contentPanel.add(txtrRglePartieClassique);
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
