package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.exceptions.AlReadyExistsException;
import br.com.montreal.api_projeto_blog_pessoal.exceptions.TemaNotFoundException;
import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.PostagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostagemService {

    private final PostagemRepository postagemRepository;

    public PostagemService(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }

    @Transactional
    public Postagem criarNovaPostagem(Postagem postagem) {
        validarPostagemExistente(postagem.getTitulo());
        return postagemRepository.save(postagem);
    }

    @Transactional
    public Postagem atualizarPostagemPostagemPorId(Long id, Postagem novaPostagem) {
        return postagemRepository.findById(id)
                .map(postagem -> {
                    postagem.setTitulo(novaPostagem.getTitulo());
                    postagem.setTexto(novaPostagem.getTexto());
                    postagem.setTema(novaPostagem.getTema());
                    return postagemRepository.save(postagem);
                })
                .orElseThrow(() -> new TemaNotFoundException("POSTAGEM NAO ENCONTRADA"));
    }

    @Transactional
    public MensagemDTO deletarPostagemPorId(Long id) {
        if (!postagemRepository.existsById(id)) {
            throw new TemaNotFoundException("POSTAGEM NAO ENCONTRADA");
        }
        postagemRepository.deleteById(id);
        return MensagemDTO.builder()
                .mensagem("POSTAGEM DELETADA")
                .build();
    }

    public List<Postagem> listarTodasPostagens() {
        return postagemRepository.findAll();
    }

    public List<Postagem> filtrarPostagensPorTemaOuUsuario(Long idTema, Long idUsuario) {
        if (idTema != null && idUsuario != null) {
            return postagemRepository.findByTemaIdAndUsuarioId(idTema, idUsuario);
        } else if (idTema != null) {
            return postagemRepository.findByTemaId(idTema);
        } else if (idUsuario != null) {
            return postagemRepository.findByUsuarioId(idUsuario);
        } else {
            return listarTodasPostagens();
        }
    }

    private void validarPostagemExistente(String titulo) {
        if (postagemRepository.existsByTitulo(titulo)) {
            throw new AlReadyExistsException("POSTAGEM JA EXISTE");
        }
    }
}
