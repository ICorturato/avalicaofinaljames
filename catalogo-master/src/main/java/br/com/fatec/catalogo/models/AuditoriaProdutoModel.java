package br.com.fatec.catalogo.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_AUDITORIA_PRODUTO")
public class AuditoriaProdutoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuditoria;

    private long idProduto;

    private String produto;

    private Integer estoque;

    private LocalDateTime dataAlteracao;

    private String tipoAlteracao;

    private String autorAcao;

    private LocalDateTime dataAcao;

    public AuditoriaProdutoModel() {
    }

    public AuditoriaProdutoModel(long idProduto, String produto, Integer estoque, LocalDateTime dataAlteracao,
                                 String tipoAlteracao, String autorAcao, LocalDateTime dataAcao) {
        this.idProduto = idProduto;
        this.produto = produto;
        this.estoque = estoque;
        this.dataAlteracao = dataAlteracao;
        this.tipoAlteracao = tipoAlteracao;
        this.autorAcao = autorAcao;
        this.dataAcao = dataAcao;
    }

    public long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getTipoAlteracao() {
        return tipoAlteracao;
    }

    public void setTipoAlteracao(String tipoAlteracao) {
        this.tipoAlteracao = tipoAlteracao;
    }

    public String getAutorAcao() {
        return autorAcao;
    }

    public void setAutorAcao(String autorAcao) {
        this.autorAcao = autorAcao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }
}
