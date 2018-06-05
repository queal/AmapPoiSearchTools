package per.queal.win;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import per.queal.pojo.JTextAreaOutputStream;
import per.queal.service.AmapPoiService;
import per.queal.xls.ExcelUtils;
import per.queal.xls.RowData;

public class MainWin extends JFrame {

	private JPanel contentPane;
	private JTextField tf_adCode;
	private JTextField tf_poiCode;
	private JTextField tf_filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin frame = new MainWin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("城市编号：");
		label.setBounds(10, 10, 72, 15);
		contentPane.add(label);

		tf_adCode = new JTextField();
		tf_adCode.setBounds(92, 7, 226, 21);
		contentPane.add(tf_adCode);
		tf_adCode.setColumns(10);

		JLabel label_1 = new JLabel("类别编码：");
		label_1.setBounds(10, 35, 72, 15);
		contentPane.add(label_1);

		tf_poiCode = new JTextField();
		tf_poiCode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String adCode = tf_adCode.getText().trim();
				String poiCode = tf_poiCode.getText().trim();
				if (StringUtils.isNotBlank(adCode)
						&& StringUtils.isNotBlank(poiCode)) {
					tf_filePath.setText("E:\\"
							+ adCode
							+ "-"
							+ poiCode
							+ "-"
							+ DateFormatUtils.format(new Date(),
									"yyyyMMddHHmmss") + ".xlsx");
				}

			}
		});
		tf_poiCode.setBounds(92, 32, 226, 21);
		contentPane.add(tf_poiCode);
		tf_poiCode.setColumns(10);

		JLabel lblNewLabel = new JLabel("保存地址：");
		lblNewLabel.setBounds(10, 60, 72, 15);
		contentPane.add(lblNewLabel);

		tf_filePath = new JTextField();
		tf_filePath.setBounds(92, 57, 226, 21);
		contentPane.add(tf_filePath);
		tf_filePath.setColumns(10);

		JButton btn_save = new JButton("保存");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String adCode = tf_adCode.getText().trim();
				final String poiCode = tf_poiCode.getText().trim();
				final String filePath = tf_filePath.getText().trim();

				if (StringUtils.isBlank(adCode)) {
					JOptionPane.showMessageDialog(null, "城市编码不能为空!");
					return;
				}

				if (StringUtils.isBlank(poiCode)) {
					JOptionPane.showMessageDialog(null, "类别编码不能为空!");
					return;
				}
				if (StringUtils.isBlank(filePath)) {
					JOptionPane.showMessageDialog(null, "保存地址不能为空!");
					return;
				}

				final File file = new File(filePath);
				if (file.isDirectory()) {
					JOptionPane.showMessageDialog(null, "保存地址不能为目录!");
					return;
				}

				new Thread(new Runnable() {
					@Override
					public void run() {
						List<RowData> rowDataList = AmapPoiService
								.queryPoiData(adCode, poiCode);

						System.out.println("正在保存数据, 共 " + rowDataList.size()
								+ "条");
						ExcelUtils.writeLine(file, rowDataList);
						System.out.println("保存完成!");
					}
				}).start();

			}
		});
		btn_save.setBounds(225, 228, 93, 23);
		contentPane.add(btn_save);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 308, 130);
		contentPane.add(scrollPane);

		JTextArea ta_console = new JTextArea();
		scrollPane.setViewportView(ta_console);

		JTextAreaOutputStream out = new JTextAreaOutputStream(ta_console);
		System.setOut(new PrintStream(out));

	}
}
