package com.webex.webex.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webex.webex.model.Pedido;
import com.webex.webex.repository.ClienteRepository;
import com.webex.webex.repository.PedidoRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
public class PedidoController {
    @Autowired
    PedidoRepository bd;
    ClienteRepository bd_Cliente;

    @GetMapping ("api/pedido/listar")
    public List<Pedido> listar(){
        return bd.findAll();
    }

    @GetMapping("api/pedido/listar/{id}")
    public Pedido retornaPedido(@PathVariable("id") int id){
        if(bd.existsById(id)){
            return bd.findById(id).get();
        }else{
            return null;
        }
    }

    @PostMapping ("api/pedido")
    public String gravar(@RequestBody Pedido obj){
        bd.save(obj);
        bd_Cliente.findById(obj.getIdCliente()).ifPresent(cliente -> {
            if (cliente.getPedidos() == null) {
                cliente.setPedidos(new ArrayList<>());
            }
            cliente.getPedidos().add(obj);
            bd_Cliente.save(cliente);
        });
        return "Pedido gravado com sucesso!";
    }

    @PutMapping ("api/pedido/atualizar/{id}")
    public String atualizar(@PathVariable("id") int id, @RequestBody Pedido obj){
        if(bd.existsById(obj.getId())){
            bd.save(obj);
            return "Pedido atualizado com sucesso!";
        } else {
            return "Pedido não encontrado!";
        }       
    }

    @DeleteMapping ("api/pedido/deletar/{id}")
    public String deletar(@PathVariable("id") int id){
        bd.deleteById(id);
        return "Pedido deletado com sucesso!";
    }
}
