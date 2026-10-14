package com.webex.webex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ClienteRepository bd;

    @GetMapping ("api/cliente/listar")
    public List<Cliente> listar(){
        return bd.findAll();
    }

    @GetMapping("api/cliente/listar/{id}")
    public Cliente retornaCliente(@PathVariable("id") int id){
        if(bd.existsById(id)){
            return bd.findById(id).get();
        }else{
            return null;
        }
    }

    @PostMapping ("api/cliente")
    public String gravar(@RequestBody Cliente obj){
        bd.save(obj);
        return "Cliente gravado com sucesso!";
    }

    @PutMapping ("api/cliente/atualizar/{id}")
    public String atualizar(@PathVariable("id") int id, @RequestBody Cliente obj){
        if(bd.existsById(obj.getId())){
            bd.save(obj);
            return "Cliente atualizado com sucesso!";
        } else {
            return "Cliente não encontrado!";
        }       
    }

    @DeleteMapping ("api/cliente/deletar/{id}")
    public String deletar(@PathVariable("id") int id){
        bd.deleteById(id);
        return "Cliente deletado com sucesso!";
    }

}
