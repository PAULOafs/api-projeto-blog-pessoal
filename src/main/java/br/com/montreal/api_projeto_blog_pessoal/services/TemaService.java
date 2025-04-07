package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.exceptions.AlReadyExistsException;
import br.com.montreal.api_projeto_blog_pessoal.exceptions.TemaNotFoundException;
import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.TemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemaService {

    private final TemaRepository temaRepository;

    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    @Transactional
    public Tema criarNovoTema(Tema tema) {
        validaTemaExistente(tema.getDescricao());
        tema.setDescricao(tema.getDescricao().trim());
        return temaRepository.save(tema);
    }

    @Transactional
    public Tema atualizarTemaPorId(Long id, Tema novoTema) {
        return temaRepository.findById(id)
                .map(tema -> {
                    tema.setDescricao(novoTema.getDescricao());
                    return temaRepository.save(tema);
                })
                .orElseThrow(() -> new TemaNotFoundException("TEMA NAO ENCONNTRADO"));
    }

    @Transactional
    public MensagemDTO deletarTemaPorId(Long id) {
        if (!temaRepository.existsById(id)) {
            throw new TemaNotFoundException("TEMA NAO ENCONTRAO");
        }
        temaRepository.deleteById(id);
        return MensagemDTO.builder()
                .mensagem("TEMA DELETADO")
                .build();
    }

    public List<Tema> listarTodosOsTemas() {
        return temaRepository.findAll();
    }

    private void validaTemaExistente(String descricao) {
        if (temaRepository.existsByDescricao(descricao)) {
            throw new AlReadyExistsException("TEMA JA EXISTE");
        }
    }
}
