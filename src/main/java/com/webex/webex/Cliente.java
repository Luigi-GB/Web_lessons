package com.webex.webex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String documento;
    private String logradouro;
    private String senha;

    
}
