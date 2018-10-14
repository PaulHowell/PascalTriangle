import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class Main extends JFrame {

	final int TOP_MARGIN = 30;
	final int BOTTOM_MARGIN = 5;
	final int SIDE_MARGIN = 5;

	final int MAX_MOD = 50;
	private int mod = 2;
	private Color[] colors = new Color[mod];

	private JPanel p = new JPanel(new BorderLayout());
	private JPanel colorPanel = null;

	public Main() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.setTitle("Pascal Triangle - mod "+mod);
		this.setSize(500, 500);

		JLabel lbl = new JLabel("mod: ");
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMaximumIntegerDigits(2);
		JFormattedTextField txtFld = new JFormattedTextField(nf);
		txtFld.setValue(mod);
		JButton btn = new JButton("update");
		btn.setVisible(true);
		btn.addActionListener(e -> {
			try {
				int newMod = Integer.parseInt(txtFld.getText());
				if (newMod < 2 || MAX_MOD < newMod) {
					JOptionPane.showMessageDialog(this, "2以上"+MAX_MOD+"以下の値を入力してください");
				}else updateMod(newMod);
			}catch (NumberFormatException exc) {
				JOptionPane.showMessageDialog(this, "不正な値です");
			}
		});
		txtFld.addActionListener(e -> btn.doClick());
		p.add(lbl, BorderLayout.LINE_START);
		p.add(txtFld, BorderLayout.CENTER);
		p.add(btn, BorderLayout.LINE_END);
		getContentPane().add(p);

		updateMod(mod);
	}

	public void paint(Graphics g){
		super.paint(g);
		int w = this.getWidth();
		int h = this.getHeight();
		//描画
		int half = w/2;
		int[] row = {1};
		for (int n=0; n<Math.min((h-TOP_MARGIN-BOTTOM_MARGIN)/2, (w-(2*SIDE_MARGIN))/2); n++){
			for(int k=0; k<=n; k++){
				g.setColor(colors[row[k]]);
				g.fillRect(half-(n+1)+(2*k), 2*n+TOP_MARGIN, 2, 2);
			}
			int[] nextRow = new int[n+2];
			for (int k=0; k<=n+1; k++){
				if (k==0 || k==n+1) nextRow[k] = 1;
				else nextRow[k] = (row[k-1]+row[k]) % mod;
			}
			row = nextRow;
		}
	}

	private void updateMod(int newMod){
		mod = newMod;
		this.setTitle("Pascal Triangle - mod "+mod);

		//色の配列
		colors = new Color[mod];
		for (int i=0; i<mod; i++) {
			if (i==0) colors[i] = Color.WHITE;
			else colors[i] = Color.getHSBColor((float)(i-1)/(mod-1), 1, 1);
		}

		//凡例表示
		if (colorPanel!=null) p.remove(colorPanel);
		colorPanel = new JPanel(new BorderLayout());
		colorPanel.add(new JLabel("凡例"), BorderLayout.PAGE_START);
		JPanel listPanel = new JPanel(new GridLayout(0, 2));
		for (int i=0; i<mod; i++) {
			JLabel lbl = new JLabel(i+": ");
			JLabel clrLbl = new JLabel("■");
			clrLbl.setForeground(colors[i]);
			listPanel.add(lbl);
			listPanel.add(clrLbl);
		}
		colorPanel.add(listPanel, BorderLayout.CENTER);
		p.add(colorPanel, BorderLayout.PAGE_END);

		repaint();
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
