package com.fschinaider.helpdesk.services;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fschinaider.helpdesk.domain.Pessoa;
import com.fschinaider.helpdesk.domain.Tecnico;
import com.fschinaider.helpdesk.domain.dtos.TecnicoDTO;
import com.fschinaider.helpdesk.repositories.PessoaRepository;
import com.fschinaider.helpdesk.repositories.TecnicoRepository;
import com.fschinaider.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.fschinaider.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
