import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;

public class Inicio {

	private JFrame frmDatabaseConnecion;
	static Connection con;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio window = new Inicio();
					window.frmDatabaseConnecion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Inicio() {
		initialize();
	}
	/**
	 * Metodos para la apertura o cierre de la conexion con el servidor.
	 */
	static public void conectar(){
        try{
            con = Conexion.crearConexion();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error en la conexion", "Aviso", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la conexion");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (con != null){
            JOptionPane.showMessageDialog(null, "Conexion establecida...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            System.out.print("Conexion establecida...");
            try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
    static public void desconectar(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Se desconecto de la base de datos...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Se desconecto de la base de datos...");
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDatabaseConnecion = new JFrame();
		//frmDatabaseConnecion.setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/addons/icon.png")));
		frmDatabaseConnecion.getContentPane().setBackground(new Color(221, 160, 221));
		frmDatabaseConnecion.getContentPane().setForeground(new Color(0, 0, 0));
		frmDatabaseConnecion.setTitle("Westminster Kennel Club - Conexion con la Base de Datos");
		frmDatabaseConnecion.setBounds(100, 100, 392, 373);
		frmDatabaseConnecion.setLocationRelativeTo(null);
		frmDatabaseConnecion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDatabaseConnecion.getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("Salir");
		btnExit.setFont(new Font("Arial", Font.PLAIN, 11));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(300, 300, 66, 23);
		frmDatabaseConnecion.getContentPane().add(btnExit);
		
		JLabel lblLogo = new JLabel("");
		//lblLogo.setIcon(new ImageIcon(Inicio.class.getResource("/addons/logo.png")));
		
		lblLogo.setBounds(10, 0, 365, 224);
		frmDatabaseConnecion.getContentPane().add(lblLogo);
		
		JButton btnConectarse = new JButton("Conectarse");
		btnConectarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inicio.conectar();
				if (con != null) {
					frmDatabaseConnecion.setVisible(false);
					Perrocampeon frame = new Perrocampeon();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		});
		btnConectarse.setFont(new Font("Arial", Font.PLAIN, 16));
		btnConectarse.setBounds(115, 235, 140, 59);
		frmDatabaseConnecion.getContentPane().add(btnConectarse);
	}
}