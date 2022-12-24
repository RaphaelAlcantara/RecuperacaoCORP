/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author angel
 */
@Embeddable
public class Endereco implements Serializable {
    
    @Column(name = "END_TXT_CEP", nullable = false)
    private String cep;
    
    @Column(name = "END_TXT_CIDADE", nullable = false)
    private String cidade;
    
    @Column(name = "END_TXT_BAIRRO", nullable = false)
    private String bairro;
    
    @Column(name = "END_NUMERO", nullable = false)
    private String nCasa;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getnCasa() {
        return nCasa;
    }

    public void setnCasa(String nCasa) {
        this.nCasa = nCasa;
    }
    
}
