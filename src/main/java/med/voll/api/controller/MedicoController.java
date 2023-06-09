package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author screamersam
 */

@RestController
@RequestMapping("medicos")
public class MedicoController {
    
    @Autowired
    private MedicoRepository repository;
    
    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        
        repository.save(new Medico(dados));
    }
    
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10) Pageable pageable) {
        
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }
    
    @PutMapping 
    @Transactional
    public void atualisar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        
        //repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
