package repository;

import entities.Gravacao;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final File dataDir;
    private final File dbFile;
    private final String dbUrl;
    private final boolean novoBanco;

    public Database() {
        File projectRoot = encontrarRaizDoProjeto();
        this.dataDir = new File(projectRoot, "data");
        if (!this.dataDir.exists()) this.dataDir.mkdirs();
        this.dbFile = new File(this.dataDir, "caregate.db");
        this.novoBanco = !this.dbFile.exists();
        this.dbUrl = "jdbc:sqlite:" + this.dbFile.getAbsolutePath();
        criarTabelas();
    }

    public Connection conectar() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite nao encontrado em lib/sqlite-jdbc.jar", e);
        }

        Connection conn = DriverManager.getConnection(dbUrl);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public File getDataDir() {
        return dataDir;
    }

    public File getDbFile() {
        return dbFile;
    }

    public boolean isNovoBanco() {
        return novoBanco;
    }

    public void limparDados() {
        try (Connection conn = conectar();
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM consultas");
            st.executeUpdate("DELETE FROM pacientes");
            st.executeUpdate("DELETE FROM medicos");
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel limpar o banco SQLite.", e);
        }
    }

    private void criarTabelas() {
        try (Connection conn = conectar();
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS medicos (" +
                    "crm TEXT PRIMARY KEY, " +
                    "tipo TEXT NOT NULL, " +
                    "nome TEXT NOT NULL, " +
                    "especialidade TEXT NOT NULL, " +
                    "idade INTEGER NOT NULL, " +
                    "valor_consulta INTEGER NOT NULL" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS pacientes (" +
                    "cpf TEXT PRIMARY KEY, " +
                    "nome TEXT NOT NULL, " +
                    "endereco TEXT NOT NULL, " +
                    "telefone TEXT NOT NULL" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS consultas (" +
                    "id INTEGER PRIMARY KEY, " +
                    "cpf TEXT NOT NULL, " +
                    "crm TEXT NOT NULL, " +
                    "data_hora TEXT NOT NULL, " +
                    "diagnostico TEXT, " +
                    "FOREIGN KEY (cpf) REFERENCES pacientes(cpf) ON DELETE CASCADE, " +
                    "FOREIGN KEY (crm) REFERENCES medicos(crm) ON DELETE CASCADE" +
                    ")");
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel iniciar o SQLite. Verifique se lib/sqlite-jdbc.jar existe.", e);
        }
    }

    private File encontrarRaizDoProjeto() {
        File userDir = new File(System.getProperty("user.dir"));
        if (userDir.exists()) {
            boolean pareceProjeto = new File(userDir, "src").exists()
                    || new File(userDir, "run.bat").exists()
                    || new File(userDir, ".git").exists();
            if (pareceProjeto) return userDir;
        }

        try {
            File codeLocation = new File(Gravacao.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File candidate = codeLocation.isFile() ? codeLocation.getParentFile() : codeLocation;
            String name = candidate.getName().toLowerCase();
            if ((name.equals("build") || name.equals("out") || name.equals("classes")) && candidate.getParentFile() != null) {
                return candidate.getParentFile();
            }
            return candidate;
        } catch (URISyntaxException e) {
            return new File(".");
        }
    }
}
