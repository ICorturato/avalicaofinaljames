package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.AuditoriaProdutoModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.AuditoriaProdutoRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private AuditoriaProdutoRepository auditoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAll();
    }

    public List<ProdutoModel> listarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public ProdutoModel buscarPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public List<ProdutoModel> listarPorCategoria(Long idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);
    }

    public List<AuditoriaProdutoModel> listarHistoricoAtualizacoes() {
        return auditoriaRepository.findAllByOrderByDataAcaoDesc();
    }

    @Transactional
    public void salvar(ProdutoModel produto, String usuarioLogado) {
        if (produto.getQuantidade() == null || produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }

        if (produto.getIdProduto() == 0 && repository.existsByNome(produto.getNome())) {
            throw new IllegalArgumentException("Já existe um produto com este nome.");
        }

        LocalDateTime agora = LocalDateTime.now();

        if (produto.getIdProduto() == 0) {
            produto.setDataCadastro(agora);
            produto.setUsuarioCadastro(usuarioLogado);
            ProdutoModel produtoSalvo = repository.save(produto);
            registrarAuditoria(produtoSalvo, "CADASTRO", usuarioLogado, agora);
            return;
        }

        ProdutoModel produtoExistente = buscarPorId(produto.getIdProduto());
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setValor(produto.getValor());
        produtoExistente.setQuantidade(produto.getQuantidade());
        produtoExistente.setCategoria(produto.getCategoria());
        produtoExistente.setDataAlteracao(agora);
        produtoExistente.setUsuarioAtualizacao(usuarioLogado);

        ProdutoModel produtoSalvo = repository.save(produtoExistente);
        registrarAuditoria(produtoSalvo, "ALTERAÇÃO", usuarioLogado, agora);
    }

    @Transactional
    public void excluir(long id, String usuarioLogado) {
        ProdutoModel produto = buscarPorId(id);
        LocalDateTime agora = LocalDateTime.now();
        registrarAuditoria(produto, "DELETE", usuarioLogado, agora);
        repository.deleteById(id);
    }

    private void registrarAuditoria(ProdutoModel produto, String tipoAlteracao, String usuarioLogado, LocalDateTime dataAcao) {
        auditoriaRepository.save(new AuditoriaProdutoModel(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getQuantidade(),
                dataAcao,
                tipoAlteracao,
                usuarioLogado,
                dataAcao
        ));
    }
}
