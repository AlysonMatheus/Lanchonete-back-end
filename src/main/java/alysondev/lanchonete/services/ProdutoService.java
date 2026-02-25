package alysondev.lanchonete.services;


import alysondev.lanchonete.dtos.response.ProdutoResponseDTO;
import alysondev.lanchonete.dtos.request.ProdutoRequestDTO;
import alysondev.lanchonete.entity.Produto;
import alysondev.lanchonete.execption.ProdutoNaoEncontradoException;
import alysondev.lanchonete.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO criar(ProdutoRequestDTO produtoRequestDTO) {

        Produto produto = new Produto(produtoRequestDTO);
        produto.setAtivo(true);
        produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto);
    }
    @Transactional
    public void desativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
        produto.setAtivo(false);


    }
    @Transactional
    public void ativarProduto(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoException("Produto não econtrado"));
        produto.setAtivo(true);
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAllByAtivoTrue().stream().map(ProdutoResponseDTO::new).toList();


    }

    public ProdutoResponseDTO editar(Long id, ProdutoRequestDTO produtoRequestDTO) {
      Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoException("Produto não encontrado"));
     produto.atualizarDados(produtoRequestDTO);

     return new ProdutoResponseDTO(produtoRepository.save(produto));


    }
}
