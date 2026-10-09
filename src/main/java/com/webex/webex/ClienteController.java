package com.webex.webex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
public class ClienteController {
    private List<Cliente> listaClientes= new ArrayList<>();

    @GetMapping ("api/cliente/listar")
    public List<Cliente> listar(){
        return listaClientes;
    }

    @GetMapping("api/cliente/listar/{id}")
    public Cliente retornaCliente(@PathVariable("id") int id){
        return listaClientes.get(id);
    }

    @PostMapping ("api/cliente")
    public String gravar(@RequestBody Cliente obj){
        listaClientes.add(obj);
        return "Cliente gravado com sucesso!";
    }

    @PutMapping ("api/cliente/atualizar/{id}")
    public String atualizar(@PathVariable("id") int id, @RequestBody Cliente obj){
        listaClientes.set(id, obj);
        return "Cliente atualizado com sucesso!";
    }

    @DeleteMapping ("api/cliente/deletar/{id}")
    public String deletar(@PathVariable("id") int id){
        listaClientes.remove(id);
        return "Cliente deletado com sucesso!";
    }

}
