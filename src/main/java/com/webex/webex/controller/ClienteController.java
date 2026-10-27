package com.webex.webex.controller;

import java.util.List;

import com.webex.webex.model.Cliente;
import com.webex.webex.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping("api/cliente/login")
    public ResponseEntity<?> login(@RequestBody Cliente loginCliente) {
        Optional<Cliente> cliente = bd.realizarLogin(loginCliente.getEmail(), loginCliente.getSenha());
        
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get()); // Login bem-sucedido
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
    }

    @PostMapping("api/cliente/rec-senha")
    public ResponseEntity<?> solicitarRecuperacao(@RequestBody Cliente dto) {
        Optional<Cliente> clienteOpt = bd.findByEmail(dto.getEmail());
        
        if (clienteOpt.isEmpty()) {
            // Por segurança, muitas vezes retornamos OK mesmo se o e-mail não existir para evitar enumeração de usuários
            return ResponseEntity.ok("Se o e-mail estiver cadastrado, as instruções foram enviadas.");
        }

        Cliente cliente = clienteOpt.get();
        
        // Gera um token aleatório único
        String token = UUID.randomUUID().toString();
        cliente.setToken(token);
        cliente.setTokenExpiracao(LocalDateTime.now().plusHours(1)); // Token válido por 1 hora
        
        bd.save(cliente);

        System.out.println("TOKEN DE RECUPERAÇÃO GERADO PARA " + cliente.getEmail() + ": " + token);

        return ResponseEntity.ok("E-mail de recuperação enviado com sucesso.");
    }

    @PostMapping("api/cliente/reset-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Cliente dto) {
        Optional<Cliente> clienteOpt = bd.findByToken(dto.getToken());

        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
        }

        Cliente cliente = clienteOpt.get();

        // Verifica se o token expirou
        if (cliente.getTokenExpiracao().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expirado.");
        }

        // Atualiza a senha e limpa o token usado
        // (Lembre-se de criptografar a senha com BCrypt em produção!)
        cliente.setSenha(dto.getSenha());
        cliente.setToken(null);
        cliente.setTokenExpiracao(null);

        bd.save(cliente);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
