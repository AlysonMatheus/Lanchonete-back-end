package alysondev.lanchonete.controller;


import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController()
@RequestMapping("/login")
public class LoginController{
    @Autowired
    private LoginService loginService;
@PostMapping("/entrar")
    public ResponseEntity<?> logar(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            LoginResponseDTO responseDTO = loginService.login(loginRequestDTO);
            return ResponseEntity.ok(responseDTO);
        }catch (RuntimeException e){
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
