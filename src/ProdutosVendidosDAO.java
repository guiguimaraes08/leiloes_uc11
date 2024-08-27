
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosVendidosDAO {

    private Connection conn;

    public ProdutosVendidosDAO() {
        this.conn = new conectaDAO().connectDB();
    }

    public ArrayList<ProdutosVendidosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosVendidosDTO> lista = new ArrayList<>();
        String sql = "SELECT id, nome, valor FROM produtos_vendidos";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ProdutosVendidosDTO produto = new ProdutosVendidosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));

                lista.add(produto);
            }
            pstm.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
        }
        return lista;
    }
}
