/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author gabri
 */
@Entity
@Table(name="TB_DOCE") 
@DiscriminatorValue(value = "D")
@PrimaryKeyJoinColumn(name="ID_PRODUTO", referencedColumnName = "ID")
public class Doce extends Produto{
    @Column(length = 15, name = "TXT_DATAFABRICACAO")
    @Temporal(TemporalType.DATE)
    private Date dataFrabricacao;
    @Column(length = 50, name = "TXT_DESCRICAO")
    private String descricao;

    public Date getDataFrabricacao() {
        return dataFrabricacao;
    }

    public void setDataFrabricacao(Date dataFrabricacao) {
        this.dataFrabricacao = dataFrabricacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
