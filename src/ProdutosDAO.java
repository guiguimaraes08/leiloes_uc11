import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    public void cadastrarProduto(ProdutosDTO produto) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=qwerqwer");
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();
            System.out.println("Produto cadastrado: " + produto.getNome());
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar produto no banco de dados: " + e.getMessage());
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public ProdutosDTO buscarProdutoPorNome(String nome) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ProdutosDTO produto = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=qwerqwer");
            String sql = "SELECT * FROM produtos WHERE nome = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new ProdutosDTO();
                produto.setId(rs.getLong("id")); // Ajuste para long
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produto no banco de dados: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return produto;
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=qwerqwer");
            String sql = "SELECT * FROM produtos";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id")); // Certifique-se de que ProdutosDTO tem este método
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar produtos: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return lista;
    }

    public void venderProduto(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=qwerqwer");
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao vender produto: " + e.getMessage());
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
