import dao.ModeloCarroDAO;
import dao.MontadoraDAO;
import model.ModeloCarro;
import model.Montadora;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		MontadoraDAO montadoraDAO = new MontadoraDAO();
		ModeloCarroDAO modeloDAO = new ModeloCarroDAO();

		try {
			System.out.println("=== CADASTRO ===");

			
			Montadora montadora = new Montadora("Toyota", "Japão");
			montadoraDAO.salvar(montadora);
			System.out.println("Montadora salva: " + montadora);

			
			ModeloCarro corolla = new ModeloCarro("Corolla", "Híbrido", 122, montadora);
			modeloDAO.salvar(corolla);
			System.out.println("Modelo salvo: " + corolla);

			ModeloCarro yaris = new ModeloCarro("Yaris", "Flex", 110, montadora);
			modeloDAO.salvar(yaris);
			System.out.println("Modelo salvo: " + yaris);

			System.out.println("\\n=== LISTAGEM COM INNER JOIN ===");
			for (ModeloCarro modelo : modeloDAO.listarTodos()) {
				System.out.println(modelo);
				System.out.println("  Fabricante: " + modelo.getMontadora().getNome() + " | País: "
						+ modelo.getMontadora().getPaisSede());
			}

			System.out.println("\\n=== BUSCA POR ID ===");
			System.out.println("Montadora: " + montadoraDAO.buscarPorId(montadora.getId()));
			System.out.println("Modelo: " + modeloDAO.buscarPorId(corolla.getId()));

			System.out.println("\\n=== ATUALIZAÇÃO ===");
			corolla.setPotenciaCv(140);
			corolla.setTipoCombustivel("Híbrido Flex");
			System.out.println("Modelo atualizado? " + modeloDAO.atualizar(corolla));
			montadora.setPaisSede("Japão (sede global)");
			System.out.println("Montadora atualizada? " + montadoraDAO.atualizar(montadora));

			System.out.println("\\n=== EXCLUSÃO ===");
			
			System.out.println("Yaris removido? " + modeloDAO.deletar(yaris.getId()));
			System.out.println("Corolla removido? " + modeloDAO.deletar(corolla.getId()));
			System.out.println("Montadora removida? " + montadoraDAO.deletar(montadora.getId()));

			System.out.println("\\nDemonstração concluída.");
		} catch (SQLException e) {
			System.err.println("Falha na operação com o banco de dados: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Dados inválidos: " + e.getMessage());
		}
	}
}
