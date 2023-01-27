//cliente

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Rapha
 */


@Entity
@Table(name = "TB_CLIENTE")

@NamedQuery(
        name = "Cliente.consultaClientePorNome",
        query = "SELECT c FROM Cliente c WHERE c.nome = :nome"
)

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "dono", cascade = CascadeType.ALL)
    private CartaoCredito cartao;
    
    @Column(name = "TXT_CPF", nullable = false, length = 14, unique = true)
    private String cpf;
    
    @Column(name = "TXT_NOME", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "TXT_EMAIL", nullable = false, length = 50)
    private String email;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    private Date dataNascimento;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<Venda> vendas;
    
    @ElementCollection 
    @CollectionTable(name = "TB_CONTATOS", 
            joinColumns = @JoinColumn(name = "ID_CLIENTE", nullable = false))
    @Column(name = "TXT_NUM_CONTATOS", nullable = false, length = 20)
    private Collection<String> contatos;
    
    @Embedded
    protected Endereco endereco;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartaoCredito getCartao() {
        return cartao;
    }

    public void setCartao(CartaoCredito cartao) {
        this.cartao = cartao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Collection<String> getContatos() {
        return contatos;
    }
    
    public void addContato(String contato) {
        if (contatos == null) {
            contatos = new HashSet<>();
        }
        contatos.add(contato);
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Venda venda) {
        if(this.vendas == null){
            this.vendas = new ArrayList<>();
        }
        
        this.vendas.add(venda);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

     @Override
    public boolean equals(Object object) {

        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    
}
