public class MineSweeperImpl implements MineSweeper {

	int N;
	int M;
	boolean bombs[][];
	boolean setUp;

	@Override
	public void setMineField(String mineField) throws IllegalArgumentException {
		String[] rows = mineField.split("\n");

		if (rows.length <= 1) {
			throw new IllegalArgumentException("Not enough rows (minimum 2)");
		}

		N = rows.length;
		M = rows[0].length();

		for (String row : rows) {
			if (row.length() != M) {
				throw new IllegalArgumentException("Rows have different lengths");
			}
		}

		bombs = new boolean[N][M];

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				switch (rows[i].charAt(j)) {
				case '*':
					bombs[i][j] = true;
					break;
				case '.':
					bombs[i][j] = false;
					break;
				default:
					throw new IllegalArgumentException("illegal character");
				}
			}
		}
		setUp = true;
	}

	@Override
	public String getHintField() throws IllegalStateException {
		if (!setUp)
			throw new IllegalStateException("mine-field has not been initialised");

		String[] rows = new String[N];
		int hintField[][] = new int[N + 2][N + 2];

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				if (bombs[i][j]) {
					hintField[i][j] += 1;
					hintField[i][j + 1] += 1;
					hintField[i][j + 2] += 1;

					hintField[i + 1][j] += 1;
					hintField[i + 1][j + 2] += 1;

					hintField[i + 2][j] += 1;
					hintField[i + 2][j + 1] += 1;
					hintField[i + 2][j + 2] += 1;
				}
			}
		}

		for (int i = 0; i < N; ++i) {
			String row = "";
			for (int j = 0; j < M; ++j) {
				if (bombs[i][j])
					row += "*";
				else
					row += hintField[i + 1][j + 1];
			}
			rows[i] = row;
		}
		return String.join("\n", rows);
	}

	public static void main(String[] argv) {
		MineSweeper ms = new MineSweeperImpl();
		ms.setMineField("*...\n..*.\n....");
		System.out.println(ms.getHintField());
	}
}
